package model.interfaces.messages.data;

public interface IPacket {

	IPhoto getPhoto();
	IStatus getStatus();
	
	void setPhoto(IPhoto photo);
	void setStatus(IStatus status);
}
