import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.io.*;

public class SecurityGuardGUI extends JFrame {
    private JTextField nameField;
    private JTextField mobileField;
    private JTextField timeField;
    private JRadioButton maleButton;
    private JRadioButton femaleButton;
    private JRadioButton otherButton;
    private ButtonGroup genderGroup;
    private JButton saveButton;
    private JButton showVisitorsButton;
    private JTable visitorTable;
    private DefaultTableModel tableModel;
    
    private enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
    public SecurityGuardGUI() {
        
        nameField = new JTextField(20);
        mobileField = new JTextField(20);
        timeField = new JTextField(20);
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        otherButton = new JRadioButton("Other");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderGroup.add(otherButton);
        saveButton = new JButton("Save visitor");
        showVisitorsButton = new JButton("Show visitors");

        
        tableModel = new DefaultTableModel(new String[]{"Name", "Mobile", "Time", "Gender"}, 0);
        visitorTable = new JTable(tableModel);

        
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String mobile = mobileField.getText();
                String time = timeField.getText();
                Gender gender = Gender.OTHER;
                if (maleButton.isSelected()) {
                    gender = Gender.MALE;
                } else if (femaleButton.isSelected()) {
                    gender = Gender.FEMALE;
                }

                
                try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("visitor.txt", true)))) {
                    out.println(name + "," + mobile + "," + time + "," + gender);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                nameField.setText("");
                mobileField.setText("");
                timeField.setText("");
            }
        });

        showVisitorsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                tableModel.setRowCount(0);

               
                try (BufferedReader br = new BufferedReader(new FileReader("visitor.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] visitor = line.split(",");
                        tableModel.addRow(visitor);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                
            }
        });

       
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Name:"), constraints);
        constraints.gridx = 1;
        add(nameField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(new JLabel("Mobile:"), constraints);
        constraints.gridx = 1;
        add(mobileField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(new JLabel("Time:"), constraints);
        constraints.gridx = 1;
        add(timeField, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(new JLabel("Gender:"), constraints);
        constraints.gridx = 1;
        add(maleButton, constraints);
        constraints.gridx = 2;
        add(femaleButton, constraints);
        constraints.gridx = 3;
        add(otherButton, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 2;
        add(saveButton, constraints);
        constraints.gridy = 5;
        add(showVisitorsButton, constraints);
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        add(new JScrollPane(visitorTable), constraints);

        
        setTitle("Security Guard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SecurityGuardGUI();
    }
}

