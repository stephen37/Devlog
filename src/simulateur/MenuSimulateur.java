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
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
	DefaultListModel<Labyrinthe> listeModelLaby;
	JPanel panelLaby;
	ListeLabyrinthes classListLabyrinthes;
	JButton persoButton;

	/* Partie Personnages */
	Gestionnaire gestionnaire = new Gestionnaire();
	JList<Personnage> listPerso;
	DefaultListModel<Personnage> listeModelPerso;
	JPanel panelPersos;
	JButton labyButton;
	ArrayList<Personnage> arraylistPersos = new ArrayList<Personnage>();
	ArrayList<Personnage> listPersoSelected = new ArrayList<Personnage>();

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
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
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
		listeModelLaby = new DefaultListModel<Labyrinthe>();
		liste_labyrinthe = new JList<Labyrinthe>(listeModelLaby);
		JScrollPane scroll_paneLaby = new JScrollPane(liste_labyrinthe);
		scroll_paneLaby.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
				10));
		this.getContentPane().add(scroll_paneLaby);
		panelJlist.add(scroll_paneLaby);

		listeModelPerso = new DefaultListModel<Personnage>();
		listPerso = new JList<Personnage>(listeModelPerso);
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
		liste_labyrinthe.addMouseListener(new LabyrintheListener());
		listPerso.addMouseListener(new PersoListener());
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
						arraylistPersos.add(gestionnaire
								.createPersonnageFromLine(line));
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
		if (this.arraylistPersos != null) {
			listeModelPerso.clear();
			for (Personnage personnage : arraylistPersos) {
				listeModelPerso.addElement(personnage);
			}
			System.out.println(listPersoSelected);
		}
	}

	class ChargerLabyListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			laby.charger();
			initLabyrintheIntoList(laby);
			repaint();
		}
	}

	class LabyrintheListener extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			selectedLabyrinthe = liste_labyrinthe.getSelectedValue();
		}
	}

	class PersoListener extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			listPersoSelected.add(listPerso.getSelectedValue());
			System.out.println(listPerso.getSelectedValue());
		}
	} 
	 /*class PersoListener extends MouseAdapter {
		 public void mouseClicked(MouseEvent e) {
			 listPersoSelected.add(listPerso.getSelectedValue());
			 int index = listPerso.locationToIndex(e.getPoint());
             System.out.println("clicked on Item " + index);  
		 }
	 }*/
	

	/**
	 * Ajoute les Labyrinthes à la Jlist.
	 */
	public void initLabyrintheIntoList(Labyrinthe laby) {
		if (laby != null) {
			listeModelLaby.clear();
			listeModelLaby.addElement(laby);
		}
	}

	class ValiderListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			if (selectedLabyrinthe != null) {
				new Simulateur(selectedLabyrinthe, listPersoSelected);
			} else {
				JOptionPane.showMessageDialog(MenuSimulateur.this,
						"veuillez selectionner un labyrinthe valide", "Erreur",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public static void main(String[] args) {
		new MenuSimulateur();
	}
}