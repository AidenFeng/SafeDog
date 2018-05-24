package et199tool.util;

import et199tool.bean.KeyInnerError;

/**
* Module: Security
    * Comments: This class provides a set of system functions to visit ET199Tool.
    *		All methods in this class are native methods.
* JDK version used: <JDK1.6>
*/
public class SystemUtil {

	/**
	 * FunName: initTool
	 	* Description: A native method to do some initialization, like load library, set environment.
	 	* 		This method has to be called before any action to ET199Tool.
	 */
	public native static boolean initTool();

	/**
	 * FunName: openTool
	 	* Description: A native method to open ET199Tool.
	 	* 		This method has to be called before any action needs to interact with data within ET199Tool, like read or write data.
	 */
	public native static boolean openTool();

	/**
	 * FunName: closeTool
	 	* Description: Corresponding to openTool() method. A native method to close ET199Tool.
	 */
	public native static void closeTool();

	/**
	 * FunName: clearTool
	 	* Description: Corresponding to initTool() method. A native method to free something created in initialization.
	 */
	public native static void clearTool();

	/**
	 * FunName: isKeyAvailable
	 	* Description: A light weighted method to check if the key is available.
	 */
	/*
	 * The monitor process is replaced with hidxxx.dll which also used for capturer and simulator.
	 */
	//public native static boolean isKeyAvailable();

	/**
	 * FunName: getCerBytes
	 	* Description: A native function to get certificate within ET199Tool.
	 	* 		This certificate is stored by a byte array, needs to be transfered to a ByteArrayInputStream.
	 */
	public native static byte[] getCerBytes();

	/**
	 * FunName: getError
	 	* Description: A native function to get the latest ErrorReport when an error occurs in ET199Tool.
	 		* Input:
	 			* @param ErrorReport errorTemp
	 			* Description: A empty template for jni method to create a ErrorReport object.
	 		* Return:
	 			* @type: ErrorReport
	 			* Description: Returns the latest error from ET199Tool.
	 */
	public native static KeyInnerError getError(KeyInnerError errorTemp);

	public native static boolean writeTimeString(String data);
	public native static String readTimeString();
}
