package Requests;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class PostRequest {
    //Este metodo manda el json a la api de aws

    public static void PostRequest(JsonObject json) throws MalformedURLException {
        try {
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
            System.out.println(conexion.getResponseCode());
//            JOptionPane.showMessageDialog(null, conexion.getResponseCode());
            //itera la response
            for (int c; (c = in.read()) >= 0;) {
                str = str + (char) c;
            }
//            JOptionPane.showMessageDialog(null, str);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }
    
    public static Integer VerificationKey(String userkey) throws MalformedURLException {
        String str = "";
        Integer response=null;
        try {
              HashMap<String,String> map=new HashMap<>();
              map.put("code", userkey);
              
              Gson gson = new Gson();
              JsonObject json = gson.toJsonTree(map).getAsJsonObject();
           
            URL url = new URL("https://d385kulk3g.execute-api.us-east-1.amazonaws.com/dev/getUserToApp");
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/json");
            //conexion.setRequestProperty("authorizationToken", "cLIclvsgcawKWDwkYszKw73ph25pJl");
            //conexion.setRequestProperty("x-api-key", "zEba5xqtOz98eYdZ2GJWh4SBxMlGo4cM37C2rxSN");
            conexion.setDoOutput(true);
            OutputStream output = conexion.getOutputStream();
            output.write(json.toString().getBytes());
             response=conexion.getResponseCode();
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
            JOptionPane.showMessageDialog(null, "USERKEY INVALID");
            return null;
        }

    

         return response;
    }
}


