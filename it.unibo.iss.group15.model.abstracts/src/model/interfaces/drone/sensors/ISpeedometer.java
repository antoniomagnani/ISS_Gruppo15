package model.interfaces.drone.sensors;

import model.interfaces.messages.data.IStatusElement;

public interface ISpeedometer {
	public void setSensorValue(IStatusElement value);
	public IStatusElement getSensorValue();
}
