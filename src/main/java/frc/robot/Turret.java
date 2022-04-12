package frc.robot;

import static frc.robot.constants.Constants.*;
import static frc.robot.constants.ShooterTable.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {
	private CANSparkMax turret = new CANSparkMax(7, MotorType.kBrushless);
	private SparkMaxPIDController pid = turret.getPIDController();
	private RelativeEncoder encoder = turret.getEncoder();
	private Servo linearActuator = new Servo(1);

	private Odometry odometry = new Odometry();

	private boolean flipInProgress = false;

	private boolean flippingLeft = false;

	public Turret() {
		turret.restoreFactoryDefaults();
		turret.setIdleMode(IdleMode.kBrake);
		resetPosition();

		pid.setP(0.2);
		pid.setOutputRange(-1, 1);

		linearActuator.setBounds(2.0, 1.8, 1.5, 1.2, 1.0);
	}

	// Adjust the turret via odometry to give a loose position if the limelight
	// can't see the hub
	public void odometryAlign() {
		double linearActuator = getLinearActuatorFromDistance(odometry.getDistanceToHub(odometry.getPose()));
		if (isMovable()) {
			if (!atLimitLeft() && !atLimitRight()) {
				double x = -odometry.getPose().getRotation().getDegrees()
						+ odometry.calculateTurretDegreesFromPoint(odometry.getPose());
				if (x > 180)
					x -= 360;
				else if (x < -180)
					x += 360;
				rotateDegrees(x);
			} else
				turret.set(0);
		}
		if (linearActuator > 0.125)
			this.linearActuator.set(linearActuator);
	}

	// Align the turret precisely with the limelight
	public boolean limelightAlign() {
		boolean atTarget = false;
		if (!flipInProgress) {
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

			linearActuator.set(getLinearActuatorFromDistance(Robot.limelight.getDistance()));

			if (Math.abs(tx) < allowedError)
				atTarget = true;
			atTarget = false;
		}
		return atTarget;
	}

	// Keep the turret aligned
	public void fullAlign() {
		if (Robot.limelight.hasTarget())
			limelightAlign();
		else
			odometryAlign();
	}

	/**
	 * Calculates the angle the linear actuator should be set to at a specified
	 * distance from the hub.
	 * 
	 * @param distance The distance to the hub in inches
	 * @return The value at which to set the linear actuator
	 */
	public double getLinearActuatorFromDistance(double distance) {
		return linearActuatorInterpolator.getInterpolatedValue(distance);
	}

	/**
	 * Calculates the RPM the shooter should be set to at a specified distance from
	 * the hub.
	 * 
	 * @param distance The distance to the hub in inches
	 * @return The RPM at which to set the shooter
	 */
	public double getShooterRPMFromDistance(double distance) {
		return shooterRPMInterpolator.getInterpolatedValue(distance);
	}

	/**
	 * Calculates the time between shots from a specified distance to the hub.
	 * 
	 * @param distance The distance to the hub in inches
	 * @return The time between shots
	 */
	public double getShooterTimingFromDistance(double distance) {
		return shooterTimingInterpolator.getInterpolatedValue(distance);
	}

	/**
	 * Calculates the ramp time for the shooter from a specified distance to the
	 * hub.
	 * 
	 * @param distance The distance to the hub in inches
	 * @return The time it takes to ramp up the shooter
	 */
	public double getShooterRampFromDistance(double distance) {
		return shooterRampInterpolator.getInterpolatedValue(distance);
	}

	/**
	 * Set the speed of the shooter motor after checking if it has reached either
	 * limit
	 * 
	 * @param speed The precentage of power to set the turret to
	 */
	public void set(double speed) {
		if (!(Math.signum(speed) == 1 && !atLimitLeft()) ^ !(Math.signum(speed) == -1 && !atLimitRight()))
			turret.set(speed);
		else
			turret.set(0);
	}

	/**
	 * Reset the turret position to 0
	 */
	public void resetPosition() {
		encoder.setPosition(0);
	}

	/**
	 * Stop the turret motor
	 */
	public void stop() {
		turret.stopMotor();
	}

	/**
	 * Convert a number of degrees for the turret to turn into degrees for the motor
	 * to rotate
	 * 
	 * @param degrees Degrees for the turret to turn
	 * @return Degrees for the motor o turn
	 */
	public double convertDegrees(double degrees) {
		return (degrees / 360) * kTurretRatio;
	}

	/**
	 * Get the turret position in degrees
	 * 
	 * @return The turret position in degrees
	 */
	public double getTurretDegrees() {
		return getTurretRotations() * 360;
	}

	/**
	 * Get the turret position in rotations
	 * 
	 * @return The turret position in rotations
	 */
	public double getTurretRotations() {
		return encoder.getPosition() / kTurretRatio;
	}

	/**
	 * Rotate the turret to a number of degrees
	 * 
	 * @param targetDegrees The degrees to set the turret to
	 * @return If the target is at the specified value, return true
	 */
	public boolean rotateDegrees(double targetDegrees) {
		pid.setReference(convertDegrees(targetDegrees), CANSparkMax.ControlType.kPosition);

		if (Math.abs(targetDegrees - getTurretDegrees()) < 0.25)
			return true;
		return false;
	}

	/**
	 * 
	 * @return Returns true if the turret is at the left limit
	 */
	public boolean atLimitLeft() {
		if (getTurretDegrees() > 180)
			return true;
		return false;
	}

	/**
	 * 
	 * @return Returns true if the turret is at the right limit
	 */
	public boolean atLimitRight() {
		if (getTurretDegrees() < -180)
			return true;
		return false;
	}

	/**
	 * Flip the turret around if it is at the limit on either side
	 * 
	 * @return Returns true if the flip is complete
	 */
	public boolean flip() {
		if (!flipInProgress) {
			if (atLimitLeft()) {
				rotateDegrees(-175);
				flipInProgress = true;
				flippingLeft = false;
			} else if (atLimitRight()) {
				rotateDegrees(175);
				flipInProgress = true;
				flippingLeft = true;
			}
		}

		if (flippingLeft && flipInProgress == true) {
			if (getTurretDegrees() > 170)
				flipInProgress = false;
		} else if (!flippingLeft && flipInProgress == true) {
			if (getTurretDegrees() < -170)
				flipInProgress = false;
		}

		return flipInProgress;
	}

	/**
	 * Checks both turret limits and flips the turret if it has reached one
	 * 
	 * @return Returns true if the turret is able to be controlled
	 */
	public boolean isMovable() {
		if (!flip())
			return true;
		return false;
	}
}
