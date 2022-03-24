package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Odometry extends SubsystemBase {
	public static final double kRobotStartPosX = 8;
	public static final double kRobotStartPosY = 4;
	public static final double kRobotStartRotY = 0;

	public static AHRS IMU = new AHRS(Port.kUSB1);

	private final Pose2d kStartPosition;

	private static Rotation2d rotation;
	public static Pose2d position;
	private static DifferentialDriveOdometry odometry;
	private static Field2d field;

	private boolean calibrated = false;

	public Odometry() {
		Robot.drive.resetEncoders();
		position = new Pose2d();
		field = new Field2d();
		rotation = new Rotation2d();
		kStartPosition = new Pose2d(kRobotStartPosX, kRobotStartPosY, rotation);
		odometry = new DifferentialDriveOdometry(rotation, kStartPosition);
	}

	@Override
	public void periodic() {
		rotation = new Rotation2d(degreesToRadians(-IMU.getYaw()));
		position = odometry.update(rotation, ((Robot.left.getPosition() / Robot.kDriveGearbox) * 6) / 39.37,
				((Robot.right.getPosition() / Robot.kDriveGearbox) * 6) / 39.37);
		field.setRobotPose(position);
		SmartDashboard.putData(field);

		if (!IMU.isCalibrating() && !calibrated) {
			IMU.zeroYaw();
			calibrated = false;
		}
	}

	// Converts degrees to radians
	public double degreesToRadians(double degrees) {
		return degrees * (Math.PI / 180);
	}

	// Converts radians to degrees
	public static double radiansToDegrees(double radians) {
		return radians * (180 / Math.PI);
	}

	public static final double kHubCenterX = 8;
	public static final double kHubCenterY = 4;

	public double calculateTurret(Pose2d position) {
		double x = position.getX();
		double y = position.getY();

		return radiansToDegrees(Math.atan((y - kHubCenterY) / (x - kHubCenterX)));
	}

	public double angle(Pose2d pos) {
		return 180.0 / Math.PI * Math.atan2(kHubCenterX - Math.abs(pos.getX()), kHubCenterY - Math.abs(pos.getY()));
	}

	// Adjust the turret via odometry to give a loose position if the limelight
	// can't see the hub
	public void odometryAdjustTurret() {
		if (Robot.autoTurretEnabled)
			Robot.turret.rotateDegrees(-rotation.getDegrees() + -angle(position));
	}
}