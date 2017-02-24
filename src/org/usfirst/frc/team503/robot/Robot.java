
package org.usfirst.frc.team503.robot;

import org.usfirst.frc.team503.robot.commands.ArcadeDriveCommand;
import org.usfirst.frc.team503.robot.commands.GyroCommand;
import org.usfirst.frc.team503.robot.subsystems.GetAngleNeededToTurn;
import org.usfirst.frc.team503.robot.utils.LVMaxSonarEZ4;
import org.usfirst.frc.team503.robot.utils.Logger;

import edu.wpi.first.wpilibj.IterativeRobot;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static RobotHardwarePracticeBot bot = null;
	//private static double startTime;
	public static LVMaxSonarEZ4 leftUltrasonic = new LVMaxSonarEZ4(bot.leftUltrasonicPort);
	public static LVMaxSonarEZ4 rightUltrasonic = new LVMaxSonarEZ4(bot.rightUltrasonicPort);
	
	/**
	 * RobotInit - Fires when robot is powered-up 
	 */
	@Override
	public void robotInit() {
        NetworkTable.globalDeleteAll(); //Removes unused garbage from SmartDashboard
        NetworkTable.initialize();      //Initialize Network Tables
		
        bot = new RobotHardwarePracticeBot();
		bot.initialize();
		bot.logSmartDashboard();         /*put name of selected bot on smartdashboard */
		
		//SteamworksChooser.getInstance().autonInitChooser();
		RobotState.getInstance().setState(RobotState.State.DISABLED);
	}
 
	/**
	 * disabledInit - fires when disabled mode is pressed.
	 */
	@Override
	public void disabledInit() {
		//DrivetrainSubsystem.getInstance().stopTrapezoidControl();    	
		//DrivetrainSubsystem.getInstance().percentVoltageMode();
		//TurretSubsystem.getInstance().getThread().stopTurret();
		SmartDashboard.putNumber("Turn by", GetAngleNeededToTurn.getInstance().AngleNeededToTurn());
		SmartDashboard.putNumber("Turn P", Robot.bot.GYRO_P);
		SmartDashboard.putNumber("Turn I", Robot.bot.GYRO_I);
		SmartDashboard.putNumber("Turn D", Robot.bot.GYRO_D);

		RobotState.getInstance().setState(RobotState.State.DISABLED);
	}

	/**
	 * disabledPeriodic - fires when disabled mode is pressed.
	 */
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * autonomousInit - fires when Auto mode is selected  
	 */
	@Override
	public void autonomousInit() {
		//SteamworksChooser.getInstance().executeAuton();
		//startTime = Timer.getFPGATimestamp();
		SmartDashboard.putNumber("LeftUltrasonicDistance", leftUltrasonic.getDistance());
		SmartDashboard.putNumber("RightUltrasonicDistance", rightUltrasonic.getDistance());
		//SmartDashboard.putNumber("difference", GetAngleNeededToTurn.getInstance().Difference());
		SmartDashboard.putNumber("Angle Needed To Turn 1", Math.toDegrees(Math.atan(((leftUltrasonic.getDistance() - rightUltrasonic.getDistance())/13.7))));
		SmartDashboard.putNumber("Angle Needed To Turn 2", GetAngleNeededToTurn.getInstance().AngleNeededToTurn());
		RobotState.getInstance().setState(RobotState.State.AUTON);
		//(new CenterPegCenterStart()).start();
		//(new SelfCorrectCommand()).start();
		//(new GyroCommand(GetAngleNeededToTurn.getInstance().AngleNeededToTurn())).start();
		(new GyroCommand(90)).start();
		//s(new TurnUsingGyroCommand()).start();
	}

	/**
	 * AutonomousPeriodic - This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//DrivetrainSubsystem.getInstance()trainSubsystem.getInstance().populateLog(startTime);
		Scheduler.getInstance().run();
		//SmartDashboard.putNumber("LeftUltrasonicDistance", leftUltrasonic.getDistance());
		//SmartDashboard.putNumber("RightUltrasonicDistance", rightUltrasonic.getDistance());
		//SmartDashboard.putNumber("difference", GetAngleNeededToTurn.getInstance().Difference());
		//SmartDashboard.putNumber("Angle Needed To Turn", Math.toDegrees(Math.atan(((leftUltrasonic.getDistance() - rightUltrasonic.getDistance())/15))));
		if (!Robot.bot.getName().equals("ProgrammingBot")){
			//SmartDashboard.putNumber("Shooter Motor Speed", ShooterSubsystem.getInstance().getSpeed());
			//DeflectorSubsystem.getInstance().sendDashboardData();
			//TurretSubsystem.getInstance().sendDashboardData();
		}
	}

	/**
	 * teleopInit - first with Telop is selected 
	 */
	@Override
	public void teleopInit() {
		// Ensure the autonomous commands are cancelled if not finished 
		//if (autonomousCommand != null)
		//	autonomousCommand.cancel();
		//DrivetrainSubsystem.getInstance().stopTrapezoidControl();    	
		//DrivetrainSubsystem.getInstance().percentVoltageMode();
    	//DrivetrainSubsystem.getInstance().resetEncoders();
		OI.initialize();
		RobotState.getInstance().setState(RobotState.State.TELEOP);
	   // startTime = Timer.getFPGATimestamp();
	    
	    //TurretSubsystem.getInstance().getThread().startTurret();
	    //start commands that use joysticks and dpads manually from Robot.java
    	(new ArcadeDriveCommand()).start();
    	
    	if (!Robot.bot.getName().equals("ProgrammingBot")){
    		//(new TeleopTurretCommand()).start();
        	//(new TeleopDeflectorCommand()).start();
        	//(new CameraTurnCommand()).start();
    	}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
	//	DrivetrainSubsystem.getInstance().populateLog(startTime);		
		Scheduler.getInstance().run();
		//DrivetrainSubsystem.getInstance().populateLog(startTime);
		//DrivetrainSubsystem.getInstance().printEncCounts();
		
		SmartDashboard.putNumber("LeftUltrasonicVoltage", leftUltrasonic.getVoltage());
		SmartDashboard.putNumber("RightUltrasonicVoltage", rightUltrasonic.getVoltage());
		SmartDashboard.putNumber("Left Ultrasonic Distance", leftUltrasonic.getDistance());
		SmartDashboard.putNumber("Right Ultrasonic Distance", rightUltrasonic.getDistance());
		
		SmartDashboard.putNumber("Angle Needed To Turn TeleOp", Math.toDegrees(Math.atan(((leftUltrasonic.getDistance() - rightUltrasonic.getDistance())/bot.kDistanceBetweenSensors))));
		if (!Robot.bot.getName().equals("ProgrammingBot")){
			//SmartDashboard.putNumber("Shooter Motor Speed", ShooterSubsystem.getInstance().getSpeed());
			//SmartDashboard.putNumber("left operator trigger", OI.getOperatorLeftTrigger());
			//DeflectorSubsystem.getInstance().sendDashboardData();
			//TurretSubsystem.getInstance().sendDashboardData();
		}
		
		
	}
	
	/**
	 * This function is called to initialize the test mode 
	 */
	@Override
	public void testInit(){
		Logger.froglog("In Roborio Test Mode...initiating Power On Self Test (POST) Diagnostics ...");
		RobotState.getInstance().setState(RobotState.State.TEST);
	}
	
	/**
	 * testPeriodic - This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
	}
	
}
