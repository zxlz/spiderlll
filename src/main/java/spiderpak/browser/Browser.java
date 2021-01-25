package spiderpak.browser;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.security.PrivateKey;
import java.util.HashMap;


public interface  Browser {
	public  void setTaskContext(HashMap taskContext);
	public  WebClient getWc();
	public HtmlPage download(String url) throws Exception;
	public  void proxy(boolean use);
}
