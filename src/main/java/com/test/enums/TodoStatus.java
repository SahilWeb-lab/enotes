package com.test.enums;

public enum TodoStatus {
	
	NOT_STARTED(1, "Not Started"),
	IN_PROGRESS(2, "In Progress"),
	COMPLETED(3, "Completed");
	
	private Integer id;
	
	private String status;

	private TodoStatus(Integer id, String status) {
		this.id = id;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
