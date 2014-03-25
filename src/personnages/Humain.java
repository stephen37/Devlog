package personnages;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Humain extends Personnage {

	protected Image image;

	public Humain(String nom,String race,  int force, int vitesseMouvement) {
		super(nom, "Humain", force, vitesseMouvement);
	}

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
		try {
			image = ImageIO.read(new File("images/p-elf.png"));
		} catch (IOException e) {
			System.out.println("L'image Elf n'existe pas");
		}
	}

}
