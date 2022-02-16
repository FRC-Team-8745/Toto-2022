package frc.robot;

//Import the central system for the components
import frc.robot.BaseRobot.*;

/*
 * Button numbers: [1: trigger] [2: sidebutton] [3:labeled] [4: labeled] [5:
 * labeled] [6: labeled] [7: labeled] [8: labeled] [9: labeled] [10: labeled]
 * [11: labeled] [12: labeled]
 */

public class Drivetrain {

    // Declare variables for the speed modifiers
    private static double speedModifierDriving;
    private static boolean runShooter;

    // Main drive method
    public static void drive() {

        // Set the speed modifier depending on wether or not the trigger(1) on the
        // joystick is held down
        if (RobotBaseX.main.getRawButton(1)) {
            speedModifierDriving = 1;
        } else {
            speedModifierDriving = 0.5;
        }
        //intake code
        if(RobotBaseX.main.getRawButton(2)){
            RobotBaseX.intake.set(0.5); 
        }        
        // If sidebutton is pressed start the shooter
        if (RobotBaseX.main.getRawButtonPressed(3)) {
           runShooter = true;
        }
        // Stop motor if button 3 pressed
        if (RobotBaseX.main.getRawButtonPressed(4)) {
            runShooter = false;
        }

        RobotBaseX.shooter.set(runShooter ? 1.0 : 0.0);


        // If button 4 pressed start loader otherwise stop it.
        if (RobotBaseX.main.getRawButtonPressed(1)) {
            RobotBaseX.cLifter.set(1);
        } else {
            RobotBaseX.cLifter.set(0);
        }
        
        // Set variables for the left and right motors to the controllers axis, using
        // both the up/down and left/right values and some math; multiplied by the speed
        // modifier
        RobotBaseX.leftDrive.set(
                (-RobotBaseX.main.getRawAxis(1) * 0.5 - RobotBaseX.main.getRawAxis(0) * 0.5) * speedModifierDriving);
        RobotBaseX.rightDrive.set(
                (-RobotBaseX.main.getRawAxis(1) * 0.5 + RobotBaseX.main.getRawAxis(0) * 0.5) * speedModifierDriving);
    }
}