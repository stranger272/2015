
package org.usfirst.frc.team4215.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DigitalInput;


/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */
public class Robot extends SampleRobot { 
	public static void main(String[] args) {
		
	}
   
	// Objects defined for drive train.
	Joystick leftStick = new Joystick(0);
	Joystick rightStick = new Joystick(1);
	
	Talon frontLeft = new Talon(0);
	Talon backLeft = new Talon(1);
	Talon backRight = new Talon(2);
	Talon frontRight = new Talon(3);
	
	Talon elevator = new Talon(4);
	Talon rackPinion = new Talon(5);
	DigitalInput limitSwitch;
	DigitalInput limitSwitch2;
	
	double tankLeft;
	double tankRight;
	double strafe;
	double MAXELEVAT=.75;
	double MINELEVAT=.15;
	
	private double MAXINPUT = .75;
    private double MININPUT = .15;
	
	
    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
        
    }
    
    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        
        while (isOperatorControl() && isEnabled()) {
            drivingMethod();
        }
    }
    
    public void drivingMethod(){
    	tankLeft = leftStick.getY();
    	tankRight = rightStick.getY();
    	if (rightStick.getX() >= MININPUT) {			// This line gives the right joystick strafing priority if both joysticks are moved in the x-direction.
    		strafe = rightStick.getX();
    	}
    	else {
    		strafe = .5*leftStick.getX();
    	}
    	
    	
    	// This moves the wheels on the left side of the robot.
    	//tankLeft = Math.floor(tankLeft*20)/20;
    	if (tankLeft <= Math.abs(MININPUT)) {
    		tankLeft = 0;
    	}
    	else if (tankLeft >= MAXINPUT) {
    		tankLeft = MAXINPUT;
    	}
    	else if (tankLeft <= -MAXINPUT) {
    		tankLeft = -MAXINPUT;
    	}
    	
    	// This moves the wheels on the right side of the robot.
    	//tankRight = Math.floor(tankRight*20)/20;
    	if (tankRight <= Math.abs(MININPUT)) {
    		tankRight = MININPUT;
    	}
    	else if (tankRight >= MAXINPUT) {
    		tankRight = MAXINPUT;
    	}
    	else if (tankRight <= -MAXINPUT) {
    		tankRight = -MAXINPUT;
    	}
    	
    	// This controls the strafing.
    	if (strafe <= Math.abs(MININPUT)) {
    		strafe = 0;
    	}
    	else if (strafe >= MAXINPUT) {
    		strafe = MAXINPUT;
    	}
    	else if (strafe <= -MAXINPUT) {
    		strafe = -MAXINPUT;
    	}
    	
    	
    	frontLeft.set(-tankLeft + strafe);
    	backLeft.set(-tankLeft - strafe);
    	backRight.set(tankRight - strafe);
    	frontRight.set(tankRight + strafe);
    }

  public void elevateMethod() {
    	limitSwitch = new DigitalInput(6);
    	limitSwitch2 = new DigitalInput(7);
    	elevation=ThirdStick.getY();
    	
    	if(elevation>0 && elevation<=.1 && limitSwitch.get())  {
    	Elevator.set(MINELEVAT);
    	}
    	else if(elevation>.1 && elevation<=1 && limitSwitch.get())  {   
    	Elevator.set(MAXELEVAT);
    	}
    	else if(elevation<0 && elevation>=-.1 && limitSwitch2.get())  {
    	Elevator.set(-1*MINELEVAT);
    	}
    	else if(elevation<-.1 && elevation<=-1 && limitSwitch2.get())  {   
    	Elevator.set(-1*MAXELEVAT);
    	}
    }

    /**
     * Runs during test mode
     */
    public void test() {
    }
}
