package org.computate.scolaire.frFR.gardien;

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
 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true">Trouver la classe objectSuggest dans Solr</a>
 * <br/>
 **/
public abstract class GardienScolaireGen<DEV> extends Cluster {
	private static final Logger LOGGER = LoggerFactory.getLogger(GardienScolaire.class);

	public static final String GardienScolaire_UnNom = "un gardien";
	public static final String GardienScolaire_Ce = "ce ";
	public static final String GardienScolaire_CeNom = "ce gardien";
	public static final String GardienScolaire_Un = "un ";
	public static final String GardienScolaire_LeNom = "le gardien";
	public static final String GardienScolaire_NomSingulier = "gardien";
	public static final String GardienScolaire_NomPluriel = "gardiens";
	public static final String GardienScolaire_NomActuel = "gardien actuel";
	public static final String GardienScolaire_Tous = "all ";
	public static final String GardienScolaire_TousNom = "tous les gardiens";
	public static final String GardienScolaire_RechercherTousNomPar = "rechercher gardiens par ";
	public static final String GardienScolaire_RechercherTousNom = "rechercher gardiens";
	public static final String GardienScolaire_LesNoms = "les gardiens";
	public static final String GardienScolaire_AucunNomTrouve = "aucun gardien trouvé";
	public static final String GardienScolaire_NomVar = "gardien";
	public static final String GardienScolaire_DeNom = "de gardien";
	public static final String GardienScolaire_NomAdjectifSingulier = "gardien";
	public static final String GardienScolaire_NomAdjectifPluriel = "gardiens";
	public static final String GardienScolaire_Couleur = "yellow";
	public static final String GardienScolaire_IconeGroupe = "regular";
	public static final String GardienScolaire_IconeNom = "chevron-right";

	////////////////
	// gardienCle //
	////////////////

	/**	L'entité « gardienCle »
	 *	 is defined as null before being initialized. 
	 */
	protected Long gardienCle;
	@JsonIgnore
	public Couverture<Long> gardienCleCouverture = new Couverture<Long>().p(this).c(Long.class).var("gardienCle").o(gardienCle);

	/**	<br/>L'entité « gardienCle »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:gardienCle">Trouver l'entité gardienCle dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _gardienCle(Couverture<Long> c);

	public Long getGardienCle() {
		return gardienCle;
	}

	public void setGardienCle(Long gardienCle) {
		this.gardienCle = gardienCle;
		this.gardienCleCouverture.dejaInitialise = true;
	}
	public GardienScolaire setGardienCle(String o) {
		if(NumberUtils.isParsable(o))
			this.gardienCle = Long.parseLong(o);
		this.gardienCleCouverture.dejaInitialise = true;
		return (GardienScolaire)this;
	}
	protected GardienScolaire gardienCleInit() {
		if(!gardienCleCouverture.dejaInitialise) {
			_gardienCle(gardienCleCouverture);
			if(gardienCle == null)
				setGardienCle(gardienCleCouverture.o);
		}
		gardienCleCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
	}

	public Long solrGardienCle() {
		return gardienCle;
	}

	public String strGardienCle() {
		return gardienCle == null ? "" : gardienCle.toString();
	}

	public String jsonGardienCle() {
		return gardienCle == null ? "" : gardienCle.toString();
	}

	public String nomAffichageGardienCle() {
		return "clé";
	}

	public String htmTooltipGardienCle() {
		return null;
	}

	public String htmGardienCle() {
		return gardienCle == null ? "" : StringEscapeUtils.escapeHtml4(strGardienCle());
	}

	public void htmGardienCle(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchGardienScolaire", strPk(), "GardienCle\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "GardienCle() {");
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
				r.l("				data: {\"setGardienCle\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageGardienCle()), "</span>");
				r.s("			<input");
							r.s(" name=\"gardienCle\"");
							r.s(" value=\"", htmGardienCle(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmGardienCle());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionCles">Trouver l'entité inscriptionCles dans Solr</a>
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
	public GardienScolaire addInscriptionCles(Long...objets) {
		for(Long o : objets) {
			addInscriptionCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addInscriptionCles(Long o) {
		if(o != null && !inscriptionCles.contains(o))
			this.inscriptionCles.add(o);
		return (GardienScolaire)this;
	}
	public GardienScolaire setInscriptionCles(JsonArray objets) {
		inscriptionCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addInscriptionCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addInscriptionCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addInscriptionCles(p);
		}
		return (GardienScolaire)this;
	}
	protected GardienScolaire inscriptionClesInit() {
		if(!inscriptionClesCouverture.dejaInitialise) {
			_inscriptionCles(inscriptionCles);
		}
		inscriptionClesCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "InscriptionCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "InscriptionCles() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:familleTri">Trouver l'entité familleTri dans Solr</a>
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
	public GardienScolaire setFamilleTri(String o) {
		if(NumberUtils.isParsable(o))
			this.familleTri = Integer.parseInt(o);
		this.familleTriCouverture.dejaInitialise = true;
		return (GardienScolaire)this;
	}
	protected GardienScolaire familleTriInit() {
		if(!familleTriCouverture.dejaInitialise) {
			_familleTri(familleTriCouverture);
			if(familleTri == null)
				setFamilleTri(familleTriCouverture.o);
		}
		familleTriCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "FamilleTri\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "FamilleTri() {");
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

	////////////////
	// gardienTri //
	////////////////

	/**	L'entité « gardienTri »
	 *	 is defined as null before being initialized. 
	 */
	protected Integer gardienTri;
	@JsonIgnore
	public Couverture<Integer> gardienTriCouverture = new Couverture<Integer>().p(this).c(Integer.class).var("gardienTri").o(gardienTri);

	/**	<br/>L'entité « gardienTri »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:gardienTri">Trouver l'entité gardienTri dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _gardienTri(Couverture<Integer> c);

	public Integer getGardienTri() {
		return gardienTri;
	}

	public void setGardienTri(Integer gardienTri) {
		this.gardienTri = gardienTri;
		this.gardienTriCouverture.dejaInitialise = true;
	}
	public GardienScolaire setGardienTri(String o) {
		if(NumberUtils.isParsable(o))
			this.gardienTri = Integer.parseInt(o);
		this.gardienTriCouverture.dejaInitialise = true;
		return (GardienScolaire)this;
	}
	protected GardienScolaire gardienTriInit() {
		if(!gardienTriCouverture.dejaInitialise) {
			_gardienTri(gardienTriCouverture);
			if(gardienTri == null)
				setGardienTri(gardienTriCouverture.o);
		}
		gardienTriCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
	}

	public Integer solrGardienTri() {
		return gardienTri;
	}

	public String strGardienTri() {
		return gardienTri == null ? "" : gardienTri.toString();
	}

	public String jsonGardienTri() {
		return gardienTri == null ? "" : gardienTri.toString();
	}

	public String nomAffichageGardienTri() {
		return null;
	}

	public String htmTooltipGardienTri() {
		return null;
	}

	public String htmGardienTri() {
		return gardienTri == null ? "" : StringEscapeUtils.escapeHtml4(strGardienTri());
	}

	public void htmGardienTri(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchGardienScolaire", strPk(), "GardienTri\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "GardienTri() {");
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
				r.l("				data: {\"setGardienTri\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageGardienTri()), "</span>");
				r.s("			<input");
							r.s(" name=\"gardienTri\"");
							r.s(" value=\"", htmGardienTri(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmGardienTri());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptionRecherche">Trouver l'entité inscriptionRecherche dans Solr</a>
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
	protected GardienScolaire inscriptionRechercheInit() {
		if(!inscriptionRechercheCouverture.dejaInitialise) {
			_inscriptionRecherche(inscriptionRecherche);
		}
		inscriptionRecherche.initLoinPourClasse(requeteSite_);
		inscriptionRechercheCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:inscriptions">Trouver l'entité inscriptions dans Solr</a>
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
	public GardienScolaire addInscriptions(InscriptionScolaire...objets) {
		for(InscriptionScolaire o : objets) {
			addInscriptions(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addInscriptions(InscriptionScolaire o) {
		if(o != null && !inscriptions.contains(o))
			this.inscriptions.add(o);
		return (GardienScolaire)this;
	}
	protected GardienScolaire inscriptionsInit() {
		if(!inscriptionsCouverture.dejaInitialise) {
			_inscriptions(inscriptions);
		}
		inscriptionsCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:ecoleCles">Trouver l'entité ecoleCles dans Solr</a>
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
	public GardienScolaire addEcoleCles(Long...objets) {
		for(Long o : objets) {
			addEcoleCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addEcoleCles(Long o) {
		if(o != null && !ecoleCles.contains(o))
			this.ecoleCles.add(o);
		return (GardienScolaire)this;
	}
	public GardienScolaire setEcoleCles(JsonArray objets) {
		ecoleCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addEcoleCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addEcoleCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addEcoleCles(p);
		}
		return (GardienScolaire)this;
	}
	protected GardienScolaire ecoleClesInit() {
		if(!ecoleClesCouverture.dejaInitialise) {
			_ecoleCles(ecoleCles);
		}
		ecoleClesCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "EcoleCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "EcoleCles() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:anneeCles">Trouver l'entité anneeCles dans Solr</a>
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
	public GardienScolaire addAnneeCles(Long...objets) {
		for(Long o : objets) {
			addAnneeCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addAnneeCles(Long o) {
		if(o != null && !anneeCles.contains(o))
			this.anneeCles.add(o);
		return (GardienScolaire)this;
	}
	public GardienScolaire setAnneeCles(JsonArray objets) {
		anneeCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addAnneeCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addAnneeCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addAnneeCles(p);
		}
		return (GardienScolaire)this;
	}
	protected GardienScolaire anneeClesInit() {
		if(!anneeClesCouverture.dejaInitialise) {
			_anneeCles(anneeCles);
		}
		anneeClesCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "AnneeCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "AnneeCles() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:saisonCles">Trouver l'entité saisonCles dans Solr</a>
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
	public GardienScolaire addSaisonCles(Long...objets) {
		for(Long o : objets) {
			addSaisonCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addSaisonCles(Long o) {
		if(o != null && !saisonCles.contains(o))
			this.saisonCles.add(o);
		return (GardienScolaire)this;
	}
	public GardienScolaire setSaisonCles(JsonArray objets) {
		saisonCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSaisonCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addSaisonCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSaisonCles(p);
		}
		return (GardienScolaire)this;
	}
	protected GardienScolaire saisonClesInit() {
		if(!saisonClesCouverture.dejaInitialise) {
			_saisonCles(saisonCles);
		}
		saisonClesCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "SaisonCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "SaisonCles() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:sessionCles">Trouver l'entité sessionCles dans Solr</a>
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
	public GardienScolaire addSessionCles(Long...objets) {
		for(Long o : objets) {
			addSessionCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addSessionCles(Long o) {
		if(o != null && !sessionCles.contains(o))
			this.sessionCles.add(o);
		return (GardienScolaire)this;
	}
	public GardienScolaire setSessionCles(JsonArray objets) {
		sessionCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSessionCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addSessionCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSessionCles(p);
		}
		return (GardienScolaire)this;
	}
	protected GardienScolaire sessionClesInit() {
		if(!sessionClesCouverture.dejaInitialise) {
			_sessionCles(sessionCles);
		}
		sessionClesCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "SessionCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "SessionCles() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:ageCles">Trouver l'entité ageCles dans Solr</a>
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
	public GardienScolaire addAgeCles(Long...objets) {
		for(Long o : objets) {
			addAgeCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addAgeCles(Long o) {
		if(o != null && !ageCles.contains(o))
			this.ageCles.add(o);
		return (GardienScolaire)this;
	}
	public GardienScolaire setAgeCles(JsonArray objets) {
		ageCles.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addAgeCles(o);
		}
		return (GardienScolaire)this;
	}
	public GardienScolaire addAgeCles(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addAgeCles(p);
		}
		return (GardienScolaire)this;
	}
	protected GardienScolaire ageClesInit() {
		if(!ageClesCouverture.dejaInitialise) {
			_ageCles(ageCles);
		}
		ageClesCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "AgeCles\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "AgeCles() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personnePrenom">Trouver l'entité personnePrenom dans Solr</a>
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
	protected GardienScolaire personnePrenomInit() {
		if(!personnePrenomCouverture.dejaInitialise) {
			_personnePrenom(personnePrenomCouverture);
			if(personnePrenom == null)
				setPersonnePrenom(personnePrenomCouverture.o);
		}
		personnePrenomCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonnePrenom\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonnePrenom() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:familleNom">Trouver l'entité familleNom dans Solr</a>
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
	protected GardienScolaire familleNomInit() {
		if(!familleNomCouverture.dejaInitialise) {
			_familleNom(familleNomCouverture);
			if(familleNom == null)
				setFamilleNom(familleNomCouverture.o);
		}
		familleNomCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "FamilleNom\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "FamilleNom() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNomComplet">Trouver l'entité personneNomComplet dans Solr</a>
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
	protected GardienScolaire personneNomCompletInit() {
		if(!personneNomCompletCouverture.dejaInitialise) {
			_personneNomComplet(personneNomCompletCouverture);
			if(personneNomComplet == null)
				setPersonneNomComplet(personneNomCompletCouverture.o);
		}
		personneNomCompletCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneNomComplet\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneNomComplet() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNomCompletPrefere">Trouver l'entité personneNomCompletPrefere dans Solr</a>
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
	protected GardienScolaire personneNomCompletPrefereInit() {
		if(!personneNomCompletPrefereCouverture.dejaInitialise) {
			_personneNomCompletPrefere(personneNomCompletPrefereCouverture);
			if(personneNomCompletPrefere == null)
				setPersonneNomCompletPrefere(personneNomCompletPrefereCouverture.o);
		}
		personneNomCompletPrefereCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneNomCompletPrefere\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneNomCompletPrefere() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNomFormel">Trouver l'entité personneNomFormel dans Solr</a>
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
	protected GardienScolaire personneNomFormelInit() {
		if(!personneNomFormelCouverture.dejaInitialise) {
			_personneNomFormel(personneNomFormelCouverture);
			if(personneNomFormel == null)
				setPersonneNomFormel(personneNomFormelCouverture.o);
		}
		personneNomFormelCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneNomFormel\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneNomFormel() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneOccupation">Trouver l'entité personneOccupation dans Solr</a>
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
	protected GardienScolaire personneOccupationInit() {
		if(!personneOccupationCouverture.dejaInitialise) {
			_personneOccupation(personneOccupationCouverture);
			if(personneOccupation == null)
				setPersonneOccupation(personneOccupationCouverture.o);
		}
		personneOccupationCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneOccupation\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneOccupation() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneNumeroTelephone">Trouver l'entité personneNumeroTelephone dans Solr</a>
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
	protected GardienScolaire personneNumeroTelephoneInit() {
		if(!personneNumeroTelephoneCouverture.dejaInitialise) {
			_personneNumeroTelephone(personneNumeroTelephoneCouverture);
			if(personneNumeroTelephone == null)
				setPersonneNumeroTelephone(personneNumeroTelephoneCouverture.o);
		}
		personneNumeroTelephoneCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneNumeroTelephone\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneNumeroTelephone() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneMail">Trouver l'entité personneMail dans Solr</a>
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
	protected GardienScolaire personneMailInit() {
		if(!personneMailCouverture.dejaInitialise) {
			_personneMail(personneMailCouverture);
			if(personneMail == null)
				setPersonneMail(personneMailCouverture.o);
		}
		personneMailCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneMail\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneMail() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneRelation">Trouver l'entité personneRelation dans Solr</a>
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
	protected GardienScolaire personneRelationInit() {
		if(!personneRelationCouverture.dejaInitialise) {
			_personneRelation(personneRelationCouverture);
			if(personneRelation == null)
				setPersonneRelation(personneRelationCouverture.o);
		}
		personneRelationCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneRelation\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneRelation() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneSms">Trouver l'entité personneSms dans Solr</a>
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
	public GardienScolaire setPersonneSms(String o) {
		this.personneSms = Boolean.parseBoolean(o);
		this.personneSmsCouverture.dejaInitialise = true;
		return (GardienScolaire)this;
	}
	protected GardienScolaire personneSmsInit() {
		if(!personneSmsCouverture.dejaInitialise) {
			_personneSms(personneSmsCouverture);
			if(personneSms == null)
				setPersonneSms(personneSmsCouverture.o);
		}
		personneSmsCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneSms\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneSms() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneRecevoirMail">Trouver l'entité personneRecevoirMail dans Solr</a>
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
	public GardienScolaire setPersonneRecevoirMail(String o) {
		this.personneRecevoirMail = Boolean.parseBoolean(o);
		this.personneRecevoirMailCouverture.dejaInitialise = true;
		return (GardienScolaire)this;
	}
	protected GardienScolaire personneRecevoirMailInit() {
		if(!personneRecevoirMailCouverture.dejaInitialise) {
			_personneRecevoirMail(personneRecevoirMailCouverture);
			if(personneRecevoirMail == null)
				setPersonneRecevoirMail(personneRecevoirMailCouverture.o);
		}
		personneRecevoirMailCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneRecevoirMail\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneRecevoirMail() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneContactUrgence">Trouver l'entité personneContactUrgence dans Solr</a>
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
	public GardienScolaire setPersonneContactUrgence(String o) {
		this.personneContactUrgence = Boolean.parseBoolean(o);
		this.personneContactUrgenceCouverture.dejaInitialise = true;
		return (GardienScolaire)this;
	}
	protected GardienScolaire personneContactUrgenceInit() {
		if(!personneContactUrgenceCouverture.dejaInitialise) {
			_personneContactUrgence(personneContactUrgenceCouverture);
			if(personneContactUrgence == null)
				setPersonneContactUrgence(personneContactUrgenceCouverture.o);
		}
		personneContactUrgenceCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneContactUrgence\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneContactUrgence() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:personneChercher">Trouver l'entité personneChercher dans Solr</a>
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
	public GardienScolaire setPersonneChercher(String o) {
		this.personneChercher = Boolean.parseBoolean(o);
		this.personneChercherCouverture.dejaInitialise = true;
		return (GardienScolaire)this;
	}
	protected GardienScolaire personneChercherInit() {
		if(!personneChercherCouverture.dejaInitialise) {
			_personneChercher(personneChercherCouverture);
			if(personneChercher == null)
				setPersonneChercher(personneChercherCouverture.o);
		}
		personneChercherCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PersonneChercher\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PersonneChercher() {");
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

	///////////////////////
	// gardienNomComplet //
	///////////////////////

	/**	L'entité « gardienNomComplet »
	 *	 is defined as null before being initialized. 
	 */
	protected String gardienNomComplet;
	@JsonIgnore
	public Couverture<String> gardienNomCompletCouverture = new Couverture<String>().p(this).c(String.class).var("gardienNomComplet").o(gardienNomComplet);

	/**	<br/>L'entité « gardienNomComplet »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:gardienNomComplet">Trouver l'entité gardienNomComplet dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _gardienNomComplet(Couverture<String> c);

	public String getGardienNomComplet() {
		return gardienNomComplet;
	}

	public void setGardienNomComplet(String gardienNomComplet) {
		this.gardienNomComplet = gardienNomComplet;
		this.gardienNomCompletCouverture.dejaInitialise = true;
	}
	protected GardienScolaire gardienNomCompletInit() {
		if(!gardienNomCompletCouverture.dejaInitialise) {
			_gardienNomComplet(gardienNomCompletCouverture);
			if(gardienNomComplet == null)
				setGardienNomComplet(gardienNomCompletCouverture.o);
		}
		gardienNomCompletCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
	}

	public String solrGardienNomComplet() {
		return gardienNomComplet;
	}

	public String strGardienNomComplet() {
		return gardienNomComplet == null ? "" : gardienNomComplet;
	}

	public String jsonGardienNomComplet() {
		return gardienNomComplet == null ? "" : gardienNomComplet;
	}

	public String nomAffichageGardienNomComplet() {
		return null;
	}

	public String htmTooltipGardienNomComplet() {
		return null;
	}

	public String htmGardienNomComplet() {
		return gardienNomComplet == null ? "" : StringEscapeUtils.escapeHtml4(strGardienNomComplet());
	}

	public void htmGardienNomComplet(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchGardienScolaire", strPk(), "GardienNomComplet\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "GardienNomComplet() {");
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
				r.l("				data: {\"setGardienNomComplet\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageGardienNomComplet()), "</span>");
				r.s("			<input");
							r.s(" name=\"gardienNomComplet\"");
							r.s(" value=\"", htmGardienNomComplet(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmGardienNomComplet());
			}
			r.l("</div>");
		}
	}

	///////////////
	// gardienId //
	///////////////

	/**	L'entité « gardienId »
	 *	 is defined as null before being initialized. 
	 */
	protected String gardienId;
	@JsonIgnore
	public Couverture<String> gardienIdCouverture = new Couverture<String>().p(this).c(String.class).var("gardienId").o(gardienId);

	/**	<br/>L'entité « gardienId »
	 *  est défini comme null avant d'être initialisé. 
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:gardienId">Trouver l'entité gardienId dans Solr</a>
	 * <br/>
	 * @param c est pour envelopper une valeur à assigner à cette entité lors de l'initialisation. 
	 **/
	protected abstract void _gardienId(Couverture<String> c);

	public String getGardienId() {
		return gardienId;
	}

	public void setGardienId(String gardienId) {
		this.gardienId = gardienId;
		this.gardienIdCouverture.dejaInitialise = true;
	}
	protected GardienScolaire gardienIdInit() {
		if(!gardienIdCouverture.dejaInitialise) {
			_gardienId(gardienIdCouverture);
			if(gardienId == null)
				setGardienId(gardienIdCouverture.o);
		}
		gardienIdCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
	}

	public String solrGardienId() {
		return gardienId;
	}

	public String strGardienId() {
		return gardienId == null ? "" : gardienId;
	}

	public String jsonGardienId() {
		return gardienId == null ? "" : gardienId;
	}

	public String nomAffichageGardienId() {
		return "ID";
	}

	public String htmTooltipGardienId() {
		return null;
	}

	public String htmGardienId() {
		return gardienId == null ? "" : StringEscapeUtils.escapeHtml4(strGardienId());
	}

	public void htmGardienId(ToutEcrivain r, Boolean patchDroits) {
		if(pk!= null) {
			r.s("<div id=\"patchGardienScolaire", strPk(), "GardienId\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "GardienId() {");
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
				r.l("				data: {\"setGardienId\": this.value },");
				r.l("				");
				r.l("			});");
				r.l("		}");
				r.l("	//]]></script>");
				r.l("	<div class=\"\">");
				r.l("		<label class=\"w3-tooltip \">");
				r.l("			<span>", StringEscapeUtils.escapeHtml4(nomAffichageGardienId()), "</span>");
				r.s("			<input");
							r.s(" name=\"gardienId\"");
							r.s(" value=\"", htmGardienId(), "\");");
							r.s(" onchange=\"\"");
							r.l("/>");
				r.l("		</label>");
				r.l("	</div>");
			} else {
				r.s(htmGardienId());
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:pageUrl">Trouver l'entité pageUrl dans Solr</a>
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
	protected GardienScolaire pageUrlInit() {
		if(!pageUrlCouverture.dejaInitialise) {
			_pageUrl(pageUrlCouverture);
			if(pageUrl == null)
				setPageUrl(pageUrlCouverture.o);
		}
		pageUrlCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "PageUrl\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "PageUrl() {");
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
	 * <br/><a href="http://localhost:10383/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_frFR_indexed_string:org.computate.scolaire.frFR.gardien.GardienScolaire&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_frFR_indexed_string:objetSuggere">Trouver l'entité objetSuggere dans Solr</a>
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
	protected GardienScolaire objetSuggereInit() {
		if(!objetSuggereCouverture.dejaInitialise) {
			_objetSuggere(objetSuggereCouverture);
			if(objetSuggere == null)
				setObjetSuggere(objetSuggereCouverture.o);
		}
		objetSuggereCouverture.dejaInitialise(true);
		return (GardienScolaire)this;
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
			r.s("<div id=\"patchGardienScolaire", strPk(), "ObjetSuggere\">");
			if(patchDroits) {
				r.l();
				r.l("	<script>//<![CDATA[");
				r.l("		function patchGardienScolaire", strPk(), "ObjetSuggere() {");
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

	protected boolean dejaInitialiseGardienScolaire = false;

	public GardienScolaire initLoinGardienScolaire(RequeteSiteFrFR requeteSite_) {
		setRequeteSite_(requeteSite_);
		if(!dejaInitialiseGardienScolaire) {
			dejaInitialiseGardienScolaire = true;
			initLoinGardienScolaire();
		}
		return (GardienScolaire)this;
	}

	public void initLoinGardienScolaire() {
		super.initLoinCluster(requeteSite_);
		initGardienScolaire();
	}

	public void initGardienScolaire() {
		gardienCleInit();
		inscriptionClesInit();
		familleTriInit();
		gardienTriInit();
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
		gardienNomCompletInit();
		gardienIdInit();
		pageUrlInit();
		objetSuggereInit();
	}

	@Override public void initLoinPourClasse(RequeteSiteFrFR requeteSite_) {
		initLoinGardienScolaire(requeteSite_);
	}

	/////////////////
	// requeteSite //
	/////////////////

	public void requeteSiteGardienScolaire(RequeteSiteFrFR requeteSite_) {
			super.requeteSiteCluster(requeteSite_);
		if(inscriptionRecherche != null)
			inscriptionRecherche.setRequeteSite_(requeteSite_);
	}

	public void requeteSitePourClasse(RequeteSiteFrFR requeteSite_) {
		requeteSiteGardienScolaire(requeteSite_);
	}

	/////////////
	// obtenir //
	/////////////

	@Override public Object obtenirPourClasse(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtenirGardienScolaire(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtenirPourClasse(v);
			}
		}
		return o;
	}
	public Object obtenirGardienScolaire(String var) {
		GardienScolaire oGardienScolaire = (GardienScolaire)this;
		switch(var) {
			case "gardienCle":
				return oGardienScolaire.gardienCle;
			case "inscriptionCles":
				return oGardienScolaire.inscriptionCles;
			case "familleTri":
				return oGardienScolaire.familleTri;
			case "gardienTri":
				return oGardienScolaire.gardienTri;
			case "inscriptionRecherche":
				return oGardienScolaire.inscriptionRecherche;
			case "inscriptions":
				return oGardienScolaire.inscriptions;
			case "ecoleCles":
				return oGardienScolaire.ecoleCles;
			case "anneeCles":
				return oGardienScolaire.anneeCles;
			case "saisonCles":
				return oGardienScolaire.saisonCles;
			case "sessionCles":
				return oGardienScolaire.sessionCles;
			case "ageCles":
				return oGardienScolaire.ageCles;
			case "personnePrenom":
				return oGardienScolaire.personnePrenom;
			case "familleNom":
				return oGardienScolaire.familleNom;
			case "personneNomComplet":
				return oGardienScolaire.personneNomComplet;
			case "personneNomCompletPrefere":
				return oGardienScolaire.personneNomCompletPrefere;
			case "personneNomFormel":
				return oGardienScolaire.personneNomFormel;
			case "personneOccupation":
				return oGardienScolaire.personneOccupation;
			case "personneNumeroTelephone":
				return oGardienScolaire.personneNumeroTelephone;
			case "personneMail":
				return oGardienScolaire.personneMail;
			case "personneRelation":
				return oGardienScolaire.personneRelation;
			case "personneSms":
				return oGardienScolaire.personneSms;
			case "personneRecevoirMail":
				return oGardienScolaire.personneRecevoirMail;
			case "personneContactUrgence":
				return oGardienScolaire.personneContactUrgence;
			case "personneChercher":
				return oGardienScolaire.personneChercher;
			case "gardienNomComplet":
				return oGardienScolaire.gardienNomComplet;
			case "gardienId":
				return oGardienScolaire.gardienId;
			case "pageUrl":
				return oGardienScolaire.pageUrl;
			case "objetSuggere":
				return oGardienScolaire.objetSuggere;
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
				o = attribuerGardienScolaire(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attribuerPourClasse(v, val);
			}
		}
		return o != null;
	}
	public Object attribuerGardienScolaire(String var, Object val) {
		GardienScolaire oGardienScolaire = (GardienScolaire)this;
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
					o = definirGardienScolaire(v, val);
				else if(o instanceof Cluster) {
					Cluster cluster = (Cluster)o;
					o = cluster.definirPourClasse(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definirGardienScolaire(String var, String val) {
		switch(var) {
			case "personnePrenom":
				setPersonnePrenom(val);
				sauvegardesGardienScolaire.add(var);
				return val;
			case "familleNom":
				setFamilleNom(val);
				sauvegardesGardienScolaire.add(var);
				return val;
			case "personneNomComplet":
				setPersonneNomComplet(val);
				sauvegardesGardienScolaire.add(var);
				return val;
			case "personneNomCompletPrefere":
				setPersonneNomCompletPrefere(val);
				sauvegardesGardienScolaire.add(var);
				return val;
			case "personneNomFormel":
				setPersonneNomFormel(val);
				sauvegardesGardienScolaire.add(var);
				return val;
			default:
				return super.definirCluster(var, val);
		}
	}

	/////////////////
	// sauvegardes //
	/////////////////

	protected List<String> sauvegardesGardienScolaire = new ArrayList<String>();

	/////////////
	// peupler //
	/////////////

	@Override public void peuplerPourClasse(SolrDocument solrDocument) {
		peuplerGardienScolaire(solrDocument);
	}
	public void peuplerGardienScolaire(SolrDocument solrDocument) {
		GardienScolaire oGardienScolaire = (GardienScolaire)this;
		sauvegardesGardienScolaire = (List<String>)solrDocument.get("sauvegardesGardienScolaire_stored_strings");
		if(sauvegardesGardienScolaire != null) {

			if(sauvegardesGardienScolaire.contains("gardienCle")) {
				Long gardienCle = (Long)solrDocument.get("gardienCle_stored_long");
				if(gardienCle != null)
					oGardienScolaire.setGardienCle(gardienCle);
			}

			if(sauvegardesGardienScolaire.contains("inscriptionCles")) {
				List<Long> inscriptionCles = (List<Long>)solrDocument.get("inscriptionCles_stored_longs");
				if(inscriptionCles != null)
					oGardienScolaire.inscriptionCles.addAll(inscriptionCles);
			}

			if(sauvegardesGardienScolaire.contains("familleTri")) {
				Integer familleTri = (Integer)solrDocument.get("familleTri_stored_int");
				if(familleTri != null)
					oGardienScolaire.setFamilleTri(familleTri);
			}

			if(sauvegardesGardienScolaire.contains("gardienTri")) {
				Integer gardienTri = (Integer)solrDocument.get("gardienTri_stored_int");
				if(gardienTri != null)
					oGardienScolaire.setGardienTri(gardienTri);
			}

			if(sauvegardesGardienScolaire.contains("ecoleCles")) {
				List<Long> ecoleCles = (List<Long>)solrDocument.get("ecoleCles_stored_longs");
				if(ecoleCles != null)
					oGardienScolaire.ecoleCles.addAll(ecoleCles);
			}

			if(sauvegardesGardienScolaire.contains("anneeCles")) {
				List<Long> anneeCles = (List<Long>)solrDocument.get("anneeCles_stored_longs");
				if(anneeCles != null)
					oGardienScolaire.anneeCles.addAll(anneeCles);
			}

			if(sauvegardesGardienScolaire.contains("saisonCles")) {
				List<Long> saisonCles = (List<Long>)solrDocument.get("saisonCles_stored_longs");
				if(saisonCles != null)
					oGardienScolaire.saisonCles.addAll(saisonCles);
			}

			if(sauvegardesGardienScolaire.contains("sessionCles")) {
				List<Long> sessionCles = (List<Long>)solrDocument.get("sessionCles_stored_longs");
				if(sessionCles != null)
					oGardienScolaire.sessionCles.addAll(sessionCles);
			}

			if(sauvegardesGardienScolaire.contains("ageCles")) {
				List<Long> ageCles = (List<Long>)solrDocument.get("ageCles_stored_longs");
				if(ageCles != null)
					oGardienScolaire.ageCles.addAll(ageCles);
			}

			if(sauvegardesGardienScolaire.contains("personnePrenom")) {
				String personnePrenom = (String)solrDocument.get("personnePrenom_stored_string");
				if(personnePrenom != null)
					oGardienScolaire.setPersonnePrenom(personnePrenom);
			}

			if(sauvegardesGardienScolaire.contains("familleNom")) {
				String familleNom = (String)solrDocument.get("familleNom_stored_string");
				if(familleNom != null)
					oGardienScolaire.setFamilleNom(familleNom);
			}

			if(sauvegardesGardienScolaire.contains("personneNomComplet")) {
				String personneNomComplet = (String)solrDocument.get("personneNomComplet_stored_string");
				if(personneNomComplet != null)
					oGardienScolaire.setPersonneNomComplet(personneNomComplet);
			}

			if(sauvegardesGardienScolaire.contains("personneNomCompletPrefere")) {
				String personneNomCompletPrefere = (String)solrDocument.get("personneNomCompletPrefere_stored_string");
				if(personneNomCompletPrefere != null)
					oGardienScolaire.setPersonneNomCompletPrefere(personneNomCompletPrefere);
			}

			if(sauvegardesGardienScolaire.contains("personneNomFormel")) {
				String personneNomFormel = (String)solrDocument.get("personneNomFormel_stored_string");
				if(personneNomFormel != null)
					oGardienScolaire.setPersonneNomFormel(personneNomFormel);
			}

			if(sauvegardesGardienScolaire.contains("personneOccupation")) {
				String personneOccupation = (String)solrDocument.get("personneOccupation_stored_string");
				if(personneOccupation != null)
					oGardienScolaire.setPersonneOccupation(personneOccupation);
			}

			if(sauvegardesGardienScolaire.contains("personneNumeroTelephone")) {
				String personneNumeroTelephone = (String)solrDocument.get("personneNumeroTelephone_stored_string");
				if(personneNumeroTelephone != null)
					oGardienScolaire.setPersonneNumeroTelephone(personneNumeroTelephone);
			}

			if(sauvegardesGardienScolaire.contains("personneMail")) {
				String personneMail = (String)solrDocument.get("personneMail_stored_string");
				if(personneMail != null)
					oGardienScolaire.setPersonneMail(personneMail);
			}

			if(sauvegardesGardienScolaire.contains("personneRelation")) {
				String personneRelation = (String)solrDocument.get("personneRelation_stored_string");
				if(personneRelation != null)
					oGardienScolaire.setPersonneRelation(personneRelation);
			}

			if(sauvegardesGardienScolaire.contains("personneSms")) {
				Boolean personneSms = (Boolean)solrDocument.get("personneSms_stored_boolean");
				if(personneSms != null)
					oGardienScolaire.setPersonneSms(personneSms);
			}

			if(sauvegardesGardienScolaire.contains("personneRecevoirMail")) {
				Boolean personneRecevoirMail = (Boolean)solrDocument.get("personneRecevoirMail_stored_boolean");
				if(personneRecevoirMail != null)
					oGardienScolaire.setPersonneRecevoirMail(personneRecevoirMail);
			}

			if(sauvegardesGardienScolaire.contains("personneContactUrgence")) {
				Boolean personneContactUrgence = (Boolean)solrDocument.get("personneContactUrgence_stored_boolean");
				if(personneContactUrgence != null)
					oGardienScolaire.setPersonneContactUrgence(personneContactUrgence);
			}

			if(sauvegardesGardienScolaire.contains("personneChercher")) {
				Boolean personneChercher = (Boolean)solrDocument.get("personneChercher_stored_boolean");
				if(personneChercher != null)
					oGardienScolaire.setPersonneChercher(personneChercher);
			}

			if(sauvegardesGardienScolaire.contains("gardienNomComplet")) {
				String gardienNomComplet = (String)solrDocument.get("gardienNomComplet_stored_string");
				if(gardienNomComplet != null)
					oGardienScolaire.setGardienNomComplet(gardienNomComplet);
			}

			if(sauvegardesGardienScolaire.contains("gardienId")) {
				String gardienId = (String)solrDocument.get("gardienId_stored_string");
				if(gardienId != null)
					oGardienScolaire.setGardienId(gardienId);
			}

			if(sauvegardesGardienScolaire.contains("pageUrl")) {
				String pageUrl = (String)solrDocument.get("pageUrl_stored_string");
				if(pageUrl != null)
					oGardienScolaire.setPageUrl(pageUrl);
			}

			if(sauvegardesGardienScolaire.contains("objetSuggere")) {
				String objetSuggere = (String)solrDocument.get("objetSuggere_suggested");
				oGardienScolaire.setObjetSuggere(objetSuggere);
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
			rechercheSolr.addFilterQuery("id:" + ClientUtils.escapeQueryChars("org.computate.scolaire.frFR.gardien.GardienScolaire"));
			QueryResponse reponseRecherche = requeteSite.getSiteContexte_().getClientSolr().query(rechercheSolr);
			if(reponseRecherche.getResults().size() > 0)
				requeteSite.setDocumentSolr(reponseRecherche.getResults().get(0));
			GardienScolaire o = new GardienScolaire();
			o.requeteSiteGardienScolaire(requeteSite);
			o.initLoinGardienScolaire(requeteSite);
			o.indexerGardienScolaire();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexerPourClasse() {
		indexerGardienScolaire();
	}

	@Override public void indexerPourClasse(SolrInputDocument document) {
		indexerGardienScolaire(document);
	}

	public void indexerGardienScolaire(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexerGardienScolaire(document);
			clientSolr.add(document);
			clientSolr.commit();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexerGardienScolaire() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexerGardienScolaire(document);
			SolrClient clientSolr = requeteSite_.getSiteContexte_().getClientSolr();
			clientSolr.add(document);
			clientSolr.commit();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexerGardienScolaire(SolrInputDocument document) {
		if(sauvegardesGardienScolaire != null)
			document.addField("sauvegardesGardienScolaire_stored_strings", sauvegardesGardienScolaire);

		if(gardienCle != null) {
			document.addField("gardienCle_indexed_long", gardienCle);
			document.addField("gardienCle_stored_long", gardienCle);
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
		if(gardienTri != null) {
			document.addField("gardienTri_indexed_int", gardienTri);
			document.addField("gardienTri_stored_int", gardienTri);
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
		if(gardienNomComplet != null) {
			document.addField("gardienNomComplet_indexed_string", gardienNomComplet);
			document.addField("gardienNomComplet_stored_string", gardienNomComplet);
		}
		if(gardienId != null) {
			document.addField("gardienId_indexed_string", gardienId);
			document.addField("gardienId_stored_string", gardienId);
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

	public void desindexerGardienScolaire() {
		try {
		RequeteSiteFrFR requeteSite = new RequeteSiteFrFR();
			requeteSite.initLoinRequeteSiteFrFR();
			SiteContexteFrFR siteContexte = new SiteContexteFrFR();
			siteContexte.initLoinSiteContexteFrFR();
			requeteSite.setSiteContexte_(siteContexte);
			requeteSite.setConfigSite_(siteContexte.getConfigSite());
			initLoinGardienScolaire(requeteSite);
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
		stockerGardienScolaire(solrDocument);
	}
	public void stockerGardienScolaire(SolrDocument solrDocument) {
		GardienScolaire oGardienScolaire = (GardienScolaire)this;

		Long gardienCle = (Long)solrDocument.get("gardienCle_stored_long");
		if(gardienCle != null)
			oGardienScolaire.setGardienCle(gardienCle);

		List<Long> inscriptionCles = (List<Long>)solrDocument.get("inscriptionCles_stored_longs");
		if(inscriptionCles != null)
			oGardienScolaire.inscriptionCles.addAll(inscriptionCles);

		Integer familleTri = (Integer)solrDocument.get("familleTri_stored_int");
		if(familleTri != null)
			oGardienScolaire.setFamilleTri(familleTri);

		Integer gardienTri = (Integer)solrDocument.get("gardienTri_stored_int");
		if(gardienTri != null)
			oGardienScolaire.setGardienTri(gardienTri);

		List<Long> ecoleCles = (List<Long>)solrDocument.get("ecoleCles_stored_longs");
		if(ecoleCles != null)
			oGardienScolaire.ecoleCles.addAll(ecoleCles);

		List<Long> anneeCles = (List<Long>)solrDocument.get("anneeCles_stored_longs");
		if(anneeCles != null)
			oGardienScolaire.anneeCles.addAll(anneeCles);

		List<Long> saisonCles = (List<Long>)solrDocument.get("saisonCles_stored_longs");
		if(saisonCles != null)
			oGardienScolaire.saisonCles.addAll(saisonCles);

		List<Long> sessionCles = (List<Long>)solrDocument.get("sessionCles_stored_longs");
		if(sessionCles != null)
			oGardienScolaire.sessionCles.addAll(sessionCles);

		List<Long> ageCles = (List<Long>)solrDocument.get("ageCles_stored_longs");
		if(ageCles != null)
			oGardienScolaire.ageCles.addAll(ageCles);

		String personnePrenom = (String)solrDocument.get("personnePrenom_stored_string");
		if(personnePrenom != null)
			oGardienScolaire.setPersonnePrenom(personnePrenom);

		String familleNom = (String)solrDocument.get("familleNom_stored_string");
		if(familleNom != null)
			oGardienScolaire.setFamilleNom(familleNom);

		String personneNomComplet = (String)solrDocument.get("personneNomComplet_stored_string");
		if(personneNomComplet != null)
			oGardienScolaire.setPersonneNomComplet(personneNomComplet);

		String personneNomCompletPrefere = (String)solrDocument.get("personneNomCompletPrefere_stored_string");
		if(personneNomCompletPrefere != null)
			oGardienScolaire.setPersonneNomCompletPrefere(personneNomCompletPrefere);

		String personneNomFormel = (String)solrDocument.get("personneNomFormel_stored_string");
		if(personneNomFormel != null)
			oGardienScolaire.setPersonneNomFormel(personneNomFormel);

		String personneOccupation = (String)solrDocument.get("personneOccupation_stored_string");
		if(personneOccupation != null)
			oGardienScolaire.setPersonneOccupation(personneOccupation);

		String personneNumeroTelephone = (String)solrDocument.get("personneNumeroTelephone_stored_string");
		if(personneNumeroTelephone != null)
			oGardienScolaire.setPersonneNumeroTelephone(personneNumeroTelephone);

		String personneMail = (String)solrDocument.get("personneMail_stored_string");
		if(personneMail != null)
			oGardienScolaire.setPersonneMail(personneMail);

		String personneRelation = (String)solrDocument.get("personneRelation_stored_string");
		if(personneRelation != null)
			oGardienScolaire.setPersonneRelation(personneRelation);

		Boolean personneSms = (Boolean)solrDocument.get("personneSms_stored_boolean");
		if(personneSms != null)
			oGardienScolaire.setPersonneSms(personneSms);

		Boolean personneRecevoirMail = (Boolean)solrDocument.get("personneRecevoirMail_stored_boolean");
		if(personneRecevoirMail != null)
			oGardienScolaire.setPersonneRecevoirMail(personneRecevoirMail);

		Boolean personneContactUrgence = (Boolean)solrDocument.get("personneContactUrgence_stored_boolean");
		if(personneContactUrgence != null)
			oGardienScolaire.setPersonneContactUrgence(personneContactUrgence);

		Boolean personneChercher = (Boolean)solrDocument.get("personneChercher_stored_boolean");
		if(personneChercher != null)
			oGardienScolaire.setPersonneChercher(personneChercher);

		String gardienNomComplet = (String)solrDocument.get("gardienNomComplet_stored_string");
		if(gardienNomComplet != null)
			oGardienScolaire.setGardienNomComplet(gardienNomComplet);

		String gardienId = (String)solrDocument.get("gardienId_stored_string");
		if(gardienId != null)
			oGardienScolaire.setGardienId(gardienId);

		String pageUrl = (String)solrDocument.get("pageUrl_stored_string");
		if(pageUrl != null)
			oGardienScolaire.setPageUrl(pageUrl);

		String objetSuggere = (String)solrDocument.get("objetSuggere_suggested");
		oGardienScolaire.setObjetSuggere(objetSuggere);

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
		if(!(o instanceof GardienScolaire))
			return false;
		GardienScolaire that = (GardienScolaire)o;
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
		sb.append("GardienScolaire { ");
		sb.append( "personnePrenom: \"" ).append(personnePrenom).append( "\"" );
		sb.append( ", familleNom: \"" ).append(familleNom).append( "\"" );
		sb.append( ", personneNomComplet: \"" ).append(personneNomComplet).append( "\"" );
		sb.append( ", personneNomCompletPrefere: \"" ).append(personneNomCompletPrefere).append( "\"" );
		sb.append( ", personneNomFormel: \"" ).append(personneNomFormel).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}