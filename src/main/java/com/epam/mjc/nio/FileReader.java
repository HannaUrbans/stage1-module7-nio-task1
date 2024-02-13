package com.epam.mjc.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class FileReader {
    private String name;
    private Integer age;
    private String email;
    private Long phone;


    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public Long getPhone() {
        return phone;
    }

    public static Profile getDataFromFile(File file) {
        String fileData = readFileData(file);
        Map<String, String> keyValuePairs = parseKeyValuePairs(fileData);
              //  for (Map.Entry<String, String> entry : keyValuePairs.entrySet()) {
                    //String key = entry.getKey();
                    //String value = entry.getValue();
        String name = keyValuePairs.get("Name");
        int age = Integer.parseInt(keyValuePairs.get("Age"));
        String email = keyValuePairs.get("Email");
        long phone = Long.parseLong(keyValuePairs.get("Phone"));
        return new Profile(name, age, email, phone);}

    private static String readFileData(File file) {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(file)) {
            FileChannel channel = fis.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) != -1) {
                buffer.flip();
                sb.append(StandardCharsets.UTF_8.decode(buffer));
                buffer.clear();
            }
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        return sb.toString();
    }

    public static Map<String, String> parseKeyValuePairs(String fileData) {
        Map<String, String> keyValuePairs = new HashMap<>();
        String[] parsedLines = fileData.split("\n");
        for (String parsedLine : parsedLines) {
            String[] words = parsedLine.split(":");
            if (words.length == 2) {
                String key = words[0].trim();
                String value = words[1].trim();
                keyValuePairs.put(key, value);
            }
        }
        return keyValuePairs;
    }


    public void main(String[] args) {

        try {
            File file = new File("src/main/resources/Profile.txt");
            getDataFromFile(file);
            System.out.println("Name: " + this.getName());
            for (String s : Arrays.asList("Age: " + this.getAge(), "Email: " + this.getEmail(), "Phone: " + this.getPhone())) {
                System.out.println(s);
            }


        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            System.out.println("Stack trace:");
            for (StackTraceElement element : e.getStackTrace()) {
                System.out.println(element.toString());
            }
        }

    }
}


