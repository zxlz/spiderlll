package spiderpak.browser;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;

public class ApiGetImpl implements Browser {
	WebClient wc = new WebClient();
	private HashMap taskContext;
	private boolean useProxy;
	public ApiGetImpl(){


	}


	@Override
	public void setTaskContext(HashMap taskContext) {
		this.taskContext=taskContext;
	}

//	@Override
//	public WebClient getWc() {
//		return wc;
//	}

	@Override
	public String download(String url) throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con;
		if(useProxy){
//			InetSocketAddress addr = new InetSocketAddress("180.118.128.74",	9000);

			InetSocketAddress addr = new InetSocketAddress("123.101.207.190",	9999);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
			 con = (HttpURLConnection) obj.openConnection(proxy);
			System.out.println("open proxy ok");
		}else{
			con = (HttpURLConnection) obj.openConnection();
		}
//		Authenticator.setDefault(new BasicAuthenticator(dto.getUsername(),dto.getPassword()));

		con.setConnectTimeout(6000000);

		con.setReadTimeout(6000000);


		//默认值我GET
		con.setRequestMethod("GET");

		//添加请求头
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//打印结果
		System.out.println(response.toString().length());
//		taskContext.put("apiResult",response.toString());
		String str=url+" "+response.toString();
		return str;
	}

	@Override
	public void proxy(boolean use) {
		useProxy=use;
	}

}
