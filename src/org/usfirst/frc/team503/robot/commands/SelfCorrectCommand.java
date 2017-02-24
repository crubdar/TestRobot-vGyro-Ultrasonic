package org.usfirst.frc.team503.robot.commands;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.subsystems.DrivetrainSubsystem;
import org.usfirst.frc.team503.robot.utils.LVMaxSonarEZ4;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * @author Owner
 *
 */
public class SelfCorrectCommand extends Command {
	
	private double diff;
	
	private double currentLeftSpeed;
	private double currentRightSpeed;
	
	private static final double kStoppingDistance = 16;
	
	private static final double kPosStoppingTolerence = kStoppingDistance +0.5;
	private static final double kNegStoppingTolerence = kStoppingDistance -0.5;
	
	private static final double kPosTolerence = 0.125;
	private static final double kNegTolerence = -0.125;
	
	private LVMaxSonarEZ4 leftUltrasonic;
	private LVMaxSonarEZ4 rightUltrasonic;
	
	private DrivetrainSubsystem drivetrain;
	
    public SelfCorrectCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftUltrasonic = Robot.leftUltrasonic;
    	rightUltrasonic = Robot.rightUltrasonic;
    	
    	drivetrain = DrivetrainSubsystem.getInstance();
    	
    	currentLeftSpeed = -0.2;
    	currentRightSpeed = -0.2;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Gets the difference between the left and right sensors
    	diff = leftUltrasonic.getDistance() - rightUltrasonic.getDistance();
    	
    	//If one side is within the tolerance, slow it down
    	if(leftUltrasonic.getDistance() <= kPosStoppingTolerence && leftUltrasonic.getDistance() >= kNegStoppingTolerence)
    		currentLeftSpeed = 0.0;
    	else if(rightUltrasonic.getDistance() <= kPosStoppingTolerence && rightUltrasonic .getDistance() >= kNegStoppingTolerence)
    		currentRightSpeed = 0.0;
    	else
    	{
    		currentLeftSpeed = -0.2;
    		currentRightSpeed = -0.2;
    	}
    	
    	
    	if(leftUltrasonic.getDistance() > kPosStoppingTolerence || rightUltrasonic.getDistance() > kPosStoppingTolerence) {
    		if(diff > kPosTolerence) {
    			drivetrain.tankDrive(currentLeftSpeed - 0.1, currentRightSpeed, false);
//    			drivetrain.tankDrive(-0.3, 0.0, false);
    		} 
    		else if (diff < kNegTolerence) {
    			drivetrain.tankDrive(currentLeftSpeed, currentRightSpeed - 0.1, false);
//    			drivetrain.tankDrive(0.0, -0.3, false);
    		} 
    		else if (diff <= kPosTolerence && diff >= kNegTolerence) {
    			drivetrain.tankDrive(currentLeftSpeed, currentRightSpeed, false);
//    			drivetrain.tankDrive(-0.2, -0.2, false);
    		}
    	}
    	else if(leftUltrasonic.getDistance() < kNegStoppingTolerence || rightUltrasonic.getDistance() < kNegStoppingTolerence)
    	{
    		if(diff > kPosTolerence) {
    			drivetrain.tankDrive(-currentLeftSpeed + 0.1, -currentRightSpeed, false);
//    			drivetrain.tankDrive(-0.3, 0.0, false);
    		} 
    		else if (diff < kNegTolerence) {
    			drivetrain.tankDrive(-currentLeftSpeed, -currentRightSpeed + 0.1, false);
//    			drivetrain.tankDrive(0.0, -0.3, false);
    		} 
    		else if (diff <= kPosTolerence && diff >= kNegTolerence) {
    			drivetrain.tankDrive(-currentLeftSpeed, -currentRightSpeed, false);
//    			drivetrain.tankDrive(-0.2, -0.2, false);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (leftUltrasonic.getDistance() <= kPosStoppingTolerence && leftUltrasonic.getDistance() >= kNegStoppingTolerence)
        	&& (rightUltrasonic.getDistance() <= kPosStoppingTolerence && rightUltrasonic.getDistance() >= kNegStoppingTolerence);
    }

    // Called once after isFinished returns true
    /**
     * J
     */
    protected void end() {
    	drivetrain.tankDrive(0.0, 0.0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}