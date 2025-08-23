package telran.employees.dto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Programmer {
	int id;
	String name;
	Set<String> technologies;
	int salary;
	
	public Programmer() {	}

	public Programmer(int id, String name, String[] technologies, int salary) throws Exception {
		super();
		if(id < 0 || name == null || salary <= 0)
			throw new Exception("Wrong data. Programmer not created");
		this.id = id;
		this.name = name;
		if(technologies == null)
			this.technologies = new HashSet<>();
		else this.technologies = new HashSet<>(Arrays.asList(technologies));
		this.salary = salary;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<String> getTechnologies() {
		return technologies;
	}

	@Override
	public String toString() {
		return "Programmer [id=" + id + ", name=" + name + ", technologies=" + technologies + ", salary=" + salary
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Programmer other = (Programmer) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
