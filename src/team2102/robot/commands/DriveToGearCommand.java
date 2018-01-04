package team2102.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robotCore.Logger;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;
import team2102.robot.RobotMap;

/**
 *
 */
public class DriveToGearCommand extends LoggingCommandBase {

	private final double m_kP = 10;
	private final double m_kPa = 50;
	private final double m_kMaxAngle = 6;
	private final double m_distance;
	private final double m_speed;
	private double m_targetAngle;
	private final boolean m_enableTargetAngle;
	private double m_leftStartingPosition;

	public DriveToGearCommand(double distance, double speed, boolean enableTargetAngle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveSubsystem);

		m_distance = distance;
		m_speed = speed;
		m_enableTargetAngle = enableTargetAngle;

	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.driveSubsystem.setSpeedControl();
		Robot.driveSubsystem.set(0, 0);
		m_leftStartingPosition = Robot.driveSubsystem.getLeftPosition();
		Logger.SetLogFile("DriveToGear", "DriveToGear");
		Logger.Log("DriveToGear", 3, "label,enc dist,dist,center,target center");
		m_targetAngle = Robot.navigator.getGearAngle();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Gyro angle", Robot.navigator.getAngle());
		if (Robot.navigator.getGearCenter() == 0) {
			Robot.driveSubsystem.set(0, 0);

		} else {
			double deltaCenterTarget = Robot.navigator.getDeltaCenterLine();
			double deltaAngle = Robot.navigator.getAngleDiff(m_targetAngle);

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

			Robot.driveSubsystem.set(m_speed - (deltaCenterTarget * m_kP) - deltaAngle * m_kPa,
					m_speed + (deltaCenterTarget * m_kP) + deltaAngle * m_kPa);
		}
		double currentLeftPosition = Robot.driveSubsystem.getLeftPosition() - m_leftStartingPosition;
		Logger.Log("DriveToGear", 3,
				String.format(",%.1f,%.1f,%.1f, %.1f", 110 - currentLeftPosition / RobotMap.k_inchesToTicks,
						Robot.navigator.getDistanceToTarget(Robot.navigator.getGearHeight()),
						Robot.navigator.getGearCenter(), Robot.navigator.getTargetCenterLine(
								Robot.navigator.getDistanceToTarget(Robot.navigator.getGearHeight()))));
		// encoder distance, distance, centerLine, and targetCenterLine
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		// Logger.Log("DriveToGearCommand", 0, "isFinished()");

		if (!Robot.navigator.isGearTargetValid()) {
			SmartDashboard.putBoolean("is gear target valid", !Robot.navigator.isGearTargetValid());
			return true;
		}
		
		double currentDistance = Robot.navigator.getDistanceToTarget(Robot.navigator.getGearHeight());

		SmartDashboard.putNumber("Current Distance From Target", currentDistance);
		SmartDashboard.putNumber("Gear Height", Robot.navigator.getGearHeight());

		if (currentDistance <= m_distance) {
			return true;
		} else {
			return false;
		}
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();

		Robot.driveSubsystem.setVoltageControl();
		Robot.driveSubsystem.set(0, 0);
		Logger.CloseLogFile("DriveToGear");
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();
		end();
	}
}
