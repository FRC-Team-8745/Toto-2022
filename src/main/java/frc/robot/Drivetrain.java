package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

/*
 * Button numbers: [1: trigger] [2: sidebutton] [3:labeled] [4: labeled] [5:
 * labeled] [6: labeled] [7: labeled] [8: labeled] [9: labeled] [10: labeled]
 * [11: labeled] [12: labeled]
 */

public class Drivetrain {
    private BrushlessNEO right;
    private BrushlessNEO left;
    private BrushlessNEO shooter;
    private BrushlessNEO intake;
    private BrushlessNEO climber1;
    private BrushlessNEO climber2;
    private Spark loader;
    //Joystick Import
    private Joystick cont;
    //Xbox Import
    private XboxController xbox;
    private int step;
    private Shooter com;

    public Drivetrain(BrushlessNEO right_, BrushlessNEO left_, BrushlessNEO shooter_, BrushlessNEO intake_,
            Spark loader_, BrushlessNEO climber1_, BrushlessNEO climber2_, Joystick cont_, XboxController xbox_, Shooter com_) {
        right = right_;
        left = left_;
        shooter = shooter_;
        intake = intake_;
        loader = loader_;
        climber1 = climber1_;
        climber2 = climber2_;
        cont = cont_;
        xbox = xbox_;
        com = com_;
    }

    public void setDrive(double speed) {
        if (speed <= 1 && speed >= -1) {
            this.right.set(speed);
            this.left.set(speed);
        }
    }

    public void stopDrive() {
        this.right.stop();
        this.left.stop();
    }

    public void resetEncoders() {
        this.right.resetPosition();
        this.left.resetPosition();
    }

    // Declare variable for drive speed
    private double driveSpeed;

    public void driveTeleop() {

        // Set the speed based on the trigger(1) of the joystick
        if (this.cont.getRawButtonPressed(1)) {
            driveSpeed = 1;
        } else {
            driveSpeed = 0.5;
        }
        /*

        // Shooter
        if (this.cont.getRawButton(3))
            this.shooter.set(1);
        else
            this.shooter.stop();
        */

        //Shooter Pottential Fire Function
        if(this.cont.getRawButtonPressed(1)){
        switch (this.step) {
            case 0:
                this.shooter.set(1);
                this.step += this.com.returnValue();
                break;
            case 1:
                this.loader.set(1);
                this.step += this.com.returnValue();
                break;
            case 2:
                this.loader.stopMotor();
                this.shooter.stop();
                this.step = 0;
                break;
            }
        }
        
        /*
        // Loader
        if (this.cont.getRawButton(4))
            this.loader.set(1);
        else
            this.loader.stopMotor();
        */
        
        // Intake
        if (this.cont.getRawButton(5))
            this.intake.set(1);
        else
            this.intake.stop();
        
        // Climbers
        if (this.cont.getRawButton(6)) {
            this.climber1.set(.25);
            this.climber2.set(.25);
        } else if (this.cont.getRawButton(4)) {
            this.climber1.set(-.25);
            this.climber2.set(-.25);
        } else {
            this.climber1.set(0);
            this.climber2.set(0);
        }

        /*
         * Set variables for the left and right motors to the controllers axis, using
         * both the up/down and left/right joystick values and some math; multiplied by
         * the speed modifier
         * 
         * If the side button (2) is pressed, reverse the controls so the back of the
         * robot acts as the front while driving
         */

        if (this.cont.getRawButton(2)) {
            this.left.set(
                    (-this.cont.getRawAxis(1) * 0.5 - this.cont.getRawAxis(0) * 0.5) * driveSpeed);
            this.right.set(
                    (-this.cont.getRawAxis(1) * 0.5 + this.cont.getRawAxis(0) * 0.5) * driveSpeed);
        } else {
            this.left.set(
                    (this.cont.getRawAxis(1) * 0.5 - this.cont.getRawAxis(0) * 0.5) * driveSpeed);
            this.right.set(
                    (this.cont.getRawAxis(1) * 0.5 + this.cont.getRawAxis(0) * 0.5) * driveSpeed);
        }
    }
}
