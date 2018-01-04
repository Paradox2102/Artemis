package team2102.robot.subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import team2102.robot.RobotMap;

/**
 *
 */
public class CameraSubsystem extends Subsystem {

	private final static int k_frameWidth = 320;
	private final static int k_frameHeight = 240;

	public enum CameraPosition {
		UP(1.0), CENTER(0.65), DOWN(0.35);

		private final double pos;

		private CameraPosition(double pos) {
			this.pos = pos;
		}

		private double getValue() {
			return pos;
		}
	}

	private final Servo m_servo = new Servo(RobotMap.k_cameraServo);

	public CameraSubsystem() {
		/*
		 * UsbCamera cam =
		 * CameraServer.getInstance().startAutomaticCapture(RobotMap.
		 * k_driverCamera); cam.setResolution(k_frameWidth, k_frameHeight);
		 * cam.setExposureManual(50);
		 */
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}

	public void setPosition(CameraPosition pos) {
		m_servo.set(pos.getValue());
	}
}
