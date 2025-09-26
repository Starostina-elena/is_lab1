package org.lia;

import org.lia.service.LocationService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.lia.config.JpaConfig;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JpaConfig.class);
        LocationService locationService = context.getBean(LocationService.class);
        locationService.saveLocation(1.2, 1.2f, 1.2f, "city");
    }
}
