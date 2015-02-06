package model.implementations.operativeUnit.dashboard;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.implementations.parameters.Parameters;
import model.interfaces.messages.data.IStatus;
import model.interfaces.operativeUnit.dashboard.ICmdDisplay;
import model.interfaces.operativeUnit.dashboard.IDroneControlDashboard;
import model.interfaces.operativeUnit.dashboard.IGaugeDisplay;

public class DroneControlDashboard extends JFrame implements IDroneControlDashboard{

private static final long serialVersionUID = 1L;
	
	private static DroneControlDashboard singleton = null;
	private static Lock getInstanceLock = new ReentrantLock(true);
	private static JMenuBar jmnBar = new JMenuBar();
    private static JMenu jmnFile = new JMenu();
    private static JMenuItem jmnItem = new JMenuItem();
	
	protected CmdDisplayGUI cmd_display;
	protected GaugeDisplayGUI gauge_display;
	protected Speedometer speedometerView;
	protected Fuelometer fuelometerView;
	protected Odometer odometerView;
	protected LocTracker locTrackerView;
	
	private static int window_width = 1070;
	private static int window_height = 630;
	
	/**
	 * Costruttore utilizzato per inizializzare i componenti GUI
	 */
	public DroneControlDashboard() throws Exception {
		super("DroneControlDashboard");
		
		try {
			// Istanziazione Command Display
			cmd_display = CmdDisplayGUI.istantiate("CommandDisplay");
			
			// Componenti del gaugeDisplay
			speedometerView = Speedometer.istantiate("Speedometer", "km/h", Parameters.minSpeed, Parameters.maxSpeed);	
		    fuelometerView = Fuelometer.istantiate("Fuelometer", "L", 0, Parameters.maxFuel);
		    odometerView = Odometer.istantiate("Odometer", "km", 0, 3.0);	
		    locTrackerView = LocTracker.istantiate("LocTracker");
	
		    // gaugeDisplay
		    gauge_display = GaugeDisplayGUI.istantiate("Gauge Display", fuelometerView, 
		    		speedometerView, odometerView, locTrackerView);
	
		    setTitle("Drone control dashboard");
		    setVisible(true);
		    //this.setLocationRelativeTo(null);

		    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	        setPreferredSize(new java.awt.Dimension(window_width, window_height));
	        setSize(new java.awt.Dimension(window_width, window_height));
	        setResizable(false);
	       
	        // Menu Item
	        jmnFile.setText("File");
	        jmnBar.add(jmnFile);
	        setJMenuBar(jmnBar);

	        jmnItem.setText("Archivio");
	        jmnItem.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	jmnItemActionPerformed(evt);
	            }
	        });
	        jmnFile.add(jmnItem);
		    
	        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addComponent(cmd_display.getPanelMain(), javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(gauge_display.getPanelMain(), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(cmd_display.getPanelMain(), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addComponent(gauge_display.getPanelMain(), javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	        );

	        pack();
	        
	        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void jmnItemActionPerformed(java.awt.event.ActionEvent evt) {     
		new MissionArchiveGUI(); 
	}
	
	/**
	 * Ritorna l'istanza corrispondente alla classe DroneControlDashboard
	 * @return
	 * @throws Exception
	 */
	public static DroneControlDashboard getInstance() throws Exception {
		getInstanceLock.lock();
		
		try {
			if (singleton == null) {
				singleton = new DroneControlDashboard();
			}
			return singleton;
		} finally {
			getInstanceLock.unlock();
		}
	}	

	/**
	 * Metodo utilizzato per impostare una istanza della classe CommandDisplay
	 */
	public void setCommandDisplay(ICmdDisplay value){
		cmd_display.setDisplay(value);
	}
	
	/**
	 * Metodo utilizzato per impostare una istanza della classe GaugeDisplay
	 */
	public void setGaugeDisplay(IGaugeDisplay value){
		gauge_display.setDisplay(value);
	}	
	
	/**
	 * Metodo utilizzato per aggiornare i dati della GUI
	 */
	public void updateGauge(IStatus value) {
		gauge_display.updateGauge(value);
	}

	/**
	 * Metodo utilizzato per interrompere la graficazione della GUI
	 */
	public void stopDroneControlDashboard() {
		cmd_display.stopCmdDisplay();
	}


}
