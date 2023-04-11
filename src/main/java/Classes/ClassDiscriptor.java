//---------------IMPORTANTE------------------------
//Esta clase tiene los atributos que se muestran en el json.
package Classes;

import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ClassDiscriptor {
    

    public String packageName;
    public String name;
    public Boolean interfaz;
    public String pathfile;
    public String userkey;
    public List<String> extend;
    public List<String> implement;
    //se define un hashset para que no haya valores repetidos
    public Set<String> constructor;
    public List<String> datasources;

    //public String lastModified;
    public ClassDiscriptor() {

    }

    public ClassDiscriptor(String packageName, String name, Boolean interfaz, String pathfile, String userkey, List<String> extend, List<String> implement, Set<String> constructor, List<String> datasources) {
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

    
    public List<String> getDatasources() {
        return datasources;
    }

    public void setDatasources(List<String>list)  {
        
        this.datasources=list;
       
        
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

    public List<String> getExtend() {
        return extend;
    }

    public void setExtend(List<String> extend) {
        this.extend = extend;
    }

    public List<String> getImplement() {
        return implement;
    }

    public void setImplement(List<String> implement) {
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

    @Override
    public String toString() {
        return "ClassDiscriptor{" + "packageName=" + packageName + ", name=" + name + ", interfaz=" + interfaz + ", extend=" + extend + ", implement=" + implement + ", constructor=" + constructor + '}';
    }

    
}
