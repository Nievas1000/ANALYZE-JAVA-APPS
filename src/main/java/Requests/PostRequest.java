package Requests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonObject;
import javax.swing.JOptionPane;

public class PostRequest {
	
	public static void PostRequest(JsonObject json) throws MalformedURLException {
		try {
			URL url=new URL("https://3itqr368e0.execute-api.us-east-1.amazonaws.com/test/codojo");
			HttpURLConnection conexion=(HttpURLConnection) url.openConnection();
			conexion.setRequestMethod("POST");
			conexion.setRequestProperty("Content-Type", "application/json");
			conexion.setRequestProperty("authorizationToken","cLIclvsgcawKWDwkYszKw73ph25pJl");
			conexion.setRequestProperty("x-api-key", "zEba5xqtOz98eYdZ2GJWh4SBxMlGo4cM37C2rxSN");
			conexion.setDoOutput(true);
			OutputStream output=conexion.getOutputStream();
			output.write(json.toString().getBytes());
			output.flush();
			output.close();
			Reader in = new BufferedReader(new InputStreamReader(conexion.getInputStream(), "UTF-8"));
			System.out.println(conexion.getResponseCode());
			for (int c; (c = in.read()) >= 0;)
	            System.out.print((char)c);
			
		} catch (Exception e) {
			e.printStackTrace();
                        JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		
	}

}
