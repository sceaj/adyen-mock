package sceaj.adyenmock.persistence.entities;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;

@Embeddable
public class AddressEmbeddable {
	
	@Size(max=40)
	private String addressLine1;
	
	@Size(max=40)
	private String addressLine2;

	@Size(max=40)
	private String addressLine3;

	@Size(max=30)
	private String city;

	@Size(max=30)
	private String provinceOrState;

	@Size(max=20)
	private String postalCode;

	@Size(max=3)
	private String countryCode;

}
