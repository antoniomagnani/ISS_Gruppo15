package model.implementations.drone.sensors;

import model.implementations.parameters.Parameters;
import model.interfaces.drone.sensors.ILocTracker;
import model.interfaces.messages.data.IPosition;
import model.interfaces.messages.data.IStatusElement;

public class LocTracker implements ILocTracker {

	private IPosition position;
	
	public LocTracker(IPosition positionValue){
		position = positionValue;
	}

	@Override
	public void updatePosition(double distTraveled) {
		//111km => 1°
		double newLatitude = position.getLatitude() + Parameters.movementVector[0] * distTraveled/111;
		this.position.setLatitude(newLatitude);
		this.position.setLongitude(position.getLongitude() + Parameters.movementVector[1] * distTraveled / (111 * Math.cos(newLatitude * Math.PI / 180)));
	}

	@Override
	public void setSensorValue(IPosition value) {
		position = value;		
	}

	@Override
	public IPosition getSensorValue() {
		return position;
	}

}
