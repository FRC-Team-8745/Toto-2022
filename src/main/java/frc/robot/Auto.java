package frc.robot;

public class Auto {

    public static void AutoDrive() {
        // make the robot drive backward to the tarmac
        // TODO: fix guesstimate
        if (Robot.left.getPosition() > -18 && Robot.right.getPosition() > -18) {
            Robot.left.set(0.5);
            Robot.right.set(0.5);
        } else {
            Robot.right.stop();
            Robot.left.stop();
            Robot.left.resetPosition();
            Robot.right.resetPosition();
            Robot.shooter.resetPosition();
            Robot.intake.resetPosition();
        }
        // shoot cargo
        // TODO: fix guesstimate
        if ((Robot.left.getPosition() < -17 && Robot.left.getPosition() > -19) &&
                (Robot.right.getPosition() < -17 && Robot.right.getPosition() > -19)) {
            Robot.shooter.set(1);
        } else if (Robot.shooter.getPosition() > 17 && Robot.shooter.getPosition() < 19) {
            Robot.shooter.stop();
            Robot.left.resetPosition();
            Robot.right.resetPosition();
            Robot.shooter.resetPosition();
            Robot.intake.resetPosition();
        }

        if (Robot.left.getPosition() > -10 && Robot.right.getPosition() > -10) {
            Robot.left.set(-0.1);
            Robot.right.set(-0.1);
        } else {
            Robot.left.stop();
            Robot.right.stop();
            Robot.left.resetPosition();
            Robot.right.resetPosition();
            Robot.shooter.resetPosition();
            Robot.intake.resetPosition();
        }
    }
}
