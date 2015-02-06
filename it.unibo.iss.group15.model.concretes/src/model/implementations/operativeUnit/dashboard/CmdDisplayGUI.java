package model.implementations.operativeUnit.dashboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.implementations.messages.data.Speed;
import model.implementations.parameters.Parameters;
import model.interfaces.messages.data.IStatusElement;
import model.interfaces.operativeUnit.dashboard.ICmdDisplay;

public class CmdDisplayGUI {
	private final String name;
	private final JPanel pnlMain;
	private final JPanel pnlCmd;

	private final JTextField txtFieldSpeed;
	private final JLabel lblTitle;
	private final JLabel lblState;
	private final JLabel lblSetSpeed;

	private final JButton btnDecSpeed;
	private final JButton btnIncSpeed;
	private final JButton btnSetSpeed;
	private final JButton btnStart;
	private final JButton btnStop;

	protected ICmdDisplay CDisplay;

	public CmdDisplayGUI(final String name) {

		this.name = name;
		this.pnlMain = new JPanel();
		this.pnlCmd = new JPanel();

		TitledBorder titled = new TitledBorder("Command Display");
		this.pnlMain.setBorder(titled);

		this.txtFieldSpeed = new JFormattedTextField();
		this.lblTitle = new JLabel();
		this.lblState = new JLabel();
		this.lblSetSpeed = new JLabel();
		this.btnIncSpeed = new JButton();
		this.btnDecSpeed = new JButton();
		this.btnSetSpeed = new JButton();
		this.btnStart = new JButton();
		this.btnStop = new JButton();

		// Imposto la velocita al valore fornito da specifiche
		this.txtFieldSpeed.setText("" + Parameters.defaultSpeed);

		this.lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
		this.lblTitle.setText(this.name);
		this.lblState.setToolTipText("Commands state");
		this.lblState.setFont(new Font("Arial", Font.ITALIC, 12));
		this.lblState.setForeground(Color.lightGray);
		this.lblState.setText("Command display inizializzata.");

		this.lblSetSpeed.setFont(new Font("Arial", Font.ITALIC, 12));
		this.lblSetSpeed.setForeground(Color.lightGray);
		this.lblSetSpeed.setText("Inserire velocità(60 - 120 km/h)");

		// BtnDecSpeed
		this.btnDecSpeed.setToolTipText("Decrementa la velocità del drone di "
				+ Parameters.DS + " km/h.");
		this.btnDecSpeed.setText("Dec Speed");
		this.btnDecSpeed.setEnabled(false);
		this.btnDecSpeed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				decSpeed();
			}
		});

		// BtnIncSpeed
		this.btnIncSpeed.setToolTipText("Incrementa la velocità del drone di "
				+ Parameters.DS + " km/h.");
		this.btnIncSpeed.setText("Inc Speed");
		this.btnIncSpeed.setEnabled(false);
		this.btnIncSpeed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				incSpeed();
			}
		});

		// BtnStop
		this.btnSetSpeed
				.setToolTipText("Imposta la velocità di crociera del drone.");
		this.btnSetSpeed.setText("Set Speed");
		this.btnSetSpeed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				setSpeed(txtFieldSpeed.getText());
			}
		});

		// BtnStart
		this.btnStart.setToolTipText("Avvia il drone.");
		this.btnStart.setText("Start Mission");
		this.btnStart.setEnabled(false);
		this.btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				start();
			}
		});

		// BtnStop
		this.btnStop.setToolTipText("Ferma il drone.");
		this.btnStop.setText("Stop Mission");

		// Abilito il pulsante solo a seguito di SetSpeed
		this.btnStop.setEnabled(false);

		this.btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				stop();
			}
		});
		this.setPnlMain();
		this.setPnlCmd();

	}
	
	public JPanel getPanelMain() {
		return this.pnlMain;
	}
	
	public static CmdDisplayGUI istantiate(final String name) {
		return new CmdDisplayGUI(name);
	}
	
	public void setDisplay(ICmdDisplay value) {
		this.CDisplay = value;
	}

	private void setPnlCmd() {
		// TODO Auto-generated method stub
		javax.swing.GroupLayout pnlCmdLayout = new javax.swing.GroupLayout(
				pnlCmd);
		pnlCmd.setLayout(pnlCmdLayout);
		pnlCmdLayout
				.setHorizontalGroup(pnlCmdLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlCmdLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												pnlCmdLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																btnIncSpeed,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																btnDecSpeed,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																btnStart,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(
																txtFieldSpeed,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																lblSetSpeed,
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																pnlCmdLayout
																		.createSequentialGroup()
																		.addGap(0,
																				0,
																				Short.MAX_VALUE)
																		.addComponent(
																				btnStop,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				240,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap())
						.addGroup(
								pnlCmdLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												pnlCmdLayout
														.createSequentialGroup()
														.addContainerGap()
														.addComponent(
																btnSetSpeed,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addContainerGap())));
		pnlCmdLayout
				.setVerticalGroup(pnlCmdLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								pnlCmdLayout
										.createSequentialGroup()
										.addGap(80, 80, 80)
										.addComponent(
												lblSetSpeed,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												30,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addComponent(
												txtFieldSpeed,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												30,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(57, 57, 57)
										.addComponent(
												btnStart,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												33,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												btnDecSpeed,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												33,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												btnIncSpeed,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												33,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(
												btnStop,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												33,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap(143, Short.MAX_VALUE))
						.addGroup(
								pnlCmdLayout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(
												pnlCmdLayout
														.createSequentialGroup()
														.addGap(150, 150, 150)
														.addComponent(
																btnSetSpeed,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																33,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addContainerGap(270,
																Short.MAX_VALUE))));

	}

	private void setPnlMain() {
		// TODO Auto-generated method stub
		this.getPanelMain().setLayout(new BorderLayout());
		//this.getPanelMain().add(this.lblTitle, BorderLayout.PAGE_START);
	    this.getPanelMain().add(this.pnlCmd, BorderLayout.CENTER);
	    this.getPanelMain().add(this.lblState, BorderLayout.PAGE_END);
	}

	protected void stop() {
		// TODO Auto-generated method stub
		stopCmdDisplay();
		CDisplay.stopMission();
	}

	public void stopCmdDisplay() {
		// TODO Auto-generated method stub
		this.btnSetSpeed.setEnabled(false);
		this.btnStart.setEnabled(false);
		this.btnStop.setEnabled(false);
		this.btnIncSpeed.setEnabled(false);
		this.btnDecSpeed.setEnabled(false);
		this.lblState.setText(String.format("Stopped Display"));
	}

	protected void start() {
		// TODO Auto-generated method stub
		// Abilita i pulsanti di settaggio velocità
		this.btnStart.setEnabled(false);
		this.btnSetSpeed.setEnabled(false);
		this.btnStop.setEnabled(true);
		this.btnIncSpeed.setEnabled(true);
		this.btnDecSpeed.setEnabled(true);
		CDisplay.startMission();
		this.lblState
				.setText(String.format("Missione iniziata correttamente."));
	}

	private void decSpeed() {
		// TODO Auto-generated method stub
		CDisplay.decSpeed();
		this.lblState.setText(String.format("Dec correctly sent."));
	}

	private void incSpeed() {
		// TODO Auto-generated method stub
		CDisplay.incSpeed();
		this.lblState.setText(String.format("Inc correctly sent."));
	}

	private void setSpeed(final String value) {
		try {
			double speedValue = Double.parseDouble(value);
			/*IStatusElement speed = new Speed(speedValue); // Check
			speed.setValue(speedValue);*/
			if(!checkSpeed(speedValue)){
				this.lblState.setText(String.format("Speed initial value not correct."));
				return;
			}
				
			CDisplay.setSpeed(speedValue);
			this.btnStart.setEnabled(true);
			this.lblState.setText(String.format("Command correctly sent."));

		} catch (Exception e) {
			this.btnStart.setEnabled(false);
			this.lblState.setText("Invalid input speed.");
			this.txtFieldSpeed.setText("" + Parameters.defaultSpeed);
		}
	}
	
	private boolean checkSpeed(double speedValue){
		if(speedValue < Parameters.minSpeed || speedValue > Parameters.maxSpeed)
			return false;
		return true;
	}
}
