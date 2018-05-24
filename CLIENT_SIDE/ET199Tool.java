import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;


import et199tool.*


import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import java.security.SignatureException;
import java.util.Map;

/**
* Module: Security
* Comments: 1. This class is a direct java API to visit ET199Tool.
* JDK version used: <JDK1.6>
*/
public class ET199Tool {
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String ENCODE_ALGORITHM = "SHA-256";
    public static final String PLAIN_TEXT = "true";

	static
	{
		//load library to enable et199 native methods.
		//Can print System.getproperty("java.library.path") to check if library is under system path
		System.loadLibrary("et199tool");
	}

	/** FieldName: keyError
	 *
	 *  Description: 1. If an error occurs, create a keyError.
	 *  			 2. KeyError can be created by platform, or returned by JNI.
	 */
	KeyError keyError = null;

	/** FieldName: encryptor
	 *
	 *  Description: 1. This field tells which algorithm ET199 is using for encryption.
	 *  			 2. Encryptor can only be set in initialization process.
	 */
	private Encryptor encryptor;

	private void setEncryptor(Encryptor encryptor)
	{
		this.encryptor = encryptor;
	}

	/** FieldName: isInit
	 *  Description: 1. This field tells if et199 tool has been init
	 *  			 2. This field can only be set true in the end of initET199 function
	 */
	private boolean isInit = false;

	private boolean firstCheck = true;

	private boolean curStatus = false;

	public boolean isInit() {
		return isInit;
	}

	/**
	 * FunName: userLogin
	 	* Description: Any process visit private domain need to login first.
	 	* Input:
	 		* @param String userPin
	 			* Description: userPin is the password for an ET199Tool.
	 	* Return:
	 		* @type: boolean
	 			* Description: 1. Returns a tag to tell if login successful.
	 			* 			   2. UserPin invalid or system error both return false.
	 */
	public boolean userLogin(String userPin)
	{
		if(UserUtil.userLogin(userPin) == true)
		{
			return true;
		}
		else
		{
			//if false tag returns, use getError process to check the reason.
			keyError = SystemUtil.getError(new KeyInnerError());
			return false;
		}
	}

	/**
	 * FunName: initET199
	 	* Description: This function needs to be called before any operation to ET199Tool.
	 	* Input:
	 		* @param Encryptor encryptor
	 	* Return:
	 		* @type: boolean
	 			* Description: Returns true if initialization successful.
	 */
	public boolean initET199(Encryptor encryptor)
	{
		//encryptor needs to be and can only be set in this function
		if(encryptor == null)
		{
			keyError = new KeyOuterError();
        	keyError.errorCode = SystemConstants.ERROR_NO_ENCRYPTOR_SET;

			return false;
		}
		else
		{
			setEncryptor(encryptor);
		}

		if(SystemUtil.initTool() == true)
		{
			//when init process ends, set isInit field to true
			isInit = true;
			return true;
		}
		else
		{
			//if false tag returns, use getError process to check the reason.
			keyError = SystemUtil.getError(new KeyInnerError());
			return false;
		}
	}

	/**
	 * FunName: clearET199
	 	* Description: The corresponding function to initET199, call as the last step to operate ET199Tool.
	 */
	public void clearET199()
	{
		SystemUtil.clearTool();
	}

	/**
	 * FunName: openET199
	 	* Description: Open this tool and login.
	 	* Input:
	 		* @param String userPin
	 	* Return:
	 		* @type: boolean
	 			* Description: Returns true if open tool and login successful.
	 */
	public boolean openET199(String userPin)
	{
		//open tool
		if(SystemUtil.openTool() != true)
		{
			keyError = SystemUtil.getError(new KeyInnerError());
			return false;
		}

		if(userPin != null)
		{
			//user login
			if(userLogin(userPin) == true)
			{
				return true;
			}
			else
			{
				keyError = SystemUtil.getError(new KeyInnerError());
				return false;
			}
		}
		else
		{
			//if userPin is null, create an outer error and return false
			keyError = new KeyOuterError();
			keyError.errorCode = SystemConstants.ERROR_USERPIN_NULL;

			return false;
		}
	}

	/**
	 * FunName: closeET199
	 	* Description: The corresponding function to openET199.
	 */
	public void closeET199()
	{
		SystemUtil.closeTool();
	}

	/**
	 * FunName: sign
	 	* Description: sign a string and return a signature.
	 	* Input:
	 		* @param String str
	 	* Return:
	 		* @type: Signature
	 			* Description: Returns a signature from JNI or null.
	 */
	public Signature sign(String str)
	{
		//call encryptor's own signing process to sign.
		Signature sig = encryptor.sign(str);

		if(sig == null)		//an error occurs in signing process
		{
			keyError = SystemUtil.getError(new KeyInnerError());
			return null;
		}
		else
		{
			return sig;
		}
	}

	/**
	 * FunName: verify
	 	* Description: verify if a signature is valid.
	 	* Input:
	 		* @param Signature sig
	 	* Return:
	 		* @type: boolean
	 			* Description: Returns true if signature is valid, false if not valid or any error occurs.
	 */
	public boolean verify(Signature sig)
	{
		//call encryptor's own verifying process to verify.
		if(encryptor.verify(sig))
		{
			return true;
		}
		else
		{
			//call getError to check the reason

			keyError = SystemUtil.getError(new KeyInnerError());
			return false;
		}
	}

	/**
	 * FunName: isKeyAvailable
	 	* Description: check if any key is inserted.
	 	* Return:
	 		* @type: boolean
	 			* Description: Returns true if at least one key is inserted, false if no key inserted or error occurs.
	 */

	/*
	 * SystemUtil.isKeyAvailable() == true is replaced with AuthKeyConnectStatus.getInstance().getStatus() == true
	 */
	public synchronized boolean isKeyAvailable()
	{
		if(AuthKeyConnectStatus.getInstance().getStatus() == true)

		{
			return true;
		}
		else
		{
			//call getError to check the reason
			keyError = SystemUtil.getError(new KeyInnerError());
			return false;
		}
	}

	/**
	 * FunName: isCerValid
	 	* Description: get a cert from ET199Tool, and check if this cert is valid.
	 	* Input:
	 		* @param String cerType
	 			* Description: This function read bytes from JNI, and use them to rebuild a license cert by cerType.
	 		* @param String cerPath
	 			* Description: Root cert's location path. Root cert is used to check if the license cert is valid.
	 	* Return:
	 		* @type: boolean
	 			* Description: Returns true if cert within ET199Tool is valid, false if not valid or error occurs.
	 */
	public boolean isCerValid(String cerType, String cerPath)
	{
		try
		{
			/*time control
			* Description: Each time before checking, compare time record inside and time now,
			* if time record inside is latter than time now, seems user has changed system time, return false.
			* Otherwise, do next check and write time now into ukey to replace time record.
			*/

// [Comment start]: The following code is commented out to accommodate the scenario when customer's machine has a future time.
// Below code write the last working time into the ET199 key. If this future time is written, the following logic will always fail the time check, even customer change the time back.
// We should allow such scenario. So the code below is commented.
//			String timeInKey =SystemUtil.readTimeString();
//			String timeNow = DateUtil.getCurrentDate();	//date format is like "MM/dd/yyyy"

//			if(timeInKey != null && !timeInKey.equals(""))	//not the first time to read time
//			{
//				/*
//				 * Changed by Liang, the original codes are show as follows
//				 *   if(timeInKey.compareTo(timeNow) > 0)	//time record is latter than time now
//				 *	 {
//				 *		return false;
//				 *	 }
//				 */
//
//				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//				Date date1 = sdf.parse(timeInKey);
//				Date date2 = sdf.parse(timeNow);
//				if(date1.compareTo(date2) > 0)	//time record is latter than time now
//				{
//					return false;
//				}
//				/*
//				 * Change end
//				 */
//				else if(date1.compareTo(date2) < 0)
//				{
//					SystemUtil.writeTimeString(timeNow);
//				}
//			}
//			else
//			{
//				SystemUtil.writeTimeString(timeNow);
//			}
//[Comment end.]

			CertificateFactory cf = CertificateFactory.getInstance(cerType);

			//ceof1 is root cert
			FileInputStream in1 = null;
			InputStream in2 = null;
			try{
		        in1 = new FileInputStream(cerPath);
		        Certificate ceof1 = cf.generateCertificate(in1);
		      //read cert bytes from ET199Tool, and rebuild license cert ceof2
		        byte[] cerBytes = SystemUtil.getCerBytes();
		        if(cerBytes == null)
		        {
		        	//cannot read cert from ET199Tool, check the reason
		        	keyError = SystemUtil.getError(new KeyInnerError());
		        	return false;
		        }

		        in2 = new ByteArrayInputStream(cerBytes);
		        Certificate ceof2 = cf.generateCertificate(in2);

		        //get root cert's pubkey
		        PublicKey pbk = ceof1.getPublicKey();
		        boolean pass = false;
		        try
		        {
		        	//license cert had signed by root cert's prikey, so use pubkey to verify
		        	ceof2.verify(pbk);

		        	if(isCerExpired(ceof2) == true)
		        	{
		        		pass = true;
		        	}
		        	else
		        	{
		        		pass = false;
		        	}
		        }
		        catch(SignatureException e)
		        {
		        	pass = false;

		        	//SignatureException is thrown when license cert not valid, create an outer error
		        	keyError = new KeyOuterError();
		        	keyError.errorCode = SystemConstants.ERROR_CERT_NOT_MATCHED;
		        }

		        return pass;
			}finally{
				IOUtils.closeQuietly(in1);
				IOUtils.closeQuietly(in2);
			}
		}
	    catch(Exception e)
	    {
	    	//any unknown error occurs, create an outer error
	    	keyError = new KeyOuterError();
	    	keyError.errorCode = SystemConstants.ERROR_UNKNOWN_ERROR;
	    }

		return false;
	}

	/**
	 * FunName: isCerExpired
	 	* Description: check if the cert inside expired.
	 	* Input:
	 		* @param Certificate cert
	 			* Description: The cert be checked.
	 	* Return:
	 		* @type: boolean
	 			* Description: Returns true if cert in ET199Tool is still not overdue, false if overdue or any error happens.
	 */
	public boolean isCerExpired(Certificate cert)
	{
		X509Certificate ceof = (X509Certificate) cert;

	    try
	    {
	    	ceof.checkValidity(Calendar.getInstance().getTime());	//compare key expired time and time now

	        return true;
	    }
	    catch(CertificateExpiredException e)	//if cert is expired
	    {
	    	return true;//This is a workaround to fix the key timeout issue caused by the limited time length stored in ET199 key.
		}
	    catch(Exception e)
	    {
	    	return false;
	    }
	}

	/**
	 * FunName: isCerComplete
	 	* Description: A complete process of cert checking, including checking if key pair is matched within ET199Tool,
	 	* 		and checking if cert is valid.
	 	* Input:
	 		* @param String cerType
	 			* Description: a param to pass to isCerValid.
	 		* @param String cerPath
	 			* Description: a param to pass to isCerValid.
	 	* Return:
	 		* @type: boolean
	 			* Description: Returns true if cert within ET199Tool is complete, false if not complete or error occurs.
	 */
	public boolean isCerComplete(String cerType, String cerPath)
	{
		//generate a random number for signing
		Double checkingNum = Math.random();

		//ET199Tool use prikey to sign this number
		Signature sig = sign(checkingNum.toString());
		if(sig == null)		//signing process fail, errors have tackled in sign function
		{
			return false;
		}

		//verify the signature returned
		//ET199Tool uses pubkey to verify. If ok, call isCerValid function.
		if(verify(sig) && isCerValid(cerType, cerPath))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * FunName: checkStatus
	 	* Description: 1. Wrapping a set of serial operations to check if cert within ET199Tool is copmlete.
	 	* 			   2. This function is set as synchronized to make sure only one thread executes inside et199
	 	* Return:
	 		* @type: boolean
	 			* Description: Returns true if cert is complete, false if not complete or error occurs.
	 */
	public synchronized boolean checkStatus()
	{
		boolean isOk = false;
		//if old key
		if(isKeyAvailable())
		{
			//get userPin to login
			String userPin = SystemConfig.USERPIN;

			//open tool
			if(openET199(userPin) == true)
			{
				//build root cert's path string
				String certPath = CGlobal.GetGlobalLocalHome() + "/" + SystemConfig.ROOT_CERT_LOCATION;
				isOk = isCerComplete(SystemConfig.CERT_TYPE, certPath);

				//if tool opened, close it
				firstCheck = false;
				closeET199();
			}
			else
			{
				isOk = false;
			}
		}//if superdog key

		 //else if (DogStatusManager.getInstance().updateDogStatus() == true){
		else if (invokeRequest() == true){
			isOk = true;
		}
		else
		{
			isOk = false;
		}

		//For accelerating the return
		if(isOk){
			setCurStatus(true);
		}else{
			setCurStatus(false);
		}

		return isOk;
	}

	public void setCurStatus(boolean curStat){
		curStatus = curStat;
	}

	public boolean getCurStatus(){
		return curStatus;
	}

    public static boolean verifySign(PublicKey publicKey, String plain_text, byte[] signed) {

        MessageDigest messageDigest;
        boolean SignedSuccess=false;
        try {
            messageDigest = MessageDigest.getInstance(ENCODE_ALGORITHM);
            messageDigest.update(plain_text.getBytes());
            byte[] outputDigest_verify = messageDigest.digest();
            //System.out.println("SHA-256 Encrypted-----》" +bytesToHexString(outputDigest_verify));
            java.security.Signature verifySign = java.security.Signature.getInstance(SIGNATURE_ALGORITHM);
            verifySign.initVerify(publicKey);
            verifySign.update(outputDigest_verify);
            SignedSuccess = verifySign.verify(signed);
            System.out.println("Verify Success?---" + SignedSuccess);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return SignedSuccess;
    }


	public boolean invokeRequest(){
        Socket socket = null;
       // Socket signSocket = null;
        //InputStream is = null;
        boolean rtval = false;
		try{
			socket = new Socket("localhost", 6345);
			//signSocket = new Socket("localhost", 8111);
//            is=socket.getInputStream();
//            BufferedReader br=new BufferedReader(new InputStreamReader(is));
//            String msg = br.readLine();

			//Get Signature
			/*********
			DataInputStream dIn = new DataInputStream(signSocket.getInputStream());
			int length = dIn.readInt();
			byte[] sing_byte = null;
			// read length of incoming message
			if(length>0) {
			    sing_byte = new byte[length];
			    dIn.readFully(sing_byte, 0, sing_byte.length); // read the message
			}
			*/

			//Get key from socket
			InputStream in = socket.getInputStream();
			ObjectInputStream objIn = new ObjectInputStream(in);
			String result = (String)objIn.readObject();
			PublicKey key = (PublicKey)objIn.readObject();
			byte[] sing_byte = (byte[])objIn.readObject();

			boolean authKey = verifySign(key, PLAIN_TEXT, sing_byte);

			if (!authKey){
				socket.close();
				//signSocket.close();
			}

            if (authKey & result.equals("true")){
            	rtval  = true;
            }else{
            	rtval  = false;
            }
			System.out.println("Yolo@Client Side:" + result);
			System.out.println("Yolo@Key Verify:" + authKey);

		}catch(Exception e){
			System.out.println("Holy@:" + e);
		}finally{
			try{
				//is.close();
				socket.close();
				//signSocket.close();
			}catch(Exception e){}

		}
		return rtval;
	}


	/**
	 * FunName: tackleError
	 	* Description: Tackle errors.
	 	* Input:
	 		* @param ILogger logger
	 			* Description: logger to print error messages.
	 */
	public void tackleError(ILogger logger)
	{
		if (keyError != null && keyError.tackleError(logger) == true) 	// If a system error occurs, reload tool
		{
			clearET199();
			Encryptor encryptor = new RSAEncryptor();
			initET199(encryptor);
		}

		//after an error tackled, set keyError tag to null
		keyError = null;
	}

	public static void main(String[] args)
	{
			//System.out.println(System.getProperty("java.library.path"));
			ET199Tool tool = new ET199Tool();
			Encryptor encryptor = new RSAEncryptor();
			tool.initET199(encryptor);
			tool.openET199("FORGITHUB");
		//	System.out.println(SystemUtil.writeTimeString("time", DateUtil.getCurrentDate()));

			for(int i = 0; i < 10; i ++)
			{
				System.out.println(tool.checkStatus());
			}

			//tool.clearET199();
			tool.closeET199();
		}
}
