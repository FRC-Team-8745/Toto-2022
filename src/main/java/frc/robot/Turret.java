package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {

	public BrushlessNEO turret = new BrushlessNEO(7, false);

	private final int kTurretRatio = 120;
	// Turret error in degrees
	private final double kTurretError = 5;
	private final double kTurretProportional = 0.2;

	@Override
	public void periodic() {

	}

	public Turret() {
		turret.setRamp(0.5);
		turret.idleMode(IdleMode.kBrake);
		turret.resetPosition();
	}
	//converts degrees to motor turns
	public double convertDegrees(double degrees) {
		return (degrees / 360) * kTurretRatio;
	}
	public double getTurretDegrees() {
		return getTurretPos() * 360;
	}
	public double getTurretPos() {
		return turret.getPosition() / kTurretRatio;
	}

	public void rotateToDegrees(double targetDegrees, double speed) {
		/*
		double turretErrorRotations = kTurretError / 360;
		double targetRotations = targetDegrees / 360;

		turret.set(speed - ((targetRotations * kTurretRatio) - turret.getPosition()) * kTurretProportional);

		if (Math.abs(turret.getPosition() * kTurretRatio) < turretErrorRotations)
			turret.stop();
		*/
		double turnsLeft = convetDegrees(targetDegrees) - getTurretPos;
		double speed = (turnsLeft / convertDegrees(targetDegrees)) * speed;
		turret.set(speed * kTurretProportional);
		
		if ((targetDegrees - Math.abs(getTurretDegrees())) < kTurretError)
			turret.stop()
	}
}
