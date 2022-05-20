package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.AnalogInput;

public class Robot extends TimedRobot {

  Drivetrain drive;
  CargoSystem cargo;
  Climber climber;
  Autonomas autonomas;
  XboxController controller;
  double power;
  double rotation;

  Thread Distance;
  AnalogInput distanceSensor = new AnalogInput(0);

  @Override
  public void robotInit() {

    drive = new Drivetrain();
    cargo = new CargoSystem();
    climber = new Climber();
    autonomas = new Autonomas();
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

    if (autonomas.firstLittleBack != 0) {
      autonomas.firstLittleBack -= 1;
      autonomas.driveBack();
      autonomas.stopCargoMotors();
    }

    if (autonomas.firstLittleBack == 1) {
      autonomas.prepareShooter = 100;
    }

    if (autonomas.prepareShooter != 0) {
      autonomas.prepareShooter -= 1;
      cargo.shooter.set(autonomas.shootingOutput);
      cargo.intake.set(0);
      autonomas.stopDriving();
    }

    if (autonomas.prepareShooter == 1) {
      autonomas.startShooting = 50;
    }

    if (autonomas.startShooting != 0) {
      autonomas.startShooting -= 1;
      cargo.intake.set(autonomas.shootingOutput);
      cargo.shooter.set(autonomas.shootingOutput);
      autonomas.stopDriving();
    }

    if (autonomas.startBack != 0) {
      autonomas.startBack -= 1;
      autonomas.driveBack();
      autonomas.stopCargoMotors();
    }

    if (autonomas.startShooting == 1) {
      autonomas.startBack = 40;
    }

    if (autonomas.startBack == 0 && autonomas.startShooting == 0 
                                 && autonomas.prepareShooter == 0 && autonomas.firstLittleBack == 0) {
      autonomas.stopDriving();
      autonomas.stopCargoMotors();
    }

  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {

    // driveTrain
    power    = -controller.getLeftTriggerAxis() + controller.getRightTriggerAxis();
    rotation = controller.getRightX();

    drive.drivetrain.arcadeDrive(power, rotation);

    // intake & shooter
    if (controller.getStartButtonPressed()) {
      cargo.switchIntakeMode();
    }

    if (cargo.intakeMode == true) {

      // ボールがロボット内にない場合
      if (cargo.distanceTojudgeCargo <= cargo.distance && cargo.isIntakeReveresd == true) {
        cargo.keepIntaking();// intakeし続ける
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