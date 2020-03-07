/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

	public static final int kPIDLoopIdx = 0;
	public static final int kTimeoutMs = 10;

	//velocity constant
	public static final double velocityConstantsFalcon = 2048.0/600.0;
	public static final double velocityConstants = 4096.0/600.0;

	//rotation
	public static final double kTurnToleranceDeg = 1;
	public static final double kTurnRateToleranceDegPerS = 10;

	//target speed
	public static final double drivetrainTargetRPM = 5000;
	public static final double conveyerOutput = 0;
	public static final double spinnerOutput = 0;
	public static final double intakeMotorOutput = 0;

	//motor safety
	public static final int kCurrentLimit = 8;
	public static final int kCurrentLimitDuration = 10;

	//shooter
		//shooter angle
		public static final int shooterMaxAngle = 0;
		public static final int shooterMinAngle = 0;

		//shooter calculation constants
		public static final double taToDistanceRatio = 0;
		public static final double regressionCoeff = 0;
		public static final double largerShooterAngleDis = 0;

		//shooter whole cycle (in raw sensor units)
		public static final double shooterOneCircle = 4096;
		public static final double shooterRegularRunningSpeed = 0;

		//shooter & sensor gap
		public static final double shooterWaitSecond = 1;

	//limelight
	public static final double kpAim = -0.1;
	public static final double minAimCommend = 0.05;
	public static final double kTxTorrance = 0;
	public static final double kTyTorrance = 0;
	public static final double kpDistance = -0.1;

	//spinner(postion control)
	public static final int robotToSensorDis = 0;


	//Joystick
		//intake
		public static final int bBallIntake = 0;
		public static final int bIntakeBasket = 0;
		public static final int bUpperBallIntake = 0;

		//spinner
		public static final int bRotationControl = 0;
		public static final int bPositionControl = 0;
		public static final int bSpinnerSolenoid = 0;
	
		//shooter
		public static final int bShoot = 0;

		//drivetrain
		public static final int forwardAxis = 0;
		public static final int turnAxis = 0;
		public static final int bShiftGear = 0;





}
