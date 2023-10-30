package com.example.spring.CafeManagerApplication.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    private final String CLOUD_NAME = "dkk1iqnch";
    private final String API_KEY = "713439211635238";
    private final String API_SECRET = "iPKTPz8F7X8_1sa1-iptM_NttDU";

    @Bean
    public Cloudinary cloudinary() {
        Map<String,String> config = new HashMap<>();
        config.put("cloud_name",CLOUD_NAME);
        config.put("api_key",API_KEY);
        config.put("api_secret",API_SECRET);

        return new Cloudinary(config);
    }
}
