package telran.employees.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import telran.employees.dto.Programmer;

public class ProgrammerMap implements IProgrammer {
		
	private HashMap<Integer, Programmer> programmers = new HashMap<>();

		@Override
		public boolean addProgrammer(Programmer programmer) {
			if(programmer == null)
			return false;
			return programmers.putIfAbsent(programmer.getId(), programmer) == null;
		}

		@Override
		public boolean removeProgrammer(int id) {
			
			return programmers.remove(id) != null;
		}

		@Override
		public Programmer getProgrammerData(int id) {
			
			return programmers.get(id);
		}

		@Override
		public boolean addNewTechnology(int id, String technology) {
			if(technology == null || technology.isBlank())
				return false;
			Programmer prog = programmers.get(id);
			return prog == null? false : prog.getTechnologies().add(technology);
		}

		@Override
		public boolean removeTechnology(int id, String technology) {
			if(technology == null || technology.isBlank())
			return false;
			Programmer prog = programmers.get(id);
			return prog == null? false : prog.getTechnologies().remove(technology);
		}

		@Override
		public List<Programmer> getProgrammersWithTechnology(String technology) {
			List<Programmer>res = new ArrayList<>();
			if(technology == null || technology.isBlank())
			return res;
			
			for(Programmer p : programmers.values()) {
				if(p.getTechnologies().contains(technology))
					res.add(p);
			}
			return res;
		}

		@Override
		public List<Programmer> getProgrammersWithSalaries(int salaryFrom, int salaryTo) {
			List<Programmer>res = new ArrayList<>();
			if(salaryFrom  >= salaryTo || salaryFrom <=0)
			return res;
			for(Programmer p : programmers.values()) {
				int salary = p.getSalary();
				if(salary >=salaryFrom && salary <= salaryTo)
					res.add(p);
			}
			return res;
		}

		@Override
		public boolean updateSalary(int id, int salary) {
			if(salary <=0)
			return false;
			Programmer prog = programmers.get(id);
			if( prog == null || salary == prog.getSalary())
				return false;
			prog.setSalary(salary);
			return true;
		}
		@Override
		public Map<String, Set<Programmer>> convertBaseMapToTechProgrammersMap(Map<Integer, Programmer> programmers) {
			Map<String, Set<Programmer>> res = new HashMap<>();
			
			for(Programmer p : programmers.values()) {
				for(String tech: p.getTechnologies()) {
					HashSet<Programmer> set = new HashSet<>();
					set.add(p);
					res.merge(tech, set, new BiFunction<Set<Programmer>, Set<Programmer>, Set<Programmer>>() {

						@Override
						public Set<Programmer> apply(Set<Programmer> oldSet, Set<Programmer> newSet) {
//							System.out.println("oldset"+oldSet);
//							System.out.println("newset"+newSet);
							oldSet.addAll(newSet);
							return oldSet;
						}
					});
					//System.out.println("set ="+set);
					//System.out.println("res ="+res);
				}
				//System.out.println("res for 1 ="+res);
			}
			return res;
		}
		

		
		
}
