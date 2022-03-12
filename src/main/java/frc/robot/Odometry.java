package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class Odometry {

	public static final double kRobotStartPosX = 0;
	public static final double kRobotStartPosY = 0;
	public static AHRS IMU = new AHRS(Port.kUSB1);
	public DifferentialDriveOdometry robotOdometry = new DifferentialDriveOdometry(
			new Rotation2d(degreesToRadians(IMU.getYaw())),
			new Pose2d(kRobotStartPosX, kRobotStartPosY, new Rotation2d()));

	// Converts degrees to radians
	public double degreesToRadians(double degrees) {
		return degrees * (Math.PI / 180);
	}
}
