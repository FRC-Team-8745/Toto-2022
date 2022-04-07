package frc.robot;

import static frc.robot.Constants.Constants.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AutoCommands extends SubsystemBase {

	private final PIDController drivePID = new PIDController(kP, kI, kD);

	public Boolean driveFeet(double feet, double speed, boolean resetOnEnd) {
		if (Math.abs(feet / ((kDiameter / 12) * Math.PI)) < Math.abs(Robot.right.getPosition() / kDriveGearbox)) {
			Robot.drive.stopDrive();
			if (resetOnEnd)
				Robot.drive.resetEncoders();
			return true;
		}

		Robot.left.set(speed);
		Robot.right.set(speed + drivePID.calculate(Robot.right.getPosition() - Robot.left.getPosition(), 0));
		return false;
	}
}