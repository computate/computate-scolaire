package org.computate.scolaire.enUS.dad;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
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
 * Color: light-blue
 * IconGroup: regular
 * IconName: male
 * Role.enUS: SiteManager
 * ApiUri.enUS: /api/dad
 * ApiTag.enUS: Dad
 * AName.enUS: a dad
 * Role.frFR: SiteManager
 * ApiUri.frFR: /api/pere
 * ApiTag.frFR: Père
 * AName.frFR: un père
 * CanonicalName: org.computate.scolaire.frFR.pere.PereScolaire
 **/
public class SchoolDad extends SchoolDadGen<Cluster> {

	protected void _dadKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _enrollmentKeys(List<Long> o) {}

	protected void _familySort(Wrap<Integer> c) {
		c.o(2);
	}

	protected void _schoolSort(Wrap<Integer> c) {
		c.o(2);
	}

	protected void _enrollmentSearch(SearchList<SchoolEnrollment> l) {
		l.setQuery("*:*");
		l.addFilterQuery("dadKey_indexed_long:" + pk);
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

	protected void _personOccupation(Wrap<String> c) {
	}

	protected void _personPhoneNumber(Wrap<String> c) {
	}

	protected void _personEmail(Wrap<String> c) {
	}

	@Override()
	public void  setPersonEmail(String personEmail) {
		if(personEmail != null)
			super.setPersonEmail(personEmail.toLowerCase());
	}

	protected void _personRelation(Wrap<String> c) {
		c.o("père");
	}

	protected void _personSms(Wrap<Boolean> c) {
		c.o(true);
	}

	protected void _personReceiveEmail(Wrap<Boolean> c) {
		c.o(true);
	}

	protected void _personEmergencyContact(Wrap<Boolean> c) {
		c.o(true);
	}

	protected void _personPickup(Wrap<Boolean> c) {
		c.o(true);
	}

	protected void _photo(Wrap<String> c) {
	}

	protected void _dadCompleteName(Wrap<String> c) {
		c.o(personCompleteName);
	}

	@Override()
	protected void  _objectTitle(Wrap<String> c) {
		c.o(dadCompleteName);
	}
}
