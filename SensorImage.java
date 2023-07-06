package backend;

public class SensorImage
{
	private int x;
	private int y;
	private String filename;

	public SensorImage(int x, int y, String filename)
	{
		this.x = x;
		this.y = y;
		this.filename = filename;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
	
	public void setImage(String filename)
	{
		this.filename = filename;
	}

	public String getImage()
	{
		return filename;
	}
}
