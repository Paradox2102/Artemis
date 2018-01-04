package team2102.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class DriveCurveCommand extends LoggingCommandBase {

	private final double m_speed;
	private double m_targetAngle;
	private final double m_kP;
	private final DistanceSide m_distanceSide;
	private final double m_distance;
	private final boolean m_stopAfter;
	private final boolean m_automaticTargetAngle;

	private double m_startingPosition;

	public DriveCurveCommand(double speed, double targetAngle, double kP, DistanceSide distanceSide, double distance,
			boolean stopAfter) {

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);

		m_speed = speed;
		m_targetAngle = targetAngle;
		m_kP = kP;
		m_distanceSide = distanceSide;
		m_distance = distance;
		m_stopAfter = stopAfter;
		m_automaticTargetAngle = false;
	}

	public DriveCurveCommand(double speed, double kP, DistanceSide distanceSide, double distance, boolean stopAfter) {

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);
		m_targetAngle = 0;
		m_speed = speed;
		m_kP = kP;
		m_distanceSide = distanceSide;
		m_distance = distance;
		m_stopAfter = stopAfter;
		m_automaticTargetAngle = true;
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

		if (m_automaticTargetAngle) {
			m_targetAngle = Robot.navigator.getGearAngle();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Angle off from " + m_targetAngle, Robot.navigator.getAngleDiff(m_targetAngle));
		double adjustment = Robot.navigator.getAngleDiff(m_targetAngle) * m_kP;
		Robot.driveSubsystem.set(m_speed + adjustment, m_speed - adjustment);
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

		if (m_stopAfter) {
			Robot.driveSubsystem.stop();
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();
		end();
	}
}
