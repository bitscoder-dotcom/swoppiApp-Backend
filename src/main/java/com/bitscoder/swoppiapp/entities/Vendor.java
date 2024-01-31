package com.bitscoder.swoppiapp.entities;

import jakarta.persistence.Entity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@Getter
@Setter
public class Vendor extends BaseUser{
}
