package backend;

public class Bogey
{
	private double[] coords = new double[2]; // x then y
	private double diameter;
	private String filename;
	private Roomba r;

	public Bogey(Roomba r, double angleToBot, double distance, double diameter, String filename)// DISTANCE IN MM
	{

		this.r = r;
		this.coords[0] = r.getX();
		this.coords[1] = r.getY();
		this.coords = calcCoords(angleToBot, distance);
		if (diameter > 20)
		{
			this.diameter = 20;
		}
		else
		{

			this.diameter = diameter;
		}
		if (distance > 60)
		{
			this.diameter = 0;
		}
		this.filename = filename;

	}

	public Bogey(Roomba r, String filename)
	{

		this.r = r;
		this.coords[0] = r.getX();
		this.coords[1] = r.getY();
		this.coords = calcSensorCoords();
		this.diameter = 20;
		this.filename = filename;
	}

	public double[] calcSensorCoords()
	{
		double roombaAngleRadians = Math.toRadians(r.getAngle() + 90);
		double x = this.getX() - 19 * Math.cos(roombaAngleRadians);
		double y = this.getY() - 19 * Math.sin(roombaAngleRadians);
		double[] coordinates =
		{ x, y };
		return coordinates;
	}

	public double[] calcCoords(double angle, double distance)// DIST IN mm
	{
		System.out.print(r.getX());
		System.out.print(r.getY());
		double roombaAngleRadians = Math.toRadians(r.getAngle() + 90);

		double radians = Math.toRadians(r.getAngle() - angle);

		double offsetX = 0;
		double offsetY = 0;
		offsetX = distance * 1.557480135 * Math.sin(radians) + 19 * Math.cos(roombaAngleRadians);
		offsetY = distance * 1.557480135 * Math.cos(radians) + 19 * Math.sin(roombaAngleRadians);

		double x = r.getX() + offsetX;
		double y = r.getY() - offsetY; // y-axis is inverted in Java graphics
		double[] coordinates =
		{ x, y };
		System.out.println(coordinates[0]);
		System.out.println(coordinates[1]);
		return coordinates;
	}

	public double getDiameter()
	{
		return diameter;
	}

	public String getImage()
	{
		return filename;
	}

	public double getX()
	{
		return coords[0];
	}

	public double getY()

	{
		return coords[1];
	}

}
