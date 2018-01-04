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
public class ClimberSubsystem extends Subsystem {

	private final CANTalon m_motor = new CANTalon(RobotMap.k_climberMotor);
	private final CANTalon m_followerMotor = new CANTalon(RobotMap.k_climberFollowerMotor);

	public ClimberSubsystem() {
		SmartDashboard.putData(this);

		LiveWindow.addActuator(getName(), m_motor.getDescription(), m_motor);
		LiveWindow.addActuator(getName(), m_followerMotor.getDescription(), m_followerMotor);

		m_motor.changeControlMode(TalonControlMode.Voltage);
		m_motor.set(0.0);

		m_followerMotor.changeControlMode(TalonControlMode.Follower);
		m_followerMotor.set(m_motor.getDeviceID());
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void climb(double power) {
		SmartDashboard.putNumber("Climber power", power);
		if (power < 0) {
			power = 0;
		}
		m_motor.set(power * Robot.k_maxVoltage);
	}

	public void stop() {
		m_motor.set(0.0);
	}
}
