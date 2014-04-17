package simulateur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
	JPanel panel_game = new JPanel();
	JPanel content_pane = new JPanel();
	JPanel panelPerso = new JPanel();
	JPanel panelDroit = new JPanel();
	JPanel panelLog = new JPanel();
	JPanel panelButton = new JPanel();
	ArrayList<Personnage> personnage;
	JScrollPane scrollpaneLaby = new JScrollPane();
	InterfaceEditeur ie = new InterfaceEditeur();
	Personnage persoJoueur;
	Personnage perso;
	int indexTabI;
	int indexTabJ;

	static JList<Personnage> listPerso;
	DefaultListModel<Personnage> listeModelPerso;
	Personnage persoSelected;
	String race;

	public Simulateur(Labyrinthe laby, ArrayList<Personnage> persos) {
		this.laby = laby;
		this.personnage = persos;
		init();
		initMenu();
		ie.EtablirLabyrinthe(laby, panel_laby);
		this.setVisible(true);
		ie.setVisible(false);

	}

	public static JList<Personnage> getJlist() {
		return listPerso;
	}

	// ///////////////////////////////////////////////////// INIT
	// ///////////////////////////////////////////////////////

	protected void init() {

		this.setTitle("Simulateur");
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		listeModelPerso = new DefaultListModel<Personnage>();
		listPerso = new JList<Personnage>(listeModelPerso);
		JScrollPane scrollpanePerso = new JScrollPane(listPerso);
		// scrollpanePerso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
		// 10));
		initPersoIntoList();
		panelPerso.setLayout(new BoxLayout(panelPerso, BoxLayout.PAGE_AXIS));
		panelPerso.add(scrollpanePerso);
		content_pane = new JPanel();
		content_pane
				.setLayout(new BoxLayout(content_pane, BoxLayout.LINE_AXIS));
		int xSize = ((int) Toolkit.getDefaultToolkit().getScreenSize()
				.getWidth());
		int ySize = ((int) Toolkit.getDefaultToolkit().getScreenSize()
				.getHeight());
		int gameHeightLaby = (int) (Math.round(ySize * 1));
		int gameWidthLaby = (int) (Math.round(xSize * 0.75));
		panel_laby
				.setPreferredSize(new Dimension(gameWidthLaby, gameHeightLaby));
		panel_laby.setLayout(new BorderLayout());
		panel_game.setPreferredSize(new Dimension(ie.gx, ie.gy));

		int gameHeightPanelDroit = (int) (Math.round(ySize * 0.95));
		int gameWidthPanelDroit = (int) (Math.round(xSize * 0.25));
		int gameHeightPanelPerso = (int) (Math.round(ySize * 0.5));
		panelDroit.setLayout(new BoxLayout(panelDroit, BoxLayout.PAGE_AXIS));
		panelPerso.setPreferredSize(new Dimension(gameWidthPanelDroit,
				gameHeightPanelPerso));
		panelLog.setPreferredSize(new Dimension(gameWidthPanelDroit, 100));
		panelLog.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		initPanelSac();
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

		// InterfaceEditeur.panel_labyrinthe.setPreferredSize(new
		// Dimension(gbc.gridx * 98, gbc.gridy * 98));

		scrollpaneLaby = new JScrollPane(panel_game);
		scrollpaneLaby.setViewportView(panel_game);
		panel_laby.add(scrollpaneLaby);

		content_pane.add(panel_laby);
		content_pane.add(panelDroit);

		listPerso.addMouseListener(new SelectedPersoListener());
		startSimu.addActionListener(new StartSimulateurListener());

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

	private void initPanelSac() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		panelLog.setLayout(new GridBagLayout());
		gbc.gridy = 0;
		for (int i = 0; i < 2/* persoJoueur.getTailleSac() */; i++) {
			gbc.gridx = i;
			panelLog.add(new Objet("empty").panel_objet);
		}
		panelLog.revalidate();
		panelLog.repaint();
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

	// ///////////////////////////////////////////////////// METHODES
	// ///////////////////////////////////////////////////////

	/**
	 * Le personnage en param�tre se d�placera suivant les directions indiqu�es
	 * au clavier par le joueur
	 */
	protected void deplacementJoueur(Personnage p) {

	}

	/**
	 * Cette m�thode, en plus de v�rifier si un combat peut avoir lieu, g�re le
	 * d�roulement des combats si il y a entre 2 et 4 personnages en m�me temps.
	 */
	protected void organiserCombat(Salle s) {

		if (s.perso_dans_salle.size() == 2) {
			s.combat(s.perso_dans_salle.get(0), s.perso_dans_salle.get(1));
		}
		if (s.perso_dans_salle.size() == 3) {
			s.combat(
					s.perso_dans_salle.get(0),
					(s.combat(s.perso_dans_salle.get(0),
							s.perso_dans_salle.get(1))));
		}
		if (s.perso_dans_salle.size() == 4) {
			Personnage vainqueur1 = s.combat(s.perso_dans_salle.get(0),
					s.perso_dans_salle.get(1));
			Personnage vainqueur2 = s.combat(s.perso_dans_salle.get(2),
					s.perso_dans_salle.get(3));
			s.combat(vainqueur1, vainqueur2);
		} else {
			System.out.println("Un combat n'est pas possible ici !");
		}
	}

	private void pause() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void MyFrame() {
		System.out.println("test");
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());
	}

	@Override
	public void run() {
	}

	@SuppressWarnings("static-access")
	private void random() {
		// ThreadPerso th;
		for (int i = 0; i < Simulateur.getJlist().getModel().getSize(); i++) {
			indexTabI = (int) (Math.random() * ((ie.tab.length) - 0) + 0);
			indexTabJ = (int) (Math.random() * ((ie.tab.length) - 0) + 0);
			Salle salleRandom = InterfaceEditeur.tab[indexTabI][indexTabJ];
			if (salleRandom.verifierAccessible()
					&& !salleRandom.verifierSortie()
					&& salleRandom.perso_dans_salle.isEmpty()) {
				perso = Simulateur.getJlist().getModel().getElementAt(i);
				int j = i;
				while (perso.getPresent()) {
					j++;
					perso = Simulateur.getJlist().getModel().getElementAt(j);
				}
				salleRandom.entreePerso(perso);
				Runnable r = new ThreadPerso(perso, salleRandom);
				new Thread(r).start();
				// System.out.println("Etat thread" + Thread(r).getState());
			} else {
				i--;
			}
		}

	}

	// ////////////////////////////////////////////////// LISTENERS
	// ////////////////////////////////////////////////////////////////

	class ChargerMenuListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new MenuSimulateur();
		}
	}

	class SelectedPersoListener extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			persoSelected = listPerso.getSelectedValue();
		}
	}

	class StartSimulateurListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			random();

		}
	}

	// ////////////////////////////////////////////////// GETTERS
	// ////////////////////////////////////////////////////////////////
	// Ces m�thodes �tant explicites, elles ne seront pas comment�es
	// individuellement

	public Labyrinthe getLaby() {
		return laby;
	}

	// ////////////////////////////////////////////////// INNER CLASS
	// ////////////////////////////////////////////////////
	private class MyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				// deplacement(Simulateur.getJlist().getSelectedValue(),
				// Character.toString(e.getKeyChar()));
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

	/**
	 * Cette classe nous permet de passer un param�tre � un Thread. Ainsi,
	 * chaque personnage est suivi par son propre thread.
	 * 
	 */
	public class ThreadPerso implements Runnable {
		Personnage perso;
		Salle salle;

		public ThreadPerso(Personnage p, Salle s) {
			Personnage perso;
			Salle salle;
			this.perso = p;
			this.salle = s;
		}

		/**
		 * 
		 * Execute un d�placement du personnage en param�tre dans une direction
		 * al�atoire
		 */
		protected void deplacementRandom() {
			String[] directionTab = { "north", "south", "west", "east", "stand" };
			int ranDirection = (int) (Math.random() * directionTab.length);
			String direction = directionTab[ranDirection];
			deplacement(direction);
		}

		/**
		 * @param s
		 * 
		 *            M�thode qui d�place le personnage p dans la direction s
		 */
		public void deplacement(String s) {
			try {
				if (s.equalsIgnoreCase("north")
						&& InterfaceEditeur.tab[salle.GetX()][salle.GetY() + 1]
								.verifierAccessible()) {
					salle.raz();
					//salle.sortiePerso(perso);
					salle = InterfaceEditeur.tab[salle.GetX()][salle.GetY() + 1];
					salle.entreePerso(perso);
				} else if (s.equalsIgnoreCase("south" )&& InterfaceEditeur.tab[salle.GetX()][salle.GetY() - 1]
						.verifierAccessible()) {
					salle.raz();
					//salle.sortiePerso(perso);
					salle = InterfaceEditeur.tab[salle.GetX()][salle.GetY() - 1];
					salle.entreePerso(perso);
				} else if (s.equalsIgnoreCase("west")&& InterfaceEditeur.tab[salle.GetX()-1][salle.GetY()]
						.verifierAccessible()) {
					salle.raz();
					//salle.sortiePerso(perso);
					salle = InterfaceEditeur.tab[salle.GetX() - 1][salle.GetY()];
					salle.entreePerso(perso);
				} else if (s.equalsIgnoreCase("east")&& InterfaceEditeur.tab[salle.GetX()+1][salle.GetY() ]
						.verifierAccessible()) {
					salle.raz();
					//salle.sortiePerso(perso);
					salle = InterfaceEditeur.tab[salle.GetX() + 1][salle.GetY()];
					salle.entreePerso(perso);
				} else if (s.equalsIgnoreCase("stand")) {

				}
			} catch (IndexOutOfBoundsException e) {
			}
		}

		@Override
		public void run() {
			try {
				while (!salle.verifierSortie()) {
					Thread.sleep(100 * perso.getTempsMouvement());
					deplacementRandom();
				}
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
