/**
 * TemperatureConverter.java
 *
 * LESSON: Building a GUI Application with Java Swing
 * ====================================================
 * Java Swing is a GUI (Graphical User Interface) toolkit built into Java.
 * It lets us create windows, buttons, text fields, and other visual components
 * that users can interact with — instead of just printing text to a terminal.
 *
 * Key Swing concept: Everything in Swing is a "component" (JButton, JLabel, etc.)
 * that lives inside a "container" (JPanel, JFrame). Think of it like nested boxes.
 */

import java.awt.*;           // Imports all Swing components (JFrame, JPanel, JLabel, etc.)
import java.awt.event.*;    // Imports border utilities for spacing and outlines
import javax.swing.*;              // Imports core graphics tools: Color, Font, Dimension, etc.
import javax.swing.border.*;        // Imports event listeners so we can respond to user actions

/**
 * LESSON: Classes and Inheritance
 * ================================
 * Our class "extends JFrame" — that means TemperatureConverter IS a JFrame (a window).
 * By inheriting from JFrame, we get all window behavior (title bar, close button, etc.)
 * for free, and we just add our own custom content on top.
 */
public class App extends JFrame {

    /**
     * LESSON: Instance Variables (Fields)
     * =====================================
     * These are variables that belong to the entire object — any method in this
     * class can access them. We declare them here (outside any method) so that
     * multiple methods like initComponents() and convert() can share them.
     *
     * If we declared these inside a single method, the other methods couldn't see them!
     */
    private JTextField inputField;            // Where the user types a temperature number
    private JLabel resultLabel;               // Where we display the converted result
    private JRadioButton fahrenheitToCelsius; // Option: convert F -> C
    private JRadioButton celsiusToFahrenheit; // Option: convert C -> F
    private ButtonGroup conversionGroup;      // Groups radio buttons so only ONE can be selected

    /**
     * LESSON: Constructors
     * =====================
     * A constructor is a special method that runs automatically when you create
     * a new object with the "new" keyword. It has the same name as the class
     * and no return type. We use it to set up our window's initial state.
     */
    public App() {
        // setTitle() comes from JFrame — sets the text in the window's title bar
        setTitle("Temperature Converter");

        // EXIT_ON_CLOSE means the program fully quits when the user closes the window.
        // Without this, closing the window would hide it but keep the program running!
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Prevent the user from resizing the window.
        // This keeps our layout looking exactly as we designed it.
        setResizable(false);

        // Call our helper method to build and add all the visual components.
        // Breaking setup into a separate method keeps the constructor clean and readable.
        initComponents();

        // pack() tells the window to shrink-wrap itself to perfectly fit its contents.
        // Always call this AFTER adding your components, not before!
        pack();

        // null means "center relative to the whole screen."
        // This makes the window appear in the middle of the monitor on launch.
        setLocationRelativeTo(null);

        // The window starts hidden by default. setVisible(true) makes it appear.
        setVisible(true);
    }

    /**
     * LESSON: Methods and Organization
     * ==================================
     * Rather than writing 100 lines of code in the constructor, we break the UI
     * setup into its own method: initComponents(). This is a best practice called
     * "separation of concerns" — each method does ONE clear job.
     *
     * "private" means only this class can call this method — it's an internal helper.
     * "void" means the method doesn't return any value; it just does work.
     */
    private void initComponents() {

        // =====================================================================
        // LESSON: Panels and Layout Managers
        // =====================================================================
        // A JPanel is an invisible container that holds other components.
        // Every Swing app uses panels to group and organize things visually.
        //
        // Layout Managers control HOW components are arranged inside a container.
        // BoxLayout.Y_AXIS stacks components vertically, one on top of another.
        // Other options: FlowLayout (left-to-right), GridLayout (rows & columns), etc.

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // EmptyBorder adds invisible padding (like CSS padding) around the panel's edges.
        // Order is: top, left, bottom, right — just like CSS!
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Set the panel's background to a deep navy color.
        // new Color(r, g, b) lets you specify any color using RGB values (0-255 each).
        mainPanel.setBackground(new Color(18, 22, 45));

        // =====================================================================
        // LESSON: JLabel — Displaying Text
        // =====================================================================
        // A JLabel shows non-editable text. It's perfect for titles, descriptions,
        // and displaying results. The user can read it but not type into it.

        JLabel titleLabel = new JLabel("Temperature Converter");

        // new Font(name, style, size) creates a font.
        // Font.BOLD makes it bold. Size 34 is large and easy to read.
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 34));
        titleLabel.setForeground(new Color(255, 220, 100)); // Warm golden color

        // CENTER_ALIGNMENT tells BoxLayout to horizontally center this component.
        // Without this, BoxLayout would stretch or left-align it by default.
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =====================================================================
        // LESSON: JRadioButton and ButtonGroup — Mutually Exclusive Choices
        // =====================================================================
        // JRadioButton lets the user pick one option from a group.
        // IMPORTANT: Radio buttons only work as a group if you add them to a ButtonGroup.
        // The ButtonGroup enforces that selecting one automatically deselects the others.

        fahrenheitToCelsius = new JRadioButton("  F  ->  C   (Fahrenheit to Celsius)");
        celsiusToFahrenheit = new JRadioButton("  C  ->  F   (Celsius to Fahrenheit)");

        // Set the first option as the default selection when the app opens.
        fahrenheitToCelsius.setSelected(true);

        // Style both radio buttons to match our dark theme.
        // We loop over them using an array to avoid repeating ourselves (DRY principle).
        for (JRadioButton btn : new JRadioButton[]{fahrenheitToCelsius, celsiusToFahrenheit}) {
            btn.setFont(new Font("SansSerif", Font.PLAIN, 26));
            btn.setForeground(new Color(200, 220, 255));  // Soft blue-white text
            btn.setBackground(new Color(28, 34, 65));     // Slightly lighter than main panel
            btn.setFocusPainted(false);                   // Removes the default focus box
        }

        // ButtonGroup links the two radio buttons so they're mutually exclusive.
        // Note: ButtonGroup is NOT a visual component — it's just a logical grouping.
        conversionGroup = new ButtonGroup();
        conversionGroup.add(fahrenheitToCelsius);
        conversionGroup.add(celsiusToFahrenheit);

        // Place the radio buttons in a visual panel with a visible border.
        JPanel radioPanel = new JPanel(new GridLayout(2, 1, 0, 8)); // 2 rows, 1 col, 8px gap
        radioPanel.setBackground(new Color(28, 34, 65));
        radioPanel.setBorder(BorderFactory.createCompoundBorder(
            // Outer: a rounded, colored line border
            new LineBorder(new Color(80, 100, 180), 2, true),
            // Inner: padding so text doesn't touch the border edge
            new EmptyBorder(10, 16, 10, 16)
        ));
        radioPanel.add(fahrenheitToCelsius);
        radioPanel.add(celsiusToFahrenheit);

        // Constrain the panel's width so it doesn't stretch to fill the window.
        // setMaximumSize works with BoxLayout to cap the width at 620px.
        radioPanel.setMaximumSize(new Dimension(620, 140));
        radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =====================================================================
        // LESSON: JLabel for Sub-headings
        // =====================================================================
        JLabel inputLabel = new JLabel("Enter temperature:");
        inputLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        inputLabel.setForeground(new Color(160, 180, 255));
        inputLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =====================================================================
        // LESSON: JTextField — Getting Text Input from the User
        // =====================================================================
        // JTextField is a single-line box where the user can type.
        // Unlike JLabel, it's interactive — the user controls its content.

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.BOLD, 32)); // Large, easy-to-read input
        inputField.setForeground(new Color(255, 255, 255));        // White text
        inputField.setBackground(new Color(40, 48, 90));           // Dark blue background
        inputField.setCaretColor(Color.WHITE);                     // The blinking cursor color
        inputField.setHorizontalAlignment(JTextField.CENTER);      // Center the typed text

        // Cap the height and width so it stays a reasonable size.
        inputField.setMaximumSize(new Dimension(620, 70));

        // Create a compound border: colored rounded outline + inner padding.
        inputField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(100, 130, 220), 2, true),
            new EmptyBorder(10, 16, 10, 16)
        ));

        // =====================================================================
        // LESSON: Event Listeners — Responding to User Actions
        // =====================================================================
        // Swing uses the "Observer Pattern": components fire "events" when something
        // happens (a click, a key press), and we write "listeners" that respond.
        //
        // ActionListener fires when the user presses Enter inside a JTextField.
        // "e -> convert()" is a lambda — a compact way to write a one-line listener.
        // It's shorthand for:
        //   new ActionListener() {
        //       public void actionPerformed(ActionEvent e) { convert(); }
        //   }

        inputField.addActionListener(e -> convert());

        // =====================================================================
        // LESSON: JButton — Triggering Actions on Click
        // =====================================================================
        // A JButton is a clickable button. We attach an ActionListener to it
        // so that our code runs whenever the user clicks it.

        JButton convertButton = new JButton("Convert");
        convertButton.setFont(new Font("SansSerif", Font.BOLD, 30));
        convertButton.setBackground(new Color(80, 120, 220));  // Bright blue
        convertButton.setForeground(Color.WHITE);
        convertButton.setFocusPainted(false);   // Cleaner look — no dotted focus rectangle
        convertButton.setBorder(new EmptyBorder(16, 48, 16, 48));
        convertButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Pointer cursor on hover
        convertButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Lambda ActionListener: calls our convert() method when clicked.
        convertButton.addActionListener(e -> convert());

        // =====================================================================
        // LESSON: MouseListener — Reacting to Mouse Events (Hover Effects)
        // =====================================================================
        // MouseAdapter is a convenience class that implements MouseListener with
        // empty methods, so we only need to OVERRIDE the ones we care about.
        // Here we change the button color on hover for a polished feel.

        convertButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Mouse is now over the button — brighten it slightly
                convertButton.setBackground(new Color(60, 100, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Mouse left the button — restore the original color
                convertButton.setBackground(new Color(80, 120, 220));
            }
        });

        // =====================================================================
        // LESSON: Displaying Results with JLabel
        // =====================================================================
        // We reuse a JLabel to display the result after conversion.
        // Initially it's blank (" "), and we update its text in the convert() method.
        // The space " " (not empty "") ensures the label keeps its height in the layout.

        resultLabel = new JLabel(" ");
        resultLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        resultLabel.setForeground(new Color(100, 255, 160)); // Bright green for success
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setBorder(new EmptyBorder(12, 0, 0, 0));

        // =====================================================================
        // LESSON: A Secondary Button — Clear
        // =====================================================================
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("SansSerif", Font.PLAIN, 26));
        clearButton.setBackground(new Color(50, 56, 90));
        clearButton.setForeground(new Color(180, 200, 255));
        clearButton.setFocusPainted(false);
        clearButton.setBorder(new EmptyBorder(12, 32, 12, 32));
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        clearButton.addActionListener(e -> {
            // Reset the input field to empty
            inputField.setText("");
            // Reset the result label to a blank space (keeps layout stable)
            resultLabel.setText(" ");
            // Reset result color back to green (in case it was red from an error)
            resultLabel.setForeground(new Color(100, 255, 160));
            // Move keyboard focus back to the input field for convenience
            inputField.requestFocus();
        });

        // =====================================================================
        // LESSON: Box.createVerticalStrut() — Adding Vertical Spacing
        // =====================================================================
        // With BoxLayout, there's no CSS "margin" — instead, we insert invisible
        // "strut" components that act as spacers between other components.
        // createVerticalStrut(n) creates a rigid n-pixel tall invisible block.

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(22));
        mainPanel.add(radioPanel);
        mainPanel.add(Box.createVerticalStrut(22));
        mainPanel.add(inputLabel);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(inputField);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(convertButton);
        mainPanel.add(resultLabel);
        mainPanel.add(Box.createVerticalStrut(8));
        mainPanel.add(clearButton);

        // Finally, add the fully assembled mainPanel to the JFrame (our window).
        // "this" refers to the TemperatureConverter object, which IS the JFrame.
        add(mainPanel);
    }

    /**
     * LESSON: The Logic Method — Separating UI from Business Logic
     * ==============================================================
     * convert() is where the actual math happens. Notice how it's completely
     * separate from the UI setup code above. This is good design — if we ever
     * wanted to change the look of the app, we wouldn't need to touch this method.
     *
     * This pattern is called "separation of concerns" and is a fundamental
     * principle in software engineering.
     */
    private void convert() {
        // getText() retrieves whatever the user typed in the JTextField.
        // trim() removes any leading/trailing whitespace (spaces, tabs, newlines).
        String input = inputField.getText().trim();

        // Guard clause: if the field is empty, show an error and stop early.
        // Returning early like this avoids deeply nested if-else blocks.
        if (input.isEmpty()) {
            showError("Please enter a temperature value.");
            return; // Stop here — don't try to convert nothing
        }

        // =====================================================================
        // LESSON: try-catch — Handling Exceptions Gracefully
        // =====================================================================
        // Double.parseDouble() converts a String like "98.6" into an actual number.
        // BUT — if the user types "abc", it can't convert that and throws a
        // NumberFormatException. Without try-catch, the whole program would crash!
        //
        // try  { } — attempt the risky code
        // catch { } — run this block if the risky code throws an exception

        try {
            // Parse the user's input string into a double (decimal number)
            double value = Double.parseDouble(input);

            double converted; // Will hold the result of our formula
            String resultText; // Will hold the nicely formatted result string

            // =====================================================================
            // LESSON: if-else and the isSelected() Method
            // =====================================================================
            // isSelected() returns true if a radio button is currently checked.
            // We use it to decide WHICH conversion formula to apply.

            if (fahrenheitToCelsius.isSelected()) {
                // Formula: subtract 32, then multiply by 5/9
                // Note: 5.0/9.0 uses doubles (decimal division).
                // If you wrote 5/9 instead, Java would do INTEGER division and get 0!
                converted = (value - 32) * 5.0 / 9.0;

                // String.format() works like a template:
                // %.2f means "a decimal number rounded to 2 decimal places"
                resultText = String.format("%.2f F  =  %.2f C", value, converted);

            } else {
                // Formula: multiply by 9/5, then add 32
                converted = value * 9.0 / 5.0 + 32;
                resultText = String.format("%.2f C  =  %.2f F", value, converted);
            }

            // Update the result label with our formatted string
            resultLabel.setText(resultText);

            // Make sure the text is green (it could be red from a previous error)
            resultLabel.setForeground(new Color(100, 255, 160));

        } catch (NumberFormatException ex) {
            // This block runs only if parseDouble() failed —
            // meaning the input wasn't a valid number (e.g. user typed "hello").
            showError("Invalid input — please enter a number.");
        }
    }

    /**
     * LESSON: Helper Methods — Reusable, Named Chunks of Logic
     * ==========================================================
     * showError() is a tiny helper we call from multiple places.
     * Instead of duplicating the same two lines everywhere,
     * we write it once here and call it by name.
     * This is the DRY principle: Don't Repeat Yourself.
     *
     * @param message  The error message string to display to the user.
     */
    private void showError(String message) {
        resultLabel.setText(message);
        resultLabel.setForeground(new Color(255, 100, 100)); // Red text signals an error
    }

    /**
     * LESSON: The main() Method — The Entry Point of Every Java Program
     * ===================================================================
     * Every Java application must have a main() method. It's the very first
     * thing that runs when you execute: java TemperatureConverter
     *
     * "public static void main(String[] args)" is the exact signature Java looks for.
     * - public:     accessible from anywhere
     * - static:     belongs to the class, not an object (runs before any object exists)
     * - void:       returns nothing
     * - String[] args: accepts command-line arguments (we don't use them here)
     */
    public static void main(String[] args) {

        // LESSON: Look and Feel — Native OS Styling
        // ==========================================
        // UIManager.setLookAndFeel() tells Swing to mimic the appearance of the
        // operating system. On Windows it looks like Windows, on macOS like macOS, etc.
        // We wrap it in try-catch because it can throw multiple checked exceptions.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
            // If it fails, Swing falls back to its default "Metal" look — no big deal.
        }

        // LESSON: SwingUtilities.invokeLater() — Thread Safety in Swing
        // ================================================================
        // Swing is NOT thread-safe. All UI code must run on the "Event Dispatch Thread"
        // (EDT), a special thread Swing uses to handle user events and repaints.
        //
        // invokeLater() schedules our code to run on the EDT instead of the main thread.
        // Skipping this can cause subtle, hard-to-debug visual glitches or crashes.
        //
        // "TemperatureConverter::new" is a method reference — shorthand for:
        //   () -> new TemperatureConverter()
        // It creates a new window object, which triggers our constructor above.
        SwingUtilities.invokeLater(App::new);
    }
}