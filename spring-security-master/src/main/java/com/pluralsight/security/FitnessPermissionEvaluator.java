package com.pluralsight.security;

import java.io.Serializable;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

public class FitnessPermissionEvaluator implements PermissionEvaluator {

	@Autowired
	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public boolean hasPermission(Authentication auth, Object domainObject, Object permission) {
		// TODO Auto-generated method stub

		JdbcTemplate template = new JdbcTemplate(dataSource);

		System.out.println("Princicple : " + auth.getPrincipal());
		System.out.println("User : " + ((User) auth.getPrincipal()).getUsername());

		Object[] args = { ((User) auth.getPrincipal()).getUsername(), domainObject.getClass().getName(),
				permission.toString() };

		int count = template.queryForObject(
				"select count(*) from permissions p where " + "p.username = ? and p.target = ? and p.permission = ?",
				args, Integer.class);

		if (count == 1) {
			return true;
		} else {
			return false;
		}

	}

	public boolean hasPermission(Authentication auth, Serializable id, String type, Object permission) {
		// TODO Auto-generated method stub
		return false;
	}

}
