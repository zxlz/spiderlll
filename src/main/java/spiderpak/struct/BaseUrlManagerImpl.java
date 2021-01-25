package spiderpak.struct;

import java.util.HashSet;
import java.util.Set;

public class BaseUrlManagerImpl implements UrlManager {
	// ??????url????
	private Set<String> visitedUrl = new HashSet();

	// ???????url????
	private Queue unVisitedUrl = new Queue();

	// ???????????URL??????
	public void addVisitedUrl(String url) {
		visitedUrl.add(url);
	}

	// ??????????URL
	public void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	// ??????URL?????????
	public Object unVisitedUrlDeQueue() {
		return unVisitedUrl.deQueue();
	}
	
	//??????????URL?????????????????URL??????????
	public boolean enfiQueue(String url) {
		if (url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contians(url)) {
			unVisitedUrl.enfiQueue(url);
			return true;
		} else {
			return false;
		}
	}

	// ?????????URL????,???????????URL??????????
	public boolean addUnvisitedUrl(String url) {
		if (url != null && !url.trim().equals("") && !visitedUrl.contains(url) && !unVisitedUrl.contians(url)) {
			unVisitedUrl.enQueue(url);
			return true;
		} else {
			return false;
		}

	}

	// ???????????URL???
	public long getVisitedUrlNum() {
		return visitedUrl.size();
	}

	// ?????????URL????????????
	public boolean unVisitedUrlIsEmpty() {
		return unVisitedUrl.empty();
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
