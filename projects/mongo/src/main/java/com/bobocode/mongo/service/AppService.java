package com.bobocode.mongo.service;

import com.bobocode.mongo.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppService {

    private final PersonRepository personRepository;

    @EventListener(classes = ContextRefreshedEvent.class)
    public void run() {
//        Print.INSTANCE.print();
        System.out.println(personRepository.findAll());
    }
}
