
package myTest.idpTest;

/**
 * description  : HASH 암호화 처리.
 * Project Name : 통합 Identity 시스템
 * @author      : 임항호 (regento@empal.com)
 * @since       : 2009.3.10
 *
 * Revision history
 * Revision 0.1 : 2009.3.10, 임항호
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CryptoCode {

	public String getCryptoEncodeHex(String inputValue, String alg) throws Exception {

		if (inputValue == null) {
			throw new Exception("Can't conver to " + alg + " String value!!");
		}
		byte[] ret = digest(alg, inputValue.getBytes());
		String result = toHex(ret);
		return result;
	}

	public String getCryptoMD5EncodeHex(String inputValue) throws Exception {

		if (inputValue == null) {
			throw new Exception("Can't conver to Message Digest 5 String value!!");
		}
		byte[] ret = digest("MD5", inputValue.getBytes());
		String result = toHex(ret);
		return result;
	}

	public String getCryptoSHA1EncodeHex(String inputValue) throws Exception {

		if (inputValue == null) {
			throw new Exception("Can't conver to Secure Hash Standard String value!!");
		}
		byte[] ret = digest("SHA1", inputValue.getBytes());
		String result = toHex(ret);
		return result;
	}

	/*************** SHA-256 start*******************/
	public String getCryptoSHA256EncodeHex(String inputValue) throws Exception {

		if (inputValue == null) {
			throw new Exception("Can't conver to Message Digest 5 String value!!");
		}
		byte[] ret = digest("SHA-256", inputValue.getBytes());
		String result = toHex(ret);
		return result;
	}

	public String getCryptoSHA256EncodeHexLoop(String inputValue) throws Exception {

		if (inputValue == null) {
			throw new Exception("Can't conver to Message Digest 5 String value!!");
		}
		byte[] ret = digest("SHA-256", inputValue.getBytes());
		String result = toHex(ret);
		int count = 99;
		for (int i=0; i< count; i++){
			ret =digest("SHA-256", result.getBytes());
			result = toHex(ret);
		}
		return result;
	}
	/*************** SHA-256 end*******************/
	public byte[] digest(String alg, byte[] input) throws NoSuchAlgorithmException {

		MessageDigest md = MessageDigest.getInstance(alg);
		return md.digest(input);
	}

	private String toHex(byte hash[]) {

		StringBuffer buf = new StringBuffer(hash.length * 2);
		for (int i = 0; i < hash.length; i++) {
			int intVal = hash[i] & 0xff;
			if (intVal < 0x10) {
				// append a zero before a one digit hex
				// number to make it two digits.
				buf.append("0");
			}
			buf.append(Integer.toHexString(intVal));
		}
		return buf.toString();
	}

	/**
	 * 메세지 인증(MAC)을 위한 비밀키를 생성한다.
	 * @author yhchoi
	 * @return
	 * @throws Exception
	 */
	public String generateSecretKey() throws Exception {

		KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA1", "SunJCE");
		keygen.init(128);
		SecretKey skey = keygen.generateKey();
		return toHex(skey.getEncoded());
	}

	/**
	 * 해당 메세지를 축약(hashing)하여 인증 signature를 생성한다.
	 * @author yhchoi
	 * @param key 발급된 비밀키
	 * @param message 축약할 메세지
	 * @return
	 * @throws Exception
	 */
	public String generateMacSignature(String key, String message) throws Exception {

		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(skeySpec);

		byte[] result = mac.doFinal(message.getBytes("utf-8"));
		return toHex(result);
	}

	public String generateSalt(String key) throws Exception {

		String message = "gerotorl18nom";
		SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(skeySpec);
		byte[] result = mac.doFinal(message.getBytes("euc-kr"));
		return toHex(result);
	}

	public static void main(String[] args) throws Exception {
		String userPw = "Z8WZTx";
		String userSort = "f501f54f1196579e347f283adabfedbf68a0e4b1a8a25c30470cd30bada34a11";
		CryptoCode code = new CryptoCode();
		String result = code.getCryptoSHA256EncodeHex(userPw); // 사용자 평문 암호 값
		// String hashPw = Password.generate2(userSort, result);
		// System.out.println(hashPw);
	}
}
