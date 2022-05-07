package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.AnalogInput;

public class Robot extends TimedRobot {

  YukikazeDriveTrain drive;
  YukikazeCargoSystem cargo;
  YukikazeClimber climber;
  XboxController controller;

  Thread Distance;
  AnalogInput distanceSensor = new AnalogInput(0);

  @Override
  public void robotInit() {

    drive = new YukikazeDriveTrain();
    cargo = new YukikazeCargoSystem();
    climber = new YukikazeClimber();
    controller = new XboxController(0);

    Distance = new Thread(
        () -> {
          while (!Thread.interrupted()) {
            cargo.setCargoCentimeter();
            SmartDashboard.putNumber("CargoCentimeter", cargo.distance);
          }
        });
    Distance.start();

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {

    // driveTrain
    drive.setDriveInput();
    drive.arcadedrive(drive.power, drive.rotation);

    // intake & shooter
    if (controller.getStartButtonPressed()) {
      cargo.switchIntakeMode();
    }

    if (cargo.intakeMode == true) {

      // ボールがロボット内にない場合
      if (cargo.distanceTojudgeCargo <= cargo.distance && cargo.isIntakeReveresd == true) {
        cargo.keepIntaking();//intakeし続ける
      }

      // ボールがロボット内にある場合
      if (cargo.distance < cargo.distanceTojudgeCargo && cargo.isIntakeReveresd == true) {
        cargo.prepareToReverseCargo(); // 変数の切り替え
      }
      if (cargo.isCameInCargo == true) {
        cargo.reverseCargo();// 距離センサーに反応するまでCargoを逆流させる
      }

      if (cargo.isIntakeReveresd == false && cargo.isCameInCargo == false) {
        cargo.manualShoot();// shooterの手動操作
      } 
    } else {
      cargo.stopMotors();
    }

    // climbing
    if (controller.getXButton()) {
      climber.angleBcak();
    } else if (controller.getYButton()) {
      climber.goUp();
    } else if (controller.getAButton()) {
      climber.goDown();
    } else if (controller.getBButton()) {
      climber.angleForward();
    } else {
      climber.stopMotors();
    }

  }

  @Override
  public void disabledInit() {
    climber.stopMotors();
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }
}