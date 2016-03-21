package myTest.idpTest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Base64;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HmacSha1Signature {

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private static Formatter formatter;

	/**
	 * @description byte 배열을 16진수로 변환한다.
	 */
	private static String toHexString(byte[] bytes) {

		formatter = new Formatter();

		for (byte b : bytes) {
			formatter.format("%02x", b);
		}

		return formatter.toString();
	}

	/**
	 * @description byte 배열을 Base64로 인코딩한다.
	 */
	public static String toBase64String(byte[] bytes) {

		byte[] byteArray = new byte[bytes.length * 3];
		Base64.getEncoder().encode(bytes, byteArray);
		return new String(byteArray);

	}

	/**
	 * @throws UnsupportedEncodingException
	 * @throws IllegalStateException
	 * @description HmacSHA1로 암호화한다. (HmacSHA1은 hash algorithm이라서 복호화는 불가능)
	 */
	public static String encryption(String key, String message) throws SignatureException, NoSuchAlgorithmException,
			InvalidKeyException, IllegalStateException, UnsupportedEncodingException {

		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
		Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
		mac.init(signingKey);

		byte[] result = mac.doFinal(message.getBytes("utf-8"));

		// base64 encoding
		// return toBase64String(result);

		// get hex string
		return toHexString(result);
	}

	public static void main(String[] args) throws Exception {

		String key = "key";
		String message = "The quick brown fox jumps over the lazy dog";

		String expected = "de7c9b85b8b78aa6bc8a7a36f70a90701c9db4d9";

		String encryptedStr = encryption(key, message);

		System.out.println("encryptedStr : " + encryptedStr);

		if (expected.equals(encryptedStr)) {
			System.out.println("encryption success");
		} else {
			System.out.println("encryption failed");
		}

	}

}
