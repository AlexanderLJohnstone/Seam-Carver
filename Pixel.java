/** Object that represents a pixel in an image, with RGB int values and a representation of whether they are part of
 * the lowest weight seam or not.
 *
 * @author ht44
 */
public class Pixel {

    private int red;
    private int green;
    private int blue;
    private boolean isLowestEnergy;

    public Pixel(int red, int green, int blue){
        this.red = red;
        this.green = green;
        this.blue = blue;
        isLowestEnergy = false;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public boolean getIsLowestEnergy(){
        return isLowestEnergy;
    }

    public void setIsLowestEnergy(boolean isLowestEnergy){
        this.isLowestEnergy = isLowestEnergy;
    }


}