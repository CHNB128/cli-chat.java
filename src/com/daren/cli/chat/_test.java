package com.daren.cli.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class _test {

    public static void main(String args[]) {
        HashMap<Cat, Num> map = new HashMap<>();
        map.put(new Cat("Garfield","Black"), new Num(1));
        map.put(new Cat("Glory","Black"), new Num(2));
        map.forEach((k, v) -> {
            System.out.println("Name : " + k.getName() + "\n Color : " + k.getColor() + " \nNum : " + v.getId());
        });
        /*
        for(Map.Entry<Cat, Num> entry : map.entrySet()) {
            System.out.println("s" + entry.getKey() + ":" + entry.getValue());
        }
        */
    }

    static class Cat {

        private String name;
        private String color;

        Cat(String name, String color){
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public String getColor() {
            return color;
        }
    }

    static class Num {

        private int id;

        Num(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
