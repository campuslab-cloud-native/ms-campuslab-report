package cl.campuslab.report.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
public class ReportTimeConfiguration {

    @Bean
    public Clock reportClock() {
        return Clock.system(ZoneId.of("America/Santiago"));
    }
}
