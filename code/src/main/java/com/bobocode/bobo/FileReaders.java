package com.bobocode.bobo;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * {@link FileReaders} provides an API that allow to read whole file into a {@link String} by file name.
 */
public class  FileReaders {

    public static void main(String[] args) {
        System.out.println(FileReaders.readWholeFile("lesson-demo/src/main/java/com/bobocode/my/FileReaders.java"));
    }

    /**
     * Returns a {@link String} that contains whole text from the file specified by name.
     *
     * @param fileName a name of a text file
     * @return string that holds whole file content
     */
    @SneakyThrows
    public static String readWholeFile(String fileName) {
        return filesLines(fileName);
//        return filesReadAllLines(fileName);
//        return filesReadAllLines2(fileName);
//        return scanner(fileName);
//        return bufferedStream(fileName);
    }

    private static String filesLines(String fileName) throws IOException {
        return Files.lines(Paths.get(new File(fileName).toURI())).collect(Collectors.joining("\n"));
    }

    private static String filesReadAllLines(String fileName) throws IOException {
        var list = Files.readAllLines(Paths.get(fileName));
//        return list.stream().reduce("", (res, el) -> res += el);
//        return list.stream().collect(Collectors.joining("\n"));
        return String.join("\n", list);
    }

    private static String filesReadAllLines2(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private static String scanner(String fileName) throws IOException {
        var file = new File(fileName);
        try (var scanner = new Scanner(file)) {
            var sb = new StringBuilder();
            while (scanner.hasNext()) {
                sb.append(scanner.next());
            }

            return sb.toString();
        }
    }

    private static String bufferedStream(String fileName) throws IOException {
        try (var bufferedReader = new BufferedReader(new FileReader(fileName))) {
//            return bufferedReader.lines().parallel().reduce("", String::concat);
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        }
    }

    private static void testReduce(String[] args) {
        var sdf = List.of("1", "sdf", "23r", "45", "34");
        var result = "";
        var reduce = sdf.stream().reduce(result, (str, el) -> str += el);
        System.out.println(reduce);
    }
}
