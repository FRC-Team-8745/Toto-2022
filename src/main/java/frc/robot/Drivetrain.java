package frc.robot;

public class Drivetrain {
	public void setDrive(double speed) {
		Robot.right.set(speed);
		Robot.left.set(speed);
	}

	public void stopDrive() {
		Robot.right.stop();
		Robot.left.stop();
	}

	public void resetEncoders() {
		Robot.right.resetPosition();
		Robot.left.resetPosition();
	}
}