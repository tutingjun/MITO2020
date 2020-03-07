/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallIntake;
import frc.robot.subsystems.Shooter;

public class conveyBall extends CommandBase {

  /**
  * stop Solenoid false
  * convey the ball until the sensor detected
  * stop solenoid true
  */

  private Shooter mShooter = new Shooter();
  private BallIntake mBallIntake = new BallIntake();

  public conveyBall() {
    addRequirements(mShooter);
    addRequirements(mBallIntake);
  }

  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    mShooter.conveyBallState(true);
    if (mShooter.isBallInPosition()){
      mShooter.conveyBallState(true);
      mShooter.pushBall(true);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (!mShooter.isBallInPosition()){
      mShooter.conveyBallState(false);
      mShooter.pushBall(false);
      mBallIntake.numberOfBalls(3, 0);
      return true;
    }
    return false;
  }
}
