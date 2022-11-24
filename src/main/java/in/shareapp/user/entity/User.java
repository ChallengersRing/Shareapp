package in.shareapp.user.entity;

//ENTITY/BEAN/POJO CLASS
public class User {
    //PROPERTIES
    private int id;
    private String photo;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String phone;
    private String password;

    public User() {
    }

    public User(int id, String photo, String firstName, String lastName, String dateOfBirth, String gender, String email, String phone, String password) {
        this.id = id;
        this.photo = photo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    // for signup
    public User(String photo, String firstName, String lastName, String email, String phone, String password) {
        this.photo = photo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    //for signin
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //for update profile


    public User(String photo, String firstName, String lastName, String dateOfBirth, String gender, String email, String phone, String password) {
        this.photo = photo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    //ACCESSOR/GETTER METHODS
    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoto() {
        return photo;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    //MUTATOR/SETTER METHODS
    public void setId(int id) {
        this.id = id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", photo='" + photo + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", password='" + password + '\'' +
                '}';
    }
}