package spiderpak.struct;

import org.eclipse.jetty.util.ConcurrentHashSet;
import org.eclipse.jetty.util.Pool;

import java.nio.channels.NonReadableChannelException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class BaseUrlManagerImpl implements UrlManager {
	//
//	private Set<String> visitedUrl = new HashSet();
	private Set<String> visitedUrl = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());


	private LinkedBlockingQueue<String> unVisitedUrl = new LinkedBlockingQueue<String>();

	@Override
	public void addVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	@Override
	public void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	@Override
	public String unVisitedUrlDeQueue() {
		return unVisitedUrl.poll();
	}

	/**
	 * 阻塞取队首
	 * @param timeout
	 * @param unit
	 * @return
	 * @throws InterruptedException
	 */
	@Override
	public String unVisitedUrlDeQueue(long timeout, TimeUnit unit) throws InterruptedException {
		return unVisitedUrl.poll(timeout,unit);
	}

	/**
	 * 阻塞放入
	 * @param url
	 * @return
	 * @throws InterruptedException
	 */
	@Override
	public boolean addUnvisitedUrl(String url) throws InterruptedException {
		if (url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contains(url)) {
			unVisitedUrl.put(url);
			return true;
		} else {
			return false;
		}

	}

	@Override
	public long getVisitedUrlNum() {
		return visitedUrl.size();
	}

	@Override
	public boolean unVisitedUrlIsEmpty() {
		return unVisitedUrl.isEmpty();
	}

	@Override//?????????url???
	public long unVisitedUrlNum() {
		
		return unVisitedUrl.size();
	}

	@Override
	public boolean vistiedContains(String url) {
		return visitedUrl.contains(url);
	}



}
