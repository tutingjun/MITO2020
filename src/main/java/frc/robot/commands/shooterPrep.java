/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class shooterPrep extends CommandBase {

  private Limelight mLimelight = new Limelight();
  private Shooter mShooter = new Shooter();
  private Drivetrain mDrivetrain = new Drivetrain();

  private double optiumRPM;
  private double distanceEstimate;

/** 
* calculate the destinated RPM
* raise speed
* change the intake angle solenoid
*/

  public shooterPrep() {
    addRequirements(mLimelight);
    addRequirements(mShooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    distanceEstimate = mLimelight.getEstimateDistance();
    optiumRPM = mLimelight.getOptimumRPM(distanceEstimate);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mShooter.shootBall(optiumRPM);
    if (distanceEstimate < Constants.largerShooterAngleDis){
      mShooter.changeShooterAngle(true);
    }else{
      mShooter.changeShooterAngle(false);
    }
  }

  @Override
  public void end(boolean interrupted) {
    mDrivetrain.shiftGear();
    mShooter.shootBall(Constants.shooterRegularRunningSpeed);
  }

}
