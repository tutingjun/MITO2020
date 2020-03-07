/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Pin;
import frc.robot.Utility;

public class Shooter extends SubsystemBase {

  private TalonFX shooterMotorL = new TalonFX(Pin.shooterFalconL);
  private TalonFX shooterMotorR = new TalonFX(Pin.shooterFalconR);
  private VictorSPX ballConveyMotor =  new VictorSPX(Pin.conveyerVictor);
  private VictorSPX upperBallConveyer = new VictorSPX(Pin.upperBallConveyer);
  private TalonSRX shooterRotation = new TalonSRX(Pin.shooterTalon);

  private DigitalInput ballSensor = new DigitalInput(Pin.ballSensorID);

  private Solenoid shooterAngleSolenoid = new Solenoid(Pin.shooterAngleSolenoid);
  

  public Shooter() {
    Utility.TalonSRXInit(shooterRotation);
    Utility.TalonFXInit(shooterMotorL);
    Utility.TalonFXInit(shooterMotorR);

    Utility.configTalonFXPID(shooterMotorR,0.1097, 0.22, 0, 0, 0);
    Utility.configTalonSRXPID(shooterRotation, 0.0, 0.4, 0.0002, 40, 150, 0.5);

    shooterMotorL.follow(shooterMotorR);

    shooterRotation.setInverted(true);
    shooterRotation.setSelectedSensorPosition(0);

    shooterAngleSolenoid.set(true);
  }

  /**
   * set shootermotor to targetVelocity
   */
   public void shootBall(double targetVelocity){
    targetVelocity *= Constants.velocityConstantsFalcon;
     shooterMotorR.set(ControlMode.Velocity, targetVelocity);
   }

   /**
    * start upperBallConveyer when True
    * 
    */
   public void pushBall(boolean pushBallState){
     if (pushBallState){
      upperBallConveyer.set(ControlMode.PercentOutput,Constants.conveyerOutput);
     }else{
      upperBallConveyer.set(ControlMode.PercentOutput,0);
     }
   }

   /**
    * start ballConveyMotor when True
    */
   public void conveyBallState(boolean conveyBallState){
    if (conveyBallState){
      ballConveyMotor.set(ControlMode.PercentOutput, Constants.conveyerOutput);
    }else{
      ballConveyMotor.set(ControlMode.PercentOutput, 0);
    }
   }

   /**
    * return true when ballSensor detected ball
    */
   public boolean isBallInPosition(){
     return ballSensor.get();
   }

   public void changeShooterAngle(boolean flag){
    shooterAngleSolenoid.set(!flag);
   }

   /**
    * Change the angle of shooter when not reaching its extreme positions.
    * Returning true when succefully rotated.
    */
   public boolean shooterRotation(double changeInAngle){
     if (shooterRotation.getSelectedSensorPosition() >= Constants.shooterMaxAngle &&
          shooterRotation.getSelectedSensorPosition() < Constants.shooterMinAngle)
     {
       SmartDashboard.putString("Shooter Angle", "reached extreme");
       return false;
     }else{
       shooterRotation.set(ControlMode.Position, changeInAngle * Constants.shooterOneCircle);
       SmartDashboard.putNumber("Shooter Angle", shooterRotation.getSelectedSensorPosition());
       return true;
     }

   }


}
