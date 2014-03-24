package labyrinthe;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class InterfaceEditeur extends JFrame {
	
	JPanel content_pane;
	
	private JButton save = new JButton("Save");
	private JButton saveas = new JButton("Save as");
	private JPanel  panel_save = new JPanel();
	private JPanel  panel_size = new JPanel();
	private JPanel  panel_labyrinthe = new JPanel();
	private JComboBox<Integer> taille_x = new JComboBox<Integer>();
	private JComboBox<Integer> taille_y = new JComboBox<Integer>();
	
	public InterfaceEditeur() {
		init();
		EtablirLabyrinthe(3, 3);
		this.setVisible(true);
		//this.setResizable(false);
	}
	
	public void init(){
		
		this.setTitle("Editeur de Labyrinthe");
        this.setSize(900, 900);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); 
        content_pane = new JPanel();
        content_pane.setLayout(new BorderLayout());
        JPanel top = new JPanel();        
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        
        for(int i=1; i<10; i++) {
        	taille_x.addItem(new Integer (i));
        	taille_y.addItem(new Integer (i));
        }
        

        JPanel bas_fenetre = new JPanel();
        bas_fenetre.setLayout(new BorderLayout());
        	panel_size.setLayout(new BoxLayout(panel_size, BoxLayout.X_AXIS));
        	panel_size.add(new JLabel("Taille: "));
        	panel_size.add(taille_x);
        	panel_size.add(new JLabel(" x "));
        	panel_size.add(taille_y);
        	panel_size.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        panel_save.setLayout(new BoxLayout(panel_save, BoxLayout.X_AXIS));
	        panel_save.add(save);
	        panel_save.add(saveas);
	        panel_save.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		bas_fenetre.add(panel_save, BorderLayout.EAST);
		bas_fenetre.add(panel_size, BorderLayout.WEST);
        content_pane.add(top, BorderLayout.WEST);
        content_pane.add(bas_fenetre, BorderLayout.PAGE_END);
        
        this.setContentPane(content_pane); // CETTE CLASSE EST DENUEE DE LISTENER POUR LE MOMENT
        
	}
	
	public void EtablirLabyrinthe(int x, int y) {
		
		panel_labyrinthe.setLayout(new GridBagLayout());
		panel_labyrinthe.setPreferredSize(new Dimension (300, 300));
		//Border door = BorderFactory.createDashedBorder(Color.black, 5, 10, 20, true);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridheight = 1;
	    gbc.gridwidth = 1;
	    gbc.anchor = GridBagConstraints.CENTER;
		
		for(int i=0; i<x; i++){
			for(int j=0; j<y; j++){
				gbc.gridx = i;
				gbc.gridy = j;
				
				JPanel panel_case = new JPanel();
				   //panel_case.setBorder(door);
				   panel_case.setPreferredSize(new Dimension (90, 90));
				   panel_case.add(new JLabel (new ImageIcon("./images/s-normal.png")));
				   panel_labyrinthe.add(panel_case, gbc);
			}
			
			
		}
		content_pane.add(panel_labyrinthe, BorderLayout.CENTER);
	}
	
//	public class ImagePanel extends JPanel {
//		public void paintComponent(Graphics g) {
//			super.paintComponents(g);
//			try {
//					BufferedImage img = ImageIO.read(new File(
//							("./images/s-normal.png")));
//
//					g.drawImage(img, img.getHeight(), img.getWidth(), null);
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//		}
//	}
	
	public static void main(String[] args) {
		InterfaceEditeur Editeur = new InterfaceEditeur();
	}


}
