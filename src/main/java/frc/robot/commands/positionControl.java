/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Arrays;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Spinner;

public class positionControl extends CommandBase {

  private Spinner mSpinner = new Spinner();

  private String colorRequired;
  private String colorOrder[] = {"R","G","B","Y"};
  private int indexOfTarget;
  private double direction = 1.0;

  public positionControl() {
    addRequirements(mSpinner);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    colorRequired = mSpinner.getPositionColorRequired();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    indexOfTarget = Arrays.binarySearch(colorOrder, colorRequired) - Constants.robotToSensorDis;
    if (indexOfTarget < 0){
      indexOfTarget += 4;
      direction = -1.0;
    }
    mSpinner.spinWheel(direction);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (colorRequired.equals("")){
      return true;
    }else if(mSpinner.getColor().equals(colorOrder[indexOfTarget])){
      return true;
    }
    return false;
  }
}
