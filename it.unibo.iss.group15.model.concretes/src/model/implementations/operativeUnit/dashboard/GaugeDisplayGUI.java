package model.implementations.operativeUnit.dashboard;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.jdesktop.swingx.mapviewer.GeoPosition;

import model.implementations.operativeUnit.dashboard.Fuelometer;
import model.implementations.operativeUnit.dashboard.LocTracker;
import model.implementations.operativeUnit.dashboard.Odometer;
import model.implementations.operativeUnit.dashboard.Speedometer;
import model.interfaces.messages.data.IStatus;
import model.interfaces.operativeUnit.dashboard.IGaugeDisplay;


public class GaugeDisplayGUI {
	private final String name;

	private final Fuelometer fuelometer; // Fuelometer
	private final Speedometer speedometer; // Speedometer
	private final Odometer odometer; // Odometer
	private LocTracker locTracker; // LocTracker (posizione drone)

	// Panel principale, radice dei Panel Contenuti
	private final JPanel pnlGaugeMain;
	// Contenitore del LocTracker
	private final JPanel pnlGaugeLoc;
	// Contenitore OSF (Odometer, Speedometer e Fuelometer)
	private final JPanel pnlGaugeOSF;

	private final JLabel lblTitle; // Titolo - Gauge Display
	private final JLabel lblState; // Stato della Gauge

	protected IGaugeDisplay gauge_Display;

	protected GaugeDisplayGUI(final String name, final Fuelometer fuelometer,
			final Speedometer speedometer, final Odometer odometer,
			final LocTracker locTracker) {

		this.name = name;
		this.locTracker = locTracker;
		this.odometer = odometer;
		this.speedometer = speedometer;
		this.fuelometer = fuelometer;

		this.pnlGaugeMain = new JPanel();
		this.pnlGaugeLoc = new JPanel();
		this.pnlGaugeOSF = new JPanel();

		TitledBorder titled = new TitledBorder("Gauge Display");
		this.pnlGaugeMain.setBorder(titled);

		this.lblState = new JLabel();
		this.lblState.setFont(new Font("Arial", Font.ITALIC, 12));
		this.lblState.setForeground(Color.lightGray);
		this.lblState.setText("Gauge Display inizializzata.");
		this.lblTitle = new JLabel();
		this.lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
		this.lblTitle.setText(this.name);

		this.setPnlGaugeMain();
		this.setPnlGaugeLoc();
		this.setPnlGaugeOSF();
	}

	public JPanel getPanelMain() {
		return this.pnlGaugeMain;
	}

	public static GaugeDisplayGUI istantiate(final String name,
			final Fuelometer fuelometerView, final Speedometer speedometerView,
			final Odometer odometerView, final LocTracker locTrackerView) {
		return new GaugeDisplayGUI(name, fuelometerView, speedometerView,
				odometerView, locTrackerView);
	}

	protected void setPnlGaugeMain() {
		javax.swing.GroupLayout pnlGaugeMainLayout = new javax.swing.GroupLayout(
				this.getPanelMain());
		this.getPanelMain().setLayout(pnlGaugeMainLayout);

		pnlGaugeMainLayout.setHorizontalGroup(pnlGaugeMainLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(this.pnlGaugeOSF,
						javax.swing.GroupLayout.Alignment.TRAILING,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						pnlGaugeMainLayout
								.createSequentialGroup()
								.addComponent(this.pnlGaugeLoc,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE).addContainerGap())
				.addComponent(this.lblState,
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		pnlGaugeMainLayout
				.setVerticalGroup(pnlGaugeMainLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlGaugeMainLayout
										.createSequentialGroup()
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.pnlGaugeLoc,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												this.pnlGaugeOSF,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												this.lblState,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)));
	}

	private void setPnlGaugeLoc() {
		// TODO Auto-generated method stub
		javax.swing.GroupLayout pnlGaugeLocLayout = new javax.swing.GroupLayout(
				this.pnlGaugeLoc);
		this.pnlGaugeLoc.setLayout(pnlGaugeLocLayout);
		pnlGaugeLocLayout.setHorizontalGroup(pnlGaugeLocLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(this.locTracker.getPanel(),
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		pnlGaugeLocLayout.setVerticalGroup(pnlGaugeLocLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(this.locTracker.getPanel(),
						javax.swing.GroupLayout.PREFERRED_SIZE, 233,
						javax.swing.GroupLayout.PREFERRED_SIZE));
	}

	private void setPnlGaugeOSF() {
		// TODO Auto-generated method stub
		javax.swing.GroupLayout pnlGaugeOSFLayout = new javax.swing.GroupLayout(
				this.pnlGaugeOSF);
		this.pnlGaugeOSF.setLayout(pnlGaugeOSFLayout);
		pnlGaugeOSFLayout.setHorizontalGroup(pnlGaugeOSFLayout
				.createSequentialGroup()
				.addComponent(this.speedometer.getPanel(),
						javax.swing.GroupLayout.PREFERRED_SIZE, 250,
						javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(this.odometer.getPanel(),
						javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addPreferredGap(
						javax.swing.LayoutStyle.ComponentPlacement.RELATED)
				.addComponent(this.fuelometer.getPanel(),
						javax.swing.GroupLayout.PREFERRED_SIZE, 250,
						javax.swing.GroupLayout.PREFERRED_SIZE));
		pnlGaugeOSFLayout
				.setVerticalGroup(pnlGaugeOSFLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.BASELINE)
						.addGroup(
								pnlGaugeOSFLayout
										.createParallelGroup()
										.addComponent(
												this.fuelometer.getPanel(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												255,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(
												this.speedometer.getPanel(),
												javax.swing.GroupLayout.PREFERRED_SIZE,
												255,
												javax.swing.GroupLayout.PREFERRED_SIZE))
						.addComponent(this.odometer.getPanel(),
								javax.swing.GroupLayout.PREFERRED_SIZE, 255,
								javax.swing.GroupLayout.PREFERRED_SIZE));
	}

	public void updateGauge(IStatus value) {
		this.lblState.setText("Gauge Display aggiornata. Posizione: ("
				+ value.getPosition().getLatitude() + "°, "
				+ value.getPosition().getLongitude() + "°, "
				+ value.getPosition().getAltitude() + " m)");
		speedometer.setValue(value.getSpeed().getValue());
		fuelometer.setValue(value.getFuel().getValue());
		// problema con il tipo di dati - riguardare (noi usiamo double, sarebbe
		// meglio GeoPos?)
		GeoPosition geoP = new GeoPosition(value.getPosition().getLatitude(),
				value.getPosition().getLongitude());
		locTracker.setValue(geoP);
		odometer.setValue(value.getDistance().getValue());
	}

	public void setDisplay(IGaugeDisplay gaugeDisplay) {
		// TODO Auto-generated method stub
		this.gauge_Display = gaugeDisplay;
	}

}
