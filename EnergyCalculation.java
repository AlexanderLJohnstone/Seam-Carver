import java.lang.Math;

public class EnergyCalculation{


    /** Takes an input image as an array of Pixel objects, and outputs an array of the energies of each pixel.
     *
     * The energy of a pixel is as specified in the practical, an average of the difference of the
     * RGB values of the pixels above and below the pixel and the difference of the RGB values of
     * the pixel to the left and to the right of the pixel.
     *
     * @author ht44
     * @param imageMatrix an array of Pixel objects that represent an image
     * @return an array of doubles representing the energies of the pixels in the image
     */
    public double[][] energyCalculator(Pixel[][] imageMatrix){
        double[][] energyMatrix = new double[imageMatrix.length][imageMatrix[0].length]; //Since the image will always be rectangular this is fine
        
        double xDiff, yDiff;

        for(int i = 0; i < imageMatrix.length; i++){
            for(int j = 0; j < imageMatrix[0].length; j++){

                if (i == 0){

                    xDiff = xDifference(imageMatrix, imageMatrix.length-1, i + 1, j);

                } else if ( i == imageMatrix.length-1){

                    xDiff = xDifference(imageMatrix, i, 0, j);

                } else {

                    xDiff = xDifference(imageMatrix, i - 1, i + 1, j);

                }

                if (j == 0){

                    yDiff = yDifference(imageMatrix, i, imageMatrix[i].length-1, j+1);

                } else if (j == imageMatrix[i].length-1){

                    yDiff = yDifference(imageMatrix, i, j-1, 0);

                } else {

                    yDiff = yDifference(imageMatrix, i, j - 1, j + 1);

                }

                energyMatrix[i][j] = Math.sqrt(xDiff + yDiff);
            }
        }

        return energyMatrix;
    }

    /** Takes in the RGB differences of two pixels as three values and finds the average of these difference.
     *
     * After being passed the differences of the red values, green values and blue values of two pixels,
     * the method calculates the root mean square average of the three parameters, returning this double.
     * This was as specified in the practical
     *
     * @author ht44
     * @param redDiff double representing the difference between the red values of two pixels
     * @param greenDiff double representing the difference between the green values of two pixels
     * @param blueDiff double representing the difference between the blue values of two pixels
     * @return a double that is the sum of the squares of the parameters
     */
    public double RGBDifference(double redDiff, double greenDiff, double blueDiff){
        return (redDiff*redDiff) + (greenDiff * greenDiff) + (blueDiff*blueDiff);
    }

    /** Takes two values and returns the absolute value of the difference between them.
     *
     * @author ht44
     * @param value1 The first int to be compared
     * @param value2 The second int to be compared
     * @return The difference between the two parameters
     */
    public int difference(int value1, int value2){ return Math.abs(value1-value2);
    }


    /** Used to find the rms average of the difference between two values in the same row within an array of pixels.
     *
     * It does this by obtaining the individual red, green and blue values of the two parameter-identified pixels
     * and finding the root mean square average of the difference of the RGB values of these pixels.
     *
     * This can be used to find the energy of a pixel by finding the xDifference of the pixel to the left
     * and the right of a target pixel, so usually when this is called in the main body of the
     * calculation x1 and x2 represent the pixels to the left and the right of the target pixel.
     *
     * @author ht44
     * @param imageMatrix The array of pixels representing the image
     * @param x1 The x value of the first pixel
     * @param x2 The x value of the second pixel
     * @param y The y value of both of the pixels
     * @return A double representing the root mean square average of the difference between the RGB values of two pixels
     */
    public double xDifference(Pixel[][] imageMatrix, int x1, int x2, int y){

        double redDiff = difference(imageMatrix[x1][y].getRed(), imageMatrix[x2][y].getRed()); //The difference between the R value of the two pixels
        double greenDiff = difference(imageMatrix[x1][y].getGreen(), imageMatrix[x2][y].getGreen()); //The difference between the G value of the two pixels
        double blueDiff = difference(imageMatrix[x1][y].getBlue(), imageMatrix[x2][y].getBlue()); //The difference between the B value of the two pixels

        return RGBDifference(redDiff,greenDiff,blueDiff);
    }


    /** Used to find the rms average of the difference between two values in the same column within an array of pixels.
     *
     * It does this by obtaining the individual red, green and blue values of the two parameter-identified pixels
     * and finding the root mean square average of the difference of the RGB values of these pixels.
     *
     * This can be used to find the energy of a pixel by finding the xDifference of the pixel above and below a
     * target pixel, so usually when this is called in the main body of the calculation x1 and x2 represent
     * the pixels above and below the target pixel.
     *
     * @author ht44
     * @param imageMatrix The array of pixels representing the image
     * @param x The x value of both pixels
     * @param y1 The y value of the first pixel
     * @param y2 The y value of the second pixel
     * @return A double representing the root mean square average of the difference between the RGB values of two pixels
     */
    public double yDifference(Pixel[][] imageMatrix, int x, int y1, int y2){

        double redDiff = difference(imageMatrix[x][y1].getRed(), imageMatrix[x][y2].getRed()); //The diff between the R values of the two pixels
        double greenDiff = difference(imageMatrix[x][y1].getGreen(), imageMatrix[x][y2].getGreen()); //The diff between the G values of the two pixels
        double blueDiff = difference(imageMatrix[x][y1].getBlue(), imageMatrix[x][y2].getBlue()); //The diff between the B values of the two pixels

        return RGBDifference(redDiff,greenDiff,blueDiff);
    }

}