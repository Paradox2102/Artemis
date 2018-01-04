package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import team2102.robot.Robot;

/**
 *
 */
public class TimedDriveCommand extends TimedCommand {

	private final double m_voltage;

	public TimedDriveCommand(double timeout, double power) {
		super(timeout);

		m_voltage = power * Robot.k_maxVoltage;

		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveSubsystem);

	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.driveSubsystem.shiftDown();
		Robot.driveSubsystem.setVoltageControl();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.driveSubsystem.set(m_voltage, m_voltage);
	}

	// Called once after timeout
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
