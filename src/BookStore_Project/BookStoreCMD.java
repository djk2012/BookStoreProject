/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookStore_Project;

import BookStore_Project.BookInfo;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author ding
 */
public class BookStoreCMD {

    static BookInfo bi;
    static BookListImp bimp;
    static Book[] books;
    static int[] stock;

    public BookStoreCMD() {
        bi = new BookInfo();

    }

    //BookInfo object
    private static Book[] bookList;

    public static void initBookInfo() throws FileNotFoundException {
        books = bi.getBookInfo();
        System.out.println("sfdfsaff" + books.length);

    }

    public static void showBookInfo() {

        //System.out.println("    Book Number    |             Book Tittle               |               Book Auhtor               |             Book  Price               |");
        for (int i = 0; i < books.length; i++) {
            System.out.println("----------------------------------------------");
            System.out.println(" Book number    " + i);
            System.out.println(" Book Tittle    " + books[i].getTitle());
            System.out.println(" Book Author    " + books[i].getAuthor());
            System.out.println(" Book Price    " + books[i].getPrice());
            System.out.println(" Book Stock    " + books[i].getStock());

        }

    }

    public static void showSearchBookInfo(Book[] books) {

        //System.out.println("    Book Number    |             Book Tittle               |               Book Auhtor               |             Book  Price               |");
        for (int i = 0; i < books.length; i++) {
            System.out.println("----------------------------------------------");
            System.out.println(" Book number    " + i);
            System.out.println(" Book Tittle    " + books[i].getTitle());
            System.out.println(" Book Author    " + books[i].getAuthor());
            System.out.println(" Book Price    " + books[i].getPrice());
            System.out.println(" Book Stock    " + books[i].getStock());

        }

    }

    public static void printMenu() {
        System.out.println("****************************  Book Store ***************************");
        System.out.println();
        System.out.println("1. Show books");
        System.out.println("2. Add book stock");
        System.out.println("3. Remove book stock");
        System.out.println("4. Buy book");
        System.out.println("5. Search ");
        System.out.println("6. Add new book");
        System.out.println("7. Exit");
        System.out.println();
        System.out.println("****************************  Book Store ***************************");
    }

    public static void addBook(Book book, int quantity) {
        System.out.println("----------0----");
        bimp = new BookListImp(books);

        System.out.println("---------1-----" + book.getTitle());
        boolean add = bimp.add(book, quantity);
        System.out.println("----------2----");

        if (add == true) {
            System.out.println("The books have been sucessfully added!");
        } else {
            System.out.println("Sorry,The books have not been added!");

        }

    }

    public static void removeBook(Book book, int quantity) {
        bimp = new BookListImp(books);
        boolean add = bimp.remove(book, quantity);
        if (add == true) {
            System.out.println("The books have been sucessfully removed!");
        } else {
            System.out.println("Sorry,The books have not been removed!");
        }

    }

    public static int[] buy(Book... books) {
        bimp = new BookListImp(BookStoreCMD.books);
        int[] status = bimp.buy(books);
        return status;
    }

    public static Book[] search(String searchstring) {
        bimp = new BookListImp(books);
        Book[] books = bimp.list(searchstring);
        return books;
    }

    public static void main(String[] args) throws Exception {
        ArrayList<Book> bookal = new ArrayList();
        double amount = 0.0;
        double sum = 0.0;
        int count = 1;
        List list = new ArrayList();
        Scanner scan = new Scanner(System.in);
        initBookInfo();

        int menu = 1;
        boolean start = true;
        while (start) {
            printMenu();
            System.out.println("Please enter your choice: ");
            menu = scan.nextInt();

            switch (menu) {

                case 1:
                    showBookInfo();
                    break;
                case 2:
                    showBookInfo();
                    System.out.println("Please enter number of book you want to add");
                    int add_number = scan.nextInt();
                    System.out.println("Please enter quantity of book you want to add");
                    int add_quantity = scan.nextInt();

                    if (add_quantity > 0) {
                        System.out.println(books.length + "  " + add_number + " " + add_quantity);
                        //  System.out.println(bookList[add_number].getTitle());
                        addBook(books[add_number], add_quantity);
                        //System.out.println("before"+books.length+" "+add_number+" "+add_quantity);
                        bi.updateBookStock(books);
                    } else {
                        System.out.println("Sorry, the quantity must be bigger than 0");
                        System.out.println("The quantity has not been added into stock");
                    }
                    break;
                case 3:
                    showBookInfo();
                    System.out.println("Please enter number of book you want to remove");
                    int remove_number = scan.nextInt();
                    System.out.println("Please enter quantity of book you want to remove");
                    int remove_quantity = scan.nextInt();
                    if (remove_quantity > 0) {
                        removeBook(books[remove_number], remove_quantity);
                        bi.updateBookStock(books);
                    } else {
                        System.out.println("Sorry, the quantity must be bigger than 0");
                        System.out.println("The quantity has not been added into stock");
                    }
                    break;
                case 4:
                    showBookInfo();

                    System.out.println("Please enter number of book you want to buy");
                    int book_number = scan.nextInt();
                    System.out.println("Please enter quantity of book you want to buy");
                    int book_quantity = scan.nextInt();
                    if (books[book_number].getStock() >= book_quantity && book_quantity > 0) {

                        for (int i = 0; i < book_quantity; i++) {
                            bookal.add(books[book_number]);

                        }

                        Book[] buy_books = new Book[bookal.size()];
                        for (int i = 0; i < bookal.size(); i++) {

                            buy_books[i] = (Book) bookal.get(i);
                        }
                        buy(buy_books);
                        bookal.clear();

                    } else {
                        System.out.println("Sorry,out of the stock about this book");

                    }

                    break;
                case 5:
                    showBookInfo();
                    System.out.println("Please enter tittle or author of book you want to search");
                    String searchstring = scan.next();
                    Book[] search_book = search(searchstring);
                    System.out.println("search number" + search_book.length);
                    showSearchBookInfo(search_book);

                    break;
                case 6:
                    System.out.println("Please enter new book's tittle");
                    String tittle = scan.next();
                    System.out.println("Please enter new book's auther");
                    String author = scan.next();
                    System.out.println("Please enter new book's price");
                    String input = scan.next();
                    BigDecimal price = new BigDecimal(input);
                    System.out.println("Please enter new book's stock");
                    int stock = scan.nextInt();
                    Book newbook = new Book();
                    newbook.setTitle(tittle);
                    newbook.setAuthor(author);
                    newbook.setPrice(price);
                    newbook.setStock(stock);

                    Book[] temp = books;
                    books = new Book[temp.length + 1];
                    for(int i=0;i<temp.length;i++){
                        books[i]=temp[i];
                    }
                    books[books.length - 1] = newbook;
                    bi.updateBookStock(books);

                    break;
                case 7:
                    System.out.println();
                    start = false;
                    break;
                default:
                    System.out.println();
                    break;

            }
        }

    }

}
