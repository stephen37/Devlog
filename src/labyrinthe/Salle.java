package labyrinthe;

import gestionnaire.Gestionnaire;
import gestionnaire.gui.AddPersonnageDialogUI;
import gestionnaire.gui.GestionnaireUI;
import gestionnaire.run.EntreesSorties;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.MouseInputAdapter;

import personnages.Personnage;

@SuppressWarnings("serial")
public class Salle extends JPanel {

	JPanel panel_case = new JPanel();
	String etat = "normal";
	protected int x;
	protected int y;
	double period = 0;
	double time = 0;
	int proba = 0;
	private JPopupMenu mouseMenu = new JPopupMenu();
	private JMenuItem sortie = new JMenuItem("Sortie");
	private JMenuItem bloquee = new JMenuItem("Salle Bloquée");
	private JMenuItem raz = new JMenuItem("Raz");
	private JMenuItem ajouterPerso = new JMenuItem("Ajouter Personnage");
	private Gestionnaire gestionnaire = new Gestionnaire();
	File fileSelected;
	GestionnaireUI gui;
	Personnage selectedSerie;

	JLabel label = new JLabel();

	public Salle(int x, int y, String etat, double period, int proba,
			double time) {
		this.x = x;
		this.y = y;
		this.etat = etat;
		this.period = period;
		this.time = time;
		init();
		// definirEtat(etat);
		this.setVisible(true);
	}

	/**
	 * Initialise la JPanel et ajoute une image à la case.
	 */
	protected void init() {
		mouseMenu.add(sortie);
		mouseMenu.add(bloquee);
		mouseMenu.add(raz);
		mouseMenu.add(ajouterPerso);

		panel_case.setPreferredSize(new Dimension(90, 90));
		panel_case.setMinimumSize(new Dimension(90, 90));

		definirEtat(etat);
		panel_case.add(label);
		panel_case.addMouseListener(new CaseListener());
		sortie.addActionListener(new SortieListener());
		bloquee.addActionListener(new BloqueListener());
		raz.addActionListener(new RazListener());
		ajouterPerso.addActionListener(new PersoListener());
	}

	/**
	 * Lance la fenêtre permettant de bloquer une case.
	 */
	protected void definirBlock() {
		raz();
		panel_case.removeAll();
		new FenetreBloquee(this);
		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	/**
	 * Permet de définir un point de sortie sur une case.
	 */
	protected void definirSortie() {
		raz();
		panel_case.removeAll();
		definirEtat("exit");
		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	protected void definirPerso(String race) {
		panel_case.removeAll();
		switch (race) {
		case "Elf":
			panel_case.add(new JLabel(new ImageIcon("./images/p-elf.png")));
			break;
		case "Ogre":
			panel_case.add(new JLabel(new ImageIcon("./images/p-ogre.png")));
			break;
		case "Humain":
			panel_case.add(new JLabel(new ImageIcon("./images/p-human.png")));
			break;
		default:
			panel_case.add(new JLabel(new ImageIcon("./images/s-normal.png")));
			break;
		}
		panel_case.validate();
		panel_case.repaint();
		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	/**
	 * Dessine des images en fonction de l'état de la case
	 * 
	 * @param e
	 */
	protected void definirEtat(String e) {
		this.etat = e;
		if (this.etat.equalsIgnoreCase("normal")) {
			panel_case.add(new JLabel(new ImageIcon("./images/s-normal.png")));
		}
		if (this.etat.equalsIgnoreCase("locked")) {
			label = new JLabel(new ImageIcon("./images/s-locked.png"));
			panel_case.add(label);
		}
		if (this.etat.equalsIgnoreCase("exit")) {
			label = new JLabel(new ImageIcon("./images/s-exit.png"));
			panel_case.add(label);
		}
		panel_case.validate();
		panel_case.repaint();
//		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	/**
	 * Remise à zéro de la case.
	 */
	public void raz() {
		time = 0;
		period = 0;
		proba = 0;
		panel_case.removeAll();
		definirEtat("normal");
		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	/**
	 * Obtient la periode selectionnée dans la fenêtre FenetreBloquee
	 * 
	 * @return double
	 */
	protected double blockPeriod() {
		period = (double) FenetreBloquee.spinner_periode.getValue();
		return period;
	}

	/**
	 * Obtient le temps selectionné dans la fenêtre FenetreBloquee
	 * 
	 * @return double
	 */
	protected double blockTime() {
		time = (double) FenetreBloquee.spinner_duree.getValue();
		return time;
	}

	/**
	 * Obtient la proba selectionnée dans la fenêtre FenetreBloquee
	 * 
	 * @return int
	 */
	protected int blockProb() {
		proba = (int) FenetreBloquee.slider_prob.getValue();
		return proba;
	}

	public int GetX() {
		return x;
	}

	public int GetY() {
		return y;
	}

	public String getEtat() {
		return etat;
	}

	public JPanel getPanel() {
		return panel_case;
	}

	/**
	 * Ecoute la souris pour savoir si on clique sur une case.
	 */
	class CaseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			mouseMenu.show(e.getComponent(), e.getX(), e.getY());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	/**
	 * Ecoute la souris afin de savoir si le JMenuItem "Sortie" a été
	 * selectionné
	 */
	class SortieListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			definirSortie();
			panel_case.validate();
			panel_case.repaint();
		}
	}

	/**
	 * Ecoute la souris afin de savoir si le JMenuItem "Bloquer" a été
	 * selectionné
	 */
	class BloqueListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			definirBlock();
			panel_case.validate();
			panel_case.repaint();
		}
	}

	/**
	 * Ecoute la souris afin de savoir si le JMenuItem "Bloquer" a été
	 * selectionné
	 */
	class RazListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			raz();
			panel_case.repaint();
		}
	}

	class PersoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser filechooser = new JFileChooser(".");
			if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					fileSelected = filechooser.getSelectedFile();
					gestionnaire.chargerPersonnage(fileSelected.getPath());
					gui = new GestionnaireUI();

					gui.initPersonnageIntoList(gestionnaire.getPersonnages());

				} catch (Exception ex) {
					Logger.getLogger(GestionnaireUI.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
			gui.getJList().addMouseListener(new listSeriesDoubleClickedAL());
			panel_case.validate();
			panel_case.repaint();
		}

	}

	class listSeriesDoubleClickedAL extends MouseInputAdapter {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				selectedSerie = gui.getJList().getSelectedValue();
				if (selectedSerie != null) {
					definirPerso(selectedSerie.getRace());
				}
			}
		}
	}
}