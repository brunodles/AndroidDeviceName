/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brunodles.device;

import com.brunodles.device.api.model.Device;
import com.brunodles.util.JsonHelper;
import com.brunodles.util.WebClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dev
 */
public class DeviceOnPageFinder {

    private static final String DEVICES_URL = "https://support.google.com/googleplay/answer/1727131?hl=en";

    private static final String DEVICE_IN_PAGE_REGEX = "<li>(.*?) \\((.*?)/(.*?)\\)</li>";

//    public static void main(String[] args) throws MalformedURLException, IOException {
////        AndroidDeviceName androidDeviceName = new AndroidDeviceName();
////        String page = "<li>Nexus 7 (deb/Nexus 7)</li>";
//        long startTime = System.currentTimeMillis();
//        String page = webDevicesPage();
//        System.out.println("Finished in "+(System.currentTimeMillis()-startTime));
//        startTime = System.currentTimeMillis();
//        List<Device> findDevicesInPage = listDevicesInPage(page);
//        System.out.println("Finished in "+(System.currentTimeMillis()-startTime));
//        for (Device device : findDevicesInPage)
//            System.out.println(device);
//    }

    private static List<Device> listDevicesInPage(String page) {
        Pattern pattern = Pattern.compile(DEVICE_IN_PAGE_REGEX);
        Matcher matcher = pattern.matcher(page);
        ArrayList<Device> devices = new ArrayList<Device>();
        while (matcher.find()) {
//            System.out.println(matcher.group(0));
            Device device = new Device();
            device.name = matcher.group(1);
            device.device = matcher.group(2);
            device.model = matcher.group(3);
            devices.add(device);
//            print(matcher);
        }
        return devices;
    }

    private void print(Matcher matcher) {
        System.out.printf("Real Name: %s\n"
                + " - Device:%s\n"
                + " - Model :%s\n",
                matcher.group(1),
                matcher.group(2),
                matcher.group(3)
        );
        System.out.println("-----------------------");
    }

    private static String webDevicesPage() throws IOException {
        return new WebClient(DEVICES_URL).doGet().response;
    }

    private static String localDevicesPage() throws IOException {
        InputStream resource = DeviceOnPageFinder.class.getResourceAsStream("/devicesPage.htm");
        return WebClient.readInputStream(resource);
    }

    public static void buildDevicesFile(List<Device> devices) throws IOException {
        StringBuilder stringBuilder = new StringBuilder("[");
        boolean first = true;
        for (Device device : devices) {
            if (first)
                first = false;
            else
                stringBuilder.append(",\n");
            stringBuilder.append(JsonHelper.toJson(device.toMap()));
//            find(device);
//            save(device);
        }
        stringBuilder.append("]");
//        System.out.println(stringBuilder.toString());
//        String devicesJsonPath = AndroidDeviceName.class.getResource("/devices.json").getPath();
//        System.out.println("devicesJsonPath "+devicesJsonPath);
//        final File file = new File(devicesJsonPath);
        File file = new File("./src/devices.json");
        System.out.println(file.getAbsoluteFile());
        FileWriter fileWriter = new FileWriter(file, false);
        fileWriter.write(stringBuilder.toString());
        fileWriter.flush();
        fileWriter.close();
    }
}
