package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class YukikazeDriveTrain {


    public CANSparkMax LFmotor;
    public CANSparkMax RFmotor;
    public CANSparkMax LBmotor;
    public CANSparkMax RBmotor;
    public XboxController controller;
    public DifferentialDrive driveTrain;


    public double power;
    public double rotation;

    public final int driveLeftFrontCANid = 1;
    public final int driveRightFrontCANid = 2;
    public final int driveLeftBackCANid = 3;
    public final int driveRightBackCANid = 4;


    public YukikazeDriveTrain() {
        LFmotor = new CANSparkMax(driveLeftFrontCANid,  MotorType.kBrushless);
        RFmotor = new CANSparkMax(driveRightFrontCANid, MotorType.kBrushless);
        LBmotor = new CANSparkMax(driveLeftBackCANid,   MotorType.kBrushless);
        RBmotor = new CANSparkMax(driveRightBackCANid,  MotorType.kBrushless);

        LFmotor.restoreFactoryDefaults();
        LBmotor.restoreFactoryDefaults();
        RFmotor.restoreFactoryDefaults();
        RBmotor.restoreFactoryDefaults();

        LBmotor.follow(LFmotor);
        RBmotor.follow(RFmotor);

        RFmotor.setInverted(true);
        RBmotor.setInverted(true);
    }

    public void setDriveInput() {
        power = - controller.getLeftTriggerAxis() + controller.getRightTriggerAxis();
        rotation = controller.getRightX();
    }

    public void arcadedrive(double power, double rotation) {
        this.power    = power;
        this.rotation = rotation;
        driveTrain.arcadeDrive(power, rotation);
    }
}