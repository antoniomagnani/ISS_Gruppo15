package testPlans;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.interfaces.*;
import model.implementations.*;
import model.implementations.drone.sensors.Fuelometer;
import model.implementations.drone.sensors.LocTracker;
import model.implementations.drone.sensors.Odometer;
import model.implementations.drone.sensors.Speedometer;
import model.implementations.messages.common.Common;
import model.implementations.messages.data.*;
import model.implementations.messages.operativeUnit.*;
import model.implementations.parameters.Parameters;
import model.interfaces.drone.sensors.*;
import model.interfaces.messages.common.ICommon;
import model.interfaces.messages.data.*;
import model.interfaces.messages.operativeUnit.*;

public class DroneSystemTest {

	IStatusElement distance;
	IStatusElement fuel;
	IStatusElement speed;
	IPosition position;
	
	IStatus 		status;
	IInitialSpeed	initialSpeed;
	IChangeSpeed	incSpeed;
	IChangeSpeed	decSpeed;
	IPacket			packet;
	Common			common;
	
	IOdometer		odometer;
	IFuelometer		fuelometer;
	ISpeedometer 	speedometer;
	ILocTracker		locTracker;
	
	
	@Before
	public void initializeTests(){
		
		distance = new Distance(0);
		fuel = new Fuel(20);
		speed = new Speed(80);
		position = new Position(44.139728, 12.243369, 100.0);
		
		status = new Status(fuel, distance, speed, position);
		initialSpeed = new InitialSpeed(speed);
		incSpeed = new IncSpeed();
		decSpeed = new DecSpeed();
		packet = new Packet(new Photo("1", 1000), status);
		common = new Common();
	}
	
	
	// =============== ISTANZIAZIONE CORRETTA =============== 
	
	@Test
	public void testNotNullDistance(){
		assertNotNull("Errore di istanziazione: Distance", distance);
	}
	
	@Test
	public void testNotNullFuel(){
		assertNotNull("Errore di istanziazione: Fuel", fuel);
	}
	
	@Test
	public void testNotNullSpeed(){
		assertNotNull("Errore di istanziazione: Speed", speed);
	}
	
	@Test
	public void testNotNullPosition(){
		assertNotNull("Errore di istanziazione: Position", position);
	}
	
	@Test
	public void testNotNullStatus(){
		assertNotNull("Errore di istanziazione: Status", status);
	}
	
	@Test
	public void testNotNullInitialSpeed(){
		assertNotNull("Errore di istanziazione: InitialSpeed", initialSpeed);
	}
	
	@Test
	public void testNotNullIncSpeed(){
		assertNotNull("Errore di istanziazione: IncSpeed", incSpeed);
	}
	
	@Test
	public void testNotNullDecSpeed(){
		assertNotNull("Errore di istanziazione: DecSpeed", decSpeed);
	}
	
	@Test
	public void testNotNullCommon(){
		assertNotNull("Errore di istanziazione: Common", common);
	}
	
	
	// =============== INTERFACCE CORRETTA =============== 
	
	@Test
	public void testInterfaceDistance(){
		assertTrue(distance instanceof IStatusElement);
	}
	
	@Test
	public void testInterfaceFuel(){
		assertTrue(fuel instanceof IStatusElement);
	}
	
	@Test
	public void testInterfaceSpeed(){
		assertTrue(speed instanceof IStatusElement);
	}
	
	@Test
	public void testInterfacePosition(){
		assertTrue(position instanceof IPosition);
	}
	
	@Test
	public void testInterfaceStatus(){
		assertTrue(status instanceof IStatus);
	}
	
	@Test
	public void testInterfaceInitialSpeed(){
		assertTrue(initialSpeed instanceof IInitialSpeed);
	}
	
	@Test
	public void testInterfaceIncSpeed(){
		assertTrue(incSpeed instanceof IChangeSpeed);
	}
	
	@Test
	public void testInterfaceDecSpeed(){
		assertTrue(decSpeed instanceof IChangeSpeed);
	}
	
	@Test
	public void testInterfacePacket(){
		assertTrue(packet instanceof IPacket);
	}
	
	@Test
	public void testInterfaceCommon(){
		assertTrue(common instanceof ICommon);
	}
	
	// =============== METODI GET/SET ===============
	
	@Test
	public void testGetDistance(){
		double value = 80.0;
		Distance dist = new Distance(value);
		try {
			assertTrue(dist.getValue() == value);
		} catch (Exception e) {
			fail("Errore set/get di Distance");
		}	
	}
	
	@Test
	public void testGetFuel(){
		double value = 20.0;
		Fuel fuel = new Fuel(value);
		try {
			assertTrue(fuel.getValue() == value);
		} catch (Exception e) {
			fail("Errore set/get di Fuel");
		}	
	}
	
	@Test
	public void testGetSpeed(){
		double value = 80.0;
		Speed speed = new Speed(value);
		try {
			assertTrue(speed.getValue() == value);
		} catch (Exception e) {
			fail("Errore set/get di Speed");
		}	
	}
	
	@Test
	public void testGetPosition(){
		double altitude = 80.0;
		double latitude = 90.0;
		double longitude = 100.0;
		Position position = new Position(latitude, longitude, altitude);
		try {
			assertTrue(position.getLatitude() == latitude);
			assertTrue(position.getLongitude() == longitude);
			assertTrue(position.getAltitude() == altitude);
		} catch (Exception e) {
			fail("Errore set/get di Position");
		}	
	}
	
	@Test
	public void testGetOdometer(){
		Distance newDist = new Distance(10.0);
		odometer = new Odometer(newDist);
		try{
			assertTrue(odometer.getSensorValue() instanceof IStatusElement);
			assertTrue(odometer.getSensorValue().getValue() == newDist.getValue());
		} catch (Exception e ){
			fail("Errore set/get di Odometer");
		}
	}
	
	@Test
	public void testGetFuelometer(){
		Fuel newFuel = new Fuel(10.0);
		fuelometer = new Fuelometer(newFuel);
		try{
			assertTrue(fuelometer.getSensorValue() instanceof IStatusElement);
			assertTrue(fuelometer.getSensorValue().getValue() == newFuel.getValue());
		} catch (Exception e ){
			fail("Errore set/get di Fuelometer");
		}
	}
	
	@Test
	public void testGetSpeedometer(){
		Speed newSpeed = new Speed(10.0);
		speedometer = new Speedometer(newSpeed);
		try{
		assertTrue(speedometer.getSensorValue() instanceof IStatusElement);
		assertTrue(speedometer.getSensorValue().getValue() == newSpeed.getValue());
		} catch (Exception e ){
			fail("Errore set/get di Speedometer");
		}
	}
	
	@Test
	public void testGetLocTracker(){
		Position newPosition = new Position(44.139728, 12.243369, 100.0);
		locTracker = new LocTracker(position);
		try {
			assertTrue(locTracker.getSensorValue() instanceof IPosition);
			assertTrue(locTracker.getSensorValue().getLatitude() == newPosition.getLatitude());
			assertTrue(locTracker.getSensorValue().getLongitude() == newPosition.getLongitude());
			assertTrue(locTracker.getSensorValue().getAltitude() == newPosition.getAltitude());
		} catch (Exception e){
			fail("Errore set/get di LocTracker"); 
		}
	}
	
	// =============== INVARIANTI ===============
	
	@Test
	public void testOverLimitFuel(){
		fuel = new Fuel(Parameters.maxFuel + 1.0);
		try{
			assertTrue(fuel.getValue() == Parameters.maxFuel);
		} catch (Exception e ){
			fail("Errore invariante fuel oltre il limite");
		}
	}
	
	@Test
	public void testUnderLimitFuel(){
		fuel = new Fuel(Parameters.minFuel - 1.0);
		try{
			assertTrue(fuel.getValue() == Parameters.minFuel);
		} catch (Exception e ){
			fail("Errore invariante fuel sotto il limite");
		}
	}
	
	@Test
	public void testOverLimitSpeed(){
		speed = new Speed(Parameters.maxSpeed + 1.0);
		try {
			assertTrue(speed.getValue() == Parameters.maxSpeed);
		} catch (Exception e){
			fail("Errore invariante speed oltre il limite");
		}
	}
	
	@Test
	public void testUnderLimitSpeed(){
		speed = new Speed(Parameters.minSpeed - 1.0);
		try{
			assertTrue(speed.getValue() == Parameters.minSpeed);
		} catch (Exception e){
			fail("Errore invariante speed sotto il limite");
		}
	}
	
	// =============== READ/CREATE MESSAGE ===============
	
	@Test
	public void testReadDistance(){
		distance = new Distance(50.0);
		try{
			assertTrue(Distance.readMessage("distance(" + distance.getValue() + ")") instanceof IStatusElement);
			assertTrue(Distance.readMessage("distance(" + distance.getValue() + ")").getValue() == distance.getValue());
		} catch (Exception e ){
			fail("Errore read Distance");
		}
	}
	
	@Test
	public void testReadFuel(){
		fuel = new Fuel(50.0);
		try{
			assertTrue(Fuel.readMessage("fuel(" + speed.getValue() + ")") instanceof IStatusElement);
			assertTrue(Fuel.readMessage("fuel(" + speed.getValue() + ")").getValue() == fuel.getValue());
		} catch (Exception e ){
			fail("Errore read Fuel");
		}
	}
	
	@Test
	public void testReadSpeed(){
		speed = new Speed(50.0);
		try{
			assertTrue(Speed.readMessage("speed(" + speed.getValue() + ")") instanceof IStatusElement);
			assertTrue(Speed.readMessage("speed(" + speed.getValue() + ")").getValue() == speed.getValue());
		} catch (Exception e ){
			fail("Errore read Speed");
		}
	}
	
	@Test
	public void testReadPosition(){
		position = new Position(44.139728, 12.243369, 100.0);
		try{
			assertTrue(Position.readMessage("position(" + position.getLatitude() + "," + position.getLongitude() + "," + position.getAltitude() +")") instanceof IPosition);
			assertTrue(Position.readMessage("position(" + position.getLatitude() + "," + position.getLongitude() + "," + position.getAltitude() +")").getLatitude() == position.getLatitude());
			assertTrue(Position.readMessage("position(" + position.getLatitude() + "," + position.getLongitude() + "," + position.getAltitude() +")").getLongitude() == position.getLongitude());
			assertTrue(Position.readMessage("position(" + position.getLatitude() + "," + position.getLongitude() + "," + position.getAltitude() +")").getAltitude() == position.getAltitude());
		} catch (Exception e ){
			fail("Errore read Position");
		}
	}
	
	@Test
	public void testReadStatus(){
		try {
			assertTrue(Status.readMessage("droneStatus(fuel(" + fuel.getValue() + "), distance(" + distance.getValue() + "),"+
					"speed(" + speed.getValue() + "), position(" + position.getLatitude() + "," + position.getLongitude() + "," +
					position.getAltitude() + "))") instanceof IStatus);
			assertTrue(Status.readMessage("droneStatus(fuel(" + fuel.getValue() + "), distance(" + distance.getValue() + "),"+
					"speed(" + speed.getValue() + "), position(" + position.getLatitude() + "," + position.getLongitude() + "," +
					position.getAltitude() + "))").getFuel() instanceof IStatusElement);
			assertTrue(Status.readMessage("droneStatus(fuel(" + fuel.getValue() + "), distance(" + distance.getValue() + "),"+
					"speed(" + speed.getValue() + "), position(" + position.getLatitude() + "," + position.getLongitude() + "," +
					position.getAltitude() + "))").getDistance() instanceof IStatusElement);
			assertTrue(Status.readMessage("droneStatus(fuel(" + fuel.getValue() + "), distance(" + distance.getValue() + "),"+
					"speed(" + speed.getValue() + "), position(" + position.getLatitude() + "," + position.getLongitude() + "," +
					position.getAltitude() + "))").getPosition() instanceof IPosition);
			assertTrue(Status.readMessage(("droneStatus(fuel(" + fuel.getValue() + "), distance(" + distance.getValue() + "),"+
					"speed(" + speed.getValue() + "), position(" + position.getLatitude() + "," + position.getLongitude() + "," +
					position.getAltitude() + "))")).getSpeed() instanceof IStatusElement);
        } catch (Exception e) {
        	fail("Errore lettura messaggio Status!");
		}
	}
	
	@Test
	public void testCreateStatus() {
		status = new Status(fuel, distance, speed, position);
		try {
			assertTrue(Status.createMessage(status).equals("droneStatus(fuel(" + fuel.getValue() + "),"
					+ "distance(" + distance.getValue()+ "),"
					+ "speed(" + speed.getValue() + "),"
					+ "position(" + position.getLatitude() + "," + position.getLongitude() + ","
					+ position.getAltitude() + "))"));
			
        } catch (Exception e) {
        	fail("Errore nella creazione messaggio Status");
		}
	}
	
	@Test
	public void testReadPacket(){
		IPhoto photo = new Photo("photo1", 1000);
		try{
			assertTrue(Packet.readMessage("packet(" + Status.createMessage(status) + "," + Photo.createMessage(photo) + ")") instanceof IPacket);
		} catch (Exception e){
			fail("Errore lettura messaggio Packet");
		}
	}
	
	@Test
	public void testCreatePacket(){
		IPhoto photo = new Photo("photo1", 1000);
		packet = new Packet(photo, status);
		try{
			assertTrue(Packet.createMessage(packet).equals(
					"packet(" + Status.createMessage(status) + "," + Photo.createMessage(photo) + ")"));
		} catch (Exception e){
			fail("Errore nella creazione messaggio Packet");
		}
	}
	
	@Test
	public void testReadInitialSpeed(){
		speed = new Speed(80.0);
		try{
			assertTrue(InitialSpeed.readMessage("initialSpeedValue(value(" + speed.getValue() +"))") instanceof IInitialSpeed); 
			assertTrue(InitialSpeed.readMessage("initialSpeedValue(value(" + speed.getValue() +"))").getSpeed().getValue() == speed.getValue());
		} catch(Exception e){
			fail("Errore lettura messaggio InitialSpeed");
		}
	}
	
	@Test
	public void testCreateInitialSpeed(){
		speed = new Speed(80.0);
		initialSpeed = new InitialSpeed(speed);
		try{
			assertTrue(InitialSpeed.createMessage(speed).equals("initialSpeed(value(" + speed.getValue() + "))"));
		} catch (Exception e){
			fail("Errore nella creazione InitialSpeed");
		}
	}
	
	@Test
	public void testCreateStart(){
		try{
			assertTrue(common.start().equals("start"));
		} catch(Exception e){
			fail("Errore nella creazione Start");
		}
	}
	
	@Test
	public void testCreateStop(){
		try{
			assertTrue(common.stop().equals("stop"));
		} catch(Exception e){
			fail("Errore nella creazione Stop");
		}
	}
	
	@Test
	public void testCreateCurrentStatus(){
		try{
			assertTrue(common.currentStatusMessage().equals("currentStatus"));
		} catch(Exception e){
			fail("Errore nella creazione CurrentStatus");
		}
	}
	
	@Test
	public void testCreateIncSpeed(){
		try{
			assertTrue(IncSpeed.createMessage().equals("incSpeed"));
		} catch(Exception e){
			fail("Errore nella creazione IncSpeed");
		}
	}
	
	@Test
	public void testCreateDecSpeed(){
		try{
			assertTrue(DecSpeed.createMessage().equals("decSpeed"));
		} catch(Exception e){
			fail("Errore nella creazione IncSpeed");
		}
	}
	
	// =============== CALCOLI UPDATE VALORI ===============
	
	@Test
	public void testUpdateFuel(){
		fuel = new Fuel(20.0);
		speed = new Speed(80.0);
		fuelometer = new Fuelometer(fuel);
		double updatedFuel = fuel.getValue() - (speed.getValue()*30)*(1000.0/(1000*3600));
		fuelometer.updateFuel(speed, 1000);
		try{
			assertTrue(fuelometer.getSensorValue().getValue() == updatedFuel);
		} catch(Exception e){
			fail("Errore nel calcolo del fuel aggiornato");
		}
	}
	
	@Test
	public void testUpdateDistance(){
		distance = new Distance(20.0);
		speed = new Speed(80.0);
		odometer = new Odometer(distance);
		double updatedDistance = distance.getValue()+(speed.getValue()*(1000.0/(1000*3600)));
		odometer.updateDistance(speed, 1000.0);
		
		try{
			assertTrue(odometer.getSensorValue().getValue() == updatedDistance);
		} catch(Exception e){
			fail("Errore nel calcolo della distance aggiornata");
		}
	}
	
	@Test
	public void testUpdatePosition(){
		position = new Position(44.139728, 12.243369, 100.0);
		locTracker = new LocTracker(position);
		double updatedLat = position.getLatitude() + Parameters.movementVector[0] * 2.0/111;
		double updatedLong = position.getLongitude() + Parameters.movementVector[1] * 2.0 / (111 * Math.cos(updatedLat * Math.PI / 180));
		locTracker.updatePosition(2);
		try {
			assertTrue(locTracker.getSensorValue().getLatitude() == updatedLat);
			assertTrue(locTracker.getSensorValue().getLongitude() == updatedLong);
		} catch (Exception e) {
			fail("Errore nel calcolo della position aggiornata");
		}
	}
	
	
}
