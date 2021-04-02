package mm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

// creating a class DemoLayout
public class DemoLayout {

    private JFrame mainFrame;

    private JLabel headerLabel;
    private JLabel pathLabel;

    private JButton refreshButton;
    private JButton addButton;
    private JButton removeButton;

    private JPanel basicPanel;
    private JPanel upPanel;
    private JPanel headerPanel;
    private JPanel buttonsPanel;
    private JPanel tablePanel;
    private JPanel pathPanel;
    private JScrollPane scrollPanel;

    private JTable table;

    public DemoLayout()
    {
        prepareGUI();
    }

    public static void main(String[] args)
    {
        DemoLayout DemoLayout = new DemoLayout();
        DemoLayout.showDemoLayout();
    }

    private void prepareGUI()
    {
        mainFrame = new JFrame("vcpkg-gui");
        mainFrame.setSize(600, 600);
        mainFrame.setMinimumSize(new Dimension(500,500));
        mainFrame.getContentPane().setBackground(Color.pink);

        mainFrame.getContentPane().setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.PAGE_AXIS));


        GroupLayout layout = new GroupLayout(mainFrame.getContentPane());
        mainFrame.getContentPane().setLayout(layout);

        upPanel = new JPanel();
        headerPanel = new JPanel();
        buttonsPanel = new JPanel();
        tablePanel = new JPanel();
        pathPanel = new JPanel();

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(upPanel)
                    .addComponent(tablePanel)
                    .addComponent(pathPanel))
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
        addPathPanel();
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

        scrollPanel = new JScrollPane(table);

        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPanel, BorderLayout.CENTER);
        tablePanel.add(scrollPanel);
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

    private void addPathPanel() {
        pathLabel = new JLabel("vcpkg path:");
        pathPanel.add(pathLabel);
        pathPanel.setBackground(Color.BLUE);
    }

}