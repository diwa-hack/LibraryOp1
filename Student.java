public class Student {
    String name;
    int id_no;
    String Stream;
    String book1, book2;
    int book_no, issuedbook;

    public Student(String name, int id_no, String Stream) {
        this.name = name;
        this.id_no = id_no;
        this.Stream = Stream;
        this.book1 = null;
        this.book2 = null;
        this.book_no = 0;
        this.issuedbook = 0;
    }
}
