package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PushAgainstBoilerCommand extends CommandGroup {

	public PushAgainstBoilerCommand() {
		addSequential(new DriveToBoilerCommand(30, 800, 8.0));
		addSequential(new TimedDriveCommand(1.0, 0.3));
	}
}
