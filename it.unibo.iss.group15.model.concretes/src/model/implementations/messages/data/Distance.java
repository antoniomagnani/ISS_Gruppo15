package model.implementations.messages.data;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import model.interfaces.messages.data.IStatusElement;

public class Distance implements IStatusElement {

	private double distance;
	
	public Distance(double distanceValue){
		this.distance = distanceValue;
	}
	
	public static IStatusElement readMessage(String content) throws Exception {
		Struct rt = (Struct) Term.createTerm(content);
		return new Distance(Double.parseDouble(rt.getArg(0).toString()));
	}

	@Override
	public double getValue() {
		return distance;
	}

	@Override
	public void setValue(double value) {
		this.distance = value;
	}
}
