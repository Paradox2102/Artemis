package team2102.lib;

import java.util.ArrayList;
import java.util.List;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Notifier;

public class TrajectoryRecorder {

	private final List<double[]> m_trajectory = new ArrayList<>();

	private final CANTalon m_motor;

	private boolean isRecording = false;

	public TrajectoryRecorder(CANTalon motor, double period) {
		m_motor = motor;

		m_pointRecorder.startPeriodic(period);
	}

	private final Notifier m_pointRecorder = new Notifier(() -> {
		if (isRecording) {
			recordOnePoint();
		}
	});

	public void recordOnePoint() {
		final double[] point = new double[2];
		point[0] = m_motor.getPosition();
		point[1] = m_motor.getSpeed();
		m_trajectory.add(point);
	}

	public void startRecording() {
		isRecording = true;
	}

	public void stopRecording() {
		isRecording = false;
	}

	public List<double[]> getRecordedTrajectory() {
		return m_trajectory;
	}
}
