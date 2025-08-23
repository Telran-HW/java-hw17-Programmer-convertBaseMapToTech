package telran.employees.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employees.dto.Programmer;
import telran.employees.service.IProgrammer;
import telran.employees.service.ProgrammerMap;

class ProgrammerstTest {
	private static final String CPP = "c++";
	private static final String JAVA = "java";
	private static final String WEB = "javaScript";
	private static final String SQL = "sql";
	private static final String PYTHON = "python";
	private static final String GO = "go";
	
	private static final int ID_NEW = 1111;
	
	String[] tech1 = {CPP, JAVA, SQL };
	String[] tech2 = {CPP, WEB };
	String[] tech3 = {PYTHON, GO, JAVA };
	
	int salary1 = 10000;
	int salary2 = 5000;
	int salary3 = 15000;
	
	Programmer prog1, prog2, prog3;
	
	IProgrammer programmerService;
	

	@BeforeEach
	void setUp() throws Exception {
		 prog1 = new Programmer(1, "Frodo", tech1, salary1);
		prog2 = new Programmer(2, "Aragorn", tech2, salary2);
		prog3 = new Programmer(3, "Legolas", tech3, salary3);
		Programmer[] progs = {prog1, prog2, prog3};
		
		programmerService = new ProgrammerMap();
		for(Programmer p : progs)
		programmerService.addProgrammer(p);
	}
	
	@Test
	void testConvertBaseMapToTechProgrammersMap() {
		Map<Integer, Programmer> temp = new HashMap<>();
		temp.put(prog1.getId(), prog1);
		temp.put(prog2.getId(), prog2);
		temp.put(prog3.getId(), prog3);
		
		 Map<String, Set<Programmer>> techMap = programmerService.convertBaseMapToTechProgrammersMap(temp);
		            
		
		techMap.forEach(new BiConsumer<String, Set<Programmer>>() {

			@Override
			public void accept(String t, Set<Programmer> u) {
				System.out.println("Key: " + t +", Value: " +u);
				
			}
		});
		    assertTrue(techMap.containsKey(CPP));
		    assertTrue(techMap.containsKey(PYTHON));
		    assertTrue(techMap.get(JAVA).contains(prog1));
		    assertTrue(techMap.get(JAVA).contains(prog3));
		    assertTrue(techMap.get(SQL).contains(prog1));
		    assertEquals(2, techMap.get(CPP).size()); 
		    assertEquals(1, techMap.get(WEB).size());   
		    assertEquals(1, techMap.get(GO).size());   
	}

	@Test
	void testAddProgrammer() throws Exception {
		assertFalse(programmerService.addProgrammer(new Programmer(1, "name1", tech1, salary1)));
		assertTrue(programmerService.addProgrammer(new Programmer(ID_NEW, "name", new String[] {GO}, 20000)));
		assertNotNull(programmerService.getProgrammerData(ID_NEW));
		assertFalse(programmerService.addProgrammer(null));
		try {
			programmerService.addProgrammer(new Programmer(1, null, tech1, salary1));
			fail("Exception expected");
		}catch (Exception e) {
			assertEquals("Wrong data. Programmer not created", e.getMessage());
		}
	}

	@Test
	void testRemoveProgrammer()
	{
		assertFalse(programmerService.removeProgrammer(ID_NEW));
		assertTrue(programmerService.removeProgrammer(1));
		assertNull(programmerService.getProgrammerData(1));
	}

	@Test
	void testGetProgrammerData() 	{
		assertEquals(prog1, programmerService.getProgrammerData(1));
		assertEquals(prog2, programmerService.getProgrammerData(2));
		assertNull(programmerService.getProgrammerData(ID_NEW));
	}

	@Test
	void testAddNewTechnology()	{
		assertFalse(prog2.getTechnologies().contains(JAVA));
		programmerService.addNewTechnology(2, JAVA);
		assertTrue(prog2.getTechnologies().contains(JAVA));
	}

	@Test
	void testRemoveTechnology()	{
		assertTrue(prog1.getTechnologies().contains(JAVA));
		programmerService.removeTechnology(1, JAVA);
		assertFalse(prog2.getTechnologies().contains(JAVA));
	}

	@Test
	void testGetProgrammersWithTechnology()
	{
		List<Programmer> programmers = 
				programmerService.getProgrammersWithTechnology(JAVA);
		assertEquals(2, programmers.size());
		assertTrue(programmers.contains(prog1));
		assertTrue(programmers.contains(prog3));
	}

	@Test
	void testGetProgrammersWithSalaries()
	{
		List<Programmer> programmers = 
				programmerService.getProgrammersWithSalaries(5000, 11000);
		assertEquals(2, programmers.size());
		assertTrue(programmers.contains(prog1));
		assertTrue(programmers.contains(prog2));
	}

	@Test
	void testUpdateSalary()
	{
		Programmer programmer = programmerService.getProgrammerData(2);
		assertEquals(salary2, programmer.getSalary());
		programmerService.updateSalary(2, salary3);
		programmer = programmerService.getProgrammerData(2);
		assertEquals(salary3, programmer.getSalary());
	}

}
