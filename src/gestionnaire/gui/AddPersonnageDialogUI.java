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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

import personnages.Personnage;

/**
 * @author Loesch & Batifol 
 *
 */
@SuppressWarnings("serial")
public class AddPersonnageDialogUI extends JDialog {

	static Gestionnaire gestionnaire;
	static File fileSelected;
	static AddPersonnagePanelUI persoPanel;
	static JDialog mainframe;

	public AddPersonnageDialogUI(JFrame owner, Gestionnaire newgestionnaire,
			String title) {

		mainframe = this;
		gestionnaire = newgestionnaire;
		AddPersonnagePanelUI persoPanel = new AddPersonnagePanelUI();
		mainframe.add(persoPanel);
		mainframe.setTitle(title);
		mainframe.setSize(500, 500);
		mainframe.setLocationRelativeTo(owner);
		mainframe.setVisible(true);
		mainframe.setResizable(false);
	}

	/**
	 * Ajoute un panel permettant de recueillir les infos sur le personnage
	 * 
	 */
	public static class AddPersonnagePanelUI extends JPanel {

		static JTextField persoName;
		static JSlider vitesseSlider;
		static JSlider forceSlider;
		static String[] tabPerso = { "Humain", "Ogre", "Elf" };
		static JComboBox<String> race;
		File file;
		static String raceChoosen = "Humain";
		String name = "";
		int vitesse;
		int force;
		JPanel leftJpanel;
		ArrayList<Personnage> list = gestionnaire.getPersonnages();
		String[] tabArmure = { "Cuir", "Maille", "Or" };
		String[] tabArme = { "Epee", "Arc", "Hache" };
		JComboBox<String> arme;
		JComboBox<String> armure;
		String armeChoisie = "Epee";
		String armureChoisie = "Cuir";
		String inclinaisonChoisie = ":)";
		ButtonGroup bg;
		static JRadioButton gentil;
		static JRadioButton mechant;

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
			JPanel inclinaisonPanel = new JPanel();
			JPanel armePanel = new JPanel();
			JPanel armurePanel = new JPanel();

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
			JLabel inclinaisonLabel = new JLabel("Inclinaison");
			gentil = new JRadioButton(":)");
			mechant = new JRadioButton(":(");
			bg = new ButtonGroup();
			bg.add(gentil);
			bg.add(mechant);
			inclinaisonPanel.add(inclinaisonLabel);
			inclinaisonPanel.add(gentil);
			inclinaisonPanel.add(mechant);
			armePanel.setLayout(new BoxLayout(armePanel, BoxLayout.LINE_AXIS));
			armePanel.add(new JLabel(" Arme "));
			arme = new JComboBox<String>(tabArme);
			armePanel.add(arme);
			arme.addItemListener(new ArmeComboBoxListener());

			armurePanel.setLayout(new BoxLayout(armurePanel,
					BoxLayout.LINE_AXIS));
			armurePanel.add(new JLabel(" Armure "));
			armure = new JComboBox<String>(tabArmure);
			armure.addItemListener(new ArmureComboBoxListener());
			armurePanel.add(armure);

			leftJpanel.add(namePanel);
			leftJpanel.add(racePanel);
			leftJpanel.add(vitessePanel);
			leftJpanel.add(forcePanel);
			leftJpanel.add(inclinaisonPanel);
			leftJpanel.add(armePanel);
			leftJpanel.add(armurePanel);
			this.add(leftJpanel, BorderLayout.WEST);
			this.add(new ImagePanel());

			race.addItemListener(new RaceComboBoxListener());

			/* A Button valider placed South of the main JPanel */
			JButton randomButton = new JButton(" Random ");

			buttonPanel.setLayout(new BoxLayout(buttonPanel,
					BoxLayout.LINE_AXIS));
			JPanel buttonValiderPanel = new JPanel();
			buttonValiderPanel.setLayout(new BoxLayout(buttonValiderPanel,
					BoxLayout.X_AXIS));

			buttonPanel.setLayout(new BorderLayout());
			buttonPanel.add(buttonValiderPanel, BorderLayout.EAST);
			buttonPanel.add(randomButton, BorderLayout.WEST);
			JButton validerButton = new JButton(" Valider ");
			validerButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10,
					20));
			buttonValiderPanel.add(validerButton);
			validerButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					name = persoName.getText().toString();
					vitesse = vitesseSlider.getValue();
					force = forceSlider.getValue();

					gestionnaire.ajouterPersonnage(name, raceChoosen, force,
							vitesse, inclinaisonChoisie, armeChoisie,
							armureChoisie);

					GestionnaireUI.initPersonnageIntoList();
					mainframe.dispose();
				}
			});

			this.add(buttonPanel, BorderLayout.SOUTH);
			randomButton.addActionListener(new RandomListener());
			gentil.addActionListener(new InclinaisonListener());
			mechant.addActionListener(new InclinaisonListener());

		}

		class ArmeComboBoxListener implements ItemListener {
			@Override
			public void itemStateChanged(ItemEvent e) {
				repaint();
				if (e.getStateChange() == 1) {
					armeChoisie = (String) e.getItem();
				}
			}
		}

		class ArmureComboBoxListener implements ItemListener {
			@Override
			public void itemStateChanged(ItemEvent e) {
				repaint();
				if (e.getStateChange() == 1) {
					armureChoisie = (String) e.getItem();
				}
			}
		}

		/**
		 * Obtient la dernière valeur selectionnée par la jcombobox par
		 * l'utilisateur.
		 */
		public class RaceComboBoxListener implements ItemListener {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// Get the last object selected in the JComboBox
				repaint();

				if (e.getStateChange() == 1) {
					raceChoosen = (String) e.getItem();

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
		class ImagePanel extends JPanel {
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

		class InclinaisonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JRadioButton) {
					JRadioButton radiobutton = (JRadioButton) e.getSource();
					if (radiobutton.isSelected()) {
						inclinaisonChoisie = radiobutton.getText();
						System.out.println("inclinaison " + inclinaisonChoisie);
					}
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
						gestionnaire.ajouterPersonnage(name, raceChoosen,
								force, vitesse, inclinaisonChoisie,
								armeChoisie, armureChoisie);
						EntreesSorties.sauvegarderFichier(gestionnaire
								.getPersonnages().toString(), filechooser
								.getSelectedFile());
					}
				} else {
					JOptionPane
							.showMessageDialog(
									persoPanel,
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
				mainframe.dispose();

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
			int armeAleatoire = (int) (Math.random() * tabArme.length);
			arme.setSelectedItem(arme.getItemAt(armeAleatoire));
			int armureAleatoire = (int) (Math.random() * tabArmure.length);
			armure.setSelectedItem(armure.getItemAt(armureAleatoire));
			int inclinaisonAleatoire = (int) (Math.random() * (3 - 1) + 1);
			if (inclinaisonAleatoire == 1) {
				gentil.setSelected(true);
				inclinaisonChoisie = gentil.getText();
			} else {
				mechant.setSelected(true);
				inclinaisonChoisie = mechant.getText();
			}
		}

		public void setName(String name) {
			persoName.setText(name);
		}

		public void setRace(String raceCharge) {
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