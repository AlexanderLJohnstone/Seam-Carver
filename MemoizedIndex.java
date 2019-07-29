public class MemoizedIndex {
    /**
     * Class instances store the energy value of the shortest possible path from
     * its position ot the "bottom" of the image
     *
     * Instances store the column and row of the next pixel in the shortest path
     * as well as the total energy from the pixel to a pixel in the last row/column
     *
     * @author: aj87
     */


    private double energyPathValue;
    private int rowRef;
    private int columnRef;

    public MemoizedIndex(double energyPathValue, int rowRef, int columnRef) {
        this.energyPathValue = energyPathValue;
        this.rowRef = rowRef;
        this.columnRef = columnRef;
    }

    public double getEnergyPathValue() {
        return energyPathValue;
    }

    public void setEnergyPathValue(double energyPathValue) {
        this.energyPathValue = energyPathValue;
    }

    public int getRowRef() {
        return rowRef;
    }

    public void setRowRef(int rowRef) {
        this.rowRef = rowRef;
    }

    public int getColumnRef() {
        return columnRef;
    }

    public void setColumnRef(int columnRef) {
        this.columnRef = columnRef;
    }
}
