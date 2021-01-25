package spiderpak.struct;

public interface UrlManager {

	// ��ӵ����ʹ���URL������
	public  void addVisitedUrl(String url) ;

	// �Ƴ����ʹ���URL
	public  void removeVisitedUrl(String url);

	// δ���ʵ�URL���׳�����
	public  Object unVisitedUrlDeQueue() ;
	
	//��ӵ�δ���ʵ�URL�Ķ��ף�����Ҫ��֤ÿ��URLֻ������һ��
	public  boolean enfiQueue(String url);

	// ���δ���ʵ�URL�Ķ�β,����Ҫ��֤ÿ��URLֻ������һ��
	public  boolean addUnvisitedUrl(String url);

	
	// ����Ѿ����ʵ�URL��Ŀ
	public  long getVisitedUrlNum();

	// �ж�δ���ʵ�URL�������Ƿ�Ϊ��
	public  boolean unVisitedUrlIsEmpty();
	
	// ��ȡδ���ʵ�URL��Ŀ
	public  long unVisitedUrlNum();
	public boolean vistiedContains(String url);
}
