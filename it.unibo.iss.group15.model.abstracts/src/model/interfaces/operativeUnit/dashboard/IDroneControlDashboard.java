package model.interfaces.operativeUnit.dashboard;

import model.interfaces.messages.data.IStatus;

public interface IDroneControlDashboard {
	public void setCommandDisplay(ICmdDisplay value);
	public void setGaugeDisplay(IGaugeDisplay value);
	public void updateGauge(IStatus value);
	public void stopDroneControlDashboard();
}
