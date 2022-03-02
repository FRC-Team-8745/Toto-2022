package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.math.controller.PIDController;

public class AimTurret {
    private AHRS disp_x;
    private AHRS disp_z;
    public AimTurret(AHRS dispX, AHRS dispZ) {
    disp_x = dispX;
    disp_z = dispZ;
}
    AHRS hubCenter = disp_z += 3;
    AHRS startPositionX = (disp_x = 0);
    AHRS startPositionY = (disp_z = 0);
}
