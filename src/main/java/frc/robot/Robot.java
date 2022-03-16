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
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;

public class Robot extends TimedRobot {
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
	 * 
	 * Robot perimiter with bumpers is 33" x 39"
	 * Robot weight is 107 pounds
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

	public static final double kDriveGearbox = 10.71;

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
		// Set the Spark controller to inverted
		loader.setInverted(true);
		// Lock climber arms
		climberRight.idleMode(IdleMode.kBrake);
		climberLeft.idleMode(IdleMode.kBrake);
		SmartDashboard.putNumber("Auto", Auto.kDefaultAuto);
		SmartDashboard.putNumber("Short Auto Distance", 6.61);

		// Setup and put the front camera on the dashboard
		UsbCamera frontCamera = CameraServer.startAutomaticCapture();
		frontCamera.setFPS(30);
		frontCamera.setResolution(350, 350);
	}

	@Override
	public void robotPeriodic() {
		// Shooter status
		SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
		SmartDashboard.putBoolean("Shooter Ready", (shooter.getRPM() > 3000));

		// Temprature warnings
		SmartDashboard.putBoolean("Right Tempratue", (right.getTemp() < 150));
		SmartDashboard.putBoolean("Left Tempratue", (left.getTemp() < 150));

		// Runs the command scheduler while the robot is on
		CommandScheduler.getInstance().run();

		// Set motor ramp speeds
		shooter.setRamp(0.5);
		intake.setRamp(0.5);
	}

	@Override
	public void autonomousInit() {
		right.resetPosition();
		left.resetPosition();
		noCont.auto();
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
		shooter.stop();
		loader.stopMotor();
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
	}

	@Override
	public void testPeriodic() {
	}
}