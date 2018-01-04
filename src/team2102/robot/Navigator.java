package team2102.robot;

import java.util.List;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import team2102.lib.LIDARLite;
import team2102.lib.PiCamera;
import team2102.lib.PiCamera.TargetRegion;
import team2102.lib.SPIGyro;

/**
 *
 */
public class Navigator {

	public static final int k_minDistance = 5;

	private static final double k_boilerTargetRatio = 2.35;
	private static final int k_maxTargetArea = 4000;

	private final LIDARLite m_distanceSensor = new LIDARLite(I2C.Port.kOnboard);
	private final SPIGyro m_gyro = new SPIGyro(SPI.Port.kMXP);
	private final PiCamera m_boilerCamera = new PiCamera("10.21.2.11", 5800);
	private final PiCamera m_gearCamera = new PiCamera("10.21.2.12", 5800);
	
	private int m_lidarFailCount = 0;
	private static final int k_maxFailCount = 5;
	
	public static final int k_invalidDistance = -1;

	public void startMeasuringDistance() {
		m_distanceSensor.startMeasuring();
	}

	public void stopMeasuringDistance() {
		m_distanceSensor.stopMeasuring();
	}

	public int getDistance() {
		int distance = m_distanceSensor.getDistance();
		SmartDashboard.putNumber("Distance (raw)", distance);
		if (distance < k_minDistance) {
			if (++m_lidarFailCount >= k_maxFailCount) {
				return k_invalidDistance;
			}
		} else {
			m_lidarFailCount = 0;
		}
		return distance;
	}

	public void resetAngle(double start) {
		m_gyro.resetYaw(start);
	}

	public double getAngle() {
		double angle = m_gyro.getYaw();
		SmartDashboard.putNumber("Gyro angle", angle);
		return angle;
	}

	public double getAngleDiff(double angle) {
		return m_gyro.getYawDiff(angle);
	}

	public TargetRegion getBoilerTarget() {
		List<TargetRegion> regions = m_boilerCamera.getRegions();

		TargetRegion winningRegion = null;
		double lowestError = Double.POSITIVE_INFINITY;

		for (TargetRegion region : regions) {
			int width = region.m_bounds.m_right - region.m_bounds.m_left;
			int height = region.m_bounds.m_bottom - region.m_bounds.m_top;

			int area = width * height;

			if (area >= k_maxTargetArea) {
				continue;
			}

			double ratio = (double) width / (double) height;
			double error = Math.abs(k_boilerTargetRatio - ratio);

			if (error < lowestError) {
				lowestError = error;
				winningRegion = region;
			}
		}

		return winningRegion;
	}

	public int getFrameCenterX() {
		return m_boilerCamera.getCenterX();
	}

	public double getGearCenter() {
		return m_gearCamera.getGearCenter();
	}
	
	public boolean isGearTargetValid() {
		return m_gearCamera.isGearTargetValid();
	}

	public double getGearHeight() {
		return m_gearCamera.getGearHeight();
	}

	public double getGearAngle() {
		double currentAngle = getAngle();
		double targetAngle = 0;
		if (-145 <= currentAngle && currentAngle <= -95) {
			targetAngle = -120;
		} else if (155 <= currentAngle || currentAngle <= -155) {
			targetAngle = 180;
		} else if (95 <= currentAngle && currentAngle <= 145) {
			targetAngle = 120;
		}
		return targetAngle;
	}

	/*public double getDistanceToTarget(double gearHeight) {
		return (1248 * Math.pow(gearHeight, -0.6533));
	}

	public double getTargetCenterLine(double distanceToTarget) {
		return (-0.0006224 * distanceToTarget * distanceToTarget * distanceToTarget
				+ 0.1984 * distanceToTarget * distanceToTarget - 21.94 * distanceToTarget + 1053);
	} */
	
	public double getDistanceToTarget(double gearHeight) {
		return (1211 * Math.pow(gearHeight, -0.6446));
	}

	public double getTargetCenterLine(double distanceToTarget) {
		return (-0.000606 * distanceToTarget * distanceToTarget * distanceToTarget
				+ 0.1933 * distanceToTarget * distanceToTarget
				- 21.3 * distanceToTarget + 1050);
	}


	public double getDeltaCenterLine() {
		double distanceToTarget = Robot.navigator.getDistanceToTarget(Robot.navigator.getGearHeight());
		double targetCenterLine = Robot.navigator.getTargetCenterLine(distanceToTarget);
		double centerLine = Robot.navigator.getGearCenter();
		return targetCenterLine - centerLine;
	}

}
