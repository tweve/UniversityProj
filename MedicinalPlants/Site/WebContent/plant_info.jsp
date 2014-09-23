<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.hp.hpl.jena.rdf.model.Model,com.hp.hpl.jena.rdf.model.ModelFactory,com.hp.hpl.jena.util.FileManager,java.io.InputStream,com.hp.hpl.jena.query.Query,com.hp.hpl.jena.query.QueryExecution,com.hp.hpl.jena.query.ResultSetFormatter,com.hp.hpl.jena.query.QueryFactory,com.hp.hpl.jena.query.QueryExecutionFactory,com.hp.hpl.jena.query.QuerySolution,java.util.*,com.hp.hpl.jena.rdf.model.RDFNode"%>

<%
	String inputFileName = "populated_model.rdf";
	// create an empty model
	Model model = ModelFactory.createDefaultModel();

	// use the FileManager to find the input file
	InputStream in = FileManager.get().open(inputFileName);
	if (in == null) {
		throw new IllegalArgumentException("File: " + inputFileName
				+ " not found");
	}

	model.read(in, null);
	String p = request.getParameter("p");
	p = p.replace("http://www.owl-ontologies.com/Plantas.owl#", "");
	////////////////////////////////////////////////////////////////////////////////////////////////////
	String family = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

			+ "SELECT ?familylabel ?genderlabel ?specieslabel WHERE {medicinal_plants:"
			+ p
			+ " medicinal_plants:hasClassification ?class ."
			+ "?class a medicinal_plants:Family ."
			+ "?class rdfs:label ?familylabel  ." + "}";
	////////////////////////////////////////////////////////////////////////////////////////////////////
	String genre = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

			+ "SELECT ?genrelabel WHERE {medicinal_plants:"
			+ p
			+ " medicinal_plants:hasClassification ?class ."
			+ "?class a medicinal_plants:Genre ."
			+ "?class rdfs:label ?genrelabel  ." + "}";
	////////////////////////////////////////////////////////////////////////////////////////////////////
	String species = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

			+ "SELECT ?specieslabel WHERE {medicinal_plants:"
			+ p
			+ " medicinal_plants:hasClassification ?class ."
			+ "?class a medicinal_plants:Species ."
			+ "?class rdfs:label ?specieslabel  ." + "}";
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	String description = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

			+ "SELECT ?desc WHERE {medicinal_plants:"
			+ p
			+ " medicinal_plants:hasDescription ?desc ." + "}";
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	String diseases = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

			+ "SELECT ?diseases WHERE {medicinal_plants:"
			+ p
			+ " medicinal_plants:hasTreatmentFor ?class ."
			//+ "?class a medicinal_plants:Disease ."
			+ "?class rdfs:label ?diseases  ." + "}";
	//+ " medicinal_plants:hasTreatmentFor ?class .}";
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	String ediblePart = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

			+ "SELECT ?ediblePart WHERE {medicinal_plants:"
			+ p
			+ " medicinal_plants:hasEdiblePart ?class ."
			//+ "?class a medicinal_plants:Disease ."
			+ "?class rdfs:label ?ediblePart  ." + "}";
	//+ " medicinal_plants:hasTreatmentFor ?class .}";
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	String plantPart = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

			+ "SELECT ?plantPart WHERE {medicinal_plants:"
			+ p
			+ " medicinal_plants:hasPlantPart ?class ."
			//+ "?class a medicinal_plants:Disease ."
			+ "?class rdfs:label ?plantPart  ." + "}";
	//+ " medicinal_plants:hasTreatmentFor ?class .}";
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	String medicalPropertie = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

			+ "SELECT ?medicalPropertie WHERE {medicinal_plants:"
			+ p
			+ " medicinal_plants:hasMedicalProperty ?class ."
			//+ "?class a medicinal_plants:Disease ."
			+ "?class rdfs:label ?medicalPropertie  ." + "}";
	//+ " medicinal_plants:hasTreatmentFor ?class .}";
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	Query query2 = QueryFactory.create(family);
	QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
	com.hp.hpl.jena.query.ResultSet results2 = qe2.execSelect();

	QuerySolution aux;
	while (results2.hasNext()) {
		aux = results2.next();
		out.print("<b>Family: </b>" + "<a href=\"browseplants.jsp?f="+aux.get("familylabel").toString()+"\">"+aux.get("familylabel").toString()+"</a>"
				+ "<br>");
	}

	////////////////////////////////////////////////////////////////////////
	Query query3 = QueryFactory.create(genre);
	QueryExecution qe3 = QueryExecutionFactory.create(query3, model);
	com.hp.hpl.jena.query.ResultSet results3 = qe3.execSelect();

	while (results3.hasNext()) {
		aux = results3.next();
		out.print("<b>Genre: </b>" + "<a href=\"browseplants.jsp?g="+aux.get("genrelabel").toString()+"\">" +aux.get("genrelabel").toString()+"</a>"+ "<br>");
	}
	///////////////////////////////////////////////////////////////////////
	Query query4 = QueryFactory.create(species);
	QueryExecution qe4 = QueryExecutionFactory.create(query4, model);
	com.hp.hpl.jena.query.ResultSet results4 = qe4.execSelect();

	while (results4.hasNext()) {
		aux = results4.next();
		out.print("<b>Species: </b>"
				+ aux.get("specieslabel").toString() + "<br>");
	}
	/////////////////////////////////////////////////////////////////////////
	Query query5 = QueryFactory.create(description);
	QueryExecution qe5 = QueryExecutionFactory.create(query5, model);
	com.hp.hpl.jena.query.ResultSet results5 = qe5.execSelect();

	while (results5.hasNext()) {
		aux = results5.next();
		out.print("<br><b>Description: </b>"
				+ aux.get("desc").toString() + "<br>");
	}
	//////////////////////////////////////////////////////////////////////////
	Query query6 = QueryFactory.create(diseases);
	QueryExecution qe6 = QueryExecutionFactory.create(query6, model);
	com.hp.hpl.jena.query.ResultSet results6 = qe6.execSelect();

	ArrayList<String> dis = new ArrayList<String>();
	ArrayList<String> disLabel = new ArrayList<String>();
	while (results6.hasNext()) {
		aux = results6.next();
		dis.add("<a href=\"browseplants.jsp?d="+aux.get("diseases").toString()+"\">"+aux.get("diseases").toString()+"</a>");
		disLabel.add(aux.get("diseases").toString());
	}
	out.print("<br><b>Treats: </b>" + dis + "<br>");

	/////////////////////////////////////////////////////////////////////////
	Query query7 = QueryFactory.create(ediblePart);
	QueryExecution qe7 = QueryExecutionFactory.create(query7, model);
	com.hp.hpl.jena.query.ResultSet results7 = qe7.execSelect();

	ArrayList<String> edible = new ArrayList<String>();
	while (results7.hasNext()) {
		aux = results7.next();
		edible.add(aux.get("ediblePart").toString());
	}
	out.print("<br><b>Edible Parts: </b>" + edible + "<br>");

	/////////////////////////////////////////////////////////////////////////
	Query query8 = QueryFactory.create(plantPart);
	QueryExecution qe8 = QueryExecutionFactory.create(query8, model);
	com.hp.hpl.jena.query.ResultSet results8 = qe8.execSelect();

	ArrayList<String> part = new ArrayList<String>();
	while (results8.hasNext()) {
		aux = results8.next();
		part.add(aux.get("plantPart").toString());
	}
	out.print("<br><b>Plant parts used in treatment: </b>" + part
			+ "<br>");
	/////////////////////////////////////////////////////////////////////////
	Query query9 = QueryFactory.create(medicalPropertie);
	QueryExecution qe9 = QueryExecutionFactory.create(query9, model);
	com.hp.hpl.jena.query.ResultSet results9 = qe9.execSelect();

	ArrayList<String> medProp = new ArrayList<String>();
	ArrayList<String> medPropLabel = new ArrayList<String>();
	while (results9.hasNext()) {
		aux = results9.next();
		medProp.add("<a href=\"browseplants.jsp?mp="+aux.get("medicalPropertie").toString()+"\">"+aux.get("medicalPropertie").toString()+"</a>");
		medPropLabel.add(aux.get("medicalPropertie").toString());
	}
	out.print("<br><b>Medicinal properties: </b>" + medProp + "<br>");
	/////////////////////////////////////////////////////////////////////////

	
	
	
	// RECOMENDATIONZ

	HashMap<String, Integer> recomendationScores = new HashMap<String, Integer>();
	HashMap<String, String> whyRecomend = new HashMap<String, String>();

	
	// BY MEDICINAL PROPERTY
	for (String d : medPropLabel) {
		System.out.println(d);

		String faux = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

				+ "SELECT ?plant WHERE { ?plant medicinal_plants:hasMedicalProperty ?d ."
				+ "?d rdfs:label \'" + d + "\'" + "}";

		Query queryfaux = QueryFactory.create(faux);
		QueryExecution qefaux = QueryExecutionFactory.create(queryfaux,
				model);
		com.hp.hpl.jena.query.ResultSet resultsfaux = qefaux
				.execSelect();

		QuerySolution ffaux;

		while (resultsfaux.hasNext()) {
			ffaux = resultsfaux.next();
			/*String familyaux = ffaux.get("family").toString();
			familyaux = familyaux.replace("http://www.owl-ontologies.com/Plantas.owl#", "");
			System.out.print(familyaux);*/
			String plant = ffaux.get("plant").toString();
			plant = plant.replace(
					"http://www.owl-ontologies.com/Plantas.owl#", "");
			//System.out.println(plant);

			if (recomendationScores.containsKey(plant)) {
				recomendationScores.put(plant,
						recomendationScores.get(plant) + 2);
				whyRecomend.put(plant,whyRecomend.get(plant)+" /" +"<font color=\"green\">" +d+"</font>");
			} else{
				recomendationScores.put(plant, 2);
				whyRecomend.put(plant,"<font color=\"green\">" +d+"</font>");
			}

			

		}
	}

	// BY DESEASE
	for (String d : disLabel) {
		System.out.println(d);

		String faux = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

				+ "SELECT ?plant WHERE { ?plant medicinal_plants:hasTreatmentFor ?d ."
				+ "?d rdfs:label \'" + d + "\'" + "}";

		Query queryfaux = QueryFactory.create(faux);
		QueryExecution qefaux = QueryExecutionFactory.create(queryfaux,
				model);
		com.hp.hpl.jena.query.ResultSet resultsfaux = qefaux
				.execSelect();

		QuerySolution ffaux;

		while (resultsfaux.hasNext()) {
			ffaux = resultsfaux.next();
			/*String familyaux = ffaux.get("family").toString();
			familyaux = familyaux.replace("http://www.owl-ontologies.com/Plantas.owl#", "");
			System.out.print(familyaux);*/
			String plant = ffaux.get("plant").toString();
			plant = plant.replace(
					"http://www.owl-ontologies.com/Plantas.owl#", "");
			//System.out.println(plant);

			if (recomendationScores.containsKey(plant)) {
				recomendationScores.put(plant,
						recomendationScores.get(plant) + 1);
				whyRecomend.put(plant,whyRecomend.get(plant)+" /" +"<font color=\"red\">" +d+"</font>");
			} else{
				recomendationScores.put(plant, 1);
				whyRecomend.put(plant,"<font color=\"red\">" +d+"</font>");
			}

		}
	}
	// BY FAMILY
	String faux1 = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

			+ "SELECT ?plant ?label WHERE {medicinal_plants:"
			+ p
			+ " medicinal_plants:hasClassification ?family ."
			+ "?family a medicinal_plants:Family ."
			+ " ?plant medicinal_plants:hasClassification ?family ."
			+ "?family rdfs:label ?label "
			+ "}";

	Query queryfaux1 = QueryFactory.create(faux1);
	QueryExecution qefaux1 = QueryExecutionFactory.create(queryfaux1,
			model);
	com.hp.hpl.jena.query.ResultSet resultsfaux1 = qefaux1.execSelect();

	QuerySolution ffaux1;
	while (resultsfaux1.hasNext()) {
		ffaux1 = resultsfaux1.next();
		/*String familyaux = ffaux.get("family").toString();
		familyaux = familyaux.replace("http://www.owl-ontologies.com/Plantas.owl#", "");
		System.out.print(familyaux);*/
		String plant = ffaux1.get("plant").toString();
		plant = plant.replace(
				"http://www.owl-ontologies.com/Plantas.owl#", "");
		String label = ffaux1.get("label").toString();
		label = label.replace(
				"http://www.owl-ontologies.com/Plantas.owl#", "");
		//System.out.println(plant);
		if (recomendationScores.containsKey(plant)) {
			recomendationScores.put(plant,
					recomendationScores.get(plant) + 2);
			whyRecomend.put(plant,whyRecomend.get(plant)+" /" +"<font color=\"orange\">" +label+"</font>");
		} else{
			recomendationScores.put(plant, 2);
			whyRecomend.put(plant,"<font color=\"orange\">" +label+"</font>");
		}
		
	}

	// BY GENRE
	String faux2 = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

			+ "SELECT ?plant ?label WHERE {medicinal_plants:"
			+ p
			+ " medicinal_plants:hasClassification ?family ."
			+ "?family a medicinal_plants:Genre ."
			+ " ?plant medicinal_plants:hasClassification ?family ."
			+ "?family rdfs:label ?label "
			+ "}";

	Query queryfaux2 = QueryFactory.create(faux2);
	QueryExecution qefaux2 = QueryExecutionFactory.create(queryfaux2,
			model);
	com.hp.hpl.jena.query.ResultSet resultsfaux2 = qefaux2.execSelect();

	QuerySolution ffaux2;
	while (resultsfaux2.hasNext()) {
		ffaux2 = resultsfaux2.next();
		/*String familyaux = ffaux.get("family").toString();
		familyaux = familyaux.replace("http://www.owl-ontologies.com/Plantas.owl#", "");
		System.out.print(familyaux);*/
		String plant = ffaux2.get("plant").toString();
		plant = plant.replace(
				"http://www.owl-ontologies.com/Plantas.owl#", "");
		String label = ffaux2.get("label").toString();
		label = label.replace(
				"http://www.owl-ontologies.com/Plantas.owl#", "");
		//System.out.println(plant);
		if (recomendationScores.containsKey(plant)) {
			recomendationScores.put(plant,
					recomendationScores.get(plant) + 3);
			whyRecomend.put(plant,whyRecomend.get(plant)+" /" +"<font color=\"grey\">" +label+"</font>");
		} else{
			recomendationScores.put(plant, 3);
			whyRecomend.put(plant,"<font color=\"greys\">" +label+"</font>");
		}
	}

	System.out.println(recomendationScores);

	class ValueComparator implements Comparator<String> {

		Map<String, Integer> base;

		public ValueComparator(Map<String, Integer> base) {
			this.base = base;
		}

		// Note: this comparator imposes orderings that are inconsistent with equals.    
		public int compare(String a, String b) {
			if (base.get(a) >= base.get(b)) {
				return -1;
			} else {
				return 1;
			} // returning 0 would merge keys
		}
	}
	ValueComparator bvc = new ValueComparator(recomendationScores);
	TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(
			bvc);
	sorted_map.putAll(recomendationScores);

	out.print("<br><b>You may also be interested in the following plants: </b><br>");

	HashMap<String, String> plantid_plantname = new HashMap<String, String>();

	for (int i = 0; i < 8; i++) {
		try {
			Object key = sorted_map.keySet().toArray(
					new Object[sorted_map.size()])[i];
			
			if(key.equals(p))
				continue;
			////////////////////////////////////////////////////////////////////////////////////////////////////
			String genrerelated = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

					+ "SELECT ?genrelabel WHERE {medicinal_plants:"
					+ key
					+ " medicinal_plants:hasClassification ?class ."
					+ "?class a medicinal_plants:Genre ."
					+ "?class rdfs:label ?genrelabel  ." + "}";
			////////////////////////////////////////////////////////////////////////////////////////////////////
			String speciesrelated = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

					+ "SELECT ?specieslabel WHERE {medicinal_plants:"
					+ key
					+ " medicinal_plants:hasClassification ?class ."
					+ "?class a medicinal_plants:Species ."
					+ "?class rdfs:label ?specieslabel  ." + "}";

			////////////////////////////////////////////////////////////////////////
			Query queryrelatedgenre = QueryFactory.create(genrerelated);
			QueryExecution qerelatedgenre = QueryExecutionFactory
					.create(queryrelatedgenre, model);
			com.hp.hpl.jena.query.ResultSet resultsrelatedgenre = qerelatedgenre
					.execSelect();

			while (resultsrelatedgenre.hasNext()) {
				aux = resultsrelatedgenre.next();
				
				if (plantid_plantname.containsKey((String) key)) {
					plantid_plantname.put((String) key, plantid_plantname.get((String) key) +" "+aux.get("genrelabel").toString());
				} else
					plantid_plantname.put((String) key, aux.get("genrelabel").toString());
			}
			///////////////////////////////////////////////////////////////////////
			Query queryspeciesrelated = QueryFactory
					.create(speciesrelated);
			QueryExecution qespeciesrelated = QueryExecutionFactory
					.create(queryspeciesrelated, model);
			com.hp.hpl.jena.query.ResultSet resultsspeciesrelated = qespeciesrelated
					.execSelect();

			while (resultsspeciesrelated.hasNext()) {
				aux = resultsspeciesrelated.next();
				
				
				if (plantid_plantname.containsKey((String) key)) {
					plantid_plantname.put((String) key, plantid_plantname.get((String) key) +" "+aux.get("specieslabel").toString());
				} else
					plantid_plantname.put((String) key, aux.get("specieslabel").toString());

			}

			/////////////////////////////////////////////////////////////////////////

		
		} catch (Exception e) {

		}
	}
	
	for (String plant: plantid_plantname.keySet()){
		out.print( "<a class=\"innerlink\" onclick=\"innerLoadPlant(\'"+plant+"\');\">" + plantid_plantname.get(plant) + "</a>"+" " +whyRecomend.get(plant) +"<br>");
	}
	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
	System.out.println(whyRecomend);
	System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
%>
<br>