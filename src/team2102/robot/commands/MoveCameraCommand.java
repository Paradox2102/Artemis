package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;
import team2102.robot.subsystems.CameraSubsystem.CameraPosition;

/**
 *
 */
public class MoveCameraCommand extends LoggingCommandBase {

	private final CameraPosition m_pos;

	public MoveCameraCommand(CameraPosition pos) {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		m_pos = pos;

	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		super.initialize();
		Robot.cameraSubsystem.setPosition(m_pos);
	}

}
