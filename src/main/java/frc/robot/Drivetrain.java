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
    private Spark loader;
    //Joystick Import
    private Joystick cont;
    //Xbox Import
    private XboxController xbox;
    private AutoCommands auto;
    private int step;

    public Drivetrain(BrushlessNEO right_, BrushlessNEO left_, BrushlessNEO shooter_, BrushlessNEO intake_,
            Spark loader_, Joystick cont_, XboxController xbox_, AutoCommands auto_) {
        right = right_;
        left = left_;
        shooter = shooter_;
        intake = intake_;
        loader = loader_;
        cont = cont_;
        xbox = xbox_;
        auto = auto_;
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

        // Shooter
        if (this.cont.getRawButton(3))
            this.shooter.set(1);
        else
            this.shooter.stop();

        //Shooter Pottential Fire Function
        if(this.cont.getRawButton(1)){
            this.shooter.set(1);
        } else if(this.shooter.getPosition() > 5 && this.shooter.getPosition() < 6){
            this.loader.set(1);
        } else if(this.shooter.getPosition() > 40 && this.shooter.getPosition() < 41){
            this.loader.stopMotor();
            this.shooter.stop();
        }
        // Loader
        if (this.cont.getRawButton(4))
            this.loader.set(1);
        else
            this.loader.stopMotor();

        // Intake
        if (this.cont.getRawButton(5))
            this.intake.set(1);
        else
            this.intake.stop();

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