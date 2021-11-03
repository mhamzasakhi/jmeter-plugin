import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.io.FileWriter;
import java.nio.file.StandardCopyOption;

public class DriverClass {

	public DriverClass()
	{

	}

	public int generateFinalResult(int threadResult, int avgResult, int errRateResult)
	{
		int result = 0;
		// threads 
		if(threadResult == 1 && avgResult == 1 && errRateResult == 1) 
		{
			result = 1;
		}

		// avg
		if(avgResult == 2)
		{
			result = 2;
		}
		return result;
	}
	
	public int getKeyIndex(ArrayList<AssociatePaires> inputs, String key)
	{
		for (int i=0; i<inputs.size(); i++) 
		{
			if (inputs.get(i).getKey().equals(key)) 
			{
				return i;
			}	
		}
		return -1;
	}


	public boolean copyFile(String fileName) throws IOException 
	{
		fileName += ".jmx";
		File source = new File(fileName);
        File dest = new File("processingFile/"+fileName);
        try
        {
        	Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);	
        	return true;
        }
		catch(IOException e)
		{
			System.out.println(e);
		}
		return false;
	}


	public boolean myFileWriter(String fileName, String output)
	{
		fileName += ".jmx";
		try
        {
            FileWriter writer = new FileWriter("processingFile/"+fileName, false);
            writer.write(output);
            writer.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return false;
        }
		return true;
	}


	/*
    String groupNames = "[ ";
    ArrayList <ThreadGroup> threadGroups = filePreprocessing.getThreadGroups();
    String[] threadGroupsFromJMX = new String[threadGroups.size()];
    System.out.println(threadGroups.size());
    for(int i=0; i<threadGroups.size(); i++)
    {
        threadGroupsFromJMX[i] = threadGroups.get(i).getThreadGroupName();
        groupNames += "{ Thread Group "+(i+1)+": "+threadGroupsFromJMX[i]+" }";
    }
	groupNames += " ]";
	System.out.println(groupNames);
	*/


	public static void main(String[] args)
	{
		DriverClass driverClass = new DriverClass();

		int result = 1;
		boolean flage = false;
		String inputTestFile = "";

		// GET all command line parameters
		ArrayList<AssociatePaires> inputs = new ArrayList<AssociatePaires>();
		if (args.length > 0) 
		{
			String[] spliter;
			for (int i=0; i<args.length; i++) 
			{
				spliter = args[i].split("=");
				inputs.add(new AssociatePaires(spliter[0], spliter[1]));
			}
		}

		final int INPUTSIZE = inputs.size(); // number of parameters
		int index;
		// end with command line parameters 
		// and store all of them into inputs ArrayList
		

		// make a copy of the input file into processingFile folder
		if (INPUTSIZE >= 1)
		{
			index = driverClass.getKeyIndex(inputs, "test");
			if (index != -1) 
			{
				inputTestFile = inputs.get(index).getValue();
				try
				{
					boolean copy = driverClass.copyFile(inputTestFile);	
				}
				catch(IOException e)
				{
					System.out.println(e);
				}
			}
			//System.out.println("file successfully copied");
		}
		


		/*
		check these three parameters in command line
		(maxthreads, ramptime, duration) if any one is specified
		in command line inputs then modify it value in jmx test file.
		*/
		
		if (INPUTSIZE >= 2) 
		{
			// Update the valeus according to command line parameters
		    FilePreprocessing filePreprocessing = new FilePreprocessing();
		    String modifiedStr = "";
		    String key = "";
		    String value = "";

			// check maxthreads in command line
			index = driverClass.getKeyIndex(inputs, "maxthreads");
			if (index != -1) 
			{
				key = "ThreadGroup.num_threads"; //inputs.get(index).getKey();
				value = inputs.get(index).getValue();
		        modifiedStr = filePreprocessing.readXML("processingFile/"+inputTestFile, key, value);
		        modifiedStr = modifiedStr.replaceAll("(?m)^[ \t]*\r?\n", "");
		        driverClass.myFileWriter(inputTestFile, modifiedStr);		
			}

			// check ramptime in command line
			index = driverClass.getKeyIndex(inputs, "ramptime");
			if (index != -1) 
			{
				key = "ThreadGroup.ramp_time"; //inputs.get(index).getKey();
				value = inputs.get(index).getValue();
		        modifiedStr = filePreprocessing.readXML("processingFile/"+inputTestFile, key, value);
		        modifiedStr = modifiedStr.replaceAll("(?m)^[ \t]*\r?\n", "");
		        driverClass.myFileWriter(inputTestFile, modifiedStr);		
			}
		

			// check ramptime in command line
			index = driverClass.getKeyIndex(inputs, "duration");
			if (index != -1) 
			{
				key = "ThreadGroup.duration"; //inputs.get(index).getKey();
				value = inputs.get(index).getValue();
		        modifiedStr = filePreprocessing.readXML("processingFile/"+inputTestFile, key, value);
		        modifiedStr = modifiedStr.replaceAll("(?m)^[ \t]*\r?\n", "");
		        driverClass.myFileWriter(inputTestFile, modifiedStr);		
			}
		}

        // creating al objects
		EvaluateJmeter evaluateJmeter = new EvaluateJmeter();

		// get the test name and run the test 
		if (INPUTSIZE >= 1) 
		{
			System.out.println("Test is running...");
			index = driverClass.getKeyIndex(inputs, "test");
			if (index != -1) 
			{
				flage = evaluateJmeter.evaluate("processingFile/"+inputs.get(index).getValue());
				result = 1;
			}
			System.out.println("Test is completed");
		}


		// get the optional parameters and generate the result
		if (INPUTSIZE >= 2)
		{
			int threadResult = 1;
			int avgResult = 1;
			int errRateResult = 1;

			// get expectedThreads from comand line parameters
			double expectedThreads = 0;
			index = driverClass.getKeyIndex(inputs, "completions");
			if (index != -1) 
			{
				expectedThreads = Double.parseDouble(inputs.get(index).getValue());	
			}

			// get expectedAvg from comand line parameters
			double expectedAvg = 0;
			index = driverClass.getKeyIndex(inputs, "avg");
			if (index != -1) 
			{
				expectedAvg = Double.parseDouble(inputs.get(index).getValue());	
			}

			// get varience from comand line parameters
			double varience = 0;
			index = driverClass.getKeyIndex(inputs, "varience");
			if (index != -1) 
			{
				varience = Double.parseDouble(inputs.get(index).getValue());
				//System.out.println(varience);	
			}

			if (varience == 0 || varience > 100) 
			{
				varience = 20;
			}
			varience = (varience/100);
			
			// get expectedAvg from comand line parameters
			double expectedErrRate = 0;
			index = driverClass.getKeyIndex(inputs, "rate");
			if (index != -1) 
			{
				expectedErrRate = Double.parseDouble(inputs.get(index).getValue());	
			}

			// generate the final result
			threadResult = evaluateJmeter.checkCompletedThreads(expectedThreads, varience);
			//System.out.println("ThreadResult: "+threadResult);
			avgResult = evaluateJmeter.checkAverageDelay(expectedAvg, varience);
			//System.out.println("AvgResult: "+avgResult);
			errRateResult = evaluateJmeter.checkErrorRate(expectedErrRate);
			//System.out.println("ErrResult: "+errRateResult);
			result = driverClass.generateFinalResult(threadResult, avgResult, errRateResult);
			//System.out.println("Result: "+result);
		}


		// check the response code
		// if there are any 500 series errors or any 400 series 
		// (except for 401) return zero (fail)
		int responseCode = evaluateJmeter.checkResponseCode("processingFile/"+inputTestFile);
		if (responseCode == 0) 
		{
			result = 0;
		}
		//System.out.println("ResponseCode: "+responseCode);
		System.out.println("Result: "+ result);
	}
}
