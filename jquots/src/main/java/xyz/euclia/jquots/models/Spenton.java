/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xyz.euclia.jquots.models;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pantelispanka
 */
public class Spenton {
    
    private String appid;
    
    private Map<String, Object> usage;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public Map<String, Object> getUsage() {
        return usage;
    }

    public void setUsage(HashMap<String, Object> usage) {
        this.usage = usage;
    }
    
}
