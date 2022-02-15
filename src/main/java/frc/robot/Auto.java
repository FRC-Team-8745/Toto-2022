/*
package frc.robot;

public class Auto {
    private static int step = 1;

    public static void AutoDrive() {
        // make the robot drive forward towards the low hub
        /*
         * if(RobotBaseX.leftEncoder.getPosition() < 18 &&
         * RobotBaseX.rightEncoder.getPosition() < 18){
         * RobotBaseX.leftDrive.set(0.1);
         * RobotBaseX.rightDrive.set(0.1);
         * } else {
         * RobotBaseX.rightDrive.stopMotor();;
         * RobotBaseX.leftDrive.stopMotor();
         * }
         * //once it reaches the low hub, make itdump the pre-loaded ball into the hu
         * /*if((RobotBaseX.leftEncoder.getPosition() >17 &&
         * RobotBaseX.leftEncoder.getPosition() <19) &&
         * (RobotBaseX.rightEncoder.getPosition() >17 &&
         * RobotBaseX.rightEncoder.getPosition() <19 )){
         * RobotBaseX.rotate.set(0.1);
         * } else if(RobotBaseX.rotateEncoder.getPosition() > 17 &&
         * RobotBaseX.rotateEncoder.getPosition() <
         * 19){
         * RobotBaseX.rotate.stopMotor();
         * }
         */
        // make it drive out of the tarmac
        // if(RobotBaseX.rotateEncoder.getPosition() > 17 &&
        // RobotBaseX.rotateEncoder.getPosition() < 19){
        // if((RobotBaseX.leftEncoder.getPosition() >17 &&
        // RobotBaseX.leftEncoder.getPosition() <19) &&
        // (RobotBaseX.rightEncoder.getPosition() >17 &&
        // RobotBaseX.rightEncoder.getPosition() <19 )){
            /*
        if (step == 1) {
            if (RobotBaseX.left.getPosition() < 40 && RobotBaseX.right.getPosition() < 40) {
                RobotBaseX.left.set(0.1);
                RobotBaseX.right.set(0.1);
                // if((RobotBaseX.leftEncoder.getPosition() > -19 &&
                // RobotBaseX.leftEncoder.getPosition() < -17) &&
                // (RobotBaseX.rightEncoder.getPosition() > -19 &&
                // RobotBaseX.rightEncoder.getPosition() < -17 ))
            } else {
                RobotBaseX.left.stop();
                RobotBaseX.right.stop();
                RobotBaseX.zeroEncoders();
                step ++;
            }
        }
        if (step == 2) {
            if(RobotBaseX.left.getPosition() < 20 && RobotBaseX.right.getPosition() > -20) {
                RobotBaseX.left.set(0.1);
                RobotBaseX.right.set(-0.1);
            } else {
                RobotBaseX.left.stop();
                RobotBaseX.right.stop();
                RobotBaseX.zeroEncoders();
                step ++;
            }
            
        }

    }
}
*/