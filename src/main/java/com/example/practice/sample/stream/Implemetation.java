package com.example.practice.sample.stream;

import com.example.practice.sample.Inventory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Implemetation {

    public static void main(String[] args) {
        System.out.println("---- start ----");
        
        /*
        * stream 은 세가지 과정으로 요약할 수 있다.
            1. 데이터 소스
            2. 중간 연산
            3. 최종 연산

        * stream의 특징
            1. 게으르게 계산 된다
            2. 최종연산을 수행해야 결과가 생성된다.


        */

        List<Inventory> inventories = Arrays.asList(
                new Inventory("apple", 5, 100, 1500, true)
                , new Inventory("banana", 12, 50, 3000, true)
                , new Inventory("orange", 6, 120, 1200, true)
                , new Inventory("peach", 6, 60, 5500, true)
                , new Inventory("melon", 20, 15, 10000, true)
                , new Inventory("pineapple", 18, 20, 8000, true)
                , new Inventory("mango", 8, 20, 6000, true)
                , new Inventory("belt", 15, 2, 25000, false)
                , new Inventory("blue shirt", 13, 3, 35000, false)
        );

//        System.out.println("※ stream ※");
        inventories.stream().filter(item -> { // 각 중간 연산을 수행 후 다음 중간 연산을 수행하는 것이 아니라, 아이템 별로 순차적으로 수행
//            System.out.println("fileter : " + item.getName());
            return item.getWeight() > 1;
        }).map(item -> {
//            System.out.println("map : " + item.getName());
            return item.getName();
        }).limit(3) // 처음 세개만 선택 됨, 쇼트서킷
                .collect(Collectors.toList()); // collect 최종 연산 전까지는 중간연산이 수행되지 않음

//        System.out.println("※ parallelStream ※");
        inventories.parallelStream().filter(item -> {
//            System.out.println("fileter : " + item.getName());
            return item.getWeight() > 1;
        }).map(item -> {
//            System.out.println("map : " + item.getName());
            return item.getName();
        }).limit(3)
                .collect(Collectors.toList()); // collect 최종 연산 전까지는 중간연산이 수행되지 않음


        /* 메서드참조 */
        List<String> foodItems = inventories.stream().filter(Inventory::isFood).map(Inventory::getName).collect(Collectors.toList());
//        foodItems.forEach(System.out::println);

        /* flatmap */
        List<String> words = inventories.stream().map(item -> item.getName().split("")).flatMap(Arrays::stream).distinct().collect(Collectors.toList());
//        words.forEach(System.out::println);

        List<Integer> numList1 = Arrays.asList(new Integer[]{1 , 2, 3});
        List<Integer> numList2 = Arrays.asList(new Integer[]{10 , 20, 30});

        numList1.stream().flatMap(n1 -> {
//                    System.out.println("flatMap : " + n1);
                    return numList2.stream().map(n2 -> {
//                        System.out.println("map : " + n2);
                        return new int[]{n1, n2};
                    });
                });
//                .forEach(f ->
//                System.out.println("forEach : " + f[0] + " " + f[1]));

        /* 리듀싱, 최종연산 */
        Optional<Integer> sum1 = inventories.stream().map(item -> item.getName()).map(String::length).reduce((a, b) -> a + b);
        Optional<Integer> sum2 = inventories.stream().map(item -> item.getName()).map(String::length).reduce(Integer::sum);
        Optional<Integer> max = inventories.stream().map(item -> item.getName()).map(String::length).reduce(Integer::max);
        Optional<Integer> min = inventories.stream().map(item -> item.getName()).map(String::length).reduce(Integer::min);
        System.out.println("sum1 ? " + sum1);
        System.out.println("sum2 ? " + sum2);
        System.out.println("max ? " + max);
        System.out.println("min ? " + min);


        System.out.println("---- end ----");
    }
}
