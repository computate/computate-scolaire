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
		l.addFacetField("ecoleCle_indexed_long");
		l.addFacetField("anneeCle_indexed_long");
		l.addFacetField("saisonCle_indexed_long");
		l.addFacetField("sessionCle_indexed_long");
		l.addFacetField("ageCle_indexed_long");
	}

	protected void _inscriptions(List<SchoolEnrollment> l) {
		l.addAll(enrollmentSearch.getList());
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

	protected void _familyName(Wrap<String> c) {
	}

	protected void _personCompleteName(Wrap<String> c) {
		c.o(String.format("%s %s", personFirstName, familyName));
	}

	protected void _personCompleteNamePreferred(Wrap<String> c) {
		c.o(String.format("%s %s", personFirstName, familyName));
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

	protected void _dadCompleteName(Wrap<String> c) {
		c.o(personCompleteName);
	}

	protected void _dadId(Wrap<String> c) {
		if(dadCompleteName != null) {
			String s = Normalizer.normalize(dadCompleteName, Normalizer.Form.NFD);
			s = StringUtils.lowerCase(s);
			s = StringUtils.trim(s);
			s = StringUtils.replacePattern(s, "\\s{1,}", "-");
			s = StringUtils.replacePattern(s, "[^\\w-]", "");
			s = StringUtils.replacePattern(s, "-{2,}", "-");
			c.o(s);
		}
		else if(pk != null){
			c.o(pk.toString());
		}
	}

	protected void _pageUrl(Wrap<String> c) {
		if(dadId != null) {
			String o = siteRequest_.getSiteConfig_().getSiteBaseUrl() + "/chilc/" + dadId;
			c.o(o);
		}
	}

	protected void _objectSuggest(Wrap<String> c) { 
		c.o(dadCompleteName);
	}

	@Override()
	protected void  _classCanonicalNames(List<String> l) {
		l.add(SchoolDad.class.getCanonicalName());
		super._classCanonicalNames(l);
	}
}