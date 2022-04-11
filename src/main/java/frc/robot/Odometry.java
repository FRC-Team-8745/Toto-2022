package frc.robot;

import static frc.robot.constants.Constants.*;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Odometry extends SubsystemBase {
	public static AHRS IMU = new AHRS(Port.kUSB1);

	private final Pose2d kStartPosition;

	private Rotation2d rotation;
	public Pose2d position;
	private DifferentialDriveOdometry odometry;
	private Field2d field;

	private boolean calibrated = false;

	public Odometry() {
		// Robot.drive.resetEncoders();
		position = new Pose2d();
		field = new Field2d();
		rotation = new Rotation2d();
		kStartPosition = new Pose2d(kRobotStartPosX, kRobotStartPosY, rotation);
		odometry = new DifferentialDriveOdometry(rotation, kStartPosition);
	}

	@Override
	public void periodic() {
		rotation = new Rotation2d(degreesToRadians(-IMU.getYaw()));
		position = odometry.update(
				/* Rotation of the robot */
				rotation,
				/*
				 * Left encoder divided by the gearbox ratio, multiplied by the diameter, and
				 * converted to meters
				 */
				(((Robot.drive.getLeftEncoder() * Math.PI) / kDriveGearbox) * 6) / 39.37,
				/* Same for the right wheel */
				(((Robot.drive.getRightEncoder() * Math.PI) / kDriveGearbox) * 6) / 39.37);

		// Update the robot position on the field
		field.setRobotPose(position);

		// Update the field on the dashboard
		SmartDashboard.putData(field);

		// Zero the gyro sensor yaw angle once, but only after it is calibrated
		if (!IMU.isCalibrating() && !calibrated) {
			IMU.zeroYaw();
			calibrated = true;
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

	public Pose2d getPose() {
		return position;
	}

	// Adjust the turret
	public double calculateTurretDegreesFromPoint(Pose2d pose) {
		// Calculate the angle between the center of the hub and the current robot
		// position. This returns a value in radians.
		double angleInRadians = Math.atan2(pose.getY() - kHubCenterY, pose.getX() - kHubCenterX);

		// Changes the direction of the angle by 180 degrees. We can use this
		// to adjust the angle if it's working, but in the wrong direction.
		// angleInRadians += Math.PI;

		// Convert from radians to degrees
		double angleInDegrees = Math.toDegrees(angleInRadians);

		// Normalize the angle to a number between 0 and 360.
		angleInDegrees %= 360.0;

		// Return the angle to which the turret needs to be adjusted.
		return angleInDegrees;
	}
}