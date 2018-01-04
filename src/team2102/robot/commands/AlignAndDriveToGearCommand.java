package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import team2102.robot.RobotMap;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class AlignAndDriveToGearCommand extends CommandGroup {
	public AlignAndDriveToGearCommand(double turnAngle, double targetAngle, double distance) {
		addSequential(new TurnCommand(turnAngle, RobotMap.k_gearTurnKp, RobotMap.k_gearTurnSpeed));
		addSequential(new DriveCurveCommand(-1200, turnAngle, RobotMap.k_gearDriveKp, DistanceSide.LEFT,
				Math.abs(distance) * RobotMap.k_inchesToTicks, true));
		addSequential(new TurnCommand(targetAngle, RobotMap.k_gearTurnKp, RobotMap.k_gearTurnSpeed));
		addSequential(new GearPlacerCommand(RobotMap.k_driveToGearSpeed, false));

	}
}