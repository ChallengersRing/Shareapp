package in.shareapp.user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

//ENTITY/BEAN/POJO CLASS
public class User {
    private Long id;
    private UUID extId;
    private String avatar;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String phone;
    private String password;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(Builder builder) {
        this.id = builder.id;
        this.extId = builder.extId;
        this.avatar = builder.avatar;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.dateOfBirth = builder.dateOfBirth;
        this.gender = builder.gender;
        this.email = builder.email;
        this.phone = builder.phone;
        this.password = builder.password;
        this.isDeleted = builder.isDeleted;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    //for signin
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // for signup
    public User(String avatar, String firstName, String lastName, String email, String phone, String password) {
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    //for update
    public User(String avatar, String firstName, String lastName, String dateOfBirth, String gender, String email,
                String phone, String password) {
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public UUID getExtId() {
        return extId;
    }

    public String getAvatar() {
        return avatar;
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

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder {
        // Required parameters
        private final String email;
        private final String password;

        // Optional parameters - initialized to default values
        private Long id;
        private UUID extId;
        private String avatar;
        private String firstName;
        private String lastName;
        private String dateOfBirth;
        private String gender;
        private String phone;
        private boolean isDeleted;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        // Builder constructor with required parameters
        public Builder(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Setter methods for optional parameters
        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder extId(UUID extId) {
            this.extId = extId;
            return this;
        }

        public Builder avatar(String avatar) {
            this.avatar = avatar;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder dateOfBirth(String dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        // Build method to create User instance
        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", extId=" + extId +
                ", avatar='" + avatar + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", isDeleted=" + isDeleted +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }


}