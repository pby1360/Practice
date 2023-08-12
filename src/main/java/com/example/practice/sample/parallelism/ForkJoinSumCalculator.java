package com.example.practice.sample.parallelism;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    /*
        Java7에 추가된 포크/조인 프레임워크는 병렬화할 수 있는 작업을 재귀적으로 작은 작업으로 분할한 다음에
        서브태스크 각각의 결과를 합쳐서 전체 결과를 만들도록 설계되었다.

        RecursiveTask를 상속받아 포크/조인 프레임워크에서 사용할 태스크를 생성
    */

    private final long[] numbers;   // 더할 숫자 배열
    private final int start;    // 초기 위치
    private final int end;  // 최종 위치
    public static final long THRESHOLD = 10_000;    // 이 값 이하의 서브태스크는 더 이상 분할 불가

    public ForkJoinSumCalculator (long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {

        int length = end - start;

        if (length <= THRESHOLD) {
            return computeSequentially();
        } else {
            log.info("분할");
        }

        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2); // 배열의 절반을 더하도록 서브태스크 생성
        leftTask.fork();    // 비동기 실행

        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end); // 배열의 나머지 절반을 더하도록 서브태스크 생성

        Long rightResult = rightTask.compute(); // 동기 실행
        Long leftResult = leftTask.join();  // 첫 번째 태스크의 결과를 읽거나 아직 결과가 없으면 기다린다.

        return leftResult + rightResult; // 두 서브태스크의 결과를 조합한 결과 값을 리턴
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i< end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    private static Logger log = LoggerFactory.getLogger("");

    public static void main(String[] args) {
        long startTime = 0L;

        startTime = System.currentTimeMillis();
        long result = forkJoinSum(10_000_000L);
        log.info("::1) forJoin 경과시간 ? {}", (System.currentTimeMillis() - startTime) / 1000.0 + " seconds");
        log.info("::1) forJoin 결과값 ? {}", result);
    }
}
