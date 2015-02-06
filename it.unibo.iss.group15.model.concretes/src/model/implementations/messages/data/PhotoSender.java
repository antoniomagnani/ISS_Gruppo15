package model.implementations.messages.data;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.implementations.parameters.Parameters;
import model.interfaces.messages.data.IPhotoSender;

public class PhotoSender implements IPhotoSender {
	
	public static IPhotoSender createObject(){
		return new PhotoSender();
	}

	@Override
	public void sendPhotoThroughSocket(String ip) throws Exception {
		ip = ip.replace(" ", "");
		ip = ip.replace("\'", "");
		Socket socket = new Socket(ip, Parameters.socketPort);
		File imageMap = new File("image.jpg");
		int count;
		byte[] buffer = new byte[1024];

		OutputStream outStream = socket.getOutputStream();
		BufferedInputStream streamFile = new BufferedInputStream(new FileInputStream(imageMap));
		while ((count = streamFile.read(buffer)) >= 0) {
			outStream.write(buffer, 0, count);
			outStream.flush();
		}
		socket.close();
	}

	@Override
	public void receivePhotoThroughSocket(FileOutputStream fos) throws Exception {
		new Thread(new PhotoReceiver(fos)).start();
	}
	
	class PhotoReceiver implements Runnable {

		private FileOutputStream fos;
		
		public PhotoReceiver(FileOutputStream fosValue){
			this.fos = fosValue;
		}
		
		@Override
		public void run() {
			try {
				ServerSocket sSocket = new ServerSocket(Parameters.socketPort);
				Socket socket = sSocket.accept();
				
				byte[] buffer = new byte[1024];
				int count;
				InputStream in = socket.getInputStream();
				while((count=in.read(buffer)) >= 0){
					fos.write(buffer, 0, count);
				}
				fos.close();
				socket.close();
				sSocket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
