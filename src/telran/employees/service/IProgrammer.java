package telran.employees.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import telran.employees.dto.Programmer;

public interface IProgrammer {
	boolean addProgrammer(Programmer programmer);  //if added-true, if already exists - false
	boolean removeProgrammer(int id); //true if removed, if no employee - false
	Programmer getProgrammerData(int id);
	boolean addNewTechnology(int id, String technology); //technology - regular string such as "java","web","c++"
	boolean removeTechnology(int id, String technology);
	List<Programmer> getProgrammersWithTechnology(String technology); //returns list of programmers using the given technology
	List<Programmer> getProgrammersWithSalaries(int salaryFrom, int salaryTo); //return list of Programmers with salary in the given range
	boolean updateSalary(int id, int salary);
	
	Map<String, Set<Programmer>> convertBaseMapToTechProgrammersMap(Map<Integer, Programmer> programmers);

}
