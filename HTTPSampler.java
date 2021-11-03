
public class HTTPSampler {
    public int id;
    public String httpSamplerName;
    public String httpSamplerPath;

    public HTTPSampler(int id, String name, String path) 
    {
        this.id = id;
        this.httpSamplerName = name;
        this.httpSamplerPath = path;
    }

    @Override
    public String toString() 
    {
        return this.id +" "+ this.httpSamplerName +" "+ this.httpSamplerPath;
    }
    
    public int getHTTPSamplerId()
    {
        return this.id;
    }

    public String getHTTPSamplerName()
    {
        return this.httpSamplerName;
    }

    public String getHTTPSamplerPath()
    {
        return this.httpSamplerPath;
    }

    public String test () 
    {
        return this.httpSamplerName;
    }

}
