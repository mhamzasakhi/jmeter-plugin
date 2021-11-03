
public class ThreadGroup {
    private int id;
    private String threadGroupName;
    private String num_threads;

    public ThreadGroup(int id, String name, String numThreads) 
    {
        this.id = id;
        this.threadGroupName = name;
        this.num_threads = numThreads;
    }

    @Override
    public String toString() 
    {
        return this.id +" "+ this.threadGroupName +" "+ this.num_threads;
    }

    public int getThreadGroupId()
    {
        return this.id;
    }
    public String getThreadGroupName()
    {
        return this.threadGroupName;
    }

    public String getNumThreads()
    {
        return this.num_threads;
    }
}