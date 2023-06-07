/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prog1a.poe;

/**
 *
 * @author Mbavh
 */
import javax.swing.JOptionPane;

public class RegNLogIn {
    private static String[] usernames;
    private static String[] passwords;
    private static String[] firstNames;
    private static String[] lastNames;
    private static String[] taskNames;
    private static String[] taskIDs;
    private static double[] taskDurations;
    private static String[] taskStatuses;
    private static int numTasks;

    public static void main(String[] args) {
        initializeArrays();
        Login();
    }

    private static void initializeArrays() {
        usernames = new String[100];
        passwords = new String[100];
        firstNames = new String[100];
        lastNames = new String[100];
        taskNames = new String[100];
        taskIDs = new String[100];
        taskDurations = new double[100];
        taskStatuses = new String[100];
        numTasks = 0;
    }

    private static void Login() {
        String username;
        String password;
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            // prompt the user for input here, e.g.
            username = JOptionPane.showInputDialog(null, "Enter your username:");
            password = JOptionPane.showInputDialog(null, "Enter your password:");

            // Validate username and password
            if (!isValidUsername(username)) {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than 5 characters in length.");
                continue;
            }
            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(null, "Password must be at least 8 characters long and contain a capital letter, a number, and a special character.");
                continue;
            }

            // Login successful
            JOptionPane.showMessageDialog(null, "Welcome to EasyKanban");
            isLoggedIn = true;
        }

        int choice;
        do {
            // Display menu
            choice = Integer.parseInt(JOptionPane.showInputDialog(null, "Please choose an option:\n" +
                    "1. Add task\n" +
                    "2. Show report\n" +
                    "3. Display tasks with status 'Done'\n" +
                    "4. Display task with longest duration\n" +
                    "5. Search task by name\n" +
                    "6. Search tasks by developer\n" +
                    "7. Delete task by name\n" +
                    "8. Quit"));

            // Handle user choice
            switch (choice) {
                case 1:
                    addTask();
                    break;
                case 2:
                    displayFullReport();
                    break;
                case 3:
                    displayTasksWithStatus("Done");
                    break;
                case 4:
                    displayTaskWithLongestDuration();
                    break;
                case 5:
                    searchTaskByName();
                    break;
                case 6:
                    searchTasksByDeveloper();
                    break;
                case 7:
                    deleteTaskByName();
                    break;
                case 8:
                    JOptionPane.showMessageDialog(null, "Thank you for using our application. Goodbye!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
            }
        } while (choice != 8);
    }

    private static boolean isValidUsername(String username) {
        return username.length() <= 5 && username.contains("_");
    }

    private static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean containsCapital = false;
        boolean containsNumber = false;
        boolean containsSpecial = false;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                containsCapital = true;
            } else if (Character.isDigit(c)) {
                containsNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                containsSpecial = true;
            }
        }
        return containsCapital && containsNumber && containsSpecial;
    }

    private static void addTask() {
        if (numTasks >= taskNames.length) {
            JOptionPane.showMessageDialog(null, "Task limit reached. Cannot add more tasks.");
            return;
        }

        // Prompt user for task details
        String taskName = JOptionPane.showInputDialog(null, "Enter task name:");
        String taskDesc = JOptionPane.showInputDialog(null, "Enter task description:");
        while (taskDesc.length() > 50) {
            taskDesc = JOptionPane.showInputDialog(null, "Please enter a task description of less than 50 characters:");
        }
        String devFirstName = JOptionPane.showInputDialog(null, "Enter developer first name:");
        String devLastName = JOptionPane.showInputDialog(null, "Enter developer last name:");
        double taskDuration = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter task duration (in hours):"));
        String taskStatus = JOptionPane.showInputDialog(null, "Select task status:\n1)To Do\n2)Done\n3)Doing");

        // Generate task ID
        String taskNumber = Integer.toString(numTasks + 1);
        String taskId = taskName.substring(0, 2).toUpperCase() + ":" +
                taskNumber + ":" +
                devLastName.substring(devLastName.length() - 3).toUpperCase();

        // Store task details in arrays
        taskNames[numTasks] = taskName;
        taskIDs[numTasks] = taskId;
        taskDurations[numTasks] = taskDuration;
        taskStatuses[numTasks] = taskStatus;

        // Display task details
        JOptionPane.showMessageDialog(null, "Task successfully captured.\n" +
                "Task Name: " + taskName + "\n" +
                "Task Description: " + taskDesc + "\n" +
                "Developer Details: " + devFirstName +
                " " + devLastName + "\n" +
                "Task Duration: " + taskDuration + "\n" +
                "Task Status: " + taskStatus);

        numTasks++;
    }

    private static void displayFullReport() {
        StringBuilder report = new StringBuilder();
        report.append("Full Task Report:\n\n");
        for (int i = 0; i < numTasks; i++) {
            report.append("Task Name: ").append(taskNames[i]).append("\n");
            report.append("Task ID: ").append(taskIDs[i]).append("\n");
            report.append("Task Duration: ").append(taskDurations[i]).append("\n");
            report.append("Task Status: ").append(taskStatuses[i]).append("\n\n");
        }
        JOptionPane.showMessageDialog(null, report.toString());
    }

    private static void displayTasksWithStatus(String status) {
        StringBuilder report = new StringBuilder();
        report.append("Tasks with Status '").append(status).append("':\n\n");
        for (int i = 0; i < numTasks; i++) {
            if (taskStatuses[i].equalsIgnoreCase(status)) {
                report.append("Task Name: ").append(taskNames[i]).append("\n");
                report.append("Developer: ").append(getDeveloperFullName(i)).append("\n");
                report.append("Task Duration: ").append(taskDurations[i]).append("\n\n");
            }
        }
        JOptionPane.showMessageDialog(null, report.toString());
    }

    private static void displayTaskWithLongestDuration() {
        if (numTasks == 0) {
            JOptionPane.showMessageDialog(null, "No tasks captured yet.");
            return;
        }

        int longestTaskIndex = 0;
        double longestDuration = taskDurations[0];
        for (int i = 1; i < numTasks; i++) {
            if (taskDurations[i] > longestDuration) {
                longestTaskIndex = i;
                longestDuration = taskDurations[i];
            }
        }

        JOptionPane.showMessageDialog(null, "Task with Longest Duration:\n\n" +
                "Task Name: " + taskNames[longestTaskIndex] + "\n" +
                "Developer: " + getDeveloperFullName(longestTaskIndex) + "\n" +
                "Task Duration: " + longestDuration);
    }

    private static void searchTaskByName() {
        String searchTerm = JOptionPane.showInputDialog(null, "Enter the task name to search:");
        boolean found = false;
        for (int i = 0; i < numTasks; i++) {
            if (taskNames[i].equalsIgnoreCase(searchTerm)) {
                JOptionPane.showMessageDialog(null, "Task Found:\n\n" +
                        "Task Name: " + taskNames[i] + "\n" +
                        "Developer: " + getDeveloperFullName(i) + "\n" +
                        "Task Status: " + taskStatuses[i]);
                found = true;
                break;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "Task not found.");
        }
    }

    private static void searchTasksByDeveloper() {
        String searchTerm = JOptionPane.showInputDialog(null, "Enter the developer's last name to search:");
        boolean found = false;
        StringBuilder report = new StringBuilder();
        report.append("Tasks assigned to ").append(searchTerm).append(":\n\n");
        for (int i = 0; i < numTasks; i++) {
            if (lastNames[i].equalsIgnoreCase(searchTerm)) {
                report.append("Task Name: ").append(taskNames[i]).append("\n");
                report.append("Task Status: ").append(taskStatuses[i]).append("\n\n");
                found = true;
            }
        }
        if (found) {
            JOptionPane.showMessageDialog(null, report.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No tasks found for the developer.");
        }
    }

    private static void deleteTaskByName() {
        if (numTasks == 0) {
            JOptionPane.showMessageDialog(null, "No tasks captured yet.");
            return;
        }

        String searchTerm = JOptionPane.showInputDialog(null, "Enter the task name to delete:");
        boolean found = false;
        for (int i = 0; i < numTasks; i++) {
            if (taskNames[i].equalsIgnoreCase(searchTerm)) {
                shiftTasksUp(i);
                numTasks--;
                JOptionPane.showMessageDialog(null, "Task deleted successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            JOptionPane.showMessageDialog(null, "Task not found.");
        }
    }

    private static void shiftTasksUp(int index) {
        for (int i = index; i < numTasks - 1; i++) {
            taskNames[i] = taskNames[i + 1];
            taskIDs[i] = taskIDs[i + 1];
            taskDurations[i] = taskDurations[i + 1];
            taskStatuses[i] = taskStatuses[i + 1];
        }
    }

    private static String getDeveloperFullName(int index) {
        return firstNames[index] + " " + lastNames[index];
    }
}