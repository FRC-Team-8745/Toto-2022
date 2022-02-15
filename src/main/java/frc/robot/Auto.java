package frc.robot;

//imports packages allowing us to code without all the imports in this file
import frc.robot.BaseRobot.*;

public class Auto {
    
    public static void AutoDrive(){
        //make the robot drive backward to the tarmac
        if(RobotBaseX.leftEncoder.getPosition() > -18 && RobotBaseX.rightEncoder.getPosition() > -18){
            RobotBaseX.leftDrive.set(0.5);
            RobotBaseX.rightDrive.set(0.5);
        } else {
            RobotBaseX.rightDrive.stopMotor();;
            RobotBaseX.leftDrive.stopMotor();
        }
        /
        if((RobotBaseX.leftEncoder.getPosition() >17 && RobotBaseX.leftEncoder.getPosition() <19) && 
        (RobotBaseX.rightEncoder.getPosition() >17 && RobotBaseX.rightEncoder.getPosition() <19 )){
            RobotBaseX.rotate.set(0.1);
        } else if(RobotBaseX.rotateEncoder.getPosition() > 17 && RobotBaseX.rotateEncoder.getPosition() <
         19){
            RobotBaseX.rotate.stopMotor();
        } 
        //make it drive out of the tarmac
        //if(RobotBaseX.rotateEncoder.getPosition() > 17 && RobotBaseX.rotateEncoder.getPosition() < 19){
        //if((RobotBaseX.leftEncoder.getPosition() >17 && RobotBaseX.leftEncoder.getPosition() <19) && (RobotBaseX.rightEncoder.getPosition() >17 && RobotBaseX.rightEncoder.getPosition() <19 )){
        if(RobotBaseX.leftEncoder.getPosition() > -18 && RobotBaseX.rightEncoder.getPosition() > -18){    
            RobotBaseX.leftDrive.set(-0.1);
            RobotBaseX.rightDrive.set(-0.1); 
        //if((RobotBaseX.leftEncoder.getPosition() > -19 && RobotBaseX.leftEncoder.getPosition() < -17) && (RobotBaseX.rightEncoder.getPosition() > -19 && RobotBaseX.rightEncoder.getPosition() < -17 ))
        } else {
            RobotBaseX.leftDrive.stopMotor();
            RobotBaseX.rightDrive.stopMotor();
        }
        }
}

