package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Principal extends Application{
	
	protected static Automata automata = Automata.getInstance();
	public Stage window;
	public Scene scene1;
	public static Proba p = new Proba();
	
	public static void main(String args[]) {
		
		//fileselect(); //sele��o do arquivo
		
		launch(args);
		
		//System.out.println(automata.regras.get(1).get(0).size());

	}
	static public boolean open(String dir) {
		Character tipo = dir.charAt(dir.length()-1);
		if (tipo.equals('f')) {
			return stackOpen(dir);
		}
		else if(tipo.equals('v')) {
			return normalOpen(dir);
		}
		return false;
		
	}
	static public boolean stackOpen(String dir) {
		automata.setComPilha(true);
		File arqv;
		BufferedReader ler;
		String linha;
		String[] dados;
		
		 // Cria arquivo
		String type = "dot";
		File out = new File("..\\Apptomata\\grafoPgraphviz." + type); 

		arqv = new File(dir);
		
		try {
			  // Se o arquivo nao existir, ele gera
	        if (!out.exists()) {
	            out.createNewFile();
	        }
	        
	     // Prepara buffer e  escreve no arquivo
	        FileWriter fw = new FileWriter(out.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	         
			ler = new BufferedReader(new FileReader(arqv));
			
			linha = ler.readLine();
			
			//le estados
			automata.zera();
			dados = linha.split(",");
			automata.finalInicial(dados);
			automata.alocaEstados(dados);
			
			//le o alfabeto
			linha = ler.readLine();
			automata.alocaSimbolos(linha);
		} catch (IOException ex) {
			System.out.print(ex);
			return false;
		}
		return true;
	}
	static public boolean normalOpen(String dir) {
		File arqv;
		BufferedReader ler;
		String linha;
		String[] dados;
		
		 // Cria arquivo
		String type = "dot";
		File out = new File("..\\Apptomata\\grafoPgraphviz." + type); 

		arqv = new File(dir);
		
		try {
			  // Se o arquivo nao existir, ele gera
	        if (!out.exists()) {
	            out.createNewFile();
	        }
	        
	     // Prepara buffer e  escreve no arquivo
	        FileWriter fw = new FileWriter(out.getAbsoluteFile());
	        BufferedWriter bw = new BufferedWriter(fw);
	         
			ler = new BufferedReader(new FileReader(arqv));
			
			linha = ler.readLine();
			
			//le estados
			automata.zera();
			dados = linha.split(",");
			automata.finalInicial(dados);
			automata.alocaEstados(dados);
			
			//le o alfabeto
			linha = ler.readLine();
			automata.alocaSimbolos(linha);
		
			bw.write("digraph a{");
			while (linha != null) {
				linha = ler.readLine();
				if (linha == null) {
					bw.append("}"); 
			    	bw.close();
			    	p.start2();
					return false;
				}				
				
				dados = linha.split(",");
				
				automata.novaRegra(dados[0], dados[1], dados[2]);
				
				
				String from = ""+dados[0];
				String to = ""+ dados[2];
				
				if (automata.eFinal(dados[0]))
					from="| "+from+" |";
				if (automata.inicial(dados[0]))
					from = ">"+from+"";
				if (automata.eFinal(dados[2]))
					to= "| "+to+" |";
				if (automata.inicial(dados[2]))
					to= ">" + to+"";
				from = "\"" + from + "\"";
				to = "\"" + to + "\"";
				bw.append(from + " -> " + to + "[label=<" + dados[1] + ">]\n");
				
			}
			bw.close();
			ler.close();
			
		} catch (IOException ex) {
			System.out.print(ex);
			return false;
		}
		
		return true;
	}

	static public void fileselect() {
		JFileChooser fc = new JFileChooser();

		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		/*
		fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
			@Override
			public boolean accept(File f) {
				return (f.getName().endsWith(".elv")) || f.isDirectory();
			}

			@Override
			public String getDescription() {
				return "*.elv";
			}
		});*/
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Automata files","bonf", "elv"));
		//fc.addChoosableFileFilter(new FileNameExtensionFilter("*.elv"));
		fc.setDialogTitle("Selecione o arquivo");
		fc.showDialog(fc, "Abrir");
		open(fc.getSelectedFile().toString());
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("TelaPrincipal.fxml"));
		primaryStage.setTitle("GUI - TP1/TP2");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();  		
	}
	
}
