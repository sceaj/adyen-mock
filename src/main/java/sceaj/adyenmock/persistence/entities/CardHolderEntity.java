package sceaj.adyenmock.persistence.entities;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.UUID;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "card_holders")
public class CardHolderEntity {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer recordId;
	
	@NotNull
	private UUID externalKey;	

	@Size(max = 40)
	private String firstName;
	
	@Size(max = 40)
	private String lastName;

	@Embedded
	private AddressEmbeddable address;

}
