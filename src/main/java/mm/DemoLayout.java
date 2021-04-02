package mm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// creating a class DemoLayout
public class DemoLayout {

    // Declaration of objects 
    // of JFrame class
    private JFrame mainFrame;

    // Declaration of objects 
    // of JLabel class
    private JLabel headerLabel, statusLabel, msglabel;

    // Declaration of objects 
    // of JPanel class
    private JPanel upPanel;
    private JPanel headerPanel;
    private JPanel buttonsPanel;
    private JPanel tablePanel;
    private JPanel pathPanel;

    private JTable table;

    // create a class DemoLayout
    public DemoLayout()
    {

        // used to prepare GUI
        prepareGUI();
    }

    public static void main(String[] args)
    {

        // Creating Object of "DemoLayout" class
        DemoLayout DemoLayout = new DemoLayout();

        // to show the group layout demo
        DemoLayout.showDemoLayout();
    }

    private void prepareGUI()
    {
        mainFrame = new JFrame("vcpkg-gui");
        mainFrame.setSize(400, 400);

        mainFrame.setLayout(new GridLayout(3, 1));

        upPanel = new JPanel();
        upPanel.setLayout(new GridLayout(1,2));
        mainFrame.add(upPanel);

        tablePanel = new JPanel();
        tablePanel.setSize(200, 200);
        mainFrame.add(tablePanel);

        pathPanel = new JPanel();
        pathPanel.setLayout(new FlowLayout());
        mainFrame.add(pathPanel);




        // to add action WindowListner in JFrame
        mainFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent windowEvent)
            {
                System.exit(0);
            }
        });


        // Function to set the visible of JFrame.
        mainFrame.setVisible(true);
    }

    private void showDemoLayout()
    {

        updateTablePanel();
        // Creating Object of 
        // "Panel" class
//        JPanel panel = new JPanel();
//
//        // Function to set the size of JFrame.
//        panel.setSize(200, 200);
//
//        // Creating Object of
//        // "layout" class
//        GroupLayout layout = new GroupLayout(panel);
//
//        // it used to set Auto
//        // Create Gaps
//        layout.setAutoCreateGaps(true);
//
//        // it used to set Auto
//        // Create Container Gaps
//        layout.setAutoCreateContainerGaps(true);
//
//        // Creating Object of
//        // "btn2" class
//        JButton btn2 = new JButton("Button 2");
//
//
//        // It used to set the
//        // Horizontal group
//        layout.setHorizontalGroup(layout.createSequentialGroup()
//
//                // Adding the JButton "btn1"
//                .addComponent(table));
//
//        // set the vertical layout group
//        layout.setVerticalGroup(layout.createSequentialGroup()
//
//                // Adding the JButton "btn1"
//                .addComponent(table));
//
//        // Function to set the Layout of JFrame.
//        panel.setLayout(layout);
//

        // Function to set the visible of JFrame.
        mainFrame.setVisible(true);
    }

    private void updateTablePanel(){

        String[] columnNames = {"Package name",
                "Version",
                "Description"};

        Object[][] data = {
                {"ffmpeg[avcodec]:x64-linux", "",
                        "Codec support in ffmpeg"},
                {"ffmpeg[avdevice]:x64-linux", "",
                        "Device support in ffmpeg"},
                {"argh:x64-linux", "2018-12-18-2",
                        "Argh! A minimalist argument handler."},
                {"ffmpeg:x64-linux", "4.3.2#1",
                        "a library to decode, encode, transcode, mux, dem..."}
        };

        table = new JTable(data, columnNames);
//        table.
        tablePanel.add(new JScrollPane(table));
    }
}