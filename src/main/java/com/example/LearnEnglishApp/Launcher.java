package com.example.LearnEnglishApp;

import javafx.scene.text.Font;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Launcher {
    public static void main(String[] args) {
        //Берём данные из файла "Configs.txt" и настраиваем параметры соединения в классе "Configs", если файл не пустой
        //Если файл пустой или его нет, то создаем файл и заполняем его значениями по умолчанию из класса "Configs"
        Path fileConfigs = Paths.get(Const.CONFIGS);
        HashMap<String, String> configsFromFile = new HashMap<>();
        if(Files.exists(fileConfigs)){
            try (FileReader fileReader = new FileReader(fileConfigs.toFile())){
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String str= bufferedReader.lines().collect(Collectors.joining());
                Arrays.stream(str.split(";")).forEach(s->{
                    String[] strs = s.split("=");
                    configsFromFile.put(strs[0],strs[1]);
                });
                configsFromFile.forEach(new BiConsumer<String, String>() {
                    @Override
                    public void accept(String s, String s2) {
                        Configs.setConfig(s, s2);
                    }
                });
            } catch (IOException e) {
                throw  new RuntimeException(e);
            }
        }
        else{
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(fileConfigs.toString())))){
                HashMap<String,String> configsDefault = Configs.getConfigs();
                configsDefault.forEach(new BiConsumer<String, String>() {
                    @Override
                    public void accept(String s, String s2) {
                        try {
                            bufferedWriter.write(s + " = " + s2 + ";");
                            bufferedWriter.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        HelloApplication.main(args);
    }
}
