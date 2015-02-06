package model.implementations.operativeUnit.dashboard;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.xml.crypto.Data;

import model.implementations.parameters.*;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class MissionArchiveGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel pnlCommand;
	private JPanel pnlData;
	private JPanel pnlInfo;
	private JPanel pnlPhoto;
	private JComboBox<String> cbxMissionSelector;
	private JButton btnConfirmMission;
	private JButton btnPreviousPhoto;
	private JButton btnNextPhoto;
	
	
	private JLabel lblMissionName;
	private JLabel lblMissionDate;
	private JLabel lblPhotoName;
	private JLabel lblSpeed;
	private JLabel lblFuel;
	private JLabel lblDistance;
	private JLabel lblLatitude;
	private JLabel lblLongitude;
	private JLabel lblAltitude;
	
	private Immagine imgViewer;
	private HashMap<Integer, String> stuff;
	private ArrayList<PhotoComponents> stuffPhoto;
	
	private String urlSql;
	private Connection connection;
	private Properties userInfo;
	private int photoIndex = 0;
	
	public MissionArchiveGUI() {
		
		super();
		
		this.setTitle("Archive Mission drone");
		this.pnlCommand = new JPanel();
		this.pnlData = new JPanel();
		this.pnlInfo = new JPanel();
		this.pnlPhoto = new JPanel();
		
		this.lblMissionName = new JLabel("Mission name: ");
		this.lblMissionDate = new JLabel("Mission date: ");
		this.lblPhotoName = new JLabel();
		this.lblPhotoName.setHorizontalAlignment(SwingConstants.CENTER);
		this.lblSpeed = new JLabel("Speed: ");
		this.lblFuel = new JLabel("Fuel: ");
		this.lblDistance = new JLabel("Distance: ");
		this.lblLatitude = new JLabel("Latitude: ");
		this.lblLongitude = new JLabel("Longitude: ");
		this.lblAltitude = new JLabel("Altitude: ");
		
		this.btnConfirmMission = new JButton("Select Mission");
		this.btnPreviousPhoto = new JButton("<");
		this.btnPreviousPhoto.setEnabled(false);
		this.btnNextPhoto = new JButton(">");
		this.btnNextPhoto.setEnabled(false);
		
		this.imgViewer = new Immagine();
		this.cbxMissionSelector = new JComboBox<String>();
		this.stuff = new HashMap<Integer, String>();	
		this.stuffPhoto = new ArrayList<PhotoComponents>();
		
		this.setBounds(0, 0, 500, 700);
		this.setVisible(true);
		this.setResizable(false);
		

		
		// LAYOUT GUI
		GroupLayout pnlPhotoLayout = new GroupLayout(pnlPhoto);
		pnlPhoto.setLayout(pnlPhotoLayout);
		pnlPhotoLayout.setHorizontalGroup(
				pnlPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMissionName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblMissionDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblPhotoName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPhotoLayout.createSequentialGroup()
                .addComponent(btnPreviousPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imgViewer, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNextPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlPhotoLayout.setVerticalGroup(
        		pnlPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPhotoLayout.createSequentialGroup()
                .addComponent(lblMissionName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMissionDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPhotoName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imgViewer, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPreviousPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNextPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        
		/*
		GroupLayout pnlPhotoLayout = new GroupLayout(pnlPhoto);
		pnlPhoto.setLayout(pnlPhotoLayout);
		pnlPhotoLayout.setHorizontalGroup(
				pnlPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
	            .addComponent(imgViewer, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
	            .addComponent(lblMissionName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addComponent(lblMissionDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addComponent(lblPhotoName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	        );
		pnlPhotoLayout.setVerticalGroup(
			pnlPhotoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPhotoLayout.createSequentialGroup()
                .addComponent(lblMissionName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMissionDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPhotoName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imgViewer, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
		*/
		
        pnlInfo.setLayout(new BoxLayout(pnlInfo, BoxLayout.Y_AXIS));
        pnlInfo.add(Box.createVerticalStrut(10));
        pnlInfo.add(new JLabel("DRONE INFORMATION"));
        pnlInfo.add(Box.createVerticalStrut(10));
        pnlInfo.add(lblSpeed);
        pnlInfo.add(lblFuel);
        pnlInfo.add(lblDistance);
        pnlInfo.add(lblLatitude);
        pnlInfo.add(lblLongitude);
        pnlInfo.add(lblAltitude);
        
        GroupLayout pnlDataLayout = new GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
        		pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addComponent(pnlPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlDataLayout.setVerticalGroup(
        		pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addComponent(pnlPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        
        /*
        javax.swing.GroupLayout pnlDataLayout = new javax.swing.GroupLayout(pnlData);
        pnlData.setLayout(pnlDataLayout);
        pnlDataLayout.setHorizontalGroup(
        		pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addComponent(btnPreviousPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(pnlPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnNextPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13))
        );
        pnlDataLayout.setVerticalGroup(
        		pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDataLayout.createSequentialGroup()
                .addGroup(pnlDataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNextPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPreviousPhoto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        */
        
        GroupLayout pnlCommandLayout = new GroupLayout(pnlCommand);
        pnlCommand.setLayout(pnlCommandLayout);
        pnlCommandLayout.setHorizontalGroup(
            pnlCommandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCommandLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCommandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnConfirmMission, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxMissionSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlCommandLayout.setVerticalGroup(
            pnlCommandLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCommandLayout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(cbxMissionSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnConfirmMission, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlCommand, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCommand, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
		
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		// Setto i parametri di connessione al DB
		this.urlSql = "jdbc:mysql://localhost:3306/"+DbData.dbName;
	    this.userInfo = new Properties();
	    this.userInfo.put("user", DbData.dbUser);
	    this.userInfo.put("password", DbData.dbPass);
		
	    try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			this.connection = (Connection) DriverManager.getConnection(this.urlSql, userInfo);
			
			// Getting all IDs from 'mission'
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement("SELECT ID, Name FROM mission;");
			
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				stuff.put(rs.getInt("ID"), rs.getString("Name"));
	        }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    // Prelevo i record del resultSet e li inserisco all'interno della JComboBox
		Iterator<Integer> it = stuff.keySet().iterator();
		
		while (it.hasNext())
		{
			cbxMissionSelector.addItem(stuff.get(it.next()));
		}
		
		// Aggiungo l'Action Listener al bottone di conferma della missione
		btnConfirmMission.addActionListener(new ActionListener() {
			
		    @Override
		    public void actionPerformed (ActionEvent evt) {
		    	selectAllInfoAboutMission();
		    	
		    	if (stuffPhoto.size() > 1)
		    		btnNextPhoto.setEnabled(true);
		    }
		});
		
		btnNextPhoto.addActionListener(new ActionListener() {
			
		    @Override
		    public void actionPerformed (ActionEvent evt) {
		    	photoIndex++;
		    	if(photoIndex == stuffPhoto.size() - 1) {
		    		btnNextPhoto.setEnabled(false);
		    	}
		    	
		    	btnPreviousPhoto.setEnabled(true);
		    	loadPhoto();
		    }
		});
		
		btnPreviousPhoto.addActionListener(new ActionListener() {
			
		    @Override
		    public void actionPerformed (ActionEvent evt) {
		    	photoIndex--;
		    	if(photoIndex == 0) {
		    		btnPreviousPhoto.setEnabled(false);
		    	}
		    	
		    	btnNextPhoto.setEnabled(true);
		    	loadPhoto();
		    }
		});
	}
	
	private void selectAllInfoAboutMission() {		
		
		// Ricavo la Key relativa alla missione selezionata
        String item = (String) cbxMissionSelector.getSelectedItem();
        int key = -1;
        
        for (Entry<Integer, String> entry : stuff.entrySet()) {
            if (entry.getValue().equals(item)) {
            	key = entry.getKey();
            	break;
            }
        }
        btnPreviousPhoto.setEnabled(false);
        stuffPhoto.clear();
        photoIndex = 0;
        // Ricavo tutte le informazioni relative alla missione selezionata
        try {
			PreparedStatement statement = (PreparedStatement) connection.prepareStatement("SELECT DM.Date as 'DateMission', " +
					"DM.Folder as 'FolderName'," +
					"DS.Speed as 'Speed'," +
					"DS.Fuel as 'Fuel'," +
					"DS.Distance as 'Distance', " +
					"DS.Latitude as 'Latitude', " +
					"DS.Longitude as 'Longitude', " +
					"DS.Altitude as 'Altitude'," +
					"DS.PhotoName as 'NamePhoto' " +
					"FROM mission DM " +
					"INNER JOIN dronestatus DS " +
					"ON DM.ID = DS.MissionID " +
					"WHERE DM.ID = " + key +
					" ORDER BY DS.ID;");
			
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				PhotoComponents photo = new PhotoComponents();
				
				photo.InizializePhoto(resultSet.getString("FolderName"), 
						resultSet.getString("DateMission"),
						resultSet.getDouble("Speed"),
						resultSet.getDouble("Fuel"),
						resultSet.getDouble("Distance"),
						resultSet.getDouble("Latitude"),
						resultSet.getDouble("Longitude"),
						resultSet.getDouble("Altitude"),
						resultSet.getString("NamePhoto"));
				
				// Aggiungo nella lista l'elemento corrente appena inizializzato grazie ai record del ResultSet
				stuffPhoto.add(photo);
			}
			
			loadPhoto();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
	}
		
	private class Immagine extends Component {
		
		private static final long serialVersionUID = 1L;
		private BufferedImage img;
		
		private Immagine() {
			this.setMinimumSize(new Dimension(400, 400));
			this.setMaximumSize(new Dimension(400, 400));
		}
		
		private void paintPhoto(String photoName) {
			try {
				// Try to read file 'image.jpg'
				img = ImageIO.read(new File(photoName));	
				repaint();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Draw image
		public void paint(Graphics g) { 
			g.drawImage(img, 0, 0, null); 
		}
	}
	
	private class PhotoComponents
	{
		String missionFolder;
		String name;
		String missionDate;
		String date;
		double speed;
		double fuel;
		double distance;
		double latitude;
		double longitude;
		double altitude;
		
		private void InizializePhoto(String missionFolder, String missionDate, double speed,
				double fuel, double distance, double latitude, double longitude, double altitude, String name)
		{
			this.missionFolder = missionFolder;
			this.missionDate = missionDate;
			this.speed = speed;
			this.fuel = fuel;
			this.distance = distance;
			this.latitude = latitude;
			this.longitude = longitude;
			this.altitude = altitude;
			this.name = name;
		}
	}
	
	/**
	 * 	private JLabel lblMissionName;
		private JLabel lblMissionDate;
		private JLabel lblPhotoName;
		private JLabel lblSpeed;
		private JLabel lblFuel;
		private JLabel lblDistance;
		private JLabel lblLatitude;
		private JLabel lblLongitude;
		private JLabel lblAltitude;
	 */
	public void loadPhoto() {
		
		String photoNameAndPath = "";
		
		// Carico la prima photo sul Viewer
		photoNameAndPath = stuffPhoto.get(photoIndex).missionFolder + "/" + stuffPhoto.get(photoIndex).name + ".jpg";
		this.imgViewer.paintPhoto(photoNameAndPath);
		lblMissionName.setText("Mission name: " + stuffPhoto.get(photoIndex).missionFolder);
		lblMissionDate.setText("Mission date: " + stuffPhoto.get(photoIndex).missionDate);
		lblPhotoName.setText("" + stuffPhoto.get(photoIndex).name);
		lblSpeed.setText("Speed: " + stuffPhoto.get(photoIndex).speed +" Km/h");
		lblFuel.setText("Fuel: " + stuffPhoto.get(photoIndex).fuel + " lt");
		lblDistance.setText("Distance: " + stuffPhoto.get(photoIndex).distance + " Km");
		lblAltitude.setText("Altitude: " + stuffPhoto.get(photoIndex).altitude + " m");
		lblLatitude.setText("Latitude: " + stuffPhoto.get(photoIndex).latitude + " °");
		lblLongitude.setText("Longitude: " + stuffPhoto.get(photoIndex).longitude + " °");
	}
}
