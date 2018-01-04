package team2102.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import team2102.robot.RobotMap;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;
import team2102.robot.subsystems.DriveSubsystem.DriveSide;

/**
 *
 */
public class DriveAndShootFromPegCommand extends CommandGroup {

	public DriveAndShootFromPegCommand() {
		addParallel(new SpinShooterCommand(3200));
		addSequential(new TurnToBoilerCommand(10, 800, DriveSide.BOTH));
		addParallel(new AutoSpinShooterCommand());
		addSequential(new DriveToBoilerCommand(145, 2500, 12.0));
		addSequential(new ShootCommand());
	}
}
