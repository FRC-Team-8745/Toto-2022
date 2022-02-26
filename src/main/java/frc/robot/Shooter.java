package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

public class Shooter {

    // Unload a single ball from the loader
    SequentialCommandGroup unloadSingle = new SequentialCommandGroup(
            new InstantCommand(() -> Robot.loader.set(-1)),
            new WaitCommand(2.5),
            new InstantCommand(() -> Robot.loader.stopMotor()));

    // Load a single ball to a point directly before the shooter
    SequentialCommandGroup loadSingle = new SequentialCommandGroup(
            new InstantCommand(() -> Robot.loader.set(1)),
            new WaitCommand(2),
            new InstantCommand(() -> Robot.loader.stopMotor()));

    // Shoot the ball at a lower speed (80%)
    SequentialCommandGroup shooterSlow = new SequentialCommandGroup(
            new InstantCommand(() -> Robot.shooter.set(0.8)),
            new WaitCommand(2),
            new InstantCommand(() -> Robot.loader.set(1)),
            new WaitCommand(2),
            new InstantCommand(() -> Robot.loader.stopMotor()),
            new InstantCommand(() -> Robot.shooter.stop()));

    // Shoot the ball at full speed
    SequentialCommandGroup shootSingle = new SequentialCommandGroup(
            new InstantCommand(() -> Robot.shooter.set(0.8)),
            new WaitCommand(2),
            new InstantCommand(() -> Robot.loader.set(1)),
            new WaitCommand(2),
            new InstantCommand(() -> Robot.loader.stopMotor()),
            new InstantCommand(() -> Robot.shooter.stop()));
}
