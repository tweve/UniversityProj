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
					<li ><a href="plants.jsp">Diseases</a></li>
					<li class="active"><a href="bByClass1.jsp">Plants</a></li>
					
				</ul>
				<div>
					<form class="navbar-form navbar-right" action='browseplants.jsp' method ='post'>
						<div class="form-group">
							<input type="text" name='query' placeholder="Semantic Search" size="60"
								class="form-control">
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

 			<%
				String startsBy = request.getParameter("d");
				char c = startsBy.charAt(0);
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

				String queryString = "PREFIX medp: <http://www.owl-ontologies.com/Plantas.owl#>"
						+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
						+ "prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
						+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"

						+ "SELECT DISTINCT ?family ?familyLabel WHERE {"
						+ "?family rdf:type medp:Family ."
						+ "?family rdfs:label ?familyLabel ."
						+ "FILTER regex(?familyLabel,\"^"+startsBy+"\")"
						+ "?plant medp:hasClassification ?family ."
						+ "}"
						+ "ORDER BY ?genreLabel";
				Query query = QueryFactory.create(queryString);

				// procura uri sabendo a label
				QueryExecution qe = QueryExecutionFactory.create(query, model);
				com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
				
				//ResultSetFormatter.out(System.out, results, query);

				HashMap<String, String> families = new HashMap<String, String>();

				while (results.hasNext()) {
					QuerySolution aux = results.next();
					String dislabel = aux.get("familyLabel").toString();
					String dis = aux.get("family").toString();

					families.put(dislabel, dis.replace(
							"http://www.owl-ontologies.com/Plantas.owl#", ""));
				}

				//System.out.println(diseases);
				qe.close();
			%>


			<ul >

				<%
					out.println("<li>"
					 + "<a class=\"browsinglink\" href=\"bByClass1.jsp?d="+ c + "\">" + c + "</a>");

					out.println("<ul>");

 					for (String d : families.keySet())
							out.print("<li>"
									+ "<a class=\"browsinglink\" href=\"bByClass3.jsp?d="
									+ d + "\">" + d + "</a>" + "</li>");
 					
					out.println("</ul>");
					out.println("</li>");


				%>
				
			</ul>

			<p></p>
		</div>
	</div>

	<div class="plant" id=divplant></div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="assets/js/jquery.js"></script>
	<script src="dist/js/bootstrap.min.js"></script>

</body>
</html>