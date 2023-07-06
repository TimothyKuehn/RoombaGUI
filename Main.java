package backend;

import javax.swing.JFrame;

import java.io.IOException;
import java.util.ArrayList;

public class Main
{
	private static Roomba r = new Roomba();
	private static ArrayList<Bogey> bogeys = new ArrayList<Bogey>();
	private static GUI gui = new GUI();
	private static TCPclient client = new TCPclient("192.168.1.1", 288);

	public static void main(String[] args) throws IOException
	{


//		String s = "I100!53.00!25.17";
//		processString(s);
		client.startListening();
		runGUI();
		

	}

	public static void sendDirection(char direction)
	{

		updateGUI();
		client.sendChar(direction);
		
			updateGUI();
	}

	public static void runGUI()
	{
		JFrame frame = new JFrame("Grid Panel");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);

		// Initialize gui object
		gui = new GUI();
		gui.setRoomba(r);
		r.setPosition(672, 384);//672 384
		frame.add(gui);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void updateGUI()
	{
		gui.repaint();
	}

	public static void processString(String s)
	{
		System.out.println(s);
		char labelChar = s.charAt(0);
		// removes label character from string
		s = s.substring(1);
		String[] objectData;

		switch (labelChar)
		{
		// SCAN DATA
		case 'S':
			// decimal ascii 140
			// splits the different objects data
			objectData = s.split("!");

			for (String l : objectData)
			{
				System.out.println(l);
			}
			break;

		case 'I':
			// decimal ascii 140
			// splits the different objects data
			objectData = s.split("@");

			for (String l : objectData)
			{
				// ascii 135
				String[] add = l.split("!");
				double bogeyAngle = Double.parseDouble(add[0]);
				double bogeyDistance = Double.parseDouble(add[1]);
				double bogeyDiameter = Double.parseDouble(add[2]);
				Bogey b = new Bogey(r, bogeyAngle - 90, bogeyDistance, bogeyDiameter, "redObject.png");
					bogeys.add(b);
				
			}

			break;
		// BUMP DATA
		case 'B':
			Bogey b = new Bogey(r,"redsquare.png");
			bogeys.add(b);
			r.updateBumpData(s.charAt(0));
			break;

		// CLIFF DATA
		case 'C':
			b = new Bogey(r,"bluesquare.png");
			bogeys.add(b);
			r.updateCliffData(s.charAt(0));
			break;

		case 'L':
			b = new Bogey(r,"yellowsquare.png");
			bogeys.add(b);
			r.updateLineData(s.charAt(0));
			break;

		// MOVEMENT DATA
		case 'D':
			objectData = s.split("!");
			double angle = Double.parseDouble(objectData[0]);
			double distance = Double.parseDouble(objectData[1]);

			if (angle != 0)
			{
				r.setAngle(angle);
			}
			if (distance != 0)
			{
				r.move(distance / 25.4);
			}
			break;

		default:
			break;
		}

	}
	public static void bogeyClear()
	{
		bogeys.clear();
		updateGUI();
	}

	public static ArrayList<Bogey> getBogeys()
	{
		return bogeys;
	}

}
