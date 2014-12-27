package simulateur;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import labyrinthe.InterfaceEditeur;
import labyrinthe.Salle;
/**
 * @author Loesch & Batifol 
 *
 */
@SuppressWarnings("serial")
public class Objet extends JPanel {

	JPanel panel_objet = new JPanel();
	String objet = "empty";
	JLabel label = new JLabel();
	private JPopupMenu mouseMenu = new JPopupMenu();
	private JMenuItem utiliser = new JMenuItem("Utiliser");
	private JMenuItem deposer = new JMenuItem("Deposer");
	int index = 0;
	Salle salle;

	public Objet(String objet) {
		this.objet = objet;
		init();
		this.setVisible(true);
	}

	// /////////////////////////////////////////////////////// INIT
	// /////////////////////////////////////////////////////////

	protected void init() {

		initMouseMenu();
		panel_objet.setPreferredSize(new Dimension(90, 90));
		// panel_case.setMinimumSize(new Dimension(90, 90));
		panel_objet.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		definirObjet(objet);
		initListeners();
	}

	protected void initListeners() {
		panel_objet.addMouseListener(new CaseListener());
	}

	protected void initMouseMenu() {
		mouseMenu.add(utiliser);
		mouseMenu.add(deposer);
		// deposer.addActionListener(new DeposerListener());

	}

	// /////////////////////////////////////////////////////// METHODES
	// /////////////////////////////////////////////////////////

	protected void definirObjet(String e) {
		this.objet = e;
		if (this.objet.equalsIgnoreCase("empty")) {
			label = new JLabel(new ImageIcon("./images/s-normal.png"));
			panel_objet.add(label);
		}
		if (this.objet.equalsIgnoreCase("key")) {
			label = new JLabel(new ImageIcon("./images/o-key.png"));
			panel_objet.add(label);
		}
		if (this.objet.equalsIgnoreCase("light")) {
			label = new JLabel(new ImageIcon("./images/o-light.png"));
			panel_objet.add(label);
		}
		if (this.objet.equalsIgnoreCase("potion")) {
			label = new JLabel(new ImageIcon("./images/o-potion.png"));
			panel_objet.add(label);
		}
		if (this.objet.equalsIgnoreCase("spyglass")) {
			label = new JLabel(new ImageIcon("./images/o-spyglass.png"));
			panel_objet.add(label);
		}
		if (this.objet.equalsIgnoreCase("jacket")) {
			label = new JLabel(new ImageIcon("./images/o-jacket.jpg"));
			panel_objet.add(label);
		}

		panel_objet.validate();
		panel_objet.repaint();
	}
	
	/**
	 * @param s
	 * 
	 * Méthode qui dépose l'objet. N'est pas complète. Son listener associé est donc mis en commentaire.
	 */
	public void deposerObjet(Salle s) {
		s.setObjet(this.objet);
		s.getPanel().validate();
		s.getPanel().repaint();
		InterfaceEditeur.tab[s.GetX()][s.GetY()] = s;
		definirObjet("empty");
		s.getPerso().setObjetdansSac(index, "empty");
	}

	// /////////////////////////////////////////////////////// GETTERS ET
	// SETTERS /////////////////////////////////////////////////////////

	public void setIndex(int i) {
		index = i;
	}

	// /////////////////////////////////////////////////////// LISTENERS
	// /////////////////////////////////////////////////////////

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

	class DeposerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			deposerObjet(salle);

		}

	}
}
