/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;

public class dummyAutoAim extends CommandBase {
  private Limelight mLimelight = new Limelight();
  private Shooter mShooter = new Shooter();

  double headError;
  double steeringAdjustment;

  public dummyAutoAim() {
    addRequirements(mLimelight);
    addRequirements(mShooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    steeringAdjustment = 0.0;
    if (mLimelight.isTargetFound()){
      headError = -mLimelight.getTx();
      if (mLimelight.getTx() > 1.0){
        steeringAdjustment = Constants.kpAim * headError 
                                - Constants.minAimCommend;
      }else if (mLimelight.getTx() < 1.0){
        steeringAdjustment = Constants.kpAim * headError 
                                + Constants.minAimCommend;
      }

      mShooter.shooterRotation(steeringAdjustment);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(mLimelight.getTx()) < Constants.kTxTorrance;
  }
}
