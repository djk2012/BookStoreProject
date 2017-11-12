package BookStore_Project;

import java.util.Scanner;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class BookInfo {

    public static void updateBooks(Book[] book) throws FileNotFoundException, IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("./src/BookStore_Project/BookPrices.txt")));
        String line;
        int count = 0;
        while (count < book.length) {
            out.println(book[count].getTitle() + ";" + book[count].getAuthor() + ";" + book[count].getPrice() + ";" + book[count].getStock());
            count++;
        }
        
        out.close();
    }

    public static Book[] getBookInfo() throws FileNotFoundException {
        int count = 0;
        ArrayList<Book> tempbooks = new ArrayList<Book>();
        Book book;
        File myFile = new File("./src/BookStore_Project/BookPrices.txt");
        Scanner inputFile = new Scanner(myFile);
        while (inputFile.hasNext()) {
            String str;
            str = inputFile.nextLine();
            book = new Book();
            String[] parts = str.split(";");
            book.setTitle(parts[0]);
            book.setAuthor(parts[1]);
            if (parts[2].contains(",")) {
                book.setPrice(new BigDecimal(parts[2].replaceAll(",", "")));
            } else {
                book.setPrice(new BigDecimal(parts[2]));
            }
            book.setStock(Integer.parseInt(parts[3]));
            tempbooks.add(book);
            book = null;
            count++;
        }
        inputFile.close();
        int SIZE = tempbooks.size();
        Book[] books = new Book[SIZE];
        for (int i = 0; i < tempbooks.size(); i++) {
            books[i] = tempbooks.get(i);
        }
        return books;
    }
}
