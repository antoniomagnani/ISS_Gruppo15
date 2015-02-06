package model.interfaces.messages.data;

import java.io.InputStream;

public interface IPhoto {
	void setStream(InputStream photoStream);
	void setPhotoId(String id);
	void setPhotoDimension(int dimension);
	
	String getPhotoId();
	int getPhotoDimension();
}
