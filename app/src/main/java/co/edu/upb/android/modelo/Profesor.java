package co.edu.upb.android.modelo;

public class Profesor {
    private Long id;
    private String name;
    private String lastname;
    private String address;
    private String phone;
    private String email;
    private int age;
    private float salary;

    //We use this for initialize the variables
    public Profesor() {
        this.id = Long.valueOf(0);
        this.name = "";
        this.lastname = "";
        this.address = "";
        this.phone = "";
        this.email = "";
        this.age = 0;
        this.salary = 0;
    }

    //We use this for add Teacher
    public Profesor(String name, String lastname, String address, String phone, String email, int age, float salary) {
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.salary = salary;
    }

    //Get all data from Teacher
    public Profesor(Long id, String name, String lastname, String address, String phone, String email, int age, float salary) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.age = age;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }
}

