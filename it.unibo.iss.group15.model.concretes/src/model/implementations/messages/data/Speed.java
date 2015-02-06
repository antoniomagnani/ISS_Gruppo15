package model.implementations.messages.data;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import model.implementations.parameters.Parameters;
import model.interfaces.messages.data.IStatusElement;

public class Speed implements IStatusElement  {

	private double speed; 
	
	public Speed(double speedValue){
		this.speed = setBoundSpeed(speedValue);
	}
	
	public static IStatusElement readMessage(String content) throws Exception {
		Struct rt = (Struct) Term.createTerm(content);
		return new Speed(Double.parseDouble(rt.getArg(0).toString()));
	}
	
	@Override
	public double getValue() {
		return speed;
	}
	
	
	@Override
	public void setValue(double value) {
		this.speed = setBoundSpeed(value);
	}

	private double setBoundSpeed(double speed) 
	{
		if(speed < Parameters.minSpeed)
			return Parameters.minSpeed;
		else if(speed > Parameters.maxSpeed)
			return Parameters.maxSpeed;
		
		return speed;
	}
}
