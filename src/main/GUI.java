package main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import graphics.GPanel;

/**
 * GUI JFrame class for setting the panes, functional interface
 * and graphics.
 *
 * @author Alexander Sniffin
 * @project DiningPhilosophers
 * @date Mar 20, 2016
 */
public class GUI extends JFrame {
	
	/**
	 * Reference to the static Philosopher List
	 */
	private static ArrayList<Philosopher> philosophers = Driver.philosophers;
	
	/**
	 * Reference to the static Philosopher List
	 */
	private static ArrayList<Fork> forks = Driver.forks;
	
	private GPanel graphics;

	private static ExecutorService es = null;
	
	public static ExecutorService getEs() {
		return es;
	}

	public static void setEs(ExecutorService es) {
		GUI.es = es;
	}

	private boolean started = false;
	
	/**
	 * The total amount of Philosophers
	 */
	private int total = 5;

	private JButton startStop;
	private JPanel statusPane;
	private JSlider slider;
	
	private JLabel statuses[] = new JLabel[Config.TOTAL_PHILOSOPHERS];
	private JLabel eatCounts[] = new JLabel[Config.TOTAL_PHILOSOPHERS];
	private JLabel names[] = new JLabel[Config.TOTAL_PHILOSOPHERS];
	
	/**
	 * Set up the GUI
	 * 
	 * @param title Title bar text
	 * @param size Size of the JFrame
	 */
	public GUI(String title, Dimension size) {
		this.setTitle(title);
		this.setPreferredSize(size);
		this.setResizable(false);
		this.setVisible(true);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		this.getContentPane().setLayout(gridBagLayout);	
		
		graphics = new GPanel(Config.TABLE_DIAMETER);       
		
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 10;
		gbc_panel_1.insets = new Insets(0, 0, 0, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		this.getContentPane().add(graphics, gbc_panel_1);
		
		addMenus(gridBagLayout);
		addRightPane(gridBagLayout);
		
		this.pack();
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	//close threads
		        System.exit(0);
		    }
		});
		
		JOptionPane.showMessageDialog(this, 
				"Set the amount of Philosophers and then click start.", "Welcome",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Adds in the menus
	 * @param gridBagLayout 
	 */
	public void addMenus(GridBagLayout gridBagLayout) {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem exit = new JMenuItem("Exit", KeyEvent.VK_X);
		JMenuItem about = new JMenuItem("About", KeyEvent.VK_A);
		
		exit.setActionCommand("exit");
		about.setActionCommand("about");
		
		exit.addActionListener(new GUIAction());
		about.addActionListener(new GUIAction());
		
		mnFile.add(exit);
		mnHelp.add(about);
	}
	
	/**
	 * Adds in the right pane
	 * @param gridBagLayout 
	 */
	public void addRightPane(GridBagLayout gridBagLayout) {
		JPanel rightPanel = new JPanel();
		rightPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.EAST;
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 10;
		gbc_panel.gridy = 0;
		this.getContentPane().add(rightPanel, gbc_panel);
		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		rightPanel.setLayout(gbl_panel);
		
		startStop = new JButton("Start");
		startStop.addActionListener(new GUIAction());
		startStop.setActionCommand("startOrStop");
		
		JLabel lblConfig = new JLabel("Configuration");
		lblConfig.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblConfig = new GridBagConstraints();
		gbc_lblConfig.gridwidth = 6;
		gbc_lblConfig.insets = new Insets(8, 0, 5, 0);
		gbc_lblConfig.gridx = 0;
		gbc_lblConfig.gridy = 0;
		rightPanel.add(lblConfig, gbc_lblConfig);
		
		GridBagConstraints gbc_startStop = new GridBagConstraints();
		gbc_startStop.fill = GridBagConstraints.VERTICAL;
		gbc_startStop.gridheight = 2;
		gbc_startStop.gridwidth = 2;
		gbc_startStop.insets = new Insets(0, 5, 5, 5);
		gbc_startStop.gridx = 0;
		gbc_startStop.gridy = 1;
		rightPanel.add(startStop, gbc_startStop);
		
		JLabel lblAmount = new JLabel("Amount");
		GridBagConstraints gbc_lblAmount = new GridBagConstraints();
		gbc_lblAmount.gridwidth = 4;
		gbc_lblAmount.insets = new Insets(0, 0, 5, 0);
		gbc_lblAmount.gridx = 2;
		gbc_lblAmount.gridy = 1;
		rightPanel.add(lblAmount, gbc_lblAmount);
		
		slider = new JSlider(JSlider.HORIZONTAL, 3, 16, 5);
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.insets = new Insets(0, 0, 5, 0);
		gbc_slider.gridwidth = 4;
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.gridx = 2;
		gbc_slider.gridy = 2;
		rightPanel.add(slider, gbc_slider);
		
		//Anon listener
		slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				int amount = (int)source.getValue();
				
				if (!started)
					total = Config.TOTAL_PHILOSOPHERS - (Config.TOTAL_PHILOSOPHERS - amount);
			}
		});
		
		JLabel lblNewLabel = new JLabel("Details");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 6;
		gbc_lblNewLabel.insets = new Insets(5, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		rightPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		statusPane = new JPanel();
		statusPane.setBorder(BorderFactory.createEtchedBorder());
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 6;
		gbc_scrollPane.insets = new Insets(5, 5, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 4;
		rightPanel.add(statusPane, gbc_scrollPane);
		
		GridBagLayout gbl_statusLayout = new GridBagLayout();
		gbl_statusLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_statusLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_statusLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_statusLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		statusPane.setLayout(gbl_statusLayout);
		
		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.fill = GridBagConstraints.BOTH;
		gbc_lblName.insets = new Insets(5, 5, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		statusPane.add(lblName, gbc_lblName);
		
		JLabel lblCount = new JLabel("Count");
		lblCount.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCount = new GridBagConstraints();
		gbc_lblCount.fill = GridBagConstraints.BOTH;
		gbc_lblCount.insets = new Insets(5, 5, 5, 5);
		gbc_lblCount.gridx = 1;
		gbc_lblCount.gridy = 0;
		statusPane.add(lblCount, gbc_lblCount);
		
		JLabel lblStatus = new JLabel("Status");
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.fill = GridBagConstraints.BOTH;
		gbc_lblStatus.insets = new Insets(5, 5, 5, 5);
		gbc_lblStatus.gridx = 2;
		gbc_lblStatus.gridy = 0;
		statusPane.add(lblStatus, gbc_lblStatus);

	}
	
	public void updateDetails() {
		for (int i = 0; i < total; i++) {
			if (names[i] != null && eatCounts[i] != null && statuses[i] != null) {
				names[i].setText(philosophers.get(i).getName() + "[" + philosophers.get(i).getId() + "]:");
				eatCounts[i].setText("" + philosophers.get(i).getEatCount());
				statuses[i].setText("" + philosophers.get(i).getStatus());
			} else break;
		}
	}
	
	public GPanel getDrawing() {
		return graphics;
	}
	
	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}
	
	private class GUIAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			switch (e.getActionCommand()) {
				case "exit":
					if (es != null) {
						es.shutdown();
						try {
							while (!es.isTerminated()) {
								Thread.sleep(1000);
							}
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
			        System.exit(0);
					break;
				
				case "about":
					JOptionPane.showMessageDialog(GUI.this, 
							Config.ABOUT, "About",
							JOptionPane.INFORMATION_MESSAGE);
					break;
					
				case "startOrStop":
					try {
						if (!started) {
							for (int i = 0; i < total; i++)
								forks.add(new Fork(i));

							//Create our Philosophers
							for (int i = 0; i < total; i++)
								philosophers.add(new Philosopher(i, Config.NAMES[i], true, forks.get(i), forks.get((i + (total - 1)) % total)));
							
							for (int i = 0; i < philosophers.size(); i++) {
								philosophers.get(i).setLeftNeighbor(philosophers.get((i + 1) % total));
								philosophers.get(i).setRightNeighbor(philosophers.get((i + (total - 1)) % total));
							}
							
							graphics.createPhilosophers(graphics.getG2d(), graphics.getWidth()/2, graphics.getHeight()/2, Config.TABLE_DIAMETER/2, total);
							graphics.createForks(graphics.getG2d(), graphics.getWidth()/2, graphics.getHeight()/2, Config.TABLE_DIAMETER/2, total);
							
							if ((names[0] == null && eatCounts[0] == null && statuses[0] == null) ||
								(names[0].getText() == "" && eatCounts[0].getText() == "" && statuses[0].getText() == ""))
								for (int i = 0; i < total; i++) {
									names[i] = new JLabel(philosophers.get(i).getName() + "[" + philosophers.get(i).getId() + "]:");
	
									GridBagConstraints gbc_name = new GridBagConstraints();
									gbc_name.fill = GridBagConstraints.BOTH;
									gbc_name.insets = new Insets(5, 5, 5, 5);
									gbc_name.gridx = 0;
									gbc_name.gridy = i + 2;
									statusPane.add(names[i], gbc_name);
									
									eatCounts[i] = new JLabel("" + philosophers.get(i).getEatCount());
	
									GridBagConstraints gbc_count = new GridBagConstraints();
									gbc_count.fill = GridBagConstraints.BOTH;
									gbc_count.insets = new Insets(5, 5, 5, 5);
									gbc_count.gridx = 1;
									gbc_count.gridy = i + 2;
									statusPane.add(eatCounts[i], gbc_count);
									
									statuses[i] = new JLabel("" + philosophers.get(i).getStatus());
	
									GridBagConstraints gbc_status = new GridBagConstraints();
									gbc_status.fill = GridBagConstraints.BOTH;
									gbc_status.insets = new Insets(5, 5, 5, 5);
									gbc_status.gridx = 2;
									gbc_status.gridy = i + 2;
									statusPane.add(statuses[i], gbc_status);
								}
								
							graphics.setLoad(true);
							
							es = Executors.newFixedThreadPool(total);
							
							philosophers.forEach(philosophers -> { 
								philosophers.setHungry(true);
								es.execute(philosophers);
							});
							
							statusPane.revalidate();
							statusPane.repaint();
							
							slider.disable();
							startStop.setText("Stop");
						} else {
							graphics.setLoad(false);
							
							philosophers.forEach(philosophers -> { 
								philosophers.setHungry(false);
								philosophers.setStatus("Away");
							});
							
							es.shutdown();
							
							while (!es.isTerminated()) {
								Thread.sleep(1000);
							}
							
							for (int i = 0; i < total; i++) {
								names[i].setText("");
								statuses[i].setText("");
								eatCounts[i].setText("");
							}
							
							//Clean out the philosophers and forks and make room for new ones..
							philosophers.removeAll(philosophers);
							forks.removeAll(forks);
							
							slider.enable();
							startStop.setText("Start");
						}
						started = !started;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;
			}
		}
	}
}