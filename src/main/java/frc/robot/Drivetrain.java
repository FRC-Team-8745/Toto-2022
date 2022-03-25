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
	private BrushlessNEO right;
	private BrushlessNEO left;
	private BrushlessNEO intake;
	private BrushlessNEO climberRight;
	private BrushlessNEO climberLeft;
	private Turret turret;
	private Joystick cont;
	private XboxController xbox;
	private Shooter autoShooter;

	public Drivetrain(BrushlessNEO right_, BrushlessNEO left_, BrushlessNEO intake_,
			BrushlessNEO climberRight_, BrushlessNEO climberLeft_, Turret turret_, Joystick cont_,
			XboxController xbox_, Shooter autoShooter_) {
		right = right_;
		left = left_;
		intake = intake_;
		climberRight = climberRight_;
		climberLeft = climberLeft_;
		cont = cont_;
		xbox = xbox_;
		turret = turret_;
		autoShooter = autoShooter_;
	}

	public void setDrive(double speed) {
		this.right.set(speed);
		this.left.set(speed);
	}

	public void stopDrive() {
		this.right.stop();
		this.left.stop();
	}

	public void resetEncoders() {
		this.right.resetPosition();
		this.left.resetPosition();
	}

	// Declare variable for drive speed
	private double driveSpeed;

	public void driveTeleop() {
		// Shoot cargo
		if (this.xbox.getRawButtonPressed(8))
			this.autoShooter.shootDouble.schedule();

		// Shoot two cargo
		if (this.xbox.getRawButtonPressed(3))
			this.autoShooter.shootSingle.schedule();

		// Intake
		if (this.cont.getRawButton(5))
			this.intake.set(-1);
		else if (this.cont.getRawButton(3)) {
			this.intake.set(0.5);
			driveSpeed = 0.3;
		} else
			this.intake.stop();

		// Climbers
		if (this.xbox.getPOV() == 0) {
			this.climberRight.set(.75);
			this.climberLeft.set(.75);
		} else if (this.xbox.getPOV() == 180) {
			this.climberRight.set(-.75);
			this.climberLeft.set(-.75);
		} else {
			this.climberRight.set(0);
			this.climberLeft.set(0);
		}

		// Turret
		if (this.xbox.getRawButton(5))
			this.turret.set(0.6);
		else if (this.xbox.getRawButton(6))
			this.turret.set(-0.6);
		else
			this.turret.stop();

		// Climber Calibration
		if (this.cont.getRawButton(7)) {
			if (this.cont.getRawButton(9))
				this.climberLeft.set(.1);
			else if (this.cont.getRawButton(11))
				this.climberLeft.set(-.1);
			else
				this.climberLeft.stop();

			if (this.cont.getRawButton(10))
				this.climberRight.set(.1);
			else if (this.cont.getRawButton(12))
				this.climberRight.set(-.1);
			else
				this.climberRight.stop();
		}

		if (this.cont.getRawButton(8)) {
			if (this.cont.getRawButton(9))
				this.climberLeft.set(.5);
			else if (this.cont.getRawButton(11))
				this.climberLeft.set(-.5);
			else
				this.climberLeft.stop();

			if (this.cont.getRawButton(10))
				this.climberRight.set(.5);
			else if (this.cont.getRawButton(12))
				this.climberRight.set(-.5);
			else
				this.climberRight.stop();
		}

		// Load a single cargo
		if (this.xbox.getRawButtonPressed(2))
			this.autoShooter.loadSingle.schedule();

		// Unload a single cargo
		if (this.xbox.getRawButtonPressed(4))
			this.autoShooter.unloadSingle.schedule();

		// Set the speed based on the trigger(1) of the joystick
		if (this.cont.getRawButton(1) && !this.cont.getRawButton(5) && !this.cont.getRawButton(3)) {
			driveSpeed = 1;
		} else if (!this.cont.getRawButton(5) && !this.cont.getRawButton(3)) {
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

		if (this.cont.getRawButton(2)) {
			this.left.set(
					(this.cont.getRawAxis(1) + this.cont.getRawAxis(0)) * driveSpeed);
			this.right.set(
					(this.cont.getRawAxis(1) - this.cont.getRawAxis(0)) * driveSpeed);
		} else {
			this.left.set(
					(-this.cont.getRawAxis(1) + this.cont.getRawAxis(0)) * driveSpeed);
			this.right.set(
					(-this.cont.getRawAxis(1) - this.cont.getRawAxis(0)) * driveSpeed);
		}

		if (this.xbox.getRawButton(7)) {
			SmartDashboard.putBoolean("target aquired", turret.limelightAlign());
		}

		if (this.xbox.getRawButton(9))
			Robot.limelight.setLEDMode(LEDMode.kOff);
		else if (this.xbox.getRawButton(10))
			Robot.limelight.setLEDMode(LEDMode.kOn);
	}
}
