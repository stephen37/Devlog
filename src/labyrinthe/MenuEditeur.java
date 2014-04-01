package labyrinthe;

import gestionnaire.gui.GestionnaireUI;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class MenuEditeur extends JFrame {
	JList<Labyrinthe> liste_labyrinthe;
	DefaultListModel<Labyrinthe> liste_model;
	JPanel content_pane;
	ListeLabyrinthes classListLabyrinthes;

	private JButton ajouter;
	private JButton supprimer;
	private JButton charger;
	File file;

	public MenuEditeur() {
		init();
		initListLabyrinthe();
		initLabyrintheIntoList();

		this.setVisible(true);
	}

	/**
	 * Initialise la Jframe, y ajoute ses boutons et ses Listeners.
	 */
	public void init() {
		classListLabyrinthes = new ListeLabyrinthes();
		this.setTitle("Editeur de Labyrinthe");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		content_pane = new JPanel();
		content_pane
				.setLayout(new BoxLayout(content_pane, BoxLayout.PAGE_AXIS));

		this.setContentPane(content_pane);

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

	/**
	 * Ajoute une Jlist dans notre JFrame, y ajoute ses boutons et ses
	 * Listeners.
	 */
	public void initListLabyrinthe() {
		liste_model = new DefaultListModel<Labyrinthe>();
		liste_labyrinthe = new JList<Labyrinthe>(liste_model);
		JScrollPane scroll_pane = new JScrollPane(liste_labyrinthe);
		scroll_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(scroll_pane);
		ajouter = new JButton("+");
		supprimer = new JButton("-");
		charger = new JButton("charger");

		JPanel buttonPane1 = new JPanel();
		buttonPane1.setLayout(new BoxLayout(buttonPane1, BoxLayout.LINE_AXIS));
		buttonPane1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPane1.add(ajouter);
		buttonPane1.add(Box.createRigidArea(new Dimension(10, 0)));
		buttonPane1.add(supprimer);

		JPanel buttonPane2 = new JPanel();
		buttonPane2.setLayout(new BoxLayout(buttonPane2, BoxLayout.LINE_AXIS));
		buttonPane2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPane2.add(Box.createHorizontalGlue());
		buttonPane2.add(charger);

		content_pane.add(buttonPane1);
		content_pane.add(buttonPane2);

		ajouter.addActionListener(new AjouterListener());
		charger.addActionListener(new ChargerListener());
	}

	class AjouterListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new InterfaceEditeur();

		}
	}

	/**
	 * Lance la JFileChooser afin de selectionner le fichier à charger
	 */
	class ChargerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser filechooser = new JFileChooser(".");
			// filechooser.setFileFilter(new FileNameExtensionFilter(
			// "Gestionnaire", ".*"));
			if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
//					InterfaceEditeur ie = new InterfaceEditeur();
					Labyrinthe laby = new Labyrinthe(InterfaceEditeur.tab);
					file = filechooser.getSelectedFile();
//					classListLabyrinthes.addToFile(laby, file);

				} catch (Exception ex) {
					Logger.getLogger(GestionnaireUI.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}
			repaint();
			initLabyrintheIntoList();
		}
	}

	/**
	 * Ajoute les Labyrinthes à la Jlist.
	 */
	public void initLabyrintheIntoList() {

	}
}