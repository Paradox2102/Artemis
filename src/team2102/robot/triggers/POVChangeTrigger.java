package team2102.robot.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class POVChangeTrigger extends Trigger {

	private final GenericHID m_input;
	private final int m_triggerAngle;

	public POVChangeTrigger(GenericHID input, int triggerAngle) {
		m_input = input;
		m_triggerAngle = triggerAngle;
	}

	@Override
	public boolean get() {
		return m_input.getPOV() == m_triggerAngle;
	}
}
