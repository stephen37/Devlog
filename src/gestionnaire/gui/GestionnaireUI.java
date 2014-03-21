package gestionnaire.gui;

import gestionnaire.Gestionnaire;
import gestionnaire.run.EntreesSorties;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
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

@SuppressWarnings("serial")
public class GestionnaireUI extends JFrame {
	Gestionnaire gestionnaire;
	JList<Personnage> listPerso;
	DefaultListModel lmodel;
	JPanel contentPane;
	
	public GestionnaireUI() {
		init();
		initMenu();
		initListPersonnages();
		initPersonnageIntoList();

		this.setVisible(true);
	}

	private void init() {
		/*
		 * Initializing the main JFrame, keeping the default FlowLayout for the
		 * Jpanel's content pane
		 */
		this.setTitle("Gestionnaire");
		gestionnaire = new Gestionnaire();
		this.setSize(400, 400); // this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// center the window
		this.setLocationRelativeTo(null);
		// Instanciate a content pane
		contentPane = new JPanel();
		contentPane
				.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		contentPane.add(new ImagePanel());

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
						File file = filechooser.getSelectedFile();
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
		class MenuSauvegarderAL implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (GestionnaireUI.this.gestionnaire != null) {
					JFileChooser filechooser = new JFileChooser(".") {
						public void approveSelection() {
							File f = getSelectedFile();
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
						EntreesSorties.sauvegarderFichier(gestionnaire,
								filechooser.getSelectedFile());
					}
				} else
					JOptionPane
							.showMessageDialog(
									GestionnaireUI.this,
									"veuillez créer un nouveau gestionnaire ou charger un gestionnaire existant",
									"Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
		menuSave.addActionListener(new MenuSauvegarderAL());
	}

	private void initPersonnageIntoList() {
		if (this.gestionnaire != null) {
			lmodel.clear();
			for (Personnage personnage : this.gestionnaire.getPersonnages()) {
				lmodel.addElement(personnage);
			}
		}

	}

	private void initListPersonnages() {
		lmodel = new DefaultListModel<Personnage>();
		listPerso = new JList(lmodel);
		/* Scroll pane */
		JScrollPane scrollpane = new JScrollPane(listPerso);
		this.getContentPane().add(scrollpane);

		/* Adding a Button to add series */
		JButton buttonAddPersonnage = new JButton("Ajouter personnage");
		contentPane.add(buttonAddPersonnage);

		class ButtonAddPersoAL implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (GestionnaireUI.this.gestionnaire != null) {
					AddPersonnageDialogUI popup = new AddPersonnageDialogUI(
							GestionnaireUI.this, gestionnaire, "Ajouter Perso");
				} else
					JOptionPane
							.showMessageDialog(
									GestionnaireUI.this,
									"veuillez créer un nouveau gestionnaire ou charger un gestionnaire existant",
									"Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
		buttonAddPersonnage.addActionListener(new ButtonAddPersoAL());

		/* Adding a listener for double clicked series in the JList */
		class listSeriesDoubleClickedAL extends MouseInputAdapter {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Personnage selectedSerie = listPerso.getSelectedValue();
					if (selectedSerie != null) {
						PersonnagesUI personnageDialog = new PersonnagesUI(
								GestionnaireUI.this, gestionnaire);
					}
				}
			}
		}
		listPerso.addMouseListener(new listSeriesDoubleClickedAL());
	}

	public static void main(String args[]) {
		new GestionnaireUI();
	}

	public class ImagePanel extends JComponent {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			try {
				BufferedImage img = ImageIO.read(new File("./images/p-human.png"));

				g.drawImage(img, (this.getWidth() - img.getWidth() / 2) / 2,
						(this.getHeight() - img.getHeight() / 2) / 2,
						img.getWidth() / 2, img.getHeight() / 2, null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}