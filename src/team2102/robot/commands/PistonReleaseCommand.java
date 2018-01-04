package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import team2102.robot.Robot;

/**
 *
 */
public class PistonReleaseCommand extends InstantCommand {

	public PistonReleaseCommand() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.intakeSubsystem);

	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		super.initialize();
		Robot.intakeSubsystem.release();
	}

}
