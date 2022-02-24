package frc.robot;

import edu.wpi.first.math.controller.PIDController;

public class ShooterCommands {
    PIDController teleDrivePID;
    PIDController teleTurnPID;
    // TODO: Replace 'imu' with the imu's getDegrees method
    double imu;
    double circumference;
    public ShooterCommands(Double[] pidDrive_, Double[] pidTurn_,
            double circumference_) {
        teleDrivePID = new PIDController(pidDrive_[0], pidDrive_[1], pidDrive_[2]);
        teleTurnPID = new PIDController(pidTurn_[0], pidTurn_[1], pidTurn_[2]);
        imu = 0;
        circumference = circumference_;
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
