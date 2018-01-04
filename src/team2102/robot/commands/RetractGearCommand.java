package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class RetractGearCommand extends LoggingCommandBase {

    public RetractGearCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	super.initialize();
    	
    	Robot.gearSubsystem.retractGear();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	super.end();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	super.interrupted();
    	end();
    }
}
