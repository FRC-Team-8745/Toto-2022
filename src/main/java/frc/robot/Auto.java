package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

public class Auto {

	public void AutoDrive() {
		// Make the robot drive backward to the tarmac
		// TODO: fix guesstimate
		SequentialCommandGroup shooter = new SequentialCommandGroup(
				new InstantCommand(() -> Robot.right.set(-1)),
				new InstantCommand(() -> Robot.left.set(-1)),
				new WaitCommand(1),
				new InstantCommand(() -> Robot.left.stop()),
				new InstantCommand(() -> Robot.right.stop()),
				new InstantCommand(() -> Robot.shooter.shootSingle()),
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
}