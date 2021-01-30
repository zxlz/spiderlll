package spiderpak.spider.core;

import spiderpak.spider.Server;
import spiderpak.struct.TaskComponent;
import spiderpak.utils.Log;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class Download implements Runnable {
    private static final int INITIAL_ERROR_DELAY = 1000;
    private static final int MAX_ERROR_DELAY = 10000;
    private static final int MAX_ERROR_COUNT = 10;
    //秒
    private static final int INITIAL_POLL_DELAY = 1;
    private static final int MAX_POLL_DELAY = 4;

    private final TaskComponent task;
    private final MySpider spider;

    private  int SUCCESS_DELAY=2500;

    protected volatile DownloadState state = DownloadState.NEW;
    private  volatile boolean isIdle = false;

    public Download( TaskComponent task,MySpider spider) {
        this.task = task;
        this.spider=spider;
        //取延迟配置
        if(task.getTaskContext().get("delaytime")!=null && !"".equals(task.getTaskContext().get("delaytime"))){
            SUCCESS_DELAY= (int) task.getTaskContext().get("delaytime");
        }
//        Server.pushMessage("delaytime: "+SUCCESS_DELAY);
    }
    public final DownloadState getState() {
        return state;
    }


    /**
     *  从urlmanage取url，下载  直到spider not running
     */
    @Override
    public void run() {
        int errorDelay = 0;
        int currentErrorCount = 0;
        int pollDelay = INITIAL_POLL_DELAY;
        while (spider.isRunning()) {
            while (spider.isPaused() && spider.isRunning()) {
                state = DownloadState.PAUSED;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
            if (!spider.isRunning()) {
                break;
            }
            state = DownloadState.RUNNING;
//            //检查中断状态
//            if ("over".equals(task.getTaskContext().get("state"))) {
//                Server.pushMessage("quick over");
//                break;
//            }
            String currUrl=null;
            try {
                currUrl= task.getUrlManager().unVisitedUrlDeQueue(pollDelay, TimeUnit.SECONDS);
                if(currUrl==null){
                    if(pollDelay<MAX_POLL_DELAY){
                        pollDelay++;
                    }else{
                        //polldelay到了最大，设置为空闲
                        isIdle=true;
                    }
                    continue;
                }else{
                    task.getUrlManager().addVisitedUrl(currUrl);//几率小，下载结果还会去重，所以不锁
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            pollDelay=INITIAL_POLL_DELAY;
            isIdle=false;

            // 处理url
            currentErrorCount=0;
            while (currUrl!=null) {
                try {
                    Log.info("获取中" + currUrl);
                    Object content = task.getBrowser().download(currUrl);
//                  task.getParse().go(content, currUrl);
                    task.getDataPipeline().OriginalDataPut(content);
                    currUrl=null;
                    Thread.sleep(SUCCESS_DELAY);
                } catch (Exception e) {
                    Log.severe("connect--error：" + currUrl + "Number of try: " + currentErrorCount);
                    currUrl=handleException(++currentErrorCount,currUrl);
//				    task.getBrowser().proxy(true);//open proxy
                    errorDelay = handleExceptionWithDelay(errorDelay);
                    e.getMessage();
                }

            }

        }
        isIdle=true;
        state=DownloadState.ENDED;
    }
    protected String handleException(int err_try,String currUrl) {
        if (err_try < MAX_ERROR_COUNT) {//继续重试
            return currUrl;
        } else {
            //记录错误，不再重试
            if (task.getTaskContext().containsKey("downloadErr")) {
                HashSet<String> errSet = (HashSet<String>) task.getTaskContext().get("downloadErr");
                errSet.add(currUrl);
            } else {
                HashSet<String> errSet = new HashSet<>();
                errSet.add(currUrl);
                task.getTaskContext().put("downloadErr", errSet);
            }
            return null;
        }
    }
    protected int handleExceptionWithDelay(int currentErrorDelay) {
        // Don't delay on first exception
        if (currentErrorDelay > 0) {
            try {
                Thread.sleep(currentErrorDelay);
            } catch (InterruptedException e) {
                // Ignore
            }
        }

        // On subsequent exceptions, start the delay at 50ms, doubling the delay
        // on every subsequent exception until the delay reaches 1.6 seconds.
        if (currentErrorDelay == 0) {
            return INITIAL_ERROR_DELAY;
        } else if (currentErrorDelay < MAX_ERROR_DELAY) {
            return currentErrorDelay * 2;
        } else {
            return MAX_ERROR_DELAY;
        }
    }

    public boolean isIdle() {
        return isIdle;
    }

    public enum DownloadState {
        NEW, RUNNING, PAUSED, ENDED
    }
}
