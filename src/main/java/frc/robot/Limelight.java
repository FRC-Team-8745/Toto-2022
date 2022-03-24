package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.net.PortForwarder;

public class Limelight extends SubsystemBase {

	/*
	 * Left of target = positive
	 * Right of target = negative
	 */

	/*
	 * EXPOSURE:
	 * Used to black out most incoming data. For filtering out anything that
	 * doesn't reflect or emit light.
	 * 
	 * BLACK LEVEL OFFSET:
	 * Used to darken the camera input. For removing additional light sources such
	 * as arena lights.
	 * 
	 * THRESHOLDING:
	 * Used to filter out any pixels that are not the correct color. For filtering
	 * out the red and blue arena lights to only view the green reflection on the
	 * tape.
	 * 
	 * HUE:
	 * A pure color range
	 * 
	 * SATURATION:
	 * The white level of the color
	 * 
	 * VALUE:
	 * The black level of the color
	 * 
	 * EROSION:
	 * Removes some results, useful for multiple targets
	 * 
	 * DIALATION:
	 * Patches holes in a target
	 * 
	 * TARGET AREA:
	 * Used to filter out detected targets such as a large display screen
	 * 
	 * TARGET FULLNESS:
	 * A solid rectangle target will have a near-1.0 fullness, while a U-shaped
	 * target will have a low fullness.
	 * 
	 * ASPECT RATIO:
	 * A low aspect ratio describes a “tall” rectangle, while a high aspect ratio
	 * describes a “wide” rectangle.
	 * 
	 * DIRECTION FILTER:
	 * Removes targets at unwanted orientations
	 * 
	 * SPECKLE DETECTION:
	 * Rejects relatively small contours that have passed through all other filters.
	 * This is essential if a target must remain trackable from short-range and
	 * long-range.
	 * 
	 * TARGET GROUPING:
	 * Detects groups of targets, like the multiple sections of tape on the high hub
	 * 
	 * TARGETING REGION:
	 * Determines where to place the crosshair on the target
	 * 
	 * 
	 */
	NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
	NetworkTableEntry tx = limelightTable.getEntry("tx");
	NetworkTableEntry ty = limelightTable.getEntry("ty");
	NetworkTableEntry ta = limelightTable.getEntry("ta");
	NetworkTableEntry tv = limelightTable.getEntry("tv");
	NetworkTableEntry camMode = limelightTable.getEntry("camMode");
	NetworkTableEntry pipeline = limelightTable.getEntry("pipeline");

	public Limelight() {
		PortForwarder.add(5800, "limelight.local", 5800);
		PortForwarder.add(5801, "limelight.local", 5801);
		PortForwarder.add(5802, "limelight.local", 5802);
		PortForwarder.add(5803, "limelight.local", 5803);
		PortForwarder.add(5804, "limelight.local", 5804);
		PortForwarder.add(5805, "limelight.local", 5805);
	}

	// LED modes of the limelight
	public static enum LEDMode {
		kOn, kOff, kBlink, kDefault;
	}

	// Different pipelines
	public static enum visionPipeline {
		kTesting, kDefault, kWorlds;
	}

	@Override
	public void periodic() {
		// read values periodically
		double x = tx.getDouble(0.0);
		double y = ty.getDouble(0.0);
		double area = ta.getDouble(0.0);

		// post to smart dashboard periodically
		SmartDashboard.putNumber("LimelightX", x);
		SmartDashboard.putNumber("LimelightY", y);
		SmartDashboard.putNumber("LimelightArea", area);
	}

	public double getTx() {
		return tx.getDouble(0);
	}

	public double getTy() {
		return ty.getDouble(0);
	}

	public double getTa() {
		return ta.getDouble(0);
	}

	public double getTv() {
		return tv.getDouble(0);
	}

	public double getPipeline(){
		return pipeline.getDouble(0);
	}

	// Return true if the limelight has a vision target
	public boolean hasTarget() {
		if (tv.getDouble(0) == 1)
			return true;
		return false;
	}

	// Disable the vision processing on the limelight
	public void disableProcessing() {
		camMode.setNumber(1);
	}

	// Enable the vision processing on the limelight
	public void enableProcessing() {
		camMode.setNumber(0);
	}

	// Sets the LED mode of the limelight
	public void setLEDMode(LEDMode mode) {
		switch (mode) {
			case kOn:
				camMode.setNumber(3);
				break;

			case kOff:
				camMode.setNumber(1);
				break;

			case kBlink:
				camMode.setNumber(2);
				break;

			case kDefault:
				camMode.setNumber(0);
				break;
		}
	}

	// Sets the vision pipeline to usw on the limelight
	public void setPipeline(visionPipeline pipeline) {
		switch (pipeline) {
			case kDefault:
			this.pipeline.setNumber(0);
				break;

			case kTesting:
				this.pipeline.setNumber(1);
				break;

			case kWorlds:
				this.pipeline.setNumber(2);
				break;
		}
	}
}
