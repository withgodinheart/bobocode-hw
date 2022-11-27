package com.bobocode.bobo.generics;

import com.bobocode.bobo.generics.domain.Developer;
import com.bobocode.bobo.generics.domain.Team;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {

    static Team generateTeam() {
        Developer developer = new Developer();
        developer.setEmployeeNumber(ThreadLocalRandom.current().nextLong(1000));
        developer.setSeniority(getRandomSeniority());
        Team team = new Team();
        team.setLead(developer);
        System.out.println(team);

        return team;
    }

    static Developer.SENIORITY getRandomSeniority() {
        Developer.SENIORITY[] seniorityArray = Developer.SENIORITY.values();
        int index = ThreadLocalRandom.current().nextInt(seniorityArray.length - 1);
        return seniorityArray[index];
    }
}
