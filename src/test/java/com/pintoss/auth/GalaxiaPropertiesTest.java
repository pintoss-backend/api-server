//package com.pintoss.auth;
//
//import com.pintoss.auth.common.client.billgate.GalaxiaApiProperties;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//@SpringBootTest
//@ActiveProfiles("test")
//public class GalaxiaPropertiesTest {
//
//    @Autowired
//    private GalaxiaApiProperties galaxiaApiProperties;
//
//
//    @Test
//    void test() {
//        System.out.println(galaxiaApiProperties.getSecret().getKey());
//        System.out.println(galaxiaApiProperties.getSecret().getIv());
//        System.out.println(galaxiaApiProperties.getServer().getHost());
//        System.out.println(galaxiaApiProperties.getServer().getPort());
//        System.out.println(galaxiaApiProperties.getServer().getTimeOut());
//    }
//
//}
