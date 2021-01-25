package spiderpak.struct;

import java.util.LinkedHashMap;

public class InfoBean {//��ȡ������bean

	private String articleUrl=null;//�������� 
	private String articletime=null;//����ʱ�� 
	private String articletitle=null; //�� ��
	private String articleresume=null;//��� 
	private String articlereadNum=null;//Χ���� 
	private String authorName=null;// �����˵��û���
	private String authorUrl=null;//�����˵ĸ�����ҳ 
	private String pageUrl=null;//��Դҳ����
	
	private String sysuptime=null;//����ʱ��//һ�������ݿ��Զ�����

	private String field1=null;
	private String field2=null;
	private String brief = null;
	private LinkedHashMap<String,byte[]> spareMap=null;//�������ݴ洢��Ա
	
	public String getArticleUrl() {
		return articleUrl;
	}
	public void setArticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}
	public String getArticletime() {
		return articletime;
	}
	public void setArticletime(String articletime) {
		this.articletime = articletime;
	}
	public String getArticletitle() {
		return articletitle;
	}
	public void setArticletitle(String articletitle) {
		this.articletitle = articletitle;
	}
	public String getArticleresume() {
		return articleresume;
	}
	public void setArticleresume(String articleresume) {
		this.articleresume = articleresume;
	}

	public String getArticlereadNum() {
		return articlereadNum;
	}
	public void setArticlereadNum(String articlereadNum) {
		this.articlereadNum = articlereadNum;
	}

	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
//	public String getAuthorUrl() {
//		return authorUrl;
//	}
//	public void setAuthorUrl(String authorUrl) {
//		this.authorUrl = authorUrl;
//	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getSysuptime() {
		return sysuptime;
	}
	public void setSysuptime(String sysuptime) {
		this.sysuptime = sysuptime;
	}
	public String getAuthorUrl() {
		return authorUrl;
	}
	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}
	public LinkedHashMap<String,byte[]> getSpareMap() {
		return spareMap;
	}
	public void setSpareMap(LinkedHashMap<String,byte[]> spareMap) {
		this.spareMap = spareMap;
	}




	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}
}
