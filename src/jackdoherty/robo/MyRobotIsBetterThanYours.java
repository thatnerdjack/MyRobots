package jackdoherty.robo;
import robocode.*;
import java.awt.Color;
import java.util.ArrayList;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * MyRobotIsBetterThanYours - a robot by Jack Doherty
 */
public class MyRobotIsBetterThanYours extends Robot {

	private boolean isLooking = false;
	private double bearing = -1;
	private boolean isMovingRight = false;
	private ScannedRobotEvent enemyRobot;
	private final Color[] defaultColors = {
		Color.red, Color.blue, Color.green, Color.magenta, Color.green
	};

	public void run() {
		while(true) {
			arraySetColors(defaultColors);
			if(!isLooking) {
				turnRadarRight(360);
			} else {
				back(100);
			}
		}
	}

	public void arraySetColors(Color[] colors) {
		setColors(defaultColors[0], defaultColors[1], defaultColors[2], defaultColors[3], defaultColors[4]); // body,gun,radar, bullet, radar arc
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		enemyRobot = e;
		fire(1);
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		setAllColors(Color.red);
		back(25);
	}
	
	/**
	 * onHitWall: What to do when you hit a wall
	 */
	public void onHitWall(HitWallEvent e) {
		// Replace the next line with any behavior you would like
		back(20);
	}	
}
