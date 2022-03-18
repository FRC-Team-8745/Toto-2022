package frc.robot;

public class Teleop {
	// Declare variable for drive speed
	private static double driveSpeed;
	private static double climberSpeed;

	public static void runTeleop() {

		/*
		 * Xbox
		 */

		// Shoot a single cargo
		if (Robot.xbox.getRawButtonPressed(8))
			Robot.shootCommands.shootDouble();

		// Shoot two cargo
		if (Robot.xbox.getRawButtonPressed(3))
			Robot.shootCommands.shootSingle();

		// Climbers
		if (Robot.xbox.getPOV() == 0) {
			Robot.climberRight.set(.75);
			Robot.climberLeft.set(.75);
		} else if (Robot.xbox.getPOV() == 180) {
			Robot.climberRight.set(-.75);
			Robot.climberLeft.set(-.75);
		} else {
			Robot.climberRight.set(0);
			Robot.climberLeft.set(0);
		}

		// Turret
		if (Robot.xbox.getRawButton(5))
			Robot.turret.set(0.6);
		else if (Robot.xbox.getRawButton(6))
			Robot.turret.set(-0.6);
		else
			Robot.turret.stop();

		// Load a single cargo
		if (Robot.xbox.getRawButtonPressed(2))
			Robot.shootCommands.load();

		// Unload a single cargo
		if (Robot.xbox.getRawButtonPressed(4))
			Robot.shootCommands.unload();

		/*
		 * Joystick
		 */

		// Intake
		if (Robot.cont.getRawButton(5))
			Robot.intake.set(-1);
		else if (Robot.cont.getRawButton(3)) {
			Robot.intake.set(0.5);
			driveSpeed = 0.3;
		} else
			Robot.intake.stop();

		// Set the speed based on the trigger(1) of the joystick
		if (Robot.cont.getRawButton(1) && !Robot.cont.getRawButton(5) && !Robot.cont.getRawButton(3))
			driveSpeed = 1;
		else if (!Robot.cont.getRawButton(5) && !Robot.cont.getRawButton(3))
			driveSpeed = 0.5;

		/*
		 * Control the drive motors with the joystick using standard arcade drive. If
		 * the trigger (1) is pressed, it doubles the robot speed. If the side button
		 * (2) is pressed, reverse the controls so the back of the robot acts as the
		 * front for defence.
		 */

		if (Robot.cont.getRawButton(2)) {
			Robot.left.set(
					(getJoystick1() + getJoystick0()) * driveSpeed);
			Robot.right.set(
					(getJoystick1() - getJoystick0()) * driveSpeed);
		} else {
			Robot.left.set(
					(-getJoystick1() + getJoystick0()) * driveSpeed);
			Robot.right.set(
					(-getJoystick1() - getJoystick0()) * driveSpeed);
		}

		/*
		 * Adjust climbers manually if they get unaligned
		 */

		// Set the climber calibration speed
		if (Robot.cont.getRawButton(8))
			climberSpeed = 0.5;
		else
			climberSpeed = 0.1;

		// Climber Calibration
		if (Robot.cont.getRawButton(7)) {
			if (Robot.cont.getRawButton(9))
				Robot.climberLeft.set(climberSpeed);
			else if (Robot.cont.getRawButton(11))
				Robot.climberLeft.set(-climberSpeed);
			else
				Robot.climberLeft.stop();

			if (Robot.cont.getRawButton(10))
				Robot.climberRight.set(climberSpeed);
			else if (Robot.cont.getRawButton(12))
				Robot.climberRight.set(-climberSpeed);
			else
				Robot.climberRight.stop();
		}
	}

	public static double getJoystick1() {
		if (Robot.cont.getRawAxis(1) >= -0.05 && Robot.cont.getRawAxis(1) <= 0.05)
			return 0;
		return Robot.cont.getRawAxis(1);
	}
	
	
	public static double getJoystick0() {
		if (Robot.cont.getRawAxis(0) >= -0.05 && Robot.cont.getRawAxis(0) <= 0.05)
			return 0;
		return Robot.cont.getRawAxis(0);
	}
}