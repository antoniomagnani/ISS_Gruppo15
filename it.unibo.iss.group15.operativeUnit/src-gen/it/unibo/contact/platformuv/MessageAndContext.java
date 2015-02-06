/*
*  Generated by AN Unibo
*/
package it.unibo.contact.platformuv;
import it.unibo.is.interfaces.platforms.ILindaLike;
import it.unibo.is.interfaces.IMessage;
import it.unibo.is.interfaces.platforms.IMessageAndContext;
import alice.tuprolog.*;
import it.unibo.is.interfaces.protocols.IConnInteraction;
import java.io.FileInputStream;
import java.io.InputStream;


public class MessageAndContext implements IMessageAndContext{
 private IMessage msgIn;
 private String WorkerName;
 private ILindaLike core;
 private	String caller ;
 private	String msgId ;
 private	IConnInteraction support = null;
 private    boolean  useHttp = false;
	
	public MessageAndContext(ILindaLike core, IMessage msgIn, String WorkerName ){
		this.core = core;
		this.msgIn = msgIn;
		this.WorkerName = WorkerName;
		caller = msgIn.msgEmitter();
	    msgId =  msgIn.msgId();
support =  RunTimeKb.getSubjectInConnectionSupport(WorkerName, caller, msgId);
//System.out.println("%%% useHttp=" + useHttp);
  	}
	
	 public void replyToCaller(String answerMsg) throws Exception{
		 String channelId = channel3( msgIn.msgEmitter(),WorkerName,msgIn.msgId());
 	 String outMsg ="";
	 //System.out.println("replyToCaller " + useHttp + " support=" + support);
	 if( useHttp ){
  		 replyContentBareToCaller("HTTP/1.0 200 OK\r\n",false);
// 		 replyContentBareToCaller( "Server:  simple java httpServer\r\n", false  );
 		 replyContentBareToCaller( "\r\n", false  );
 		 replyContentBareToCaller(answerMsg,false);
 	 	 replyContentBareToCaller(null,false); //close the connection
 	 	 return;
 	 }
	 //The answerMsg (like any other msg content) must be structured as a term (AN Jun2012)
	 	answerMsg = MsgUtil.putInEnvelope(answerMsg);
     	//TOREMOVE answerMsg = alice.tuprolog.Term.createTerm("envelope('"+ answerMsg.replace("'", "\"") +" ')").toString(); //Check the syntax 		outMsg = bm( channelId ,WorkerName, msgIn.msgId(), answerMsg , msgIn.msgNum());
 		outMsg = bm( channelId ,WorkerName, msgIn.msgId(), answerMsg , msgIn.msgNum());
 		core.out(  outMsg   );
		String endMsg = bm( channelId ,WorkerName, msgIn.msgId(), IMessageAndContext.endMessage, msgIn.msgNum());
		core.out(  endMsg   );	 
	 }
	public void replyStepContentToCaller(String path) throws Exception {
 	 if( useHttp ){
 	 	 replyContentBareToCaller(path,true);
 	 	 return;
 	 }
	// 1) GET the caller and the msgId
		String caller = getReceivedMessage().msgEmitter();
		String msgId =  getReceivedMessage().msgId();
		if (caller.startsWith("proxy")) caller = caller.replace("proxy", "");
	// 2) OPEN THE FILE ON THE LOCAL NODE
		//String fName = dir + "\\" + fileName;
		FileInputStream inps = new FileInputStream(path);
		replyStepContentToCaller(inps);
	}
	/*
	* This operation allows users to have other input sources then files
	* However it is excluded form the IMessageAndContext interface 
	* since it dependents on InputStream that is a concept related to the implementation lanaguage 
	*/
	public void replyStepContentToCaller(InputStream inps ) throws Exception {
		String caller = getReceivedMessage().msgEmitter();
		String msgId =  getReceivedMessage().msgId();
 	// 3) WRAP A ContentSegmentable over the stream
		ContentSegmentable segmentableSender = new ContentSegmentable(inps);
		int bookSize = segmentableSender.getSize();
	// 4) Send the size to the caller (internal dispatch)
		String answer = "internalMsg_rowComm(" + bookSize + ")";
		//System.out.println("---> Source replyContent " + answer + " to " + caller );
		replyStepToCaller(answer);
	// 5) Wait for a ready-to-go message (internal dispatch)
		IMessage m = new Message(WorkerName + "_rowReady(ANYx1y2,rowReady,M,N)");
		//System.out.println("---> Source serve_rowReady " + m);
		IMessage inMsg = core.in("" + m);
		//System.out.println("---> Source serve_rowReady received " + inMsg);
	// 6) Get the connection support with the caller
		IConnInteraction support = RunTimeKb.getSubjectInConnectionSupport(
				WorkerName, caller, msgId);
		// System.out.println("---> Source We should send the content over " + support );
	// 7) SEND the file content over the network
		byte[] segm = segmentableSender.nextSegment();
			while (segm != null) {
	// System.out.println("))) Source sending " + segm.length );
				support.sendRaw(segm);
				segm = segmentableSender.nextSegment();
			} 
		segmentableSender.closeSending();
	}
	
	public void replyContentBareToCaller(String source, boolean isFile) throws Exception {
		if( source == null ) {
			support.closeConnection();
			return;
		}
 		if( isFile ){
 			replyContentBareToCaller( new FileInputStream(source) );
		}else{
  			support.sendRaw( source.getBytes() )   ;		
		}		
	}

	
	public void replyContentBareToCaller(FileInputStream requestedfile) throws Exception {
		ContentSegmentable segmentableSender = new ContentSegmentable(requestedfile);
//		int bookSize = segmentableSender.getSize();
//		System.out.println("---> Source We should send the content over " + support );
 		byte[] segm = segmentableSender.nextSegment();
			while (segm != null) {
				System.out.println("))) Source sending " + segm.length );
				support.sendRaw(segm);
				segm = segmentableSender.nextSegment();
			} 
		segmentableSender.closeSending();
	}
	
	 public synchronized void replyStepToCaller(String answerMsg) throws Exception{
		 String channelId = channel3( msgIn.msgEmitter(),WorkerName,msgIn.msgId());
 	 String outMsg ="";
 	 if( useHttp ){
 	 	 replyContentBareToCaller(answerMsg,false);
 	 	 return;
 	 }
	 //The answerMsg (like any other msg content) must be structured as a term (AN Jun2012)
		answerMsg = MsgUtil.putInEnvelope(answerMsg);
     	//TOREMOVE answerMsg = alice.tuprolog.Term.createTerm("envelope('"+ answerMsg.replace("'", "\"") +" ')").toString(); //Check the syntax 		outMsg = bm( channelId ,WorkerName, msgIn.msgId(), answerMsg , msgIn.msgNum());
	 		outMsg = bm( channelId ,WorkerName, msgIn.msgId(), answerMsg , msgIn.msgNum());
		 //System.out.println("replyToCaller " + outMsg);
			core.out(  outMsg   );	
	 }
	 public synchronized void replyEndToCaller() throws Exception{
		 String channelId = channel3( msgIn.msgEmitter(),WorkerName,msgIn.msgId());
 	 String outMsg ="";
 	 if( useHttp ){
 	 	 replyContentBareToCaller(null,false);
 	 	 return;
 	 }
	 		outMsg = bm( channelId ,WorkerName, msgIn.msgId(), IMessageAndContext.endMessage, msgIn.msgNum());
		 //System.out.println("replyToCaller " + outMsg);
			core.out(  outMsg   );	
	 }

 	 public IMessage getReceivedMessage(){
	 	return msgIn;
	 }
	 	 
	protected String channel3( String a1, String a2, String a3  ){
		return a1+"_"+a2+"_"+a3;
	}	

	protected String bm( String channelId, String workerName, 
		String msgId, String content, String msgNum  ){
		return channelId+"("+workerName+" , "+msgId+" , "+content+" , "+msgNum+")";
	}	
}	

