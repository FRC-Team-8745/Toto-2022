package frc.robot;

import frc.robot.BaseRobot.*;

public class Auto {
    
    public static void AutoDrive(){
        if(RobotBase.leftEncoder.getPosition() < 18 && RobotBase.rightEncoder.getPosition() < 18){
        RobotBase.leftDrive.set(0.1);
        RobotBase.rightDrive.set(0.1);
        } else {
        RobotBase.rightDrive.set(0);
        RobotBase.leftDrive.set(0);
        }
        if((RobotBase.leftEncoder.getPosition() >17 && RobotBase.leftEncoder.getPosition() <19) && 
        (RobotBase.rightEncoder.getPosition() >17 && RobotBase.rightEncoder.getPosition() <19 )){
            RobotBase.rotate.set(0.1);

        }
    }
}
