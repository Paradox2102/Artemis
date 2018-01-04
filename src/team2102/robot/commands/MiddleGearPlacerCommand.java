package team2102.robot.commands;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import team2102.robot.RobotMap;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class MiddleGearPlacerCommand extends CommandGroup {

	public MiddleGearPlacerCommand(Alliance alliance) {
		addSequential(new ResetAngleCommand(-180));
		addSequential(new GearPlacerCommand(RobotMap.k_driveToGearSpeed, true));
		addSequential(new DriveDistanceCommand(RobotMap.k_autoDriveAwayFromPegSpeed,
				RobotMap.k_autoDriveAwayFromPegSpeed, DistanceSide.LEFT, RobotMap.k_autoDriveAwayFromPegDistance));
		addParallel(new SpinShooterCommand(3200));
		addSequential(new TurnCommand((alliance == Alliance.Red) ? 110 : -110, 2000.0 / 45.0, 1000));
		addParallel(new AutoSpinShooterCommand());
		addSequential(new DriveToBoilerCommand(145, 2500, 8.0));
		addSequential(new ShootCommand());
		// addSequential(new DriveToGearCommand(48, -1000, true));
		// addSequential(new DriveCurveCommand(-1000, 10, DistanceSide.LEFT, 12
		// * RobotMap.k_inchesToTicks, true));
	}
}
