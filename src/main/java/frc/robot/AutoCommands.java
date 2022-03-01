package frc.robot;

//import java.util.concurrent.TimeUnit;

import edu.wpi.first.math.controller.PIDController;
import com.kauailabs.navx.frc.AHRS;

public class AutoCommands {
	PIDController drivePID;
	PIDController turnPID;
	// TODO: Replace 'imu' with the imu's getDegrees method
	AHRS IMU;
	double circumference;

	public AutoCommands(Double[] pidDrive_, Double[] pidTurn_,
			double circumference_, AHRS imu_) {
		drivePID = new PIDController(pidDrive_[0], pidDrive_[1], pidDrive_[2]);
		turnPID = new PIDController(pidTurn_[0], pidTurn_[1], pidTurn_[2]);
		circumference = circumference_;
		IMU = imu_;
	}

	public int driveFeet(double feet, double speed, boolean resetOnEnd) {
		if (Math.abs((Robot.right.getPosition() * circumference) - feet) < 0.25) {
			Robot.drive.stopDrive();
			return 1;
		}

		Robot.left.set(speed);
		Robot.right.set(speed + drivePID.calculate(Robot.right.getPosition() - Robot.left.getPosition(), 0));
		return 0;
	}

	public int turnDegrees(double degrees, double speed, boolean resetOnEnd) {
		if (Math.abs(IMU.getRawGyroX() - degrees) < 5) {
			Robot.drive.stopDrive();
			return 1;
		}

		Robot.left.set(speed);
		Robot.right.set(-speed - turnPID.calculate(IMU.getRawGyroX() - degrees, 0));
		return 0;
	}
}