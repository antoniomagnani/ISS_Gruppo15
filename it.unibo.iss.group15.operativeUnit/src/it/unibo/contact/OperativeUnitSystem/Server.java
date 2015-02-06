package it.unibo.contact.OperativeUnitSystem;

import it.unibo.contact.OperativeUnitSystem.ServerSupport;
import it.unibo.tools.DbTool;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.mysql.jdbc.Connection;

import model.implementations.messages.data.PhotoSender;
import model.implementations.parameters.DbData;
import model.implementations.parameters.Parameters;
import model.interfaces.messages.data.IPacket;
import model.interfaces.messages.data.IPhotoSender;
import model.interfaces.messages.data.IPosition;



public class Server extends ServerSupport {	
	
	private Properties 	prop; 
	private String 		dbUrl;
	private DbTool 		dbTool;
	private int			actualMission;
	private String		actualMissionName;
	private String		actualFolderName;
	private int			actualMissionID;

	public Server(String name) throws Exception {
		super(name);
		
		prop = new Properties();
		prop.setProperty("user", Parameters.dbUser);
		prop.setProperty("password", Parameters.dbPass);
		dbUrl = "jdbc:mysql://localhost:3306/";
		
		dbTool = new DbTool();
		this.actualMission = 1;
	}

	/*
	 *	Creazione di un database MySql nel caso esso non esista. 
	 *	Occorre registrare le librerie utilizzate ed aprire una connessione.
	 *	Definisco quale deve essere il numero di missione attuale.
	 */
	@Override
	protected void createDatabase() throws Exception {

		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		dbTool.setConnection(dbUrl, prop);
		
		dbTool.CreateDBIfNotExists(Parameters.dbName);
		
		dbTool.setConnection(dbUrl += Parameters.dbName, prop);
		
		if(!dbTool.CheckIfTableExists("Mission")){		
			dbTool.Execute(DbData.MissionTableCreate);
			dbTool.Execute(DbData.DroneStatusTableCreate);	
		}
		
		ResultSet missionNumberSet = dbTool.ExecuteQuery(DbData.ActualMissionNumber);
		
		String query = "SELECT MAX(CAST(TRIM('Mission' FROM Name) as signed)) " +
        		"as resultValue FROM Mission;";
        ResultSet result = dbTool.ExecuteQuery(query);
        int a = 0;
        // Fetcho il primo elemento della lista
        if (result.next()) {
        	if (!(result.getString("resultValue") == null)) {
        		a = Integer.parseInt(result.getString("resultValue"));
        	}
        }
        if(a != 0)
			actualMission = a + 1;
        
        actual = actualMission;
	}

	/*
	 * 	Aggiungo un record al database nella tabella Mission al fine di registrare 
	 * 	la missione appena cominciata.
	 */
	@Override
	protected void addActualMissionRecord() throws Exception {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String missionDate = formatter.format(new Date());
		
		this.actualMissionName = "Mission" + actualMission;
		
		dbTool.InsertInto("Mission", "Name, Date, Folder", 
						"'" + actualMissionName + "', " +
						"'" + missionDate + "', " +
						"'Folder" + actualMission + "'");
		
		ResultSet missionId = dbTool.ExecuteQuery(DbData.ActualMissionID("Mission" + actualMission));
		missionId.next();
		this.actualMissionID = Integer.parseInt(missionId.getString(1));
		
		actual = this.actualMissionID;
		
		this.actualFolderName = "Folder" + actualMission;
		// Creo una cartella chiamata come la missione attuale per contenere le foto
		File photoDirectory = new File(actualFolderName);
		photoDirectory.mkdir();
	}

	@Override
	protected void addMissionStatus(IPacket packetStatus) throws Exception {
		
		double fuel = packetStatus.getStatus().getFuel().getValue();
		double distance = packetStatus.getStatus().getDistance().getValue();
		double speed = packetStatus.getStatus().getSpeed().getValue();
		IPosition position = packetStatus.getStatus().getPosition();
		String photoName = packetStatus.getPhoto().getPhotoId();
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String statusDate = formatter.format(new Date());
		
		String query = "INSERT INTO DroneStatus" +
						"(MissionID,StatusDate,Fuel,Distance,Speed,Altitude,Latitude,Longitude,PhotoName)" +
						"VALUES (" + this.actualMissionID + ",'" + statusDate + "', " + String.valueOf(fuel)+ ", " +
								 String.valueOf(distance) + ", " + String.valueOf(speed) + ", " + String.valueOf(position.getAltitude()) + ", " +
								 String.valueOf(position.getLatitude()) + ", " + String.valueOf(position.getLongitude()) + ", '" + 
								 photoName + "');";
		
		dbTool.Execute(query);
	}

	@Override
	protected String getOwnIPAddress() throws Exception {
		String ownIP = "127.0.0.1";
		
		try {
			ownIP = InetAddress.getLocalHost().toString();
			ownIP = ownIP.substring(ownIP.indexOf("/") + 1, ownIP.length());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return ownIP;			
	}

	@Override
	protected void receivePhoto(String photoName) throws Exception {
		FileOutputStream fos = new FileOutputStream(this.actualFolderName + "\\" + photoName + ".jpg");
		IPhotoSender photoSender = new PhotoSender();
		photoSender.receivePhotoThroughSocket(fos);
	}

}
