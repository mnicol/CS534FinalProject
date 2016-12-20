import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class DrawArea extends JComponent
{
	// Mode that we are currently in
	private String selectionMode = "freeCircle";
	
	// Image in which we're going to draw
	private Image image;

	// BackgroundImage
	private BufferedImage buffImg;
	
	private BufferedImage drawingLayer;

	// Graphics2D object ==> used to draw on
	private Graphics2D g2;
	
	private File imageA, imageB;

	// Brush Size Options
	private int[][] offsetArray;
	
	private static final int HORIZONTAL_OFFSET = 10;
	
	// Initial brush size on startup
	private static final int INITIAL_BRUSH_SIZE = 1;
	
	// Input Image Dimensions
	private int height = 600;
	private int width = 600;

	// Mouse coordinates
	private int currentX_drag, currentY_drag, oldX, oldY, newX, newY;
	private int currentX, currentY;
	private int findLeftX, findBottomY;
	
	// Refrence to the frame for resizing
	private JFrame frame = null;
	
	// Current Paint Color
	private Color curColor = Color.black;

	public DrawArea(JFrame frame)
	{
		this.frame = frame;
		
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				// save coord x,y when mouse is pressed
				oldX = e.getX();
				oldY = e.getY();
				
				if(g2 != null && selectionMode.equals("findObject"))
				{
					image = buffImg;
					repaint();
				}
				
				System.out.println("X Coordinate: " + oldX + ", Y Coordinate: " + oldY);
			}
			
			public void mouseReleased(MouseEvent e)
			{
				newX = e.getX();
				newY = e.getY();
				
				if(g2 != null && selectionMode.equals("findObject"))
				{
					// Draw the first pixel that is the middle
					g2.drawLine(oldX, oldY, oldX, newY);
					g2.drawLine(oldX, oldY, newX, oldY);
					g2.drawLine(oldX, newY, newX, newY);
					g2.drawLine(newX, oldY, newX, newY);
					
					System.out.println("First point: " + oldX + ", " + oldY);
					System.out.println("Second point: " + newX + ", " + newY);

					// refresh the draw area with new lines
					repaint();
				}
		    }
		});
		
		addMouseMotionListener(new MouseMotionAdapter()
		{
			// Detect when to draw a line
			public void mouseDragged(MouseEvent e)
			{
				currentX_drag = e.getX();
				currentY_drag = e.getY();

				if (g2 != null)
				{
					if(selectionMode.equals("freeCircle"))
					{
						// Draw the first pixel that is the middle
						g2.drawLine(oldX, oldY, currentX_drag, currentY_drag);
	
						// refresh the draw area with new lines
						repaint();
						
						// Update coordinates
						oldX = currentX_drag;
						oldY = currentY_drag;
					}
					else if(selectionMode.equals("pointCircle"))
					{
						
					}
				}
			}
		});
		
	}

	private BufferedImage buffImg2 = null;
	// Setup area that can be drawn on
	protected void paintComponent(Graphics g)
	{
		if (image == null)
		{
			BufferedImage buffImg = null;
			
			try
			{
				//File file = new File("elk.jpg");
				FileInputStream fis = new FileInputStream(imageA);
				buffImg = ImageIO.read(fis); // reading the image file
				
				FileInputStream fis2 = new FileInputStream(imageB);
				buffImg2 = ImageIO.read(fis2); // reading the image file
				
				drawingLayer = new BufferedImage(buffImg2.getWidth(), buffImg2.getHeight(), BufferedImage.TYPE_INT_ARGB);
				
			} catch (IOException e)
			{
				System.out.println("Error With Image Input: " + e.getMessage());
			}

			
			// Set the background to an image
			if(buffImg != null)
			{
				image = buffImg2 ;
			}
			else
			{
				image = createImage(getSize().width, getSize().height);
			}
			
			height = image.getHeight(null);
			width = image.getWidth(null);
			
			if(drawingLayer != null)
			{
				g2 = (Graphics2D) drawingLayer.getGraphics();
			}
			else
			{
				g2 = (Graphics2D) image.getGraphics();
			}
			
			// enable antialiasing
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g2.setPaint(curColor);

		}

		g.drawImage(image, 0, 0, null);
		g.drawImage(drawingLayer, 0, 0, null);
		frame.setSize(width + 205, height + 105);
		//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	// now we create exposed methods
	public void clear()
	{
		int confirmationResult = JOptionPane.showConfirmDialog(null, 
									"This will clear all of your changes are you sure you want to do this?", 
									"Warning!", 
									JOptionPane.YES_NO_OPTION);
		
		if(confirmationResult == JOptionPane.YES_OPTION)
		{
			// Reset Background
			image = buffImg;
			
			findLeftX = 0;
			findBottomY = 0;
			
			repaint();
		}
	}
	
	// Sets the current mode to selecting an area from a free hand drawn circle
	public void setModeFreeCircle()
	{
		this.selectionMode = "freeCircle";
		clear();
	}
	
	// Sets the current mode to selecting my connected dots
	public void setModePointCircle()
	{
		this.selectionMode = "pointCircle";
		clear();
	}
	
	// Use a rectangle to select an object and automatically get that object
	public void setModeFindObject()
	{
		this.selectionMode = "findObject";
		clear();
	}
	
	// Sets the current thickness of the brush being used
	public void setBrushSize(int size)
	{
	}
	
	// Saves the drawn image to be interpreted by the matlab functions
	public void save()
	{
		image = buffImg2;
		
		repaint();
		try
		{
			ImageIO.write(drawingLayer, "PNG", new File("./drawnArea.PNG"));
			
			System.out.println("-- Saved");

		} catch (IOException ie)
		{
			ie.printStackTrace();
		}
	}

	// Color selections
	public void black()
	{
		g2.setPaint(Color.black);
		curColor = Color.black;
	}

	public void green()
	{
		g2.setPaint(Color.green);
		curColor = Color.green;
	}
	
	public void setImageA(File a)
	{
		this.imageA = a;	
	}
	
	public void setImageB(File b)
	{
		this.imageB = b;	
	}
}