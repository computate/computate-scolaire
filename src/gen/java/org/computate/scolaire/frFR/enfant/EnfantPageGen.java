package org.computate.scolaire.frFR.enfant;

import org.computate.scolaire.frFR.enfant.EnfantGenPage;
import java.math.MathContext;
import org.computate.scolaire.frFR.cluster.Cluster;
import org.apache.commons.text.StringEscapeUtils;
import org.computate.scolaire.frFR.ecrivain.ToutEcrivain;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.computate.scolaire.frFR.couverture.Couverture;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.math.NumberUtils;
import org.computate.scolaire.frFR.requete.RequeteSiteFrFR;

/**	
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.enfant.EnfantPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr</a>
 * <br/>
 **/
public abstract class EnfantPageGen<DEV> extends EnfantGenPage {

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseEnfantPage = false;

	public EnfantPage initLoinEnfantPage(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseEnfantPage) {
			dejaInitialiseEnfantPage = true;
			initLoinEnfantPage();
		}
		return (EnfantPage)this;
	}

	public void initLoinEnfantPage() {
		initEnfantPage();
		super.initLoinEnfantGenPage(requeteSite_);
	}

	public void initEnfantPage() {
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinEnfantPage(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteEnfantPage(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteEnfantGenPage(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteEnfantPage(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirEnfantPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirEnfantPage(String var) {
		EnfantPage oEnfantPage = (EnfantPage)this;
		switch(var) {
			default:
				return super.obtenirEnfantGenPage(var);
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
				o = attribuerEnfantPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerEnfantPage(String var, Object val) {
		EnfantPage oEnfantPage = (EnfantPage)this;
		switch(var) {
			default:
				return super.attribuerEnfantGenPage(var, val);
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
					o = definirEnfantPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirEnfantPage(String var, String val) {
		switch(var) {
			default:
				return super.definirEnfantGenPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsEnfantPage();
		super.htmlScripts();
	}

	public void htmlScriptsEnfantPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptEnfantPage();
		super.htmlScript();
	}

	public void htmlScriptEnfantPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyEnfantPage();
		super.htmlBody();
	}

	public void htmlBodyEnfantPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlEnfantPage();
		super.html();
	}

	public void htmlEnfantPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaEnfantPage();
		super.htmlMeta();
	}

	public void htmlMetaEnfantPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesEnfantPage();
		super.htmlStyles();
	}

	public void htmlStylesEnfantPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleEnfantPage();
		super.htmlStyle();
	}

	public void htmlStyleEnfantPage() {
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
		if(!(o instanceof EnfantPage))
			return false;
		EnfantPage that = (EnfantPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("EnfantPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
