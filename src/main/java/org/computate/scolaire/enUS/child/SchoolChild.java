package org.computate.scolaire.enUS.child;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.computate.scolaire.enUS.block.SchoolBlock;
import org.computate.scolaire.enUS.cluster.Cluster;
import org.computate.scolaire.enUS.wrap.Wrap;
import org.computate.scolaire.enUS.enrollment.SchoolEnrollment;
import org.computate.scolaire.enUS.search.SearchList;

/**
 * Model: true
 * Api: true
 * Page: true
 * Saved: true
 * RoleSession: true
 * RoleUser: true
 * Color: orange
 * IconGroup: regular
 * IconName: child
 * Role.enUS: SiteManager
 * ApiUri.enUS: /api/child
 * ApiTag.enUS: Child
 * AName.enUS: a child
 * Role.frFR: SiteManager
 * ApiUri.frFR: /api/enfant
 * ApiTag.frFR: Enfant
 * AName.frFR: un enfant
 * CanonicalName: org.computate.scolaire.frFR.enfant.EnfantScolaire
 **/
public class SchoolChild extends SchoolChildGen<Cluster> {

	protected void _childKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _enrollmentKeys(List<Long> o) {}

	protected void _familySort(Wrap<Integer> c) {
		c.o(1);
	}

	protected void _schoolSort(Wrap<Integer> c) {
		c.o(1);
	}

	protected void _enrollmentSearch(SearchList<SchoolEnrollment> l) { 
		l.setQuery("*:*");
		l.addFilterQuery("childKey_indexed_long:" + pk);
		l.setC(SchoolEnrollment.class);
		l.setStore(true);
		l.setFacet(true);
		l.addFacetField("schoolKey_indexed_long");
		l.addFacetField("yearKey_indexed_long");
		l.addFacetField("seasonKey_indexed_long");
		l.addFacetField("sessionKey_indexed_long");
		l.addFacetField("ageKey_indexed_long");
		l.addFacetField("userKeys_indexed_longs");
	}

	protected void _enrollments(List<SchoolEnrollment> l) {
		l.addAll(enrollmentSearch.getList());
	}

	protected void _userKeys(List<Long> l) {
		l.addAll(enrollmentSearch.getQueryResponse().getFacetField("userKeys_indexed_longs").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	protected void _schoolKeys(List<Long> l) {
		l.addAll(enrollmentSearch.getQueryResponse().getFacetField("schoolKey_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	protected void _yearKeys(List<Long> l) {
		l.addAll(enrollmentSearch.getQueryResponse().getFacetField("yearKey_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	protected void _seasonKeys(List<Long> l) {
		l.addAll(enrollmentSearch.getQueryResponse().getFacetField("seasonKey_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	protected void _sessionKeys(List<Long> l) {
		l.addAll(enrollmentSearch.getQueryResponse().getFacetField("sessionKey_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	protected void _ageKeys(List<Long> l) {
		l.addAll(enrollmentSearch.getQueryResponse().getFacetField("ageKey_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	protected void _personFirstName(Wrap<String> c) {
	}

	protected void _personFirstNamePreferred(Wrap<String> c) {
		c.o(personFirstName);
	}

	protected void _familyName(Wrap<String> c) {
	}

	protected void _personCompleteName(Wrap<String> c) {
		if(personFirstNamePreferred != null && familyName != null)
			c.o(String.format("%s %s", personFirstNamePreferred, familyName));
		else if(personFirstNamePreferred != null)
			c.o(personFirstNamePreferred);
		else if(familyName != null)
			c.o(familyName);
	}

	protected void _personCompleteNamePreferred(Wrap<String> c) {
		c.o(personCompleteName);
	}

	protected void _personFormalName(Wrap<String> c) {
		c.o(String.format("%s %s", personFirstName, familyName));
	}

	protected void _personBirthDate(Wrap<LocalDate> c) {
	}

	protected void _personBirthDateYear(Wrap<Integer> c) {
		if(personBirthDate != null)
			c.o(personBirthDate.getYear());
	}

	protected void _personBirthDateMonthOfYear(Wrap<String> c) {
		if(personBirthDate != null)
			c.o(personBirthDate.format(DateTimeFormatter.ofPattern("MMMM", Locale.US)));
	}

	protected void _personBirthDateDayOfWeek(Wrap<String> c) {
		if(personBirthDate != null)
			c.o(personBirthDate.format(DateTimeFormatter.ofPattern("EEEE", Locale.US)));
	}

	@Override()
	public String strPersonBirthDate() {
		return personBirthDate == null ? "" : personBirthDate.format(DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US));
	}

	protected void _personAgeInSeptember(Wrap<String> c) {
		if(personBirthDate != null) {
			Integer year = LocalDate.now().getYear();
			LocalDate septemberFirst = LocalDate.of(year, 9, 1);
			long age = ChronoUnit.YEARS.between(personBirthDate, septemberFirst);
			c.o(String.format("age %s on September 1 %s", age, year));
		}
	}

	protected void _photo(Wrap<String> c) {
	}

	protected void _childCompleteName(Wrap<String> c) {
		c.o(personCompleteName);
	}

	@Override()
	protected void  _objectTitle(Wrap<String> c) {
		c.o(childCompleteName);
	}
}
