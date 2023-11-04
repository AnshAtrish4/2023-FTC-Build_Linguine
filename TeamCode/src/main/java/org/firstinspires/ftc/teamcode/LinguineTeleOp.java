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
    final double SLOW_SPEED = 0.30;
    final double FAST_SPEED = 0.8;
    final double INTAKE_SPEED = 1.0;
    final double ARM_SPEED = 0.5;
    double speedConstant;
    ElapsedTime driveTime = null;

    boolean intakeOn = false;
    @Override
    public void init() {
        hardware = new Hardware();
        hardware.init(hardwareMap);
        speedConstant = FAST_SPEED;
        driveTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

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
        intake();
        arm();
        launch();


    }


    private void drive() {

        //Slow mode
        if (gamepad1.square && speedConstant == FAST_SPEED && driveTime.time() >= 500)
        {
            speedConstant = SLOW_SPEED;
            driveTime.reset();
        }
        else if (gamepad1.square && speedConstant == SLOW_SPEED && driveTime.time() >= 500)
        {
            speedConstant = FAST_SPEED;
            driveTime.reset();
        }


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

        leftFrontPower = forward + turn + strafe;
        leftBackPower = forward + turn - strafe;
        rightFrontPower = forward - turn - strafe;
        rightBackPower = forward - turn + strafe;


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


        //set drive motor power
        hardware.leftFront.setPower(leftFrontPower*speedConstant);
        hardware.leftBack.setPower(leftBackPower*speedConstant);
        hardware.rightFront.setPower(rightFrontPower*speedConstant);
        hardware.rightBack.setPower(rightBackPower*speedConstant);
    }

    private void intake(){

        // if(gamepad2.right_bumper&& intakeTime.time() >= 500){
        //   intakeOn = !intakeOn;
        //  intakeTime.reset();
        //}
        //if(intakeOn){
        //   hardware.intake.setPower(INTAKE_SPEED);
        //}
        if(gamepad1.left_trigger > 0.0){
            hardware.intake.setPower(gamepad1.left_trigger);
        }else if(gamepad1.right_trigger > 0.0){
            hardware.intake.setPower(-gamepad1.right_trigger);
        }else{
            hardware.intake.setPower(0);
        }



    }
    public void launch(){
        //if(gamepad2.share ) {
          //  if (gamepad2.right_trigger > 0.0) {
            //    hardware.outtake.setPower(-gamepad2.right_trigger);
              //  telemetry.addData("Flywheels:: ", "Spinning");
            //} else {
             //   hardware.outtake.setPower(0.0);
              //  telemetry.addData("Flywheels:: ", "Stopped");
            //}
        //}

        //New Outtake Flywheel controls
        if(gamepad2.right_trigger>0.0){
            hardware.outtake.setPower(-gamepad2.right_trigger);
            telemetry.addData("Flywheels:: ", "Spinning");
        }else{
            hardware.outtake.setPower(0.0);
            telemetry.addData("Flywheels:: ", "Stopped");
        }

        //New pusher controls
        if(gamepad2.right_bumper){
            hardware.launchPusher.setPosition(1.0);
        }else if(gamepad2.left_bumper){
            hardware.launchPusher.setPosition(-1.0);
        }
    }

    public void arm(){
     //   if(gamepad2.options) {
       //     if (gamepad2.right_trigger > 0.0) {
         //       hardware.arm.setPower(gamepad2.right_trigger * 0.50);
           // } else if (gamepad2.left_trigger > 0.0) {
             //   hardware.arm.setPower(-gamepad2.left_trigger * 0.50);
            //} else {
             //   hardware.arm.setPower(0);
            //}

        //New Arm Controls

        if(gamepad2.square){
            hardware.arm.setPower(ARM_SPEED);
        }else if (gamepad2.circle){
            hardware.arm.setPower(-ARM_SPEED);
        }
    }




}






