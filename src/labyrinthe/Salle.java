package labyrinthe;

import gestionnaire.gui.GestionnaireUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import personnages.Personnage;
import simulateur.Simulateur;

@SuppressWarnings("serial")
public class Salle extends JPanel implements Runnable {

	JPanel panel_case = new JPanel();
	String etat = "normal";
	String objet = "empty";
	protected int x;
	protected int y;
	double period = 0;
	double time = 0;
	int proba = 0;
	Personnage perso;
	public ArrayList<Personnage> perso_dans_salle = new ArrayList<Personnage>();
	boolean isaccessible;
	private JPopupMenu mouseMenu = new JPopupMenu();
	private JMenu mouseMenuObjets = new JMenu("Deposer un objet...");
	private JMenuItem sortie = new JMenuItem("Sortie");
	private JMenuItem bloquee = new JMenuItem("Salle Bloquee");
	private JMenuItem raz = new JMenuItem("Raz");
	private JMenuItem submerged = new JMenuItem("Submerger");
	private JMenuItem dark = new JMenuItem("Sombre");
	private JMenuItem ajouterPerso = new JMenuItem("Ajouter Personnage");
	private JMenuItem key = new JMenuItem("Cle");
	private JMenuItem light = new JMenuItem("Lampe");
	private JMenuItem potion = new JMenuItem("Potion");
	private JMenuItem spyglass = new JMenuItem("Telescope");
	File fileSelected;
	GestionnaireUI gui;
	Personnage selectedSerie;

	JLabel label = new JLabel();

	public Salle(int x, int y, String etat, double period, int proba,
			double time, String objet) {
		this.x = x;
		this.y = y;
		this.etat = etat;
		this.period = period;
		this.time = time;
		this.objet = objet;
		init();
		verifierAccessible();
		this.setVisible(true);
	}

	// ////////////////////////////////////////////////// INIT
	// ////////////////////////////////////////////////////

	/**
	 * Initialise la JPanel et ajoute une image à la case.
	 */
	protected void init() {
		mouseMenuObjets.add(key);
		mouseMenuObjets.add(light);
		mouseMenuObjets.add(potion);
		mouseMenuObjets.add(spyglass);
		mouseMenuObjets.setToolTipText("Objets !");
		mouseMenu.add(sortie);
		mouseMenu.add(bloquee);
		mouseMenu.add(submerged);
		mouseMenu.add(dark);
		mouseMenu.add(raz);
		mouseMenu.add(ajouterPerso);
		mouseMenu.add(mouseMenuObjets);

		panel_case.setPreferredSize(new Dimension(90, 90));
		panel_case.setMinimumSize(new Dimension(90, 90));

		definirEtat(etat);
		definirObjet(objet);
		verifierVide();
		// panel_case.repaint();
		// InterfaceEditeur.tab[this.x][this.y] = this;
		// panel_case.add(label);
		panel_case.addMouseListener(new CaseListener());
		sortie.addActionListener(new SortieListener());
		bloquee.addActionListener(new BloqueListener());
		submerged.addActionListener(new SubmergedListener());
		dark.addActionListener(new DarkListener());
		raz.addActionListener(new RazListener());
		ajouterPerso.addActionListener(new PersoListener());

		key.addActionListener(new ObjectKeyListener());
		light.addActionListener(new LightListener());
		potion.addActionListener(new PotionListener());
		spyglass.addActionListener(new SpyglassListener());
	}

	@Override
	public void run() {
		while (!verifierSortie()) {
			System.out.println("lancement run");
			entreePerso(perso);
		}
	}

	// ////////////////////////////////////////////////// METHODES
	// ////////////////////////////////////////////////////

	public boolean verifierAccessible() {
		if (this.etat.equalsIgnoreCase("locked")) {
			return isaccessible = false;
		} else {
			return isaccessible = true;
		}
	}

	public boolean verifierVide() {
		if (this.etat.equalsIgnoreCase("normal")
				&& this.objet.equalsIgnoreCase("empty")) {
			panel_case.add(new JLabel(new ImageIcon("./images/s-normal.png")));
			return true;
		}
		return false;
	}

	public boolean verifierSortie() {
		if (this.etat.equalsIgnoreCase("exit")) {
			return true;
		} else {
			return false;
		}
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

	protected void definirSubmerged() {
		raz();
		panel_case.removeAll();
		definirEtat("submerged");
		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	protected void definirDark() {
		raz();
		panel_case.removeAll();
		definirEtat("dark");
		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	protected void definirKey() {
		raz();
		panel_case.removeAll();
		definirObjet("key");
		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	protected void definirLight() {
		raz();
		panel_case.removeAll();
		definirObjet("light");
		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	protected void definirPotion() {
		raz();
		panel_case.removeAll();
		definirObjet("potion");
		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	protected void definirSpyglass() {
		raz();
		panel_case.removeAll();
		definirObjet("spyglass");
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
			break;
		}
		panel_case.validate();
		panel_case.repaint();
		InterfaceEditeur.tab[this.x][this.y] = this;
		System.out.println("X : " + InterfaceEditeur.tab[this.x][this.y].x
				+ " Y : " + InterfaceEditeur.tab[this.x][this.y].y);
	}

	/**
	 * Dessine des images en fonction de l'�tat de la case
	 * 
	 * @param e
	 */
	protected void definirEtat(String e) {
		this.etat = e;
		if (this.etat.equalsIgnoreCase("locked")) {
			label = new JLabel(new ImageIcon("./images/s-locked.png"));
			panel_case.add(label);
		}
		if (this.etat.equalsIgnoreCase("exit")) {
			label = new JLabel(new ImageIcon("./images/s-exit.png"));
			panel_case.add(label);
		}
		if (this.etat.equalsIgnoreCase("submerged")) {
			label = new JLabel(new ImageIcon("./images/s-submerged.png"));
			panel_case.add(label);
		}
		if (this.etat.equalsIgnoreCase("dark")) {
			label = new JLabel(new ImageIcon("./images/s-dark.png"));
			panel_case.add(label);
		}

		panel_case.validate();
		panel_case.repaint();
		// InterfaceEditeur.tab[this.x][this.y] = this;
	}

	protected void definirObjet(String e) {
		this.objet = e;
		// if (this.objet.equalsIgnoreCase("empty")) {
		// label = new JLabel(new ImageIcon("./images/s-normal.png"));
		// panel_case.add(label);
		// }
		if (this.objet.equalsIgnoreCase("key")) {
			label = new JLabel(new ImageIcon("./images/o-key.png"));
			panel_case.add(label);
		}
		if (this.objet.equalsIgnoreCase("light")) {
			label = new JLabel(new ImageIcon("./images/o-light.png"));
			panel_case.add(label);
		}
		if (this.objet.equalsIgnoreCase("potion")) {
			label = new JLabel(new ImageIcon("./images/o-potion.png"));
			panel_case.add(label);
		}
		if (this.objet.equalsIgnoreCase("spyglass")) {
			label = new JLabel(new ImageIcon("./images/o-spyglass.png"));
			panel_case.add(label);
		}

		panel_case.validate();
		panel_case.repaint();
		// InterfaceEditeur.tab[this.x][this.y] = this;
	}

	/**
	 * Remise à zero de la case.
	 */
	public void raz() {
		time = 0;
		period = 0;
		proba = 0;
		panel_case.removeAll();
		definirEtat("normal");
		// definirObjet("empty");
		InterfaceEditeur.tab[this.x][this.y] = this;
	}

	/**
	 * @param p
	 * 
	 *            G�re le comportement de la case quand un personnage y entre
	 */
	public void entreePerso(Personnage p) {
		if (this.isaccessible == true) {
			this.perso = p;
			definirPerso(perso.getRace());
			perso_dans_salle.add(p);
		} else {
			System.out.println("Salle Inaccessible");
		}
	}

	/**
	 * @param p
	 * 
	 *            G�re le comportement de la case quand un personnage la quitte
	 */
	public void sortiePerso(Personnage p) {
		if (this.perso != null) {
			this.perso = null;
			perso_dans_salle.remove(p);
			definirEtat(this.etat);
			definirObjet(this.objet);
			panel_case.repaint();
		} else {
			System.out.println("ERREUR, SORTIE DE PERSO SUR UNE CASE VIDE");
		}
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

	/**
	 * @param p1
	 * @param p2
	 * 
	 *            Cette m�thode execute un combat entre deux personnages dans la
	 *            salle
	 */
	public Personnage combat(Personnage p1, Personnage p2) {
		double Resultat;
		int attaque_chance_p1 = (int) (Math.random() * (5 - 0) + 0);
		int attaque_chance_p2 = (int) (Math.random() * (5 - 0) + 0);
		int defense_chance_p1 = (int) (Math.random() * (5 - 0) + 0);
		int defense_chance_p2 = (int) (Math.random() * (5 - 0) + 0);
		Resultat = ((p1.getAttaque() + attaque_chance_p1) - p2.getDefense() + defense_chance_p2)
				- ((p2.getAttaque() + attaque_chance_p2) - p1.getDefense() + defense_chance_p1);
		if (Resultat > 0) {
			p2.combatPerdu();
			System.out.println(p1.getNom() + " a remporte le combat !");
			return p1;
		}
		if (Resultat < 0) {
			p1.combatPerdu();
			System.out.println(p2.getNom() + " a remporte le combat !");
			return p2;
		} else {
			System.out
					.println(p1.getNom()
							+ " et "
							+ p2.getNom()
							+ " sont de meme niveau ! Incroyable, ils sont tous deux vainqueurs");
			return null;
		}
	}

	// ////////////////////////////////////////////////// GETTERS
	// ////////////////////////////////////////////////////////////////
	// Ces m�thodes �tant explicites, elles ne seront pas comment�es
	// individuellement

	public int GetX() {
		return x;
	}

	public int GetY() {
		return y;
	}

	public String getEtat() {
		return etat;
	}

	public String getObjet() {
		return objet;
	}

	public JPanel getPanel() {
		return panel_case;
	}

	// ////////////////////////////////////////////////// LISTENERS
	// ////////////////////////////////////////////////////////////////

	/**
	 * Ecoute la souris pour savoir si on clique sur une case.
	 */
	class CaseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(GetX());
			System.out.println(GetY());
			System.out.println(getObjet());
			System.out.println(getEtat());
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

	class SubmergedListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			definirSubmerged();
			panel_case.validate();
			panel_case.repaint();
		}
	}

	class DarkListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			definirDark();
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

	class ObjectKeyListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			definirKey();
			panel_case.validate();
			panel_case.repaint();
		}
	}

	class LightListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			definirLight();
			panel_case.validate();
			panel_case.repaint();
		}
	}

	class PotionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			definirPotion();
			panel_case.validate();
			panel_case.repaint();
		}
	}

	class SpyglassListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			definirSpyglass();
			panel_case.validate();
			panel_case.repaint();
		}
	}

	class PersoListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			panel_case.validate();
			panel_case.repaint();
			definirPerso(Simulateur.getJlist().getSelectedValue().getRace());
		}

	}

}