package frc.robot;

import com.kauailabs.navx.frc.AHRS;

//import java.util.concurrent.TimeUnit;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AutoCommands extends SubsystemBase {
	PIDController drivePID;
	PIDController turnPID;
	double imu;
	AHRS IMU = new AHRS(Port.kUSB);

	@Override
	public void periodic() {
		imu = IMU.getRawGyroZ();
	}

	final double kDiameter = 6;

	public AutoCommands(Double[] pidDrive_, Double[] pidTurn_) {
		drivePID = new PIDController(pidDrive_[0], pidDrive_[1], pidDrive_[2]);
		turnPID = new PIDController(pidTurn_[0], pidTurn_[1], pidTurn_[2]);
		imu = 0;
	}

	/*
	 * public Boolean driveFeet(double feet, double speed, boolean resetOnEnd) {
	 * if (Math.abs((((-Robot.right.getPosition() * 8.45) * circumference) / 12) -
	 * feet) < 0.25) {
	 * Robot.drive.stopDrive();
	 * if (resetOnEnd)
	 * Robot.drive.resetEncoders();
	 * return true;
	 * }
	 * 
	 * Robot.left.set(-speed);
	 * Robot.right.set(-speed + drivePID.calculate(Robot.right.getPosition() -
	 * Robot.left.getPosition(), 0));
	 * return false;
	 * }
	 */
	public Boolean driveFeet(double feet, double speed, boolean resetOnEnd) {
		if (Math.abs(calculator(kDiameter, feet)) < -Robot.right.getPosition() / 10.71) {
			Robot.drive.stopDrive();
			return true;
		}

		Robot.left.set(-speed);
		Robot.right.set(-speed + drivePID.calculate(Robot.right.getPosition() - Robot.left.getPosition(), 0));
		return false;
	}


	public double calculator(double diameter, double targetFeet) {
		double circumference = (diameter / 12) * Math.PI;
		return targetFeet / circumference;
	}

	public Boolean turnDegrees(double degrees, double speed, boolean resetOnEnd) {
		if (Math.abs(imu - degrees) < 5) {
			Robot.drive.stopDrive();
			if (resetOnEnd)
				Robot.drive.resetEncoders();
			return true;
		}

		Robot.left.set(speed);
		Robot.right.set(-speed - turnPID.calculate(imu - degrees, 0));
		return false;
	}
}