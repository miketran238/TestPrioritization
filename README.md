"# TestPrioritization on Class Level"
Steps by steps how to use the tool.
Sample run on project common-dbutils:

0. Download the project common-dbutils:
https://drive.google.com/file/d/0B2CUmN6iobxCNG5qcWZPSmV0NkU/view?usp=sharing
	Download and unzip the TestPrioritization
	Run mvn install to create a jar file. Record the location to the jar file 
1.	Create a pomTest.xml file. Add the following lines to the Surefire plugin section:
<configuration>
	<argLine>-javaagent:"Your-own-location-of-jar-file/code-coverage-1.0-SNAPSHOT.jar"</argLine>
	<properties>
		<property>
			<name>listener</name>
			<value>edu.utdallas.JUnitExecutionListener</value>
		</property>
	</properties>
</configuration>
And to dependencies section:
  <dependency>
	  <groupId>edu.utdallas</groupId>
	  <artifactId>code-coverage</artifactId>
	  <version>1.0-SNAPSHOT</version>
	  <scope>test</scope>
  </dependency>

2.	Create pomBug.xml file. Same as pomTest.xml but delete the ArgLine section

3.	Create some random bugs. Here, we modified org.apache.commons.dbutils.BeanProcessor on line 59
protected static final int PROPERTY_NOT_FOUND = -1;
to protected static final int PROPERTY_NOT_FOUND = 1;

4.	mvn test -f pomBug.xml
To run the test in default order with recorded time for bug

5.	Fix the bug. 
Then run: mvn test -f pomTest.xml
To collect the coverage statements of tests and rank them based on total and additional strategies

6.	Re-create the bug

7.	mvn -Dtest=TestTotal test -f pomBug.xml
To run the test set in order sorted by Total strategy, and record time to discover bugs

8.	mvn -Dtest=TestAdditional test -f pomBug.xml
To run the test set in order sorted by Total strategy, and record time to discover bugs
 
