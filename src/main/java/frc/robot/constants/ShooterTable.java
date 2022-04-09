package frc.robot.Constants;

import frc.robot.LinearInterpolator;

public final class ShooterTable {
	public static final double[][] shooterRPM = {
			{ 48, 3000 },
			{ 60, 3000 },
			{ 72, 3000 },
			{ 84, 3000 },
			{ 96, 3250 },
			{ 108, 3250 },
			{ 120, 3250 },
			{ 121, 3750 },
			{ 132, 3750 },
			{ 144, 3750 },
			{ 156, 4000 },
			{ 172, 4500 }
	};

	public static final double[][] linearActuatorAngle = {
			{ 48, 0.125 },
			{ 60, 0.125 },
			{ 72, 0.2 },
			{ 84, 0.2 },
			{ 96, 0.3 },
			{ 108, 0.3 },
			{ 120, 0.4 },
			{ 132, 0.4 },
			{ 144, 0.4 },
			{ 156, 0.4 }
	};

	public static final double[][] shooterRampTime = {
			{ 48, 2 },
			{ 60, 2 },
			{ 72, 2 },
			{ 84, 2 },
			{ 96, 2 },
			{ 108, 2 },
			{ 120, 2 },
			{ 132, 2 },
			{ 144, 2 },
			{ 156, 2 }
	};

	public static final double[][] shooterShotTime = {
			{ 48, 2 },
			{ 60, 2 },
			{ 72, 2 },
			{ 84, 2 },
			{ 96, 2 },
			{ 108, 2 },
			{ 120, 2 },
			{ 132, 2 },
			{ 144, 2 },
			{ 156, 1.5 }
	};

	public static final LinearInterpolator shooterRPMInterpolator = new LinearInterpolator(shooterRPM);
	public static final LinearInterpolator linearActuatorInterpolator = new LinearInterpolator(linearActuatorAngle);
	public static final LinearInterpolator shooterRampInterpolator = new LinearInterpolator(shooterRampTime);
	public static final LinearInterpolator shooterTimingInterpolator = new LinearInterpolator(shooterShotTime);
}
