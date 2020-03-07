/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.commands.accurateAutoAim;
import frc.robot.commands.conveyBall;
import frc.robot.commands.dummyAutoAim;
import frc.robot.commands.intakeBall;
import frc.robot.commands.positionControl;
import frc.robot.commands.rotationControl;
import frc.robot.commands.shooterPrep;
import frc.robot.commands.turnToAngle;
import frc.robot.subsystems.BallIntake;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Spinner;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private Joystick moveStick = new Joystick(Pin.moveStickPort);
  private Joystick functionStick = new Joystick(Pin.functionStickPort);

  private Drivetrain mDrivetrain = new Drivetrain();
  private Shooter mShooter = new Shooter();
  private BallIntake mBallIntake = new BallIntake();
  private Spinner mSpinner = new Spinner();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    mDrivetrain
        .setDefaultCommand(new RunCommand(() -> mDrivetrain.velocityDrive(moveStick.getRawAxis(Constants.forwardAxis),
            moveStick.getRawAxis(Constants.turnAxis)), mDrivetrain));

    mShooter
        .setDefaultCommand(new RunCommand(() -> mShooter.shootBall(Constants.shooterRegularRunningSpeed), mShooter));

    //mLimelight.setDefaultCommand(new dummyAutoAim());
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Spinner
    new JoystickButton(functionStick, Constants.bPositionControl)
        .whenPressed(new positionControl());
    new JoystickButton(functionStick, Constants.bRotationControl)
        .whenPressed(new rotationControl());
    new JoystickButton(functionStick, Constants.bSpinnerSolenoid)
        .whenPressed(new InstantCommand(mSpinner::spinnerState, mSpinner));

    // Ball Intake
    new JoystickButton(functionStick, Constants.bBallIntake)
        .and(new JoystickButton(functionStick,Constants.bUpperBallIntake))
          .whenActive(new intakeBall(1))
          .whenInactive(new intakeBall(3));

    new JoystickButton(functionStick, Constants.bBallIntake)
        .whenActive(new intakeBall(2))
        .whenInactive(new intakeBall(3));
          
    new JoystickButton(functionStick, Constants.bIntakeBasket)
        .whenPressed(new InstantCommand(mBallIntake::basketStateChange, mBallIntake));

    // shooter
    new JoystickButton(moveStick, Constants.bShoot)
        .whenPressed(new SequentialCommandGroup(new accurateAutoAim().alongWith(new dummyAutoAim()),
                                                new SequentialCommandGroup(new conveyBall(),
                                                                           new WaitCommand(Constants.shooterWaitSecond))
                                                                               .deadlineWith(new shooterPrep())));

    // drivetrain
    new JoystickButton(moveStick, Constants.bShiftGear)
        .whenPressed(new InstantCommand(mDrivetrain::shiftGear, mDrivetrain));
    if (moveStick.getPOV() != 0){
        new POVButton(moveStick, moveStick.getPOV())
            .whenPressed(new turnToAngle(Utility.targetAngleModify(moveStick.getPOV(), 
                                                                   mDrivetrain.getCurrentAngle()), 
                                         mDrivetrain));
    }
    
  }

}
