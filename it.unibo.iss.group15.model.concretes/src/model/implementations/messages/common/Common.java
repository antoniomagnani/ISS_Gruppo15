package model.implementations.messages.common;

import model.interfaces.messages.common.ICommon;

public class Common implements ICommon {

	@Override
	public String currentStatusMessage() {
		return "currentStatus";
	}

	@Override
	public String start() {
		return "start";
	}
	
	public static ICommon createObject(){
		return new Common();
	}

	@Override
	public String stop() {
		return "stop";
	}

}
