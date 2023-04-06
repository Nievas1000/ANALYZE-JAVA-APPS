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
    PostRequest request = new PostRequest();
    DataBaseData db = new DataBaseData();
    ClassParser cp = new ClassParser();

    @Test
    public void toJson() {

        try {
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

            String json = main.toJSON(map, "no", "c6j76d7931a0a04bed50", "",null);

            assertEquals("{\"userApplicationKey\":\"c6j76d7931a0a04bed50\",\"classes\":[{\"name\":\"pruebaci.classes.Auto\"},"
                    + "{\"name\":\"pruebaci.classes.Moto\"}],\"applicationName\":\"PruebaCI\"}", json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void verificationKey() {

        try {
            Integer response = request.VerificationKey("c6j76d7931a0a04bed50");
            assertEquals(response.toString(), "200");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postRequest() {
        try {
            Gson gson = new Gson();
            HashMap<String, String> map = new HashMap<>();
            JsonObject json = gson.toJsonTree(map).getAsJsonObject();
            Integer response = request.PostRequest(json);
            assertEquals(response.toString(), "200");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void getTables(){
//        try{
//        List<String> tablesdb=new ArrayList<String>();
//        List<String> tablesdbtest=new ArrayList<String>();
//        tablesdbtest.add("cliente");
//        
//        tablesdb=db.getTables("mysql", "sql.freedb.tech", "3306", "freedb_practicas", "freedb_leonardo", "kWPgVw8z5Bg?9MU");
//        assertEquals(tablesdbtest , tablesdb);
//        }catch(Exception e){
//         e.printStackTrace();
//    }
//    }
    @Test
    public void main() {
        String json = "";
        String jsonexpected = "{\"userApplicationKey\":\"c6j76d7931a0a04bed50\",\"classes\":"
                + "[{\"name\":\"pruebaci.classes.Auto\",\"extend\":[\"pruebaci.classes.Moto\"]}"
                + ",{\"name\":\"pruebaci.classes.Moto\",\"constructor\":[\"pruebaci.classes.Auto\"]"
                + ",\"datasources\":[\"cliente\"]},{\"name\":\"pruebaci.PruebaCI\"}],"
                + "\"applicationName\":\"PruebaCI\"}";
        
        String hashexpected = null;
        String hashnow = null;
        String path = System.getProperty("user.dir").concat("/PruebaCI");
        try {
            json = main.implementacion(path, "c6j76d7931a0a04bed50", "no","PruebaCI");
           
            hashexpected = cp.ObtenerHASHMD5(jsonexpected);
            hashnow = cp.ObtenerHASHMD5(json);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
           
            assertEquals(hashexpected, hashnow);
        }
    }

    @Test
    public void noProjectFound() {

        try {
            main.implementacion("dsada", "asdasd", "asdad","");
        } catch (Exception ex) {
            assertEquals("No .java file found in dsada or it's subfolders", ex.getMessage());

            
       }
       }
    @Test
    public void userKeyInvalid() {
        Integer response = null;

        

        try {
            response = request.VerificationKey("***");

        } catch (Exception ex) {
            System.out.println(response);

//             assertEquals(response.toString(), "403");
//             System.out.println(response.toString() + "resposneeeee");
            assertEquals("USER.APPLICATION.KEY is incorrect." 
                    + " Find your USER.APPLICATION.KEY at https://app.codojo.io/how-to-add-application " + "\n"
                    + "and set the USER.APPLICATION.KEY variable in " + System.getProperty("user.dir") + "\\SendToCodojo.config.properties",ex.getMessage());
        }
    }
    
    @Test
    public void constructortest(){
        String path = System.getProperty("user.dir") + "/test/constructors";
        String json = null;
        String jsonexpected = "{\"userApplicationKey\":\"c6j76d7931a0a04bed50\",\"classes\":[{\"name\":\"classes.Construct\",\"constructor\""
                + ":[\"classes.Construct1\"]},{\"name\":\"classes.Construct1\",\"constructor\""
                + ":[\"classes.Construct3\",\"classes.Construct4\",\"classes.Construct5\",\"classes.Construct6\",\"classes.Construct2\"]}"
                + ",{\"name\":\"classes.Construct2\"},{\"name\":\"classes.Construct3\"},{\"name\":\"classes.Construct4\"}"
                + ",{\"name\":\"classes.Construct5\"},{\"name\":\"classes.Construct6\"},{\"name\":\"constructors.Constructors\"}"
                + ",{\"name\":\"Service.ConstructService\",\"constructor\":[\"classes.Construct\",\"classes.Construct1\"]}]"
                + ",\"applicationName\":\"constructors\"}";
        
       
        try {
     //      jsonexpected=cp.ObtenerHASHMD5(jsonexpected);
            json = main.implementacion(path, "c6j76d7931a0a04bed50", "no","");
       //    json=cp.ObtenerHASHMD5(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
          
            assertEquals(jsonexpected.length(), json.length());
        }
    }
    
    public void interfacetest(){
           String path = System.getProperty("user.dir") + "/test/interfaces";
       String jsonexpected="{\"userApplicationKey\":\"c6j76d7931a0a04bed50\",\"classes\":"
               + "[{\"name\":\"Classes.Interfaces1\",\"extend\":"
               + "[\"Classes.Interfaces4\"],\"implement\":[\"interfaces.In1\",\"interfaces.In2\"],\"constructor\":"
               + "[\"interfaces.In2\",\"interfaces.In1\"]},{\"name\":\"Classes.Interfaces2\"},"
               + "{\"name\":\"Classes.Interfaces3\",\"implement\":[\"interfaces.In2\",\"interfaces.In1\"],"
               + "\"constructor\":[\"interfaces.In2\",\"interfaces.In1\"]},{\"name\":\"Classes.Interfaces4\","
               + "\"implement\":[\"interfaces.In1\",\"interfaces.In2\"],\"constructor\":[\"interfaces.In2\","
               + "\"interfaces.In1\"]},{\"name\":\"Classes.Interfaces5\",\"implement\":[\"interfaces.In1\",\"interfaces.In2\"],"
               + "\"constructor\":[\"interfaces.In2\",\"interfaces.In1\"]},{\"name\":\"interfaces.In1\",\"interfaz\":true},"
               + "{\"name\":\"interfaces.In2\",\"interfaz\":true},{\"name\":\"main.Interfaces\"}],\"applicationName\":\"interfaces\"}";
       
       String json=null;
        
       try {
     //      jsonexpected=cp.ObtenerHASHMD5(jsonexpected);
            json = main.implementacion(path, "c6j76d7931a0a04bed50", "no","");
       //    json=cp.ObtenerHASHMD5(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
            assertEquals(jsonexpected, json);
        }
    }
    
    @Test
    public void extendstest(){
        String path = System.getProperty("user.dir") + "/test/extends";
       String jsonexpected="{\"userApplicationKey\":\"c6j76d7931a0a04bed50\",\"classes\":[{\"name\":\"Classes.Extend1\",\"extend\":"
               + "[\"Classes.Extend2\"]},{\"name\":\"Classes.Extend2\",\"extend\":"
               + "[\"Classes.Extend3\"]},{\"name\":\"Classes.Extend3\",\"extend\":"
               + "[\"Classes.Extend4\"]},{\"name\":\"Classes.Extend4\",\"extend\":"
               + "[\"Classes.Extend5\"],\"implement\":[\"Classes.Int1\"]},{\"name\":\"Classes.Extend5\"}"
               + ",{\"name\":\"Classes.Int1\",\"interfaz\":true},{\"name\":\"pkgextends.Extends\"}],"
               + "\"applicationName\":\"extends\"}";
       
       String json=null;
        
       try {
     //      jsonexpected=cp.ObtenerHASHMD5(jsonexpected);
            json = main.implementacion(path, "c6j76d7931a0a04bed50", "no","");
       //    json=cp.ObtenerHASHMD5(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           
            assertEquals(jsonexpected.length(), json.length());
        }
    }
    
    @Test
    public void tablestest(){
        String path = System.getProperty("user.dir") + "/test/relationsjpa";
       String jsonexpected="{\"userApplicationKey\":\"c6j76d7931a0a04bed50\",\"classes\":"
               + "[{\"name\":\"Classes.Foo1\",\"constructor\":[\"Classes.Foo2\"],\"datasources\":"
               + "[\"foo2\",\"foo1_foos3\"]},{\"name\":\"Classes.Foo2\",\"constructor\":"
               + "[\"Classes.Foo3\"],\"datasources\":[\"foo2s_foo3\",\"foo2s_foo1s\"]},"
               + "{\"name\":\"Classes.Foo3\"},{\"name\":\"relationsjpa.Relationsjpa\"}],"
               + "\"applicationName\":\"relationsjpa\"}";
       
       String json=null;
        
       try {
     //      jsonexpected=cp.ObtenerHASHMD5(jsonexpected);
            json = main.implementacion(path, "c6j76d7931a0a04bed50", "no","");
       //    json=cp.ObtenerHASHMD5(json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            
            assertEquals(jsonexpected.length(), json.length());
        }
    }
    
    
}
    

  

