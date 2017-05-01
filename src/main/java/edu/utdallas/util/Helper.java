package edu.utdallas.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Helper 
{
    public static HashSet<String> cov = new HashSet<String>();
    public static ArrayList<Help> all = new ArrayList<Help>();  //For Total
    public static ArrayList<HelpAdd> add = new ArrayList<HelpAdd>(); //For Additional
    //<ClassName, SetOfStmtCov>
    public static HashMap<String, HashSet<Integer>> cove = new HashMap<String, HashSet<Integer>>();
    
//	<Test, <ClassName, SetOfStmtCovered>> 
//	public static HashMap<String, HashMap<String, HashSet<Integer>>> all = 
//			new HashMap<String, HashMap<String, HashSet<Integer>>>();
	
	// <className, arrayList of object Test contains test name and total number of lines coverage 
	public static HashMap<String, ArrayList<MyTest>> rank = new HashMap<String, ArrayList<MyTest>>();
	public static void addExecutedLine(String content){
		cov.add(content);
	}
	
	public static void addLine(String s)
	{
		
		int sep = s.indexOf(':'); //Separator of class name and line number
		
		String className = s.substring(0, sep);
		int lineNo = Integer.valueOf(s.substring( sep+1 ) );
		if ( cove.get(className) == null)
		{
			cove.put(className, new HashSet<Integer>());
		}
		cove.get(className).add(lineNo);
	}
	

	
}
