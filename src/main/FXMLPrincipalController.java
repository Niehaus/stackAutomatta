package main;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FXMLPrincipalController extends Principal implements Initializable {
 
  @FXML
	private CheckBox afd;
	
  @FXML
    private CheckBox afn;

  @FXML
  	private Button fileopen;
  
  @FXML
  	private Label lab;
  
  @FXML
  private TextField entraPalavra;
 
  @FXML
  private Button testaPalavra;
  
  @FXML
  private Label automTipo;
  
  @FXML
  private ImageView automato;
  
  @FXML
	  private void acaoDoBotao(ActionEvent event) throws FileNotFoundException {
		  System.out.println("Voce clicou no select..");
		  Principal.fileselect();
		  if(automata.isComPilha()) {
	  			automTipo.setText("com Pilha ");
	  		}
	  		else {
	  			automTipo.setText(" sem Pilha");
	  		}
		  FileInputStream input = new FileInputStream("..\\Apptomata\\grafoGerado.png");
		  Image image = new Image(input);
		  automato.setImage(image);
	  }
  @FXML
	private void testaPalavra(ActionEvent event) {
		 System.out.println(entraPalavra.getText());
		  
		  //Principal.automata.aceita("ab");
		
		  if(Principal.automata.aceita(entraPalavra.getText())) {
			  lab.setText("Palavra Aceita!");  
		  }else {
			  lab.setText("Palavra N�o Aceita!");
		  }
		  entraPalavra.setText("");
	 }
  
  
  	@FXML
  	private void checkBox(ActionEvent event) {
  		//System.out.println("SELECIONEI" + afd.isSelected() + afn.isSelected());
  		
  	}
  	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//TODO
	}
}
