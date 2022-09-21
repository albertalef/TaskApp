package me.albertalef.realtodoapp.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ToDo {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	@Column(nullable = false)
	private String resume;
	@Column(nullable = false)
	private String description;

	@Column(updatable = false)
	private LocalDateTime createdAt;
	private LocalDateTime finishedAt;

	@PrePersist
	void createdAt(){
		this.createdAt = LocalDateTime.now();
	}

	public boolean isFinishedAt(){
		return finishedAt != null;
	}

	@SuppressWarnings("all")
	public static class ToDoBuilder{
		private String resume;
		private String description;

		public ToDoBuilder resume(String resume){
			this.resume = resume == null ? "" : resume;
			return this;
		}
		public ToDoBuilder description(String description){
			this.description = description == null ? "" : description;
			return this;
		}
	}
}
