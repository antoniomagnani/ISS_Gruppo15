package it.unibo.contact.DroneSystem;

import model.implementations.drone.sensors.*;
import model.implementations.messages.data.*;
import model.implementations.parameters.Parameters;
import model.interfaces.drone.sensors.IFuelometer;
import model.interfaces.drone.sensors.ILocTracker;
import model.interfaces.drone.sensors.IOdometer;
import model.interfaces.drone.sensors.ISpeedometer;
import model.interfaces.messages.data.IPosition;
import model.interfaces.messages.data.IStatus;
import model.interfaces.messages.data.IStatusElement;

public class DroneSensor extends DroneSensorSupport {
	
	//Sensori
	private IFuelometer fuelometer;
	private ILocTracker locTracker;
	private IOdometer odometer;
	private ISpeedometer speedometer;
	
	// Private variables
	private double timer;

	public DroneSensor(String name) throws Exception {
		super(name);
	}

	/**
	 *	Alla ricezione della velocità iniziale il droneSensor inizializza i sensori 
	 *  ed imposta la velocità, facendo così partire la missione
	 */
	@Override
	protected void startMission(IStatusElement speed) throws Exception {
		
		speedometer = new Speedometer(speed);
		
		position = new Position(Parameters.startLatitude, 
											   Parameters.startLongitude,
											   Parameters.startAltitude);
		locTracker = new LocTracker(position);
		
		IStatusElement distance = new Distance(0);
		odometer = new Odometer(distance);
		
		IStatusElement fuel = new Fuel(Parameters.maxFuel);
		fuelometer = new Fuelometer(fuel);
		
		timer = System.currentTimeMillis();
	}

	@Override
	protected void incSpeed() throws Exception {
		updateSensors();
		double oldSpeed = speedometer.getSensorValue().getValue();
		IStatusElement newSpeed = new Speed(oldSpeed + Parameters.DS);
		speedometer.setSensorValue(newSpeed);
	}

	@Override
	protected void decSpeed() throws Exception {
		updateSensors();
		double oldSpeed = speedometer.getSensorValue().getValue();
		IStatusElement newSpeed = new Speed(oldSpeed - Parameters.DS);
		speedometer.setSensorValue(newSpeed);
	}
	
	private void updateSensors(){
		double timeElapsed = System.currentTimeMillis() - timer;
		double oldDistance = odometer.getSensorValue().getValue();
		odometer.updateDistance(speedometer.getSensorValue(), timeElapsed);
		fuelometer.updateFuel(speedometer.getSensorValue(), timeElapsed);
		
		double distanceTraveled = odometer.getSensorValue().getValue() - oldDistance;
		locTracker.updatePosition(distanceTraveled);
		timer += timeElapsed; 
		
		position = locTracker.getSensorValue();
	}

	@Override
	protected IStatus prepareResponseStatus() throws Exception {
		updateSensors();
		
		IStatus responseStatus = new Status(
				fuelometer.getSensorValue(), 
				odometer.getSensorValue(),
				speedometer.getSensorValue(),
				locTracker.getSensorValue());
		return responseStatus;
	}
}
