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

    public Drivetrain(BrushlessNEO right_, BrushlessNEO left_, Joystick cont_, XboxController xbox_) {
        right = right_;
        left = left_;
        cont = cont_;
        xbox = xbox_;
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

    // Declare variables for the speed modifiers
    private static double speedModifier;

    // Main drive method
    public void driveTeleop() {
        // Set the speed modifier depending on wether or not the trigger(1) on the
        // joystick is held down
        if (this.cont.getRawButtonPressed(1)) {
            speedModifier = 1;
        } else {
            speedModifier = 0.5;
        }

        // Set variables for the left and right motors to the controllers axis, using
        // both the up/down and left/right values and some math; multiplied by the speed
        // modifier
        this.left.set(
                (this.cont.getRawAxis(1) * 0.5 - this.cont.getRawAxis(0) * 0.5) * speedModifier);
        this.right.set(
                (this.cont.getRawAxis(1) * 0.5 + this.cont.getRawAxis(0) * 0.5) * speedModifier);
    }
}