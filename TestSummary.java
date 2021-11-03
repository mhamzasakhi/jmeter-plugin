
public class TestSummary{

	private double threads;
	private double max;
	private double min;
	private double avg;
	private double err;

	public TestSummary()
	{
		this.threads = 0;
		this.max = 0;
		this.min = 0;
		this.avg = 0;
		this.err = 0;
	}

	public TestSummary(double threads, double max, double min, double avg, double err)
	{
		this.threads = threads;
		this.max = max;
		this.min = min;
		this.avg = avg;
		this.err = err;
	}

	public void setThreads(double th)
	{
		this.threads = th;
	}
	public double getThreads()
	{
		return this.threads;
	}

	public void setMax(double max)
	{
		this.max = max;
	}
	public double getMax()
	{
		return this.max;
	}

	public void setMin(double min)
	{
		this.min = min;
	}
	public double getMin()
	{
		return this.min;
	}

	public void setAvg(double avg)
	{
		this.avg = avg;
	}
	public double getAvg()
	{
		return this.avg;
	}

	public void setErr(double err)
	{
		this.err = err;
	}
	public double getErr()
	{
		return this.err;
	}

}