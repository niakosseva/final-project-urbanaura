//package com.example.UrbanAura.config;
//
//import jakarta.annotation.PostConstruct;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//@Getter
//@Setter
//@Configuration
//@ConfigurationProperties(prefix = "forex.api")
//public class ForexApiConfig {
//
//    private String key;
//    private String url;
//    private String base;
//
//    public ForexApiConfig() {
//    }
//
//    @PostConstruct
//    public void checkConfig() {
//        verifyNotNullOrEmpty("key", key);
//        verifyNotNullOrEmpty("base", base);
//        verifyNotNullOrEmpty("url", url);
//        if (!"USD".equals(base)) {
//            throw new IllegalStateException("Forex API config is incorrect.");
//        }
//    }
//
//    private static void verifyNotNullOrEmpty(String name, String value) {
//        if (value == null || value.isBlank()) {
//            throw new IllegalArgumentException("Property " +
//                    name + "cannot be empty.");
//        }
//    }
//}
