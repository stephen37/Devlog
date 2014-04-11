package personnages;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import labyrinthe.InterfaceEditeur;

public class Humain extends Personnage {

	protected Image image;

	public Humain(String nom, String race, int force, int vitesseMouvement) {
		super(nom, "Humain", force, vitesseMouvement);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see personnages.Personnage#toBase()
	 */
	@Override
	public String toBase() {
		return "Humain\t" + nom + "\t" + vie + "\t" + force + "\t"
				+ vitesseMouvement;
	}

	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}

	@Override
	public Component setImage() {
		return new JLabel(new ImageIcon("./images/p-human.png"));
	}

	@Override
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
	}
}
