package SEPIA;

import java.io.Serializable;


/**
 * 
 * @author Igor Cruz
 * @author Carlos Santos
 * 
 * @version 1
 * @since 0.1
 * 
 * This class is a major class since the start of the project.
 * */
public class n2009108991_PontoInteresse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**Interest Point name */
	private String nome;
	/**Interest point description*/
	private String descricao;

	/**
	 * Class constructor
	 * 
	 * @param nome Interest point name
	 * @param descricao interest point description
	 */
	public n2009108991_PontoInteresse(String nome,String descricao){
		setNome(nome);
		setDescricao(descricao);
	}
	

	/**
	 * This class allows us to get an interest point name
	 * 
	 * @return 		interest point name
	 */
	public String getNome(){
		return nome;
	}
	
	/**
	 * This class allows us to get an interest description
	 * 
	 * @return 		interest point description
	 */
	public String getDescricao(){
		return descricao;
	}
	
	/**
	 * This class allows us to set an interest point name
	 * 
	 * @param nome 		interest point name
	 */
	public void setNome(String nome){
		this.nome=new String (nome);
	}

	/**
	 * This class allows us to set an interest point description
	 * 
	 * @param descricao 		interest point description
	 */
	void setDescricao(String descricao){
		this.descricao=new String (descricao);
	}
	
	/**
	 * This class allows us to get an interest point description
	 * 
	 * @return interest point description
	 */
	public String toString(){
		String buff = new String();
		buff=buff.concat("Nome: "+this.getNome()+"\n");
		buff=buff.concat("Descricao: "+this.getDescricao()+"\n");
		return buff;
	}
}