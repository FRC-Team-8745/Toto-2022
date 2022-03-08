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

  public static double sliderSpeed;

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

    // Encoder positions
    SmartDashboard.putNumber("Right Motor Position, ID " + right.getCAN(), right.getPosition());
    SmartDashboard.putNumber("Left Motor Position, ID " + left.getCAN(), left.getPosition());
    SmartDashboard.putNumber("Shooter Position, ID " + shooter.getCAN(), shooter.getPosition());
    SmartDashboard.putNumber("Intake Position, ID " + intake.getCAN(), intake.getPosition());
    SmartDashboard.putNumber("Right Climber Position, ID " + climberRight.getCAN(), climberRight.getPosition());
    SmartDashboard.putNumber("Left Climber Position, ID " + climberLeft.getCAN(), climberLeft.getPosition());
    SmartDashboard.putNumber("Turret Position, ID " + turret.getCAN(), turret.getPosition());

    // Shooter status
    SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
    SmartDashboard.putBoolean("Shooter Ready", (shooter.getRPM() > 3000));

    // Temprature warnings
    SmartDashboard.putBoolean("Right Tempratue", (right.getTemp() < 150));
    SmartDashboard.putBoolean("Left Tempratue", (left.getTemp() < 150));
    SmartDashboard.putBoolean("Shooter Tempratue", (shooter.getTemp() < 150));
    SmartDashboard.putBoolean("Intake Tempratue", (intake.getTemp() < 150));
    SmartDashboard.putBoolean("Right Climber Temprature", (climberRight.getTemp() < 150));
    SmartDashboard.putBoolean("Left Climber Temprature", (climberLeft.getTemp() < 150));
    SmartDashboard.putBoolean("Turret Temprature", (turret.getTemp() < 150));

    // Runs the command scheduler while the robot is on
    CommandScheduler.getInstance().run();

    shooter.setRamp(1);
    turret.setRamp(0.5);


    sliderSpeed = SmartDashboard.getNumber("RPM", 1);
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    // Autonomous code in Auto.java
    noCont.fullAuto();
  }

  @Override
  public void teleopInit() {
    SmartDashboard.putNumber("RPM", 1);
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
