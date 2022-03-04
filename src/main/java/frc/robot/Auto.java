package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

public class Auto {
private AutoCommands autoCom;
private Turret turret;
public Auto(AutoCommands auto_, Turret turret_){
    autoCom = auto_;
    turret = turret_;
}
    public void AutoDrive() {
    // make the robot drive backward to the tarmac
    //In theory, this code will drive backward out of the tarmac, shoot, and then drive to pick up a ball
        SequentialCommandGroup workAuto = new SequentialCommandGroup(
        new InstantCommand(() -> autoCom.driveFeet(-7, 0.2, true)),
        new InstantCommand(() -> Robot.autoShooter.shootSingle.schedule()),
        new WaitCommand(1),
        new InstantCommand(() -> autoCom.turnDegrees(120, 0.2, true)),
        new WaitUntilCommand (() -> Robot.turret.rotateDegrees(240, 0.5)),
        new WaitCommand(0.5),
        new InstantCommand(() -> autoCom.driveFeet(7, 0.2, true)),
        new InstantCommand(() -> Robot.intake.set(0.2)),
        new InstantCommand(() -> Robot.intake.stop()));
    workAuto.schedule();
    }
}