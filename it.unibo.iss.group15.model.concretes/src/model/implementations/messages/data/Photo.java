package model.implementations.messages.data;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;

import model.interfaces.messages.data.IPhoto;
import model.interfaces.messages.data.IPosition;

public class Photo implements IPhoto {

	private String id;
	private InputStream inputStream;
	private int photoDimension;
	
	public Photo(String idPhoto, int dim){
		this.id = idPhoto;
		this.photoDimension = dim;
	}
	
	public static String createMessage(IPhoto photo){
		return "photo(" + photo.getPhotoId() + "," + photo.getPhotoDimension() + ")";
	}
	
	public static IPhoto readMessage(String msg){
		Struct message = (Struct) Term.createTerm(msg);
		return new Photo(message.getArg(0).toString(), Integer.parseInt(message.getArg(1).toString()));
	}
	
	public static IPhoto photoFromPosition(IPosition position, int idNumber) throws Exception{
		IPhoto photo;
		InputStream is;
		FileOutputStream fos;
		byte[] buffer = new byte[4096];
        int len, fileDim;
        URL url;
        URLConnection urlConn;
        String name;
        
		url = new URL("http://maps.googleapis.com/maps/api/streetview?size=400x400&location="+position.getLatitude()+","+position.getLongitude()+"&fov=90&heading=275&pitch=10&sensor=false");
		urlConn = url.openConnection();
		//creo l'oggetto buffered image
		is = urlConn.getInputStream();
		fileDim = is.available();
		fos= new FileOutputStream("image.jpg");
		
	    //Nome:  photo + idNumber + (latitude,longitude)
		name = "photo"+idNumber+"("+position.getLatitude()+","+position.getLongitude()+")";
		
		while ((len = is.read(buffer)) > 0) {
        	fos.write(buffer, 0, len);
        }
        fos.close();
        photo = new Photo(name, fileDim);
		photo.setStream(is);
		is.close();
		
		return photo;
	}



	@Override
	public void setStream(InputStream photoStream) {
		this.inputStream = photoStream;
	}

	@Override
	public void setPhotoId(String id) {
		this.id = id;
	}

	@Override
	public void setPhotoDimension(int dimension) {
		this.photoDimension = dimension;
	}

	@Override
	public String getPhotoId() {
		return id;
	}

	@Override
	public int getPhotoDimension() {
		return photoDimension;
	}
}
