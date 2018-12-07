package main;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

//Singleton
class StackTransition{
	char desempilha; 
	String empilha; 
	int destino;
	
	protected StackTransition(char desempilha, String empilha, int destino) {
		this.desempilha = desempilha;
		this.empilha = empilha;
		this.destino = destino;
	}
	
	public char getLeitura() {
		return desempilha;
	}
	public void setLeitura(char desempilha) {
		this.desempilha = desempilha;
	}
	public String getEmpilha() {
		return empilha;
	}
	public void setEmpilha(String empilha) {
		this.empilha = empilha;
	}
	public int getDestino() {
		return destino;
	}
	public void setDestino(int destino) {
		this.destino = destino;
	}
	
	public String toString() {
		return desempilha + ", " + empilha + "),q"+destino+"\n";
	}
	
}

public class Automata {
	private int inicial;
	private List<Integer> finais;
	private static Automata instance = new Automata();
	private String alfabeto;
	private List<String> states;
	private List<List<List<Integer>>> regras;
	private List<List<List<StackTransition>>> stackRegras;
	private Deque<Integer> stack = new ArrayDeque<Integer>();
	private boolean comPilha = false;
	//[estados][simbolos][dest]
	
	private Automata() {
		inicial = 1;
		finais = new ArrayList<Integer>();
		regras = new ArrayList<List<List<Integer>>>();
		alfabeto = "E";
		states = new ArrayList<String>();
	}
	
	public void zera() {
		inicial = 0;
		finais.clear();
		regras.clear();
		alfabeto = "";
		states.clear();
		inicial = 1;
		finais = new ArrayList<Integer>();
		regras = new ArrayList<List<List<Integer>>>();
		stackRegras = new ArrayList<List<List<StackTransition>>>();
		alfabeto = "E";
		states = new ArrayList<String>();
	}
	
	
	public static Automata getInstance() {
		return instance;
	}
	
	public void finalInicial(String dados[]) {
		int cont = 0;
		for (String s: dados) {
			if (s.contains(">")) {
				inicial = cont;
			}
			if (s.contains("*")) {
				finais.add(cont);
			}
			cont++;
		}
	}
	
	public void alocaEstados(String dados[]) {
		for (String a : dados) {
			a= a.replace("*", "");
			a= a.replace(">", "");
			states.add(a);
		}
		if (isComPilha()) {
			for (int i = 0; i < dados.length; i++) {
				stackRegras.add(new ArrayList<List<StackTransition>>());
			}
		}else {
			for (int i = 0; i < dados.length; i++) {
				regras.add(new ArrayList<List<Integer>>());
			}
		}
	}
	
	public void alocaSimbolos(String alfabeto) {
		
		this.alfabeto = this.alfabeto + alfabeto;
		if (isComPilha()) {
			this.alfabeto = this.alfabeto + "?";
			for (List<List<StackTransition>> estado : stackRegras) 
				for (int i = 0; i < this.alfabeto.length(); i++) 
					estado.add(new ArrayList<StackTransition>());
			
		}else {
			for (List<List<Integer>> estado : regras) 
				for (int i = 0; i < this.alfabeto.length(); i++) 
					estado.add(new ArrayList<Integer>());
		}
	}
	
	public void novaRegra(String from, String letter, String to) {
		int iFrom = states.indexOf(from);
		int iLetter = alfabeto.indexOf(letter);
		int iTo = states.indexOf(to);
		
		regras.get(iFrom).get(iLetter).add(iTo);
	}
	
	public String toString() {
		String retorno="";
		int i = 0, j=0;
		if (isComPilha()) {
			for (i = 0; i < stackRegras.size(); i++) {
				for (j = 0; j < stackRegras.get(i).size(); j++)
					for (int k =0; k < stackRegras.get(i).get(j).size(); k++) {
						retorno += "q" + i +",(" + alfabeto.charAt(j) +","+
								stackRegras.get(i).get(j).get(k).getLeitura() +","+
								stackRegras.get(i).get(j).get(k).getEmpilha() + "),"+
								stackRegras.get(i).get(j).get(k).getDestino()+"\n";
					}
			}
			/*retorno = "";
			
			for (List<List<StackTransition>> from: stackRegras) {
				j = 0;
				for (List<StackTransition> letter:from) {
					for (StackTransition stackRegra:letter) {
						if(inicial == i)
							retorno += ">";
						if (finais.contains(i))
							retorno+="*";
						retorno += "q"+ i+",(" + alfabeto.charAt(j)+",";
						retorno+= stackRegra.toString() + "\n";
					}
					j++;
				}
				i++;
			}*/
		}else {
			retorno = "   ";
			for (i = 0; i < alfabeto.length(); i++) {
				char a = alfabeto.charAt(i);
				retorno += "|    "+a+"    ";
			}
			i = 0;
			retorno += "\n";
			
			for (List<List<Integer>> from:regras) {
				if(inicial == i)
					retorno += ">";
				if (finais.contains(i))
					retorno+="*";
				
				retorno += "q"+ i+"  ";
				
				for (List<Integer> letter:from) {
					retorno +="{";
					for (Integer to:letter) {
						retorno+="q"+ to;
					}
					retorno+="}  ";
				}
				retorno+="\n";
				i++;
			}
		}
		
		return retorno;
	}
	
	public boolean aceita(String palavra) {
		try {
			if (isComPilha()) 
				return stackAceita(0,palavra,0);
			else 
				return aceita(0,palavra,0);
		}catch(Exception ex) {
			return false;
		}
	}
	
	private boolean aceita(int atual, String palavra, int pos) throws Exception{
		boolean retorno = false;
		int aux;
		
		
		if(finais.contains(atual) && palavra.length() == pos) {	//caso seja o estado seja final 
			return true;										//e tenha chegado no final da palavra
		} else if(palavra.length() == pos) {					//caso tenha chegado no final da palavra mas 
			return false;										//o estado não é final
		}
		
		
		char letraAtual= palavra.charAt(pos);
		int codLetra = alfabeto.indexOf(letraAtual);
		int nEstAlcancaveis = regras.get(atual).get(codLetra).size();
							//a partir do estado atual, lendo a letra atual consegue-se ir pra quais estados?
		
		
		for(int i = 0; i < regras.get(atual).get(0).size(); i++) {
			aux = regras.get(atual).get(0).get(i);
			retorno = aceita(aux,palavra,pos);
			if (retorno == true)
				return retorno;
		}
		
		//itera entre os estados disponiveis para a letra palavra[pos]
		for(int i = 0; i < nEstAlcancaveis; i++) {
			
			aux = regras.get(atual).get(codLetra).get(i);
			retorno = aceita(aux,palavra,pos + 1);
			if(retorno == true) {
				break;
			}
		}	
		return retorno;
	}
	
	public boolean stackAceita(int atual, String palavra, int pos) throws Exception{
		boolean retorno = false;
		int aux;
		
		
		if(finais.contains(atual) && palavra.length() == pos) {	//caso seja o estado seja final 
			return true;										//e tenha chegado no final da palavra
		} else if(palavra.length() == pos) {					//caso tenha chegado no final da palavra mas 
			return false;										//o estado não é final
		}
		
		
		char letraAtual= palavra.charAt(pos);
		int codLetra = alfabeto.indexOf(letraAtual);
		int nEstAlcancaveis = regras.get(atual).get(codLetra).size();
							//a partir do estado atual, lendo a letra atual consegue-se ir pra quais estados?
		
		
		for(int i = 0; i < regras.get(atual).get(0).size(); i++) {	//le nada da string
			
			aux = regras.get(atual).get(0).get(i);
			retorno = aceita(aux,palavra,pos);
			if (retorno == true)
				return retorno;
		}
		
		//itera entre os estados disponiveis para a letra palavra[pos]
		for(int i = 0; i < nEstAlcancaveis; i++) {
			
			aux = regras.get(atual).get(codLetra).get(i);
			retorno = aceita(aux,palavra,pos + 1);
			if(retorno == true) {
				break;
			}
		}	
		return retorno;
	}
	
	public boolean inicial(String estado) {
		String dado = "" + estado;
		if(states.contains(dado))
			return (inicial == states.indexOf(dado));
		return false;
	}
	
	public boolean eFinal(String estado) {
		String dado = "" + estado;
		
		if (states.contains(dado))
			return finais.contains(states.indexOf(dado));
		return false;
	}
	
	public boolean isComPilha() {
		return comPilha;
	}

	public void setComPilha(boolean comPilha) {
		this.comPilha = comPilha;
	}
	
	public void novaStackRegra(String from, 
								char letter, char desempilha, String empilha, 
								String to) {
		
		int iFrom = states.indexOf(from);
		int iLetter = alfabeto.indexOf(letter);
		int iTo = states.indexOf(to);
		
		stackRegras.get(iFrom).get(iLetter).add(new StackTransition(desempilha, empilha, iTo));
	}


	
}
