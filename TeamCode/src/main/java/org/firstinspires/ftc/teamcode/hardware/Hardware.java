package org.firstinspires.ftc.teamcode.hardware;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Servo;


import org.firstinspires.ftc.robotcore.internal.system.Assert;

public class Hardware {

    public DcMotorEx rightFront;
    public DcMotorEx leftFront;
    public DcMotorEx rightBack;
    public DcMotorEx leftBack;
    public DcMotorEx[] driveMotors;
    public DcMotorEx outtake;
    public DcMotorEx intake;
    public DcMotorEx arm;
    public Servo launchPusher;




    public void init(HardwareMap hardwareMap) {
        Assert.assertNotNull(hardwareMap);
        initializeDriveMotors(hardwareMap);
        intializeIntakeMotors(hardwareMap);
        intializeArmMotors(hardwareMap);
        intializeLaunchMotors(hardwareMap);

    }

    public void initializeDriveMotors(HardwareMap hardwareMap) {
        //set up drive motors
        rightFront = hardwareMap.get(DcMotorEx.class, HardwareIDs.RIGHT_FRONT_MOTOR);
        rightBack = hardwareMap.get(DcMotorEx.class, HardwareIDs.RIGHT_BACK_MOTOR);
        leftFront = hardwareMap.get(DcMotorEx.class, HardwareIDs.LEFT_FRONT_MOTOR);
        leftBack = hardwareMap.get(DcMotorEx.class, HardwareIDs.LEFT_BACK_MOTOR);

        //set left side to reverse
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        //set motors to desired settings
        driveMotors = new DcMotorEx[]{rightFront, rightBack, leftFront, leftBack};
        for(DcMotorEx motor: driveMotors){
            motor.setPower(0.0);
            motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }


    }

    public void intializeIntakeMotors(HardwareMap hardwareMap){
      intake = hardwareMap.get(DcMotorEx.class,HardwareIDs.INTAKE);
      intake.setPower(0.0);
      intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
      intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void intializeArmMotors(HardwareMap hardwareMap){
        arm = hardwareMap.get(DcMotorEx.class,HardwareIDs.ARM);
        arm.setPower(0.0);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void intializeLaunchMotors(HardwareMap hardwareMap){
        outtake = hardwareMap.get(DcMotorEx.class,HardwareIDs.OUTTAKE);
        outtake.setPower(0.0);
        outtake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        outtake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        launchPusher = hardwareMap.get(Servo.class,HardwareIDs.PUSHER);
        launchPusher.setPosition(1.0);   //Make this 1.0 if the condition doesn't work
        //The position above was 0.0 before

    }


}
