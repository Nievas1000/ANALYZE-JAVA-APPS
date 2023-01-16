package main;

import Classes.ClassDiscriptor;
import Classes.ClassParser;
import Classes.DataBaseData;
import java.awt.Toolkit;
import Requests.PostRequest;
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

import javax.swing.JOptionPane;

public class Main {

    List<File> files;

    public void implementacion(String args, DataBaseData db) {
        // DataBaseData db=new DataBaseData();
        System.out.println(args + "Dsa");
        //trae la direccion del archivo
        String sCarpAct = System.getProperty(args, args);
        System.out.println(sCarpAct);
        //Se crea un nuevo file y se le pasa como argumento la direccion del archivo
        File file = new File(sCarpAct + File.separator + "src");//+ File.separator + "main" + File.separator + "java" + File.separator + "com");
        System.out.println(file);
        files = new ArrayList();
        if (file != null && file.exists()) {
            //trae el archivo
            getFiles(file);
        }

        //se hace una list con todos lo atributos de classdiscriptor
        List<ClassDiscriptor> cdList = new ArrayList();
        //Itera cada file si tiene name y no es null se agrega a una lista
        for (File f : files) {
            System.out.println("File: " + f.getAbsolutePath());
            ClassParser parser = new ClassParser(f);
            ClassDiscriptor cd = parser.getDiscriptor();
            cd.setPackageName(null);
            if (cd != null && cd.name != null) {
                cdList.add(cd);
            }
//              DatabaseDescriptor db = parser.getDBDescriptor();
//            if (db != null && db.db != null) {
//                dbList.add(db);
//            }
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
        StringBuilder builder = new StringBuilder();
        for (ClassDiscriptor cd : cdList) {
            builder.append("\r\nName: " + cd.name);
            builder.append("\r\n\tInterfaz: " + cd.interfaz);
            cd.setConstructor("null");
            builder.append("\r\n\tConstructors: ").append(cd.getConstructor());
            builder.append("\r\n\tExtends: " + cd.extend);
            builder.append("\r\n\tImplements: " + cd.implement);
            //builder.append("\r\n\tPackage: " + cd.packageName);
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
        }
        try {
            HashMap<String, List> map = new HashMap();
            //comprobacion de si el usuario ingreso sus datos de la db
            if (!db.getDb().isEmpty() && !db.getHost().isEmpty() && !db.getPort().isEmpty() && !db.getPassword().isEmpty() && !db.getUsername().isEmpty()) {
                //trae las tablas de la db
                List<String> lista = db.getTables(db);
                //se agrega la lista a un hashmap
                map.put("classes", cdList);
                map.put("tables", lista);
            } else {
                map.put("classes", cdList);
            }

            String json = toJSON(map);
            StringSelection selection = new StringSelection(json);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            String user = System.getProperty("user.name");
            String pathFile = "C:\\Users\\" + user + "\\Desktop\\" + user + ".json";
            try {
                //se escribe el json en el pathfile del usuario y se guarda alli
                FileWriter jsonFile = new FileWriter(pathFile);
                jsonFile.write(json);
                jsonFile.close();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage());

            }
            System.out.println("JSON file created" + builder);
            System.out.println("JSON: " + json);

        } catch (Exception e2) {
            e2.printStackTrace();
            JOptionPane.showMessageDialog(null, e2.getMessage());

        }

    }
    //esta funcion 

    //Este metodo transforma un hashmap a json mediante la dependencia de google.gson
    public String toJSON(HashMap map) throws Exception {

        Gson gson = new Gson();
        JsonObject json = gson.toJsonTree(map).getAsJsonObject();

        PostRequest.PostRequest(json);
        return json.toString();
    }

    //Obtiene cada archivo y los agrega a una lista de files.
    public void getFiles(File file) {
        File[] subdir = file.listFiles();
        for (File f : subdir) {
            if (f.isFile() && f.getName().endsWith(".java")) {
                files.add(f);
            } else {
                getFiles(f);
            }
        }
    }
}
