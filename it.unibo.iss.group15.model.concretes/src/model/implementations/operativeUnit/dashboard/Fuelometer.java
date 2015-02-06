package model.implementations.operativeUnit.dashboard;

import eu.hansolo.steelseries.gauges.LinearBargraph;
import eu.hansolo.steelseries.gauges.Radial1Vertical;
import eu.hansolo.steelseries.tools.BackgroundColor;
import eu.hansolo.steelseries.tools.FrameDesign;
import eu.hansolo.steelseries.tools.FrameEffect;
import eu.hansolo.steelseries.tools.LcdColor;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.math.BigInteger;
import java.security.Policy.Parameters;


public class Fuelometer extends GaugeElement<Double> {
	//public static int fuelometerWidth = 480;
	//public static int fuelometerHeight = 160;
	public static int fuelometerWidth = 220;
	public static int fuelometerHeight = 220;
	
	protected String               	unitName;
	//private final LinearBargraph 	linearBargraph;
	private final Radial1Vertical radialVertical;
	private final BigInteger       	minWidth;
	private final BigInteger       	minHeight;
	private final double            minValue;
	private final double            maxValue;

	protected Fuelometer(final String name, final String unitName,
			final double minValueShown, final double maxValueShown) {
		super(name);

		this.unitName = unitName;

		this.minWidth = BigInteger.valueOf(fuelometerWidth);
		this.minHeight = BigInteger.valueOf(fuelometerHeight);

		this.minValue = minValueShown;
		this.maxValue = maxValueShown;

		//this.linearBargraph = new LinearBargraph();
		this.radialVertical = new Radial1Vertical();
		this.init();
	}

	public static Fuelometer istantiate(final String name,
			final String unitName, final double minValueShown,
			final double maxValueShown) {
		return new Fuelometer(name, unitName, minValueShown, maxValueShown);
	}

	@Override
	public void setValue(final Double value) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				//Fuelometer.this.linearBargraph.setValueAnimated(value);
				Fuelometer.this.radialVertical.setValueAnimated(value);
			}
		});
	}
	
	protected void init() {
		
		this.radialVertical.init(this.minWidth.intValue(),
				this.minHeight.intValue());
		this.radialVertical.setMinValue(this.minValue);
		this.radialVertical.setMaxValue(this.maxValue);

		this.radialVertical.setTitle(this.getName());
		this.radialVertical.setUnitString(this.unitName);

		this.radialVertical.setLcdDecimals(2);
		this.radialVertical.setDigitalFont(true);
		this.radialVertical.setThresholdBehaviourInverted(true);
		this.radialVertical.setThreshold((model.implementations.parameters.Parameters.minFuel)*10);
		this.radialVertical.setLcdColor(LcdColor.SECTIONS_LCD);
		this.radialVertical.setFrameEffect(FrameEffect.EFFECT_INNER_FRAME);
		this.radialVertical.setFrameDesign(FrameDesign.GLOSSY_METAL);
		this.radialVertical.setBackgroundColor(BackgroundColor.PUNCHED_SHEET);

		FlowLayout mainPanelLayout = new FlowLayout();
		mainPanelLayout.setAlignment(FlowLayout.CENTER);
		this.getPanel().setLayout(mainPanelLayout);
		this.getPanel().add(this.radialVertical);

		this.radialVertical.setPreferredSize(new Dimension(this.minWidth
				.intValue(), this.minHeight.intValue()));
	}

}
