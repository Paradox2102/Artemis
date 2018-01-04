package team2102.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	public static final int k_leftMotor = 0;
	public static final int k_leftFollowerMotor = 1;
	public static final int k_rightMotor = 2;
	public static final int k_rightFollowerMotor = 3;

	public static final int k_shooterMotor = 4;
	public static final int k_indexerMotor = 5;

	public static final int k_intakeMotor = 6;

	public static final int k_climberMotor = 7;
	public static final int k_climberFollowerMotor = 8;

	public static final int k_agitatorMotor = 9;

	public static final int k_shifterSolenoid = 0;
	public static final int k_releaseSolenoid = 1;
	public static final int k_gearSolenoid = 2;

	public static final int k_cameraServo = 0;

	public static final int k_driverCamera = 0;

	public static final double k_inchesToTicks = 34000.0 / 57.0;

	public static final double k_driveToGearDistance = 48.0;
	public static final double k_finalDistanceToGearTicks = 7 * k_inchesToTicks;
	public static final double k_autoDriveAwayFromPegDistance = 10 * k_inchesToTicks;

	public static final double k_redAutoDriveLength = 74;
	public static final double k_blueAutoDriveLength = 69;

	public static final double k_autoGearLineupSpeed = -2500;
	public static final double k_driveToGearSpeed = -2500;
	public static final double k_finalDriveToGearSpeed = -1500;
	public static final double k_teleopGearLineupSpeed = -2500;
	public static final double k_autoDriveAwayFromPegSpeed = 2500;
	public static final double k_gearTurnSpeed = 1200;
	public static final double k_gearDriveKp = 50;
	public static final double k_gearTurnKp = 1700.0 / 45.0;

}
