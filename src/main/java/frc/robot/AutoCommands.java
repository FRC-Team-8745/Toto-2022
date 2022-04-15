package frc.robot;

import static frc.robot.constants.Constants.*;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AutoCommands extends SubsystemBase {

	private final PIDController drivePID = new PIDController(kP, kI, kD);
	private double startDistance;

	public Boolean driveFeet(double feet, double speed) {
		if (Math.abs(feet / (kDiameter / 12) * Math.PI) < Math.abs(Robot.drive.getRightEncoder() / kDriveGearbox)) {
			Robot.drive.stopDrive();
			return true;
		}

		Robot.drive.setLeft(speed);
		Robot.drive.setRight(speed + drivePID.calculate(Robot.drive.getRightEncoder() - Robot.drive.getLeftEncoder(), 0));
		return false;
	}

	public double feetToRotations(double feet) {
		return feet / (kDiameter / 12) * Math.PI;
	}

	public double rotationsToFeet(double rotations) {
		return (rotations * (kDiameter * Math.PI)) / 12;
	}

	public Boolean driveFeetRelitave(double feet, double speed) {
		if (startDistance == 0)
			startDistance = rotationsToFeet(Robot.drive.getRightEncoder() / kDriveGearbox);

		if (driveFeet(startDistance + feet, speed)) {
			startDistance = 0;
			return true;
		}
		return false;
	}
}