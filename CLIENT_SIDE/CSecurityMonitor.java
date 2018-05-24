import et199tool.encrytor.Encryptor;
import et199tool.encrytor.RSAEncryptor;

public class CSecurityMonitor{
	private static CSecurityMonitor mInstance = null;

	private final ET199Tool tool;
	private static ET199StartThread startTh = null;

	static {
		ET199Tool tool = new ET199Tool();
		mInstance = new CSecurityMonitor(tool);

		//Description: start et199 tool using a thread
		startTh = mInstance.new ET199StartThread(tool);
		startTh.start();
	}

	public ET199Tool getTool(){

		/**
		 * Description: check if et199 init thread is finished, if not, wait it to end
		 */
		if(tool.isInit() == false)
		{
			try
			{
				startTh.join();
			} catch (Exception e) { System.err.println(e.getMessage()); }
		}

		return tool;
	}

	public void reloadTool(){
		Encryptor encryptor = new RSAEncryptor();
		tool.initET199(encryptor);
	}

	private CSecurityMonitor(ET199Tool tool) {
		this.tool = tool;
	}

	public static CSecurityMonitor getInstance() {

		return mInstance;
	}

	/**
	* Module: Security
	    * Comments: 1. This inner class is used to start et199 tool asynchronously

	* JDK version used: <JDK1.6>
	*/
	private class ET199StartThread extends Thread{

		/** FieldName: tool
		 *
		 *  Description: 1. This field has to be set when thread is created and null is not allowed
		 */
		ET199Tool tool;

		public ET199StartThread(ET199Tool tool)
		{
			this.tool = tool;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			//init et199 tool
			Encryptor encryptor = new RSAEncryptor();
			tool.initET199(encryptor);
		}
	}

}
