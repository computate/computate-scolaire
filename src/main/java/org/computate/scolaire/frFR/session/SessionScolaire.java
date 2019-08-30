package org.computate.scolaire.frFR.session;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.computate.scolaire.frFR.annee.AnneeScolaire;
import org.computate.scolaire.frFR.cluster.Cluster;
import org.computate.scolaire.frFR.couverture.Couverture;
import org.computate.scolaire.frFR.recherche.ListeRecherche;
import org.computate.scolaire.frFR.saison.SaisonScolaire;

/**    
 * NomCanonique.enUS: org.computate.scolaire.enUS.session.SchoolSession
 * Modele: true
 * Api: true
 * Indexe: true
 * Sauvegarde: true
 * 
 * ApiTag.frFR: Session
 * ApiUri.frFR: /frFR/api/sesson
 * 
 * ApiTag.enUS: Season
 * ApiUri.enUS: /enUS/api/session
 * 
 * ApiMethode: POST
 * ApiMethode: PATCH
 * ApiMethode: GET
 * ApiMethode: DELETE
 * ApiMethode.frFR: Recherche
 * ApiMethode.enUS: Search
 * 
 * ApiMethode.frFR: PageRecherche
 * PagePageRecherche.frFR: SessionPage
 * PageSuperPageRecherche.frFR: ClusterPage
 * ApiUriPageRecherche.frFR: /frFR/sesson
 * 
 * ApiMethode.enUS: SearchPage
 * PageSearchPage.enUS: SessionPage
 * PageSuperSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /enUS/session
 * 
 * UnNom.frFR: une sesson
 * UnNom.enUS: a session
 * Couleur: green
 * IconeGroupe: duotone
 * IconeNom: graduation-cap
*/                                                   
public class SessionScolaire extends SessionScolaireGen<Cluster> {

	/**
	 * {@inheritDoc}
	 * Var.enUS: schoolKey
	 * Indexe: true
	 * Stocke: true
	 * HtmlLigne: 3
	 * HtmlColonne: 3
	 * Description.frFR: La clé primaire de l'école dans la base de données. 
	 * Description.enUS: The primary key of the school in the database. 
	 * NomAffichage.frFR: école
	 * NomAffichage.enUS: school
	 */                
	protected void _ecoleCle(Couverture<Long> c) {
	}

	/*
	 * {@inheritDoc}
	 * Var.enUS: yearKey
	 * Indexe: true
	 * Stocke: true
	 * HtmlLigne: 3
	 * HtmlColonne: 4
	 * Description.frFR: L'année scolaire de la sesson scolaire. 
	 * Description.enUS: The school year of the school session. 
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
	 * HtmlLigne: 3
	 * HtmlColonne: 4
	 * Description.frFR: La saison scolaire de la sesson scolaire. 
	 * Description.enUS: The school season of the school session. 
	 * NomAffichage.frFR: saison
	 * NomAffichage.enUS: season
	 */          
	protected void _saisonCle(Couverture<Long> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sessionKey
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire de la sesson dans la base de données. 
	 * Description.enUS: The primary key of the session in the database. 
	 * NomAffichage.frFR: clé
	 * NomAffichage.enUS: key
	 */          
	protected void _sessonCle(Couverture<Long> c) {
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
	 * Var.enUS: ageKeys
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _ageCles(List<Long> o) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sessionKeys
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _sessionCles(List<Long> o) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: educationSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _scolaireTri(Couverture<Integer> c) {
		c.o(4);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: schoolSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _ecoleTri(Couverture<Integer> c) {
		c.o(4);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: yearSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _anneeTri(Couverture<Integer> c) {
		c.o(4);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _saisonTri(Couverture<Integer> c) {
		c.o(4);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sessionSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _sessionTri(Couverture<Integer> c) {
		c.o(4);
	}

	/**
	 * Var.enUS: seasonSearch
	 * r: saisonCles
	 * r.enUS: seasonKeys
	 * r: SaisonScolaire
	 * r.enUS: SchoolSeason
	 * Ignorer: true
	 */
	protected void _saisonRecherche(ListeRecherche<AnneeScolaire> l) {
		l.setQuery("*:*");
		l.addFilterQuery("sessonCles_indexed_longs:" + pk);
		l.setC(AnneeScolaire.class);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: season
	 * r: saisonRecherche
	 * r.enUS: seasonSearch
	 * Ignorer: true
	 */   
	protected void _saison(Couverture<SaisonScolaire> c) {
		if(saisonRecherche.size() > 0) {
			c.o(saisonRecherche.get(0));
		}
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: schoolNameComplete
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: 
	 * NomAffichage.enUS: 
	 * r: EcoleNomComplet
	 * r.enUS: SchoolNameComplete
	 * r: saison
	 * r.enUS: season
	 */   
	protected void _ecoleNomComplet(Couverture<String> c) {
		if(saison != null)
			c.o((String)saison.getEcoleNomComplet());
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
		if(saison != null)
			c.o((LocalDate)saison.getAnneeDebut());
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
		if(saison != null)
			c.o(saison.getAnneeFin());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonStart
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: début de l'année
	 * NomAffichage.enUS: start of season
	 * r: SaisonJourDebut
	 * r.enUS: SeasonStartDay
	 * r: saison
	 * r.enUS: season
	 */                   
	protected void _saisonJourDebut(Couverture<LocalDate> c) {
		if(saison != null)
			c.o(saison.getSaisonJourDebut());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonSummer
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: été
	 * NomAffichage.enUS: summer
	 * r: SaisonEte
	 * r.enUS: SeasonSummer
	 * r: saison
	 * r.enUS: season
	 */                   
	protected void _saisonEte(Couverture<Boolean> c) {
		if(saison != null)
			c.o(saison.getSaisonEte());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonWinter
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: hiver
	 * NomAffichage.enUS: winter
	 * r: SaisonHiver
	 * r.enUS: SeasonWinter
	 * r: saison
	 * r.enUS: season
	 */                   
	protected void _saisonHiver(Couverture<Boolean> c) {
		if(saison != null)
			c.o(saison.getSaisonHiver());
	}

	/**   
	 * {@inheritDoc}
	 * Var.enUS: seasonNameComplete
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _saisonNomComplet(Couverture<String> c) {
		String o;
		
		if(BooleanUtils.isTrue(saisonEte))
			o = String.format("saison d'été qui commence %s à %s. ", strSaisonJourDebut(), ecoleNomComplet);
		else
			o = String.format("saison scolaire qui commence %s à %s. ", strSaisonJourDebut(), ecoleNomComplet);
		
		c.o(o);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonEnd
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: le fin de l'année
	 * NomAffichage.enUS: end of season
	 * r: AnneeFin
	 * r.enUS: YearStart
	 * r: saison
	 * r.enUS: season
	 */                      
	protected void _saisonFin(Couverture<LocalDate> c) {
		if(saison != null)
			c.o((LocalDate)saison.getAnneeFin());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sessionStartDay
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlColonne: 1
	 * NomAffichage.frFR: début de la sesson
	 * NomAffichage.enUS: start of the session
	 */                   
	protected void _sessonJourDebut(Couverture<LocalDate> c) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sessionSummer
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlColonne: 1
	 * NomAffichage.frFR: été
	 * NomAffichage.enUS: summer
	 */                   
	protected void _sessonEte(Couverture<Boolean> c) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sessionWinter
	 * Indexe: true
	 * Stocke: true
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlColonne: 1
	 * NomAffichage.frFR: hiver
	 * NomAffichage.enUS: winter
	 */                   
	protected void _sessonHiver(Couverture<Boolean> c) {}

	/**   
	 * {@inheritDoc}
	 * Var.enUS: sessionNameComplete
	 * Indexe: true
	 * Stocke: true
	 * VarTitre: true
	 * r: sessonEte
	 * r.enUS: sessionSummer
	 * r: sesson scolaire qui commence %s à %s. 
	 * r.enUS: school session starting %s at %s. 
	 * r: strSaisonJourDebut
	 * r.enUS: strSeasonStartDay
	 * r: ecoleNomComplet
	 * r.enUS: schoolNameComplete
	 */
	protected void _sessonNomComplet(Couverture<String> c) {
		String o;
		
		if(BooleanUtils.isTrue(sessonEte))
			o = String.format("sesson d'été qui commence %s à %s. ", strSaisonJourDebut(), ecoleNomComplet);
		else
			o = String.format("sesson scolaire qui commence %s à %s. ", strSaisonJourDebut(), ecoleNomComplet);
		
		c.o(o);
	}

	/**   
	 * {@inheritDoc}
	 * Var.enUS: sessionId
	 * Indexe: true
	 * Stocke: true
	 * VarId: true
	 * HtmlLigne: 1
	 * HtmlColonne: 4
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: ID
	 * NomAffichage.enUS: ID
	 * r: sessonNomComplet
	 * r.enUS: sessionNameComplete
	 */            
	protected void _sessonId(Couverture<String> c) {
		if(sessonNomComplet != null) {
			String s = Normalizer.normalize(sessonNomComplet, Normalizer.Form.NFD);
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
	 * r: sessonId
	 * r.enUS: sessionId
	 * r: /frFR/sesson/
	 * r.enUS: /enUS/session/
	 * r: requeteSite
	 * r.enUS: siteRequest
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: SiteUrlBase
	 * r.enUS: SiteBaseUrl
	 * **/   
	protected void _pageUrl(Couverture<String> c)  {
		if(sessonId != null) {
			String o = requeteSite_.getConfigSite_().getSiteUrlBase() + "/frFR/sesson/" + sessonId;
			c.o(o);
		}
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: objectSuggest
	 * Suggere: true
	 * r: sessonNomComplet
	 * r.enUS: sessionNameComplete
	 */         
	protected void _objetSuggere(Couverture<String> c) { 
		c.o(sessonNomComplet);
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
