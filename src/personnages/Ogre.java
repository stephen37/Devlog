package personnages;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Ogre extends Personnage{

	protected int vitesseMouvement;
	protected Image image;
	
	public Ogre(String nom, String race, int force, int vitesseMouvement) {
		super(nom,"Ogre", force, vitesseMouvement);
	}

	@Override
	public String toBase() {
		return "Ogre\t" + nom + "\t" + vie + "\t" + force + "\t" + vitesseMouvement;
	}
	
	
	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}
	
	@Override
	public void setImage() {
		try {
			image = ImageIO.read(new File("images/p-ogre.png"));
		} catch (IOException e) {
			System.out.println("L'image Ogre n'existe pas");
		}
	}

}
