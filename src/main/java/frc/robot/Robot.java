// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
   * 
   * Motor PWM ID's:
   * Loader: 0
   */

  public static BrushlessNEO right = new BrushlessNEO(1, false);
  public static BrushlessNEO left = new BrushlessNEO(2, true);
  public static BrushlessNEO shooter = new BrushlessNEO(3, true);
  public static BrushlessNEO intake = new BrushlessNEO(4, false);
  //TODO: fix climber CAN ids and inversions
  public static BrushlessNEO climber1 = new BrushlessNEO(5, false);
  public ststic brushlessNEO climber2 = new BrushlessNEO(6, false)
  public static Spark loader = new Spark(0);
  public static Joystick cont = new Joystick(0);
  public static XboxController xbox = new XboxController(1);
  public static Double[] drivePID = { 0.0, 1.0, 2.0 };
  public static Double[] turnPID = { 0.0, 0.1, 0.2 };
  public static AutoCommands auto = new AutoCommands(drivePID, turnPID, 6.0);
  public static Drivetrain drive = new Drivetrain(right, left, shooter, intake, loader, climber1, climber2, cont, xbox, auto);

  @Override
  public void robotInit() {
    // Reset encoders
    right.resetPosition();
    left.resetPosition();
    shooter.resetPosition();
    intake.resetPosition();
    // Set the Spark controller to inverted
    loader.setInverted(true);
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

    // Shooter status
    SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
    SmartDashboard.putBoolean("Shooter Ready", (shooter.getRPM() > 3000));

    // Temprature warnings
    SmartDashboard.putBoolean("Right Tempratue", !(right.getTemp() > 150));
    SmartDashboard.putBoolean("Left Tempratue", !(left.getTemp() > 150));
    SmartDashboard.putBoolean("Shooter Tempratue", !(shooter.getTemp() > 150));
    SmartDashboard.putBoolean("Intake Tempratue", !(intake.getTemp() > 150));
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    // Autonomous code in Auto.java
    Auto.AutoDrive();
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
  }

  @Override
  public void testPeriodic() {
  }
}
