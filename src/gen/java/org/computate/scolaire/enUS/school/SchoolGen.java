package org.computate.scolaire.enUS.school;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import org.computate.scolaire.enUS.contexte.SiteContextEnUS;
import java.util.HashMap;
import org.computate.scolaire.enUS.writer.AllWriter;
import org.computate.scolaire.enUS.request.api.ApiRequest;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import io.vertx.core.logging.LoggerFactory;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.computate.scolaire.enUS.wrap.Wrap;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.vertx.core.json.JsonObject;
import org.computate.scolaire.enUS.request.SiteRequestEnUS;
import java.lang.String;
import io.vertx.core.logging.Logger;
import org.computate.scolaire.enUS.year.SchoolYear;
import java.math.RoundingMode;
import java.math.MathContext;
import org.computate.scolaire.enUS.receipt.SchoolReceipt;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.computate.scolaire.enUS.cluster.Cluster;
import java.util.Set;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.solr.client.solrj.SolrClient;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SchoolGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(School.class);

	public static final List<String> ROLES = Arrays.asList("SiteManager");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String School_AName = "a school";
	public static final String School_This = "this ";
	public static final String School_ThisName = "this school";
	public static final String School_A = "a ";
	public static final String School_TheName = "the school";
	public static final String School_NameSingular = "school";
	public static final String School_NamePlural = "schools";
	public static final String School_NameActual = "current school";
	public static final String School_AllName = "all the schools";
	public static final String School_SearchAllNameBy = "search schools by ";
	public static final String School_Title = "schools";
	public static final String School_ThePluralName = "the schools";
	public static final String School_NoNameFound = "no school found";
	public static final String School_NameVar = "school";
	public static final String School_OfName = "of school";
	public static final String School_ANameAdjective = "a school";
	public static final String School_NameAdjectiveSingular = "school";
	public static final String School_NameAdjectivePlural = "schools";
	public static final String School_Color = "pink";
	public static final String School_IconGroup = "regular";
	public static final String School_IconName = "school";

	///////////////
	// schoolKey //
	///////////////

	/**	 The entity schoolKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long schoolKey;
	@JsonIgnore
	public Wrap<Long> schoolKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("schoolKey").o(schoolKey);

	/**	<br/> The entity schoolKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolKey">Find the entity schoolKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolKey(Wrap<Long> c);

	public Long getSchoolKey() {
		return schoolKey;
	}

	public void setSchoolKey(Long schoolKey) {
		this.schoolKey = schoolKey;
		this.schoolKeyWrap.alreadyInitialized = true;
	}
	public School setSchoolKey(String o) {
		if(NumberUtils.isParsable(o))
			this.schoolKey = Long.parseLong(o);
		this.schoolKeyWrap.alreadyInitialized = true;
		return (School)this;
	}
	protected School schoolKeyInit() {
		if(!schoolKeyWrap.alreadyInitialized) {
			_schoolKey(schoolKeyWrap);
			if(schoolKey == null)
				setSchoolKey(schoolKeyWrap.o);
		}
		schoolKeyWrap.alreadyInitialized(true);
		return (School)this;
	}

	public Long solrSchoolKey() {
		return schoolKey;
	}

	public String strSchoolKey() {
		return schoolKey == null ? "" : schoolKey.toString();
	}

	public String jsonSchoolKey() {
		return schoolKey == null ? "" : schoolKey.toString();
	}

	public String nomAffichageSchoolKey() {
		return "key";
	}

	public String htmTooltipSchoolKey() {
		return null;
	}

	public String htmSchoolKey() {
		return schoolKey == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolKey());
	}

	//////////////
	// yearKeys //
	//////////////

	/**	 The entity yearKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> yearKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> yearKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("yearKeys").o(yearKeys);

	/**	<br/> The entity yearKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:yearKeys">Find the entity yearKeys in Solr</a>
	 * <br/>
	 * @param yearKeys is the entity already constructed. 
	 **/
	protected abstract void _yearKeys(List<Long> o);

	public List<Long> getYearKeys() {
		return yearKeys;
	}

	public void setYearKeys(List<Long> yearKeys) {
		this.yearKeys = yearKeys;
		this.yearKeysWrap.alreadyInitialized = true;
	}
	public School addYearKeys(Long...objets) {
		for(Long o : objets) {
			addYearKeys(o);
		}
		return (School)this;
	}
	public School addYearKeys(Long o) {
		if(o != null && !yearKeys.contains(o))
			this.yearKeys.add(o);
		return (School)this;
	}
	public School setYearKeys(JsonArray objets) {
		yearKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addYearKeys(o);
		}
		return (School)this;
	}
	public School addYearKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addYearKeys(p);
		}
		return (School)this;
	}
	protected School yearKeysInit() {
		if(!yearKeysWrap.alreadyInitialized) {
			_yearKeys(yearKeys);
		}
		yearKeysWrap.alreadyInitialized(true);
		return (School)this;
	}

	public List<Long> solrYearKeys() {
		return yearKeys;
	}

	public String strYearKeys() {
		return yearKeys == null ? "" : yearKeys.toString();
	}

	public String jsonYearKeys() {
		return yearKeys == null ? "" : yearKeys.toString();
	}

	public String nomAffichageYearKeys() {
		return "years";
	}

	public String htmTooltipYearKeys() {
		return null;
	}

	public String htmYearKeys() {
		return yearKeys == null ? "" : StringEscapeUtils.escapeHtml4(strYearKeys());
	}

	public void inputYearKeys(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
				e("input")
					.a("type", "text")
					.a("placeholder", "years")
					.a("class", "valueObjectSuggest suggestYearKeys w3-input w3-border w3-cell w3-cell-middle ")
					.a("name", "setYearKeys")
					.a("id", classApiMethodMethod, "_yearKeys")
					.a("autocomplete", "off")
					.a("oninput", "suggestSchoolYearKeys($(this).val() ? searchSchoolYearFilters($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'schoolKey:" + pk + "'}", "], $('#listSchoolYearKeys_", classApiMethodMethod, "'), ", pk, "); ")
				.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "YearKeys ").f().sx(htmYearKeys()).g("span");
			}
		}
	}

	public void htmYearKeys(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolYearKeys").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/year?fq=schoolKey:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-orange w3-hover-orange ").f();
								e("i").a("class", "far fa-calendar-check ").f().g("i");
								sx("years");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate years to this school");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputYearKeys(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listSchoolYearKeys_", classApiMethodMethod).f();
								} g("ul");
								if(
										CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), SchoolYear.ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), SchoolYear.ROLES)
										) {
									{ e("div").a("class", "w3-cell-row ").f();
										e("button")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-orange ")
											.a("id", classApiMethodMethod, "_yearKeys_add")
											.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postSchoolYearVals({ schoolKey: \"", pk, "\" }, function() {}, function() { addError($('#", classApiMethodMethod, "yearKeys')); });")
											.f().sx("add a year")
										.g("button");
									} g("div");
								}
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////
	// receiptKeys //
	/////////////////

	/**	 The entity receiptKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> receiptKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> receiptKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("receiptKeys").o(receiptKeys);

	/**	<br/> The entity receiptKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:receiptKeys">Find the entity receiptKeys in Solr</a>
	 * <br/>
	 * @param receiptKeys is the entity already constructed. 
	 **/
	protected abstract void _receiptKeys(List<Long> o);

	public List<Long> getReceiptKeys() {
		return receiptKeys;
	}

	public void setReceiptKeys(List<Long> receiptKeys) {
		this.receiptKeys = receiptKeys;
		this.receiptKeysWrap.alreadyInitialized = true;
	}
	public School addReceiptKeys(Long...objets) {
		for(Long o : objets) {
			addReceiptKeys(o);
		}
		return (School)this;
	}
	public School addReceiptKeys(Long o) {
		if(o != null && !receiptKeys.contains(o))
			this.receiptKeys.add(o);
		return (School)this;
	}
	public School setReceiptKeys(JsonArray objets) {
		receiptKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addReceiptKeys(o);
		}
		return (School)this;
	}
	public School addReceiptKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addReceiptKeys(p);
		}
		return (School)this;
	}
	protected School receiptKeysInit() {
		if(!receiptKeysWrap.alreadyInitialized) {
			_receiptKeys(receiptKeys);
		}
		receiptKeysWrap.alreadyInitialized(true);
		return (School)this;
	}

	public List<Long> solrReceiptKeys() {
		return receiptKeys;
	}

	public String strReceiptKeys() {
		return receiptKeys == null ? "" : receiptKeys.toString();
	}

	public String jsonReceiptKeys() {
		return receiptKeys == null ? "" : receiptKeys.toString();
	}

	public String nomAffichageReceiptKeys() {
		return "receipts";
	}

	public String htmTooltipReceiptKeys() {
		return null;
	}

	public String htmReceiptKeys() {
		return receiptKeys == null ? "" : StringEscapeUtils.escapeHtml4(strReceiptKeys());
	}

	public void inputReceiptKeys(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
				e("input")
					.a("type", "text")
					.a("placeholder", "receipts")
					.a("class", "valueObjectSuggest suggestReceiptKeys w3-input w3-border w3-cell w3-cell-middle ")
					.a("name", "setReceiptKeys")
					.a("id", classApiMethodMethod, "_receiptKeys")
					.a("autocomplete", "off")
					.a("oninput", "suggestSchoolReceiptKeys($(this).val() ? searchSchoolReceiptFilters($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'schoolKey:" + pk + "'}", "], $('#listSchoolReceiptKeys_", classApiMethodMethod, "'), ", pk, "); ")
				.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "ReceiptKeys ").f().sx(htmReceiptKeys()).g("span");
			}
		}
	}

	public void htmReceiptKeys(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolReceiptKeys").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/receipt?fq=schoolKey:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-light-green w3-hover-light-green ").f();
								e("i").a("class", "fas fa-file-invoice-dollar ").f().g("i");
								sx("receipts");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate receipts to this school");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputReceiptKeys(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listSchoolReceiptKeys_", classApiMethodMethod).f();
								} g("ul");
								if(
										CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), SchoolReceipt.ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), SchoolReceipt.ROLES)
										) {
									{ e("div").a("class", "w3-cell-row ").f();
										e("button")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-light-green ")
											.a("id", classApiMethodMethod, "_receiptKeys_add")
											.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postSchoolReceiptVals({ schoolKey: \"", pk, "\" }, function() {}, function() { addError($('#", classApiMethodMethod, "receiptKeys')); });")
											.f().sx("add a receipt")
										.g("button");
									} g("div");
								}
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////
	// seasonKeys //
	////////////////

	/**	 The entity seasonKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> seasonKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> seasonKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("seasonKeys").o(seasonKeys);

	/**	<br/> The entity seasonKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:seasonKeys">Find the entity seasonKeys in Solr</a>
	 * <br/>
	 * @param seasonKeys is the entity already constructed. 
	 **/
	protected abstract void _seasonKeys(List<Long> o);

	public List<Long> getSeasonKeys() {
		return seasonKeys;
	}

	public void setSeasonKeys(List<Long> seasonKeys) {
		this.seasonKeys = seasonKeys;
		this.seasonKeysWrap.alreadyInitialized = true;
	}
	public School addSeasonKeys(Long...objets) {
		for(Long o : objets) {
			addSeasonKeys(o);
		}
		return (School)this;
	}
	public School addSeasonKeys(Long o) {
		if(o != null && !seasonKeys.contains(o))
			this.seasonKeys.add(o);
		return (School)this;
	}
	public School setSeasonKeys(JsonArray objets) {
		seasonKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSeasonKeys(o);
		}
		return (School)this;
	}
	public School addSeasonKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSeasonKeys(p);
		}
		return (School)this;
	}
	protected School seasonKeysInit() {
		if(!seasonKeysWrap.alreadyInitialized) {
			_seasonKeys(seasonKeys);
		}
		seasonKeysWrap.alreadyInitialized(true);
		return (School)this;
	}

	public List<Long> solrSeasonKeys() {
		return seasonKeys;
	}

	public String strSeasonKeys() {
		return seasonKeys == null ? "" : seasonKeys.toString();
	}

	public String jsonSeasonKeys() {
		return seasonKeys == null ? "" : seasonKeys.toString();
	}

	public String nomAffichageSeasonKeys() {
		return "";
	}

	public String htmTooltipSeasonKeys() {
		return null;
	}

	public String htmSeasonKeys() {
		return seasonKeys == null ? "" : StringEscapeUtils.escapeHtml4(strSeasonKeys());
	}

	/////////////////
	// sessionKeys //
	/////////////////

	/**	 The entity sessionKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> sessionKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> sessionKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("sessionKeys").o(sessionKeys);

	/**	<br/> The entity sessionKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sessionKeys">Find the entity sessionKeys in Solr</a>
	 * <br/>
	 * @param sessionKeys is the entity already constructed. 
	 **/
	protected abstract void _sessionKeys(List<Long> o);

	public List<Long> getSessionKeys() {
		return sessionKeys;
	}

	public void setSessionKeys(List<Long> sessionKeys) {
		this.sessionKeys = sessionKeys;
		this.sessionKeysWrap.alreadyInitialized = true;
	}
	public School addSessionKeys(Long...objets) {
		for(Long o : objets) {
			addSessionKeys(o);
		}
		return (School)this;
	}
	public School addSessionKeys(Long o) {
		if(o != null && !sessionKeys.contains(o))
			this.sessionKeys.add(o);
		return (School)this;
	}
	public School setSessionKeys(JsonArray objets) {
		sessionKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSessionKeys(o);
		}
		return (School)this;
	}
	public School addSessionKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSessionKeys(p);
		}
		return (School)this;
	}
	protected School sessionKeysInit() {
		if(!sessionKeysWrap.alreadyInitialized) {
			_sessionKeys(sessionKeys);
		}
		sessionKeysWrap.alreadyInitialized(true);
		return (School)this;
	}

	public List<Long> solrSessionKeys() {
		return sessionKeys;
	}

	public String strSessionKeys() {
		return sessionKeys == null ? "" : sessionKeys.toString();
	}

	public String jsonSessionKeys() {
		return sessionKeys == null ? "" : sessionKeys.toString();
	}

	public String nomAffichageSessionKeys() {
		return "";
	}

	public String htmTooltipSessionKeys() {
		return null;
	}

	public String htmSessionKeys() {
		return sessionKeys == null ? "" : StringEscapeUtils.escapeHtml4(strSessionKeys());
	}

	//////////////////
	// ageGroupKeys //
	//////////////////

	/**	 The entity ageGroupKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> ageGroupKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> ageGroupKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("ageGroupKeys").o(ageGroupKeys);

	/**	<br/> The entity ageGroupKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:ageGroupKeys">Find the entity ageGroupKeys in Solr</a>
	 * <br/>
	 * @param ageGroupKeys is the entity already constructed. 
	 **/
	protected abstract void _ageGroupKeys(List<Long> o);

	public List<Long> getAgeGroupKeys() {
		return ageGroupKeys;
	}

	public void setAgeGroupKeys(List<Long> ageGroupKeys) {
		this.ageGroupKeys = ageGroupKeys;
		this.ageGroupKeysWrap.alreadyInitialized = true;
	}
	public School addAgeGroupKeys(Long...objets) {
		for(Long o : objets) {
			addAgeGroupKeys(o);
		}
		return (School)this;
	}
	public School addAgeGroupKeys(Long o) {
		if(o != null && !ageGroupKeys.contains(o))
			this.ageGroupKeys.add(o);
		return (School)this;
	}
	public School setAgeGroupKeys(JsonArray objets) {
		ageGroupKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addAgeGroupKeys(o);
		}
		return (School)this;
	}
	public School addAgeGroupKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addAgeGroupKeys(p);
		}
		return (School)this;
	}
	protected School ageGroupKeysInit() {
		if(!ageGroupKeysWrap.alreadyInitialized) {
			_ageGroupKeys(ageGroupKeys);
		}
		ageGroupKeysWrap.alreadyInitialized(true);
		return (School)this;
	}

	public List<Long> solrAgeGroupKeys() {
		return ageGroupKeys;
	}

	public String strAgeGroupKeys() {
		return ageGroupKeys == null ? "" : ageGroupKeys.toString();
	}

	public String jsonAgeGroupKeys() {
		return ageGroupKeys == null ? "" : ageGroupKeys.toString();
	}

	public String nomAffichageAgeGroupKeys() {
		return "";
	}

	public String htmTooltipAgeGroupKeys() {
		return null;
	}

	public String htmAgeGroupKeys() {
		return ageGroupKeys == null ? "" : StringEscapeUtils.escapeHtml4(strAgeGroupKeys());
	}

	///////////////
	// blockKeys //
	///////////////

	/**	 The entity blockKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> blockKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> blockKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("blockKeys").o(blockKeys);

	/**	<br/> The entity blockKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:blockKeys">Find the entity blockKeys in Solr</a>
	 * <br/>
	 * @param blockKeys is the entity already constructed. 
	 **/
	protected abstract void _blockKeys(List<Long> o);

	public List<Long> getBlockKeys() {
		return blockKeys;
	}

	public void setBlockKeys(List<Long> blockKeys) {
		this.blockKeys = blockKeys;
		this.blockKeysWrap.alreadyInitialized = true;
	}
	public School addBlockKeys(Long...objets) {
		for(Long o : objets) {
			addBlockKeys(o);
		}
		return (School)this;
	}
	public School addBlockKeys(Long o) {
		if(o != null && !blockKeys.contains(o))
			this.blockKeys.add(o);
		return (School)this;
	}
	public School setBlockKeys(JsonArray objets) {
		blockKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addBlockKeys(o);
		}
		return (School)this;
	}
	public School addBlockKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addBlockKeys(p);
		}
		return (School)this;
	}
	protected School blockKeysInit() {
		if(!blockKeysWrap.alreadyInitialized) {
			_blockKeys(blockKeys);
		}
		blockKeysWrap.alreadyInitialized(true);
		return (School)this;
	}

	public List<Long> solrBlockKeys() {
		return blockKeys;
	}

	public String strBlockKeys() {
		return blockKeys == null ? "" : blockKeys.toString();
	}

	public String jsonBlockKeys() {
		return blockKeys == null ? "" : blockKeys.toString();
	}

	public String nomAffichageBlockKeys() {
		return "";
	}

	public String htmTooltipBlockKeys() {
		return null;
	}

	public String htmBlockKeys() {
		return blockKeys == null ? "" : StringEscapeUtils.escapeHtml4(strBlockKeys());
	}

	///////////////
	// childKeys //
	///////////////

	/**	 The entity childKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> childKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> childKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("childKeys").o(childKeys);

	/**	<br/> The entity childKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:childKeys">Find the entity childKeys in Solr</a>
	 * <br/>
	 * @param childKeys is the entity already constructed. 
	 **/
	protected abstract void _childKeys(List<Long> o);

	public List<Long> getChildKeys() {
		return childKeys;
	}

	public void setChildKeys(List<Long> childKeys) {
		this.childKeys = childKeys;
		this.childKeysWrap.alreadyInitialized = true;
	}
	public School addChildKeys(Long...objets) {
		for(Long o : objets) {
			addChildKeys(o);
		}
		return (School)this;
	}
	public School addChildKeys(Long o) {
		if(o != null && !childKeys.contains(o))
			this.childKeys.add(o);
		return (School)this;
	}
	public School setChildKeys(JsonArray objets) {
		childKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addChildKeys(o);
		}
		return (School)this;
	}
	public School addChildKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addChildKeys(p);
		}
		return (School)this;
	}
	protected School childKeysInit() {
		if(!childKeysWrap.alreadyInitialized) {
			_childKeys(childKeys);
		}
		childKeysWrap.alreadyInitialized(true);
		return (School)this;
	}

	public List<Long> solrChildKeys() {
		return childKeys;
	}

	public String strChildKeys() {
		return childKeys == null ? "" : childKeys.toString();
	}

	public String jsonChildKeys() {
		return childKeys == null ? "" : childKeys.toString();
	}

	public String nomAffichageChildKeys() {
		return "";
	}

	public String htmTooltipChildKeys() {
		return null;
	}

	public String htmChildKeys() {
		return childKeys == null ? "" : StringEscapeUtils.escapeHtml4(strChildKeys());
	}

	///////////////////
	// educationSort //
	///////////////////

	/**	 The entity educationSort
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer educationSort;
	@JsonIgnore
	public Wrap<Integer> educationSortWrap = new Wrap<Integer>().p(this).c(Integer.class).var("educationSort").o(educationSort);

	/**	<br/> The entity educationSort
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:educationSort">Find the entity educationSort in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _educationSort(Wrap<Integer> c);

	public Integer getEducationSort() {
		return educationSort;
	}

	public void setEducationSort(Integer educationSort) {
		this.educationSort = educationSort;
		this.educationSortWrap.alreadyInitialized = true;
	}
	public School setEducationSort(String o) {
		if(NumberUtils.isParsable(o))
			this.educationSort = Integer.parseInt(o);
		this.educationSortWrap.alreadyInitialized = true;
		return (School)this;
	}
	protected School educationSortInit() {
		if(!educationSortWrap.alreadyInitialized) {
			_educationSort(educationSortWrap);
			if(educationSort == null)
				setEducationSort(educationSortWrap.o);
		}
		educationSortWrap.alreadyInitialized(true);
		return (School)this;
	}

	public Integer solrEducationSort() {
		return educationSort;
	}

	public String strEducationSort() {
		return educationSort == null ? "" : educationSort.toString();
	}

	public String jsonEducationSort() {
		return educationSort == null ? "" : educationSort.toString();
	}

	public String nomAffichageEducationSort() {
		return "";
	}

	public String htmTooltipEducationSort() {
		return null;
	}

	public String htmEducationSort() {
		return educationSort == null ? "" : StringEscapeUtils.escapeHtml4(strEducationSort());
	}

	////////////////
	// schoolSort //
	////////////////

	/**	 The entity schoolSort
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer schoolSort;
	@JsonIgnore
	public Wrap<Integer> schoolSortWrap = new Wrap<Integer>().p(this).c(Integer.class).var("schoolSort").o(schoolSort);

	/**	<br/> The entity schoolSort
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolSort">Find the entity schoolSort in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolSort(Wrap<Integer> c);

	public Integer getSchoolSort() {
		return schoolSort;
	}

	public void setSchoolSort(Integer schoolSort) {
		this.schoolSort = schoolSort;
		this.schoolSortWrap.alreadyInitialized = true;
	}
	public School setSchoolSort(String o) {
		if(NumberUtils.isParsable(o))
			this.schoolSort = Integer.parseInt(o);
		this.schoolSortWrap.alreadyInitialized = true;
		return (School)this;
	}
	protected School schoolSortInit() {
		if(!schoolSortWrap.alreadyInitialized) {
			_schoolSort(schoolSortWrap);
			if(schoolSort == null)
				setSchoolSort(schoolSortWrap.o);
		}
		schoolSortWrap.alreadyInitialized(true);
		return (School)this;
	}

	public Integer solrSchoolSort() {
		return schoolSort;
	}

	public String strSchoolSort() {
		return schoolSort == null ? "" : schoolSort.toString();
	}

	public String jsonSchoolSort() {
		return schoolSort == null ? "" : schoolSort.toString();
	}

	public String nomAffichageSchoolSort() {
		return "";
	}

	public String htmTooltipSchoolSort() {
		return null;
	}

	public String htmSchoolSort() {
		return schoolSort == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolSort());
	}

	////////////////
	// schoolName //
	////////////////

	/**	 The entity schoolName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolName;
	@JsonIgnore
	public Wrap<String> schoolNameWrap = new Wrap<String>().p(this).c(String.class).var("schoolName").o(schoolName);

	/**	<br/> The entity schoolName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolName">Find the entity schoolName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolName(Wrap<String> c);

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
		this.schoolNameWrap.alreadyInitialized = true;
	}
	protected School schoolNameInit() {
		if(!schoolNameWrap.alreadyInitialized) {
			_schoolName(schoolNameWrap);
			if(schoolName == null)
				setSchoolName(schoolNameWrap.o);
		}
		schoolNameWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolName() {
		return schoolName;
	}

	public String strSchoolName() {
		return schoolName == null ? "" : schoolName;
	}

	public String jsonSchoolName() {
		return schoolName == null ? "" : schoolName;
	}

	public String nomAffichageSchoolName() {
		return "name of the school";
	}

	public String htmTooltipSchoolName() {
		return null;
	}

	public String htmSchoolName() {
		return schoolName == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolName());
	}

	public void inputSchoolName(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "name of the school")
				.a("id", classApiMethodMethod, "_schoolName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSchoolName classSchool inputSchool", pk, "SchoolName w3-input w3-border ");
					a("name", "setSchoolName");
				} else {
					a("class", "valueSchoolName w3-input w3-border classSchool inputSchool", pk, "SchoolName w3-input w3-border ");
					a("name", "schoolName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSchoolName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_schoolName')); }, function() { addError($('#", classApiMethodMethod, "_schoolName')); }); ");
				}
				a("value", strSchoolName())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "SchoolName ").f().sx(htmSchoolName()).g("span");
			}
		}
	}

	public void htmSchoolName(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolSchoolName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_schoolName").a("class", "").f().sx("name of the school").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSchoolName(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_schoolName')); $('#", classApiMethodMethod, "_schoolName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SchoolForm :input[name=pk]').val() }], 'setSchoolName', null, function() { addGlow($('#", classApiMethodMethod, "_schoolName')); }, function() { addError($('#", classApiMethodMethod, "_schoolName')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	///////////////////////
	// schoolPhoneNumber //
	///////////////////////

	/**	 The entity schoolPhoneNumber
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolPhoneNumber;
	@JsonIgnore
	public Wrap<String> schoolPhoneNumberWrap = new Wrap<String>().p(this).c(String.class).var("schoolPhoneNumber").o(schoolPhoneNumber);

	/**	<br/> The entity schoolPhoneNumber
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolPhoneNumber">Find the entity schoolPhoneNumber in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolPhoneNumber(Wrap<String> c);

	public String getSchoolPhoneNumber() {
		return schoolPhoneNumber;
	}

	public void setSchoolPhoneNumber(String schoolPhoneNumber) {
		this.schoolPhoneNumber = schoolPhoneNumber;
		this.schoolPhoneNumberWrap.alreadyInitialized = true;
	}
	protected School schoolPhoneNumberInit() {
		if(!schoolPhoneNumberWrap.alreadyInitialized) {
			_schoolPhoneNumber(schoolPhoneNumberWrap);
			if(schoolPhoneNumber == null)
				setSchoolPhoneNumber(schoolPhoneNumberWrap.o);
		}
		schoolPhoneNumberWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolPhoneNumber() {
		return schoolPhoneNumber;
	}

	public String strSchoolPhoneNumber() {
		return schoolPhoneNumber == null ? "" : schoolPhoneNumber;
	}

	public String jsonSchoolPhoneNumber() {
		return schoolPhoneNumber == null ? "" : schoolPhoneNumber;
	}

	public String nomAffichageSchoolPhoneNumber() {
		return "phone number";
	}

	public String htmTooltipSchoolPhoneNumber() {
		return null;
	}

	public String htmSchoolPhoneNumber() {
		return schoolPhoneNumber == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolPhoneNumber());
	}

	public void inputSchoolPhoneNumber(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "phone number")
				.a("id", classApiMethodMethod, "_schoolPhoneNumber");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSchoolPhoneNumber classSchool inputSchool", pk, "SchoolPhoneNumber w3-input w3-border ");
					a("name", "setSchoolPhoneNumber");
				} else {
					a("class", "valueSchoolPhoneNumber w3-input w3-border classSchool inputSchool", pk, "SchoolPhoneNumber w3-input w3-border ");
					a("name", "schoolPhoneNumber");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSchoolPhoneNumber', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_schoolPhoneNumber')); }, function() { addError($('#", classApiMethodMethod, "_schoolPhoneNumber')); }); ");
				}
				a("value", strSchoolPhoneNumber())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "SchoolPhoneNumber ").f().sx(htmSchoolPhoneNumber()).g("span");
			}
		}
	}

	public void htmSchoolPhoneNumber(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolSchoolPhoneNumber").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_schoolPhoneNumber").a("class", "").f().sx("phone number").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSchoolPhoneNumber(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_schoolPhoneNumber')); $('#", classApiMethodMethod, "_schoolPhoneNumber').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SchoolForm :input[name=pk]').val() }], 'setSchoolPhoneNumber', null, function() { addGlow($('#", classApiMethodMethod, "_schoolPhoneNumber')); }, function() { addError($('#", classApiMethodMethod, "_schoolPhoneNumber')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////
	// schoolForm //
	////////////////

	/**	 The entity schoolForm
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolForm;
	@JsonIgnore
	public Wrap<String> schoolFormWrap = new Wrap<String>().p(this).c(String.class).var("schoolForm").o(schoolForm);

	/**	<br/> The entity schoolForm
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolForm">Find the entity schoolForm in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolForm(Wrap<String> c);

	public String getSchoolForm() {
		return schoolForm;
	}

	public void setSchoolForm(String schoolForm) {
		this.schoolForm = schoolForm;
		this.schoolFormWrap.alreadyInitialized = true;
	}
	protected School schoolFormInit() {
		if(!schoolFormWrap.alreadyInitialized) {
			_schoolForm(schoolFormWrap);
			if(schoolForm == null)
				setSchoolForm(schoolFormWrap.o);
		}
		schoolFormWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolForm() {
		return schoolForm;
	}

	public String strSchoolForm() {
		return schoolForm == null ? "" : schoolForm;
	}

	public String jsonSchoolForm() {
		return schoolForm == null ? "" : schoolForm;
	}

	public String nomAffichageSchoolForm() {
		return "school form name";
	}

	public String htmTooltipSchoolForm() {
		return null;
	}

	public String htmSchoolForm() {
		return schoolForm == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolForm());
	}

	public void inputSchoolForm(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "school form name")
				.a("id", classApiMethodMethod, "_schoolForm");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSchoolForm classSchool inputSchool", pk, "SchoolForm w3-input w3-border ");
					a("name", "setSchoolForm");
				} else {
					a("class", "valueSchoolForm w3-input w3-border classSchool inputSchool", pk, "SchoolForm w3-input w3-border ");
					a("name", "schoolForm");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSchoolForm', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_schoolForm')); }, function() { addError($('#", classApiMethodMethod, "_schoolForm')); }); ");
				}
				a("value", strSchoolForm())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "SchoolForm ").f().sx(htmSchoolForm()).g("span");
			}
		}
	}

	public void htmSchoolForm(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolSchoolForm").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_schoolForm").a("class", "").f().sx("school form name").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSchoolForm(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_schoolForm')); $('#", classApiMethodMethod, "_schoolForm').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SchoolForm :input[name=pk]').val() }], 'setSchoolForm', null, function() { addGlow($('#", classApiMethodMethod, "_schoolForm')); }, function() { addError($('#", classApiMethodMethod, "_schoolForm')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////////
	// schoolNumber //
	//////////////////

	/**	 The entity schoolNumber
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer schoolNumber;
	@JsonIgnore
	public Wrap<Integer> schoolNumberWrap = new Wrap<Integer>().p(this).c(Integer.class).var("schoolNumber").o(schoolNumber);

	/**	<br/> The entity schoolNumber
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolNumber">Find the entity schoolNumber in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolNumber(Wrap<Integer> c);

	public Integer getSchoolNumber() {
		return schoolNumber;
	}

	public void setSchoolNumber(Integer schoolNumber) {
		this.schoolNumber = schoolNumber;
		this.schoolNumberWrap.alreadyInitialized = true;
	}
	public School setSchoolNumber(String o) {
		if(NumberUtils.isParsable(o))
			this.schoolNumber = Integer.parseInt(o);
		this.schoolNumberWrap.alreadyInitialized = true;
		return (School)this;
	}
	protected School schoolNumberInit() {
		if(!schoolNumberWrap.alreadyInitialized) {
			_schoolNumber(schoolNumberWrap);
			if(schoolNumber == null)
				setSchoolNumber(schoolNumberWrap.o);
		}
		schoolNumberWrap.alreadyInitialized(true);
		return (School)this;
	}

	public Integer solrSchoolNumber() {
		return schoolNumber;
	}

	public String strSchoolNumber() {
		return schoolNumber == null ? "" : schoolNumber.toString();
	}

	public String jsonSchoolNumber() {
		return schoolNumber == null ? "" : schoolNumber.toString();
	}

	public String nomAffichageSchoolNumber() {
		return "school number";
	}

	public String htmTooltipSchoolNumber() {
		return null;
	}

	public String htmSchoolNumber() {
		return schoolNumber == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolNumber());
	}

	public void inputSchoolNumber(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "school number")
				.a("id", classApiMethodMethod, "_schoolNumber");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSchoolNumber classSchool inputSchool", pk, "SchoolNumber w3-input w3-border ");
					a("name", "setSchoolNumber");
				} else {
					a("class", "valueSchoolNumber w3-input w3-border classSchool inputSchool", pk, "SchoolNumber w3-input w3-border ");
					a("name", "schoolNumber");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSchoolNumber', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_schoolNumber')); }, function() { addError($('#", classApiMethodMethod, "_schoolNumber')); }); ");
				}
				a("value", strSchoolNumber())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "SchoolNumber ").f().sx(htmSchoolNumber()).g("span");
			}
		}
	}

	public void htmSchoolNumber(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolSchoolNumber").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_schoolNumber").a("class", "").f().sx("school number").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSchoolNumber(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_schoolNumber')); $('#", classApiMethodMethod, "_schoolNumber').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SchoolForm :input[name=pk]').val() }], 'setSchoolNumber', null, function() { addGlow($('#", classApiMethodMethod, "_schoolNumber')); }, function() { addError($('#", classApiMethodMethod, "_schoolNumber')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////////////
	// schoolAdministratorName //
	/////////////////////////////

	/**	 The entity schoolAdministratorName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolAdministratorName;
	@JsonIgnore
	public Wrap<String> schoolAdministratorNameWrap = new Wrap<String>().p(this).c(String.class).var("schoolAdministratorName").o(schoolAdministratorName);

	/**	<br/> The entity schoolAdministratorName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolAdministratorName">Find the entity schoolAdministratorName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolAdministratorName(Wrap<String> c);

	public String getSchoolAdministratorName() {
		return schoolAdministratorName;
	}

	public void setSchoolAdministratorName(String schoolAdministratorName) {
		this.schoolAdministratorName = schoolAdministratorName;
		this.schoolAdministratorNameWrap.alreadyInitialized = true;
	}
	protected School schoolAdministratorNameInit() {
		if(!schoolAdministratorNameWrap.alreadyInitialized) {
			_schoolAdministratorName(schoolAdministratorNameWrap);
			if(schoolAdministratorName == null)
				setSchoolAdministratorName(schoolAdministratorNameWrap.o);
		}
		schoolAdministratorNameWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolAdministratorName() {
		return schoolAdministratorName;
	}

	public String strSchoolAdministratorName() {
		return schoolAdministratorName == null ? "" : schoolAdministratorName;
	}

	public String jsonSchoolAdministratorName() {
		return schoolAdministratorName == null ? "" : schoolAdministratorName;
	}

	public String nomAffichageSchoolAdministratorName() {
		return "administrator of the school";
	}

	public String htmTooltipSchoolAdministratorName() {
		return null;
	}

	public String htmSchoolAdministratorName() {
		return schoolAdministratorName == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolAdministratorName());
	}

	public void inputSchoolAdministratorName(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "administrator of the school")
				.a("id", classApiMethodMethod, "_schoolAdministratorName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSchoolAdministratorName classSchool inputSchool", pk, "SchoolAdministratorName w3-input w3-border ");
					a("name", "setSchoolAdministratorName");
				} else {
					a("class", "valueSchoolAdministratorName w3-input w3-border classSchool inputSchool", pk, "SchoolAdministratorName w3-input w3-border ");
					a("name", "schoolAdministratorName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSchoolAdministratorName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_schoolAdministratorName')); }, function() { addError($('#", classApiMethodMethod, "_schoolAdministratorName')); }); ");
				}
				a("value", strSchoolAdministratorName())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "SchoolAdministratorName ").f().sx(htmSchoolAdministratorName()).g("span");
			}
		}
	}

	public void htmSchoolAdministratorName(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolSchoolAdministratorName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_schoolAdministratorName").a("class", "").f().sx("administrator of the school").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSchoolAdministratorName(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_schoolAdministratorName')); $('#", classApiMethodMethod, "_schoolAdministratorName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SchoolForm :input[name=pk]').val() }], 'setSchoolAdministratorName', null, function() { addGlow($('#", classApiMethodMethod, "_schoolAdministratorName')); }, function() { addError($('#", classApiMethodMethod, "_schoolAdministratorName')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////
	// schoolEmail //
	/////////////////

	/**	 The entity schoolEmail
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolEmail;
	@JsonIgnore
	public Wrap<String> schoolEmailWrap = new Wrap<String>().p(this).c(String.class).var("schoolEmail").o(schoolEmail);

	/**	<br/> The entity schoolEmail
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolEmail">Find the entity schoolEmail in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolEmail(Wrap<String> c);

	public String getSchoolEmail() {
		return schoolEmail;
	}

	public void setSchoolEmail(String schoolEmail) {
		this.schoolEmail = schoolEmail;
		this.schoolEmailWrap.alreadyInitialized = true;
	}
	protected School schoolEmailInit() {
		if(!schoolEmailWrap.alreadyInitialized) {
			_schoolEmail(schoolEmailWrap);
			if(schoolEmail == null)
				setSchoolEmail(schoolEmailWrap.o);
		}
		schoolEmailWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolEmail() {
		return schoolEmail;
	}

	public String strSchoolEmail() {
		return schoolEmail == null ? "" : schoolEmail;
	}

	public String jsonSchoolEmail() {
		return schoolEmail == null ? "" : schoolEmail;
	}

	public String nomAffichageSchoolEmail() {
		return "email";
	}

	public String htmTooltipSchoolEmail() {
		return null;
	}

	public String htmSchoolEmail() {
		return schoolEmail == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolEmail());
	}

	public void inputSchoolEmail(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "email")
				.a("id", classApiMethodMethod, "_schoolEmail");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSchoolEmail classSchool inputSchool", pk, "SchoolEmail w3-input w3-border ");
					a("name", "setSchoolEmail");
				} else {
					a("class", "valueSchoolEmail w3-input w3-border classSchool inputSchool", pk, "SchoolEmail w3-input w3-border ");
					a("name", "schoolEmail");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSchoolEmail', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_schoolEmail')); }, function() { addError($('#", classApiMethodMethod, "_schoolEmail')); }); ");
				}
				a("value", strSchoolEmail())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "SchoolEmail ").f().sx(htmSchoolEmail()).g("span");
			}
		}
	}

	public void htmSchoolEmail(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolSchoolEmail").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_schoolEmail").a("class", "").f().sx("email").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSchoolEmail(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_schoolEmail')); $('#", classApiMethodMethod, "_schoolEmail').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SchoolForm :input[name=pk]').val() }], 'setSchoolEmail', null, function() { addGlow($('#", classApiMethodMethod, "_schoolEmail')); }, function() { addError($('#", classApiMethodMethod, "_schoolEmail')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////
	// schoolEmailFrom //
	/////////////////////

	/**	 The entity schoolEmailFrom
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolEmailFrom;
	@JsonIgnore
	public Wrap<String> schoolEmailFromWrap = new Wrap<String>().p(this).c(String.class).var("schoolEmailFrom").o(schoolEmailFrom);

	/**	<br/> The entity schoolEmailFrom
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolEmailFrom">Find the entity schoolEmailFrom in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolEmailFrom(Wrap<String> c);

	public String getSchoolEmailFrom() {
		return schoolEmailFrom;
	}

	public void setSchoolEmailFrom(String schoolEmailFrom) {
		this.schoolEmailFrom = schoolEmailFrom;
		this.schoolEmailFromWrap.alreadyInitialized = true;
	}
	protected School schoolEmailFromInit() {
		if(!schoolEmailFromWrap.alreadyInitialized) {
			_schoolEmailFrom(schoolEmailFromWrap);
			if(schoolEmailFrom == null)
				setSchoolEmailFrom(schoolEmailFromWrap.o);
		}
		schoolEmailFromWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolEmailFrom() {
		return schoolEmailFrom;
	}

	public String strSchoolEmailFrom() {
		return schoolEmailFrom == null ? "" : schoolEmailFrom;
	}

	public String jsonSchoolEmailFrom() {
		return schoolEmailFrom == null ? "" : schoolEmailFrom;
	}

	public String nomAffichageSchoolEmailFrom() {
		return "emails from (1 only)";
	}

	public String htmTooltipSchoolEmailFrom() {
		return null;
	}

	public String htmSchoolEmailFrom() {
		return schoolEmailFrom == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolEmailFrom());
	}

	public void inputSchoolEmailFrom(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "emails from (1 only)")
				.a("id", classApiMethodMethod, "_schoolEmailFrom");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSchoolEmailFrom classSchool inputSchool", pk, "SchoolEmailFrom w3-input w3-border ");
					a("name", "setSchoolEmailFrom");
				} else {
					a("class", "valueSchoolEmailFrom w3-input w3-border classSchool inputSchool", pk, "SchoolEmailFrom w3-input w3-border ");
					a("name", "schoolEmailFrom");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSchoolEmailFrom', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_schoolEmailFrom')); }, function() { addError($('#", classApiMethodMethod, "_schoolEmailFrom')); }); ");
				}
				a("value", strSchoolEmailFrom())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "SchoolEmailFrom ").f().sx(htmSchoolEmailFrom()).g("span");
			}
		}
	}

	public void htmSchoolEmailFrom(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolSchoolEmailFrom").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_schoolEmailFrom").a("class", "").f().sx("emails from (1 only)").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSchoolEmailFrom(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_schoolEmailFrom')); $('#", classApiMethodMethod, "_schoolEmailFrom').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SchoolForm :input[name=pk]').val() }], 'setSchoolEmailFrom', null, function() { addGlow($('#", classApiMethodMethod, "_schoolEmailFrom')); }, function() { addError($('#", classApiMethodMethod, "_schoolEmailFrom')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	///////////////////
	// schoolEmailTo //
	///////////////////

	/**	 The entity schoolEmailTo
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolEmailTo;
	@JsonIgnore
	public Wrap<String> schoolEmailToWrap = new Wrap<String>().p(this).c(String.class).var("schoolEmailTo").o(schoolEmailTo);

	/**	<br/> The entity schoolEmailTo
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolEmailTo">Find the entity schoolEmailTo in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolEmailTo(Wrap<String> c);

	public String getSchoolEmailTo() {
		return schoolEmailTo;
	}

	public void setSchoolEmailTo(String schoolEmailTo) {
		this.schoolEmailTo = schoolEmailTo;
		this.schoolEmailToWrap.alreadyInitialized = true;
	}
	protected School schoolEmailToInit() {
		if(!schoolEmailToWrap.alreadyInitialized) {
			_schoolEmailTo(schoolEmailToWrap);
			if(schoolEmailTo == null)
				setSchoolEmailTo(schoolEmailToWrap.o);
		}
		schoolEmailToWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolEmailTo() {
		return schoolEmailTo;
	}

	public String strSchoolEmailTo() {
		return schoolEmailTo == null ? "" : schoolEmailTo;
	}

	public String jsonSchoolEmailTo() {
		return schoolEmailTo == null ? "" : schoolEmailTo;
	}

	public String nomAffichageSchoolEmailTo() {
		return "emails to (1 or more by ,)";
	}

	public String htmTooltipSchoolEmailTo() {
		return null;
	}

	public String htmSchoolEmailTo() {
		return schoolEmailTo == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolEmailTo());
	}

	public void inputSchoolEmailTo(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "emails to (1 or more by ,)")
				.a("id", classApiMethodMethod, "_schoolEmailTo");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSchoolEmailTo classSchool inputSchool", pk, "SchoolEmailTo w3-input w3-border ");
					a("name", "setSchoolEmailTo");
				} else {
					a("class", "valueSchoolEmailTo w3-input w3-border classSchool inputSchool", pk, "SchoolEmailTo w3-input w3-border ");
					a("name", "schoolEmailTo");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSchoolEmailTo', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_schoolEmailTo')); }, function() { addError($('#", classApiMethodMethod, "_schoolEmailTo')); }); ");
				}
				a("value", strSchoolEmailTo())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "SchoolEmailTo ").f().sx(htmSchoolEmailTo()).g("span");
			}
		}
	}

	public void htmSchoolEmailTo(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolSchoolEmailTo").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_schoolEmailTo").a("class", "").f().sx("emails to (1 or more by ,)").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSchoolEmailTo(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_schoolEmailTo')); $('#", classApiMethodMethod, "_schoolEmailTo').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SchoolForm :input[name=pk]').val() }], 'setSchoolEmailTo', null, function() { addGlow($('#", classApiMethodMethod, "_schoolEmailTo')); }, function() { addError($('#", classApiMethodMethod, "_schoolEmailTo')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////////
	// schoolLocation //
	////////////////////

	/**	 The entity schoolLocation
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolLocation;
	@JsonIgnore
	public Wrap<String> schoolLocationWrap = new Wrap<String>().p(this).c(String.class).var("schoolLocation").o(schoolLocation);

	/**	<br/> The entity schoolLocation
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolLocation">Find the entity schoolLocation in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolLocation(Wrap<String> c);

	public String getSchoolLocation() {
		return schoolLocation;
	}

	public void setSchoolLocation(String schoolLocation) {
		this.schoolLocation = schoolLocation;
		this.schoolLocationWrap.alreadyInitialized = true;
	}
	protected School schoolLocationInit() {
		if(!schoolLocationWrap.alreadyInitialized) {
			_schoolLocation(schoolLocationWrap);
			if(schoolLocation == null)
				setSchoolLocation(schoolLocationWrap.o);
		}
		schoolLocationWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolLocation() {
		return schoolLocation;
	}

	public String strSchoolLocation() {
		return schoolLocation == null ? "" : schoolLocation;
	}

	public String jsonSchoolLocation() {
		return schoolLocation == null ? "" : schoolLocation;
	}

	public String nomAffichageSchoolLocation() {
		return "location";
	}

	public String htmTooltipSchoolLocation() {
		return null;
	}

	public String htmSchoolLocation() {
		return schoolLocation == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolLocation());
	}

	public void inputSchoolLocation(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "location")
				.a("id", classApiMethodMethod, "_schoolLocation");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSchoolLocation classSchool inputSchool", pk, "SchoolLocation w3-input w3-border ");
					a("name", "setSchoolLocation");
				} else {
					a("class", "valueSchoolLocation w3-input w3-border classSchool inputSchool", pk, "SchoolLocation w3-input w3-border ");
					a("name", "schoolLocation");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSchoolLocation', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_schoolLocation')); }, function() { addError($('#", classApiMethodMethod, "_schoolLocation')); }); ");
				}
				a("value", strSchoolLocation())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "SchoolLocation ").f().sx(htmSchoolLocation()).g("span");
			}
		}
	}

	public void htmSchoolLocation(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolSchoolLocation").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_schoolLocation").a("class", "").f().sx("location").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSchoolLocation(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_schoolLocation')); $('#", classApiMethodMethod, "_schoolLocation').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SchoolForm :input[name=pk]').val() }], 'setSchoolLocation', null, function() { addGlow($('#", classApiMethodMethod, "_schoolLocation')); }, function() { addError($('#", classApiMethodMethod, "_schoolLocation')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	///////////////////
	// schoolAddress //
	///////////////////

	/**	 The entity schoolAddress
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolAddress;
	@JsonIgnore
	public Wrap<String> schoolAddressWrap = new Wrap<String>().p(this).c(String.class).var("schoolAddress").o(schoolAddress);

	/**	<br/> The entity schoolAddress
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolAddress">Find the entity schoolAddress in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolAddress(Wrap<String> c);

	public String getSchoolAddress() {
		return schoolAddress;
	}

	public void setSchoolAddress(String schoolAddress) {
		this.schoolAddress = schoolAddress;
		this.schoolAddressWrap.alreadyInitialized = true;
	}
	protected School schoolAddressInit() {
		if(!schoolAddressWrap.alreadyInitialized) {
			_schoolAddress(schoolAddressWrap);
			if(schoolAddress == null)
				setSchoolAddress(schoolAddressWrap.o);
		}
		schoolAddressWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolAddress() {
		return schoolAddress;
	}

	public String strSchoolAddress() {
		return schoolAddress == null ? "" : schoolAddress;
	}

	public String jsonSchoolAddress() {
		return schoolAddress == null ? "" : schoolAddress;
	}

	public String nomAffichageSchoolAddress() {
		return "address";
	}

	public String htmTooltipSchoolAddress() {
		return null;
	}

	public String htmSchoolAddress() {
		return schoolAddress == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolAddress());
	}

	public void inputSchoolAddress(String classApiMethodMethod) {
		School s = (School)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("textarea")
				.a("placeholder", "address")
				.a("id", classApiMethodMethod, "_schoolAddress");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSchoolAddress classSchool inputSchool", pk, "SchoolAddress w3-input w3-border ");
					a("name", "setSchoolAddress");
				} else {
					a("class", "valueSchoolAddress w3-input w3-border classSchool inputSchool", pk, "SchoolAddress w3-input w3-border ");
					a("name", "schoolAddress");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSchoolAddress', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_schoolAddress')); }, function() { addError($('#", classApiMethodMethod, "_schoolAddress')); }); ");
				}
			f().sx(strSchoolAddress()).g("textarea");

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varSchool", pk, "SchoolAddress ").f().sx(htmSchoolAddress()).g("span");
			}
		}
	}

	public void htmSchoolAddress(String classApiMethodMethod) {
		School s = (School)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SchoolSchoolAddress").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pink ").f();
							e("label").a("for", classApiMethodMethod, "_schoolAddress").a("class", "").f().sx("address").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSchoolAddress(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pink ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_schoolAddress')); $('#", classApiMethodMethod, "_schoolAddress').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SchoolForm :input[name=pk]').val() }], 'setSchoolAddress', null, function() { addGlow($('#", classApiMethodMethod, "_schoolAddress')); }, function() { addError($('#", classApiMethodMethod, "_schoolAddress')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////
	// schoolShortName //
	/////////////////////

	/**	 The entity schoolShortName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolShortName;
	@JsonIgnore
	public Wrap<String> schoolShortNameWrap = new Wrap<String>().p(this).c(String.class).var("schoolShortName").o(schoolShortName);

	/**	<br/> The entity schoolShortName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolShortName">Find the entity schoolShortName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolShortName(Wrap<String> c);

	public String getSchoolShortName() {
		return schoolShortName;
	}

	public void setSchoolShortName(String schoolShortName) {
		this.schoolShortName = schoolShortName;
		this.schoolShortNameWrap.alreadyInitialized = true;
	}
	protected School schoolShortNameInit() {
		if(!schoolShortNameWrap.alreadyInitialized) {
			_schoolShortName(schoolShortNameWrap);
			if(schoolShortName == null)
				setSchoolShortName(schoolShortNameWrap.o);
		}
		schoolShortNameWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolShortName() {
		return schoolShortName;
	}

	public String strSchoolShortName() {
		return schoolShortName == null ? "" : schoolShortName;
	}

	public String jsonSchoolShortName() {
		return schoolShortName == null ? "" : schoolShortName;
	}

	public String nomAffichageSchoolShortName() {
		return "r: ecoleNom";
	}

	public String htmTooltipSchoolShortName() {
		return null;
	}

	public String htmSchoolShortName() {
		return schoolShortName == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolShortName());
	}

	////////////////////////
	// schoolCompleteName //
	////////////////////////

	/**	 The entity schoolCompleteName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String schoolCompleteName;
	@JsonIgnore
	public Wrap<String> schoolCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("schoolCompleteName").o(schoolCompleteName);

	/**	<br/> The entity schoolCompleteName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.school.School&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolCompleteName">Find the entity schoolCompleteName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _schoolCompleteName(Wrap<String> c);

	public String getSchoolCompleteName() {
		return schoolCompleteName;
	}

	public void setSchoolCompleteName(String schoolCompleteName) {
		this.schoolCompleteName = schoolCompleteName;
		this.schoolCompleteNameWrap.alreadyInitialized = true;
	}
	protected School schoolCompleteNameInit() {
		if(!schoolCompleteNameWrap.alreadyInitialized) {
			_schoolCompleteName(schoolCompleteNameWrap);
			if(schoolCompleteName == null)
				setSchoolCompleteName(schoolCompleteNameWrap.o);
		}
		schoolCompleteNameWrap.alreadyInitialized(true);
		return (School)this;
	}

	public String solrSchoolCompleteName() {
		return schoolCompleteName;
	}

	public String strSchoolCompleteName() {
		return schoolCompleteName == null ? "" : schoolCompleteName;
	}

	public String jsonSchoolCompleteName() {
		return schoolCompleteName == null ? "" : schoolCompleteName;
	}

	public String nomAffichageSchoolCompleteName() {
		return "name";
	}

	public String htmTooltipSchoolCompleteName() {
		return null;
	}

	public String htmSchoolCompleteName() {
		return schoolCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strSchoolCompleteName());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSchool = false;

	public School initDeepSchool(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSchool) {
			alreadyInitializedSchool = true;
			initDeepSchool();
		}
		return (School)this;
	}

	public void initDeepSchool() {
		initSchool();
		super.initDeepCluster(siteRequest_);
	}

	public void initSchool() {
		schoolKeyInit();
		yearKeysInit();
		receiptKeysInit();
		seasonKeysInit();
		sessionKeysInit();
		ageGroupKeysInit();
		blockKeysInit();
		childKeysInit();
		educationSortInit();
		schoolSortInit();
		schoolNameInit();
		schoolPhoneNumberInit();
		schoolFormInit();
		schoolNumberInit();
		schoolAdministratorNameInit();
		schoolEmailInit();
		schoolEmailFromInit();
		schoolEmailToInit();
		schoolLocationInit();
		schoolAddressInit();
		schoolShortNameInit();
		schoolCompleteNameInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSchool(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSchool(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSchool(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSchool(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainSchool(String var) {
		School oSchool = (School)this;
		switch(var) {
			case "schoolKey":
				return oSchool.schoolKey;
			case "yearKeys":
				return oSchool.yearKeys;
			case "receiptKeys":
				return oSchool.receiptKeys;
			case "seasonKeys":
				return oSchool.seasonKeys;
			case "sessionKeys":
				return oSchool.sessionKeys;
			case "ageGroupKeys":
				return oSchool.ageGroupKeys;
			case "blockKeys":
				return oSchool.blockKeys;
			case "childKeys":
				return oSchool.childKeys;
			case "educationSort":
				return oSchool.educationSort;
			case "schoolSort":
				return oSchool.schoolSort;
			case "schoolName":
				return oSchool.schoolName;
			case "schoolPhoneNumber":
				return oSchool.schoolPhoneNumber;
			case "schoolForm":
				return oSchool.schoolForm;
			case "schoolNumber":
				return oSchool.schoolNumber;
			case "schoolAdministratorName":
				return oSchool.schoolAdministratorName;
			case "schoolEmail":
				return oSchool.schoolEmail;
			case "schoolEmailFrom":
				return oSchool.schoolEmailFrom;
			case "schoolEmailTo":
				return oSchool.schoolEmailTo;
			case "schoolLocation":
				return oSchool.schoolLocation;
			case "schoolAddress":
				return oSchool.schoolAddress;
			case "schoolShortName":
				return oSchool.schoolShortName;
			case "schoolCompleteName":
				return oSchool.schoolCompleteName;
			default:
				return super.obtainCluster(var);
		}
	}

	///////////////
	// attribute //
	///////////////

	@Override public boolean attributeForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attributeSchool(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSchool(String var, Object val) {
		School oSchool = (School)this;
		switch(var) {
			case "yearKeys":
				oSchool.addYearKeys((Long)val);
				if(!saves.contains(var))
					saves.add(var);
				return val;
			case "receiptKeys":
				oSchool.addReceiptKeys((Long)val);
				if(!saves.contains(var))
					saves.add(var);
				return val;
			default:
				return super.attributeCluster(var, val);
		}
	}

	/////////////
	// define //
	/////////////

	@Override public boolean defineForClass(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSchool(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSchool(String var, String val) {
		switch(var) {
			case "schoolName":
				if(val != null)
					setSchoolName(val);
				saves.add(var);
				return val;
			case "schoolPhoneNumber":
				if(val != null)
					setSchoolPhoneNumber(val);
				saves.add(var);
				return val;
			case "schoolForm":
				if(val != null)
					setSchoolForm(val);
				saves.add(var);
				return val;
			case "schoolNumber":
				if(val != null)
					setSchoolNumber(val);
				saves.add(var);
				return val;
			case "schoolAdministratorName":
				if(val != null)
					setSchoolAdministratorName(val);
				saves.add(var);
				return val;
			case "schoolEmail":
				if(val != null)
					setSchoolEmail(val);
				saves.add(var);
				return val;
			case "schoolEmailFrom":
				if(val != null)
					setSchoolEmailFrom(val);
				saves.add(var);
				return val;
			case "schoolEmailTo":
				if(val != null)
					setSchoolEmailTo(val);
				saves.add(var);
				return val;
			case "schoolLocation":
				if(val != null)
					setSchoolLocation(val);
				saves.add(var);
				return val;
			case "schoolAddress":
				if(val != null)
					setSchoolAddress(val);
				saves.add(var);
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateSchool(solrDocument);
	}
	public void populateSchool(SolrDocument solrDocument) {
		School oSchool = (School)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("schoolKey")) {
				Long schoolKey = (Long)solrDocument.get("schoolKey_stored_long");
				if(schoolKey != null)
					oSchool.setSchoolKey(schoolKey);
			}

			List<Long> yearKeys = (List<Long>)solrDocument.get("yearKeys_stored_longs");
			if(yearKeys != null)
				oSchool.yearKeys.addAll(yearKeys);

			List<Long> receiptKeys = (List<Long>)solrDocument.get("receiptKeys_stored_longs");
			if(receiptKeys != null)
				oSchool.receiptKeys.addAll(receiptKeys);

			if(saves.contains("seasonKeys")) {
				List<Long> seasonKeys = (List<Long>)solrDocument.get("seasonKeys_stored_longs");
				if(seasonKeys != null)
					oSchool.seasonKeys.addAll(seasonKeys);
			}

			if(saves.contains("sessionKeys")) {
				List<Long> sessionKeys = (List<Long>)solrDocument.get("sessionKeys_stored_longs");
				if(sessionKeys != null)
					oSchool.sessionKeys.addAll(sessionKeys);
			}

			if(saves.contains("ageGroupKeys")) {
				List<Long> ageGroupKeys = (List<Long>)solrDocument.get("ageGroupKeys_stored_longs");
				if(ageGroupKeys != null)
					oSchool.ageGroupKeys.addAll(ageGroupKeys);
			}

			if(saves.contains("blockKeys")) {
				List<Long> blockKeys = (List<Long>)solrDocument.get("blockKeys_stored_longs");
				if(blockKeys != null)
					oSchool.blockKeys.addAll(blockKeys);
			}

			if(saves.contains("childKeys")) {
				List<Long> childKeys = (List<Long>)solrDocument.get("childKeys_stored_longs");
				if(childKeys != null)
					oSchool.childKeys.addAll(childKeys);
			}

			if(saves.contains("educationSort")) {
				Integer educationSort = (Integer)solrDocument.get("educationSort_stored_int");
				if(educationSort != null)
					oSchool.setEducationSort(educationSort);
			}

			if(saves.contains("schoolSort")) {
				Integer schoolSort = (Integer)solrDocument.get("schoolSort_stored_int");
				if(schoolSort != null)
					oSchool.setSchoolSort(schoolSort);
			}

			if(saves.contains("schoolName")) {
				String schoolName = (String)solrDocument.get("schoolName_stored_string");
				if(schoolName != null)
					oSchool.setSchoolName(schoolName);
			}

			if(saves.contains("schoolPhoneNumber")) {
				String schoolPhoneNumber = (String)solrDocument.get("schoolPhoneNumber_stored_string");
				if(schoolPhoneNumber != null)
					oSchool.setSchoolPhoneNumber(schoolPhoneNumber);
			}

			if(saves.contains("schoolForm")) {
				String schoolForm = (String)solrDocument.get("schoolForm_stored_string");
				if(schoolForm != null)
					oSchool.setSchoolForm(schoolForm);
			}

			if(saves.contains("schoolNumber")) {
				Integer schoolNumber = (Integer)solrDocument.get("schoolNumber_stored_int");
				if(schoolNumber != null)
					oSchool.setSchoolNumber(schoolNumber);
			}

			if(saves.contains("schoolAdministratorName")) {
				String schoolAdministratorName = (String)solrDocument.get("schoolAdministratorName_stored_string");
				if(schoolAdministratorName != null)
					oSchool.setSchoolAdministratorName(schoolAdministratorName);
			}

			if(saves.contains("schoolEmail")) {
				String schoolEmail = (String)solrDocument.get("schoolEmail_stored_string");
				if(schoolEmail != null)
					oSchool.setSchoolEmail(schoolEmail);
			}

			if(saves.contains("schoolEmailFrom")) {
				String schoolEmailFrom = (String)solrDocument.get("schoolEmailFrom_stored_string");
				if(schoolEmailFrom != null)
					oSchool.setSchoolEmailFrom(schoolEmailFrom);
			}

			if(saves.contains("schoolEmailTo")) {
				String schoolEmailTo = (String)solrDocument.get("schoolEmailTo_stored_string");
				if(schoolEmailTo != null)
					oSchool.setSchoolEmailTo(schoolEmailTo);
			}

			if(saves.contains("schoolLocation")) {
				String schoolLocation = (String)solrDocument.get("schoolLocation_stored_string");
				if(schoolLocation != null)
					oSchool.setSchoolLocation(schoolLocation);
			}

			if(saves.contains("schoolAddress")) {
				String schoolAddress = (String)solrDocument.get("schoolAddress_stored_string");
				if(schoolAddress != null)
					oSchool.setSchoolAddress(schoolAddress);
			}

			if(saves.contains("schoolShortName")) {
				String schoolShortName = (String)solrDocument.get("schoolShortName_stored_string");
				if(schoolShortName != null)
					oSchool.setSchoolShortName(schoolShortName);
			}

			if(saves.contains("schoolCompleteName")) {
				String schoolCompleteName = (String)solrDocument.get("schoolCompleteName_stored_string");
				if(schoolCompleteName != null)
					oSchool.setSchoolCompleteName(schoolCompleteName);
			}
		}

		super.populateCluster(solrDocument);
	}

	/////////////
	// index //
	/////////////

	public static void index() {
		try {
			SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.getSiteConfig().setConfigPath("/usr/local/src/computate-scolaire/config/computate-scolaire.config");
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			solrQuery.setRows(1);
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("org.computate.scolaire.enUS.school.School"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			School o = new School();
			o.siteRequestSchool(siteRequest);
			o.initDeepSchool(siteRequest);
			o.indexSchool();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexSchool();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexSchool(document);
	}

	public void indexSchool(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexSchool(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexSchool() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexSchool(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexSchool(SolrInputDocument document) {
		if(schoolKey != null) {
			document.addField("schoolKey_indexed_long", schoolKey);
			document.addField("schoolKey_stored_long", schoolKey);
		}
		if(yearKeys != null) {
			for(java.lang.Long o : yearKeys) {
				document.addField("yearKeys_indexed_longs", o);
			}
			for(java.lang.Long o : yearKeys) {
				document.addField("yearKeys_stored_longs", o);
			}
		}
		if(receiptKeys != null) {
			for(java.lang.Long o : receiptKeys) {
				document.addField("receiptKeys_indexed_longs", o);
			}
			for(java.lang.Long o : receiptKeys) {
				document.addField("receiptKeys_stored_longs", o);
			}
		}
		if(seasonKeys != null) {
			for(java.lang.Long o : seasonKeys) {
				document.addField("seasonKeys_indexed_longs", o);
			}
			for(java.lang.Long o : seasonKeys) {
				document.addField("seasonKeys_stored_longs", o);
			}
		}
		if(sessionKeys != null) {
			for(java.lang.Long o : sessionKeys) {
				document.addField("sessionKeys_indexed_longs", o);
			}
			for(java.lang.Long o : sessionKeys) {
				document.addField("sessionKeys_stored_longs", o);
			}
		}
		if(ageGroupKeys != null) {
			for(java.lang.Long o : ageGroupKeys) {
				document.addField("ageGroupKeys_indexed_longs", o);
			}
			for(java.lang.Long o : ageGroupKeys) {
				document.addField("ageGroupKeys_stored_longs", o);
			}
		}
		if(blockKeys != null) {
			for(java.lang.Long o : blockKeys) {
				document.addField("blockKeys_indexed_longs", o);
			}
			for(java.lang.Long o : blockKeys) {
				document.addField("blockKeys_stored_longs", o);
			}
		}
		if(childKeys != null) {
			for(java.lang.Long o : childKeys) {
				document.addField("childKeys_indexed_longs", o);
			}
			for(java.lang.Long o : childKeys) {
				document.addField("childKeys_stored_longs", o);
			}
		}
		if(educationSort != null) {
			document.addField("educationSort_indexed_int", educationSort);
			document.addField("educationSort_stored_int", educationSort);
		}
		if(schoolSort != null) {
			document.addField("schoolSort_indexed_int", schoolSort);
			document.addField("schoolSort_stored_int", schoolSort);
		}
		if(schoolName != null) {
			document.addField("schoolName_indexed_string", schoolName);
			document.addField("schoolName_stored_string", schoolName);
		}
		if(schoolPhoneNumber != null) {
			document.addField("schoolPhoneNumber_indexed_string", schoolPhoneNumber);
			document.addField("schoolPhoneNumber_stored_string", schoolPhoneNumber);
		}
		if(schoolForm != null) {
			document.addField("schoolForm_indexed_string", schoolForm);
			document.addField("schoolForm_stored_string", schoolForm);
		}
		if(schoolNumber != null) {
			document.addField("schoolNumber_indexed_int", schoolNumber);
			document.addField("schoolNumber_stored_int", schoolNumber);
		}
		if(schoolAdministratorName != null) {
			document.addField("schoolAdministratorName_indexed_string", schoolAdministratorName);
			document.addField("schoolAdministratorName_stored_string", schoolAdministratorName);
		}
		if(schoolEmail != null) {
			document.addField("schoolEmail_indexed_string", schoolEmail);
			document.addField("schoolEmail_stored_string", schoolEmail);
		}
		if(schoolEmailFrom != null) {
			document.addField("schoolEmailFrom_indexed_string", schoolEmailFrom);
			document.addField("schoolEmailFrom_stored_string", schoolEmailFrom);
		}
		if(schoolEmailTo != null) {
			document.addField("schoolEmailTo_indexed_string", schoolEmailTo);
			document.addField("schoolEmailTo_stored_string", schoolEmailTo);
		}
		if(schoolLocation != null) {
			document.addField("schoolLocation_indexed_string", schoolLocation);
			document.addField("schoolLocation_stored_string", schoolLocation);
		}
		if(schoolAddress != null) {
			document.addField("schoolAddress_indexed_string", schoolAddress);
			document.addField("schoolAddress_stored_string", schoolAddress);
		}
		if(schoolShortName != null) {
			document.addField("schoolShortName_indexed_string", schoolShortName);
			document.addField("schoolShortName_stored_string", schoolShortName);
		}
		if(schoolCompleteName != null) {
			document.addField("schoolCompleteName_indexed_string", schoolCompleteName);
			document.addField("schoolCompleteName_stored_string", schoolCompleteName);
		}
		super.indexCluster(document);

	}

	public void unindexSchool() {
		try {
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			initDeepSchool(siteRequest);
			SolrClient solrClient = siteContext.getSolrClient();
			solrClient.deleteById(id.toString());
			solrClient.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexedSchool(String entityVar) {
		switch(entityVar) {
			case "schoolKey":
				return "schoolKey_indexed_long";
			case "yearKeys":
				return "yearKeys_indexed_longs";
			case "receiptKeys":
				return "receiptKeys_indexed_longs";
			case "seasonKeys":
				return "seasonKeys_indexed_longs";
			case "sessionKeys":
				return "sessionKeys_indexed_longs";
			case "ageGroupKeys":
				return "ageGroupKeys_indexed_longs";
			case "blockKeys":
				return "blockKeys_indexed_longs";
			case "childKeys":
				return "childKeys_indexed_longs";
			case "educationSort":
				return "educationSort_indexed_int";
			case "schoolSort":
				return "schoolSort_indexed_int";
			case "schoolName":
				return "schoolName_indexed_string";
			case "schoolPhoneNumber":
				return "schoolPhoneNumber_indexed_string";
			case "schoolForm":
				return "schoolForm_indexed_string";
			case "schoolNumber":
				return "schoolNumber_indexed_int";
			case "schoolAdministratorName":
				return "schoolAdministratorName_indexed_string";
			case "schoolEmail":
				return "schoolEmail_indexed_string";
			case "schoolEmailFrom":
				return "schoolEmailFrom_indexed_string";
			case "schoolEmailTo":
				return "schoolEmailTo_indexed_string";
			case "schoolLocation":
				return "schoolLocation_indexed_string";
			case "schoolAddress":
				return "schoolAddress_indexed_string";
			case "schoolShortName":
				return "schoolShortName_indexed_string";
			case "schoolCompleteName":
				return "schoolCompleteName_indexed_string";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchSchool(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedSchool(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeSchool(solrDocument);
	}
	public void storeSchool(SolrDocument solrDocument) {
		School oSchool = (School)this;

		Long schoolKey = (Long)solrDocument.get("schoolKey_stored_long");
		if(schoolKey != null)
			oSchool.setSchoolKey(schoolKey);

		List<Long> yearKeys = (List<Long>)solrDocument.get("yearKeys_stored_longs");
		if(yearKeys != null)
			oSchool.yearKeys.addAll(yearKeys);

		List<Long> receiptKeys = (List<Long>)solrDocument.get("receiptKeys_stored_longs");
		if(receiptKeys != null)
			oSchool.receiptKeys.addAll(receiptKeys);

		List<Long> seasonKeys = (List<Long>)solrDocument.get("seasonKeys_stored_longs");
		if(seasonKeys != null)
			oSchool.seasonKeys.addAll(seasonKeys);

		List<Long> sessionKeys = (List<Long>)solrDocument.get("sessionKeys_stored_longs");
		if(sessionKeys != null)
			oSchool.sessionKeys.addAll(sessionKeys);

		List<Long> ageGroupKeys = (List<Long>)solrDocument.get("ageGroupKeys_stored_longs");
		if(ageGroupKeys != null)
			oSchool.ageGroupKeys.addAll(ageGroupKeys);

		List<Long> blockKeys = (List<Long>)solrDocument.get("blockKeys_stored_longs");
		if(blockKeys != null)
			oSchool.blockKeys.addAll(blockKeys);

		List<Long> childKeys = (List<Long>)solrDocument.get("childKeys_stored_longs");
		if(childKeys != null)
			oSchool.childKeys.addAll(childKeys);

		Integer educationSort = (Integer)solrDocument.get("educationSort_stored_int");
		if(educationSort != null)
			oSchool.setEducationSort(educationSort);

		Integer schoolSort = (Integer)solrDocument.get("schoolSort_stored_int");
		if(schoolSort != null)
			oSchool.setSchoolSort(schoolSort);

		String schoolName = (String)solrDocument.get("schoolName_stored_string");
		if(schoolName != null)
			oSchool.setSchoolName(schoolName);

		String schoolPhoneNumber = (String)solrDocument.get("schoolPhoneNumber_stored_string");
		if(schoolPhoneNumber != null)
			oSchool.setSchoolPhoneNumber(schoolPhoneNumber);

		String schoolForm = (String)solrDocument.get("schoolForm_stored_string");
		if(schoolForm != null)
			oSchool.setSchoolForm(schoolForm);

		Integer schoolNumber = (Integer)solrDocument.get("schoolNumber_stored_int");
		if(schoolNumber != null)
			oSchool.setSchoolNumber(schoolNumber);

		String schoolAdministratorName = (String)solrDocument.get("schoolAdministratorName_stored_string");
		if(schoolAdministratorName != null)
			oSchool.setSchoolAdministratorName(schoolAdministratorName);

		String schoolEmail = (String)solrDocument.get("schoolEmail_stored_string");
		if(schoolEmail != null)
			oSchool.setSchoolEmail(schoolEmail);

		String schoolEmailFrom = (String)solrDocument.get("schoolEmailFrom_stored_string");
		if(schoolEmailFrom != null)
			oSchool.setSchoolEmailFrom(schoolEmailFrom);

		String schoolEmailTo = (String)solrDocument.get("schoolEmailTo_stored_string");
		if(schoolEmailTo != null)
			oSchool.setSchoolEmailTo(schoolEmailTo);

		String schoolLocation = (String)solrDocument.get("schoolLocation_stored_string");
		if(schoolLocation != null)
			oSchool.setSchoolLocation(schoolLocation);

		String schoolAddress = (String)solrDocument.get("schoolAddress_stored_string");
		if(schoolAddress != null)
			oSchool.setSchoolAddress(schoolAddress);

		String schoolShortName = (String)solrDocument.get("schoolShortName_stored_string");
		if(schoolShortName != null)
			oSchool.setSchoolShortName(schoolShortName);

		String schoolCompleteName = (String)solrDocument.get("schoolCompleteName_stored_string");
		if(schoolCompleteName != null)
			oSchool.setSchoolCompleteName(schoolCompleteName);

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSchool() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof School) {
			School original = (School)o;
			if(!Objects.equals(schoolKey, original.getSchoolKey()))
				apiRequest.addVars("schoolKey");
			if(!Objects.equals(yearKeys, original.getYearKeys()))
				apiRequest.addVars("yearKeys");
			if(!Objects.equals(receiptKeys, original.getReceiptKeys()))
				apiRequest.addVars("receiptKeys");
			if(!Objects.equals(seasonKeys, original.getSeasonKeys()))
				apiRequest.addVars("seasonKeys");
			if(!Objects.equals(sessionKeys, original.getSessionKeys()))
				apiRequest.addVars("sessionKeys");
			if(!Objects.equals(ageGroupKeys, original.getAgeGroupKeys()))
				apiRequest.addVars("ageGroupKeys");
			if(!Objects.equals(blockKeys, original.getBlockKeys()))
				apiRequest.addVars("blockKeys");
			if(!Objects.equals(childKeys, original.getChildKeys()))
				apiRequest.addVars("childKeys");
			if(!Objects.equals(educationSort, original.getEducationSort()))
				apiRequest.addVars("educationSort");
			if(!Objects.equals(schoolSort, original.getSchoolSort()))
				apiRequest.addVars("schoolSort");
			if(!Objects.equals(schoolName, original.getSchoolName()))
				apiRequest.addVars("schoolName");
			if(!Objects.equals(schoolPhoneNumber, original.getSchoolPhoneNumber()))
				apiRequest.addVars("schoolPhoneNumber");
			if(!Objects.equals(schoolForm, original.getSchoolForm()))
				apiRequest.addVars("schoolForm");
			if(!Objects.equals(schoolNumber, original.getSchoolNumber()))
				apiRequest.addVars("schoolNumber");
			if(!Objects.equals(schoolAdministratorName, original.getSchoolAdministratorName()))
				apiRequest.addVars("schoolAdministratorName");
			if(!Objects.equals(schoolEmail, original.getSchoolEmail()))
				apiRequest.addVars("schoolEmail");
			if(!Objects.equals(schoolEmailFrom, original.getSchoolEmailFrom()))
				apiRequest.addVars("schoolEmailFrom");
			if(!Objects.equals(schoolEmailTo, original.getSchoolEmailTo()))
				apiRequest.addVars("schoolEmailTo");
			if(!Objects.equals(schoolLocation, original.getSchoolLocation()))
				apiRequest.addVars("schoolLocation");
			if(!Objects.equals(schoolAddress, original.getSchoolAddress()))
				apiRequest.addVars("schoolAddress");
			if(!Objects.equals(schoolShortName, original.getSchoolShortName()))
				apiRequest.addVars("schoolShortName");
			if(!Objects.equals(schoolCompleteName, original.getSchoolCompleteName()))
				apiRequest.addVars("schoolCompleteName");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), schoolKey, yearKeys, receiptKeys, seasonKeys, sessionKeys, ageGroupKeys, blockKeys, childKeys, educationSort, schoolSort, schoolName, schoolPhoneNumber, schoolForm, schoolNumber, schoolAdministratorName, schoolEmail, schoolEmailFrom, schoolEmailTo, schoolLocation, schoolAddress, schoolShortName, schoolCompleteName);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof School))
			return false;
		School that = (School)o;
		return super.equals(o)
				&& Objects.equals( schoolKey, that.schoolKey )
				&& Objects.equals( yearKeys, that.yearKeys )
				&& Objects.equals( receiptKeys, that.receiptKeys )
				&& Objects.equals( seasonKeys, that.seasonKeys )
				&& Objects.equals( sessionKeys, that.sessionKeys )
				&& Objects.equals( ageGroupKeys, that.ageGroupKeys )
				&& Objects.equals( blockKeys, that.blockKeys )
				&& Objects.equals( childKeys, that.childKeys )
				&& Objects.equals( educationSort, that.educationSort )
				&& Objects.equals( schoolSort, that.schoolSort )
				&& Objects.equals( schoolName, that.schoolName )
				&& Objects.equals( schoolPhoneNumber, that.schoolPhoneNumber )
				&& Objects.equals( schoolForm, that.schoolForm )
				&& Objects.equals( schoolNumber, that.schoolNumber )
				&& Objects.equals( schoolAdministratorName, that.schoolAdministratorName )
				&& Objects.equals( schoolEmail, that.schoolEmail )
				&& Objects.equals( schoolEmailFrom, that.schoolEmailFrom )
				&& Objects.equals( schoolEmailTo, that.schoolEmailTo )
				&& Objects.equals( schoolLocation, that.schoolLocation )
				&& Objects.equals( schoolAddress, that.schoolAddress )
				&& Objects.equals( schoolShortName, that.schoolShortName )
				&& Objects.equals( schoolCompleteName, that.schoolCompleteName );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("School { ");
		sb.append( "schoolKey: " ).append(schoolKey);
		sb.append( ", yearKeys: " ).append(yearKeys);
		sb.append( ", receiptKeys: " ).append(receiptKeys);
		sb.append( ", seasonKeys: " ).append(seasonKeys);
		sb.append( ", sessionKeys: " ).append(sessionKeys);
		sb.append( ", ageGroupKeys: " ).append(ageGroupKeys);
		sb.append( ", blockKeys: " ).append(blockKeys);
		sb.append( ", childKeys: " ).append(childKeys);
		sb.append( ", educationSort: " ).append(educationSort);
		sb.append( ", schoolSort: " ).append(schoolSort);
		sb.append( ", schoolName: \"" ).append(schoolName).append( "\"" );
		sb.append( ", schoolPhoneNumber: \"" ).append(schoolPhoneNumber).append( "\"" );
		sb.append( ", schoolForm: \"" ).append(schoolForm).append( "\"" );
		sb.append( ", schoolNumber: " ).append(schoolNumber);
		sb.append( ", schoolAdministratorName: \"" ).append(schoolAdministratorName).append( "\"" );
		sb.append( ", schoolEmail: \"" ).append(schoolEmail).append( "\"" );
		sb.append( ", schoolEmailFrom: \"" ).append(schoolEmailFrom).append( "\"" );
		sb.append( ", schoolEmailTo: \"" ).append(schoolEmailTo).append( "\"" );
		sb.append( ", schoolLocation: \"" ).append(schoolLocation).append( "\"" );
		sb.append( ", schoolAddress: \"" ).append(schoolAddress).append( "\"" );
		sb.append( ", schoolShortName: \"" ).append(schoolShortName).append( "\"" );
		sb.append( ", schoolCompleteName: \"" ).append(schoolCompleteName).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}
