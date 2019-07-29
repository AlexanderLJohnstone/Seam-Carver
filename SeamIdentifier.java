public class SeamIdentifier {
    /**
     * This class takes an energy matrix, and uses methods to
     * find the lowest energy vertical/horizontal seam.
     *
     * The pathMatrix is a matrix of the same size as the
     * energy matrix passed in. The matrix indices hold the
     * shortest path from the corresponding pixel to the
     * edge/bottom of the matrix.
     *
     * @author: aj87
     */
    private double[][] energyMatrix;
    private MemoizedIndex[][] pathMatrix;
    private int rowLength;
    private int columnLength;
    private boolean seamOrientation;
    private boolean cropAvoider;

    public double[][] getEnergyMatrix() {
        return energyMatrix;
    }

    public void setEnergyMatrix(double[][] energyMatrix) {
        this.energyMatrix = energyMatrix;
    }

    public SeamIdentifier(double[][] energyMatrix, boolean cropAvoider) {
        this.energyMatrix = energyMatrix;
        this.pathMatrix = new MemoizedIndex[energyMatrix.length][energyMatrix[0].length];
        this.rowLength = energyMatrix.length - 1;
        this.columnLength = energyMatrix[0].length - 1;
        this.cropAvoider = cropAvoider;
    }

    /**
     * This method sets the path of all the last row indices to the
     * last row energy values in the energy matrix.
     *
     * @author: aj87
     */
    private void initialiseVertical(){
        for(int i = 0; i <= columnLength; i++){
            pathMatrix[rowLength][i] = new MemoizedIndex(energyMatrix[rowLength][i], rowLength,i);
        }
    }

    /**
     * This method sets the path of all the last column indices to the
     * last column energy values in the energy matrix.
     *
     * @author: aj87
     */
    private void initialiseHorizontal(){
        for(int i = 0; i <= rowLength; i++){
            pathMatrix[i][columnLength] = new MemoizedIndex(energyMatrix[i][columnLength], i,columnLength);
        }
    }

    /**
     * This method takes a position in the matrix and finds the shortest path
     * to the row beneath it (using the path matrix which holds the shortest path
     * from each pixel to the last row).
     *
     * This method looks at the two/three columns that it can access in the row beneath
     * it and finds the path matrix position with the shortest path to the last row. The
     * method then updates the pathMatrix position at hand adding the current energy of that
     * position plus the rest of the path.
     *
     * @param row the row of the pixel
     * @param column the column of the pixel
     * @author: aj87
     */
    private void bestImmediatePathVertical(int row, int column){
        MemoizedIndex minEnergy = new MemoizedIndex(pathMatrix[row + 1][column].getEnergyPathValue(), row + 1, column);

        for(int i = -1; i < 2; i+= 2){
            if( (column + i) >= 0 && (column + i) <= columnLength){
                if(pathMatrix[row + 1][column + i].getEnergyPathValue()< minEnergy.getEnergyPathValue()){
                    minEnergy.setEnergyPathValue(pathMatrix[row + 1][column + i].getEnergyPathValue());
                    minEnergy.setColumnRef(column + i);
                }
            }
        }
        minEnergy.setEnergyPathValue( energyMatrix[row][column] + pathMatrix[minEnergy.getRowRef()][minEnergy.getColumnRef()].getEnergyPathValue());
        pathMatrix[row][column] = minEnergy;
    }


    /**
     * This method takes a position in the matrix and finds the shortest path
     * to the column ahead of it (using the path matrix which holds the shortest path
     * from each pixel to the last column).
     *
     * This method looks at the two/three rows that it can access in the column ahead of
     * it and finds the path matrix position with the shortest path to the last column. The
     * method then updates the pathMatrix position at hand adding the current energy of that
     * position plus the rest of the path.
     *
     * @param row the row of the pixel
     * @param column the column of the pixel
     * @author: aj87
     */
    private void bestImmediatePathHorizontal(int row, int column){
        MemoizedIndex minEnergy = new MemoizedIndex(pathMatrix[row][column + 1].getEnergyPathValue(), row , column + 1);

        for(int i = -1; i < 2; i+= 2){
            if( (row + i) >= 0 && (row + i) <= rowLength){
                if(pathMatrix[row + i][column + 1].getEnergyPathValue()< minEnergy.getEnergyPathValue()){
                    minEnergy.setEnergyPathValue(pathMatrix[row + i][column + 1].getEnergyPathValue());
                    minEnergy.setRowRef(row + i);
                }
            }
        }
        minEnergy.setEnergyPathValue( energyMatrix[row][column] + pathMatrix[minEnergy.getRowRef()][minEnergy.getColumnRef()].getEnergyPathValue());
        pathMatrix[row][column] = minEnergy;
    }

    /**This method finds the shortest path and returns a 2d
     * integer array of all the values of the shortest
     * vertical path.
     *
     * This method firstly initialises the array, gets the shortest path
     * from each pixel to the last row using dynamic programming and then
     * searches the top row for the shortest path from top to bottom.
     *
     * @return seam: a 2d integer array of the coordinate values of
     * the shortest path
     * @author: aj87
     */
    private int[][] identifyVerticalSeam(){
        initialiseVertical();

        for(int i = rowLength - 1 ; i > -1; i--){
            for(int j = 0; j <= columnLength; j++){
                bestImmediatePathVertical(i,j);
            }
        }
        MemoizedIndex minPath = new MemoizedIndex(pathMatrix[0][0].getEnergyPathValue(), 0,0);
        for(int i = 1; i <= columnLength; i ++){
            if (cropAvoider){
                if(pathMatrix[0][i].getEnergyPathValue() < minPath.getEnergyPathValue()){
                    minPath.setEnergyPathValue(pathMatrix[0][i].getEnergyPathValue());
                    minPath.setColumnRef(i);
                }
            } else {
                if (pathMatrix[0][i].getEnergyPathValue() <= minPath.getEnergyPathValue()) {
                    minPath.setEnergyPathValue(pathMatrix[0][i].getEnergyPathValue());
                    minPath.setColumnRef(i);
                }
            }

        }
            int seam[][] = new int[rowLength + 1][2];
            int column = minPath.getColumnRef();

            seam[0][1] = column;
            for(int i = 1; i <= rowLength; i++){
                seam[i][1] = pathMatrix[i-1][column].getColumnRef();
                column = pathMatrix[i-1][column].getColumnRef();
                seam[i][0] = i;
            }

        return seam;
    }

    /**This method finds the shortest path and returns asd
     * integer array of all the coordinates of the shortest
     * horizontal path.
     *
     * This method firstly initialises the array, gets the shortest path
     * from each pixel to the last column using dynamic programming and then
     * searches the first column for the shortest path from left to right.
     *
     *
     * @return seam: a 2D integer array of the coordinates of
     * the shortest path
     * @author: aj87
     */
        private int[][] identifyHorizontalSeam(){

        initialiseHorizontal();

        for(int j = columnLength - 1; j >-1; j--){
            for(int i =  0 ; i <= rowLength ; i++){
                bestImmediatePathHorizontal(i,j) ;
            }
        }
        MemoizedIndex minPath = new MemoizedIndex(pathMatrix[0][0].getEnergyPathValue(), 0,0);
        for(int i = 1; i <= rowLength; i ++){
                if(cropAvoider) {
                    if (pathMatrix[i][0].getEnergyPathValue() < minPath.getEnergyPathValue()) {
                        minPath.setEnergyPathValue(pathMatrix[i][0].getEnergyPathValue());
                        minPath.setRowRef(i);
                    }
                }else{
                        if(pathMatrix[i][0].getEnergyPathValue() <= minPath.getEnergyPathValue()){
                             minPath.setEnergyPathValue(pathMatrix[i][0].getEnergyPathValue());
                            minPath.setRowRef(i);
                         }
                }
        }
            int seam[][] = new int[columnLength + 1][2];
            int row = minPath.getRowRef();

            seam[0][0] = row;
            for(int i = 1; i <= columnLength; i++){
                seam[i][0] = pathMatrix[row][i-1].getRowRef();
                row = pathMatrix[row][i-1].getRowRef();
                seam[i][1] = i;
            }
            return seam;
    }

    /**
     * This method takes dimensions and returns the seam
     * based on what type of seam is required or what seam is lowest
     *
     * @param desiredHeight is the height of the final image
     * @param desiredWidth is the width of the final image
     * @return seam: a 2d integer array of values corresponding to a seam
     * @author: aj87
     */
    public int[][] getSeam( int desiredWidth, int desiredHeight){

            if(desiredHeight < rowLength && desiredWidth < columnLength){
                int[][] vertical = identifyVerticalSeam();
                int[][] horizontal = identifyHorizontalSeam();;
                if(pathMatrix[vertical[0][0]][vertical[0][1]].getEnergyPathValue() < pathMatrix[horizontal[0][0]][horizontal[0][1]].getEnergyPathValue()){
                    seamOrientation = true;
                    return vertical;
                }
                else{
                    seamOrientation = false;
                    return horizontal;
                }
            }else{
                if(desiredHeight < rowLength){
                    seamOrientation = false;
                    return identifyHorizontalSeam();
                }else{
                    seamOrientation = true;
                   return identifyVerticalSeam();
                }
            }

    }
    public boolean getSeamOrientation(){return seamOrientation;}
}
