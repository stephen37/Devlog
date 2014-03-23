package personnages;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Elf extends Personnage {

	protected Image image;
	
	public Elf(String nom, String race,int force, int vitesseMouvement) {
		super(nom,"Elf", force, vitesseMouvement);
	}

	@Override
	public String toBase() {
		return "Elf\t" + nom + "\t" + vie + "\t" + force + "\t vitesse de mouvement " + vitesseMouvement;
	}
	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}
	
	@Override
	public void setImage() {
		try {
			image = ImageIO.read(new File("images/p-elf.png"));
		} catch (IOException e) {
			System.out.println("L'image Elf n'existe pas");
		}
	}


}
