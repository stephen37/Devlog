package labyrinthe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class FenetreBloquee extends JDialog {

	JPanel content_pane;

	private JButton valider = new JButton("Valider");
	private JPanel panel_boutton1 = new JPanel();
	private JPanel panel_boutton2 = new JPanel();
	private JPanel panel_boutton3 = new JPanel();
	private JPanel panel_duree = new JPanel();
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton jr1 = new JRadioButton("Toujours");
	private JRadioButton jr2 = new JRadioButton("Periodiquement chaque");
	private JRadioButton jr3 = new JRadioButton("Aléatoirement avec prob");
	static JSlider slider_prob = new JSlider();
	static SpinnerModel modelspin_duree = new SpinnerNumberModel(0, 0, 9999,
			0.5);
	static SpinnerModel modelspin_periode = new SpinnerNumberModel(0, 0, 9999,
			0.5);
	static JSpinner spinner_duree = new JSpinner(modelspin_duree);
	static JSpinner spinner_periode = new JSpinner(modelspin_periode);
	Salle c;
	
	public FenetreBloquee(Salle c) {
		init();
		this.c = c;
		this.setVisible(true);
		this.setResizable(false);
	}

	/**
	 * Initialise les valeurs pour la JFrame, y ajoute les boutons et les listeners correspondants aux boutons.
	 */
	public void init() {

		this.setTitle("Salle Bloquée");
		this.setSize(525, 300);
		this.setLocationRelativeTo(null);
		content_pane = new JPanel();
		content_pane.setLayout(new BorderLayout());
		slider_prob.setMaximum(100);
		slider_prob.setMinimum(0);
		slider_prob.setValue(50);
		slider_prob.setPaintTicks(true);
		slider_prob.setPaintLabels(true);
		slider_prob.setMinorTickSpacing(10);
		slider_prob.setMajorTickSpacing(20);
		JPanel panel_parametres = new JPanel();
		panel_parametres.setLayout(new BoxLayout(panel_parametres,
				BoxLayout.Y_AXIS));

		panel_boutton1.add(jr1);
		panel_boutton1.setLayout(new BorderLayout());
		panel_boutton1.add(jr1, BorderLayout.WEST);

		// spinner_periode.setSize(75, 10); Ne fonctionne pas
		panel_boutton2
				.setLayout(new BoxLayout(panel_boutton2, BoxLayout.X_AXIS));
		panel_boutton2.add(jr2);
		panel_boutton2.add(spinner_periode);
		panel_boutton2.add(new JLabel(" sec"));
		panel_boutton2.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		panel_boutton2.setAlignmentX(Component.RIGHT_ALIGNMENT);

		panel_boutton3
				.setLayout(new BoxLayout(panel_boutton3, BoxLayout.X_AXIS));
		panel_boutton3.add(jr3);
		panel_boutton3.add(slider_prob);

		panel_duree.setLayout(new BoxLayout(panel_duree, BoxLayout.X_AXIS));
		panel_duree.add(new JLabel("Durée: "));
		panel_duree.add(spinner_duree);
		panel_duree.add(new JLabel(" sec"));
		panel_duree.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 0));
		panel_duree.setAlignmentX(Component.RIGHT_ALIGNMENT);

		bg.add(jr1);
		bg.add(jr2);
		bg.add(jr3);

		panel_parametres.add(panel_boutton1);
		panel_parametres.add(panel_boutton2);
		panel_parametres.add(panel_boutton3);
		panel_parametres.add(panel_duree);

		JPanel bas_fenetre = new JPanel();
		bas_fenetre.setLayout(new BorderLayout());
		JPanel panel_sauver = new JPanel();
		panel_sauver.add(valider);
		panel_sauver.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		bas_fenetre.add(panel_sauver, BorderLayout.EAST);
		content_pane.add(panel_parametres, BorderLayout.WEST);
		content_pane.add(bas_fenetre, BorderLayout.PAGE_END);
		valider.addActionListener(new ValiderListener());
		this.setContentPane(content_pane);
	}

	/**
	 * Affiche le temps de blocage d'une case sur l'image de la case.
	 */
	class ValiderListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			c.definirEtat("locked");
			if (jr1.isSelected()) {	
			}else if (jr2.isSelected()) {
				c.blockPeriod();
				c.label.setForeground(Color.red);
				c.label.setFont(new Font("Arial", Font.BOLD, 16));
				c.label.setText(""+c.blockPeriod());
				c.label.setHorizontalTextPosition(JLabel.CENTER);
				c.label.setVerticalTextPosition(JLabel.CENTER);
			}else if (jr3.isSelected()) {
				c.blockProb();
			}else {
				c.blockTime();
				c.label.setForeground(Color.red);
				c.label.setFont(new Font("Arial", Font.BOLD, 16));
				c.label.setText(""+c.blockTime());
				c.label.setHorizontalTextPosition(JLabel.CENTER);
				c.label.setVerticalTextPosition(JLabel.CENTER);
			}
			InterfaceEditeur.tab[c.GetX()][c.GetY()] = c;
			dispose();
		}
	}
}
