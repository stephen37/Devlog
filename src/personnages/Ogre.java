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
	
	@Override
	public void definirAttaque() {
		if (this.arme.equalsIgnoreCase("Arc")) {
			attaque = force*1.1;
		}
		if (this.arme.equalsIgnoreCase("Epee")) {
			attaque = force*1.2;
		}
		if (this.arme.equalsIgnoreCase("Hache")) {
			attaque = force*1.3;
		}
		
	}

	@Override
	public void definirDefense() {
		if (this.armure.equalsIgnoreCase("Cuir")) {
			defense = vitesseMouvement*1.1;
		}
		if (this.armure.equalsIgnoreCase("Maille")) {
			defense = vitesseMouvement*1.2;
		}
		if (this.armure.equalsIgnoreCase("Or")) {
			defense = vitesseMouvement*1.5;
		}
		
	}
}
