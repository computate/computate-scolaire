package org.computate.scolaire.frFR.inscription;

import org.computate.scolaire.frFR.config.ConfigSite;
import org.computate.scolaire.frFR.requete.RequeteSiteFrFR;
import org.computate.scolaire.frFR.contexte.SiteContexteFrFR;
import org.computate.scolaire.frFR.utilisateur.UtilisateurSite;
import org.computate.scolaire.frFR.recherche.ResultatRecherche;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import io.vertx.core.json.Json;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.commons.lang3.StringUtils;
import java.security.Principal;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.io.PrintWriter;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrDocument;
import java.util.Collection;
import java.math.BigDecimal;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Router;
import io.vertx.core.Vertx;
import io.vertx.ext.reactivestreams.ReactiveReadStream;
import io.vertx.ext.reactivestreams.ReactiveWriteStream;
import io.vertx.core.MultiMap;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.api.validation.HTTPRequestValidationHandler;
import io.vertx.ext.web.api.validation.ParameterTypeValidator;
import io.vertx.ext.web.api.validation.ValidationException;
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Timestamp;
import io.vertx.core.Future;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.core.CompositeFuture;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import org.apache.http.NameValuePair;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.auth.oauth2.KeycloakHelper;
import java.util.Optional;
import java.util.stream.Stream;
import java.net.URLDecoder;
import org.computate.scolaire.frFR.recherche.ListeRecherche;
import org.computate.scolaire.frFR.ecrivain.ToutEcrivain;


/**
 * Traduire: false
 **/
public class InscriptionScolaireFrFRGenApiServiceImpl implements InscriptionScolaireFrFRGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(InscriptionScolaireFrFRGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "InscriptionScolaireFrFRApiServiceImpl";

	protected SiteContexteFrFR siteContexte;

	public InscriptionScolaireFrFRGenApiServiceImpl(SiteContexteFrFR siteContexte) {
		this.siteContexte = siteContexte;
		InscriptionScolaireFrFRGenApiService service = InscriptionScolaireFrFRGenApiService.creerProxy(siteContexte.getVertx(), SERVICE_ADDRESS);
	}

	// POST //

	@Override
	public void postInscriptionScolaire(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourInscriptionScolaire(siteContexte, operationRequete, body);
			sqlInscriptionScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					creerPOSTInscriptionScolaire(requeteSite, b -> {
						if(b.succeeded()) {
							InscriptionScolaire inscriptionScolaire = b.result();
							sqlPOSTInscriptionScolaire(inscriptionScolaire, c -> {
								if(c.succeeded()) {
									definirInscriptionScolaire(inscriptionScolaire, d -> {
										if(d.succeeded()) {
											attribuerInscriptionScolaire(inscriptionScolaire, e -> {
												if(e.succeeded()) {
													indexerInscriptionScolaire(inscriptionScolaire, f -> {
														if(f.succeeded()) {
															reponse200POSTInscriptionScolaire(inscriptionScolaire, g -> {
																if(f.succeeded()) {
																	SQLConnection connexionSql = requeteSite.getConnexionSql();
																	connexionSql.commit(h -> {
																		if(a.succeeded()) {
																			connexionSql.close(i -> {
																				if(a.succeeded()) {
																					gestionnaireEvenements.handle(Future.succeededFuture(g.result()));
																				} else {
																					erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, i);
																				}
																			});
																		} else {
																			erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, h);
																		}
																	});
																} else {
																	erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, g);
																}
															});
														} else {
															erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, f);
														}
													});
												} else {
													erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, e);
												}
											});
										} else {
											erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurInscriptionScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void creerPOSTInscriptionScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<InscriptionScolaire>> gestionnaireEvenements) {
		try {
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			String utilisateurId = requeteSite.getUtilisateurId();

			connexionSql.queryWithParams(
					SiteContexteFrFR.SQL_creer
					, new JsonArray(Arrays.asList(InscriptionScolaire.class.getCanonicalName(), utilisateurId))
					, creerAsync
			-> {
				JsonArray creerLigne = creerAsync.result().getResults().stream().findFirst().orElseGet(() -> null);
				Long pk = creerLigne.getLong(0);
				InscriptionScolaire o = new InscriptionScolaire();
				o.setPk(pk);
				o.setRequeteSite_(requeteSite);
				gestionnaireEvenements.handle(Future.succeededFuture(o));
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void sqlPOSTInscriptionScolaire(InscriptionScolaire o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			JsonObject jsonObject = requeteSite.getObjetJson();
			StringBuilder postSql = new StringBuilder();
			List<Object> postSqlParams = new ArrayList<Object>();

			if(jsonObject != null) {
				Set<String> entiteVars = jsonObject.fieldNames();
				for(String entiteVar : entiteVars) {
					switch(entiteVar) {
					case "blocCles":
						postSql.append(SiteContexteFrFR.SQL_addA);
						postSqlParams.addAll(Arrays.asList("blocCles", pk, "inscriptionCles", jsonObject.getLong(entiteVar)));
						break;
					case "enfantCle":
						postSql.append(SiteContexteFrFR.SQL_addA);
						postSqlParams.addAll(Arrays.asList("enfantCle", pk, "inscriptionCles", jsonObject.getLong(entiteVar)));
						break;
					case "mereCles":
						postSql.append(SiteContexteFrFR.SQL_addA);
						postSqlParams.addAll(Arrays.asList("inscriptionCles", jsonObject.getLong(entiteVar), "mereCles", pk));
						break;
					case "pereCles":
						postSql.append(SiteContexteFrFR.SQL_addA);
						postSqlParams.addAll(Arrays.asList("inscriptionCles", jsonObject.getLong(entiteVar), "pereCles", pk));
						break;
					case "gardienCles":
						postSql.append(SiteContexteFrFR.SQL_addA);
						postSqlParams.addAll(Arrays.asList("gardienCles", pk, "inscriptionCles", jsonObject.getLong(entiteVar)));
						break;
					case "paiementCles":
						postSql.append(SiteContexteFrFR.SQL_addA);
						postSqlParams.addAll(Arrays.asList("inscriptionCles", jsonObject.getLong(entiteVar), "paiementCles", pk));
						break;
					case "blocHeureDebut":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("blocHeureDebut", jsonObject.getString(entiteVar), pk));
						break;
					case "blocHeureFin":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("blocHeureFin", jsonObject.getString(entiteVar), pk));
						break;
					case "blocPrixParMois":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("blocPrixParMois", jsonObject.getString(entiteVar), pk));
						break;
					case "inscriptionApprouve":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("inscriptionApprouve", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "inscriptionImmunisations":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("inscriptionImmunisations", jsonObject.getBoolean(entiteVar), pk));
						break;
					}
				}
			}
			connexionSql.updateWithParams(
					postSql.toString()
					, new JsonArray(postSqlParams)
					, postAsync
			-> {
				gestionnaireEvenements.handle(Future.succeededFuture());
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void reponse200POSTInscriptionScolaire(InscriptionScolaire o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(json)));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchInscriptionScolaire(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourInscriptionScolaire(siteContexte, operationRequete, body);
			sqlInscriptionScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurInscriptionScolaire(requeteSite, b -> {
						if(b.succeeded()) {
							rechercheInscriptionScolaire(requeteSite, false, true, null, c -> {
								if(c.succeeded()) {
									ListeRecherche<InscriptionScolaire> listeInscriptionScolaire = c.result();
									listePATCHInscriptionScolaire(listeInscriptionScolaire, d -> {
										if(d.succeeded()) {
											SQLConnection connexionSql = requeteSite.getConnexionSql();
											if(connexionSql == null) {
												gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
											} else {
												connexionSql.commit(e -> {
													if(e.succeeded()) {
														connexionSql.close(f -> {
															if(f.succeeded()) {
																gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
															} else {
																erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, f);
															}
														});
													} else {
														erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, e);
													}
												});
											}
										} else {
											erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurInscriptionScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void listePATCHInscriptionScolaire(ListeRecherche<InscriptionScolaire> listeInscriptionScolaire, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
			RequeteSiteFrFR requeteSite = listeInscriptionScolaire.getRequeteSite_();
		listeInscriptionScolaire.getList().forEach(o -> {
			futures.add(
				futurePATCHInscriptionScolaire(o, a -> {
					if(a.succeeded()) {
					} else {
						erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				reponse200PATCHInscriptionScolaire(listeInscriptionScolaire, gestionnaireEvenements);
			} else {
				erreurInscriptionScolaire(listeInscriptionScolaire.getRequeteSite_(), gestionnaireEvenements, a);
			}
		});
	}

	public Future<InscriptionScolaire> futurePATCHInscriptionScolaire(InscriptionScolaire o,  Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		Future<InscriptionScolaire> future = Future.future();
		try {
			sqlPATCHInscriptionScolaire(o, a -> {
				if(a.succeeded()) {
					InscriptionScolaire inscriptionScolaire = a.result();
					definirInscriptionScolaire(inscriptionScolaire, b -> {
						if(b.succeeded()) {
							attribuerInscriptionScolaire(inscriptionScolaire, c -> {
								if(c.succeeded()) {
									indexerInscriptionScolaire(inscriptionScolaire, d -> {
										if(d.succeeded()) {
											future.complete(o);
											gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
										} else {
											erreurInscriptionScolaire(o.getRequeteSite_(), gestionnaireEvenements, d);
										}
									});
								} else {
									erreurInscriptionScolaire(o.getRequeteSite_(), gestionnaireEvenements, c);
								}
							});
						} else {
							erreurInscriptionScolaire(o.getRequeteSite_(), gestionnaireEvenements, b);
						}
					});
				} else {
					erreurInscriptionScolaire(o.getRequeteSite_(), gestionnaireEvenements, a);
				}
			});
			return future;
		} catch(Exception e) {
			return Future.failedFuture(e);
		}
	}

	public void sqlPATCHInscriptionScolaire(InscriptionScolaire o, Handler<AsyncResult<InscriptionScolaire>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			JsonObject requeteJson = requeteSite.getObjetJson();
			StringBuilder patchSql = new StringBuilder();
			List<Object> patchSqlParams = new ArrayList<Object>();
			Set<String> methodeNoms = requeteJson.fieldNames();
			InscriptionScolaire o2 = new InscriptionScolaire();

			patchSql.append(SiteContexteFrFR.SQL_modifier);
			patchSqlParams.addAll(Arrays.asList(pk, "org.computate.scolaire.frFR.inscription.InscriptionScolaire"));
			for(String methodeNom : methodeNoms) {
				switch(methodeNom) {
					case "setCree":
						o2.setCree(requeteJson.getString(methodeNom));
						if(o2.getCree() == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "cree"));
						} else {
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("cree", o2.jsonCree(), pk));
						}
						break;
					case "setModifie":
						o2.setModifie(requeteJson.getString(methodeNom));
						if(o2.getModifie() == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "modifie"));
						} else {
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("modifie", o2.jsonModifie(), pk));
						}
						break;
					case "setArchive":
						o2.setArchive(requeteJson.getBoolean(methodeNom));
						if(o2.getArchive() == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "archive"));
						} else {
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("archive", o2.jsonArchive(), pk));
						}
						break;
					case "setSupprime":
						o2.setSupprime(requeteJson.getBoolean(methodeNom));
						if(o2.getSupprime() == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "supprime"));
						} else {
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("supprime", o2.jsonSupprime(), pk));
						}
						break;
					case "addBlocCles":
						patchSql.append(SiteContexteFrFR.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("blocCles", pk, "inscriptionCles", requeteJson.getString(methodeNom)));
						break;
					case "addAllBlocCles":
						JsonArray addAllBlocClesValeurs = requeteJson.getJsonArray(methodeNom);
						for(Integer i = 0; i <  addAllBlocClesValeurs.size(); i++) {
							patchSql.append(SiteContexteFrFR.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("blocCles", pk, "inscriptionCles", addAllBlocClesValeurs.getString(i)));
						}
						break;
					case "setBlocCles":
						JsonArray setBlocClesValeurs = requeteJson.getJsonArray(methodeNom);
						patchSql.append(SiteContexteFrFR.SQL_clearA1);
						patchSqlParams.addAll(Arrays.asList("blocCles", pk, "inscriptionCles", requeteJson.getJsonArray(methodeNom)));
						for(Integer i = 0; i <  setBlocClesValeurs.size(); i++) {
							patchSql.append(SiteContexteFrFR.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("blocCles", pk, "inscriptionCles", setBlocClesValeurs.getString(i)));
						}
						break;
					case "removeBlocCles":
						patchSql.append(SiteContexteFrFR.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("blocCles", pk, "inscriptionCles", requeteJson.getLong(methodeNom)));
						break;
					case "setEnfantCle":
						o2.setEnfantCle(requeteJson.getString(methodeNom));
						patchSql.append(SiteContexteFrFR.SQL_setA1);
						patchSqlParams.addAll(Arrays.asList("enfantCle", pk, "inscriptionCles", o2.getEnfantCle()));
						break;
					case "removeEnfantCle":
						o2.setEnfantCle(requeteJson.getString(methodeNom));
						patchSql.append(SiteContexteFrFR.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enfantCle", pk, "inscriptionCles", o2.getEnfantCle()));
						break;
					case "addMereCles":
						patchSql.append(SiteContexteFrFR.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("inscriptionCles", requeteJson.getString(methodeNom), "mereCles", pk));
						break;
					case "addAllMereCles":
						JsonArray addAllMereClesValeurs = requeteJson.getJsonArray(methodeNom);
						for(Integer i = 0; i <  addAllMereClesValeurs.size(); i++) {
							patchSql.append(SiteContexteFrFR.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("inscriptionCles", addAllMereClesValeurs.getString(i), "mereCles", pk));
						}
						break;
					case "setMereCles":
						JsonArray setMereClesValeurs = requeteJson.getJsonArray(methodeNom);
						patchSql.append(SiteContexteFrFR.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("inscriptionCles", requeteJson.getString(methodeNom), "mereCles", pk));
						for(Integer i = 0; i <  setMereClesValeurs.size(); i++) {
							patchSql.append(SiteContexteFrFR.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("inscriptionCles", setMereClesValeurs.getString(i), "mereCles", pk));
						}
						break;
					case "removeMereCles":
						patchSql.append(SiteContexteFrFR.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("inscriptionCles", requeteJson.getLong(methodeNom), "mereCles", pk));
						break;
					case "addPereCles":
						patchSql.append(SiteContexteFrFR.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("inscriptionCles", requeteJson.getString(methodeNom), "pereCles", pk));
						break;
					case "addAllPereCles":
						JsonArray addAllPereClesValeurs = requeteJson.getJsonArray(methodeNom);
						for(Integer i = 0; i <  addAllPereClesValeurs.size(); i++) {
							patchSql.append(SiteContexteFrFR.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("inscriptionCles", addAllPereClesValeurs.getString(i), "pereCles", pk));
						}
						break;
					case "setPereCles":
						JsonArray setPereClesValeurs = requeteJson.getJsonArray(methodeNom);
						patchSql.append(SiteContexteFrFR.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("inscriptionCles", requeteJson.getString(methodeNom), "pereCles", pk));
						for(Integer i = 0; i <  setPereClesValeurs.size(); i++) {
							patchSql.append(SiteContexteFrFR.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("inscriptionCles", setPereClesValeurs.getString(i), "pereCles", pk));
						}
						break;
					case "removePereCles":
						patchSql.append(SiteContexteFrFR.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("inscriptionCles", requeteJson.getLong(methodeNom), "pereCles", pk));
						break;
					case "addGardienCles":
						patchSql.append(SiteContexteFrFR.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("gardienCles", pk, "inscriptionCles", requeteJson.getString(methodeNom)));
						break;
					case "addAllGardienCles":
						JsonArray addAllGardienClesValeurs = requeteJson.getJsonArray(methodeNom);
						for(Integer i = 0; i <  addAllGardienClesValeurs.size(); i++) {
							patchSql.append(SiteContexteFrFR.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("gardienCles", pk, "inscriptionCles", addAllGardienClesValeurs.getString(i)));
						}
						break;
					case "setGardienCles":
						JsonArray setGardienClesValeurs = requeteJson.getJsonArray(methodeNom);
						patchSql.append(SiteContexteFrFR.SQL_clearA1);
						patchSqlParams.addAll(Arrays.asList("gardienCles", pk, "inscriptionCles", requeteJson.getJsonArray(methodeNom)));
						for(Integer i = 0; i <  setGardienClesValeurs.size(); i++) {
							patchSql.append(SiteContexteFrFR.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("gardienCles", pk, "inscriptionCles", setGardienClesValeurs.getString(i)));
						}
						break;
					case "removeGardienCles":
						patchSql.append(SiteContexteFrFR.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("gardienCles", pk, "inscriptionCles", requeteJson.getLong(methodeNom)));
						break;
					case "addPaiementCles":
						patchSql.append(SiteContexteFrFR.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("inscriptionCles", requeteJson.getString(methodeNom), "paiementCles", pk));
						break;
					case "addAllPaiementCles":
						JsonArray addAllPaiementClesValeurs = requeteJson.getJsonArray(methodeNom);
						for(Integer i = 0; i <  addAllPaiementClesValeurs.size(); i++) {
							patchSql.append(SiteContexteFrFR.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("inscriptionCles", addAllPaiementClesValeurs.getString(i), "paiementCles", pk));
						}
						break;
					case "setPaiementCles":
						JsonArray setPaiementClesValeurs = requeteJson.getJsonArray(methodeNom);
						patchSql.append(SiteContexteFrFR.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("inscriptionCles", requeteJson.getString(methodeNom), "paiementCles", pk));
						for(Integer i = 0; i <  setPaiementClesValeurs.size(); i++) {
							patchSql.append(SiteContexteFrFR.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("inscriptionCles", setPaiementClesValeurs.getString(i), "paiementCles", pk));
						}
						break;
					case "removePaiementCles":
						patchSql.append(SiteContexteFrFR.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("inscriptionCles", requeteJson.getLong(methodeNom), "paiementCles", pk));
						break;
					case "setBlocHeureDebut":
						o2.setBlocHeureDebut(requeteJson.getString(methodeNom));
						if(o2.getBlocHeureDebut() == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "blocHeureDebut"));
						} else {
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("blocHeureDebut", o2.jsonBlocHeureDebut(), pk));
						}
						break;
					case "setBlocHeureFin":
						o2.setBlocHeureFin(requeteJson.getString(methodeNom));
						if(o2.getBlocHeureFin() == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "blocHeureFin"));
						} else {
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("blocHeureFin", o2.jsonBlocHeureFin(), pk));
						}
						break;
					case "setBlocPrixParMois":
						o2.setBlocPrixParMois(requeteJson.getString(methodeNom));
						if(o2.getBlocPrixParMois() == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "blocPrixParMois"));
						} else {
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("blocPrixParMois", o2.jsonBlocPrixParMois(), pk));
						}
						break;
					case "setInscriptionApprouve":
						o2.setInscriptionApprouve(requeteJson.getBoolean(methodeNom));
						if(o2.getInscriptionApprouve() == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "inscriptionApprouve"));
						} else {
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("inscriptionApprouve", o2.jsonInscriptionApprouve(), pk));
						}
						break;
					case "setInscriptionImmunisations":
						o2.setInscriptionImmunisations(requeteJson.getBoolean(methodeNom));
						if(o2.getInscriptionImmunisations() == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "inscriptionImmunisations"));
						} else {
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("inscriptionImmunisations", o2.jsonInscriptionImmunisations(), pk));
						}
						break;
				}
			}
			connexionSql.updateWithParams(
					patchSql.toString()
					, new JsonArray(patchSqlParams)
					, patchAsync
			-> {
				InscriptionScolaire o3 = new InscriptionScolaire();
				o3.setRequeteSite_(o.getRequeteSite_());
				o3.setPk(pk);
				gestionnaireEvenements.handle(Future.succeededFuture(o3));
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void reponse200PATCHInscriptionScolaire(ListeRecherche<InscriptionScolaire> listeInscriptionScolaire, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listeInscriptionScolaire.getRequeteSite_();
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(json)));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getInscriptionScolaire(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourInscriptionScolaire(siteContexte, operationRequete);
			rechercheInscriptionScolaire(requeteSite, false, true, null, a -> {
				if(a.succeeded()) {
					ListeRecherche<InscriptionScolaire> listeInscriptionScolaire = a.result();
					reponse200GETInscriptionScolaire(listeInscriptionScolaire, b -> {
						if(b.succeeded()) {
							gestionnaireEvenements.handle(Future.succeededFuture(b.result()));
						} else {
							erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurInscriptionScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void reponse200GETInscriptionScolaire(ListeRecherche<InscriptionScolaire> listeInscriptionScolaire, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listeInscriptionScolaire.getRequeteSite_();
			SolrDocumentList documentsSolr = listeInscriptionScolaire.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listeInscriptionScolaire.get(0));
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(json)));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// DELETE //

	@Override
	public void deleteInscriptionScolaire(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourInscriptionScolaire(siteContexte, operationRequete);
			sqlInscriptionScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					rechercheInscriptionScolaire(requeteSite, false, true, null, b -> {
						if(b.succeeded()) {
							ListeRecherche<InscriptionScolaire> listeInscriptionScolaire = b.result();
							supprimerDELETEInscriptionScolaire(requeteSite, c -> {
								if(c.succeeded()) {
									reponse200DELETEInscriptionScolaire(requeteSite, d -> {
										if(d.succeeded()) {
											SQLConnection connexionSql = requeteSite.getConnexionSql();
											if(connexionSql == null) {
												gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
											} else {
												connexionSql.commit(e -> {
													if(e.succeeded()) {
														connexionSql.close(f -> {
															if(f.succeeded()) {
																gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
															} else {
																erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, f);
															}
														});
													} else {
														erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, e);
													}
												});
											}
										} else {
											erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurInscriptionScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void supprimerDELETEInscriptionScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			String utilisateurId = requeteSite.getUtilisateurId();
			Long pk = requeteSite.getRequetePk();

			connexionSql.updateWithParams(
					SiteContexteFrFR.SQL_supprimer
					, new JsonArray(Arrays.asList(pk, InscriptionScolaire.class.getCanonicalName(), pk, pk, pk, pk))
					, supprimerAsync
			-> {
				gestionnaireEvenements.handle(Future.succeededFuture());
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void reponse200DELETEInscriptionScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(json)));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// Recherche //

	@Override
	public void rechercheInscriptionScolaire(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourInscriptionScolaire(siteContexte, operationRequete);
			rechercheInscriptionScolaire(requeteSite, false, true, null, a -> {
				if(a.succeeded()) {
					ListeRecherche<InscriptionScolaire> listeInscriptionScolaire = a.result();
					reponse200RechercheInscriptionScolaire(listeInscriptionScolaire, b -> {
						if(b.succeeded()) {
							gestionnaireEvenements.handle(Future.succeededFuture(b.result()));
						} else {
							erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurInscriptionScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void reponse200RechercheInscriptionScolaire(ListeRecherche<InscriptionScolaire> listeInscriptionScolaire, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listeInscriptionScolaire.getRequeteSite_();
			QueryResponse reponseRecherche = listeInscriptionScolaire.getQueryResponse();
			SolrDocumentList documentsSolr = listeInscriptionScolaire.getSolrDocumentList();
			Long millisRecherche = Long.valueOf(reponseRecherche.getQTime());
			Long millisTransmission = reponseRecherche.getElapsedTime();
			Long numCommence = reponseRecherche.getResults().getStart();
			Long numTrouve = reponseRecherche.getResults().getNumFound();
			Integer numRetourne = reponseRecherche.getResults().size();
			String tempsRecherche = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(millisRecherche), TimeUnit.MILLISECONDS.toMillis(millisRecherche) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(millisRecherche)));
			String tempsTransmission = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(millisTransmission), TimeUnit.MILLISECONDS.toMillis(millisTransmission) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(millisTransmission)));
			Exception exceptionRecherche = reponseRecherche.getException();

			JsonObject json = new JsonObject();
			json.put("numCommence", numCommence);
			json.put("numTrouve", numTrouve);
			json.put("numRetourne", numRetourne);
			json.put("tempsRecherche", tempsRecherche);
			json.put("tempsTransmission", tempsTransmission);
			JsonArray l = new JsonArray();
			listeInscriptionScolaire.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listeInscriptionScolaire.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					for(String fieldName : fieldNames) {
						if(!fls.contains(fieldName))
							json2.remove(fieldName);
					}
				}
				l.add(json2);
			});
			json.put("liste", l);
			if(exceptionRecherche != null) {
				json.put("exceptionRecherche", exceptionRecherche.getMessage());
			}
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(json)));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PageRecherche //

	@Override
	public void pagerechercheInscriptionScolaireId(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		pagerechercheInscriptionScolaire(operationRequete, gestionnaireEvenements);
	}

	@Override
	public void pagerechercheInscriptionScolaire(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourInscriptionScolaire(siteContexte, operationRequete);
			sqlInscriptionScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurInscriptionScolaire(requeteSite, b -> {
						if(b.succeeded()) {
							rechercheInscriptionScolaire(requeteSite, false, true, "/inscription", c -> {
								if(c.succeeded()) {
									ListeRecherche<InscriptionScolaire> listeInscriptionScolaire = c.result();
									reponse200PageRechercheInscriptionScolaire(listeInscriptionScolaire, d -> {
										if(d.succeeded()) {
											SQLConnection connexionSql = requeteSite.getConnexionSql();
											if(connexionSql == null) {
												gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
											} else {
												connexionSql.commit(e -> {
													if(e.succeeded()) {
														connexionSql.close(f -> {
															if(f.succeeded()) {
																gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
															} else {
																erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, f);
															}
														});
													} else {
														erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, e);
													}
												});
											}
										} else {
											erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurInscriptionScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurInscriptionScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void reponse200PageRechercheInscriptionScolaire(ListeRecherche<InscriptionScolaire> listeInscriptionScolaire, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listeInscriptionScolaire.getRequeteSite_();
			Buffer buffer = Buffer.buffer();
			ToutEcrivain w = ToutEcrivain.creer(listeInscriptionScolaire.getRequeteSite_(), buffer);
			InscriptionPage page = new InscriptionPage();
			SolrDocument pageDocumentSolr = new SolrDocument();

			pageDocumentSolr.setField("pageUri_frFR_stored_string", "/inscription");
			page.setPageDocumentSolr(pageDocumentSolr);
			page.setW(w);
			page.setListeInscriptionScolaire(listeInscriptionScolaire);
			page.initLoinInscriptionPage(requeteSite);
			page.html();
			gestionnaireEvenements.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, new CaseInsensitiveHeaders())));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public String varIndexeInscriptionScolaire(String entiteVar) {
		switch(entiteVar) {
			case "pk":
				return "pk_indexed_long";
			case "id":
				return "id_indexed_string";
			case "cree":
				return "cree_indexed_date";
			case "modifie":
				return "modifie_indexed_date";
			case "archive":
				return "archive_indexed_boolean";
			case "supprime":
				return "supprime_indexed_boolean";
			case "classeNomCanonique":
				return "classeNomCanonique_indexed_string";
			case "classeNomSimple":
				return "classeNomSimple_indexed_string";
			case "classeNomsCanoniques":
				return "classeNomsCanoniques_indexed_strings";
			case "ecoleCle":
				return "ecoleCle_indexed_long";
			case "saisonCle":
				return "saisonCle_indexed_long";
			case "sessionCle":
				return "sessionCle_indexed_long";
			case "ageCle":
				return "ageCle_indexed_long";
			case "inscriptionCle":
				return "inscriptionCle_indexed_long";
			case "blocCles":
				return "blocCles_indexed_longs";
			case "enfantCle":
				return "enfantCle_indexed_long";
			case "mereCles":
				return "mereCles_indexed_longs";
			case "pereCles":
				return "pereCles_indexed_longs";
			case "gardienCles":
				return "gardienCles_indexed_longs";
			case "paiementCles":
				return "paiementCles_indexed_longs";
			case "familleCle":
				return "familleCle_indexed_long";
			case "scolaireTri":
				return "scolaireTri_indexed_int";
			case "ecoleTri":
				return "ecoleTri_indexed_int";
			case "anneeTri":
				return "anneeTri_indexed_int";
			case "saisonTri":
				return "saisonTri_indexed_int";
			case "sessionTri":
				return "sessionTri_indexed_int";
			case "ageTri":
				return "ageTri_indexed_int";
			case "enfantNomComplet":
				return "enfantNomComplet_indexed_string";
			case "ecoleNomComplet":
				return "ecoleNomComplet_indexed_string";
			case "anneeDebut":
				return "anneeDebut_indexed_date";
			case "anneeFin":
				return "anneeFin_indexed_date";
			case "saisonJourDebut":
				return "saisonJourDebut_indexed_date";
			case "saisonEte":
				return "saisonEte_indexed_boolean";
			case "saisonHiver":
				return "saisonHiver_indexed_boolean";
			case "saisonFraisInscription":
				return "saisonFraisInscription_indexed_double";
			case "saisonNomComplet":
				return "saisonNomComplet_indexed_string";
			case "sessionJourDebut":
				return "sessionJourDebut_indexed_date";
			case "sessionJourFin":
				return "sessionJourFin_indexed_date";
			case "ageNomComplet":
				return "ageNomComplet_indexed_string";
			case "ageDebut":
				return "ageDebut_indexed_int";
			case "ageFin":
				return "ageFin_indexed_int";
			case "blocHeureDebut":
				return "blocHeureDebut_indexed_string";
			case "blocHeureFin":
				return "blocHeureFin_indexed_string";
			case "blocPrixParMois":
				return "blocPrixParMois_indexed_double";
			case "blocDimanche":
				return "blocDimanche_indexed_boolean";
			case "blocLundi":
				return "blocLundi_indexed_boolean";
			case "blocMardi":
				return "blocMardi_indexed_boolean";
			case "blocMercredi":
				return "blocMercredi_indexed_boolean";
			case "blocJeudi":
				return "blocJeudi_indexed_boolean";
			case "blocVendredi":
				return "blocVendredi_indexed_boolean";
			case "blocSamedi":
				return "blocSamedi_indexed_boolean";
			case "inscriptionApprouve":
				return "inscriptionApprouve_indexed_boolean";
			case "inscriptionImmunisations":
				return "inscriptionImmunisations_indexed_boolean";
			case "blocNomComplet":
				return "blocNomComplet_indexed_string";
			case "blocId":
				return "blocId_indexed_string";
			case "pageUrl":
				return "pageUrl_indexed_string";
			case "objetSuggere":
				return "objetSuggere_indexed_string";
			default:
				throw new RuntimeException(String.format("\"%s\" n'est pas une entité indexé. ", entiteVar));
		}
	}

	public String varRechercheInscriptionScolaire(String entiteVar) {
		switch(entiteVar) {
			case "objetSuggere":
				return "objetSuggere_suggested";
			default:
				throw new RuntimeException(String.format("\"%s\" n'est pas une entité indexé. ", entiteVar));
		}
	}

	public String varSuggereInscriptionScolaire(String entiteVar) {
		switch(entiteVar) {
			case "objetSuggere":
				return "objetSuggere_suggested";
			default:
				throw new RuntimeException(String.format("\"%s\" n'est pas une entité indexé. ", entiteVar));
		}
	}

	// Partagé //

	public void erreurInscriptionScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements, AsyncResult<?> resultatAsync) {
		Throwable e = resultatAsync.cause();
		ExceptionUtils.printRootCauseStackTrace(e);
		OperationResponse reponseOperation = new OperationResponse(400, "BAD REQUEST", 
			Buffer.buffer().appendString(
				new JsonObject() {{
					put("erreur", new JsonObject() {{
					put("message", e.getMessage());
					}});
				}}.encodePrettily()
			)
			, new CaseInsensitiveHeaders()
		);
		if(requeteSite != null) {
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			if(connexionSql != null) {
				connexionSql.rollback(a -> {
					if(a.succeeded()) {
						connexionSql.close(b -> {
							if(a.succeeded()) {
								gestionnaireEvenements.handle(Future.succeededFuture(reponseOperation));
							} else {
								gestionnaireEvenements.handle(Future.succeededFuture(reponseOperation));
							}
						});
					} else {
						gestionnaireEvenements.handle(Future.succeededFuture(reponseOperation));
					}
				});
			} else {
				gestionnaireEvenements.handle(Future.succeededFuture(reponseOperation));
			}
		} else {
			gestionnaireEvenements.handle(Future.succeededFuture(reponseOperation));
		}
	}

	public void sqlInscriptionScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			SQLClient clientSql = requeteSite.getSiteContexte_().getClientSql();

			if(clientSql == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				clientSql.getConnection(sqlAsync -> {
					if(sqlAsync.succeeded()) {
						SQLConnection connexionSql = sqlAsync.result();
						connexionSql.setAutoCommit(false, a -> {
							if(a.succeeded()) {
								requeteSite.setConnexionSql(connexionSql);
								gestionnaireEvenements.handle(Future.succeededFuture());
							} else {
								gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
							}
						});
					} else {
						gestionnaireEvenements.handle(Future.failedFuture(sqlAsync.cause()));
					}
				});
			}
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public RequeteSiteFrFR genererRequeteSiteFrFRPourInscriptionScolaire(SiteContexteFrFR siteContexte, OperationRequest operationRequete) {
		return genererRequeteSiteFrFRPourInscriptionScolaire(siteContexte, operationRequete, null);
	}

	public RequeteSiteFrFR genererRequeteSiteFrFRPourInscriptionScolaire(SiteContexteFrFR siteContexte, OperationRequest operationRequete, JsonObject body) {
		Vertx vertx = siteContexte.getVertx();
		RequeteSiteFrFR requeteSite = new RequeteSiteFrFR();
		requeteSite.setObjetJson(body);
		requeteSite.setVertx(vertx);
		requeteSite.setSiteContexte_(siteContexte);
		requeteSite.setConfigSite_(siteContexte.getConfigSite());
		requeteSite.setOperationRequete(operationRequete);
		requeteSite.initLoinRequeteSiteFrFR(requeteSite);

		return requeteSite;
	}

	public void utilisateurInscriptionScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			String utilisateurId = requeteSite.getUtilisateurId();
			if(utilisateurId == null) {
				gestionnaireEvenements.handle(Future.succeededFuture());
			} else {
				connexionSql.queryWithParams(
						SiteContexteFrFR.SQL_selectC
						, new JsonArray(Arrays.asList("org.computate.scolaire.frFR.utilisateur.UtilisateurSite", utilisateurId))
						, selectCAsync
				-> {
					if(selectCAsync.succeeded()) {
						JsonArray utilisateurValeurs = selectCAsync.result().getResults().stream().findFirst().orElse(null);
						if(utilisateurValeurs == null) {
							connexionSql.queryWithParams(
									SiteContexteFrFR.SQL_creer
									, new JsonArray(Arrays.asList("org.computate.scolaire.frFR.utilisateur.UtilisateurSite", utilisateurId))
									, creerAsync
							-> {
								JsonArray creerLigne = creerAsync.result().getResults().stream().findFirst().orElseGet(() -> null);
								Long pkUtilisateur = creerLigne.getLong(0);
								UtilisateurSite utilisateurSite = new UtilisateurSite();
								utilisateurSite.setPk(pkUtilisateur);

								connexionSql.queryWithParams(
										SiteContexteFrFR.SQL_definir
										, new JsonArray(Arrays.asList(pkUtilisateur, pkUtilisateur, pkUtilisateur))
										, definirAsync
								-> {
									if(definirAsync.succeeded()) {
										try {
											for(JsonArray definition : definirAsync.result().getResults()) {
												utilisateurSite.definirPourClasse(definition.getString(0), definition.getString(1));
											}
											JsonObject utilisateurVertx = requeteSite.getOperationRequete().getUser();
											JsonObject principalJson = KeycloakHelper.parseToken(utilisateurVertx.getString("access_token"));
											utilisateurSite.setUtilisateurNom(principalJson.getString("preferred_username"));
											utilisateurSite.setUtilisateurPrenom(principalJson.getString("given_name"));
											utilisateurSite.setUtilisateurNomFamille(principalJson.getString("family_name"));
											utilisateurSite.setUtilisateurId(principalJson.getString("sub"));
											utilisateurSite.initLoinPourClasse(requeteSite);
											utilisateurSite.indexerPourClasse();
											requeteSite.setUtilisateurSite(utilisateurSite);
											requeteSite.setUtilisateurNom(principalJson.getString("preferred_username"));
											requeteSite.setUtilisateurPrenom(principalJson.getString("given_name"));
											requeteSite.setUtilisateurNomFamille(principalJson.getString("family_name"));
											requeteSite.setUtilisateurId(principalJson.getString("sub"));
											gestionnaireEvenements.handle(Future.succeededFuture());
										} catch(Exception e) {
											gestionnaireEvenements.handle(Future.failedFuture(e));
										}
									} else {
										gestionnaireEvenements.handle(Future.failedFuture(definirAsync.cause()));
									}
								});
							});
						} else {
							Long pkUtilisateur = utilisateurValeurs.getLong(0);
							UtilisateurSite utilisateurSite = new UtilisateurSite();
							utilisateurSite.setPk(pkUtilisateur);

							connexionSql.queryWithParams(
									SiteContexteFrFR.SQL_definir
									, new JsonArray(Arrays.asList(pkUtilisateur, pkUtilisateur, pkUtilisateur))
									, definirAsync
							-> {
								if(definirAsync.succeeded()) {
									for(JsonArray definition : definirAsync.result().getResults()) {
										utilisateurSite.definirPourClasse(definition.getString(0), definition.getString(1));
									}
									JsonObject utilisateurVertx = requeteSite.getOperationRequete().getUser();
									JsonObject principalJson = KeycloakHelper.parseToken(utilisateurVertx.getString("access_token"));
									utilisateurSite.setUtilisateurNom(principalJson.getString("preferred_username"));
									utilisateurSite.setUtilisateurPrenom(principalJson.getString("given_name"));
									utilisateurSite.setUtilisateurNomFamille(principalJson.getString("family_name"));
									utilisateurSite.setUtilisateurId(principalJson.getString("sub"));
									utilisateurSite.initLoinPourClasse(requeteSite);
									utilisateurSite.indexerPourClasse();
									requeteSite.setUtilisateurSite(utilisateurSite);
									requeteSite.setUtilisateurNom(principalJson.getString("preferred_username"));
									requeteSite.setUtilisateurPrenom(principalJson.getString("given_name"));
									requeteSite.setUtilisateurNomFamille(principalJson.getString("family_name"));
									requeteSite.setUtilisateurId(principalJson.getString("sub"));
									gestionnaireEvenements.handle(Future.succeededFuture());
								} else {
									gestionnaireEvenements.handle(Future.failedFuture(definirAsync.cause()));
								}
							});
						}
					} else {
						gestionnaireEvenements.handle(Future.failedFuture(selectCAsync.cause()));
					}
				});
			}
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void rechercheInscriptionScolaire(RequeteSiteFrFR requeteSite, Boolean peupler, Boolean stocker, String classeApiUriMethode, Handler<AsyncResult<ListeRecherche<InscriptionScolaire>>> gestionnaireEvenements) {
		try {
			OperationRequest operationRequete = requeteSite.getOperationRequete();
			String entiteListeStr = requeteSite.getOperationRequete().getParams().getJsonObject("query").getString("fl");
			String[] entiteListe = entiteListeStr == null ? null : entiteListeStr.split(",\\s*");
			ListeRecherche<InscriptionScolaire> listeRecherche = new ListeRecherche<InscriptionScolaire>();
			listeRecherche.setPeupler(peupler);
			listeRecherche.setStocker(stocker);
			listeRecherche.setQuery("*:*");
			listeRecherche.setC(InscriptionScolaire.class);
			if(entiteListe != null)
				listeRecherche.addFields(entiteListe);
			listeRecherche.addSort("archive_indexed_boolean", ORDER.asc);
			listeRecherche.addSort("supprime_indexed_boolean", ORDER.asc);
			listeRecherche.addFilterQuery("classeNomsCanoniques_indexed_strings:" + ClientUtils.escapeQueryChars("org.computate.scolaire.frFR.inscription.InscriptionScolaire"));
			UtilisateurSite utilisateurSite = requeteSite.getUtilisateurSite();
			if(utilisateurSite != null && !utilisateurSite.getVoirSupprime())
				listeRecherche.addFilterQuery("supprime_indexed_boolean:false");
			if(utilisateurSite != null && !utilisateurSite.getVoirArchive())
				listeRecherche.addFilterQuery("archive_indexed_boolean:false");

			String id = operationRequete.getParams().getJsonObject("path").getString("id");
			if(id != null) {
				listeRecherche.addFilterQuery("(id:" + ClientUtils.escapeQueryChars(id) + " OR blocId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
			}

			operationRequete.getParams().getJsonObject("query").forEach(paramRequete -> {
				String entiteVar = null;
				String valeurIndexe = null;
				String varIndexe = null;
				String valeurTri = null;
				Integer rechercheDebut = null;
				Integer rechercheNum = null;
				String paramNom = paramRequete.getKey();
				Object paramValeursObjet = paramRequete.getValue();
				JsonArray paramObjets = paramValeursObjet instanceof JsonArray ? (JsonArray)paramValeursObjet : new JsonArray().add(paramValeursObjet);

				try {
					for(Object paramObjet : paramObjets) {
						switch(paramNom) {
							case "q":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								varIndexe = "*".equals(entiteVar) ? entiteVar : varRechercheInscriptionScolaire(entiteVar);
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								valeurIndexe = StringUtils.isEmpty(valeurIndexe) ? "*" : valeurIndexe;
								listeRecherche.setQuery(varIndexe + ":" + ("*".equals(valeurIndexe) ? valeurIndexe : ClientUtils.escapeQueryChars(valeurIndexe)));
								if(!"*".equals(entiteVar)) {
									listeRecherche.setHighlight(true);
									listeRecherche.setHighlightSnippets(3);
									listeRecherche.addHighlightField(varIndexe);
									listeRecherche.setParam("hl.encoder", "html");
								}
								break;
							case "fq":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, ":"));
								valeurIndexe = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObjet, ":")), "UTF-8");
								varIndexe = varIndexeInscriptionScolaire(entiteVar);
								listeRecherche.addFilterQuery(varIndexe + ":" + ClientUtils.escapeQueryChars(valeurIndexe));
								break;
							case "sort":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, " "));
								valeurTri = StringUtils.trim(StringUtils.substringAfter((String)paramObjet, " "));
								varIndexe = varIndexeInscriptionScolaire(entiteVar);
								listeRecherche.addSort(varIndexe, ORDER.valueOf(valeurTri));
								break;
							case "start":
								rechercheDebut = (Integer)paramObjet;
								listeRecherche.setStart(rechercheDebut);
								break;
							case "rows":
								rechercheNum = (Integer)paramObjet;
								listeRecherche.setRows(rechercheNum);
								break;
						}
					}
				} catch(Exception e) {
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
			listeRecherche.initLoinPourClasse(requeteSite);
			gestionnaireEvenements.handle(Future.succeededFuture(listeRecherche));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void definirInscriptionScolaire(InscriptionScolaire o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			connexionSql.queryWithParams(
					SiteContexteFrFR.SQL_definir
					, new JsonArray(Arrays.asList(pk, pk, pk))
					, definirAsync
			-> {
				if(definirAsync.succeeded()) {
					try {
						for(JsonArray definition : definirAsync.result().getResults()) {
							o.definirPourClasse(definition.getString(0), definition.getString(1));
						}
						gestionnaireEvenements.handle(Future.succeededFuture());
					} catch(Exception e) {
						gestionnaireEvenements.handle(Future.failedFuture(e));
					}
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(definirAsync.cause()));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void attribuerInscriptionScolaire(InscriptionScolaire o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			connexionSql.queryWithParams(
					SiteContexteFrFR.SQL_attribuer
					, new JsonArray(Arrays.asList(pk, pk))
					, attribuerAsync
			-> {
				try {
					if(attribuerAsync.succeeded()) {
						if(attribuerAsync.result() != null) {
							for(JsonArray definition : attribuerAsync.result().getResults()) {
								if(pk.equals(definition.getLong(0)))
									o.attribuerPourClasse(definition.getString(2), definition.getLong(1));
								else
									o.attribuerPourClasse(definition.getString(3), definition.getLong(0));
							}
						}
						gestionnaireEvenements.handle(Future.succeededFuture());
					} else {
						gestionnaireEvenements.handle(Future.failedFuture(attribuerAsync.cause()));
					}
				} catch(Exception e) {
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void indexerInscriptionScolaire(InscriptionScolaire o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = o.getRequeteSite_();
		try {
			o.initLoinPourClasse(requeteSite);
			o.indexerPourClasse();
			gestionnaireEvenements.handle(Future.succeededFuture());
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}
}