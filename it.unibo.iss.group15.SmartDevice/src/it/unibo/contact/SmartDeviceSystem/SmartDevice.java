package it.unibo.contact.SmartDeviceSystem;

import android.os.Bundle;
import android.os.Message;
import it.unibo.android.SmartDeviceGEN.BaseActivity;
import it.unibo.android.smartDevice.DashboardActivity;
import it.unibo.android.smartDevice.R;


public class SmartDevice extends SmartDeviceSupport {

	private BaseActivity baseAct = null;

	public SmartDevice(String name) throws Exception {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public SmartDevice(String name, BaseActivity baseAct) throws Exception
	{
		super(name);
		this.baseAct = baseAct;	
	}
	
	/* 
	 * Invia una notifica su schermo per informare l'utente che il drone è partito
	 */
	@Override
	protected void sendNotification() throws Exception {
		baseAct.sendNotification(0, R.drawable.ic_dronemission, "Drone Message", "Drone in movement...", DashboardActivity.class);	
	}

	/* 
	 * Aggiornamento della dashboard secondo i parametri ricevuti dal drone
	 */
	@Override
	protected void refreshDashboard(double d_speed, double d_fuel, double d_distance,
			double d_pos_latitude, double d_pos_longitude, double d_pos_altitude)
			throws Exception {
		// TODO Auto-generated method stub
		if (DashboardActivity.handlerDashboard == null) return;
		
		Message d_message = DashboardActivity.handlerDashboard.obtainMessage();
		Bundle bundle_msg = new Bundle();
		bundle_msg.putBoolean("d_stop_user", false);
		bundle_msg.putBoolean("d_stop_fuel", false);
		bundle_msg.putInt("d_speed", (int)d_speed);
		bundle_msg.putDouble("d_fuel", d_fuel);
		bundle_msg.putDouble("d_distance", d_distance);
		bundle_msg.putDouble("d_latitude", d_pos_latitude);
		bundle_msg.putDouble("d_longitude", d_pos_longitude);
		bundle_msg.putDouble("d_altitude", d_pos_altitude);
		d_message.setData(bundle_msg);
		
		DashboardActivity.handlerDashboard.sendMessage(d_message);
	}

	/* 
	 * 	Stoppa la dashboard dopo aver ricevuto il messaggio di stop dal drone
	 */

	@Override
	protected void stopUserDashboard() throws Exception {
		if(DashboardActivity.handlerDashboard == null)
			return;
		Message droneMessage = DashboardActivity.handlerDashboard.obtainMessage();
		Bundle bundle_msg = new Bundle();
		bundle_msg.putBoolean("d_user_stop", true);
		droneMessage.setData(bundle_msg);
		
		DashboardActivity.handlerDashboard.sendMessage(droneMessage);	
		
	}

	@Override
	protected void stopFuelDashboard() throws Exception {
		if(DashboardActivity.handlerDashboard == null)
			return;
		Message droneMessage = DashboardActivity.handlerDashboard.obtainMessage();
		Bundle bundle_msg = new Bundle();
		bundle_msg.putBoolean("d_fuel_stop", true);
		droneMessage.setData(bundle_msg);
		
		DashboardActivity.handlerDashboard.sendMessage(droneMessage);			
	}
}
