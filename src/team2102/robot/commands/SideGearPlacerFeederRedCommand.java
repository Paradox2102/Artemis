package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team2102.robot.RobotMap;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class SideGearPlacerFeederRedCommand extends CommandGroup {

	public SideGearPlacerFeederRedCommand() {
		addSequential(new ResetAngleCommand(180));

		addSequential(new DriveCurveCommand(RobotMap.k_autoGearLineupSpeed, RobotMap.k_gearDriveKp, DistanceSide.LEFT,
				RobotMap.k_redAutoDriveLength * RobotMap.k_inchesToTicks, true));
		addSequential(new TurnCommand(-120, RobotMap.k_gearTurnKp, RobotMap.k_gearTurnSpeed));
		addSequential(new GearPlacerCommand(RobotMap.k_driveToGearSpeed, true));
		// addSequential(new DriveToGearCommand(48, -1000, true));
		// addSequential(new DriveCurveCommand(-1000, 10, DistanceSide.LEFT, 13
		// * RobotMap.k_inchesToTicks, true));
	}
}
