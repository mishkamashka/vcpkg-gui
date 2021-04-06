package mm;

import mm.listeners.AddButtonListener;
import mm.listeners.RefreshButtonListener;
import mm.listeners.RemoveButtonListener;
import mm.listeners.SetPathButtonListener;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.List;

public class App {

    private static final VcpkgService service = new VcpkgServiceImpl();

    private static JFrame mainFrame;

    private static JLabel headerLabel;
    private static JLabel pathLabel;
    private static JLabel processLabel;

    private static JButton refreshButton;
    private static JButton addButton;
    private static JButton removeButton;
    private static JButton pathButton;

    private static JPanel upPanel;
    private static JPanel headerPanel;
    private static JPanel buttonsPanel;
    private static JPanel tablePanel;
    private static JPanel downPanel;
    private static JPanel pathPanel;
    private static JPanel processPanel;
    private static JScrollPane scrollPanel;

    private static JTable table;

    private static JTextField pathField;

    public App() {
        prepareGUI();
    }

    public static void main(String[] args) {
        App App = new App();
        App.showDemoLayout();
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
        downPanel = new JPanel();
        processPanel = new JPanel();
        pathPanel = new JPanel();
        processLabel = new JLabel();

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(upPanel)
                                .addComponent(tablePanel)
                                .addComponent(downPanel))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(upPanel)
                        .addComponent(tablePanel)
                        .addComponent(downPanel)
        );

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

    }

    private void showDemoLayout() {
        findVcpkg();
        updateTablePanel();
        addUpPanel();
        addPathPanel();
        mainFrame.setVisible(true);
    }

    private static void updateTableModel() {
        TableModel tableModel = table.getModel();
        List<Pkg> list = service.loadInstalledPkges();
        String[] pkg;
        for (int i = 0; i < list.size()-1; i++) {
            pkg = list.get(i).toStringArray();
            tableModel.setValueAt(pkg[0], i, 0);
            tableModel.setValueAt(pkg[1], i, 1);
            tableModel.setValueAt(pkg[2], i, 2);
        }
        table.setModel(tableModel);
    }

    public static void updateTablePanel() {
        if (service.getPath().equals("")) {
            pathField.setEditable(true);
            pathButton.setText("ok");
            mainFrame.setVisible(true);
            JOptionPane.showMessageDialog(mainFrame, "VCPKG_PATH not set, enter vcpkg path please");
            return;
        }
        if (table != null) {
            updateTableModel();
            return;
        }
        String[] columnNames = {"Package name", "Version", "Description"};
        List<Pkg> list = service.loadInstalledPkges();
        Object[][] data = new Object[list.size()][3];
        if (list.size() == 0) {
            data = new Object[1][3];
            data[0] = new Object[]{"", "", ""};
        }
        for (int i = 0; i < list.size(); i++)
            data[i] = list.get(i).toStringArray();

        table = new JTable(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(400);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(600);

        //TODO add text wrapping for description
//        table.getColumnModel().getColumn(2).setCellRenderer(new WordWrapCellRenderer());

        scrollPanel = new JScrollPane(table);

        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(scrollPanel, BorderLayout.CENTER);
        tablePanel.add(scrollPanel);
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
        refreshButton.setFocusPainted(false);
        refreshButton.addActionListener(new RefreshButtonListener(mainFrame));

        headerPanel.add(headerLabel);
        headerPanel.add(refreshButton);

        addButton = new JButton("+");
        addButton.setFocusPainted(false);
        addButton.addActionListener(new AddButtonListener(mainFrame, service, processLabel));
        removeButton = new JButton("-");
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(new RemoveButtonListener(mainFrame, service, processLabel, table));
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
        GroupLayout layout = new GroupLayout(downPanel);
        downPanel.setPreferredSize(new Dimension(600, 30));
        downPanel.setMaximumSize(new Dimension(3000, 30));
        downPanel.setLayout(layout);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(pathPanel)
                        .addComponent(processPanel)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(pathPanel)
                                .addComponent(processPanel))
        );

        pathPanel.setPreferredSize(new Dimension(600, 30));
        pathPanel.setMaximumSize(new Dimension(3000, 30));
        pathLabel = new JLabel("vcpkg path:");
        pathPanel.add(pathLabel);

        pathField = new JTextField(service.getPath());
        pathField.setEditable(false);
        pathField.setPreferredSize(new Dimension(150, 20));
        pathField.setMaximumSize(new Dimension(300, 20));
        pathPanel.add(pathField);

        pathButton = new JButton("edit");
        pathButton.addActionListener(new SetPathButtonListener(mainFrame, pathButton, pathField, service));
        pathButton.setFocusPainted(false);
        pathPanel.add(pathButton);

        layout = new GroupLayout(pathPanel);
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

        processPanel.setLayout(new BorderLayout());
        processLabel.setHorizontalAlignment(JLabel.CENTER);
        processLabel.setVerticalAlignment(JLabel.CENTER);
        processPanel.add(processLabel);

    }

    private static void findVcpkg() {
        String env = System.getenv("VCPKG_PATH");
        if (env == null)
            return;
        service.testVcpkgPath(env);
    }
}