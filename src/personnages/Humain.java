package personnages;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Humain extends Personnage {

	protected Image image;

	public Humain(String nom, String race, int force, int vitesseMouvement, String inclinaison, String arme, String armure) {
		super(nom, "Humain", force, vitesseMouvement, inclinaison, arme, armure);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see personnages.Personnage#toBase()
	 */
	@Override
	public String toBase() {
		System.out.println();
		return "Humain\t" + nom + "\t" + vie + "\t" + force + "\t"
				+ vitesseMouvement + "\t" + inclinaison + "\t" + arme + "\t"
				+ armure;
	}

	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}

	@Override
	public Component setImage() {
		return new JLabel(new ImageIcon("./images/p-human.png"));
	}

	/*@Override
	public void run() {
		System.out.println("Lancement de run");
		int rand = (int) Math.random() * (5 - 1) + 1;
		if (InterfaceEditeur.tab[laby.getX()][laby.getY()].getEtat().equals(
				"exit")) {
			return;
		} else {
			switch (tabMouvements[rand]) {
			case "haut":
				InterfaceEditeur.tab[laby.getX()][laby.getY() + 1]
						.add(new JLabel(new ImageIcon("./images/p-human.png")));
				break;
			case "droite":
				InterfaceEditeur.tab[laby.getX() + 1][laby.getY()]
						.add(new JLabel(new ImageIcon("./images/p-human.png")));
				break;
			case "gauche":
				InterfaceEditeur.tab[laby.getX() - 1][laby.getY()]
						.add(new JLabel(new ImageIcon("./images/p-human.png")));
				break;
			case "bas":
				InterfaceEditeur.tab[laby.getX()][laby.getY() - 1]
						.add(new JLabel(new ImageIcon("./images/p-human.png")));
				break;
			default:
				break;
			}

		}
	}*/

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
