package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@Autonomous(name = "Rough")
public class Auton extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Hardware hardware = new Hardware();

        hardware.init(hardwareMap);

        hardware.leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        hardware.leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        hardware.arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        Utilities utilities = new Utilities(hardware);
        //initialize
        waitForStart();
        while(opModeIsActive()) {

        }
    }
}
