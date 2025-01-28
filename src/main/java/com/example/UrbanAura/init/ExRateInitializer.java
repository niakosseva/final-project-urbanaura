//package com.example.UrbanAura.init;
//
//import com.example.UrbanAura.services.exCurrency.ExRateService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ExRateInitializer implements CommandLineRunner {
//
// private final ExRateService exRateService;
//
//    public ExRateInitializer(ExRateService exRateService) {
//        this.exRateService = exRateService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        if(!exRateService.hasInitializedRates()) {
//            exRateService.updateRates(exRateService.fetchRates());
//        }
//    }
//}
