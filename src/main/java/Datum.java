public class Datum {
    public int id;
    public String email;
    public String first_name;
    public String last_name;
    public String avatar;


    public String name;
    public int year;
    public String color;
    public String pantone_value;

    public Datum(int id, String email, String first_name, String last_name, String avatar, String name, int year, String color, String pantone_value) {
        this.id = id;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
        this.name = name;
        this.year = year;
        this.color = color;
        this.pantone_value = pantone_value;
    }

    public Datum() {
    }
}
