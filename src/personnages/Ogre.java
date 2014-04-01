package personnages;
import java.awt.Image;


public class Ogre extends Personnage{

	protected int vitesseMouvement;
	protected Image image;
	
	public Ogre(String nom, String race, int force, int vitesseMouvement) {
		super(nom,"Ogre", force, vitesseMouvement);
	}

	/* (non-Javadoc)
	 * @see personnages.Personnage#toBase()
	 */
	@Override
	public String toBase() {
		return "Ogre\t" + nom + "\t" + vie + "\t" + force + "\t" + vitesseMouvement;
	}
	
	
	public int GetVItesseMouvement() {
		return vitesseMouvement;
	}
}
