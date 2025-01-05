import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherLoginFrame extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public TeacherLoginFrame() {
        // Setup the frame
        setTitle("Teacher Login");
        setSize(800, 600); // Double the frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Increase spacing
        getContentPane().setBackground(Color.cyan);
        // Create UI components
        JLabel titleLabel = new JLabel("Student Attendance System");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 32)); // Change font style
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20)); // Change font style
        gbc.gridwidth = 1; gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        add(usernameLabel, gbc);
        
        usernameField = new JTextField(30); // Increase field size
        usernameField.setFont(new Font("Times New Roman", Font.PLAIN, 20)); // Change font style
        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        add(usernameField, gbc);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20)); // Change font style
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(30); // Increase field size
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 20)); // Change font style
        gbc.gridx = 1; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);
        
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Times New Roman", Font.PLAIN, 20)); // Change font style
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);

        // Add action listener to the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Simple login check 
                if (("osy".equals(username) && "osy".equals(password)) ||
                    ("ajp".equals(username) && "ajp".equals(password)) ||
                    ("est".equals(username) && "est".equals(password)) ||
                    ("ste".equals(username) && "ste".equals(password)) ||
                    ("acn".equals(username) && "acn".equals(password))) {
                    new AttendanceFrame();
                    dispose(); // Close the login frame
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect username or password");
                }
            }
        });

        // Center the frame
        setLocationRelativeTo(null);
        setVisible(true);
    }

    static class AttendanceFrame extends JFrame {
        public AttendanceFrame() {
            setTitle("Attendance Management");
            setSize(800, 600);
            setLayout(new GridBagLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setBackground(Color.cyan); // Set background color to light gray

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.CENTER; // Center components

            Font font = new Font("Times New Roman", Font.PLAIN, 18); // Set font size to 18

            JButton addStudentButton = new JButton("Add Student");
            addStudentButton.setFont(font);
            gbc.gridx = 0; gbc.gridy = 0;
            add(addStudentButton, gbc);

            JButton markedAttendanceButton = new JButton("Marked Attendance");
            markedAttendanceButton.setFont(font);
            gbc.gridx = 1; gbc.gridy = 0;
            add(markedAttendanceButton, gbc);

            JButton viewDetailsButton = new JButton("View Details");
            viewDetailsButton.setFont(font);
            gbc.gridx = 2; gbc.gridy = 0; 
            add(viewDetailsButton, gbc);

            // Add action listener to the Add Student button
            addStudentButton.addActionListener(e -> {
                new AddStud(); // Open AddStud frame
                dispose(); // Close the current frame
            });

            // Add action listener for marked attendance button
            markedAttendanceButton.addActionListener(e -> {
                new AttendanceFrame1(); // Open AttendanceFrame1
                dispose(); // Close the current frame
            });

            // Add action listener for view details button
            viewDetailsButton.addActionListener(e -> {
                new ViewDetailsFrame(); // Open ViewDetailsFrame
                dispose(); // Close the current frame
            });

            // Center the frame
            setLocationRelativeTo(null);
            setVisible(true);
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(AttendanceFrame::new);
        }
    }
   
//Marked attendance
    static class AttendanceFrame1 extends JFrame {
        private JSpinner dateSpinner;
        private JComboBox<String> classComboBox;
        private JComboBox<String> subjectComboBox;
        private JPanel studentPanel;
        private List<JCheckBox> studentCheckBoxes; // Store checkboxes

        public AttendanceFrame1() {
            setTitle("Attendance Management");
            setSize(800, 600);
            setLayout(new GridBagLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setBackground(Color.cyan);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.CENTER;

            Font font = new Font("Times New Roman", Font.PLAIN, 17);

            // Date Spinner
            JLabel dateLabel = new JLabel("Select Date:");
            dateLabel.setFont(font);
            gbc.gridx = 0; gbc.gridy = 0;
            add(dateLabel, gbc);

            dateSpinner = new JSpinner(new SpinnerDateModel());
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
            dateSpinner.setEditor(dateEditor);
            dateSpinner.setFont(font);
            gbc.gridx = 1; gbc.gridy = 0;
            add(dateSpinner, gbc);

            // Class ComboBox
            JLabel classLabel = new JLabel("Select Class:");
            classLabel.setFont(font);
            gbc.gridx = 0; gbc.gridy = 1;
            add(classLabel, gbc);

            classComboBox = new JComboBox<>(new String[]{"TY"});
            classComboBox.setFont(font);
            gbc.gridx = 1; gbc.gridy = 1;
            add(classComboBox, gbc);

            // Subject ComboBox
            JLabel subjectLabel = new JLabel("Select Subject:");
            subjectLabel.setFont(font);
            gbc.gridx = 0; gbc.gridy = 2;
            add(subjectLabel, gbc);

            subjectComboBox = new JComboBox<>(new String[]{"OSY", "AJP", "EST", "STE", "ACN"});
            subjectComboBox.setFont(font);
            gbc.gridx = 1; gbc.gridy = 2;
            add(subjectComboBox, gbc);

            // Mark Attendance Button
            JButton markAttendanceButton = new JButton("OK");
            markAttendanceButton.setFont(font);
            gbc.gridx = 0; gbc.gridy = 3;
            add(markAttendanceButton, gbc);

            // Back Button
            JButton backButton = new JButton("Back");
            backButton.setFont(font);
            gbc.gridx = 1; gbc.gridy = 3;
            add(backButton, gbc);

            backButton.addActionListener(e -> {
                new AttendanceFrame();
                dispose();
            });

            // Panel for displaying students
            studentPanel = new JPanel();
            studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
            gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
            gbc.weighty = 1;
            add(studentPanel, gbc);
            studentCheckBoxes = new ArrayList<>(); // Initialize checkbox list

            // Load students when the OK button is clicked
            markAttendanceButton.addActionListener(e -> loadStudents());

            // Center the frame
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void loadStudents() {
            studentPanel.removeAll();
            studentCheckBoxes.clear(); // Clear existing checkboxes

            String selectedClass = (String) classComboBox.getSelectedItem();
            String date = ((JSpinner.DateEditor) dateSpinner.getEditor()).getFormat().format(dateSpinner.getValue());
            String selectedSubject = (String) subjectComboBox.getSelectedItem();

            String query = "SELECT name, roll_no FROM students WHERE class = ?";
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/microproject", "root", "1234");
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, selectedClass);
                ResultSet rs = pstmt.executeQuery();

                boolean hasStudents = false;
                while (rs.next()) {
                    hasStudents = true;
                    String studentName = rs.getString("name");
                    String rollNo = rs.getString("roll_no");
                    JCheckBox studentCheckBox = new JCheckBox(studentName + " (Roll No: " + rollNo + ")");
                    studentCheckBox.setActionCommand(rollNo);
                    studentCheckBox.setFont(new Font("Times New Roman", Font.PLAIN, 22));
                    studentPanel.add(studentCheckBox);
                    studentCheckBoxes.add(studentCheckBox); // Add checkbox to the list
                }

                if (hasStudents) {
                    JButton submitButton = new JButton("Submit Attendance");
                    submitButton.setFont(new Font("Times New Roman", Font.PLAIN, 18));
                    submitButton.addActionListener(e -> submitAttendance());
                    studentPanel.add(submitButton);
                } else {
                    studentPanel.add(new JLabel("No students found for the selected class."));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading students: " + ex.getMessage());
            }

            studentPanel.revalidate();
            studentPanel.repaint();
        }

        private void submitAttendance() {
            String date = ((JSpinner.DateEditor) dateSpinner.getEditor()).getFormat().format(dateSpinner.getValue());
            String selectedSubject = (String) subjectComboBox.getSelectedItem();
            String teacherName = JOptionPane.showInputDialog(this, "Enter Teacher's Name:");

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/microproject", "root", "1234")) {
                String sql = "INSERT INTO attendance (date, class, subject, teacher, Roll_NO) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    for (JCheckBox checkbox : studentCheckBoxes) {
                        if (checkbox.isSelected()) {
                            String rollNo = checkbox.getActionCommand();
                            stmt.setString(1, date);
                            stmt.setString(2, (String) classComboBox.getSelectedItem());
                            stmt.setString(3, selectedSubject);
                            stmt.setString(4, teacherName);
                            stmt.setString(5, rollNo);
                            stmt.executeUpdate();
                        }
                    }
                }
                JOptionPane.showMessageDialog(this, "Attendance marked successfully.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error marking attendance: " + ex.getMessage());
            }
        }

        public static void main(String[] args) {
            SwingUtilities.invokeLater(AttendanceFrame1::new);
        }
    }


    static class ViewDetailsFrame extends JFrame {
	    private JSpinner dateSpinner;
	    private JComboBox<String> classComboBox;
	    private JButton displayDetailsButton;
	    private JTable attendanceTable;

	    public ViewDetailsFrame() {
	        setTitle("View Attendance Details");
	        setSize(800, 600);
	        setLayout(new GridBagLayout());
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        getContentPane().setBackground(Color.cyan); // Set background color to light gray

	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(10, 10, 10, 10);
	        gbc.anchor = GridBagConstraints.CENTER; // Center components

	        Font font = new Font("Times New Roman", Font.PLAIN, 18); // Set font size to 18

	        // Date Spinner
	        JLabel dateLabel = new JLabel("Select Date:");
	        dateLabel.setFont(font);
	        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.EAST;
	        add(dateLabel, gbc);

	        dateSpinner = new JSpinner(new SpinnerDateModel());
	        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd");
	        dateSpinner.setEditor(dateEditor);
	        dateSpinner.setFont(font);
	        gbc.gridx = 1; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
	        add(dateSpinner, gbc);

	        // Class ComboBox
	        JLabel classLabel = new JLabel("Select Class:");
	        classLabel.setFont(font);
	        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
	        add(classLabel, gbc);

	        classComboBox = new JComboBox<>(new String[]{"TY"}); // Add actual classes as needed
	        classComboBox.setFont(font);
	        gbc.gridx = 1; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
	        add(classComboBox, gbc);

	        // Display Details Button
	        displayDetailsButton = new JButton("Display Details");
	        displayDetailsButton.setFont(font);
	        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
	        add(displayDetailsButton, gbc);

	        // Table for displaying attendance details
	        attendanceTable = new JTable();
	        attendanceTable.setFont(font); // Set font for the table
	        JScrollPane scrollPane = new JScrollPane(attendanceTable);
	        scrollPane.setPreferredSize(new Dimension(500, 200));
	        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.BOTH;
	        add(scrollPane, gbc);

	        // Action listener for Display Details button
	        displayDetailsButton.addActionListener(e -> displayAttendanceDetails());

	        // Back Button
	        JButton backButton = new JButton("Back");
	        backButton.setFont(font);
	        gbc.gridx = 0; 
	        gbc.gridy = 4; 
	        gbc.anchor = GridBagConstraints.EAST;
	        add(backButton, gbc);
	        backButton.addActionListener(e -> {
	            new AttendanceFrame(); // Open the previous frame
	            dispose(); // Close the current frame
	        });

	        setLocationRelativeTo(null);
	        setVisible(true);
	    }

	    private void displayAttendanceDetails() {
	        String selectedClass = (String) classComboBox.getSelectedItem();
	        String date = ((JSpinner.DateEditor) dateSpinner.getEditor()).getFormat().format(dateSpinner.getValue());

	        // Query to get the total number of students in the selected class
	        String totalStudentsQuery = "SELECT COUNT(*) AS total FROM students WHERE class = ?";
	        
	        // Query to get attendance details
	        String attendanceQuery = "SELECT subject, " +
	                                  "SUM(CASE WHEN Roll_No IS NOT NULL THEN 1 ELSE 0 END) AS present " +
	                                  "FROM attendance WHERE date = ? AND class = ? GROUP BY subject";

	        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/microproject", "root", "1234");
	             PreparedStatement totalStmt = conn.prepareStatement(totalStudentsQuery);
	             PreparedStatement attendanceStmt = conn.prepareStatement(attendanceQuery)) {
	            
	            // Get total students
	            totalStmt.setString(1, selectedClass);
	            ResultSet totalRs = totalStmt.executeQuery();
	            int totalStudents = 0;
	            if (totalRs.next()) {
	                totalStudents = totalRs.getInt("total");
	            }

	            // Get attendance details
	            attendanceStmt.setString(1, date);
	            attendanceStmt.setString(2, selectedClass);
	            ResultSet attendanceRs = attendanceStmt.executeQuery();

	            // Create a model for the table
	            DefaultTableModel model = new DefaultTableModel(new String[]{"Subject", "Total Students", "Present", "Absent"}, 0);

	            while (attendanceRs.next()) {
	                String subject = attendanceRs.getString("subject");
	                int present = attendanceRs.getInt("present");
	                int absent = totalStudents - present; // Calculate absent as total - present

	                // Add a row to the model
	                model.addRow(new Object[]{subject, totalStudents, present, absent});
	            }

	            // Set the model to the table
	            attendanceTable.setModel(model);
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Error loading attendance details: " + ex.getMessage());
	        }
	    }


	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(ViewDetailsFrame::new);
	    }
	}
    static  class AddStud extends JFrame {
        public AddStud() {
            setTitle("Add Student");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new GridBagLayout());
            getContentPane().setBackground(Color.cyan); // Set background color
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.CENTER; // Center all components

            // Font settings
            Font font = new Font("Times New Roman", Font.PLAIN, 20);

            // Create Labels and Text Fields with size 20
            JLabel StudnameLabel = new JLabel("Student Name:");
            StudnameLabel.setFont(font);
            JTextField StudnameField = new JTextField(20); // Set width to 20
            StudnameField.setFont(font);

            JLabel RollNoLabel = new JLabel("Student Roll Number:");
            RollNoLabel.setFont(font);
            JTextField RollNoField = new JTextField(20); // Set width to 20
            RollNoField.setFont(font);

            JLabel StudConNo = new JLabel("Student Contact Number:");
            StudConNo.setFont(font);
            JTextField StudConNoF = new JTextField(20); // Set width to 20
            StudConNoF.setFont(font);

            JLabel EmailLabel = new JLabel("Student Email ID:");
            EmailLabel.setFont(font);
            JTextField EmailField = new JTextField(20); // Set width to 20
            EmailField.setFont(font);

            JLabel StudClassLabel = new JLabel("Student Class:");
            StudClassLabel.setFont(font);
            String[] classes = {"Select Class", "TY"};
            JComboBox<String> StudClassCombo = new JComboBox<>(classes);
            StudClassCombo.setFont(font);
            StudClassCombo.setPreferredSize(new Dimension(200, StudClassCombo.getPreferredSize().height)); // Adjust size

            JLabel StudDivLabel = new JLabel("Student Division:");
            StudDivLabel.setFont(font);
            String[] divisions = {"Select Division", "A", "B"};
            JComboBox<String> StudDivCombo = new JComboBox<>(divisions);
            StudDivCombo.setFont(font);
            StudDivCombo.setPreferredSize(new Dimension(200, StudDivCombo.getPreferredSize().height)); // Adjust size

            // Create Submit Button with preferred size
            JButton AddStudButton1 = new JButton("Add Student");
            AddStudButton1.setFont(font);
            AddStudButton1.setPreferredSize(new Dimension(150, 30)); // Adjust as needed

            // Set GridBag constraints for each component
            gbc.gridx = 0; gbc.gridy = 0; add(StudnameLabel, gbc);
            gbc.gridx = 1; add(StudnameField, gbc);

            gbc.gridx = 0; gbc.gridy = 1; add(RollNoLabel, gbc);
            gbc.gridx = 1; add(RollNoField, gbc);

            gbc.gridx = 0; gbc.gridy = 2; add(StudConNo, gbc);
            gbc.gridx = 1; add(StudConNoF, gbc);

            gbc.gridx = 0; gbc.gridy = 3; add(EmailLabel, gbc);
            gbc.gridx = 1; add(EmailField, gbc);

            gbc.gridx = 0; gbc.gridy = 4; add(StudClassLabel, gbc);
            gbc.gridx = 1; add(StudClassCombo, gbc);

            gbc.gridx = 0; gbc.gridy = 5; add(StudDivLabel, gbc);
            gbc.gridx = 1; add(StudDivCombo, gbc);

            // Button for adding students
            gbc.gridx = 0; 
            gbc.gridy = 6; 
            gbc.gridwidth = 2; 
            gbc.anchor = GridBagConstraints.CENTER; 
            add(AddStudButton1, gbc);

            // Button for going back
            JButton backButton = new JButton("Back");
            backButton.setFont(font);
            gbc.gridy = 7; // Move down one row
            add(backButton, gbc);

            // Action listener for the back button
            backButton.addActionListener(e -> {
                new AttendanceFrame(); // Open the AttendanceFrame
                dispose(); // Close the current frame if desired
            });

            // Action listener for the Add Student button
            AddStudButton1.addActionListener(e -> {
                String name = StudnameField.getText().trim();
                String rollNo = RollNoField.getText().trim();
                String contactNo = StudConNoF.getText().trim();
                String email = EmailField.getText().trim();
                String studentClass = (String) StudClassCombo.getSelectedItem();
                String division = (String) StudDivCombo.getSelectedItem();

                if (validateFields(name, rollNo, contactNo, email, studentClass, division)) {
                    addStudent(name, rollNo, contactNo, email, studentClass, division);
                }
            });

            // Center the frame
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private boolean validateFields(String name, String rollNo, String contactNo, String email, String studentClass, String division) {
            if (name.isEmpty() || rollNo.isEmpty() || contactNo.isEmpty() || email.isEmpty() ||
                "Select Class".equals(studentClass) || "Select Division".equals(division)) {
                JOptionPane.showMessageDialog(this, "All fields are mandatory!");
                return false;
            }
            return true;
        }

        private void addStudent(String name, String rollNo, String contactNo, String email, String studentClass, String division) {
            // Database connection details
            String url = "jdbc:mysql://localhost:3306/microproject";
            String user = "root";
            String password = "1234";

            // Query to check for duplicate
            String checkQuery = "SELECT COUNT(*) FROM students WHERE roll_no = ? OR email = ?";
            // Insert student into the database
            String insertQuery = "INSERT INTO students (name, roll_no, contact_no, email, class, division) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                // Check for duplicate entries
                try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                    checkStmt.setString(1, rollNo);
                    checkStmt.setString(2, email);
                    ResultSet rs = checkStmt.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        JOptionPane.showMessageDialog(this, "A student with this Roll Number or Email already exists.");
                        return; // Stop execution if a duplicate is found
                    }
                }

                // Insert new student if no duplicates found
                try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                    pstmt.setString(1, name);
                    pstmt.setString(2, rollNo);
                    pstmt.setString(3, contactNo);
                    pstmt.setString(4, email);
                    pstmt.setString(5, studentClass);
                    pstmt.setString(6, division);

                    int rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Student added successfully!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add student.");
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
            }
        }
    }
    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TeacherLoginFrame::new);
    }
}
