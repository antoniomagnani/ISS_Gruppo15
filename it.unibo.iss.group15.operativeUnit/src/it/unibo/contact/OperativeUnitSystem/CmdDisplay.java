package it.unibo.contact.OperativeUnitSystem;

import it.unibo.contact.OperativeUnitSystem.CmdDisplaySupport;
import model.implementations.messages.data.Speed;
import model.implementations.messages.operativeUnit.InitialSpeed;
import model.implementations.operativeUnit.dashboard.DroneControlDashboard;
import model.interfaces.operativeUnit.dashboard.ICmdDisplay;
import model.interfaces.operativeUnit.dashboard.IDroneControlDashboard;

public class CmdDisplay extends CmdDisplaySupport implements ICmdDisplay {

	protected IDroneControlDashboard dashboard;
	
	public CmdDisplay(String name) throws Exception {
		super(name);
		
		dashboard = DroneControlDashboard.getInstance();
		dashboard.setCommandDisplay(this);
	}

	//Setto la velocita iniziale e creo il messaggio di IInitialSpeed da inviare al drone
	@Override
	public void setSpeed(double value) {
		// TODO Auto-generated method stub
		
		this.currentSpeed = new Speed(value);
		curstate = "cmdSetSpeedState";
	}

	@Override
	public void startMission() {
		curstate = "cmdStartState";
	}

	@Override
	public void stopMission() {
		curstate = "cmdUserStop";
	}

	@Override
	public void incSpeed() {
		curstate = "cmdIncSpeedState";
	}

	@Override
	public void decSpeed() {
		curstate = "cmdDecSpeedState";
	}

}