/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Classes.ClassDiscriptor.Member;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClassParser {

//import solutionparser.handlers.ClassDiscriptor.Member;
    private Path file;
    private ClassDiscriptor discriptor;
    //private DatabaseDescriptor dbDescriptor;

    public ClassParser(File file) {
        this.file = file.toPath();

        this.discriptor = new ClassDiscriptor();
	//this.dbDescriptor=new DatabaseDescriptor();
        discriptor.lastModified = String.valueOf(file.lastModified());
        discriptor.members = new ArrayList();
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
//            if (discriptor.packageName == null) {
//                getPackageName(lines.get(i));
//                continue;
//            }
            if (discriptor.name == null) {
                getClassName(lines.get(i));
                continue;
            }
            findMembers(lines.get(i));
        }
    }

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

    private void addMember(Member m) {
        if (discriptor.members == null) {
            discriptor.members = new ArrayList();
        }
        discriptor.members.add(m);
    }

    public void findMembers(String line) {
        line = line.trim();
        String[] parts = line.split("\\s+");
        if (parts.length == 2 && parts[1].endsWith(";")) {
            Member m = new Member();
            m.modifier = Constants.MODIFIER_DEFAULT;
            m.type = parts[0];
            m.member = parts[1].substring(0, parts[1].length() - 1);
            addMember(m);
        } else if (parts.length == 3 && isModifier(parts[0]) && parts[2].endsWith(";")) {
            Member m = new Member();
            m.modifier = parts[0];
            m.type = parts[1];
            m.member = parts[2].substring(0, parts[2].length() - 1);
            addMember(m);
        }else if(parts.length > 3 && isModifier(parts[0]) && line.endsWith(";")){
            Member m = new Member();
            m.modifier = parts[0];
            m.type = parts[1];
            m.member = parts[2]+parts[3];
            addMember(m);
        }
    }

//    public void getPackageName(String line) {
//        line = line.trim();
//        if (line.startsWith("package")) {
//            String[] parts = line.split("\\s+");
//            if (parts.length == 2) {
//                if (parts[1].endsWith(";")) {
//                    discriptor.packageName = parts[1].substring(0, parts[1].length() - 1);
//                } else {
//                    discriptor.packageName = parts[1];
//                }
//            } else if (parts.length == 3 && parts[2].equals(";")) {
//                discriptor.packageName = parts[1];
//            }
//        } else if (getClassName(line)) {
//            discriptor.packageName = "default";
//        }
//    }

    public boolean getClassName(String line) {
        line = line.trim();
        if (line.contains("class") || line.contains("interface")) {
            String[] parts = line.split("\\s+");

            if (parts[3].equals("extends")) {
                discriptor.extend = parts[4].substring(0, parts[4].length() - 1);
            } else if (parts[3].equals("implements")) {
                discriptor.implement = parts[4].substring(0, parts[4].length() - 1);
            }
//            }else if(parts[5].equals("implements")){
//                discriptor.implement = parts[4].substring(0, parts[4].length() - 1);
//            }
            System.out.println(line.split("\\s+"));
            if (parts.length == 2 && isClassOrInterface(parts[0])) {
                if (parts[1].endsWith("{")) {
                    discriptor.name = parts[1].substring(0, parts[1].length() - 1);

                } else {
                    discriptor.name = parts[1];
                }
                discriptor.modifier = Constants.MODIFIER_DEFAULT;

            } else if (parts.length == 3 && isModifier(parts[0]) && isClassOrInterface(parts[1])) {
                if (parts[2].endsWith("{")) {
                    discriptor.name = parts[2].substring(0, parts[1].length() - 1);

                } else {
                    discriptor.name = parts[2];
                }
                discriptor.modifier = parts[0];
            } else if (parts.length == 3 && isClassOrInterface(parts[0]) && parts[2].equals("{")) {
                discriptor.name = parts[1];
                //	discriptor.modifier=Constants.MODIFIER_DEFAULT;
            } else if (parts.length == 4 && isModifier(parts[0]) && isClassOrInterface(parts[1]) && parts[3].equals("{")) {
                discriptor.name = parts[2];
                //	discriptor.modifier=parts[0];
            } else if (parts.length == 5 && isModifier(parts[0]) && isClassOrInterface(parts[1]) && isParentLinker(parts[3])) {
                discriptor.name = parts[2];

            } else {
                discriptor.extend = parts[4];
                discriptor.implement = parts[4];
            }

            discriptor.modifier = parts[0];

            if (discriptor.name != null) {
                if (discriptor.extend == null) {
                    discriptor.extend = "java.lang.Object";
                }
                /*	if(discriptor.modifier==null) {
				discriptor.modifier=Constants.MODIFIER_DEFAULT;
			}*/
                return true;
            }
        }
        return false;
    }

    public boolean isParentLinker(String line) {
        return line.trim().equals("extends") || line.trim().equals("implements");
    }

    public boolean isClassOrInterface(String part) {
        return part.trim().equals("class") || part.trim().equals("interface");
    }

    public boolean isModifier(String modifier) {
        return modifier.equals(Constants.MODIFIER_PRIVATE) || modifier.equals(Constants.MODIFIER_PROTECTED) || modifier.equals(Constants.MODIFIER_PUBLIC);
    }
}
