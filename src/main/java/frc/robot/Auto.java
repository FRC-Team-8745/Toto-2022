package frc.robot;



public class Auto {

    private AutoCommands auto;
    private int step;

    public Auto(AutoCommands aauto) {

    auto = aauto;
    }
        public void AutoDrive(){
        // make the robot drive backward to the tarmac
        // TODO: fix guesstimate

        switch (step) {
            case 0:
                step += this.auto.driveFeet(-4, 0.5, true);
                break;
            case 1:
                step += this.auto.autoShoot(5, 1, true);
                break;
            case 2:
                step += this.auto.turnDegrees(45, 1, true);
                break;
            case 3:
                step += this.auto.driveFeet(4, 1, true);
                this.auto.autoIntake(4, 0.5, true);
                break;
            case 4:

        }
    }
}



