package edu.utdallas;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import edu.utdallas.util.Help;
import edu.utdallas.util.HelpAdd;
import edu.utdallas.util.Helper;
import edu.utdallas.util.MyTest;



public class JUnitExecutionListener extends RunListener {
	long startTime = 0;
	long endTime = 0;
	File totalFile;
	File addFile;
	PrintWriter writerTotal = null;
	PrintWriter writerAdd = null;
	
	//Whole test suite is finished
	@Override
	public void testRunStarted(Description description) throws Exception 
	{
		startTime = System.currentTimeMillis();
		super.testRunStarted(description);
		totalFile = new File(System.getProperty("user.dir") + "/src/test/java/TestTotal.java");
		addFile = new File(System.getProperty("user.dir") + "/src/test/java/TestAdditional.java");
		try {
			writerTotal = new PrintWriter(totalFile);
			writerAdd = new PrintWriter(addFile);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		writerTotal.println("import org.junit.runner.RunWith; import org.junit.runners.Suite; import org.junit.runners.Suite.SuiteClasses;");
		writerTotal.println("@RunWith(Suite.class)");
		writerTotal.println("@SuiteClasses({");
		writerAdd.println("import org.junit.runner.RunWith; import org.junit.runners.Suite; import org.junit.runners.Suite.SuiteClasses;");
		writerAdd.println("@RunWith(Suite.class)");
		writerAdd.println("@SuiteClasses({");
	}
	
	
	//One test is started
	@Override
	public void testStarted(Description description) throws Exception {
		// TODO Auto-generated method stub
		super.testStarted(description);

	}
	
	//Test failure
	@Override
	public void testFailure(Failure failure) throws Exception {
		// TODO Auto-generated method stub
		super.testFailure(failure);
		endTime = System.currentTimeMillis();
		System.out.println("Time to find the bug:" + (endTime-startTime) +"ms");
	}
	
	//One Test is finished
	@Override
    public void testFinished(Description description) throws Exception 
    {
		super.testFinished(description);
		String testName = description.getClassName();
		
		//Total Coverage
		int totalLine = Helper.cov.size();
		boolean check = false;
		for(Help help: Helper.all)
		{
			if (help.tc.equals(testName))
			{ help.tl += totalLine; check = true; break;}
		}
		if(!check)
		{
			Helper.all.add(new Help(totalLine,testName));
		}
		
		//Additional Coverage
		boolean checkAdd = false;
		for(HelpAdd helpAdd: Helper.add)
		{
			if (helpAdd.tc.equals(testName))
			{
				helpAdd.lineCov.addAll(Helper.cov); checkAdd = true; break;
			}
		}
		if(!checkAdd){Helper.add.add(new HelpAdd(testName, Helper.cov));}
		
		
		Helper.cov.clear();
//        String testName = description.getClassName();
//        Set<String> set = Helper.cove.keySet();
//        for (String key:set)
//        {
//        	int totalLine = Helper.cove.get(key).size();
//        	if (!Helper.rank.containsKey(key))
//        	{
//        		Helper.rank.put(key, new ArrayList<MyTest>());
//        	}
//        	Helper.rank.get(key).add(new MyTest(testName, totalLine));
//        }
//        Helper.cove.clear();
        
//		for (String line : Helper.cov) {
//			writer.println(line.replace('/', '.'));
//		}
//		Helper.cov.clear();
		
//    	Set<String> set = Helper.cove.keySet();
//    	for (String key : set)
//    	{
//    		for(int line : Helper.cove.get(key))
//    		{
//    			writer.println(key.replace('/', '.') + ":" + line);
//    		}
//    	}
//    	Helper.cove.clear();
    }
    
	//Whole test suite is finished
	@Override
	public void testRunFinished(Result result) throws Exception {
		// TODO Auto-generated method stub
		super.testRunFinished(result);
//    	Set<String> set = Helper.rank.keySet();
//    	for (String key : set)
//    	{
//    		writer.println("Class: " + key);
//    		Collections.sort(Helper.rank.get(key));
//    		for(MyTest theTest:Helper.rank.get(key))
//    		{
//    			writer.println(theTest.testName + " covers: " + theTest.totalLine + " lines");
//    		}
//    		writer.println("----------------------------------------------------------------------------------------------");
//    	}
		//Sort Total Line Coverage and Print to file
		Collections.sort(Helper.all);
		for(Help help:Helper.all)
		{
			writerTotal.println("// covers total " + help.tl);
			writerTotal.println(help.tc + ".class,");
		}
		writerTotal.println("}) ");
		writerTotal.println("public class TestTotal {}");
		writerTotal.close();
		
		//Sort additional line coverage and print to file
		Collections.sort(Helper.add);
		ArrayList<HelpAdd> addSort = new ArrayList<HelpAdd>();
		addSort.add(Helper.add.get(0));
		System.out.println(Helper.add.get(0).tc + " this is the top class ");
		HashSet<String> total = new HashSet<String>();
		total.addAll(Helper.add.get(0).lineCov);
		Helper.add.remove(0);
		
		
		
		for(int i=0; i < Helper.add.size(); i++)
		{
			int index = 0; int max = 0;
			if(Helper.add.size() == 0)
			{break;}
			for(int j=0; j < Helper.add.size(); j++)
			{
				HashSet<String> temp = new HashSet<String>();
				temp.addAll(total);
				temp.addAll(Helper.add.get(j).lineCov);
				temp.removeAll(total);
				if(temp.size() > max)
				{
					max = temp.size();
					index = j;
				}
			}
			i = i - 1;
			addSort.add(Helper.add.get(index));
			total.addAll(Helper.add.get(index).lineCov);
			Helper.add.remove(index);
			
		}
		for(HelpAdd helpA: addSort)
		{
			writerAdd.println(helpA.tc + ".class,");
		}
		writerAdd.println("}) ");
		writerAdd.println("public class TestAdditional {}");
		writerAdd.close();
	}

}
