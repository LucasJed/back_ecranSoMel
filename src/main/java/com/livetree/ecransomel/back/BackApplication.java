package com.livetree.ecransomel.back;

import com.livetree.ecransomel.back.Entities.TrendTable_Jour;
import com.livetree.ecransomel.back.Reporitory.TrendTable_JourRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.stream.Stream;

@SpringBootApplication
public class BackApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*").allowedOrigins("http://localhost:4200");
            }
        };
    }

    @Bean
    CommandLineRunner init(TrendTable_JourRepository trendTable_jourRepository) {
        return args -> {
            //TODO Partie a supprimer en production
            Stream.of("HEI_PV.Energies.EAtot_neg",
                    "HEI_13RT.Energies.EAtot_pos",
                    "HEI_5RNS.Energies.EAtot_pos",
                    "HA.Energies.EAtot_pos",
                    "Rizomm_PV.EnergiesI60.EAtot_neg",
                    "Rizomm_TGBT. Energies.EAtot_pos"
            ).forEach(name -> {
                TrendTable_Jour building = new TrendTable_Jour(name);
                trendTable_jourRepository.save(building);
            });

            //Fin de partie à supprimé


            trendTable_jourRepository.findAll().forEach(System.out::println);

        };
    }
}
