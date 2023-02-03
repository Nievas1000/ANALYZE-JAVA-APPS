//---------------IMPORTANTE------------------------
//Esta clase tiene los atributos que se muestran en el json.
package Classes;

import java.lang.*;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ClassDiscriptor {
    

    public String packageName;
    public String name;
    public Boolean interfaz;
    public String pathfile;
    public String userkey;
    public String extend;
    public String implement;
    //se define un hashset para que no haya valores repetidos
    public Set<String> constructor;
    public HashMap<String, List> datasources;

    //public String lastModified;
    public ClassDiscriptor() {

    }

    public ClassDiscriptor(String packageName, String name, Boolean interfaz, String pathfile, String userkey, String extend, String implement, Set<String> constructor, HashMap<String, List> datasources) {
        this.packageName = packageName;
        this.name = name;
        this.interfaz = interfaz;
        this.pathfile = pathfile;
        this.userkey = userkey;
        this.extend = extend;
        this.implement = implement;
        this.constructor = constructor;
        this.datasources = datasources;
    }

    

    public HashMap<String, List> getDatasources() {
        return datasources;
    }

    public void setDatasources(HashMap<String, List> datasources) {
        this.datasources = datasources;
    }

    public Set<String> getConstructor() {
        return constructor;
    }

    public void setConstructor(Set<String> constructor) {
        this.constructor = constructor;
    }



    
    public String getPathfile() {
        return pathfile;
    }

    public void setPathfile(String pathfile) {
        this.pathfile = pathfile;
    }

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(Boolean interfaz) {
        this.interfaz = interfaz;
    }


    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getImplement() {
        return implement;
    }

    public void setImplement(String implement) {
        this.implement = implement;
    }

   
//	public static class Member{
//		public String modifier;
//		public String type;
//		public String member;
//		public HashMap<String,String> toJSON(){
//			HashMap<String,String> map=new HashMap();
//			map.put("modifier", modifier);
//			map.put("type", type);
//			map.put("member", member);
//			return map;
//		}
    void setDatasources(String relation, List<String> tables) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
