package spiderpak.browser;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import spiderpak.spider.Server;

import java.util.HashMap;

public class BaseAjaxBrowserImpl implements Browser {
	WebClient wc = new WebClient();
	public BaseAjaxBrowserImpl(){

		String userAgent = "Mozilla/5.0 (Windows NT 10.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.85 Safari/537.36";
		//Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36
		// wc.setThrowExceptionOnScriptError(true);
		wc.getOptions().setJavaScriptEnabled(false); //启用JS解释器，默认为true
		wc.setJavaScriptTimeout(100000);//设置JS执行的超时时间
		wc.getOptions().setCssEnabled(false); //禁用css支持
		wc.getOptions().setThrowExceptionOnScriptError(false); //js运行错误时，是否抛出异常


			wc.setAjaxController(new NicelyResynchronizingAjaxController());
	
			wc.getOptions().setThrowExceptionOnScriptError(false);
	
			wc.setJavaScriptTimeout(60000);
			 wc.addRequestHeader("User-Agent", userAgent);
			wc.addRequestHeader("Accept-Encoding", "gzip, deflate, br");
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
		HtmlPage page = wc.getPage(url);
		page.getUrl();
		return wc.getPage(url);
	}

	@Override
	public void proxy(boolean use) {

	}

}
