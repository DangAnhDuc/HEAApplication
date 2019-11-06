package com.example.heaapp.model.food;


import java.util.List;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("barcode")
    @Expose
    private String barcode;
    @SerializedName("name_translations")
    @Expose
    private NameTranslations nameTranslations;
    @SerializedName("display_name_translations")
    @Expose
    private DisplayNameTranslations displayNameTranslations;
    @SerializedName("ingredients_translations")
    @Expose
    private IngredientsTranslations ingredientsTranslations;
    @SerializedName("origin_translations")
    @Expose
    private OriginTranslations originTranslations;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("portion_quantity")
    @Expose
    private Integer portionQuantity;
    @SerializedName("portion_unit")
    @Expose
    private String portionUnit;
    @SerializedName("alcohol_by_volume")
    @Expose
    private Integer alcoholByVolume;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;
    @SerializedName("nutrients")
    @Expose
    private Nutrients nutrients;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public NameTranslations getNameTranslations() {
        return nameTranslations;
    }

    public void setNameTranslations(NameTranslations nameTranslations) {
        this.nameTranslations = nameTranslations;
    }

    public DisplayNameTranslations getDisplayNameTranslations() {
        return displayNameTranslations;
    }

    public void setDisplayNameTranslations(DisplayNameTranslations displayNameTranslations) {
        this.displayNameTranslations = displayNameTranslations;
    }

    public IngredientsTranslations getIngredientsTranslations() {
        return ingredientsTranslations;
    }

    public void setIngredientsTranslations(IngredientsTranslations ingredientsTranslations) {
        this.ingredientsTranslations = ingredientsTranslations;
    }

    public OriginTranslations getOriginTranslations() {
        return originTranslations;
    }

    public void setOriginTranslations(OriginTranslations originTranslations) {
        this.originTranslations = originTranslations;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getPortionQuantity() {
        return portionQuantity;
    }

    public void setPortionQuantity(Integer portionQuantity) {
        this.portionQuantity = portionQuantity;
    }

    public String getPortionUnit() {
        return portionUnit;
    }

    public void setPortionUnit(String portionUnit) {
        this.portionUnit = portionUnit;
    }

    public Integer getAlcoholByVolume() {
        return alcoholByVolume;
    }

    public void setAlcoholByVolume(Integer alcoholByVolume) {
        this.alcoholByVolume = alcoholByVolume;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public void setNutrients(Nutrients nutrients) {
        this.nutrients = nutrients;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}