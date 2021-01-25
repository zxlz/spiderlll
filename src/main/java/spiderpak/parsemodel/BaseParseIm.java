package spiderpak.parsemodel;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BaseParseIm extends BaseParse{


	@Override
	public long go(HtmlPage page, String visitUrl) throws Exception {
		System.out.println(visitUrl);
		System.out.println(page.getBody());
		return 1;
	}

}
