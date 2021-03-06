package models;

import com.avaje.ebean.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import utilities.View;
import com.avaje.ebean.config.PersistBatch;
import com.fasterxml.jackson.annotation.JsonView;
import utilities.PersistenceManager;
import utilities.Validation;
import utilities.View;
import utilities.Validation;
import utilities.View;
import javax.persistence.*;
import java.util.List;

/**
 * Created by Ejub on 15/02/16.
 * Class Restaurant can be used for storing restaurants.
 */
@Entity
@Table(name = "abh_restaurant")
public class Restaurant extends Model implements Validation{
    @Id
    @Column(name = "restaurantId", columnDefinition = "BIGINT")
    @JsonView(View.BasicDetails.class)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long restaurantId;
    @Column(length = 100)
    @JsonView(View.BasicDetails.class)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(referencedColumnName = "restaurantId")
    @JsonView(View.BasicDetails.class)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn(referencedColumnName = "restaurantId")
    @JsonView(View.AllDetails.class)
    private Coordinates coordinates;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonView(View.AdditionalDetails.class)
    @JsonIgnore
    private List<Review> reviews;
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonView(View.AdditionalDetails.class)
    @JsonIgnore
    private List<Photo> photos;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonView(View.AdditionalDetails.class)
    @JsonIgnore
    private List<Reservation> reservations;

    @Column(columnDefinition = "BIGINT")
    @JsonView(View.BasicDetails.class)
    private long phone;
    @Column(name = "workingHours",length = 20)
    @JsonView(View.BasicDetails.class)
    private String workingHours;
    @JsonView(View.BasicDetails.class)
    private double rating;
    @Column(columnDefinition = "BIGINT DEFAULT 0")
    @JsonIgnore
    private long numberOfRatings;
    @Column(columnDefinition = "DOUBLE DEFAULT 0")
    @JsonIgnore
    private double ratingsTotal;

    @Column(name = "reservationPrice")
    @JsonView(View.AllDetails.class)
    private double reservationPrice;
    @Column(length = 200)
    @JsonView(View.AllDetails.class)
    private String deals;
    @JsonView(View.BasicDetails.class)
    private String image;


    public static Model.Finder<String, Restaurant> find = new Model.Finder<String, Restaurant>(String.class, Restaurant.class);

    /**
     * Validates the restaurant properties.
     *
     * @return true if validation is successful
     */
    @Override
    @JsonView(View.AdditionalDetails.class)
    public boolean isValid() {
        return name != null && address != null && coordinates != null && !name.isEmpty() && !address.getCity().isEmpty()
                && !address.getCountry().isEmpty() && !address.getStreetName().isEmpty() &&
                coordinates.getLatitude() != 0 && coordinates.getLongitude() != 0 && phone != 0;
    }

    public void updateCoordinates(Coordinates coordinates){
        this.getCoordinates().setLatitude(coordinates.getLatitude());
        this.getCoordinates().setLongitude(coordinates.getLongitude());
    }

    public void updateAddress(Address address) {
        this.getAddress().setStreetName(address.getStreetName());
        this.getAddress().setCity(address.getCity());
        this.getAddress().setCountry(address.getCountry());
    }

    public void updateData(Restaurant restaurant) {
        this.setDeals(restaurant.getDeals());
        this.setImage(restaurant.getImage());
        this.setName(restaurant.getName());
        this.setPhone(restaurant.getPhone());
        this.setRating(restaurant.getRating());
        this.setReservationPrice(restaurant.getReservationPrice());
        this.setWorkingHours(restaurant.getWorkingHours());

        this.updateAddress(restaurant.getAddress());
        this.updateCoordinates(restaurant.getCoordinates());
        PersistenceManager.saveRestaurant(this);
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getReservationPrice() {
        return reservationPrice;
    }

    public void setReservationPrice(double reservationPrice) {
        this.reservationPrice = reservationPrice;
    }

    public String getDeals() {
        return deals;
    }

    public void setDeals(String deals) {
        this.deals = deals;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @JsonIgnore
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @JsonIgnore
    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void addReview(Review review) {
        reviews.add(review);
        this.setNumberOfRatings(getNumberOfRatings() + 1);
        this.setRatingsTotal(getRatingsTotal() + review.getRating());
        this.setRating(getRatingsTotal() / getNumberOfRatings());
        this.save();
    }

    @JsonIgnore
    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public long getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(long numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public double getRatingsTotal() {
        return ratingsTotal;
    }

    public void setRatingsTotal(double ratingsTotal) {
        this.ratingsTotal = ratingsTotal;
    }
}
