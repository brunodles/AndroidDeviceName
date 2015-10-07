/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brunodles.device;

import com.brunodles.device.api.model.Device;
import com.brunodles.util.JsonHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bruno
 */
public class DeviceApi {

    private final Device device = new Device();

    public static void main(String[] args) throws IOException {
        List<Device> findList = new DeviceApi().model("Nexus 5").findList();
        for (Device device : findList)
            System.out.println(device);

        System.out.println(new DeviceApi().model("GT-I9300").findSingle());
    }

    public DeviceApi model(String model) {
        device.model = model;
        return this;
    }

    public DeviceApi device(String device) {
        this.device.device = device;
        return this;
    }

    public DeviceApi name(String name) {
        this.device.name = name;
        return this;
    }

    public Device find() throws IOException {
        String response = ParseIntegrator.device(device).response;
        response = JsonHelper.findListBracers(response);
        response = JsonHelper.findFirstJson(response);
        return deviceOnJson(response);
    }

    public Device findSingle() throws IOException {
        String response = ParseIntegrator.findSingle(device).response;
        response = JsonHelper.findListBracers(response);
        response = JsonHelper.findFirstJson(response);
        return deviceOnJson(response);
    }

    public List<Device> findList() throws IOException {
        String response = ParseIntegrator.findList(device).response;
        response = JsonHelper.findListBracers(response);
        List<String> jsonList = JsonHelper.findJsonList(response);
        ArrayList<Device> devices = new ArrayList<Device>();
        for (String string : jsonList)
            devices.add(deviceOnJson(string));
        return devices;
    }

    private static Device deviceOnJson(String response) {
        String json = JsonHelper.findFirstJson(response);
        if (null != json)
            return new Device(JsonHelper.toValues(json));
        return null;
    }
}
