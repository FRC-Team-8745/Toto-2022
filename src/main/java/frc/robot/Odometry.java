package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Odometry extends SubsystemBase {
	public static final double kRobotStartPosX = 0;
	public static final double kRobotStartPosY = 0;
	public static AHRS IMU = new AHRS(Port.kUSB1);

	private final Pose2d startPosition;

	private Rotation2d rotation;
	private Pose2d position;
	private DifferentialDriveOdometry odometry;
	private Field2d field;

	public Odometry() {
		rotation = new Rotation2d(degreesToRadians(IMU.getYaw()));
		startPosition = new Pose2d(kRobotStartPosX, kRobotStartPosY, new Rotation2d());
		odometry = new DifferentialDriveOdometry(rotation, startPosition);
	}

	@Override
	public void periodic() {
		position = odometry.update(rotation, (Robot.left.getPosition() * Robot.kDriveGearbox), (Robot.right.getPosition() * Robot.kDriveGearbox));
		field.setRobotPose(position);
	}


	// Converts degrees to radians
	public double degreesToRadians(double degrees) {
		return degrees * (Math.PI / 180);
	}
}