package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class ArcadeDriveCommand extends LoggingCommandBase {

	public ArcadeDriveCommand() {

		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.driveSubsystem.setVoltageControl();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double x = Robot.oi.getX();
		double y = Robot.oi.getY();

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
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();

		end();
	}
}
