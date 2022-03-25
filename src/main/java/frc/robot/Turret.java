package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {
	public CANSparkMax turret = new CANSparkMax(7, MotorType.kBrushless);

	private final int kTurretRatio = 120;

	public SparkMaxPIDController pid = turret.getPIDController();
	public RelativeEncoder encoder = turret.getEncoder();

	public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

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

		turret.setOpenLoopRampRate(0.5);
	}

	public void set(double speed) {
		turret.set(speed);
	}

	public void resetPosition() {
		encoder.setPosition(0);
	}

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
		if (atLimit())
			pid.setReference(-convertDegrees(targetDegrees), CANSparkMax.ControlType.kPosition);
		else
			pid.setReference(convertDegrees(targetDegrees), CANSparkMax.ControlType.kPosition);
	}

	// Returnes true if the turret is rotated to it's limit
	public boolean atLimit() {
		if (getTurretDegrees() > 180 || getTurretDegrees() < -180)
			return true;
		return false;
	}

	public void odometryAlign() {
		Robot.odometry.odometryAdjustTurret();
	}

	public boolean limelightAlign() {
		// Proportional constant
		double kP = (0.02);
		// Minimum power needed to make the robot move
		double minimumPower = 0.03;
		// The allowed error from the center
		double allowedError = 1;
		// Distance from the center of the hub
		double tx = Robot.limelight.getTx();
		// Speed to set the turret to, defaults to 0
		double turretSpeed = 0;

		// Checks if the turret is at its limit of rotation
		if (!atLimit()) {
			if (Math.abs(tx) > allowedError)
				turretSpeed = (kP * tx + ((minimumPower) * Math.signum(tx)));

			turret.set(-turretSpeed);
		}
		if (Math.abs(tx) < allowedError)
			return true;
		return false;

	}

	public void fullAlign() {
		if (Robot.limelight.hasTarget())
			limelightAlign();
		else
			odometryAlign();
	}
}
