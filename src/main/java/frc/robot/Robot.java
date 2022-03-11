// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
	/**
	 * This function is run when the robot is first started up and should be used
	 * for any
	 * initialization code.
	 */

	/*
	 * Motor CAN ID's:
	 * Right drive: 1
	 * Left drive: 2
	 * Shooter: 3
	 * Intake: 4
	 * Right Climber: 5
	 * Left Climber: 6
	 * Turret: 7
	 * 
	 * Motor PWM ID's:
	 * Loader: 0
	 */

	public static BrushlessNEO right = new BrushlessNEO(1, true);
	public static BrushlessNEO left = new BrushlessNEO(2, false);
	public static BrushlessNEO shooter = new BrushlessNEO(3, true);
	public static BrushlessNEO intake = new BrushlessNEO(4, false);
	public static BrushlessNEO climberRight = new BrushlessNEO(5, false);
	public static BrushlessNEO climberLeft = new BrushlessNEO(6, false);
	public static BrushlessNEO turret = new BrushlessNEO(7, false);
	public static Spark loader = new Spark(0);
	public static Joystick cont = new Joystick(0);
	public static XboxController xbox = new XboxController(1);
	public static Shooter autoShooter = new Shooter();
	public static Drivetrain drive = new Drivetrain(right, left, intake, climberRight, climberLeft, turret, cont,
			xbox, autoShooter);
	public static Auto noCont = new Auto();

	@Override
	public void robotInit() {
		// Reset encoders
		right.resetPosition();
		left.resetPosition();
		shooter.resetPosition();
		intake.resetPosition();
		climberRight.resetPosition();
		climberLeft.resetPosition();
		turret.resetPosition();
		intake.setRamp(0.5);
		// Set the Spark controller to inverted
		loader.setInverted(true);
		// Lock climber arms
		climberRight.idleMode(IdleMode.kBrake);
		climberLeft.idleMode(IdleMode.kBrake);
		turret.idleMode(IdleMode.kBrake);
		SmartDashboard.putNumber("Auto", Auto.kDefaultAuto);
	}

	@Override
	public void robotPeriodic() {
		/*
		 * Put data about the robot on the dashboard.
		 * Includes:
		 * - All NEO encoder positions and CAN id's
		 * - Shooter RPM and status
		 * - Status lights for the temprature of each NEO motor
		 */

		// Shooter status
		SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
		SmartDashboard.putBoolean("Shooter Ready", (shooter.getRPM() > 3000));

		// Temprature warnings
		SmartDashboard.putBoolean("Right Tempratue", (right.getTemp() < 150));
		SmartDashboard.putBoolean("Left Tempratue", (left.getTemp() < 150));

		// Runs the command scheduler while the robot is on
		CommandScheduler.getInstance().run();

		// Set motor ramp speeds
		shooter.setRamp(1);
		turret.setRamp(0.5);
	}

	@Override
	public void autonomousInit() {
		noCont.auto();
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
	}

	@Override
	public void teleopPeriodic() {
		drive.driveTeleop();
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
	}

	@Override
	public void testInit() {
		SmartDashboard.putNumber("Auto", 0);
	}

	@Override
	public void testPeriodic() {
	}
}
