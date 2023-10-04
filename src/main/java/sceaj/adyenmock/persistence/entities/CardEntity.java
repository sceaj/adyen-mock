package sceaj.adyenmock.persistence.entities;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "cards")
public class CardEntity {

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer recordId;

	@NotNull
	private UUID externalKey;

	@NotNull
	@Size(max=24)
	private String cardNumber;
	
	@NotNull
	@Size(min=3, max=32)
	private String nameOnCard;
	
	@NotNull
	@Min(1)
	@Max(12)
	private Integer expirationMonth;
	
	@NotNull
	@Min(1)
	private Integer expirationYear;
	
	@NotNull
	@Size(max=8)
	private String cvv;
	
	public String getExpiration() {
		return String.format("%d/%d", expirationMonth, expirationYear);
	}
	
	public void setExpiration(String expirationString) {
		String[] expirationParts = expirationString.split("/");
		expirationMonth = Integer.valueOf(expirationParts[0]);
		expirationYear = Integer.valueOf(expirationParts[1]);
	}

}
