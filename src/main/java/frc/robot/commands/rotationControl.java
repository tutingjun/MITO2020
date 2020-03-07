/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Spinner;

public class rotationControl extends CommandBase {

  private Spinner mSpinner = new Spinner();

  private String colorFirstDetected;
  private String colorDetectedNow;
  private String colorDetectedPre;
  private int numberOfDetected;

  public rotationControl() {
    addRequirements(mSpinner);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    colorFirstDetected = mSpinner.getColor();
    numberOfDetected = 1;
    colorDetectedNow = colorFirstDetected;
    colorDetectedPre = colorFirstDetected;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mSpinner.spinWheel(1.0);
    colorDetectedNow = mSpinner.getColor();

    if (colorDetectedNow.equals(colorFirstDetected)
        && !colorDetectedNow.equals(colorDetectedPre)) {   
      numberOfDetected ++;
    }else if (numberOfDetected == 5 &&
              colorDetectedNow.equals(colorFirstDetected) &&
              colorDetectedNow.equals(colorDetectedPre)){
      numberOfDetected ++;
    }

    colorDetectedPre = colorDetectedNow;
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (numberOfDetected == 6){
      mSpinner.stopWheel();
      return true;
    }
    return false;
  }
}
