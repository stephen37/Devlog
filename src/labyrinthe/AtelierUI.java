package labyrinthe;

import gestionnaire.Gestionnaire;
import gestionnaire.gui.AddPersonnageDialogUI;
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
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class AtelierUI extends JFrame {

	private Dimension dimensionJDialog = new Dimension(400, 400);
	private JPanel container;
	private JList listPerso;
	private DefaultListModel lmodel;
	private JButton ajouter, supprimer, ouvrir;
	private Gestionnaire gestionnaire = new Gestionnaire();
	private GestionnaireUI gui;

	public AtelierUI() {
		init();

		initEditeurLabyrinthe();
		this.setVisible(true);
	}

	public void init() {

		this.setTitle("Atelier");
		this.setSize(dimensionJDialog);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));

		this.setContentPane(container);

	}

	public void initEditeurLabyrinthe() {
		lmodel = new DefaultListModel();
		listPerso = new JList(lmodel);
		JScrollPane scrollPane = new JScrollPane(listPerso);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(scrollPane);
		ajouter = new JButton("+");
		supprimer = new JButton("-");
		ouvrir = new JButton("ouvrir");

		ajouter.addActionListener(new AjouterListener());
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
		buttonPane2.add(ouvrir);

		container.add(buttonPane1);
		container.add(buttonPane2);
	}

	class AjouterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new AddPersonnageDialogUI(AtelierUI.this, gestionnaire, "dsdq");
		}

	}

	class OuvrirListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser filechooser = new JFileChooser(".");
			filechooser.setFileFilter(new FileNameExtensionFilter(
					"Gestionnaire", "save"));
			if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				try {
					File file = filechooser.getSelectedFile();
					gestionnaire.chargerPersonnage(file.getAbsolutePath());
				} catch (Exception ex) {
					Logger.getLogger(GestionnaireUI.class.getName()).log(
							Level.SEVERE, null, ex);
				}
			}

//			gui.initPersonnageIntoList();
		}
	}

}
