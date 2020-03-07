/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Pin;
import frc.robot.Utility;

public class Drivetrain extends SubsystemBase {

  private TalonFX rMotorMaster = new TalonFX(Pin.rFalconMaster);
  private TalonFX rMotorSlave = new TalonFX(Pin.rFalconSlave);
  private TalonFX lMotorMaster = new TalonFX(Pin.lFalconMaster);
  private TalonFX lMotorSlave = new TalonFX(Pin.lFalconSlave);

  private Solenoid lShiftGear = new Solenoid(Pin.lShiftGear);
  private Solenoid rShiftGear = new Solenoid(Pin.rShiftGear);

  private AHRS ahrs = new AHRS(SPI.Port.kMXP);

  private int gearState = 0;
  //private SpeedControllerGroup test = new SpeedControllerGroup(new WPI_TalonFX(Pin.lFalconMaster),
  //                                                             new WPI_TalonFX(Pin.lFalconSlave));
  

  public Drivetrain() {
    Utility.TalonFXInit(rMotorMaster);
    Utility.TalonFXInit(lMotorMaster);
    Utility.TalonFXInit(rMotorSlave);
    Utility.TalonFXInit(lMotorSlave);

    rMotorSlave.follow(rMotorMaster);
    lMotorSlave.follow(lMotorMaster);

    lMotorMaster.setInverted(true);
    lMotorSlave.setInverted(true);
    rMotorMaster.setInverted(false);
    rMotorSlave.setInverted(false);
    
    Utility.configTalonFXPID(lMotorMaster,0.1097, 0.22, 0, 0, 0);
    Utility.configTalonFXPID(rMotorMaster,0.1097, 0.22, 0, 0, 0);

    SmartDashboard.putNumber("Gear value", 1);
    ahrs.reset();
  }

  public void velocityDrive(double forward, double turn){
    double lSpeed = (forward - turn) 
                      * Constants.drivetrainTargetRPM 
                      * Constants.velocityConstantsFalcon;
    double rSpeed = (forward + turn) 
                      * Constants.drivetrainTargetRPM 
                      * Constants.velocityConstantsFalcon;
    
    lMotorMaster.set(ControlMode.Velocity, lSpeed);
    rMotorMaster.set(ControlMode.Velocity, rSpeed);
  }



  /**
   * change to next gear ratio through shiftgear solenoids
   */
  public void shiftGear(){
    gearState += 1;
    if (gearState % 2 == 0){
      rShiftGear.set(true);
      lShiftGear.set(true);
      SmartDashboard.putNumber("Gear value", 1);
    }else{
      rShiftGear.set(false);
      lShiftGear.set(false);
      SmartDashboard.putNumber("Gear value", 2);
    }

  }

  public int getGearState(){
    return (gearState % 2) + 1;
  }

  /**
   * rotate use pid to turn
   */
  public double getCurrentAngle(){
    return Math.IEEEremainder(ahrs.getAngle(), 360);
  }




}
