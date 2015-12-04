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
			result.add(Pattern.compile(getName(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(t.getName()).find());
		}
		
		if (getFirstLastName() != null && getFirstLastName().length() > 0) {
			result.add(Pattern.compile(getFirstLastName(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(t.getFirstLastName()).find());
		}
		
		if (getSecondLastName() != null && getSecondLastName().length() > 0) {
			result.add(Pattern.compile(getSecondLastName(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(t.getSecondLastName()).find());
		}
		
		if (getIdentification() != null && getIdentification().length() > 0) {
			result.add(Pattern.compile(getIdentification(), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(t.getIdentification()).find());
		}
		
		return result.size() == 0 ? false : result.stream().allMatch(p -> p);
	}

}
