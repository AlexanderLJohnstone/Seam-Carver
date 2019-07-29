import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame{
    /**This class defines al of the components to create a GUI that allows
     * the user to get a file and enter the desired dimensions to carve that
     * file to.
     *
     * @author: aj87
     */


    private File imagePath;

    public GUI(){
        initialiseUI();
    }

    /**This method initialises and lays out all of the components
     * of the GUI. It also defines the functionality of the buttons
     * one of which calls all the other methods to perform a carving
     *
     * @author:aj87
     */
    private void initialiseUI(){

        setTitle("Seam Carver");
        setSize(300,175);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JTextField widthEntry = new JTextField();
        JTextField heightEntry = new JTextField();

        JButton run = new JButton();
        run.setVisible(true);
        run.setText("Carve Image");
        run.setBounds(60, 75, 150, 30);
        run.setBackground(Color.white);
        Font runButtonFont = new Font("Arial", Font.PLAIN, 12);
        run.setFont(runButtonFont);
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {//Validate that all input is correct and won't break the program

                final int MIN_ARRAY_SIZE = 2;
                try{
                    int width = Integer.parseInt(widthEntry.getText());
                    int height = Integer.parseInt(heightEntry.getText());
                    if(width < MIN_ARRAY_SIZE || height < MIN_ARRAY_SIZE){
                        throw new NegativeArraySizeException();
                    }
                    if(imagePath == null){
                        throw new NullPointerException();
                    }
                    BufferedImage inputImage = ImageIO.read(new File(imagePath.getAbsolutePath()));
                    SeamCarve.carveSeam(inputImage, width, height);
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(getContentPane(),"Enter Valid Integers.");
                }catch(NegativeArraySizeException e){
                    JOptionPane.showMessageDialog(getContentPane(),"Enter Integers Larger than 2!");
                }catch(IOException e){
                    JOptionPane.showMessageDialog(getContentPane(),"Choose a Valid File Path.");
                }catch(NullPointerException e){
                    JOptionPane.showMessageDialog(getContentPane(),"Choose a Valid File Path.");
                }catch(ArrayIndexOutOfBoundsException e){
                    JOptionPane.showMessageDialog(getContentPane(),"Enter Dimensions Smaller Than or Equal To The Image.");
                }


            }
        });

        JLabel fileChosen = new JLabel();
        fileChosen.setVisible(true);
        fileChosen.setText("No File Chosen");
        fileChosen.setBounds(135,10,100,22);
        fileChosen.setFont(runButtonFont);

        JButton chooseFile = new JButton();
        chooseFile.setVisible(true);
        chooseFile.setText("Browse ...");
        chooseFile.setBounds(30, 10, 100, 21);
        chooseFile.setBackground(Color.white);
        chooseFile.setFont(runButtonFont);
        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent)
            {
               ImageFinderGUI imageGUI = new ImageFinderGUI();
               imagePath = imageGUI.getFileSelected();
               if(imagePath != null){
                   fileChosen.setText(imagePath.toString());
                   try{
                       BufferedImage inputImage = ImageIO.read(new File(imagePath.getAbsolutePath()));
                       JOptionPane.showMessageDialog(getContentPane(), "Width: "+ inputImage.getWidth() + " Height: " + inputImage.getHeight());
                   }catch(IOException e){

                       JOptionPane.showMessageDialog(getContentPane(), "Not a suitable file");
                   }
               }else {
                   fileChosen.setText("No File Chosen");
               }
            }
        });


        widthEntry.setVisible(true);
        heightEntry.setVisible(true);
        widthEntry.setText("Enter Width");
        heightEntry.setText("Enter Height");
        widthEntry.setBounds(30,50,80,20);
        heightEntry.setBounds(155,50,80,20);



        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(widthEntry);
        mainPanel.add(heightEntry);
        mainPanel.add(run);
        mainPanel.add(chooseFile);
        mainPanel.add(fileChosen);
        add(mainPanel);
        mainPanel.setLocation(0,0);
    }


    /**Main method invokes an event queue that waits for the users input
     *
     * @param args arguments of the program, none are need
     */
    public static void main(String[] args){

        EventQueue.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }

}
