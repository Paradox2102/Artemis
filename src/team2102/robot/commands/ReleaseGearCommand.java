package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class ReleaseGearCommand extends LoggingCommandBase {

	public ReleaseGearCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.gearSubsystem);
		setTimeout(0.3333);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.gearSubsystem.releaseGear();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();

		Robot.gearSubsystem.retractGear();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();
		end();
	}
}
