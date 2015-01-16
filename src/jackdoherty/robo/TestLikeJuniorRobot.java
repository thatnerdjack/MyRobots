/**
 * Copyright (c) 2001-2014 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package jackdoherty.robo;


import robocode.JuniorRobot;

/*
ANSWER (5): Changing the heading field dose not work.
Changing the heading field only does just that, it does not tell the game to actually turn the robot.
What happens in reality is the robot thread never even starts, causing the game to have to force stop it after every turn.
Doing this is not a good idea because it leads to unnecessary bugs that can be avoided with "good style code".
 */

public class TestLikeJuniorRobot extends JuniorRobot {

	public void run() {
		// Set robot colors
		setColors(green, black, blue);

		// Seesaw forever
		while (true) {
			heading = 90;
			heading = 180;
		}
	}

	/**
	 * When we see a robot, turn the gun towards it and fire
	 */
	public void onScannedRobot() {
		// Turn gun to point at the scanned robot
		turnGunTo(scannedAngle);

		// Fire!
		fire(1);
	}

	/**
	 * We were hit!  Turn and move perpendicular to the bullet,
	 * so our seesaw might avoid a future shot.
	 */
	public void onHitByBullet() {
		// Move ahead 100 and in the same time turn left papendicular to the bullet
		turnAheadLeft(100, 90 - hitByBulletBearing);
	}
}
