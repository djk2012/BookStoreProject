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
    public void check_Booklist_NotNull() throws FileNotFoundException {
        Book[] booklist = BookInfo.getBookInfo();
        Assert.assertNotNull(booklist);

    }
    
     @Test
    public void check_book() throws FileNotFoundException {
        Book[] booklist = BookInfo.getBookInfo();
        BookListImp bimp=new BookListImp(booklist);
        String searchtittle="Desired";
        Book [] result = bimp.list(searchtittle);
        String resulttittle=result[result.length-1].getTitle();
        Assert.assertEquals(searchtittle, resulttittle);
        

    }
    
}
