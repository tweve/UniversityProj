import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class MedicinalPlants {
	
	static DB db= new DB();
	public static void main(String[] args) {

		try{

			db.connect();

			URL website = new URL("http://www.instituteofayurveda.org/plants/plants_list.php?s=Scientific_name");
			BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));

			Pattern MY_PATTERN = Pattern.compile("'[0-9]*','",Pattern.DOTALL);
			Pattern Number = Pattern.compile("[0-9]*",Pattern.DOTALL);

			String htmlSource = "";

			String inputLine;
			while ((inputLine = in.readLine()) != null){
				htmlSource += inputLine+"\n";
			}

			Matcher m = MY_PATTERN.matcher(htmlSource);

			while(m.find()) {
				String aux = m.group(0);
				Matcher m2 = Number.matcher(aux);
				while(m2.find()) {
					try{
						int aux2 = Integer.parseInt(m2.group(0));

						plantDataExtraction("http://www.instituteofayurveda.org/plants/plants_detail.php?i="+aux2+"&s=Scientific_name");

					}
					catch (Exception e){
					}
				}
			}

			in.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}




		// ---------------------------------------------------


		/*	// some definitions
		String plantURI    = "http://somewhere/Absinto";
		String name    = "Eucalyptus";
		String scientificName   = "Eucalyptus kitsoniana";

		// create an empty model
		Model model = ModelFactory.createDefaultModel();

		// create the resource
		//   and add the properties cascading style
		Resource eucalyptus 
		= model.createResource(plantURI)
		.addProperty(VCARD.N, name)
		.addProperty(VCARD.N, scientificName);

		// list the statements in the graph
		StmtIterator iter = model.listStatements();

		model.write(System.out, "N-TRIPLES");
		 */
	}

	private static void plantDataExtraction(String url) {
		try{

			URL website = new URL(url);
			BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));

			//Pattern MY_PATTERN = Pattern.compile("Scientific name:(.*)<i>(.*)</i>",Pattern.DOTALL);
			Pattern MY_PATTERN = Pattern.compile("<!--Plants Details Start-->(.*)<!--Plants Details End-->",Pattern.DOTALL);

			//Pattern MY_PATTERN = Pattern.compile("href='(.*?)'");


			String htmlSource = "";
			String newString = "";
			String allPlants = "";

			String inputLine;
			while ((inputLine = in.readLine()) != null){
				htmlSource += inputLine+"\n";
			}
			in.close();
			//System.out.println(htmlSource);

			Matcher m = MY_PATTERN.matcher(htmlSource);

			while(m.find()) {
				newString = m.group(0);
			}
			ArrayList <String> insertfields = new ArrayList<String>();
			ArrayList <String> insert = new ArrayList<String>();

			// Scientific name://////////////////////////////////////////////////////////////////
			MY_PATTERN = Pattern.compile("Scientific name:(.*)<i>(.*)</i>",Pattern.DOTALL);
			m = MY_PATTERN.matcher(newString);
			while(m.find()) {
				String sn = m.group(2);
				sn = sn.replace("*", "");
				sn = sn.replace("$", "");
				sn = sn.replace("@", "");


				int contains = 0;
				if (m.group(2).contains("*")||m.group(2).contains("$")||m.group(2).contains("@")){
					contains = 1;
				}
				if (contains == 0){
					return;
				}
				allPlants+="Scientific name: "+sn+"\n";
				insert.add(sn);
				insertfields.add("scientific_name");
			}
			// Family name://////////////////////////////////////////////////////////////////////
			MY_PATTERN = Pattern.compile("Family name(.*)\"top\">(.*)</td>(.*)English",Pattern.DOTALL);
			m = MY_PATTERN.matcher(newString);
			while(m.find()) {
				allPlants+="Family name: "+m.group(2)+"\n";
				insert.add(m.group(2));
				insertfields.add("family_name");
			}
			// English name://////////////////////////////////////////////////////////////////////
			MY_PATTERN = Pattern.compile("English name(.*)\"top\">(.*)</td>(.*)Local",Pattern.DOTALL);
			m = MY_PATTERN.matcher(newString);
			while(m.find()) {
				allPlants+="English name: "+m.group(2)+"\n";
				insert.add(m.group(2));
				insertfields.add("english_name");
			}
			// Local name://////////////////////////////////////////////////////////////////////
			MY_PATTERN = Pattern.compile("Local name(.*)\"top\">(.*)</td>(.*)Description",Pattern.DOTALL);
			m = MY_PATTERN.matcher(newString);
			while(m.find()) {
				allPlants+="Local name: "+m.group(2)+"\n";
				insert.add(m.group(2));
				insertfields.add("local_name");
			}
			// Description://////////////////////////////////////////////////////////////////////
			MY_PATTERN = Pattern.compile("Description(.*)\"top\">(.*)</td>(.*)Status",Pattern.DOTALL);
			m = MY_PATTERN.matcher(newString);
			while(m.find()) {
				allPlants+="Description: "+m.group(2)+"\n";
				insert.add(m.group(2));
				insertfields.add("description");
			}
			// Status://////////////////////////////////////////////////////////////////////
			MY_PATTERN = Pattern.compile("Status(.*)\"top\">(.*)</td>(.*)Edible",Pattern.DOTALL);
			m = MY_PATTERN.matcher(newString);
			while(m.find()) {
				allPlants+="Status: "+m.group(2)+"\n";
				insert.add(m.group(2));
				insertfields.add("status");
			}
			// Edible parts://////////////////////////////////////////////////////////////////////
			MY_PATTERN = Pattern.compile("Edible parts(.*)\"top\">(.*)</td>(.*)Ayurvedic",Pattern.DOTALL);
			m = MY_PATTERN.matcher(newString);
			while(m.find()) {
				allPlants+="Edible parts: "+m.group(2)+"\n";
				insert.add(m.group(2));
				System.out.println(m.group(2));
				insertfields.add("edible_parts");
			}
			// Status://////////////////////////////////////////////////////////////////////
			MY_PATTERN = Pattern.compile("Treatment for(.*)PARTS",Pattern.DOTALL);
			m = MY_PATTERN.matcher(newString);
			while(m.find()) {
				String temp = m.group(1);
				String plainTextBody = Jsoup.parse(temp).text();
				plainTextBody = plainTextBody.replaceAll("-", "");
				plainTextBody = plainTextBody.replaceAll(","," ");
				plainTextBody = plainTextBody.replaceAll("[^\\p{Print}]","");
				plainTextBody = plainTextBody.replaceAll("  "," ");
				plainTextBody = plainTextBody.replaceAll("(.)([A-Z])", "$1-$2");


				plainTextBody = plainTextBody.trim();

				allPlants+="Treatment for: "+plainTextBody+"\n";
				insert.add(plainTextBody);
				insertfields.add("treatment_for");
			}
			MY_PATTERN = Pattern.compile("TREATMENT(.*)RELATED",Pattern.DOTALL);
			m = MY_PATTERN.matcher(newString);
			while(m.find()) {
				String temp = m.group(1);
				String plainTextBody = Jsoup.parse(temp).text();
				plainTextBody = plainTextBody.replaceAll("-", "");
				plainTextBody = plainTextBody.replaceAll(":","");
				plainTextBody = plainTextBody.replaceAll(","," ");
				plainTextBody = plainTextBody.replaceAll("[^\\p{Print}]","");
				plainTextBody = plainTextBody.replaceAll("  "," ");
				plainTextBody = plainTextBody.replaceAll("(.)([A-Z])", "$1-$2");


				plainTextBody = plainTextBody.trim();


				allPlants+="Parts used for treatment: "+plainTextBody+"\n";
				insert.add(plainTextBody);
				insertfields.add("parts_used");
			}
			MY_PATTERN = Pattern.compile("MEDICINAL PROPERTIES(.*)<!--Plants Details End-->",Pattern.DOTALL);
			m = MY_PATTERN.matcher(newString);
			while(m.find()) {
				String temp = m.group(1);
				String plainTextBody = Jsoup.parse(temp).text();
				plainTextBody = plainTextBody.replaceAll("-", "");
				plainTextBody = plainTextBody.replaceAll(":", "");
				plainTextBody = plainTextBody.replaceAll(","," ");
				plainTextBody = plainTextBody.replaceAll("[^\\p{Print}]","");
				plainTextBody = plainTextBody.replaceAll("  "," ");
				plainTextBody = plainTextBody.replaceAll("(.)([A-Z])", "$1-$2");

				plainTextBody = plainTextBody.trim();


				allPlants+="Medical properties: "+plainTextBody+"\n";
				insert.add(plainTextBody);
				insertfields.add("medical_properties");
			}
			allPlants+="__________________"+"\n";
			System.out.println(allPlants);

			String listString = "";
			int index = 0;
			for (String s : insert)
			{
				
				if (s != ""){
					if (index != insert.size()-1){
						listString +="'"+ s +"'"+",";
					}
					else
						listString +="'"+ s +"'";
					
				}
				index++;
			}


			String col_names = "";
			int index2 = 0;
			for (String s : insertfields)
			{
				
				if (s != ""){
					if (index2 != insertfields.size()-1){
						col_names += s +",";
					}
					else
						col_names +=s;
					
				}
				index2++;
			}

			
			
			//db.query("insert into plants ("+col_names+") values ("+listString+");");
			
			
		}
		catch (Exception e){
			e.printStackTrace();
		}

	}




}
