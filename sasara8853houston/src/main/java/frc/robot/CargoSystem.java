package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;

import edu.wpi.first.wpilibj.XboxController;

public class YukikazeCargoSystem {

    public CANSparkMax intake;
    public CANSparkMax shooter;
    private AnalogInput distanceSensor;
    private XboxController controller;

    private double distanceSensorValue;
    private double distanceSensorValue4;
    private double distanceSensorValue4pow;
    public double distance;

    private final int intakeCANid = 5;
    private final int shooterCANid = 6;

    public boolean intakeMode = true;
    public boolean isIntakeReveresd = true;
    public boolean isCameInCargo = false;
    private boolean isShooterOn = false;
    private boolean isIntakeOn = false;
    public int overIntakeCount = 0;
    public double distanceTojudgeCargo = 23.0;


    public YukikazeCargoSystem() {
        intake = new CANSparkMax(intakeCANid, MotorType.kBrushless);
        shooter = new CANSparkMax(shooterCANid, MotorType.kBrushless);
        controller = new XboxController(0);
        distanceSensor = new AnalogInput(0);
    }

    public void setCargoCentimeter() {
        distanceSensorValue = distanceSensor.getAverageValue();
        distanceSensorValue4 = distanceSensorValue / 4;
        distanceSensorValue4pow = Math.pow(distanceSensorValue4, -1.136);
        distance = 25391 * distanceSensorValue4pow;
    }

    public void switchIntakeMode() {
        intakeMode = !intakeMode;
    }

    public void shootCargo() {

    }

    public void stopMotors() {
        intake.set(0);
        shooter.set(0);
    }

    public void keepIntaking() {
        intake.set(-0.2);
        if (controller.getBButton()) {
          shooter.set(-1);
        } else {
          shooter.set(0);
        }
    }

    public void reverseCargo() {
        isIntakeReveresd = false; // ボールがあるかないかの判断を中止する
        if (overIntakeCount != 0) {
          overIntakeCount -= 1;
          intake.set(-0.2);
        }

        if (overIntakeCount == 0) {
          // ボールをセンサーの前まで戻す（戻すときの目標値はここで設定）
          if (distanceTojudgeCargo < distance) {
            intake.set(0.08);
          }
          if (distance <= distanceTojudgeCargo) {
            isCameInCargo = false; // 「ボールを指定の位置まで戻す」を中止する
          }
        }
    }

    public void prepareToReverseCargo() {
        isCameInCargo = true; // ボールを指定の位置まで戻す
        overIntakeCount = 50;
    }

    public void manualShoot() {
              // ボタンでshoot作業を行う
      if (controller.getXButtonPressed()) {
        isIntakeOn = !isIntakeOn;
      }
      if (isIntakeOn == true) {
        intake.set(-1);
      } else if (controller.getLeftStickButton()) {
        intake.set(0.3);
      } else {
        intake.set(0);
      }
      if (controller.getBButtonPressed()) {
        isShooterOn = !isShooterOn;
      }

      if (isShooterOn == true) {
        shooter.set(-1);
      } else {
        shooter.set(0);
      }
      if (controller.getRightBumperReleased()) {
        isIntakeReveresd = true; // 「ボールがあるかないかの判断」をON
      }
    }
}