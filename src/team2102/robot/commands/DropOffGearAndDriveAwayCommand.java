package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import team2102.robot.RobotMap;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class DropOffGearAndDriveAwayCommand extends CommandGroup {

    public DropOffGearAndDriveAwayCommand() {
    	addSequential(new ExtendGearCommand());
		addSequential(new WaitCommand(0.3333));
		addSequential(new DriveDistanceCommand(RobotMap.k_autoDriveAwayFromPegSpeed,
			RobotMap.k_autoDriveAwayFromPegSpeed, DistanceSide.LEFT, RobotMap.k_autoDriveAwayFromPegDistance));
		addSequential(new RetractGearCommand());
    }
}
