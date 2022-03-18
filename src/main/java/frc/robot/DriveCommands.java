package frc.robot;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveCommands extends SubsystemBase {

	private final double kDiameter = 6;
	private final double kP = 0.2;
	private final double kI = 0.0;
	private final double kD = 0.0;
	private PIDController drivePID = new PIDController(kP, kI, kD);

	public Boolean driveFeet(double feet, double speed, boolean resetOnEnd) {
		if (Math.abs(feet / ((kDiameter / 12) * Math.PI)) < Math.abs(Robot.right.getPosition() / Robot.kDriveGearbox)) {
			Robot.driveTrain.stopDrive();
			if (resetOnEnd)
				Robot.driveTrain.resetEncoders();
			return true;
		}

		Robot.left.set(speed);
		Robot.right.set(speed + drivePID.calculate(Robot.right.getPosition() - Robot.left.getPosition(), 0));
		return false;
	}
}	