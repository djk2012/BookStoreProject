/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BookStore_Project;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

/**
 *
 * @author ding
 */
public class BookListImp implements BookList {

    private Book[] booklist;
    int OK = 0;
    int NOT_IN_STOCK = 1;
    int DOES_NOT_EXIST = 2;
    int EMPTY_IN_STOCK=-1;
    int DOES_NOT_FOUND=-2;

    public BookListImp(Book[] bl) {
        booklist = bl;
    }

    @Override
    public Book[] list(String searchString) {
        ArrayList<Book> resultlist = new ArrayList();
        for (int i = 0; i < this.booklist.length; i++) {
            if (booklist[i].getTitle().contains(searchString) || booklist[i].getAuthor().contains(searchString)) {
                resultlist.add(booklist[i]);
                System.out.println(booklist[i].getTitle()+"     "+booklist[i].getAuthor());
            }
        }
        
        Book [] list= new Book[resultlist.size()];
        for(int i=0;i<resultlist.size();i++){
            list[i]= (Book) resultlist.get(i);
        }
        return list;
    }

    @Override
    public boolean add(Book book, int quantity) {
        for (int i = 0; i < this.booklist.length; i++) {
            if (booklist[i].getTitle().equals(book.getTitle()) && booklist[i].getAuthor().equals(book.getAuthor()) && booklist[i].getPrice().equals(book.getPrice()) && quantity>=0) {
                booklist[i].setStock(booklist[i].getStock() + quantity);
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] buy(Book... books) {
        ArrayList<Integer> result = new ArrayList();
        BigDecimal price= new BigDecimal(0.00);
        for (int i = 0; i < books.length; i++) {
            if (check_book_exist(books[i]) == true) {
                if (check_book_in_stock(books[i]) >= 0) {
                    int buy_index = check_book_in_stock(books[i]);
                    booklist[buy_index].setStock(booklist[buy_index].getStock() - 1);
                    price=price.add(books[i].getPrice());
                    System.out.println("Tittle: "+booklist[buy_index].getTitle()+"  Author: "+booklist[buy_index].getAuthor()+"  Price: "+booklist[buy_index].getPrice());
                    result.add(OK);
                } else {
                    result.add(NOT_IN_STOCK);
                }
            } else {
                result.add(DOES_NOT_EXIST);
            }
        }
        
        System.out.println("The price of total books is:"+price);

        int[] reult_arr = new int[result.size()];
        for (int i = 0; i < result.size(); i++) {
            reult_arr[i] = result.get(i);
        }

        return reult_arr;
    }
    public boolean remove(Book book, int quantity) {
        for (int i = 0; i < this.booklist.length; i++) {
            if (booklist[i].getTitle().equals(book.getTitle()) && booklist[i].getAuthor().equals(book.getAuthor()) && booklist[i].getPrice().equals(book.getPrice()) && quantity<0 &&
                    booklist[i].getStock()+ quantity>=0) {
                booklist[i].setStock(booklist[i].getStock() - quantity);
                return true;
            }
        }
        return false;
    }
    public Boolean check_book_exist(Book book) {

        for (int i = 0; i < this.booklist.length; i++) {
            if (booklist[i].getTitle().equals(book.getTitle()) && booklist[i].getAuthor().equals(book.getAuthor()) && booklist[i].getPrice().equals(book.getPrice())) {
                return true;
            }
        }
        return false;
    }

    public int check_book_in_stock(Book book) {

        for (int i = 0; i < this.booklist.length; i++) {
            if (booklist[i].getTitle().equals(book.getTitle()) && booklist[i].getAuthor().equals(book.getAuthor()) && booklist[i].getPrice().equals(book.getPrice())) {
                if (booklist[i].getStock() - 1 >= 0) {
                    return i;
                } else {
                    return EMPTY_IN_STOCK;
                }
            }
        }
        return DOES_NOT_FOUND;
    }
     public int[] getBookStock() {

        int[] bookStock = new int[this.booklist.length];
        for (int i = 0; i < this.booklist.length; i++) {
            bookStock[i] = this.booklist[i].getStock();
        }
        return bookStock;
    }
     
     

}
