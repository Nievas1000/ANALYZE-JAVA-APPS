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
	public String modifier;
	public String extend;
	public String lastModified;
	public String constructor;
	public List<Member> members;
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