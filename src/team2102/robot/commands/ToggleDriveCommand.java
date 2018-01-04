package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class ToggleDriveCommand extends LoggingCommandBase {

	public ToggleDriveCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.oi.toggleReversed();
	}

}
