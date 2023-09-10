package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name = "Ravioli TeleOp")
public class RavioliTeleOp extends OpMode {

    Hardware hardware;

    @Override
    public void init() {
        hardware = new Hardware();
        hardware.init(hardwareMap);
    }

    @Override
    public void loop() {
        updateMotors();
    }

    private void updateMotors() {
        checkControllerInput();
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

