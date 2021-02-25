package view;

import java.util.List;

public class PerfomanceFrame extends PerformanceFrameInitializer {

    public PerfomanceFrame(List<Integer> performance) {
        this.updateGraph(performance);
    }
    @Override
    protected void updateGraph(List<Integer> performance) {
        int x = 0;
        for(Integer integer : performance) {
            dataset.addValue(integer, "Tendies", "" + x++);
        }
    }
}