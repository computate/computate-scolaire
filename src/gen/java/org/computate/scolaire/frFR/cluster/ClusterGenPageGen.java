package org.computate.scolaire.frFR.cluster;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.computate.scolaire.frFR.recherche.ListeRecherche;
import java.util.HashMap;
import org.computate.scolaire.frFR.ecrivain.ToutEcrivain;
import org.computate.scolaire.frFR.requete.api.RequeteApi;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.computate.scolaire.frFR.couverture.Couverture;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.computate.scolaire.frFR.requete.RequeteSiteFrFR;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
import java.math.MathContext;
import org.computate.scolaire.frFR.cluster.Cluster;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.computate.scolaire.frFR.page.MiseEnPage;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.cluster.ClusterGenPage&fq=classeEtendGen_indexed_boolean:true">Trouver la classe  dans Solr. </a>
 * <br/>
 **/
public abstract class ClusterGenPageGen<DEV> extends MiseEnPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(ClusterGenPage.class);

	//////////////////
	// listeCluster //
	//////////////////

	/**	 L'entité listeCluster
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected ListeRecherche<Cluster> listeCluster;
	@JsonIgnore
	public Couverture<ListeRecherche<Cluster>> listeClusterCouverture = new Couverture<ListeRecherche<Cluster>>().p(this).c(ListeRecherche.class).var("listeCluster").o(listeCluster);

	/**	<br/> L'entité listeCluster
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.cluster.ClusterGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:listeCluster">Trouver l'entité listeCluster dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _listeCluster(Couverture<ListeRecherche<Cluster>> c);

	public ListeRecherche<Cluster> getListeCluster() {
		return listeCluster;
	}

	public void setListeCluster(ListeRecherche<Cluster> listeCluster) {
		this.listeCluster = listeCluster;
		this.listeClusterCouverture.dejaInitialise = true;
	}
	protected ClusterGenPage listeClusterInit() {
		if(!listeClusterCouverture.dejaInitialise) {
			_listeCluster(listeClusterCouverture);
			if(listeCluster == null)
				setListeCluster(listeClusterCouverture.o);
		}
		if(listeCluster != null)
			listeCluster.initLoinPourClasse(requeteSite_);
		listeClusterCouverture.dejaInitialise(true);
		return (ClusterGenPage)this;
	}

	//////////////
	// cluster_ //
	//////////////

	/**	 L'entité cluster_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Cluster cluster_;
	@JsonIgnore
	public Couverture<Cluster> cluster_Couverture = new Couverture<Cluster>().p(this).c(Cluster.class).var("cluster_").o(cluster_);

	/**	<br/> L'entité cluster_
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.cluster.ClusterGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:cluster_">Trouver l'entité cluster_ dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _cluster_(Couverture<Cluster> c);

	public Cluster getCluster_() {
		return cluster_;
	}

	public void setCluster_(Cluster cluster_) {
		this.cluster_ = cluster_;
		this.cluster_Couverture.dejaInitialise = true;
	}
	protected ClusterGenPage cluster_Init() {
		if(!cluster_Couverture.dejaInitialise) {
			_cluster_(cluster_Couverture);
			if(cluster_ == null)
				setCluster_(cluster_Couverture.o);
		}
		cluster_Couverture.dejaInitialise(true);
		return (ClusterGenPage)this;
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseClusterGenPage = false;

	public ClusterGenPage initLoinClusterGenPage(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseClusterGenPage) {
			dejaInitialiseClusterGenPage = true;
			initLoinClusterGenPage();
		}
		return (ClusterGenPage)this;
	}

	public void initLoinClusterGenPage() {
		initClusterGenPage();
		super.initLoinMiseEnPage(requeteSite_);
	}

	public void initClusterGenPage() {
		listeClusterInit();
		cluster_Init();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinClusterGenPage(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteClusterGenPage(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteMiseEnPage(requeteSite_);
		if(listeCluster != null)
			listeCluster.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteClusterGenPage(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirClusterGenPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirClusterGenPage(String var) {
		ClusterGenPage oClusterGenPage = (ClusterGenPage)this;
		switch(var) {
			case "listeCluster":
				return oClusterGenPage.listeCluster;
			case "cluster_":
				return oClusterGenPage.cluster_;
			default:
				return super.obtenirMiseEnPage(var);
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
				o = attribuerClusterGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerClusterGenPage(String var, Object val) {
		ClusterGenPage oClusterGenPage = (ClusterGenPage)this;
		switch(var) {
			default:
				return super.attribuerMiseEnPage(var, val);
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
					o = definirClusterGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirClusterGenPage(String var, String val) {
		switch(var) {
			default:
				return super.definirMiseEnPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsClusterGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsClusterGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptClusterGenPage();
		super.htmlScript();
	}

	public void htmlScriptClusterGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyClusterGenPage();
		super.htmlBody();
	}

	public void htmlBodyClusterGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlClusterGenPage();
		super.html();
	}

	public void htmlClusterGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaClusterGenPage();
		super.htmlMeta();
	}

	public void htmlMetaClusterGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesClusterGenPage();
		super.htmlStyles();
	}

	public void htmlStylesClusterGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleClusterGenPage();
		super.htmlStyle();
	}

	public void htmlStyleClusterGenPage() {
	}

	//////////////////
	// requeteApi //
	//////////////////

	public void requeteApiClusterGenPage() {
		RequeteApi requeteApi = Optional.ofNullable(requeteSite_).map(RequeteSiteFrFR::getRequeteApi_).orElse(null);
		Object o = Optional.ofNullable(requeteApi).map(RequeteApi::getOriginal).orElse(null);
		if(o != null && o instanceof ClusterGenPage) {
			ClusterGenPage original = (ClusterGenPage)o;
			super.requeteApiMiseEnPage();
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
		if(!(o instanceof ClusterGenPage))
			return false;
		ClusterGenPage that = (ClusterGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("ClusterGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
