package model.implementations.operativeUnit.dashboard;

import eu.hansolo.steelseries.gauges.Radial;
import eu.hansolo.steelseries.tools.BackgroundColor;
import eu.hansolo.steelseries.tools.FrameDesign;
import eu.hansolo.steelseries.tools.FrameEffect;
import eu.hansolo.steelseries.tools.LcdColor;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.math.BigInteger;

public class Speedometer extends GaugeElement<Double> {

	private final String     unitName;
	protected final Radial   radialValue;
	private final BigInteger minWidth;
	private final BigInteger minHeight;
	private final double     minValue;
	private final double     maxValue;
	
	public static int speedometerWidth = 220;
	public static int speedometerHeight = 220;

	protected Speedometer(final String name, final String unitName,
			final double minValueShown, final double maxValueShown) {
		
		super(name);
		this.unitName = unitName;

		this.minWidth = BigInteger.valueOf(speedometerWidth);
		this.minHeight = BigInteger.valueOf(speedometerHeight);

		this.minValue = minValueShown;
		this.maxValue = maxValueShown;

		this.radialValue = new Radial();
		this.init();
	}

	public static Speedometer istantiate(final String name, final String unitName, 
			final double minValueShown, final double maxValueShown) {

		return new Speedometer(name, unitName, minValueShown, maxValueShown);
	}
	
	@Override
	public void setValue(final Double value) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Speedometer.this.radialValue.setValueAnimated(value);
			}
		});
	}

	protected void init() {
		this.radialValue.init(this.minWidth.intValue(), this.minHeight.intValue());

		this.radialValue.setMinValue(0);
		this.radialValue.setMaxValue(this.maxValue);

		this.radialValue.setTitle(this.getName());
		this.radialValue.setUnitString(this.unitName);

		this.radialValue.setTrackVisible(true);
		this.radialValue.setMinMeasuredValueVisible(true);
		this.radialValue.setMaxMeasuredValueVisible(true);

		//this.radialValue.setTrackStart((this.maxValue - this.minValue) * 0.7);
		this.radialValue.setTrackStart(this.minValue);
		this.radialValue.setTrackStop(this.maxValue);

		this.radialValue.setDigitalFont(true);
		this.radialValue.setLcdColor(LcdColor.SECTIONS_LCD);
		this.radialValue.setFrameEffect(FrameEffect.EFFECT_INNER_FRAME);
		this.radialValue.setFrameDesign(FrameDesign.GLOSSY_METAL);
		this.radialValue.setBackgroundColor(BackgroundColor.PUNCHED_SHEET);

		FlowLayout mainPanelLayout = new FlowLayout();
		mainPanelLayout.setAlignment(FlowLayout.CENTER);
		
		this.getPanel().setLayout(mainPanelLayout);
		this.getPanel().add(this.radialValue);

		this.radialValue.setPreferredSize(new Dimension(this.minWidth.intValue(), this.minHeight.intValue()));
	}
  
}
