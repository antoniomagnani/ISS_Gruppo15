package model.implementations.messages.data;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import model.implementations.parameters.Parameters;
import model.interfaces.messages.data.IPosition;
import model.interfaces.messages.data.IStatus;
import model.interfaces.messages.data.IStatusElement;

public class Status implements IStatus{
	
	private IStatusElement fuel;
	private IStatusElement distance;
	private IStatusElement speed;
	private IPosition position; 	
	
	public Status(IStatusElement fuelValue, IStatusElement distanceValue, IStatusElement speedValue, IPosition positionValue){
		this.fuel = fuelValue;
		this.distance = distanceValue;
		this.speed = speedValue;
		this.position = positionValue;
	}
	
	public boolean isFuelLow(){
		return (fuel.getValue() < Parameters.minFuel);
	}
	
	public static IStatus readMessage(String msg) throws Exception{
		Struct message = (Struct) Term.createTerm(msg);
		Status newState = new Status(
				Fuel.readMessage(message.getArg(0).toString()),
				Distance.readMessage(message.getArg(1).toString()),
				Speed.readMessage(message.getArg(2).toString()),
				Position.readMessage(message.getArg(3).toString()));
		return newState;
	}
	
	public static String createMessage(IStatus status) throws Exception{
		return "droneStatus(" +
				"fuel("+ status.getFuel().getValue() +")," +
				"distance(" + status.getDistance().getValue() +")," +
				"speed(" + status.getSpeed().getValue() + ")," +
				"position(" + 
					status.getPosition().getLatitude() + "," +
					status.getPosition().getLongitude() + "," +
					status.getPosition().getAltitude() + "))";
	}

	@Override
	public IStatusElement getFuel() {
		return fuel;
	}

	@Override
	public IStatusElement getDistance() {
		return distance;
	}

	@Override
	public IStatusElement getSpeed() {
		return speed;
	}

	@Override
	public IPosition getPosition() {
		return position;
	}

	@Override
	public void setFuel(IStatusElement fuelValue) {
		this.fuel = fuelValue;
	}

	@Override
	public void setDistance(IStatusElement distanceValue) {
		this.distance = distanceValue;
	}

	@Override
	public void setSpeed(IStatusElement speedValue) {
		this.speed = speedValue;
	}

	@Override
	public void setPosition(IPosition positionValue) {
		this.position = positionValue;
	}

}
