package com.immutableant.sfgpetclinic.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pet_types")
public class PetType extends BaseEntity {

  @Column(name = "name")
  String name;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    PetType petType = (PetType) o;
    return getId() != null && Objects.equals(getId(), petType.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
