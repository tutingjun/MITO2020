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

public class accurateAutoAim extends CommandBase {

  private Limelight mLimelight = new Limelight();
  private Drivetrain mDrivetrain = new Drivetrain();

  double distanceError;
  double distanceAdjustment;

  public accurateAutoAim() {
    addRequirements(mLimelight);
    addRequirements(mDrivetrain);
  }

  @Override
  public void initialize() {
    if (mDrivetrain.getGearState() == 1){
      mDrivetrain.shiftGear();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    distanceAdjustment = 0.0;
    if (mLimelight.isTargetFound()){
      distanceError = -mLimelight.getTy();
      distanceAdjustment = Constants.kpDistance * distanceError;
    }

      mDrivetrain.velocityDrive(distanceAdjustment, 0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(mLimelight.getTy()) < Constants.kTyTorrance;
  }
}
