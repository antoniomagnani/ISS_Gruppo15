package model.interfaces.operativeUnit.dashboard;

import javax.swing.JPanel;

public interface IGaugeElement<ValueType> {
	public JPanel getPanel(); 
	public abstract void setValue(final ValueType value);
}