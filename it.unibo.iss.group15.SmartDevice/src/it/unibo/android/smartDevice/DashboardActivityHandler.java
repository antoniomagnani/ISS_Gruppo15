package it.unibo.android.smartDevice;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivityHandler extends Handler {

	protected Activity dashboardActivity = null;
	protected TextView speedometerTxt = null;
	protected ProgressBar speedometerBar = null;
	protected TextView odometerTxt = null;
	protected TextView fuelometerTxt = null;
	protected ProgressBar fuelometerBar = null;
	protected TextView locTrackerTxt = null;
	protected TextView altitudeTxt = null;
	protected LatLng drone_position_latlng = null;
	protected MapFragment mMapFragment = null;
	protected GoogleMap locTrackerMap = null;
	protected int positionProgressive = 0;
	private String msgUserStop = "Mission Stopped by user";
	private String msgFuelStop = "Fuel low, mission stopped";

	private int drone_speed;
	private double drone_fuel;
	private double drone_distance;
	private double drone_latitude;
	private double drone_longitude;
	private double drone_altitude;
	private Marker drone_position_marker;

	public DashboardActivityHandler(Activity dashboardActivity,
			TextView speedometerTxt, ProgressBar speedometerBar,
			TextView odometerTxt, TextView fuelometerTxt,
			ProgressBar fuelometerBar, TextView locTrackerTxt,
			TextView altitudeTxt, MapFragment mMapFragment,
			GoogleMap locTrackerMap) {
		this.dashboardActivity = dashboardActivity;
		this.speedometerTxt = speedometerTxt;
		this.speedometerBar = speedometerBar;
		this.odometerTxt = odometerTxt;
		this.fuelometerTxt = fuelometerTxt;
		this.fuelometerBar = fuelometerBar;
		this.locTrackerTxt = locTrackerTxt;
		this.altitudeTxt = altitudeTxt;
		this.mMapFragment = mMapFragment;
		this.locTrackerMap = locTrackerMap;
	}

	// Gestione del messaggio: Subclasses must implement this to receive
	// messages.
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		Bundle msg_data = msg.getData();
		boolean drone_fuel_stop = msg_data.getBoolean("d_fuel_stop");
		boolean drone_user_stop = msg_data.getBoolean("d_user_stop");

		if (drone_fuel_stop) {
			Toast.makeText(dashboardActivity, msgFuelStop, Toast.LENGTH_LONG)
					.show();
			refreshLastMarker();
		} else if (drone_user_stop) {
			Toast.makeText(dashboardActivity, msgUserStop, Toast.LENGTH_LONG)
					.show();
			refreshLastMarker();
		} else {
			drone_speed = msg_data.getInt("d_speed");
			drone_fuel = msg_data.getDouble("d_fuel");
			drone_distance = msg_data.getDouble("d_distance");
			drone_latitude = msg_data.getDouble("d_latitude");
			drone_longitude = msg_data.getDouble("d_longitude");
			drone_altitude = msg_data.getDouble("d_altitude");
			refreshOSF(drone_speed, drone_fuel, drone_distance, drone_altitude);
			handlePosition(drone_latitude, drone_longitude);
		}
	}

	private void refreshOSF(int speed, double fuel, double distance,
			double altitude) {
		speedometerTxt.setText("Speed: " + speed + " km/h");
		speedometerBar.setProgress(speed);
		fuelometerTxt.setText("Fuel: " + String.format("%.2f", fuel) + " L");
		fuelometerBar.setProgress((int) fuel);
		odometerTxt.setText("Distance: " + String.format("%.2f", distance)
				+ " km");
		altitudeTxt.setText("Altitude: " + String.format("%.2f", altitude)
				+ " m");
	}

	private void handlePosition(double latitude, double longitude) {

		locTrackerTxt.setText("Position: " + String.format("%.2f", latitude)
				+ "° - " + String.format("%.2f", longitude) + "°");
		// AGGIORNA POSIZIONE SU MAPPA
		drone_position_latlng = new LatLng(latitude, longitude);

		locTrackerMap = mMapFragment.getMap();
		locTrackerMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
				drone_position_latlng, 17));

		drone_position_marker = locTrackerMap
				.addMarker(new MarkerOptions()
						.title("Drone Position "
								+ String.format("%03d", positionProgressive))
						.position(drone_position_latlng)
						.icon(BitmapDescriptorFactory
								.defaultMarker((positionProgressive++ == 0) ? BitmapDescriptorFactory.HUE_GREEN
										: BitmapDescriptorFactory.HUE_AZURE)));
	}

	private void refreshLastMarker() {
		if (drone_position_marker != null)
			drone_position_marker.remove();
		locTrackerMap = mMapFragment.getMap();
		drone_position_marker = locTrackerMap.addMarker(new MarkerOptions()
				.position(drone_position_latlng)
				.title("Drone Stop Position")
				.snippet(
						"Final Speed: " + drone_speed
								+ " km/h\nFinal Altitude: " + drone_altitude)
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
		refreshOSF(0, drone_fuel, drone_distance, 0);
	}
}
