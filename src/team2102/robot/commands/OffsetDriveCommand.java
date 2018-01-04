package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class OffsetDriveCommand extends LoggingCommandBase {

	private final int m_offset;
	private final int m_speed;

	public OffsetDriveCommand(int offset, int speed) {

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);

		m_offset = offset;
		m_speed = speed;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.driveSubsystem.shiftDown();
		Robot.driveSubsystem.setSpeedControl();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.driveSubsystem.set(m_speed, m_speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.navigator.getDistance() < m_offset;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();

		Robot.driveSubsystem.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();
		end();
	}
}
