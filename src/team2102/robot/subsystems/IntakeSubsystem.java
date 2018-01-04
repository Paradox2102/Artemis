package team2102.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.robot.Robot;
import team2102.robot.RobotMap;

/**
 *
 */
public class IntakeSubsystem extends Subsystem {

	private final CANTalon m_motor = new CANTalon(RobotMap.k_intakeMotor);
	private final Solenoid m_releasePiston = new Solenoid(RobotMap.k_releaseSolenoid);

	public IntakeSubsystem() {
		SmartDashboard.putData(this);

		LiveWindow.addActuator(getName(), "Intake Motor", m_motor);
		LiveWindow.addActuator(getName(), "Release Piston", m_releasePiston);

		m_motor.changeControlMode(TalonControlMode.Voltage);
		m_motor.set(0.0);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void intake() {
		m_motor.set(Robot.k_maxVoltage);
	}

	public void outtake() {
		m_motor.set(-Robot.k_maxVoltage);
	}

	public void stop() {
		m_motor.set(0.0);
	}

	public void release() {
		m_releasePiston.set(true);
	}

	public void _getSpeed() {
		SmartDashboard.putNumber("test intake get speed", m_motor.getSpeed());
	}
}
