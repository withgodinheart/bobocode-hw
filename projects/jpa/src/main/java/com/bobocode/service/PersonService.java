package com.bobocode.service;

import com.bobocode.entity.Note;
import com.bobocode.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    @Transactional
    @EventListener(classes = ContextRefreshedEvent.class)
    public void insert() {
        var person = personRepository.findById(2L).orElseThrow();
        var notes = IntStream.range(0, 10)
                .mapToObj(i -> Note.builder().body("Body" + i).build())
                .toList();
        person.addNotes(notes);
    }
}
