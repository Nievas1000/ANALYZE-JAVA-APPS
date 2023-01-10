/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;


import java.lang.*;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class ClassDiscriptor {
	public String packageName;
	public String name;
        public Boolean interfaz=false;
        public String constructor;
	public String modifier;
	public String extend;
        public String implement;
	public String lastModified;
	public List<Member> members;

    public ClassDiscriptor() {
    }

    public ClassDiscriptor(String packageName, String name, String constructor, String modifier, String extend, String implement, String lastModified, List<Member> members) {
        this.packageName = packageName;
        this.name = name;
        this.constructor = constructor;
        this.modifier = modifier;
        this.extend = extend;
        this.implement = implement;
        this.lastModified = lastModified;
        this.members = members;
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

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
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

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }


    
    

        
        
   
        
        
        
	public static class Member{
		public String modifier;
		public String type;
		public String member;
		public HashMap<String,String> toJSON(){
			HashMap<String,String> map=new HashMap();
			map.put("modifier", modifier);
			map.put("type", type);
			map.put("member", member);
			return map;
		}
	}
}