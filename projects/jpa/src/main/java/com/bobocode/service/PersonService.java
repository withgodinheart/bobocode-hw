package com.bobocode.service;

import com.bobocode.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

//    @EventListener(classes = ContextRefreshedEvent.class)
    @Transactional
    public void run() {
//        var person = personRepository.findById(2L).orElseThrow();
//        System.out.println(person.getFirst_name());

//        var person = personRepository.myMethod(1L);
//        System.out.println(person);
    }

    @Transactional
    @EventListener(classes = ContextRefreshedEvent.class)
    public void doWork() {
//        personRepository.findAll().forEach(System.out::println);

        personRepository.streamAllBy().forEach(System.out::println);
    }
}
