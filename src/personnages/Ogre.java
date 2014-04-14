package personnages;
import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Ogre extends Personnage{

	protected int vitesseMouvement; 
	protected Image image;
	
	public Ogre(String nom, String race, int force, int vitesseMouvement, String inclinaison, String arme, String armure) {
		super(nom, "Ogre", force, vitesseMouvement, inclinaison, arme, armure);
	}

	/* (non-Javadoc)
	 * @see personnages.Personnage#toBase()
	 */
	@Override
	public String toBase() {
		return "Ogre\t" + nom + "\t" + vie + "\t" + force + "\t"
				+ vitesseMouvement + "\t" + inclinaison + "\t" + arme + "\t"
				+ armure;
	}
	
	@Override
	public Component setImage() {
		return new JLabel(new ImageIcon("./images/p-ogre.png"));
	}
	
	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}
}
