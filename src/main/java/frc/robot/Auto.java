package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;

public class Auto {
	// The default auto program to run
	public static final int kDefaultAuto = 0;

	// The distances to drive in the two autos that collect cargo
	private final double kNormalAutoDriveDistance = 7.583;
	private final double kShortAutoDriveDistance = 0;

	// The four auto programs
	public enum AutoSelections {
		FullAuto, ShortAuto, ShootnTarmac, Shoot;
	}

	// Start AutoCommands
	public static AutoCommands auto = new AutoCommands();

	// Set the auto program based on the number from shuffleboard
	public static int autoProgram = (int) SmartDashboard.getNumber("Auto", 0);
	public static AutoSelections selection = AutoSelections.values()[autoProgram];

	public void auto() {
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
						new InstantCommand(() -> Robot.autoShooter.shootFull.schedule()),
						new WaitCommand(4),
						new InstantCommand(() -> auto.driveFeet(7.583, 0.25, true))).schedule();
				break;

			case Shoot: // Deploy intake, shoot
				new SequentialCommandGroup(
						new InstantCommand(() -> deployIntake()),
						new InstantCommand(() -> Robot.autoShooter.shootFull.schedule())).schedule();
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
				new InstantCommand(() -> Robot.autoShooter.shootFull.schedule()),
				new WaitCommand(4),
				new InstantCommand(() -> Robot.intake.set(1)),
				new WaitUntilCommand(() -> auto.driveFeet(distance, 0.25, true)),
				new WaitCommand(1),
				new WaitUntilCommand(() -> auto.driveFeet(distance, -0.25, true)),
				new InstantCommand(() -> Robot.intake.stop()),
				new InstantCommand(() -> Robot.autoShooter.shootFull.schedule())).schedule();
	}
}