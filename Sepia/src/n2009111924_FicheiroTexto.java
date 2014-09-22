package SEPIA;

import java.io.*;


/**
 * 
 * @author Doutor António José Mendes
 * 
 * @version 1
 * @since 0.2
 * 
 * Support class for Object files.
 * class given by the teacher.
 * 
 * */
public class n2009111924_FicheiroTexto {

	/**tin input variable, from file.*/
	private BufferedReader tin;
	/**tout output variable, to file.*/
	private BufferedWriter tout;
	
	/**
	 * Opens a file for reading.
	 * 
	 * @param nomeDoFicheiro filename
	 */
	public void abreLeitura(String nomeDoFicheiro){
		try {
			tin = new BufferedReader(new FileReader(nomeDoFicheiro));
		}			
		catch (Exception e){
			System.out.println("Nao foi posssivel abrir o ficheiro para leitura");	
		}
	}

	/**
	 * Opens a file for writing.
	 * 
	 * @param nomeDoFicheiro filename
	 */
	public void abreEscrita(String nomeDoFicheiro){
		try {
			tout = new BufferedWriter(new FileWriter(nomeDoFicheiro));
		}
		catch (Exception e){
			System.out.println("Nao foi posssivel abrir o ficheiro para escrita");	
		}
	}
	
	/**
	 * Reads a Line from a file.
	 * 
	 * @return	the line read. 
	 */
	public String leLinha(){
		try{
			return tin.readLine();
		} 
		catch (Exception e1){

		}
		return null;
	}
	
	/**
	 * Writes a Line to a file.
	 * 
	 * @param	linha	the line to be written. 
	 */
	public void escreveLinha(String linha){
		try{
			tout.write(linha,0,linha.length());
			tout.newLine();
		}
		catch (Exception e){
			System.out.println("Nao foi possivel escrever no ficheiro");
		}
	}
	
	/**
	 * Closes a file which was set to be read.
	 * 
	 */
	public void fechaLeitura(){
		try{
			tin.close();
		}
		catch (Exception e){
			System.out.println("Nao foi possivel fechar o ficheiro");		
		}
	}
	
	/**
	 * Closes a file which was set to be written.
	 * 
	 */
	public void fechaEscrita(){
		try{
			tout.close();
		}
		catch (Exception e){
				System.out.println("Nao foi possivel fechar o ficheiro");		
			}
		}
	
}
