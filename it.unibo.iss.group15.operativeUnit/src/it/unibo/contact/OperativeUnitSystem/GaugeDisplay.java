package it.unibo.contact.OperativeUnitSystem;

import javax.swing.SwingUtilities;

import it.unibo.contact.OperativeUnitSystem.GaugeDisplaySupport;
import model.implementations.operativeUnit.dashboard.DroneControlDashboard;
import model.interfaces.messages.data.IStatus;
import model.interfaces.operativeUnit.dashboard.IDroneControlDashboard;

public class GaugeDisplay extends GaugeDisplaySupport {

	protected IDroneControlDashboard dashboard;
	
	public GaugeDisplay(String name) throws Exception {
		super(name);
		dashboard = DroneControlDashboard.getInstance();
	}
	
	class GUIupdater implements Runnable{
		IStatus value;
		public GUIupdater(IStatus value){
			this.value = value;
		}

		@Override
		public void run() {
			dashboard.updateGauge(value);			
		}
	}

	@Override
	protected void updateGUI(IStatus droneStatus) throws Exception {
		SwingUtilities.invokeLater(new GUIupdater(droneStatus));	
	}

	@Override
	protected void stopGUI() throws Exception {
		dashboard.stopDroneControlDashboard();		
	}

}
