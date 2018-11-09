import me.andreaiacono.code.SampleRun;

public class WallTime extends SampleRun {

    private final int ARR_SIZE = 2 * 1024 * 1024;
    private final int[] testData = new int[ARR_SIZE];

    public void run() throws Exception {

        int avg = 0;
        for (int j = 0; j < 40; j++) {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 250; i++) {
                Thread.sleep(1);
            }
            long elapsed = System.currentTimeMillis() - start;
            avg += elapsed;
            addDataPoints(elapsed, avg/(float)(j+1));
        }
        updateStatusBar("Test completed");
    }

    public WallTime() {
        addSeries("Wall Time (ms)", "Average (ms)");
    }
}