package gestionnaire.gui;

import gestionnaire.Gestionnaire;
import gestionnaire.gui.AddPersonnageDialogUI.AddPersonnagePanelUI;
import gestionnaire.run.EntreesSorties;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.MouseInputAdapter;
import javax.swing.filechooser.FileNameExtensionFilter;

import personnages.Personnage;

/**
 * @author Loesch & Batifol 
 *
 */
@SuppressWarnings("serial")
public class GestionnaireUI extends JFrame {
	static Gestionnaire gestionnaire;
	JList<Personnage> listPerso;
	static DefaultListModel<Personnage> lmodel;
	JPanel contentPane;
	File file;
	AddPersonnageDialogUI persoDialog;
	File fileSelected;
	DefaultListModel<Personnage> listeModelPerso;

	public GestionnaireUI() {
		init();
		initMenu();
		initListPersonnages();
		// initPersonnageIntoList();

		this.setVisible(true);
	}

	/**
	 * Initialise la JFrame.
	 */
	private void init() {
		/*
		 * Initializing the main JFrame, keeping the default FlowLayout for the
		 * Jpanel's content pane
		 */
		this.setTitle("Gestionnaire");
		gestionnaire = new Gestionnaire();
		this.setSize(600, 400); // this.pack();
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// center the window
		this.setLocationRelativeTo(null);
		// Instanciate a content pane
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));

		this.setContentPane(contentPane);

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
	 * Initialise le menu dans la jframe.
	 */
	private void initMenu() {
		// A JMenuBar
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("Gestionnaire");
		menubar.add(menu);
		this.setJMenuBar(menubar);

		/* Créer un nouveau Gestionnaire */
		JMenuItem menuNew = new JMenuItem("Nouveau...");
		menu.add(menuNew);
		menuNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gestionnaire = new Gestionnaire();
				initPersonnageIntoList();
			}
		});

		/* Charger un gestionnaire existant */
		JMenuItem menuLoad = new JMenuItem("Charger...");

		menu.add(menuLoad);
		class MenuChargerAL implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				JFileChooser filechooser = new JFileChooser(".");
				filechooser.setFileFilter(new FileNameExtensionFilter(
						"Gestionnaire", "save"));
				if (filechooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						file = filechooser.getSelectedFile();
						gestionnaire.chargerPersonnage(file.getAbsolutePath());
					} catch (Exception ex) {
						Logger.getLogger(GestionnaireUI.class.getName()).log(
								Level.SEVERE, null, ex);
					}
				}
				initPersonnageIntoList();
			}
		}

		menuLoad.addActionListener(new MenuChargerAL());

		/* Sauvegarder un gestionnaire existant */
		JMenuItem menuSave = new JMenuItem("Sauvegarder...");
		menu.add(menuSave);

		class SaveAsListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (gestionnaire != null) {
					JFileChooser filechooser = new JFileChooser(".") {
						public void approveSelection() {
							fileSelected = getSelectedFile();
							if (fileSelected.exists()) {
								int result = JOptionPane.showConfirmDialog(
										this, "Écraser le fichier?",
										"Confirmation",
										JOptionPane.YES_NO_CANCEL_OPTION);
								switch (result) {
								case JOptionPane.YES_OPTION:
									super.approveSelection();
									return;
								default:
									super.cancelSelection();
								}
							} else
								super.approveSelection();
						}
					};
					if (filechooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						EntreesSorties.sauvegarderFichier(gestionnaire
								.getPersonnages().toString(), filechooser
								.getSelectedFile());
					}
				} else {
					JOptionPane
							.showMessageDialog(
									GestionnaireUI.this,
									"veuillez créer un nouveau gestionnaire ou charger un gestionnaire existant",
									"Erreur", JOptionPane.ERROR_MESSAGE);
				}

				try {
					gestionnaire.addToFile(gestionnaire.getPersonnages(),
							fileSelected);
					GestionnaireUI.initPersonnageIntoList();

				} catch (Exception e1) {
					e1.printStackTrace();
				}

				revalidate();
			}
		}
		menuSave.addActionListener(new SaveAsListener());

	}

	/**
	 * Charge les personnages dans la Jmodel (liste d'affichage).
	 */
	public static void initPersonnageIntoList() {
		if (gestionnaire != null) {
			lmodel.clear();
			for (Personnage personnage : gestionnaire.getPersonnages()) {
				lmodel.addElement(personnage);
			}
		}
	}

	public void initPersonnageIntoList(ArrayList<Personnage> list) {
		if (list != null) {
			lmodel.clear();
			for (Personnage personnage : list) {
				lmodel.addElement(personnage);
			}
		}
	}

	public JList<Personnage> getJList() {
		return listPerso;
	}

	/**
	 * Initialise la Jlist et la DefaultListModel
	 */
	public void initListPersonnages() {
		lmodel = new DefaultListModel<Personnage>();
		listPerso = new JList<Personnage>(lmodel);
		/* Scroll pane */
		JScrollPane scrollpane = new JScrollPane(listPerso);
		this.getContentPane().add(scrollpane);

		/* Adding a Button to add series */
		JButton buttonAddPersonnage = new JButton("Ajouter personnage");
		contentPane.add(buttonAddPersonnage);

		/* Permet de SUPPRIMER un personnage */
		JButton removePersonnageButton = new JButton("Supprimer personnage");
		contentPane.add(removePersonnageButton);

		JButton closeButton = new JButton(" Fermer ");
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.add(closeButton);

		/**
		 * Supprime un personnage de la liste.
		 */
		class RemovePersonnage implements ActionListener {
			public void actionPerformed(ActionEvent e) {

				int selected = listPerso.getSelectedIndex();
				lmodel.remove(selected);

			}
		}
		class ButtonAddPersoAL implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (gestionnaire != null) {
					// AddPersonnageDialogUI popup = new AddPersonnageDialogUI(
					// GestionnaireUI.this, gestionnaire, "Ajouter Perso");
					new AddPersonnageDialogUI(GestionnaireUI.this,
							gestionnaire, "Ajouter Perso");
				} else
					JOptionPane
							.showMessageDialog(
									GestionnaireUI.this,
									"veuillez créer un nouveau gestionnaire ou charger un gestionnaire existant",
									"Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
		removePersonnageButton.addActionListener(new RemovePersonnage());
		buttonAddPersonnage.addActionListener(new ButtonAddPersoAL());

		/* Adding a listener for double clicked series in the JList */
		class DoubleClickedPerso extends MouseInputAdapter {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Personnage selectedSerie = listPerso.getSelectedValue();

					persoDialog = new AddPersonnageDialogUI(
							GestionnaireUI.this, gestionnaire, "Modifier Perso");

					AddPersonnagePanelUI.persoName.setText(selectedSerie
							.getNom());

					AddPersonnagePanelUI.raceChoosen = selectedSerie.getRace();
					AddPersonnagePanelUI.vitesseSlider.setValue(selectedSerie
							.getVitesse());
					AddPersonnagePanelUI.forceSlider.setValue(selectedSerie
							.getForce());
					if (selectedSerie.getInclinaison().equals(":)")) {
						AddPersonnagePanelUI.gentil.setSelected(true);
						AddPersonnagePanelUI.mechant.setSelected(false);
					} else {
						AddPersonnagePanelUI.gentil.setSelected(false);
						AddPersonnagePanelUI.mechant.setSelected(true);
					}

					System.out.println("Nom " + selectedSerie.getNom()
							+ " Race " + selectedSerie.getRace() + " Force "
							+ selectedSerie.getForce() + " Vitesse "
							+ selectedSerie.getVitesse());
				}
			}
		}
		listPerso.addMouseListener(new DoubleClickedPerso());
	}
}