package com.sm.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MedsolResponse<T> {
	boolean success;
    private int status;
    private String message;
    private Object result;
}
