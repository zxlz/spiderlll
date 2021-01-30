package spiderpak.parsemodel;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class BaseParseIm extends BaseParse{

	@Override
	public long parse(Object o) throws Exception {
		System.out.println(((HtmlPage)o).getUrl());
		System.out.println(((HtmlPage)o).getBody());
		return 1;
	}

	@Override
	public void init() throws Exception {

	}

	@Override
	public void destroy() throws Exception {

	}
}
