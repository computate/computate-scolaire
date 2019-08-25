package org.computate.scolaire.enUS.school;

import java.text.Normalizer;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.computate.scolaire.enUS.cluster.Cluster;
import org.computate.scolaire.enUS.wrap.Wrap;

public class School extends SchoolGen<Cluster> {

	protected void _schoolKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _childKeys(List<Long> o) {}

	protected void _blockKeys(List<Long> o) {}

	protected void _ageGroupKeys(List<Long> o) {}

	protected void _sessionKeys(List<Long> o) {}

	protected void _seasonKeys(List<Long> o) {}

	protected void _yearKeys(List<Long> o) {}

	protected void _educationSort(Wrap<Integer> c) {
		c.o(1);
	}

	protected void _schoolSort(Wrap<Integer> c) {
		c.o(1);
	}

	protected void _schoolName(Wrap<String> c) {
	}

	protected void _schoolPhoneNumber(Wrap<String> c) {
	}

	protected void _schoolAdministratorName(Wrap<String> c) {
	}

	protected void _schoolAddress(Wrap<String> c) {
	}

	protected void _objectSuggestWeight(Wrap<Double> c) {
		c.o(1D);
	}

	protected void _objectSuggest(Wrap<String> c) { 
		c.o(schoolName);
	}

	protected void _schoolNameShort(Wrap<String> c) {
		c.o(schoolName);
	}

	protected void _schoolNameComplete(Wrap<String> c) {
		c.o(schoolName);
	}

	protected void _schoolId(Wrap<String> c) {
		if(schoolName != null) {
			String s = Normalizer.normalize(schoolName, Normalizer.Form.NFD);
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
		if(schoolId != null) {
			String o = siteRequest_.getSiteConfig_().getSiteBaseUrl() + "/enUS/school/" + schoolId;
			c.o(o);
		}
	}

	protected void _pageH1(Wrap<String> c) {
		c.o("School: " + schoolName);
	}

	public void  htmlBody() {
		super.htmlBody();
	}

	@Override()
	protected void  _classCanonicalNames(List<String> l) {
		l.add(School.class.getCanonicalName());
		super._classCanonicalNames(l);
	}
}
