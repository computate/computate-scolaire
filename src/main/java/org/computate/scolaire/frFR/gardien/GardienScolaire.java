package org.computate.scolaire.frFR.gardien;

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.computate.scolaire.frFR.cluster.Cluster;
import org.computate.scolaire.frFR.couverture.Couverture;
import org.computate.scolaire.frFR.inscription.InscriptionScolaire;
import org.computate.scolaire.frFR.recherche.ListeRecherche;

/**    
 * NomCanonique.enUS: org.computate.scolaire.enUS.guardian.SchoolGuardian
 * Modele: true
 * Api: true
 * Indexe: true
 * Sauvegarde: true
 * 
 * ApiTag.frFR: Gardien
 * ApiUri.frFR: /api/gardien
 * 
 * ApiTag.enUS: Guardian
 * ApiUri.enUS: /api/guardian
 * 
 * ApiMethode: POST
 * ApiMethode: PATCH
 * ApiMethode: GET
 * ApiMethode: DELETE
 * ApiMethode.frFR: Recherche
 * ApiMethode.enUS: Search
 * 
 * ApiMethode.frFR: PageRecherche
 * PagePageRecherche.frFR: GardienPage
 * PageSuperPageRecherche.frFR: ClusterPage
 * ApiUriPageRecherche.frFR: /gardien
 * 
 * ApiMethode.enUS: SearchPage
 * PageSearchPage.enUS: GuardianPage
 * PageSuperSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /guardian
 * 
 * UnNom.frFR: un gardien
 * UnNom.enUS: a guardian
 * Couleur: yellow
 * IconeGroupe: regular
 * IconeNom: chevron-right
*/    
public class GardienScolaire extends GardienScolaireGen<Cluster> {

	/**
	 * {@inheritDoc}
	 * Var.enUS: enrollmentKeys
	 * Indexe: true
	 * Stocke: true
	 * Attribuer: InscriptionScolaire.gardienCles
	 * HtmlLigne: 5
	 * HtmlCellule: 1
	 * Description.frFR: La clé primaire du gardien dans la base de données. 
	 * Description.enUS: The primary key of the school guardian in the database. 
	 * NomAffichage.frFR: inscriptions
	 * NomAffichage.enUS: enrollments
	 */               
	protected void _inscriptionCles(Couverture<Long> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: guardianKey
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire du gardien dans la base de données. 
	 * Description.enUS: The primary key of the school guardian in the database. 
	 * NomAffichage.frFR: clé
	 * NomAffichage.enUS: key
	 */               
	protected void _gardienCle(Couverture<Long> c) {
		c.o(pk);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: enrollmentKeys
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: inscriptions
	 * NomAffichage.enUS: enrollments
	 */               
	protected void _inscriptionCles(List<Long> o) {}

	/**
	 * {@inheritDoc}
	 * Var.enUS: familySort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _familleTri(Couverture<Integer> c) {
		c.o(2);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: schoolSort
	 * Indexe: true
	 * Stocke: true
	 */
	protected void _gardienTri(Couverture<Integer> c) {
		c.o(2);
	}

	/**
	 * Var.enUS: enrollmentSearch
	 * r: gardienCle
	 * r.enUS: guardianKey
	 * r: InscriptionScolaire
	 * r.enUS: SchoolEnrollment
	 * r: setStocker
	 * r.enUS: setStore
	 * Ignorer: true
	 */
	protected void _inscriptionRecherche(ListeRecherche<InscriptionScolaire> l) {
		l.setQuery("*:*");
		l.addFilterQuery("gardienCle_indexed_long:" + pk);
		l.setC(InscriptionScolaire.class);
		l.setStocker(true);
		l.setFacet(true);
		l.addFacetField("ecoleCle_indexed_long");
		l.addFacetField("anneeCle_indexed_long");
		l.addFacetField("saisonCle_indexed_long");
		l.addFacetField("sessionCle_indexed_long");
		l.addFacetField("ageCle_indexed_long");
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: inscriptions
	 * r: inscriptionRecherche
	 * r.enUS: enrollmentSearch
	 * r: inscriptions
	 * r.enUS: enrollments
	 * Ignorer: true
	 */   
	protected void _inscriptions(List<InscriptionScolaire> l) {
		l.addAll(inscriptionRecherche.getList());
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: schoolKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire de l'école dans la base de données. 
	 * Description.enUS: The primary key of the school in the database. 
	 * NomAffichage.frFR: écoles
	 * NomAffichage.enUS: schools
	 * r: ecoleCle
	 * r.enUS: schoolKey
	 * r: inscriptionRecherche
	 * r.enUS: enrollmentSearch
	 */                  
	protected void _ecoleCles(List<Long> l) {
		l.addAll(inscriptionRecherche.getQueryResponse().getFacetField("ecoleCle_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: yearKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: L'année scolaire du inscription scolaire. 
	 * Description.enUS: The school year of the school enrollment. 
	 * NomAffichage.frFR: années
	 * NomAffichage.enUS: years
	 * r: anneeCle
	 * r.enUS: yearKey
	 * r: inscriptionRecherche
	 * r.enUS: enrollmentSearch
	 */
	protected void _anneeCles(List<Long> l) {
		l.addAll(inscriptionRecherche.getQueryResponse().getFacetField("anneeCle_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: seasonKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La saison scolaire du inscription scolaire. 
	 * Description.enUS: The school season of the school enrollment. 
	 * NomAffichage.frFR: saisons
	 * NomAffichage.enUS: seasons
	 * r: saisonCle
	 * r.enUS: seasonKey
	 * r: inscriptionRecherche
	 * r.enUS: enrollmentSearch
	 */          
	protected void _saisonCles(List<Long> l) {
		l.addAll(inscriptionRecherche.getQueryResponse().getFacetField("saisonCle_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: sessionKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire de la session dans la base de données. 
	 * Description.enUS: The primary key of the school enrollment in the database. 
	 * NomAffichage.frFR: sessions
	 * NomAffichage.enUS: sessions
	 * r: sessionCle
	 * r.enUS: sessionKey
	 * r: inscriptionRecherche
	 * r.enUS: enrollmentSearch
	 */          
	protected void _sessionCles(List<Long> l) {
		l.addAll(inscriptionRecherche.getQueryResponse().getFacetField("sessionCle_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: ageKeys
	 * Indexe: true
	 * Stocke: true
	 * Description.frFR: La clé primaire de l'âge dans la base de données. 
	 * Description.enUS: The primary key of the age in the database. 
	 * NomAffichage.frFR: âges
	 * NomAffichage.enUS: ages
	 * r: ageCle
	 * r.enUS: ageKey
	 * r: inscriptionRecherche
	 * r.enUS: enrollmentSearch
	 */                  
	protected void _ageCles(List<Long> l) {
		l.addAll(inscriptionRecherche.getQueryResponse().getFacetField("ageCle_indexed_long").getValues().stream().map(o -> Long.parseLong(o.getName())).collect(Collectors.toList()));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personFirstName
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: prénom
	 * NomAffichage.enUS: first name
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 1
	 */                   
	protected void _personnePrenom(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: familyName
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: nom de famille
	 * NomAffichage.enUS: last name
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 1
	 */                   
	protected void _familleNom(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personCompleteName
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: nom complèt
	 * NomAffichage.enUS: complete name
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 1
	 * r: personnePrenom
	 * r.enUS: personFirstName
	 * r: familleNom
	 * r.enUS: familyName
	 */                   
	protected void _personneNomComplet(Couverture<String> c) {
		c.o(String.format("%s %s", personnePrenom, familleNom));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personCompleteNamePreferred
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: nom complèt préferé
	 * NomAffichage.enUS: complete name preferred
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 1
	 * r: personnePrenom
	 * r.enUS: personFirstName
	 * r: familleNom
	 * r.enUS: familyName
	 */                   
	protected void _personneNomCompletPrefere(Couverture<String> c) {
		c.o(String.format("%s %s", personnePrenom, familleNom));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personFormalName
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: nom formel
	 * NomAffichage.enUS: formal name
	 * Definir: true
	 * HtmlLigne: 3
	 * HtmlCellule: 1
	 * r: personnePrenom
	 * r.enUS: personFirstName
	 * r: familleNom
	 * r.enUS: familyName
	 */                   
	protected void _personneNomFormel(Couverture<String> c) {
		c.o(String.format("%s %s", personnePrenom, familleNom));
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personOccupation
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: occupation
	 * NomAffichage.enUS: occupation
	 */                   
	protected void _personneOccupation(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personPhoneNumber
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: numéro de téléphone
	 * NomAffichage.enUS: phone number
	 */                   
	protected void _personneNumeroTelephone(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personEmail
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: mail
	 * NomAffichage.enUS: email
	 */                   
	protected void _personneMail(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personRelation
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: relation
	 * NomAffichage.enUS: relation
	 */                   
	protected void _personneRelation(Couverture<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personSms
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: text me
	 * NomAffichage.enUS: envoyer SMS
	 */                   
	protected void _personneSms(Couverture<Boolean> c) {
		c.o(true);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personReceiveEmail
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: recevoir des mails
	 * NomAffichage.enUS: receive email
	 */                   
	protected void _personneRecevoirMail(Couverture<Boolean> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personEmergencyContact
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: contacter en cas d'urgence
	 * NomAffichage.enUS: contact in case of emergency
	 */                  
	protected void _personneContactUrgence(Couverture<Boolean> c) {
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: personPickup
	 * Indexe: true
	 * Stocke: true
	 * NomAffichage.frFR: autorisé à venir chercher
	 * NomAffichage.enUS: authorized to pickup
	 */                   
	protected void _personneChercher(Couverture<Boolean> c) {
	}

	/**    
	 * {@inheritDoc}
	 * Var.enUS: guardianCompleteName
	 * Indexe: true
	 * Stocke: true
	 * VarTitre: true
	 * HtmlColonne: 1
	 * r: personneNomComplet
	 * r.enUS: personCompleteName
	 */  
	protected void _gardienNomComplet(Couverture<String> c) {
		c.o(personneNomComplet);
	}

	/**   
	 * {@inheritDoc}
	 * Var.enUS: guardianId
	 * Indexe: true
	 * Stocke: true
	 * VarId: true
	 * HtmlLigne: 1
	 * HtmlCellule: 4
	 * Description.frFR: 
	 * Description.enUS: 
	 * NomAffichage.frFR: ID
	 * NomAffichage.enUS: ID
	 * r: gardienNomComplet
	 * r.enUS: guardianCompleteName
	 */            
	protected void _gardienId(Couverture<String> c) {
		if(gardienNomComplet != null) {
			String s = Normalizer.normalize(gardienNomComplet, Normalizer.Form.NFD);
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
	 * r: gardienId
	 * r.enUS: guardianId
	 * r: /gardien/
	 * r.enUS: /chilc/
	 * r: requeteSite
	 * r.enUS: siteRequest
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: SiteUrlBase
	 * r.enUS: SiteBaseUrl
	 * **/   
	protected void _pageUrl(Couverture<String> c)  {
		if(gardienId != null) {
			String o = requeteSite_.getConfigSite_().getSiteUrlBase() + "/gardien/" + gardienId;
			c.o(o);
		}
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: objectSuggest
	 * Suggere: true
	 * r: gardienNomComplet
	 * r.enUS: guardianCompleteName
	 */         
	protected void _objetSuggere(Couverture<String> c) { 
		c.o(gardienNomComplet);
	}

	/**
	 * {@inheritDoc}
	 * Var.enUS: _classCanonicalNames
	 * Indexe: true
	 * Stocke: true
	 * r: GardienScolaire
	 * r.enUS: SchoolGuardian
	 * r: classeNomsCanoniques
	 * r.enUS: classCanonicalNames
	 **/      
	@Override protected void _classeNomsCanoniques(List<String> l) {
		l.add(GardienScolaire.class.getCanonicalName());
		super._classeNomsCanoniques(l);
	}
}