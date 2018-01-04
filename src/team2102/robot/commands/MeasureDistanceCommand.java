package team2102.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class MeasureDistanceCommand extends LoggingCommandBase {

	private final double m_speed;
	private final double m_distance;

	private double m_startingPositionLeft;
	private double m_startingPositionRight;

	public MeasureDistanceCommand(double speed, double distance) {

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);

		m_speed = speed;
		m_distance = distance;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.driveSubsystem.shiftDown();
		Robot.driveSubsystem.setSpeedControl();

		m_startingPositionLeft = Robot.driveSubsystem.getLeftPosition();
		m_startingPositionRight = Robot.driveSubsystem.getRightPosition();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.driveSubsystem.set(m_speed, m_speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.driveSubsystem.getLeftPosition() - m_startingPositionLeft) >= m_distance;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();

		Robot.driveSubsystem.stop();

		SmartDashboard.putNumber("Left ending distance",
				Robot.driveSubsystem.getLeftPosition() - m_startingPositionLeft);
		SmartDashboard.putNumber("Right ending distance",
				Robot.driveSubsystem.getRightPosition() - m_startingPositionRight);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();
		end();
	}
}
