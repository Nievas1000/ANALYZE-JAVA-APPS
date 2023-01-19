//---------------IMPORTANTE------------------------
//Esta clase tiene la funcion de obtener los packages, name de la clase,clasificar si es interfaz o una clase
//y obtener los extends e implements
package Classes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.JOptionPane;

public class ClassParser {

//import solutionparser.handlers.ClassDiscriptor.Member;
    private Path file;
    private ClassDiscriptor discriptor;
    Object key=new Object();
    Object value=new Object();
    
    //private DatabaseDescriptor dbDescriptor;

    public ClassParser(File file) {
        this.file = file.toPath();

        this.discriptor = new ClassDiscriptor();
        //this.dbDescriptor=new DatabaseDescriptor();
//        discriptor.lastModified = String.valueOf(file.lastModified());
//        discriptor.members = new ArrayList();
        try {
            parse();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public ClassDiscriptor getDiscriptor() {
        return this.discriptor;
    }

    //Este metodo lee linea por linea cada clase
    public void parse() throws Exception {
        List<String> lines = Files.readAllLines(file);
        for (int i = 0; i < lines.size(); i++) {
            if (isCommented(lines.get(i))) {
                continue;
            }
//			if(containsSQL(lines.get(i))) {
//				System.out.println("DB CODE DETECTED!!!");
//				parseDB(lines);
//			}
            if (discriptor.packageName == null) {
                getPackageName(lines.get(i));
                continue;
            }
            if (discriptor.name == null) {
                getClassName(lines.get(i));
                continue;
            }
//            findMembers(lines.get(i));
        }
    }

    //Comprueba si el codigo esta comentado para analizarlo o no
    public boolean isCommented(String line) {
        return (line.trim().startsWith("//") || line.trim().startsWith("/*"));
    }
    //public DatabaseDescriptor getDBDescriptor() {
    //return this.dbDescriptor;
    //}
//	private void parseDB(List<String> lines) {
//		for(String line:lines) {
//			if(line.contains("jdbc:")) {
//				System.out.println("Detected line: "+line);
//				int start=line.indexOf("\"");
//				int end=line.substring(start+1).indexOf("\"");
//				if(end>0)
//					end+=start;
//				System.out.println("Start point: "+start+", "+end);
//				if(end>start) {
//					String connectionString=line.substring(start+1,end+1);
//					System.out.println("Connection String: "+connectionString);
//					if(connectionString.contains(":")) {
//						String[] params=connectionString.split(":");
//						if(params.length==4) {
//							String dbType=params[1];
//							String host=params[2];
//							String port="";
//							String db="";
//							if(params[3].contains("/")) {
//								String[] pd=params[3].split("/");
//								if(pd.length==2) {
//									port=pd[0];
//									db=pd[1];
//								}
//							}else {
//								port=params[3];
//								db="Unknown";
//							}
//							dbDescriptor.type=dbType;
//							dbDescriptor.host=host;
//							dbDescriptor.port=port;
//							dbDescriptor.db=db;
//							dbDescriptor.username="root";
//							dbDescriptor.password="";
//						}
//					}
//				}
//			}
//		}
//	}

    private boolean containsSQL(String line) {
        return line.trim().contains("java.sql");
    }

//    private void addMember(Member m) {
//        if (discriptor.members == null) {
//            discriptor.members = new ArrayList();
//        }
//        discriptor.members.add(m);
//    }
//
//    public void findMembers(String line) {
//        line = line.trim();
//        String[] parts = line.split("\\s+");
//        if (parts.length == 2 && parts[1].endsWith(";")) {
//            Member m = new Member();
//            m.modifier = Constants.MODIFIER_DEFAULT;
//            m.type = parts[0];
//            m.member = parts[1].substring(0, parts[1].length() - 1);
//            addMember(m);
//        } else if (parts.length == 3 && isModifier(parts[0]) && parts[2].endsWith(";")) {
//            Member m = new Member();
//            m.modifier = parts[0];
//            m.type = parts[1];
//            m.member = parts[2].substring(0, parts[2].length() - 1);
//            addMember(m);
//        }else if(parts.length > 3 && isModifier(parts[0]) && line.endsWith(";")){
//            Member m = new Member();
//            m.modifier = parts[0];
//            m.type = parts[1];
//            m.member = parts[2]+parts[3];
//            addMember(m);
//        }
//    }
    //Este metodo obtiene el nombre del package donde estan las clases
    public void getPackageName(String line) {
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
        } else if (getClassName(line)) {
            discriptor.packageName = "default";
        }
    }
    

    // Este metodo obtiene el nombre de cada clase y clasifica si es class o interface ademas dde obtener los extends e implements.
    public boolean getClassName(String line) {
        HashMap<String,String> map=new HashMap<>();
    
        line = line.trim();
        int cont = 0;
        if (line.contains("class") || line.contains("interface")) {
            String[] parts = line.split("\\s+");
//            for (int i = 0; i < parts.length; i++) {
//                System.out.println(parts[i]);
//            }
            if (line.contains("interface")) {
                discriptor.interfaz = true;
            }
            //Este if es para leer cuando las clase tiene un extends e implements al mismo tiempo
//            if (parts[0].concat(" ").concat(parts[1]).equalsIgnoreCase("public interface")) {
//                System.out.println(parts[2]);
//                System.out.println(discriptor.packageName);
//                System.out.println(parts[2]+discriptor.packageName);
//                map.put(parts[2],discriptor.packageName);
//                for (Map.Entry<String, String> entry : map.entrySet()) {
//                    String llave = entry.getKey();
//                    key=llave;
//                    System.out.println(key+"key");
//                    Object valor = entry.getValue();
//                    value=valor;
//                    
////                    System.out.println(value+"value");
////                    
////                }
//               // System.out.println(parts[0].concat(" ").concat(parts[1]));
//            }
//            }
         
            if (line.contains("extends") && line.contains("implements")) {
//                for (Map.Entry<String, Object> entry : map.entrySet()) {
//                        String key = entry.getKey();
//                        System.out.println(key+"1");
//                        Object value = entry.getValue();
//                        System.out.println(value+"2");
//                }
                cont = +1;
                discriptor.implement = discriptor.packageName + "."+ parts[6].substring(0, parts[6].length());
                discriptor.name = discriptor.packageName + "." + parts[2].substring(0, parts[2].length());
                if (parts[3].equals("extends")) {
                    discriptor.extend = discriptor.packageName + "." + parts[4].substring(0, parts[4].length());
                    //System.out.println(discriptor.extend);

                }
                System.out.println(parts[5]);
                
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
            }
               
            
            //Este if tiene un contador para que no entre 2 veces ya que las condiciones en el if de extend de arriba son similares a este
            //por eso tiene una contador para verificar si entro en el if anterior y si entro que aca no entre.
            if (parts[3].equals("extends") && cont <= 0) {
                discriptor.extend = discriptor.packageName + "." + parts[4].substring(0, parts[4].length() - 1);
            } else if (parts[3].equals("implements")) {
                discriptor.implement = discriptor.packageName + "." + parts[4].substring(0, parts[4].length() - 1);
            }
//    
            
            System.out.println(line.split("\\s+"));
            if (parts.length == 2 && isClassOrInterface(parts[0])) {
                if (parts[1].endsWith("{")) {
                    discriptor.name = discriptor.packageName + "." + parts[1].substring(0, parts[1].length() - 1);

                } else {
                    discriptor.name = discriptor.packageName + "." + parts[1];
                }
//                discriptor.modifier = Constants.MODIFIER_DEFAULT;

            } else if (parts.length == 3 && isClassOrInterface(parts[1])) {
                if (parts[2].endsWith("{")) {
                    discriptor.name = discriptor.packageName + "." + parts[2].substring(0, parts[1].length() - 1);

                } else {
                    discriptor.name = discriptor.packageName + "." + parts[2];
                }
                // discriptor.modifier = parts[0];
            } else if (parts.length == 3 && isClassOrInterface(parts[0]) && parts[2].equals("{")) {
                discriptor.name = discriptor.packageName + "." + parts[1];
                //	discriptor.modifier=Constants.MODIFIER_DEFAULT;
            } else if (parts.length == 4 && isClassOrInterface(parts[1]) && parts[3].equals("{")) {
                discriptor.name = discriptor.packageName + "." + parts[2];
//                //	discriptor.modifier=parts[0];
            } else if (parts.length == 5 && isClassOrInterface(parts[1]) && isParentLinker(parts[3])) {
                discriptor.name = discriptor.packageName + "." + parts[2];

//            } else {
//                discriptor.extend = parts[4];
//                discriptor.implement = parts[4];
            }
            // discriptor.modifier = parts[0];
            if (discriptor.name != null) {
//                if (discriptor.extend == null) {
//                    discriptor.extend = "java.lang.Object";
//                }
                /*	if(discriptor.modifier==null) {
				discriptor.modifier=Constants.MODIFIER_DEFAULT;
			}*/
                return true;
            }
        }
        
    
        return false;
    }
    //compara entre extends e implements
    public boolean isParentLinker(String line) {
        return line.trim().equals("extends") || line.trim().equals("implements");
    }

    //compara entre class e interfaz
    public boolean isClassOrInterface(String part) {
        return part.trim().equals("class") || part.trim().equals("interface");
    }

}
