package model.implementations.drone.sensors;

import model.interfaces.drone.sensors.IOdometer;
import model.interfaces.messages.data.IStatusElement;

public class Odometer implements IOdometer{

	private IStatusElement distance;
	
	public Odometer (IStatusElement distanceValue){
		this.distance = distanceValue;
	}
	
	@Override
	public void setSensorValue(IStatusElement distanceValue) {
		this.distance = distanceValue;
	}

	@Override
	public IStatusElement getSensorValue() {
		return distance;
	}

	@Override
	public void updateDistance(IStatusElement kmh, double time) {
		double newDistanceTraveled = kmh.getValue() * (time/(1000*3600));
		distance.setValue(distance.getValue() + newDistanceTraveled);
	}


}
