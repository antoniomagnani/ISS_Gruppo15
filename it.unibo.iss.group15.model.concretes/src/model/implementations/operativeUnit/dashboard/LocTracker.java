package model.implementations.operativeUnit.dashboard;

import model.implementations.parameters.Parameters;

import org.jdesktop.swingx.JXMapKit; // From swingx-ws-1.0.jar
import org.jdesktop.swingx.JXMapKit.DefaultProviders;
import org.jdesktop.swingx.JXMapViewer;
import org.jdesktop.swingx.mapviewer.GeoPosition;
import org.jdesktop.swingx.mapviewer.Waypoint;
import org.jdesktop.swingx.mapviewer.WaypointPainter;
import org.jdesktop.swingx.mapviewer.WaypointRenderer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class LocTracker extends GaugeElement<GeoPosition> {

	// JComponent della GUI relativa al locTracker
	private final JXMapKit jxmMap;

	private final List<Waypoint> waypoints;

	public static int locTrackerZoom = 1;
	public static int locTrackerWidth = 720;
	public static int locTrackerHeight = 194;
	


	/**
	 * Inizializza i componenti relativi all GUI
	 * 
	 * @param name
	 */
	protected LocTracker(final String name) {
		super(name);

		this.jxmMap = new JXMapKit();
		this.waypoints = new ArrayList<Waypoint>();

		this.setJxmMap();
		this.setPnlMain();
	}

	/**
	 * Metodo utilizzato da contact per ricevere una istanza dell'oggetto
	 * LocTracker
	 * 
	 * @param name
	 * @return
	 */
	public static LocTracker istantiate(final String name) {
		return new LocTracker(name);
	}

	/**
	 * Metodo relativo all'inizializzazione del componente JXMapKit. Viene
	 * impostato il provider di default da cui ricevere l'immagine e settata una
	 * posizione iniziale di partenza del drone a partire dalle configurazioni
	 * presente nel file Data
	 */
	protected void setJxmMap() {
		this.jxmMap.setDefaultProvider(DefaultProviders.OpenStreetMaps);

		// Imposto la posizione di partenza e lo Zoom del componente grafico di
		// visualizzazione della mappa
		this.jxmMap.setAddressLocation(new GeoPosition(
				Parameters.startLatitude, Parameters.startLongitude));
		this.jxmMap.setZoom(locTrackerZoom);

		this.jxmMap.setZoomSliderVisible(true);
		this.jxmMap.setZoomButtonsVisible(true);
		this.jxmMap.setMiniMapVisible(false);
		this.jxmMap.setAddressLocationShown(true);
		
		this.setValue(new GeoPosition(
				Parameters.startLatitude, Parameters.startLongitude));
	}

	/**
	 * Metodo utilizzato per organizzare il Layout dei componenti grafici
	 */
	protected void setPnlMain() {
		javax.swing.GroupLayout pnlLocTracker = new javax.swing.GroupLayout(
				this.getPanel());
		this.getPanel().setLayout(pnlLocTracker);

		pnlLocTracker
				.setHorizontalGroup(pnlLocTracker
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								pnlLocTracker
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												pnlLocTracker
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																false)
														.addComponent(
																this.jxmMap,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																locTrackerWidth,
																javax.swing.GroupLayout.PREFERRED_SIZE))));
		pnlLocTracker
				.setVerticalGroup(pnlLocTracker
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlLocTracker
										.createSequentialGroup()
										.addComponent(
												this.jxmMap,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												locTrackerHeight,
												Short.MAX_VALUE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)));
	}

	@Override
	public void setValue(final GeoPosition value) {
		// Updating the map according to the last observed position
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				LocTracker.this.jxmMap.setAddressLocation(value);
			}
		});
		addWaypoint(value);
	}

	private void addWaypoint(GeoPosition value) {
		double latitude = value.getLatitude();
		double longitude = value.getLongitude();
		
		this.waypoints.add(new Waypoint(latitude, longitude));

		@SuppressWarnings("rawtypes")
		WaypointPainter painter = new WaypointPainter();
		extracted(painter);
		
		painter.setRenderer(new WaypointRenderer() {

			@Override
			public boolean paintWaypoint(final Graphics2D g, final JXMapViewer map,
					final Waypoint waypoint) {
				g.setColor(Color.RED);
				g.drawLine(-5, -5, +5, +5);
				g.drawLine(-5, +5, +5, -5);
				return true;
			}
    	});

		this.jxmMap.getMainMap().setOverlayPainter(painter);
		
	}

	@SuppressWarnings("unchecked")
	private void extracted(@SuppressWarnings("rawtypes") WaypointPainter painter) {
		painter.setWaypoints(new HashSet<Waypoint>(this.waypoints));
	}

}
