package model.implementations.messages.data;

import alice.tuprolog.Struct;
import alice.tuprolog.Term;
import model.interfaces.messages.data.IPacket;
import model.interfaces.messages.data.IPhoto;
import model.interfaces.messages.data.IStatus;

public class Packet implements IPacket {
	
	private IPhoto photo;
	private IStatus status;
	
	public Packet(IPhoto photoValue, IStatus statusValue){
		this.photo = photoValue;
		this.status = statusValue;
	}
	
	public static String createMessage(IPacket packet) throws Exception{
		return "packet(" +
				Status.createMessage(packet.getStatus()) + "," +
				Photo.createMessage(packet.getPhoto()) + ")";
	}
	
	public static IPacket readMessage(String content) throws Exception{
		Struct rt = (Struct) Term.createTerm(content);
		IStatus status = Status.readMessage(rt.getArg(0).toString());
		IPhoto photo = Photo.readMessage(rt.getArg(1).toString());
		return new Packet(photo, status);
	}
	
	@Override
	public IPhoto getPhoto() {
		return photo;
	}

	@Override
	public IStatus getStatus() {
		return status;
	}

	@Override
	public void setPhoto(IPhoto photo) {
		this.photo = photo;
	}

	@Override
	public void setStatus(IStatus status) {
		this.status = status;
	}

}
