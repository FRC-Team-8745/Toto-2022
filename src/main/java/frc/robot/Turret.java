package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {


	public BrushlessNEO turret = new BrushlessNEO(7, false);

	private final int kTurretRatio = 120;
	private final double kTurretError = 5;
	private final double kTurretProportional = 0.8;

	public Turret() {
		turret.setRamp(0.5);
		turret.idleMode(IdleMode.kBrake);
		turret.resetPosition();
	}

	// Converts degrees of turret to motor turns
	public double convertDegrees(double degrees) {
		return (degrees / 360) * kTurretRatio;
	}

	// Get the turrets degrees
	public double getTurretDegrees() {
		return getTurretRotations() * 360;
	}

	// Gets the current turret position in rotations
	public double getTurretRotations() {
		return turret.getPosition() / kTurretRatio;
	}

	// Rotate the turret to a specified number of degrees
	public Boolean rotateDegrees(double targetDegrees, double speed) {
		// Stop when aligned within the error
		if (Math.abs(targetDegrees - getTurretDegrees()) < kTurretError) {
			turret.stop();
			return true;
		}
		
		// Formula used below: (((((targetDegrees / 360) * kTurretRatio) - (turret.getPosition() / kTurretRatio)) / ((targetDegrees / 360) * kTurretRatio)) * speed) * kTurretProportional)
		
		// Get number of turret turns left
		double turnsLeft = convertDegrees(targetDegrees) - getTurretRotations();
		// Calculate speed. It will be slower when it gets closer to the set degrees.
		speed = Math.signum(turnsLeft) * (turnsLeft / convertDegrees(targetDegrees)) * speed;
		// Set speed
		turret.set(speed * kTurretProportional);
		System.out.println(speed);
		return false;
	}
}
