package com.yangnjo.dessert_atelier.domain.order;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BasketProperty implements Serializable {
  private Long dppId;
  private LocalDateTime updatedAt;
  private List<Long> optionIds;
  private Integer quantity;

}
