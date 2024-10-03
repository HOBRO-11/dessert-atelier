package com.yangnjo.dessert_atelier.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisplayProducts extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String thumb;

    @Column(nullable = false)
    private String description;

    @JoinColumn(name = "display_product_images_id")
    @JdbcTypeCode(SqlTypes.JSON)
    @OneToOne(fetch = FetchType.LAZY)
    private DisplayProductImages dpImgs;

    @Enumerated(value = EnumType.STRING)
    private SalePolicyStatus saleStatus;

    @Enumerated(value = EnumType.STRING)
    private DisplayProductStatus dpStatus;

    @OneToMany(mappedBy = "displayProducts")
    private List<Reviews> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "displayProducts")
    private List<QnAs> qnas = new ArrayList<>();

    @OneToMany(mappedBy = "displayProducts")
    private List<Options> options = new ArrayList<>();

    @OneToMany(mappedBy = "displayProducts")
    private List<Carts> carts = new ArrayList<>();

    public DisplayProducts(String title, Integer price, String thumb, String desc,
            DisplayProductImages dpImgs, SalePolicyStatus saleStatus, DisplayProductStatus dpStatus) {
        this.title = title;
        this.price = price;
        this.thumb = thumb;
        this.description = desc;
        this.dpImgs = dpImgs;
        this.saleStatus = saleStatus;
        this.dpStatus = dpStatus;
    }

    protected void addReview(Reviews reviews) {
        this.reviews.add(reviews);
        reviews.setDisplayProducts(this);
    }

    protected void addQna(QnAs qnas) {
        this.qnas.add(qnas);
    }

    public void changeSaleStatus(SalePolicyStatus saleStatus) {
        this.saleStatus = saleStatus;
    }

    public void changeDpStatus(DisplayProductStatus dpStatus) {
        this.dpStatus = dpStatus;
    }

    public void addOption(Options options) {
        this.options.add(options);
        options.setDisplayProducts(this);
    }

    protected void addCart(Carts carts) {
        this.carts.add(carts);
    }

}
