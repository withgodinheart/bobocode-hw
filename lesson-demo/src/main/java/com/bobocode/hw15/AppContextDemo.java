package com.bobocode.hw15;

import com.bobocode.hw15.context.ApplicationContextImpl;
import com.bobocode.hw15.service.DayService;
import com.bobocode.hw15.service.EveningService;
import com.bobocode.hw15.service.MorningService;

public class AppContextDemo {

    public static void main(String[] args) {
        var appContext = new ApplicationContextImpl(AppContextDemo.class.getPackageName());
        var morningService = appContext.getBean(MorningService.class);
        var eveningService = appContext.getBean("evening", EveningService.class);
        var allBeans = appContext.getAllBeans(DayService.class);
        morningService.greeting();
        eveningService.greeting();
        allBeans.get("afternoonService").greeting();
        System.out.println(allBeans);
    }
}
