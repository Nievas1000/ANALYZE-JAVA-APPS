package Requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import com.google.gson.JsonObject;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.api.ApiTestResponse;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.BasicConfigurator;

public class PostRequest {
    SlackIntegration sl=new SlackIntegration();
    //Este metodo manda el json a la api de aws

    public Integer PostRequest(JsonObject json) throws Exception  {
         String str = "";
         
        
        
            
            URL url = new URL("https://3itqr368e0.execute-api.us-east-1.amazonaws.com/test/codojo");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            conexion.setRequestProperty("authorizationToken", "cLIclvsgcawKWDwkYszKw73ph25pJl");
            conexion.setRequestProperty("x-api-key", "zEba5xqtOz98eYdZ2GJWh4SBxMlGo4cM37C2rxSN");
            conexion.setDoOutput(true);
            OutputStream output = conexion.getOutputStream();     
            output.write(json.toString().getBytes());
            //limpia
            output.flush();
            //cierra la conexion
            output.close();
            //trae la response de la db
            Reader in = new BufferedReader(new InputStreamReader(conexion.getInputStream(), "UTF-8"));
            //System.out.println(conexion.getResponseCode());
//            JOptionPane.showMessageDialog(null, conexion.getResponseCode());
            //itera la response
            for (int c; (c = in.read()) >= 0;) {
                str = str + (char) c;
            }
            
//            System.out.println(str);
//            JOptionPane.showMessageDialog(null, str);
       return conexion.getResponseCode();
        
    
      
    }

    public Integer VerificationKey(String userkey) throws MalformedURLException, IOException, SlackApiException {
        String str = "";
        Integer response = null;
        try {
            HashMap<String, String> map = new HashMap<>();
            map.put("code", userkey);

            Gson gson = new Gson();
            JsonObject json = gson.toJsonTree(map).getAsJsonObject();

            URL url = new URL("https://d385kulk3g.execute-api.us-east-1.amazonaws.com/dev/getUserToApp");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            //conexion.setRequestProperty("authorizationToken", "cLIclvsgcawKWDwkYszKw73ph25pJl");
            conexion.setRequestProperty("x-api-key", "EqxfNm57Pn4OFEwE2NGIn5SE2r7jGUHj1WjeVvTc");
            conexion.setDoOutput(true);
            OutputStream output = conexion.getOutputStream();
            output.write(json.toString().getBytes());
            response = conexion.getResponseCode();
            //limpia
            output.flush();
            //cierra la conexion
            output.close();
            //trae la response de la db
            Reader in = new BufferedReader(new InputStreamReader(conexion.getInputStream(), "UTF-8"));
            //response=conexion.getResponseCode();
//            JOptionPane.showMessageDialog(null, conexion.getResponseCode());
            //itera la response
            for (int c; (c = in.read()) >= 0;) {
                str = str + (char) c;
            }

//            JOptionPane.showMessageDialog(null, str);
        } catch (Exception e) {
            System.out.println("USERKEY INVALID");
            sl.send("USERKEY INVALID");
//            JOptionPane.showMessageDialog(null, "USERKEY INVALID");
            return response;
        }

        return response;
    }


}
