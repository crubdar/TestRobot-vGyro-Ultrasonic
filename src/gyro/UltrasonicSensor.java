package ultrasonic;

import edu.wpi.first.wpilibj.AnalogInput;
/**=============================================================
 * +--------------------+--------------+
 * | UltrasonicSensor.java | FRC Team 503 |
 * +--------------------+--------------+
 * 
 * This is the code for the ultrasonic sensors of type LVMaxSonarEZ4.
 *=============================================================*/
public class UltrasonicSensor {
	private final double VOLTS_PER_INCH = 0.009766;
	private AnalogInput analogSensor;
	 
	public UltrasonicSensor(int port){                          
		analogSensor = new AnalogInput(port);                      
	}                                                                  
	//This next method has not been tested yet                                    
	//Double check please!
	public double getDistance()
	{														
		return analogSensor.getVoltage()/VOLTS_PER_INCH;	
	}
	
}
