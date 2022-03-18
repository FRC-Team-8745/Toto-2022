package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;

public class Robot extends TimedRobot {
	/*
	 * Motor CAN IDs:
	 * Right drive: 1
	 * Left drive: 2
	 * Shooter: 3
	 * Intake: 4
	 * Right Climber: 5
	 * Left Climber: 6
	 * Turret: 7
	 * 
	 * Motor PWM IDs:
	 * Loader: 0
	 * 
	 * Robot perimiter with bumpers is 33" x 39"
	 * Robot weight is 107 pounds
	 * Bumper weight is 12.6 pounds
	 */

	/*
	 * Button numbers:
	 * [1: Trigger] [2: Side Button] [3: Labeled] [4: Labeled] [5: Labeled]
	 * [6: Labeled] [7: Labeled] [8: Labeled] [9: Labeled] [10:Labeled]
	 * [11: Labeled] [12: Labeled]
	 */

	public static BrushlessNEO right = new BrushlessNEO(1, true);
	public static BrushlessNEO left = new BrushlessNEO(2, false);
	public static BrushlessNEO shooter = new BrushlessNEO(3, true);
	public static BrushlessNEO intake = new BrushlessNEO(4, false);
	public static BrushlessNEO climberRight = new BrushlessNEO(5, false);
	public static BrushlessNEO climberLeft = new BrushlessNEO(6, false);
	public static BrushlessNEO turret = new BrushlessNEO(7, false);
	public static Spark loader = new Spark(0);
	public static Joystick cont = new Joystick(0);
	public static XboxController xbox = new XboxController(1);
	public static ShootCommands shootCommands = new ShootCommands();
	public static Drivetrain driveTrain = new Drivetrain();
	public static Autonomous autonomous = new Autonomous();

	public static final double kDriveGearbox = 10.71;

	@Override
	public void robotInit() {
		// Reset encoders
		right.resetPosition();
		left.resetPosition();
		shooter.resetPosition();
		intake.resetPosition();
		climberRight.resetPosition();
		climberLeft.resetPosition();
		turret.resetPosition();
		// Set the Spark controller to inverted
		loader.setInverted(true);
		// Lock climber arms
		climberRight.idleMode(IdleMode.kBrake);
		climberLeft.idleMode(IdleMode.kBrake);
		SmartDashboard.putNumber("Auto", Autonomous.kDefaultAuto);

		turret.setRamp(0.5);
		turret.idleMode(IdleMode.kBrake);

		// Setup and put the front camera on the dashboard
		UsbCamera frontCamera = CameraServer.startAutomaticCapture();
		frontCamera.setFPS(30);
		frontCamera.setResolution(350, 350);
	}

	@Override
	public void robotPeriodic() {
		// Shooter status
		SmartDashboard.putNumber("Shooter RPM", shooter.getRPM());
		SmartDashboard.putBoolean("Shooter Ready", (shooter.getRPM() > 3000));

		// Temprature warnings
		SmartDashboard.putBoolean("Right Tempratue", (right.getTemp() < 150));
		SmartDashboard.putBoolean("Left Tempratue", (left.getTemp() < 150));

		// Runs the command scheduler while the robot is on
		CommandScheduler.getInstance().run();

		// Set motor ramp speeds
		shooter.setRamp(0.5);
		intake.setRamp(0.5);
	}

	@Override
	public void autonomousInit() {
		right.resetPosition();
		left.resetPosition();
		autonomous.auto();
		left.idleMode(IdleMode.kBrake);
		right.idleMode(IdleMode.kBrake);
		turret.idleMode(IdleMode.kBrake);
		climberLeft.idleMode(IdleMode.kBrake);
		climberRight.idleMode(IdleMode.kBrake);
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopInit() {
		shooter.stop();
		loader.stopMotor();
		left.idleMode(IdleMode.kCoast);
		right.idleMode(IdleMode.kCoast);
		turret.idleMode(IdleMode.kBrake);
		climberLeft.idleMode(IdleMode.kBrake);
		climberRight.idleMode(IdleMode.kBrake);
	}

	@Override
	public void teleopPeriodic() {
		Teleop.runTeleop();
	}

	@Override
	public void disabledInit() {
		left.idleMode(IdleMode.kCoast);
		right.idleMode(IdleMode.kCoast);
		turret.idleMode(IdleMode.kCoast);
		climberLeft.idleMode(IdleMode.kCoast);
		climberRight.idleMode(IdleMode.kCoast);
	}

	@Override
	public void disabledPeriodic() {
	}

	@Override
	public void testInit() {
	}

	@Override
	public void testPeriodic() {
	}
}