package com.devzees.appointment.configs;

import com.devzees.appointment.services.HealthProviderClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

    @LoadBalanced
    @Bean
    RestClient.Builder restClientBuilder() {
        return RestClient.builder();
    }

    @Bean
    public HttpServiceProxyFactory httpServiceProxyFactory(RestClient.Builder restClientBuilder) {
        RestClient restClient = restClientBuilder
                .baseUrl("http://HEALTHPROVIDER-SERVICE")
                .build();
        RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
        return HttpServiceProxyFactory
                .builderFor(restClientAdapter)
                .build();
    }

    @Bean
    public HealthProviderClient healthProviderClient(HttpServiceProxyFactory httpServiceProxyFactory) {
        return httpServiceProxyFactory.createClient(HealthProviderClient.class);
    }

}