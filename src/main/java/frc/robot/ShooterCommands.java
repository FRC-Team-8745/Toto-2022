package frc.robot;

import edu.wpi.first.math.controller.PIDController;

public class ShooterCommands {
    PIDController teleDrivePID;
    PIDController teleTurnPID;
    // TODO: Replace 'imu' with the imu's getDegrees method
    double imu;
    double circumference;
    public ShooterCommands() {
    }

    public int teleShoot(double shoot, double speed, boolean resetOnEnd) {
        if (Math.abs(imu - shoot) < 5) {
            Robot.drive.stopDrive();
            return 1;
        }

        Robot.left.set(speed);
        Robot.right.set(-speed - teleTurnPID.calculate(imu - shoot, 0));
        return 0;
    }
    public int returnValue(){
        return 1;
    }        
}
