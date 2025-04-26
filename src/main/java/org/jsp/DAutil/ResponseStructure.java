package org.jsp.DAutil;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseStructure <T>{

	private int status;
	private String message;
	private T body;
}
