package model.implementations.messages.operativeUnit;

import model.implementations.messages.data.Speed;
import model.interfaces.messages.data.IStatusElement;
import model.interfaces.messages.operativeUnit.IInitialSpeed;
import alice.tuprolog.Struct;
import alice.tuprolog.Term;

public class InitialSpeed implements IInitialSpeed{

	private IStatusElement speed;
	
	public InitialSpeed(IStatusElement speedSet){
		speed = speedSet;
	}
	@Override
	public IStatusElement getSpeed() {
		return speed;
	}

	@Override
	public void setInitialSpeed(IStatusElement speedValue) {
		this.speed = speedValue;
	}
	
	//legge messaggio velocità iniziale e parsa il messaggio
	public static IInitialSpeed readMessage(String msg) throws Exception{
		Struct message = (Struct) Term.createTerm(msg); 
		return new InitialSpeed(Speed.readMessage(message.getArg(0).toString()));		
	}
	
	public static String createMessage(IStatusElement speedValue){
		return "initialSpeed(value(" + speedValue.getValue() + "))";
	}

}
