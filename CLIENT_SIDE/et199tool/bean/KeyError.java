package et199tool.bean;


/**
    * Comments: 1. This class defines a basic abstract class for errors.
    * 			2. Errors contains innerErrors(within ET199Tool) and outerErrors(out of ET199Tool).
* JDK version used: <JDK1.6>

*/

public abstract class KeyError {

	/** FieldName: errorCode
	 *
	 *  Description: 1. ErrorCode is recorded in SystemConstants class, contains all of the errors' ids occur.
	 *  			 2. All of the results beyond our expectations(like null or false) are regarded as errors.
	 */
	public int errorCode;

	/**
	 * FunName: tackleError
	 	* Description: This function is used as a handle to tackle errors.
	 	* Input:
	 		* @param ILogger logger
	 			* Description: logger to print error messages
	 	* Return:
	 		* @type: boolean
	 			* Description: Returns a tag to tell if this tool needs to be reloaded
	 */
	public abstract boolean tackleError(ILogger logger);

}
