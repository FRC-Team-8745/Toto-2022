package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShuffleboardNumber {
	public String name;
	public double value;

	public ShuffleboardNumber(String name_) {
		name = name_;
		SmartDashboard.putNumber(name, 0);
	}

	public ShuffleboardNumber(String name_, double value_) {
		name = name_;
		value = value_;
		SmartDashboard.putNumber(name, value);
	}

	public void reset(double value) {
		SmartDashboard.putNumber(name, this.value);
	}

	public double get() {
		return SmartDashboard.getNumber(name, value);
	}

	public void reset() {
		SmartDashboard.putNumber(name, this.value);
	}
}
