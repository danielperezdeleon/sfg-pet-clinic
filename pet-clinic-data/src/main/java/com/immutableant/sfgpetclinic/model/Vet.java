package com.immutableant.sfgpetclinic.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vets")
public class Vet extends Person {
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "vet_specialties",
      joinColumns = @JoinColumn(name = "vet_id"),
      inverseJoinColumns = @JoinColumn(name = "speciality_id"))
  private Set<Specialty> specialties = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    Vet vet = (Vet) o;
    return getId() != null && Objects.equals(getId(), vet.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
