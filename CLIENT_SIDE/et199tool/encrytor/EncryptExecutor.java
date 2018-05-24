package et199tool.encrytor;

import et199tool.bean.CryptData;
import et199tool.bean.Signature;

/**
  * Comments: This class is the real executor of the methods defined in Encryptor interface.
    * 	All of the methods are native methods within this class.
    * 	Methods in dll call the corresponding encryptor to deal with the request by "encryptorType".

*/

public class EncryptExecutor {

	/**
	 * FunName: doEncrypt
	 	* Description: A native function to do encrypting.
	 	* 	The param CryptData data provides a template for jni method to create a CryptData object.

	 */
	public native static CryptData doEncrypt(String str, String encryptorType, CryptData data);

	/**
	 * FunName: doDecrypt
	 	* Description: A native function to do decrypting.
	 */
	public native static String doDecrypt(CryptData data, String encryptorType);

	/**
	 * FunName: doEncrypt
	 	* Description: A native function to do signing.
	 	* 	The param Signature sig provides a template for jni method to create a Signature object.
	 */
	public native static Signature doSign(String str, String entryptorType, Signature sig);

	/**
	 * FunName: doVerify
	 	* Description: A native function to do verifying.
	 */
	public native static boolean doVerify(Signature signature, String entryptorType);

}
