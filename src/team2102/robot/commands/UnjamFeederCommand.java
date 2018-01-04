package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class UnjamFeederCommand extends LoggingCommandBase {

	public UnjamFeederCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.feederSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.feederSubsystem.startUnjamming();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();

		Robot.feederSubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();

		end();
	}
}
