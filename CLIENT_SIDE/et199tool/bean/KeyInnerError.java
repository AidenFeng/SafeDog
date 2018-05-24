package et199tool.bean;

import i18n.DisplayInfo;
import et199tool.system.SystemConstants;

/**
    * Comments: This class is an implement of errors occur within ET199Tool.
*/

public class KeyInnerError extends KeyError{

	/**
	* Description: This constructing method is mainly used by JNI to create a new InnerError data,
	*	so no param needs to be provided.
	*/
	public KeyInnerError() {
		super();
	}

	/** FieldName: actionCode
	 *
	 *  Description: 1. This field records the last action lead an error.
	 *  			 2. All actions can be checked in SystemConstants.java.
	 */
	private int actionCode;

	public int getActionCode() {
		return actionCode;
	}
	public void setActionCode(int actionCode) {
		this.actionCode = actionCode;
	}

	@Override
	public boolean tackleError(ILogger logger)
	{
		switch(this.getActionCode())
		{

		case SystemConstants.ACTION_CHECKKEY:
		{
			switch(this.errorCode)
			{
			case SystemConstants.ERROR_NO_DEVICE_FOUND:
			{
				logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_No_Device_Found_Error")));
				return false;
			}
			}

			logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Tool_Inner_Error")));
			return true;
		}

		case SystemConstants.ACTION_SIGN:
		{
			switch(this.errorCode)
			{
			case SystemConstants.ERROR_NO_PRIKEY_FOUND:
			{
				logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Certificate_Inner_Error")));
				return false;
			}
			}

			logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Tool_Inner_Error")));
			return true;
		}

		case SystemConstants.ACTION_VERIFY:
		{
			switch(this.errorCode)
			{
			case SystemConstants.ERROR_NO_PUBKEY_FOUND:
			{
				logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Certificate_Inner_Error")));
				return false;
			}

			case SystemConstants.ERROR_SIG_NOT_VALID:
			{
				logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Certificate_Not_Valid_Error")));
				return false;
			}
			}

			logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Tool_Inner_Error")));
			return true;
		}

		case SystemConstants.ACTION_LOGIN:
		{
			switch(this.errorCode)
			{
			case SystemConstants.ERROR_USERPIN_NOT_VALID:
			{
				logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Userpin_Not_Correct_Error")));
				return false;
			}
			}

			logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Tool_Inner_Error")));
			return true;
		}

		case SystemConstants.ACTION_GETCERT:
		{
			switch(this.errorCode)
			{
			case SystemConstants.ERROR_NO_OBJECT_FOUND:
			{
				logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Certificate_Not_Found_Error")));
				return false;
			}
			}

			logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Tool_Inner_Error")));
			return true;
		}

		case SystemConstants.ACTION_WRITE_TIME:
		{
			switch(this.errorCode)
			{
			case SystemConstants.ERROR_WRITING_PROCESS:
			{
				logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Tool_Inner_Error")));
				return false;
			}
			}

			logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Tool_Inner_Error")));
			return true;
		}

		case SystemConstants.ACTION_READ_TIME:
		{
			switch(this.errorCode)
			{
			case SystemConstants.ERROR_NO_OBJECT_FOUND:
			{
				logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Tool_Inner_Error")));
				return false;
			}

			case SystemConstants.ERROR_READING_PROCESS:
			{
				logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Tool_Inner_Error")));
				return false;
			}
			}

			logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Tool_Inner_Error")));
			return true;
		}
		}

		return true;
	}


}
