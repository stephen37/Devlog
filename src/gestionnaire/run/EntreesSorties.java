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
import java.util.ArrayList;

public class EntreesSorties {

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

	private static int lireEntier() {
		int n = -1;
		while (n<0){
			try {
				n = Integer.parseInt(lireClavier());
			}
			catch (NumberFormatException e){
				System.out.print("Nombre incorrect. Entrez un nouveau nombre : ");
			}
		}
		return n;
	}

	public static int lireEntier(int min, int max) {
		int n;
		do {
			System.out.print("Entrez un nombre compris entre " + min + " et " + max + " : ");
			n = lireEntier();
		} while (n<min || n>max);
		return n;
	}
	
	public static ArrayList<Integer> lireEntiers(int min, int max) {
		ArrayList<Integer> integers = new ArrayList<Integer>();
		boolean ok = false;
		while (!ok){
			try {
				String [] entiers = lireClavier().split(" ");
				for (String e : entiers) {
					Integer val = Integer.parseInt(e);
					System.out.println(max);
					if (val < min || val > max){
						throw new Exception("Nombres incorrects.");
					}
					integers.add(val);
				}
				ok = true;
			}
			catch (NumberFormatException e){
				integers.clear();
				System.out.print("Nombres incorrects. Entrez les nouveaux nombres : ");

			}
			catch (Exception e) {
				integers.clear();
				System.out.print(e.getMessage() + " Entrez les nouveaux nombres : ");
			}
		}
		return integers;
	}


	public static void sauvegarderFichier (Object contenu) {
		File file = getFile(false);
		save (contenu,  file);
	}
	
	public static void sauvegarderFichier (Object contenu, File file) {

		save (contenu,  file);
	}


	public static Object chargerFichier () {
		File file = getFile(true);

		return load(file);
	}
	
	public static Object chargerFichier (File file) {
		return load(file);
	}

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
