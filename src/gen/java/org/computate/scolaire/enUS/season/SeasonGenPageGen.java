package org.computate.scolaire.enUS.season;

import org.computate.scolaire.enUS.writer.AllWriter;
import org.computate.scolaire.enUS.wrap.Wrap;
import org.computate.scolaire.enUS.cluster.Cluster;
import java.math.MathContext;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.computate.scolaire.enUS.cluster.ClusterPage;
import org.computate.scolaire.enUS.search.SearchList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.math.NumberUtils;
import org.computate.scolaire.enUS.request.SiteRequestEnUS;
import org.computate.scolaire.enUS.season.SchoolSeason;

/**	
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.season.SeasonGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class SeasonGenPageGen<DEV> extends ClusterPage {

	//////////////////////
	// listSchoolSeason //
	//////////////////////

	/**	L'entité « listSchoolSeason »
	 *	 is defined as null before being initialized. 
	 */
	protected SearchList<SchoolSeason> listSchoolSeason;
	@JsonIgnore
	public Wrap<SearchList<SchoolSeason>> listSchoolSeasonWrap = new Wrap<SearchList<SchoolSeason>>().p(this).c(SearchList.class).var("listSchoolSeason").o(listSchoolSeason);

	/**	<br/>L'entité « listSchoolSeason »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.season.SeasonGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listSchoolSeason">Trouver l'entité listSchoolSeason dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _listSchoolSeason(Wrap<SearchList<SchoolSeason>> c);

	public SearchList<SchoolSeason> getListSchoolSeason() {
		return listSchoolSeason;
	}

	public void setListSchoolSeason(SearchList<SchoolSeason> listSchoolSeason) {
		this.listSchoolSeason = listSchoolSeason;
		this.listSchoolSeasonWrap.alreadyInitialized = true;
	}
	protected SeasonGenPage listSchoolSeasonInit() {
		if(!listSchoolSeasonWrap.alreadyInitialized) {
			_listSchoolSeason(listSchoolSeasonWrap);
			if(listSchoolSeason == null)
				setListSchoolSeason(listSchoolSeasonWrap.o);
		}
		listSchoolSeasonWrap.alreadyInitialized(true);
		return (SeasonGenPage)this;
	}

	//////////////////
	// schoolSeason //
	//////////////////

	/**	L'entité « schoolSeason »
	 *	 is defined as null before being initialized. 
	 */
	protected SchoolSeason schoolSeason;
	@JsonIgnore
	public Wrap<SchoolSeason> schoolSeasonWrap = new Wrap<SchoolSeason>().p(this).c(SchoolSeason.class).var("schoolSeason").o(schoolSeason);

	/**	<br/>L'entité « schoolSeason »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:org.computate.scolaire.enUS.season.SeasonGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:schoolSeason">Trouver l'entité schoolSeason dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _schoolSeason(Wrap<SchoolSeason> c);

	public SchoolSeason getSchoolSeason() {
		return schoolSeason;
	}

	public void setSchoolSeason(SchoolSeason schoolSeason) {
		this.schoolSeason = schoolSeason;
		this.schoolSeasonWrap.alreadyInitialized = true;
	}
	protected SeasonGenPage schoolSeasonInit() {
		if(!schoolSeasonWrap.alreadyInitialized) {
			_schoolSeason(schoolSeasonWrap);
			if(schoolSeason == null)
				setSchoolSeason(schoolSeasonWrap.o);
		}
		schoolSeasonWrap.alreadyInitialized(true);
		return (SeasonGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSeasonGenPage = false;

	public SeasonGenPage initDeepSeasonGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSeasonGenPage) {
			alreadyInitializedSeasonGenPage = true;
			initDeepSeasonGenPage();
		}
		return (SeasonGenPage)this;
	}

	public void initDeepSeasonGenPage() {
		super.initDeepClusterPage(siteRequest_);
		initSeasonGenPage();
	}

	public void initSeasonGenPage() {
		listSchoolSeasonInit();
		schoolSeasonInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSeasonGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSeasonGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestClusterPage(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSeasonGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSeasonGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainSeasonGenPage(String var) {
		SeasonGenPage oSeasonGenPage = (SeasonGenPage)this;
		switch(var) {
			case "listSchoolSeason":
				return oSeasonGenPage.listSchoolSeason;
			case "schoolSeason":
				return oSeasonGenPage.schoolSeason;
			default:
				return super.obtainClusterPage(var);
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
				o = attributeSeasonGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSeasonGenPage(String var, Object val) {
		SeasonGenPage oSeasonGenPage = (SeasonGenPage)this;
		switch(var) {
			default:
				return super.attributeClusterPage(var, val);
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
					o = defineSeasonGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSeasonGenPage(String var, String val) {
		switch(var) {
			default:
				return super.defineClusterPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsSeasonGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsSeasonGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptSeasonGenPage();
		super.htmlScript();
	}

	public void htmlScriptSeasonGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodySeasonGenPage();
		super.htmlBody();
	}

	public void htmlBodySeasonGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlSeasonGenPage();
		super.html();
	}

	public void htmlSeasonGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaSeasonGenPage();
		super.htmlMeta();
	}

	public void htmlMetaSeasonGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesSeasonGenPage();
		super.htmlStyles();
	}

	public void htmlStylesSeasonGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleSeasonGenPage();
		super.htmlStyle();
	}

	public void htmlStyleSeasonGenPage() {
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode());
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof SeasonGenPage))
			return false;
		SeasonGenPage that = (SeasonGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SeasonGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}