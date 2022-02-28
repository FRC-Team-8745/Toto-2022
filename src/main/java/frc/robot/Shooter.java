package frc.robot;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.*;

public class Shooter {

        public BrushlessNEO shooter = new BrushlessNEO(3, true);
        public Spark loader = new Spark(0);

        // Unload a single ball from the loader & shooter
        SequentialCommandGroup unloadSingle = new SequentialCommandGroup(
                        new InstantCommand(() -> this.loader.set(-1)),
                        new InstantCommand(() -> this.shooter.set(-0.2)),
                        new WaitCommand(2),
                        new InstantCommand(() -> this.loader.stopMotor()),
                        new InstantCommand(() -> this.shooter.stop()));

        // Load a single ball to a point directly before the shooter
        SequentialCommandGroup loadSingle = new SequentialCommandGroup(
                        new InstantCommand(() -> this.loader.set(1)),
                        new WaitCommand(1.7),
                        new InstantCommand(() -> this.loader.stopMotor()));

        // Shoot the ball at a lower speed (80%)
        SequentialCommandGroup shooterSlow = new SequentialCommandGroup(
                        new InstantCommand(() -> this.shooter.set(Robot.sliderSpeed)),
                        new WaitCommand(2),
                        new InstantCommand(() -> this.loader.set(1)),
                        new WaitCommand(2),
                        new InstantCommand(() -> this.loader.stopMotor()),
                        new InstantCommand(() -> this.shooter.stop()));

        // Shoot the ball at full speed
        SequentialCommandGroup shootSingle = new SequentialCommandGroup(
                        new InstantCommand(() -> this.shooter.set(1)),
                        new WaitCommand(2),
                        new InstantCommand(() -> this.loader.set(1)),
                        new WaitCommand(2),
                        new InstantCommand(() -> this.loader.stopMotor()),
                        new InstantCommand(() -> this.shooter.stop()));
}
