package spiderpak.browser;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import spiderpak.spider.Server;

import java.util.HashMap;

public class NoCookiesAjaxBrowserImpl implements Browser {
	WebClient wc = new WebClient();
	public NoCookiesAjaxBrowserImpl(){
		Server.pushMessage("BaseAjaxBrowserImpl���췽��");
		String userAgent = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36";
		
		// wc.setThrowExceptionOnScriptError(true);
		
			wc.getOptions().setCssEnabled(false);
			wc.getOptions().setJavaScriptEnabled(true);
			wc.setAjaxController(new NicelyResynchronizingAjaxController());
	
			wc.getOptions().setThrowExceptionOnScriptError(false);

			wc.setJavaScriptTimeout(60000);
			// wc.addRequestHeader("User-Agent", userAgent);
			wc.addRequestHeader("Accept-Encoding", " deflate, sdch");
	}




	@Override
	public void setTaskContext(HashMap taskContext) {

	}

//	@Override
//	public WebClient getWc() {
//		return wc;
//	}

	@Override
	public HtmlPage download(String url) throws Exception {
		
		return wc.getPage(url);
	}

	@Override
	public void proxy(boolean use) {

	}

}
