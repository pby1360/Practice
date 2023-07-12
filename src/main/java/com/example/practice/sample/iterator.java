package com.example.practice.sample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class iterator {

    /*
    iterator�� ArrayList, HashSet�� ���� �÷����� �ݺ��ϴ� �� ����� �� �ִ� ��ü��.
    iterator�� �ݺ��� ��� ���� ������ �ݺ��ڶ�� �Ѵ�
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
            // iterator.remove(); // ��ȸ�ϴ� ���� ���� ����
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

