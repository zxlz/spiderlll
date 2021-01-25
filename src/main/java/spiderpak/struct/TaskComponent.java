package spiderpak.struct;

import spiderpak.browser.Browser;
import spiderpak.parsemodel.BaseParse;
import spiderpak.service.BaseService;
import spiderpak.utils.repeatfilter.RepeatFilter;

import java.util.HashMap;

public class TaskComponent {
	private HashMap taskContext=new HashMap();
	private UrlManager urlManager;
	private RepeatFilter repeatFilter;
	private Browser browser;
	
	private InfoBuffer infoBuffer;
	private BaseService pageService;//��task
	private BaseParse parse;//��task
	
	
	
	public TaskComponent(UrlManager urlManager, RepeatFilter repeatFilter, Browser browser, InfoBuffer infoBuffer,
                         BaseService pageService, BaseParse parse, String name) {
		this.urlManager = urlManager;
		this.repeatFilter = repeatFilter;
		this.browser = browser;
		this.infoBuffer = infoBuffer;
		this.pageService = pageService;
		if(pageService!=null){
			pageService.setTask(this);
		}

		this.parse = parse;
		parse.setTask(this);
		browser.setTaskContext(taskContext);
		taskContext.put("name",name);
	}
	public UrlManager getUrlManager() {
		return urlManager;
	}
	public void setUrlManager(UrlManager urlManager) {
		this.urlManager = urlManager;
	}
	public RepeatFilter getRepeatFilter() {
		return repeatFilter;
	}
	public void setRepeatFilter(RepeatFilter repeatFilter) {
		this.repeatFilter = repeatFilter;
	}
	public Browser getBrowser() {
		return browser;
	}
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}
	public InfoBuffer getInfoBuffer() {
		return infoBuffer;
	}
	public void setInfoBuffer(InfoBuffer infoBuffer) {
		this.infoBuffer = infoBuffer;
	}
	public BaseService getPageService() {
		return pageService;
	}
	public void setPageService(BaseService pageService) {
		this.pageService = pageService;
		pageService.setTask(this);
	}
	public BaseParse getParse() {
		return parse;
	}
	public void setParse(BaseParse parse) {
		this.parse = parse;
		parse.setTask(this);
	}


	public HashMap getTaskContext() {
		return taskContext;
	}

	public void setTaskContext(HashMap taskContext) {
		this.taskContext = taskContext;
	}
}
