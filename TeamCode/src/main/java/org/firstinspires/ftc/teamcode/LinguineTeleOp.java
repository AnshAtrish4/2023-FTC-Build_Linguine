package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name = "Linguine TeleOp")
public class LinguineTeleOp extends OpMode {

    Hardware hardware;
    final double SLOW_SPEED = 0.35;
    final double FAST_SPEED = 0.8;
    double speedConstant;
    ElapsedTime buttonTime = null;



    @Override
    public void init() {
        hardware = new Hardware();
        hardware.init(hardwareMap);
        speedConstant = FAST_SPEED;
        buttonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
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



        //get controls from the controller
        double forward, strafe, turn;
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.right_stick_x; //Counteract imperfect strafing
        turn = -gamepad1.right_stick_x;

        //calculate drive values
        double rightFrontPower = forward - turn - strafe;
        double rightBackPower = forward - turn + strafe;
        double leftFrontPower = forward + turn + strafe;
        double leftBackPower = forward + turn - strafe;

        if(Math.abs(leftFrontPower) > 1 || Math.abs(leftBackPower) > 1 || Math.abs(rightFrontPower) > 1 || Math.abs(rightBackPower) > 1) {
            //Find max
            double max;
            max = Math.max(Math.abs(leftFrontPower), Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(rightBackPower));

            //Divide everything by the max
            leftFrontPower /= max;
            leftBackPower /= max;
            rightFrontPower /= max;
            rightBackPower /= max;
        }

        if (gamepad1.dpad_up )
        {
            leftFrontPower = speedConstant;
            rightBackPower = speedConstant;
            rightFrontPower = speedConstant;
            leftBackPower = speedConstant;
        }
        else if (gamepad1.dpad_down)
        {
            leftFrontPower = -speedConstant;
            rightBackPower = -speedConstant;
            rightFrontPower = -speedConstant;
            leftBackPower = -speedConstant;
        }
        else if (gamepad1.dpad_right)
        {
            leftFrontPower = -1;
            rightBackPower = -1;
            rightFrontPower = 1;
            leftBackPower = 1;
        }
        else if (gamepad1.dpad_left)
        {
            leftFrontPower = 1;
            rightBackPower = 1;
            rightFrontPower = -1;
            leftBackPower = -1;
        }


        if (gamepad1.square && speedConstant == FAST_SPEED && buttonTime.time() >= 500)
        {
            speedConstant = SLOW_SPEED;
            buttonTime.reset();
        }
        else if (gamepad1.square && speedConstant == SLOW_SPEED && buttonTime.time() >= 500)
        {
            speedConstant = FAST_SPEED;
            buttonTime.reset();
        }


        //set drive motor power
        hardware.leftFront.setPower(leftFrontPower*speedConstant);
        hardware.leftBack.setPower(leftBackPower*speedConstant);
        hardware.rightFront.setPower(rightFrontPower*speedConstant);
        hardware.rightBack.setPower(rightBackPower*speedConstant);
    }


}

