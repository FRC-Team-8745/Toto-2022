package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

public class Auto {
	private AutoCommands autoCom;
	private Turret autoTurret;

	public Auto(AutoCommands auto_, Turret turret_) {
		autoCom = auto_;
		autoTurret = turret_;
	}

	public void AutoDrive1() {
    // This auto drops the intake, Drives out of the tarmac, turns, shoots, and intakes another ball.
    new SequentialCommandGroup(
		new InstantCommand(() -> Robot.climberLeft.set(0.2)),
		new WaitCommand(0.5),
		new InstantCommand(() -> Robot.climberLeft.stop()),
		new WaitUntilCommand(() -> autoCom.driveFeet(7, -0.2, true)),
		// new WaitCommand(1),
		new WaitUntilCommand(() -> autoCom.turnDegrees(120, 0.2, true)),
		new WaitUntilCommand(() -> autoTurret.rotateDegrees(-120, 0.5)),
		// new WaitCommand(2),
		new InstantCommand(() -> Robot.autoShooter.shootSingle.schedule()),
		new WaitCommand(4),
		new InstantCommand(() -> Robot.intake.set(0.2)),
		new WaitUntilCommand(() -> autoCom.driveFeet(7, 0.2, true)),
		new InstantCommand(() -> Robot.intake.stop())).schedule();
}

	public void AutoDrive2(){
    // This Auto drops the intake, drives, picks up a ball, and shoots where it is at.
	new SequentialCommandGroup(
	   new InstantCommand(() -> Robot.climberLeft.set(0.2)),
	   new WaitCommand(0.5),
	   new InstantCommand(() -> Robot.climberLeft.stop()),
	   new InstantCommand(() -> Robot.intake.set(0.2)),
	   new WaitUntilCommand(() -> autoCom.driveFeet(7, 0.2, true)),
	   new InstantCommand(() -> Robot.intake.stop()),
	   new WaitUntilCommand(() -> Robot.autoShooter.shoot()),
	   new WaitUntilCommand(() -> Robot.autoShooter.shoot())).schedule();
	}


	public void simpleAuto() {
    // This Auto loads, 
    new SequentialCommandGroup(
        new InstantCommand(() -> Robot.autoShooter.loadSingle.schedule()),
        new InstantCommand(() -> Robot.climberRight.set(0.5)),
        new WaitCommand(0.2),
        new InstantCommand(() -> Robot.climberRight.stop()),
        new WaitCommand(1.5),
        new InstantCommand(() -> Robot.autoShooter.shootSingle.schedule()),
        new WaitCommand(4),
        new InstantCommand(() -> Robot.right.resetPosition()),
        new WaitUntilCommand(() -> autoCom.driveFeet(8, 0.5, true))).schedule();
	}
	
    public void newAuto(){
    // This loads, brings the intake down, intakes a ball, drives up to the hub, and shoots.
    new SequentialCommandGroup(
        new InstantCommand(() -> Robot.autoShooter.loadSingle.schedule()),
        new InstantCommand(() -> Robot.climberRight.set(0.5)),
        new WaitCommand(0.2),
        new InstantCommand(() -> Robot.climberRight.stop()),
        new WaitCommand(4),
        new InstantCommand(() -> Robot.right.resetPosition()),
        new InstantCommand(() -> Robot.intake.set(1)),
        new WaitUntilCommand(() -> autoCom.driveFeet(8, 0.5, true)),
        new InstantCommand(() -> Robot.intake.stop()),
        new InstantCommand(() -> Robot.right.resetPosition()),
        new InstantCommand(() -> Robot.left.resetPosition()),
        new WaitUntilCommand(() -> autoCom.driveFeet(-8, 0.5, true)),
        new WaitUntilCommand(() -> Robot.autoShooter.shoot()),
        new WaitUntilCommand(() -> Robot.autoShooter.shoot())).schedule();
    }
}