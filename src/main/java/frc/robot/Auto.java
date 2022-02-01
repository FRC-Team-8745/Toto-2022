package frc.robot;

//imports packages allowing us to code without all the imports in this file
import frc.robot.BaseRobot.*;

public class Auto {
    
    public static void AutoDrive(){
        //make the robot drive forward towards the low hub
        /*if(RobotBase.leftEncoder.getPosition() < 18 && RobotBase.rightEncoder.getPosition() < 18){
            RobotBase.leftDrive.set(0.1);
            RobotBase.rightDrive.set(0.1);
        } else {
            RobotBase.rightDrive.stopMotor();;
            RobotBase.leftDrive.stopMotor();
        }
        //once it reaches the low hub, make itdump the pre-loaded ball into the hu
        /*if((RobotBase.leftEncoder.getPosition() >17 && RobotBase.leftEncoder.getPosition() <19) && 
        (RobotBase.rightEncoder.getPosition() >17 && RobotBase.rightEncoder.getPosition() <19 )){
            RobotBase.rotate.set(0.1);
        } else if(RobotBase.rotateEncoder.getPosition() > 17 && RobotBase.rotateEncoder.getPosition() <
         19){
            RobotBase.rotate.stopMotor();
        } */
        //make it drive out of the tarmac
        //if(RobotBase.rotateEncoder.getPosition() > 17 && RobotBase.rotateEncoder.getPosition() < 19){
        //if((RobotBase.leftEncoder.getPosition() >17 && RobotBase.leftEncoder.getPosition() <19) && (RobotBase.rightEncoder.getPosition() >17 && RobotBase.rightEncoder.getPosition() <19 )){
        if(RobotBase.leftEncoder.getPosition() > -18 && RobotBase.rightEncoder.getPosition() > -18){    
            RobotBase.leftDrive.set(-0.1);
            RobotBase.rightDrive.set(-0.1); 
        //if((RobotBase.leftEncoder.getPosition() > -19 && RobotBase.leftEncoder.getPosition() < -17) && (RobotBase.rightEncoder.getPosition() > -19 && RobotBase.rightEncoder.getPosition() < -17 ))
        } else {
            RobotBase.leftDrive.stopMotor();
            RobotBase.rightDrive.stopMotor();
        }
        }
}

