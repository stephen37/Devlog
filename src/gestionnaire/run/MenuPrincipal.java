package gestionnaire.run;

import gestionnaire.gui.GestionnaireUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import labyrinthe.MenuEditeur;
import simulateur.MenuSimulateur;

/**
 * @author Loesch & Batifol 
 *
 */
@SuppressWarnings("serial")
public class MenuPrincipal extends JFrame {

	JPanel content_pane;

	private JButton atelier = new JButton("Creer de nouveaux personnages");
	private JButton simulateur = new JButton("Acceder au simulateur");
	private JButton editeur = new JButton("Creer et editer des labyrinthes");
	private JPanel panel_atelier = new JPanel();
	private JPanel panel_simulateur = new JPanel();
	private JPanel panel_editeur = new JPanel();
	private JPanel panel_box = new JPanel();

	public MenuPrincipal() {
		init();

		this.setVisible(true);
	}

	public void init() {
		this.setTitle("Menu Principal");
		this.setSize(600, 450);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		content_pane = new JPanel();
		content_pane.setLayout(new BorderLayout());
		panel_box.setLayout(new BoxLayout(panel_box, BoxLayout.Y_AXIS));

		atelier.setPreferredSize(new Dimension(250, 80));
		panel_atelier
				.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel_atelier.add(atelier);
		editeur.setPreferredSize(new Dimension(250, 80));
		panel_editeur
				.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel_editeur.add(editeur);
		simulateur.setPreferredSize(new Dimension(250, 80));
		panel_simulateur.setBorder(BorderFactory.createEmptyBorder(10, 10, 10,
				10));
		panel_simulateur.add(simulateur);

		panel_box.add(panel_atelier);
		panel_box.add(panel_editeur);
		panel_box.add(panel_simulateur);
		content_pane.add(panel_box);

		atelier.addActionListener(new AtelierListener());
		editeur.addActionListener(new EditeurListener());
		simulateur.addActionListener(new SimulateurListener());

		this.setContentPane(content_pane);

	}

	class AtelierListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new GestionnaireUI();

		}
	}

	class EditeurListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new MenuEditeur();

		}
	}

	class SimulateurListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new MenuSimulateur();

		}
	}

	public static void main(String[] args) {
		new MenuPrincipal();
	}
}