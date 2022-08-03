package com.bobocode.hw19.controller;

import com.bobocode.hw19.model.PersonStatus;
import com.bobocode.hw19.model.Persons;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;

@RestController
@RequestMapping("/reactive/persons")
public class ReactiveController {

    @GetMapping("/max")
    @SneakyThrows
    public PersonStatus getMax() {
        var restTemplate = new RestTemplate();
        var url = "http://localhost:8081/reactive/persons";
//        var response = restTemplate.getForObject(url, JsonNode.class);
//        return StreamSupport.stream(response.spliterator(), false)
//                .map(el -> new PersonStatus(
//                        el.get("firstName").asText(),
//                        el.get("lastName").asText(),
//                        el.get("reactiveProgrammingLevel").asInt(),
//                        el.get("hasSpringWebFluxExperience").asBoolean()))
//                .max(Comparator.comparingInt(PersonStatus::getReactiveProgrammingLevel))
//                .orElseThrow();

        var response = restTemplate.getForObject(url, PersonStatus[].class);
        return Arrays.stream(response)
                .max(Comparator.comparingInt(PersonStatus::getReactiveProgrammingLevel))
                .orElseThrow();

//        var response = restTemplate.getForObject(url, Persons.class);
//        return response.getList().stream()
//                .max(Comparator.comparingInt(PersonStatus::getReactiveProgrammingLevel))
//                .orElseThrow();
    }
}
