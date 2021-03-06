package labyrinthe;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;
/**
 * @author Loesch & Batifol 
 *
 */
@SuppressWarnings("serial")
public class MenuEditeur extends JFrame {
	JList<Labyrinthe> liste_labyrinthe;
	DefaultListModel<Labyrinthe> liste_model;
	ListeLabyrinthes classListLabyrinthes;

	private JButton ajouter;

	JPanel content_pane = new JPanel();
	static Salle[][] tab;
	static ArrayList<Labyrinthe> labyrinthes;
	File file;
	ListeLabyrinthes listeLabyrinthes = new ListeLabyrinthes();
	Labyrinthe laby = new Labyrinthe(tab);
	ArrayList<Salle> listSalle = new ArrayList<Salle>();

	public MenuEditeur() {
		init();
		initListLabyrinthe();
		this.setVisible(true);
	}

	/**
	 * Initialise la Jframe, y ajoute ses boutons et ses Listeners.
	 */
	public void init() {

		classListLabyrinthes = new ListeLabyrinthes();
		this.setTitle("Editeur de Labyrinthe");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
		ajouter = new JButton("Nouveau");

		JPanel buttonPane1 = new JPanel();
		buttonPane1.setLayout(new BoxLayout(buttonPane1, BoxLayout.LINE_AXIS));
		buttonPane1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPane1.add(ajouter);
		buttonPane1.add(Box.createRigidArea(new Dimension(10, 0)));

		JPanel buttonPane2 = new JPanel();
		buttonPane2.setLayout(new BoxLayout(buttonPane2, BoxLayout.LINE_AXIS));
		buttonPane2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		buttonPane2.add(Box.createHorizontalGlue());

		content_pane.add(buttonPane1);
		content_pane.add(buttonPane2);
		ajouter.addActionListener(new AjouterListener());
		liste_labyrinthe
		.addMouseListener(new listSeriesDoubleClickedAL());
	}

	class AjouterListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new InterfaceEditeur();

		}
	}

	/**
	 * Lance la JFileChooser afin de selectionner le fichier à charger
	 */

	class listSeriesDoubleClickedAL extends MouseInputAdapter {
		@SuppressWarnings("static-access")
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				
				Labyrinthe selectedSerie = liste_labyrinthe.getSelectedValue();
				if (selectedSerie != null) {
					InterfaceEditeur ie = new InterfaceEditeur();
					ie.EtablirLabyrinthe(selectedSerie, ie.content_pane);					
				}
			}
		}
	}

	/**
	 * Ajoute les Labyrinthes à la Jlist.
	 */
	public void initLabyrintheIntoList(Labyrinthe laby) {
		if (laby != null) {
			liste_model.clear();
			// for (Labyrinthe labyrinthe : laby) {
			liste_model.addElement(laby);
			// }

		}
	}

}