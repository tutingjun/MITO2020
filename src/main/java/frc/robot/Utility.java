/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Add your docs here.
 */
public class Utility {

    //TalonFX init
    public static void TalonFXInit(TalonFX _talon) {
         _talon.configSelectedFeedbackSensor(TalonFXFeedbackDevice.IntegratedSensor,
          Constants.kPIDLoopIdx,Constants.kTimeoutMs);
        _talon.setSensorPhase(true);
        
        _talon.configNominalOutputForward(0, Constants.kTimeoutMs);
        _talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
        _talon.configPeakOutputForward(1, Constants.kTimeoutMs);
        _talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);
      }

    //TalonSRX init
    public static void TalonSRXInit(TalonSRX _talon) {        
        _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
          Constants.kPIDLoopIdx,Constants.kTimeoutMs);
        _talon.setSensorPhase(true);
        
        _talon.configNominalOutputForward(0, Constants.kTimeoutMs);
        _talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
        _talon.configPeakOutputForward(1, Constants.kTimeoutMs);
        _talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);
      }


    //PID setting
    public static void configTalonSRXPID(TalonSRX _talon,double kF, double kP,double kI, double kD, int kIzone, double kRamp) {
		_talon.config_kF(Constants.kPIDLoopIdx, kF, Constants.kTimeoutMs);
		_talon.config_kP(Constants.kPIDLoopIdx, kP, Constants.kTimeoutMs);
		_talon.config_kI(Constants.kPIDLoopIdx, kI, Constants.kTimeoutMs);
        _talon.config_kD(Constants.kPIDLoopIdx, kD, Constants.kTimeoutMs);
        _talon.config_IntegralZone(Constants.kPIDLoopIdx, kIzone, Constants.kTimeoutMs);
        _talon.configClosedloopRamp(kRamp, Constants.kTimeoutMs);

    }

    public static void configTalonFXPID(TalonFX _talon,double kF, double kP, double kI, double kD, double kRamp) {
		_talon.config_kF(Constants.kPIDLoopIdx, kF, Constants.kTimeoutMs);
		_talon.config_kP(Constants.kPIDLoopIdx, kP, Constants.kTimeoutMs);
		_talon.config_kI(Constants.kPIDLoopIdx, kI, Constants.kTimeoutMs);
        _talon.config_kD(Constants.kPIDLoopIdx, kD, Constants.kTimeoutMs);
        _talon.configClosedloopRamp(kRamp, Constants.kTimeoutMs);
    }

    //motor safety
    public static void configMotorSafety(TalonSRX _talon) {
        _talon.enableCurrentLimit(true);
        _talon.configContinuousCurrentLimit(Constants.kCurrentLimit, Constants.kTimeoutMs);
        _talon.configPeakCurrentLimit((int)(Constants.kCurrentLimit*1.5), Constants.kTimeoutMs);
        _talon.configPeakCurrentDuration(Constants.kCurrentLimitDuration, Constants.kTimeoutMs); 
    }

    //turn angle
    public static double targetAngleModify(double turnAngle, double currentAngle){
      if (turnAngle > 180.0){
        return currentAngle + turnAngle - 360.0;
      }
      return currentAngle + turnAngle;
    }

}
