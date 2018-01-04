package team2102.lib;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import robotCore.Logger;
import robotCore.Network;

public class PiCamera implements Network.NetworkReceiver {
	private final Network m_network = new Network();

	private final Object m_regionsLock = new Object();
	private List<TargetRegion> m_regions = new ArrayList<>();
	private List<TargetRegion> m_nextRegions = new ArrayList<>();

	private final Object m_frameDataLock = new Object();
	private int m_centerX;
	private int m_centerY;
	private int m_frameNo;

	private final double k_noTarget = 800.0;
	
	public class Rect {
		public int m_left;
		public int m_top;
		public int m_right;
		public int m_bottom;

		public Rect(int left, int top, int right, int bottom) {
			m_left = left;
			m_top = top;
			m_right = right;
			m_bottom = bottom;
		}
	}

	public class TargetRegion {
		public int m_color;
		public int m_centerTop;
		// public int m_topRight;
		public Rect m_bounds;

		public TargetRegion(int color, int left, int top, int right, int bottom, int centerTop, int topRight) {
			m_color = color;
			m_centerTop = centerTop;
			// m_topRight = topRight;
			m_bounds = new Rect(left, top, right, bottom);
		}
	}

	public PiCamera(String host, int port) {
		m_network.Connect(this, host, port);
	}

	private void processCameraFrame(String args) {
		Scanner scanner = new Scanner(args);

		try {
			synchronized (m_frameDataLock) {
				m_frameNo = scanner.nextInt();
				m_centerY = scanner.nextInt();
				m_centerX = scanner.nextInt();
			}
		} catch (InputMismatchException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}

	private void processCameraRegion(String args) {
		if (m_nextRegions != null) {
			Scanner scanner = new Scanner(args);

			try {
				m_nextRegions.add(new TargetRegion(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(),
						scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
			} catch (InputMismatchException ex) {
				ex.printStackTrace();
			} finally {
				scanner.close();
			}
		}
	}

	private void processCameraEnd(String args) {
		synchronized (m_regionsLock) {
			m_regions = m_nextRegions;
			m_nextRegions = new ArrayList<>();
		}
	}

	// TODO: Immutable list
	public List<TargetRegion> getRegions() {
		synchronized (m_regionsLock) {
			return m_regions;
		}
	}

	public double getGearCenter() {
		List<TargetRegion> regions = getRegions();

		if (regions.size() < 2) {
			return 0;
		}

		TargetRegion region1 = regions.get(0);
		TargetRegion region2 = regions.get(1);

		if (region1.m_bounds.m_left < region2.m_bounds.m_left) {
			return ((double) region1.m_bounds.m_left + region2.m_bounds.m_right) / 2;
		}

		return ((double) region1.m_bounds.m_right + region2.m_bounds.m_left) / 2;
	}
	
	public boolean isGearTargetValid(){
		List<TargetRegion> regions = getRegions();

		if(regions.size() > 1) {
			TargetRegion region1 = regions.get(0);
			
			if(region1.m_bounds.m_top == 0) {
				return false;
			}
			
			if (regions.size() >= 2 && regions.get(1).m_bounds.m_top == 0){
				return false;
			} 			
		}
		return true;
	}

	public double getGearHeight() {
		List<TargetRegion> regions = getRegions();

		if (regions.size() < 1) {
			return 0;
		}

		if (regions.size() == 1) {

			TargetRegion region1 = regions.get(0);

			return ((double) region1.m_bounds.m_bottom - region1.m_bounds.m_top);
		} else {
			TargetRegion region1 = regions.get(0);
			TargetRegion region2 = regions.get(1);

			if (region1.m_bounds.m_left < region2.m_bounds.m_left) {
				return ((double) region1.m_bounds.m_bottom - region1.m_bounds.m_top);
			} else {
				return ((double) region2.m_bounds.m_bottom - region2.m_bounds.m_top);
			}
		}

	}

	/*
	 * public double getGearCenter(){ List<TargetRegion> regions = GetRegions();
	 *
	 * if(regions.size() < 1) { return 0; }
	 *
	 * if (regions.size() == 1){
	 *
	 * TargetRegion region1 = regions.get(0);
	 *
	 * return ((double) region1.m_bounds.m_left + region1.m_bounds.m_right)/2; }
	 * else { TargetRegion region1 = regions.get(0); TargetRegion region2 =
	 * regions.get(1);
	 *
	 * if (region1.m_bounds.m_left < region2.m_bounds.m_left){ return ((double)
	 * region1.m_bounds.m_left + region1.m_bounds.m_right)/2; } else { return
	 * ((double) region2.m_bounds.m_left + region2.m_bounds.m_right)/2; } } }
	 */

	public int getCenterX() {
		synchronized (m_frameDataLock) {
			return m_centerX;
		}
	}

	public int getCenterY() {
		synchronized (m_frameDataLock) {
			return m_centerY;
		}
	}

	@Override
	public void ProcessData(String data) {
		Logger.Log("PiCamera", -1, String.format("Data: %s", data));

		if (data == null) {
			synchronized (m_regionsLock) {
				m_regions = new ArrayList<>();
			}

			return;
		}

		switch (data.charAt(0)) {
		case 'F':
			processCameraFrame(data.substring(1).trim());
			break;

		case 'R':
			processCameraRegion(data.substring(1).trim());
			break;

		case 'E':
			processCameraEnd(data.substring(1).trim());
			Logger.Log("PiCamera", -1, "# regions: " + m_nextRegions.size());
			break;

		default:
			Logger.Log("PiCamera", 3, String.format("Invalid command: %s", data));
			break;
		}
	}
}
