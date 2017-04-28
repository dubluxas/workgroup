package aston.group18.shop;

import java.util.LinkedList;
import java.util.Queue;

import aston.group18.vehicle.Driver;

/**
 * 
 * @author Justas Petrusonis
 * @author Jasmin Sabaka
 * @version V1.00, 03/10/17
 */

public class Till {

	private Queue<Driver> queue;

	public Till() {
		queue = new LinkedList<>();
	}

	public boolean addtoQueue(Driver o) {
		if (o != null) {
			if (!queue.contains(o)) {
					queue.add(o);
				return true;
			}
		}

		return false;
	}

	public Queue<Driver> getDriverQueue() {
		return queue;
	}


}
