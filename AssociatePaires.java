

public class AssociatePaires {
	private String key;
	private String value;

	public AssociatePaires()
	{

	}
	
	public AssociatePaires(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	public void setKey(String key)
	{
		this.key = key;
	}
	public String getKey()
	{
		return this.key;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
	public String getValue()
	{
		return this.value;
	}

	@Override
	public String toString()
	{
		return this.key + " " + this.value;
	}
}