package it.unibo.android.smartDevice;

import it.unibo.android.SmartDeviceGEN.DashboardActivitySupport;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends DashboardActivitySupport {

	public static Handler handlerDashboard;
	private GoogleMap locTrackerMap;
	private MapFragment mMapFragment;

	final int RQS_GooglePlayServices = 1;
	private static final String TAG_MAPFRAGMENT = "TAG_LocTracker";

	// private final LatLng STARTING_POINT = new LatLng(44.139728, 12.243369);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard_layout);

		FragmentManager myFragmentManager = getFragmentManager();
		mMapFragment = (MapFragment) myFragmentManager
				.findFragmentByTag(TAG_MAPFRAGMENT);

		if (mMapFragment == null) {
			mMapFragment = MapFragment.newInstance();
			FragmentTransaction fragmentTransaction = myFragmentManager
					.beginTransaction();
			fragmentTransaction.add(R.id.dashboard_layout, mMapFragment,
					TAG_MAPFRAGMENT);
			fragmentTransaction.commit();
		}

		handlerDashboard = new DashboardActivityHandler(this,
				(TextView) findViewById(R.id.speedTxt),
				(ProgressBar) findViewById(R.id.speedPB),
				(TextView) findViewById(R.id.distanceTxt),
				(TextView) findViewById(R.id.fuelTxt),
				(ProgressBar) findViewById(R.id.fuelPB),
				(TextView) findViewById(R.id.positionTxt),
				(TextView) findViewById(R.id.altitudeTxt), mMapFragment,
				locTrackerMap);
	}

	@Override
	protected void doJob() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onResume() {
		super.onResume();

		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		if (resultCode == ConnectionResult.SUCCESS) {
			Toast.makeText(getApplicationContext(),
					"isGooglePlayServicesAvailable SUCCESS", Toast.LENGTH_LONG)
					.show();

			if (locTrackerMap == null) {
				locTrackerMap = mMapFragment.getMap();
				if (locTrackerMap != null) {
					locTrackerMap.setMyLocationEnabled(true);
					locTrackerMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				} else {
					Toast.makeText(getApplicationContext(), "cannot getMap!",
							Toast.LENGTH_LONG).show();
				}
			}

		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this,
					RQS_GooglePlayServices);
		}
	}

}
