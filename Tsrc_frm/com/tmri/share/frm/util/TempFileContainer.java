package com.tmri.share.frm.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;


public class TempFileContainer {
	
	private static TempFileContainer _container = null;
	
	static int MaxNumber = 512;  // ��󲢷�����
	static int MaxHostNumber = 128;  // ÿ����������󲢷���
	private String[] hostes = null;
	private String[] files = null;
	private boolean[] locks = null;
	public TempFileContainer()
	{
		init();
	}
	
	public static TempFileContainer getInstance()
	{
		if (_container==null)
			_container = new TempFileContainer();
		return _container;
	}
	
	private void init()
	{
		if (files==null)
		{
			String fullpath = FuncUtilpic.getRootPath();			
			fullpath += "images/";
			try {
				fullpath = URLDecoder.decode(fullpath,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			    
			hostes = new String[MaxNumber];
			files = new String[MaxNumber];
			locks = new boolean[MaxNumber];
			for (int i =0;i<MaxNumber;i++)
			{
				files[i] = fullpath + "temppic" + i + ".jpg";
				locks[i] = false;
			}
		}
	}
	
	public synchronized void Lock(int i)
	{
		if (i>=0 && i<MaxNumber)
		    locks[i] = true;
	}
	
	public synchronized void UnLock(int i)
	{
		if (i>=0 && i<MaxNumber)
		    locks[i] = false;
	}
	
	public synchronized boolean bLock(int i)
	{
		if (i>=0 && i<MaxNumber)
		    return locks[i];
		else
			return false;
	}
	
	private int hostCount(String host)
	{
		int iCount = 0;
		for (int i=0;i<MaxNumber;i++)
		{
			if (locks[i] && host.compareTo(hostes[i])==0)
			{
				iCount++;
			}
		}
		return iCount;
	}
	
	public String QueryFile(String host)
	{
		if (host!=null && host.length()>0 && this.hostCount(host)>=MaxHostNumber)     //�������е�ͬһǰ�˳���MaxHostNumber��ʱ���ȴ�
			return null;
		for (int i=0;i<MaxNumber;i++)
		{
			if (!bLock(i))
			{
				Lock(i);
				hostes[i] = host;
				return files[i];
			}
		}
		return null;
	}
	
	public void ReleaseFile(String file)
	{
		if (file==null)
			return;
		for (int i=0;i<MaxNumber;i++)
		{
			if (files[i].equals(file))
			{
				UnLock(i);
				break;
			}
		}
	}	
	
	public void Release()
	{
		for (int i=0;i<MaxNumber;i++)
		{
			if (bLock(i))
			{
				UnLock(i);
			}
		}
	}	
	
	public int unlocksize()
	{
		int nUnlock = 0;
		for (int i=0;i<MaxNumber;i++)
		{
			if (!bLock(i))
			{
				nUnlock++;
			}
		}
		java.lang.System.out.println("Unlock " + nUnlock);
		return nUnlock;
	}
	
	public int locksize()
	{
		int nLock = 0;
		for (int i=0;i<MaxNumber;i++)
		{
			if (bLock(i))
			{
				nLock++;
			}
		}
		return nLock;
	}
}
