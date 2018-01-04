package team2102.robot;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.lib.PiCamera.TargetRegion;
import team2102.robot.commands.MeasureDistanceCommand;
import team2102.robot.commands.PistonReleaseCommand;
import team2102.robot.commands.SelectBoilerAutoCommand;
import team2102.robot.commands.SelectBoilerAutoFrontCommand;
import team2102.robot.commands.SelectGearBoilerCommand;
import team2102.robot.commands.SelectGearFeederCommand;
import team2102.robot.commands.SelectGearMiddleAutoCommand;
import team2102.robot.commands.TestLIDARCommand;
import team2102.robot.commands.TimedDriveCommand;
import team2102.robot.subsystems.CameraSubsystem;
import team2102.robot.subsystems.ClimberSubsystem;
import team2102.robot.subsystems.DriveSubsystem;
import team2102.robot.subsystems.FeederSubsystem;
import team2102.robot.subsystems.GearSubsystem;
import team2102.robot.subsystems.IntakeSubsystem;
import team2102.robot.subsystems.ShooterSubsystem;

/* IMPORTANT: For competition
 * * Change shifting and intake release solenoids
 * * Distance from front of lidar mount to shooter plate behind the indexer: 16.75in
 * * Distance from front of lidar mount to back of intake plate: 4.5in
 */

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final double k_maxVoltage = 12.0;

	public static DriveSubsystem driveSubsystem;
	public static ShooterSubsystem shooterSubsystem;
	public static FeederSubsystem feederSubsystem;
	public static IntakeSubsystem intakeSubsystem;
	public static GearSubsystem gearSubsystem;
	public static ClimberSubsystem climberSubsystem;
	public static CameraSubsystem cameraSubsystem;

	public static Navigator navigator;
	public static OI oi;

	private Command m_autoCmd;
	private SendableChooser<Command> m_chooser;

	public Robot() {
		try {
			Logger.getLogger("").addHandler(new FileHandler());
		} catch (SecurityException | IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		SmartDashboard.putData(new TestLIDARCommand());

		driveSubsystem = new DriveSubsystem();
		shooterSubsystem = new ShooterSubsystem();
		feederSubsystem = new FeederSubsystem();
		intakeSubsystem = new IntakeSubsystem();
		gearSubsystem = new GearSubsystem();
		climberSubsystem = new ClimberSubsystem();
		cameraSubsystem = new CameraSubsystem();

		navigator = new Navigator();
		oi = new OI();

		m_chooser = new SendableChooser<>();
		m_chooser.addDefault("Do Nothing", new PistonReleaseCommand());
		//m_chooser.addObject("Boiler Auto (side hopper)", new SelectBoilerAutoCommand());
		m_chooser.addObject("Boiler Auto (front hopper)", new SelectBoilerAutoFrontCommand());
		m_chooser.addObject("Test drive straight", new MeasureDistanceCommand(1500, 100000));
		m_chooser.addObject("Test max speed", new TimedDriveCommand(5.0, 1.00));
		m_chooser.addObject("Gear Auto (boiler)", new SelectGearBoilerCommand());
		m_chooser.addObject("Gear Auto (feeder)", new SelectGearFeederCommand());
		m_chooser.addObject("Gear Auto (middle)", new SelectGearMiddleAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);

		Robot.navigator.startMeasuringDistance();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		Robot.driveSubsystem.setCoastMode();
	}

	@Override
	public void disabledPeriodic() {
		SmartDashboard.putNumber("Gyro angle", Robot.navigator.getAngle());

		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		Robot.driveSubsystem.shiftDown();

		Robot.driveSubsystem.setBrakeMode();

		m_autoCmd = m_chooser.getSelected();
		if (m_autoCmd != null) {
			m_autoCmd.start();
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		SmartDashboard.putNumber("Left Speed", Robot.driveSubsystem.getLeftSpeed());
		SmartDashboard.putNumber("Right Speed", Robot.driveSubsystem.getRightSpeed());

		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		Robot.driveSubsystem.setBrakeMode();

		(new PistonReleaseCommand()).start();

		if (m_autoCmd != null) {
			m_autoCmd.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Robot.intakeSubsystem._getSpeed();

		Robot.driveSubsystem.operatorOverride();
		Scheduler.getInstance().run();

		TargetRegion boilerTarget = Robot.navigator.getBoilerTarget();
		if (boilerTarget != null) {
			SmartDashboard.putNumber("Target Center X", boilerTarget.m_centerTop);
			SmartDashboard.putNumber("Target Center Y", boilerTarget.m_bounds.m_top);
		} else {
			SmartDashboard.putNumber("Target Center X", -1);
			SmartDashboard.putNumber("Target Center Y", -1);
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
