package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import com.acmerobotics.roadrunner.geometry.Pose2d;

public class CameraPathSequence {
    SampleMecanumDrive drive;
    Hardware hardware;
    Utilities utilities;

    //Trajectories
    Trajectory toGoal;
    Trajectory parkTwo;


    Pose2d startPose = new Pose2d(-60,12,Math.toRadians(180));

    public CameraPathSequence(HardwareMap hardwareMap , Utilities utilities){
        hardware = new Hardware();
        hardware.init(hardwareMap);
        this.utilities = utilities;
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startPose);

        toGoal = drive.trajectoryBuilder(startPose)
                .strafeLeft(2)
                .forward(10)
                .strafeLeft(4)
                .build();
        parkTwo = drive.trajectoryBuilder((toGoal.end()))
                .strafeRight(10)
                .back(28)
                .build();




    }

    public void blue2(){
        drive.followTrajectory(toGoal);
        drive.turn(Math.toRadians(30));
        utilities.outtakeWheel(1);
        utilities.shoot();
        drive.turn(Math.toRadians(-30));
        drive.followTrajectory(parkTwo);

    }

}
