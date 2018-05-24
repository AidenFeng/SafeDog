package et199tool.encrytor;

import et199tool.bean.CryptData;
import et199tool.bean.Signature;

/**
    * Comments:		 This class is an implement of Encryptor using rsa algorithm.
* JDK version used:    <JDK1.6>
*/
public class RSAEncryptor implements Encryptor{

	//set encryptor type to rsa
	private final static String encryptorTypeRsa = "rsa";

	@Override
	public CryptData encrypt(String str) {
		// TODO Auto-generated method stub
		return EncryptExecutor.doEncrypt(str, encryptorTypeRsa, new CryptData());
	}

	@Override
	public String decrypt(CryptData data) {
		// TODO Auto-generated method stub
		return EncryptExecutor.doDecrypt(data, encryptorTypeRsa);
	}
	@Override
	public Signature sign(String str) {
		// TODO Auto-generated method stub
		return EncryptExecutor.doSign(str, encryptorTypeRsa, new Signature());
	}
	@Override
	public boolean verify(Signature signature) {
		// TODO Auto-generated method stub
		return EncryptExecutor.doVerify(signature, encryptorTypeRsa);
	}

}
