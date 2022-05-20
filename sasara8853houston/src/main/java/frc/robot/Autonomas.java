package frc.robot;

public class YukikazeAutonomas {

    private int firstLittleBack = 370;
    private int startBack = 0;
    private int startShooter = 0;
    private int startPulley = 0;

    YukikazeDrivetrain drive;
    YukikazeCargoSystem cargo;

    public YukikazeAutonomas() {

        drive = new YukikazeDrivetrain();
        cargo = new YukikazeCargoSystem();

    }

    public void justDoIt() {
        if (startBack != 0) {
            startBack -= 1;
            drive.LFmotor.set(-0.4);
            drive.LBmotor.set(-0.4);
            drive.RFmotor.set(0.4);
            drive.RBmotor.set(0.4);
            cargo.shooter.set(0);
            cargo.intake.set(0);
        }

        if (startPulley == 1) {
            startBack = 40;
        }

        if (startPulley != 0) {
            startPulley -= 1;
            cargo.intake.set(-0.7);// -0.7
            cargo.shooter.set(-0.8);// -0.8
            drive.LFmotor.set(0);
            drive.LBmotor.set(0);
            drive.RFmotor.set(0);
            drive.RBmotor.set(0);
        }

        if (startShooter == 1) {
            startPulley = 50;// 50
        }

        if (startShooter != 0) {
            startShooter -= 1;
            cargo.shooter.set(-0.7);// -0.7
            cargo.intake.set(0);
            drive.LFmotor.set(0);
            drive.LBmotor.set(0);
            drive.RFmotor.set(0);
            drive.RBmotor.set(0);
        }

        if (firstLittleBack == 1) {
            startShooter = 100;
        }

        if (firstLittleBack != 0) {
            firstLittleBack -= 1;
            drive.LFmotor.set(0);// -0.4
            drive.LBmotor.set(0);// -0.4
            drive.RFmotor.set(0);// 0.4
            drive.RBmotor.set(0);// 0.4
            cargo.shooter.set(0);
            cargo.intake.set(0);
        }

        if (startBack == 0 && startPulley == 0 && startShooter == 0 && firstLittleBack == 0) {
            drive.LFmotor.set(0);
            drive.LBmotor.set(0);
            drive.RFmotor.set(0);
            drive.RBmotor.set(0);
            cargo.shooter.set(0);
            cargo.intake.set(0);
        }
    }

}
