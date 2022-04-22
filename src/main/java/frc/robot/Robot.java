// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.constants.Constants.*;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

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
	 * Linear Actuator: 1
	 * 
	 * Robot perimiter with bumpers is 33" x 39"
	 * Robot weight is 109 pounds
	 */

	public static BrushlessNEO climberRight = new BrushlessNEO(5, false);
	public static BrushlessNEO climberLeft = new BrushlessNEO(6, false);
	public static BrushlessNEO intake = new BrushlessNEO(4, false);
	public static Turret turret = new Turret();
	public static Spark loader = new Spark(0);
	public static Shooter autoShooter = new Shooter();
	public static Drivetrain drive = new Drivetrain(intake, turret, autoShooter);
	public static Auto noCont = new Auto();
	public static Shooter shooter = new Shooter();
	public static Limelight limelight = new Limelight();
	public static double kRobotStartPosX;
	public static double kRobotStartPosY;
	public static double kRobotStartRotY;
	public static Odometry odometry = new Odometry();

	@Override
	public void robotInit() {
		// Reset encoders
		drive.resetEncoders();
		turret.resetPosition();
		// Set the Spark controller to inverted
		loader.setInverted(true);
		// Lock climber arms
		climberRight.idleMode(IdleMode.kBrake);
		climberLeft.idleMode(IdleMode.kBrake);
		SmartDashboard.putNumber("Auto", kDefaultAuto);

		// Setup and put the front camera on the dashboard
		UsbCamera frontCamera = CameraServer.startAutomaticCapture();
		frontCamera.setFPS(30);
		frontCamera.setResolution(350, 350);

		// Enable the limelight
		limelight.enableProcessing();

		Shooter.shooter.setInverted(true);
		turret.autoTurretEnabled = true;

		SmartDashboard.putNumber("Start X", 6.951);
		SmartDashboard.putNumber("Start Y", 2.947);
		SmartDashboard.putNumber("Start Rotation", -155);

		// Set motor ramp speed
		intake.setRamp(0.5);
	}

	@Override
	public void robotPeriodic() {
		// Runs the command scheduler while the robot is on
		CommandScheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		turret.autoTurretEnabled = true;
		drive.resetEncoders();
		noCont.auto(autoToRun);
		drive.setBrakeMode(IdleMode.kBrake);
		turret.resetPosition();
	}

	@Override
	public void autonomousPeriodic() {
		turret.fullAlign();
	}

	@Override
	public void teleopInit() {
		shooter.stop();
		loader.stopMotor();
		turret.autoTurretEnabled = true;
	}

	@Override
	public void teleopPeriodic() {
		drive.driveTeleop();

		SmartDashboard.putNumber("inches coord", odometry.getDistanceToHub(odometry.getPose()));
		SmartDashboard.putBoolean("auto turret", turret.autoTurretEnabled);
		SmartDashboard.putNumber("RPM", Shooter.encoder.getVelocity());
	}

	@Override
	public void disabledInit() {
	}

	@Override
	public void disabledPeriodic() {
	}

	@Override
	public void testInit() {
		turret.resetPosition();

		SmartDashboard.putBoolean("Test Enabled", false);
		SmartDashboard.putNumber("Linear Actuator", 0);
		SmartDashboard.putNumber("Shooter test RPM", 0);
		limelight.enableProcessing();
	}

	@Override
	public void testPeriodic() {
		SmartDashboard.putNumber("turret pos", turret.getTurretDegrees());
		SmartDashboard.putNumber("Shooter RPM", Shooter.encoder.getVelocity());

		double LA = SmartDashboard.getNumber("Linear Actuator", 0);
		/*
		 * if (LA > kLinearActuatorMin && LA < kLinearActuatorMax)
		 * linearActuator.set(LA);
		 */
		if (!turret.atLimitLeft() && !turret.atLimitRight())
			turret.rotateDegrees(LA);
	}
}