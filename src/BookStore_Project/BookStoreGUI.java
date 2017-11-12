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

    private static final int WINDOW_WIDTH = 1200;		
    private static final int WINDOW_LENGTH = 250;	

    private JPanel booksPanel;			
    private JPanel buttonsPanel;		
    private JPanel shoppingCartPanel;	
    private JPanel bannerPanel;			
    private JPanel searchButtonsPanel;	

    private JList booksList;			
    private JList selectedList;			

    private JButton addSelected;		
    private JButton removeSelected;		
    private JButton checkOut;			
    private JButton searchButton;		
    private JButton showAllButton;		

    private BookInfo booksInfo = new BookInfo(); 			
    private Book[] bookList = booksInfo.getBookInfo();	
    private BookListImp bookimp = new BookListImp(bookList);
    private int[] bookStock = bookimp.getBookStock();

    private JScrollPane scrollPane1;	
    private JScrollPane scrollPane2;	

    private JLabel panelTitle;			
    private JLabel cartTitle;			
    private JLabel banner;			

    private JTextField searchField;		

    private int element = -1;			
    private int selectedIndex;			
    private int index;					
    private int i, count;				

    private BigDecimal total;				
    private BigDecimal bookPrice;		

    private ListModel books;			
    private ListModel shoppingCart;		
    private DefaultListModel shoppingCartDFM;

    private DecimalFormat money;		
    private Object selectedBookName; 	

    private String searchResults;		
    private String notFound = " Title not found";	

    private boolean found = false;		
    private BookListImp imp = new BookListImp(bookList);

   
    public BookStoreGUI() throws IOException {

        setTitle("Book Store");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(WINDOW_WIDTH, WINDOW_LENGTH);
        buildBooksPanel();
        buildButtonsPanel();
        buildShoppingCartPanel();
        buildBannerPanel();
        buildSearchButtonsPanel();
        add(bannerPanel, BorderLayout.NORTH);
        add(booksPanel, BorderLayout.WEST);
        add(buttonsPanel, BorderLayout.CENTER);
        add(shoppingCartPanel, BorderLayout.EAST);
        add(searchButtonsPanel, BorderLayout.SOUTH);
        setVisible(true);
        pack();
    }

    public void buildBooksPanel() {

        booksPanel = new JPanel();
        booksPanel.setLayout(new BorderLayout());
        booksList = new JList(DisplayBookInfo());
        booksList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        booksList.setVisibleRowCount(5);
        scrollPane1 = new JScrollPane(booksList);
        scrollPane1.setPreferredSize(new Dimension(500, 350));
        panelTitle = new JLabel("Available Books");
        booksPanel.add(panelTitle, BorderLayout.NORTH);
        booksPanel.add(scrollPane1);
        bookPrice = new BigDecimal(0.00);
        total = new BigDecimal(0.00);
    }

    public void buildButtonsPanel() {

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3, 1));
        addSelected = new JButton("Add Book");
        removeSelected = new JButton("Remove Selected Book");
        checkOut = new JButton("Check Out");
        addSelected.addActionListener(new AddButtonListener());
        removeSelected.addActionListener(new RemoveButtonListener());
        checkOut.addActionListener(new CheckOutButtonListener());
        buttonsPanel.add(addSelected);
        buttonsPanel.add(removeSelected);
        buttonsPanel.add(checkOut);
    }

    public void buildShoppingCartPanel() {

        shoppingCartPanel = new JPanel();
        shoppingCartPanel.setLayout(new BorderLayout());
        selectedList = new JList();
        selectedList.setVisibleRowCount(5);
        scrollPane2 = new JScrollPane(selectedList);
        scrollPane2.setPreferredSize(new Dimension(500, 300));
        cartTitle = new JLabel("Shopping Cart ");
        shoppingCartPanel.add(cartTitle, BorderLayout.NORTH);
        shoppingCartPanel.add(scrollPane2);
    }

    public void buildBannerPanel() {
        
        bannerPanel = new JPanel();
        setLayout(new BorderLayout());
        String labelText = "<html><b COLOR=RED> Welcome to Bookstore !</b>";
        JLabel banner = new JLabel(labelText);
        banner.setFont(new Font("Serif", Font.BOLD, 28));
        bannerPanel.add(banner);
    }

    public String[] DisplayBookInfo() {

        String[] bookTittles = new String[bookList.length];
        for (int i = 0; i < bookList.length; i++) {
            bookTittles[i] = "Number:" + i + ":" + "Tittle:" + bookList[i].getTitle() + "Author:" + bookList[i].getAuthor() + "Price:" + bookList[i].getPrice() + "Stock:" + bookList[i].getStock();
        }
        return bookTittles;
    }

    public void buildSearchButtonsPanel() {

        searchButtonsPanel = new JPanel();
        searchButtonsPanel.setLayout(new GridLayout(1, 3, 5, 5));
        searchButton = new JButton("Search");
        showAllButton = new JButton("Show All");
        searchField = new JTextField(15);
        searchButton.addActionListener(new SearchButtonListener());
        showAllButton.addActionListener(new ShowAllButtonListener());
        searchButtonsPanel.add(searchField);
        searchButtonsPanel.add(searchButton);
        searchButtonsPanel.add(showAllButton);
    }

    public class AddButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            selectedIndex = booksList.getSelectedIndex();
            selectedBookName = booksList.getSelectedValue();
            books = booksList.getModel();
            if (bookStock[selectedIndex] - 1 >= 0) {
                shoppingCart = selectedList.getModel();
                shoppingCartDFM = new DefaultListModel();
                for (count = 0; count < shoppingCart.getSize(); count++) {
                    shoppingCartDFM.addElement(shoppingCart.getElementAt(count));
                }
                if (element == -1) {
                    bookStock[selectedIndex] = bookStock[selectedIndex] - 1;
                    bookPrice = bookPrice.add(bookList[selectedIndex].getPrice());
                } else {
                    bookStock[selectedIndex] = bookStock[selectedIndex] - 1;
                    bookPrice = bookPrice.add(bookList[element].getPrice());
                }
                shoppingCartDFM.addElement(selectedBookName);
                selectedList.setModel(shoppingCartDFM);
            } else {
                JOptionPane.showMessageDialog(null, "Sorry,The book you select is out of stock!");
            }
        }
    }

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


    public class SearchButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
             index = selectedList.getSelectedIndex();
            ((DefaultListModel) selectedList.getModel()).removeAllElements();
            ArrayList<Integer> al = new ArrayList();
            index = 0;
            while (index < bookList.length) {
                if (bookList[index].getAuthor().equals(searchField.getText()) || bookList[index].getTitle().equals(searchField.getText())) {
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
                    searchResults = "Number:" + i + ":" + "Tittle:" + bookList[al.get(i)].getTitle() + "Author:" + bookList[al.get(i)].getAuthor() + "Price:" + bookList[al.get(i)].getPrice() + "Stock:" + bookList[al.get(i)].getStock();                    
                    ((DefaultListModel) booksList.getModel()).addElement(searchResults);
                }
            }
        }
    }


    public class ShowAllButtonListener implements ActionListener {

       
           
        public void actionPerformed(ActionEvent e) {

            //selectedList.removeAll();
          //  index = selectedList.getSelectedIndex();
          // (
          bookPrice = new BigDecimal(0.00);
          total=new BigDecimal(0.00);
            booksList.setModel(new DefaultListModel());
            for (i = 0; i < bookList.length; i++) {
                ((DefaultListModel) booksList.getModel()).addElement("Number:" + i + ":" + "Tittle:" + bookList[i].getTitle() + "Author:" + bookList[i].getAuthor() + "Price:" + bookList[i].getPrice() + "Stock:" + bookList[i].getStock());
            }
        }
    }

    public static void main(String[] args) throws IOException {

        new BookStoreGUI();
    }
}
