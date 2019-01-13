import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Scratch {
    public static void main(String[] args) {
        System.out.println(IntStream.of(1, 2, 3));
        IntStream.range(1,3);
        IntStream.rangeClosed(1,3);
        IntStream.iterate(0,i -> i + 2).limit(3);
        IntStream.generate(() -> ThreadLocalRandom.current().nextInt(10)).limit(3);

        //Int Stream functions
       IntStream st =  IntStream.range(1,5).map(i -> i * i );
       st.forEach(s -> {
           System.out.println(s);
       });
       int value = IntStream.range(1,5).reduce(1,(x,y) -> x * y);
        System.out.println(value);

        IntStream.range(1, 5).anyMatch(i -> i % 2 == 0);

        int maxValue = IntStream.rangeClosed(1, 5).max().getAsInt();
        System.out.println(maxValue);
        IntStream.range(1, 5).reduce(1, (x, y) -> x * y);
        List<String> source = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            source.add(new String(i+""));
        }
        Stream<List<String>> sublists =slice(source,10);
        sublists.forEach(sl -> {
            System.out.println(sl.size());
        });
    }

    public static <T> Stream<List<T>> slice(List<T> source, int length) {

        if (length <= 0)
            throw new IllegalArgumentException("length = " + length);
        int size = source.size();
        if (size <= 0)
            return Stream.empty();
        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(
                n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }

}