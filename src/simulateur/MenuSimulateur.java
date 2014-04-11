package simulateur;

import gestionnaire.Gestionnaire;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;

import labyrinthe.Labyrinthe;
import labyrinthe.ListeLabyrinthes;
import labyrinthe.MenuEditeur;
import labyrinthe.Salle;
import personnages.Personnage;

@SuppressWarnings("serial")
public class MenuSimulateur extends JDialog {

	/* Partie labyrinthe */
	JList<Labyrinthe> liste_labyrinthe;
	DefaultListModel<Labyrinthe> liste_model;
	JPanel panelLaby;
	ListeLabyrinthes classListLabyrinthes;
	JButton persoButton;

	/* Partie Personnages */
	Gestionnaire gestionnaire = new Gestionnaire();
	JList<Personnage> listPerso;
	DefaultListModel<Personnage> lmodel;
	JPanel panelPersos;
	JButton labyButton;
	ArrayList<Personnage> arraylistPersos = new ArrayList<Personnage>();

	JPanel container;
	JPanel panelJlist;
	JPanel panelButton;
	JPanel panelValider;
	JPanel panelChargerLaby;
	JPanel panelChargerPerso;
	
	Labyrinthe selectedLabyrinthe;
	

	JButton validerButton;
	ArrayList<Salle> listSalle = new ArrayList<Salle>();
	public static Salle[][] tab;
	Labyrinthe laby = new Labyrinthe(tab);
	static ArrayList<Labyrinthe> labyrinthes;
	File file;
	ListeLabyrinthes listeLabyrinthes = new ListeLabyrinthes();


	public MenuSimulateur() {
		init();
		initJpanel();
		initListener();
		this.setVisible(true);
	}

	public void init() {
		classListLabyrinthes = new ListeLabyrinthes();
		this.setTitle("Menu Simulateur");
		this.setSize(800, 500);
		this.setLocationRelativeTo(null);
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		this.setContentPane(container);

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

	private void initJpanel() {
		panelJlist = new JPanel();
		panelButton = new JPanel();
		panelValider = new JPanel();
		panelChargerLaby = new JPanel();
		panelChargerPerso = new JPanel();
		persoButton = new JButton(" Charger Personnages ");
		labyButton = new JButton(" Charger Labyrinthe ");
		validerButton = new JButton(" Valider ");

		panelButton.setMaximumSize(new Dimension(this.getWidth(), 100));
		panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.LINE_AXIS));
		panelChargerLaby.add(labyButton);
		panelChargerPerso.add(persoButton);
		panelButton.add(panelChargerLaby);
		panelButton.add(panelChargerPerso);

		panelJlist.setLayout(new BoxLayout(panelJlist, BoxLayout.LINE_AXIS));
		liste_model = new DefaultListModel<Labyrinthe>();
		liste_labyrinthe = new JList<Labyrinthe>(liste_model);
		JScrollPane scroll_paneLaby = new JScrollPane(liste_labyrinthe);
		scroll_paneLaby.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
				10));
		this.getContentPane().add(scroll_paneLaby);
		panelJlist.add(scroll_paneLaby);

		lmodel = new DefaultListModel<Personnage>();
		listPerso = new JList<Personnage>(lmodel);
		JScrollPane scrollpanePerso = new JScrollPane(listPerso);
		scrollpanePerso.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
				10));
		panelJlist.add(scrollpanePerso);

		panelValider
				.setLayout(new BoxLayout(panelValider, BoxLayout.LINE_AXIS));
		panelValider.add(validerButton);

		container.add(panelJlist);
		container.add(panelButton);
		container.add(panelValider);
	}

	private void initListener() {
		persoButton.addActionListener(new ChargerPersoListener());
		labyButton.addActionListener(new ChargerLabyListener());
		validerButton.addActionListener(new ValiderListener());
		liste_labyrinthe.addMouseListener(new listSeriesDoubleClickedAL());
	}

	class ChargerPersoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser filechooser = new JFileChooser(".");
			// filechooser.setFileFilter(new FileNameExtensionFilter(
			// "Gestionnaire", ".*"));
			if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					file = filechooser.getSelectedFile();
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line;
					while ((line = br.readLine()) != null) {
						arraylistPersos.add(gestionnaire.createPersonnageFromLine(line));
					}
					br.close();
				} catch (Exception ex) {
					Logger.getLogger(MenuEditeur.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
			initPersonnageIntoList();
			repaint();
		}
	}
	
	public void initPersonnageIntoList() {
		if (this.gestionnaire != null) {
			lmodel.clear();
			for (Personnage personnage : arraylistPersos) {
				lmodel.addElement(personnage); 
			}
		}
	}

	class ChargerLabyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser filechooser = new JFileChooser(".");
			// filechooser.setFileFilter(new FileNameExtensionFilter(
			// "Gestionnaire", ".*"));
			if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					file = filechooser.getSelectedFile();
					BufferedReader br = new BufferedReader(new FileReader(file));
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
							System.out.print(tab[i][j].getEtat() + " ");
						}
						System.out.println();
					}
					laby = new Labyrinthe(tab);
					br.close();
				} catch (Exception ex) {
					Logger.getLogger(MenuEditeur.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
			initLabyrintheIntoList(laby);
			repaint();
		}
	}

	class listSeriesDoubleClickedAL extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				selectedLabyrinthe = liste_labyrinthe.getSelectedValue();
			}
		}
	}

	/**
	 * Ajoute les Labyrinthes à la Jlist.
	 */
	public void initLabyrintheIntoList(Labyrinthe laby) {
		if (laby != null) {
			liste_model.clear();
			liste_model.addElement(laby);

		}
	}
	
	class ValiderListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			if(selectedLabyrinthe != null) {
				new Simulateur(selectedLabyrinthe);
			}
		}
	}
	public static void main(String[] args) {
		new MenuSimulateur();
	}
}