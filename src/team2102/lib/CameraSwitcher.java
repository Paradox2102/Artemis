package team2102.lib;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@Deprecated
public class CameraSwitcher {

	public enum Camera {
		NONE(0), GEAR(1), CLIMB(2);

		private final int cam;

		Camera(int cam) {
			this.cam = cam;
		}

		public int getValue() {
			return cam;
		}
	}

	private final Object m_lock = new Object();

	private final Socket m_socket = new Socket();

	private Camera m_currentCamera = Camera.NONE;
	private boolean m_camSwitched = false;

	public CameraSwitcher(SocketAddress addr) {
		new Thread(() -> {
			try {
				m_socket.connect(addr);
			} catch (IOException ex) {
				ex.printStackTrace();
				return;
			}

			while (true) {
				synchronized (m_lock) {
					while (!m_camSwitched) {
						try {
							m_lock.wait();
						} catch (InterruptedException e) {
						}
					}

					SmartDashboard.putString("Switch Camera", m_currentCamera.toString());
					try {
						PrintWriter out = new PrintWriter(m_socket.getOutputStream(), true);
						out.format("S %d", m_currentCamera.ordinal());
					} catch (IOException e) {
						SmartDashboard.putString("Print socket error", e.getMessage());
					}
				}
			}
		}).start();
	}

	public void switchCamera(Camera cam) {
		synchronized (m_lock) {
			m_currentCamera = cam;
			m_camSwitched = true;
			m_lock.notify();
		}
	}
}
