package frc.robot;

import edu.wpi.first.math.controller.PIDController;

public class Shooter {
    PIDController teleDrivePID;
    PIDController teleTurnPID;
    public Shooter(BrushlessNEO shooter) {
    }

    public int teleShoot(double shoot, double speed, boolean resetOnEnd) {
\

        Robot.left.set(speed);
        Robot.right.set(-speed - teleTurnPID.calculate(imu - shoot, 0));
        return 0;
    }
    public int returnValue(){
        return 1;
    }        
}
