import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JFileChooser;

public class Main {
	public static void main(String args[]) {
		
	}
	
	static public void open(String dir) {
		File arqv;
		BufferedReader ler;
		String linha;
		String[] dados;

		arqv = new File(dir);
		
		
		
		try {
		     
			ler = new BufferedReader(new FileReader(arqv));
			
			linha = ler.readLine();
			dados = linha.split(",");
		}catch(Exception Ex) {
			
		}
			
			
	}
	
	static public void fileselect(Object automato) {
		JFileChooser fc = new JFileChooser();

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

		fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public boolean accept(File f) {
				return (f.getName().endsWith(".bonf")) || f.isDirectory();
			}

			@Override
			public String getDescription() {
				return "*.bonf";
			}
		});
		fc.setDialogTitle("Selecione o arquivo");
		fc.showDialog(fc, "Abrir");
		
		open(fc.getSelectedFile().toString());
	}
}
