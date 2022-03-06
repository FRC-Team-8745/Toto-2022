package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

public class Auto {
	private AutoCommands autoCom;
	private Turret turret;

	public Auto(AutoCommands auto_, Turret turret_) {
		autoCom = auto_;
		turret = turret_;
	}

	public void AutoDrive1() {
		// make the robot drive backward to the tarmac
		// In theory, this code will drive backward out of the tarmac, shoot, and then
		// drive to pick up a ball
		SequentialCommandGroup workAuto = new SequentialCommandGroup(
				new InstantCommand(() -> Robot.climberLeft.set(0.2)),
				new WaitCommand(0.5),
				new InstantCommand(() -> Robot.climberLeft.stop()),
				new WaitUntilCommand(() -> autoCom.driveFeet(7, -0.2, true)),
				// new WaitCommand(1),
				new WaitUntilCommand(() -> autoCom.turnDegrees(120, 0.2, true)),
				new WaitUntilCommand(() -> turret.rotateDegrees(-120, 0.5)),
				// new WaitCommand(2),
				new InstantCommand(() -> Robot.autoShooter.shootSingle.schedule()),
				new WaitCommand(4),
				new InstantCommand(() -> Robot.intake.set(0.2)),
				new WaitUntilCommand(() -> autoCom.driveFeet(7, 0.2, true)),
				new InstantCommand(() -> Robot.intake.stop()));
		workAuto.schedule();
	}
	/*
	 * public void AutoDrive2(){
	 * SequentialCommandGroup autoTwo = new SequentialCommandGroup(
	 * new InstantCommand(() -> Robot.climberLeft.set(0.2)),
	 * new WaitCommand(0.5),
	 * new InstantCommand(() -> Robot.climberLeft.stop()),
	 * new InstantCommand(() -> Robot.intake.set(0.2)),
	 * new WaitUntilCommand(() -> autoCom.drivefeet(7, 0.2, true)),
	 * new InstantCommand(() -> Robot.intake.stop()),
	 * //new WaitUntilCommand(() -> Robot.autoShooter.shootSingle.schedule()),
	 * //new WaitUntilCommand(() -> Robot.autoShooter.shootSingle.schedule()),
	 * );
	 * autoTwo.schedule();
	 * }
	 */

	public void simpleAuto() {
		new SequentialCommandGroup(
				new InstantCommand(() -> Robot.autoShooter.loadSingle.schedule()),
				new InstantCommand(() -> Robot.climberRight.set(0.5)),
				new WaitCommand(0.2),
				new InstantCommand(() -> Robot.climberRight.stop()),
				new WaitCommand(1.5),
				new InstantCommand(() -> Robot.autoShooter.shootSingle.schedule()),
				new WaitCommand(4),
				new InstantCommand(() -> Robot.right.resetPosition()),
                new InstantCommand(() -> Robot.intake.set(1)),
				new WaitUntilCommand(() -> autoCom.driveFeet(8, 0.5, true)),
                new InstantCommand(() -> Robot.intake.stop())).schedule();
	}
}