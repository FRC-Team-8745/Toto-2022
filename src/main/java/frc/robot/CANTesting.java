package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;

public class CANTesting {

    static CANSparkMax Motor1 = new CANSparkMax(1, MotorType.kBrushless);
    static RelativeEncoder Encoder1 = Motor1.getEncoder();
    
    static CANSparkMax Motor2 = new CANSparkMax(2, MotorType.kBrushless);
    static RelativeEncoder Encoder2 = Motor2.getEncoder();

    static CANSparkMax Motor3 = new CANSparkMax(3, MotorType.kBrushless);
    static RelativeEncoder Encoder3 = Motor3.getEncoder();

    static CANSparkMax Motor4 = new CANSparkMax(4, MotorType.kBrushless);
    static RelativeEncoder Encoder4 = Motor4.getEncoder();

    static SparkMaxPIDController pid = Motor1.getPIDController();

    public static void initCAN() {
        Encoder1.setPosition(0);
        Encoder2.setPosition(0);
        Encoder3.setPosition(0);
        Encoder4.setPosition(0);
        Motor1.setIdleMode(IdleMode.kBrake);
        Motor2.setIdleMode(IdleMode.kBrake);
        Motor3.setIdleMode(IdleMode.kBrake);
        Motor4.setIdleMode(IdleMode.kBrake);
    }

    public static void testCAN() {
        if (Encoder1.getPosition() <= 18) {
            Motor1.set(0.1);
        } else {
            Motor1.stopMotor();
        }


        /*
        Motor1.set(0.2);
        Motor2.set(0.2);
        Motor3.set(0.2);
        Motor4.set(0.2);
        */
    }

    public static void shuffleboard() {
        SmartDashboard.putNumber("temprature one", Motor1.getMotorTemperature() * (9/5) + 32);
        SmartDashboard.putNumber("temprature two", Motor2.getMotorTemperature() * (9/5) + 32);
        SmartDashboard.putNumber("temprature three", Motor3.getMotorTemperature() * (9/5) + 32);
        SmartDashboard.putNumber("temprature four", Motor4.getMotorTemperature() * (9/5) + 32);

        SmartDashboard.putNumber("motor one", Encoder1.getPosition());
        SmartDashboard.putNumber("motor two", Encoder2.getPosition());
        SmartDashboard.putNumber("motor three", Encoder3.getPosition());
        SmartDashboard.putNumber("motor four", Encoder4.getPosition()); 
    }
}
