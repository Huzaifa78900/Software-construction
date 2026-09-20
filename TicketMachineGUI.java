
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * TicketMachineGUI
 *
 * A professional graphical interface for the TicketMachine class.
 *
 * Features:
 * - Modern dark interface
 * - Gradient header
 * - Animated ticket printing
 * - Payment progress indicator
 * - Ticket preview
 * - Price selection
 * - Custom amount insertion
 * - Balance and total cards
 * - Machine emptying
 * - Input validation
 * - Hover effects
 */
public class TicketMachineGUI extends JFrame
{
    // ---------------------------------------------------------
    // Machine
    // ---------------------------------------------------------

    private TicketMachine machine;

    // ---------------------------------------------------------
    // GUI Components
    // ---------------------------------------------------------

    private JLabel balanceLabel;
    private JLabel totalLabel;
    private JLabel priceLabel;
    private JLabel statusLabel;
    private JLabel animationLabel;

    private JTextField amountField;
    private JTextArea ticketArea;

    private JProgressBar paymentProgress;

    private JComboBox<String> priceSelector;

    private JButton insertButton;
    private JButton printButton;
    private JButton emptyButton;
    private JButton showPriceButton;

    // ---------------------------------------------------------
    // Colors
    // ---------------------------------------------------------

    private final Color BACKGROUND = new Color(15, 23, 42);
    private final Color CARD = new Color(26, 62, 94);
    private final Color CARD_LIGHT = new Color(255, 255, 255);

    private final Color PRIMARY = new Color(59, 130, 246);
    private final Color SUCCESS = new Color(34, 197, 94);
    private final Color WARNING = new Color(245, 158, 11);
    private final Color DANGER = new Color(239, 68, 68);

    private final Color TEXT = new Color(0, 0, 0);
    private final Color MUTED = new Color(148, 163, 184);

    // ---------------------------------------------------------
    // Constructor
    // ---------------------------------------------------------

    public TicketMachineGUI()
    {
        machine = new TicketMachine(500);

        configureWindow();
        createInterface();

        updateDashboard();

        setVisible(true);
    }

    // ---------------------------------------------------------
    // Window Configuration
    // ---------------------------------------------------------

    private void configureWindow()
    {
        setTitle("BlueJ Smart Ticket Machine");
        setSize(1100, 720);
        setMinimumSize(new Dimension(950, 620));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        try
        {
            UIManager.setLookAndFeel(
                UIManager.getSystemLookAndFeelClassName()
            );
        }
        catch (Exception e)
        {
            // Use default Swing appearance.
        }
    }

    // ---------------------------------------------------------
    // Main Interface
    // ---------------------------------------------------------

    private void createInterface()
    {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BACKGROUND);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createMainPanel(), BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);

        setContentPane(root);
    }

    // ---------------------------------------------------------
    // Header
    // ---------------------------------------------------------

    private JPanel createHeader()
    {
        GradientPanel header = new GradientPanel(
            new Color(30, 64, 175),
            new Color(79, 70, 229)
        );

        header.setLayout(new BorderLayout());
        header.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel title = new JLabel("HUZAIFA SMART TICKET MACHINE");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
            "PROFESSOR ENGR SAAD"
        );
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        subtitle.setForeground(new Color(219, 234, 254));

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(
            textPanel,
            BoxLayout.Y_AXIS
        ));

        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitle);

        header.add(textPanel, BorderLayout.WEST);

        animationLabel = new JLabel("● SYSTEM ONLINE");
        animationLabel.setFont(
            new Font("SansSerif", Font.BOLD, 13)
        );
        animationLabel.setForeground(
            new Color(187, 247, 208)
        );

        header.add(animationLabel, BorderLayout.EAST);

        startPulseAnimation();

        return header;
    }

    // ---------------------------------------------------------
    // Main Panel
    // ---------------------------------------------------------

    private JPanel createMainPanel()
    {
        JPanel main = new JPanel(new BorderLayout(20, 20));
        main.setBackground(BACKGROUND);
        main.setBorder(new EmptyBorder(25, 30, 25, 30));

        main.add(createDashboard(), BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 20, 0));
        center.setOpaque(false);

        center.add(createControlCard());
        center.add(createTicketCard());

        main.add(center, BorderLayout.CENTER);

        return main;
    }

    // ---------------------------------------------------------
    // Dashboard
    // ---------------------------------------------------------

    private JPanel createDashboard()
    {
        JPanel dashboard = new JPanel(
            new GridLayout(1, 3, 15, 0)
        );
        dashboard.setOpaque(false);

        dashboard.add(
            createInfoCard(
                "TICKET PRICE",
                "0 cents",
                PRIMARY
            )
        );

        dashboard.add(
            createInfoCard(
                "CURRENT BALANCE",
                "0 cents",
                SUCCESS
            )
        );

        dashboard.add(
            createInfoCard(
                "TOTAL COLLECTED",
                "0 cents",
                WARNING
            )
        );

        return dashboard;
    }

    private JPanel createInfoCard(
        String title,
        String value,
        Color accent
    )
    {
        RoundedPanel card = new RoundedPanel(
            CARD,
            18
        );

        card.setLayout(
            new BoxLayout(card, BoxLayout.Y_AXIS)
        );

        card.setBorder(
            new EmptyBorder(18, 20, 18, 20)
        );

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(
            new Font("SansSerif", Font.BOLD, 12)
        );
        titleLabel.setForeground(MUTED);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(
            new Font("SansSerif", Font.BOLD, 25)
        );
        valueLabel.setForeground(accent);

        if (title.equals("TICKET PRICE"))
        {
            priceLabel = valueLabel;
        }
        else if (title.equals("CURRENT BALANCE"))
        {
            balanceLabel = valueLabel;
        }
        else
        {
            totalLabel = valueLabel;
        }

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(7));
        card.add(valueLabel);

        return card;
    }

    // ---------------------------------------------------------
    // Control Card
    // ---------------------------------------------------------

    private JPanel createControlCard()
    {
        RoundedPanel card = new RoundedPanel(
            CARD,
            20
        );

        card.setLayout(new BorderLayout());
        card.setBorder(
            new EmptyBorder(22, 22, 22, 22)
        );

        JLabel heading = new JLabel("Payment Console");
        heading.setFont(
            new Font("SansSerif", Font.BOLD, 21)
        );
        heading.setForeground(TEXT);

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        top.add(heading, BorderLayout.WEST);

        card.add(top, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setOpaque(false);
        content.setLayout(
            new BoxLayout(content, BoxLayout.Y_AXIS)
        );

        // Price selector
        JLabel priceText = createLabel("SELECT TICKET PRICE");

        priceSelector = new JComboBox<>(
            new String[]
            {
                "300 cents",
                "500 cents",
                "700 cents",
                "1000 cents",
                "1500 cents"
            }
        );

        priceSelector.setSelectedIndex(1);

        styleComboBox(priceSelector);

        priceSelector.addActionListener(e ->
        {
            changeTicketPrice();
        });

        content.add(priceText);
        content.add(Box.createVerticalStrut(7));
        content.add(priceSelector);

        content.add(Box.createVerticalStrut(20));

        // Amount field
        JLabel amountText = createLabel(
            "AMOUNT TO INSERT (CENTS)"
        );

        amountField = new JTextField();
        styleTextField(amountField);

        amountField.addActionListener(e ->
        {
            insertMoney();
        });

        content.add(amountText);
        content.add(Box.createVerticalStrut(7));
        content.add(amountField);

        content.add(Box.createVerticalStrut(15));

        // Progress
        paymentProgress = new JProgressBar(
            0,
            machine.getPrice()
        );

        paymentProgress.setStringPainted(true);
        paymentProgress.setString("0% paid");

        paymentProgress.setForeground(SUCCESS);
        paymentProgress.setBackground(CARD_LIGHT);

        content.add(paymentProgress);

        content.add(Box.createVerticalStrut(18));

        // Buttons
        JPanel buttons = new JPanel(
            new GridLayout(2, 2, 10, 10)
        );
        buttons.setOpaque(false);

        insertButton = createButton(
            "INSERT MONEY",
            PRIMARY
        );

        printButton = createButton(
            "PRINT TICKET",
            SUCCESS
        );

        showPriceButton = createButton(
            "SHOW PRICE",
            WARNING
        );

        emptyButton = createButton(
            "EMPTY MACHINE",
            DANGER
        );

        insertButton.addActionListener(
            e -> insertMoney()
        );

        printButton.addActionListener(
            e -> printTicket()
        );

        showPriceButton.addActionListener(
            e -> showPrice()
        );

        emptyButton.addActionListener(
            e -> emptyMachine()
        );

        buttons.add(insertButton);
        buttons.add(printButton);
        buttons.add(showPriceButton);
        buttons.add(emptyButton);

        content.add(buttons);

        content.add(Box.createVerticalStrut(18));

        statusLabel = new JLabel(
            "Welcome! Please select a ticket and insert money."
        );

        statusLabel.setFont(
            new Font("SansSerif", Font.PLAIN, 13)
        );

        statusLabel.setForeground(MUTED);

        content.add(statusLabel);

        card.add(content, BorderLayout.CENTER);

        return card;
    }

    // ---------------------------------------------------------
    // Ticket Card
    // ---------------------------------------------------------

    private JPanel createTicketCard()
    {
        RoundedPanel card = new RoundedPanel(
            CARD,
            20
        );

        card.setLayout(new BorderLayout());
        card.setBorder(
            new EmptyBorder(22, 22, 22, 22)
        );

        JLabel heading = new JLabel(
            "Ticket Preview"
        );

        heading.setFont(
            new Font("SansSerif", Font.BOLD, 21)
        );

        heading.setForeground(TEXT);

        card.add(heading, BorderLayout.NORTH);

        ticketArea = new JTextArea();

        ticketArea.setEditable(false);
        ticketArea.setFont(
            new Font("Monospaced", Font.BOLD, 15)
        );

        ticketArea.setForeground(
            new Color(30, 41, 59)
        );

        ticketArea.setBackground(
            new Color(248, 250, 252)
        );

        ticketArea.setBorder(
            new EmptyBorder(25, 20, 25, 20)
        );

        ticketArea.setText(
            "\n\n"
            + "       BLUEJ LINE\n"
            + "     SMART TICKET\n"
            + "\n"
            + "       No ticket\n"
            + "       printed yet.\n"
            + "\n"
            + "   Insert the required\n"
            + "       amount.\n"
            + "\n"
        );

        JScrollPane scrollPane =
            new JScrollPane(ticketArea);

        scrollPane.setBorder(null);

        card.add(
            scrollPane,
            BorderLayout.CENTER
        );

        return card;
    }

    // ---------------------------------------------------------
    // Footer
    // ---------------------------------------------------------

    private JPanel createFooter()
    {
        JPanel footer = new JPanel(
            new BorderLayout()
        );

        footer.setBackground(
            new Color(2, 6, 23)
        );

        footer.setBorder(
            new EmptyBorder(10, 25, 10, 25)
        );

        JLabel left = new JLabel(
            "TicketMachine "
        );

        left.setForeground(MUTED);
        left.setFont(
            new Font("SansSerif", Font.PLAIN, 12)
        );

        JLabel right = new JLabel(
            "HUZI Edition"
        );

        right.setForeground(
            new Color(100, 116, 139)
        );

        right.setFont(
            new Font("SansSerif", Font.PLAIN, 12)
        );

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);

        return footer;
    }

    // ---------------------------------------------------------
    // Insert Money
    // ---------------------------------------------------------

    private void insertMoney()
    {
        String input = amountField.getText().trim();

        if (input.isEmpty())
        {
            showStatus(
                "Please enter an amount first.",
                DANGER
            );
            shake(amountField);
            return;
        }

        try
        {
            int amount = Integer.parseInt(input);

            if (amount <= 0)
            {
                showStatus(
                    "Enter a positive amount of money.",
                    DANGER
                );

                shake(amountField);
                return;
            }

            machine.insertMoney(amount);

            amountField.setText("");

            updateDashboard();

            showStatus(
                amount + " cents inserted successfully.",
                SUCCESS
            );

            animateProgress();

        }
        catch (NumberFormatException e)
        {
            showStatus(
                "Invalid amount. Please enter whole cents.",
                DANGER
            );

            shake(amountField);
        }
    }

    // ---------------------------------------------------------
    // Print Ticket
    // ---------------------------------------------------------

    private void printTicket()
    {
        int balanceBefore = machine.getBalance();
        int price = machine.getPrice();

        if (balanceBefore < price)
        {
            int remaining = price - balanceBefore;

            showStatus(
                "Not enough money. "
                + remaining
                + " cents still required.",
                WARNING
            );

            animateProgress();
            return;
        }

        printButton.setEnabled(false);
        insertButton.setEnabled(false);

        showStatus(
            "Preparing your ticket...",
            PRIMARY
        );

        animateTicketPrinting(
            () ->
            {
                machine.printTicket();

                createTicketPreview(
                    price,
                    balanceBefore
                );

                updateDashboard();

                showStatus(
                    "Ticket printed successfully!",
                    SUCCESS
                );

                printButton.setEnabled(true);
                insertButton.setEnabled(true);
            }
        );
    }

    // ---------------------------------------------------------
    // Animated Ticket Printing
    // ---------------------------------------------------------

    private void animateTicketPrinting(
        Runnable onComplete
    )
    {
        final String[] frames =
        {
            "Printing.",
            "Printing..",
            "Printing...",
            "Printing....",
            "Printing....."
        };

        final int[] index = {0};

        Timer timer = new Timer(
            180,
            null
        );

        timer.addActionListener(e ->
        {
            showStatus(
                frames[index[0] % frames.length],
                PRIMARY
            );

            index[0]++;

            if (index[0] >= 10)
            {
                timer.stop();

                onComplete.run();
            }
        });

        timer.start();
    }

    // ---------------------------------------------------------
    // Ticket Preview
    // ---------------------------------------------------------

    private void createTicketPreview(
        int price,
        int paid
    )
    {
        int remainingBalance =
            machine.getBalance();

        String ticketNumber =
            generateTicketNumber();

        String text =
            "\n"
            + "       ╔══════════════════╗\n"
            + "       ║   BLUEJ LINE     ║\n"
            + "       ║   SMART TICKET   ║\n"
            + "       ╠══════════════════╣\n"
            + "       ║ Ticket: "
            + ticketNumber
            + "\n"
            + "       ║ Price: "
            + price
            + " cents\n"
            + "       ║ Paid:  "
            + paid
            + " cents\n"
            + "       ║ Balance: "
            + remainingBalance
            + " cents\n"
            + "       ║ Status: VALID\n"
            + "       ╚══════════════════╝\n"
            + "\n"
            + "          Thank you!\n";

        ticketArea.setText(text);
    }

    private String generateTicketNumber()
    {
        Random random = new Random();

        int number =
            100000 + random.nextInt(900000);

        return String.valueOf(number);
    }

    // ---------------------------------------------------------
    // Price
    // ---------------------------------------------------------

    private void changeTicketPrice()
    {
        String selected =
            (String) priceSelector.getSelectedItem();

        if (selected == null)
        {
            return;
        }

        String numeric =
            selected.replace(" cents", "");

        int newPrice =
            Integer.parseInt(numeric);

        machine.setPrice(newPrice);

        paymentProgress.setMaximum(
            newPrice
        );

        updateDashboard();

        showStatus(
            "Ticket price changed to "
            + newPrice
            + " cents.",
            PRIMARY
        );
    }

    private void showPrice()
    {
        showStatus(
            "The price of a ticket is "
            + machine.getPrice()
            + " cents.",
            WARNING
        );
    }

    // ---------------------------------------------------------
    // Empty Machine
    // ---------------------------------------------------------

    private void emptyMachine()
    {
        int removed =
            machine.emptyMachine();

        updateDashboard();

        paymentProgress.setValue(0);
        paymentProgress.setString("0% paid");

        showStatus(
            "Machine emptied. "
            + removed
            + " cents removed from total collection.",
            WARNING
        );
    }

    // ---------------------------------------------------------
    // Dashboard
    // ---------------------------------------------------------

    private void updateDashboard()
    {
        int price = machine.getPrice();
        int balance = machine.getBalance();
        int total = machine.getTotal();

        priceLabel.setText(
            price + " cents"
        );

        balanceLabel.setText(
            balance + " cents"
        );

        totalLabel.setText(
            total + " cents"
        );

        paymentProgress.setMaximum(price);

        int progress =
            Math.min(balance, price);

        paymentProgress.setValue(progress);

        int percentage =
            price == 0
            ? 0
            : (progress * 100 / price);

        paymentProgress.setString(
            percentage + "% paid"
        );
    }

    private void animateProgress()
    {
        final int target =
            Math.min(
                machine.getBalance(),
                machine.getPrice()
            );

        Timer timer = new Timer(
            12,
            null
        );

        final int[] current = {0};

        timer.addActionListener(e ->
        {
            if (current[0] < target)
            {
                current[0] += Math.max(
                    1,
                    target / 20
                );

                if (current[0] > target)
                {
                    current[0] = target;
                }

                paymentProgress.setValue(
                    current[0]
                );

                int percentage =
                    machine.getPrice() == 0
                    ? 0
                    : current[0] * 100
                    / machine.getPrice();

                paymentProgress.setString(
                    percentage + "% paid"
                );
            }
            else
            {
                timer.stop();
            }
        });

        timer.start();
    }

    // ---------------------------------------------------------
    // Status
    // ---------------------------------------------------------

    private void showStatus(
        String message,
        Color color
    )
    {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    // ---------------------------------------------------------
    // Styling Helpers
    // ---------------------------------------------------------

    private JLabel createLabel(String text)
    {
        JLabel label = new JLabel(text);

        label.setFont(
            new Font(
                "SansSerif",
                Font.BOLD,
                11
            )
        );

        label.setForeground(MUTED);

        return label;
    }

    private void styleTextField(
        JTextField field
    )
    {
        field.setFont(
            new Font(
                "SansSerif",
                Font.PLAIN,
                16
            )
        );

        field.setForeground(TEXT);
        field.setBackground(CARD_LIGHT);

        field.setCaretColor(Color.WHITE);

        field.setBorder(
            new EmptyBorder(
                10,
                12,
                10,
                12
            )
        );
    }

    private void styleComboBox(
        JComboBox<String> combo
    )
    {
        combo.setFont(
            new Font(
                "SansSerif",
                Font.PLAIN,
                15
            )
        );

        combo.setForeground(TEXT);
        combo.setBackground(CARD_LIGHT);

        combo.setBorder(
            new EmptyBorder(
                5,
                5,
                5,
                5
            )
        );
    }

    private JButton createButton(
        String text,
        Color color
    )
    {
        JButton button =
            new JButton(text);

        button.setFont(
            new Font(
                "SansSerif",
                Font.BOLD,
                12
            )
        );

        button.setForeground(Color.WHITE);
        button.setBackground(color);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setCursor(
            new Cursor(
                Cursor.HAND_CURSOR
            )
        );

        button.setBorder(
            new EmptyBorder(
                12,
                10,
                12,
                10
            )
        );

        final Color original = color;

        button.addMouseListener(
            new MouseAdapter()
            {
                public void mouseEntered(
                    MouseEvent e
                )
                {
                    button.setBackground(
                        original.brighter()
                    );
                }

                public void mouseExited(
                    MouseEvent e
                )
                {
                    button.setBackground(
                        original
                    );
                }
            }
        );

        return button;
    }

    // ---------------------------------------------------------
    // Pulse Animation
    // ---------------------------------------------------------

    private void startPulseAnimation()
    {
        Timer timer = new Timer(
            700,
            null
        );

        final boolean[] bright = {true};

        timer.addActionListener(e ->
        {
            if (bright[0])
            {
                animationLabel.setForeground(
                    new Color(134, 239, 172)
                );
            }
            else
            {
                animationLabel.setForeground(
                    new Color(74, 222, 128)
                );
            }

            bright[0] = !bright[0];
        });

        timer.start();
    }

    // ---------------------------------------------------------
    // Shake Animation
    // ---------------------------------------------------------

    private void shake(
        JComponent component
    )
    {
        Point original =
            component.getLocation();

        Timer timer = new Timer(
            30,
            null
        );

        final int[] count = {0};

        timer.addActionListener(e ->
        {
            if (count[0] < 8)
            {
                int offset =
                    (count[0] % 2 == 0)
                    ? 5
                    : -5;

                component.setLocation(
                    original.x + offset,
                    original.y
                );

                count[0]++;
            }
            else
            {
                component.setLocation(
                    original
                );

                timer.stop();
            }
        });

        timer.start();
    }

    // ---------------------------------------------------------
    // GUI Main Method
    // ---------------------------------------------------------

    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(
            () ->
            {
                new TicketMachineGUI();
            }
        );
    }

    // =========================================================
    // Custom Rounded Panel
    // =========================================================

    private static class RoundedPanel
        extends JPanel
    {
        private Color color;
        private int radius;

        public RoundedPanel(
            Color color,
            int radius
        )
        {
            this.color = color;
            this.radius = radius;

            setOpaque(false);
        }

        protected void paintComponent(
            Graphics g
        )
        {
            Graphics2D g2 =
                (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(color);

            g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                radius,
                radius
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }

    // =========================================================
    // Custom Gradient Panel
    // =========================================================

    private static class GradientPanel
        extends JPanel
    {
        private Color color1;
        private Color color2;

        public GradientPanel(
            Color color1,
            Color color2
        )
        {
            this.color1 = color1;
            this.color2 = color2;

            setOpaque(false);
        }

        protected void paintComponent(
            Graphics g
        )
        {
            Graphics2D g2 =
                (Graphics2D) g.create();

            g2.setRenderingHint(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY
            );

            GradientPaint gradient =
                new GradientPaint(
                    0,
                    0,
                    color1,
                    getWidth(),
                    getHeight(),
                    color2
                );

            g2.setPaint(gradient);

            g2.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
            );

            g2.dispose();

            super.paintComponent(g);
        }
    }
}