import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

public class BookRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ksiegarnia");

    public void addBook(Book book){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(book);
        em.getTransaction().commit();
        em.close();
    }

    public  Book findById(long id){
        EntityManager em = emf.createEntityManager();
        return em.find(Book.class,id);
    }

    public  void updatePriceBook(BigDecimal price, long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, id);
        book.setPrice(price);
        em.getTransaction().commit();
    }

    public  void updateBook(Book book){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(book);
        em.getTransaction().commit();
    }

    public  void deleteBook(long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Book book = em.find(Book.class, id);
        em.remove(book);
        em.getTransaction().commit();
    }

    public  List<Book> findAllBooks(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("FROM Book").getResultList();
    }

    public List<String> findCategoriesOfBooks(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT DISTINCT CASE WHEN b.category IS NULL THEN 'brak kategorii' ELSE b.category END FROM Book b").getResultList();
    }

    public List<Book> findByAuthor(String authorStr){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT b FROM Book b WHERE b.author like :authorStr")
                .setParameter("authorStr", authorStr)
                .getResultList();
    }

    public long countBooks(){
        EntityManager em = emf.createEntityManager();

        return em.createQuery("SELECT COUNT(b) FROM Book b", Long.class).getSingleResult();
    }

    public BigDecimal getPriceSumOfBooks(){
        EntityManager em = emf.createEntityManager();
        return em.createQuery("SELECT SUM(b.price) FROM Book b", BigDecimal.class).getSingleResult();
    }
}
