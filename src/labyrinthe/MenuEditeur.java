package labyrinthe;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class MenuEditeur extends JFrame {
	JList liste_labyrinthe;
	DefaultListModel liste_model;
	JPanel content_pane;
	
	private JButton ajouter;
	private JButton supprimer;
	private JButton ouvrir;
	private DefaultListModel listModel;
	private JList list;
	private JPanel container;
	private JTextField perso_nom;
	private JTextField perso_type;
	
	public MenuEditeur(){
		init();
		initListLabyrinthe();
		initLabyrintheIntolist();

		this.setVisible(true);
	}
	
	public void init(){
		
		this.setTitle("Editeur de Labyrinthe");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); 
        content_pane = new JPanel();
        content_pane.setLayout(new BoxLayout(content_pane, BoxLayout.PAGE_AXIS));
        
        this.setContentPane(content_pane);
        
	}

	public void initListLabyrinthe(){
		liste_model = new DefaultListModel();
		liste_labyrinthe = new JList(liste_model);
		JScrollPane scroll_pane = new JScrollPane(liste_labyrinthe);
		scroll_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.getContentPane().add(scroll_pane);
		ajouter = new JButton("+");
		supprimer =  new JButton("-");
		ouvrir = new JButton("ouvrir");
		
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
			
		
		
		content_pane.add(buttonPane1);
		content_pane.add(buttonPane2);
	}
	
	public void initLabyrintheIntolist(){
		
	}
	
	public void initButton(){
		
	}
	
	public static void main(String[] args) {
		MenuEditeur Menu = new MenuEditeur();
	}
}

