package gestionnaire;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
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

	
	public boolean ajouterPersonnage(String nom,String race,  final int pointForce, final int vitesse) {
		return personnages.add(new Personnage(nom, race, pointForce, vitesse) {
			@Override
			public String toBase() {
				return nom + "\t" + race + "\t" + pointForce + "\t" +vitesse + "\t" +vie;
//				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void setImage() {
//				throw new UnsupportedOperationException("Not supported yet.");

			}
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

	public Personnage createPersonnageFromLine(String ligne) {
		String[] tab = ligne.split("\t");
		if (tab[0].equals("Humain")) {
			String nom = tab[1];
			// int vie = Integer.parseInt(tab[2]);
			int force = Integer.parseInt(tab[3]);
			int vitesse = Integer.parseInt(tab[4]);
			return new Humain(nom,"Humain", force, vitesse);
		} else if (tab[0].equals("Elf")) {
			String nom = tab[1];
			// int vie = Integer.parseInt(tab[2]);
			int force = Integer.parseInt(tab[3]);
			int vitesse = Integer.parseInt(tab[4]);
			return new Elf(nom,"Elf", force, vitesse);
		} else {
			String nom = tab[1];
			// int vie = Integer.parseInt(tab[2]);
			int force = Integer.parseInt(tab[2]);
			int vitesse = Integer.parseInt(tab[3]);
			return new Ogre(nom,"Ogre", force, vitesse);
		}

	}

	public ArrayList<Personnage> addPersonnage(ArrayList<Personnage> personnages)
			throws Exception {
		boolean continu = true;
		while (continu) {
			continu = false;
			/*
			 * Personnage ajout = creerPersonnage(); boolean existe = false for
			 * (Personnage personnage : personnages) { if
			 * (ajout.equals(personnage)) { existe = true; } } if (!existe) {
			 * personnages.add(ajout); } else {
			 * System.out.println("Le personnage existe déjà!"); }
			 */
			boolean boucle = true;
			while (boucle) {
				System.out
						.println("voulez vous insérer d'autres personnages (OUI ou NON) ? ");
				InputStream input = System.in;
				BufferedReader buff = new BufferedReader(new InputStreamReader(
						input));
				String suite = buff.readLine();
				if (((suite.equals("OUI") || (suite.equals("NON"))))) {
					boucle = false;
				}
				if (suite.equals("OUI")) {
					continu = true;
				}
			}
		}
		return personnages;
	}

	public void addToFile(ArrayList<Personnage> personnages, File file) throws Exception {
//		BufferedWriter buffer = new BufferedWriter(new FileWriter(
//				"personnages.txt"));
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
		for (Personnage l : personnages) {
			buffer.write(l.toBase() + "\n");
		}
		buffer.close();
	}

	public void chargerPersonnage(String file_name) throws Exception {

		BufferedReader buff = new BufferedReader(new FileReader(file_name));
		String line = buff.readLine();
		while (line != null) {
			Personnage new_personnage = createPersonnageFromLine(line);
			personnages.add(new_personnage);

			line = buff.readLine();
		}

	}

	public ArrayList<Personnage> getPersonnages() {
		return new ArrayList<Personnage>(this.personnages);
	}

}
