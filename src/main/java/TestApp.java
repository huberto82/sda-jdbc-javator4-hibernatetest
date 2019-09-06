import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class TestApp {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ksiegarnia");

    public static void addBook(Book book){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.close();
    }

    public static Book findById(long id){
        EntityManager em = emf.createEntityManager();
        return em.find(Book.class,id);
    }

    public static void updatePriceBook(BigDecimal price, long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, id);
        book.setPrice(price);
        em.getTransaction().commit();
    }

    public static void updateBook(Book book){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
    }

    public static void deleteBook(long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, id);
        em.remove(book);
        em.getTransaction().commit();
    }

    public static List<Book> findAllBooks(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("FROM Book").getResultList();
    }

    public static void main(String[] args) {
        System.out.println(findAllBooks());
        Book book;
        System.out.println(findById(5));
        updatePriceBook(new BigDecimal("400"),5);
        System.out.println(findById(5));
        System.out.println("Wyciągamy z bazy encję i zmieniamy");
        book = findById(6);
        System.out.println("PRZED UPDATE " + book);
        book.setTitle("NOWY TYTUL");
        book.setPageCount(45);
        updateBook(book);
        System.out.println("POD UPDATE" + findById(6));
        System.out.println("Tworzymy nowy obiekt encji");
        book = new Book();
        System.out.println("PRZED UPdATE " + book);
        book.setTitle("NOWA KSIAZKA");
        //dwie możliwosci dodania nowej encji
        //przez persist
        addBook(book); //to powoduje oprócz utrwalenia nadanie wartości polu id
        //albo merge
        //updateBook(book);//to powoduje utrwalenie kopii obiektu book, dlatego book nie ma nadanej wartości pola id
        System.out.println("PO UPDATE " + book);
        deleteBook(10L);
        System.out.println(findById(10L));
    }
}
