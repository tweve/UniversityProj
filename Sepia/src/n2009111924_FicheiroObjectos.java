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
public class n2009111924_FicheiroObjectos {
	/**iS input variable, from file.*/
	private ObjectInputStream iS;
	/**oS output variable, to file.*/
	private ObjectOutputStream oS;
	
	/**
	 * Opens a file for reading.
	 * 
	 * @param nomeDoFicheiro filename
	 */
	public void abreLeitura(String nomeDoFicheiro){
		try {
			iS = new ObjectInputStream(new FileInputStream(nomeDoFicheiro));
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
			oS = new ObjectOutputStream(new FileOutputStream(nomeDoFicheiro));
		}
		catch (Exception e){
			System.out.println("Nao foi posssivel abrir o ficheiro para escrita");	
		}
	}
	
	/**
	 * Reads an object from a file.
	 * 
	 * @return			the object read. 
	 */
	public Object leObjecto(){
		
		try{
			return iS.readObject();
		} 
		catch (ClassNotFoundException e){
			System.out.println("O ficheiro nao contem objectos");
		}
		catch (Exception e1){
	
		}
		return null;
	}
	
	/**
	 * Writes an object to a file.
	 * 
	 * @param	o	the object to be written. 
	 */
	public void escreveObjecto(Object o){
		try{
			oS.writeObject(o);
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
			iS.close();
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
			oS.close();
		}
		catch (Exception e){
			System.out.println("Nao foi possivel fechar o ficheiro");		
		}
	}
}