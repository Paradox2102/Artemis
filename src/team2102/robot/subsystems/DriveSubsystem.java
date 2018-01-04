package team2102.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.robot.Robot;
import team2102.robot.RobotMap;
import team2102.robot.commands.ArcadeDriveCommand;
import team2102.robot.commands.RetractGearCommand;

/**
 *
 */
public class DriveSubsystem extends Subsystem {

	private final CANTalon m_leftMotor = new CANTalon(RobotMap.k_leftMotor);
	private final CANTalon m_leftFollowerMotor = new CANTalon(RobotMap.k_leftFollowerMotor);
	private final CANTalon m_rightMotor = new CANTalon(RobotMap.k_rightMotor);
	private final CANTalon m_rightFollowerMotor = new CANTalon(RobotMap.k_rightFollowerMotor);

	private final Solenoid m_shifter = new Solenoid(RobotMap.k_shifterSolenoid);

	public enum DistanceSide {
		LEFT, RIGHT
	}

	public enum DriveSide {
		LEFT, RIGHT, BOTH
	}

	public DriveSubsystem() {
		SmartDashboard.putData(this);

		LiveWindow.addActuator(getName(), "Left Motor", m_leftMotor);
		LiveWindow.addActuator(getName(), "Left Follower Motor", m_leftFollowerMotor);
		LiveWindow.addActuator(getName(), "Right Motor", m_rightMotor);
		LiveWindow.addActuator(getName(), "Right Follower Motor", m_rightFollowerMotor);

		LiveWindow.addActuator(getName(), "Shifter", m_shifter);

		m_leftMotor.setInverted(true);
		m_leftMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		// m_leftMotor.reverseOutput(true); // needed for motion profile
		// m_leftMotor.reverseSensor(true); // see above

		m_leftFollowerMotor.changeControlMode(TalonControlMode.Follower);
		m_leftFollowerMotor.set(m_leftMotor.getDeviceID());

		m_rightMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder);

		m_rightFollowerMotor.changeControlMode(TalonControlMode.Follower);
		m_rightFollowerMotor.set(m_rightMotor.getDeviceID());
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new ArcadeDriveCommand());
	}

	public void setBrakeMode() {
		m_leftMotor.enableBrakeMode(true);
		m_leftFollowerMotor.enableBrakeMode(true);
		m_rightMotor.enableBrakeMode(true);
		m_rightFollowerMotor.enableBrakeMode(true);
	}

	public void setCoastMode() {
		m_leftMotor.enableBrakeMode(false);
		m_leftFollowerMotor.enableBrakeMode(false);
		m_rightMotor.enableBrakeMode(false);
		m_rightFollowerMotor.enableBrakeMode(false);
	}

	public void setVoltageControl() {
		m_leftMotor.changeControlMode(TalonControlMode.Voltage);
		m_rightMotor.changeControlMode(TalonControlMode.Voltage);
	}

	public void setSpeedControl() {
		m_leftMotor.changeControlMode(TalonControlMode.Speed);
		m_rightMotor.changeControlMode(TalonControlMode.Speed);
	}

	public void set(double left, double right) {
		m_leftMotor.set(left);
		m_rightMotor.set(right);
	}

	public void stop() {
		m_leftMotor.changeControlMode(TalonControlMode.PercentVbus);
		m_leftMotor.set(0);

		m_rightMotor.changeControlMode(TalonControlMode.PercentVbus);
		m_rightMotor.set(0);
	}

	@Deprecated
	public void resetPosition() {
		m_leftMotor.setPosition(0);
		m_rightMotor.setPosition(0);
	}

	public double getLeftPosition() {
		return m_leftMotor.getPosition();
	}

	public double getRightPosition() {
		return m_rightMotor.getPosition();
	}

	public double getLeftSpeed() {
		return m_leftMotor.getSpeed();
	}

	public double getRightSpeed() {
		return m_rightMotor.getSpeed();
	}

	public void shiftUp() {
		SmartDashboard.putString("Shifter", "Switching H");
		m_shifter.set(true);
	}

	public void shiftDown() {
		SmartDashboard.putString("Shifter", "Switching LOW");
		m_shifter.set(false);
	}

	@Deprecated
	public CANTalon getLeftMotor() {
		return m_leftMotor;
	}

	@Deprecated
	public CANTalon getRightMotor() {
		return m_rightMotor;
	}

	public void operatorOverride() {
		if (Robot.oi.outsideDeadzone() && getCurrentCommand() != getDefaultCommand()) {
			getDefaultCommand().start();
			(new RetractGearCommand()).start(); // TODO: temp fix
		}
	}
}
