package et199tool.bean;

import i18n.DisplayInfo;
import et199tool.system.SystemConstants;

/**
    * Comments: This class is an implement of errors occur out of ET199Tool.
* JDK version used: <JDK1.6>
*/

public class KeyOuterError extends KeyError{

	@Override
	public boolean tackleError(ILogger logger) {
		// TODO Auto-generated method stub

		switch(errorCode)
		{
		case SystemConstants.ERROR_CERT_NOT_MATCHED:
		{
			logger.failInner(String.format(DisplayInfo.ERRORINFO.getString("Security_Certificate_Not_Valid_Error")));
			return false;
		}
		}

		return false;
	}

}
