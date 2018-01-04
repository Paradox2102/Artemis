package team2102.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.robot.RobotMap;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {

	public final CANTalon m_motor = new CANTalon(RobotMap.k_shooterMotor);

	public ShooterSubsystem() {
		SmartDashboard.putData(this);

		LiveWindow.addActuator(getName(), "Shooter Motor", m_motor);

		m_motor.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		// m_motor.setFeedbackDevice(FeedbackDevice.EncRising);
		// m_motor.configEncoderCodesPerRev(16);
		// m_motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		// m_motor.configEncoderCodesPerRev(6);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void enable() {
		m_motor.changeControlMode(TalonControlMode.Speed);
	}

	public void spinUp(double speed) {
		m_motor.set(speed);

		SmartDashboard.putNumber("Shooter speed", m_motor.getSpeed() + Math.random() * .1);
		SmartDashboard.putNumber("Shooter power", m_motor.getOutputVoltage());
	}

	public void stopAndDisable() {
		m_motor.changeControlMode(TalonControlMode.PercentVbus);
		m_motor.set(0.0);
	}

	public double getSpeed() {
		return m_motor.getSpeed();
	}
}
