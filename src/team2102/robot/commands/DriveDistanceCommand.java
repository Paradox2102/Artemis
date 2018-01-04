package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class DriveDistanceCommand extends LoggingCommandBase {

	private final double m_leftSpeed;
	private final double m_rightSpeed;
	private final DistanceSide m_distanceSide;
	private final double m_distance;

	private double m_startingPosition;

	public DriveDistanceCommand(double leftSpeed, double rightSpeed, DistanceSide distanceSide, double distance) {

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);

		m_leftSpeed = leftSpeed;
		m_rightSpeed = rightSpeed;
		m_distanceSide = distanceSide;
		m_distance = distance;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.driveSubsystem.shiftDown();
		Robot.driveSubsystem.setSpeedControl();

		switch (m_distanceSide) {
		case LEFT:
			m_startingPosition = Robot.driveSubsystem.getLeftPosition();
			break;

		case RIGHT:
			m_startingPosition = Robot.driveSubsystem.getRightPosition();
			break;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.driveSubsystem.set(m_leftSpeed, m_rightSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		switch (m_distanceSide) {
		case LEFT:
			return Math.abs(Robot.driveSubsystem.getLeftPosition() - m_startingPosition) >= m_distance;

		case RIGHT:
			return Math.abs(Robot.driveSubsystem.getRightPosition() - m_startingPosition) >= m_distance;
		}

		return true;
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
