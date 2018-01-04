package team2102.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;

/**
 *
 */
public class AutoSpinShooterCommand extends LoggingCommandBase {

	private static final double k_adjustmentRange = 100;

	public AutoSpinShooterCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.shooterSubsystem);

		SmartDashboard.putNumber("Target shooter speed", 0.0);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.shooterSubsystem.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		super.execute();

		double speed = 4.694 * Robot.navigator.getDistance() + /* 2465 */2465;
		Robot.shooterSubsystem.spinUp(speed + Robot.oi.getAdjustment() * k_adjustmentRange);// SmartDashboard.getNumber("Target
																							// shooter
																							// speed",
																							// 0.0));
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

		Robot.shooterSubsystem.stopAndDisable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();

		end();
	}
}
