package com.ontology.bean;

import java.io.Serializable;

public class DemoBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String field_id;
	private String field_name;
	private String field_desc;
	
	private String esort_id;
	private String esort_name;
	private String esort_desc;
	
	private String entity_id;
	private String entity_key;
	private String entity_value;
	public String getField_id() {
		return field_id;
	}
	public void setField_id(String fieldId) {
		field_id = fieldId;
	}
	public String getField_name() {
		return field_name;
	}
	public void setField_name(String fieldName) {
		field_name = fieldName;
	}
	public String getField_desc() {
		return field_desc;
	}
	public void setField_desc(String fieldDesc) {
		field_desc = fieldDesc;
	}
	public String getEsort_id() {
		return esort_id;
	}
	public void setEsort_id(String esortId) {
		esort_id = esortId;
	}
	public String getEsort_name() {
		return esort_name;
	}
	public void setEsort_name(String esortName) {
		esort_name = esortName;
	}
	public String getEsort_desc() {
		return esort_desc;
	}
	public void setEsort_desc(String esortDesc) {
		esort_desc = esortDesc;
	}
	public String getEntity_id() {
		return entity_id;
	}
	public void setEntity_id(String entityId) {
		entity_id = entityId;
	}
	public String getEntity_key() {
		return entity_key;
	}
	public void setEntity_key(String entityKey) {
		entity_key = entityKey;
	}
	public String getEntity_value() {
		return entity_value;
	}
	public void setEntity_value(String entityValue) {
		entity_value = entityValue;
	}
	

	
	
}
