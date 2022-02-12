package frc.robot;

public class Test {
    public static void test() {
        if (Robot.cont.getRawButton(1))
            Robot.left.set(-0.5);
        else if (Robot.cont.getRawButton(2))
            Robot.left.set(-1);
        else
            Robot.left.stop();
    }
}
