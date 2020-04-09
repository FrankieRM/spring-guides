package com.example.accessingdataneo4j;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.List;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
@EnableNeo4jRepositories
public class AccessingDataNeo4jApplication {

    private final static Logger LOGGER = getLogger(AccessingDataNeo4jApplication.class);

    public static void main(String[] args) {
        run(AccessingDataNeo4jApplication.class, args).close();
    }

    @Bean
    CommandLineRunner demo(PersonRepository personRepository) {
        return args -> {
            personRepository.deleteAll();

            Person greg = new Person("Greg");
            Person roy = new Person("Roy");
            Person craig = new Person("Craig");

            List<Person> team = asList(greg, roy, craig);

            LOGGER.info("Before linking up with Neo4j...");

            team.forEach(person -> LOGGER.info("\t" + person.toString()));

            personRepository.save(greg);
            personRepository.save(roy);
            personRepository.save(craig);

            greg = personRepository.findByName(greg.getName());
            greg.worksWith(roy);
            greg.worksWith(craig);
            personRepository.save(greg);

            roy = personRepository.findByName(roy.getName());
            roy.worksWith(craig);
            // We already know that roy works with greg
            personRepository.save(roy);

            // We already know craig works with roy and greg
            LOGGER.info("Lookup each person by name...");
            team.forEach(person -> LOGGER.info(
                    "\t" + personRepository.findByName(person.getName()).toString()));
        };
    }
}