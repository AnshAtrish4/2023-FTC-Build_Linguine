package org.firstinspires.ftc.teamcode.hardware;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.bosch.BNO055IMU;


import org.firstinspires.ftc.robotcore.internal.system.Assert;

public class Hardware {

    public DcMotorEx rightFront;
    public DcMotorEx leftFront;
    public DcMotorEx rightBack;
    public DcMotorEx leftBack;
    public DcMotorEx[] driveMotors;
    public DcMotorEx intake;
    public BNO055IMU imu;



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

        imu = hardwareMap.get(BNO055IMU.class,HardwareIDs.IMU);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;
        imu.initialize(parameters);
    }

    public void initializeSupplementaryMotors(HardwareMap hardwareMap){
      intake = hardwareMap.get(DcMotorEx.class,HardwareIDs.INTAKE);
      intake.setPower(0);
      intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
      intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }


}
