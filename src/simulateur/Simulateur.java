package simulateur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

import labyrinthe.InterfaceEditeur;
import labyrinthe.Labyrinthe;
import labyrinthe.ListeLabyrinthes;
import labyrinthe.Salle;
import personnages.Personnage;

@SuppressWarnings("serial")
public class Simulateur extends JFrame implements Runnable {

	static JPanel panel_labyrinthe = new JPanel();
	static JPanel content_pane = new JPanel();
	GridBagConstraints gbc;
	static Salle[][] tab;
	File fileSelected;
	ArrayList<Labyrinthe> labyrinthes;
	ListeLabyrinthes listeLabyrinthes = new ListeLabyrinthes();
	static Labyrinthe laby = new Labyrinthe(tab);
	ArrayList<Salle> listSalle = new ArrayList<Salle>();
	Salle[][] tabSalles;
	
	JPanel persoPanel = new JPanel();
	

	public Simulateur(Labyrinthe laby) {
		init();
		initMenu();
		InterfaceEditeur.EtablirLabyrinthe(laby);
		this.setVisible(true);
		
	}

	protected void init() {

		this.setTitle("Simulateur");
//		this.setSize(1400, 900);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		content_pane = new JPanel();
//		content_pane.setLayout(new BorderLayout());
		content_pane.setLayout(new BoxLayout(content_pane,BoxLayout.PAGE_AXIS));
		panel_labyrinthe.setMinimumSize(new Dimension(1400, 800));
		content_pane.add(panel_labyrinthe);
		
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());

		this.setContentPane(content_pane);
		persoPanel.setBorder(BorderFactory.createLineBorder(Color.red));

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
	protected void deplacementJoueur(Personnage p) {

	}

	protected void deplacementRandom(Personnage p) {
		String[] directionTab = { "north", "south", "west", "east", "stand" };
		int ranDirection = (int) (Math.random() * directionTab.length);
		String direction = directionTab[ranDirection];
		deplacement(p, direction);
	}

	protected Component deplacement(Personnage p, String s) {
		if (s.equalsIgnoreCase("north")) {
			InterfaceEditeur.tab[laby.getX()][laby.getY()].raz();
			return InterfaceEditeur.tab[laby.getX()][laby.getY() + 1].add(p
					.setImage());
		} else if (s.equalsIgnoreCase("south")) {
			InterfaceEditeur.tab[laby.getX()][laby.getY()].raz();
			return InterfaceEditeur.tab[laby.getX()][laby.getY() - 1].add(p
					.setImage());

		} else if (s.equalsIgnoreCase("west")) {
			InterfaceEditeur.tab[laby.getX()][laby.getY()].raz();
			return InterfaceEditeur.tab[laby.getX() - 1][laby.getY()].add(p
					.setImage());
		} else if (s.equalsIgnoreCase("east")) {
			InterfaceEditeur.tab[laby.getX()][laby.getY()].raz();
			return InterfaceEditeur.tab[laby.getX() + 1][laby.getY()].add(p
					.setImage());
		} else {
			return InterfaceEditeur.tab[laby.getX()][laby.getY()].add(p
					.setImage());
		}
	}

	private class MyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				System.out.println("touche pressée :" + e.getKeyChar());
			} else if (e.getID() == KeyEvent.KEY_RELEASED) {
			} else if (e.getID() == KeyEvent.KEY_TYPED) {}
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

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
