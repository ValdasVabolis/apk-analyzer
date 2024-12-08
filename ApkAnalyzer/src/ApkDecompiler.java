import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class ApkDecompiler {

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }

    public static String Decompile() {
        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");

        System.out.println("Starting decompilation of the APK file.");

        try (Scanner reader = new Scanner(System.in)) {
            System.out.println("Enter file path to APK to be analyzed: ");
            String inputPath = reader.nextLine();
            if (!(inputPath.endsWith(".apk") || inputPath.endsWith(".APK"))) {
                throw new IllegalArgumentException(inputPath + " not apk file");
            }

            System.out.println("Provide output directory: ");
            String outputDir = reader.nextLine();

            Process process;
            if (isWindows) {
                try {
                    process = Runtime.getRuntime()
                            .exec(String.format("cmd.exe /c start apktool d %s -o %s -f", inputPath, outputDir));
                    StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
                    try {
                        process.waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    process = Runtime.getRuntime()
                            .exec(String.format("/bin/sh -c apktool d %s -o %s -f", inputPath, outputDir));
                    StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
                    try {
                        process.waitFor();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("APK successfully decompiled.");
            return outputDir;
        }
    }
}