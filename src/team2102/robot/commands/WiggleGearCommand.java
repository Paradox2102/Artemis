package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class WiggleGearCommand extends CommandGroup {

	public WiggleGearCommand() {
		addSequential(new DriveDistanceCommand(0, -2000, DistanceSide.RIGHT, 3000));
		addSequential(new DriveDistanceCommand(-1000, 0, DistanceSide.LEFT, 3000));
		addSequential(new DriveDistanceCommand(0, -2000, DistanceSide.RIGHT, 3000));
		addSequential(new DriveDistanceCommand(-1000, 0, DistanceSide.LEFT, 3000));
	}
}
