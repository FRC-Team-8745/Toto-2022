package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Limelight.LEDMode;

/*
 * Button numbers: [1: trigger] [2: sidebutton] [3:labeled] [4: labeled] [5:
 * labeled] [6: labeled] [7: labeled] [8: labeled] [9: labeled] [10: labeled]
 * [11: labeled] [12: labeled]
 */

public class Drivetrain {
	private BrushlessNEO intake;
	private Turret turret;
	private Shooter autoShooter;

	private Joystick cont = new Joystick(1);
	private XboxController xbox = new XboxController(0);
	private BrushlessNEO right = new BrushlessNEO(1, true);
	private BrushlessNEO left = new BrushlessNEO(2, false);

	public Drivetrain(BrushlessNEO intake_, Turret turret_, Shooter autoShooter_) {
		intake = intake_;
		turret = turret_;
		autoShooter = autoShooter_;

		right.setRamp(0.5);
		left.setRamp(0.5);
	}

	public void setDrive(double speed) {
		right.set(speed);
		left.set(speed);
	}

	public void stopDrive() {
		right.stop();
		left.stop();
	}

	public void resetEncoders() {
		right.resetPosition();
		left.resetPosition();
	}

	public double getRightEncoder() {
		return right.getPosition();
	}

	public double getLeftEncoder() {
		return left.getPosition();
	}

	public void setRight(double speed) {
		right.set(speed);
	}

	public void setLeft(double speed) {
		left.set(speed);
	}

	// Declare variable for drive speed
	private double driveSpeed;

	public void driveTeleop() {
		// Shoot cargo
		if (xbox.getRawButtonPressed(8))
			autoShooter.shootDouble();

		// Shoot two cargo
		if (xbox.getRawButtonPressed(3))
			autoShooter.shootSingle();

		// Intake
		if (cont.getRawButton(5))
			intake.set(-1);
		else if (cont.getRawButton(3)) {
			intake.set(0.5);
			driveSpeed = 0.3;
		} else
			intake.stop();

		// Climbers
		if (xbox.getPOV() == 0) {
			Robot.climberRight.set(.75);
			Robot.climberLeft.set(.75);
		} else if (xbox.getPOV() == 180) {
			Robot.climberRight.set(-.75);
			Robot.climberLeft.set(-.75);
		} else {
			Robot.climberRight.set(0);
			Robot.climberLeft.set(0);
		}

		// Turret
		if (turret.isMovable()) {
			if (xbox.getRawButton(5))
				turret.set(0.3);
			else if (xbox.getRawButton(6))
				turret.set(-0.3);
			else
				turret.set(0);
		}

		// Climber Calibration
		if (cont.getRawButton(7)) {
			if (cont.getRawButton(9))
				Robot.climberLeft.set(.1);
			else if (cont.getRawButton(11))
				Robot.climberLeft.set(-.1);
			else
				Robot.climberLeft.stop();

			if (cont.getRawButton(10))
				Robot.climberRight.set(.1);
			else if (cont.getRawButton(12))
				Robot.climberRight.set(-.1);
			else
				Robot.climberRight.stop();
		}

		if (cont.getRawButton(8)) {
			if (cont.getRawButton(9))
				Robot.climberLeft.set(.5);
			else if (cont.getRawButton(11))
				Robot.climberLeft.set(-.5);
			else
				Robot.climberLeft.stop();

			if (cont.getRawButton(10))
				Robot.climberRight.set(.5);
			else if (cont.getRawButton(12))
				Robot.climberRight.set(-.5);
			else
				Robot.climberRight.stop();
		}

		// Load a single cargo
		if (xbox.getRawButtonPressed(2))
			autoShooter.loadSingle.schedule();

		// Unload a single cargo
		if (xbox.getRawButtonPressed(4))
			autoShooter.unloadSingle();

		// Set the speed based on the trigger(1) of the joystick
		if (cont.getRawButton(1) && !cont.getRawButton(5) && !cont.getRawButton(3)) {
			driveSpeed = 1;
		} else if (!cont.getRawButton(5) && !cont.getRawButton(3)) {
			driveSpeed = 0.5;
		}

		/*
		 * Set variables for the left and right motors to the controllers axis, using
		 * both the up/down and left/right joystick values and some math; multiplied by
		 * the speed modifier
		 * 
		 * If the side button (2) is pressed, reverse the controls so the back of the
		 * robot acts as the front while driving
		 */

		if (cont.getRawButton(2)) {
			left.set(
					(cont.getRawAxis(1) + cont.getRawAxis(0) * 0.25) * driveSpeed);
			right.set(
					(cont.getRawAxis(1) - cont.getRawAxis(0) * 0.25) * driveSpeed);
		} else {
			left.set(
					(-cont.getRawAxis(1) + cont.getRawAxis(0) * 0.25) * driveSpeed);
			right.set(
					(-cont.getRawAxis(1) - cont.getRawAxis(0) * 0.25) * driveSpeed);
		}

		if (xbox.getRawButton(7))
			turret.fullAlign();

		if (xbox.getRawButton(9))
			Robot.limelight.setLEDMode(LEDMode.kOff);
		else if (xbox.getRawButton(10))
			Robot.limelight.setLEDMode(LEDMode.kOn);

		if (xbox.getRawButton(1))
			turret.odometryAlign();

		if (cont.getRawButton(12))
			SmartDashboard.putBoolean("flip", turret.flip());

	}
}
