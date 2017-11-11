/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import BookStore_Project.Book;
import BookStore_Project.BookInfo;
import BookStore_Project.BookListImp;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ding
 */
public class Bookstoretest {

    @Test
    public void check_book() throws FileNotFoundException {
        Book[] booklist = BookInfo.getBookInfo();

        Assert.assertNotNull(booklist);

    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
