<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page
	import="com.hp.hpl.jena.rdf.model.Model,com.hp.hpl.jena.rdf.model.ModelFactory,com.hp.hpl.jena.util.FileManager,java.io.InputStream,com.hp.hpl.jena.query.Query,com.hp.hpl.jena.query.QueryExecution,com.hp.hpl.jena.query.ResultSetFormatter,com.hp.hpl.jena.query.QueryFactory,com.hp.hpl.jena.query.QueryExecutionFactory,com.hp.hpl.jena.query.QuerySolution,java.util.*,com.hp.hpl.jena.rdf.model.RDFNode"%>
	
	<link href="http://fonts.googleapis.com/css?family=Cambo" rel="stylesheet" type="text/css">
	
	<%
		String disease = request.getParameter("d");

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

		String queryString = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "SELECT distinct ?plant WHERE { ?plant medicinal_plants:hasTreatmentFor medicinal_plants:"
				+ disease + " ." + "}";

		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

		while (results.hasNext()) {
			QuerySolution aux = results.next();
			String p = aux.get("plant").toString();
			p = p.replace("http://www.owl-ontologies.com/Plantas.owl#", "");
			out.print("<div class=\"bloco\"		onclick = \"testFunction('"+p+"'); document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\" >");
			String family = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
					
					+ "SELECT ?familylabel ?genderlabel ?specieslabel WHERE {medicinal_plants:"+p+" medicinal_plants:hasClassification ?class ."
					+ "?class a medicinal_plants:Family ."
					+ "?class rdfs:label ?familylabel  ."	
					+ "}";
			String genre = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
					
					+ "SELECT ?genrelabel WHERE {medicinal_plants:"+p+" medicinal_plants:hasClassification ?class ."
					+ "?class a medicinal_plants:Genre ."
					+ "?class rdfs:label ?genrelabel  ."	
					+ "}";
			String species = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
					+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
					
					+ "SELECT ?specieslabel WHERE {medicinal_plants:"+p+" medicinal_plants:hasClassification ?class ."
					+ "?class a medicinal_plants:Species ."
					+ "?class rdfs:label ?specieslabel  ."	
					+ "}";

			Query query2 = QueryFactory.create(family);
			QueryExecution qe2 = QueryExecutionFactory.create(query2, model);
			com.hp.hpl.jena.query.ResultSet results2 = qe2.execSelect();
		
			while (results2.hasNext()) {
				aux = results2.next();
				out.print("Family: "+aux.get("familylabel").toString()+"<br>");
			}
			Query query3 = QueryFactory.create(genre);
			QueryExecution qe3 = QueryExecutionFactory.create(query3, model);
			com.hp.hpl.jena.query.ResultSet results3 = qe3.execSelect();
		
			while (results3.hasNext()) {
				aux = results3.next();
				out.print("Genre: "+aux.get("genrelabel").toString()+"<br>");
			}
			Query query4 = QueryFactory.create(species);
			QueryExecution qe4 = QueryExecutionFactory.create(query4, model);
			com.hp.hpl.jena.query.ResultSet results4 = qe4.execSelect();
		
			while (results4.hasNext()) {
				aux = results4.next();
				out.print("Species: "+aux.get("specieslabel").toString()+"<br>");
			}
			out.print("</div>");
			
			//out.print( "<p>This is the main content. To display a lightbox click <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\">here</a></p> <div id=\"light\" class=\"white_content\">This is the lightbox content. <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'\">Close</a></div> <div id=\"fade\" class=\"black_overlay\"></div>");
			out.print( "<div id=\"light\" class=\"white_content\"> </div> <div id=\"fade\" class=\"black_overlay\"></div>");

		}
		qe.close();
	%>
