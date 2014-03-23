package gestionnaire.gui;

import gestionnaire.Gestionnaire;
import gestionnaire.run.EntreesSorties;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import personnages.Personnage;

@SuppressWarnings("serial")
public class AddPersonnageDialogUI extends JFrame {

	Gestionnaire gestionnaire;
	GestionnaireUI gestionnaireUI;
	File f;

	public AddPersonnageDialogUI(JFrame owner, Gestionnaire gestionnaire,
			String title) {
		// Third argument? DOCUMENT MODAL blocks user input
		// JDialog.ModalityType.DOCUMENT_MODAL*
		// super(owner, title);
		this.gestionnaire = gestionnaire;
		AddPersonnagePanelUI add_perso = new AddPersonnagePanelUI();
		this.add(add_perso);
		this.setTitle("Ajouter Personnage");
		this.setSize(500, 300);
		this.setLocationRelativeTo(owner);
		this.setVisible(true);
		this.setResizable(false);
	}

	class AddPersonnagePanelUI extends JPanel {

		JTextField persoName;
		JSlider vitesseSlider;
		JSlider forceSlider;
		String[] tabPerso = { "Humain", "Ogre", "Elf" };
		JComboBox<String> race;
		File file;
		String raceChoosen = "";
		String name = "";
		int vitesse;
		int force;
		JPanel leftJpanel;
		ArrayList<Personnage> list = gestionnaire.getPersonnages();

		public AddPersonnagePanelUI() {
			this.setLayout(new BorderLayout());
			/*
			 * A JPanel asking for a name and a file, centered in the main
			 * JPanel
			 */
			JPanel namePanel = new JPanel();
			JPanel racePanel = new JPanel();
			JPanel vitessePanel = new JPanel();
			JPanel forcePanel = new JPanel();
			JPanel buttonPanel = new JPanel();

			leftJpanel = new JPanel();
			leftJpanel
					.setLayout(new BoxLayout(leftJpanel, BoxLayout.PAGE_AXIS));
			leftJpanel.setAlignmentY(CENTER_ALIGNMENT);
			namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
			namePanel.add(new JLabel("Nom "));
			persoName = new JTextField();
			namePanel.add(persoName);
			racePanel.setLayout(new BoxLayout(racePanel, BoxLayout.LINE_AXIS));
			racePanel.add(new JLabel("Race "));
			race = new JComboBox<String>(tabPerso);
			racePanel.add(race);
			vitessePanel.setLayout(new BoxLayout(vitessePanel,
					BoxLayout.LINE_AXIS));
			vitessePanel.add(new JLabel("Vitesse "));
			vitesseSlider = new JSlider(4, 10, 7);
			vitesseSlider.setMinorTickSpacing(1);
			vitesseSlider.setMajorTickSpacing(2);
			vitesseSlider.setPaintTicks(true);
			vitesseSlider.setPaintLabels(true);
			vitessePanel.add(vitesseSlider);
			forcePanel
					.setLayout(new BoxLayout(forcePanel, BoxLayout.LINE_AXIS));
			forcePanel.add(new JLabel("Force "));
			forceSlider = new JSlider(1, 7, 4);
			forceSlider.setMinorTickSpacing(1);
			forceSlider.setMajorTickSpacing(2);
			forceSlider.setPaintTicks(true);
			forceSlider.setPaintLabels(true);
			forcePanel.add(forceSlider);
			leftJpanel.add(namePanel);
			leftJpanel.add(racePanel);
			leftJpanel.add(vitessePanel);
			leftJpanel.add(forcePanel);
			this.add(leftJpanel, BorderLayout.WEST);
			this.add(new ImagePanel());

			race.addItemListener(new ComboBoxListener());

			/* A Button valider placed South of the main JPanel */
			JButton buttonSave = new JButton(" Save ");
			JButton buttonSaveAs = new JButton(" Save as ");
			JButton randomButton = new JButton(" Random ");

			buttonPanel.setLayout(new BoxLayout(buttonPanel,
					BoxLayout.LINE_AXIS));
			JPanel buttonSavePanel = new JPanel();
			buttonSavePanel.setLayout(new BoxLayout(buttonSavePanel,
					BoxLayout.X_AXIS));
			buttonSavePanel.add(buttonSave);
			buttonSavePanel.add(buttonSaveAs);
			buttonPanel.setLayout(new BorderLayout());
			buttonPanel.add(buttonSavePanel, BorderLayout.EAST);
			buttonPanel.add(randomButton, BorderLayout.WEST);
			JButton validerButton = new JButton(" Valider ");
			validerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10,
					20));
			buttonPanel.add(validerButton, BorderLayout.AFTER_LAST_LINE);
			validerButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			// TODO : Créer des bordures entre les buttons.

			this.add(buttonPanel, BorderLayout.SOUTH);
			// buttonSave.addActionListener(new SaveListener());
			buttonSaveAs.addActionListener(new SaveAsListener());
		}

		public class ComboBoxListener implements ItemListener {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Get the last object selected in the JComboBox
				getContentPane().repaint();

				if (e.getStateChange() == 1) {
					raceChoosen = (String) e.getItem();
					System.out.println("Race choisie est " + raceChoosen);

					if (raceChoosen.equals("Elf")) {
						vitesseSlider.setMinimum(8);
						vitesseSlider.setMaximum(10);
						vitesseSlider.setValue(9);
						forceSlider.setMinimum(1);
						forceSlider.setMaximum(3);
						forceSlider.setValue(2);
					} else if (raceChoosen.equals("Ogre")) {
						vitesseSlider.setMinimum(1);
						vitesseSlider.setMaximum(7);
						vitesseSlider.setValue(4);
						forceSlider.setMinimum(4);
						forceSlider.setMaximum(10);
						forceSlider.setValue(7);
					} else {
						vitesseSlider.setMinimum(4);
						vitesseSlider.setMaximum(10);
						vitesseSlider.setValue(7);
						forceSlider.setMinimum(1);
						forceSlider.setMaximum(7);
						forceSlider.setValue(4);

					}
				}

			}
		}

		public class ImagePanel extends JPanel {
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				try {
					if (raceChoosen.equals("Elf")) {
						BufferedImage img = ImageIO.read(new File(
								("./images/p-elf.png")));

						g.drawImage(img, img.getHeight(), img.getWidth(), null);
					} else if (raceChoosen.equals("Ogre")) {
						BufferedImage img = ImageIO.read(new File(
								("./images/p-ogre.png")));
						g.drawImage(img, img.getHeight(), img.getWidth(), null);
					} else {
						BufferedImage img = ImageIO.read(new File(
								("./images/p-human.png")));
						g.drawImage(img, img.getHeight(), img.getWidth(), null);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		public class SaveAsListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				name = persoName.getText().toString();
				vitesse = vitesseSlider.getValue();
				force = forceSlider.getValue();
				if (AddPersonnageDialogUI.this.gestionnaire != null) {
					JFileChooser filechooser = new JFileChooser(".") {
						public void approveSelection() {
							f = getSelectedFile();
							if (f.exists()) {
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
						gestionnaire.ajouterPersonnage(name, raceChoosen, force,
								vitesse);
						
						
						EntreesSorties.sauvegarderFichier(gestionnaire
								.getPersonnages().toString(), filechooser
								.getSelectedFile());
					}
				} else {
					JOptionPane
							.showMessageDialog(
									AddPersonnageDialogUI.this,
									"veuillez créer un nouveau gestionnaire ou charger un gestionnaire existant",
									"Erreur", JOptionPane.ERROR_MESSAGE);
				}

				// Le personnage s'ajoute bien à l'arraylist lors du chargement.
				try {
					gestionnaire.addToFile(gestionnaire.getPersonnages(), f);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		/*
		 * 
		 * TODO : Récupérer le chemin du fichier ouvert par l'utilisateur en cas
		 * de modification de personnages.
		 * 
		 * public class SaveListener implements ActionListener {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { vitesse =
		 * vitesseSlider.getValue(); force = forceSlider.getValue();
		 * 
		 * if (AddPersonnageDialogUI.this.gestionnaire != null) {
		 * EntreesSorties.sauvegarderFichier(gestionnaire,
		 * filechooser.getSelectedFile()); } else { JOptionPane
		 * .showMessageDialog( AddPersonnageDialogUI.this,
		 * "veuillez créer un nouveau gestionnaire ou charger un gestionnaire existant"
		 * , "Erreur", JOptionPane.ERROR_MESSAGE); } dispose(); } }
		 */
		/*
		 * public void creerPersonnage(String nom, String race, int vitesse, int
		 * force) { nom = this.name; race = this.raceChoosen; vitesse =
		 * this.vitesse; force = this.force; gestionnaire.ajouterPersonnage(nom,
		 * force, vitesse);
		 * 
		 * }
		 */
	}
}