package myTest.idpTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleRestApiClient {

	/*
	 * 1. HTTP URL 호출하는 자바 어플리케이션
	 * 2. IDP 의 HMAC-SHA1 sp_auth_key 생성
	 * 3. HTTPS URL 호출하도록 개선
	 * 4. Property 를 외부 파일로 관리하도록 개선
	 * 5. 화면단 받아서 처리 ?
	 *
	 */


	private String scheme = "http";
	private String domain = "idpdev-ss.skplanetoneid.com";
	private int port = 80;
	// private String path = "/web/MobileAuth.api";
	// private String query = "cmd=findID&sp_id=tacademy&sp_auth_key=test";
	private String query = "";

	private String path = "/web/Search.api";

	private String secretKey = "e37915fa5c6a2e0c236d5aaafdb0f678";
	private String spId = "tacademy";
	private String spDomain = "oic.skplanet.com";
	private String timeStamp = null;

	public String getUrlAddress() {
		return String.format("%s://%s:%s/%s?%s", scheme, domain, Integer.toString(port), path, query);
	}

	public URLConnection getConnection() throws IOException {

		setQueryString();

		String urlString = scheme + "://" + domain + ":" + port + path + "?" + query;

		URL url = new URL(urlString);

		URLConnection conn = url.openConnection();

		return conn;

	}

	/**
	 * sp_auth_key를 생성하여 리턴한다.
	 *
	 * sp_auth_key 생성에 필요한 secretKey(associationKey) 는 인스턴스 변수에 선언
	 *
	 * data format : "sp_id=%s|time=%s|domain=%s"
	 *
	 * sp_auth_key = HMacSha1(secretKey , dataString)
	 */
	public String getSpAuthKey() {

		timeStamp = Long.toString(Calendar.getInstance().getTimeInMillis());
		String data = String.format("sp_id=%s|time=%s|domain=%s", spId, timeStamp, spDomain);

		String encryptedStr = "";
		try {
			encryptedStr = HmacSha1Signature.encryption(secretKey, data);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedStr + "|" + timeStamp;
	}

	public void setQueryString() {

		Map<String, String> map = new LinkedHashMap<>();

		map.put("cmd", "mobileAuthcodeSend");
		map.put("sp_id", spId);
		map.put("sp_auth_key", getSpAuthKey());
		map.put("resp_flow", "resp");
		map.put("resp_type", "2");
		map.put("resp_url", "");
		map.put("user_mdn", "01035743506");
		map.put("user_mdn_type", "SKT");
		map.put("send_num", "01035743506");

		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, String> elem : map.entrySet()) {

			if (sb.length() > 0) {
				sb.append("&");
			}

			sb.append(elem.getKey() + "=" + elem.getValue());
		}

		query = sb.toString();
	}

	public String getHtmlData(URLConnection connection) {

		byte[] buffer = new byte[4096];
		int len = -1;

		StringBuilder sb = new StringBuilder();


		InputStream is = null;

		try {
			is = connection.getInputStream();

			while ((len = is.read(buffer, 0, buffer.length)) != -1) {

				System.out.write(buffer, 0, len);
				// System.out.println("length : " + len);

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// do nothing
			} catch (Exception e) {
				// do nothing
			}
		}


		return null;

	}


	public static void main(String[] args) {

		SimpleRestApiClient restClient = new SimpleRestApiClient();


		try {
			URLConnection conn = restClient.getConnection();

			restClient.getHtmlData(conn);





		} catch (IOException e) {
			e.printStackTrace();
		}



	}


}
