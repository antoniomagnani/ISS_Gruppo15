/*
*  Generated by AN Unibo
*/
package it.unibo.contact.platformuv;
import java.util.Hashtable;
import it.unibo.is.interfaces.*;
import it.unibo.is.interfaces.platforms.ILindaLike;
import it.unibo.is.interfaces.protocols.IConnInteraction;

public class ConnReceiver extends ConnInputReceiver{
 	private Boolean withAnswer;
 	protected IMessage firstMsg;
	protected boolean isTwoWay = false;
	//For connection
	protected static Hashtable<String,Boolean> inputMsgs = new Hashtable<String,Boolean>();

	public ConnReceiver( 
				String name,IMessage firstMsg, IConnInteraction conn, 
				Boolean withAnswer, IOutputView view   ) throws Exception {
		super(name, conn, view);
		this.firstMsg = firstMsg;
		this.withAnswer = withAnswer;
 	}

	//For connection
	public ConnReceiver( 
			String name, IConnInteraction conn, IOutputView view  ) throws Exception {
		this(name, null, conn, null,view);	
	}

	public void run() {
		try {
		if( firstMsg != null ){ //else is a connection
			boolean withAnsw = RunTimeKb.getInputConnMsg( firstMsg.msgId() );
			println("FIRSTMSG " + firstMsg + "  withAnswer=" + withAnsw + " " + firstMsg.getMsgName() );
			if( firstMsg.getMsgName().contains(SupportFactory.connSetMsg) ){
				handleConnectionMsg(firstMsg);
				isTwoWay = true;
				receiveAndStore( firstMsg, true  );
			}
 			else{
				receiveAndStore( firstMsg, withAnsw  );
				doJob();
			}
		}else{
			isTwoWay = true;
			println(" *** !!!!!!!!! *** ConnReceiver " + name + " for CONNECTION PORT starts " );
		}
		//doJob();
		for( int i = 0; i<mexs.size(); i++){
			//System.out.println( " %%% ConnReceiver emits exception " + mexs.elementAt(i)  );
			try {
				core.out(mexs.elementAt(i));
			} catch (Exception e) {
				System.out.println( " %%% ConnReceiver error " + e.getMessage()  );
 				//e.printStackTrace();
			}
		}
		} catch (Exception e) {
			println( "WARNING (ConnReceiver):" + e.getMessage() + " ... going to terminate" );
			try {
				conn.closeConnection();
				//Generate an exception message to terminate the Sensor AcquireReply Thread 
				core.out( "coreToDSpace_space_coreCmd(space,coreCmd,'exception',N)" );		
			} catch (Exception e1) {
				println( "ERROR " + e1  );
			}
			goon = false;
		}
		println("ends ConnReceiver" );
	}		
	
 
	/*
	 * ===============================================================
	 * 				CONNECTION PART
	 * The first msg is SupportFactory.connSetMsg (setConnChannel)
	 * TODO: protocol independence
	 * ===============================================================
	 */
	protected void handleConnectionMsg( IMessage mm ) throws Exception{
		String sender    = mm.msgEmitter();
		String receiver  = getName();
		println("handleConnectionMsg " + sender+mm.msgId() );
//		Here we are receiver site: create a support for the out (BEFORE SENDING the ack)
 		ILindaLike sup = new ConnProtOut(receiver, mm.msgId(), sender, conn, view);
//		register the support in connChannel
		RunTimeKb.addSubjectConnectionSupport(receiver+sender+"conn" ,sup,view); 
//		RunTimeKb.addSubjectConnectionSupport(sender+receiver+"conn" ,sup,view); 
 	}
	
	public static void setInputMsg( String msgId, boolean withAnswer ){
		inputMsgs.put( msgId, withAnswer);
	}

}
