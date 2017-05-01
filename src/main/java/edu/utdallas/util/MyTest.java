package edu.utdallas.util;

public class MyTest implements Comparable<MyTest>
{
	public String testName;
	public int totalLine;
	public MyTest(String tn, int tl)
	{
		this.testName = tn;
		this.totalLine = tl;
	}

	@Override
	public int compareTo(MyTest o) {
		// TODO Auto-generated method stub
		return this.totalLine - o.totalLine;
	}
}
