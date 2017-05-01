package edu.utdallas.util;

import java.util.HashSet;

public class HelpAdd implements Comparable<HelpAdd>
{
	public HashSet<String> lineCov = new HashSet<String>(); //Line coverage of a class
	public String tc;//testclass
	public HelpAdd(String tC, HashSet<String> lc)
	{
		this.lineCov.addAll(lc); this.tc = tC;
	}
	@Override
	public int compareTo(HelpAdd o) {
		//Diff between the two sets
		return o.lineCov.size() - this.lineCov.size();
	}
}
