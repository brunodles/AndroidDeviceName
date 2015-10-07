/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brunodles.device;

import com.brunodles.device.api.model.Device;
import com.brunodles.util.JsonHelper;
import com.brunodles.util.WebClient;

import java.io.IOException;

/**
 *
 * @author bruno
 */
public class ParseIntegrator {

    private static final String PARSE_APP_ID = "CR8YxEDmDip01Wt0M2BoIyI6b5uQCHEKpJnpRioQ";
    private static final String PARSE_REST_KEY = "w2iN7xlaueZW75F4HkrVsknyo8ITpcbhssChmeuO";

    private final static String PARSE_DEVICE_FUNCTION_URL = "https://api.parse.com/1/functions/device";
    private final static String PARSE_DEVICE_CLASS_URL = "https://api.parse.com/1/classes/Device";

    public static WebClient.Response findSingle(Device device) throws IOException {
        return find(device, 1);
    }

    public static WebClient.Response findList(Device device) throws IOException {
        return find(device, 0);
    }

    private static WebClient.Response find(Device device, int limit) throws IOException {
        final WebClient webClient = newWebClient()
                .addUrlParameter("where", JsonHelper.toJson(device.toMap()));
        //                .addUrlParameter("order", "device")
        if (limit > 0)
            webClient.addUrlParameter("limit", String.valueOf(limit));

        WebClient.Response response = webClient.doGet();
        return response;
    }

    public static WebClient.Response save(Device device) throws IOException {
        final String deviceJson = JsonHelper.toJson(device.toMap());
        System.out.println("deviceJson = " + deviceJson);

        WebClient.Response response = newWebClient()
                .setOut(deviceJson)
                .doPost();

        return response;
    }

    public static WebClient.Response device(Device device) throws IOException {
        final String deviceJson = JsonHelper.toJson(device.toMap());
        WebClient webClient = newWebClient(PARSE_DEVICE_FUNCTION_URL)
                .setOut(deviceJson);
        return webClient.doPost();
    }

    private static WebClient newWebClient() {
        return newWebClient(PARSE_DEVICE_CLASS_URL);
    }

    private static WebClient newWebClient(String url) {
        return new WebClient(url)
                .setContentTypeJson()
                .addRequestProperty("X-Parse-Application-Id", PARSE_APP_ID)
                .addRequestProperty("X-Parse-REST-API-Key", PARSE_REST_KEY);
    }

    public static void main(String[] args) throws IOException {
        Device device = new Device();
        device.name = "Nexus 5";
        device.model = "Nexus 5";
        device.device = "hammerhead";
        System.out.println(ParseIntegrator.device(device));
    }
}
