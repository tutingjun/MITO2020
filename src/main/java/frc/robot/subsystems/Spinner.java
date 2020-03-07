/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Pin;

public class Spinner extends SubsystemBase {

  String gameData;

  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final VictorSPX spinnerMotor = new VictorSPX(Pin.spinnerMotor);

  private final Solenoid spinnerSolenoid = new Solenoid(Pin.spinnerSolenoid);

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);

  private String colorString;
  private int spinnerState = 1;

  public Spinner() {
    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);
  }

  public String getColor() {
    Color detectedColor = m_colorSensor.getColor();
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

    if (match.color == kBlueTarget) {
      colorString = "B";
    } else if (match.color == kRedTarget) {
      colorString = "R";
    } else if (match.color == kGreenTarget) {
      colorString = "G";
    } else if (match.color == kYellowTarget) {
      colorString = "Y";
    }
    return colorString;
  }

  /**
   * Returning the target color.
   * If failed, then return an empty string.
   */
  public String getPositionColorRequired() {
    gameData = DriverStation.getInstance().getGameSpecificMessage();

    if (gameData.length() > 0) {
      SmartDashboard.putString("DriverStationCode", String.valueOf(gameData.charAt(0)));
      return String.valueOf(gameData.charAt(0));
    } else {
      SmartDashboard.putString("DriverStationCode", "Not Found");
      return "";
    }
  }

  public void spinWheel(double direction) {
    spinnerMotor.set(ControlMode.PercentOutput, direction * Constants.spinnerOutput);
  }

  public void stopWheel() {
    spinnerMotor.set(ControlMode.PercentOutput, 0);
  }

  /**
   * Changing the spinnerState to next state,
   * and lower or raise it according to the spinnerState.
   */
  public void spinnerState(){
    spinnerState += 1;
    if (spinnerState % 2 == 0){
      this.spinnerUp();
    }else{
      this.spinnerDown();
    }
  }

  /**
   * raise the spinner using spinnerSolenoid
   */
  private void spinnerUp() {
    spinnerSolenoid.set(true);
  }

  /**
   * lower the spinner using spinnerSolenoid
   */
  private void spinnerDown() {
    spinnerSolenoid.set(false);
  }

}
