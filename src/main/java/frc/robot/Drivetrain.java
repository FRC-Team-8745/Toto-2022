package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/*
 * Button numbers: [1: trigger] [2: sidebutton] [3:labeled] [4: labeled] [5:
 * labeled] [6: labeled] [7: labeled] [8: labeled] [9: labeled] [10: labeled]
 * [11: labeled] [12: labeled]
 */

public class Drivetrain {
    private BrushlessNEO right;
    private BrushlessNEO left;
    private Joystick cont;
    private XboxController xbox;
    private AutoCommands auto;
    private int step;

    public Drivetrain(BrushlessNEO right_, BrushlessNEO left_, Joystick cont_, XboxController xbox_,
            AutoCommands auto_) {
        right = right_;
        left = left_;
        cont = cont_;
        xbox = xbox_;
        auto = auto_;
    }

    public void set(double speed) {
        if (speed <= 1 && speed >= -1) {
            this.right.set(speed);
            this.left.set(speed);
        }
    }

    public void stop() {
        this.right.stop();
        this.left.stop();
    }

    public void resetEncoders() {
        this.right.resetPosition();
        this.left.resetPosition();
    }

    public void driveAuto() {
        this.resetEncoders();

        switch (step) {
            case 0:
                step += auto.driveFeet(2.3, 0.5, true);
                break;
            case 1:
                step += auto.turnDegrees(1243, 1, true);
                break;
        }
    }

    // Declare variable for drive speed
    private double driveSpeed;

    public void driveTeleop() {

        /*
         * Set the speed based on the trigger(1) of the joystick
         */

        if (this.cont.getRawButtonPressed(1)) {
            driveSpeed = 1;
        } else {
            driveSpeed = 0.5;
        }

        // Shooter
        if (this.xbox.getRawButtonPressed(3)) {
            // this.shooter.set(1);
        }

        // Loader
        if (this.xbox.getRawButtonPressed(2)) {
            // this.loader.set(1);
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