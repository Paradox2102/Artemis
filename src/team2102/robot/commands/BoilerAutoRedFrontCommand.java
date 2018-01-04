package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;
import team2102.robot.subsystems.DriveSubsystem.DriveSide;

/**
 *
 */
public class BoilerAutoRedFrontCommand extends CommandGroup {

	public BoilerAutoRedFrontCommand() {
		addSequential(new ResetAngleCommand(0));

		addSequential(new PistonReleaseCommand());
		addSequential(new DriveCurveCommand(2500, 0, 200.0 / 15.0, DistanceSide.LEFT, 31000, true));
		addSequential(new DriveDistanceCommand(2500, 500, DistanceSide.LEFT, 39000));
		addSequential(new TimedDriveCommand(0.7, 0.5));
		addSequential(new WaitCommand(1.0));
		addParallel(new SpinShooterCommand(3200));
		addSequential(new DriveDistanceCommand(-500, -2000, DistanceSide.RIGHT, 30000));
		addSequential(new TurnToBoilerCommand(12, 1000, DriveSide.BOTH)); // (10, 800, BOTH)
		addParallel(new AutoSpinShooterCommand());
		addSequential(new DriveToBoilerCommand(140, 2500, 10.0)); // (140, 1500, 10.0)
		addSequential(new ShootCommand());
	}
}
