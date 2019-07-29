import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.jar.JarFile;

public class ImageFinderGUI extends JFrame{
    /**This class defines all the components
     * and functionality to allow the user to
     * enter an image from a file chooser.
     *
     *@author: aj87
     */

    JFileChooser chooser = new JFileChooser();

    public ImageFinderGUI(){
        initialise();
    }
    
    private void initialise(){
        setTitle("Choose File");
        setSize(750,450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(false);

        JFileChooser chooser = new JFileChooser();
        chooser.setVisible(true);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setBounds(100,100,300,300);

    }

    public File getFileSelected(){
        int returnVal = chooser.showOpenDialog(null);
        File selectedFile;
        if(returnVal == JFileChooser.APPROVE_OPTION){
            selectedFile = chooser.getSelectedFile();
        }
        else{
            selectedFile = null;
        }

        setVisible(false);
       return selectedFile;
    }

    
}
