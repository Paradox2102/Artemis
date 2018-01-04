package team2102.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robotCore.Logger;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class CalibrateGearCommand extends LoggingCommandBase {

	private final double m_speed;
	private final double m_targetAngle;
	private final double m_kP;
	private final DistanceSide m_distanceSide;
	private final double m_distance;
	private final boolean m_stopAfter;

	private double m_startingPosition;

	public CalibrateGearCommand(double speed, double targetAngle, double kP, DistanceSide distanceSide, double distance,
			boolean stopAfter) {

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);

		m_speed = speed;
		m_targetAngle = targetAngle;
		m_kP = kP;
		m_distanceSide = distanceSide;
		m_distance = distance;
		m_stopAfter = stopAfter;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.navigator.resetAngle(180);

		Robot.driveSubsystem.resetPosition();
		Logger.SetLogFile("GearData", "GearData");

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

	// Called repeatedly when this Command is schReduled to run
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Gyro angle", Robot.navigator.getAngle());

		SmartDashboard.putNumber("Left Encoder", Robot.driveSubsystem.getLeftPosition());
		SmartDashboard.putNumber("Right Encoder", Robot.driveSubsystem.getRightPosition());

		SmartDashboard.putNumber("Angle off from " + m_targetAngle, Robot.navigator.getAngleDiff(m_targetAngle));
		double adjustment = Robot.navigator.getAngleDiff(m_targetAngle) * m_kP;
		Robot.driveSubsystem.set(m_speed + adjustment, m_speed - adjustment);
		double distance = 48.0 - (Robot.driveSubsystem.getLeftPosition() * 57.0 / 34000.0);// 51
																							// is
																							// normally
																							// 48.0
		Logger.Log("GearData", 3, String.format(",%.1f,%.1f,%.1f", Robot.navigator.getGearHeight(), distance,
				Robot.navigator.getGearCenter()));

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
		Logger.CloseLogFile("GearData");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();
		end();
	}
}
