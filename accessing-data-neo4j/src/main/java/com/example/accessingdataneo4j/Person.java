package com.example.accessingdataneo4j;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.neo4j.ogm.annotation.Relationship.UNDIRECTED;

@NodeEntity
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Getter
    @Setter
    private String name;

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "TEAMMATE", direction = UNDIRECTED)
    public Set<Person> teammates;

    public Person(String name) {
        this.name = name;
    }

    public void worksWith(Person person) {
        if (this.teammates == null) {
            this.teammates = new HashSet<>();
        }
        this.teammates.add(person);
    }

    public String toString() {
        List<String> teammates = ofNullable(this.teammates)
                .orElse(emptySet())
                .stream()
                .map(Person::getName)
                .collect(toList());
        return format("%s's teammates => %s", this.name, teammates);
    }
}