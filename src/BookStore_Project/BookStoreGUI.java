package BookStore_Project;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.*;
import java.awt.event.*;

import java.util.Scanner;
import java.io.*;
import java.math.BigDecimal;

import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import BookStore_Project.Book;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ding
 */
public class BookStoreGUI extends JFrame {

    private static final int WINDOW_WIDTH = 800;		//Width of GUI frame
    private static final int WINDOW_LENGTH = 250;	//Length of GUI frame

    private JPanel booksPanel;			//Holds all the books
    private JPanel buttonsPanel;		//Has add/remove/checkout buttons
    private JPanel shoppingCartPanel;	//To hold books added by user
    private JPanel bannerPanel;			//Banner panel
    private JPanel searchButtonsPanel;	//Holds search/showall buttons

    private JList booksList;			//List with all book names
    private JList selectedList;			//List in shopping cart

    private JButton addSelected;		//Adds book to shopping cart
    private JButton removeSelected;		//Removes book from shopping cart
    private JButton checkOut;			//Adds all books prices + taxes
    private JButton searchButton;		//Searches for desired book
    private JButton showAllButton;		//shows all books available

    private BookInfo booksInfo = new BookInfo(); 			//BookInfo object
    private Book[] bookList = booksInfo.getBookInfo();	//Array that Holds all book names
    private BookListImp bookimp = new BookListImp(bookList);
    private int[] bookStock = bookimp.getBookStock();//Array that holds all book prices

    private JScrollPane scrollPane1;	//Holds available books list
    private JScrollPane scrollPane2;	//Holds selected book list

    private JLabel panelTitle;			//Panel title
    private JLabel cartTitle;			//Panel title
    private JLabel banner;				//Panel title

    private JTextField searchField;		//Allows user to input search

    private int element = -1;			// control variable
    private int selectedIndex;			//Index selected among available books
    private int index;					//Int that holds selected index.
    private int i, count;				//Control variables

    private BigDecimal total;				//Total of prices
    private BigDecimal bookPrice;		//Hold book prices

    private ListModel books;			//List model for book name list
    private ListModel shoppingCart;		//List model for shopping cart list
    private DefaultListModel shoppingCartDFM;

    private DecimalFormat money;		//Money format
    private Object selectedBookName; 	//Selected book

    private String searchResults;		//Hold search results
    private String notFound = " Title not found";	//Holds search results

    private boolean found = false;		//Boolean holds false if search results not found
    private BookListImp imp = new BookListImp(bookList);

    ;
    /*Constructor
	 * BookStoreGUI - Buuilds a GUI with multiple panels
     */
    public BookStoreGUI() throws IOException {
        //Title of GUI
        setTitle("Book Store Shopping Cart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(WINDOW_WIDTH, WINDOW_LENGTH);

        //BuildPanels
        buildBooksPanel();
        buildButtonsPanel();
        buildShoppingCartPanel();
        buildBannerPanel();
        buildSearchButtonsPanel();

        //Add panels to GUI frame
        add(bannerPanel, BorderLayout.NORTH);
        add(booksPanel, BorderLayout.WEST);
        add(buttonsPanel, BorderLayout.CENTER);
        add(shoppingCartPanel, BorderLayout.EAST);
        add(searchButtonsPanel, BorderLayout.SOUTH);

        //set visibility
        setVisible(true);
        pack();
    }

    //METHODS
    /*
	  *buildBooksPanel() - Builds panel containing a JList/ScrollPane
     */
    public void buildBooksPanel() {

        //Create panel to hold list of books
        booksPanel = new JPanel();

        //Set Panel layout
        booksPanel.setLayout(new BorderLayout());

        //Create the list
        // System.out.println(bookNames.length);
        booksList = new JList(DisplayBookInfo());

        //Set selection preferrence
        booksList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        //Visible book names
        booksList.setVisibleRowCount(5);

        //Create scroll pane containing book list
        scrollPane1 = new JScrollPane(booksList);
        scrollPane1.setPreferredSize(new Dimension(175, 50));

        //JLabel/Panel title
        panelTitle = new JLabel("Available Books");

        //Add JLabel and scroll to panel
        booksPanel.add(panelTitle, BorderLayout.NORTH);
        booksPanel.add(scrollPane1);
        bookPrice = new BigDecimal(0.00);
        total = new BigDecimal(0.00);
    }

    /*
	 * buildButtonsPanel - builds panel containing add/remove/checkout buttons
     */
    public void buildButtonsPanel() {
        //Create panel to hold buttons
        buttonsPanel = new JPanel();
        //Set Layout
        buttonsPanel.setLayout(new GridLayout(3, 1));
        //Create Buttons
        addSelected = new JButton("Add Selected Item");
        removeSelected = new JButton("Remove Selected Item");
        checkOut = new JButton("Check Out");

        //add Listeners
        addSelected.addActionListener(new AddButtonListener());
        removeSelected.addActionListener(new RemoveButtonListener());
        checkOut.addActionListener(new CheckOutButtonListener());

        //Add button panel to GUI
        buttonsPanel.add(addSelected);
        buttonsPanel.add(removeSelected);
        buttonsPanel.add(checkOut);
    }

    /*
	 * buildShoppingCartPanel builds panel containing JList/Scroll pane
     */
    public void buildShoppingCartPanel() {
        //Create panel
        shoppingCartPanel = new JPanel();

        //Set panel layout
        shoppingCartPanel.setLayout(new BorderLayout());

        //Create shopping cart list
        selectedList = new JList();

        //Set row visility
        selectedList.setVisibleRowCount(5);

        //Create scrollpane containin selected list items
        scrollPane2 = new JScrollPane(selectedList);

        scrollPane2.setPreferredSize(new Dimension(175, 50));
        //JLabel/Panel title
        cartTitle = new JLabel("Shopping Cart ");

        //Add JLabel and scroll pane to panel
        shoppingCartPanel.add(cartTitle, BorderLayout.NORTH);
        shoppingCartPanel.add(scrollPane2);
    }

    /*
	 * buildBannerPanel - builds panel containing banner for GUI
     */
    public void buildBannerPanel() {
        //Create panel
        bannerPanel = new JPanel();

        //Set Border layout
        setLayout(new BorderLayout());

        //String containing JLabel text
        String labelText = "<html><b COLOR=RED> Welcome</b>" + "<b><i COLOR=#006363> To </i></b>"
                + "<b><u COLOR=#BF3030>As</u><u COLOR=#8170D8>The</u><u COLOR=#00CC00>Pages</u><u COLOR=BLUE>Turn.com</u></b>";

        //create JLabel
        JLabel banner = new JLabel(labelText);
        banner.setFont(new Font("Serif", Font.BOLD, 28));

        //add banner to panel
        bannerPanel.add(banner);
    }

    public String[] DisplayBookInfo() {

        String[] bookTittles = new String[bookList.length];
        for (int i = 0; i < bookList.length; i++) {
            bookTittles[i] = "Number:" + i + ":" + "Tittle:" + bookList[i].getTitle() + "Author:" + bookList[i].getAuthor() + "Price:" + bookList[i].getPrice() + "Stock:" + bookList[i].getStock();
        }
        return bookTittles;
    }


    /*
	 * buildSearchButtonsPanel - builds panel containing search and showall buttons
     */
    public void buildSearchButtonsPanel() {
        //Create panel
        searchButtonsPanel = new JPanel();

        //Set Border layout
        searchButtonsPanel.setLayout(new GridLayout(1, 3, 5, 5));
        //Create buttons
        searchButton = new JButton("Search");
        showAllButton = new JButton("Show All");

        //Create text field
        searchField = new JTextField(15);

        //Add listeners
        searchButton.addActionListener(new SearchButtonListener());
        showAllButton.addActionListener(new ShowAllButtonListener());

        //Add buttons and text field to panel
        searchButtonsPanel.add(searchField);
        searchButtonsPanel.add(searchButton);
        searchButtonsPanel.add(showAllButton);
    }

    //ACTION LISTENERS
    /*
	 * AddButtonListener - adds selected item to shopping cart upon selection
     */
    public class AddButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            selectedIndex = booksList.getSelectedIndex();
            System.out.println("selectedIndex " + selectedIndex);
            selectedBookName = booksList.getSelectedValue();
            System.out.println("selectedBookName " + selectedBookName);

            books = booksList.getModel();

            if (bookStock[selectedIndex] - 1 >= 0) {
                shoppingCart = selectedList.getModel();
                // System.out.println("  ============================== "+shoppingCart.getElementAt(count));
                shoppingCartDFM = new DefaultListModel();

                for (count = 0; count < shoppingCart.getSize(); count++) {
                    shoppingCartDFM.addElement(shoppingCart.getElementAt(count));
                    // System.out.println("   shoppingCart.getElementAt(count) "+shoppingCart.getElementAt(count));
                }

                if (element == -1) {
                    //System.out.println("AAAAAAAAAAAAAAAAAA"+bookPrices[selectedIndex]);
                    //System.out.println("BBBBBBBBBBBBB"+bookPrice);
                    bookStock[selectedIndex] = bookStock[selectedIndex] - 1;
                    bookPrice = bookPrice.add(bookList[selectedIndex].getPrice());

                    // System.out.println("CCCCCCCCCCCCCCCCC"+bookPrice);
                } else {
                    bookStock[selectedIndex] = bookStock[selectedIndex] - 1;
                    bookPrice = bookPrice.add(bookList[element].getPrice());
                    // System.out.println(bookPrice);
                }
                shoppingCartDFM.addElement(selectedBookName);
                selectedList.setModel(shoppingCartDFM);

            } else {
                JOptionPane.showMessageDialog(null, "Sorry,The book you select is out of stock!");
            }

        }

    }

    /*
	 * RemoveButtonListener - Removes selected item from shopping cart upon selection
     */
    public class RemoveButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            index = selectedList.getSelectedIndex();
            ((DefaultListModel) selectedList.getModel()).remove(index);
            bookStock[selectedIndex] = bookStock[selectedIndex] + 1;

            if (element == -1) {
                if (bookPrice.compareTo(bookList[selectedIndex].getPrice()) >= 0) {
                    bookPrice = bookPrice.subtract(bookList[selectedIndex].getPrice());
                } else {
                    bookPrice = bookList[index].getPrice().subtract(bookPrice);
                }
            } else if (bookPrice.compareTo(bookList[element].getPrice()) >= 0) {
                bookPrice = bookPrice.subtract(bookList[element].getPrice());
            } else {
                bookPrice = bookList[index].getPrice().subtract(bookPrice);
            }

        }
    }

    /*
	 * CheckOutButtonListener - Calculates total and displays it to user
     */
    public class CheckOutButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            money = new DecimalFormat("#,##0.00");
            total = bookPrice;

            ArrayList<Book> bl = new ArrayList<Book>();
            Book book = null;
            for (int i = 0; i < shoppingCartDFM.getSize(); i++) {

                System.out.println(shoppingCartDFM.get(i).toString().split(":")[1]);
                String book_index = shoppingCartDFM.get(i).toString().split(":")[1];
                int index = Integer.parseInt(book_index);
                //int index=Integer.parseInt(shoppingCartDFM.get(0).toString().split("#")[0]);
                book = bookList[index];
                bl.add(book);

            }

            Book[] buy_book = new Book[bl.size()];
            for (int i = 0; i < bl.size(); i++) {
                buy_book[i] = bl.get(i);
            }
            int[] result = imp.buy(buy_book);
            try {
                BookInfo.updateBooks(imp.updatedbook);
            } catch (IOException ex) {
                Logger.getLogger(BookStoreGUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            JOptionPane.showMessageDialog(null, "Total: $" + (money.format(total)));
        }
    }

    /*
	 * SearchButtonListener - searches for user desired item
     */
    public class SearchButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            ArrayList<Integer> al = new ArrayList();
            index = 0;

            while (index < bookList.length) {
                if (bookList[index].getAuthor().equals(searchField.getText()) || bookList[index].getTitle().equals(searchField.getText())) {
                    System.out.println("Price" + bookList[index].getPrice());
                    element = index;
                    al.add(index);
                }
                index++;
            }
            booksList.setModel(new DefaultListModel());
            for (int i = 0; i < al.size(); i++) {
                if (element == -1) {
                    booksList.setModel(new DefaultListModel());
                    ((DefaultListModel) booksList.getModel()).addElement(notFound);
                } else {
                    searchResults = bookList[al.get(i)].getTitle() + "  " + bookList[al.get(i)].getAuthor() + "  " + bookList[al.get(i)].getPrice();

                    ((DefaultListModel) booksList.getModel()).addElement(searchResults);
                }
            }
        }
    }

    /*
	 * ShowsAllButtonListener - shows all available books
     */
    public class ShowAllButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            booksList.setModel(new DefaultListModel());

            for (i = 0; i < bookList.length; i++) {
                ((DefaultListModel) booksList.getModel()).addElement(bookList[i].getAuthor());

            }
        }
    }

    public static void main(String[] args) throws IOException {

        new BookStoreGUI();
    }
}
