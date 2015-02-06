package model.implementations.drone.sensors;

import model.implementations.parameters.Parameters;
import model.interfaces.drone.sensors.ISpeedometer;
import model.interfaces.messages.data.IStatusElement;

public class Speedometer implements ISpeedometer {
	
	private IStatusElement speed;

	public Speedometer(IStatusElement speedValue){
		this.speed = speedValue;
	}
		
	@Override
	public void setSensorValue(IStatusElement value) {
		value.setValue(value.getValue());
		speed = value;
	}

	@Override
	public IStatusElement getSensorValue() {
		return speed;
	}
}
