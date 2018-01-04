package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;
import team2102.robot.subsystems.DriveSubsystem.DriveSide;

/**
 *
 */
public class BoilerAutoBlueCommand extends CommandGroup {

	public BoilerAutoBlueCommand() {

		addSequential(new ResetAngleCommand(-135));

		addSequential(new DriveCurveCommand(-2000, 180,
				700.0 / 45.0 /* practice: 1600.0 */, DistanceSide.LEFT,
				32000 /* practice: 30000; calculated: 27935 */, true));
		addSequential(new DriveCurveCommand(-1500, -170, 700.0 / 15.0, DistanceSide.LEFT,
				7000 /* calculated: 4640 */, true));

		addParallel(new SpinShooterCommand(3200));
		addSequential(new PistonReleaseCommand());
		addSequential(new TurnCommand(170, 1000.0 / 45.0, 800));
		addSequential(new WaitCommand(0.5));
		addSequential(new DriveDistanceCommand(600, 600, DistanceSide.LEFT, 12000));

		addSequential(new TurnToBoilerCommand(10.0, 800, DriveSide.LEFT));
		addSequential(new ShootCommand());
	}
}
