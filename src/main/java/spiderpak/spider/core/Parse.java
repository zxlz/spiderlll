package spiderpak.spider.core;

import spiderpak.spider.Server;
import spiderpak.struct.TaskComponent;
import spiderpak.utils.Log;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class Parse implements Runnable {
    //毫秒
    private static final int INITIAL_ERROR_DELAY = 50;
    private static final int MAX_ERROR_DELAY = 1600;
    private static final int MAX_ERROR_COUNT = 5;
    //秒
    private static final int INITIAL_POLL_DELAY = 1;
    private static final int MAX_POLL_DELAY = 5;

    private static final int SUCCESS_DELAY=500;

    private final TaskComponent task;
    private final MySpider spider;


    protected volatile ParseState state = ParseState.NEW;
    private  volatile boolean isIdle = false;

    public Parse(TaskComponent task, MySpider spider) {
        this.task = task;
        this.spider=spider;

//        Server.pushMessage("delaytime: "+SUCCESS_DELAY);
    }
    public final ParseState getState() {
        return state;
    }




    /**
     *  从datapipeline取原始数据，交给parse  直到spider not running
     */
    @Override
    public void run() {
        int errorDelay = 0;
        int currentErrorCount = 0;
        int pollDelay = INITIAL_POLL_DELAY;
        while (spider.isRunning()) {
            while (spider.isPaused() && spider.isRunning()) {
                state = ParseState.PAUSED;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // Ignore
                }
            }
            if (!spider.isRunning()) {
                break;
            }
            state = ParseState.RUNNING;
//            //检查中断状态
//            if ("over".equals(task.getTaskContext().get("state"))) {
//                Server.pushMessage("quick over");
//                break;
//            }


            Object currentObj=null;
            try {
                currentObj= task.getDataPipeline().OriginalDataPoll(pollDelay,TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(currentObj==null){
                if(pollDelay<MAX_POLL_DELAY){
                    pollDelay++;
                }else{
                    isIdle=true;
                }
                continue;
            }
            pollDelay=INITIAL_POLL_DELAY;
            isIdle=false;

            // 处理url
            currentErrorCount=0;
            while (currentObj!=null) {
                try {
                    Log.info("parse处理中:"+Thread.currentThread());
                    task.getParse().parse(currentObj);
                    currentObj=null;
                    if("over".equals(task.getTaskContext().get("state"))){
                        spider.running=false;
                        break;
                    }
                    Thread.sleep(SUCCESS_DELAY);
                } catch (Exception e) {
                    Log.severe("!!!!parse--error：" + currentObj + "Number of try: " + currentErrorCount);
                    currentObj = handleException(++currentErrorCount,currentObj);
                    errorDelay = handleExceptionWithDelay(errorDelay);
                    e.printStackTrace();
                }

            }
        }
        isIdle=true;
        state= ParseState.ENDED;
    }
    protected Object handleException(int err_try,Object currentObj) {
        if (err_try < MAX_ERROR_COUNT) {//继续重试
            return currentObj;
        } else {

            //记录错误，不再重试
            if (task.getTaskContext().containsKey("parseErr")) {
                HashSet<String> errSet = (HashSet<String>) task.getTaskContext().get("parseErr");
                errSet.add(currentObj.toString());
            } else {
                HashSet<String> errSet = new HashSet<>();
                errSet.add(currentObj.toString());
                task.getTaskContext().put("parseErr", errSet);
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

    public enum ParseState {
        NEW, RUNNING, PAUSED, ENDED
    }
}
