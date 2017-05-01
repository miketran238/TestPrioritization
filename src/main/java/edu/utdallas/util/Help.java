package edu.utdallas.util;


public class Help implements Comparable<Help>
{
	public int tl;public String tc;//testclass
	public Help(int tL, String tC)
	{
		this.tl = tL; this.tc = tC;
	}
	@Override
	public int compareTo(Help o) {
		return o.tl - this.tl;
	}
}
