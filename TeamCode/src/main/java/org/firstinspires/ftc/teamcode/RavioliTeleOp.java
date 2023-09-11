package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name = "Ravioli TeleOp")
public class RavioliTeleOp extends OpMode {

    Hardware hardware;
    final double SLOW_SPEED = 0.5;
    final double FAST_SPEED = 0.8;
    double speedConstant;
    boolean fineControl;
    ElapsedTime speedSwapButtonTime = null;
    ElapsedTime fineControlButtonTime = null;

    @Override
    public void init() {
        hardware = new Hardware();
        hardware.init(hardwareMap);
        speedConstant = FAST_SPEED;
        fineControl = false;
        speedSwapButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        fineControlButtonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        telemetry.addData("Status:: ", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        telemetry.addData("Status:: ", "Started");
        telemetry.update();
    }

    @Override
    public void loop() {
        drive();
    }

    private void drive() {
        //check for speed swap
        if(gamepad1.square && speedSwapButtonTime.time() >= 500) {
            if(speedConstant == FAST_SPEED)
                speedConstant = SLOW_SPEED;
            if(speedConstant == SLOW_SPEED)
                speedConstant = FAST_SPEED;
            speedSwapButtonTime.reset();
        }
        //check for fine control/normal mode swap
        if(gamepad1.triangle && fineControlButtonTime.time() >= 500) {
            fineControl = !fineControl;
            fineControlButtonTime.reset();
        }

        //get controller input
        double forward, strafe, turn;
        forward = gamepad1.right_stick_y;
        strafe = gamepad1.right_stick_x;
        turn = gamepad1.left_stick_x;

        //calculate initial powers
        double rightFrontPower = forward - turn - strafe;
        double rightBackPower = forward - turn + strafe;
        double leftFrontPower = forward + turn + strafe;
        double leftBackPower = forward + turn - strafe;

        if(Math.abs(rightFrontPower) > 1 || Math.abs(rightBackPower) > 1 || Math.abs(leftFrontPower) > 1 || Math.abs(leftBackPower) > 1) {
            double max;
            max = Math.max(Math.abs(rightFrontPower), Math.abs(rightBackPower));
            max = Math.max(max, Math.abs(leftFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));

            rightFrontPower /= max;
            rightBackPower /= max;
            leftFrontPower /= max;
            leftBackPower /= max;
        }

        //adjust power based on mode
        if(fineControl) {
            rightFrontPower = Math.pow(rightFrontPower, 2);
            rightBackPower = Math.pow(rightBackPower, 2);
            leftFrontPower = Math.pow(leftFrontPower, 2);
            leftBackPower = Math.pow(leftBackPower, 2);
        }
        else {
            rightFrontPower = rightFrontPower * speedConstant;
            rightBackPower = rightBackPower * speedConstant;
            leftFrontPower = leftFrontPower * speedConstant;
            leftBackPower = leftBackPower * speedConstant;
        }

        //set drive motor power
        hardware.rightFront.setPower(rightFrontPower);
        hardware.rightBack.setPower(rightBackPower);
        hardware.leftFront.setPower(leftFrontPower);
        hardware.leftBack.setPower(leftBackPower);
    }
}

