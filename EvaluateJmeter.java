
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;

public class EvaluateJmeter {

	RunJmeterTest runJmeterTest = new RunJmeterTest();
	TestSummary testSummary = new TestSummary();
	
	public EvaluateJmeter()
	{

	}

	public boolean evaluate(String testFile)
	{
		// run the test on given file name and get the output
		String output = this.runJmeterTest.run(testFile);
		//System.out.println(output);

		if (!output.isEmpty()) 
		{
			// split the output line and parse the result.
		    String[] spliter = output.split("\\s+");
		    //System.out.println(output);
			this.testSummary.setThreads(Double.parseDouble(spliter[2]));
			this.testSummary.setAvg(Double.parseDouble(spliter[8]));
			this.testSummary.setMin(Double.parseDouble(spliter[10]));
			this.testSummary.setMax(Double.parseDouble(spliter[12]));
			this.testSummary.setErr(Double.parseDouble(spliter[14]));
			//System.out.println("Threads are: "+this.testSummary.getThreads());
			return true;
		}
		return false;
	}

	public int checkCompletedThreads(double expectedThreads, double varience)
	{
		if (this.testSummary.getThreads() != 0) 
		{
			//double coVarience = (this.testSummary.getThreads() * varience);
			double coVarience = (expectedThreads * varience);
			//System.out.println(coVarience);
			//if ((expectedThreads >= this.testSummary.getThreads() - coVarience) && (expectedThreads <= this.testSummary.getThreads() + coVarience)) 
			//System.out.println("varience: "+coVarience +" Actual: "+this.testSummary.getThreads()+ " expected: " +expectedThreads);
			if ((this.testSummary.getThreads() < (expectedThreads - coVarience)))// || ( this.testSummary.getThreads() > (expectedThreads + coVarience)))
			{
				return 0; // an error  
			}
		}
		return 1; // threads are okay
	}

	public int checkAverageDelay(double expectedAvg, double varience)
	{
		if (this.testSummary.getAvg() != 0)
		{
			double coVarience = (this.testSummary.getAvg() * varience);
			if (expectedAvg >= (this.testSummary.getAvg() + coVarience)) 
			{
				return 2; // return warning
			}
			else
			{
				return 1; // server delay is okay
			}
		}
		return 0;
	}

	public int checkErrorRate(double expectedErrRate)
	{
		if (this.testSummary.getErr() > expectedErrRate) 
		{
			return 0; // in case of failar
		}
		return 1; // error rate is okay
	}

	public int checkResponseCode(String fileName)
	{
		int output = 1;
		BufferedReader br = null;
		fileName += ".jtl";
        try
        {
            //Reading the CSV file
            br = new BufferedReader(new FileReader(fileName));
            String singal_line = "";
            // get the header
            singal_line = br.readLine();
            int responseCode;
            while ((singal_line = br.readLine()) != null) 
            {
                String[] line_spliter = singal_line.split(",");
                try 
                {
				   responseCode = Integer.parseInt(line_spliter[3]);
				}
				catch (NumberFormatException e)
				{
					output = 0;
					responseCode = 0;
				}

				if (responseCode >= 400) 
				{
					//System.out.println(responseCode);
					if (responseCode != 401) 
					{
						output = 0;
					}	
				}
            }
        }
        catch(IOException e)
        {
        	System.out.println(e);
        }
        return output;
	}
}