package com.bobocode.av.app.service;

import com.bobocode.av.trimmer.annotation.Trimmed;
import org.springframework.stereotype.Service;

@Service
@Trimmed
public class EchoService {

    public String echo(final String msg) {
        System.out.println(msg);
        return "    " + msg;
    }
}
