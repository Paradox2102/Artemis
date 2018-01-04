package team2102.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import team2102.robot.commands.AutoSpinShooterCommand;
import team2102.robot.commands.CalibrateGearCommand;
import team2102.robot.commands.ClimbCommand;
import team2102.robot.commands.DriveToBoilerCommand;
import team2102.robot.commands.DropOffGearAndDriveAwayCommand;
import team2102.robot.commands.IntakeCommand;
import team2102.robot.commands.ReleaseGearCommand;
import team2102.robot.commands.ShiftCommand;
import team2102.robot.commands.ShootCommand;
import team2102.robot.commands.SpinShooterCommand;
import team2102.robot.commands.TeleopPlaceGearCommand;
import team2102.robot.commands.ToggleDriveCommand;
import team2102.robot.commands.TurnCommand;
import team2102.robot.commands.TurnToBoilerCommand;
import team2102.robot.commands.UnjamFeederCommand;
import team2102.robot.commands.UnjamIntakeCommand;
import team2102.robot.subsystems.DriveSubsystem.DistanceSide;
import team2102.robot.subsystems.DriveSubsystem.DriveSide;
import team2102.robot.triggers.POVChangeTrigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private static final double k_deadzone = 0.5;
	private static final double k_dzMagnitudeSquared = 2 * k_deadzone * k_deadzone;

	private final Joystick m_driveStick = new Joystick(0);
	private final Joystick m_otherStick = new Joystick(1);

	private final Button shiftBtn = new JoystickButton(m_driveStick, 5);

	private final Button spinShooterBtn = new JoystickButton(m_otherStick, 2);
	private final Button shootBtn = new JoystickButton(m_otherStick, 1);
	private final Button unjamBtn = new JoystickButton(m_otherStick, 3);

	private final Button unjamIntakeBtn = new JoystickButton(m_otherStick, 5);

	private final Button intakeBtn = new JoystickButton(m_otherStick, 4);

	private final Button climberBtn = new JoystickButton(m_otherStick, 6);

	private final Button turnBoilerBtn = new JoystickButton(m_otherStick, 10);
	private final Button driveBoilerBtn = new JoystickButton(m_otherStick, 12);

	private final Button toggleDriveDirectionBtn = new JoystickButton(m_driveStick, 7);

	private final Button gearDriveBtn = new JoystickButton(m_otherStick, 9);
	private final Button gearReleaseBtn = new JoystickButton(m_otherStick, 7);
	private final Button gearReleaseAndDriveAwayBtn = new JoystickButton(m_otherStick, 11);
	// private final Button climbGoalBtn = new JoystickButton(m_driveStick, 11);

	private final Button testBtn = new JoystickButton(m_driveStick, 3);

	private boolean m_driveReversed = false;

	public OI() {
		for (int i = 0; i < 8; ++i) {
			int angle = i * 45;
			(new POVChangeTrigger(m_driveStick, angle)).whenActive(new TurnCommand(angle, 1000.0 / 45.0, 400));
		}
//new AutoSpinShooterCommand()
		spinShooterBtn.toggleWhenPressed(new SpinShooterCommand(3200));
		// new SpinShooterCommand(3200));
		shootBtn.whileHeld(new ShootCommand());
		unjamBtn.whileHeld(new UnjamFeederCommand());

		unjamIntakeBtn.whileHeld(new UnjamIntakeCommand());

		shiftBtn.toggleWhenPressed(new ShiftCommand());
		intakeBtn.whileHeld(new IntakeCommand());
		climberBtn.whileHeld(new ClimbCommand());

		turnBoilerBtn.whenPressed(new TurnToBoilerCommand(10, 800, DriveSide.BOTH));
		driveBoilerBtn.whenPressed(new DriveToBoilerCommand(135, 800, 8.0));// distance
																			// 125,
																			// normally

		toggleDriveDirectionBtn.whenPressed(new ToggleDriveCommand());

		gearDriveBtn.whenPressed(new TeleopPlaceGearCommand());
		gearReleaseBtn.whenPressed(new ReleaseGearCommand());
		gearReleaseAndDriveAwayBtn.whenPressed(new DropOffGearAndDriveAwayCommand());
		// climbGoalBtn.whenPressed(new MoveCameraCommand(CameraPosition.DOWN));

		testBtn.whenPressed(new CalibrateGearCommand(800, 180, 80, DistanceSide.LEFT, 80 * RobotMap.k_inchesToTicks, true));

	}

	public void toggleReversed() {
		m_driveReversed = !m_driveReversed;
	}

	public double getX() {
		double x = m_driveStick.getX();
		return x * x * x;
	}

	public double getY() {
		double y = (m_driveReversed ? 1 : -1) * m_driveStick.getY();
		return y * y * y;
	}

	public double getAdjustment() {
		double adj = -m_otherStick.getY();
		return adj * adj * adj;
	}

	public double getThrottle() {
		return m_otherStick.getThrottle();
	}

	public boolean outsideDeadzone() {
		double x = m_driveStick.getX();
		double y = m_driveStick.getY();
		return x * x + y * y > k_dzMagnitudeSquared;
	}
}
