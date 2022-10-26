package com.immutableant.sfgpetclinic.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visits")
public class Visit extends BaseEntity {
  @Column(name = "date")
  private LocalDate date;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "pet_id")
  private Pet pet;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Visit visit = (Visit) o;
    return getId() != null && Objects.equals(getId(), visit.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
