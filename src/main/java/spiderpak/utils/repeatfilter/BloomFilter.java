package spiderpak.utils.repeatfilter;

import java.util.BitSet;

public class BloomFilter implements RepeatFilter {
	private static final int DEFAULT_SIZE = 2 << 28;
	/*
	 * ��ͬ��ϣ���������ӣ�
	 */
	private int[] hashSigns = new int[] { 3, 5, 7, 11, 13, 17, 19, 53 };
	/*
	 * ��ʼ��һ��������С��λ�� BitSetʵ�����ɡ�������λ�����ɵ�һ��Vector�� ����ϣ����Ч�ʵر�������������ء���Ϣ����Ӧʹ��BitSet.
	 */
	private BitSet bitSets = new BitSet(DEFAULT_SIZE);
	// ����hash��������
	private static HashFunc[] hashFuns =new HashFunc[8];
  //��ʼ������hash����
	public BloomFilter() {
		for (int i = 0; i < hashSigns.length; i++) {
			hashFuns[i] = new HashFunc(DEFAULT_SIZE, hashSigns[i]);
		}
	}
	//��value�������hash����ֵ������bitset��
	@Override
	public synchronized void add(String value){  
        for(HashFunc hashFun : hashFuns){  
            bitSets.set(hashFun.hash(value), true);  
        }  
    }  
	
	@Override
	 public synchronized boolean isExit(String value){  
	        //�жϴ����ֵ�Ƿ�Ϊnull  
	        if(null == value){  
	            return false;  
	        }  
	          
	        for(HashFunc hashFun : hashFuns){  
	            if(!bitSets.get(hashFun.hash(value))){  
	                //����ж�8��hash����ֵ����һ��λ�ò����ڼ����ж�Ϊ������Bloofilter��  
	                return false;  
	            }  
	        }  
	          
	        return true;  
	    }  
	 

}

