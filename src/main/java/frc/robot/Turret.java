package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import java.awt.Point;

import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase {

	// Declare the IMU
	private final AHRS IMU = new AHRS(Port.kUSB);

	// Base distance from hub
	public static final double kFeetToHubCenterX = 3;
	public static final double kFeetToHubCenterY = 1;

	// Coordinates from center of the hub for the robot in feet (x, y)
	public Point pos = new Point(0, 0);

	// Inital method to set base coordinates
	public void init() {
		pos.setLocation(kFeetToHubCenterX, kFeetToHubCenterY);
		IMU.calibrate();
		IMU.resetDisplacement();
		IMU.reset();
		
	}

	// Periodic update method
	@Override
	public void periodic() {
		updatePos();
		SmartDashboard.putString("Robot Position", " X: " + pos.getX() + " Y: " + pos.getY());
		SmartDashboard.putBoolean("IMU Connection", IMU.isConnected());
		printPos();
	}

	// Return the X movement value of the IMU in feet
	public double getPosX() {
		return IMU.getDisplacementX() * 3.2808;
	}

	// Return the Y movement value of the IMU in feet
	public double getPosY() {
		return IMU.getDisplacementY() * 3.2808;
	}

	// Update the X and Y values for the robot position
	public void updatePos() {
		pos.setLocation(getPosX(), getPosY());
	}

	// Print the position of the robot relative to the center of the hub in feet
	public void printPos() {
		System.out.println(" X: " + pos.getX() + " Y: " + pos.getY());
	}

}
