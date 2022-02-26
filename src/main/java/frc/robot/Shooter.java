package frc.robot;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Shooter {
    private BrushlessNEO shooter;
    public Shooter(BrushlessNEO shooter_) {
        shooter = shooter_;
    }

    public void shoot() {
        SequentialCommandGroup shooter = new SequentialCommandGroup(
            new InstantCommand(() -> Robot.shooter.set(1)),
            new WaitCommand(2),
            new InstantCommand(() -> Robot.loader.set(1)),
            new WaitCommand(5),
            new InstantCommand(() -> Robot.loader.stopMotor()),
            new InstantCommand(() -> Robot.shooter.stop()));

        shooter.schedule();
    }

    public int returnValue() {
        return 1;
    }

}
