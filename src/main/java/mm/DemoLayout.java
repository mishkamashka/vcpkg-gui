package mm;

import mm.listeners.RefreshButtonListener;
import mm.util.WordWrapCellRenderer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;

public class DemoLayout {

    private static VcpkgService service = new VcpkgServiceImpl();

    private static JFrame mainFrame;

    private static JLabel headerLabel;
    private static JLabel pathLabel;

    private static JButton refreshButton;
    private static JButton addButton;
    private static JButton removeButton;
    private static JButton pathButton;

    private static JPanel upPanel;
    private static JPanel headerPanel;
    private static JPanel buttonsPanel;
    private static JPanel tablePanel;
    private static JPanel pathPanel;
    private static JScrollPane scrollPanel;

    private static JTable table;

    private static JTextField pathField;

    public DemoLayout() {
        prepareGUI();
    }

    public static void main(String[] args) {
        DemoLayout DemoLayout = new DemoLayout();
        DemoLayout.showDemoLayout();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("vcpkg-gui");
        mainFrame.setSize(new Dimension(800, 600));
        mainFrame.setMinimumSize(new Dimension(600, 600));
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

    public static void updateTablePanel() {
        String[] columnNames = {"Package name", "Version", "Description"};
        List<Pkg> list = service.loadInstalledPkges();
        Object[][] data = new Object[list.size()][3];
        for (int i = 0; i < list.size(); i++)
            data[i] = list.get(i).toStringArray();

        table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(true);
        table.getColumnModel().getColumn(0).setPreferredWidth(400);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(600);

        table.getColumnModel().getColumn(2).setCellRenderer(new WordWrapCellRenderer());

        scrollPanel = new JScrollPane(table);

        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPanel, BorderLayout.CENTER);
        tablePanel.add(scrollPanel);
        tablePanel.setBackground(Color.GREEN);
    }

    private static void addUpPanel() {
        upPanel.setPreferredSize(new Dimension(600, 20));
        upPanel.setMaximumSize(new Dimension(3000, 20));
        GroupLayout layout = new GroupLayout(upPanel);
        upPanel.setLayout(layout);

        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        headerLabel = new JLabel("INSTALLED PACKAGES");
        refreshButton = new JButton("↻");
        refreshButton.addActionListener(new RefreshButtonListener(mainFrame));

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

    private static void addPathPanel() {
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