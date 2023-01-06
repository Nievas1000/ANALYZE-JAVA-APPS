package Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


public class DataBaseData {

    public String db;
	public String type;
	public String username;
	public String password;
	public String host;
	public String port;

    public DataBaseData() {
    }

    public DataBaseData(String db, String type, String username, String password, String host, String port) {
        this.db = db;
        this.type = type;
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
        
        
     public List getTables() throws ClassNotFoundException, SQLException {
        List<String> tables=new ArrayList();
        Class.forName("com.mysql.jdbc.Driver");
        Connection conexion = DriverManager.getConnection(
                "jdbc:mysql://"+host+":"+port+"/"+db, username, password);

                Statement statement=conexion.createStatement();
		ResultSet rs=statement.executeQuery("show tables from "+"freedb_practicas");
               
                
                while(rs.next()) {
			tables.add(rs.getString(1));
                      
		}
		     
        return tables;
    }
}
