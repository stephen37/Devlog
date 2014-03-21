package gestionnaire.gui;

import gestionnaire.Gestionnaire;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.sun.org.apache.bcel.internal.generic.LLOAD;

import personnages.Elf;
import personnages.Humain;
import personnages.Ogre;
import personnages.Personnage;

@SuppressWarnings("serial")
public class AddPersonnageDialogUI extends JFrame {

	Gestionnaire gestionnaire;

	public AddPersonnageDialogUI(JFrame owner, Gestionnaire gestionnaire,
			String title) {
		// Third argument? DOCUMENT MODAL blocks user input
		// JDialog.ModalityType.DOCUMENT_MODAL
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
			// leftJpanel.setLayout(new GridLayout(4, 1));
			// leftJpanel.add(new JLabel("Nom "));
			namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.LINE_AXIS));
			namePanel.add(new JLabel("Nom "));
			persoName = new JTextField();
			namePanel.add(persoName);
			// leftJpanel.add(persoName);
			// leftJpanel.add(new JLabel("Race "));
			racePanel.setLayout(new BoxLayout(racePanel, BoxLayout.LINE_AXIS));
			racePanel.add(new JLabel("Race "));
			race = new JComboBox<String>(tabPerso);
			// leftJpanel.add(race);
			racePanel.add(race);
			// leftJpanel.add(new JLabel("Vitesse "));
			vitessePanel.setLayout(new BoxLayout(vitessePanel,
					BoxLayout.LINE_AXIS));
			vitessePanel.add(new JLabel("Vitesse "));
			vitesseSlider = new JSlider(4, 10, 7);
			vitesseSlider.setMinorTickSpacing(1);
			vitesseSlider.setMajorTickSpacing(2);
			vitesseSlider.setPaintTicks(true);
			vitesseSlider.setPaintLabels(true);
			// leftJpanel.add(vitesseSlider);
			vitessePanel.add(vitesseSlider);
			// leftJpanel.add(new JLabel("Force "));
			forcePanel
					.setLayout(new BoxLayout(forcePanel, BoxLayout.LINE_AXIS));
			forcePanel.add(new JLabel("Force "));
			forceSlider = new JSlider(1, 7, 4);
			forceSlider.setMinorTickSpacing(1);
			forceSlider.setMajorTickSpacing(2);
			forceSlider.setPaintTicks(true);
			forceSlider.setPaintLabels(true);
			// leftJpanel.add(forceSlider);
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
			
			buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.LINE_AXIS));
			JPanel buttonSavePanel = new JPanel();
			buttonSavePanel.setLayout(new BoxLayout(buttonSavePanel, BoxLayout.X_AXIS));
			buttonSavePanel.add(buttonSave);
			buttonSavePanel.add(buttonSaveAs);
			buttonPanel.setLayout(new BorderLayout());
			buttonPanel.add(buttonSavePanel, BorderLayout.EAST);
			buttonPanel.add(randomButton, BorderLayout.WEST);
			
			
//			buttonSavePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
//			buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
			this.add(buttonPanel, BorderLayout.SOUTH);
//			buttonOk.addActionListener(new ValiderListener());
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

		public class ValiderListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				name = persoName.getText();
				vitesse = vitesseSlider.getValue();
				force = forceSlider.getValue();
				dispose();
			}
		}

		public Personnage creerPersonnage(String nom, String race, int vitesse,
				int force) {
			nom = this.name;
			race = this.raceChoosen;
			vitesse = this.vitesse;
			force = this.force;
			System.out.println(nom + "," + race + "," + vitesse + ", " + force);
			if (race.equals("Elf")) {

				return new Elf(nom, 100, force, vitesse);
			} else if (race.equals("Ogre")) {
				return new Ogre(nom, 100, force, vitesse);
			} else {
				return new Humain(nom, 100, force, vitesse);
			}
		}
	}
}