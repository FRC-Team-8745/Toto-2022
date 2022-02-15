package frc.robot;

//imports packages allowing us to code without all the imports in this file
import frc.robot.BaseRobot.*;

public class Auto {
    
    public static void AutoDrive(){
        //make the robot drive backward to the tarmac
        //TODO: fix guestiment
        if(RobotBaseX.leftEncoder.getPosition() > -18 && RobotBaseX.rightEncoder.getPosition() > -18){
            RobotBaseX.leftDrive.set(0.5);
            RobotBaseX.rightDrive.set(0.5);
        } else {
            RobotBaseX.rightDrive.stopMotor();;
            RobotBaseX.leftDrive.stopMotor();
            RobotBaseX.leftEncoder.setPosition(0);
            RobotBaseX.rightEncoder.setPosition(0);
            RobotBaseX.shooterEncoder.setPosition(0);
            RobotBaseX.intakeEncoder.setPosition(0);
        }
        //shoot cargo
        //TODO: fix guestiment
        if((RobotBaseX.leftEncoder.getPosition() <-17 && RobotBaseX.leftEncoder.getPosition() >-19) && 
        (RobotBaseX.rightEncoder.getPosition() < -17 && RobotBaseX.rightEncoder.getPosition() >-19 )){
            RobotBaseX.shooter.set(1);
        } else if(RobotBaseX.shooterEncoder.getPosition()>17 && RobotBaseX.shooterEncoder.getPosition()<19){
            RobotBaseX.shooter.stopMotor();
            RobotBaseX.leftEncoder.setPosition(0);
            RobotBaseX.rightEncoder.setPosition(0);
            RobotBaseX.shooterEncoder.setPosition(0);
            RobotBaseX.intakeEncoder.setPosition(0);
        } 
        //make it drive out of the tarmac
        //if(RobotBaseX.rotateEncoder.getPosition() > 17 && RobotBaseX.rotateEncoder.getPosition() < 19){
        //if((RobotBaseX.leftEncoder.getPosition() >17 && RobotBaseX.leftEncoder.getPosition() <19) && (RobotBaseX.rightEncoder.getPosition() >17 && RobotBaseX.rightEncoder.getPosition() <19 )){
        //TODO: fix guestiment
        if(RobotBaseX.leftEncoder.getPosition() > -10 && RobotBaseX.rightEncoder.getPosition() > -10){    
            RobotBaseX.leftDrive.set(-0.1);
            RobotBaseX.rightDrive.set(-0.1); 
        //if((RobotBaseX.leftEncoder.getPosition() > -19 && RobotBaseX.leftEncoder.getPosition() < -17) && (RobotBaseX.rightEncoder.getPosition() > -19 && RobotBaseX.rightEncoder.getPosition() < -17 ))
        } else {
            RobotBaseX.leftDrive.stopMotor();
            RobotBaseX.rightDrive.stopMotor();
            RobotBaseX.leftEncoder.setPosition(0);
            RobotBaseX.rightEncoder.setPosition(0);
            RobotBaseX.shooterEncoder.setPosition(0);
            RobotBaseX.intakeEncoder.setPosition(0);
        }
        }
}

