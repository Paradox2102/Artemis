package team2102.lib;

import edu.wpi.first.wpilibj.SerialPort;

@Deprecated
public class SerialGyro {
	private final SerialPort m_port = new SerialPort(115200, SerialPort.Port.kMXP);
	private String m_data = "";

	private double m_yaw = 0;
	private double m_pitch = 0;
	private double m_roll = 0;

	private double m_yawZero = 0;
	private double m_pitchZero = 0;
	private double m_rollZero = 0;

	public SerialGyro() {

		m_port.reset();

		new Thread(() -> {
			while (true) {
				try {
					ReadSerial();
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				try {
					Thread.sleep(20);
				} catch (InterruptedException ex) {
					//
				}
			}
		}).start();
	}

	private static double[] ParseDouble(String str, int count) {
		double[] args = new double[count];
		int i = 0;

		String[] tokens = str.trim().split(" ");

		for (String token : tokens) {
			try {
				args[i] = Double.parseDouble(token);

			} catch (NumberFormatException nfe) {
				break;
			}

			if (++i >= count) {
				break;
			}
		}

		if (i == count) {
			return (args);
		}

		return (null);
	}

	private void ReadSerial() {
		int index;

		m_data = m_data + m_port.readString();

		while ((index = m_data.indexOf('\n')) >= 0) {
			String line = m_data.substring(0, index);

			double[] args = ParseDouble(line, 3);

			if (args != null) {
				synchronized (this) {
					m_yaw = args[0];
					m_pitch = args[1];
					m_roll = args[2];
				}
			}

			if (index < m_data.length() - 1) {
				m_data = m_data.substring(index + 1);
			} else {
				m_data = "";
			}
		}
	}

	public double GetYaw() {
		double yaw;

		synchronized (this) {
			yaw = m_yaw - m_yawZero;
		}

		if (yaw > 180) {
			yaw -= 360;
		} else if (yaw < -180) {
			yaw += 360;
		}

		return (yaw);
	}

	public void Reset() {
		synchronized (this) {
			m_yawZero = m_yaw;
			m_pitchZero = m_pitch;
			m_rollZero = m_roll;
		}
	}

	public void ResetYaw(double start) {
		m_yawZero = m_yaw + start;
	}

	public double GetYawDiff(double angle) {
		double diff = angle - GetYaw();

		if (diff < -180) {
			diff += 360;
		} else if (diff > 180) {
			diff -= 360;
		}

		return diff;
	}
}
