package be.ugent.tiwi.datastructures.lab1;

import java.util.Random;

public class Main {

    private static final Random rnd = new Random();

    public static void main(String[] args) {

        SortedList d = new SkipList(10);
        //SortedList d = new SortedLinkedList();

        for (int i : new int[]{1, 21, 8, 25, 54, 12, 65, 3, 18, 9, 27, 45, 14, 32}) {
            d.insert(i);
            d.print();
            System.out.println("");

        }

        System.out.println(d.contains(27));
        System.out.println("");
        System.out.println(d.contains(89));
        System.out.println("");

        d.remove(12);
        d.print();
        System.out.println("");
        System.out.println(d.contains(12));


        //benchmark(new SortedLinkedList());
        benchmark(new SkipList(10));

    }

    public static void benchmark(SortedList l){

        l.clear();
        for (int i = 0; i < 100; i++){
            for (int j = 0; j < 50000; j++){
                l.insert(rnd.nextInt());
            }

            long start = System.nanoTime();
            for (int k = 0; k < 50000; k++){
                l.contains(rnd.nextInt());
            }
            long stop = System.nanoTime();

            System.out.println(50000+i*50000+"\t"+(stop-start));

        }
    }
}
