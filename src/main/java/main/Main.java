package main;

import Classes.ClassDiscriptor;
import Classes.ClassParser;
import Classes.DataBaseData;
import java.awt.Toolkit;
import Requests.PostRequest;
import Requests.SlackIntegration;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class Main {

    SlackIntegration sl = new SlackIntegration();
    List<File> files;

//    public static void main(String[] args) {
//        
//    }
    //Metodo principal que desencadena todo los procesos
    public String implementacion(String args, String userkey, String sendjson) throws Exception {
        //se hace una list con todos lo atributos de classdiscriptor
        List<ClassDiscriptor> cdList = new ArrayList();
        //map global 
        HashMap<String, String> map = new HashMap<>();
        //trae la direccion del archivo
        String sCarpAct = System.getProperty(args, args);
        //Se crea un nuevo file y se le pasa como argumento la direccion del archivo

        File filename = new File(sCarpAct);
        File file = new File(sCarpAct);
        try {

            //System.out.println(sCarpAct);
            //Se crea un nuevo file y se le pasa como argumento la direccion del archivo
// +  File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "com" );
//        System.out.println(file);
//        System.out.println(filename.getName());
            files = new ArrayList();
            if (file != null && file.exists()) {
                //trae el archivo
                mostrarCarpeta(file);
            }

            //Itera cada file si tiene name y no es null se agrega a una lista
            int cont = 0;
            Integer contclasses = 0;
            //este for se recorre 2 veces para que primero haga un paneo de todas las clases y guarde sus nombres y package en el map.
            //y despues la ultima iteracion es donde hace el parse de todos los datos obtenidos aparte de obtener mas data.
            for (int i = 0; i < 2; i++) {

                for (File f : files) {
//            System.out.println("File: " + f.getAbsolutePath());
                    if (f.exists()) {
                        contclasses += +1;
                    }

                    if (contclasses.toString().contains("000") && contclasses.toString().length() == 4) {
                        System.out.println(contclasses + " classes discovered…");
                    }
                    ClassParser parser = new ClassParser(f, map, i, cont);
                    ClassDiscriptor cd = parser.getDiscriptor();
                    cd.setPackageName(null);
                    if (cd != null && cd.name != null && i == 1) {
                        cdList.add(cd);
                    }
//              DatabaseDescriptor db = parser.getDBDescriptor();
//            if (db != null && db.db != null) {
//                dbList.add(db);
//            }
                }
            }
            cont = cont + 1;

        } catch (Exception e) {
            sl.send("Error scanning project", Arrays.toString(e.getStackTrace()));
             System.out.println("”The program did not complete successfully.”");
//            System.out.println("Error scanning project");
//            System.out.println("HELP: contact us to give you a solution");
        }

        if (files.isEmpty()) {
//            sl.send("ERROR: No project found", null);
            
            throw new Exception("No .java file found in "+ args +" or it’s subfolders");
             
        }

//        if (!db.getDb().isEmpty() && !db.getHost().isEmpty() && !db.getPort().isEmpty() && !db.getPassword().isEmpty() && !db.getUsername().isEmpty()) {
//            try {
//
//               
////                for (String string : lista) {
////                    System.out.println(string);
////                }
//            } catch (Exception e) {
//                System.out.println(e);
//                JOptionPane.showMessageDialog(null, e.getMessage());
//            }
//        }
//        StringBuilder builder = new StringBuilder();
//        for (ClassDiscriptor cd : cdList) {
//           builder.append("\r\nName: " + cd.name);
//            builder.append("\r\n\tInterfaz: " + cd.interfaz);          
//            builder.append("\r\n\tConstructors: ").append(cd.getConstructor());
//            builder.append("\r\n\tExtends: " + cd.extend);
//            builder.append("\r\n\tImplements: " + cd.implement);
//        builder.append("\r\n\tPackage: " + cd.packageName);
//        }
        // builder.append("\r\n\tLast Modified: " + cd.lastModified);
//            Long myLong = Long.parseLong(cd.lastModified);
//            Date dateD = new Date(myLong);
//            Calendar dateC = new GregorianCalendar();
//            dateC.setTime(dateD);
//            String dia, mes, anio, hora, minuto, segundo;
//            dia = Integer.toString(dateC.get(Calendar.DATE));
//            mes = Integer.toString(dateC.get(Calendar.MONTH));
//            anio = Integer.toString(dateC.get(Calendar.YEAR));
//            hora = Integer.toString(dateC.get(Calendar.HOUR_OF_DAY));
//            minuto = Integer.toString(dateC.get(Calendar.MINUTE));
//            segundo = Integer.toString(dateC.get(Calendar.SECOND));
//            cd.lastModified = dia + "/" + mes + "/" + anio + "     " + hora + ":" + minuto + ":" + segundo;
//
//            builder.append("\r\n\tLast Modified Date: " + dia + "/" + mes + "/" + anio + "     " + hora + ":" + minuto + ":" + segundo);
//            builder.append("\r\n\r\nDatabases:");
//		for(DatabaseDescriptor db:dbList) {
//			builder.append("\r\n\tSchema: "+db.db);
//			builder.append("\r\n\t\tDatabaseType: "+db.type);
//			builder.append("\r\n\t\tHost: "+db.host);
//			builder.append("\r\n\t\tPort: "+db.port);
//			builder.append("\r\n\t\tTables: [");
//			List<String> tables=db.getTables();
//			for(String table:tables) {
//				builder.append(table+", ");
//			}
//			builder.append("]\r\n");
//		}
//            builder.append("\r\n\tClass Members: ");
//            for (int i = 0; i < cd.members.size(); i++) {
//                Member m = cd.members.get(i);
//                builder.append("\r\n\t\t" + (i + 1) + ": " + m.member);
//                builder.append("\r\n\t\t Modifier: " + m.modifier);
//                builder.append("\r\n\t\t Type: " + m.type);
//
//            }
//       }
        String json = null;

        try {
            //Hashmap Generico
            HashMap<String, Object> mapjson = new HashMap();
            
            mapjson.put("userApplicationKey", userkey);
            mapjson.put("applicationName", filename.getName());
            mapjson.put("classes", cdList);

            //}
            json = toJSON(mapjson, sendjson, userkey, filename);
            StringSelection selection = new StringSelection(json);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            String user = System.getProperty("user.name");
            String dir = System.getProperty("user.dir");

            String pathFile = dir + "\\" + filename.getName() + ".json";
            try {
                //se escribe el json en el pathfile del usuario y se guarda alli
                FileWriter jsonFile = new FileWriter(pathFile);
                jsonFile.write(json);
                jsonFile.close();
            } catch (Exception e) {
//                System.out.println("Error generating json");
//                sl.send("Error generating json", e.getMessage());
//                e.printStackTrace();
//                System.out.println("HELP: contact us to give you a solution");
              System.out.println("”The program did not complete successfully.”");

            }

//            System.out.println("JSON: " + json);
        } catch (Exception e2) {
//            System.out.println("Error getting json");
//            sl.send("Error Error getting json", e2.getMessage());
//            e2.printStackTrace();
//            System.out.println("HELP: contact us to give you a solution");
            System.out.println("”The program did not complete successfully.”");

            // JOptionPane.showMessageDialog(null, e2.getMessage());
        }

        return json.toString();
    }

    //esta funcion 
    //Este metodo transforma un hashmap a json mediante la dependencia de google.gson
    public String toJSON(HashMap map, String sendjson, String userkey, File filename) throws IOException, Exception {
        Scanner scan = new Scanner(System.in);
        Gson gson = new Gson();
        JsonObject json = gson.toJsonTree(map).getAsJsonObject();
        ClassParser cp = new ClassParser();
        PostRequest p = new PostRequest();
        if(userkey.equals("j312312312gdfg")){
        System.out.println(cp.ObtenerHASHMD5(json.toString()));
        }
        try  {    
            if (p.VerificationKey(userkey) == 200) {
                if (sendjson.equalsIgnoreCase("yes")) {
                    p.PostRequest(json);
                    System.out.println("The results are now viewable at app.codojo.io");

                } else {
                    System.out.println("See <" + filename.getName() + "> to view application results. \n"
                            + "This file was not sent to Codojo. \n"
                            + "To send the results to Codojo, open the <SendToCodojo.config.properties>, set the variable “SEND_RESULTS_TO_CODOJO = true”, and rerun this application");

                }
            }
        } catch (Exception e) {
//            sl.send("Error sending or getting json", e.getMessage());
            System.out.println(e.getMessage());
            System.out.println("”The program did not complete successfully.”");
            
//            e.printStackTrace();
//
        }

//        

        return json.toString();
    }

    public void mostrarCarpeta(File fichero) throws Exception{
      
            if (fichero.isDirectory()) {
                File[] lista = fichero.listFiles();
                for (int i = 0; i < lista.length; i++) {
                    // System.out.println(lista[i].getName());
                    if (lista[i].isDirectory()) {

                        mostrarCarpeta(lista[i]);
                    }
                    if (lista[i].isFile() && lista[i].getName().endsWith(".java")) {

                        files.add(lista[i]);

                    }

                }
            }
//        } catch (Exception ex) {
//            sl.send("Error getting files", ex.getMessage());
//            System.out.println("Error getting files");
//            System.out.println("HELP: contact us to give you a solution");
//            ex.printStackTrace();
//
//        }
    }

    //Obtiene cada archivo y los agrega a una lista de files.
//    public void getFiles(File file) {
//        File[] subdir = file.listFiles();
//        for (File f : subdir) {
//            if (f.isFile() && f.getName().endsWith(".java")) {
//                files.add(f);
//            } else {
//                getFiles(f);
//            }
//        }
//    }
    //Este metodo obtiene los datos del archivo de propiedades y llama al metodo implementacion que desencadena todo el programa
    public void fileReader() throws IOException, Exception {
        String json = null;
        System.out.println("The program is running...");
        Object lines = new Object();
        InputStream input = new FileInputStream(System.getProperty("user.dir") + "/" + "SendToCodojo.config.properties");

//            System.out.println(System.getProperty("user.dir"));
        Properties prop = new Properties();

        // load a properties file
        prop.load(input);

        // get the property value and print it out
        String userkey = prop.getProperty("USER.APPLICATION.KEY");
        String sendjson = prop.getProperty("SEND.JSON.TO.SAAS.AUTOMATICALLY");

        if (userkey.isEmpty()) {
            throw new Exception("USER.APPLICATION.KEY is required. Find your USER.APPLICATION.KEY "
                    + "at https://app.codojo.io/how-to-add-application" + "\n"
                    + " and set the USER.APPLICATION.KEY variable in " + System.getProperty("user.dir") + "/" + "SendToCodojo.config.properties" );

        }

        if ((sendjson.isEmpty()) || (sendjson != "no" && sendjson != "yes")) {
            sendjson = "yes";
        }
//este bloque de codigo obtiene el filepath por medio del escaneo de linea por linea ya que no se puede usar el getproperty porque
//no considera "/" entonces la direccion del archivo sin / es erronea.

        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "SendToCodojo.config.properties"));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("APPLICATION.FILEPATH")) {
                lines = line.substring(22, line.length()).trim();

//                            
            }
        }

        if (lines.toString().isEmpty()) {


            lines= System.getProperty("user.dir");
            System.out.println("Looking at the code in <" + lines + ">...");
        }else{
             System.out.println("Looking at the code in <" + lines + ">...");
        }
        

//            } catch (Exception e) {
//                //e.printStackTrace();
//                sl.send("Error in property files",e.getMessage());
//                System.out.println("Error in property files");
//                System.out.println("HELP: make sure you have all the correct values in the properties file");
//                e.printStackTrace();
//            }
        DataBaseData db = new DataBaseData();

        Main main = new Main();

        json = main.implementacion(lines.toString(), userkey, sendjson);
        // System.out.println(prop.getProperty("db.password"));

//        } catch (IOException ex) {
//            sl.send("Error getting values",ex.getMessage());
//            System.out.println("Error getting values");
//            System.out.println("HELP: make sure you have all the correct values in the properties file");
//
//        } catch (Exception ex) {
//            sl.send("Error",ex.getMessage());
//            System.out.println("Error");
//            System.out.println("HELP: contact us to give you a solution");
//            ex.printStackTrace();
//        }
    }
}
