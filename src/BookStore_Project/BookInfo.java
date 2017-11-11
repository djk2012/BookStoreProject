package BookStore_Project;

/*Adonis Morales Assignment 3
  11/18/13
  Description: Reads a text file and stores its values into arrays.
 */
import java.util.Scanner;
import java.io.*;
import java.math.BigDecimal;

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
        final int SIZE = 7;

        Book[] books = new Book[SIZE];

        Book book;

        File myFile = new File("./src/BookStore_Project/BookPrices.txt");
        //File myFile = new File("C:\\Users\\ding\\Documents\\NetBeansProjects\\BookStore_Project\\src\\BookStore_Project\\BookPrices.txt");
        Scanner inputFile = new Scanner(myFile);

        while (inputFile.hasNext() && count < books.length) {
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
            books[count] = book;
            book = null;
            count++;
        }
        inputFile.close();

        return books;

    }

}
