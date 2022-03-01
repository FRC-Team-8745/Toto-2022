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

	// Declare shooter and loader motors
	private BrushlessNEO shooter = new BrushlessNEO(3, true);
	private Spark loader = new Spark(0);

	// Declare variables for target RPM, needed power, and status of the motor
	private double targetRPM;

	/*
	 * The periodic method runs constantly and is in control of putting data to
	 * shuffleboard, calculating the speed needed for setting the RPM of the shooter
	 * and stopping/starting the motor.
	 */
	@Override
	public void periodic() {
		// Puts the shooter RPM and status on the dashboard
		SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
		SmartDashboard.putBoolean("Shooter Ready", this.isReady());

		// Set the shooter to the calculated speed, unless it is stopped
		if (this.targetRPM != 0)
			this.shooter.set(0.2 * (this.targetRPM - this.shooter.getRPM()));
		else
			this.shooter.stop();
	}

	/*
	 * Initalization method for the shooter which inverts the loader, resets
	 * encoders, and sets ramp rates.
	 */
	public void shooterInit() {
		shooter.resetPosition();
		loader.setInverted(true);
		shooter.setRamp(1);
	}

	/*
	 * Set the RPM of the motor.
	 */
	public void setRPM(double targetRPM) {
		this.targetRPM = targetRPM;
	}

	/*
	 * Detect the RPM of the motor and return a value if it is within the allocated
	 * error of the target RPM.
	 */
	private boolean isReady() {
		return Math.abs(targetRPM - shooter.getRPM()) < Constants.kShooterAllowedError;
	}

	/*
	 * Detect when a ball is shot by sensing a dip in the RPM of the shooter motor
	 * cause by a ball exiting between the wheels. It returns a value of true when
	 * the RPM dips, and false otherwise.
	 */
	private boolean detectShot() {
		return ((shooter.getRPM() < (Constants.kShooterTargetRPM - 500)));
	}

	/*
	 * Shoot a single ball using minimal timing logic and RPM
	 */
	public void shootSingle() {
		new SequentialCommandGroup(
				// Set the RPM of the shooter
				new InstantCommand(() -> this.setRPM(Constants.kShooterTargetRPM)),
				// Wait until the shooter is ready
				new WaitUntilCommand(this::isReady),
				// Start the loader
				new InstantCommand(() -> this.loader.set(1)),
				// Wait until the cargo has been shot
				new WaitUntilCommand(this::detectShot),
				// Stop the loader
				new InstantCommand(() -> this.loader.stopMotor())).schedule();
	}

	/*
	 * Unload all cargo from the shooter and loader by reversing the motors.
	 */
	public void unloadCargo() {
		new SequentialCommandGroup(
				// Reverse motors
				new InstantCommand(() -> this.loader.set(-1)),
				new InstantCommand(() -> this.shooter.set(-0.2)),
				// Wait until it is clear
				new WaitCommand(2),
				// Stop motors
				new InstantCommand(() -> this.loader.stopMotor()),
				new InstantCommand(() -> this.shooter.stop())).schedule();
	}

	/*
	 * Load a cargo up the ramp to a point just before the shooter
	 */
	public void loadSingle() {
		new SequentialCommandGroup(
				// Start motor
				new InstantCommand(() -> this.loader.set(1)),
				// Wait until the ball is loaded
				new WaitCommand(1.7),
				// Stop motor
				new InstantCommand(() -> this.loader.stopMotor())).schedule();
	}

	/*
	 * Shoot two balls using minimal timing logic and RPM
	 */
	public void shootDouble() {
		new SequentialCommandGroup(
				new InstantCommand(() -> this.setRPM(Constants.kShooterTargetRPM)),
				new WaitUntilCommand(this::isReady),
				new InstantCommand(() -> this.loader.set(1)),
				new WaitUntilCommand(this::detectShot),
				new InstantCommand(() -> this.loader.stopMotor()),
				new InstantCommand(() -> this.setRPM(Constants.kShooterTargetRPM)),
				new WaitUntilCommand(this::isReady),
				new InstantCommand(() -> this.loader.set(1)),
				new WaitUntilCommand(this::detectShot),
				new InstantCommand(() -> this.loader.stopMotor())).schedule();
	}
}
