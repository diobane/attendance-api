package com.attendance_api.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Attendance API")
                        .version("1.0.0")
                        .description("This API manages the registration of families, children, and guardians " +
                                "for church events, controlling attendance, check-ins, and check-outs.")
                        .termsOfService("https://your-terms-of-service-url.com")
                        .contact(new Contact()
                                .name("API Support Team")
                                .email("support@attendanceapi.com")
                                .url("https://github.com/diobane/attendance-api"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://springdoc.org")));
    }
}
