import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class BookApp {
    static BookRepository bookRepository = new BookRepository();

    static void menu() {
        System.out.println("1. Dodaj książkę");
        System.out.println("2. Zmien tytuł książki");
        System.out.println("3. Wyświetl wszystkie");
        System.out.println("4. Usuń książkę");
        System.out.println("5. Wyświetl książki z kategorii");
        System.out.println("6. Wyświetl książki autora/autorów");
        System.out.println("Wyjście - jeśli cokolwiek innego");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            menu();
            if (scanner.hasNext()) {
                int option = scanner.nextInt();
                switch (option) {
                    case 1: {
                        Book book = new Book();
                        System.out.println("Podaj tytuł:");
                        String title = scanner.nextLine();
                        System.out.println("Podaj autora:");
                        String author = scanner.nextLine();
                        System.out.println("Podaj datę publikacji w formacie RRRR-MM-DD:");
                        String date = scanner.nextLine();
                        book.setPublished(LocalDate.parse(date));
                        book.setTitle(title);
                        book.setAuthor(author);
                        //dopisać wczytanie pozostałych dancyh książki
                        bookRepository.addBook(book);
                    }
                    break;
                    case 2: {
                        System.out.println("Podaj id książki:");
                        long id = scanner.nextLong();
                        scanner.nextLine();
                        Book book = bookRepository.findById(id);
                        System.out.println("Podaj nowy tytuł:");
                        String title = scanner.nextLine();
                        book.setTitle(title);
                        bookRepository.updateBook(book);
                    }
                    break;
                    case 3: {
                        List<Book> books = bookRepository.findAllBooks();
                        System.out.println("Liczba książek: " + bookRepository.countBooks());
                        System.out.println("Suma cen książek: " + bookRepository.getPriceSumOfBooks());
                        books.forEach(System.out::println);
                    }
                    break;
                    case 4: {
                        System.out.println("Podaj id książki:");
                        long id = scanner.nextLong();
                        scanner.nextLine();
                        bookRepository.deleteBook(id);
                    }
                    break;
                    case 5:{
                        System.out.println(bookRepository.findCategoriesOfBooks());
                        System.out.println("Podaj kategorię:");
                        scanner.nextLine();
                        String category = scanner.nextLine();
                        if (category.equals("brak kategorii")){
                            bookRepository.findAllBooks().stream().filter(b -> b.getCategory() == null).forEach(System.out::println);
                        } else
                        bookRepository.findAllBooks().stream().filter(b -> category.equals(b.getCategory())).forEach(System.out::println);
                    }
                    case 6: {
                        System.out.println("Podaj autora: ");
                        scanner.nextLine();
                        String authorStr = scanner.nextLine();
                        System.out.println(bookRepository.findByAuthor(authorStr));
                    }
                    break;
                    default:
                        return;
                }
            }
        }
    }
}
