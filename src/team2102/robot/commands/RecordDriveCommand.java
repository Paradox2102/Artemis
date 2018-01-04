package team2102.robot.commands;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import team2102.lib.TrajectoryRecorder;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class RecordDriveCommand extends LoggingCommandBase {

	private static final double k_recordInterval = 0.010;

	private TrajectoryRecorder m_leftRecorder;
	private TrajectoryRecorder m_rightRecorder;

	public RecordDriveCommand() {

		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.driveSubsystem.setVoltageControl();
		Robot.driveSubsystem.resetPosition();

		// TODO: Clear already recorded trajectories instead of re-allocating

		m_leftRecorder = new TrajectoryRecorder(Robot.driveSubsystem.getLeftMotor(), k_recordInterval);
		m_leftRecorder.startRecording();

		m_rightRecorder = new TrajectoryRecorder(Robot.driveSubsystem.getRightMotor(), k_recordInterval);
		m_rightRecorder.startRecording();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		final double x = Robot.oi.getX();
		final double y = Robot.oi.getY();

		Robot.driveSubsystem.set((y + x) * Robot.k_maxVoltage, (y - x) * Robot.k_maxVoltage);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();

		Robot.driveSubsystem.stop();

		m_leftRecorder.stopRecording();
		m_rightRecorder.stopRecording();

		try {
			final FileOutputStream outFile = new FileOutputStream("/home/lvuser/trajectories");
			final ObjectOutputStream outStream = new ObjectOutputStream(outFile);
			outStream.writeObject(m_leftRecorder.getRecordedTrajectory());
			outStream.writeObject(m_rightRecorder.getRecordedTrajectory());
			outStream.close();
			outFile.close();
		} catch (final IOException e) {
			e.printStackTrace();
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
