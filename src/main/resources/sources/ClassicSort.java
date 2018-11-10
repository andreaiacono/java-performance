import me.andreaiacono.code.SampleRun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ClassicSort extends SampleRun {

    private static final int listSize = 100_000;
    private static final int cycles = 500;
    private static final List<Integer> testData = new ArrayList<>();

    public void run() throws Exception {

        updateStatusBar("Creating random values");
        Random randomGenerator = new Random();
        for (int i = 0; i < listSize; i++) {
            testData.add(randomGenerator.nextInt(Integer.MAX_VALUE));
        }
        updateStatusBar("Sorting..");
        for (int i = 0; i < cycles; i++) {
            List<Integer> copy = new ArrayList<>(testData);
            double startTime = System.nanoTime();
            Collections.sort(copy);
            double cycleTime = System.nanoTime() - startTime;
            addDataPoints(cycleTime / 1_000_000d);
        }
        updateStatusBar("Terminated");
    }

    public ClassicSort() {
        addSeries("Sorting Time (ms)");
    }
}
