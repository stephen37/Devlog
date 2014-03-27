package labyrinthe;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class Case extends JPanel {

	JPanel panel_case = new JPanel();
	String etat = "";
	protected int x;
	protected int y;
	int period = 0;
	int time = 0;
	int proba = 0;
	private JPopupMenu mouseMenu = new JPopupMenu();
	private JMenuItem sortie = new JMenuItem("Sortie");
	private JMenuItem bloquee = new JMenuItem("Salle Bloquée");
	private JMenuItem raz = new JMenuItem("Raz");

	public Case() {
		init();
		this.setVisible(true);
	}

	protected void init() {
		mouseMenu.add(sortie);
		mouseMenu.add(bloquee);
		mouseMenu.add(raz);
		panel_case.setPreferredSize(new Dimension(90, 90));
		panel_case.add(new JLabel(new ImageIcon("./images/s-normal.png")));

		panel_case.addMouseListener(new CaseListener());
		sortie.addActionListener(new SortieListener());
		bloquee.addActionListener(new BloqueListener());
		raz.addActionListener(new RazListener());
	}

	protected void definirBlock() {
		raz();
		panel_case.removeAll();
		FenetreBloquee fenetre = new FenetreBloquee();
		panel_case.add(new JLabel(new ImageIcon("./images/s-locked.png")));
		etat = "locked";
		panel_case.repaint();
	}

	protected void definirSortie() {
		raz();
		panel_case.removeAll();
		panel_case.add(new JLabel(new ImageIcon("./images/s-exit.png")));
		etat = "exit";
		panel_case.repaint();
	}

	protected void raz() {
		time = 0;
		period = 0;
		proba = 0;
		panel_case.removeAll();
		panel_case.add(new JLabel(new ImageIcon("./images/s-normal.png")));
		etat = "";
		panel_case.validate();
		panel_case.repaint();
	}

	protected int blockPeriod() {
		period = (int) FenetreBloquee.spinner_periode.getValue();
		return period;
	}

	protected int blockTime() {
		time = (int) FenetreBloquee.spinner_duree.getValue();
		return time;
	}

	protected int probBlock() {
		proba = (int) FenetreBloquee.slider_prob.getValue();
		return proba;

	}

	protected int GetX() {
		return x;
	}

	protected int GetY() {
		return y;
	}

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

	class SortieListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Clique sur sortie");
			definirSortie();
			panel_case.validate();
			panel_case.repaint();
		}
	}

	class BloqueListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Clique sur bloque");
			definirBlock();
			panel_case.validate();
			panel_case.repaint();
		}
	}

	class RazListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			raz();
			panel_case.repaint();
			System.out.println("Clique sur raz");
		}
	}
}