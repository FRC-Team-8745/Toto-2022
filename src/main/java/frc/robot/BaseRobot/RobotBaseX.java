package frc.robot.BaseRobot;

//Controllers
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;


//Pneumatic System
/*
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
*/

//Motor Control
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

//Sensors
//import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

//Base Robot
import edu.wpi.first.wpilibj.PowerDistribution;

//Other
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class RobotBaseX {
    // Controllers
    public static XboxController xbox = new XboxController(0);
    public static Joystick main = new Joystick(1);

    // Pneumatic System

    // Motor Control
    public static CANSparkMax rightDrive = new CANSparkMax(1, MotorType.kBrushless);
    public static CANSparkMax leftDrive = new CANSparkMax(2, MotorType.kBrushless);
    public static CANSparkMax shooter = new CANSparkMax(3,MotorType.kBrushless);
    public static CANSparkMax intake = new CANSparkMax(4,MotorType.kBrushless);
    public static Spark cLifter = new Spark(1);
    // Sensors
    public static RelativeEncoder rightEncoder = rightDrive.getEncoder();
    public static RelativeEncoder leftEncoder = leftDrive.getEncoder();
    public static RelativeEncoder shooterEncoder = shooter.getEncoder();
    public static RelativeEncoder intakeEncoder = intake.getEncoder();
    public static ADXRS450_Gyro Gyro = new ADXRS450_Gyro();

    // Base robot
    public static PowerDistribution PDP = new PowerDistribution();

    // Shuffleboard
    public static void shuffleboard() {
        SmartDashboard.putNumber("Right Rotation", rightEncoder.getPosition());
        SmartDashboard.putNumber("Left Rotation", leftEncoder.getPosition());
        SmartDashboard.putNumber("Shooter Rotation", shooterEncoder.getPosition());
        SmartDashboard.putNumber("Right Motor Temprature", rightDrive.getMotorTemperature());
        SmartDashboard.putNumber("Left Motor Temprature", leftDrive.getMotorTemperature());
        SmartDashboard.putNumber("Climber Rotate Temprature", shooter.getMotorTemperature());
    }

    public static void initSparks() {
        rightDrive.restoreFactoryDefaults();
        leftDrive.restoreFactoryDefaults();
        leftDrive.setInverted(true);
    }
}