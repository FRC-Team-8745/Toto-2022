package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

public class Auto {
	public static AutoCommands auto = new AutoCommands();

	// Old code, in case something doesn't work

	/*/
	public void fullAuto() {
		new SequentialCommandGroup(
				new InstantCommand(() -> Robot.climberLeft.set(0.5)),
				new InstantCommand(() -> Robot.autoShooter.shootFull.schedule()),
				new WaitCommand(0.5),
				new InstantCommand(() -> Robot.climberLeft.stop()),
				new WaitCommand(3.5),
				new InstantCommand(() -> Robot.intake.set(1)),
				new WaitUntilCommand(() -> auto.driveFeet(7.583, 0.25, true)),
				new WaitUntilCommand(() -> auto.driveFeet(7.583, -0.25, true)),
				new InstantCommand(() -> Robot.intake.stop()),
				new InstantCommand(() -> Robot.autoShooter.shootFull.schedule())
		).schedule();
	}
	/*/

	public void fullAuto() {
		new SequentialCommandGroup(
				new InstantCommand(() -> deployIntake()),
				new InstantCommand(() -> Robot.autoShooter.shootFull.schedule()),
				new WaitCommand(4),
				new InstantCommand(() -> Robot.intake.set(1)),
				new WaitUntilCommand(() -> auto.driveFeet(7.583, 0.25, true)),
				new WaitUntilCommand(() -> auto.driveFeet(7.583, -0.25, true)),
				new InstantCommand(() -> Robot.intake.stop()),
				new InstantCommand(() -> Robot.autoShooter.shootFull.schedule())
		).schedule();
	}

	public void deployIntake() {
		new SequentialCommandGroup(
			new InstantCommand(() -> Robot.climberLeft.set(-0.5)),
			new WaitCommand(0.3),
			new InstantCommand(() -> Robot.climberLeft.stop())
		).schedule();
	}
}