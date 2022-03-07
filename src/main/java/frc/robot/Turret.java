package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {


	public BrushlessNEO turret = new BrushlessNEO(7, false);

	private final int kTurretRatio = 120;
	// Turret error in degrees
	private final double kTurretError = 5;
	private final double kTurretProportional = 0.8;

	@Override
	public void periodic() {

	}

	public Turret() {
		turret.setRamp(0.5);
		turret.idleMode(IdleMode.kBrake);
		turret.resetPosition();
	}
	// converts degrees of turret to motor turns
	public double convertDegrees(double degrees) {
		return (degrees / 360) * kTurretRatio;
	}
	// get the turrets degrees
	public double getTurretDegrees() {
		return getTurretPos() * 360;
	}
	// gets the current turret position in rotations
	public double getTurretPos() {
		return turret.getPosition() / kTurretRatio;
	}
	// sets the rotate speed of the turret.  must be used on a loop.  It returns false when not aligned and true when aligned.
	public Boolean rotateDegrees(double targetDegrees, double speed) {
		/*
		double turretErrorRotations = kTurretError / 360;
		double targetRotations = targetDegrees / 360;

		turret.set(speed - ((targetRotations * kTurretRatio) - turret.getPosition()) * kTurretProportional);

		if (Math.abs(turret.getPosition() * kTurretRatio) < turretErrorRotations)
			turret.stop();
		*/
		// stop when aligned
		if (Math.abs(targetDegrees - getTurretDegrees()) < kTurretError) {
			turret.stop();
			return true;
		}
		/*
		if (targetDegrees == 0) {
			targetDegrees = 1;
		}
		*/
		// formula used below: (((((targetDegrees / 360) * kTurretRatio) - (turret.getPosition() / kTurretRatio)) / ((targetDegrees / 360) * kTurretRatio)) * speed) * kTurretProportional)
		
		// get number of turret turns left
		double turnsLeft = convertDegrees(targetDegrees) - getTurretPos();
		// calculate speed.  It will be slower when it gets closer to the set degrees
		speed = Math.signum(turnsLeft) * (turnsLeft / convertDegrees(targetDegrees)) * speed;
		// set speed
		turret.set(speed * kTurretProportional);
		System.out.println(speed);
		return false;
	}
}
