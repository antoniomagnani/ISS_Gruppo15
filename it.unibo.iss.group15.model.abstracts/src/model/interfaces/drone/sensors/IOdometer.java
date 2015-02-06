package model.interfaces.drone.sensors;

import model.interfaces.messages.data.IStatusElement;

public interface IOdometer {
	public void setSensorValue(IStatusElement value);
	public IStatusElement getSensorValue();
	
	public void updateDistance(IStatusElement kmh, double time );
}
