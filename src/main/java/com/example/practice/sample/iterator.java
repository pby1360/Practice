package com.example.practice.sample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class iterator {

    /*
    iterator는 ArrayList, HashSet과 같은 컬렉션을 반복하는 데 사용할 수 있는 객체다.
    iterator는 반복의 기술 용어기 때문에 반복자라고 한다
    */

    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>();
        fruits.add("apple");
        fruits.add("lemon");
        fruits.add("banana");
        fruits.add("mango");

        for (Iterator<String> iterator = fruits.iterator(); iterator.hasNext();) {
            String fruit = iterator.next();
            System.out.println(fruit);
            // iterator.remove(); // 순회하는 동안 제거 가능
        }

        // System.out.println(fruits.size())

        System.out.println("----------------------------------");
        ListIterator<String> listIterator = fruits.listIterator();
        while(listIterator.hasNext()) {
            System.out.println(listIterator.next());
        }

        ;
    }

}

