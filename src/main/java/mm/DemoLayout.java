package mm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DemoLayout {

    private JFrame mainFrame;

    private JLabel headerLabel;
    private JLabel pathLabel;

    private JButton refreshButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton pathButton;

    private JPanel upPanel;
    private JPanel headerPanel;
    private JPanel buttonsPanel;
    private JPanel tablePanel;
    private JPanel pathPanel;
    private JScrollPane scrollPanel;

    private JTable table;

    private JTextField pathField;

    public DemoLayout() {
        prepareGUI();
    }

    public static void main(String[] args) {
        DemoLayout DemoLayout = new DemoLayout();
        DemoLayout.showDemoLayout();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("vcpkg-gui");
        mainFrame.setSize(600, 600);
        mainFrame.setMinimumSize(new Dimension(500, 500));
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

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

    }

    private void showDemoLayout() {
        updateTablePanel();
        addUpPanel();
        addPathPanel();
        mainFrame.setVisible(true);
    }

    private void updateTablePanel() {

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

    private void addUpPanel() {
        upPanel.setPreferredSize(new Dimension(600, 20));
        upPanel.setMaximumSize(new Dimension(3000, 20));
        GroupLayout layout = new GroupLayout(upPanel);
        upPanel.setLayout(layout);

        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        headerLabel = new JLabel("INSTALLED PACKAGES");
        refreshButton = new JButton("↻");
        headerPanel.add(headerLabel);
        headerPanel.add(refreshButton);

        addButton = new JButton("+");
        removeButton = new JButton("-");
        buttonsPanel.add(removeButton);
        buttonsPanel.add(addButton);

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
        pathPanel.setPreferredSize(new Dimension(600, 30));
        pathPanel.setMaximumSize(new Dimension(3000, 30));
        pathLabel = new JLabel("vcpkg path:");
        pathPanel.add(pathLabel);

        pathField = new JTextField("/path/to/directory/vcpkg");
        pathField.setPreferredSize(new Dimension(150, 20));
        pathField.setMaximumSize(new Dimension(300, 20));
        pathPanel.add(pathField);

        pathButton = new JButton("set");
        pathPanel.add(pathButton);

        GroupLayout layout = new GroupLayout(pathPanel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        pathPanel.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(pathLabel)
                        .addComponent(pathField)
                        .addComponent(pathButton)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(pathLabel)
                                .addComponent(pathField)
                                .addComponent(pathButton))
        );
    }
}