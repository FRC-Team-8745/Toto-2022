package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants.Constants;

public class Shooter extends SubsystemBase {

	private double targetRPM = 0;
	private double calculatedPower;
	private boolean isStopped;
	private boolean isReady;

	public Shooter() {
	}

	@Override
	// This method will be called once per scheduler run
	public void periodic() {
		// Shooter status
		SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
		SmartDashboard.putBoolean("Shooter Ready", (shooter.getRPM() > 3000));

		calculatedPower = (targetRPM - this.shooter.getRPM()) * 0.2;

		if ((targetRPM != 0) && (!isStopped))
			shooter.set(calculatedPower);
		else if ((targetRPM == 0) || (isStopped))
			shooter.stop();
	}

	private BrushlessNEO shooter = new BrushlessNEO(3, true);
	private Spark loader = new Spark(0);

	public void shooterInit() {
		loader.setInverted(true);
		shooter.setRamp(1);
	}

	public void setRPM(double targetRPM) {
		this.targetRPM = targetRPM;
	}

	private boolean isReady() {
		if (Math.abs(shooter.getRPM() - Constants.kShooterTargetRPM) < Constants.kShooterAllowedError)
			isReady = true;
		else
			return false;

		return isReady;

	}

	private boolean shot() {
		if (isReady && (shooter.getRPM() < (Constants.kShooterTargetRPM - 500))) {
			isReady = false;
			return true;
		} else
			return false;
	}

	public void unloadCargo() {
		// Unload a single ball from the loader & shooter
		new SequentialCommandGroup(
				new InstantCommand(() -> this.loader.set(-1)),
				new InstantCommand(() -> this.shooter.set(-0.2)),
				new WaitCommand(2),
				new WaitUntilCommand(this::isReady),
				new InstantCommand(() -> this.loader.stopMotor()),
				new InstantCommand(() -> this.shooter.stop()));
	}

	public void loadSingle() {
		// Load a single ball to a point directly before the shooter
		new SequentialCommandGroup(
				new InstantCommand(() -> this.loader.set(1)),
				new WaitCommand(1.7),
				new InstantCommand(() -> this.loader.stopMotor()));
	}

	public void shootSingle() {
		// Shoot a single ball
		new SequentialCommandGroup(
				new InstantCommand(() -> isStopped = false),
				new InstantCommand(() -> this.setRPM(Constants.kShooterTargetRPM)),
				new WaitUntilCommand(this::isReady),
				new InstantCommand(() -> this.loader.set(1)),
				new WaitUntilCommand(this::shot),
				new InstantCommand(() -> this.loader.stopMotor()),
				new InstantCommand(() -> isStopped = true));
	}

	public void shootDouble() {
		// Shoot two balls
		new SequentialCommandGroup(
				new InstantCommand(() -> isStopped = false),
				new InstantCommand(() -> this.setRPM(Constants.kShooterTargetRPM)),
				new WaitUntilCommand(this::isReady),
				new InstantCommand(() -> this.loader.set(1)),
				new WaitUntilCommand(this::shot),
				new InstantCommand(() -> this.loader.stopMotor()),
				new InstantCommand(() -> this.setRPM(Constants.kShooterTargetRPM)),
				new WaitUntilCommand(this::isReady),
				new InstantCommand(() -> this.loader.set(1)),
				new WaitUntilCommand(this::shot),
				new InstantCommand(() -> this.loader.stopMotor()),
				new InstantCommand(() -> isStopped = true));
	}
}
