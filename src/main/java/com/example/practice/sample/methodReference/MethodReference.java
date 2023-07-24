package com.example.practice.sample;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;

public class MethodReference {

    public File[] getHiddenFiles () {
//        File[] hiddenFiles = new File(".").listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File file) {
//                return file.isHidden();
//            }
//        });

        File[] hiddenFiles = new File(".").listFiles(File::isHidden);
        return hiddenFiles;
    }

    public static void main(String[] args) {
        MethodReference sample = new MethodReference();
        File[] files = sample.getHiddenFiles();
        System.out.println("--- Start ---");
        Arrays.stream(files).forEach(file -> System.out.println(file.getName()));
        System.out.println("--- Ends ---");
    }
}
