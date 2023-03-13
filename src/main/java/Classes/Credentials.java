/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;


public class Credentials {

    public String key;
    public String projectname;     

    public Credentials() {
    }

    public Credentials(String key, String projectname) {
        this.key = key;
        this.projectname = projectname;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }
    
    
}
