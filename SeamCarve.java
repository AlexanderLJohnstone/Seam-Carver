import java.io.*;
import java.awt.image.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

public class SeamCarve extends JPanel{
    /**This class is used to carve an image to the desired dimensions
     * and then output the results.
     */
    Dimension size = new Dimension();
    BufferedImage image;

    public SeamCarve(BufferedImage image){
        this.image = image;
        size.setSize(image.getWidth(), image.getHeight());
    }

    protected void paintComponent(Graphics g){
        int x = (getWidth()- size.width)/2;
        int y = (getHeight() - size.height)/2;
        g.drawImage(image, x, y, this);
    }

    /** This method carves the image
     *
     * The method firstly initialises the image calculations getting the
     *
     *
     * @param chosenImage
     * @param desiredWidth
     * @param desiredHeight
     * @throws IOException
     *
     * @author: aj87
     */
    public static void carveSeam(BufferedImage chosenImage, int desiredWidth, int desiredHeight) throws IOException{

        BufferedImage inputImage = chosenImage;

        if(desiredWidth > (inputImage.getWidth()) || desiredHeight > (inputImage.getHeight())){
            throw new ArrayIndexOutOfBoundsException();
        }

        Pixel[][] imagePixels = new Pixel[inputImage.getHeight()][inputImage.getWidth()];
        for (int i = 0; i < inputImage.getWidth(); i++){
            for (int j = 0; j < inputImage.getHeight(); j++){
                int rgb = inputImage.getRGB(i,j);
                int red = (rgb >>16) & 0xFF;
                int green = (rgb >>8) & 0xFF;
                int blue = rgb & 0xFF;
                imagePixels[j][i] = new Pixel(red ,green, blue);
            }
        }


        EnergyCalculation energyCalculation = new EnergyCalculation();
        SeamRemoval seamRemoval = new SeamRemoval(inputImage, imagePixels);


        double[][] energyMatrix = energyCalculation.energyCalculator(imagePixels);

        BufferedImage outputImage = null;
        boolean avoidCrop = false;

        do{
            SeamIdentifier identifier = new SeamIdentifier(energyMatrix, avoidCrop);
            seamRemoval.markSeam(identifier.getSeam( desiredWidth, desiredHeight));
            outputImage = seamRemoval.carveSeam(identifier.getSeamOrientation());
            energyMatrix = energyCalculation.energyCalculator(seamRemoval.getImagePixels());
            avoidCrop = !avoidCrop;

        }while(desiredWidth < outputImage.getWidth() && desiredHeight < outputImage.getHeight());

        SeamCarve seamCarve = new SeamCarve(outputImage);
        Graphics2D outputG = outputImage.createGraphics();

        JFrame g = new JFrame();
        g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        g.add(new JScrollPane(seamCarve));
        g.setSize((int) seamCarve.size.getWidth(),(int) seamCarve.size.getHeight());
        g.setLocationRelativeTo(null);
        g.setVisible(true);

        outputG.drawImage(outputImage,0,0,null);


    }


}