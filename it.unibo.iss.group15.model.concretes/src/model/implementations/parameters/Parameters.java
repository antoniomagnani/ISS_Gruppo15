package model.implementations.parameters;

public class Parameters {

	public static final int DTF = 3000;
	
	public static final double minFuel = 0.5;
	public static final double maxFuel = 30.0;
	
	public static final double startAltitude = 100;
	public static final double startLongitude = 12.243369;
	public static final double startLatitude = 44.139728;
	
	public static final double DS = 10;
	
	public static final double minSpeed = 60.0;
	public static final double defaultSpeed = 60.0;
	public static final double maxSpeed = 120.0;
	
	public static final int[] movementVector = { 1, -1, 0 };
		
	// Database parameters
	public static final String dbName = "DroneMission";
	public static final String dbUser = "root";
	public static final String dbPass = "admin";
	
	// PORTS
	public static final int socketPort = 8071;
}
