package model.implementations.messages.operativeUnit;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import model.interfaces.messages.operativeUnit.IChangeSpeed;

public class IncSpeed implements IChangeSpeed {
	
	
	public static String createMessage(){
		return "incSpeed";
	}
	
	public static IncSpeed readMessage(String msg) {
		Struct message = (Struct) Term.createTerm(msg);
		return new IncSpeed();
	}
	

}
