package gestionnaire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;
import java.util.ArrayList;

import personnages.Elf;
import personnages.Humain;
import personnages.Ogre;
import personnages.Personnage;

@SuppressWarnings("serial")
public class Gestionnaire implements Serializable {

	private static ArrayList<Personnage> personnages;

	public Gestionnaire() {
		personnages = new ArrayList<Personnage>();
	}

	
	/**
	 * 
	 * Ajoute un personnage à partir de ses attributs à l'arrayList de personnages.
	 * @param nom
	 * @param race
	 * @param pointForce
	 * @param vitesse
	 * @return String 
	 */
	public boolean ajouterPersonnage(String nom,String race,  final int pointForce, final int vitesse) {
		return personnages.add(new Personnage(nom, race, pointForce, vitesse) {
			@Override
			public String toBase() {
				return nom + "\t" + race + "\t" + pointForce + "\t" +vitesse + "\t" +vie;
//				throw new UnsupportedOperationException("Not supported yet.");
			}
@Override
			public void setImage() {}
		});
	}

	@Override
	public String toString() {
		String res = new String();
		for (int i = 1; i <= personnages.size(); i++) {
			res += i + ". " + personnages.get(i - 1) + "\n";
		}
		return res;
	}

	/**
	 * Crée des personnages à partir d'un fichier de personnages.
	 * @param ligne
	 * @return Personnage
	 */
	public Personnage createPersonnageFromLine(String ligne) {
		String[] tab = ligne.split("\t");
		if (tab[0].equals("Humain")) {
			String nom = tab[1];
			int force = Integer.parseInt(tab[2]);
			int vitesse = Integer.parseInt(tab[3]);
			return new Humain(nom,"Humain", force, vitesse);
		} else if (tab[0].equals("Elf")) {
			String nom = tab[1];
			int force = Integer.parseInt(tab[2]);
			int vitesse = Integer.parseInt(tab[3]);
			return new Elf(nom,"Elf", force, vitesse);
		} else {
			String nom = tab[1];
			int force = Integer.parseInt(tab[2]);
			int vitesse = Integer.parseInt(tab[3]);
			return new Ogre(nom,"Ogre", force, vitesse);
		}

	}
	
	/**
	 * Ajoute dans un fichier la liste des personnages
	 * @param personnages
	 * @param file
	 * @throws Exception
	 */
	public void addToFile(ArrayList<Personnage> personnages, File file) throws Exception {
//		BufferedWriter buffer = new BufferedWriter(new FileWriter(
//				"personnages.txt"));
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
		for (Personnage l : personnages) {
			buffer.write(l.toBase() + "\n");
		}
		buffer.close();
	}

	/**
	 * 
	 * Charge les personnages qui sont enregistrés dans un fichier dans une arraylist.
	 * @param file_name
	 * @throws Exception
	 */
	public void chargerPersonnage(String file_name) throws Exception {

		BufferedReader buff = new BufferedReader(new FileReader(file_name));
		String line = buff.readLine();
		while (line != null) {
			Personnage new_personnage = createPersonnageFromLine(line);
			personnages.add(new_personnage);

			line = buff.readLine();
		}

	}

	/**
	 * Renvoie l'arraylist de personnages
	 * @return ArrayList<Personnage>
	 */
	public ArrayList<Personnage> getPersonnages() {
		return new ArrayList<Personnage>(this.personnages);
	}

}
