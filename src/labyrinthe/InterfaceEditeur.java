package labyrinthe;

import gestionnaire.gui.GestionnaireUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import personnages.Personnage;

@SuppressWarnings("serial")
public class InterfaceEditeur extends JFrame {

	private JButton load = new JButton("Load");
	private JButton saveas = new JButton("Save as");
	private JPanel panel_save = new JPanel();
	private JPanel panel_size = new JPanel();
	static JPanel panel_labyrinthe = new JPanel();
	static JPanel content_pane = new JPanel();
	private JButton randomButton = new JButton(" Random ");
	private JPanel panel_random = new JPanel();
	private JComboBox<Integer> taille_x = new JComboBox<Integer>();
	private JComboBox<Integer> taille_y = new JComboBox<Integer>();
	private int tailleChoisieX = 1;
	private int tailleChoisieY = 1;
	GridBagConstraints gbc;
	static Salle[][] tab;
	ListeLabyrinthes listelabyrinthes;
	File fileSelected;
	ArrayList<Labyrinthe> labyrinthes;
	JTextField text_nom = new JTextField("Donnez un nom à votre labyrinthe");
	File file;
	ListeLabyrinthes listeLabyrinthes = new ListeLabyrinthes();
	Labyrinthe laby = new Labyrinthe(tab);
	ArrayList<Salle> listSalle = new ArrayList<Salle>();

	public InterfaceEditeur() {
		init();
		EtablirLabyrinthe(tailleChoisieX, tailleChoisieY);
		this.setVisible(true);

		// this.setResizable(false);
	}

	/**
	 * Initialise la Jframe, y ajoute ses boutons et ses Listeners.
	 */
	public void init() {

		this.setTitle("Editeur de Labyrinthe");
		this.setSize(900, 900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		content_pane = new JPanel();
		content_pane.setLayout(new BorderLayout());
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

		for (int i = 1; i < 10; i++) {
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

		this.setContentPane(content_pane); // CETTE CLASSE EST DENUEE DE
											// LISTENER POUR LE MOMENT

		taille_x.addItemListener(new TailleXListener());
		taille_y.addItemListener(new TailleYlistener());
		saveas.addActionListener(new SaveAsListener());
		load.addActionListener(new ChargerListener());
		randomButton.addActionListener(new RandomListener());

	}

	/**
	 * Permet de d'ajouter les cases de notre labyrinthe à la fenêtre contenant
	 * notre labyrinthe.
	 * 
	 * @param x
	 * @param y
	 */
	public void EtablirLabyrinthe(int x, int y) {

		panel_labyrinthe.setLayout(new GridBagLayout());
		panel_labyrinthe.setPreferredSize(new Dimension(300, 300));
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
				gbc.gridy = j;
				// panel_case.setBorder(door);
				Salle case_labyrinthe = new Salle(i, j, "normal", 0, 0, 0);
				tab[i][j] = case_labyrinthe;
				panel_labyrinthe.add(case_labyrinthe.panel_case, gbc);
			}
		}
		content_pane.add(panel_labyrinthe, BorderLayout.CENTER);

	}

	public void EtablirLabyrinthe(Labyrinthe laby) {

		panel_labyrinthe.setLayout(new GridBagLayout());
		panel_labyrinthe.setPreferredSize(new Dimension(300, 300));
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
				// panel_case.setBorder(door);
				// Salle case_labyrinthe = new Salle(i, j, "normal", 0, 0, 0);
				// tab[i][j] = case_labyrinthe;
				// panel_labyrinthe.add(case_labyrinthe.panel_case, gbc);
				panel_labyrinthe.add(laby.tab_cases[i][j].panel_case, gbc);
				System.out.print(laby.tab_cases[i][j].etat + " ");
				tab = laby.tab_cases;
			}
			System.out.println();
		}
		content_pane.add(panel_labyrinthe, BorderLayout.CENTER);
	}

	/**
	 * Renvoie le tableau contenant les cases.
	 * 
	 * @return Case[][]
	 */
	public Salle[][] getTabSalle() {
		return tab;
	}

	/**
	 * Ecoute le changement de valeur pour Y
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

	/**
	 * Ecoute le bouton Save afin de lancer la fenêtre de sauvegarde quand on
	 * clique sur le bouton.
	 */
	public class SaveAsListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane
					.showInputDialog("Donnez un nom à votre labyrinthe");
			Labyrinthe laby = new Labyrinthe(tab);
			laby.nom = name;

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

	class ChargerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser filechooser = new JFileChooser(".");
			if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					fileSelected = filechooser.getSelectedFile();
					BufferedReader br = new BufferedReader(new FileReader(
							fileSelected));
					String line;
					while ((line = br.readLine()) != null) {
						listSalle.add(laby.createSalleFromLine(line));
					}

					int x = listSalle.get(listSalle.size() - 1).GetX();
					int y = listSalle.get(listSalle.size() - 1).GetY();
					tab = new Salle[x][y];
					int cpt = 0;
					for (int i = 0; i < x; i++) {
						for (int j = 0; j < y; j++) {
							tab[i][j] = listSalle.get(cpt);
							cpt++;
							System.out.print(tab[i][j].etat + " ");
						}
						System.out.println();
					}
					laby = new Labyrinthe(tab);
					br.close();
				} catch (Exception ex) {
					Logger.getLogger(GestionnaireUI.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
			EtablirLabyrinthe(laby);
			panel_labyrinthe.revalidate();
			panel_labyrinthe.repaint();
			// initLabyrintheIntoList();
		}
	}

	// TODO : A compléter
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
		}
		laby = new Labyrinthe(tab);
		panel_labyrinthe.removeAll();
		EtablirLabyrinthe(laby);
		panel_labyrinthe.validate();
		panel_labyrinthe.repaint();
//		repaint();
//		getContentPane().revalidate();

	}

	public class RandomListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			initRandomLaby();
		}
	}
}