package frc.robot;

import static frc.robot.Constants.Constants.*;
import static frc.robot.Constants.ShooterTable.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {
	private CANSparkMax turret = new CANSparkMax(7, MotorType.kBrushless);

	public SparkMaxPIDController pid = turret.getPIDController();
	public RelativeEncoder encoder = turret.getEncoder();

	public Odometry odometry = new Odometry();

	private double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

	public Turret() {
		turret.restoreFactoryDefaults();
		turret.setIdleMode(IdleMode.kBrake);
		resetPosition();

		kP = 0.2;
		kI = 0;
		kD = 0;
		kIz = 3;
		kFF = 0;
		kMaxOutput = 1;
		kMinOutput = -1;

		pid.setP(kP);
		pid.setI(kI);
		pid.setD(kD);
		pid.setIZone(kIz);
		pid.setFF(kFF);
		pid.setOutputRange(kMinOutput, kMaxOutput);
	}

	// Get the position the linear actuator should be set to from any specified distance
	public double getLinearActuatorFromDistance(double distance) {
		return linearActuatorInterpolator.getInterpolatedValue(distance);
	}

	// Get the speed the shooter should be set to from any specified distance
	public double getShooterRPMFromDistance(double distance) {
		return shooterRPMInterpolator.getInterpolatedValue(distance);
	}

	public double getShooterTimingFromDistance(double distance){
		return shooterTimingInterpolator.getInterpolatedValue(distance);
	}

	public double getShooterRampFromDistance(double distance) {
		return shooterRampInterpolator.getInterpolatedValue(distance);
	}

	// Test method for setting the value of the linear actuator and shooter from a distance
	public void testShootDistance(double distance) {
		Robot.linearActuator.set(getLinearActuatorFromDistance(distance));
		Robot.shooter.setRPM(getShooterRPMFromDistance(distance));
	}

	// Set the speed of the turret, unless it is at one of the limits
	public void set(double speed) {
		if (!(Math.signum(speed) == 1 && !atLimitLeft()) ^ !(Math.signum(speed) == -1 && !atLimitRight()))
			turret.set(speed);
		else
			turret.set(0);
	}

	// Reset the position of the turret
	public void resetPosition() {
		encoder.setPosition(0);
	}

	// Stop the turret motor
	public void stop() {
		turret.stopMotor();
	}

	// Converts degrees of turret to
	public double convertDegrees(double degrees) {
		return (degrees / 360) * kTurretRatio;
	}

	// Get the turrets degrees
	public double getTurretDegrees() {
		return getTurretRotations() * 360;
	}

	// Gets the current turret position in rotations
	public double getTurretRotations() {
		return encoder.getPosition() / kTurretRatio;
	}

	// Rotate the turret to a set number of degrees
	public void rotateDegrees(double targetDegrees) {
		pid.setReference(-convertDegrees(targetDegrees), CANSparkMax.ControlType.kPosition);
	}

	// Returns true if the turret is rotated to it's limit to the left
	public boolean atLimitLeft() {
		if (getTurretDegrees() > 180)
			return true;
		return false;
	}

	// Returns true if the turret is rotated to it's limit to the right
	public boolean atLimitRight() {
		if (getTurretDegrees() < -180)
			return true;
		return false;
	}

	// Adjust the turret via odometry to give a loose position if the limelight
	// can't see the hub
	public void odometryAlign() {
		if (!atLimitLeft() && !atLimitRight())
			rotateDegrees(Math.toDegrees(-odometry.getPose().getRotation().getDegrees()) + -odometry.calculateTurretDegreesFromPoint(odometry.getPose()));
		else
			turret.set(0);
	}

	// Align the turret precisely with the limelight
	public boolean limelightAlign() {
		// Proportional constant
		double kP = (0.02);
		// Minimum power needed to make the turret move
		double minimumPower = 0.03;
		// The allowed error from the center
		double allowedError = 0.25;
		// Distance from the center of the hub
		double tx = Robot.limelight.getTx();
		// Speed to set the turret to, defaults to 0
		double turretSpeed = 0;

		if (Math.abs(tx) > allowedError)
			turretSpeed = (kP * tx + ((minimumPower) * Math.signum(tx)));

		set(-turretSpeed);

		Robot.linearActuator.set(getLinearActuatorFromDistance(Robot.limelight.getDistance()));

		if (Math.abs(tx) < allowedError)
			return true;
		return false;
	}

	// Keep the turret aligned
	public void fullAlign() {
		if (Robot.limelight.hasTarget())
			limelightAlign();
		else
			odometryAlign();
	}
}
