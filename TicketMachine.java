
public class TicketMachine
{
    // ---------------------------------------------------------
    // Fields
    // ---------------------------------------------------------

    private int price;
    private int balance;
    private int total;

    // ---------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------

    /**
     * Default constructor.
     * Creates a machine with a ticket price of 1000 cents.
     */
    public TicketMachine()
    {
        price = 1000;
        balance = 0;
        total = 0;
    }

    /**
     * Creates a machine with the supplied ticket price.
     *
     * @param ticketCost price of a ticket in cents
     */
    public TicketMachine(int ticketCost)
    {
        if (ticketCost > 0)
        {
            price = ticketCost;
        }
        else
        {
            price = 1000;
        }

        balance = 0;
        total = 0;
    }

    // ---------------------------------------------------------
    // Accessor Methods
    // ---------------------------------------------------------

    /**
     * Returns the price of a ticket.
     *
     * @return ticket price in cents
     */
    public int getPrice()
    {
        return price;
    }

    /**
     * Returns the current balance.
     *
     * @return inserted money in cents
     */
    public int getBalance()
    {
        return balance;
    }

    /**
     * Returns the total money collected by the machine.
     *
     * @return total collected amount in cents
     */
    public int getTotal()
    {
        return total;
    }

    // ---------------------------------------------------------
    // Mutator Methods
    // ---------------------------------------------------------

    /**
     * Inserts money into the machine.
     *
     * Only positive amounts are accepted.
     *
     * @param amount amount of money in cents
     */
    public void insertMoney(int amount)
    {
        if (amount > 0)
        {
            balance = balance + amount;
        }
        else
        {
            System.out.println(
                "Use a positive amount rather than: " + amount
            );
        }
    }

    /**
     * Changes the ticket price.
     *
     * @param newPrice new ticket price in cents
     */
    public void setPrice(int newPrice)
    {
        if (newPrice > 0)
        {
            price = newPrice;
        }
        else
        {
            System.out.println("Ticket price must be greater than zero.");
        }
    }

    /**
     * Removes all money from the machine's total.
     */
    public void empty()
    {
        total = 0;
    }

    /**
     * Empties the machine and returns the amount removed.
     *
     * @return amount that was stored in total
     */
    public int emptyMachine()
    {
        int oldTotal = total;
        total = 0;
        return oldTotal;
    }

    // ---------------------------------------------------------
    // Ticket Operations
    // ---------------------------------------------------------

    /**
     * Prints a ticket if sufficient money has been inserted.
     *
     * If more money than required has been inserted, the
     * remaining balance is retained in the machine.
     */
    public void printTicket()
    {
        int amountLeftToPay = price - balance;

        if (amountLeftToPay <= 0)
        {
            printTicketDesign();

            // Add only the ticket price to the collected total.
            total = total + price;

            // Keep any remaining balance.
            balance = balance - price;
        }
        else
        {
            System.out.println(
                "You must insert at least: "
                + amountLeftToPay
                + " more cents."
            );
        }
    }

    /**
     * Prints the ticket design in the BlueJ terminal.
     */
    private void printTicketDesign()
    {
        System.out.println("############################");
        System.out.println("#      THE BLUEJ LINE      #");
        System.out.println("#         TICKET           #");
        System.out.println("# Price: " + price + " cents");
        System.out.println("############################");
        System.out.println();
    }

    /**
     * Displays a message asking the customer to insert money.
     */
    public void prompt()
    {
        System.out.println(
            "Please insert the correct amount of money."
        );
    }

    /**
     * Displays the current ticket price.
     */
    public void showPrice()
    {
        System.out.println(
            "The price of a ticket is " + price + " cents."
        );
    }

    /**
     * Returns a textual description of the current machine state.
     *
     * This is useful for the GUI and testing.
     *
     * @return machine status
     */
    public String getStatus()
    {
        return "Price: " + price
            + " cents | Balance: " + balance
            + " cents | Total: " + total + " cents";
    }
}