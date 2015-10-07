/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brunodles.device.api.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dev
 */
public class Device {

    public String device;
    public String name;
    public String model;
    public String brand;

    public Device() {
    }

    public Device(Map<String, String> values) {
        device = values.get("device");
        name = values.get("name");
        model = values.get("model");
        brand = values.get("brand");
    }

    public Map<String, String> toMap() {
        HashMap<String, String> map = new HashMap<String, String>();

        if (null != name) {
            String fixedName = name.replaceAll("\"", "");
            map.put("name", fixedName);
//            b.append("\"name\":\"").append(fixedName).append("\",");
        }
        if (null != device)
            map.put("device", device);
//            b.append("\"device\":\"").append(device).append("\",");

        if (null != model)
            map.put("model", model);
//            b.append("\"model\":\"").append(model).append("\"");

        if (null != brand)
            map.put("brand", brand);
//            b.append("\"model\":\"").append(model).append("\"");

        return map;
    }

    @Override
    public String toString() {
        return "Device{" + "device=" + device + ", name=" + name + ", model=" + model + ", brand=" + brand + '}';
    }
}
