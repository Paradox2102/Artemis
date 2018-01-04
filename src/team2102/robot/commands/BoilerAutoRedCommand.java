package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;
import team2102.robot.subsystems.DriveSubsystem.DriveSide;

/**
 *
 */
public class BoilerAutoRedCommand extends CommandGroup {

	public BoilerAutoRedCommand() {

		addSequential(new ResetAngleCommand(-45));

		addSequential(new DriveCurveCommand(2000, 0,
				700.0 / 45.0 /* practice: 1600.0 */, DistanceSide.LEFT,
				31000 /* calculated: 27935 */, true));
		addSequential(new DriveCurveCommand(1500, -10, 700.0 / 15.0, DistanceSide.LEFT, 8000, true));
		addSequential(new PistonReleaseCommand());
		addSequential(new TurnCommand(15, 1000.0 / 45.0, 1000));
		addSequential(new WaitCommand(1.0));

		addParallel(new SpinShooterCommand(3100));
		addSequential(new DriveDistanceCommand(-2000, -2000, DistanceSide.LEFT, 19000));
		addSequential(new TurnCommand(170, 1500.0 / 45.0, 1000));
		addSequential(new TurnToBoilerCommand(10.0, 700, DriveSide.BOTH));

		addSequential(new ShootCommand());
	}
}
