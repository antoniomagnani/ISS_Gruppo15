package model.interfaces.drone.sensors;

import model.interfaces.messages.data.IPosition;

public interface ILocTracker {
	public void setSensorValue(IPosition value);
	public IPosition getSensorValue();
	
	public void updatePosition(double distanceTraveled);
}
