package backend;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JPanel implements KeyListener
{

	private static final int CELL_SIZE = 48;
	private static final int GRID_ROWS = 16;
	private static final int GRID_COLS = 28;
	private static final int WIDTH = GRID_COLS * CELL_SIZE;
	private static final int HEIGHT = GRID_ROWS * CELL_SIZE;
	private static final int BUTTON_HEIGHT = 30;
	private Roomba roomba;

	public GUI()
	{
		setPreferredSize(new Dimension(WIDTH + 300, HEIGHT + BUTTON_HEIGHT));
		setLayout(new BorderLayout()); // Use BorderLayout
		addKeyListener(this); // register the GUI object as a KeyListener
		setFocusable(true);

		// Create button panel
		JPanel buttonPanel = new JPanel();
		// Add buttons to the button panel
		JButton reverseButton = new JButton("Reverse");
		JButton scanButton = new JButton("Scan");
		JButton minus15Button = new JButton("-15");
		JButton minus45Button = new JButton("-45");
		JButton minus90Button = new JButton("-90");
		JButton plus15Button = new JButton("+15");
		JButton plus45Button = new JButton("+45");
		JButton plus90Button = new JButton("+90");
		JButton moveButton = new JButton("Move");
		JButton acknowledge = new JButton("Acknowledge Sensors");
		JButton music = new JButton("Music");
		JButton clear = new JButton("Clear");
		// Add button panel to the NORTH position of the layout
		add(buttonPanel, BorderLayout.SOUTH);
		clear.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.bogeyClear();

			}
		});
		scanButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.sendDirection('s');
			}
		});
		minus90Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.sendDirection('1');
			}
		});
		minus45Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.sendDirection('2');
			}
		});
		minus15Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.sendDirection('3');
			}
		});
		moveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.sendDirection('m');
			}
		});
		plus15Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.sendDirection('4');
			}
		});
		plus45Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.sendDirection('5');
			}
		});
		plus90Button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.sendDirection('6');
			}
		});
		reverseButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.sendDirection('r');
			}
		});
		acknowledge.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				roomba.clearImages();

			}
		});
		music.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.sendDirection('c');
			}
		});
		// Add buttons to the button panel
		buttonPanel.add(clear);
		buttonPanel.add(reverseButton);
		buttonPanel.add(scanButton);
		buttonPanel.add(minus90Button);
		buttonPanel.add(minus45Button);
		buttonPanel.add(minus15Button);
		buttonPanel.add(moveButton);
		buttonPanel.add(plus15Button);
		buttonPanel.add(plus45Button);
		buttonPanel.add(plus90Button);
		buttonPanel.add(acknowledge);
		buttonPanel.add(music);

		// Add button panel to the NORTH position of the layout
		add(buttonPanel, BorderLayout.SOUTH);
	}

	public void setRoomba(Roomba r)
	{
		this.roomba = r;
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		for (int row = 0; row <= GRID_ROWS; row++)
		{
			int y = row * CELL_SIZE;
			g.drawLine(0, y, WIDTH, y);
		}
		for (int col = 0; col <= GRID_COLS; col++)
		{
			int x = col * CELL_SIZE;
			g.drawLine(x, 0, x, HEIGHT);
		}
		try
		{
			Image roombaImage = ImageIO.read(new File("roomba.png"));
			int originalWidth = roombaImage.getWidth(null);
			int originalHeight = roombaImage.getHeight(null);
			double scaleFactor = 0.2;
			int roombaWidth = (int) (originalWidth * scaleFactor);
			int roombaHeight = (int) (originalHeight * scaleFactor);
			int roombaX = (int) roomba.getX() - roombaWidth / 2;
			int roombaY = (int) roomba.getY() - roombaHeight / 2;
			AffineTransform transform = new AffineTransform();
			transform.rotate(Math.toRadians(roomba.getAngle()), roombaX + roombaWidth / 2, roombaY + roombaHeight / 2);
			Graphics2D g2d = (Graphics2D) g.create();
			for (int i = 0; i < roomba.getLine().size(); i++)
			{
				g2d.setColor(Color.RED);
				Line2D line = roomba.getLine().get(i);
				g2d.setStroke(new BasicStroke(2));
				g2d.draw(line);
			}
			g2d.transform(transform);
			g2d.drawImage(roombaImage, roombaX, roombaY, roombaWidth, roombaHeight, null);
			g2d.dispose();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		ArrayList<SensorImage> images = roomba.getImages();
		for (int j = 0; j < images.size(); j++)
		{
			try
			{
				Image image = ImageIO.read(new File(images.get(j).getImage()));
				int originalWidth = image.getWidth(null);
				int originalHeight = image.getHeight(null);
				int imageX = (int) images.get(j).getX() - originalWidth / 2;
				int imageY = (int) images.get(j).getY() - originalHeight / 2;
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.drawImage(image, imageX, imageY, originalWidth, originalHeight, null);
				g2d.dispose();
			} catch (IOException e)
			{
			}
		}
		ArrayList<Bogey> bogeys = Main.getBogeys();
		for (int i = 0; i < bogeys.size(); i++)
		{
			try
			{
				Image bogeyImage = ImageIO.read(new File(bogeys.get(i).getImage()));
				int originalWidth = bogeyImage.getWidth(null);
				int originalHeight = bogeyImage.getHeight(null);
				double scaleFactor = 0.01574803 * bogeys.get(i).getDiameter();
				int bogeyWidth = (int) (originalWidth * scaleFactor);
				int bogeyHeight = (int) (originalHeight * scaleFactor);
				int bogeyX = (int) bogeys.get(i).getX() - bogeyWidth / 2;
				int bogeyY = (int) bogeys.get(i).getY() - bogeyHeight / 2;
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.drawImage(bogeyImage, bogeyX, bogeyY, bogeyWidth, bogeyHeight, null);
				g2d.dispose();
			} catch (IOException e)
			{
				System.out.println("Error finding the object: " + i);
			}
		}

	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			Main.sendDirection('=');
		}

		try
		{
			Thread.sleep(500); // wait for 250 milliseconds (i.e., a quarter second)
		} catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub

	}

}
