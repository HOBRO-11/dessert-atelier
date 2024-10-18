package com.yangnjo.dessert_atelier.domain.order;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
// @EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BasketProperty implements Serializable {
  private Long dppId;
  private LocalDateTime updatedAt;
  private List<Long> optionIds;
  private Integer quantity;

  public BasketProperty(Long dppId, List<Long> optionIds) {
    this.dppId = dppId;
    this.optionIds = optionIds;
  }

  public void addQuantity(int amount){
    this.quantity += amount;
  };

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((dppId == null) ? 0 : dppId.hashCode());
    result = prime * result + ((optionIds == null) ? 0 : optionIds.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BasketProperty other = (BasketProperty) obj;
    if (dppId == null) {
      if (other.dppId != null)
        return false;
    } else if (!dppId.equals(other.dppId))
      return false;
    if (optionIds == null) {
      if (other.optionIds != null)
        return false;
    } else if (!optionIds.equals(other.optionIds))
      return false;
    return true;
  }

}
