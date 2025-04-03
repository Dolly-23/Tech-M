import java.io.*;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MemoryLeakDemo {
    private static List<Object> memoryLeakList = new ArrayList<>();

    public static void createLeak() {
        for (int i = 0; i < 10000; i++) {
            memoryLeakList.add(new byte[1024 * 1024]);
        }
    }

    public static void checkMemory() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Total Memory: " + runtime.totalMemory());
        System.out.println("Free Memory: " + runtime.freeMemory());
    }

    public static void fixLeak() {
        List<WeakReference<byte[]>> weakList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            weakList.add(new WeakReference<>(new byte[1024 * 1024]));
        }
        System.gc();
    }

    public static void resourceLeak() {
        try (BufferedReader br = new BufferedReader(new FileReader("sample.txt"))) {
            System.out.println(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        checkMemory();
        createLeak();
        checkMemory();
        fixLeak();
        checkMemory();
        resourceLeak();
    }
}
