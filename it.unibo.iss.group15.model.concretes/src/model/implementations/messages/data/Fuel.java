package model.implementations.messages.data;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import model.implementations.parameters.Parameters;
import model.interfaces.messages.data.IStatusElement;

public class Fuel implements IStatusElement {

	private double fuel;
	
	public Fuel(double fuelValue){
		this.fuel = setBoundFuel(fuelValue);
	}
	
	public static IStatusElement readMessage(String content) throws Exception {
		Struct rt = (Struct) Term.createTerm(content);
		return new Fuel(Double.parseDouble(rt.getArg(0).toString()));
	}
	
	@Override
	public double getValue() {
		return fuel;
	}

	@Override
	public void setValue(double value) {
		this.fuel = value;
	}
	
	private double setBoundFuel(double fuel) 
	{
		if(fuel < Parameters.minFuel)
			return Parameters.minFuel;
		else if(fuel > Parameters.maxFuel)
			return Parameters.maxFuel;
		
		return fuel;
	}

}
