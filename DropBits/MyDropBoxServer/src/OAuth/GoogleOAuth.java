package OAuth;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

public class GoogleOAuth {

    private static final String NETWORK_NAME = "Google";
    private static final String AUTHORIZE_URL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=";
    private static final String PROTECTED_RESOURCE_URL = "https://docs.google.com/feeds/default/private/full/";
    private static final String SCOPE = "https://www.googleapis.com/auth/tasks";
    private static Token accessToken;
    private static OAuthService service;

    public static String addTask(String taskname) {

        OAuthRequest request = new OAuthRequest(Verb.POST, "https://www.googleapis.com/tasks/v1/lists/@default/tasks");
        request.addHeader("Content-Type", "application/json");
        request.addPayload("{'title': " + "'" + taskname + "'" + "}");
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println(response.getBody());
        String rsp = response.getBody();
        JSONObject item = (JSONObject) JSONValue.parse(rsp);

        return (String) item.get("id");
   
    }
    public static void deleteTask(String googleid) {


        OAuthRequest request = new OAuthRequest(Verb.DELETE, "https://www.googleapis.com/tasks/v1/lists/@default/tasks/"+ googleid);
        
        service.signRequest(accessToken, request);
       
        
        Response response = request.send();
        System.out.println(response.getBody());
        
    }
    public static void renameTask(String googleid, String newName) {

        

        OAuthRequest request = new OAuthRequest(Verb.PUT, "https://www.googleapis.com/tasks/v1/lists/@default/tasks/"+ googleid);
        
        request.addHeader("Content-Type", "application/json");
        
        request.addPayload("{"+
                "'id': "+"'"+googleid+"'" + ","
                +"'title': "+ "'"+newName+"'"      
                + "}");
        
        
        service.signRequest(accessToken, request);
        
        Response response = request.send();
        System.out.println(response.getBody());
        
    }

    public static void setup() {
        service = new ServiceBuilder().provider(GoogleApi.class).apiKey("99753242199.apps.googleusercontent.com").apiSecret("98dns-SoFcWl93pvBJYcdnk8").scope(SCOPE).build();


        ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream("accessToken"));
            accessToken = (Token) inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException e) {




            Scanner in = new Scanner(System.in);

            System.out.println("=== " + NETWORK_NAME + "'s OAuth Workflow ===");
            System.out.println();

            // Obtain the Request Token
            System.out.println("Fetching the Request Token...");
            Token requestToken = service.getRequestToken();
            System.out.println("Got the Request Token!");
            System.out.println("(if your curious it looks like this: " + requestToken + " )");
            System.out.println();

            System.out.println("Now go and authorize Scribe here:");
            System.out.println(AUTHORIZE_URL + requestToken.getToken());
            System.out.println("And paste the verifier here");
            System.out.print(">>");
            Verifier verifier = new Verifier(in.nextLine());
            System.out.println();

            // Trade the Request Token and Verfier for the Access Token
            System.out.println("Trading the Request Token for an Access Token...");
            accessToken = service.getAccessToken(requestToken, verifier);
            System.out.println("Got the Access Token!");


            String filename = "accessToken";
            File f = new File(filename);
            try{
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);


                oos.writeObject( accessToken );
                fos.close();
            }
            catch (IOException ioe){
            }

        } catch (ClassNotFoundException e) {
            System.out.println("userlist.bin\t>Corrupt file,Starting with blank user details.");
        } catch (Exception e) {
        }





    }
}