package fr.florianburel.things.model.modelObject;

/**
 * Created by fl0 on 03/08/15.
 */
public class Product {

    public Product(String ref) {
        this.ref = ref;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageuRL() {
        return imageuRL;
    }

    public void setImageuRL(String imageuRL) {
        this.imageuRL = imageuRL;
    }

    public String getRef() {
        return ref;
    }

    private String ref;
    private String designation;
    private double price;
    private String description;
    private String imageuRL;

    @Override
    public String toString() {
        return "(" + getRef() + ") " + getDesignation() + " - " + getPrice() + "€";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        return !(getRef() != null ? !getRef().equals(product.getRef()) : product.getRef() != null);

    }

    @Override
    public int hashCode() {
        return getRef() != null ? getRef().hashCode() : 0;
    }
}
