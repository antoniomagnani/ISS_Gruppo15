package model.interfaces.operativeUnit.dashboard;

public interface ICmdDisplay {
	public void startMission();
	public void stopMission();
	public void setSpeed(double value);
	public void incSpeed();
	public void decSpeed();
}
