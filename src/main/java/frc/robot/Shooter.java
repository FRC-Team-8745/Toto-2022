package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;

public class Shooter extends SubsystemBase {
	// Shooter speed (in RPM)
	private final double kFenderSpeed = 3000;
	private final double kbatteryMax = 13;
	private final double kmaxRPM = 5500;

	public static BrushlessNEO shooter = new BrushlessNEO(3, true);

	@Override
	public void periodic() {
		// Shooter status
		SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
		SmartDashboard.putBoolean("Shooter Ready", (shooter.getRPM() > 3000));
	}

	public Shooter() {
		// Set shooter ramp rate
		shooter.setRamp(0.5);
	}

	// Stop the shooter
	public void stop() {
		shooter.stop();
	}

	// Sets the RPM of the shooter motor
	public void setRPM(double rpm) {
		shooter.setVoltage((rpm / kmaxRPM) * kbatteryMax);
	}

	// Unload a single ball from the loader & shooter
	SequentialCommandGroup unloadSingle = new SequentialCommandGroup(
			new InstantCommand(() -> Robot.loader.set(-1)),
			new InstantCommand(() -> shooter.set(-0.2)),
			new WaitCommand(2),
			new InstantCommand(() -> Robot.loader.stopMotor()),
			new InstantCommand(() -> shooter.stop()));

	// Load a single ball to a point directly before the shooter
	SequentialCommandGroup loadSingle = new SequentialCommandGroup(
			new InstantCommand(() -> Robot.loader.set(1)),
			new WaitCommand(1.7),
			new InstantCommand(() -> Robot.loader.stopMotor()));

	// Shoot the ball at full speed
	SequentialCommandGroup shootSingle = new SequentialCommandGroup(
			new InstantCommand(() -> setRPM(kFenderSpeed)),
			new WaitCommand(1.5),
			new InstantCommand(() -> Robot.loader.set(1)),
			new WaitCommand(1),
			new InstantCommand(() -> Robot.loader.stopMotor()),
			new InstantCommand(() -> shooter.stop()));

	// Shoot two balls at full speed
	SequentialCommandGroup shootDouble = new SequentialCommandGroup(
			new InstantCommand(() -> setRPM(kFenderSpeed)),
			new WaitCommand(1),
			new InstantCommand(() -> Robot.loader.set(1)),
			new WaitCommand(3),
			new InstantCommand(() -> Robot.loader.stopMotor()),
			new InstantCommand(() -> shooter.stop()));

	// Shoot and load a single ball
	SequentialCommandGroup shootFull = new SequentialCommandGroup(
			new InstantCommand(() -> setRPM(kFenderSpeed)),
			new InstantCommand(() -> Robot.loader.set(1)),
			new WaitCommand(4),
			new InstantCommand(() -> shooter.stop()),
			new InstantCommand(() -> Robot.loader.stopMotor()));
}