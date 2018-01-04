package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team2102.robot.RobotMap;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class SideGearBoilerRedCenterCameraCommand extends CommandGroup {

	public SideGearBoilerRedCenterCameraCommand() {

		addSequential(new DriveCurveCommand(-2000, 0, 40, DistanceSide.LEFT, 80 * RobotMap.k_inchesToTicks, true));
		addSequential(new TurnCommand(-60, 1000.0 / 45.0, 800));
		addSequential(new DriveToGearCommand(48, -1500, true));
	}
}
