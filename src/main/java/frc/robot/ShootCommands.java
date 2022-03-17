package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

public class ShootCommands {
	// Shooter speed (in RPM) when shooting from the fender
	private final double kFenderSpeed = 3000;
	// The max voltage of the battery
	private final double kbatteryMax = 13;
	// The max RPM of a neo motor
	private final double kmaxRPM = 5500;

	// Sets the RPM of the shooter motor
	public void setRPM(double rpm) {
		Robot.shooter.setVoltage((rpm / kmaxRPM) * kbatteryMax);
	}

	// Unload a single ball from the loader & shooter
	public void unload() {
		new SequentialCommandGroup(
				new InstantCommand(() -> Robot.loader.set(-1)),
				new InstantCommand(() -> Robot.shooter.set(-0.2)),
				new WaitCommand(2),
				new InstantCommand(() -> Robot.loader.stopMotor()),
				new InstantCommand(() -> Robot.shooter.stop())).schedule();
	}

	// Load a single ball to a point directly before the shooter
	public void load() {
		new SequentialCommandGroup(
				new InstantCommand(() -> Robot.loader.set(1)),
				new WaitCommand(1.4),
				new InstantCommand(() -> Robot.loader.stopMotor())).schedule();
	}

	// Shoot the ball at full speed
	public void shootSingle() {
		new SequentialCommandGroup(
				new InstantCommand(() -> setRPM(kFenderSpeed)),
				new WaitCommand(1.5),
				new InstantCommand(() -> Robot.loader.set(1)),
				new WaitCommand(1),
				new InstantCommand(() -> Robot.loader.stopMotor()),
				new InstantCommand(() -> Robot.shooter.stop())).schedule();
	}

	// Shoot two balls at full speed
	public void shootDouble() {
		new SequentialCommandGroup(
				new InstantCommand(() -> setRPM(kFenderSpeed)),
				new WaitCommand(1),
				new InstantCommand(() -> Robot.loader.set(1)),
				new WaitCommand(3),
				new InstantCommand(() -> Robot.loader.stopMotor()),
				new InstantCommand(() -> Robot.shooter.stop())).schedule();
	}

	// Shoot and load a single ball
	public void shootFull() {
		new SequentialCommandGroup(
				new InstantCommand(() -> setRPM(kFenderSpeed)),
				new InstantCommand(() -> Robot.loader.set(1)),
				new WaitCommand(4),
				new InstantCommand(() -> Robot.shooter.stop()),
				new InstantCommand(() -> Robot.loader.stopMotor())).schedule();
	}
}