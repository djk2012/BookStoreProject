# Bookstore

BookStoreCMD is command line version of BookStore (Back-end)

Function: 1. view books 2. add book into stock 3. remove book from stock 4. add new book 5 search book by inputing tittle or author 6. display total price of bought books 7. buy book

BookStoreGUI is GUI version of BookStore (Front-end)

Function: 1. view books 2. add book into basket 3. remove book from basket 4. buy book 5 search book by inputing tittle or author 6. display total price of bought books.

Book defines the all attributes about book

Attribute: 1. title 2. author 3. price 4. stock (*could be better as attribute in bookstore in the future)

BookInfo is responsible for extracting book's data and storing book's into bookstore from txt file

BookList is the interface which define the abstract methods which the bookstore should have

BookListImp is the sub-class which implements all abstract methods in BookList.

BookPrice is the txt file that stores book'tittle,book's author, book'price,book's stock for each book

Bookstoretest is the Junit class which test the functions about bookstore
