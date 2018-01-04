package team2102.robot.commands;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;

/**
 *
 */
public class GearAutoMiddleCommand extends CommandGroup {

	public GearAutoMiddleCommand(Alliance alliance) {
		addSequential(new ResetAngleCommand(-180));

		// Straight distance: 46500
		// addSequential(new DriveDistanceCommand(-2000, -2000,
		// DistanceSide.LEFT, 39000));
		addSequential(new PistonReleaseCommand());

		addSequential(new DriveCurveCommand(-2000, 180, 100.0, DistanceSide.LEFT, 39000, true));
		addSequential(new WiggleGearCommand());

		addSequential(new WaitCommand(2.0));
		addSequential(new DriveDistanceCommand(2500, 2500, DistanceSide.LEFT, 20000));

		addParallel(new SpinShooterCommand(3175));
		addSequential(new TurnCommand((alliance == Alliance.Red) ? 110 : -110, 2500.0 / 90.0, 1000));
		addSequential(new DriveToBoilerCommand(150, 2500, 17.0));
		addSequential(new ShootCommand());
	}
}
