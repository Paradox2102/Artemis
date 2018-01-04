package team2102.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class TestLIDARCommand extends LoggingCommandBase {

	public TestLIDARCommand() {
		// Use requires() here to declare subsystem dependencies
		setRunWhenDisabled(true);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double distance = Robot.navigator.getDistance();
		SmartDashboard.putNumber("Distance", distance);
		System.out.println(distance);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
