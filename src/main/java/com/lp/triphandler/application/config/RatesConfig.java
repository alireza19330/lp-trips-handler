package com.lp.triphandler.application.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class RatesConfig {

    private Map<String, Map<String, Float>> rates;

}
