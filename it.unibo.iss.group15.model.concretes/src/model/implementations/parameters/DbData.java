package model.implementations.parameters;

public class DbData {

	public static final String dbName = "DroneMission";
	public static final String dbUser = "root";
	public static final String dbPass = "admin";
	
	public static final String MissionTableCreate = 
			"CREATE TABLE Mission ("
			+ " ID int(10) NOT NULL auto_increment,"
			+ " Name varchar(100),"
			+ " Date datetime,"
			+ " Folder varchar(100),"
			+ " PRIMARY KEY (ID)"
		 	+ ");";
	
	public static final String DroneStatusTableCreate = 
			"CREATE TABLE DroneStatus ("
		 	+ " ID int(10) NOT NULL auto_increment,"
		 	+ " MissionID int(10),"
		 	+ " StatusDate datetime,"
		 	+ " Fuel double(10,5),"
		 	+ " Distance double(10,5),"
		 	+ " Speed double(10,2),"
		 	+ " Altitude nvarchar(100),"
		 	+ " Latitude nvarchar(100),"
		 	+ " Longitude nvarchar(100),"
		 	+ " PhotoName nvarchar(255),"
		 	+ " PRIMARY KEY (ID),"
		 	+ " FOREIGN KEY (MissionID)"
		 	+ " REFERENCES Mission(ID));";
	
	public static final String ActualMissionNumber = 
			"SELECT MAX(CAST(TRIM('Mission ' FROM Name) as signed)) " +
			"as missionNumber FROM Mission;";
	
	public static String ActualMissionID(String missionName){
		return	"SELECT ID FROM Mission as MissionID WHERE Name = '" + missionName + "';";
	}
}
