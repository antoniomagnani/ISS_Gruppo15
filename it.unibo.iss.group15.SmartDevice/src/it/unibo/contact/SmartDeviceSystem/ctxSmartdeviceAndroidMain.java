package it.unibo.contact.SmartDeviceSystem;


import it.unibo.android.SmartDeviceGEN.BaseActivity;

public class ctxSmartdeviceAndroidMain extends CtxSmartdeviceMain 
{
	private int deviceNumber = 0;
	protected BaseActivity baseAct = null;
	
	public ctxSmartdeviceAndroidMain(BaseActivity baseAct)
	{
		super();
		this.baseAct = baseAct;
	}
		
	protected void configureSubjects() {
		try 
		{
			//Nome differente per ogni device tramite un progressivo
			smartDevice = new SmartDevice("SD" + deviceNumber, baseAct);
			smartDevice.setEnv(env);
			smartDevice.initInputSupports();
			deviceNumber++;
			registerObservers();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

}
