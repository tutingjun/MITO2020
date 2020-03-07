/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Limelight extends SubsystemBase {
  /**
   * Creates a new Limelight.
   */

  private NetworkTable table;
  private double distance;

  public Limelight(){
    table = NetworkTableInstance.getDefault().getTable("limelight");
  }

  public boolean isTargetFound(){
    double tv =  table.getEntry("tv").getDouble(0);
    if (tv < 1.0){
        return false;
    }else{
        return true;
    }
  }

  public double getTx(){
     return table.getEntry("tx").getDouble(0);
  }

  public double getTy(){
      return table.getEntry("ty").getDouble(0);
  }

  public double getTa(){
    return table.getEntry("ta").getDouble(0);
  }

  public double getEstimateDistance(){
    //calibrate the ratio between ta and the actual distance
    distance = getTa() * Constants.taToDistanceRatio;
    return distance;
  }

  public double getOptimumRPM(double distance){
    //use regression to model the RPM
    return this.getEstimateDistance()*Constants.regressionCoeff;
  }
  
}
