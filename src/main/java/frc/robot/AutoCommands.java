package frc.robot;

import edu.wpi.first.math.controller.PIDController;

public class AutoCommands {
    PIDController drivePID;
    PIDController turnPID;
    double imu;
    double circumference;

    public AutoCommands(Double[] pidDrive_, Double[] pidTurn_,
            double circumference_) {
        drivePID = new PIDController(pidDrive_[0], pidDrive_[1], pidDrive_[2]);
        turnPID = new PIDController(pidTurn_[0], pidTurn_[1], pidTurn_[2]);
        imu = 0;
        circumference = circumference_;
    }

    // Reset Encoders before using
    // Returns true when reached
    public void driveFeet(double feet, double speed) {
        if (Math.abs((Robot.right.getPosition() * circumference) - feet) < 0.25)
            Robot.drive.stop();

        Robot.left.set(speed);
        Robot.right.set(speed + drivePID.calculate(Robot.right.getPosition() - Robot.left.getPosition(), 0));
    }
}