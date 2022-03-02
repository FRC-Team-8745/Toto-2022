package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

public class Autonomous {
private AutoCommands autoCom;
public Autonomous(AutoCommands auto_){
    autoCom = auto_;
}
    public void AutoDrive() {
    // make the robot drive backward to the tarmac
    //In theory, this code will drive backward out of the tarmac, shoot, and then drive to pick up a ball
        SequentialCommandGroup workAuto = new SequentialCommandGroup(
        new InstantCommand(() -> autoCom.driveFeet(-7, 1, true)),
        new InstantCommand(() -> autoCom.driveFeet(-7, 1, true)),
        new InstantCommand(() -> Robot.autoShooter.shootSingle.schedule()),
        new WaitCommand(1),
        new InstantCommand(() -> autoCom.turnDegrees(120, 1, true)),
        new WaitCommand(0.5),
        new InstantCommand(() -> autoCom.driveFeet(7, 1, true)),
        new InstantCommand(() -> Robot.intake.set(1)),
        new InstantCommand(() -> Robot.intake.stop()));
    workAuto.schedule();
    }
}