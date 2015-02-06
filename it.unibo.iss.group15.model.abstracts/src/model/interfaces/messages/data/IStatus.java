package model.interfaces.messages.data;

public interface IStatus {
	IStatusElement getFuel();
	IStatusElement getDistance();
	IStatusElement getSpeed();
	IPosition getPosition();
	
	void setFuel(IStatusElement fuelValue);
	void setDistance(IStatusElement distanceValue);
	void setSpeed(IStatusElement speedValue);
	void setPosition(IPosition position);
	
	boolean isFuelLow();
}
