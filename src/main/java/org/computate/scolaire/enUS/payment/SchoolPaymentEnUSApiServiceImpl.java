package org.computate.scolaire.enUS.payment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.computate.scolaire.enUS.contexte.SiteContextEnUS;
import org.computate.scolaire.enUS.request.SiteRequestEnUS;
import org.computate.scolaire.enUS.request.api.ApiRequest;
import org.computate.scolaire.enUS.search.SearchList;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

/**
 * Translate: false
 * CanonicalName.frFR: org.computate.scolaire.frFR.paiement.PaiementScolaireFrFRApiServiceImpl
 **/
public class SchoolPaymentEnUSApiServiceImpl extends SchoolPaymentEnUSGenApiServiceImpl {

	public SchoolPaymentEnUSApiServiceImpl(SiteContextEnUS siteContext) {
		super(siteContext);
	}

	@Override public void sqlPATCHSchoolPayment(SchoolPayment o, Boolean inheritPk, Handler<AsyncResult<SchoolPayment>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		ApiRequest apiRequest = siteRequest.getApiRequest_();
		List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
		List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
		Long enrollmentKey = o.getEnrollmentKey();
		if(enrollmentKey != null) {
			pks.add(enrollmentKey);
			classes.add("SchoolEnrollment");
		}
		super.sqlPATCHSchoolPayment(o, inheritPk, eventHandler);
	}

	@Override public void aSearchSchoolPaymentUri(String uri, String apiMethod, SearchList<SchoolPayment> searchList) {
		super.aSearchSchoolPaymentUri(uri, apiMethod, searchList);
		if(searchList.getSorts().isEmpty())
			searchList.addSort("paymentDate_indexed_date", ORDER.desc);
	}
}
