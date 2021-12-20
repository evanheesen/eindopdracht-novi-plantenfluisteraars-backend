package nl.evanheesen.plantenfluisteraars.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthorityKey implements Serializable {
    private String username;
    private String authority;
}
