package personnages;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Ogre extends Personnage{

	protected int vitesseMouvement; 
	protected Image image;
	
	public Ogre(String nom, String race, int force, int vitesseMouvement) {
		super(nom,"Ogre", force, vitesseMouvement);
	}

	/* (non-Javadoc)
	 * @see personnages.Personnage#toBase()
	 */
	@Override
	public String toBase() {
		return "Ogre\t" + nom + "\t" + vie + "\t" + force + "\t" + vitesseMouvement;
	}
	
	@Override
	public void setImage() {
		JPanel panelImage = new JPanel();
		panelImage.add(new JLabel(new ImageIcon("./images/p-ogre.png")));
	}
	
	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}
}
