package simulateur;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;

import labyrinthe.InterfaceEditeur;
import labyrinthe.Labyrinthe;
import labyrinthe.ListeLabyrinthes;
import labyrinthe.Salle;
import personnages.Personnage;

@SuppressWarnings("serial")
public class Simulateur extends JFrame implements Runnable {

	GridBagConstraints gbc;
	static Salle[][] tab;
	File fileSelected;
	ArrayList<Labyrinthe> labyrinthes;
	ListeLabyrinthes listeLabyrinthes = new ListeLabyrinthes();
	Labyrinthe laby;
	ArrayList<Salle> listSalle = new ArrayList<Salle>();
	Salle[][] tabSalles;
	JPanel panel_laby = new JPanel();
	JPanel content_pane = new JPanel();
	JPanel panelPerso = new JPanel();
	JPanel panelDroit = new JPanel();
	JPanel panelLog = new JPanel();
	JPanel panelButton = new JPanel();
	ArrayList<Personnage> personnage;
	JScrollPane scrollpaneLaby = new JScrollPane();

	static JList<Personnage> listPerso;
	DefaultListModel<Personnage> listeModelPerso;
	Personnage persoSelected;
	String race;

	public Simulateur(Labyrinthe laby, ArrayList<Personnage> persos) {
		this.laby = laby;
		this.personnage = persos;
		init();
		initMenu();
		InterfaceEditeur.EtablirLabyrinthe(laby, panel_laby);
		this.setVisible(true);

	}

	public static JList<Personnage> getJlist() {
		return listPerso;
	}

	protected void init() {

		this.setTitle("Simulateur");
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		listeModelPerso = new DefaultListModel<Personnage>();
		listPerso = new JList<Personnage>(listeModelPerso);
		JScrollPane scrollpanePerso = new JScrollPane(listPerso);
//		scrollpanePerso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
//				10));
		initPersoIntoList();
		panelPerso.add(scrollpanePerso);
		content_pane = new JPanel();
		content_pane
				.setLayout(new BoxLayout(content_pane, BoxLayout.LINE_AXIS));
		int xSize = ((int) Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth());
		int ySize = ((int) Toolkit.getDefaultToolkit().getScreenSize()
				.getHeight());
		int gameHeightLaby = (int) (Math.round(ySize * 1));
		int gameWidthLaby = (int) (Math.round(xSize * 0.85));
		panel_laby.setPreferredSize(new Dimension(gameWidthLaby,
				gameHeightLaby));

		int gameHeightPanelDroit = (int) (Math.round(ySize * 0.95));
		int gameWidthPanelDroit = (int) (Math.round(xSize * 0.15));
		int gameHeightPanelPerso = (int) (Math.round(ySize * 0.5));
		panelDroit.setLayout(new BoxLayout(panelDroit, BoxLayout.PAGE_AXIS));
		panelPerso.setPreferredSize(new Dimension(gameWidthPanelDroit,
				gameHeightPanelPerso));
		panelLog.setPreferredSize(new Dimension(gameWidthPanelDroit,
				gameHeightPanelPerso));
		panelDroit.setPreferredSize(new Dimension(gameWidthPanelDroit,
				gameHeightPanelDroit));

		panelDroit.add(panelPerso);
		panelDroit.add(panelLog);
		int gameHeightButton = (int) (Math.round(ySize * 0.05));
		panelButton.setPreferredSize(new Dimension(gameWidthPanelDroit,
				gameHeightButton));
		JButton startSimu = new JButton("Start");

		panelButton.add(startSimu);
		panelDroit.add(panelButton);
		
		//InterfaceEditeur.panel_labyrinthe.setPreferredSize(new Dimension(gbc.gridx * 98, gbc.gridy * 98));
		
		scrollpaneLaby = new JScrollPane(panel_laby);
		scrollpaneLaby.setViewportView(panel_laby);

		content_pane.add(scrollpaneLaby);
		content_pane.add(panelDroit);

		listPerso.addMouseListener(new SelectedPersoListener());

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());

		this.setContentPane(content_pane);

		UIManager.LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();
		for (int i = 0; i < laf.length; i++) {
			System.out.println("Class name: " + laf[i].getClassName());
			System.out.println("Name: " + laf[i].getName());
		}
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu(" Menu ");
		JMenuItem load = new JMenuItem("Charger Simulateur");
		JMenuItem save = new JMenuItem("Sauvegarder Simulateur");
		menu.add(load);
		menu.add(save);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		load.addActionListener(new ChargerMenuListener());
	}

	/*
	 * protected boolean joueurAGagne() { if (deplacement(p, s) ==
	 * InterfaceEditeur.tab[laby.getX()][laby.getY()].equals("exit")) { return
	 * true; }else return false;
	 * 
	 * }
	 */

	private void initPersoIntoList() {
		System.out.println(this.personnage);
		for (Personnage perso : this.personnage) {
			listeModelPerso.addElement(perso);
		}
	}

	protected void deplacementJoueur(Personnage p) {

	}

	protected void deplacementRandom(Personnage p) {
		String[] directionTab = { "north", "south", "west", "east", "stand" };
		int ranDirection = (int) (Math.random() * directionTab.length);
		String direction = directionTab[ranDirection];
		deplacement(p, direction);
	}

	protected Component deplacement(Personnage p, String s) {
		try {
			if (s.equalsIgnoreCase("z")) {
				InterfaceEditeur.tab[laby.getX()][laby.getY()].raz();
				getContentPane().revalidate();
				getContentPane().repaint();
				return InterfaceEditeur.tab[laby.getX()][laby.getY()+1].add(new JLabel("sqdsqd"));
//				return InterfaceEditeur.tab[laby.getX()][laby.getY() + 1].add(p
//						.setImage());
			} else if (s.equalsIgnoreCase("s")) {
				InterfaceEditeur.tab[laby.getX()][laby.getY() -1].raz();
				getContentPane().revalidate();
				getContentPane().repaint();
				InterfaceEditeur.tab[0][0].add(new JLabel(new ImageIcon("./images/o-light.png")));
				validate();
				repaint();
//				panel_labyrinthe.revalidate();
//				panel_labyrinthe.repaint();
//				return InterfaceEditeur.tab[laby.getX()][laby.getY() - 1].add(p
//						.setImage());
				return InterfaceEditeur.tab[laby.getX()][laby.getY()-1].add(new JLabel(new ImageIcon("./images/o-light.png")));
			} else if (s.equalsIgnoreCase("q")) {
				InterfaceEditeur.tab[laby.getX()][laby.getY()].raz();
				getContentPane().revalidate();
				getContentPane().repaint();
				return InterfaceEditeur.tab[laby.getX()-1][laby.getY()].add(new JLabel(new ImageIcon("./images/o-light.png")));
//				return InterfaceEditeur.tab[laby.getX()-1][laby.getY()].add(new JLabel("sqdsqd"));
//				return InterfaceEditeur.tab[laby.getX() - 1][laby.getY()].add(p
//						.setImage());
			} else if (s.equalsIgnoreCase("d")) {
				InterfaceEditeur.tab[laby.getX()][laby.getY()].raz();
				getContentPane().revalidate();
				getContentPane().repaint();
				return InterfaceEditeur.tab[laby.getX() + 1][laby.getY()].add(new JLabel(new ImageIcon("./images/o-light.png")));
//				return InterfaceEditeur.tab[laby.getX()+1][laby.getY()].add(new JLabel("sqdsqd"));
//				return InterfaceEditeur.tab[laby.getX() + 1][laby.getY()].add(p
//						.setImage());
			} else {
				getContentPane().revalidate();
				getContentPane().repaint();
				return InterfaceEditeur.tab[laby.getX()][laby.getY()].add(new JLabel(new ImageIcon("./images/o-light.png")));
//				return InterfaceEditeur.tab[laby.getX()][laby.getY()].add(p
//						.setImage());
			}
		} catch (IndexOutOfBoundsException e) {
			JOptionPane.showMessageDialog(Simulateur.this,
					"Vous ne pouvez pas sortir du labyrinthe ! Bien tenté!!",
					"Tricheur !!", JOptionPane.ERROR_MESSAGE);
		}
		return InterfaceEditeur.tab[laby.getX()][laby.getY()].add(new JLabel(new ImageIcon("./images/o-light.png")));

//		return InterfaceEditeur.tab[laby.getX()][laby.getY()].add(p.setImage());
	}

	private class MyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				deplacement(Simulateur.getJlist().getSelectedValue(),
						Character.toString(e.getKeyChar()));
				getContentPane().revalidate();
				getContentPane().repaint();
				revalidate();
				repaint();
				
			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
			} else if (e.getID() == KeyEvent.KEY_TYPED) {
			}
			return false;
		}
	}

	public void MyFrame() {
		System.out.println("test");
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());
	}

	class ChargerMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new MenuSimulateur();
		}
	}

	public Labyrinthe getLaby() {
		return laby;
	}

	private void pause() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	class SelectedPersoListener extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			persoSelected = listPerso.getSelectedValue();
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
