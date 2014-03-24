package labyrinthe;
import java.awt.BorderLayout;
//import java.awt.GridLayout;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
//import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;


public class FenetreBloquee extends JFrame {
	
	JPanel content_pane;
	
	private JButton sauver = new JButton("Sauver");
	private JPanel  panel_boutton1 = new JPanel();
	private JPanel  panel_boutton2 = new JPanel();
	private JPanel  panel_boutton3 = new JPanel();
	private JPanel  panel_duree = new JPanel();
	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton jr1 = new JRadioButton("Toujours");
	private JRadioButton jr2 = new JRadioButton("Periodiquement chaque");
	private JRadioButton jr3 = new JRadioButton("Aléatoirement avec prob");
	JSlider prob = new JSlider();
	SpinnerModel modelspin_duree = new SpinnerNumberModel(0, 0, 9999, 0.5);
	SpinnerModel modelspin_periode = new SpinnerNumberModel(0, 0, 9999, 0.5);
	JSpinner spinner_duree = new JSpinner(modelspin_duree);
	JSpinner spinner_periode = new JSpinner(modelspin_periode);
	
	public FenetreBloquee() {
		init();
		
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public void init(){
		
		this.setTitle("Salle Bloquée");
        this.setSize(500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); 
        content_pane = new JPanel();
        content_pane.setLayout(new BorderLayout());
        prob.setMaximum(100);
        prob.setMinimum(0);
        prob.setValue(50);
        prob.setPaintTicks(true);
        prob.setPaintLabels(true);
        prob.setMinorTickSpacing(10);
        prob.setMajorTickSpacing(20);
        JPanel panel_parametres = new JPanel();        
        panel_parametres.setLayout(new BoxLayout(panel_parametres, BoxLayout.Y_AXIS));
        
        panel_boutton1.add(jr1);
        panel_boutton1.setLayout(new BorderLayout());
        panel_boutton1.add(jr1, BorderLayout.WEST);
        
        //spinner_periode.setSize(75, 10);  Ne fonctionne pas
        panel_boutton2.setLayout(new BoxLayout(panel_boutton2, BoxLayout.X_AXIS));
        panel_boutton2.add(jr2);
        panel_boutton2.add(spinner_periode);
        panel_boutton2.add(new JLabel(" sec"));
        panel_boutton2.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panel_boutton2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        
        
        panel_boutton3.setLayout(new BoxLayout(panel_boutton3, BoxLayout.X_AXIS));
        panel_boutton3.add(jr3);
        panel_boutton3.add(prob);
        
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
        panel_sauver.add(sauver);
        panel_sauver.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		bas_fenetre.add(panel_sauver, BorderLayout.EAST);
        content_pane.add(panel_parametres, BorderLayout.WEST);
        content_pane.add(bas_fenetre, BorderLayout.PAGE_END);
        
        this.setContentPane(content_pane); // CETTE CLASSE EST DENUEE DE LISTENER POUR LE MOMENT
        
	}
	
	public static void main(String[] args) {
		FenetreBloquee Fenetre = new FenetreBloquee();
	}

}
