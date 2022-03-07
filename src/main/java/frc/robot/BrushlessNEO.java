package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class BrushlessNEO {
    private CANSparkMax motor;
    private RelativeEncoder encoder;

    public BrushlessNEO(int CANid, boolean inverted) {
        motor = new CANSparkMax(CANid, MotorType.kBrushless);
        encoder = motor.getEncoder();
        motor.setInverted(inverted);
    }

    // Set the motor speed
    public void set(double speed) {
        if (speed <= 1 && speed >= -1)
            this.motor.set(speed);
    }

    // Reset the encoder position to zero
    public void resetPosition() {
        this.encoder.setPosition(0);
    }

    // Get the encoder position
    public double getPosition() {
        return this.encoder.getPosition();
    }

    // Get the current RPM of the motor
    public double getRPM() {
        return this.encoder.getVelocity();
    }

    // Get the current temprature of the motor
    public double getTemp() {
        return (this.motor.getMotorTemperature() * (9 / 5)) + 32;
    }

    // Get the current speed of the motor
    public double getSpeed() {
        return this.motor.get();
    }

    // Stop the motor
    public void stop() {
        this.motor.stopMotor();
    }

    // Set the idle mode of the this.motor
    public void idleMode(IdleMode mode) {
        this.motor.setIdleMode(mode);
    }


    // Get motor CAN id
    public int getCAN() {
        return this.motor.getDeviceId();
    }

    // Set ramp rate of shooter
    public void setRamp(double ramp) {
        this.motor.setOpenLoopRampRate(ramp);
    }

	public void setVoltage(double volts) {
		this.motor.setVoltage(volts);
	}
}