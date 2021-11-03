import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.HashMap;
import java.util.ArrayList;


public class RunJmeterTest {
	
	String output = "";
	boolean testResult = true;

	public RunJmeterTest()
	{

	}

	public String run(String testname)   
	{
		String command = this.buildCommand(testname + ".jmx", testname + ".jtl");
    	this.testResult = this.execute(command);
    	return this.output;
	}

	private boolean execute(String command) 
	{
	    try 
	    {
	        ProcessBuilder processBuilder = new ProcessBuilder();
	        processBuilder.command(command.split("\\s"));
	        Process process = processBuilder.start();

	        BufferedReader reader = new BufferedReader(
	                new InputStreamReader(process.getInputStream()));
	        String line;
	        while ((line = reader.readLine()) != null) 
	        {
	        	//System.out.println(line);
	        	if (line.indexOf("summary =") !=-1) 
	        	{
	        		System.out.println(line);
	        		this.output = line;
	        	}	            
	        }

	        int exitVal = process.waitFor();
	        if (exitVal == 0) 
	        {
	            return true;
	        }
	        else 
	        {
	            return false;
	        }
	    }
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	        return false;
	    } 
	    catch (InterruptedException e) 
	    {
	        e.printStackTrace();
	        return false;
	    }
	}

    private String buildCommand(String jMeterFileName, String resultFileName) 
    {
    	String jmeter = "D:\\Java\\jmeter_plugin\\JMeter\\bin\\jmeter.bat ";   // MUST BE FULL PATH
        StringBuilder command = new StringBuilder(jmeter)
                .append("-n ")
                .append("-t ").append(jMeterFileName).append(" ")
                .append("-l ").append(resultFileName).append(" ");
        return command.toString();
    }

    public String getOutput()
    {
    	return this.output;    	
    }
}
