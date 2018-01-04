package team2102.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.robot.Robot;
import team2102.robot.RobotMap;

/**
 *
 */
public class FeederSubsystem extends Subsystem {

	private final CANTalon m_indexer = new CANTalon(RobotMap.k_indexerMotor);
	private final CANTalon m_agitator = new CANTalon(RobotMap.k_agitatorMotor);

	public FeederSubsystem() {
		SmartDashboard.putData(this);

		LiveWindow.addActuator(getName(), "Indexer Motor", m_indexer);
		LiveWindow.addActuator(getName(), "Agitator Motor", m_agitator);

		m_indexer.changeControlMode(TalonControlMode.Voltage);
		m_indexer.set(0.0);

		m_agitator.changeControlMode(TalonControlMode.Voltage);
		m_agitator.set(0.0);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void startFeeding() {
		m_indexer.set(Robot.k_maxVoltage * 0.60);
		m_agitator.set(Robot.k_maxVoltage * 0.70);
	}

	public void startUnjamming() {
		m_indexer.set(Robot.k_maxVoltage * -1.0);
		m_agitator.set(Robot.k_maxVoltage * -1.0);
	}

	public void stop() {
		m_indexer.set(0.0);
		m_agitator.set(0.0);
	}
}
