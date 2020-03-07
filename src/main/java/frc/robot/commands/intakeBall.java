/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallIntake;
import frc.robot.subsystems.Shooter;

public class intakeBall extends CommandBase {

  private BallIntake mIntakeBall = new BallIntake();
  private Shooter mShooter = new Shooter();
  private int stateOfIntake;

  public intakeBall(int state) {
    addRequirements(mIntakeBall);
    addRequirements(mShooter);
    stateOfIntake = state;
  }

  @Override
  public void initialize() {
  }

  /**
   * intake ball state
   * 1: basket down, no motor running, getting ball from upper station
   * 2: basket down, motor running, getting ball from lower station
   * above all requires conveyer
   * 3: stop converyer and intake
   */


  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (mIntakeBall.getBasketState()){
      switch(stateOfIntake){
        case 1:
          mIntakeBall.stopIntakeBall();
          mShooter.conveyBallState(true);
          mIntakeBall.numberOfBalls(1, 0);
          break;
        case 2:
          mIntakeBall.intakeBall();
          mShooter.conveyBallState(true);
          mIntakeBall.numberOfBalls(1, 0);
          break;
        case 3:
          mIntakeBall.stopIntakeBall();
          mShooter.conveyBallState(false);
          break;
      }
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (mIntakeBall.getBallNumber() == 5){
      mIntakeBall.stopIntakeBall();
      mShooter.conveyBallState(false);
      SmartDashboard.putString("Alarm", "Number of Balls in full capacity!");
      return true;
    }
    return false;
  }
}
