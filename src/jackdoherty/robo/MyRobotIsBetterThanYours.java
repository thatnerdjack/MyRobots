package jackdoherty.robo;
import robocode.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * MyRobotIsBetterThanYours - a robot by Jack Doherty
 */
public class MyRobotIsBetterThanYours extends Robot {

	private double radarHit = -1;
	private boolean isMovingRight = false;
	private final Color[] defaultColors = {
		Color.red, Color.blue, Color.green, Color.magenta, Color.green, Color.blue
	}; //body, gun, radar, bullet, radar arc, scan color

	public void run() {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);

		while(true) {
			setDefaultColors(defaultColors);
			ahead(100);
			turnRight(90);
			turnRadarRight(360);
		}
	}

	private void setGunHeading(double heading){
		double dif = heading - getGunHeading();

		if(dif < 180){
			turnGunRight(dif);
		} else{
			turnGunLeft(dif);
		}
	}

	private void setDefaultColors(Color[] colors) {
		setColors(defaultColors[0], defaultColors[1], defaultColors[2], defaultColors[3], defaultColors[4]); // body,gun,radar, bullet, radar arc
		setScanColor(defaultColors[5]);
	}

	private double getRandomDouble(double min, double max) {
		Random r = new Random();
		double randomValue = min + (max - min) * r.nextDouble();
		return randomValue;
	}

	private void roboFire(double min, double max) {
		setBulletColor(defaultColors[(int)getRandomDouble(0, defaultColors.length)]);
		fire(getRandomDouble(min, max));
	}

	/**
	 * onScannedRobot: What to do when you see another robot
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		radarHit = e.getHeading();
		setGunHeading(radarHit);
		roboFire(0, 10);
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
