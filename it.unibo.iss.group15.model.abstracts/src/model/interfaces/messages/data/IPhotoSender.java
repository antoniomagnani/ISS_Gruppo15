package model.interfaces.messages.data;

import java.io.FileOutputStream;

public interface IPhotoSender {
	void sendPhotoThroughSocket(String ip) throws Exception;
	void receivePhotoThroughSocket(FileOutputStream fos) throws Exception;
}
