import javax.swing.SwingUtilities;

/**
 * Main class used to launch the Ticket Machine GUI.
 */
public class Main
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(
            new Runnable()
            {
                public void run()
                {
                    TicketMachineGUI gui =
                        new TicketMachineGUI();

                    gui.setVisible(true);
                }
            }
        );
    }
}
