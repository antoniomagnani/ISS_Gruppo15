package it.unibo.android.smartDevice;

import it.unibo.contact.SmartDeviceSystem.ctxSmartdeviceAndroidMain;
import it.unibo.android.SmartDeviceGEN.*;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends  MainActivitySupport{
	protected ctxSmartdeviceAndroidMain context = null;

	@Override
	protected void waitDrone(String inputValue, Bundle b) throws Exception {
		//crea contesto
		context = new ctxSmartdeviceAndroidMain(this);
		context.doJob();
		
		((Button)findViewById(R.id.btnWait)).setEnabled(false);
		this.moveTaskToBack(false);
	}

	@Override
	protected void doJob() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onBackPressed() {
		onDestroy();
		if (context!=null){
			context.terminate();
		}
	}
}
