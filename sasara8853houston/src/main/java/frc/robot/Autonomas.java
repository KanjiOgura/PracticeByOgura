package frc.robot;

public class Autonomas {

    public int firstLittleBack     = 370;
    public int startBack           = 0;
    public int prepareShooter      = 0;
    public int startShooting       = 0;
    public double shootingOutput   = -0.8;
    private double driveBackOutput = -0.4;

    Drivetrain drive;
    CargoSystem cargo;

    public Autonomas() {
        drive = new Drivetrain();
        cargo = new CargoSystem();
    }

    public void stopDriving() {
        drive.LFmotor.set(0);
        drive.LBmotor.set(0);
        drive.RFmotor.set(0);
        drive.RBmotor.set(0);
    }

    public void stopCargoMotors() {
        cargo.shooter.set(0);
        cargo.intake.set(0);
    }

    public void driveBack() {
        drive.LFmotor.set(driveBackOutput);
        drive.LBmotor.set(driveBackOutput);
        drive.RFmotor.set(driveBackOutput);
        drive.RBmotor.set(driveBackOutput);
    }
}
