package team2102.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.robot.RobotMap;

/**
 *
 */
public class GearSubsystem extends Subsystem {

	private final Solenoid m_solenoid = new Solenoid(RobotMap.k_gearSolenoid);

	public GearSubsystem() {
		SmartDashboard.putData(this);

		LiveWindow.addActuator(getName(), "Piston", m_solenoid);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void releaseGear() {
		m_solenoid.set(true);
	}

	public void retractGear() {
		m_solenoid.set(false);
	}
}
