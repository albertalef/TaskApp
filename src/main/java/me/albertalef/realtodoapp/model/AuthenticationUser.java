package me.albertalef.realtodoapp.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationUser {
	private String username;
	private String password;
}
