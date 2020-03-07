/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Pin;

public class BallIntake extends SubsystemBase {

  private VictorSPX intakeMotor = new VictorSPX(Pin.intakeVictorPin);

  private Solenoid lIntakeSolenoid = new Solenoid(Pin.lIntakeChannel);
  private Solenoid rIntakeSolenoid = new Solenoid(Pin.rIntakeChannel);

  private int basketStateNumber = 1;
  private int numberOfBall =0;

  private DigitalInput ballNumberSensor = new DigitalInput(Pin.ballNumberSensor);

  /**
   * change the state of basket to next state when called
   */
  public void basketStateChange(){
    basketStateNumber += 1;
    if (basketStateNumber % 2 == 0){
      this.basketState(true);
    }else{
      this.basketState(false);
    }
  }

  /**
   * pull or push the intake solenoids according to basket state
   */
  private void basketState(boolean basketState){
    if (basketState){
      lIntakeSolenoid.set(true);
      rIntakeSolenoid.set(true);
    }else{
      lIntakeSolenoid.set(false);
      rIntakeSolenoid.set(false);
      intakeMotor.set(ControlMode.PercentOutput,0);
    }
  }

  /**
   * start intake motor
   */
  public void intakeBall(){
    intakeMotor.set(ControlMode.PercentOutput, Constants.intakeMotorOutput);
  }

  /**
   * stop intake motor
   */
  public void stopIntakeBall(){
    intakeMotor.set(ControlMode.PercentOutput, 0);
  }

  /**
   * return state of basket according to the state of intake solenoids
   * 
   */
  public boolean getBasketState(){
    return lIntakeSolenoid.get() && rIntakeSolenoid.get();
  }

  /**
   * Change the number of balls according to different cases.
   * 1: add ball using sensor
   * 2: decrese number of ball
   * 3: just completely changes number of balls
   */
  public void numberOfBalls(int state, int changesInBallNumber){
    switch(state){
      case 1:
        if (ballNumberSensor.get()){
          numberOfBall ++;
        }
        break;
      case 2:
        numberOfBall += changesInBallNumber;
        break;
      case 3:
        numberOfBall = changesInBallNumber;
        break;
    }
    SmartDashboard.putNumber("Number of Balls", this.getBallNumber());

  }

  /**
   * return number of balls
   */
  public int getBallNumber(){
    return numberOfBall;
  }

}