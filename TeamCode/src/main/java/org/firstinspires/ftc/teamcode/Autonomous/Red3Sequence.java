package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import org.firstinspires.ftc.teamcode.hardware.Hardware;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class Red3Sequence {
    SampleMecanumDrive drive;
    Hardware hardware;
    Utilities utilities;

    //Trajectories
    TrajectorySequence toGoal;
    TrajectorySequence parkTwo;


    Pose2d startPose = new Pose2d(-60,12,Math.toRadians(180));

    public Red3Sequence(HardwareMap hardwareMap , Utilities utilities){
        hardware = new Hardware();
        hardware.init(hardwareMap);
        this.utilities = utilities;
        drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startPose);

        toGoal = drive.trajectorySequenceBuilder(startPose)
                .strafeLeft(-30)
                .forward(-73)
                .turn(Math.toRadians(-12.5))
                .build();
        parkTwo = drive.trajectorySequenceBuilder((toGoal.end()))
                .turn(Math.toRadians(12.5))
                .strafeRight(-14)
                .forward(-16)
                .build();




    }

    public void red3(){
        drive.followTrajectorySequence(toGoal);
        utilities.outtakeWheel(.9);
        utilities.wait(2000);
        utilities.shoot();
        utilities.outtakeWheel(0);
        drive.followTrajectorySequence(parkTwo);

    }

}