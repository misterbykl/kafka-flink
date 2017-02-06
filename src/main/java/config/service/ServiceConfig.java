package config.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import service.Service;

@PropertySource(value = "file:./conf/application.properties")
@Configuration
public class ServiceConfig {

    /**
     * Service service.
     *
     * @return the service
     * <p>
     * <p>
     * misterbykl
     * <p>
     * 23/01/17 22:10
     */
    @Bean
    public Service service(@Value("${processor.type}") String processorType) {
        return new Service(processorType);
    }
}