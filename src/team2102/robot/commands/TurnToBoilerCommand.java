package team2102.robot.commands;

import team2102.lib.PiCamera.TargetRegion;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;
import team2102.robot.subsystems.DriveSubsystem.DriveSide;

/**
 *
 */
public class TurnToBoilerCommand extends LoggingCommandBase {

	private TargetRegion m_lastRegion;

	private final double m_kP;
	private final double m_minOutput;
	private final DriveSide m_driveSide;

	private int m_direction;

	public TurnToBoilerCommand(double kP, double minOutput, DriveSide driveSide) {

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);

		m_kP = kP;
		m_minOutput = minOutput;
		m_driveSide = driveSide;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.driveSubsystem.shiftDown();
		Robot.driveSubsystem.setSpeedControl();

		cacheTargetRegion();
		if (seeingTarget()) {
			m_direction = getTargetError() > 0 ? 1 : -1;
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		cacheTargetRegion();
		if (seeingTarget()) {
			double speed = Math.abs(getTargetError() * m_kP);

			if (speed < m_minOutput) {
				speed = m_minOutput;
			}

			switch (m_driveSide) {
			case LEFT:
				Robot.driveSubsystem.set(m_direction * speed, 0);
				break;

			case RIGHT:
				Robot.driveSubsystem.set(0, -m_direction * speed);
				break;

			case BOTH:
				Robot.driveSubsystem.set(m_direction * speed, -m_direction * speed);
				break;
			}
		} else {
			Robot.driveSubsystem.set(0, 0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (seeingTarget()) {
			if (m_direction > 0) {
				return getTargetError() <= 0;
			} else {
				return getTargetError() >= 0;
			}
		} else {
			return false;
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

	private void cacheTargetRegion() {
		// Logger.Log("TurnToBoilerCommand", -1, "cacheTargetRegion()");

		m_lastRegion = Robot.navigator.getBoilerTarget();
	}

	private boolean seeingTarget() {
		// Logger.Log("TurnToBoilerCommand", -1, "seeingTarget()");

		return m_lastRegion != null;
	}

	private int getTargetError() {
		// Logger.Log("TurnToBoilerCommand", -1, "getTargetError()");

		return m_lastRegion.m_centerTop - Robot.navigator.getFrameCenterX();
	}
}
