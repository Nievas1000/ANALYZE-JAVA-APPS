package tests;

import Classes.ClassDiscriptor;
import Classes.ClassParser;
import Classes.DataBaseData;
import Requests.PostRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Main;
import static org.junit.Assert.*;

import org.junit.Test;

public class Testapp {

    Main main = new Main();
    PostRequest request= new PostRequest();
    DataBaseData db=new DataBaseData();
    ClassParser cp=new ClassParser();

    @Test
    public void toJson() {

        try{
        HashMap<String, Object> map = new HashMap<>();
        List<Object> list = new ArrayList<Object>();
        ClassDiscriptor cd = new ClassDiscriptor();
        ClassDiscriptor cd1 = new ClassDiscriptor();
        File file = new File("prueba");
        cd.setName("pruebaci.classes.Auto");
        list.add(cd);
        cd1.setName("pruebaci.classes.Moto");
        list.add(cd1);

        map.put("userApplicationKey", "c6j76d7931a0a04bed50");
        map.put("applicationName", "PruebaCI");
        map.put("classes", list);

        String json = main.toJSON(map, "no", "c6j76d7931a0a04bed50", file);
        
        
        assertEquals("{\"userApplicationKey\":\"c6j76d7931a0a04bed50\",\"classes\":[{\"name\":\"pruebaci.classes.Auto\"},"
                + "{\"name\":\"pruebaci.classes.Moto\"}],\"applicationName\":\"PruebaCI\"}", json);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

//    @Test
//    public void verificationKey() throws MalformedURLException {
//        
//        Integer response=request.VerificationKey("c6j76d7931a0a04bed50");
//        assertEquals(response.toString(), "200");
//    }
    
    

    @Test
    public void postRequest(){
        try{
        Gson gson = new Gson();
        HashMap<String,String> map=new HashMap<>();
        JsonObject json = gson.toJsonTree(map).getAsJsonObject();
        Integer response=request.PostRequest(json);
        assertEquals(response.toString(),"200");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getTables(){
        try{
        List<String> tablesdb=new ArrayList<String>();
        List<String> tablesdbtest=new ArrayList<String>();
        tablesdbtest.add("cliente");
        
        tablesdb=db.getTables("mysql", "sql.freedb.tech", "3306", "freedb_practicas", "freedb_leonardo", "kWPgVw8z5Bg?9MU");
        assertEquals(tablesdbtest , tablesdb);
        }catch(Exception e){
         e.printStackTrace();
    }
    }
    @Test
    public void main() {
        String json="";
        String jsonexpected="{\"userApplicationKey\":\"j312312312gdfg\",\"classes\":"
                + "[{\"name\":\"pruebaci.classes.Auto\",\"extend\":\"pruebaci.classes.Moto\"},"
                + "{\"name\":\"pruebaci.classes.Moto\",\"constructor\":[\"pruebaci.classes.Auto\"],"
                + "\"datasources\":{\"OneToOne\":[\"cliente\"]}},{\"name\":\"pruebaci.PruebaCI\"}],"
                + "\"applicationName\":\"PruebaCI\"}";
        String hashexpected = null;
        String hashnow = null;
        String path=System.getProperty("user.dir").concat("/PruebaCI");
        try {
            json=main.implementacion(path, "j312312312gdfg", "no");
            hashexpected=cp.ObtenerHASHMD5(jsonexpected);           
            hashnow=cp.ObtenerHASHMD5(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        assertEquals(hashexpected, hashnow);
    }
    
    @Test
    public void noProjectFound(){
        
        try {
            main.implementacion("dsada", "asdasd", "asdad");
        } catch (Exception ex) {
            assertEquals("ERROR: No project found", ex.getMessage());
            
        }
        
        
        
    }
    
     @Test
    public void userKeyInvalid() {
        Integer response = null;
        try{
        response=request.VerificationKey("***");
        }catch(Exception ex){
             assertEquals(response.toString(), "403");
        }
    }

    
    }


