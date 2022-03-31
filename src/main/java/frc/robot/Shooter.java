package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;

public class Shooter extends SubsystemBase {
	// Shooter speed (in RPM)
	private final double kFenderSpeed = 3000;
	private final double kbatteryMax = 13;
	private final double kmaxRPM = 5500;

	public static CANSparkMax shooter = new CANSparkMax(3, MotorType.kBrushless);
	public static RelativeEncoder encoder = shooter.getEncoder();
	public static SparkMaxPIDController rpmPID = shooter.getPIDController();

	public static double targetRPM = 0;

	public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

	@Override
	public void periodic() {
		// Shooter status
		SmartDashboard.putNumber("Shooter RPM", encoder.getVelocity());
		SmartDashboard.putBoolean("Shooter Ready", (encoder.getVelocity() > 3000));

		//rpmPID.setReference(targetRPM, CANSparkMax.ControlType.kVelocity);
	}

	public Shooter() {
		// Set shooter ramp rate
		//shooter.setOpenLoopRampRate(0.5);

		kP = 0.00033;
		kI = 0;
		kD = 0;
		kIz = 0;
		kFF = 0;
		kMaxOutput = 1;
		kMinOutput = -1;

		rpmPID.setP(kP);
		rpmPID.setI(kI);
		rpmPID.setD(kD);
		rpmPID.setIZone(kIz);
		rpmPID.setFF(kFF);
		rpmPID.setOutputRange(kMinOutput, kMaxOutput);
		//0.5
	}

	public void setRPM(double RPM) {
		targetRPM = RPM;
	}

	// Stop the shooter
	public void stop() {
		shooter.set(0);
	}

	// Sets the RPM of the shooter motor
	/*
	public void setRPM(double rpm) {
		shooter.setVoltage((rpm / kmaxRPM) * kbatteryMax);
	}
	*/

	// Unload a single ball from the loader & shooter
	SequentialCommandGroup unloadSingle = new SequentialCommandGroup(
			new InstantCommand(() -> Robot.loader.set(-1)),
			new InstantCommand(() -> shooter.set(-0.2)),
			new WaitCommand(2),
			new InstantCommand(() -> Robot.loader.stopMotor()),
			new InstantCommand(() -> shooter.set(0)));

	// Load a single ball to a point directly before the shooter
	SequentialCommandGroup loadSingle = new SequentialCommandGroup(
			new InstantCommand(() -> Robot.loader.set(1)),
			new WaitCommand(1.7),
			new InstantCommand(() -> Robot.loader.stopMotor()));

	// Shoot the ball at full speed
	SequentialCommandGroup shootSingle = new SequentialCommandGroup(
			new InstantCommand(() -> setRPM(kFenderSpeed)),
			new WaitCommand(1.5),
			new InstantCommand(() -> Robot.loader.set(1)),
			new WaitCommand(1),
			new InstantCommand(() -> Robot.loader.stopMotor()),
			new InstantCommand(() -> shooter.set(0)));

	// Shoot two balls at full speed
	SequentialCommandGroup shootDouble = new SequentialCommandGroup(
			new InstantCommand(() -> setRPM(kFenderSpeed)),
			new WaitCommand(1),
			new InstantCommand(() -> Robot.loader.set(1)),
			new WaitCommand(3),
			new InstantCommand(() -> Robot.loader.stopMotor()),
			new InstantCommand(() -> shooter.set(0)));

	// Shoot and load a single ball
	SequentialCommandGroup shootFull = new SequentialCommandGroup(
			new InstantCommand(() -> setRPM(kFenderSpeed)),
			new InstantCommand(() -> Robot.loader.set(1)),
			new WaitCommand(4),
			new InstantCommand(() -> shooter.set(0)),
			new InstantCommand(() -> Robot.loader.stopMotor()));
}