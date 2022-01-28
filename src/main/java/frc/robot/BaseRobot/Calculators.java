package frc.robot.BaseRobot;

public class Calculators {
    
    // Robot features
    private static final double wheelBase = 15.5;
    private static final double wheelDiameter = 6;

    // Calculators
    private static double wheelCircumference = wheelDiameter * Math.PI;

    public static double inchesToRotations(double inches) {
        return inches / wheelCircumference;
    }
}
