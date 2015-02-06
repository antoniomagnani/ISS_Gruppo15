package model.implementations.drone.sensors;

import model.interfaces.drone.sensors.IFuelometer;
import model.interfaces.messages.data.IStatusElement;

public class Fuelometer implements IFuelometer {

	private IStatusElement fuel;
	
	public Fuelometer (IStatusElement fuelValue){
		this.fuel = fuelValue;
	}
	
	@Override
	public void setSensorValue(IStatusElement value) {
		fuel = value;
	}

	@Override
	public IStatusElement getSensorValue() {
		return fuel;
	}

	@Override
	public void updateFuel(IStatusElement kmh, double time) {
		double newFuel = this.fuel.getValue() - (kmh.getValue()*30)*(time/(1000*3600));
		fuel.setValue(newFuel);
	}

}
