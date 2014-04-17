package simulateur;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class Objet extends JPanel {

	JPanel panel_objet = new JPanel();
	String objet = "empty";
	JLabel label = new JLabel();
	private JPopupMenu mouseMenu = new JPopupMenu();
	private JMenuItem utiliser = new JMenuItem("Utiliser");
	private JMenuItem deposer = new JMenuItem("Deposer");
	

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

		this.revalidate();
		this.repaint();
		// panel_case.validate();
		// panel_case.repaint();
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
}
