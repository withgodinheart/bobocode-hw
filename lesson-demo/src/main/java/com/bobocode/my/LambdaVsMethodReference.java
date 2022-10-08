package com.bobocode.my;

public class LambdaVsMethodReference {

    public static void main(String[] args) {
        /**
         * adds in bytecode:
         * private static synthetic lambda$main$0()Ljava/lang/String;
         * INVOKEDYNAMIC get()Lcom/google/common/base/Supplier;
         */
//        Supplier<String> stringSupplier = () -> "Hello from supplier";

        /**
         * adds in bytecode:
         * INVOKEDYNAMIC accept(Ljava/io/PrintStream;)Ljava/util/function/Consumer;
         */
//        Consumer<String> methodReference = System.out::println;
    }
}
