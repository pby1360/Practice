package com.example.practice.sample.functionalInterface;

import com.example.practice.sample.Inventory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Implementation {

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("build.gradle"))) {
            return p.process(br);
        }
    }
    public static void main(String[] args) throws IOException {

        /* BufferedReaderProcessor */
//        String lines = processFile((BufferedReader br) -> {
//            String readLine = null;
//            while (br.readLine() != null) {
//                readLine += br.readLine();
//            }
//            return readLine;
//        });
//
//        System.out.println("--- Start ---");
//        System.out.println(lines);
//        System.out.println("--- End ---");

        List<Inventory> inventories = Arrays.asList(
                new Inventory("apple", 5, 100, 1500)
                , new Inventory("banana", 12, 50, 3000)
                , new Inventory("orange", 6, 120, 1200)
                , new Inventory("peach", 6, 60, 5500)
                , new Inventory("melon", 20, 15, 10000)
        );

        /* ※ Comparator ※ */
        inventories.sort(Comparator.comparing(Inventory::getWeight).thenComparing(Inventory::getPrice));
//        inventories.forEach(inventory -> System.out.println(inventory.getName()));

        /* - , thenComparing, reverse */
//        System.out.println(":: reverse");
        inventories.sort(Comparator.comparing(Inventory::getWeight).reversed().thenComparing(Inventory::getPrice));
//        inventories.forEach(inventory -> System.out.println(inventory.getName()));

        /*
         ※ Predicate ※
         boolean 을 반환하는 추상메서드 test 정의
         별도 정의 없이 바로 사용할 수 있다.
        */

        Predicate<Inventory> inventoryExpensivePredicate = (Inventory inventory) -> inventory.getPrice() > 5000;

        List<Inventory> expensiveItems = filter(inventories, inventoryExpensivePredicate);
//        System.out.println("--- expensiveItems ---");
//        expensiveItems.forEach(item -> System.out.println(item.toString()));

        /* - BehaviorParameterization 동작파라미터화 */
        InventoryPredicate inventoryHeavyPredicate = new InventoryHeavyPredicate();
        List<Inventory> heavyItems = inventories.stream().filter(item -> inventoryHeavyPredicate.test(item)).collect(Collectors.toList());
//        System.out.println("--- heavyItems ---");
//        heavyItems.forEach(item -> System.out.println(item.toString()));

        /* - Predicate and, or, negate */
        Predicate<Inventory> inventoryHeavyPredicate2 = (Inventory inventory) -> inventory.getWeight() > 10;
        List<Inventory> expensiveAndHeavyItems = filter(inventories, inventoryExpensivePredicate.and(inventoryHeavyPredicate2));
//        System.out.println("--- expensiveAndHeavyItems ---");
//        expensiveAndHeavyItems.forEach(item -> System.out.println(item.toString()));

        List<Inventory> expensiveOrHeavyItems = filter(inventories, inventoryExpensivePredicate.or(inventoryHeavyPredicate2));
//        System.out.println("--- expensiveOrHeavyItems ---");
//        expensiveOrHeavyItems.forEach(item -> System.out.println(item.toString()));

        List<Inventory> notExpensiveItems = filter(inventories, inventoryExpensivePredicate.negate());
//        System.out.println("--- notExpensiveItems ---");
//        notExpensiveItems.forEach(item -> System.out.println(item.toString()));

        /* - custom predicate */
        Predicate<Inventory> customExpensiveAndHeavy = inventoryExpensivePredicate.and(inventoryHeavyPredicate2);

        /*
         ※ Consumer ※
         제네릭 T 객체를 받아 void를 반환하는 추상메서드 accept 를 정의
        */
        soldout(inventories, inventory -> inventory.soldout());
        inventories.forEach(System.out::println);

        /*
         ※ Function ※
         제네릭 T 를 인수로 받아서 제네릭 R 을 반환하는 추상 메서드 apply를 정의
        */
        List<String> names = map(inventories, inventory -> inventory.getName());
        names.forEach(System.out::println);
    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        List<R> result = new ArrayList<>();
        for(T t: list) {
            result.add(f.apply(t));
        }
        return  result;
    }

    public static <T> void soldout(List<T> list, Consumer<T> c) {
        for (T t: list) {
            c.accept(t);
        }
    }

    public static  <T> List<T> filter(List<T> list, Predicate p) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }

    /* BehaviorParameterization 동작파라미터화 */
    static class InventoryHeavyPredicate implements InventoryPredicate  {

        @Override
        public boolean test(Inventory inventory) {
            return inventory.getWeight() > 10;
        }
    }
}
