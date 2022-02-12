package frc.robot;

import frc.robot.BaseRobot.*;

public class Test {
    public static void test() {
        if (RobotBaseX.main.getRawButton(2)) 
            RobotBaseX.leftDrive.set(-1);
        else if (RobotBaseX.main.getRawButton(1))
            RobotBaseX.rightDrive.set(-0.5);
    }
}
