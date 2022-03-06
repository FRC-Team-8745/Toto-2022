package frc.robot;

import edu.wpi.first.wpilibj2.command.*;

public class Shooter {
        Boolean yeTrue = true;

        // Unload a single ball from the loader & shooter
        SequentialCommandGroup unloadSingle = new SequentialCommandGroup(
                        new InstantCommand(() -> Robot.loader.set(-1)),
                        new InstantCommand(() -> Robot.shooter.set(-0.2)),
                        new WaitCommand(2),
                        new InstantCommand(() -> Robot.loader.stopMotor()),
                        new InstantCommand(() -> Robot.shooter.stop()));

        // Load a single ball to a point directly before the shooter
        SequentialCommandGroup loadSingle = new SequentialCommandGroup(
                        new InstantCommand(() -> Robot.loader.set(1)),
                        new WaitCommand(1.7),
                        new InstantCommand(() -> Robot.loader.stopMotor()));

        // Shoot the ball at a lower speed (80%)
        SequentialCommandGroup shooterSlow = new SequentialCommandGroup(
                        new InstantCommand(() -> Robot.shooter.set(Robot.sliderSpeed)),
                        new WaitCommand(2),
                        new InstantCommand(() -> Robot.loader.set(1)),
                        new WaitCommand(2),
                        new InstantCommand(() -> Robot.loader.stopMotor()),
                        new InstantCommand(() -> Robot.shooter.stop()));

        // Shoot the ball at full speed
        SequentialCommandGroup shootSingle = new SequentialCommandGroup(
                        new InstantCommand(() -> Robot.shooter.set(1)),
                        new WaitCommand(2),
                        new InstantCommand(() -> Robot.loader.set(1)),
                        new WaitCommand(2),
                        new InstantCommand(() -> Robot.loader.stopMotor()),
                        new InstantCommand(() -> Robot.shooter.stop()));
                        //new InstantCommand(() -> yeTrue)));

        public Boolean shoot() {
                shootSingle.schedule();
                return yeTrue;
        }
}
