package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;

public class Autonomous {
	// The default auto program to run
	public static final int kDefaultAuto = 0;

	// The distances to drive in the two autos that collect cargo
	private final double kNormalAutoDriveDistance = 7.583;
	private final double kShortAutoDriveDistance = 6.083;

	// The four auto programs
	public enum AutoSelections {
		FullAuto, ShortAuto, ShootnTarmac, Shoot, fourBall;
	}

	// Start AutoCommands
	public static DriveCommands commands = new DriveCommands();

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
						new InstantCommand(() -> Robot.shootCommands.shootFull()),
						new WaitCommand(4),
						new InstantCommand(() -> commands.driveFeet(7.583, 0.25, true))).schedule();
				break;

			case Shoot: // Deploy intake, shoot
				new SequentialCommandGroup(
						new InstantCommand(() -> deployIntake()),
						new InstantCommand(() -> Robot.shootCommands.shootFull())).schedule();
				break;

			case fourBall: // Four ball auto program
				new SequentialCommandGroup(
						new InstantCommand(() -> deployIntake()),
						new InstantCommand(() -> Robot.shootCommands.load()),
						new WaitUntilCommand(() -> commands.driveFeet(2, 1, true)),
						new InstantCommand(() -> Robot.intake.set(0.5)),
						new WaitUntilCommand(() -> commands.driveFeet(4.25, 1, true)),
						new WaitUntilCommand(() -> commands.driveFeet(6.25, -1, true)),
						new InstantCommand(() -> Robot.shootCommands.shootDouble())

				).schedule();
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
				new InstantCommand(() -> Robot.shootCommands.shootFull()),
				new WaitCommand(4),
				new InstantCommand(() -> Robot.intake.set(0.5)),
				new WaitUntilCommand(() -> commands.driveFeet(distance, 0.2, true)),
				new WaitCommand(1),
				new WaitUntilCommand(() -> commands.driveFeet(distance - 0.75, -0.35, true)),
				new InstantCommand(() -> Robot.intake.stop()),
				new InstantCommand(() -> Robot.shootCommands.shootFull())).schedule();
	}

	// Auto for testing the intake, never used in actual code
	public static void testAuto(double speed, double intake) {
		new SequentialCommandGroup(
				new InstantCommand(() -> Robot.intake.set(intake)),
				new WaitUntilCommand(() -> commands.driveFeet(4, speed, true)),
				new WaitCommand(1),
				new InstantCommand(() -> Robot.intake.set(1)),
				new WaitCommand(1),
				new WaitUntilCommand(() -> commands.driveFeet(4, -speed, true)),
				new InstantCommand(() -> Robot.intake.stop())).schedule();
	}
}