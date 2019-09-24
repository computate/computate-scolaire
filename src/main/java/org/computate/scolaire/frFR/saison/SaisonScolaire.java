package org.computate.scolaire.frFR.saison; 

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.computate.scolaire.frFR.annee.AnneeScolaire;
import org.computate.scolaire.frFR.cluster.Cluster;
import org.computate.scolaire.frFR.couverture.Couverture;
import org.computate.scolaire.frFR.recherche.ListeRecherche;

/**    
 * NomCanonique.enUS: org.computate.scolaire.enUS.season.SchoolSeason
 * Modele: true
 * Api: true
 * Indexe: true
 * Sauvegarde: true
 * 
 * ApiTag.frFR: Saison
 * ApiUri.frFR: /api/saison
 * 
 * ApiTag.enUS: Season
 * ApiUri.enUS: /api/season
 * 
 * ApiMethode: POST
 * ApiMethode: PATCH
 * ApiMethode: GET
 * ApiMethode: DELETE
 * ApiMethode.frFR: Recherche
 * ApiMethode.enUS: Search
 * 
 * ApiMethode.frFR: PageRecherche
 * PagePageRecherche.frFR: SaisonPage
 * PageSuperPageRecherche.frFR: ClusterPage
 * ApiUriPageRecherche.frFR: /saison
 * 
 * ApiMethode.enUS: SearchPage
 * PageSearchPage.enUS: SeasonPage
 * PageSuperSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /season
 * 
 * UnNom.frFR:une saison
 * UnNom.enUS:a season
 * Adjectif.frFR:scolaire
 * Adjectif.enUS:school
 * Couleur: yellow
 * IconeGroupe: regular
 * IconeNom: sun
*/                              
public class SaisonScolaire extends SaisonScolaireGen<Cluster> {

	/**
	 * {@inheritDoc}
	 * Var.enUS: schoolKey
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire de l'école dans la base de données. 
	 * Description.enUS: The primary key of the school in the database. 
	 * NomAffichage.frFR: école
	 * NomAffichage.enUS: school
	 */              
	protected void _ecoleCle(Couverture<Long> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: yearKey
	 * Indexe: true
	 * Stocke: true
	 * Attribuer: AnneeScolaire.saisonCles
	 * HtmlLigne: 4
	 * HtmlCelulle: 1
	 * Description.frFR: L'année scolaire de la saison scolaire. 
	 * Description.enUS: The school year of the school season. 
	 * NomAffichage.frFR: année
	 * NomAffichage.enUS: year
	*/             
	protected void _anneeCle(Couverture<Long> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonKey
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire de la saison dans la base de données. 
	 * Description.enUS: The primary key of the season in the database. 
	 * NomAffichage.frFR: clé
	 * NomAffichage.enUS: key
	 */          
	protected void _saisonCle(Couverture<Long> c) {
		c.o(pk);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: enrollmentKeys
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _inscriptionCles(List<Long> o) {}

	/** 
	 * {@inheritDoc}
	 * Var.enUS: sessionKeys
	 * Indexe: true
	 * Stocke: true
	 * Attribuer: SessionScolaire.saisonCle
	 * HtmlLigne: 4
	 * HtmlCelulle: 2
	 * Description.frFR: Les sessions scolaires de la saison scolaire. 
	 * Description.enUS: The school sessions of the school season. 
	 * NomAffichage.frFR: sessions
	 * NomAffichage.enUS: sessions
	 */     
	protected void _sessionCles(List<Long> o) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: educationSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _scolaireTri(Couverture<Integer> c) {
		c.o(3);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: schoolSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _ecoleTri(Couverture<Integer> c) {
		c.o(3);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: yearSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _anneeTri(Couverture<Integer> c) {
		c.o(3);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _saisonTri(Couverture<Integer> c) {
		c.o(3);
	}

	/**
	 * Var.enUS: yearSearch
	 * r: saisonCles
	 * r.enUS: seasonKeys
	 * r: AnneeScolaire
	 * r.enUS: SchoolYear
	 * r: setStocker
	 * r.enUS: setStore
	 * Ignorer: true
	 */                 
	protected void _anneeRecherche(ListeRecherche<AnneeScolaire> l) {
		l.setQuery("*:*");
		l.addFilterQuery("saisonCles_indexed_longs:" + pk);
		l.setC(AnneeScolaire.class);
		l.setStocker(true);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: year_
	 * r: anneeRecherche
	 * r.enUS: yearSearch
	 * Ignorer: true
	 */           
	protected void _annee_(Couverture<AnneeScolaire> c) {
		if(anneeRecherche.size() > 0) {
			c.o(anneeRecherche.get(0));
		}
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: schoolCompleteName
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 * r: EcoleNomComplet
	 * r.enUS: SchoolCompleteName
	 * r: annee
	 * r.enUS: year
	 */   
	protected void _ecoleNomComplet(Couverture<String> c) {
		if(annee_ != null)
			c.o((String)annee_.getEcoleNomComplet());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: yearStart
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: début de l'année
	 * NomAffichage.enUS: start of year
	 * r: AnneeDebut
	 * r.enUS: YearStart
	 * r: annee
	 * r.enUS: year
	 */                   
	protected void _anneeDebut(Couverture<LocalDate> c) {
		if(annee_ != null)
			c.o((LocalDate)annee_.getAnneeDebut());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: yearEnd
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: le fin de l'année
	 * NomAffichage.enUS: end of year
	 * r: AnneeFin
	 * r.enUS: YearStart
	 * r: annee
	 * r.enUS: year
	 */                     
	protected void _anneeFin(Couverture<LocalDate> c) {
		if(annee_ != null)
			c.o((LocalDate)annee_.getAnneeFin());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonStartDay
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCelulle: 1
	 * NomAffichage.frFR: début de la saison
	 * NomAffichage.enUS: start of the season
	 */                   
	protected void _saisonJourDebut(Couverture<LocalDate> c) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonSummer
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCelulle: 1
	 * NomAffichage.frFR: été
	 * NomAffichage.enUS: summer
	 */                   
	protected void _saisonEte(Couverture<Boolean> c) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonWinter
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCelulle: 1
	 * NomAffichage.frFR: hiver
	 * NomAffichage.enUS: winter
	 */                   
	protected void _saisonHiver(Couverture<Boolean> c) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonEnrollmentFee
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCelulle: 1
	 * NomAffichage.frFR: frais d'inscription
	 * NomAffichage.enUS: enrollment fee
	 */         
	protected void _saisonFraisInscription(Couverture<BigDecimal> c) {}

	/**   
	 * {@inheritDoc}
	 * Var.enUS: seasonCompleteName
	 * Indexe: true
	 * Stocke: true
	 * VarTitre: true
	 * VarH2: true
	 * HtmlColonne: 1
	 * r: saisonEte
	 * r.enUS: seasonSummer
	 * r: saisonHiver
	 * r.enUS: seasonWinter
	 * r: "%s saison d'été à %s"
	 * r.enUS: "%s summer season at %s"
	 * r: "%s saison scolaire à %s"
	 * r.enUS: "%s school season at %s"
	 * r: "%s saison à %s"
	 * r.enUS: "%s season at %s"
	 * r: strSaisonJourDebut
	 * r.enUS: strSeasonStartDay
	 * r: ecoleNomComplet
	 * r.enUS: schoolCompleteName
	 */              
	protected void _saisonNomComplet(Couverture<String> c) {
		String o;
		
		if(BooleanUtils.isTrue(saisonEte))
			o = String.format("%s saison d'été à %s", strSaisonJourDebut(), ecoleNomComplet);
		if(BooleanUtils.isTrue(saisonHiver))
			o = String.format("%s saison scolaire à %s", strSaisonJourDebut(), ecoleNomComplet);
		else
			o = String.format("%s saison à %s", strSaisonJourDebut(), ecoleNomComplet);
		
		c.o(o);
	}

	/**    
	 * {@inheritDoc}
	 * Var.enUS: seasonId
	 * Indexe: true
	 * Stocke: true
	 * VarId: true
	 * HtmlLigne: 1
	 * HtmlCelulle: 4
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: ID
	 * NomAffichage.enUS: ID
	 * r: saisonNomComplet
	 * r.enUS: seasonCompleteName
	 */            
	protected void _saisonId(Couverture<String> c) {
		if(saisonNomComplet != null) {
			String s = Normalizer.normalize(saisonNomComplet, Normalizer.Form.NFD);
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

	/**	la version plus courte de l'URL qui commence avec « / » 
	 * {@inheritDoc}
	 * Indexe: true
	 * Stocke: true
	 * VarUrl: true
	 * r: saisonId
	 * r.enUS: seasonId
	 * r: /saison/
	 * r.enUS: /season/
	 * r: requeteSite
	 * r.enUS: siteRequest
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: SiteUrlBase
	 * r.enUS: SiteBaseUrl
	 * **/     
	protected void _pageUrl(Couverture<String> c)  {
		if(saisonId != null) {
			String o = requeteSite_.getConfigSite_().getSiteUrlBase() + "/saison/" + saisonId;
			c.o(o);
		}
	}

	/**   
	 * {@inheritDoc}
	 * Var.enUS: objectSuggest
	 * Suggere: true
	 * r: saisonNomComplet
	 * r.enUS: seasonCompleteName
	 */         
	protected void _objetSuggere(Couverture<String> c) { 
		c.o(saisonNomComplet);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: _classCanonicalNames
	 * Indexe: true
	 * Stocke: true
	 * r: SaisonScolaire
	 * r.enUS: SchoolSeason
	 * r: classeNomsCanoniques
	 * r.enUS: classCanonicalNames
	 **/      
	@Override protected void _classeNomsCanoniques(List<String> l) {
		l.add(SaisonScolaire.class.getCanonicalName());
		super._classeNomsCanoniques(l);
	}
}