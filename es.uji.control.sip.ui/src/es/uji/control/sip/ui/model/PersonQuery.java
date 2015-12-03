package es.uji.control.sip.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import es.uji.control.domain.people.IPerson;

public class PersonQuery implements Predicate<IPerson> {

	private String name;
	private String firstLastName;
	private String secondLastName;
	private String identification;

	public PersonQuery(String name, String firstLastName, String secondLastName, String identification) {
		super();
		this.name = name;
		this.firstLastName = firstLastName;
		this.secondLastName = secondLastName;
		this.identification = identification;
	}

	public String getName() {
		return name;
	}

	public String getFirstLastName() {
		return firstLastName;
	}

	public String getSecondLastName() {
		return secondLastName;
	}

	public String getIdentification() {
		return identification;
	}

	@Override
	public boolean test(IPerson t) {
		
		List<Boolean> result = new ArrayList<>(1);
		
		if (getName() != null && getName().length() > 0) {
			Pattern patternName = Pattern.compile(getName(), Pattern.CASE_INSENSITIVE);
			result.add(patternName.pattern().matches(t.getName()));
		}
		
		if (getFirstLastName() != null && getFirstLastName().length() > 0) {
			Pattern patternFirstLastName = Pattern.compile(getFirstLastName(), Pattern.CASE_INSENSITIVE);
			result.add(patternFirstLastName.pattern().matches(t.getFirstLastName()));
		}
		
		if (getSecondLastName() != null && getSecondLastName().length() > 0) {
			Pattern patternSecondLastName = Pattern.compile(getSecondLastName(), Pattern.CASE_INSENSITIVE);
			result.add(patternSecondLastName.pattern().matches(t.getSecondLastName()));
		}
		
		if (getIdentification() != null && getIdentification().length() > 0) {
			Pattern patternIdentification = Pattern.compile(getIdentification(), Pattern.CASE_INSENSITIVE);
			result.add(patternIdentification.pattern().matches(t.getIdentification()));
		}
		
		return result.size() == 0 ? false : result.stream().allMatch(p -> p);
	}

}
