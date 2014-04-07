package personnages;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Humain extends Personnage {

	protected Image image;

	public Humain(String nom,String race,  int force, int vitesseMouvement) {
		super(nom, "Humain", force, vitesseMouvement);
	}

	/* (non-Javadoc)
	 * @see personnages.Personnage#toBase()
	 */
	@Override
	public String toBase() {
		return "Humain\t" + nom + "\t" + vie + "\t" + force
				+ "\t" + vitesseMouvement;
	}

	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}

	@Override
	public void setImage() {
		JPanel panelImage = new JPanel();
		panelImage.add(new JLabel(new ImageIcon("./images/p-human.png")));
	}
}
