package org.firstinspires.ftc.teamcode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.robotcore.external.Telemetry;
public class Utilities
{
    private Hardware hardware;

    public Utilities(Hardware hardware)
    {
        this.hardware = hardware;
    }

    public void wait(int waitTime)
    {
        ElapsedTime time = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        time.reset();
        while (time.time() < waitTime){}
    }

    public void shoot()
    {
        hardware.launchPusher.setPosition(-1.0);
        wait(500);
        hardware.launchPusher.setPosition(1.0);
    }



    public void outtakeWheel(double velocity)
    {
        hardware.outtake.setVelocity(velocity);
    }
}