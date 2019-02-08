package com.dub.spring.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class UserAuthority {
    
	/**
	 * 
	 */
	//private static final long serialVersionUID = -4910913750085618894L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	
	private String authority;

    public UserAuthority() { }

    public UserAuthority(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
