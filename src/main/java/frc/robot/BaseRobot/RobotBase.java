package frc.robot.BaseRobot;

//Controllers
import edu.wpi.first.wpilibj.XboxController;
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
import edu.wpi.first.wpilibj.PneumaticsControlModule;

//Other
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class RobotBase {
    // Controllers
    public static XboxController xbox = new XboxController(0);
    public static Joystick main = new Joystick(1);

    // Pneumatic System

    // Motor Control
    public static CANSparkMax rightDrive = new CANSparkMax(1, MotorType.kBrushless);
    public static CANSparkMax leftDrive = new CANSparkMax(2, MotorType.kBrushless);
    public static CANSparkMax rotate = new CANSparkMax(3,MotorType.kBrushless);

    // Sensors
    public static RelativeEncoder rightEncoder = rightDrive.getEncoder();
    public static RelativeEncoder leftEncoder = leftDrive.getEncoder();
    public static RelativeEncoder rotateEncoder = rotate.getEncoder();
    public static ADXRS450_Gyro Gyro = new ADXRS450_Gyro();

    // Base robot
    public static PowerDistribution PDP = new PowerDistribution();
    public static PneumaticsControlModule PCM = new PneumaticsControlModule();


    // Shuffleboard
    public static void shuffleboard() {
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getPosition());
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getPosition());
        SmartDashboard.putNumber("Climber Encoder", rotateEncoder.getPosition());
        SmartDashboard.putNumber("Right Motor Temprature", rightDrive.getMotorTemperature());
        SmartDashboard.putNumber("Left Motor Temprature", leftDrive.getMotorTemperature());
        SmartDashboard.putNumber("Climber Rotate Temprature", rotate.getMotorTemperature());
    }
}