package org.computate.scolaire.frFR.enfant;

import org.computate.scolaire.frFR.enfant.EnfantScolaire;
import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.math.MathContext;
import org.computate.scolaire.frFR.cluster.Cluster;
import org.computate.scolaire.frFR.recherche.ListeRecherche;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.computate.scolaire.frFR.ecrivain.ToutEcrivain;
import org.computate.scolaire.frFR.requete.api.RequeteApi;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.computate.scolaire.frFR.couverture.Couverture;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.computate.scolaire.frFR.requete.RequeteSiteFrFR;
import org.computate.scolaire.frFR.cluster.ClusterPage;

/**	
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.enfant.EnfantGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class EnfantGenPageGen<DEV> extends ClusterPage {

	/////////////////////////
	// listeEnfantScolaire //
	/////////////////////////

	/**	L'entité « listeEnfantScolaire »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected ListeRecherche<EnfantScolaire> listeEnfantScolaire;
	@JsonIgnore
	public Couverture<ListeRecherche<EnfantScolaire>> listeEnfantScolaireCouverture = new Couverture<ListeRecherche<EnfantScolaire>>().p(this).c(ListeRecherche.class).var("listeEnfantScolaire").o(listeEnfantScolaire);

	/**	<br/>L'entité « listeEnfantScolaire »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.enfant.EnfantGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:listeEnfantScolaire">Trouver l'entité listeEnfantScolaire dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _listeEnfantScolaire(Couverture<ListeRecherche<EnfantScolaire>> c);

	public ListeRecherche<EnfantScolaire> getListeEnfantScolaire() {
		return listeEnfantScolaire;
	}

	public void setListeEnfantScolaire(ListeRecherche<EnfantScolaire> listeEnfantScolaire) {
		this.listeEnfantScolaire = listeEnfantScolaire;
		this.listeEnfantScolaireCouverture.dejaInitialise = true;
	}
	protected EnfantGenPage listeEnfantScolaireInit() {
		if(!listeEnfantScolaireCouverture.dejaInitialise) {
			_listeEnfantScolaire(listeEnfantScolaireCouverture);
			if(listeEnfantScolaire == null)
				setListeEnfantScolaire(listeEnfantScolaireCouverture.o);
		}
		if(listeEnfantScolaire != null)
			listeEnfantScolaire.initLoinPourClasse(requeteSite_);
		listeEnfantScolaireCouverture.dejaInitialise(true);
		return (EnfantGenPage)this;
	}

	////////////////////
	// enfantScolaire //
	////////////////////

	/**	L'entité « enfantScolaire »
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected EnfantScolaire enfantScolaire;
	@JsonIgnore
	public Couverture<EnfantScolaire> enfantScolaireCouverture = new Couverture<EnfantScolaire>().p(this).c(EnfantScolaire.class).var("enfantScolaire").o(enfantScolaire);

	/**	<br/>L'entité « enfantScolaire »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.enfant.EnfantGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:enfantScolaire">Trouver l'entité enfantScolaire dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _enfantScolaire(Couverture<EnfantScolaire> c);

	public EnfantScolaire getEnfantScolaire() {
		return enfantScolaire;
	}

	public void setEnfantScolaire(EnfantScolaire enfantScolaire) {
		this.enfantScolaire = enfantScolaire;
		this.enfantScolaireCouverture.dejaInitialise = true;
	}
	protected EnfantGenPage enfantScolaireInit() {
		if(!enfantScolaireCouverture.dejaInitialise) {
			_enfantScolaire(enfantScolaireCouverture);
			if(enfantScolaire == null)
				setEnfantScolaire(enfantScolaireCouverture.o);
		}
		if(enfantScolaire != null)
			enfantScolaire.initLoinPourClasse(requeteSite_);
		enfantScolaireCouverture.dejaInitialise(true);
		return (EnfantGenPage)this;
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseEnfantGenPage = false;

	public EnfantGenPage initLoinEnfantGenPage(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseEnfantGenPage) {
			dejaInitialiseEnfantGenPage = true;
			initLoinEnfantGenPage();
		}
		return (EnfantGenPage)this;
	}

	public void initLoinEnfantGenPage() {
		initEnfantGenPage();
		super.initLoinClusterPage(requeteSite_);
	}

	public void initEnfantGenPage() {
		listeEnfantScolaireInit();
		enfantScolaireInit();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinEnfantGenPage(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteEnfantGenPage(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteClusterPage(requeteSite_);
		if(listeEnfantScolaire != null)
			listeEnfantScolaire.setRequeteSite_(requeteSite_);
		if(enfantScolaire != null)
			enfantScolaire.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteEnfantGenPage(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirEnfantGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirEnfantGenPage(String var) {
		EnfantGenPage oEnfantGenPage = (EnfantGenPage)this;
		switch(var) {
			case "listeEnfantScolaire":
				return oEnfantGenPage.listeEnfantScolaire;
			case "enfantScolaire":
				return oEnfantGenPage.enfantScolaire;
			default:
				return super.obtenirClusterPage(var);
		}
	}

	///////////////
	// attribuer //
	///////////////

	@Override public boolean attribuerPourClasse(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attribuerEnfantGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerEnfantGenPage(String var, Object val) {
		EnfantGenPage oEnfantGenPage = (EnfantGenPage)this;
		switch(var) {
			default:
				return super.attribuerClusterPage(var, val);
		}
	}

	/////////////
	// definir //
	/////////////

	@Override public boolean definirPourClasse(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = definirEnfantGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirEnfantGenPage(String var, String val) {
		switch(var) {
			default:
				return super.definirClusterPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsEnfantGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsEnfantGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptEnfantGenPage();
		super.htmlScript();
	}

	public void htmlScriptEnfantGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyEnfantGenPage();
		super.htmlBody();
	}

	public void htmlBodyEnfantGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlEnfantGenPage();
		super.html();
	}

	public void htmlEnfantGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaEnfantGenPage();
		super.htmlMeta();
	}

	public void htmlMetaEnfantGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesEnfantGenPage();
		super.htmlStyles();
	}

	public void htmlStylesEnfantGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleEnfantGenPage();
		super.htmlStyle();
	}

	public void htmlStyleEnfantGenPage() {
	}

	//////////////////
	// requeteApi //
	//////////////////

	public void requeteApiEnfantGenPage() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof EnfantGenPage) {
			EnfantGenPage original = (EnfantGenPage)o;
			super.requeteApiClusterPage();
		}
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
		if(!(o instanceof EnfantGenPage))
			return false;
		EnfantGenPage that = (EnfantGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("EnfantGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
