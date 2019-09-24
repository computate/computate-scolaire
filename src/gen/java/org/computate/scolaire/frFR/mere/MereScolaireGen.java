package org.computate.scolaire.frFR.mere;

import java.util.Date;
import org.computate.scolaire.frFR.recherche.ListeRecherche;
import org.computate.scolaire.frFR.contexte.SiteContexteFrFR;
import org.computate.scolaire.frFR.ecrivain.ToutEcrivain;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import io.vertx.core.logging.LoggerFactory;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.computate.scolaire.frFR.inscription.InscriptionScolaire;
import org.computate.scolaire.frFR.couverture.Couverture;
import java.lang.Long;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import io.vertx.core.json.JsonObject;
import org.computate.scolaire.frFR.requete.RequeteSiteFrFR;
import java.lang.String;
import io.vertx.core.logging.Logger;
import java.math.MathContext;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.computate.scolaire.frFR.cluster.Cluster;
import java.util.Set;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.solr.client.solrj.SolrClient;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import io.vertx.ext.sql.SQLConnection;
import org.apache.commons.lang3.math.NumberUtils;
import io.vertx.ext.sql.SQLClient;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**	
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true">Trouver la classe objectSuggest dans Solr</a>
 * <br/>
 **/
public abstract class MereScolaireGen<DEV> extends Cluster {
	private static final Logger LOGGER = LoggerFactory.getLogger(MereScolaire.class);

	public static final String MereScolaire_UnNom = "une mère";
	public static final String MereScolaire_Ce = "cette ";
	public static final String MereScolaire_CeNom = "cette mère";
	public static final String MereScolaire_Un = "une ";
	public static final String MereScolaire_LeNom = "la mère";
	public static final String MereScolaire_NomSingulier = "mère";
	public static final String MereScolaire_NomPluriel = "mères";
	public static final String MereScolaire_NomActuel = "mère actuelle";
	public static final String MereScolaire_Tous = "all ";
	public static final String MereScolaire_TousNom = "toutes les mères";
	public static final String MereScolaire_RechercherTousNomPar = "rechercher mères par ";
	public static final String MereScolaire_RechercherTousNom = "rechercher mères";
	public static final String MereScolaire_LesNoms = "les mères";
	public static final String MereScolaire_AucunNomTrouve = "aucune mère trouvée";
	public static final String MereScolaire_NomVar = "mère";
	public static final String MereScolaire_DeNom = "de mère";
	public static final String MereScolaire_NomAdjectifSingulier = "mère";
	public static final String MereScolaire_NomAdjectifPluriel = "mères";
	public static final String MereScolaire_Couleur = "pink";
	public static final String MereScolaire_IconeGroupe = "regular";
	public static final String MereScolaire_IconeNom = "female";

	/////////////
	// mereCle //
	/////////////

	/**	L'entité « mereCle »
	 *	 is defined as null before being initialized. 
	 */
	protected Long mereCle;
	@JsonIgnore
	public Couverture<Long> mereCleCouverture = new Couverture<Long>().p(this).c(Long.class).var("mereCle").o(mereCle);

	/**	<br/>L'entité « mereCle »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:mereCle">Trouver l'entité mereCle dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _mereCle(Couverture<Long> c);

	public Long getMereCle() {
		return mereCle;
	}

	public void setMereCle(Long mereCle) {
		this.mereCle = mereCle;
		this.mereCleCouverture.dejaInitialise = true;
	}
	public MereScolaire setMereCle(String o) {
		if(NumberUtils.isParsable(o))
			this.mereCle = Long.parseLong(o);
		this.mereCleCouverture.dejaInitialise = true;
		return (MereScolaire)this;
	}
	protected MereScolaire mereCleInit() {
		if(!mereCleCouverture.dejaInitialise) {
			_mereCle(mereCleCouverture);
			if(mereCle == null)
				setMereCle(mereCleCouverture.o);
		}
		mereCleCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public Long solrMereCle() {
		return mereCle;
	}

	public String strMereCle() {
		return mereCle == null ? "" : mereCle.toString();
	}

	public String jsonMereCle() {
		return mereCle == null ? "" : mereCle.toString();
	}

	public String nomAffichageMereCle() {
		return "clé";
	}

	public String htmTooltipMereCle() {
		return null;
	}

	public String htmMereCle() {
		return mereCle == null ? "" : StringEscapeUtils.escapeHtml4(strMereCle());
	}

	public void htmMereCle(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "MereCle\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "MereCle() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setMereCle\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageMereCle()), "</span>");
				r.s("			<input");
							r.s(" name=\"mereCle\"");
							r.s(" value=\"", htmMereCle(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmMereCle());
			}
			r.l("</div>");
		}
	}

	/////////////////////
	// inscriptionCles //
	/////////////////////

	/**	L'entité « inscriptionCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	protected List<Long> inscriptionCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> inscriptionClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("inscriptionCles").o(inscriptionCles);

	/**	<br/>L'entité « inscriptionCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionCles">Trouver l'entité inscriptionCles dans Solr</a>
	 * <br/>
	 * @param inscriptionCles est l'entité déjà construit. 
	 **/
	protected abstract void _inscriptionCles(List<Long> o);

	public List<Long> getInscriptionCles() {
		return inscriptionCles;
	}

	public void setInscriptionCles(List<Long> inscriptionCles) {
		this.inscriptionCles = inscriptionCles;
		this.inscriptionClesCouverture.dejaInitialise = true;
	}
	public MereScolaire addInscriptionCles(Long...objets) {
		for(Long o : objets) {
			addInscriptionCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addInscriptionCles(Long o) {
		if(o != null && !inscriptionCles.contains(o))
			this.inscriptionCles.add(o);
		return (MereScolaire)this;
	}
	public MereScolaire setInscriptionCles(JsonArray objets) {
		inscriptionCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addInscriptionCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addInscriptionCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addInscriptionCles(p);
		}
		return (MereScolaire)this;
	}
	protected MereScolaire inscriptionClesInit() {
		if(!inscriptionClesCouverture.dejaInitialise) {
			_inscriptionCles(inscriptionCles);
		}
		inscriptionClesCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public List<Long> solrInscriptionCles() {
		return inscriptionCles;
	}

	public String strInscriptionCles() {
		return inscriptionCles == null ? "" : inscriptionCles.toString();
	}

	public String jsonInscriptionCles() {
		return inscriptionCles == null ? "" : inscriptionCles.toString();
	}

	public String nomAffichageInscriptionCles() {
		return "inscriptions";
	}

	public String htmTooltipInscriptionCles() {
		return null;
	}

	public String htmInscriptionCles() {
		return inscriptionCles == null ? "" : StringEscapeUtils.escapeHtml4(strInscriptionCles());
	}

	public void htmInscriptionCles(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "InscriptionCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "InscriptionCles() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setInscriptionCles\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageInscriptionCles()), "</span>");
				r.s("			<input");
							r.s(" name=\"inscriptionCles\"");
							r.s(" value=\"", htmInscriptionCles(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmInscriptionCles());
			}
			r.l("</div>");
		}
	}

	////////////////
	// familleTri //
	////////////////

	/**	L'entité « familleTri »
	 *	 is defined as null before being initialized. 
	 */
	protected Integer familleTri;
	@JsonIgnore
	public Couverture<Integer> familleTriCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("familleTri").o(familleTri);

	/**	<br/>L'entité « familleTri »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:familleTri">Trouver l'entité familleTri dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _familleTri(Couverture<Integer> c);

	public Integer getFamilleTri() {
		return familleTri;
	}

	public void setFamilleTri(Integer familleTri) {
		this.familleTri = familleTri;
		this.familleTriCouverture.dejaInitialise = true;
	}
	public MereScolaire setFamilleTri(String o) {
		if(NumberUtils.isParsable(o))
			this.familleTri = Integer.parseInt(o);
		this.familleTriCouverture.dejaInitialise = true;
		return (MereScolaire)this;
	}
	protected MereScolaire familleTriInit() {
		if(!familleTriCouverture.dejaInitialise) {
			_familleTri(familleTriCouverture);
			if(familleTri == null)
				setFamilleTri(familleTriCouverture.o);
		}
		familleTriCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public Integer solrFamilleTri() {
		return familleTri;
	}

	public String strFamilleTri() {
		return familleTri == null ? "" : familleTri.toString();
	}

	public String jsonFamilleTri() {
		return familleTri == null ? "" : familleTri.toString();
	}

	public String nomAffichageFamilleTri() {
		return null;
	}

	public String htmTooltipFamilleTri() {
		return null;
	}

	public String htmFamilleTri() {
		return familleTri == null ? "" : StringEscapeUtils.escapeHtml4(strFamilleTri());
	}

	public void htmFamilleTri(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "FamilleTri\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "FamilleTri() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setFamilleTri\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageFamilleTri()), "</span>");
				r.s("			<input");
							r.s(" name=\"familleTri\"");
							r.s(" value=\"", htmFamilleTri(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmFamilleTri());
			}
			r.l("</div>");
		}
	}

	/////////////
	// mereTri //
	/////////////

	/**	L'entité « mereTri »
	 *	 is defined as null before being initialized. 
	 */
	protected Integer mereTri;
	@JsonIgnore
	public Couverture<Integer> mereTriCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("mereTri").o(mereTri);

	/**	<br/>L'entité « mereTri »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:mereTri">Trouver l'entité mereTri dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _mereTri(Couverture<Integer> c);

	public Integer getMereTri() {
		return mereTri;
	}

	public void setMereTri(Integer mereTri) {
		this.mereTri = mereTri;
		this.mereTriCouverture.dejaInitialise = true;
	}
	public MereScolaire setMereTri(String o) {
		if(NumberUtils.isParsable(o))
			this.mereTri = Integer.parseInt(o);
		this.mereTriCouverture.dejaInitialise = true;
		return (MereScolaire)this;
	}
	protected MereScolaire mereTriInit() {
		if(!mereTriCouverture.dejaInitialise) {
			_mereTri(mereTriCouverture);
			if(mereTri == null)
				setMereTri(mereTriCouverture.o);
		}
		mereTriCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public Integer solrMereTri() {
		return mereTri;
	}

	public String strMereTri() {
		return mereTri == null ? "" : mereTri.toString();
	}

	public String jsonMereTri() {
		return mereTri == null ? "" : mereTri.toString();
	}

	public String nomAffichageMereTri() {
		return null;
	}

	public String htmTooltipMereTri() {
		return null;
	}

	public String htmMereTri() {
		return mereTri == null ? "" : StringEscapeUtils.escapeHtml4(strMereTri());
	}

	public void htmMereTri(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "MereTri\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "MereTri() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setMereTri\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageMereTri()), "</span>");
				r.s("			<input");
							r.s(" name=\"mereTri\"");
							r.s(" value=\"", htmMereTri(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmMereTri());
			}
			r.l("</div>");
		}
	}

	//////////////////////////
	// inscriptionRecherche //
	//////////////////////////

	/**	L'entité « inscriptionRecherche »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut ListeRecherche<InscriptionScolaire>(). 
	 */
	@JsonIgnore
	protected ListeRecherche<InscriptionScolaire> inscriptionRecherche = new ListeRecherche<InscriptionScolaire>();
	@JsonIgnore
	public Couverture<ListeRecherche<InscriptionScolaire>> inscriptionRechercheCouverture = new Couverture<ListeRecherche<InscriptionScolaire>>().p(this).c(ListeRecherche.class).var("inscriptionRecherche").o(inscriptionRecherche);

	/**	<br/>L'entité « inscriptionRecherche »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut ListeRecherche<InscriptionScolaire>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionRecherche">Trouver l'entité inscriptionRecherche dans Solr</a>
	 * <br/>
	 * @param inscriptionRecherche est l'entité déjà construit. 
	 **/
	protected abstract void _inscriptionRecherche(ListeRecherche<InscriptionScolaire> l);

	public ListeRecherche<InscriptionScolaire> getInscriptionRecherche() {
		return inscriptionRecherche;
	}

	public void setInscriptionRecherche(ListeRecherche<InscriptionScolaire> inscriptionRecherche) {
		this.inscriptionRecherche = inscriptionRecherche;
		this.inscriptionRechercheCouverture.dejaInitialise = true;
	}
	protected MereScolaire inscriptionRechercheInit() {
		if(!inscriptionRechercheCouverture.dejaInitialise) {
			_inscriptionRecherche(inscriptionRecherche);
		}
		inscriptionRecherche.initLoinPourClasse(requeteSite_);
		inscriptionRechercheCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	//////////////////
	// inscriptions //
	//////////////////

	/**	L'entité « inscriptions »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<InscriptionScolaire>(). 
	 */
	@JsonIgnore
	protected List<InscriptionScolaire> inscriptions = new java.util.ArrayList<org.computate.scolaire.frFR.inscription.InscriptionScolaire>();
	@JsonIgnore
	public Couverture<List<InscriptionScolaire>> inscriptionsCouverture = new Couverture<List<InscriptionScolaire>>().p(this).c(List.class).var("inscriptions").o(inscriptions);

	/**	<br/>L'entité « inscriptions »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<InscriptionScolaire>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptions">Trouver l'entité inscriptions dans Solr</a>
	 * <br/>
	 * @param inscriptions est l'entité déjà construit. 
	 **/
	protected abstract void _inscriptions(List<InscriptionScolaire> l);

	public List<InscriptionScolaire> getInscriptions() {
		return inscriptions;
	}

	public void setInscriptions(List<InscriptionScolaire> inscriptions) {
		this.inscriptions = inscriptions;
		this.inscriptionsCouverture.dejaInitialise = true;
	}
	public MereScolaire addInscriptions(InscriptionScolaire...objets) {
		for(InscriptionScolaire o : objets) {
			addInscriptions(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addInscriptions(InscriptionScolaire o) {
		if(o != null && !inscriptions.contains(o))
			this.inscriptions.add(o);
		return (MereScolaire)this;
	}
	protected MereScolaire inscriptionsInit() {
		if(!inscriptionsCouverture.dejaInitialise) {
			_inscriptions(inscriptions);
		}
		inscriptionsCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	///////////////
	// ecoleCles //
	///////////////

	/**	L'entité « ecoleCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	protected List<Long> ecoleCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> ecoleClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("ecoleCles").o(ecoleCles);

	/**	<br/>L'entité « ecoleCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:ecoleCles">Trouver l'entité ecoleCles dans Solr</a>
	 * <br/>
	 * @param ecoleCles est l'entité déjà construit. 
	 **/
	protected abstract void _ecoleCles(List<Long> l);

	public List<Long> getEcoleCles() {
		return ecoleCles;
	}

	public void setEcoleCles(List<Long> ecoleCles) {
		this.ecoleCles = ecoleCles;
		this.ecoleClesCouverture.dejaInitialise = true;
	}
	public MereScolaire addEcoleCles(Long...objets) {
		for(Long o : objets) {
			addEcoleCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addEcoleCles(Long o) {
		if(o != null && !ecoleCles.contains(o))
			this.ecoleCles.add(o);
		return (MereScolaire)this;
	}
	public MereScolaire setEcoleCles(JsonArray objets) {
		ecoleCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addEcoleCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addEcoleCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addEcoleCles(p);
		}
		return (MereScolaire)this;
	}
	protected MereScolaire ecoleClesInit() {
		if(!ecoleClesCouverture.dejaInitialise) {
			_ecoleCles(ecoleCles);
		}
		ecoleClesCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public List<Long> solrEcoleCles() {
		return ecoleCles;
	}

	public String strEcoleCles() {
		return ecoleCles == null ? "" : ecoleCles.toString();
	}

	public String jsonEcoleCles() {
		return ecoleCles == null ? "" : ecoleCles.toString();
	}

	public String nomAffichageEcoleCles() {
		return "écoles";
	}

	public String htmTooltipEcoleCles() {
		return null;
	}

	public String htmEcoleCles() {
		return ecoleCles == null ? "" : StringEscapeUtils.escapeHtml4(strEcoleCles());
	}

	public void htmEcoleCles(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "EcoleCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "EcoleCles() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setEcoleCles\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageEcoleCles()), "</span>");
				r.s("			<input");
							r.s(" name=\"ecoleCles\"");
							r.s(" value=\"", htmEcoleCles(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmEcoleCles());
			}
			r.l("</div>");
		}
	}

	///////////////
	// anneeCles //
	///////////////

	/**	L'entité « anneeCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	protected List<Long> anneeCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> anneeClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("anneeCles").o(anneeCles);

	/**	<br/>L'entité « anneeCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:anneeCles">Trouver l'entité anneeCles dans Solr</a>
	 * <br/>
	 * @param anneeCles est l'entité déjà construit. 
	 **/
	protected abstract void _anneeCles(List<Long> l);

	public List<Long> getAnneeCles() {
		return anneeCles;
	}

	public void setAnneeCles(List<Long> anneeCles) {
		this.anneeCles = anneeCles;
		this.anneeClesCouverture.dejaInitialise = true;
	}
	public MereScolaire addAnneeCles(Long...objets) {
		for(Long o : objets) {
			addAnneeCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addAnneeCles(Long o) {
		if(o != null && !anneeCles.contains(o))
			this.anneeCles.add(o);
		return (MereScolaire)this;
	}
	public MereScolaire setAnneeCles(JsonArray objets) {
		anneeCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addAnneeCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addAnneeCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addAnneeCles(p);
		}
		return (MereScolaire)this;
	}
	protected MereScolaire anneeClesInit() {
		if(!anneeClesCouverture.dejaInitialise) {
			_anneeCles(anneeCles);
		}
		anneeClesCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public List<Long> solrAnneeCles() {
		return anneeCles;
	}

	public String strAnneeCles() {
		return anneeCles == null ? "" : anneeCles.toString();
	}

	public String jsonAnneeCles() {
		return anneeCles == null ? "" : anneeCles.toString();
	}

	public String nomAffichageAnneeCles() {
		return "années";
	}

	public String htmTooltipAnneeCles() {
		return null;
	}

	public String htmAnneeCles() {
		return anneeCles == null ? "" : StringEscapeUtils.escapeHtml4(strAnneeCles());
	}

	public void htmAnneeCles(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "AnneeCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "AnneeCles() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setAnneeCles\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageAnneeCles()), "</span>");
				r.s("			<input");
							r.s(" name=\"anneeCles\"");
							r.s(" value=\"", htmAnneeCles(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmAnneeCles());
			}
			r.l("</div>");
		}
	}

	////////////////
	// saisonCles //
	////////////////

	/**	L'entité « saisonCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	protected List<Long> saisonCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> saisonClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("saisonCles").o(saisonCles);

	/**	<br/>L'entité « saisonCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:saisonCles">Trouver l'entité saisonCles dans Solr</a>
	 * <br/>
	 * @param saisonCles est l'entité déjà construit. 
	 **/
	protected abstract void _saisonCles(List<Long> l);

	public List<Long> getSaisonCles() {
		return saisonCles;
	}

	public void setSaisonCles(List<Long> saisonCles) {
		this.saisonCles = saisonCles;
		this.saisonClesCouverture.dejaInitialise = true;
	}
	public MereScolaire addSaisonCles(Long...objets) {
		for(Long o : objets) {
			addSaisonCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addSaisonCles(Long o) {
		if(o != null && !saisonCles.contains(o))
			this.saisonCles.add(o);
		return (MereScolaire)this;
	}
	public MereScolaire setSaisonCles(JsonArray objets) {
		saisonCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSaisonCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addSaisonCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSaisonCles(p);
		}
		return (MereScolaire)this;
	}
	protected MereScolaire saisonClesInit() {
		if(!saisonClesCouverture.dejaInitialise) {
			_saisonCles(saisonCles);
		}
		saisonClesCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public List<Long> solrSaisonCles() {
		return saisonCles;
	}

	public String strSaisonCles() {
		return saisonCles == null ? "" : saisonCles.toString();
	}

	public String jsonSaisonCles() {
		return saisonCles == null ? "" : saisonCles.toString();
	}

	public String nomAffichageSaisonCles() {
		return "saisons";
	}

	public String htmTooltipSaisonCles() {
		return null;
	}

	public String htmSaisonCles() {
		return saisonCles == null ? "" : StringEscapeUtils.escapeHtml4(strSaisonCles());
	}

	public void htmSaisonCles(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "SaisonCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "SaisonCles() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setSaisonCles\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageSaisonCles()), "</span>");
				r.s("			<input");
							r.s(" name=\"saisonCles\"");
							r.s(" value=\"", htmSaisonCles(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmSaisonCles());
			}
			r.l("</div>");
		}
	}

	/////////////////
	// sessionCles //
	/////////////////

	/**	L'entité « sessionCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	protected List<Long> sessionCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> sessionClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("sessionCles").o(sessionCles);

	/**	<br/>L'entité « sessionCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:sessionCles">Trouver l'entité sessionCles dans Solr</a>
	 * <br/>
	 * @param sessionCles est l'entité déjà construit. 
	 **/
	protected abstract void _sessionCles(List<Long> l);

	public List<Long> getSessionCles() {
		return sessionCles;
	}

	public void setSessionCles(List<Long> sessionCles) {
		this.sessionCles = sessionCles;
		this.sessionClesCouverture.dejaInitialise = true;
	}
	public MereScolaire addSessionCles(Long...objets) {
		for(Long o : objets) {
			addSessionCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addSessionCles(Long o) {
		if(o != null && !sessionCles.contains(o))
			this.sessionCles.add(o);
		return (MereScolaire)this;
	}
	public MereScolaire setSessionCles(JsonArray objets) {
		sessionCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSessionCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addSessionCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSessionCles(p);
		}
		return (MereScolaire)this;
	}
	protected MereScolaire sessionClesInit() {
		if(!sessionClesCouverture.dejaInitialise) {
			_sessionCles(sessionCles);
		}
		sessionClesCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public List<Long> solrSessionCles() {
		return sessionCles;
	}

	public String strSessionCles() {
		return sessionCles == null ? "" : sessionCles.toString();
	}

	public String jsonSessionCles() {
		return sessionCles == null ? "" : sessionCles.toString();
	}

	public String nomAffichageSessionCles() {
		return "sessions";
	}

	public String htmTooltipSessionCles() {
		return null;
	}

	public String htmSessionCles() {
		return sessionCles == null ? "" : StringEscapeUtils.escapeHtml4(strSessionCles());
	}

	public void htmSessionCles(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "SessionCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "SessionCles() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setSessionCles\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageSessionCles()), "</span>");
				r.s("			<input");
							r.s(" name=\"sessionCles\"");
							r.s(" value=\"", htmSessionCles(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmSessionCles());
			}
			r.l("</div>");
		}
	}

	/////////////
	// ageCles //
	/////////////

	/**	L'entité « ageCles »
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	protected List<Long> ageCles = new java.util.ArrayList<java.lang.Long>();
	@JsonIgnore
	public Couverture<List<Long>> ageClesCouverture = new Couverture<List<Long>>().p(this).c(List.class).var("ageCles").o(ageCles);

	/**	<br/>L'entité « ageCles »
	 * Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:ageCles">Trouver l'entité ageCles dans Solr</a>
	 * <br/>
	 * @param ageCles est l'entité déjà construit. 
	 **/
	protected abstract void _ageCles(List<Long> l);

	public List<Long> getAgeCles() {
		return ageCles;
	}

	public void setAgeCles(List<Long> ageCles) {
		this.ageCles = ageCles;
		this.ageClesCouverture.dejaInitialise = true;
	}
	public MereScolaire addAgeCles(Long...objets) {
		for(Long o : objets) {
			addAgeCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addAgeCles(Long o) {
		if(o != null && !ageCles.contains(o))
			this.ageCles.add(o);
		return (MereScolaire)this;
	}
	public MereScolaire setAgeCles(JsonArray objets) {
		ageCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addAgeCles(o);
		}
		return (MereScolaire)this;
	}
	public MereScolaire addAgeCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addAgeCles(p);
		}
		return (MereScolaire)this;
	}
	protected MereScolaire ageClesInit() {
		if(!ageClesCouverture.dejaInitialise) {
			_ageCles(ageCles);
		}
		ageClesCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public List<Long> solrAgeCles() {
		return ageCles;
	}

	public String strAgeCles() {
		return ageCles == null ? "" : ageCles.toString();
	}

	public String jsonAgeCles() {
		return ageCles == null ? "" : ageCles.toString();
	}

	public String nomAffichageAgeCles() {
		return "âges";
	}

	public String htmTooltipAgeCles() {
		return null;
	}

	public String htmAgeCles() {
		return ageCles == null ? "" : StringEscapeUtils.escapeHtml4(strAgeCles());
	}

	public void htmAgeCles(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "AgeCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "AgeCles() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setAgeCles\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageAgeCles()), "</span>");
				r.s("			<input");
							r.s(" name=\"ageCles\"");
							r.s(" value=\"", htmAgeCles(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmAgeCles());
			}
			r.l("</div>");
		}
	}

	////////////////////
	// personnePrenom //
	////////////////////

	/**	L'entité « personnePrenom »
	 *	 is defined as null before being initialized. 
	 */
	protected String personnePrenom;
	@JsonIgnore
	public Couverture<String> personnePrenomCouverture = new Couverture<String>().p(this).c(String.class).var("personnePrenom").o(personnePrenom);

	/**	<br/>L'entité « personnePrenom »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personnePrenom">Trouver l'entité personnePrenom dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personnePrenom(Couverture<String> c);

	public String getPersonnePrenom() {
		return personnePrenom;
	}

	public void setPersonnePrenom(String personnePrenom) {
		this.personnePrenom = personnePrenom;
		this.personnePrenomCouverture.dejaInitialise = true;
	}
	protected MereScolaire personnePrenomInit() {
		if(!personnePrenomCouverture.dejaInitialise) {
			_personnePrenom(personnePrenomCouverture);
			if(personnePrenom == null)
				setPersonnePrenom(personnePrenomCouverture.o);
		}
		personnePrenomCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrPersonnePrenom() {
		return personnePrenom;
	}

	public String strPersonnePrenom() {
		return personnePrenom == null ? "" : personnePrenom;
	}

	public String jsonPersonnePrenom() {
		return personnePrenom == null ? "" : personnePrenom;
	}

	public String nomAffichagePersonnePrenom() {
		return "prénom";
	}

	public String htmTooltipPersonnePrenom() {
		return null;
	}

	public String htmPersonnePrenom() {
		return personnePrenom == null ? "" : StringEscapeUtils.escapeHtml4(strPersonnePrenom());
	}

	public void htmPersonnePrenom(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonnePrenom\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonnePrenom() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonnePrenom\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonnePrenom()), "</span>");
				r.s("			<input");
							r.s(" name=\"personnePrenom\"");
							r.s(" value=\"", htmPersonnePrenom(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonnePrenom());
			}
			r.l("</div>");
		}
	}

	////////////////
	// familleNom //
	////////////////

	/**	L'entité « familleNom »
	 *	 is defined as null before being initialized. 
	 */
	protected String familleNom;
	@JsonIgnore
	public Couverture<String> familleNomCouverture = new Couverture<String>().p(this).c(String.class).var("familleNom").o(familleNom);

	/**	<br/>L'entité « familleNom »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:familleNom">Trouver l'entité familleNom dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _familleNom(Couverture<String> c);

	public String getFamilleNom() {
		return familleNom;
	}

	public void setFamilleNom(String familleNom) {
		this.familleNom = familleNom;
		this.familleNomCouverture.dejaInitialise = true;
	}
	protected MereScolaire familleNomInit() {
		if(!familleNomCouverture.dejaInitialise) {
			_familleNom(familleNomCouverture);
			if(familleNom == null)
				setFamilleNom(familleNomCouverture.o);
		}
		familleNomCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrFamilleNom() {
		return familleNom;
	}

	public String strFamilleNom() {
		return familleNom == null ? "" : familleNom;
	}

	public String jsonFamilleNom() {
		return familleNom == null ? "" : familleNom;
	}

	public String nomAffichageFamilleNom() {
		return "nom de famille";
	}

	public String htmTooltipFamilleNom() {
		return null;
	}

	public String htmFamilleNom() {
		return familleNom == null ? "" : StringEscapeUtils.escapeHtml4(strFamilleNom());
	}

	public void htmFamilleNom(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "FamilleNom\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "FamilleNom() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setFamilleNom\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageFamilleNom()), "</span>");
				r.s("			<input");
							r.s(" name=\"familleNom\"");
							r.s(" value=\"", htmFamilleNom(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmFamilleNom());
			}
			r.l("</div>");
		}
	}

	////////////////////////
	// personneNomComplet //
	////////////////////////

	/**	L'entité « personneNomComplet »
	 *	 is defined as null before being initialized. 
	 */
	protected String personneNomComplet;
	@JsonIgnore
	public Couverture<String> personneNomCompletCouverture = new Couverture<String>().p(this).c(String.class).var("personneNomComplet").o(personneNomComplet);

	/**	<br/>L'entité « personneNomComplet »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNomComplet">Trouver l'entité personneNomComplet dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneNomComplet(Couverture<String> c);

	public String getPersonneNomComplet() {
		return personneNomComplet;
	}

	public void setPersonneNomComplet(String personneNomComplet) {
		this.personneNomComplet = personneNomComplet;
		this.personneNomCompletCouverture.dejaInitialise = true;
	}
	protected MereScolaire personneNomCompletInit() {
		if(!personneNomCompletCouverture.dejaInitialise) {
			_personneNomComplet(personneNomCompletCouverture);
			if(personneNomComplet == null)
				setPersonneNomComplet(personneNomCompletCouverture.o);
		}
		personneNomCompletCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrPersonneNomComplet() {
		return personneNomComplet;
	}

	public String strPersonneNomComplet() {
		return personneNomComplet == null ? "" : personneNomComplet;
	}

	public String jsonPersonneNomComplet() {
		return personneNomComplet == null ? "" : personneNomComplet;
	}

	public String nomAffichagePersonneNomComplet() {
		return "nom complèt";
	}

	public String htmTooltipPersonneNomComplet() {
		return null;
	}

	public String htmPersonneNomComplet() {
		return personneNomComplet == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneNomComplet());
	}

	public void htmPersonneNomComplet(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneNomComplet\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneNomComplet() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneNomComplet\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneNomComplet()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneNomComplet\"");
							r.s(" value=\"", htmPersonneNomComplet(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneNomComplet());
			}
			r.l("</div>");
		}
	}

	///////////////////////////////
	// personneNomCompletPrefere //
	///////////////////////////////

	/**	L'entité « personneNomCompletPrefere »
	 *	 is defined as null before being initialized. 
	 */
	protected String personneNomCompletPrefere;
	@JsonIgnore
	public Couverture<String> personneNomCompletPrefereCouverture = new Couverture<String>().p(this).c(String.class).var("personneNomCompletPrefere").o(personneNomCompletPrefere);

	/**	<br/>L'entité « personneNomCompletPrefere »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNomCompletPrefere">Trouver l'entité personneNomCompletPrefere dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneNomCompletPrefere(Couverture<String> c);

	public String getPersonneNomCompletPrefere() {
		return personneNomCompletPrefere;
	}

	public void setPersonneNomCompletPrefere(String personneNomCompletPrefere) {
		this.personneNomCompletPrefere = personneNomCompletPrefere;
		this.personneNomCompletPrefereCouverture.dejaInitialise = true;
	}
	protected MereScolaire personneNomCompletPrefereInit() {
		if(!personneNomCompletPrefereCouverture.dejaInitialise) {
			_personneNomCompletPrefere(personneNomCompletPrefereCouverture);
			if(personneNomCompletPrefere == null)
				setPersonneNomCompletPrefere(personneNomCompletPrefereCouverture.o);
		}
		personneNomCompletPrefereCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrPersonneNomCompletPrefere() {
		return personneNomCompletPrefere;
	}

	public String strPersonneNomCompletPrefere() {
		return personneNomCompletPrefere == null ? "" : personneNomCompletPrefere;
	}

	public String jsonPersonneNomCompletPrefere() {
		return personneNomCompletPrefere == null ? "" : personneNomCompletPrefere;
	}

	public String nomAffichagePersonneNomCompletPrefere() {
		return "nom complèt préferé";
	}

	public String htmTooltipPersonneNomCompletPrefere() {
		return null;
	}

	public String htmPersonneNomCompletPrefere() {
		return personneNomCompletPrefere == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneNomCompletPrefere());
	}

	public void htmPersonneNomCompletPrefere(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneNomCompletPrefere\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneNomCompletPrefere() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneNomCompletPrefere\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneNomCompletPrefere()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneNomCompletPrefere\"");
							r.s(" value=\"", htmPersonneNomCompletPrefere(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneNomCompletPrefere());
			}
			r.l("</div>");
		}
	}

	///////////////////////
	// personneNomFormel //
	///////////////////////

	/**	L'entité « personneNomFormel »
	 *	 is defined as null before being initialized. 
	 */
	protected String personneNomFormel;
	@JsonIgnore
	public Couverture<String> personneNomFormelCouverture = new Couverture<String>().p(this).c(String.class).var("personneNomFormel").o(personneNomFormel);

	/**	<br/>L'entité « personneNomFormel »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNomFormel">Trouver l'entité personneNomFormel dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneNomFormel(Couverture<String> c);

	public String getPersonneNomFormel() {
		return personneNomFormel;
	}

	public void setPersonneNomFormel(String personneNomFormel) {
		this.personneNomFormel = personneNomFormel;
		this.personneNomFormelCouverture.dejaInitialise = true;
	}
	protected MereScolaire personneNomFormelInit() {
		if(!personneNomFormelCouverture.dejaInitialise) {
			_personneNomFormel(personneNomFormelCouverture);
			if(personneNomFormel == null)
				setPersonneNomFormel(personneNomFormelCouverture.o);
		}
		personneNomFormelCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrPersonneNomFormel() {
		return personneNomFormel;
	}

	public String strPersonneNomFormel() {
		return personneNomFormel == null ? "" : personneNomFormel;
	}

	public String jsonPersonneNomFormel() {
		return personneNomFormel == null ? "" : personneNomFormel;
	}

	public String nomAffichagePersonneNomFormel() {
		return "nom formel";
	}

	public String htmTooltipPersonneNomFormel() {
		return null;
	}

	public String htmPersonneNomFormel() {
		return personneNomFormel == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneNomFormel());
	}

	public void htmPersonneNomFormel(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneNomFormel\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneNomFormel() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneNomFormel\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneNomFormel()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneNomFormel\"");
							r.s(" value=\"", htmPersonneNomFormel(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneNomFormel());
			}
			r.l("</div>");
		}
	}

	////////////////////////
	// personneOccupation //
	////////////////////////

	/**	L'entité « personneOccupation »
	 *	 is defined as null before being initialized. 
	 */
	protected String personneOccupation;
	@JsonIgnore
	public Couverture<String> personneOccupationCouverture = new Couverture<String>().p(this).c(String.class).var("personneOccupation").o(personneOccupation);

	/**	<br/>L'entité « personneOccupation »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneOccupation">Trouver l'entité personneOccupation dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneOccupation(Couverture<String> c);

	public String getPersonneOccupation() {
		return personneOccupation;
	}

	public void setPersonneOccupation(String personneOccupation) {
		this.personneOccupation = personneOccupation;
		this.personneOccupationCouverture.dejaInitialise = true;
	}
	protected MereScolaire personneOccupationInit() {
		if(!personneOccupationCouverture.dejaInitialise) {
			_personneOccupation(personneOccupationCouverture);
			if(personneOccupation == null)
				setPersonneOccupation(personneOccupationCouverture.o);
		}
		personneOccupationCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrPersonneOccupation() {
		return personneOccupation;
	}

	public String strPersonneOccupation() {
		return personneOccupation == null ? "" : personneOccupation;
	}

	public String jsonPersonneOccupation() {
		return personneOccupation == null ? "" : personneOccupation;
	}

	public String nomAffichagePersonneOccupation() {
		return "occupation";
	}

	public String htmTooltipPersonneOccupation() {
		return null;
	}

	public String htmPersonneOccupation() {
		return personneOccupation == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneOccupation());
	}

	public void htmPersonneOccupation(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneOccupation\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneOccupation() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneOccupation\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneOccupation()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneOccupation\"");
							r.s(" value=\"", htmPersonneOccupation(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneOccupation());
			}
			r.l("</div>");
		}
	}

	/////////////////////////////
	// personneNumeroTelephone //
	/////////////////////////////

	/**	L'entité « personneNumeroTelephone »
	 *	 is defined as null before being initialized. 
	 */
	protected String personneNumeroTelephone;
	@JsonIgnore
	public Couverture<String> personneNumeroTelephoneCouverture = new Couverture<String>().p(this).c(String.class).var("personneNumeroTelephone").o(personneNumeroTelephone);

	/**	<br/>L'entité « personneNumeroTelephone »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNumeroTelephone">Trouver l'entité personneNumeroTelephone dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneNumeroTelephone(Couverture<String> c);

	public String getPersonneNumeroTelephone() {
		return personneNumeroTelephone;
	}

	public void setPersonneNumeroTelephone(String personneNumeroTelephone) {
		this.personneNumeroTelephone = personneNumeroTelephone;
		this.personneNumeroTelephoneCouverture.dejaInitialise = true;
	}
	protected MereScolaire personneNumeroTelephoneInit() {
		if(!personneNumeroTelephoneCouverture.dejaInitialise) {
			_personneNumeroTelephone(personneNumeroTelephoneCouverture);
			if(personneNumeroTelephone == null)
				setPersonneNumeroTelephone(personneNumeroTelephoneCouverture.o);
		}
		personneNumeroTelephoneCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrPersonneNumeroTelephone() {
		return personneNumeroTelephone;
	}

	public String strPersonneNumeroTelephone() {
		return personneNumeroTelephone == null ? "" : personneNumeroTelephone;
	}

	public String jsonPersonneNumeroTelephone() {
		return personneNumeroTelephone == null ? "" : personneNumeroTelephone;
	}

	public String nomAffichagePersonneNumeroTelephone() {
		return "numéro de téléphone";
	}

	public String htmTooltipPersonneNumeroTelephone() {
		return null;
	}

	public String htmPersonneNumeroTelephone() {
		return personneNumeroTelephone == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneNumeroTelephone());
	}

	public void htmPersonneNumeroTelephone(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneNumeroTelephone\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneNumeroTelephone() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneNumeroTelephone\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneNumeroTelephone()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneNumeroTelephone\"");
							r.s(" value=\"", htmPersonneNumeroTelephone(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneNumeroTelephone());
			}
			r.l("</div>");
		}
	}

	//////////////////
	// personneMail //
	//////////////////

	/**	L'entité « personneMail »
	 *	 is defined as null before being initialized. 
	 */
	protected String personneMail;
	@JsonIgnore
	public Couverture<String> personneMailCouverture = new Couverture<String>().p(this).c(String.class).var("personneMail").o(personneMail);

	/**	<br/>L'entité « personneMail »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneMail">Trouver l'entité personneMail dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneMail(Couverture<String> c);

	public String getPersonneMail() {
		return personneMail;
	}

	public void setPersonneMail(String personneMail) {
		this.personneMail = personneMail;
		this.personneMailCouverture.dejaInitialise = true;
	}
	protected MereScolaire personneMailInit() {
		if(!personneMailCouverture.dejaInitialise) {
			_personneMail(personneMailCouverture);
			if(personneMail == null)
				setPersonneMail(personneMailCouverture.o);
		}
		personneMailCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrPersonneMail() {
		return personneMail;
	}

	public String strPersonneMail() {
		return personneMail == null ? "" : personneMail;
	}

	public String jsonPersonneMail() {
		return personneMail == null ? "" : personneMail;
	}

	public String nomAffichagePersonneMail() {
		return "mail";
	}

	public String htmTooltipPersonneMail() {
		return null;
	}

	public String htmPersonneMail() {
		return personneMail == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneMail());
	}

	public void htmPersonneMail(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneMail\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneMail() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneMail\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneMail()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneMail\"");
							r.s(" value=\"", htmPersonneMail(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneMail());
			}
			r.l("</div>");
		}
	}

	//////////////////////
	// personneRelation //
	//////////////////////

	/**	L'entité « personneRelation »
	 *	 is defined as null before being initialized. 
	 */
	protected String personneRelation;
	@JsonIgnore
	public Couverture<String> personneRelationCouverture = new Couverture<String>().p(this).c(String.class).var("personneRelation").o(personneRelation);

	/**	<br/>L'entité « personneRelation »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneRelation">Trouver l'entité personneRelation dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneRelation(Couverture<String> c);

	public String getPersonneRelation() {
		return personneRelation;
	}

	public void setPersonneRelation(String personneRelation) {
		this.personneRelation = personneRelation;
		this.personneRelationCouverture.dejaInitialise = true;
	}
	protected MereScolaire personneRelationInit() {
		if(!personneRelationCouverture.dejaInitialise) {
			_personneRelation(personneRelationCouverture);
			if(personneRelation == null)
				setPersonneRelation(personneRelationCouverture.o);
		}
		personneRelationCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrPersonneRelation() {
		return personneRelation;
	}

	public String strPersonneRelation() {
		return personneRelation == null ? "" : personneRelation;
	}

	public String jsonPersonneRelation() {
		return personneRelation == null ? "" : personneRelation;
	}

	public String nomAffichagePersonneRelation() {
		return "relation";
	}

	public String htmTooltipPersonneRelation() {
		return null;
	}

	public String htmPersonneRelation() {
		return personneRelation == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneRelation());
	}

	public void htmPersonneRelation(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneRelation\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneRelation() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneRelation\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneRelation()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneRelation\"");
							r.s(" value=\"", htmPersonneRelation(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneRelation());
			}
			r.l("</div>");
		}
	}

	/////////////////
	// personneSms //
	/////////////////

	/**	L'entité « personneSms »
	 *	 is defined as null before being initialized. 
	 */
	protected Boolean personneSms;
	@JsonIgnore
	public Couverture<Boolean> personneSmsCouverture = new Couverture<Boolean>().p(this).c(Boolean.class).var("personneSms").o(personneSms);

	/**	<br/>L'entité « personneSms »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneSms">Trouver l'entité personneSms dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneSms(Couverture<Boolean> c);

	public Boolean getPersonneSms() {
		return personneSms;
	}

	public void setPersonneSms(Boolean personneSms) {
		this.personneSms = personneSms;
		this.personneSmsCouverture.dejaInitialise = true;
	}
	public MereScolaire setPersonneSms(String o) {
		this.personneSms = Boolean.parseBoolean(o);
		this.personneSmsCouverture.dejaInitialise = true;
		return (MereScolaire)this;
	}
	protected MereScolaire personneSmsInit() {
		if(!personneSmsCouverture.dejaInitialise) {
			_personneSms(personneSmsCouverture);
			if(personneSms == null)
				setPersonneSms(personneSmsCouverture.o);
		}
		personneSmsCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public Boolean solrPersonneSms() {
		return personneSms;
	}

	public String strPersonneSms() {
		return personneSms == null ? "" : personneSms.toString();
	}

	public String jsonPersonneSms() {
		return personneSms == null ? "" : personneSms.toString();
	}

	public String nomAffichagePersonneSms() {
		return "text me";
	}

	public String htmTooltipPersonneSms() {
		return null;
	}

	public String htmPersonneSms() {
		return personneSms == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneSms());
	}

	public void htmPersonneSms(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneSms\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneSms() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneSms\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneSms()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneSms\"");
							r.s(" value=\"", htmPersonneSms(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneSms());
			}
			r.l("</div>");
		}
	}

	//////////////////////////
	// personneRecevoirMail //
	//////////////////////////

	/**	L'entité « personneRecevoirMail »
	 *	 is defined as null before being initialized. 
	 */
	protected Boolean personneRecevoirMail;
	@JsonIgnore
	public Couverture<Boolean> personneRecevoirMailCouverture = new Couverture<Boolean>().p(this).c(Boolean.class).var("personneRecevoirMail").o(personneRecevoirMail);

	/**	<br/>L'entité « personneRecevoirMail »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneRecevoirMail">Trouver l'entité personneRecevoirMail dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneRecevoirMail(Couverture<Boolean> c);

	public Boolean getPersonneRecevoirMail() {
		return personneRecevoirMail;
	}

	public void setPersonneRecevoirMail(Boolean personneRecevoirMail) {
		this.personneRecevoirMail = personneRecevoirMail;
		this.personneRecevoirMailCouverture.dejaInitialise = true;
	}
	public MereScolaire setPersonneRecevoirMail(String o) {
		this.personneRecevoirMail = Boolean.parseBoolean(o);
		this.personneRecevoirMailCouverture.dejaInitialise = true;
		return (MereScolaire)this;
	}
	protected MereScolaire personneRecevoirMailInit() {
		if(!personneRecevoirMailCouverture.dejaInitialise) {
			_personneRecevoirMail(personneRecevoirMailCouverture);
			if(personneRecevoirMail == null)
				setPersonneRecevoirMail(personneRecevoirMailCouverture.o);
		}
		personneRecevoirMailCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public Boolean solrPersonneRecevoirMail() {
		return personneRecevoirMail;
	}

	public String strPersonneRecevoirMail() {
		return personneRecevoirMail == null ? "" : personneRecevoirMail.toString();
	}

	public String jsonPersonneRecevoirMail() {
		return personneRecevoirMail == null ? "" : personneRecevoirMail.toString();
	}

	public String nomAffichagePersonneRecevoirMail() {
		return "recevoir des mails";
	}

	public String htmTooltipPersonneRecevoirMail() {
		return null;
	}

	public String htmPersonneRecevoirMail() {
		return personneRecevoirMail == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneRecevoirMail());
	}

	public void htmPersonneRecevoirMail(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneRecevoirMail\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneRecevoirMail() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneRecevoirMail\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneRecevoirMail()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneRecevoirMail\"");
							r.s(" value=\"", htmPersonneRecevoirMail(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneRecevoirMail());
			}
			r.l("</div>");
		}
	}

	////////////////////////////
	// personneContactUrgence //
	////////////////////////////

	/**	L'entité « personneContactUrgence »
	 *	 is defined as null before being initialized. 
	 */
	protected Boolean personneContactUrgence;
	@JsonIgnore
	public Couverture<Boolean> personneContactUrgenceCouverture = new Couverture<Boolean>().p(this).c(Boolean.class).var("personneContactUrgence").o(personneContactUrgence);

	/**	<br/>L'entité « personneContactUrgence »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneContactUrgence">Trouver l'entité personneContactUrgence dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneContactUrgence(Couverture<Boolean> c);

	public Boolean getPersonneContactUrgence() {
		return personneContactUrgence;
	}

	public void setPersonneContactUrgence(Boolean personneContactUrgence) {
		this.personneContactUrgence = personneContactUrgence;
		this.personneContactUrgenceCouverture.dejaInitialise = true;
	}
	public MereScolaire setPersonneContactUrgence(String o) {
		this.personneContactUrgence = Boolean.parseBoolean(o);
		this.personneContactUrgenceCouverture.dejaInitialise = true;
		return (MereScolaire)this;
	}
	protected MereScolaire personneContactUrgenceInit() {
		if(!personneContactUrgenceCouverture.dejaInitialise) {
			_personneContactUrgence(personneContactUrgenceCouverture);
			if(personneContactUrgence == null)
				setPersonneContactUrgence(personneContactUrgenceCouverture.o);
		}
		personneContactUrgenceCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public Boolean solrPersonneContactUrgence() {
		return personneContactUrgence;
	}

	public String strPersonneContactUrgence() {
		return personneContactUrgence == null ? "" : personneContactUrgence.toString();
	}

	public String jsonPersonneContactUrgence() {
		return personneContactUrgence == null ? "" : personneContactUrgence.toString();
	}

	public String nomAffichagePersonneContactUrgence() {
		return "contacter en cas d'urgence";
	}

	public String htmTooltipPersonneContactUrgence() {
		return null;
	}

	public String htmPersonneContactUrgence() {
		return personneContactUrgence == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneContactUrgence());
	}

	public void htmPersonneContactUrgence(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneContactUrgence\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneContactUrgence() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneContactUrgence\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneContactUrgence()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneContactUrgence\"");
							r.s(" value=\"", htmPersonneContactUrgence(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneContactUrgence());
			}
			r.l("</div>");
		}
	}

	//////////////////////
	// personneChercher //
	//////////////////////

	/**	L'entité « personneChercher »
	 *	 is defined as null before being initialized. 
	 */
	protected Boolean personneChercher;
	@JsonIgnore
	public Couverture<Boolean> personneChercherCouverture = new Couverture<Boolean>().p(this).c(Boolean.class).var("personneChercher").o(personneChercher);

	/**	<br/>L'entité « personneChercher »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneChercher">Trouver l'entité personneChercher dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _personneChercher(Couverture<Boolean> c);

	public Boolean getPersonneChercher() {
		return personneChercher;
	}

	public void setPersonneChercher(Boolean personneChercher) {
		this.personneChercher = personneChercher;
		this.personneChercherCouverture.dejaInitialise = true;
	}
	public MereScolaire setPersonneChercher(String o) {
		this.personneChercher = Boolean.parseBoolean(o);
		this.personneChercherCouverture.dejaInitialise = true;
		return (MereScolaire)this;
	}
	protected MereScolaire personneChercherInit() {
		if(!personneChercherCouverture.dejaInitialise) {
			_personneChercher(personneChercherCouverture);
			if(personneChercher == null)
				setPersonneChercher(personneChercherCouverture.o);
		}
		personneChercherCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public Boolean solrPersonneChercher() {
		return personneChercher;
	}

	public String strPersonneChercher() {
		return personneChercher == null ? "" : personneChercher.toString();
	}

	public String jsonPersonneChercher() {
		return personneChercher == null ? "" : personneChercher.toString();
	}

	public String nomAffichagePersonneChercher() {
		return "autorisé à venir chercher";
	}

	public String htmTooltipPersonneChercher() {
		return null;
	}

	public String htmPersonneChercher() {
		return personneChercher == null ? "" : StringEscapeUtils.escapeHtml4(strPersonneChercher());
	}

	public void htmPersonneChercher(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PersonneChercher\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PersonneChercher() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPersonneChercher\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePersonneChercher()), "</span>");
				r.s("			<input");
							r.s(" name=\"personneChercher\"");
							r.s(" value=\"", htmPersonneChercher(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPersonneChercher());
			}
			r.l("</div>");
		}
	}

	////////////////////
	// mereNomComplet //
	////////////////////

	/**	L'entité « mereNomComplet »
	 *	 is defined as null before being initialized. 
	 */
	protected String mereNomComplet;
	@JsonIgnore
	public Couverture<String> mereNomCompletCouverture = new Couverture<String>().p(this).c(String.class).var("mereNomComplet").o(mereNomComplet);

	/**	<br/>L'entité « mereNomComplet »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:mereNomComplet">Trouver l'entité mereNomComplet dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _mereNomComplet(Couverture<String> c);

	public String getMereNomComplet() {
		return mereNomComplet;
	}

	public void setMereNomComplet(String mereNomComplet) {
		this.mereNomComplet = mereNomComplet;
		this.mereNomCompletCouverture.dejaInitialise = true;
	}
	protected MereScolaire mereNomCompletInit() {
		if(!mereNomCompletCouverture.dejaInitialise) {
			_mereNomComplet(mereNomCompletCouverture);
			if(mereNomComplet == null)
				setMereNomComplet(mereNomCompletCouverture.o);
		}
		mereNomCompletCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrMereNomComplet() {
		return mereNomComplet;
	}

	public String strMereNomComplet() {
		return mereNomComplet == null ? "" : mereNomComplet;
	}

	public String jsonMereNomComplet() {
		return mereNomComplet == null ? "" : mereNomComplet;
	}

	public String nomAffichageMereNomComplet() {
		return null;
	}

	public String htmTooltipMereNomComplet() {
		return null;
	}

	public String htmMereNomComplet() {
		return mereNomComplet == null ? "" : StringEscapeUtils.escapeHtml4(strMereNomComplet());
	}

	public void htmMereNomComplet(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "MereNomComplet\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "MereNomComplet() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setMereNomComplet\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageMereNomComplet()), "</span>");
				r.s("			<input");
							r.s(" name=\"mereNomComplet\"");
							r.s(" value=\"", htmMereNomComplet(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmMereNomComplet());
			}
			r.l("</div>");
		}
	}

	////////////
	// mereId //
	////////////

	/**	L'entité « mereId »
	 *	 is defined as null before being initialized. 
	 */
	protected String mereId;
	@JsonIgnore
	public Couverture<String> mereIdCouverture = new Couverture<String>().p(this).c(String.class).var("mereId").o(mereId);

	/**	<br/>L'entité « mereId »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:mereId">Trouver l'entité mereId dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _mereId(Couverture<String> c);

	public String getMereId() {
		return mereId;
	}

	public void setMereId(String mereId) {
		this.mereId = mereId;
		this.mereIdCouverture.dejaInitialise = true;
	}
	protected MereScolaire mereIdInit() {
		if(!mereIdCouverture.dejaInitialise) {
			_mereId(mereIdCouverture);
			if(mereId == null)
				setMereId(mereIdCouverture.o);
		}
		mereIdCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrMereId() {
		return mereId;
	}

	public String strMereId() {
		return mereId == null ? "" : mereId;
	}

	public String jsonMereId() {
		return mereId == null ? "" : mereId;
	}

	public String nomAffichageMereId() {
		return "ID";
	}

	public String htmTooltipMereId() {
		return null;
	}

	public String htmMereId() {
		return mereId == null ? "" : StringEscapeUtils.escapeHtml4(strMereId());
	}

	public void htmMereId(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "MereId\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "MereId() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setMereId\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageMereId()), "</span>");
				r.s("			<input");
							r.s(" name=\"mereId\"");
							r.s(" value=\"", htmMereId(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmMereId());
			}
			r.l("</div>");
		}
	}

	/////////////
	// pageUrl //
	/////////////

	/**	L'entité « pageUrl »
	 *	 is defined as null before being initialized. 
	 */
	protected String pageUrl;
	@JsonIgnore
	public Couverture<String> pageUrlCouverture = new Couverture<String>().p(this).c(String.class).var("pageUrl").o(pageUrl);

	/**	<br/>L'entité « pageUrl »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:pageUrl">Trouver l'entité pageUrl dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _pageUrl(Couverture<String> c);

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
		this.pageUrlCouverture.dejaInitialise = true;
	}
	protected MereScolaire pageUrlInit() {
		if(!pageUrlCouverture.dejaInitialise) {
			_pageUrl(pageUrlCouverture);
			if(pageUrl == null)
				setPageUrl(pageUrlCouverture.o);
		}
		pageUrlCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrPageUrl() {
		return pageUrl;
	}

	public String strPageUrl() {
		return pageUrl == null ? "" : pageUrl;
	}

	public String jsonPageUrl() {
		return pageUrl == null ? "" : pageUrl;
	}

	public String nomAffichagePageUrl() {
		return null;
	}

	public String htmTooltipPageUrl() {
		return null;
	}

	public String htmPageUrl() {
		return pageUrl == null ? "" : StringEscapeUtils.escapeHtml4(strPageUrl());
	}

	public void htmPageUrl(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "PageUrl\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "PageUrl() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setPageUrl\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichagePageUrl()), "</span>");
				r.s("			<input");
							r.s(" name=\"pageUrl\"");
							r.s(" value=\"", htmPageUrl(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmPageUrl());
			}
			r.l("</div>");
		}
	}

	//////////////////
	// objetSuggere //
	//////////////////

	/**	L'entité « objetSuggere »
	 *	 is defined as null before being initialized. 
	 */
	protected String objetSuggere;
	@JsonIgnore
	public Couverture<String> objetSuggereCouverture = new Couverture<String>().p(this).c(String.class).var("objetSuggere").o(objetSuggere);

	/**	<br/>L'entité « objetSuggere »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.mere.MereScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:objetSuggere">Trouver l'entité objetSuggere dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _objetSuggere(Couverture<String> c);

	public String getObjetSuggere() {
		return objetSuggere;
	}

	public void setObjetSuggere(String objetSuggere) {
		this.objetSuggere = objetSuggere;
		this.objetSuggereCouverture.dejaInitialise = true;
	}
	protected MereScolaire objetSuggereInit() {
		if(!objetSuggereCouverture.dejaInitialise) {
			_objetSuggere(objetSuggereCouverture);
			if(objetSuggere == null)
				setObjetSuggere(objetSuggereCouverture.o);
		}
		objetSuggereCouverture.dejaInitialise(true);
		return (MereScolaire)this;
	}

	public String solrObjetSuggere() {
		return objetSuggere;
	}

	public String strObjetSuggere() {
		return objetSuggere == null ? "" : objetSuggere;
	}

	public String jsonObjetSuggere() {
		return objetSuggere == null ? "" : objetSuggere;
	}

	public String nomAffichageObjetSuggere() {
		return null;
	}

	public String htmTooltipObjetSuggere() {
		return null;
	}

	public String htmObjetSuggere() {
		return objetSuggere == null ? "" : StringEscapeUtils.escapeHtml4(strObjetSuggere());
	}

	public void htmObjetSuggere(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchMereScolaire", strPk(), "ObjetSuggere\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchMereScolaire", strPk(), "ObjetSuggere() {");
				r.l("			$.ajax({");
				r.l("				url: '?fq=pk:", strPk(), "',");
				r.l("				dataType: 'json',");
				r.l("				type: 'patch',");
				r.l("				contentType: 'application/json',");
				r.l("				processData: false,");
				r.l("				success: function( data, textStatus, jQxhr ) {");
				r.l("					");
				r.l("				},");
				r.l("				error: function( jqXhr, textStatus, errorThrown ) {");
				r.l("					");
				r.l("				},");
				r.l("				data: {\"setObjetSuggere\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageObjetSuggere()), "</span>");
				r.s("			<input");
							r.s(" name=\"objetSuggere\"");
							r.s(" value=\"", htmObjetSuggere(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmObjetSuggere());
			}
			r.l("</div>");
		}
	}

	//////////////
	// initLoin //
	//////////////

	protected boolean dejaInitialiseMereScolaire = false;

	public MereScolaire initLoinMereScolaire(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseMereScolaire) {
			dejaInitialiseMereScolaire = true;
			initLoinMereScolaire();
		}
		return (MereScolaire)this;
	}

	public void initLoinMereScolaire() {
		super.initLoinCluster(requeteSite_);
		initMereScolaire();
	}

	public void initMereScolaire() {
		mereCleInit();
		inscriptionClesInit();
		familleTriInit();
		mereTriInit();
		inscriptionRechercheInit();
		inscriptionsInit();
		ecoleClesInit();
		anneeClesInit();
		saisonClesInit();
		sessionClesInit();
		ageClesInit();
		personnePrenomInit();
		familleNomInit();
		personneNomCompletInit();
		personneNomCompletPrefereInit();
		personneNomFormelInit();
		personneOccupationInit();
		personneNumeroTelephoneInit();
		personneMailInit();
		personneRelationInit();
		personneSmsInit();
		personneRecevoirMailInit();
		personneContactUrgenceInit();
		personneChercherInit();
		mereNomCompletInit();
		mereIdInit();
		pageUrlInit();
		objetSuggereInit();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinMereScolaire(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteMereScolaire(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteCluster(requeteSite_);
		if(inscriptionRecherche != null)
			inscriptionRecherche.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteMereScolaire(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirMereScolaire(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirMereScolaire(String var) {
		MereScolaire oMereScolaire = (MereScolaire)this;
		switch(var) {
			case "mereCle":
				return oMereScolaire.mereCle;
			case "inscriptionCles":
				return oMereScolaire.inscriptionCles;
			case "familleTri":
				return oMereScolaire.familleTri;
			case "mereTri":
				return oMereScolaire.mereTri;
			case "inscriptionRecherche":
				return oMereScolaire.inscriptionRecherche;
			case "inscriptions":
				return oMereScolaire.inscriptions;
			case "ecoleCles":
				return oMereScolaire.ecoleCles;
			case "anneeCles":
				return oMereScolaire.anneeCles;
			case "saisonCles":
				return oMereScolaire.saisonCles;
			case "sessionCles":
				return oMereScolaire.sessionCles;
			case "ageCles":
				return oMereScolaire.ageCles;
			case "personnePrenom":
				return oMereScolaire.personnePrenom;
			case "familleNom":
				return oMereScolaire.familleNom;
			case "personneNomComplet":
				return oMereScolaire.personneNomComplet;
			case "personneNomCompletPrefere":
				return oMereScolaire.personneNomCompletPrefere;
			case "personneNomFormel":
				return oMereScolaire.personneNomFormel;
			case "personneOccupation":
				return oMereScolaire.personneOccupation;
			case "personneNumeroTelephone":
				return oMereScolaire.personneNumeroTelephone;
			case "personneMail":
				return oMereScolaire.personneMail;
			case "personneRelation":
				return oMereScolaire.personneRelation;
			case "personneSms":
				return oMereScolaire.personneSms;
			case "personneRecevoirMail":
				return oMereScolaire.personneRecevoirMail;
			case "personneContactUrgence":
				return oMereScolaire.personneContactUrgence;
			case "personneChercher":
				return oMereScolaire.personneChercher;
			case "mereNomComplet":
				return oMereScolaire.mereNomComplet;
			case "mereId":
				return oMereScolaire.mereId;
			case "pageUrl":
				return oMereScolaire.pageUrl;
			case "objetSuggere":
				return oMereScolaire.objetSuggere;
			default:
				return super.obtenirCluster(var);
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
				o = attribuerMereScolaire(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerMereScolaire(String var, Object val) {
		MereScolaire oMereScolaire = (MereScolaire)this;
		switch(var) {
			default:
				return super.attribuerCluster(var, val);
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
					o = definirMereScolaire(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirMereScolaire(String var, String val) {
		switch(var) {
			case "personnePrenom":
				setPersonnePrenom(val);
				sauvegardesMereScolaire.add(var);
				return val;
			case "familleNom":
				setFamilleNom(val);
				sauvegardesMereScolaire.add(var);
				return val;
			case "personneNomComplet":
				setPersonneNomComplet(val);
				sauvegardesMereScolaire.add(var);
				return val;
			case "personneNomCompletPrefere":
				setPersonneNomCompletPrefere(val);
				sauvegardesMereScolaire.add(var);
				return val;
			case "personneNomFormel":
				setPersonneNomFormel(val);
				sauvegardesMereScolaire.add(var);
				return val;
			default:
				return super.definirCluster(var, val);
		}
	}

	/////////////////
	// sauvegardes //
	/////////////////

	protected List<String> sauvegardesMereScolaire = new ArrayList<String>();

	/////////////
	// peupler //
	/////////////

	@Override public void peuplerPourClasse(SolrDocument solrDocument) {
		peuplerMereScolaire(solrDocument);
	}
	public void peuplerMereScolaire(SolrDocument solrDocument) {
		MereScolaire oMereScolaire = (MereScolaire)this;
		sauvegardesMereScolaire = (List<String>)solrDocument.get("sauvegardesMereScolaire_stored_strings");
		if(sauvegardesMereScolaire != null) {

			if(sauvegardesMereScolaire.contains("mereCle")) {
				Long mereCle = (Long)solrDocument.get("mereCle_stored_long");
				if(mereCle != null)
					oMereScolaire.setMereCle(mereCle);
			}

			if(sauvegardesMereScolaire.contains("inscriptionCles")) {
				List<Long> inscriptionCles = (List<Long>)solrDocument.get("inscriptionCles_stored_longs");
				if(inscriptionCles != null)
					oMereScolaire.inscriptionCles.addAll(inscriptionCles);
			}

			if(sauvegardesMereScolaire.contains("familleTri")) {
				Integer familleTri = (Integer)solrDocument.get("familleTri_stored_int");
				if(familleTri != null)
					oMereScolaire.setFamilleTri(familleTri);
			}

			if(sauvegardesMereScolaire.contains("mereTri")) {
				Integer mereTri = (Integer)solrDocument.get("mereTri_stored_int");
				if(mereTri != null)
					oMereScolaire.setMereTri(mereTri);
			}

			if(sauvegardesMereScolaire.contains("ecoleCles")) {
				List<Long> ecoleCles = (List<Long>)solrDocument.get("ecoleCles_stored_longs");
				if(ecoleCles != null)
					oMereScolaire.ecoleCles.addAll(ecoleCles);
			}

			if(sauvegardesMereScolaire.contains("anneeCles")) {
				List<Long> anneeCles = (List<Long>)solrDocument.get("anneeCles_stored_longs");
				if(anneeCles != null)
					oMereScolaire.anneeCles.addAll(anneeCles);
			}

			if(sauvegardesMereScolaire.contains("saisonCles")) {
				List<Long> saisonCles = (List<Long>)solrDocument.get("saisonCles_stored_longs");
				if(saisonCles != null)
					oMereScolaire.saisonCles.addAll(saisonCles);
			}

			if(sauvegardesMereScolaire.contains("sessionCles")) {
				List<Long> sessionCles = (List<Long>)solrDocument.get("sessionCles_stored_longs");
				if(sessionCles != null)
					oMereScolaire.sessionCles.addAll(sessionCles);
			}

			if(sauvegardesMereScolaire.contains("ageCles")) {
				List<Long> ageCles = (List<Long>)solrDocument.get("ageCles_stored_longs");
				if(ageCles != null)
					oMereScolaire.ageCles.addAll(ageCles);
			}

			if(sauvegardesMereScolaire.contains("personnePrenom")) {
				String personnePrenom = (String)solrDocument.get("personnePrenom_stored_string");
				if(personnePrenom != null)
					oMereScolaire.setPersonnePrenom(personnePrenom);
			}

			if(sauvegardesMereScolaire.contains("familleNom")) {
				String familleNom = (String)solrDocument.get("familleNom_stored_string");
				if(familleNom != null)
					oMereScolaire.setFamilleNom(familleNom);
			}

			if(sauvegardesMereScolaire.contains("personneNomComplet")) {
				String personneNomComplet = (String)solrDocument.get("personneNomComplet_stored_string");
				if(personneNomComplet != null)
					oMereScolaire.setPersonneNomComplet(personneNomComplet);
			}

			if(sauvegardesMereScolaire.contains("personneNomCompletPrefere")) {
				String personneNomCompletPrefere = (String)solrDocument.get("personneNomCompletPrefere_stored_string");
				if(personneNomCompletPrefere != null)
					oMereScolaire.setPersonneNomCompletPrefere(personneNomCompletPrefere);
			}

			if(sauvegardesMereScolaire.contains("personneNomFormel")) {
				String personneNomFormel = (String)solrDocument.get("personneNomFormel_stored_string");
				if(personneNomFormel != null)
					oMereScolaire.setPersonneNomFormel(personneNomFormel);
			}

			if(sauvegardesMereScolaire.contains("personneOccupation")) {
				String personneOccupation = (String)solrDocument.get("personneOccupation_stored_string");
				if(personneOccupation != null)
					oMereScolaire.setPersonneOccupation(personneOccupation);
			}

			if(sauvegardesMereScolaire.contains("personneNumeroTelephone")) {
				String personneNumeroTelephone = (String)solrDocument.get("personneNumeroTelephone_stored_string");
				if(personneNumeroTelephone != null)
					oMereScolaire.setPersonneNumeroTelephone(personneNumeroTelephone);
			}

			if(sauvegardesMereScolaire.contains("personneMail")) {
				String personneMail = (String)solrDocument.get("personneMail_stored_string");
				if(personneMail != null)
					oMereScolaire.setPersonneMail(personneMail);
			}

			if(sauvegardesMereScolaire.contains("personneRelation")) {
				String personneRelation = (String)solrDocument.get("personneRelation_stored_string");
				if(personneRelation != null)
					oMereScolaire.setPersonneRelation(personneRelation);
			}

			if(sauvegardesMereScolaire.contains("personneSms")) {
				Boolean personneSms = (Boolean)solrDocument.get("personneSms_stored_boolean");
				if(personneSms != null)
					oMereScolaire.setPersonneSms(personneSms);
			}

			if(sauvegardesMereScolaire.contains("personneRecevoirMail")) {
				Boolean personneRecevoirMail = (Boolean)solrDocument.get("personneRecevoirMail_stored_boolean");
				if(personneRecevoirMail != null)
					oMereScolaire.setPersonneRecevoirMail(personneRecevoirMail);
			}

			if(sauvegardesMereScolaire.contains("personneContactUrgence")) {
				Boolean personneContactUrgence = (Boolean)solrDocument.get("personneContactUrgence_stored_boolean");
				if(personneContactUrgence != null)
					oMereScolaire.setPersonneContactUrgence(personneContactUrgence);
			}

			if(sauvegardesMereScolaire.contains("personneChercher")) {
				Boolean personneChercher = (Boolean)solrDocument.get("personneChercher_stored_boolean");
				if(personneChercher != null)
					oMereScolaire.setPersonneChercher(personneChercher);
			}

			if(sauvegardesMereScolaire.contains("mereNomComplet")) {
				String mereNomComplet = (String)solrDocument.get("mereNomComplet_stored_string");
				if(mereNomComplet != null)
					oMereScolaire.setMereNomComplet(mereNomComplet);
			}

			if(sauvegardesMereScolaire.contains("mereId")) {
				String mereId = (String)solrDocument.get("mereId_stored_string");
				if(mereId != null)
					oMereScolaire.setMereId(mereId);
			}

			if(sauvegardesMereScolaire.contains("pageUrl")) {
				String pageUrl = (String)solrDocument.get("pageUrl_stored_string");
				if(pageUrl != null)
					oMereScolaire.setPageUrl(pageUrl);
			}

			if(sauvegardesMereScolaire.contains("objetSuggere")) {
				String objetSuggere = (String)solrDocument.get("objetSuggere_suggested");
				oMereScolaire.setObjetSuggere(objetSuggere);
			}
		}

		super.peuplerCluster(solrDocument);
	}

	/////////////
	// indexer //
	/////////////

	public static void indexer() {
		try {
			RequeteSiteFrFR requeteSite = new RequeteSiteFrFR();
			requeteSite.initLoinRequeteSiteFrFR();
			SiteContexteFrFR siteContexte = new SiteContexteFrFR();
			siteContexte.getConfigSite().setConfigChemin("/usr/local/src/computate-scolaire/config/computate-scolaire.config");
			siteContexte.initLoinSiteContexteFrFR();
			requeteSite.setSiteContexte_(siteContexte);
			requeteSite.setConfigSite_(siteContexte.getConfigSite());
			SolrQuery rechercheSolr = new SolrQuery();
			rechercheSolr.setQuery("*:*");
			rechercheSolr.setRows(1);
			rechercheSolr.addFilterQuery("id:" + ClientUtils.escapeQueryChars("org.computate.scolaire.frFR.mere.MereScolaire"));
			QueryResponse reponseRecherche = requeteSite.getSiteContexte_().getClientSolr().query(rechercheSolr);
			if(reponseRecherche.getResults().size() > 0)
				requeteSite.setDocumentSolr(reponseRecherche.getResults().get(0));
			MereScolaire o = new MereScolaire();
			o.requeteSiteMereScolaire(requeteSite);
			o.initLoinMereScolaire(requeteSite);
			o.indexerMereScolaire();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexerPourClasse() {
		indexerMereScolaire();
	}

	@Override public void indexerPourClasse(SolrInputDocument document) {
		indexerMereScolaire(document);
	}

	public void indexerMereScolaire(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexerMereScolaire(document);
			clientSolr.add(document);
			clientSolr.commit();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexerMereScolaire() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexerMereScolaire(document);
			SolrClient clientSolr = requeteSite_.getSiteContexte_().getClientSolr();
			clientSolr.add(document);
			clientSolr.commit();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexerMereScolaire(SolrInputDocument document) {
		if(sauvegardesMereScolaire != null)
			document.addField("sauvegardesMereScolaire_stored_strings", sauvegardesMereScolaire);

		if(mereCle != null) {
			document.addField("mereCle_indexed_long", mereCle);
			document.addField("mereCle_stored_long", mereCle);
		}
		if(inscriptionCles != null) {
			for(java.lang.Long o : inscriptionCles) {
				document.addField("inscriptionCles_indexed_longs", o);
			}
			for(java.lang.Long o : inscriptionCles) {
				document.addField("inscriptionCles_stored_longs", o);
			}
		}
		if(familleTri != null) {
			document.addField("familleTri_indexed_int", familleTri);
			document.addField("familleTri_stored_int", familleTri);
		}
		if(mereTri != null) {
			document.addField("mereTri_indexed_int", mereTri);
			document.addField("mereTri_stored_int", mereTri);
		}
		if(ecoleCles != null) {
			for(java.lang.Long o : ecoleCles) {
				document.addField("ecoleCles_indexed_longs", o);
			}
			for(java.lang.Long o : ecoleCles) {
				document.addField("ecoleCles_stored_longs", o);
			}
		}
		if(anneeCles != null) {
			for(java.lang.Long o : anneeCles) {
				document.addField("anneeCles_indexed_longs", o);
			}
			for(java.lang.Long o : anneeCles) {
				document.addField("anneeCles_stored_longs", o);
			}
		}
		if(saisonCles != null) {
			for(java.lang.Long o : saisonCles) {
				document.addField("saisonCles_indexed_longs", o);
			}
			for(java.lang.Long o : saisonCles) {
				document.addField("saisonCles_stored_longs", o);
			}
		}
		if(sessionCles != null) {
			for(java.lang.Long o : sessionCles) {
				document.addField("sessionCles_indexed_longs", o);
			}
			for(java.lang.Long o : sessionCles) {
				document.addField("sessionCles_stored_longs", o);
			}
		}
		if(ageCles != null) {
			for(java.lang.Long o : ageCles) {
				document.addField("ageCles_indexed_longs", o);
			}
			for(java.lang.Long o : ageCles) {
				document.addField("ageCles_stored_longs", o);
			}
		}
		if(personnePrenom != null) {
			document.addField("personnePrenom_indexed_string", personnePrenom);
			document.addField("personnePrenom_stored_string", personnePrenom);
		}
		if(familleNom != null) {
			document.addField("familleNom_indexed_string", familleNom);
			document.addField("familleNom_stored_string", familleNom);
		}
		if(personneNomComplet != null) {
			document.addField("personneNomComplet_indexed_string", personneNomComplet);
			document.addField("personneNomComplet_stored_string", personneNomComplet);
		}
		if(personneNomCompletPrefere != null) {
			document.addField("personneNomCompletPrefere_indexed_string", personneNomCompletPrefere);
			document.addField("personneNomCompletPrefere_stored_string", personneNomCompletPrefere);
		}
		if(personneNomFormel != null) {
			document.addField("personneNomFormel_indexed_string", personneNomFormel);
			document.addField("personneNomFormel_stored_string", personneNomFormel);
		}
		if(personneOccupation != null) {
			document.addField("personneOccupation_indexed_string", personneOccupation);
			document.addField("personneOccupation_stored_string", personneOccupation);
		}
		if(personneNumeroTelephone != null) {
			document.addField("personneNumeroTelephone_indexed_string", personneNumeroTelephone);
			document.addField("personneNumeroTelephone_stored_string", personneNumeroTelephone);
		}
		if(personneMail != null) {
			document.addField("personneMail_indexed_string", personneMail);
			document.addField("personneMail_stored_string", personneMail);
		}
		if(personneRelation != null) {
			document.addField("personneRelation_indexed_string", personneRelation);
			document.addField("personneRelation_stored_string", personneRelation);
		}
		if(personneSms != null) {
			document.addField("personneSms_indexed_boolean", personneSms);
			document.addField("personneSms_stored_boolean", personneSms);
		}
		if(personneRecevoirMail != null) {
			document.addField("personneRecevoirMail_indexed_boolean", personneRecevoirMail);
			document.addField("personneRecevoirMail_stored_boolean", personneRecevoirMail);
		}
		if(personneContactUrgence != null) {
			document.addField("personneContactUrgence_indexed_boolean", personneContactUrgence);
			document.addField("personneContactUrgence_stored_boolean", personneContactUrgence);
		}
		if(personneChercher != null) {
			document.addField("personneChercher_indexed_boolean", personneChercher);
			document.addField("personneChercher_stored_boolean", personneChercher);
		}
		if(mereNomComplet != null) {
			document.addField("mereNomComplet_indexed_string", mereNomComplet);
			document.addField("mereNomComplet_stored_string", mereNomComplet);
		}
		if(mereId != null) {
			document.addField("mereId_indexed_string", mereId);
			document.addField("mereId_stored_string", mereId);
		}
		if(pageUrl != null) {
			document.addField("pageUrl_indexed_string", pageUrl);
			document.addField("pageUrl_stored_string", pageUrl);
		}
		if(objetSuggere != null) {
			document.addField("objetSuggere_suggested", objetSuggere);
			document.addField("objetSuggere_indexed_string", objetSuggere);
		}
		super.indexerCluster(document);

	}

	public void desindexerMereScolaire() {
		try {
		RequeteSiteFrFR requeteSite = new RequeteSiteFrFR();
			requeteSite.initLoinRequeteSiteFrFR();
			SiteContexteFrFR siteContexte = new SiteContexteFrFR();
			siteContexte.initLoinSiteContexteFrFR();
			requeteSite.setSiteContexte_(siteContexte);
			requeteSite.setConfigSite_(siteContexte.getConfigSite());
			initLoinMereScolaire(requeteSite);
			SolrClient clientSolr = siteContexte.getClientSolr();
			clientSolr.deleteById(id.toString());
			clientSolr.commit();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	/////////////
	// stocker //
	/////////////

	@Override public void stockerPourClasse(SolrDocument solrDocument) {
		stockerMereScolaire(solrDocument);
	}
	public void stockerMereScolaire(SolrDocument solrDocument) {
		MereScolaire oMereScolaire = (MereScolaire)this;

		Long mereCle = (Long)solrDocument.get("mereCle_stored_long");
		if(mereCle != null)
			oMereScolaire.setMereCle(mereCle);

		List<Long> inscriptionCles = (List<Long>)solrDocument.get("inscriptionCles_stored_longs");
		if(inscriptionCles != null)
			oMereScolaire.inscriptionCles.addAll(inscriptionCles);

		Integer familleTri = (Integer)solrDocument.get("familleTri_stored_int");
		if(familleTri != null)
			oMereScolaire.setFamilleTri(familleTri);

		Integer mereTri = (Integer)solrDocument.get("mereTri_stored_int");
		if(mereTri != null)
			oMereScolaire.setMereTri(mereTri);

		List<Long> ecoleCles = (List<Long>)solrDocument.get("ecoleCles_stored_longs");
		if(ecoleCles != null)
			oMereScolaire.ecoleCles.addAll(ecoleCles);

		List<Long> anneeCles = (List<Long>)solrDocument.get("anneeCles_stored_longs");
		if(anneeCles != null)
			oMereScolaire.anneeCles.addAll(anneeCles);

		List<Long> saisonCles = (List<Long>)solrDocument.get("saisonCles_stored_longs");
		if(saisonCles != null)
			oMereScolaire.saisonCles.addAll(saisonCles);

		List<Long> sessionCles = (List<Long>)solrDocument.get("sessionCles_stored_longs");
		if(sessionCles != null)
			oMereScolaire.sessionCles.addAll(sessionCles);

		List<Long> ageCles = (List<Long>)solrDocument.get("ageCles_stored_longs");
		if(ageCles != null)
			oMereScolaire.ageCles.addAll(ageCles);

		String personnePrenom = (String)solrDocument.get("personnePrenom_stored_string");
		if(personnePrenom != null)
			oMereScolaire.setPersonnePrenom(personnePrenom);

		String familleNom = (String)solrDocument.get("familleNom_stored_string");
		if(familleNom != null)
			oMereScolaire.setFamilleNom(familleNom);

		String personneNomComplet = (String)solrDocument.get("personneNomComplet_stored_string");
		if(personneNomComplet != null)
			oMereScolaire.setPersonneNomComplet(personneNomComplet);

		String personneNomCompletPrefere = (String)solrDocument.get("personneNomCompletPrefere_stored_string");
		if(personneNomCompletPrefere != null)
			oMereScolaire.setPersonneNomCompletPrefere(personneNomCompletPrefere);

		String personneNomFormel = (String)solrDocument.get("personneNomFormel_stored_string");
		if(personneNomFormel != null)
			oMereScolaire.setPersonneNomFormel(personneNomFormel);

		String personneOccupation = (String)solrDocument.get("personneOccupation_stored_string");
		if(personneOccupation != null)
			oMereScolaire.setPersonneOccupation(personneOccupation);

		String personneNumeroTelephone = (String)solrDocument.get("personneNumeroTelephone_stored_string");
		if(personneNumeroTelephone != null)
			oMereScolaire.setPersonneNumeroTelephone(personneNumeroTelephone);

		String personneMail = (String)solrDocument.get("personneMail_stored_string");
		if(personneMail != null)
			oMereScolaire.setPersonneMail(personneMail);

		String personneRelation = (String)solrDocument.get("personneRelation_stored_string");
		if(personneRelation != null)
			oMereScolaire.setPersonneRelation(personneRelation);

		Boolean personneSms = (Boolean)solrDocument.get("personneSms_stored_boolean");
		if(personneSms != null)
			oMereScolaire.setPersonneSms(personneSms);

		Boolean personneRecevoirMail = (Boolean)solrDocument.get("personneRecevoirMail_stored_boolean");
		if(personneRecevoirMail != null)
			oMereScolaire.setPersonneRecevoirMail(personneRecevoirMail);

		Boolean personneContactUrgence = (Boolean)solrDocument.get("personneContactUrgence_stored_boolean");
		if(personneContactUrgence != null)
			oMereScolaire.setPersonneContactUrgence(personneContactUrgence);

		Boolean personneChercher = (Boolean)solrDocument.get("personneChercher_stored_boolean");
		if(personneChercher != null)
			oMereScolaire.setPersonneChercher(personneChercher);

		String mereNomComplet = (String)solrDocument.get("mereNomComplet_stored_string");
		if(mereNomComplet != null)
			oMereScolaire.setMereNomComplet(mereNomComplet);

		String mereId = (String)solrDocument.get("mereId_stored_string");
		if(mereId != null)
			oMereScolaire.setMereId(mereId);

		String pageUrl = (String)solrDocument.get("pageUrl_stored_string");
		if(pageUrl != null)
			oMereScolaire.setPageUrl(pageUrl);

		String objetSuggere = (String)solrDocument.get("objetSuggere_suggested");
		oMereScolaire.setObjetSuggere(objetSuggere);

		super.stockerCluster(solrDocument);
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), personnePrenom, familleNom, personneNomComplet, personneNomCompletPrefere, personneNomFormel);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof MereScolaire))
			return false;
		MereScolaire that = (MereScolaire)o;
		return super.equals(o)
				&& Objects.equals( personnePrenom, that.personnePrenom )
				&& Objects.equals( familleNom, that.familleNom )
				&& Objects.equals( personneNomComplet, that.personneNomComplet )
				&& Objects.equals( personneNomCompletPrefere, that.personneNomCompletPrefere )
				&& Objects.equals( personneNomFormel, that.personneNomFormel );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("MereScolaire { ");
		sb.append( "personnePrenom: \"" ).append(personnePrenom).append( "\"" );
		sb.append( ", familleNom: \"" ).append(familleNom).append( "\"" );
		sb.append( ", personneNomComplet: \"" ).append(personneNomComplet).append( "\"" );
		sb.append( ", personneNomCompletPrefere: \"" ).append(personneNomCompletPrefere).append( "\"" );
		sb.append( ", personneNomFormel: \"" ).append(personneNomFormel).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}