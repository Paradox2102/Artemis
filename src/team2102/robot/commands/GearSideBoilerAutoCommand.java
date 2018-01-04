package team2102.robot.commands;

import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;
import team2102.robot.subsystems.DriveSubsystem.DriveSide;

/**
 *
 */
public class GearSideBoilerAutoCommand extends CommandGroup {

	public GearSideBoilerAutoCommand(Alliance alliance) {
		addSequential(new ResetAngleCommand(-180));

		addSequential(new PistonReleaseCommand());

		addSequential(new DriveCurveCommand(-2000, 180, 500.0 / 45.0, DistanceSide.LEFT, 52500, true));
		addSequential(new TurnCommand((alliance == Alliance.Red) ? 120 : -120, 1000.0 / 45.0, 1000));
		addSequential(new DriveCurveCommand(-2000, 120, 500.0 / 45.0, DistanceSide.LEFT, 40000, true));
		addSequential(new WiggleGearCommand());

		addSequential(new WaitCommand(2.0));
		addSequential(new DriveDistanceCommand(2500, 2500, DistanceSide.LEFT, 20000));

		addParallel(new SpinShooterCommand(3175));
		addSequential(new TurnToBoilerCommand(10, 800, DriveSide.BOTH));
		addSequential(new DriveToBoilerCommand(150, 2500, 17.0));
		addSequential(new ShootCommand());
	}
}
