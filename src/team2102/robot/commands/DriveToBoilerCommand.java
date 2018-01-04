package team2102.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.lib.PiCamera.TargetRegion;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Navigator;
import team2102.robot.Robot;

/**
 *
 */
public class DriveToBoilerCommand extends LoggingCommandBase {

	private final int m_offset;
	private final int m_speed;
	private final double m_kP;

	public DriveToBoilerCommand(int offset, int speed, double kP) {

		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);

		requires(Robot.driveSubsystem);

		m_offset = offset;
		m_speed = speed;
		m_kP = kP;
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
		TargetRegion data = Robot.navigator.getBoilerTarget();
		double adjustment;

		if (data != null) {
			int error = data.m_centerTop - Robot.navigator.getFrameCenterX();
			adjustment = error * m_kP;
		} else {
			adjustment = 0;
		}

		SmartDashboard.putNumber("Adjustment", adjustment);

		Robot.driveSubsystem.set(m_speed + adjustment, m_speed - adjustment);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		double distance = Robot.navigator.getDistance();
		SmartDashboard.putNumber("Distance", distance);
		System.out.println(distance);
		
		if (distance < Robot.navigator.k_minDistance) {
			return distance == Robot.navigator.k_invalidDistance;
		} else {
			return distance <= m_offset;
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
