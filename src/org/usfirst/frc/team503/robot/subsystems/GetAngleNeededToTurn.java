package org.usfirst.frc.team503.robot.subsystems;

import org.usfirst.frc.team503.robot.Robot;
import org.usfirst.frc.team503.robot.utils.LVMaxSonarEZ4;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GetAngleNeededToTurn extends Subsystem {
	private static LVMaxSonarEZ4 leftUltrasonic = Robot.leftUltrasonic;
	private static LVMaxSonarEZ4 rightUltrasonic =  Robot.rightUltrasonic;

	//private static double kDifferenceBetweenSensorReadings; 
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
private static GetAngleNeededToTurn instance = new GetAngleNeededToTurn();                                         

public static GetAngleNeededToTurn getInstance(){                                          
	return instance;
}


public double AngleNeededToTurn(){
	return Math.toDegrees(Math.atan(((leftUltrasonic.getDistance() - rightUltrasonic.getDistance())/Robot.bot.kDistanceBetweenSensors)));
}


	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}