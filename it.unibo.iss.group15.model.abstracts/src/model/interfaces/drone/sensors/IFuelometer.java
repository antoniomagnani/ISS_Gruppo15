package model.interfaces.drone.sensors;

import model.interfaces.messages.data.IStatusElement;

public interface IFuelometer {
	public void setSensorValue(IStatusElement value);
	public IStatusElement getSensorValue();
	
	public void updateFuel(IStatusElement kmh, double time);
}
