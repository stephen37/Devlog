package personnages;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Elf extends Personnage {

	protected Image image;

	public Elf(String nom, String race, int force, int vitesseMouvement,
			String inclinaison, String arme, String armure) {
		super(nom, "Elf", force, vitesseMouvement, inclinaison, arme, armure);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see personnages.Personnage#toBase()
	 */
	@Override
	public String toBase() {
		return "Elf\t" + nom + "\t" + vie + "\t" + force
				+ "\t vitesse de mouvement " + vitesseMouvement + "\t"
				+ inclinaison + "\t" + arme + "\t" + armure;
	}

	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}

	@Override
	public Component setImage() {
		return new JLabel(new ImageIcon("./images/p-elf.png"));
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
