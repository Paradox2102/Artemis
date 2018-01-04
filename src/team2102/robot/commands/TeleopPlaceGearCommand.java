package team2102.robot.commands;

import team2102.robot.LoggingCommandBase;
import team2102.robot.Robot;
import team2102.robot.RobotMap;

/**
 *
 */
public class TeleopPlaceGearCommand extends LoggingCommandBase {

	private final double k_speed = -800;
	private final double k_maximumAngle = 5;

	public TeleopPlaceGearCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		super.initialize();

		Robot.driveSubsystem.setSpeedControl();
		Robot.driveSubsystem.set(0, 0);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.navigator.getDeltaCenterLine() >= 0) {
			Robot.driveSubsystem.set(k_speed, -k_speed);
		} else {
			Robot.driveSubsystem.set(-k_speed, k_speed);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		double deltaCenterLine = Robot.navigator.getDeltaCenterLine();

		if (deltaCenterLine <= 10 && deltaCenterLine >= -10) {
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		super.end();

		Robot.driveSubsystem.setVoltageControl();
		Robot.driveSubsystem.set(0, 0);
		double targetAngle = Robot.navigator.getGearAngle();
		
		double deltaAngle = Robot.navigator.getAngleDiff(targetAngle);
		double distanceToTarget = Robot.navigator.getDistanceToTarget(Robot.navigator.getGearHeight());
		double distance = distanceToTarget * Math.sin((deltaAngle * Math.PI) / 180);

		if (Math.abs(deltaAngle) <= k_maximumAngle) {
			(new GearPlacerCommand(RobotMap.k_teleopGearLineupSpeed, false)).start();
		} else {
			if (distance >= 0) {
				(new AlignAndDriveToGearCommand((targetAngle - 90), targetAngle, distance)).start();
			} else {
				(new AlignAndDriveToGearCommand((targetAngle + 90), targetAngle, distance)).start();
			}
		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		super.interrupted();

		Robot.driveSubsystem.setVoltageControl();
		Robot.driveSubsystem.set(0, 0);
	}
}
