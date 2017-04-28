package aston.group18.view;
import  DefaultPackage.Simulator;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



public class SimulatorGUI extends Simulator {

	private JFrame mainFrame;
	private JFrame settingFrame;
	private Slider pumpSlider;
	private Slider tillSlider;
	private Slider scarSlider;
	private Slider motoSlider;
	private Slider sedanSlider;
	private Slider truckSlider;
	///////////////////
	// private JFrame createFrame;
	private JPanel topPannel;
	private JLabel title;
	////////////////////////
	private JPanel navPanel;
	private JButton settings;
	private JButton run;
	private JButton save;
	private JButton cancel;
	private JPanel left;
	private JPanel right;
	//////////////////////////////////
	private JPanel settingsContainer;
	private JPanel settingsTopPanel;
	private JLabel settingsTitleLable;
	private JPanel settingsCenterPanel;
	private JPanel settingsCenterTop;
	private JPanel settingsCenterbottom;
	private JPanel settingsCentermid;
	private JLabel vehiclestitle;
	private JPanel settingsBottom;
	///////////////////////////////
	private JCheckBox allowTrucks;

	public SimulatorGUI() {
		// super(seed);
		/*
		 * try {
		 * UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 * } catch (ClassNotFoundException e) { e.printStackTrace(); } catch
		 * (InstantiationException e) { e.printStackTrace(); } catch
		 * (IllegalAccessException e) { e.printStackTrace(); } catch
		 * (UnsupportedLookAndFeelException e) { e.printStackTrace(); }
		 */
		// UIManager.installLookAndFeel(mainFrame);
		/*
		 * try {
		 * 
		 * for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		 * if ("metal".equals(info.getName())) {
		 * UIManager.setLookAndFeel(info.getClassName()); break; } }
		 * 
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */
	}

	public void mainWindow() {

		mainFrame = new JFrame("Simulator");
		mainFrame.setLayout(new BorderLayout());
		((JPanel) mainFrame.getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));

		// force to close process on exit.
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		// set frame size
		mainFrame.setSize(700, 700);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);

		// creates top panel
		topPannel = new JPanel();
		// sets layout for top panel.
		topPannel.setLayout(new FlowLayout());
		// add creates jlabel.
		title = new JLabel();
		title.setText("Simulator");
		// add jlable to top panel
		topPannel.add(title);

		//////////////////////////////
		////////////////////////////////
		navPanel = new JPanel();
		navPanel.setLayout(new FlowLayout());

		left = new JPanel();

		settings = new JButton();
		settings.setText("Settings");
		settings.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e2) {
				// TODO Auto-generated method stub
				settingsWindow();
			}
		});

		run = new JButton();
		run.setText("Run Simulator");

		right = new JPanel();

		navPanel.add(left, BorderLayout.WEST);
		navPanel.add(settings, BorderLayout.CENTER);
		navPanel.add(run, BorderLayout.CENTER);
		navPanel.add(right, BorderLayout.EAST);
		///////////////////////////////
		// add components to jframe;
		mainFrame.add(topPannel, BorderLayout.NORTH);
		mainFrame.add(navPanel, BorderLayout.SOUTH);

		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitWindow(mainFrame);
			}
		});

	}

	public void makeVisible() {
		mainFrame.setVisible(true);
	}

	public void settingsWindow() {

		settingFrame = new JFrame("Settings");
		settingFrame.setSize(500, 375);
		settingFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		settingFrame.setResizable(false);
		settingFrame.setLocationRelativeTo(mainFrame);
		settingFrame.setVisible(true);

		settingFrame.setLayout(new BorderLayout());
		((JPanel) settingFrame.getContentPane()).setBorder(new EmptyBorder(5, 5, 5, 5));

		settingFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitWindow(settingFrame);
			}

		});

		settingsContainer = new JPanel();
		settingsContainer.setLayout(new BorderLayout());
		settingsContainer.setBorder(BorderFactory.createEtchedBorder());

		settingsTopPanel = new JPanel();
		settingsTopPanel.setLayout(new FlowLayout());

		settingsTitleLable = new JLabel();
		settingsTitleLable.setText("Settings");
		///
		settingsTopPanel.add(settingsTitleLable, new BorderLayout());
		///
		settingsCenterPanel = new JPanel();
		settingsCenterPanel.setLayout(new FlowLayout());
		settingsCenterPanel.setBorder(BorderFactory.createEtchedBorder());
		////
		scarSlider = new Slider("Vodka: ", 0.01, 0.05, 0.01, 100, false);
		motoSlider = new Slider("Whiskey : ", 0.01, 0.05, 0.01, 100, false);
		motoSlider.getSlider().setEnabled(false);
		truckSlider = new Slider("Brandy: ", 0.01, 0.05, 0.01, 100, false);
		truckSlider.getSlider().setEnabled(false);

		sedanSlider = new Slider("champagne : ", 0.01, 0.05, 0.01, 100, false);

		tillSlider = new Slider("Wine: ", 1, 5, 5, 1, true);
		pumpSlider = new Slider("Repeat :): ", 1, 5, 5, 1, true);

		vehiclestitle = new JLabel("Vehicles Spawn Ratio");

		settingsCenterTop = new JPanel(new FlowLayout());

		settingsCenterTop.add(vehiclestitle);

		settingsCentermid = new JPanel(new GridLayout(2, 2));

		settingsCentermid.add(scarSlider);
		settingsCentermid.add(motoSlider);
		settingsCentermid.add(sedanSlider);

		// ================================================//
		settingsCenterbottom = new JPanel(new GridLayout(2, 2));
		allowTrucks = new JCheckBox("Allow Trucks");

		allowTrucks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!truckSlider.getSlider().isEnabled()) {
					truckSlider.getSlider().setEnabled(true);
					// station.setAllowtrucks(true);
				} else if (truckSlider.getSlider().isEnabled()) {
					truckSlider.getSlider().setEnabled(false);
					// station.setAllowtrucks(true);
				}

			}
		});

		settingsCenterbottom.add(truckSlider);
		settingsCenterbottom.add(allowTrucks);
		settingsCenterbottom.add(tillSlider);
		settingsCenterbottom.add(pumpSlider);

		settingsCenterPanel.add(settingsCenterTop, BorderLayout.NORTH);
		settingsCenterPanel.add(settingsCentermid, BorderLayout.CENTER);
		settingsCenterPanel.add(settingsCenterbottom, BorderLayout.SOUTH);

		//////////////////////////////

		save = new JButton("Save");

		save.addActionListener(new save());

		cancel = new JButton("Cancel");

		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exitWindow(settingFrame);

			}
		});

		settingsBottom = new JPanel(new FlowLayout());
		settingsBottom.add(save);
		settingsBottom.add(cancel);

		settingsContainer.add(settingsTopPanel, BorderLayout.NORTH);
		settingsContainer.add(settingsCenterPanel, BorderLayout.CENTER);
		settingsContainer.add(settingsBottom, BorderLayout.SOUTH);
		settingFrame.add(settingsContainer);
		settingFrame.setVisible(true);
	}

	private void exitWindow(JFrame frame) {

		int response = JOptionPane.showConfirmDialog(frame, "Do you really want to quit?", "Quit?",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			frame.dispose();
		}

	}

	private class save implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			// p=scarSlider.getValue().doubleValue();
			// pumpCount = pumpSlider.getValue().intValue();
			// tillCount = tillSlider.getValue().intValue();
			System.out.println(p);

		}

	}

	public static void main(String[] args) {

		SimulatorGUI s = new SimulatorGUI();
		s.mainWindow();
		// s.settingsWindow();
		s.makeVisible();
	}

}
