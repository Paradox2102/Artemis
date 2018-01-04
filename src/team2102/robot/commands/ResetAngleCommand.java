package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class ResetAngleCommand extends LoggingCommandBase {

	private final double m_startingAngle;

	public ResetAngleCommand(double startingAngle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);

		m_startingAngle = startingAngle;

	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		super.initialize();

		Robot.navigator.resetAngle(m_startingAngle);
	}
}
