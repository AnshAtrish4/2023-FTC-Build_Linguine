package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import com.qualcomm.robotcore.hardware.Gamepad;


@TeleOp(name = "Linguine TeleOp")
public class LinguineTeleOp extends OpMode {


    Hardware hardware;
    Gamepad GamePad1 = new Gamepad();
    Gamepad GamePad2 = new Gamepad();
    final double SLOW_SPEED = 0.35;
    final double FAST_SPEED = 0.8;
    double speedConstant;
    ElapsedTime buttonTime = null;
    boolean fieldOriented;
    Orientation angles = new Orientation();
    double  yaw;
    double adjusted_yaw;
    @Override
    public void init() {
        hardware = new Hardware();
        hardware.init(hardwareMap);
        speedConstant = FAST_SPEED;
        fieldOriented = false;
        buttonTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        angles = hardware.imu.getAngularOrientation(AxesReference.INTRINSIC,AxesOrder.ZYX,AngleUnit.DEGREES);
        yaw = angles.firstAngle;
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
        GamePad1.copy(gamepad1);
        GamePad2.copy(gamepad2);
        drive();


    }


    private void drive() {


        //get controls from the controller
        double forward, strafe, turn;
        forward = gamepad1.left_stick_y;
        strafe = -gamepad1.left_stick_x; //Counteract imperfect strafing
        turn = -gamepad1.right_stick_x;


        //calculate drive values
        double rightFrontPower ;//= forward - turn - strafe;
        double rightBackPower ;//= forward - turn + strafe;
        double leftFrontPower ;//= forward + turn + strafe;
        double leftBackPower ;//= forward + turn - strafe;

        //Field Oriented Code
        if (fieldOriented) {
            angles = hardware.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

            adjusted_yaw = angles.firstAngle-yaw;

            double zerodYaw = -yaw+angles.firstAngle;

            double theta = Math.atan2(forward, strafe) * 180/Math.PI; // gets angle

            double realTheta;

            realTheta = (360 - zerodYaw) + theta;

            double power = Math.hypot(strafe, forward);

            double sin = Math.sin((realTheta * (Math.PI / 180)) - (Math.PI / 4));
            double cos = Math.cos((realTheta * (Math.PI / 180)) - (Math.PI / 4));
            double maxSinCos = Math.max(Math.abs(sin), Math.abs(cos));

            leftFrontPower = (power * cos / maxSinCos + turn);
            rightFrontPower = (power * sin / maxSinCos - turn);
            leftBackPower = (power * sin / maxSinCos + turn);
            rightBackPower = (power * cos / maxSinCos - turn);
        }
        else {
            leftFrontPower = forward + turn + strafe;
            leftBackPower = forward + turn - strafe;
            rightFrontPower = forward - turn - strafe;
            rightBackPower = forward - turn + strafe;
        }

        if(Math.abs(leftFrontPower) > 1 || Math.abs(leftBackPower) > 1 || Math.abs(rightFrontPower) > 1 || Math.abs(rightBackPower) > 1) {
            //Find max
            double max;
            max = Math.max(Math.abs(leftFrontPower), Math.abs(leftBackPower));
            max = Math.max(Math.abs(rightFrontPower),max);
            max = Math.max(Math.abs(rightBackPower),max);


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





        //Slow mode
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

        //Field Oriented Mode
        if(gamepad1.options && buttonTime.time()>=500){
            fieldOriented = true;
        }else if(gamepad1.share && buttonTime.time() >= 500){
            fieldOriented = false;
        }




        //set drive motor power
        hardware.leftFront.setPower(leftFrontPower*speedConstant);
        hardware.leftBack.setPower(leftBackPower*speedConstant);
        hardware.rightFront.setPower(rightFrontPower*speedConstant);
        hardware.rightBack.setPower(rightBackPower*speedConstant);
    }




}



