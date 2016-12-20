import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JMenuItem;
import javax.swing.BoxLayout;

import java.awt.CardLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JEditorPane;

import java.awt.Panel;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JScrollBar;

public class Paint
{

	JButton clearBtn, blackBtn, greenBtn, selectBtn, btnFindObject, btnPointCircle, btnFreeCircle;
	DrawArea drawArea;
	
	private JSpinner spinner;
	private JMenuBar menuBar;
	private JMenu mnNewMenu;
	private JMenuItem mntmNew;
	private JMenuItem mntmOpen;
	private JMenuItem mntmSave;
	private Panel panel;
	
	private static final Dimension BLACK_BUTTON_SIZE = new Dimension(100, 5);
	private static final Dimension SELECT_BUTTON_SIZE = new Dimension(100, 5);
	private static final Dimension GREEN_BUTTON_SIZE = new Dimension(100, 5);
	private static final Dimension CLEAR_BUTTON_SIZE = new Dimension(100, 5);
	private static final Dimension SPINNER_SIZE = new Dimension(100, 5);
	private JSplitPane splitPane;
	private JLabel lblNewLabel;
	private JSeparator separator;
	private JSeparator separator_1;
	private JLabel lblSelectionType;
	private JLabel lblSelectionSettings;
	private JScrollBar verticalScroll;
	private JScrollBar HorizontalScroll;

	ActionListener actionListener = new ActionListener()
	{

		public void actionPerformed(ActionEvent e)
		{
			System.out.println("action Source: " + e.getSource());
			if (e.getSource() == clearBtn)
			{
				drawArea.clear();
			} 
			else if (e.getSource() == blackBtn)
			{
				drawArea.black();
			} 
			else if (e.getSource() == greenBtn)
			{
				drawArea.green();
			}
			else if (e.getSource() == selectBtn)
			{
				drawArea.save();
			}
			else if (e.getSource() == btnFreeCircle)
			{
				System.out.println("free");
				drawArea.setModeFreeCircle();
			}
			else if (e.getSource() == btnPointCircle)
			{
				System.out.println("point");
				drawArea.setModePointCircle();
			}
			else if (e.getSource() == btnFindObject)
			{
				System.out.println("find");
				drawArea.setModeFindObject();
			}
		}
	};


	public static void main(String[] args)
	{
		try
		{
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException e)
		{
			// handle exception
		} catch (ClassNotFoundException e)
		{
			// handle exception
		} catch (InstantiationException e)
		{
			// handle exception
		} catch (IllegalAccessException e)
		{
			// handle exception
		}

		// Setup brush sizes
		int brushSize = 5; // Size 1 is smallest 1 pixel
		int lowerBound = -brushSize + 1;
		int count = 0;

		// Brush Size Calc
		/*int indexes[][] = new int[100][2];

		System.out.println("Brush Size: " + brushSize + ", Lower Bound: "
				+ lowerBound);

		System.out.print("{");
		for (int x = lowerBound; x < brushSize; x++)
		{
			for (int y = lowerBound; y < brushSize; y++)
			{
				if (!(x == 0 && y == 0))
				{
					System.out.print("{" + x + "," + y + "}, ");
					// indexes[count][0] = x;
					// indexes[count][0] = y;
					count++;

				}

			}
			System.out.println();
		}
		System.out.print("};");
		*/

		// Create the swing app
		new Paint().show();

	}

	public void show()
	{
		// create main frame
		JFrame frame = new JFrame("Partial Image Placement Software");
		Container content = frame.getContentPane();
		// set layout on content pane
		content.setLayout(new BorderLayout());
		// create draw area
		drawArea = new DrawArea(frame);
		drawArea.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		// add to content pane
		content.add(drawArea, BorderLayout.CENTER);
		

		// create controls to apply colors and call clear feature
		JPanel controls = new JPanel();
		controls.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		clearBtn = new JButton("Clear");
		clearBtn.setBackground(new Color(255, 51, 51));
		clearBtn.setHorizontalAlignment(SwingConstants.LEFT);
		clearBtn.addActionListener(actionListener);
		clearBtn.setPreferredSize(CLEAR_BUTTON_SIZE);
		
		greenBtn = new JButton("Green");
		greenBtn.setHorizontalAlignment(SwingConstants.LEFT);
		greenBtn.addActionListener(actionListener);
		
		HorizontalScroll = new JScrollBar();
		HorizontalScroll.setOrientation(JScrollBar.HORIZONTAL);
		frame.getContentPane().add(HorizontalScroll, BorderLayout.SOUTH);
		
		verticalScroll = new JScrollBar();
		frame.getContentPane().add(verticalScroll, BorderLayout.EAST);
		greenBtn.setPreferredSize(GREEN_BUTTON_SIZE);

		// add to content pane
		content.add(controls, BorderLayout.WEST);
		controls.setLayout(new GridLayout(20, 1, 5, 5));
		
		lblSelectionType = new JLabel("Selection Type");
		lblSelectionType.setHorizontalAlignment(SwingConstants.CENTER);
		controls.add(lblSelectionType);
		
		btnFreeCircle = new JButton("Free Circle");
		btnFreeCircle.setHorizontalAlignment(SwingConstants.LEFT);
		btnFreeCircle.addActionListener(actionListener);
		controls.add(btnFreeCircle);
		
		btnPointCircle = new JButton("Point Circle");
		btnPointCircle.setHorizontalAlignment(SwingConstants.LEFT);
		btnPointCircle.addActionListener(actionListener);
		controls.add(btnPointCircle);
		
		btnFindObject = new JButton("Find Object");
		btnFindObject.setHorizontalAlignment(SwingConstants.LEFT);
		btnFindObject.addActionListener(actionListener);
		controls.add(btnFindObject);
		
		separator = new JSeparator();
		controls.add(separator);
		
		blackBtn = new JButton("Black");
		blackBtn.setHorizontalAlignment(SwingConstants.LEFT);
		blackBtn.addActionListener(actionListener);
		
		lblSelectionSettings = new JLabel("Selection Settings");
		lblSelectionSettings.setHorizontalAlignment(SwingConstants.CENTER);
		controls.add(lblSelectionSettings);
		blackBtn.setPreferredSize(BLACK_BUTTON_SIZE);
		
		controls.add(blackBtn);
		controls.add(greenBtn);
		
		splitPane = new JSplitPane();
		controls.add(splitPane);
		
		lblNewLabel = new JLabel("Brush Size");
		splitPane.setLeftComponent(lblNewLabel);
		
		// Spinner for brush size
		spinner = new JSpinner();
		lblNewLabel.setLabelFor(spinner);
		splitPane.setRightComponent(spinner);
		spinner.addChangeListener(new ChangeListener()
		{
			Integer currentValue = new Integer(1);
			public void stateChanged(ChangeEvent event)
			{
				currentValue = (Integer)spinner.getValue();
				drawArea.setBrushSize(currentValue.intValue());
			}
		});
		spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		spinner.setToolTipText("Brush Size");
		spinner.setPreferredSize(SPINNER_SIZE);
		
		selectBtn = new JButton("Select and Continue");
		selectBtn.setBackground(new Color(0, 204, 51));
		selectBtn.setHorizontalAlignment(SwingConstants.LEFT);
		selectBtn.addActionListener(actionListener);
		
		separator_1 = new JSeparator();
		controls.add(separator_1);
		selectBtn.setPreferredSize(SELECT_BUTTON_SIZE);
		controls.add(selectBtn);
		controls.add(clearBtn);
		
		panel = new Panel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		menuBar = new JMenuBar();
		panel.add(menuBar);
		
		mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		mntmNew = new JMenuItem("New");
		mnNewMenu.add(mntmNew);
		
		mntmOpen = new JMenuItem("Open");
		mnNewMenu.add(mntmOpen);
		
		mntmSave = new JMenuItem("Save");
		mnNewMenu.add(mntmSave);
		panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{menuBar, mnNewMenu, mntmNew, mntmOpen, mntmSave}));

		frame.setSize(600, 600);
		// can close frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// show the swing paint result
		frame.setVisible(true);

	}

}