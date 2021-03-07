package org.winjay.antireplayattack.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("relayattack")
public class RelayAttackController {
    @PutMapping("/{tenantid}/role/{roleid}")
    public String addSomething(@PathVariable("tenantid") String tentantId, @PathVariable("roleid") String roleId){
        String result = "add Something success for tentantId = " +  tentantId + ", roleId=" + roleId;
        return result;
    }

    @PutMapping("/{tenantid}/role1/{roleid}")
    public String addSomething2(@PathVariable("tenantid") String tentantId, @PathVariable("roleid") String roleId){
        String result = "add Something success for tentantId = " +  tentantId + ", roleId=" + roleId;
        return result;
    }

}
