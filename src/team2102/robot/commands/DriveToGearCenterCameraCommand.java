package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class DriveToGearCenterCameraCommand extends LoggingCommandBase {

	private final boolean m_enableTargetAngle;
	private double m_targetAngle;
	private final double m_kP = 8;
	private final double m_kPa = 40;
	private final double m_speed;
	private final double m_distance;
	private final double m_kMaxAngle = 45;

	public DriveToGearCenterCameraCommand(double distance, double speed, boolean enableTargetAngle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveSubsystem);

		m_speed = speed;
		m_distance = distance;
		m_enableTargetAngle = enableTargetAngle;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.driveSubsystem.setSpeedControl();
		Robot.driveSubsystem.set(0, 0);
		double currentAngle = Robot.navigator.getAngle();

		if (35 <= currentAngle && currentAngle <= 85) {
			m_targetAngle = 60;
		} else if (-25 <= currentAngle && currentAngle <= 25) {
			m_targetAngle = 0;
		} else if (-85 <= currentAngle && currentAngle <= -35) {
			m_targetAngle = -60;
		}
	}

	public double distanceToTarget(double gearHeight) {
		return (1036 * Math.pow(gearHeight, -0.6063));
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double targetCenterLine = Robot.navigator.getFrameCenterX();
		double centerLine = Robot.navigator.getGearCenter();
		// double gearHeight = Robot.navigator.getGearHeight();

		double deltaCenterTarget = targetCenterLine - centerLine;
		double deltaAngle = m_targetAngle - Robot.navigator.getAngle();

		if (deltaAngle > m_kMaxAngle) {
			deltaAngle = m_kMaxAngle;
		} else if (-deltaAngle > m_kMaxAngle) {
			deltaAngle = -m_kMaxAngle;
		}

		if (m_enableTargetAngle == true) {
			// deltaAngle = 0;
		} else {
			deltaAngle = 0;
		}

		if (centerLine == 0) {
			Robot.driveSubsystem.set(0, 0);
		} else {

			Robot.driveSubsystem.set(m_speed - (deltaCenterTarget * m_kP) - deltaAngle * m_kPa,
					m_speed + (deltaCenterTarget * m_kP) + deltaAngle * m_kPa);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (!Robot.navigator.isGearTargetValid()) {
			return true;
		}
		
		if (distanceToTarget(Robot.navigator.getGearHeight()) <= m_distance) {
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();

		Robot.driveSubsystem.set(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();
		end();
	}
}
