import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class Paint {

	JButton clearBtn, blackBtn, blueBtn, greenBtn, redBtn, magentaBtn;
	DrawArea drawArea;
	ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == clearBtn) {
				drawArea.clear();
			} else if (e.getSource() == blackBtn) {
				drawArea.black();
			} else if (e.getSource() == blueBtn) {
				drawArea.blue();
			} else if (e.getSource() == greenBtn) {
				drawArea.green();
			} else if (e.getSource() == redBtn) {
				drawArea.red();
			} else if (e.getSource() == magentaBtn) {
				drawArea.magenta();
			}
		}
	};

	public static void main(String[] args)
	{
		try
		{
			// Set cross-platform Java L&F (also called "Metal")
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e)
		{
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
			// handle exception
		}
		catch (IllegalAccessException e) {
			// handle exception
		}
		
		// Setup brush sizes
		int brushSize = 4; // Size 1 is smallest 1 pixel
		int lowerBound = -brushSize + 1;
		int count = 0;
		
		int indexes[][] = new int[100][2];
		
		System.out.println("Brush Size: " + brushSize + ", Lower Bound: " + lowerBound);
		for(int x = lowerBound; x < brushSize; x++)
		{
			for(int y = lowerBound; y < brushSize; y++)
			{
				if(!(x == 0 && y == 0))
				{
					//System.out.print("[" + x + "," + y + "]");
					indexes[count][0] = x;
					indexes[count][0] = y;
					count++;
				}
			}
			//System.out.println();
		}

		// Create the swing app
		new Paint().show();
	}

	public void show() {
		// create main frame
		JFrame frame = new JFrame("CS534 Final Project Paint");
		Container content = frame.getContentPane();
		// set layout on content pane
		content.setLayout(new BorderLayout());
		// create draw area
		drawArea = new DrawArea();

		// add to content pane
		content.add(drawArea, BorderLayout.CENTER);

		// create controls to apply colors and call clear feature
		JPanel controls = new JPanel();

		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(actionListener);
		blackBtn = new JButton("Black");
		controls.add(blackBtn);
		blackBtn.addActionListener(actionListener);
		blueBtn = new JButton("Blue");
		controls.add(blueBtn);
		blueBtn.addActionListener(actionListener);
		greenBtn = new JButton("Green");
		controls.add(greenBtn);
		greenBtn.addActionListener(actionListener);
		magentaBtn = new JButton("Magenta");
		controls.add(magentaBtn);
		magentaBtn.addActionListener(actionListener);
		redBtn = new JButton("Red");
		controls.add(redBtn);
		redBtn.addActionListener(actionListener);
		controls.add(clearBtn);

		// add to content pane
		content.add(controls, BorderLayout.NORTH);

		frame.setSize(600, 600);
		// can close frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// show the swing paint result
		frame.setVisible(true);

		// Now you can try our Swing Paint !!! Enjoy <img src="http://www.ssaurel.com/blog/wp-includes/images/smilies/icon_biggrin.gif" alt=":D" class="wp-smiley">
	}

}