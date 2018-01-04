package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import team2102.robot.RobotMap;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class GearPlacerCommand extends CommandGroup {

	public GearPlacerCommand(double speed, boolean releaseGear) {
		addSequential(new DriveToGearCommand(RobotMap.k_driveToGearDistance, speed, true));
		addSequential(new TimedDriveCommand(0.35, -0.5));
		if (releaseGear) {
			addSequential(new DropOffGearAndDriveAwayCommand());
		}
		// addSequential(new WaitCommand(1.5));
	}
}
