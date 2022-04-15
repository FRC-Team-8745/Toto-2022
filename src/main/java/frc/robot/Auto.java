package frc.robot;

import static frc.robot.constants.Constants.*;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class Auto {
	// The four auto programs
	public enum AutoSelections {
		FullAuto, ShortAuto, ShootnTarmac, Shoot;
	}

	// Start AutoCommands
	public static AutoCommands auto = new AutoCommands();

	public void auto() {
		// Set the auto program based on the number from shuffleboard
		int autoProgram = (int) SmartDashboard.getNumber("Auto", 0);
		AutoSelections selection = AutoSelections.values()[autoProgram];

		switch (selection) {
			case FullAuto: // Full auto program
				fullAuto(kNormalAutoDriveDistance);
				break;

			case ShortAuto: // Full auto, with a shorter distance to drive
				fullAuto(kShortAutoDriveDistance);
				break;

			case ShootnTarmac: // Deploy intake, shoot, and exit the tarmac
				new SequentialCommandGroup(
						new InstantCommand(() -> deployIntake()),
						new InstantCommand(() -> Robot.autoShooter.shootFull()),
						new WaitCommand(4),
						new InstantCommand(() -> auto.driveFeetRelitave(7.583, 0.25))).schedule();
				break;

			case Shoot: // Deploy intake, shoot
				new SequentialCommandGroup(
						new InstantCommand(() -> deployIntake()),
						new InstantCommand(() -> Robot.autoShooter.shootFull())).schedule();
				break;

		}
	}

	// Deploy the intake by lowering the left climber arm
	public void deployIntake() {
		new SequentialCommandGroup(
				new InstantCommand(() -> Robot.climberLeft.set(-0.5)),
				new WaitCommand(0.3),
				new InstantCommand(() -> Robot.climberLeft.stop())).schedule();
	}

	// Standard two ball auto program with input for the distance to the cargo
	public void fullAuto(double distance) {
		new SequentialCommandGroup(
				new InstantCommand(() -> deployIntake()),
				new InstantCommand(() -> Robot.autoShooter.shootFull()),
				new WaitCommand(4),
				new InstantCommand(() -> Robot.intake.set(0.5)),
				new WaitUntilCommand(() -> auto.driveFeetRelitave(distance, 0.2)),
				new WaitCommand(1),
				new InstantCommand(() -> Robot.intake.set(1)),
				new WaitCommand(1),
				new WaitUntilCommand(() -> auto.driveFeetRelitave(distance - 0.25, -0.35)),
				new InstantCommand(() -> Robot.intake.stop()),
				new InstantCommand(() -> Robot.autoShooter.shootFull())).schedule();
	}

	// Auto for testing the intake, never used in actual code
	public static void testAuto(double speed, double intake) {
		new SequentialCommandGroup(
				new InstantCommand(() -> Robot.intake.set(intake)),
				new WaitUntilCommand(() -> auto.driveFeetRelitave(4, speed)),
				new WaitCommand(1),
				new InstantCommand(() -> Robot.intake.set(1)),
				new WaitCommand(1),
				new WaitUntilCommand(() -> auto.driveFeetRelitave(4, -speed)),
				new InstantCommand(() -> Robot.intake.stop())).schedule();
	}
}