/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Pin {

    //Solenoid
        //intake
	    public static final int lIntakeChannel = 0;
        public static final int rIntakeChannel = 0;

        //drivetrain
        public static final int lShiftGear = 0;
        public static final int rShiftGear = 0;
        
        //shooter
        public static final int shooterSolenoid = 0;
        public static final int conveyerStopSolenoidL = 0;
        public static final int conveyerStopSolenoidR = 0;
        public static final int shooterAngleSolenoid = 0;

        //spinner
        public static final int spinnerSolenoid = 0;
    

    //Motor control
        //intake
        public static final int intakeVictorPin = 0;
        
        //drivetrain
		public static final int rFalconMaster = 0;
		public static final int rFalconSlave = 0;
		public static final int lFalconMaster = 0;
        public static final int lFalconSlave = 0;
        
        //shooter
		public static final int shooterFalconL = 0;
		public static final int shooterFalconR = 0;
        public static final int conveyerVictor = 0;
        public static final int shooterTalon = 0;
        public static final int upperBallConveyer = 0;
        
        //spinner
        public static final int spinnerMotor = 0;
    
    
    //Sensor
        //shooter
        public static final int ballSensorID = 0;

        //intake
        public static final int ballNumberSensor = 0;
    
    
    //joystick
		public static final int moveStickPort = 0;
		public static final int functionStickPort = 1;

}
