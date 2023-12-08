package org.firstinspires.ftc.teamcode.Autonomous;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

public class Red2Sequence {
    SampleMecanumDrive drive;
    Hardware hardware;
    Utilities utilities;

    //Trajectories
    TrajectorySequence toGoal;
    TrajectorySequence parkTwo;


    Pose2d startPose = new Pose2d(-60,12,Math.toRadians(180));

    public Red2Sequence(HardwareMap hardwareMap , Utilities utilities){
        hardware = new Hardware();
        hardware.init(hardwareMap);
        this.utilities = utilities;
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startPose);

        toGoal = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(-30)
                .forward(-69)
                .turn(Math.toRadians(-31))
                .build();
        parkTwo = drive.trajectorySequenceBuilder((toGoal.end()))
                .turn(Math.toRadians(31))
                .strafeRight(40)
                .build();




    }

    public void red2(){
        drive.followTrajectorySequence(toGoal);
        utilities.outtakeWheel(0.65);
        utilities.wait(2000);
        utilities.shoot();
        utilities.wait(2000);
        utilities.outtakeWheel(0);
        drive.followTrajectorySequence(parkTwo);

    }

}