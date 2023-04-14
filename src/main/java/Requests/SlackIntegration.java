///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package Requests;
//
//import Classes.ClassParser;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.slack.api.Slack;
//import com.slack.api.SlackConfig;
//import com.slack.api.audit.AuditConfig;
//import com.slack.api.methods.MethodsConfig;
//import com.slack.api.scim.SCIMConfig;
//import com.slack.api.webhook.Payload;
//import com.slack.api.webhook.WebhookResponse;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Properties;
//import org.apache.log4j.BasicConfigurator;
//
public class SlackIntegration {
    
}

//    private static String weekhookurl="https://hooks.slack.com/services/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";
//    private static String token="xoxb-4257485974725-4916310571927-SBiZh3ahzkMXGomnu1xUExc0";
//    private static String channel="C04SY867CMD";
//
//    ClassParser cp = new ClassParser();

//    public void send(String message, String info) {
////        String weekhookurl = null;
////        String token;
////        String channel = null;
////        try (InputStream input = new FileInputStream(System.getProperty("user.dir") + "/" + "slack.txt")) {
////
//////            System.out.println(System.getProperty("user.dir"));
////            Properties prop = new Properties();
////
////            // load a properties file
////            prop.load(input);
////
////            // get the property value and print it out
////            weekhookurl = prop.getProperty("weekhookurl");
////            token = prop.getProperty("token");
////            channel = prop.getProperty("channel");
////
////        } catch (Exception e) {
////            e.getMessage();
////        }
//
//        try{
//        if (!cp.getKey().equals("c6j76d7931a0a04bed50")) {
//
//            HashMap<String, String> map = new HashMap<>();
//            map.put("userApplicationKey", cp.getKey());
//            map.put("applicationName", cp.getProjectName());
//            map.put("error", message);
//            map.put("information", info);
//            Gson gson = new Gson();
//            JsonObject json = gson.toJsonTree(map).getAsJsonObject();
//
////            BasicConfigurator.configure();
//            StringBuilder str = new StringBuilder();
//            str.append(json.toString());
//            Payload payload = Payload.builder().channel(channel).text(str.toString()).build();
//            WebhookResponse resp = Slack.getInstance().send(weekhookurl, payload);
//        }
//        }catch(Exception e){
//            System.out.println("Error");
//           
//            
//        }
//    }
//}
    


