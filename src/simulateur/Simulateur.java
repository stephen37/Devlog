package simulateur;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
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
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;

import labyrinthe.InterfaceEditeur;
import labyrinthe.Labyrinthe;
import labyrinthe.ListeLabyrinthes;
import labyrinthe.Salle;
import personnages.Personnage;

/**
 * @author Loesch & Batifol 
 *
 */
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
	JPanel panelSac = new JPanel();
	JPanel panelButton = new JPanel();
	ArrayList<Personnage> personnage;
	JScrollPane scrollpaneLaby = new JScrollPane();
	InterfaceEditeur ie = new InterfaceEditeur();
	Personnage persoJoueur;
	Personnage perso;
	int indexTabI;
	int indexTabJ;
	Salle salleRandomJoueur;
	String dir = " ";
	String time;
	JLabel labelTime = new JLabel(time);
	JLabel labelNom = new JLabel();
	JTextArea textLog = new JTextArea();
	JScrollPane scrollTextLog;
	JPanel panelTemps = new JPanel();
	JPanel panelLog = new JPanel();
	JPanel panelLabyetLog = new JPanel();

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

		ie.setVisible(false);
		this.setVisible(true);

	}

	public static JList<Personnage> getJlist() {
		return listPerso;
	}

	// ///////////////////////////////////////////////////// INIT
	// ///////////////////////////////////////////////////////

	protected void init() {

		this.setTitle("Simulateur");
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		listeModelPerso = new DefaultListModel<Personnage>();
		listPerso = new JList<Personnage>(listeModelPerso);
		initJPanel();
		listPerso.addMouseListener(new SelectedPersoListener());
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());
		JButton startSimu = new JButton("Start");
		startSimu.addActionListener(new StartSimulateurListener());
		panelButton.add(startSimu);
		JButton definirJoueur = new JButton("Definir Joueur");
		definirJoueur.addActionListener(new DefinirPersoJoueur());
		panelButton.add(definirJoueur);
		JButton ramasser = new JButton("Ramasser objet");
		ramasser.addActionListener(new RamasserObjetListener());
		panelButton.add(ramasser);
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

	private void initJPanel() {
		JScrollPane scrollpanePerso = new JScrollPane(listPerso);
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
		panelSac.setPreferredSize(new Dimension(gameWidthPanelDroit, 100));
		panelSac.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		initPanelSac(2);
		panelDroit.setPreferredSize(new Dimension(gameWidthPanelDroit,
				gameHeightPanelDroit));
		panelDroit.add(panelPerso);
		panelDroit.add(panelSac);
		int gameHeightButton = (int) (Math.round(ySize * 0.05));
		panelButton.setPreferredSize(new Dimension(gameWidthPanelDroit,
				gameHeightButton));

		panelDroit.add(panelButton);
		scrollpaneLaby = new JScrollPane(panel_game);
		scrollpaneLaby.setViewportView(panel_game);
		panel_laby.add(scrollpaneLaby);
		panelLog.setBackground(Color.white);
		panelLog.setLayout(new BoxLayout(panelLog, BoxLayout.LINE_AXIS));
		panelLog.setPreferredSize(new Dimension(800, 200));
		panelLog.setMinimumSize(new Dimension(800, 200));
		panelTemps.setMaximumSize(new Dimension(50, 200));
		panelTemps.setPreferredSize(new Dimension(50, 200));

		panelLabyetLog.setLayout(new BoxLayout(panelLabyetLog,
				BoxLayout.PAGE_AXIS));

		panelTemps.setLayout(new BoxLayout(panelTemps, BoxLayout.PAGE_AXIS));
		labelNom.setText("Nom : " + laby.getName());
		panelTemps.add(labelTime);
		panelTemps.add(labelNom);
		scrollTextLog = new JScrollPane(textLog);
		textLog.setEditable(false);
		panelTemps.setPreferredSize(new Dimension(gameWidthPanelDroit,
				gameHeightButton));
		panelLog.add(scrollTextLog);
		panel_laby.add(scrollpaneLaby);

		panelLog.add(panelTemps);
		panelLabyetLog.add(panel_laby);
		panelLabyetLog.add(panelLog);
		content_pane.add(panelLabyetLog);
		content_pane.add(panelDroit);
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

	private void initPanelSac(int taille) {
		panelSac.removeAll();
		gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		panelSac.setLayout(new GridBagLayout());
		gbc.gridy = 0;
		for (int i = 0; i < taille; i++) {
			gbc.gridx = i;
			panelSac.add(new Objet("empty").panel_objet, gbc);
		}
		panelSac.revalidate();
		panelSac.repaint();
	}

	private void initPersoIntoList() {
		System.out.println(this.personnage);
		for (Personnage perso : this.personnage) {
			listeModelPerso.addElement(perso);
		}
	}

	// ///////////////////////////////////////////////////// METHODES
	// ///////////////////////////////////////////////////////

	/**
	 * Cette m�thode, en plus de v�rifier si un combat peut avoir lieu, g�re le
	 * d�roulement des combats si il y a entre 2 et 4 personnages en m�me temps.
	 */
	protected void organiserCombat(Salle s) {

		if (s.perso_dans_salle.size() == 2) {
			s.combat(s.perso_dans_salle.get(0), s.perso_dans_salle.get(1));
			textLog.append("Le personnage gagnant est "
					+ s.combat(s.perso_dans_salle.get(0),
							s.perso_dans_salle.get(1)).getNom());
		}
		if (s.perso_dans_salle.size() == 3) {
			s.combat(
					s.perso_dans_salle.get(0),
					(s.combat(s.perso_dans_salle.get(0),
							s.perso_dans_salle.get(1))));
			textLog.append("Le personnage gagnant est "
					+ s.combat(
							s.perso_dans_salle.get(0),
							(s.combat(s.perso_dans_salle.get(0),
									s.perso_dans_salle.get(1)))).getNom());

		}
		if (s.perso_dans_salle.size() == 4) {
			Personnage vainqueur1 = s.combat(s.perso_dans_salle.get(0),
					s.perso_dans_salle.get(1));
			Personnage vainqueur2 = s.combat(s.perso_dans_salle.get(2),
					s.perso_dans_salle.get(3));
			s.combat(vainqueur1, vainqueur2);
			textLog.append("Le personnage gagnant est "
					+ s.combat(vainqueur1, vainqueur2).getNom());
		} else {
			// System.out.println("Un combat n'est pas possible ici !");
		}
	}

	/**
	 * Le personnage en param�tre se d�placera suivant les directions indiqu�es
	 * au clavier par le joueur
	 */
	public void deplacement(String s) {
		try {
			if (s.equalsIgnoreCase("z")
					&& InterfaceEditeur.tab[salleRandomJoueur.GetX()][salleRandomJoueur
							.GetY() - 1].verifierAccessible()) {

				salleRandomJoueur.sortiePerso(persoJoueur);
				salleRandomJoueur = InterfaceEditeur.tab[salleRandomJoueur
						.GetX()][salleRandomJoueur.GetY() - 1];
				salleRandomJoueur.entreePerso(persoJoueur);
				textLog.append("le joueur " + perso.getNom() + " va en "
						+ salleRandomJoueur.GetX() + " "
						+ salleRandomJoueur.GetY() + "\n");
			} else if (s.equalsIgnoreCase("s")
					&& InterfaceEditeur.tab[salleRandomJoueur.GetX()][salleRandomJoueur
							.GetY() + 1].verifierAccessible()) {

				salleRandomJoueur.sortiePerso(persoJoueur);
				salleRandomJoueur = InterfaceEditeur.tab[salleRandomJoueur
						.GetX()][salleRandomJoueur.GetY() + 1];
				salleRandomJoueur.entreePerso(persoJoueur);
				textLog.append("le joueur " + perso.getNom() + " va en "
						+ salleRandomJoueur.GetX() + " "
						+ salleRandomJoueur.GetY() + "\n");
			} else if (s.equalsIgnoreCase("q")
					&& InterfaceEditeur.tab[salleRandomJoueur.GetX() - 1][salleRandomJoueur
							.GetY()].verifierAccessible()) {

				salleRandomJoueur.sortiePerso(persoJoueur);
				salleRandomJoueur = InterfaceEditeur.tab[salleRandomJoueur
						.GetX() - 1][salleRandomJoueur.GetY()];
				salleRandomJoueur.entreePerso(persoJoueur);
				textLog.append("le joueur " + perso.getNom() + " va en "
						+ salleRandomJoueur.GetX() + " "
						+ salleRandomJoueur.GetY() + "\n");
			} else if (s.equalsIgnoreCase("d")
					&& InterfaceEditeur.tab[salleRandomJoueur.GetX() + 1][salleRandomJoueur
							.GetY()].verifierAccessible()) {

				salleRandomJoueur.sortiePerso(persoJoueur);
				salleRandomJoueur = InterfaceEditeur.tab[salleRandomJoueur
						.GetX() + 1][salleRandomJoueur.GetY()];
				salleRandomJoueur.entreePerso(persoJoueur);
				textLog.append("le joueur " + perso.getNom() + " va en "
						+ salleRandomJoueur.GetX() + " "
						+ salleRandomJoueur.GetY() + "\n");
			} else if (s.equalsIgnoreCase(" ")) {

			}
		} catch (IndexOutOfBoundsException e) {

		}
	}

	/**
	 * Ajoute l'objet au sol � l'inventaire du personnage qui le ramasse
	 */
	public void ramasserObjet() {
		try {
			persoJoueur.setObjetdansSac(perso.emplacementLibreduSac(),
					salleRandomJoueur.getObjet());
			gbc.gridx = perso.emplacementLibreduSac();
			panelSac.remove(gbc.gridx);
			panelSac.add(new Objet(salleRandomJoueur.getObjet()).panel_objet);
			salleRandomJoueur.sortieObjet(salleRandomJoueur.getObjet());

		} catch (IndexOutOfBoundsException e) {
			System.out.println("Sac plein");
		}
	}

	public void verifierEquipement(Personnage p) {
		if (p.verifierObjet("jacket")) {
			p.setProtectionEau(1);
		}
		if (p.verifierObjet("light")) {
			p.setProtectionSombre(1);
		}
	}

	/**
	 * Utilis�e pour �couter le clavier
	 */
	public void MyFrame() {
		System.out.println("test");
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		try {
			while (!salleRandomJoueur.getEtat().equalsIgnoreCase("exit")) {
				Thread.sleep(100 * persoJoueur.getTempsMouvement());
				deplacement(dir);
				verifierEquipement(persoJoueur);
				persoJoueur.setTempsMouvement((11 - persoJoueur.getVitesse()
						/ getPenaliteEau(persoJoueur, salleRandomJoueur)
						/ getPenaliteSombre(persoJoueur, salleRandomJoueur)));
				organiserCombat(salleRandomJoueur);
				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				time = String.format(
						"%d min : %d sec",
						TimeUnit.MILLISECONDS.toMinutes(totalTime),
						TimeUnit.MILLISECONDS.toSeconds(totalTime)
								- TimeUnit.MINUTES
										.toSeconds(TimeUnit.MILLISECONDS
												.toMinutes(totalTime)));

				labelTime.setText("Dur�e : " + time);
				panelLog.add(panelTemps);
				panelLog.revalidate();
				panelLog.repaint();
			}
			textLog.append("Le joueur " + perso.getNom()
					+ " a atteint la sortie\n");
			try {
				PrintWriter pw = new PrintWriter("./log.txt");
				pw.println(textLog.getText());
				pw.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			salleRandomJoueur.sortiePerso(persoJoueur);
			System.out.println("Victoire ! " + persoJoueur.getNom()
					+ " est sorti vivant du labyrinthe");
			Thread.currentThread().interrupt();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Place les personnages non-control�s par le joueur dans le labyrinthe de
	 * mani�re al�atoire. Si un personnage a d�j� �t� plac� manuellement, il
	 * n'est pas plac� une seconde fois. Le thread de chaque personnage pr�sent
	 * est alors lanc�.
	 */
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
				while (perso.getPresent() && perso.equals(persoJoueur)) {
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

	@SuppressWarnings("static-access")
	private void randomJoueur() {
		Thread th;
		for (int i = 1; i < 2; i++) {
			indexTabI = (int) (Math.random() * ((ie.tab.length) - 0) + 0);
			indexTabJ = (int) (Math.random() * ((ie.tab.length) - 0) + 0);
			salleRandomJoueur = InterfaceEditeur.tab[indexTabI][indexTabJ];
			if (salleRandomJoueur.verifierAccessible()
					&& !salleRandomJoueur.verifierSortie()
					&& salleRandomJoueur.perso_dans_salle.isEmpty()
					&& !persoJoueur.getPresent()) {
				salleRandomJoueur.entreePerso(persoJoueur);
				th = new Thread(this);
				th.start();
				// System.out.println("Etat thread" + Thread(r).getState());
			} else if (persoJoueur.getPresent()) {
				th = new Thread(this);
				th.start();
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

	class RamasserObjetListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			ramasserObjet();
		}

	}

	class StartSimulateurListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (persoJoueur != null) {
				initPanelSac(persoJoueur.getTailleSac());
				randomJoueur();
				random();
			} else {
				JOptionPane.showMessageDialog(Simulateur.this,
						"S�lectionnez un h�ros avant de lancer le simulateur",
						"Attendez...", JOptionPane.ERROR_MESSAGE);
			}

		}
	}

	class DefinirPersoJoueur implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			persoJoueur = persoSelected;
			JOptionPane.showMessageDialog(Simulateur.this, persoJoueur.getNom()
					+ " est votre nouveau h�ros !", "Selection du personnage",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// ////////////////////////////////////////////////// GETTERS
	// ////////////////////////////////////////////////////////////////
	// Ces m�thodes �tant explicites, elles ne seront pas comment�es
	// individuellement

	public Labyrinthe getLaby() {
		return laby;
	}

	public int getPenaliteEau(Personnage p, Salle s) {
		if (s.getEtat().equalsIgnoreCase("submerged")
				&& p.getProtectionEau() == 0) {
			return 5;
		} else {
			return 1;
		}
	}

	public int getPenaliteSombre(Personnage p, Salle s) {
		if (s.getEtat().equalsIgnoreCase("dark")
				&& p.getProtectionSombre() == 0) {
			return 3;
		} else {
			return 1;
		}
	}

	public Salle getSallePerso() {
		return salleRandomJoueur;
	}

	// ////////////////////////////////////////////////// INNER CLASS
	// ////////////////////////////////////////////////////
	private class MyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			try {
				if (e.getID() == KeyEvent.KEY_PRESSED
						&& persoJoueur.getPresent()) {
					dir = Character.toString(e.getKeyChar());
					// deplacement(Character.toString(e.getKeyChar()),
					// salleRandomJoueur);

				} else if (e.getID() == KeyEvent.KEY_RELEASED) {
					dir = " ";
					deplacement(dir);
				} else if (e.getID() == KeyEvent.KEY_TYPED) {
				}

			} catch (NullPointerException er) {

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
			deplacement(perso, direction);
		}

		/**
		 * @param s
		 * 
		 *            M�thode qui d�place le personnage p dans la direction s
		 */
		public void deplacement(Personnage perso, String s) {
			try {
				if (s.equalsIgnoreCase("north")
						&& InterfaceEditeur.tab[salle.GetX()][salle.GetY() + 1]
								.verifierAccessible()) {

					salle.sortiePerso(perso);
					salle = InterfaceEditeur.tab[salle.GetX()][salle.GetY() + 1];
					salle.entreePerso(perso);
					// textLog.append("le joueur " + perso.getNom() + " va en "
					// + salle.GetX() + " " + salle.GetY() + "\n");
				} else if (s.equalsIgnoreCase("south")
						&& InterfaceEditeur.tab[salle.GetX()][salle.GetY() - 1]
								.verifierAccessible()) {

					salle.sortiePerso(perso);
					salle = InterfaceEditeur.tab[salle.GetX()][salle.GetY() - 1];
					salle.entreePerso(perso);
					// textLog.append("le joueur " + perso.getNom() + " va en "
					// + salle.GetX() + " " + salle.GetY() + "\n");
				} else if (s.equalsIgnoreCase("west")
						&& InterfaceEditeur.tab[salle.GetX() - 1][salle.GetY()]
								.verifierAccessible()) {

					salle.sortiePerso(perso);
					salle = InterfaceEditeur.tab[salle.GetX() - 1][salle.GetY()];
					salle.entreePerso(perso);
					// textLog.append("le joueur " + perso.getNom() + " va en "
					// + salle.GetX() + " " + salle.GetY() + "\n");
				} else if (s.equalsIgnoreCase("east")
						&& InterfaceEditeur.tab[salle.GetX() + 1][salle.GetY()]
								.verifierAccessible()) {

					salle.sortiePerso(perso);
					salle = InterfaceEditeur.tab[salle.GetX() + 1][salle.GetY()];
					salle.entreePerso(perso);
					// textLog.append("le joueur " + perso.getNom() + " va en "
					// + salle.GetX() + " " + salle.GetY() + "\n");
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
					verifierEquipement(perso);
					perso.setTempsMouvement((11 - perso.getVitesse()
							/ getPenaliteEau(perso, salle)
							/ getPenaliteSombre(perso, salle)));
					// organiserCombat(salle);
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			salle.sortiePerso(persoJoueur);
		}
	}

}
