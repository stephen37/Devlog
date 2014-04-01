package gestionnaire.run;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class EntreesSorties {

	/**
	 * Renvoie la valeur tapée au clavier.
	 * @return String
	 */
	public static String lireClavier() {
		try {
			BufferedReader clavier =
					new BufferedReader(new InputStreamReader(System.in));
			return clavier.readLine();
		}
		catch (IOException e) {
			return "";
		}
	}

	
	/**
	 * Permet de sauvegarder un Object dans un fichier sans préciser le fichier.
	 * @param contenu
	 */
	public static void sauvegarderFichier (Object contenu) {
		File file = getFile(false);
		save (contenu,  file);
	}
	

	/**
	 * Permet de sauvegarder un Object dans un fichier en précisant le fichier.
	 * @param contenu
	 */
	public static void sauvegarderFichier (Object contenu, File file) {
		save (contenu,  file);
	}


	/**
	 * Charge un fichier
	 * @return Object
	 */
	public static Object chargerFichier () {
		File file = getFile(true);

		return load(file);
	}
	
	
	/**
	 * Charge un fichier.
	 * @param file
	 * @return
	 */
	public static Object chargerFichier (File file) {
		return load(file);
	}

	/**
	 * Permet de sauvegarder un object dans un fichier
	 * @param s
	 * @param file
	 */
	private static void save (Object s, File file) {
		ObjectOutputStream output;
		try {
			System.out.println("Enregistrement du fichier dans : " + file.getAbsolutePath());
			output = new ObjectOutputStream(new FileOutputStream( file ));
			output.writeObject(s);
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * Permet de charger un fichier et de renvoyer l'object chargé.
	 * @param file
	 * @return Object
	 */
	private static Object load (File file) {
		Object res = new Object();
		ObjectInputStream input;
		try {
			input = new ObjectInputStream(new FileInputStream( file ));
			res = input.readObject();
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Permet d'obtenir un fichier
	 * @param existantFile
	 * @return
	 */
	public static File getFile (boolean existantFile) {
		File file ;
		String path;

		if (existantFile){
			do {
				System.out.println("Indiquez le chemin du fichier : ");
				path = lireClavier();
				file = new File(path);
			} while (!file.isFile());
		}
		else {
			do{
				System.out.println("Indiquez le chemin du fichier : ");
				path = lireClavier();
				file = new File(path);
			} while (file.isDirectory());
		}
		return file;
	}
}
