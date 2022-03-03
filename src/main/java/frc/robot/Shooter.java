package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
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

	double shooterSpeed;

	private BangBangController bangBang = new BangBangController();
	private SimpleMotorFeedforward shooterFeedforward = new SimpleMotorFeedforward(0.038596, 0.13977, 0.014564);
	// Ks 0.038596
	// Kv 0.13977
	// Ka 0.014564

	/*
	 * The periodic method runs constantly and is in control of putting data to
	 * shuffleboard, calculating the speed needed for setting the RPM of the shooter
	 * and stopping/starting the motor.Z
	 */
	@Override
	public void periodic() {
		// Puts the shooter RPM and status on the dashboard
		SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
		SmartDashboard.putBoolean("Shooter Ready", this.isReady());
		System.out.println(shooterSpeed);
	}

	public void tempSet(double volts) {
		shooter.setVoltage(volts);
	}

	/*
	 * Initalization method for the shooter which inverts the loader, resets
	 * encoders, and sets ramp rates.
	 */
	public void shooterInit() {
		shooter.resetPosition();
		loader.setInverted(true);
		shooter.idleMode(IdleMode.kCoast);
	}

	/*
	 * Set the RPM of the motor.
	 */
	public void setRPM(double targetRPM) {
		shooterSpeed = bangBang.calculate(shooter.getRPM(), targetRPM) + (0.1 * shooterFeedforward.calculate(targetRPM));
		shooter.set(shooterSpeed);
	}

	/*
	 * Detect the RPM of the motor and return a value if it is within the allocated
	 * error of the target RPM.
	 */
	private boolean isReady() {
		return (shooter.getRPM() > 3000) && (shooter.getRPM() < 3050);
	}

	/*
	 * Detect when a ball is shot by sensing a dip in the RPM of the shooter motor
	 * cause by a ball exiting between the wheels. It returns a value of true when
	 * the RPM dips, and false otherwise.
	 */
	private boolean detectShot() {
		return (shooter.getRPM() < 2000);
	}

	/*
	 * Shoot a single ball using minimal timing logic and RPM
	 */
	public void shootSingle() {
		new SequentialCommandGroup(
				// Set the RPM of the shooter
				new InstantCommand(() -> System.out.println("1")),
				new InstantCommand(() -> this.setRPM(Constants.kShooterTargetRPM)),
				new InstantCommand(() -> System.out.println("2")),
				// Wait until the shooter is ready
				new WaitUntilCommand(this::isReady),
				new InstantCommand(() -> System.out.println("3")),
				// Start the loader
				new InstantCommand(() -> this.loader.set(1)),
				new InstantCommand(() -> System.out.println("4")),
				// Wait until the cargo has been shot
				new WaitUntilCommand(this::detectShot),
				new InstantCommand(() -> System.out.println("5")),
				// Stop the loader
				new InstantCommand(() -> this.loader.stopMotor()),
				new InstantCommand(() -> this.setRPM(0))).schedule();
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
