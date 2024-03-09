package com.qa.gorest.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPojo {


		@JsonProperty("name") // These Json Property we are declaring so that we can map the actual json key			         			// names to the variables we declared in this class
		private String name;
		@JsonProperty("status")
		private String status;
		@JsonProperty("gender")
		private String gender;
		@JsonProperty("email")
		private String email;
		@JsonProperty("id")
		private Integer id;

		public UserPojo(String name, String status, String gender, String email) {
			this.name = name;
			this.status = status;
			this.gender = gender;
			this.email = email;
		}

	}
