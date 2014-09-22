package SEPIA;



//Trabalho realizado por:
//	Igor Cruz 2009111924
//	Carlos Santos 2009108991



import java.util.*;


/**
 *
 * @author Igor Cruz
 * @author Carlos Santos
 *
 * @version 1
 * @since 0.1
 *
 * Major class that allows communication between user and the system
 *
 * */

public class n2009108991_Sepia{
	/**Static Variable that can be accessed and changed from all methods contains all cities*/
	static ArrayList<n2009108991_Cidade> listaCidades = new ArrayList<n2009108991_Cidade>();
	/**Static Variable that can be accessed and changed from all methods contains all roads*/
	static ArrayList<n2009108991_Estrada> listaEstradas = new ArrayList<n2009108991_Estrada>();
	/**Static Variable that can be accessed and changed from all methods contains all vehicles*/
	static ArrayList<n2009108991_Viatura> listaViaturas = new ArrayList<n2009108991_Viatura>();
	/**Static Variable that can be accessed and changed from all methods contains all routes*/
	static ArrayList<n2009108991_Percurso> listaPercursos = new ArrayList<n2009108991_Percurso>();
	/**Static Variable that can be accessed and changed from all methods contains the Incidence Matrix*/
	static n2009111924_MatrizIncidencia matriz;


	/**
	 * Used to get the position index of a given city in the cities list
	 * @param nome Name of the city
	 * @return 		index of the city, in case of inexistent city returns -1
	 */
	static int getPosCidade(String nome){

		int existe = 0; // nao existe
		int i=0;
		for(;i<listaCidades.size();i++){
			if (listaCidades.get(i).getNome().equals(nome)){// encontra
				existe = 1;
				break;
			}
		}
		if (existe == 1)
			return i;
		else
			return -1;
	}

	/**
	 * Used to get the position index of a given road in the roads list
	 * @param nome Name of the road
	 * @return 		index of the city, in case of inexistent road returns -1
	 */
	static int getPosEstrada(String nome){

		int existe = 0; // nao existe
		int i=0;
		for(;i<listaEstradas.size();i++){
			if (listaEstradas.get(i).getNome().equals(nome)){// encontra
				existe = 1;
				break;
			}
		}
		if (existe == 1)
			return i;
		else
			return -1;
	}

	/**
	 * Used to get the position index of a given vehicle in the vehicles list
	 * @param nome ID of the vehicle
	 * @return 		index of the city, in case of inexistent vehicle returns -1
	 */
	static int getPosViatura(String matricula){

		int existe = 0; // nao existe
		int i=0;
		for(;i<listaViaturas.size();i++){
			if (listaViaturas.get(i).getMatricula().equals(matricula)){// encontra
				existe = 1;
				break;
			}
		}
		if (existe == 1)
			return i;
		else
			return -1;
	}

	/**
	 * Stores information in files
	 */
	static void salvaBD(){

		n2009108991_Cidade cidadeAux;
		n2009108991_Estrada estradaAux;
		n2009108991_Viatura viaturaAux;
		n2009108991_Percurso percursoAux;
		n2009111924_AutoEstrada autoestradaAux;

		n2009111924_FicheiroTexto custosAutoEstrada = new n2009111924_FicheiroTexto();
		n2009111924_FicheiroObjectos cidades = new n2009111924_FicheiroObjectos();
		n2009111924_FicheiroObjectos estradas = new n2009111924_FicheiroObjectos();
		n2009111924_FicheiroObjectos viaturas = new n2009111924_FicheiroObjectos();
		n2009111924_FicheiroObjectos percursosMaisCurtos = new n2009111924_FicheiroObjectos();
		n2009111924_FicheiroObjectos percursosMaisRapidos = new n2009111924_FicheiroObjectos();
		n2009111924_FicheiroObjectos percursosMaisTuristicos = new n2009111924_FicheiroObjectos();

		custosAutoEstrada.abreEscrita("Custos_Auto_Estrada.txt");
		cidades.abreEscrita("BD_Cidades.dat");
		estradas.abreEscrita("BD_Estradas.dat");
		viaturas.abreEscrita("BD_Viaturas.dat");
		percursosMaisCurtos.abreEscrita("percursos_mais_Curtos.dat");
		percursosMaisRapidos.abreEscrita("percursos_mais_Rapidos.dat");
		percursosMaisTuristicos.abreEscrita("percursos_mais_Turisticos.dat");

		// escrever a informaçao nos ficheiros:
		// percorremos as listas e distribuimos a informaçao pelos resectivos ficheiros

		for(int i=0;i< listaCidades.size();i++){
			cidadeAux = listaCidades.get(i);
			cidades.escreveObjecto(cidadeAux);
		}

		for(int i=0;i< listaEstradas.size();i++){
			estradaAux = listaEstradas.get(i);
			estradas.escreveObjecto(estradaAux);
			if (estradaAux.getTipo().equals("autoestrada")){
				autoestradaAux = (n2009111924_AutoEstrada) estradaAux;
				custosAutoEstrada.escreveLinha(autoestradaAux.getNome());
				custosAutoEstrada.escreveLinha(autoestradaAux.getPrecoPortagemA()+" "+autoestradaAux.getPrecoPortagemB());
			}
		}


		for(int i=0;i< listaViaturas.size();i++){
			viaturaAux = listaViaturas.get(i);
			viaturas.escreveObjecto(viaturaAux);
		}

		for(int i=0;i< listaPercursos.size();i++){
			percursoAux = listaPercursos.get(i);
			if (percursoAux.getTipo().equals("curto"))
				percursosMaisCurtos.escreveObjecto(percursoAux);
			else if (percursoAux.getTipo().equals("rapido"))
				percursosMaisRapidos.escreveObjecto(percursoAux);
			else if (percursoAux.getTipo().equals("turistico"))
				percursosMaisTuristicos.escreveObjecto(percursoAux);
		}

		cidades.fechaEscrita();
		estradas.fechaEscrita();
		viaturas.fechaEscrita();
		percursosMaisCurtos.fechaEscrita();
		percursosMaisRapidos.fechaEscrita();
		percursosMaisTuristicos.fechaEscrita();
		custosAutoEstrada.fechaEscrita();

	}

	/**
	 * Loads information from files
	 */
	static void carregaBD(){

		n2009108991_Cidade cidadeAux;
		n2009108991_Estrada estradaAux;
		n2009108991_Viatura viaturaAux;
		n2009108991_Percurso percursoAux;
		n2009111924_AutoEstrada autoestradaAux;

		n2009111924_FicheiroTexto custosAutoEstrada = new n2009111924_FicheiroTexto();
		n2009111924_FicheiroObjectos cidades = new n2009111924_FicheiroObjectos();
		n2009111924_FicheiroObjectos estradas = new n2009111924_FicheiroObjectos();
		n2009111924_FicheiroObjectos viaturas = new n2009111924_FicheiroObjectos();
		n2009111924_FicheiroObjectos percursosMaisCurtos = new n2009111924_FicheiroObjectos();
		n2009111924_FicheiroObjectos percursosMaisRapidos = new n2009111924_FicheiroObjectos();
		n2009111924_FicheiroObjectos percursosMaisTuristicos = new n2009111924_FicheiroObjectos();

		custosAutoEstrada.abreLeitura("Custos_Auto_Estrada.txt");
		cidades.abreLeitura("BD_Cidades.dat");
		estradas.abreLeitura("BD_Estradas.dat");
		viaturas.abreLeitura("BD_Viaturas.dat");
		percursosMaisCurtos.abreLeitura("percursos_mais_Curtos.dat");
		percursosMaisRapidos.abreLeitura("percursos_mais_Rapidos.dat");
		percursosMaisTuristicos.abreLeitura("percursos_mais_Turisticos.dat");

	// carregar a informaçao dos ficheiros:
	// percorremos as listas e distribuimos a informaçao pelas resectivas listas

		cidadeAux = (n2009108991_Cidade )cidades.leObjecto();
		while (cidadeAux != null){
			listaCidades.add(cidadeAux);
			cidadeAux = (n2009108991_Cidade )cidades.leObjecto();
		}

		estradaAux = (n2009108991_Estrada )estradas.leObjecto();
		while (estradaAux != null){


			if (estradaAux.getTipo().equals("autoestrada")){
				String buffer;
				autoestradaAux = (n2009111924_AutoEstrada) estradaAux; //downcasting
				custosAutoEstrada.leLinha();// saltamos a linha com o nome
				buffer =custosAutoEstrada.leLinha();// saltamos a linha com o nome
				StringTokenizer st = new StringTokenizer(buffer);

				autoestradaAux.setPrecoPortagemA(Double.parseDouble(st.nextToken()));
				autoestradaAux.setPrecoPortagemB(Double.parseDouble(st.nextToken()));

				listaEstradas.add(autoestradaAux);

			}
			else
				listaEstradas.add(estradaAux);

			estradaAux = (n2009108991_Estrada )estradas.leObjecto();
		}

		viaturaAux = (n2009108991_Viatura )viaturas.leObjecto();
		while (viaturaAux != null){
			listaViaturas.add(viaturaAux);
			viaturaAux = (n2009108991_Viatura )viaturas.leObjecto();
		}

		percursoAux = (n2009108991_Percurso )percursosMaisRapidos.leObjecto();
		while (percursoAux != null){
			listaPercursos.add(percursoAux);
			percursoAux = (n2009108991_Percurso )percursosMaisRapidos.leObjecto();
		}

		percursoAux = (n2009108991_Percurso )percursosMaisCurtos.leObjecto();
		while (percursoAux != null){
			listaPercursos.add(percursoAux);
			percursoAux = (n2009108991_Percurso )percursosMaisCurtos.leObjecto();
		}

		percursoAux = (n2009108991_Percurso )percursosMaisTuristicos.leObjecto();
		while (percursoAux != null){
			listaPercursos.add(percursoAux);
			percursoAux = (n2009108991_Percurso )percursosMaisTuristicos.leObjecto();
		}

	cidades.fechaLeitura();
	estradas.fechaLeitura();
	viaturas.fechaLeitura();
	percursosMaisCurtos.fechaLeitura();
	percursosMaisRapidos.fechaLeitura();
	percursosMaisTuristicos.fechaLeitura();
    custosAutoEstrada.fechaLeitura();

	}
	/**
	 * Class run when the program executes
	 */
	public static void main(String [ ] args){

		Scanner teclado = new Scanner(System.in);
		int opcao = 1;
		double precoGasolina =1.5;
		double precoGasoleo  =1.3;
		carregaBD();


		while (opcao != 0) {

			System.out.println("");
			System.out.println("Selecione a sua opcao:");
			System.out.println(" 1 - Cidades");
			System.out.println(" 2 - Estradas");
			System.out.println(" 3 - Viaturas");
			System.out.println(" 4 - Percursos");
			//System.out.println(" 5 - Mostrar Matriz incidencia"); - inexistente
			//System.out.println(" 6 - Carrega info"); - inexistente agr os dados sao carregados no inicio do programa
			//System.out.println(" 7 - Salva info"); - inexistente agr os dados sao salvos no fim da execuçao
			System.out.println(" 5 - Preco Gasolina");
			System.out.println(" 6 - Preco Gasoleo");

			System.out.println(" 0 - Sair do Sistema ");
			System.out.println("Opcao:");

			opcao = teclado.nextInt();
			switch (opcao){

				case 0:
					salvaBD();
					break;
				case 1:
					Scanner sc2 = new Scanner(System.in);
					System.out.println("1 -> Listar Cidades ");
					System.out.println("2 -> Adicionar Cidade ");
					System.out.println("3 -> Eliminar Cidade ");
					System.out.println("0 -> Menu Principal ");
					int subopcao = sc2.nextInt();

					if (subopcao == 1){
						if (listaCidades.size() == 0)
							System.out.print("Nao existem cidades na base de dados.\n");
						else{
							n2009108991_Cidade aux;
							for (int i =0;i<listaCidades.size();i++){
								aux = listaCidades.get(i);
								System.out.print(aux.toString());
							}
						}

					}
					if (subopcao == 2){

						System.out.println("Por favor preecha os seguintes dados:");
						System.out.println("Nome da cidade: ");
						sc2=new Scanner(System.in);
						String nome=sc2.next();
						if (getPosCidade(nome) != -1){
							System.out.println("Operacao Abortada, O sistema nao permite duas cidades com o mesmo nome.");
							break;
						}

						n2009108991_Cidade temp= new n2009108991_Cidade(nome);
						System.out.println("Cidade Criada com sucesso");

						System.out.print("Deseja associar Pontos de Interesse (y/n)?: ");
						String continua= sc2.next();

						if (continua.equals("y")){
							String descricao;
							n2009108991_PontoInteresse PI;

							System.out.print("Quantos?: ");
							int n=sc2.nextInt();
							for (int i=0;i<n;i++){

								System.out.println("Ponto de Interesse "+(i+1));
								System.out.println("Nome: " );
								sc2 = new Scanner(System.in);
								nome = sc2.nextLine();
								System.out.println("Descricao: ");
								descricao= sc2.nextLine();

								PI = new n2009108991_PontoInteresse(nome,descricao);
								temp.addPontoInteresse(PI);

							}
						}

						listaCidades.add(temp);
						System.out.println("Cidade adicionada com sucesso");
				    }
					else if (subopcao == 3){

						System.out.println("Nome da cidade: ");
						sc2=new Scanner(System.in);
						String nome=sc2.next();
						n2009108991_Cidade temp= new n2009108991_Cidade(nome);
						short existe = 0;
						for (int i=0;i<listaCidades.size();i++){
							temp= listaCidades.get(i);
							if (temp.getNome().equals(nome)){
								existe = 1 ;
								System.out.print(temp.toString());
								System.out.println("Esta cidade sera apagada, deseja continuar <y/n> ? ");
								String continua= sc2.next();

								if (continua.equals("y")){
									listaCidades.remove(temp);
									
									System.out.println("Cidade removida com sucesso. ");

								}
								else
									System.out.println("Cancelado pelo utilizador. ");
							}
						}
						if (existe == 0)
							System.out.println("Cidade inexistente. ");
					}
					break;

				case 2:
					sc2 = new Scanner(System.in);
					System.out.println("1 -> Listar Estradas ");
					System.out.println("2 -> Adicionar Estrada ");
					System.out.println("3 -> Eliminar Estrada ");
					System.out.println("0 -> Menu Principal ");
					subopcao = sc2.nextInt();
					if (subopcao == 1){
						if (listaEstradas.size() == 0)
							System.out.print("Nao existem estradas na base de dados.\n");
						else{
							n2009108991_Estrada aux;
							for (int i =0;i<listaEstradas.size();i++){
								aux = listaEstradas.get(i);
								System.out.print(aux.toString()+"\n");
							}
						}
					}
					else if (subopcao == 2){


						String origem;
						String destino;

						n2009108991_Cidade A;
						n2009108991_Cidade B;

						int posA;
						int posB;

						String nome;

						int distancia;

						String tipo;



						System.out.println("Por favor preecha os seguintes dados:");
						sc2 = new Scanner(System.in);
						System.out.print("Origem: ");
						origem = sc2.next();

						posA = getPosCidade(origem);
						if (posA==-1){
							System.out.print("Cidade Inexistente, por favor insira primeiro a cidade.\n");
							break;
						}
						else{
							A = listaCidades.get(posA);
							System.out.print("Cidade localizada na base de dados.\n");

						}
						System.out.print("Destino: ");
						destino = sc2.next();

						posB = getPosCidade(destino);
						if (posB==-1){
							System.out.print("Cidade Inexistente, por favor insira primeiro a cidade.\n");
							break;
						}
						else{
							B = listaCidades.get(posB);
							System.out.print("Cidade localizada na base de dados.\n");

						}


						System.out.print("Nome: ");
						nome = sc2.next();

						if (getPosEstrada(nome) != -1){
							System.out.println("Operacao Abortada, O sistema nao permite duas estradas com o mesmo nome.");
							break;
						}

						System.out.print("Distancia: ");
						distancia = sc2.nextInt();

						System.out.print("Tipo <nacional/autoestrada>: ");
						tipo = sc2.next();


						if (tipo.equals("autoestrada")){
							double precoPortagemA;
							double precoPortagemB;
							System.out.print("Preco Portagem Veiculo Class A: ");
							precoPortagemA = sc2.nextDouble();
							System.out.print("Preco Portagem Veiculo Class B: ");
							precoPortagemB = sc2.nextDouble();
							n2009111924_AutoEstrada auto_estrada = new n2009111924_AutoEstrada( A, B ,nome ,distancia,precoPortagemA,precoPortagemB);
							listaEstradas.add(auto_estrada);
							System.out.println("Estrada adicionada com sucesso");
						}
						else {
							int estadoconservacao;
							System.out.print("Estado Conservacao (0-Verde/1-Amarelo/2-Vermelho): ");
							estadoconservacao = sc2.nextInt();
							n2009111924_EstradaNacional estrada_nacional = new n2009111924_EstradaNacional(A,B,nome,distancia,estadoconservacao);

							// associaçao de pontos de interesse
							System.out.print("Deseja associar Pontos de Interesse (y/n)?: ");
							String continua= sc2.next();

							if (continua.equals("y")){
								String descricao;
								n2009108991_PontoInteresse PI;

								System.out.print("Quantos?: ");
								int n=sc2.nextInt();
								for (int i=0;i<n;i++){

									System.out.println("Ponto de Interesse "+(i+1));
									System.out.println("Nome: " );
									sc2 = new Scanner(System.in);
									nome = sc2.nextLine();
									System.out.println("Descricao: ");
									descricao= sc2.nextLine();

									PI = new n2009108991_PontoInteresse(nome,descricao);
									estrada_nacional.addPontoInteresse(PI);

								}
							}
							listaEstradas.add(estrada_nacional);
							System.out.println("Estrada adicionada com sucesso");

						}



					}
					else if (subopcao == 3){

						System.out.println("Nome da estrada: ");
						sc2=new Scanner(System.in);
						String nome=sc2.next();
						n2009108991_Estrada temp;
						short existe = 0;
						for (int i=0;i<listaEstradas.size();i++){
							temp= listaEstradas.get(i);
							if (temp.getNome().equals(nome)){
								existe = 1 ;
								System.out.print(temp.toString());
								System.out.println("Esta estrada sera apagada, deseja continuar <y/n> ? ");
								String continua= sc2.next();

								if (continua.equals("y")){
									listaEstradas.remove(temp);
									System.out.println("Estrada removida com sucesso. ");

								}
								else
									System.out.println("Cancelado pelo utilizador. ");
							}
						}
						if (existe == 0)
							System.out.println("Estrada inexistente. ");
					}
					break;
				case 3:
					sc2 = new Scanner(System.in);
					System.out.println("1 -> Listar Viaturas ");
					System.out.println("2 -> Adicionar Viatura ");
					System.out.println("3 -> Eliminar Viatura ");
					System.out.println("0 -> Menu Principal");
					subopcao = sc2.nextInt();
					if (subopcao == 1){
						if (listaViaturas.size() == 0)
							System.out.print("Nao existem viaturas na base de dados.\n");
						else{
							n2009108991_Viatura aux;
							for (int i =0;i<listaViaturas.size();i++){
								aux = listaViaturas.get(i);
								System.out.print(aux.toString()+"\n");
							}
						}
					}
					else if (subopcao == 2){

						String matricula;
						String classe;
						String tipoCombustivel;
						double consumo;
						System.out.println("Por favor preecha os seguintes dados:");
						sc2 = new Scanner(System.in);
						System.out.print("Matricula: ex:<11-11-AA> ");
						matricula = sc2.next();
						System.out.print("Classe: <A/B>");
						classe = sc2.next();
						System.out.print("Combustivel: <Gasoleo/Gasolina>");
						tipoCombustivel = sc2.next();
						System.out.print("Consumo (100km): ");
						consumo = sc2.nextDouble();
						n2009108991_Viatura viatura_temp= new n2009108991_Viatura(classe,matricula,tipoCombustivel,consumo);

						listaViaturas.add(viatura_temp);

					}
					else if (subopcao == 3){
						String matricula;
						System.out.print("Matricula: ");
						sc2 = new Scanner(System.in);
						matricula = sc2.next();
						n2009108991_Viatura aux;
						short existe = 0;

						for (int i =0;i<listaViaturas.size();i++){
							aux = listaViaturas.get(i);
							if (aux.getMatricula().equals(matricula)){
								existe = 1;
								listaViaturas.remove(i);
								System.out.println("\nA viatura foi removida da base de dados.");
								break;
							}
						}
						if (existe==0)
							System.out.println("\nEssa viatura nao existe!");
					}
					break;

				case 4:
					sc2 = new Scanner(System.in);
					System.out.println("1 -> Percursos Recentes");
					System.out.println("2 -> Calcular Percurso ");
					System.out.println("3 -> Eliminar Percurso ");
					System.out.println("0 -> Menu Principal");
					subopcao = sc2.nextInt();
					if (subopcao == 1){
						if (listaPercursos.size()==0)
							System.out.println("Nao existem percursos calculados.");
						else{
							n2009108991_Percurso aux;
							for(int i =0;i<listaPercursos.size();i++){
								aux = listaPercursos.get(i);
								System.out.println(i+ "- Origem:"+aux.getOrigem().getNome()+ " Destino:"+aux.getDestino().getNome() + " Tipo:"+aux.getTipo());
							}
							System.out.println("Opcao: ");

							short escolha = sc2.nextShort();
							System.out.print(listaPercursos.get(escolha).toString()); //imprime o percurso seleccionado
						}
					}
					else if (subopcao==2){

						int posA;
						int posB;
						int posViatura;
						String matricula;
						n2009108991_Cidade A;
						n2009108991_Cidade B;
						n2009108991_Viatura viatura;
						String origem,destino;
						String tipo;

						n2009108991_Percurso percurso;

						System.out.println("Por favor preencha os seguintes campos");
						System.out.println("Origem: ");
						sc2 = new Scanner(System.in);

						origem = sc2.next();

						posA = getPosCidade(origem);
						if (posA==-1){
							System.out.print("Cidade Inexistente, por favor insira primeiro a cidade.\n");
							break;
						}
						else{
							A = listaCidades.get(posA);
							System.out.print("Cidade localizada na base de dados.\n");

						}

						System.out.println("Destino: ");


						destino = sc2.next();

						posB = getPosCidade(destino);
						if (posB==-1){
							System.out.print("Cidade Inexistente, por favor insira primeiro a cidade.\n");
							break;
						}
						else{
							B = listaCidades.get(posB);
							System.out.print("Cidade localizada na base de dados.\n");
						}


						System.out.println("Viatura (matricula) <ex:11-11-AA>: ");
						matricula = sc2.next();

						posViatura = getPosViatura(matricula);
						if (posViatura==-1){
							System.out.print("Viatura Inexistente, por favor insira primeiro a Viatura.\n");
							break;
						}
						else{
							viatura = listaViaturas.get(posViatura);
							System.out.print("Viatura localizada na base de dados.\n");
						}


						System.out.println("Tipo <curto/rapido/turistico>: ");
						tipo = sc2.next();
						percurso = new n2009108991_Percurso(A,B,viatura,tipo);

						matriz = new n2009111924_MatrizIncidencia(listaCidades,listaEstradas,listaCidades.size(),listaCidades.size(),viatura);

						percurso.calcula(matriz,posA,posB,listaCidades.size(),precoGasolina,precoGasoleo);


						System.out.print(percurso.toString());

						listaPercursos.add(percurso);


					}
					else if (subopcao==3){
						if (listaPercursos.size()==0)
							System.out.println("Nao existem percursos calculados.");
						else{
							n2009108991_Percurso aux;
							for(int i =0;i<listaPercursos.size();i++){
								aux = listaPercursos.get(i);
								System.out.println(i+ " Origem: "+aux.getOrigem().getNome()+ " Destino: "+aux.getDestino().getNome() + "Tipo: "+aux.getTipo());
							}
							short escolha = sc2.nextShort();
							String continuar;
							System.out.print("O percurso "+escolha+" sera eliminado, deseja continuar <y/n>?");
							continuar = sc2.next();
							if (continuar.equals("y")){
								listaPercursos.remove(escolha);
								System.out.print("O percurso "+escolha+" foi removido com sucesso.");
							}
							else
								System.out.print("Cancelado pelo utilizador");
						}

					}

					break;
				case 5:
					System.out.print("Preco Gasolina: ");
					sc2 = new Scanner(System.in);
					precoGasolina = sc2.nextDouble();
					break;
				case 6:
					System.out.print("Preco Gasoleo: ");
					sc2 = new Scanner(System.in);
					precoGasoleo = sc2.nextDouble();
					break;
				}

		}


		}
	}
