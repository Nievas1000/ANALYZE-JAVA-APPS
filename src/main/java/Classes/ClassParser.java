//---------------IMPORTANTE------------------------
//Esta clase tiene la funcion de obtener los packages, name de la clase,clasificar si es interfaz o una clase
//y obtener los extends e implements
package Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ClassParser {

//import solutionparser.handlers.ClassDiscriptor.Member;
    private Path file;
    private ClassDiscriptor discriptor;

    //private DatabaseDescriptor dbDescriptor;
    public ClassParser() {
    }

    public ClassParser(File file, HashMap<String, String> map, int i, int cont,HashMap<String,String> hashesjson) {
        this.file = file.toPath();

        this.discriptor = new ClassDiscriptor();
        //this.dbDescriptor=new DatabaseDescriptor();
//        discriptor.lastModified = String.valueOf(file.lastModified());
//discriptor.members = new ArrayList();
        try {
            parse(map, i, cont,hashesjson);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public ClassDiscriptor getDiscriptor() {
        return this.discriptor;
    }

    //Este metodo lee linea por linea cada clase
    public void parse(HashMap<String, String> map, int a, int cont,HashMap<String,String> hashesjson) throws Exception {
        List<String> lines = Files.readAllLines(file, Charset.forName("ISO-8859-1"));

        //se crean list y hashmap globales.
        HashMap<String, List> mapdb = new HashMap<>();
        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<String>();
        List<String> listrelation = new ArrayList<>();
        String hashes="";

        //este es el for donde itera todas las clases e interfaces y hacen un paneo de sus nombres y package
        for (int i = 0; i < lines.size(); i++) {
            if (discriptor.packageName == null) {
                getPackageName(lines.get(i), lines, map);
                continue;
            }

            if (lines.get(i).contains("public enum")) {

                savevalues(lines.get(i), map, discriptor.packageName);
            }

            if (lines.get(i).contains("public interface")) {

                savevalues(lines.get(i), map, discriptor.packageName);

            }

            if (lines.get(i).contains("public class") || lines.get(i).contains("class")) {

                savevalues(lines.get(i), map, discriptor.packageName);
            }
        }

        //este for es donde se parsea toda la data obtenida anteriormente y se obtiene data nueva(relaciones,constructs,etc)
        for (int i = 0; i < lines.size(); i++) {

            if (isCommented(lines.get(i))) {
                continue;
            }

            if ((a == 1)) {

                if (searchrelationjpa(lines, lines.get(i), i, mapdb, listrelation,hashes,hashesjson).equals("true")) {

                }
                searchmain(lines.get(i));
                searchapi(lines.get(i));
            }

            searchrelation(lines, lines.get(i), map, set);

//			
            if (discriptor.name == null) {

                getClassName(lines.get(i), lines, i, map);
                continue;
            }

        }
    }

    //Comprueba si el codigo esta comentado para analizarlo o no
    public boolean isCommented(String line) {
        return (line.trim().startsWith("//") || line.trim().startsWith("/*") || line.trim().startsWith("/**")
                || line.trim().startsWith("*"));
    }

    private boolean containsSQL(String line) {
        return line.trim().contains("java.sql");
    }

    //Este metodo obtiene el nombre del package donde estan las clases
    public void getPackageName(String line, List<String> lines, HashMap<String, String> map) throws Exception {
        line = line.trim();
        if (line.startsWith("package")) {
            //divide cada linea en partes y los agrega a un array de strings
            String[] parts = line.split("\\s+");
            if (parts.length == 2) {
                if (parts[1].endsWith(";")) {
                    discriptor.packageName = parts[1].substring(0, parts[1].length() - 1);
                } else {
                    discriptor.packageName = parts[1];
                }
            } else if (parts.length == 3 && parts[2].equals(";")) {
                discriptor.packageName = parts[1];
            }
        } else if (getClassName(line, lines, i, map)) {
            discriptor.packageName = "default";
        }
    }

    // Este metodo obtiene el nombre de cada clase y clasifica si es class o interface ademas dde obtener los extends e implements.
    public boolean getClassName(String line, List<String> lines, int i, HashMap<String, String> map) throws Exception {
        line = line.trim();
        line = line.replace(",", " ").replace("<", " ").replace(">", "");
        String ex = null;

        int cont = 0;
        if (line.contains("class") || line.contains("interface") || line.contains("enum")) {
            String[] parts = line.split("\\s+");

//            for (int i = 0; i < parts.length; i++) {
//                System.out.println(parts[i]);
//            }
            if (line.contains("public interface") || line.contains("private interface") || line.contains("protected interface")) {
                discriptor.interfaz = true;
            }

            //Este if es para leer cuando las clase tiene un extends e implements al mismo tiempo
//            if (parts[0].concat(" ").concat(parts[1]).equalsIgnoreCase("public interface")) {
//                map.put(parts[2], discriptor.packageName);
//                 
//                
//            }
//                    System.out.println(value+"value");
//                    
            // System.out.println(parts[0].concat(" ").concat(parts[1]));
//            if (line.contains("OneToOne")) {
//
//                System.out.println(line.toString());
//                System.out.println("hola");
//
//            }
            if (line.contains("extends") && line.contains("implements")) {
                List<String> listex = new ArrayList<>();
//               
                cont = +1;
                //discriptor.implement = discriptor.packageName + "." + parts[6].substring(0, parts[6].length());
                discriptor.name = discriptor.packageName + "." + parts[2].substring(0, parts[2].length());

                for (int j = 0; j < parts.length - 1; j++) {
                    if (parts[j].equalsIgnoreCase("extends")) {
                        if (map.containsKey(parts[j + 1].replace("{", ""))) {
                            listex.add(map.get(parts[j + 1].replace("{", "")) + "." + parts[j + 1].replace("{", ""));
                            discriptor.extend = listex;
                        } else {
                            discriptor.extend = null;
                        }

                    }
                }
                if (line.endsWith("{")) {

                    for (int j = 0; j < parts.length - 1; j++) {
                        if (parts[j].equalsIgnoreCase("implements")) {
                            List<String> list = new ArrayList();
                            for (int k = j; k < parts.length - 1; k++) {
                                if (map.containsKey(parts[k + 1].replace("{", ""))) {
                                    list.add(map.get(parts[k + 1].replace("{", "")) + "." + parts[k + 1].replace("{", ""));
                                    discriptor.implement = list;
                                } else {

                                }

                            }
                        }
                    }
                } else {
                    List<String> list = new ArrayList<>();
                    String lineget = lines.get(i + 1);
                    int e = i + 1;
                    while (!lines.get(e).isEmpty()) {

                        lineget = lines.get(e);
                        e += 1;

                    }

                    if (lineget.endsWith("{")) {
                        lineget = lineget.replace(",", " ");
                        String[] partsget = lineget.split("\\s+");
                        for (int k = 0; k < partsget.length - 1; k++) {
                            if (map.containsKey(partsget[k].replace("{", ""))) {

                                list.add(map.get(partsget[k].replace("{", "")) + "." + partsget[k].replace("{", ""));
                                discriptor.implement = list;

                            }

                        }

                    } else {
                        List<String> listline = new ArrayList<>();
                        String linei = lines.get(i + 2);
                        linei = linei.replace(",", " ");
                        String[] partsline = linei.split("\\s+");
                        for (int n = 0; n < partsline.length - 1; n++) {
                            if (map.containsKey(partsline[n].replace("{", ""))) {

                                listline.add(map.get(partsline[n].replace("{", "")) + "." + partsline[n].replace("{", ""));
                                discriptor.implement = listline;

                            }

                        }
                    }

                }

                if (lines.get(i + 1).isEmpty()) {
                } else if (lines.get(i + 1).contains("implements")) {

                    String lineimp = lines.get(i + 1);
                    lineimp = lineimp.replace(",", " ");
                    String[] partsimp = lineimp.split("\\s+");

                    if (lineimp.contains("{")) {

                        for (int j = 0; j < partsimp.length - 1; j++) {

                            if (partsimp[j].equalsIgnoreCase("implements")) {
                                List<String> list = new ArrayList();
                                for (int k = j; k < partsimp.length - 1; k++) {

                                    if (map.containsKey(partsimp[k + 1].replace("{", ""))) {

                                        list.add(map.get(partsimp[k + 1].replace("{", "")) + "." + partsimp[k + 1].replace("{", ""));
                                        discriptor.implement = list;
                                    }
                                }
                                //discriptor.implement=list;
                            }

//                        if (map.containsKey(parts[j+1].replace("{", ""))) {
//                            
//                            discriptor.implement = map.get(parts[j + 1]) + "." + parts[j + 1];
//                        } else {
//                            discriptor.implement = null;
//                        }
                        }

                    } else {
                        List<String> list = new ArrayList<>();
                        String lineget = lines.get(i + 2);
                        if (lineget.endsWith("{")) {
                            lineget = lineget.replace(",", " ");
                            String[] partsget = lineget.split("\\s+");
                            for (int k = 0; k < partsget.length - 1; k++) {
                                if (map.containsKey(partsget[k].replace("{", ""))) {

                                    list.add(map.get(partsget[k].replace("{", "")) + "." + partsget[k].replace("{", ""));
                                    discriptor.implement = list;

                                }

                            }

                        } else {
                            List<String> listline = new ArrayList<>();
                            String linei = lines.get(i + 2);
                            linei = linei.replace(",", " ");
                            String[] partsline = linei.split("\\s+");
                            for (int n = 0; n < partsline.length - 1; n++) {
                                if (map.containsKey(partsline[n].replace("{", ""))) {

                                    listline.add(map.get(partsline[n].replace("{", "")) + "." + partsline[n].replace("{", ""));
                                    discriptor.implement = listline;

                                }
                            }
                        }

                    }

                }
            }
//                if (parts.length >= 6) {
//                    System.out.println(parts[4] + "PARTE 4");
//                    if (parts.length == 9) {
//                        if (parts[4].equals("extends")) {
//
//                            if (map.containsKey(parts[5].replace("{", ""))) {
//
//                                discriptor.extend = map.get(parts[5]) + "." + parts[5];
//                            } else {
//
//                                discriptor.extend = null;
//                            }
//
//                        }
//
//                        if (parts[6].equals("implements")) {
//                            if (map.containsKey(parts[7])) {
//                                discriptor.implement = map.get(parts[7]) + "." + parts[7];
//
//                            } else {
//                                discriptor.implement = null;
//                            }
//
//                        }
//                    } else {
//                     
//                        if (parts[3].equals("extends")) {
//
//                            if (map.containsKey(parts[4].replace("{", ""))) {
//
//                                discriptor.extend = map.get(parts[4]) + "." + parts[4];
//                            } else {
//
//                                discriptor.extend = null;
//                            }
//
//                        }
//                        if (parts[5].equals("implements")) {
//                            if (map.containsKey(parts[6])) {
//                                discriptor.implement = map.get(parts[6]) + "." + parts[6];
//
//                            } else {
//                                discriptor.implement = null;
//                            }
//
//                        }
//
//                    }
//
//                }
//                if (lol==5) {
//                    System.out.println("Entro");
//                    for (Map.Entry<String, Object> entry : map.entrySet()) {
//                        String key = entry.getKey();
//                        System.out.println(key+"1");
//                        Object value = entry.getValue();
//                        System.out.println(value+"2");
////                        if(key.equalsIgnoreCase(parts[6].substring(0, parts[6].length()))){
////                          discriptor.implement = value.toString() + "." + parts[6].substring(0, parts[6].length());
////                            System.out.println(discriptor.implement);
////                        }
//                        
//                    }
            //discriptor.implement = discriptor.packageName + "." + parts[6].substring(0, parts[6].length());
            //System.out.println(discriptor.implement);
//                     discriptor.implement = parts[6].substring(0, parts[6].length());
//                     System.out.println(discriptor.implement);
            //Este if tiene un contador para que no entre 2 veces ya que las condiciones en el if de extend de arriba son similares a este
            //por eso tiene una contador para verificar si entro en el if anterior y si entro que aca no entre.
//            System.out.println(line);
//            System.out.println(parts.length);

            String lineif;
            if (!line.contains("extends") && !line.contains("{")) {
                List<String> array = new ArrayList<>();

                int e = i + 1;

                while (!lines.get(e).isEmpty() && lines.get(e).contains("{")) {

                    lineif = lines.get(e);

                    e += 1;

                    if (lineif.contains("extends")) {
                        String[] partsif = lineif.split("\\s+");
                        for (int j = 0; j < partsif.length - 1; j++) {
                            if (map.containsKey(partsif[j].replace("{", ""))) {
//                           System.out.println(map.get(partsif[j]) + "PARTSIFFF");

                                array.add(map.get(partsif[j].replace("{", "")) + "." + partsif[j].replace("{", ""));
                                discriptor.extend = array;
                            }

                        }
                    }

                }

            }

            if (line.contains("extends")) {

                List<String> array = new ArrayList<>();

                if (!line.contains("{")) {

                    lineif = lines.get(i + 1);

                    //array.add(map.get(partsif[0]) + "." + partsif[0]);
                    List<String> list = new ArrayList<>();

                    int e = i + 1;
                    while (!lines.get(e).isEmpty()) {

                        lineif = lines.get(e);
                        e += 1;

                    }

                    lineif = lineif.replace("<", " ").replace(">", " ");
                    String[] partsif = lineif.split("\\s+");

                    //   discriptor.extend=array;
                    for (int j = 0; j < partsif.length - 1; j++) {
                        if (map.containsKey(partsif[j].replace("{", ""))) {
//                            System.out.println(map.get(partsif[j]) + "PARTSIFFF");

                            array.add(map.get(partsif[j].replace("{", "")) + "." + partsif[j].replace("{", ""));
                            //discriptor.extend= map.get(partsif[j]) + "." + partsif[j];
                        } else {
                            discriptor.extend = null;
                        }
                    }

                    discriptor.extend = array;

                } else {
                    lineif = lines.get(i);

                    lineif = lineif.replace("<", " ").replace(">", " ").replace(",", " ");
                    String[] partsif = lineif.split("\\s+");

                    for (int j = 0; j < partsif.length - 1; j++) {
                        if (partsif[j].equalsIgnoreCase("extends") && cont <= 0) {
                            for (int k = j; k < partsif.length; k++) {
                                if (map.containsKey(partsif[k].replace("{", ""))) {
                                    List<String> arrayl = new ArrayList<>();
                                    arrayl.add((map.get(partsif[k].replace("{", "")) + "." + partsif[k].replace("{", "")));
                                    discriptor.extend = arrayl;
                                }
                            }
                        }

//                        if (partsif[j].equalsIgnoreCase("extends") && cont <= 0 && partsif.length >= 5) {
//                            System.out.println("entra");
//                            if(partsif.length > 5) {
//                            if (map.containsKey(partsif[j + 2].replace("{", ""))) {
//                               
//                                array.add((map.get(partsif[j + 2]) + "." + partsif[j + 2]));
//                                discriptor.extend = array;
//                            }
//                            }
//                            } else if (map.containsKey(partsif[j + 1].replace("{", ""))) {
//                                
//                                array.add(map.get(partsif[j + 1]) + "." + partsif[j + 1]);
//                                discriptor.extend = array;
//                            } else {
//                                
//                            }
//}
                    }

                }

            } else if (line.contains("implements")) {

                String lineimp = lines.get(i);
                lineimp = lineimp.replace(",", " ");
                String[] partsimp = lineimp.split("\\s+");
                if (lineimp.endsWith("{")) {
                    for (int j = 0; j < partsimp.length - 1; j++) {
                        if (parts[j].equalsIgnoreCase("implements")) {
                            List<String> list = new ArrayList();
                            for (int k = j; k < partsimp.length - 1; k++) {
                                if (map.containsKey(partsimp[k + 1].replace("{", ""))) {

                                    list.add(map.get(partsimp[k + 1].replace("{", "")) + "." + partsimp[k + 1].replace("{", ""));
                                    discriptor.implement = list;
                                }
                            }
                            //discriptor.implement=list;
                        }

//                        if (map.containsKey(parts[j+1].replace("{", ""))) {
//                            
//                            discriptor.implement = map.get(parts[j + 1]) + "." + parts[j + 1];
//                        } else {
//                            discriptor.implement = null;
//                        }
                    }

                } else {
                    List<String> list = new ArrayList<>();
                    String lineget = lines.get(i + 1);
                    int e = i + 1;
                    while (!lines.get(e).isEmpty()) {

                        lineget = lines.get(e);
                        e += 1;

                    }

                    if (lineget.endsWith("{")) {
                        lineget = lineget.replace(",", " ");
                        String[] partsget = lineget.split("\\s+");
                        for (int k = 0; k < partsget.length - 1; k++) {
                            if (map.containsKey(partsget[k].replace("{", ""))) {

                                list.add(map.get(partsget[k].replace("{", "")) + "." + partsget[k].replace("{", ""));
                                discriptor.implement = list;

                            }

                        }

                    } else {
                        List<String> listline = new ArrayList<>();
                        String linei = lines.get(i + 2);
                        linei = linei.replace(",", " ");
                        String[] partsline = linei.split("\\s+");
                        for (int n = 0; n < partsline.length - 1; n++) {
                            if (map.containsKey(partsline[n].replace("{", ""))) {

                                listline.add(map.get(partsline[n].replace("{", "")) + "." + partsline[n].replace("{", ""));
                                discriptor.implement = listline;

                            }

                        }
                    }

                }

            }
            if (!lines.get(i).contains("{") && !lines.get(i).contains("}")) {

                if (lines.get(i + 1).contains("implements")) {

                    String lineimp = lines.get(i + 1);
                    lineimp = lineimp.replace(",", " ");
                    String[] partsimp = lineimp.split("\\s+");

                    if (lineimp.contains("{")) {

                        for (int j = 0; j < partsimp.length - 1; j++) {

                            if (partsimp[j].equalsIgnoreCase("implements")) {
                                List<String> list = new ArrayList();
                                for (int k = j; k < partsimp.length - 1; k++) {

                                    if (map.containsKey(partsimp[k + 1].replace("{", ""))) {

                                        list.add(map.get(partsimp[k + 1].replace("{", "")) + "." + partsimp[k + 1].replace("{", ""));
                                        discriptor.implement = list;
                                    }
                                }
                                //discriptor.implement=list;
                            }

//                        if (map.containsKey(parts[j+1].replace("{", ""))) {
//                            
//                            discriptor.implement = map.get(parts[j + 1]) + "." + parts[j + 1];
//                        } else {
//                            discriptor.implement = null;
//                        }
                        }

                    } else {
                        List<String> list = new ArrayList<>();
                        String lineget = lines.get(i + 2);
                        if (lineget.endsWith("{")) {
                            lineget = lineget.replace(",", " ");
                            String[] partsget = lineget.split("\\s+");
                            for (int k = 0; k < partsget.length - 1; k++) {
                                if (map.containsKey(partsget[k].replace("{", ""))) {

                                    list.add(map.get(partsget[k].replace("{", "")) + "." + partsget[k].replace("{", ""));
                                    discriptor.implement = list;

                                }

                            }

                        } else {
                            List<String> listline = new ArrayList<>();
                            String linei = lines.get(i + 2);
                            linei = linei.replace(",", " ");
                            String[] partsline = linei.split("\\s+");
                            for (int n = 0; n < partsline.length - 1; n++) {
                                if (map.containsKey(partsline[n].replace("{", ""))) {

                                    listline.add(map.get(partsline[n].replace("{", "")) + "." + partsline[n].replace("{", ""));
                                    discriptor.implement = listline;

                                }
                            }
                        }

                    }

                }
            }

//            if (parts.length >= 4) {
//
//                if (parts[parts.length - 3].equals("extends") && cont <= 0) {
//
//                    if (map.containsKey(parts[parts.length - 2].replace("{", ""))) {
//
//                        discriptor.extend = map.get(parts[parts.length - 2]) + "." + parts[parts.length - 2];
//                    } else {
//                        discriptor.extend = null;
//
//                    }
//                } else if (parts[parts.length - 3].equals("implements")) {
//                    //System.out.println(map.containsKey(parts[4]));
//                    //discriptor.implement = discriptor.packageName + "." + parts[4].substring(0, parts[4].length());
//                    System.out.println(discriptor.packageName);
////                for (Map.Entry<String, String> entry : map.entrySet()) {
////                    String key = entry.getKey();
////                    String value = entry.getValue();
////                    System.out.println(key + value + "VALORES");
////                }
//
//                    System.out.println(parts[parts.length - 2] + "PARTE");
//                    if (map.containsKey(parts[parts.length - 2])) {
//
//                        discriptor.implement = map.get(parts[parts.length - 2]) + "." + parts[parts.length - 2];//.substring(0, parts[4].length()).replace("{", "");
//
//                    } else {
//                        discriptor.implement = null;
//                    }
//                }
//
//            }
//            if (parts[3].equals("implements")) {
//                System.out.println("entro");
//                for (Map.Entry<String, String> entry : map.entrySet()) {
//                    String key = entry.getKey();
//                    String value = entry.getValue();
//                    System.out.println(key+" "+value+"mapamigo");
//                    if (parts[4].equals(key)) {
//
//                        discriptor.implement = value.concat(parts[4].substring(0, parts[4].length() - 1));
//
//                        System.out.println(discriptor.implement);
////                     
//                    }
//
////                }
//                    //discriptor.implement = discriptor.packageName + "." + parts[4].substring(0, parts[4].length() - 1);
//                }
//    
//            if (parts.length == 2 && isClassOrInterface(parts[0])) {
//                if (parts[1].endsWith("{")) {
//                    discriptor.name = discriptor.packageName + "." + parts[1].substring(0, parts[1].length() - 1);
//
//                } else {
//                    discriptor.name = discriptor.packageName + "." + parts[1];
//                }
////                discriptor.modifier = Constants.MODIFIER_DEFAULT;
//
//            } else if (parts.length == 3 && isClassOrInterface(parts[1])) {
//                if (parts[2].endsWith("{")) {
//                    discriptor.name = discriptor.packageName + "." + parts[2].substring(0, parts[1].length() - 1);
//
//                } else {
//                    discriptor.name = discriptor.packageName + "." + parts[2];
//                }
//                // discriptor.modifier = parts[0];
//            } else if (parts.length == 3 && isClassOrInterface(parts[0]) && parts[2].equals("{")) {
//                discriptor.name = discriptor.packageName + "." + parts[1];
//                //	discriptor.modifier=Constants.MODIFIER_DEFAULT;
//            } else if (parts.length == 4 && isClassOrInterface(parts[1]) && parts[3].equals("{")) {
//                discriptor.name = discriptor.packageName + "." + parts[2];
////                //	discriptor.modifier=parts[0];
//            } else if (parts.length == 5 && isClassOrInterface(parts[1]) && isParentLinker(parts[3])) {
//                discriptor.name = discriptor.packageName + "." + parts[2];
//            } else {
            for (int j = 0; j < parts.length - 1; j++) {

                if (parts[j].equalsIgnoreCase("class") || parts[j].equalsIgnoreCase("interface") || parts[j].equalsIgnoreCase("enum")) {
                    discriptor.name = discriptor.packageName + "." + parts[j + 1].replace("{", "");
                    break;
                }

            }

            return true;
        }
        //return true;

        return false;
    }

    //compara entre extends e implements
    public boolean isParentLinker(String line) {
        return line.trim().equals("extends") || line.trim().equals("implements");
    }

    //compara entre class e interfaz
    public boolean isClassOrInterface(String part) {
        return part.trim().equals("class") || part.trim().equals("interface") || part.trim().equals("enum");
    }

    //guarda los valores que se le pasan desde el primer for de esta clase
    public void savevalues(String line, HashMap<String, String> map, String dp) throws Exception {

        line = line.trim();
//        System.out.println(line);
        line = line.replace("<", " ");
        String[] parts = line.split("\\s+");

        if (parts.length >= 3) {
            for (int j = 0; j < parts.length - 1; j++) {
                if ((parts[j].equalsIgnoreCase("class") || parts[j].equalsIgnoreCase("interface") || parts[j].equalsIgnoreCase("enum")) && (!line.contains("//"))) {
                    //este if guarda adentro del map los nombres que jamas tengan {} porque algunas veces el escaneo
                    //puede filtrar eso y esto hace que no se guarde si tiene esos caracteres.

                    if (!parts[j + 1].contains("{") || !parts[j + 1].contains("}")) {
                        map.put(parts[j + 1].replace("<", "").replace(">", "").replace("<T>", "").replace(")", "").replace("(", "").replace(";", "").replace(",", "").replace("{", "").replace("}", ""), dp);

                    }
                }
            }
            //guarda nombre de la clase y package en un hashmap.
            //map.put(parts[2], dp);

        }

    }
    int i = 0;

    //busca en un clases el llamado a otras clases por medio de los import o declaraciones de variables que referencian a una clase.
    public void searchrelation(List<String> lines, String line, HashMap<String, String> map, Set<String> set) throws Exception {
        line = line.trim();
        line = line.replace(".", " ");

        //si la line contiene import entra al if
        if (line.contains("import")) {

            //divide la linea en partes separadas por espacios
            String[] parts = line.split("\\s+");
            line = parts[parts.length - 1].replace(";", "");

            //si el map contiene la key = nombre de la clase entra al if
            // System.out.println(line);
            if (map.containsKey(line)) {

                //System.out.println(map.get(line).concat(".").concat(line));
                //guarda el package de la clase que acabamos de comparar en un hashset.
                if (!line.contains("{") && !line.contains("}")) {
                    set.add(map.get(line).concat(".").concat(line));
                }
            }

//guarda el hashset en el atributo constructor de la clase discriptor
            discriptor.constructor = set;

            //si el hashset esta vacio no guarda nada
            if (set.isEmpty()) {

                discriptor.constructor = null;

            }
        }

        //este if es para buscar en una clase las declaraciones de otra clase en un variable
        if ((line.contains("public") || line.contains("private") || line.contains("final") || line.contains("protected") || line.contains(";")) && ((!line.contains("class")) && !line.contains("interface") && !line.contains("enum"))) {
            String[] parts = line.split("\\s+");

            //line=parts[parts.length-1].replace(";", "");
            if (parts.length == 4 || parts.length == 3 || parts.length == 2) {

                //este if sigue el mismo patron que el anterior y entra cuando length es 4 ya que al buscar la parts[2] nos trae el nombre de la clase
                //!discriptorname.contains es indicando que si en la clase se declara una variable con la misma clase en la que esta no guarda nada.
                if (parts.length == 4) {
                    if (map.containsKey(parts[2].replace(">", "").replace("<", "").replace(",", "")) && !discriptor.name.contains(parts[2])) {
                        //System.out.println(parts[2]+"4");
                        set.add(map.get(parts[2].replace(">", "").replace("<", "").replace(",", "")).concat(".").concat(parts[2].replace(">", "").replace("<", "").replace(",", "")));

                        //list.add(map.get(parts[2].replace(">", "")).concat(".").concat(parts[2].replace(">", "").replace("<", "").replace(",", "")));
                        //mismo patron
                    }
                }
                if (parts.length == 3) {

                    if (map.containsKey(parts[1].replace(">", "").replace("<", "").replace(",", "")) && !discriptor.name.contains(parts[1])) {

                        set.add(map.get(parts[1].replace(">", "").replace("<", "").replace(",", "")).concat(".").concat(parts[1].replace("<", "").replace(">", "").replace(",", "")));

                    }
                }

                if (parts.length == 2) {

                    if (map.containsKey(parts[0].replace(">", "").replace("<", "").replace(",", "")) && !discriptor.name.contains(parts[0])) {
                        set.add(map.get(parts[0].replace(">", "").replace("<", "").replace(",", "")).concat(".").concat(parts[0].replace("<", "").replace(">", "").replace(",", "")));

                    }

                }
            }

            //si algun nombre de la clase coincide con una key del map donde se mapearon todas los nombres de las clases y package guarda
            //en el atributo constructor de la clase discriptor
            discriptor.constructor = set;
//            if (parts.length < 1) {
//                if (map.containsKey(parts[1])) {
//                    discriptor.constructor = set;
//                }
//
//            }else{
//                  if (map.containsKey(parts[2])) {
//                        discriptor.constructor = set;
//                    }
//            }
//si el hashset no tiene nada guarda un valor en null
            if (set.isEmpty()) {
                discriptor.constructor = null;
            }

        }

    }
int contjpa=0;
    //este metodo busca las relaciones de jpa (@OneToOne,@OneToMany,@ManyToOne,@ManyToMany)
    public String searchrelationjpa(List<String> lines, String line, int i, HashMap<String, List> mapdb, List<String> list,String hashes,HashMap<String,String>hashesjson) throws FileNotFoundException, Exception {
        String entidad = "";
        String relation = "";
        String nametable = null;
        String namet=null;
        contjpa++;
        String hashesprueba="";
        DataBaseData db = new DataBaseData();
        
       
        
        

        List<String> tables = new ArrayList();
        
        
        
        for (int e = 0; e < lines.size()-1; e++) {
            if(lines.get(e).contains("@Column") || lines.get(e).contains("@JoinColumn")){
                String linejpa=lines.get(e).replace("(", "").replace(")","" ).replace("\"", "");
                hashes=hashes+linejpa.replace(",", "").replace("=", "").replace(" ", "");
                hashes=hashes.trim();
                
                hashes=ObtenerHASHMD5(hashes);
              
                
            }
            
            if(lines.get(e).contains("@Table")){
                String tn=lines.get(e).replace("(", " ").replace("=", " ").replace(")", "");
                String [] partstn=tn.split("\\s+");
                namet=partstn[2].replace("\"", "").toLowerCase();
                
                
            }
        }
      
       
        

//aca se identifica el archivo de propiedades pasandole el user.dir que seria el directorio donde esta parado ahora el usuario.
        try {

            // get the property value and print it out
            // trae los valores de las properties del archivo.
//            String typedb = prop.getProperty("TYPE.DB");
//            String hostdb = prop.getProperty("HOST.DB");
//            String portdb = prop.getProperty("PORT.DB");
//            String namedb = prop.getProperty("NAME.DB");
//            String userdb = prop.getProperty("USER.DB");
//            String password = prop.getProperty("PASSWORD.DB");
//             List<String> tablas = db.getTables(typedb, hostdb, portdb, namedb, userdb, password);
            //si la linea contiene alguna de las anotaciones de jpa entra.
            if ((line.contains("@OneToOne") || line.contains("@OneToMany") || line.contains("@ManyToMany")
                    || line.contains("@ManyToOne")) || line.contains("@JoinTable") && !line.contains("line.contains")) {

                if (line.contains("@OneToOne")) {
                    relation = "OneToOne";
                }
                if (line.contains("@OneToMany")) {
                    relation = "OneToMany";
                }
                if (line.contains("@ManyToMany")) {
                    relation = "ManyToMany";
                }
                if (line.contains("@ManyToOne")) {

                    relation = "ManyToOne";
                }

                if (lines.get(i + 1).contains("@JoinTable") && !lines.get(i + 1).contains("name") && !line.contains("//")) {
                    String[] partsJ = lines.get(i + 2).split("\\s+");
                    for (int j = 0; j < partsJ.length - 1; j++) {
                        if (partsJ[j].equals("name")) {

                            nametable = partsJ[j + 2];

                        }
                    }

                } else if (lines.get(i + 1).contains("@JoinTable") && lines.get(i + 1).contains("name") && !line.contains("//")) {

                    String[] partsJ = lines.get(i).split("\\s+");
                    for (int j = 0; j < partsJ.length - 1; j++) {
                        if (partsJ[j].equals("name")) {

                            nametable = partsJ[j + 2];

                        }
                    }
                }

                int c = 0;
                //este bucle es para que ignore todas las anotaciones que no sean las relaciones de jpa
                //y busque la declaracion de la clase donde se definio la relacion.
                do {

                    if ((!lines.get(i + 1).contains("@")) && entidad.isEmpty()) {

                        if (!lines.get(i + 1).isEmpty()) {
                            entidad = lines.get(i + 1);

                        } else {

                        }
                        //i++;
                    }
                    i++;

                } while (lines.get(i).contains("@") || !lines.get(i).isEmpty() && entidad.isEmpty());

                //divide en partes la declaracion de la clase en la que se esta haciendo la relacion.
                String[] parts1 = entidad.split("\\s+");

                if (relation.equalsIgnoreCase("OneToOne") && !line.contains("//")) {
                    if (nametable == null) {
                        entidad = parts1[parts1.length - 1].replace(";", "");
                    } else {
                        entidad = nametable.replace(";", "");
                    }

                    if (!entidad.endsWith("{")) {
                        list.add(entidad);
                    } else {
                        list = null;
                    }
                }

                //si la longitud de todas las partes es menor o igual a 3 entra
                if (parts1.length <= 3) {
                    //entidad = parts1[parts1.length-1].replace(";", "");

                    if (relation.equals("ManyToMany") && parts1.length <= 3 && !line.contains("//")) {

                        String trim = discriptor.name.trim();
                        trim = trim.replace(".", " ");
                        String[] parts = trim.split("\\s+");
                       
                        //aqui se trae el nombre de la clase atual se convierte a minuscula y luego se concatena
                        //con la clase donde se hizo la relacion

                        if (nametable == null) {
                            entidad = parts[parts.length - 1].toLowerCase() + "s_" + parts1[parts1.length - 1].replace(";", "");
                        } else {
                            entidad = parts[parts.length - 1].toLowerCase() + "s_" + nametable.replace(";", "");
                        }
                        if (!entidad.endsWith("{")) {

                            list.add(entidad);
                        } else {
                            list = null;
                        }
                    }

                    //mismo patron
                    if (relation.equals("OneToMany") && !line.contains("//")) {
                        String trim = discriptor.name.trim();
                        trim = trim.replace(".", " ");
                        String[] parts = trim.split("\\s+");

                        if (nametable == null && parts1.length < 7) {
                            entidad = parts[parts.length - 1].toLowerCase() + "_" + parts1[parts1.length - 1].replace(";", "");
                        } else if (nametable == null && parts1.length >= 7) {
//                            System.out.println(nametable + "s");
                            entidad = parts[parts.length - 1].toLowerCase() + "_" + parts1[parts1.length - 4].replace(";", "");
                        } else {
                            entidad = parts[parts.length - 1].toLowerCase() + "_" + nametable.replace(";", "").replace("\"", "").replace(",", "");
                        }

                        if (!entidad.endsWith("{")) {
                            list.add(entidad);
                        } else {
                            list = null;
                        }

                    }

                    if (relation.equals("ManyToOne") && !line.contains("//")) {
                        String trim = discriptor.name.trim();
                        trim = trim.replace(".", " ");
                        String[] parts = trim.split("\\s+");
                        if (nametable == null) {
                            entidad = parts[parts.length - 1].toLowerCase() + "s_" + parts1[parts1.length - 1].replace(";", "");
                        } else {
                            entidad = parts[parts.length - 1].toLowerCase() + "s_" + nametable;

                        }
                        if (!entidad.endsWith("{")) {
                            list.add(entidad);
                        } else {
                            list = null;
                        }

                    }

                } else {
                    //mismo patron

                    if (relation.equals("OneToMany") && !line.contains("//")) {
                        String trim = discriptor.name.trim();
                        trim = trim.replace(".", " ");
                        String[] parts = trim.split("\\s+");
                        if (nametable == null && parts1.length < 7) {
                            entidad = parts[parts.length - 1].toLowerCase() + "_" + parts1[parts1.length - 1].replace(";", "");
                        } else if (nametable == null && parts1.length >= 7) {
//                            System.out.println(nametable + "s");
                            entidad = parts[parts.length - 1].toLowerCase() + "_" + parts1[parts1.length - 4].replace(";", "");
                        } else {
                            entidad = parts[parts.length - 1].toLowerCase() + "_" + nametable.replace(";", "").replace("\"", "").replace(",", "");
                        }

                        if (!entidad.endsWith("{")) {
                            list.add(entidad);
                        } else {
                            list = null;
                        }

                    }

                    //mismo patron
                    if (relation.equals("ManyToMany") && !line.contains("//")) {

                        String trim = discriptor.name.trim();
                        trim = trim.replace(".", " ");
                        String[] parts = trim.split("\\s+");

                        if (nametable == null && parts1.length < 7) {
                            entidad = parts[parts.length - 1].toLowerCase() + "s_" + parts1[parts1.length - 1].replace(";", "");
                        } else if (nametable == null && parts1.length >= 7) {
//                            System.out.println(nametable + "s");
                            entidad = parts[parts.length - 1].toLowerCase() + "s_" + parts1[parts1.length - 4].replace(";", "");
                        } else {
                            entidad = parts[parts.length - 1].toLowerCase() + "s_" + nametable.replace(";", "").replace("\"", "").replace(",", "");
                        }

                        if (!entidad.endsWith("{")) {

                            list.add(entidad);
                        } else {
                            list = null;
                        }

                    }

                    if (relation.equals("ManyToOne") && !line.contains("//")) {
                        String trim = discriptor.name.trim();
                        trim = trim.replace(".", " ");
                        String[] parts = trim.split("\\s+");

                        if (nametable == null) {
                            entidad = parts[parts.length - 1].toLowerCase() + "s_" + parts1[parts1.length - 1].replace(";", "");
                        } else {
                            entidad = parts[parts.length - 1].toLowerCase() + "s_" + nametable;
                        }
                        if (!entidad.endsWith("{")) {
                            list.add(entidad);
                        } else {
                            list = null;
                        }

                    }

                }

              
                String hashestable=discriptor.name.replace(".", " ");
                String[] partshashes=hashestable.split("\\s+");
                if (!entidad.equalsIgnoreCase("{")) {
                    //tables.add(entidad);
                    if(namet!=null && !hashes.isEmpty()){
                    hashesjson.put(namet, hashes);
                    }else if(namet==null && !hashes.isEmpty() ){
                    hashesjson.put(partshashes[1], hashes);
                    }
                    discriptor.setDatasources(list);

                }

                // discriptor.setDatasources(tables);
//                    for (Map.Entry<String, List> entry : mapdb.entrySet()) {
//                        String key = entry.getKey();
//                        List value = entry.getValue();
//                        System.out.println(key + value + "map");
//
//                    }
//                    mapdb.put(relation, tables);
                //discriptor.setDatasources(mapdb);
//                }
                // System.out.println(res);
                //Boolean res=tablas.contains(entidad);
//                   tables.add(entidad);
//                   System.out.println(entidad);
//                   discriptor.setDatasources(tables);
//                   if(res==true){
//                     tables.add(entidad);
//                     discriptor.datasources.add(entidad);
//                      for (String table : tables) {
//                System.out.println(table);
//            }
//                   }
//                    
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(ClassParser.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (SQLException ex) {
//                    Logger.getLogger(ClassParser.class.getName()).log(Level.SEVERE, null, ex);
//                }
//          for (String table : tables) {
//                System.out.println(table);
                //  }
            }
            return "true";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "false";
    }

    public String ObtenerHASHMD5(String textoEntrada) throws Exception {
        if (textoEntrada.equals("")) {
            return "";
        } else {
            try {
                MessageDigest HashMD5 = MessageDigest.getInstance("MD5");
                byte[] mensajeMatriz = HashMD5.digest(textoEntrada.getBytes());
                BigInteger numero = new BigInteger(1, mensajeMatriz);
                StringBuilder hashMD5Salida = new StringBuilder(numero.toString(16));

                while (hashMD5Salida.length() < 32) {
                    hashMD5Salida.insert(0, "0");
                }
                return hashMD5Salida.toString();
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Error al obtener el hash: " + e.getMessage());
                return "";
            } 
        }
    }

    public String getKey() throws Exception {
        String userkey = null;
        try (InputStream input = new FileInputStream(System.getProperty("user.dir") + "/" + "SendToTaffi.config.properties")) {

//            System.out.println(System.getProperty("user.dir"));
            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            userkey = prop.getProperty("USER.APPLICATION.KEY").trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userkey;
    }

    public String getProjectName() throws Exception {
        String lines = null;
        File filename = null;

        BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/" + "SendToTaffi.config.properties"));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.contains("APPLICATION.FILEPATH")) {
                lines = line.substring(22, line.length()).trim();
//                            
            }
        }
        String sCarpAct = System.getProperty(lines, lines);

        filename = new File(sCarpAct);

        return filename.getName();
    }

    public void searchapi(String line) {
        line = line.trim();
        line = line.replace("(", " ");

        //divide la linea en partes separadas por espacios
        String[] parts = line.split("\\s+");

        for (String part : parts) {
            if (part.equals("@RequestMapping")) {
                discriptor.setEndpoints(Boolean.TRUE);

            }
        }
    }

    public void searchmain(String line) {

        line = line.trim();
        line = line.replace("(", " ");
        String[] parts = line.split("\\s+");
        if (parts[0].equals("public") && parts[1].equals("static") && parts[2].equals("void") && parts[3].equals("main")) {

            discriptor.setMainClass(Boolean.TRUE);

        }

    }

}
