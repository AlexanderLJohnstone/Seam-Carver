import java.awt.image.BufferedImage;
import java.util.Arrays;

public class SeamRemoval {

    private BufferedImage inputImage;
    private Pixel[][] imagePixels;


    public SeamRemoval(BufferedImage inputImage, Pixel[][] imagePixels){
        this.inputImage = inputImage;
        this.imagePixels = imagePixels;
    }

    public void setInputImage(BufferedImage inputImage, Pixel[][] imagePixels) {
        this.inputImage = inputImage;
        this.imagePixels = imagePixels;
    }

    public void setInputImage(BufferedImage inputImage) {
        this.inputImage = inputImage;
    }

    public void setImagePixels(Pixel[][] imagePixels) {
        this.imagePixels = imagePixels;
    }

    public BufferedImage getInputImage() {
        return inputImage;
    }

    public Pixel[][] getImagePixels() {
        return imagePixels;
    }


    /** Removes a vertical seam of pixels from a buffered images.
     *
     * Checks all the pixels of a buffered image to see if they are part of the lowest weight seam, then
     * creates a new Pixel array and BufferedImage and sets all of the corresponding pixels from the old
     * image to the new image, except those that are part of the seam. It then updates the seamRemoval object's
     * attributes to the new array and bufferedImage and returns the new image
     *
     * @return A BufferedImage of the new image after a seam has been carved
     */
    public BufferedImage seamCarverVertical(){
        BufferedImage newPicture = new BufferedImage(inputImage.getWidth()-1, inputImage.getHeight(), inputImage.getType());
        Pixel[][] newPixels = new Pixel[imagePixels.length][imagePixels[0].length-1];
        for(int i = 0; i < newPixels.length; i++){
            int count = 0;
            for(int j = 0; j < newPixels[0].length; j++){
                if (imagePixels[i][j].getIsLowestEnergy()){
                    count++;
                }
                newPixels[i][j] = imagePixels[i][count];
                newPicture.setRGB(j,i,inputImage.getRGB(count,i));
                count++;
            }
        }
        this.imagePixels = newPixels;
        this.inputImage = newPicture;
        return newPicture;
    }


    /** Removes a horizontal seam of pixels from a buffered images.
     *
     * Checks all the pixels of a buffered image to see if they are part of the lowest weight seam, then
     * creates a new Pixel array and BufferedImage and sets all of the corresponding pixels from the old
     * image to the new image, except those that are part of the seam. It then updates the seamRemoval object's
     * attributes to the new array and bufferedImage and returns the new image
     *
     * @return A BufferedImage of the new image after a seam has been carved
     */
    public BufferedImage seamCarverHorizontal(){

        BufferedImage newPicture = new BufferedImage(inputImage.getWidth(), inputImage.getHeight()-1, inputImage.getType());
        Pixel[][] newPixels = new Pixel[imagePixels.length-1][imagePixels[0].length];
        for(int i = 0; i < newPixels[0].length; i++){
            int count = 0;
            for(int j = 0; j < newPixels.length; j++){
                if (imagePixels[j][i].getIsLowestEnergy()){
                    count++;
                }
                newPixels[j][i] = imagePixels[count][i];
                newPicture.setRGB(i,j,inputImage.getRGB(i,count));
                count++;
            }
        }
        this.imagePixels = newPixels;
        this.inputImage = newPicture;

        return newPicture;
    }

    /** Marks all of the pixels in the image array which are part of the lowest weight seam, so that they can be removed later.
     *
     * The parameter array is in the format where [i][0] contains the index of the row of the ith pixel in the seam,
     * and [i][1] contains the index of the column of the ith pixel in the seam.
     *
     * @param path The array containing the indexes of all of the pixels in the Pixel array which are part of the lowest weight seam
     */
    public void markSeam(int[][] path){
        for (int i = 0; i < path.length; i++){
            imagePixels[path[i][0]][path[i][1]].setIsLowestEnergy(true);
        }
    }

    /** Removes a seam of pixels, either vertical or horizontal, from an image.
     *
     * Since the methods for removing a vertical and a horizontal seam are different, this method
     * takes in a boolean that determines whether the vertical or horizontal seam removal methods are called
     *
     * @param isVertical Boolean that determines whether a vertical or horizontal seam is being removed
     * @return BufferedImage that has had a seam of pixels removed
     */
    public BufferedImage carveSeam(boolean isVertical){

        if (isVertical){
            return seamCarverVertical();
        }
        else{ //Ideally the value of isVertical will remain the same throughout the seam identification and carving process
            return seamCarverHorizontal();
        }
    }

}