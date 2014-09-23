<%@page import="java.util.HashMap"%>
<%@ page
	import="com.hp.hpl.jena.rdf.model.Model,com.hp.hpl.jena.rdf.model.ModelFactory,com.hp.hpl.jena.util.FileManager,java.io.InputStream,com.hp.hpl.jena.query.Query,com.hp.hpl.jena.query.QueryExecution,com.hp.hpl.jena.query.ResultSetFormatter,com.hp.hpl.jena.query.QueryFactory,com.hp.hpl.jena.query.QueryExecutionFactory,com.hp.hpl.jena.query.QuerySolution,java.util.*,com.hp.hpl.jena.rdf.model.RDFNode"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<link rel="shortcut icon" href="assets/ico/favicon.png">

<title>Medicinal Plants Semantic Database</title>

<!-- Bootstrap core CSS -->
<link href="dist/css/bootstrap.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="jumbotron.css" rel="stylesheet">

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
	type="text/javascript"></script>
<script src="dist/js/scripts.js"></script>


<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="assets/js/html5shiv.js"></script>
      <script src="assets/js/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Medicinal Plants</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li><a href="index.html">Home</a></li>
					<li><a href="plants.jsp">Diseases</a></li>
					<li class="active"><a href="bByClass1.jsp">Plants</a></li>
				</ul>
				<div>
					<form class="navbar-form navbar-right" action='browseplants.jsp'
						method='post'>
						<div class="form-group">
							<input type="text" name='query' placeholder="Semantic Search"
								size="60" class="form-control">
						</div>
						<button type="submit" class="btn btn-success">Search</button>
					</form>
				</div>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</div>

	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div id="aux" class="browsing">
		<div class="container">
			<p></p>
			<h2>Plants List</h2>
			<p></p>

			<%-- 			<%
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
						+

						"SELECT distinct ?family ?familylabel WHERE { ?family rdf:type medicinal_plants:Family ."
						+ "?family rdfs:label ?familylabel"

						+ "}";

				Query query = QueryFactory.create(queryString);

				// procura uri sabendo a label
				QueryExecution qe = QueryExecutionFactory.create(query, model);
				com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
				
				//ResultSetFormatter.out(System.out, results, query);

				HashMap<String, String> families = new HashMap<String, String>();

				while (results.hasNext()) {
					QuerySolution aux = results.next();
					String dislabel = aux.get("familylabel").toString();
					String dis = aux.get("family").toString();

					families.put(dislabel, dis.replace(
							"http://www.owl-ontologies.com/Plantas.owl#", ""));
				}

				//System.out.println(diseases);
				qe.close();
			%> --%>


			<ul>

				<%
					for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
						out.println("<li>"
								+ "<a class=\"browsinglink\" href=\"bByClass2.jsp?d="
								+ alphabet + "\">" + alphabet + "</a>" + "</li>");
						//					out.println("<ul id=\"expList\">");

						/* 						for (String d : families.keySet())
											if (d.charAt(0) == alphabet)
												out.print("<li>"
														+ "<a class=\"browsinglink\" href=\"PlantsFamily.jsp?d="
														+ families.get(d) + "\">" + d + "</a>" + "</li>");
						 */

						//				out.println("</ul>");
						out.println("</li>");
					}
				%>

			</ul>

			<p></p>
		</div>
	</div>

	<div class="plant" id=divplant>

		<%
			String browse_label = request.getParameter("l");
			
		
			if (browse_label != null) {
			

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

				{
					out.print("<center> <h2>Searching: " + browse_label
							+ "</h2></center>");
				out.print("<center><h4>Similar Results: </h4></center>");
				out.print("<center><table border=\"1\">");

					out.print("<tr>");

					String queryString = "PREFIX medp: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"

							+ "SELECT DISTINCT ?label WHERE {"
							+ "?x rdfs:label ?label."
							+ "FILTER regex(?label,\"^"
							+ browse_label
							+ "\",'i')"
							+ "}" + "ORDER BY ?label";

					Query query = QueryFactory.create(queryString);

					// procura uri sabendo a label
					QueryExecution qe = QueryExecutionFactory.create(query,
							model);
					com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
					int add = 0;
					while (results.hasNext()) {
						out.print("<td>");
						add++;
						QuerySolution aux = results.next();
						String p = aux.get("label").toString();
						p=p.replace("^^http://www.w3.org/2001/XMLSchema#string", "");
						out.print("<a href=\"browseplants.jsp?l=" + p + "\">"
								+ p + "</a>");
						out.print("</td>");
						if (add > 10)
							break;
					}
					out.print("</tr>");

				
				out.print("</table></center>");
				}
				
				String queryString = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
						+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "SELECT distinct ?plant WHERE { ?plant ?prop ?value."
						+ "?value rdfs:label '" + browse_label + "' ." + "}";

				System.out.print(queryString);
				Query query = QueryFactory.create(queryString);
				QueryExecution qe = QueryExecutionFactory.create(query, model);
				com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

				while (results.hasNext()) {
					QuerySolution aux = results.next();
					String p = aux.get("plant").toString();
					System.out.print(p);
					p = p.replace("http://www.owl-ontologies.com/Plantas.owl#",
							"");
					out.print("<div class=\"bloco\"		onclick = \"testFunction('"
							+ p
							+ "'); document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\" >");
					String family = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?familylabel ?genderlabel ?specieslabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Family ."
							+ "?class rdfs:label ?familylabel  ." + "}";
					String genre = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?genrelabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Genre ."
							+ "?class rdfs:label ?genrelabel  ." + "}";
					String species = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?specieslabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Species ."
							+ "?class rdfs:label ?specieslabel  ." + "}";

					Query query2 = QueryFactory.create(family);
					QueryExecution qe2 = QueryExecutionFactory.create(query2,
							model);
					com.hp.hpl.jena.query.ResultSet results2 = qe2.execSelect();

					while (results2.hasNext()) {
						aux = results2.next();
						out.print("Family: "
								+ aux.get("familylabel").toString() + "<br>");
					}
					Query query3 = QueryFactory.create(genre);
					QueryExecution qe3 = QueryExecutionFactory.create(query3,
							model);
					com.hp.hpl.jena.query.ResultSet results3 = qe3.execSelect();

					while (results3.hasNext()) {
						aux = results3.next();
						out.print("Genre: " + aux.get("genrelabel").toString()
								+ "<br>");
					}
					Query query4 = QueryFactory.create(species);
					QueryExecution qe4 = QueryExecutionFactory.create(query4,
							model);
					com.hp.hpl.jena.query.ResultSet results4 = qe4.execSelect();

					while (results4.hasNext()) {
						aux = results4.next();
						out.print("Species: "
								+ aux.get("specieslabel").toString() + "<br>");
					}
					out.print("</div>");

					//out.print( "<p>This is the main content. To display a lightbox click <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\">here</a></p> <div id=\"light\" class=\"white_content\">This is the lightbox content. <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'\">Close</a></div> <div id=\"fade\" class=\"black_overlay\"></div>");
					out.print("<div id=\"light\" class=\"white_content\"> </div> <div id=\"fade\" class=\"black_overlay\"></div>");

				}
				qe.close();
			}

			String semantic_search = request.getParameter("query");
			if (semantic_search != null) {
				out.print("<center> <h2>Searching: " + semantic_search
						+ "</h2></center>");

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

				semantic_search = semantic_search.trim();
				String[] words = semantic_search.split(" ");
				out.print("<center><h4>Similar Results: </h4></center>");
				out.print("<center><table border=\"1\">");

				for (String word : words) {
					out.print("<tr>");

					String queryString = "PREFIX medp: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"

							+ "SELECT DISTINCT ?label WHERE {"
							+ "?x rdfs:label ?label."
							+ "FILTER regex(?label,\"^"
							+ word
							+ "\",'i')"
							+ "}" + "ORDER BY ?label";

					Query query = QueryFactory.create(queryString);

					// procura uri sabendo a label
					QueryExecution qe = QueryExecutionFactory.create(query,
							model);
					com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
					int add = 0;
					while (results.hasNext()) {
						out.print("<td>");
						add++;
						QuerySolution aux = results.next();
						String p = aux.get("label").toString();
						p=p.replace("^^http://www.w3.org/2001/XMLSchema#string", "");
						out.print("<a href=\"browseplants.jsp?l=" + p + "\">"
								+ p + "</a>");
						out.print("</td>");
						if (add > 10)
							break;
					}
					out.print("</tr>");

				}
				out.print("</table></center>");

				int n_labels = words.length;
				/*
				 String queryString = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#> "
				 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				 + "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "

				 + "SELECT distinct ?x  ";

				 queryString += "WHERE { ";

				 for (int i = 0; i < n_labels; i++) {
				 queryString += "?x rdf:type ?type"+i+" ." + "?x ?prop"+i+" ?aux" + i
				 + " ." + "FILTER regex(?label"+i+",'" + words[i]
				 + "','i')" + "?aux"+i+" rdfs:label ?label" + i + " .";
				 }

				 queryString += " }";
				 */

				ArrayList<Integer> tipos = new ArrayList<Integer>();
					
				for (int i = 0; i < n_labels; i++) {

					String queryString = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
							+ "SELECT distinct ?type ?value ?label WHERE {"
							+ "FILTER regex(?label,\"^"
							+ words[i]
							+ "\",'i')"
							+ "?value rdfs:label ?label ."
							+ "?value rdf:type ?type ." + "}";

					System.out.println(queryString);

					Query query = QueryFactory.create(queryString);
					QueryExecution qe = QueryExecutionFactory.create(query,
							model);
					com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

					int entra = 0;
					
					while (results.hasNext()) {
						entra = 1;
						
						QuerySolution aux = results.next();
						String type = aux.get("type").toString();
						System.out.println(type);
						if (type.equals("http://www.w3.org/2002/07/owl#ObjectProperty")){
							tipos.add(0);
							break;
						}
						else{
							tipos.add(1);
							break;
						}
					}
					if (entra ==0){
						tipos.add(1);
					}
				}
				
				int n_labels_return = 0;
				
				for (int val:tipos){
					if (val ==1)
						n_labels_return++;
				}
				System.out.println(tipos);

				
				 String queryString = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#> "
				 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				 + "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "

				 + "SELECT distinct ?x";

 				 for (int i = 0; i < n_labels; i++) {
 					queryString += " ?prop"+i+" "; 
				 }
 				for (int i = 0; i < n_labels; i++) {
 					queryString += " ?label"+i+" "; 
				 }
				 queryString += "WHERE { ";

				 for (int i = 0; i < n_labels; i++) {
					 if (tipos.get(i)==0){
						 
						 queryString += "?x rdf:type ?type"+i+" ." 
						 + "?x ?prop"+i+" ?y" + i+ " ." 
						 + "FILTER regex(?auxlabel"+i+",'" + words[i] + "','i')" 
						 + "?prop"+i+" rdfs:label ?auxlabel" + i + " .";
					
					 }
					 else {
						 queryString += "?x rdf:type ?type"+i+" ." + "?x ?prop"+i+" ?aux" + i
								 + " ." + "FILTER regex(?label"+i+",'" + words[i]
								 + "','i')" + "?aux"+i+" rdfs:label ?label" + i + " .";
								 
					 }
				 
				 }
				 queryString += " }";
				

					Query query = QueryFactory.create(queryString);
					QueryExecution qe = QueryExecutionFactory.create(query,
							model);
					com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

					HashMap<String,String> motivos = new HashMap<String,String>();

					while (results.hasNext()) {
						 System.out.println("entra");
						 QuerySolution aux = results.next();
						 String p = aux.get("x").toString();
						 
						 for (int i = 0; i < n_labels; i++) {
							 String motivo2 = "--";
							 String motivo1 = "--";
							 
							 try{
								 	motivo1 = aux.get("prop"+i).toString();
								 	motivo1 = motivo1.replace("http://www.owl-ontologies.com/Plantas.owl#", "");
								 	System.out.println(motivo1);
							 	 	 
							 }
							 catch (Exception e){
								 System.out.println("--");
								 motivo1 = ("--");
							 }
							 try{
								 	motivo2 = aux.get("label"+i).toString();
							 	 	motivo2 = motivo2.replace("http://www.owl-ontologies.com/Plantas.owl#", "");
							 	 	System.out.println(motivo2); 	 	 
							 }
							 catch (Exception e){
								 System.out.println("--");
								 motivo2 = ("--");
							 }
						 	if (!motivo1.equals("--") && !motivo2.equals("--")){
						 		if (motivos.containsKey(p)){
						 			motivos.put(p, motivos.get(p)+" "+motivo1+" "+ motivo2);
						 		}
						 		else
						 			motivos.put(p,motivo1+" "+ motivo2);
						 	}
						 }
						 
						 System.out.println(motivos);
						 						 
						 }
						 qe.close();				
				
						 
						 System.out.println(motivos);
						for(String p: motivos.keySet())				 {
							 QuerySolution aux = null;

							 
							p = p.replace("http://www.owl-ontologies.com/Plantas.owl#",
									 "");
									 out.print("<div class=\"bloco\"		onclick = \"testFunction('"
									 + p
									 + "'); document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\" >");
									 String family = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
									 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
									 + "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

									 + "SELECT ?familylabel ?genderlabel ?specieslabel WHERE {medicinal_plants:"
									 + p
									 + " medicinal_plants:hasClassification ?class ."
									 + "?class a medicinal_plants:Family ."
									 + "?class rdfs:label ?familylabel  ." + "}";
									 String genre = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
									 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
									 + "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

									 + "SELECT ?genrelabel WHERE {medicinal_plants:"
									 + p
									 + " medicinal_plants:hasClassification ?class ."
									 + "?class a medicinal_plants:Genre ."
									 + "?class rdfs:label ?genrelabel  ." + "}";
									 String species = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
									 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
									 + "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

									 + "SELECT ?specieslabel WHERE {medicinal_plants:"
									 + p
									 + " medicinal_plants:hasClassification ?class ."
									 + "?class a medicinal_plants:Species ."
									 + "?class rdfs:label ?specieslabel  ." + "}";

									 Query query2 = QueryFactory.create(family);
									 QueryExecution qe2 = QueryExecutionFactory.create(query2,
									 model);
									 com.hp.hpl.jena.query.ResultSet results2 = qe2.execSelect();

									 while (results2.hasNext()) {
									 aux = results2.next();
									 out.print("Family: "
									 + aux.get("familylabel").toString() + "<br>");
									 }
									 Query query3 = QueryFactory.create(genre);
									 QueryExecution qe3 = QueryExecutionFactory.create(query3,
									 model);
									 com.hp.hpl.jena.query.ResultSet results3 = qe3.execSelect();

									 while (results3.hasNext()) {
									 aux = results3.next();
									 out.print("Genre: " + aux.get("genrelabel").toString()+"<br>");
									 }
									 Query query4 = QueryFactory.create(species);
									 QueryExecution qe4 = QueryExecutionFactory.create(query4,
									 model);
									 com.hp.hpl.jena.query.ResultSet results4 = qe4.execSelect();

									 while (results4.hasNext()) {
									 aux = results4.next();
									 out.print("Species: "
									 + aux.get("specieslabel").toString() + "<br>");
									 }
									 
									 String motivo = motivos.get("http://www.owl-ontologies.com/Plantas.owl#"+p);
									 motivo = motivo.replace("http://www.w3.org/1999/02/22-rdf-syntax-ns#type", "");
									 out.print ( "<font color=\"blue\">" +motivo +"</font> <br>");
									 
									 out.print("</div>");

									 //out.print( "<p>This is the main content. To display a lightbox click <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\">here</a></p> <div id=\"light\" class=\"white_content\">This is the lightbox content. <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'\">Close</a></div> <div id=\"fade\" class=\"black_overlay\"></div>");
									 out.print("<div id=\"light\" class=\"white_content\"> </div> <div id=\"fade\" class=\"black_overlay\"></div>");

						 }
				
	
	/*			 while (results.hasNext()) {
				 QuerySolution aux = results.next();
				 String p = aux.get("x").toString();

				 p = p.replace("http://www.owl-ontologies.com/Plantas.owl#",
				 "");
				 out.print("<div class=\"bloco\"		onclick = \"testFunction('"
				 + p
				 + "'); document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\" >");
				 String family = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
				 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				 + "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

				 + "SELECT ?familylabel ?genderlabel ?specieslabel WHERE {medicinal_plants:"
				 + p
				 + " medicinal_plants:hasClassification ?class ."
				 + "?class a medicinal_plants:Family ."
				 + "?class rdfs:label ?familylabel  ." + "}";
				 String genre = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
				 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				 + "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

				 + "SELECT ?genrelabel WHERE {medicinal_plants:"
				 + p
				 + " medicinal_plants:hasClassification ?class ."
				 + "?class a medicinal_plants:Genre ."
				 + "?class rdfs:label ?genrelabel  ." + "}";
				 String species = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
				 + "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				 + "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

				 + "SELECT ?specieslabel WHERE {medicinal_plants:"
				 + p
				 + " medicinal_plants:hasClassification ?class ."
				 + "?class a medicinal_plants:Species ."
				 + "?class rdfs:label ?specieslabel  ." + "}";

				 Query query2 = QueryFactory.create(family);
				 QueryExecution qe2 = QueryExecutionFactory.create(query2,
				 model);
				 com.hp.hpl.jena.query.ResultSet results2 = qe2.execSelect();

				 while (results2.hasNext()) {
				 aux = results2.next();
				 out.print("Family: "
				 + aux.get("familylabel").toString() + "<br>");
				 }
				 Query query3 = QueryFactory.create(genre);
				 QueryExecution qe3 = QueryExecutionFactory.create(query3,
				 model);
				 com.hp.hpl.jena.query.ResultSet results3 = qe3.execSelect();

				 while (results3.hasNext()) {
				 aux = results3.next();
				 out.print("Genre: " + aux.get("genrelabel").toString()
				 + "<br>");
				 }
				 Query query4 = QueryFactory.create(species);
				 QueryExecution qe4 = QueryExecutionFactory.create(query4,
				 model);
				 com.hp.hpl.jena.query.ResultSet results4 = qe4.execSelect();

				 while (results4.hasNext()) {
				 aux = results4.next();
				 out.print("Species: "
				 + aux.get("specieslabel").toString() + "<br>");
				 }
				 out.print("</div>");

				 //out.print( "<p>This is the main content. To display a lightbox click <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\">here</a></p> <div id=\"light\" class=\"white_content\">This is the lightbox content. <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'\">Close</a></div> <div id=\"fade\" class=\"black_overlay\"></div>");
				 out.print("<div id=\"light\" class=\"white_content\"> </div> <div id=\"fade\" class=\"black_overlay\"></div>");

				 }
				 qe.close();*/

			}

			String browse_family = request.getParameter("f");

			if (browse_family != null) {

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
						+ "SELECT distinct ?plant WHERE { ?plant medicinal_plants:hasClassification ?class ."
						+ "?class a medicinal_plants:Family ."
						+ "?class rdfs:label '" + browse_family + "' ." + "}";

				System.out.print(queryString);
				Query query = QueryFactory.create(queryString);
				QueryExecution qe = QueryExecutionFactory.create(query, model);
				com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

				while (results.hasNext()) {
					QuerySolution aux = results.next();
					String p = aux.get("plant").toString();
					System.out.print(p);
					p = p.replace("http://www.owl-ontologies.com/Plantas.owl#",
							"");
					out.print("<div class=\"bloco\"		onclick = \"testFunction('"
							+ p
							+ "'); document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\" >");
					String family = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?familylabel ?genderlabel ?specieslabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Family ."
							+ "?class rdfs:label ?familylabel  ." + "}";
					String genre = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?genrelabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Genre ."
							+ "?class rdfs:label ?genrelabel  ." + "}";
					String species = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?specieslabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Species ."
							+ "?class rdfs:label ?specieslabel  ." + "}";

					Query query2 = QueryFactory.create(family);
					QueryExecution qe2 = QueryExecutionFactory.create(query2,
							model);
					com.hp.hpl.jena.query.ResultSet results2 = qe2.execSelect();

					while (results2.hasNext()) {
						aux = results2.next();
						out.print("Family: "
								+ aux.get("familylabel").toString() + "<br>");
					}
					Query query3 = QueryFactory.create(genre);
					QueryExecution qe3 = QueryExecutionFactory.create(query3,
							model);
					com.hp.hpl.jena.query.ResultSet results3 = qe3.execSelect();

					while (results3.hasNext()) {
						aux = results3.next();
						out.print("Genre: " + aux.get("genrelabel").toString()
								+ "<br>");
					}
					Query query4 = QueryFactory.create(species);
					QueryExecution qe4 = QueryExecutionFactory.create(query4,
							model);
					com.hp.hpl.jena.query.ResultSet results4 = qe4.execSelect();

					while (results4.hasNext()) {
						aux = results4.next();
						out.print("Species: "
								+ aux.get("specieslabel").toString() + "<br>");
					}
					out.print("</div>");

					//out.print( "<p>This is the main content. To display a lightbox click <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\">here</a></p> <div id=\"light\" class=\"white_content\">This is the lightbox content. <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'\">Close</a></div> <div id=\"fade\" class=\"black_overlay\"></div>");
					out.print("<div id=\"light\" class=\"white_content\"> </div> <div id=\"fade\" class=\"black_overlay\"></div>");

				}
				qe.close();
			}

			String browse_genre = request.getParameter("g");

			if (browse_genre != null) {

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
						+ "SELECT distinct ?plant WHERE { ?plant medicinal_plants:hasClassification ?class ."
						+ "?class a medicinal_plants:Genre ."
						+ "?class rdfs:label '" + browse_genre + "' ." + "}";

				System.out.print(queryString);
				Query query = QueryFactory.create(queryString);
				QueryExecution qe = QueryExecutionFactory.create(query, model);
				com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

				while (results.hasNext()) {
					QuerySolution aux = results.next();
					String p = aux.get("plant").toString();
					System.out.print(p);
					p = p.replace("http://www.owl-ontologies.com/Plantas.owl#",
							"");
					out.print("<div class=\"bloco\"		onclick = \"testFunction('"
							+ p
							+ "'); document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\" >");
					String family = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?familylabel ?genderlabel ?specieslabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Family ."
							+ "?class rdfs:label ?familylabel  ." + "}";
					String genre = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?genrelabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Genre ."
							+ "?class rdfs:label ?genrelabel  ." + "}";
					String species = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?specieslabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Species ."
							+ "?class rdfs:label ?specieslabel  ." + "}";

					Query query2 = QueryFactory.create(family);
					QueryExecution qe2 = QueryExecutionFactory.create(query2,
							model);
					com.hp.hpl.jena.query.ResultSet results2 = qe2.execSelect();

					while (results2.hasNext()) {
						aux = results2.next();
						out.print("Family: "
								+ aux.get("familylabel").toString() + "<br>");
					}
					Query query3 = QueryFactory.create(genre);
					QueryExecution qe3 = QueryExecutionFactory.create(query3,
							model);
					com.hp.hpl.jena.query.ResultSet results3 = qe3.execSelect();

					while (results3.hasNext()) {
						aux = results3.next();
						out.print("Genre: " + aux.get("genrelabel").toString()
								+ "<br>");
					}
					Query query4 = QueryFactory.create(species);
					QueryExecution qe4 = QueryExecutionFactory.create(query4,
							model);
					com.hp.hpl.jena.query.ResultSet results4 = qe4.execSelect();

					while (results4.hasNext()) {
						aux = results4.next();
						out.print("Species: "
								+ aux.get("specieslabel").toString() + "<br>");
					}
					out.print("</div>");

					//out.print( "<p>This is the main content. To display a lightbox click <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\">here</a></p> <div id=\"light\" class=\"white_content\">This is the lightbox content. <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'\">Close</a></div> <div id=\"fade\" class=\"black_overlay\"></div>");
					out.print("<div id=\"light\" class=\"white_content\"> </div> <div id=\"fade\" class=\"black_overlay\"></div>");

				}
				qe.close();
			}

			String browse_disease = request.getParameter("d");

			if (browse_disease != null) {

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
						+ "SELECT distinct ?plant WHERE { ?plant medicinal_plants:hasTreatmentFor ?disease ."
						+ "?disease rdfs:label '"
						+ browse_disease
						+ "' ."
						+ "}";

				System.out.print(queryString);
				Query query = QueryFactory.create(queryString);
				QueryExecution qe = QueryExecutionFactory.create(query, model);
				com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

				while (results.hasNext()) {
					QuerySolution aux = results.next();
					String p = aux.get("plant").toString();
					System.out.print(p);
					p = p.replace("http://www.owl-ontologies.com/Plantas.owl#",
							"");
					out.print("<div class=\"bloco\"		onclick = \"testFunction('"
							+ p
							+ "'); document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\" >");
					String family = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?familylabel ?genderlabel ?specieslabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Family ."
							+ "?class rdfs:label ?familylabel  ." + "}";
					String genre = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?genrelabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Genre ."
							+ "?class rdfs:label ?genrelabel  ." + "}";
					String species = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?specieslabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Species ."
							+ "?class rdfs:label ?specieslabel  ." + "}";

					Query query2 = QueryFactory.create(family);
					QueryExecution qe2 = QueryExecutionFactory.create(query2,
							model);
					com.hp.hpl.jena.query.ResultSet results2 = qe2.execSelect();

					while (results2.hasNext()) {
						aux = results2.next();
						out.print("Family: "
								+ aux.get("familylabel").toString() + "<br>");
					}
					Query query3 = QueryFactory.create(genre);
					QueryExecution qe3 = QueryExecutionFactory.create(query3,
							model);
					com.hp.hpl.jena.query.ResultSet results3 = qe3.execSelect();

					while (results3.hasNext()) {
						aux = results3.next();
						out.print("Genre: " + aux.get("genrelabel").toString()
								+ "<br>");
					}
					Query query4 = QueryFactory.create(species);
					QueryExecution qe4 = QueryExecutionFactory.create(query4,
							model);
					com.hp.hpl.jena.query.ResultSet results4 = qe4.execSelect();

					while (results4.hasNext()) {
						aux = results4.next();
						out.print("Species: "
								+ aux.get("specieslabel").toString() + "<br>");
					}
					out.print("</div>");

					//out.print( "<p>This is the main content. To display a lightbox click <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\">here</a></p> <div id=\"light\" class=\"white_content\">This is the lightbox content. <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'\">Close</a></div> <div id=\"fade\" class=\"black_overlay\"></div>");
					out.print("<div id=\"light\" class=\"white_content\"> </div> <div id=\"fade\" class=\"black_overlay\"></div>");

				}
				qe.close();
			}

			String browse_mp = request.getParameter("mp");

			if (browse_mp != null) {

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
						+ "SELECT distinct ?plant WHERE { ?plant medicinal_plants:hasMedicalProperty ?disease ."
						+ "?disease rdfs:label '" + browse_mp + "' ." + "}";

				System.out.print(queryString);
				Query query = QueryFactory.create(queryString);
				QueryExecution qe = QueryExecutionFactory.create(query, model);
				com.hp.hpl.jena.query.ResultSet results = qe.execSelect();

				while (results.hasNext()) {
					QuerySolution aux = results.next();
					String p = aux.get("plant").toString();
					System.out.print(p);
					p = p.replace("http://www.owl-ontologies.com/Plantas.owl#",
							"");
					out.print("<div class=\"bloco\"		onclick = \"testFunction('"
							+ p
							+ "'); document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\" >");
					String family = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?familylabel ?genderlabel ?specieslabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Family ."
							+ "?class rdfs:label ?familylabel  ." + "}";
					String genre = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?genrelabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Genre ."
							+ "?class rdfs:label ?genrelabel  ." + "}";
					String species = "PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"
							+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
							+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"

							+ "SELECT ?specieslabel WHERE {medicinal_plants:"
							+ p
							+ " medicinal_plants:hasClassification ?class ."
							+ "?class a medicinal_plants:Species ."
							+ "?class rdfs:label ?specieslabel  ." + "}";

					Query query2 = QueryFactory.create(family);
					QueryExecution qe2 = QueryExecutionFactory.create(query2,
							model);
					com.hp.hpl.jena.query.ResultSet results2 = qe2.execSelect();

					while (results2.hasNext()) {
						aux = results2.next();
						out.print("Family: "
								+ aux.get("familylabel").toString() + "<br>");
					}
					Query query3 = QueryFactory.create(genre);
					QueryExecution qe3 = QueryExecutionFactory.create(query3,
							model);
					com.hp.hpl.jena.query.ResultSet results3 = qe3.execSelect();

					while (results3.hasNext()) {
						aux = results3.next();
						out.print("Genre: " + aux.get("genrelabel").toString()
								+ "<br>");
					}
					Query query4 = QueryFactory.create(species);
					QueryExecution qe4 = QueryExecutionFactory.create(query4,
							model);
					com.hp.hpl.jena.query.ResultSet results4 = qe4.execSelect();

					while (results4.hasNext()) {
						aux = results4.next();
						out.print("Species: "
								+ aux.get("specieslabel").toString() + "<br>");
					}
					out.print("</div>");

					//out.print( "<p>This is the main content. To display a lightbox click <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='block';document.getElementById('fade').style.display='block'\">here</a></p> <div id=\"light\" class=\"white_content\">This is the lightbox content. <a href = \"javascript:void(0)\" onclick = \"document.getElementById('light').style.display='none';document.getElementById('fade').style.display='none'\">Close</a></div> <div id=\"fade\" class=\"black_overlay\"></div>");
					out.print("<div id=\"light\" class=\"white_content\"> </div> <div id=\"fade\" class=\"black_overlay\"></div>");

				}
				qe.close();
			}
		%>
	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="assets/js/jquery.js"></script>
	<script src="dist/js/bootstrap.min.js"></script>

</body>
</html>