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

import edu.wpi.first.math.controller.PIDController;
//Sensors
//import edu.wpi.first.wpilibj.DigitalInput;

//Base Robot
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PneumaticsControlModule;

//Other
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class RobotBase {
    // Controllers
    public static XboxController xbox = new XboxController(1);
    public static Joystick main = new Joystick(0);

    // Pneumatic System

    // Motor Control
    public static CANSparkMax rightDrive = new CANSparkMax(1, MotorType.kBrushless);
    public static CANSparkMax leftDrive = new CANSparkMax(2, MotorType.kBrushless);

    // Sensors
    public static RelativeEncoder rightEncoder = rightDrive.getEncoder();
    public static RelativeEncoder leftEncoder = leftDrive.getEncoder();

    // Base robot
    public static PowerDistribution PDP = new PowerDistribution();
    public static PneumaticsControlModule PCM = new PneumaticsControlModule();

    // PID
    public static PIDController pid = new PIDController(0.5, 0.01, 4);


    // Shuffleboard
    public static void shuffleboard() {
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getPosition());
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getPosition());
        SmartDashboard.putNumber("Right Motor Temprature", rightDrive.getMotorTemperature());
        SmartDashboard.putNumber("Left Motor Temprature", leftDrive.getMotorTemperature());
    }

    public static void initSparks() {
        rightDrive.restoreFactoryDefaults();
        leftDrive.restoreFactoryDefaults();
    }
}