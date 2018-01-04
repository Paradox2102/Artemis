package team2102.robot.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import team2102.lib.MotionProfileExample;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class DrivePathCommand extends LoggingCommandBase {

	private final CANTalon m_leftMotor = Robot.driveSubsystem.getLeftMotor();
	private final CANTalon m_rightMotor = Robot.driveSubsystem.getRightMotor();

	private MotionProfileExample m_leftController;
	private MotionProfileExample m_rightController;

	public DrivePathCommand() {

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	@SuppressWarnings("unchecked")
	protected void initialize() {
		super.initialize();

		List<double[]> leftTrajectory = null; // TODO: Not crash if file reading
												// fails
		List<double[]> rightTrajectory = null; // TODO: see above

		try {
			final FileInputStream infile = new FileInputStream("/home/lvuser/trajectories");
			final ObjectInputStream instream = new ObjectInputStream(infile);
			leftTrajectory = (List<double[]>) instream.readObject();
			rightTrajectory = (List<double[]>) instream.readObject();
			instream.close();
			infile.close();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}

		m_leftController = new MotionProfileExample(m_leftMotor, leftTrajectory);
		m_rightController = new MotionProfileExample(m_rightMotor, rightTrajectory);

		m_leftController.reset();
		m_rightController.reset();

		m_leftMotor.changeControlMode(TalonControlMode.MotionProfile);
		m_rightMotor.changeControlMode(TalonControlMode.MotionProfile);

		m_leftMotor.set(m_leftController.getSetValue().value);
		m_rightMotor.set(m_rightController.getSetValue().value);

		m_leftController.startMotionProfile();
		m_rightController.startMotionProfile();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		m_leftController.control();
		m_rightController.control();

		m_leftMotor.set(m_leftController.getSetValue().value);
		m_rightMotor.set(m_rightController.getSetValue().value);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return m_leftController.getSetValue() == CANTalon.SetValueMotionProfile.Hold
				&& m_rightController.getSetValue() == CANTalon.SetValueMotionProfile.Hold;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();

		m_leftController.reset();
		m_rightController.reset();

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
