package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class YukikazeClimber {


    private CANSparkMax climbUpDown;
    private CANSparkMax climbLeftAngle;
    private CANSparkMax climbRightAngle;


    private final int climbUpDownCANid = 7;
    private final int climbLeftAngleCANid = 8;
    private final int climbRightAngleCANid = 9;
    private final double climbAngleOutput = 0.05;
    private final double climbUpDownOutput = 1;


    public YukikazeClimber() {
        climbUpDown = new CANSparkMax(climbUpDownCANid, MotorType.kBrushless);
        climbLeftAngle = new CANSparkMax(climbLeftAngleCANid, MotorType.kBrushless);
        climbRightAngle = new CANSparkMax(climbRightAngleCANid, MotorType.kBrushless);

        climbRightAngle.setInverted(true);
    }

    public void goUp() {
        climbUpDown.set(climbUpDownOutput);
    }

    public void goDown() {
        climbUpDown.set(-climbUpDownOutput);
    }

    public void angleForward() {
        climbLeftAngle.set(climbAngleOutput);
        climbRightAngle.set(climbAngleOutput);
    }
    
    public void angleBcak() {
        climbLeftAngle.set(-climbAngleOutput);
        climbRightAngle.set(-climbAngleOutput);
    }

    public void stopMotors() {
        climbUpDown.set(0);
        climbLeftAngle.set(0);
        climbRightAngle.set(0);
    }
    
}
