package et199tool.bean;

/**
    * Comments:		 This class defines a template for data signed by ET199Tool's private key.
* JDK version used:    <JDK1.6>
*/
public class Signature {

	/**
	* Description: This constructing method is mainly used by JNI to create a new Signature,
	*	so no param needs to be provided.
	*/
	public Signature() {
		super();
	}

	/** FieldName: signedStr
	 *  Description: The total string get from signing process. This string contains source string and signature.
	 * 		In dll, this string's is a char array, char's range is -128 - 127, the same as byte in java.
	 *
	 *  FieldName: signedLen
	 *  Description: The length of signedStr.
	 */
	private byte[] signedStr;
	private int signedLen;

	/** FieldName: signatureStr
	 *  Description: The signature string get from signing process.
	 * 		In dll, this string's is a char array, char's range is -128 - 127, the same as byte in java.
	 *
	 *  FieldName: signatureLen
	 *  Description: The length of signatureStr.
	 */
	private byte[] signatureStr;
	private int signatureLen;

	public byte[] getSignedStr() {
		return signedStr;
	}
	public void setSignedStr(byte[] signedStr) {
		this.signedStr = signedStr;
	}
	public int getSignedLen() {
		return signedLen;
	}
	public void setSignedLen(int signedLen) {
		this.signedLen = signedLen;
	}
	public byte[] getSignatureStr() {
		return signatureStr;
	}
	public void setSignatureStr(byte[] signatureStr) {
		this.signatureStr = signatureStr;
	}
	public int getSignatureLen() {
		return signatureLen;
	}
	public void setSignatureLen(int signatureLen) {
		this.signatureLen = signatureLen;
	}

}
