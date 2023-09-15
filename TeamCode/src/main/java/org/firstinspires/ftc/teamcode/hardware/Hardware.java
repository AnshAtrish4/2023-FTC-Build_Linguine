package org.firstinspires.ftc.teamcode.hardware;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


import org.firstinspires.ftc.robotcore.internal.system.Assert;

public class Hardware {

    public DcMotorEx rightFront;
    public DcMotorEx leftFront;
    public DcMotorEx rightBack;
    public DcMotorEx leftBack;
    public DcMotorEx[] driveMotors;




    public void init(HardwareMap hardwareMap) {
        Assert.assertNotNull(hardwareMap);
        initializeDriveMotors(hardwareMap);

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

    public void initializeIntakeMotors(HardwareMap hardwareMap) {
        //set up intake servos


        //set up intake motors


        //set intake servo settings


        //set intake motor

    }
}
