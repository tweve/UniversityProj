import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;












import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.FileManager;

public class PopulateTDB {

	static Model model = null;
	static OntModel om = null;
	static String uriBase = "http://www.owl-ontologies.com/Plantas.owl#";
	static String file = "MedicinalPlants.owl";
	static String tripleDBOut = "tripleDB17";
	static int plantpart_id = 0;
	static int disease_id = 0;
	static int property_id = 0;
	static int family_id = 0;
	static int genre_id = 0;
	static int species_id = 0;

	static ArrayList <Plant> plants = null;

	public static void main(String args[]) {

		model = TDBFactory.createDataset(tripleDBOut).getDefaultModel();
		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM_TRANS_INF);
		om = ModelFactory.createOntologyModel(spec,model);


		InputStream in;
		in = FileManager.get().open(file);
		if (in == null) {
			throw new IllegalArgumentException("File: " + file + " doesn't exist!");
		}
		else{
			System.out.println("Success");
		}

		model.read(in, null);
		DB db = new DB();
		db.connect();

		plants = new ArrayList<Plant>();
		
		OntClass PlantClass = om.getOntClass( uriBase + "Plant" );
		Literal label = model.createLiteral("plant");
		PlantClass.addLabel(label);
		
		OntClass DiseaseClass = om.getOntClass( uriBase + "Disease" );
		//Literal label1 = model.createLiteral("disease");
		//DiseaseClass.addLabel(label1);
		
		OntClass MPClass = om.getOntClass( uriBase + "MedicalProperty" );
		//Literal label2 = model.createLiteral("property");
		//MPClass.addLabel(label2);
		
		OntClass PPClass = om.getOntClass( uriBase + "PlantPart" );
		//Literal label3 = model.createLiteral("part");
		//PPClass.addLabel(label3);
		
		
		for (int db_id = 1;db_id<799;db_id++){
			System.out.println(db_id);
			Plant p = db.select("select * from plants where id ="+db_id);
			addPlant(p);
		}
		
		
		/*String queryString = 
				"PREFIX medicinal_plants: <http://www.owl-ontologies.com/Plantas.owl#>"+
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
				"prefix rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
						
				 "SELECT  *  WHERE {?x  medicinal_plants:hasTreatmentFor  ?disease ."+
				 					"?disease rdfs:label \"Epilepsy\". "
				 					+ "?x medicinal_plants:hasClassification ?class."+
				 					"}"
				 				
				 					
				 ;

		Query query = QueryFactory.create(queryString);

		// procura uri sabendo a label
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();
		ResultSetFormatter.out(System.out, results, query);
*/
		
		
		model.commit();

		String fileName = "populated_model.rdf";
		FileWriter out = null;
		try {
			out = new FileWriter( fileName );
		    model.write( out, "RDF/XML-ABBREV" );
		    out.close();
		}
		catch (IOException closeException) {
		       
		}
		
		
		System.out.println("*************************************");

		model.close();
	}

	static void addPlant(Plant p){

		OntClass PlantClass = om.getOntClass( uriBase + "Plant" );
		Individual plant = om.createIndividual( uriBase+ "Plant"+p.id, PlantClass );	


		addDescription(p, plant);
		addEdibleParts(p, plant);
		addMedicalProperty(p, plant);
		addTreatmentFor(p, plant);
		addClassification(p, plant);
		addTreatmentPart(p, plant);
		
	
	}

	private static void addClassification(Plant p, Resource plant) {
		if (p.scientific_name == null)
			return;
		
		p.scientific_name = p.scientific_name.trim();
		String[] classification = p.scientific_name.split(" ");
		
		String family = p.family_name.trim();
		String genre = classification[0].trim();
		String species = "";
		for (int i = 1;i<classification.length;i++){
			species += classification[i]+" ";
		}
		species = species.trim();
	
		String queryString =  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "				SELECT ?uri { ?uri rdfs:label \""+family+"\" }";

		Query query = QueryFactory.create(queryString);

		// procura uri sabendo a label
		QueryExecution qe = QueryExecutionFactory.create(query, model);
		com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();

		try{
			// Se tem linka
			QuerySolution temp = results.next();
			RDFNode uri = temp.get("uri");
			//System.out.println("encontra");
			//System.out.println(uri.toString());
			qe.close();
			
			OntResource Family =  om.createOntResource(uri.toString());
			Property HasClassification = model.createProperty(uriBase,"hasClassification");
			plant.addProperty(HasClassification,Family);

		}
		catch (Exception e){
			// se nao tem adiciona e linka
			//System.out.println("nao encontra");
			
			OntClass FamilyClass = om.getOntClass( uriBase + "Family" );
			Individual Family = om.createIndividual( uriBase+ "Family"+family_id, FamilyClass );

			
			//OntResource Family =  om.createOntResource(uriBase+ "Family"+family_id);
			family_id++;
			
			Literal label = model.createLiteral(family);
			Family.addLabel(label);
			Property hasClassification = model.createProperty(uriBase,"hasClassification");
			plant.addProperty(hasClassification,Family);
		}

		
		
		
		queryString =  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "				SELECT ?uri { ?uri rdfs:label \""+genre+"\" }";
		query = QueryFactory.create(queryString);

		// procura uri sabendo a label
		qe = QueryExecutionFactory.create(query, model);
		results =  qe.execSelect();

		try{
			// Se tem linka
			QuerySolution temp = results.next();
			RDFNode uri = temp.get("uri");
			//System.out.println("encontra");
			//System.out.println(uri.toString());
			qe.close();

			OntResource Genre =  om.createOntResource(uri.toString());
			Property HasClassification = model.createProperty(uriBase,"hasClassification");
			plant.addProperty(HasClassification,Genre);

		}
		catch (Exception e){
			// se nao tem adiciona e linka
			//System.out.println("nao encontra");
			
			OntClass GenderClass = om.getOntClass( uriBase + "Genre" );
			Individual Genre = om.createIndividual(uriBase+ "Genre"+genre_id, GenderClass );
			
			genre_id++;

			Literal label = model.createLiteral(genre);
			Genre.addLabel(label);
			Property hasClassification = model.createProperty(uriBase,"hasClassification");
			plant.addProperty(hasClassification,Genre);
		}

		
		queryString =  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "				SELECT ?uri { ?uri rdfs:label \""+species+"\" }";
		query = QueryFactory.create(queryString);

		// procura uri sabendo a label
		qe = QueryExecutionFactory.create(query, model);
		results =  qe.execSelect();

		try{
			// Se tem linka
			QuerySolution temp = results.next();
			RDFNode uri = temp.get("uri");
			//System.out.println("encontra");
			//System.out.println(uri.toString());
			qe.close();

			OntResource Species =  om.createOntResource(uri.toString());
			Property HasClassification = model.createProperty(uriBase,"hasClassification");
			plant.addProperty(HasClassification,Species);

		}
		catch (Exception e){
			// se nao tem adiciona e linka
			//System.out.println("nao encontra");
			OntClass SpeciesClass = om.getOntClass( uriBase + "Species" );
			Individual Species = om.createIndividual(uriBase+ "Species"+species_id, SpeciesClass );
			
			species_id++;

			Literal label = model.createLiteral(species);
			Species.addLabel(label);
			Property hasClassification = model.createProperty(uriBase,"hasClassification");
			plant.addProperty(hasClassification,Species);
		}
		
		
	}

	private static void addTreatmentPart(Plant p, Resource plant) {
		if (p.treatment_parts == null)
			return;
		
		String[] parts= p.treatment_parts.split("-");
		
		for (String part :parts){
		
			part = part.trim();
			if (part.equals("")){
				continue;
			}
			//System.out.println(part);
			
			String queryString =  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ "				SELECT ?uri { ?uri rdfs:label \""+part+"\" }";

			Query query = QueryFactory.create(queryString);

			// procura uri sabendo a label
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();

			try{
				// Se tem linka
				QuerySolution temp = results.next();
				RDFNode uri = temp.get("uri");
				//System.out.println("encontra");
				//System.out.println(uri.toString());
				qe.close();

				OntResource PlantPart =  om.createOntResource(uri.toString());
				Property hasPlantPart = model.createProperty(uriBase,"hasPlantPart");
				plant.addProperty(hasPlantPart,PlantPart);

			}
			catch (Exception e){
				// se nao tem adiciona e linka
				//System.out.println("nao encontra");
				OntClass PPClass = om.getOntClass( uriBase + "PlantPart" );
				Individual PlantPart = om.createIndividual( uriBase+ "PlantPart"+plantpart_id, PPClass );
				
				plantpart_id++;

				Literal label = model.createLiteral(part);
				PlantPart.addLabel(label);
				Property hasPlantPart = model.createProperty(uriBase,"hasPlantPart");
				plant.addProperty(hasPlantPart,PlantPart);
			}

		}
		
		
	}

	private static void addMedicalProperty(Plant p, Resource plant) {
		if (p.medical_properties == null)
			return;
		String[] properties= p.medical_properties.split("-");
		
		for (String property :properties){
		
			property = property.trim();
			if (property.equals("")){
				continue;
			}
			//System.out.println(property);
			
			String queryString =  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ "				SELECT ?uri { ?uri rdfs:label \""+property+"\" }";

			Query query = QueryFactory.create(queryString);

			// procura uri sabendo a label
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();

			try{
				// Se tem linka
				QuerySolution temp = results.next();
				RDFNode uri = temp.get("uri");
				//System.out.println("encontra");
				//System.out.println(uri.toString());
				qe.close();

				OntResource MedicinalProperty =  om.createOntResource(uri.toString());
				Property hasMedicalProperty = model.createProperty(uriBase,"hasMedicalProperty");
				plant.addProperty(hasMedicalProperty,MedicinalProperty);

			}
			catch (Exception e){
				// se nao tem adiciona e linka
				//System.out.println("nao encontra");
				
				OntClass MPClass = om.getOntClass( uriBase + "MedicalProperty" );
				Individual MedicinalProperty = om.createIndividual( uriBase+ "MedicalProperty"+property_id, MPClass );
				property_id++;

				Literal label = model.createLiteral(property);
				MedicinalProperty.addLabel(label);
				Property hasMedicalProperty = model.createProperty(uriBase,"hasMedicalProperty");
				plant.addProperty(hasMedicalProperty,MedicinalProperty);
			}

		}
		
		
	}

	private static void addTreatmentFor(Plant p, Resource plant) {
		if (p.treatment_for == null)
			return;
		String[] diseases= p.treatment_for.split("-");
		
	
		for (String disease :diseases){
		
			disease = disease.trim();
			if (disease.equals("")){
				continue;
			}
			//System.out.println(disease);
			
			String queryString =  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ "				SELECT ?uri { ?uri rdfs:label \""+disease+"\" }";

			Query query = QueryFactory.create(queryString);

			// procura uri sabendo a label
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();

			try{
				// Se tem linka
				QuerySolution temp = results.next();
				RDFNode uri = temp.get("uri");
				//System.out.println("encontra");
				//System.out.println(uri.toString());
				qe.close();

				OntResource Disease =  om.createOntResource(uri.toString());
				Property hasTreatmentFor = model.createProperty(uriBase,"hasTreatmentFor");
				plant.addProperty(hasTreatmentFor,Disease);

			}
			catch (Exception e){
				// se nao tem adiciona e linka
				//System.out.println("nao encontra");
				
				OntClass DiseaseClass = om.getOntClass( uriBase + "Disease" );
				Individual Disease = om.createIndividual( uriBase+ "Disease"+disease_id, DiseaseClass );

				disease_id++;

				Literal label = model.createLiteral(disease);
				Disease.addLabel(label);
				Property hasTreatmentFor = model.createProperty(uriBase,"hasTreatmentFor");
				plant.addProperty(hasTreatmentFor,Disease);
			}

		}
		
	}


	static void addDescription(Plant p, Resource plant){
		if (p.description == null)
			return;
		if (p.description.equals(""))
			return;
		
		Property hasDescription = model.createProperty(uriBase,"hasDescription");
		Literal PlantDescription = model.createLiteral(p.description+"");
		plant.addLiteral(hasDescription, PlantDescription);
	}

	static void addEdibleParts(Plant p, Resource plant){
		
		if (p.edible_parts == null)
			return;
		String[] edible_parts = p.edible_parts.split("(?=[A-Z])");

		for (String edible_part :edible_parts){
	
			if (edible_part.equals("")){
				continue;
			}
			edible_part = edible_part.trim();
			//System.out.println(edible_part);
			String queryString =  "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
					+ "				SELECT ?uri { ?uri rdfs:label \""+edible_part+"\" }";

			Query query = QueryFactory.create(queryString);

			// procura uri sabendo a label
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			com.hp.hpl.jena.query.ResultSet results =  qe.execSelect();

			try{
				// Se tem linka
				QuerySolution temp = results.next();
				RDFNode uri = temp.get("uri");
				//System.out.println("encontra");

				//System.out.println(uri.toString());
				qe.close();

				OntResource EdiblePart =  om.createOntResource(uri.toString());
				Property hasEdiblePart = model.createProperty(uriBase,"hasEdiblePart");
				
				plant.addProperty(hasEdiblePart,EdiblePart);
				

			}
			catch (Exception e){
				// se nao tem adiciona e linka
				//System.out.println("n ao encontra");
				
				OntClass PPClass = om.getOntClass( uriBase + "PlantPart" );
				Individual EdiblePart = om.createIndividual( uriBase+ "PlantPart"+plantpart_id, PPClass);

				plantpart_id++;

				Literal label = model.createLiteral(edible_part);
				EdiblePart.addLabel(label);
				Property hasEdiblePart = model.createProperty(uriBase,"hasEdiblePart");
				plant.addProperty(hasEdiblePart,EdiblePart);
			}

		}

	}

}
