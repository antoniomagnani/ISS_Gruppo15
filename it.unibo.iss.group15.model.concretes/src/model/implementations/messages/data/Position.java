package model.implementations.messages.data;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import model.interfaces.messages.data.IPosition;

public class Position implements IPosition {

	private double latitude;
	private double longitude;
	private double altitude;
	
	public Position(double lat, double longi, double alti) {
		this.latitude  = lat;
		this.longitude = longi;
		this.altitude  = alti;
	}
	
	public static IPosition readMessage(String content) throws Exception {
		Struct rt = (Struct) Term.createTerm(content);
		return new Position(Double.parseDouble(rt.getArg(0).toString()), Double.parseDouble(rt.getArg(1).toString()), Double.parseDouble(rt.getArg(2).toString()));
	}

	@Override
	public double getAltitude() {
		return altitude;
	}

	@Override
	public double getLatitude() {
		return latitude;
	}

	@Override
	public double getLongitude() {
		return longitude;
	}

	@Override
	public void setAltitude(double altitude) {
		this.altitude = altitude;
	}

	@Override
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	@Override
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
