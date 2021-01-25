package spiderpak.spider;

import net.sf.json.JSONObject;
import spiderpak.browser.BaseAjaxBrowserImpl;
import spiderpak.browser.Browser;
import spiderpak.parsemodel.BaseParse;
import spiderpak.parsemodel.BaseParseIm;
import spiderpak.service.BasePageServiceImpl;
import spiderpak.service.BaseService;
import spiderpak.struct.*;
import spiderpak.utils.repeatfilter.BloomFilter;
import spiderpak.utils.repeatfilter.RepeatFilter;

import java.util.HashMap;

/**
 *提供api 根据models组装task任务组件 并启动和返回执行线程
 */
public class Control {

    private UrlManager urlManager;
    private RepeatFilter repeatFilter;

    private Browser browser;

    private InfoBuffer infoBuffer;

    private BaseService pageService;//��task

    private BaseParse parse;//��task	private




    public Thread CreateTh(String config)
    //(String ParseImplName,String PageServiceImplName ,String  BrowserImplName	,String UrlManagerImplName,String RepeatFilterName)
    {

        JSONObject argJson =JSONObject.fromObject(config);
        JSONObject start = argJson.getJSONObject("start");
        if(start!=null){
            SpiderRun letgo = new SpiderRun(init(start,argJson.getString("name")));

            Thread t = new Thread(letgo);
            t.start();
            return t;
        }else {
            return null;
        }

    }

   private class SpiderRun implements Runnable {
        TaskComponent task = null;


        SpiderRun(TaskComponent task) {
            this.task = task;
        }

        @Override
        public void run() {
            Server.pushMessage("任务开始");
            MySpider spider = new MySpider(task);
            try {
                spider.runspider();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //组装task的组件
    private TaskComponent init(JSONObject config, String name){



        TaskComponent task = null;
        try {
            if (  !"".equals(config.getOrDefault(urlManager,""))) {
                Server.pushMessage(config.getString("urlManager"));
                urlManager = (UrlManager) Class.forName(config.getString("urlManager")).newInstance();

            }else{
                urlManager = new BaseUrlManagerImpl();
            }
            if ( !"".equals(config.getOrDefault("repeatFilter",""))) {
                Server.pushMessage(config.getString("repeatFilter"));
                repeatFilter = (RepeatFilter) Class.forName(config.getString("repeatFilter")).newInstance();

            }else{
                repeatFilter = new BloomFilter();
            }
            if (!"".equals(config.getOrDefault("browser",""))) {
                Server.pushMessage(config.getString("browser"));
                browser = (Browser) Class.forName(config.getString("browser")).newInstance();

            }else{
                browser = new BaseAjaxBrowserImpl();
            }
            if ( !"".equals(config.getOrDefault("infoBuffer",""))) {
                Server.pushMessage(config.getString("infoBuffer"));
                infoBuffer = (InfoBuffer) Class.forName(config.getString("infoBuffer")).newInstance();

            }else {
                infoBuffer = new InfoBuffer();
            }
            if ( !"".equals(config.getOrDefault("pageService",""))) {
                Server.pushMessage(config.getString("pageService"));
                pageService = (BaseService) Class.forName(config.getString("pageService")).newInstance();

            }else {
                pageService = new BasePageServiceImpl();//��task
            }
            if ( !"".equals(config.getOrDefault("parse","") )) {
                Server.pushMessage(config.getString("parse") );
                parse = (BaseParse) Class.forName(config.getString("parse") ).newInstance();

            }else {
                parse = new BaseParseIm();//��task	private
            }
            task = new TaskComponent(urlManager, repeatFilter, browser, infoBuffer, pageService, parse,name);

        } catch (Exception e) {
            Server.pushMessage("组装task异常");
            e.printStackTrace();
        }
        Server.pushMessage("组装task完成");

        return task;

    }

}

	
		
