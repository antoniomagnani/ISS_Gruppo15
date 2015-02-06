package model.interfaces.messages.data;

public interface IPosition {
	
	double getAltitude();
	double getLatitude();
	double getLongitude();
	
	void setAltitude(double altitude);
	void setLatitude(double latitude);
	void setLongitude(double longitude);
}
