package frc.robot.constants;

public class Constants {
	/* Motor limits */

	// Linear actuator
	public static final double kLinearActuatorMin = 0.125;
	public static final double kLinearActuatorMax = 1;

	/* Gear Ratios */

	// Drive gearbox ratio
	public static final double kDriveGearbox = 10.71;

	// Turret gear ratio
	public static final int kTurretRatio = 120;

	/* Shooter RPM calculation constants */

	// The shooter speed while shooting from the fender
	public static final double kFenderSpeed = 3000;
	// Max battery voltage
	public static final double kbatteryMax = 13;
	// The max RPM of the shooter motor
	public static final double kmaxRPM = 5500;

	/* Auto Constants */

	// The default auto program to run
	public static final int kDefaultAuto = 0;

	// The distances to drive in the two autos that collect cargo
	public static final double kNormalAutoDriveDistance = 7.583;

	/* AutoCommands PID */
	public static final double kP = 0.2;
	public static final double kI = 0.0;
	public static final double kD = 0.0;

	/* Drive Wheel Diameter */
	public static final double kDiameter = 6;

	/* Limelight Constants */

	// Limelight mounting angle from perfectly vertical
	public static final double kLimelightMountAngleDegrees = 34.45;

	// Distance from the center of the Limelight lens to the floor
	public static final double kLimelightLensHeight = 45;

	/* Field Constants */

	// Vision tape height
	public static final double kVisionTapeHeight = 101.625;

	/* Odometry Constants */

	public static final double kHubCenterX = 8;
	public static final double kHubCenterY = 4;
	
	// Default starting position
	
	/*
	public static final double kRobotStartPosX = 6.951;
	public static final double kRobotStartPosY = 2.947;
	public static final double kRobotStartRotY = -155;
	*/
	
	public static final double shooterSpeedOffset = 250;
	
	// Start position
	public static final double kRobotStartPosX = 6.1;
	public static final double kRobotStartPosY = 5;
	public static final double kRobotStartRotY = 136;

	// Auto code to run
	public static final int autoToRun = 0;


}