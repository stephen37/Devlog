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

	static Gestionnaire gestionnaire;
	/*********************************************************************************************************/
	//TODO : Modifier l'addPersonnageDialogUI pour éviter d'ouvrir une nouvelle fenêtre..
	// Il faut enlever le new GestionnaireUI();
	GestionnaireUI gestionnaireUI = new GestionnaireUI();
	File fileSelected;
	AddPersonnagePanelUI persoPanel;

	public AddPersonnageDialogUI(JFrame owner, Gestionnaire gestionnaire,
			String title) {
		
		this.gestionnaire = gestionnaire;
		AddPersonnagePanelUI addPerso = new AddPersonnagePanelUI();
		this.add(addPerso);
		this.setTitle(title);
		this.setSize(500, 300);
		this.setLocationRelativeTo(owner);
		this.setVisible(true);
		this.setResizable(false);
	}

	/**
	 * Ajoute un panel permettant de recueillir les infos sur le personnage
	 * 
	 */
	class AddPersonnagePanelUI extends JPanel {

		JTextField persoName;
		JSlider vitesseSlider;
		JSlider forceSlider;
		String[] tabPerso = { "Humain", "Ogre", "Elf" };
		JComboBox<String> race;
		File file;
		String raceChoosen = "Humain";
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
			randomButton.addActionListener(new RandomListener());
		}

		/**
		 * Obtient la dernière valeur selectionnée par la jcombobox par
		 * l'utilisateur.
		 */
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

		/**
		 * Ajoute une image en fonction du choix de personnages
		 * 
		 */
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

		/**
		 * Permet de sauvegarder un personnage dans un fichier
		 */
		public class SaveAsListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {

				name = persoName.getText().toString();
				vitesse = vitesseSlider.getValue();
				force = forceSlider.getValue();
				if (AddPersonnageDialogUI.this.gestionnaire != null) {
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
						gestionnaire.ajouterPersonnage(name, raceChoosen,
								force, vitesse);
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
					gestionnaire.addToFile(gestionnaire.getPersonnages(),
							fileSelected);
					gestionnaireUI.initPersonnageIntoList();

				} catch (Exception e1) {
					e1.printStackTrace();
				}
				getContentPane().revalidate();
				dispose();
			}
		}

		/**
		 * Initialise un personnage de façon aléatoire et l'affiche dans le
		 * Jpanel
		 */
		public void initRandomPerso() {
			String[] nameTab = { "Alpha", "Beta", "Gamma", "Delta", "Epsilon",
					"Zeta", "Eta", "Theta", "iota" };
			int ranName = (int) (Math.random() * nameTab.length);
			name = nameTab[ranName];
			persoName.setText(name);
			int ranRace = (int) (Math.random() * 3);
			race.setSelectedItem(race.getItemAt(ranRace));
			int ranVitesse = 0;
			int ranForce = 0;
			if (race.getItemAt(ranRace).equals("Elf")) {
				ranVitesse = (int) (Math.random() * (11 - 8) + 8);
				ranForce = (int) (Math.random() * (4 - 1) + 1);
			}
			if (race.getItemAt(ranRace).equals("Ogre")) {
				ranVitesse = (int) (Math.random() * (8 - 1) + 1);
				ranForce = (int) (Math.random() * (11 - 4) + 4);
			}
			if (race.getItemAt(ranRace).equals("Humain")) {
				ranVitesse = (int) (Math.random() * (11 - 4) + 4);
				ranForce = (int) (Math.random() * (7 - 1) + 1);
			}
			vitesseSlider.setValue(ranVitesse);
			forceSlider.setValue(ranForce);
		}

		public void setName(String name) {
			persoName.setText(name);
		}

		public void setRace(String raceCharge) {
			// Test pour une simple selection, peut importe la race.
			race.setSelectedIndex(0);
		}

		public void setForce(int force) {
			forceSlider.setValue(force);
		}

		public void setVitesse(int vitesse) {
			vitesseSlider.setValue(vitesse);
		}

		/**
		 * 
		 * Permet de charger les valeurs d'un personnage déjà existant dans le
		 * Jpanel.
		 * 
		 * @param name
		 * @param raceCharge
		 * @param force
		 * @param vitesse
		 */
		public void SetPersonnage(String name, String raceCharge, int force,
				int vitesse) {
			persoName.setText(name);
			if (raceCharge.equals("Humain"))
				race.setSelectedIndex(0);
			else if (raceCharge.equals("Elf"))
				race.setSelectedIndex(1);
			else
				race.setSelectedIndex(2);
			forceSlider.setValue(force);
			vitesseSlider.setValue(vitesse);
		}

		/**
		 * Ecoute le bouton Random afin de lancer un personnage de façon
		 * aléatoire si l'utilisateur clique dessus.
		 */
		public class RandomListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				initRandomPerso();
			}
		}

		public String getName() {
			return name;
		}

		public String getRace() {
			return raceChoosen;
		}

		public int getVitesse() {
			return vitesse;
		}

		public int getForce() {
			return force;
		}
	}
}