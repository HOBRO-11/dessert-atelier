package com.yangnjo.dessert_atelier.domain.display_product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.yangnjo.dessert_atelier.domain.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DisplayProductPreset extends BaseEntity implements Cloneable {

  @JoinColumn(name = "display_product_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private DisplayProduct displayProduct;

  @Setter
  @Column(nullable = false)
  private String naming;

  @Column(nullable = false)
  private String thumb;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private Integer price;

  @Setter
  private Integer optionLayer;

  @Setter
  @Column(nullable = false)
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "display_product_preset_image_id")
  private DisplayProductPresetImage dppImg;
  
  @Setter
  private Integer percentDiscount;
  
  @Setter
  private LocalDateTime startDateTime;
  
  @Setter
  private LocalDateTime endDateTime;
  
  @Setter
  @Column(nullable = false)
  private boolean isDefault;

  @OneToMany(mappedBy = "displayProductPreset")
  private List<Option> options = new ArrayList<>();

  public static DisplayProductPreset createDefaultDPP(DisplayProduct displayProduct, String naming, String thumb,
      String title, int price, int optionLayer,
      String content, DisplayProductPresetImage dppImg) {
    DisplayProductPreset dpp = new DisplayProductPreset();
    dpp.displayProduct = displayProduct;
    displayProduct.addDisplayProductPreset(dpp);
    dpp.naming = naming;
    dpp.thumb = thumb;
    dpp.title = title;
    dpp.price = price;
    dpp.optionLayer = optionLayer;
    dpp.content = content;
    dpp.dppImg = dppImg;
    dpp.isDefault = true;
    return dpp;
  }

  public static DisplayProductPreset createCustomDPP(DisplayProduct displayProduct, String naming, String thumb,
      String title, int price, int optionLayer,
      String content, DisplayProductPresetImage dppImg, int percentDiscount, LocalDateTime startDateTime,
      LocalDateTime endDateTime) {
    DisplayProductPreset dpp = new DisplayProductPreset();
    dpp.displayProduct = displayProduct;
    displayProduct.addDisplayProductPreset(dpp);
    dpp.naming = naming;
    dpp.thumb = thumb;
    dpp.title = title;
    dpp.price = price;
    dpp.optionLayer = optionLayer;
    dpp.content = content;
    dpp.dppImg = dppImg;
    dpp.percentDiscount = percentDiscount;
    dpp.startDateTime = startDateTime;
    dpp.endDateTime = endDateTime;
    dpp.isDefault = false;
    return dpp;
  }

  public static DisplayProductPreset createCustomDPP(DisplayProductPreset dpp, String naming, String title,
      String thumb) {
    DisplayProductPreset dppClone = dpp.clone();
    dppClone.naming = naming;
    dppClone.title = title;
    dppClone.thumb = thumb;
    dppClone.isDefault = false;
    return dppClone;
  }

  public void addOptions(List<Option> options) {
    for (Option o : options) {
      this.options.add(o);
      o.setDisplayProductPreset(this);
    }
  }

  @Override
  public DisplayProductPreset clone() {
    try {
      return (DisplayProductPreset) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }

}
