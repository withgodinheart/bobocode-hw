package com.bobocode.hw15.av;

import com.bobocode.hw15.av.ctx.ApplicationContextImpl;
import com.bobocode.hw15.av.service.ServiceInterface;

public class CtxMain {

    public static void main(String[] args) {
        var ctx = new ApplicationContextImpl("com.bobocode.hw15.av");

        System.out.println(ctx.getBean(ServiceInterface.class));

        ctx.getAllBeans(ServiceInterface.class).forEach((k, v) -> {
            System.out.println(k);
            System.out.println(v.getClass().getSimpleName());
        });
    }
}
