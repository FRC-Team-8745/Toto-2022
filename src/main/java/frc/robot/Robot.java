// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

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

  public static BrushlessNEO right = new BrushlessNEO(2, true);
  public static BrushlessNEO left = new BrushlessNEO(1, false);
  public static Joystick cont = new Joystick(0);
  public static XboxController xbox = new XboxController(1);
  public static Double[] drivePID = { 0.0, 1.0, 2.0 };
  public static Double[] turnPID = { 0.0, 0.1, 0.2 };
  public static AutoCommands auto = new AutoCommands(drivePID, turnPID, 6.0);
  public static Drivetrain drive = new Drivetrain(right, left, cont, xbox, auto);

  @Override
  public void robotInit() {
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    // RobotBaseX.init();
  }

  @Override
  public void autonomousPeriodic() {
    // Auto.AutoDrive();
    // RobotBaseX.shuffleboard();
  }

  @Override
  public void teleopInit() {
    // RobotBaseX.init();
  }

  @Override
  public void teleopPeriodic() {
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
    Test.test();
  }
}
