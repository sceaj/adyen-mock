package sceaj.adyenmock.api.v1.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Address {

	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String city;
	private String provinceOrState;
	private String postalCode;
	private String countryCode;

}
