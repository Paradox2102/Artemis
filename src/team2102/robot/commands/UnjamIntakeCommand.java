package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class UnjamIntakeCommand extends LoggingCommandBase {

	public UnjamIntakeCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.intakeSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.intakeSubsystem.outtake();
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

		Robot.intakeSubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();

		end();
	}
}
