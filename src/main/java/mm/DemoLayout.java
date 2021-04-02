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

    private JButton refreshButton;
    private JButton addButton;
    private JButton removeButton;

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
        mainFrame.setSize(600, 600);
        mainFrame.setMinimumSize(new Dimension(500,500));

        GroupLayout layout = new GroupLayout(mainFrame.getContentPane());
        mainFrame.getContentPane().setLayout(layout);

        upPanel = new JPanel();
        mainFrame.add(upPanel, BorderLayout.NORTH);

        headerPanel = new JPanel();
        buttonsPanel = new JPanel();

        tablePanel = new JPanel();
//        tablePanel.setSize(500, 500);
//        mainFrame.add(tablePanel, BorderLayout.CENTER);

        pathPanel = new JPanel();
        pathPanel.setLayout(new FlowLayout());
//        mainFrame.add(pathPanel, BorderLayout.SOUTH);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(upPanel)
                    .addComponent(tablePanel)
                    .addComponent(pathPanel))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(upPanel)
                    .addComponent(tablePanel))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                .addComponent(upPanel)
                .addComponent(tablePanel)
                .addComponent(pathPanel)
        );








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
        addUpPanel();
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
//        table.setSize(500, 500);
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        table.
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.createVerticalScrollBar();
        scrollPane.setWheelScrollingEnabled(true);
        tablePanel.add(scrollPane);
        tablePanel.setBackground(Color.GREEN);
    }

    private void addUpPanel(){
//        upPanel.setSize(100,100);
        GroupLayout layout = new GroupLayout(upPanel);
        upPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        headerPanel.setLayout(new FlowLayout());
        headerPanel.setBackground(Color.red);

        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBackground(Color.yellow);

        headerLabel = new JLabel("INSTALLED PACKAGES");
        refreshButton = new JButton("↻");
        headerPanel.add(headerLabel);
        headerPanel.add(refreshButton);
//        upPanel.add(headerPanel);

        addButton = new JButton("+");
        removeButton = new JButton("-");
        buttonsPanel.add(removeButton);
        buttonsPanel.add(addButton);
        upPanel.add(buttonsPanel);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()

                                .addComponent(headerPanel)
                                .addComponent(buttonsPanel)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(headerPanel)
                        .addComponent(buttonsPanel))
        );

    }
}