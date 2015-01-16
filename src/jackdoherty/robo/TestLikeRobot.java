package jackdoherty.robo;

import robocode.*;

import java.awt.*;
import java.util.Random;

// API help : http://robocode.sourceforge.net/docs/robocode/robocode/Robot.html

/**
 * MyRobotIsBetterThanYours - a robot by Jack Doherty
 */
public class TestLikeRobot extends AdvancedRobot {

	//set constants for walls and then put a way to fidn wall in roboMove method.

	private final int NORTH_WALL = 0;
	private final int EAST_WALL = 1;
	private final int SOUTH_WALL = 2;
	private final int WEST_WALL = 3;

	private boolean isMovingForward;
	private double radarHit = -1;
	private int hitCount = 0;
	private final Color[] defaultColors = {
		Color.red, Color.blue, Color.green, Color.magenta, Color.green, Color.blue
	}; //body, gun, radar, bullet, radar arc, scan color

	public void run() {

		/*
		ANSWER (4): The robot can do other tasks while the run loop is running because of the multithreaded architecture.
		When the game calls another method, such as when it calls onScannedRobot because it detects the robot has scanned something, the run loop continues
		because the game uses multiple threads on the CPU to run the program. This means that it can do multiple tasks at once because each thread is working
		on something independently.
		 */

		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		setAdjustRadarForRobotTurn(true);

		while(true) {
			setDefaultColors(defaultColors);
			isMovingForward = true;
			out.println("X Coord: " + getX());
			out.println("Y Coord: " + getY());
			ahead(100);
			turnRadarRight(360);
			turnRight(90);
			execute();
		}
	}

	private void roboMove() {
		//ANSWER (2): This is an example of inheritance. The 4 methods being called below are a part of either class Robot or AdvancedRobot
		double xCoord = getX();
		double yCoord = getY();
		double fieldHeight = getBattleFieldHeight();
		double fieldWidth = getBattleFieldWidth();
		double sideA;
		double sideB;
		double distanceToWallC;
		int turnDirection = getRandomInt(0, 3); // 1=forward, 2=right, 3=back, 4=left
		turnRight(turnDirection * 90);
		//START HERE
	}

	private double getDistance(double x1, double y1, double x2, double y2) {
		double xDist = (x1 - x2) * (x1 - x2);
		double yDist = (y1 - y2) * (y1 - y2);
		return Math.sqrt(xDist + yDist);
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

	private int getRandomInt(int min, int max) {
		Random r = new Random();
		int randomNum = r.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	private double getRandomDouble(double min, double max) {
		Random r = new Random();
		double randomValue = min + (max - min) * r.nextDouble();
		return randomValue;
	}

	private void roboFire(double min, double max, int hitCount) {
		setBulletColor(defaultColors[(int)getRandomDouble(0, defaultColors.length)]);
		if(hitCount > 1) {
			fire(getRandomDouble(min + 1, max + 1));
		} else {
			fire(getRandomDouble(min, max));
		}
	}


	//ANSWER (2): This is an example of polymorphism. This overrides the method found in robocode.jar and provides a new set of instructions when this method is called by the game.
	public void onScannedRobot(ScannedRobotEvent e) {
		radarHit = e.getBearing() + getHeading();
		out.println("ROBOT: " + radarHit);
		setGunHeading(radarHit);
		out.println("GUN: " + getGunHeading());
		waitFor(new GunTurnCompleteCondition(this));
		roboFire(0.1, 2, hitCount);
	}


	/*
	ANSWER (3): This is an example of event driven behavior.
	When the game detects the robot has hit something with a bullet, it calls this method and supplies it with the BulletHitEvent.
	This allows for the robot to interact with, and get information from, the specific event.
	 */
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
