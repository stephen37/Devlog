package labyrinthe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class InterfaceEditeur extends JFrame {

	static JFrame mainframe;
	private JButton load = new JButton("Load");
	private JButton saveas = new JButton("Save as");
	private JPanel panel_save = new JPanel();
	private JPanel panel_size = new JPanel();
	public static JPanel panel_labyrinthe = new JPanel();
	static JScrollPane scrollPane = new JScrollPane();
	static JPanel content_pane = new JPanel();
	private JButton randomButton = new JButton(" Random ");
	private JPanel panel_random = new JPanel();
	private JComboBox<Integer> taille_x = new JComboBox<Integer>();
	private JComboBox<Integer> taille_y = new JComboBox<Integer>();
	private int tailleChoisieX = 1;
	private int tailleChoisieY = 1;
	public int gx;
	public int gy;
	GridBagConstraints gbc;
	public static Salle[][] tab;
	File fileSelected;
	static ArrayList<Labyrinthe> labyrinthes;
	JTextField text_nom = new JTextField("Donnez un nom Ã  votre labyrinthe");
	File file;
	ListeLabyrinthes listeLabyrinthes = new ListeLabyrinthes();
	Labyrinthe laby = new Labyrinthe(tab);
	static int xSize = ((int) Toolkit.getDefaultToolkit().getScreenSize()
			.getWidth());
	static int ySize = ((int) Toolkit.getDefaultToolkit().getScreenSize()
			.getHeight());
	static int gameHeightLaby = (int) (Math.round(ySize * 1));
	static int gameWidthLaby = (int) (Math.round(xSize * 0.85));
	static boolean sortie_presente = false;

	public InterfaceEditeur() {
		init();
		EtablirLabyrinthe(tailleChoisieX, tailleChoisieY);
		this.setVisible(true);

		// this.setResizable(false);
	}

	// ////////////////////////////////////////////////////// INIT
	// ////////////////////////////////////////////////////////

	/**
	 * Initialise la Jframe, y ajoute ses boutons et ses Listeners.
	 */
	public void init() {

		this.setTitle("Editeur de Labyrinthe");
		this.setSize(new Dimension(900, 900));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		content_pane = new JPanel();
		content_pane.setPreferredSize(new Dimension(900, 900));
		content_pane.setLayout(new BorderLayout());
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

		for (int i = 1; i < 30; i++) {
			taille_x.addItem(new Integer(i));
			taille_y.addItem(new Integer(i));
		}

		JPanel bas_fenetre = new JPanel();
		bas_fenetre.setLayout(new BorderLayout());
		panel_size.setLayout(new BoxLayout(panel_size, BoxLayout.X_AXIS));
		panel_size.add(new JLabel("Taille: "));
		panel_size.add(taille_x);
		panel_size.add(new JLabel(" x "));
		panel_size.add(taille_y);
		panel_size.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel_save.setLayout(new BoxLayout(panel_save, BoxLayout.X_AXIS));
		panel_save.add(load);
		panel_save.add(saveas);
		panel_save.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel_random.add(randomButton);
		bas_fenetre.add(panel_save, BorderLayout.EAST);
		bas_fenetre.add(panel_size, BorderLayout.WEST);
		content_pane.add(top, BorderLayout.WEST);
		bas_fenetre.add(panel_random, BorderLayout.CENTER);
		content_pane.add(bas_fenetre, BorderLayout.PAGE_END);

		this.setContentPane(content_pane);

		taille_x.addItemListener(new TailleXListener());
		taille_y.addItemListener(new TailleYlistener());
		saveas.addActionListener(new SaveAsListener());
		load.addActionListener(new ChargerListener());
		randomButton.addActionListener(new RandomListener());

		// ////////////////////////////////////////////////////// METHODES
		// ////////////////////////////////////////////////////////

	}

	/**
	 * Dessine un labyrinthe vierge de la taille indiquée
	 * 
	 * @param x
	 * @param y
	 */
	public void EtablirLabyrinthe(int x, int y) {

		panel_labyrinthe.setLayout(new GridBagLayout());

		// Border door = BorderFactory.createDashedBorder(Color.black, 5, 10,
		// 20, true);

		panel_labyrinthe.removeAll();
		panel_labyrinthe.repaint();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		tab = new Salle[x][y];
		labyrinthes = new ArrayList<Labyrinthe>();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				gbc.gridx = i;
				gx = i;
				gbc.gridy = j;
				gy = j;
				// panel_case.setBorder(door);
				Salle case_labyrinthe = new Salle(i, j, "normal", 0, 0, 0,
						"empty");
				tab[i][j] = case_labyrinthe;
				panel_labyrinthe.add(case_labyrinthe.panel_case, gbc);
			}
		}
		// scrollPane = new JScrollPane();

		scrollPane.setViewportView(panel_labyrinthe);
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		// scrollPane.setBounds(50, 50, 300, 500);
		panel_labyrinthe.setPreferredSize(new Dimension(gbc.gridx * 98,
				gbc.gridy * 98));
		content_pane.add(scrollPane, BorderLayout.CENTER);

	}

	/**
	 * @param laby
	 * @param content_pane
	 * 
	 *            Dessine le labyrinthe selon celui indiqué dans le panel
	 *            indiqué
	 */
	public void EtablirLabyrinthe(Labyrinthe laby, JPanel content_pane) {

		// JPanel panel_labyrinthe = new JPanel();
		panel_labyrinthe.setLayout(new GridBagLayout());
		panel_labyrinthe.setPreferredSize(new Dimension(gameWidthLaby,
				gameHeightLaby));
		// panel_labyrinthe.setPreferredSize(new Dimension(300, 300));
		// Border door = BorderFactory.createDashedBorder(Color.black, 5, 10,
		// 20, true);
		panel_labyrinthe.removeAll();
		panel_labyrinthe.repaint();

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		labyrinthes = new ArrayList<Labyrinthe>();
		for (int i = 0; i < laby.tab_cases.length; i++) {
			for (int j = 0; j < laby.tab_cases[i].length; j++) {
				gbc.gridx = i;
				gbc.gridy = j;
				gx = i * 98;
				gy = j * 98;
				// panel_case.setBorder(door);
				// Salle case_labyrinthe = new Salle(i, j, "normal", 0, 0, 0);
				// tab[i][j] = case_labyrinthe;
				// panel_labyrinthe.add(case_labyrinthe.panel_case, gbc);
				// System.out.println("taille y" + laby.tab_cases[i].length);
				// System.out.println("taille x" + laby.tab_cases.length);
				panel_labyrinthe.add(laby.tab_cases[i][j].panel_case, gbc);
				if (laby.tab_cases[i][j].getEtat().equalsIgnoreCase("exit")) {
					sortie_presente = true;
				}
				System.out.print(laby.tab_cases[i][j].objet + " ");

			}
			System.out.println();
		}
		tab = laby.tab_cases;
		scrollPane.setViewportView(panel_labyrinthe);
		// scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel_labyrinthe.setPreferredSize(new Dimension(gbc.gridx * 98,
				gbc.gridy * 98));
		content_pane.add(scrollPane, BorderLayout.CENTER);
		// content_pane.setPreferredSize(new Dimension(gbc.gridx * 98, gbc.gridy
		// * 98));
	}

	// TODO : A complÃ©ter
	public void initRandomLaby() { // A COMPLETER
		String[] etatTab = { "normal", "locked", "exit" };
		int ranX = (int) (Math.random() * (9 - 1) + 1);
		int ranY = (int) (Math.random() * (9 - 1) + 1);
		taille_x.setSelectedItem(ranX);
		taille_y.setSelectedIndex(ranY);

		for (int i = 0; i < (Math.random() * (ranX * ranY - 1) + 1); i++) {

			int ran_caseX = (int) (Math.random() * (ranX));
			int ran_caseY = (int) (Math.random() * (ranY));
			int ranEtat = (int) (Math.random() * etatTab.length);
			String etat = etatTab[ranEtat];
			tab[ran_caseX][ran_caseY].definirEtat(etat);
			;
		}
		laby = new Labyrinthe(tab);
		panel_labyrinthe.removeAll();
		EtablirLabyrinthe(laby, content_pane);
		panel_labyrinthe.revalidate();
		panel_labyrinthe.repaint();

	}

	// ////////////////////////////////////////////////////// LISTENERS
	// ////////////////////////////////////////////////////////

	public class RandomListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			initRandomLaby();
		}
	}

	class ChargerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			laby.charger();
			laby = new Labyrinthe(laby.tab_cases);
			EtablirLabyrinthe(laby, content_pane);
			panel_labyrinthe.revalidate();
			panel_labyrinthe.repaint();
			// initLabyrintheIntoList();
			// MenuEditeur me = new MenuEditeur();
			// me.initLabyrintheIntoList(labyrinthes);
		}
	}

	/**
	 * Ecoute le bouton Save afin de lancer la fenÃªtre de sauvegarde quand on
	 * clique sur le bouton.
	 */
	public class SaveAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// String name = JOptionPane
			// .showInputDialog("Donnez un nom Ã  votre labyrinthe");
			Labyrinthe laby = new Labyrinthe(tab);
			// laby.nom = name;

			if (this != null) {
				JFileChooser filechooser = new JFileChooser(".") {
					public void approveSelection() {
						fileSelected = getSelectedFile();
						if (fileSelected.exists()) {
							int result = JOptionPane.showConfirmDialog(this,
									"Ecraser le fichier?", "Confirmation",
									JOptionPane.YES_NO_CANCEL_OPTION);
							switch (result) {
							case JOptionPane.YES_OPTION:
								super.approveSelection();
								return;
							default:
								super.cancelSelection();
							}
						} else
							super.approveSelection();
					}
				};
				if (filechooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					for (int i = 0; i < tab.length; i++) {
						for (int j = 0; j < tab[i].length; j++) {
							System.out.println("Valeur du tableau "
									+ tab[i][j].etat);
						}
					}
				}
			} else {
				JOptionPane
						.showMessageDialog(
								InterfaceEditeur.this,
								"veuillez creer un nouveau labyrinthe ou charger un labyrinthe existant",
								"Erreur", JOptionPane.ERROR_MESSAGE);
			}

			// dispose();

			try {
				laby.addToFile(laby, fileSelected);
				// listelabyrinthes.addToFile(listelabyrinthes.getLabyrinthes(),
				// fileSelected);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			getContentPane().revalidate();
		}
	}

	/**
	 * Ecoute le changement de valeur pour X
	 */
	class TailleXListener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			getContentPane().revalidate();
			if (e.getStateChange() == 1) {
				tailleChoisieX = (int) e.getItem();
				EtablirLabyrinthe(tailleChoisieX, tailleChoisieY);
			}
		}
	}

	/**
	 * Ecoute le changement de valeur pour Y
	 */
	class TailleYlistener implements ItemListener {
		@Override
		public void itemStateChanged(ItemEvent e) {
			getContentPane().revalidate();
			if (e.getStateChange() == 1) {
				tailleChoisieY = (int) e.getItem();
				EtablirLabyrinthe(tailleChoisieX, tailleChoisieY);
			}
		}
	}

	// ////////////////////////////////////////////////////// GETTERS et SETTERS
	// ////////////////////////////////////////////////////////
	// Ces méthodes étant explicites, elles ne seront pas commentées
	// individuellement

	public static boolean getSortiePresente() {
		return sortie_presente;
	}

	public static boolean setSortiePresente(boolean b) {
		return sortie_presente = b;
	}

	/**
	 * Renvoie le tableau contenant les cases.
	 * 
	 * @return Case[][]
	 */
	public static Salle[][] getTabSalle() {
		return tab;
	}

	public int getGridx() {
		return gbc.gridx;
	}

	public int getGridy() {
		return gbc.gridy;
	}

	public int getX() {
		return tailleChoisieX;
	}

	public int getY() {
		return tailleChoisieY;
	}
}