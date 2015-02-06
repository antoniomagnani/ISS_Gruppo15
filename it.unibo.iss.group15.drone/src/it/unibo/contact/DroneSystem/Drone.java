package it.unibo.contact.DroneSystem;

import model.implementations.messages.data.Packet;
import model.implementations.messages.data.Photo;
import model.implementations.messages.data.PhotoSender;
import model.interfaces.messages.data.IPacket;
import model.interfaces.messages.data.IPhoto;
import model.interfaces.messages.data.IPhotoSender;

public class Drone extends DroneSupport{
	
	private int idPhoto = 0;

	public Drone(String name) throws Exception {
		super(name);
	}

	@Override
	protected void waitDTF(long waitTime) throws Exception {
		Thread.sleep(waitTime);
	}

	@Override
	protected IPacket createPacket() throws Exception {
		IPhoto photo = Photo.photoFromPosition(currentStatus.getPosition(), idPhoto++);
		IPacket packet = new Packet(photo, currentStatus);
		
		return packet;
	}
	
	@Override
	protected void sendPhoto() throws Exception{
		IPhotoSender photoSender = new PhotoSender();
		photoSender.sendPhotoThroughSocket(serverIP);
	}

}
