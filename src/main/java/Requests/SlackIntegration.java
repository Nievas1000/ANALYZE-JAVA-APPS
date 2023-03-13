/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Requests;

import Classes.ClassParser;
import Classes.Credentials;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.slack.api.Slack;
import com.slack.api.SlackConfig;
import com.slack.api.webhook.Payload;
import com.slack.api.webhook.WebhookResponse;
import java.util.HashMap;


public class SlackIntegration {
    private static String weekhookurl="https://hooks.slack.com/services/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";
    private static String token="xoxb-4257485974725-4916310571927-SBiZh3ahzkMXGomnu1xUExc0";
    private static String channel="C04SY867CMD";
    ClassParser cp=new ClassParser();
    
    
    public void send(String message){
         HashMap<String, String> map = new HashMap<>();
            map.put("userApplicationKey", cp.getKey());
            map.put("applicationName", cp.getProjectName());
            map.put("error", message);
            Gson gson = new Gson();
            JsonObject json = gson.toJsonTree(map).getAsJsonObject();
        try{
        StringBuilder str=new StringBuilder();
        str.append(json.toString());
        Payload payload= Payload.builder().channel(channel).text(str.toString()).build();
        WebhookResponse resp=Slack.getInstance().send(weekhookurl,payload);
        }catch(Exception e){
            e.printStackTrace();
            
        }
    }
}
