package com.example.practice.sample.parallelism;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Parallelism {

    private static Logger log = LoggerFactory.getLogger("");

    public static void main(String[] args) {

        log.info("{}",Runtime.getRuntime().availableProcessors());
        long startTime = 0L;
        long number = 10_000_000L;
        Parallelism parallelism = new Parallelism();

        /*
            병렬처리가 더 시간이 오래 소요된다.
            iterate는 본질적으로 순차적이다.
            iterate 연산을 청크로 분할하기가 어렵다.
            리듀싱 과정을 시작하는 시점에 전체 리스트가 준비되지 않았으므로 스트림을 병렬로 처리할 수 있도록 청크로 분할할 수 없다.
            오히려 박싱,언박싱 과정으로인해 오버헤드가 증가.

            limit 과 findFirst 같은 요소의 순서에 의존하는 연산은 비싼 비용이 든다.

            처리비용이 높을수록 병렬 스트림으로 성능을 개선할 수 있는 가능성이 높다.
            소량의 데이터는 오히려 병렬화 과정에서의 비용이 더 많이 든다.
        */

        startTime = System.currentTimeMillis();
        parallelism.iterativleSum(number);
        log.info("::1) for 경과시간 ? {}", (System.currentTimeMillis() - startTime) / 1000.0 + " seconds");

        startTime = System.currentTimeMillis();
        parallelism.sequentialSum(number);
        log.info("::2) 순차처리 경과시간 ? {}", (System.currentTimeMillis() - startTime) / 1000.0 + " seconds");

        startTime = System.currentTimeMillis();
        parallelism.paralleSum(number);
        log.info("::3) 병렬처리 경과시간 ? {}", (System.currentTimeMillis() - startTime) / 1000.0 + " seconds");

        /* 
            특화메서드 사용으로 성능 향상
            특화메서드는 박싱을 피할 수 있다.
        */
        startTime = System.currentTimeMillis();
        parallelism.rangedSum(number);
        log.info("::4) 특화메서드 경과시간 ? {}", (System.currentTimeMillis() - startTime) / 1000.0 + " seconds");

        startTime = System.currentTimeMillis();
        parallelism.parallelRangedSum(number);
        log.info("::5) 특화메서드 병렬처리 경과시간 ? {}", (System.currentTimeMillis() - startTime) / 1000.0 + " seconds");

        /* 부작용 */
        long result = parallelism.sideEffectSum(number);
        log.info("순차처리 결과 값 ? {}", result);
        result = parallelism.sideEffectParallelSum(number);
        log.info("병렬처리 결과 값 ? {}", result);


    }

    /* 전통적인 계산 방법 */
    public long iterativleSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result+=1;
        }

        return result;
    }

    /* 순차 stream을 사용한 계산 방법 */
    public long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    /* 병렬 stream을 사용한 계산 방법 */
    public long paralleSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
    }

    public long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }

    public long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.totatl;
    }

    public long sideEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.totatl;
    }
}

class Accumulator {
    public long totatl = 0;
    public void add (long value) { totatl+=value; };
}
