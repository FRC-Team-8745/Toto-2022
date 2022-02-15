package frc.robot.BaseRobot;

//Controllers
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

//Motor Control
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;

//Base Robot
import edu.wpi.first.wpilibj.PowerDistribution;

//Other
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class RobotBaseX {
    // Controllers
    public static XboxController xbox = new XboxController(1);
    public static Joystick main = new Joystick(0);

    // Motor Control
    public static CANSparkMax rightDrive = new CANSparkMax(1, MotorType.kBrushless);
    public static CANSparkMax leftDrive = new CANSparkMax(2, MotorType.kBrushless);
    
    // shooter Control
    public static CANSparkMax loader = new CANSparkMax(3, MotorType.kBrushless);
    public static CANSparkMax shooter = new CANSparkMax(4, MotorType.kBrushless);
    
    // Shooter encoders
    public static RelativeEncoder shooterEncoder = shooter.getEncoder();
    public static RelativeEncoder loaderEncoder = loader.getEncoder();

    // Sensors
    public static RelativeEncoder rightEncoder = rightDrive.getEncoder();
    public static RelativeEncoder leftEncoder = leftDrive.getEncoder();

    // Base robot
    public static PowerDistribution PDP = new PowerDistribution();

    // PID
    public static PIDController pid = new PIDController(0.5, 0.01, 4);


    // Shuffleboard
    public static void shuffleboard() {
        //driving encoders/temps
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getPosition());
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getPosition());
        SmartDashboard.putNumber("Right Motor Temprature", rightDrive.getMotorTemperature());
        SmartDashboard.putNumber("Left Motor Temprature", leftDrive.getMotorTemperature());
        //shooting encoders/temps
        SmartDashboard.putNumber("Loader Encoder", loaderEncoder.getPosition());
        SmartDashboard.putNumber("Shooter Encoder", shooterEncoder.getPosition());
        SmartDashboard.putNumber("Loader Motor Temprature", loader.getMotorTemperature());
        SmartDashboard.putNumber("Shooter Motor Temprature", shooter.getMotorTemperature());
    }

    public static void initStuff() {
        rightDrive.restoreFactoryDefaults();
        leftDrive.restoreFactoryDefaults();
        leftDrive.setInverted(true);
    }
}
