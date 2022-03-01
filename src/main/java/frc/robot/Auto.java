package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

//FIXME: THIS CODE DOES NOT WORK FIX THIS FIRST
public class Auto {

    public void AutoDrive() {
        // make the robot drive backward to the tarmac
        // TODO: fix guesstimate
        SequentialCommandGroup shooter = new SequentialCommandGroup(
            new InstantCommand(() -> Robot.right.set(-1)),
            new InstantCommand(() -> Robot.left.set(-1)),
            new WaitCommand(1),
            new InstantCommand(() -> Robot.left.stop()),
            new InstantCommand(() -> Robot.right.stop()),
            new InstantCommand(() -> Robot.autoShooter.shootSingle.schedule()),
            new WaitCommand(3),
            new InstantCommand(() -> Robot.left.set(-1)),
            new InstantCommand(() -> Robot.right.set(1)),
            new WaitCommand(0.5),
            new InstantCommand(() -> Robot.left.stop()),
            new InstantCommand(() -> Robot.right.stop()),
            new InstantCommand(() -> Robot.right.set(1)),
            new InstantCommand(() -> Robot.left.set(1)),
            new InstantCommand(() -> Robot.intake.set(1))); 
            

        shooter.schedule();
    }
        /*switch (step) {
            case 0:
                step += this.auto.driveFeet(-4, 0.5, true);
                break;
            case 1:
                // TODO: find where these come from and add them again
                // step += this.auto.autoShoot(5, 1, true);
                break;
            case 2:
                step += this.auto.turnDegrees(45, 1, true);
                break;
            case 3:
                step += this.auto.driveFeet(4, 1, true);
                // TODO: find where these come from and add them again
                // this.auto.autoIntake(4, 0.5, true);
                break;
            case 4:

        }*/
    }