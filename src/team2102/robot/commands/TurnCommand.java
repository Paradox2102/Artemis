package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class TurnCommand extends LoggingCommandBase {

	private final double m_targetAngle;
	private final double m_kP;
	private final double m_minOutput;

	private int m_direction;

	public TurnCommand(double targetAngle, double kP, double minOutput) {

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);

		m_targetAngle = targetAngle;
		m_kP = kP;
		m_minOutput = minOutput;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		m_direction = Robot.navigator.getAngleDiff(m_targetAngle) > 0 ? 1 : -1;

		Robot.driveSubsystem.shiftDown();
		Robot.driveSubsystem.setSpeedControl();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double speed = m_kP * Math.abs(Robot.navigator.getAngleDiff(m_targetAngle));
		if (speed < m_minOutput) {
			speed = m_minOutput;
		}

		Robot.driveSubsystem.set(m_direction * speed, -m_direction * speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		double diff = Robot.navigator.getAngleDiff(m_targetAngle);
		if (m_direction > 0) {
			return diff <= 0;
		} else {
			return diff >= 0;
		}
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
