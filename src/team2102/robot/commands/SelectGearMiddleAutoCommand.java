package team2102.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.Command;
import team2102.robot.LoggingCommandBase;

/**
 *
 */
public class SelectGearMiddleAutoCommand extends LoggingCommandBase {

	private Command m_cmd;

	public SelectGearMiddleAutoCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Alliance alliance = DriverStation.getInstance().getAlliance();

		if (alliance == Alliance.Invalid) {
			throw new IllegalStateException("Alliance is invalid");
		}

		m_cmd = new MiddleGearPlacerCommand(alliance);
		m_cmd.start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return !m_cmd.isRunning();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();

		m_cmd.cancel();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();

		end();
	}

}
