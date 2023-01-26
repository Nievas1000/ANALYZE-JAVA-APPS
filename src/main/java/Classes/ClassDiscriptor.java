//---------------IMPORTANTE------------------------
//Esta clase tiene los atributos que se muestran en el json.
package Classes;

import java.lang.*;
import java.util.HashMap;


public class ClassDiscriptor {

    public String packageName;
    public String name;
    public Boolean interfaz;
    public String constructor;
    //public String modifier;
    public String pathfile;
    public String userkey;
    public String extend;
    public String implement;
    public String number=3;
    
    //public String lastModified;

    public ClassDiscriptor() {

    }

    public ClassDiscriptor(String packageName, String name, Boolean interfaz, String constructor, String pathfile, String userkey, String extend, String implement) {
        this.packageName = packageName;
        this.name = name;
        this.interfaz = interfaz;
        this.constructor = constructor;
        this.pathfile = pathfile;
        this.userkey = userkey;
        this.extend = extend;
        this.implement = implement;
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

    public String getConstructor() {
        return constructor;
    }

    public void setConstructor(String constructor) {
        this.constructor = constructor;
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
}
