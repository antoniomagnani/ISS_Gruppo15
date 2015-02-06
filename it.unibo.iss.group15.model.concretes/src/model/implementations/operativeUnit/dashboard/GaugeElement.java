package model.implementations.operativeUnit.dashboard;

import javax.swing.JPanel;

import model.interfaces.operativeUnit.dashboard.IGaugeElement;

import java.awt.*;

public abstract class GaugeElement<ValueType> implements IGaugeElement<ValueType> {
	
	private final String name;
	private JPanel mainPanel;

	protected GaugeElement(final String name) {
		this.name = name;
	}

	@Override
	public JPanel getPanel() {
		if (this.mainPanel == null)
		{
			this.mainPanel = new JPanel();
			this.mainPanel.setLayout(new GridBagLayout());
		}
		return this.mainPanel;
	}
	
	protected String getName() {
		return this.name;
	}
}