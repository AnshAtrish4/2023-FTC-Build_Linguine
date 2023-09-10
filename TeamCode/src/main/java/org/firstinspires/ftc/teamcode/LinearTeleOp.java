package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name = "TeleOp")
public class LinearTeleOp extends LinearOpMode {

    Hardware hardware;
    @Override
    public void runOpMode() throws InterruptedException {
        hardware.init(hardwareMap);

        waitForStart();
        while(opModeIsActive()) {
            checkControllerInput();
        }
    }

    private void checkControllerInput() {
        double forward, strafe, turn;
        //forward - right y
        //strafe - right x
        //turn - left x

        forward = gamepad1.right_stick_y;
        strafe = gamepad1.right_stick_x;
        turn = gamepad1.left_stick_x;
        setDriveMotorPower(forward,turn, strafe);
    }

    private void setDriveMotorPower(double forward, double turn, double strafe) {
        hardware.rightFront.setPower(forward - turn - strafe);
        hardware.rightBack.setPower(forward - turn + strafe);
        hardware.leftFront.setPower(forward + turn + strafe);
        hardware.leftBack.setPower(forward + turn - strafe);
    }
}

