package personnages;
import java.awt.Image;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Elf extends Personnage {

	protected Image image;
	
	public Elf(String nom, String race,int force, int vitesseMouvement) {
		super(nom,"Elf", force, vitesseMouvement);
	}

	/* (non-Javadoc)
	 * @see personnages.Personnage#toBase()
	 */
	@Override
	public String toBase() {
		return "Elf\t" + nom + "\t" + vie + "\t" + force + "\t vitesse de mouvement " + vitesseMouvement;
	}
	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}
	
	@Override
	public void setImage() {
		JPanel panelImage = new JPanel();
		panelImage.add(new JLabel(new ImageIcon("./images/p-elf.png")));
	}

}
