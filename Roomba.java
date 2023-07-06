package backend;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Roomba
{
	private ArrayList<Line2D> lines = new ArrayList<Line2D>();
	private static ArrayList<SensorImage> images = new ArrayList<SensorImage>();
	private double angle = 0;
	private double x = 0;
	private double y = 0;

	public Roomba()
	{
		this.x = 0;
		this.y = 0;
		this.angle = 0;
		SensorImage meme = new SensorImage(1500,610, "ad.png");
		images.add(meme);
		clearBump();
		clearCliff();
	}

	public void updateBumpData(char c)
	{
		clearBump();
		SensorImage bumpImage = new SensorImage(1419, 50, "untriggeredBumpL.png");
		if (c == '0')
		{

			bumpImage = new SensorImage(1419, 50, "triggeredBumpL.png");
		}
		else if (c == '1')
		{

			bumpImage = new SensorImage(1569, 50, "triggeredBumpR.png");
		}

		images.add(bumpImage);

	}

	public void updateCliffData(char c)
	{
		clearCliff();
		SensorImage newImage = new SensorImage(1359, 190, "greysquare.png");
		switch (c)
		{
		case '0':
			newImage = new SensorImage(1359, 190, "bluesquare.png");
			// leftLine
			break;
		case '1':
			newImage = new SensorImage(1449, 70, "bluesquare.png");
//			frontLeftLine = 1;
			break;
		case '2':
			newImage = new SensorImage(1539, 70, "bluesquare.png");
//			frontRightLine = 1;
			break;
		case '3':
			newImage = new SensorImage(1629, 190, "bluesquare.png");
//			sideRightLine = 1;
			break;
		}
		images.add(newImage);
	}

	public void updateLineData(char c)
	{
		clearCliff();
		SensorImage newImage = new SensorImage(1359, 190, "greysquare.png");
		switch (c)
		{
		case '0':
			newImage = new SensorImage(1359, 190, "yellowsquare.png");
			// leftLine
			break;
		case '1':
			newImage = new SensorImage(1449, 70, "yellowsquare.png");
//			frontLeftLine = 1;
			break;
		case '2':
			newImage = new SensorImage(1539, 70, "yellowsquare.png");
//			frontRightLine = 1;
			break;
		case '3':
			newImage = new SensorImage(1629, 190, "yellowsquare.png");
//			sideRightLine = 1;
			break;
		}
		images.add(newImage);
	}

	public void clearBump()
	{
		SensorImage roombaMap = new SensorImage(1494, 190, "roombaSensorMap.png");
		images.add(roombaMap);

		SensorImage leftBump = new SensorImage(1419, 50, "untriggeredBumpL.png");
		images.add(leftBump);
		SensorImage rightBump = new SensorImage(1569, 50, "untriggeredBumpR.png");
		images.add(rightBump);

	}

	public void clearImages()
	{
		images.clear();
		clearCliff();
		clearBump();
	}

	public void clearCliff()
	{
		SensorImage leftCliff = new SensorImage(1359, 190, "greysquare.png");
		images.add(leftCliff);
		SensorImage rightCliff = new SensorImage(1629, 190, "greysquare.png");
		images.add(rightCliff);

		SensorImage rightFrontCliff = new SensorImage(1539, 70, "greysquare.png");
		images.add(rightFrontCliff);
		SensorImage leftFrontCliff = new SensorImage(1449, 70, "greysquare.png");
		images.add(leftFrontCliff);
	}

	public double getAngle()
	{
		return this.angle;
	}

	public void setLine(double x1, double y1, double x2, double y2)
	{
		Line2D line = new Line2D.Double(x1, y1, x2, y2);
		lines.add(line);

	}

	public ArrayList<Line2D> getLine()
	{
		return lines;
	}

	public ArrayList<SensorImage> getImages()
	{
		return images;
	}

	public void move(double distance)
	{
		double radians = Math.toRadians(angle);

		double offsetX = 0;
		double offsetY = 0;
		offsetX = distance * 4 * Math.sin(radians);
		offsetY = distance * 4 * Math.cos(radians);

		double tempX = this.x;
		double tempY = this.y;
		x += offsetX;
		y -= offsetY; // y-axis is inverted in Java graphics
		setLine(x, y, tempX, tempY);
		setPosition(x, y);
	}

	public void setAngle(double angle)
	{
		this.angle += angle % 360;
	}

	public double getX()
	{
		return this.x;
	}

	public double getY()
	{
		return this.y;
	}

	public void setPosition(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

}
