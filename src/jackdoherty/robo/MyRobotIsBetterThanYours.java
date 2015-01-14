package jackdoherty.robo;
import robocode.*;
import java.awt.Color;
import java.util.Random;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * MyRobotIsBetterThanYours - a robot by Jack Doherty
 */
public class MyRobotIsBetterThanYours extends AdvancedRobot {

	private boolean isMovingForward;
	private double radarHit = -1;
	private int hitCount = 0;
	private final Color[] defaultColors = {
		Color.red, Color.blue, Color.green, Color.magenta, Color.green, Color.blue
	}; //body, gun, radar, bullet, radar arc, scan color

	public void run() {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);

		while(true) {
			setDefaultColors(defaultColors);
			isMovingForward = true;
			ahead(100);
			turnRadarRight(360);
			turnRight(90);
			execute();
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

	private void roboFire(double min, double max, int hitCount) {
		setBulletColor(defaultColors[(int)getRandomDouble(0, defaultColors.length)]);
		if(hitCount > 1) {
			fire(getRandomDouble(min + 10, max + 10));
		} else {
			fire(getRandomDouble(min, max));
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		radarHit = e.getBearing() + getHeading();
		out.println("ROBOT: " + radarHit);
		setGunHeading(radarHit);
		out.println("GUN: " + getGunHeading());
		waitFor(new GunTurnCompleteCondition(this));
		roboFire(0, 10, hitCount);
	}

	public void onBulletHit(BulletHitEvent e) {
		hitCount += 1;
	}

	public void onBulletMissed(BulletMissedEvent event) {
		hitCount = 0;
	}

	public void onHitByBullet(HitByBulletEvent e) {
		setAllColors(Color.red);
		isMovingForward = false;
		back(25);
	}

	public void onHitWall(HitWallEvent e) {
		if(isMovingForward) {
			back(10);
		} else {
			ahead(10);
		}
	}
}
