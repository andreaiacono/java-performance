import me.andreaiacono.code.SampleRun;

public class CachingSample extends SampleRun {

    private final int ARR_SIZE = 2 * 1024 * 1024;
    private final int[] testData = new int[ARR_SIZE];

    public void run() throws Exception {

        updateStatusBar("Warming up..");
        for (int i = 0; i < 1500; i++) {
            touchEveryLine();
            touchEveryItem();
        }

        updateStatusBar("Testing..");
        for (int i = 0; i < 1500; i++) {
            long t0 = System.nanoTime();
            touchEveryLine();
            long t1 = System.nanoTime();
            touchEveryItem();
            long t2 = System.nanoTime();
            long elItem = (t2 - t1) / 1_000; // microseconds
            long elLine = (t1 - t0) / 1_000; // microseconds
            double diff = elItem - elLine;

            addDataPoints(elItem, elLine, diff);
        }

        updateStatusBar("Test completed");
    }

    private void touchEveryItem() {
        for (int i = 0; i < testData.length; i++)
            testData[i]++;
    }

    private void touchEveryLine() {
        for (int i = 0; i < testData.length; i += 16)
            testData[i]++;
    }

    public CachingSample() {
        addSeries("Item (μs)", "Line (μs)", "Difference (μs)");
    }
}