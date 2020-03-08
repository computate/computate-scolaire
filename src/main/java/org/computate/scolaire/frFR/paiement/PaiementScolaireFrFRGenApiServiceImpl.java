package org.computate.scolaire.frFR.paiement;

import org.computate.scolaire.frFR.inscription.InscriptionScolaireFrFRGenApiServiceImpl;
import org.computate.scolaire.frFR.inscription.InscriptionScolaire;
import org.computate.scolaire.frFR.config.ConfigSite;
import org.computate.scolaire.frFR.requete.RequeteSiteFrFR;
import org.computate.scolaire.frFR.contexte.SiteContexteFrFR;
import org.computate.scolaire.frFR.utilisateur.UtilisateurSite;
import org.computate.scolaire.frFR.requete.api.RequeteApi;
import org.computate.scolaire.frFR.recherche.ResultatRecherche;
import io.vertx.core.WorkerExecutor;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailMessage;
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
import io.vertx.core.Promise;
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
import java.time.ZonedDateTime;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.computate.scolaire.frFR.utilisateur.UtilisateurSiteFrFRGenApiServiceImpl;
import org.computate.scolaire.frFR.recherche.ListeRecherche;
import org.computate.scolaire.frFR.ecrivain.ToutEcrivain;


/**
 * Traduire: false
 * classeNomCanonique.enUS: org.computate.scolaire.enUS.payment.SchoolPaymentEnUSGenApiServiceImpl
 **/
public class PaiementScolaireFrFRGenApiServiceImpl implements PaiementScolaireFrFRGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PaiementScolaireFrFRGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "PaiementScolaireFrFRApiServiceImpl";

	protected SiteContexteFrFR siteContexte;

	public PaiementScolaireFrFRGenApiServiceImpl(SiteContexteFrFR siteContexte) {
		this.siteContexte = siteContexte;
	}

	// POST //

	@Override
	public void postPaiementScolaire(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementScolaire(siteContexte, operationRequete, body);
			sqlPaiementScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					creerPaiementScolaire(requeteSite, b -> {
						if(b.succeeded()) {
							RequeteApi requeteApi = new RequeteApi();
							requeteApi.setRows(1);
							requeteApi.setNumFound(1L);
							requeteApi.initLoinRequeteApi(requeteSite);
							requeteSite.setRequeteApi_(requeteApi);
							PaiementScolaire paiementScolaire = b.result();
							sqlPOSTPaiementScolaire(paiementScolaire, c -> {
								if(c.succeeded()) {
									definirPaiementScolaire(paiementScolaire, d -> {
										if(d.succeeded()) {
											attribuerPaiementScolaire(paiementScolaire, e -> {
												if(e.succeeded()) {
													indexerPaiementScolaire(paiementScolaire, f -> {
														if(f.succeeded()) {
															reponse200POSTPaiementScolaire(paiementScolaire, g -> {
																if(f.succeeded()) {
																	SQLConnection connexionSql = requeteSite.getConnexionSql();
																	connexionSql.commit(h -> {
																		if(a.succeeded()) {
																			connexionSql.close(i -> {
																				if(a.succeeded()) {
																					requeteApiPaiementScolaire(paiementScolaire);
																					paiementScolaire.requeteApiPaiementScolaire();
																					requeteSite.getVertx().eventBus().publish("websocketPaiementScolaire", JsonObject.mapFrom(requeteApi).toString());
																					gestionnaireEvenements.handle(Future.succeededFuture(g.result()));
																				} else {
																					erreurPaiementScolaire(requeteSite, gestionnaireEvenements, i);
																				}
																			});
																		} else {
																			erreurPaiementScolaire(requeteSite, gestionnaireEvenements, h);
																		}
																	});
																} else {
																	erreurPaiementScolaire(requeteSite, gestionnaireEvenements, g);
																}
															});
														} else {
															erreurPaiementScolaire(requeteSite, gestionnaireEvenements, f);
														}
													});
												} else {
													erreurPaiementScolaire(requeteSite, gestionnaireEvenements, e);
												}
											});
										} else {
											erreurPaiementScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurPaiementScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurPaiementScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void sqlPOSTPaiementScolaire(PaiementScolaire o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
					case "inscriptionCle":
						postSql.append(SiteContexteFrFR.SQL_addA);
						postSqlParams.addAll(Arrays.asList("inscriptionCle", pk, "paiementCles", Long.parseLong(jsonObject.getString(entiteVar))));
						break;
					case "enfantNomCompletPrefere":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enfantNomCompletPrefere", jsonObject.getString(entiteVar), pk));
						break;
					case "enfantDateNaissance":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enfantDateNaissance", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entiteVar))), pk));
						break;
					case "mereNomCompletPrefere":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("mereNomCompletPrefere", jsonObject.getString(entiteVar), pk));
						break;
					case "pereNomCompletPrefere":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("pereNomCompletPrefere", jsonObject.getString(entiteVar), pk));
						break;
					case "inscriptionPaimentChaqueMois":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("inscriptionPaimentChaqueMois", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "inscriptionPaimentComplet":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("inscriptionPaimentComplet", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementDescription":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementDescription", jsonObject.getString(entiteVar), pk));
						break;
					case "paiementDate":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementDate", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entiteVar))), pk));
						break;
					case "paiementMontant":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementMontant", jsonObject.getString(entiteVar), pk));
						break;
					case "fraisMontant":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("fraisMontant", jsonObject.getString(entiteVar), pk));
						break;
					case "fraisMontantFuture":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("fraisMontantFuture", jsonObject.getString(entiteVar), pk));
						break;
					case "fraisInscription":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("fraisInscription", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "fraisPremierDernier":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("fraisPremierDernier", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "fraisMois":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("fraisMois", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementEspeces":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementEspeces", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementCheque":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementCheque", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementSysteme":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementSysteme", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementPar":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementPar", jsonObject.getString(entiteVar), pk));
						break;
					case "transactionId":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("transactionId", jsonObject.getString(entiteVar), pk));
						break;
					case "customerProfileId":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("customerProfileId", jsonObject.getString(entiteVar), pk));
						break;
					case "transactionStatus":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("transactionStatus", jsonObject.getString(entiteVar), pk));
						break;
					case "paiementRecu":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementRecu", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementNomCourt":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementNomCourt", jsonObject.getString(entiteVar), pk));
						break;
					}
				}
			}
			connexionSql.queryWithParams(
					postSql.toString()
					, new JsonArray(postSqlParams)
					, postAsync
			-> {
				if(postAsync.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture());
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(new Exception(postAsync.cause())));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void reponse200POSTPaiementScolaire(PaiementScolaire o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			JsonObject json = JsonObject.mapFrom(o);
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Optional.ofNullable(json).orElse(new JsonObject()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PUT //

	@Override
	public void putPaiementScolaire(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementScolaire(siteContexte, operationRequete, body);
			sqlPaiementScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementScolaire(requeteSite, b -> {
						if(b.succeeded()) {
							SQLConnection connexionSql = requeteSite.getConnexionSql();
							connexionSql.close(c -> {
								if(c.succeeded()) {
									recherchePaiementScolaire(requeteSite, false, true, null, d -> {
										if(d.succeeded()) {
											ListeRecherche<PaiementScolaire> listePaiementScolaire = d.result();
											RequeteApi requeteApi = new RequeteApi();
											requeteApi.setRows(listePaiementScolaire.getRows());
											requeteApi.setNumFound(Optional.ofNullable(listePaiementScolaire.getQueryResponse()).map(QueryResponse::getResults).map(SolrDocumentList::getNumFound).orElse(new Long(listePaiementScolaire.size())));
											requeteApi.initLoinRequeteApi(requeteSite);
											requeteSite.setRequeteApi_(requeteApi);
											WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
											executeurTravailleur.executeBlocking(
												blockingCodeHandler -> {
													sqlPaiementScolaire(requeteSite, e -> {
														if(e.succeeded()) {
															try {
																listePUTPaiementScolaire(requeteApi, listePaiementScolaire, f -> {
																	if(f.succeeded()) {
																		SQLConnection connexionSql2 = requeteSite.getConnexionSql();
																		if(connexionSql2 == null) {
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			connexionSql2.commit(g -> {
																				if(f.succeeded()) {
																					connexionSql2.close(h -> {
																						if(g.succeeded()) {
																							blockingCodeHandler.handle(Future.succeededFuture(h.result()));
																						} else {
																							blockingCodeHandler.handle(Future.failedFuture(h.cause()));
																						}
																					});
																				} else {
																					blockingCodeHandler.handle(Future.failedFuture(g.cause()));
																				}
																			});
																		}
																	} else {
																		blockingCodeHandler.handle(Future.failedFuture(f.cause()));
																	}
																});
															} catch(Exception ex) {
																blockingCodeHandler.handle(Future.failedFuture(ex));
															}
														} else {
															blockingCodeHandler.handle(Future.failedFuture(e.cause()));
														}
													});
												}, resultHandler -> {
												}
											);
											reponse200PUTPaiementScolaire(requeteApi, gestionnaireEvenements);
										} else {
											erreurPaiementScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurPaiementScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurPaiementScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void listePUTPaiementScolaire(RequeteApi requeteApi, ListeRecherche<PaiementScolaire> listePaiementScolaire, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		RequeteSiteFrFR requeteSite = listePaiementScolaire.getRequeteSite_();
		JsonArray jsonArray = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		if(jsonArray.size() == 0) {
			listePaiementScolaire.getList().forEach(o -> {
				futures.add(
					futurePUTPaiementScolaire(requeteSite, JsonObject.mapFrom(o), a -> {
						if(a.succeeded()) {
						} else {
							erreurPaiementScolaire(requeteSite, gestionnaireEvenements, a);
						}
					})
				);
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					requeteApi.setNumPATCH(requeteApi.getNumPATCH() + listePaiementScolaire.size());
					if(listePaiementScolaire.next()) {
						requeteSite.getVertx().eventBus().publish("websocketPaiementScolaire", JsonObject.mapFrom(requeteApi).toString());
						listePUTPaiementScolaire(requeteApi, listePaiementScolaire, gestionnaireEvenements);
					} else {
						reponse200PUTPaiementScolaire(requeteApi, gestionnaireEvenements);
					}
				} else {
					erreurPaiementScolaire(listePaiementScolaire.getRequeteSite_(), gestionnaireEvenements, a);
				}
			});
		} else {
			jsonArray.forEach(o -> {
				JsonObject jsonObject = (JsonObject)o;
				futures.add(
					futurePUTPaiementScolaire(requeteSite, jsonObject, a -> {
						if(a.succeeded()) {
						} else {
							erreurPaiementScolaire(requeteSite, gestionnaireEvenements, a);
						}
					})
				);
			});
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					requeteApi.setNumPATCH(requeteApi.getNumPATCH() + jsonArray.size());
					reponse200PUTPaiementScolaire(requeteApi, gestionnaireEvenements);
				} else {
					erreurPaiementScolaire(requeteApi.getRequeteSite_(), gestionnaireEvenements, a);
				}
			});
		}
	}

	public Future<PaiementScolaire> futurePUTPaiementScolaire(RequeteSiteFrFR requeteSite, JsonObject jsonObject,  Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		jsonObject.put("sauvegardes", Optional.ofNullable(jsonObject.getJsonArray("sauvegardes")).orElse(new JsonArray()));
		JsonObject jsonPatch = Optional.ofNullable(requeteSite.getObjetJson()).map(o -> o.getJsonObject("patch")).orElse(new JsonObject());
		jsonPatch.stream().forEach(o -> {
			jsonObject.put(o.getKey(), o.getValue());
			jsonObject.getJsonArray("sauvegardes").add(o.getKey());
		});
		Promise<PaiementScolaire> promise = Promise.promise();
		try {
			creerPaiementScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					PaiementScolaire paiementScolaire = a.result();
					sqlPUTPaiementScolaire(paiementScolaire, jsonObject, b -> {
						if(b.succeeded()) {
							definirPaiementScolaire(paiementScolaire, c -> {
								if(c.succeeded()) {
									attribuerPaiementScolaire(paiementScolaire, d -> {
										if(d.succeeded()) {
											indexerPaiementScolaire(paiementScolaire, e -> {
												if(e.succeeded()) {
													requeteApiPaiementScolaire(paiementScolaire);
													paiementScolaire.requeteApiPaiementScolaire();
													promise.complete(paiementScolaire);
													gestionnaireEvenements.handle(Future.succeededFuture(e.result()));
												} else {
													gestionnaireEvenements.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
			return promise.future();
		} catch(Exception e) {
			return Future.failedFuture(e);
		}
	}

	public void remplacerPUTPaiementScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<PaiementScolaire>> gestionnaireEvenements) {
		try {
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			String utilisateurId = requeteSite.getUtilisateurId();
			Long pk = requeteSite.getRequetePk();

			connexionSql.queryWithParams(
					SiteContexteFrFR.SQL_vider
					, new JsonArray(Arrays.asList(pk, PaiementScolaire.class.getCanonicalName(), pk, pk, pk))
					, remplacerAsync
			-> {
				PaiementScolaire o = new PaiementScolaire();
				o.setPk(pk);
				gestionnaireEvenements.handle(Future.succeededFuture(o));
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void sqlPUTPaiementScolaire(PaiementScolaire o, JsonObject jsonObject, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			StringBuilder postSql = new StringBuilder();
			List<Object> postSqlParams = new ArrayList<Object>();

			if(jsonObject != null) {
				JsonArray entiteVars = jsonObject.getJsonArray("sauvegardes");
				for(Integer i = 0; i < entiteVars.size(); i++) {
					String entiteVar = entiteVars.getString(i);
					switch(entiteVar) {
					case "inscriptionCle":
						postSql.append(SiteContexteFrFR.SQL_addA);
						postSqlParams.addAll(Arrays.asList("inscriptionCle", pk, "paiementCles", Long.parseLong(jsonObject.getString(entiteVar))));
						break;
					case "enfantNomCompletPrefere":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enfantNomCompletPrefere", jsonObject.getString(entiteVar), pk));
						break;
					case "enfantDateNaissance":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enfantDateNaissance", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entiteVar))), pk));
						break;
					case "mereNomCompletPrefere":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("mereNomCompletPrefere", jsonObject.getString(entiteVar), pk));
						break;
					case "pereNomCompletPrefere":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("pereNomCompletPrefere", jsonObject.getString(entiteVar), pk));
						break;
					case "inscriptionPaimentChaqueMois":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("inscriptionPaimentChaqueMois", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "inscriptionPaimentComplet":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("inscriptionPaimentComplet", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementDescription":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementDescription", jsonObject.getString(entiteVar), pk));
						break;
					case "paiementDate":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementDate", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entiteVar))), pk));
						break;
					case "paiementMontant":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementMontant", jsonObject.getString(entiteVar), pk));
						break;
					case "fraisMontant":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("fraisMontant", jsonObject.getString(entiteVar), pk));
						break;
					case "fraisMontantFuture":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("fraisMontantFuture", jsonObject.getString(entiteVar), pk));
						break;
					case "fraisInscription":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("fraisInscription", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "fraisPremierDernier":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("fraisPremierDernier", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "fraisMois":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("fraisMois", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementEspeces":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementEspeces", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementCheque":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementCheque", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementSysteme":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementSysteme", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementPar":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementPar", jsonObject.getString(entiteVar), pk));
						break;
					case "transactionId":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("transactionId", jsonObject.getString(entiteVar), pk));
						break;
					case "customerProfileId":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("customerProfileId", jsonObject.getString(entiteVar), pk));
						break;
					case "transactionStatus":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("transactionStatus", jsonObject.getString(entiteVar), pk));
						break;
					case "paiementRecu":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementRecu", jsonObject.getBoolean(entiteVar), pk));
						break;
					case "paiementNomCourt":
						postSql.append(SiteContexteFrFR.SQL_setD);
						postSqlParams.addAll(Arrays.asList("paiementNomCourt", jsonObject.getString(entiteVar), pk));
						break;
					}
				}
			}
			connexionSql.queryWithParams(
					postSql.toString()
					, new JsonArray(postSqlParams)
					, postAsync
			-> {
				if(postAsync.succeeded()) {
					gestionnaireEvenements.handle(Future.succeededFuture());
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(new Exception(postAsync.cause())));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void reponse200PUTPaiementScolaire(RequeteApi requeteApi, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(JsonObject.mapFrom(requeteApi))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchPaiementScolaire(JsonObject body, OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementScolaire(siteContexte, operationRequete, body);
			sqlPaiementScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementScolaire(requeteSite, b -> {
						if(b.succeeded()) {
							SQLConnection connexionSql = requeteSite.getConnexionSql();
							connexionSql.close(c -> {
								if(c.succeeded()) {
									recherchePaiementScolaire(requeteSite, false, true, null, d -> {
										if(d.succeeded()) {
											ListeRecherche<PaiementScolaire> listePaiementScolaire = d.result();
											SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listePaiementScolaire.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
											Date date = null;
											if(facets != null)
												date = (Date)facets.get("max_modifie");
											String dt;
											if(date == null)
												dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
											else
												dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
											listePaiementScolaire.addFilterQuery(String.format("modifie_indexed_date:[* TO %s]", dt));

											RequeteApi requeteApi = new RequeteApi();
											requeteApi.setRows(listePaiementScolaire.getRows());
											requeteApi.setNumFound(Optional.ofNullable(listePaiementScolaire.getQueryResponse()).map(QueryResponse::getResults).map(SolrDocumentList::getNumFound).orElse(new Long(listePaiementScolaire.size())));
											requeteApi.initLoinRequeteApi(requeteSite);
											requeteSite.setRequeteApi_(requeteApi);
											if(listePaiementScolaire.size() == 1) {
												PaiementScolaire o = listePaiementScolaire.get(0);
												requeteApi.setPk(o.getPk());
												requeteApi.setOriginal(o);
												requeteApiPaiementScolaire(o);
											}
											WorkerExecutor executeurTravailleur = siteContexte.getExecuteurTravailleur();
											executeurTravailleur.executeBlocking(
												blockingCodeHandler -> {
													sqlPaiementScolaire(requeteSite, e -> {
														if(e.succeeded()) {
															try {
																listePATCHPaiementScolaire(requeteApi, listePaiementScolaire, dt, f -> {
																	if(f.succeeded()) {
																		SQLConnection connexionSql2 = requeteSite.getConnexionSql();
																		if(connexionSql2 == null) {
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			connexionSql2.commit(g -> {
																				if(f.succeeded()) {
																					connexionSql2.close(h -> {
																						if(g.succeeded()) {
																							blockingCodeHandler.handle(Future.succeededFuture(h.result()));
																						} else {
																							blockingCodeHandler.handle(Future.failedFuture(h.cause()));
																						}
																					});
																				} else {
																					blockingCodeHandler.handle(Future.failedFuture(g.cause()));
																				}
																			});
																		}
																	} else {
																		blockingCodeHandler.handle(Future.failedFuture(f.cause()));
																	}
																});
															} catch(Exception ex) {
																blockingCodeHandler.handle(Future.failedFuture(ex));
															}
														} else {
															blockingCodeHandler.handle(Future.failedFuture(e.cause()));
														}
													});
												}, resultHandler -> {
												}
											);
											reponse200PATCHPaiementScolaire(requeteApi, gestionnaireEvenements);
										} else {
											erreurPaiementScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurPaiementScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurPaiementScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void listePATCHPaiementScolaire(RequeteApi requeteApi, ListeRecherche<PaiementScolaire> listePaiementScolaire, String dt, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		List<Future> futures = new ArrayList<>();
		RequeteSiteFrFR requeteSite = listePaiementScolaire.getRequeteSite_();
		listePaiementScolaire.getList().forEach(o -> {
			futures.add(
				futurePATCHPaiementScolaire(o, a -> {
					if(a.succeeded()) {
					} else {
						erreurPaiementScolaire(requeteSite, gestionnaireEvenements, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				requeteApi.setNumPATCH(requeteApi.getNumPATCH() + listePaiementScolaire.size());
				if(listePaiementScolaire.next(dt)) {
					requeteSite.getVertx().eventBus().publish("websocketPaiementScolaire", JsonObject.mapFrom(requeteApi).toString());
					listePATCHPaiementScolaire(requeteApi, listePaiementScolaire, dt, gestionnaireEvenements);
				} else {
					reponse200PATCHPaiementScolaire(requeteApi, gestionnaireEvenements);
				}
			} else {
				erreurPaiementScolaire(listePaiementScolaire.getRequeteSite_(), gestionnaireEvenements, a);
			}
		});
	}

	public Future<PaiementScolaire> futurePATCHPaiementScolaire(PaiementScolaire o,  Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		Promise<PaiementScolaire> promise = Promise.promise();
		try {
			sqlPATCHPaiementScolaire(o, a -> {
				if(a.succeeded()) {
					PaiementScolaire paiementScolaire = a.result();
					definirPaiementScolaire(paiementScolaire, b -> {
						if(b.succeeded()) {
							attribuerPaiementScolaire(paiementScolaire, c -> {
								if(c.succeeded()) {
									indexerPaiementScolaire(paiementScolaire, d -> {
										if(d.succeeded()) {
											requeteApiPaiementScolaire(paiementScolaire);
											paiementScolaire.requeteApiPaiementScolaire();
											promise.complete(o);
											gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
										} else {
											gestionnaireEvenements.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									gestionnaireEvenements.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
				}
			});
			return promise.future();
		} catch(Exception e) {
			return Future.failedFuture(e);
		}
	}

	public void sqlPATCHPaiementScolaire(PaiementScolaire o, Handler<AsyncResult<PaiementScolaire>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = o.getRequeteSite_();
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			Long pk = o.getPk();
			JsonObject requeteJson = requeteSite.getObjetJson();
			StringBuilder patchSql = new StringBuilder();
			List<Object> patchSqlParams = new ArrayList<Object>();
			Set<String> methodeNoms = requeteJson.fieldNames();
			PaiementScolaire o2 = new PaiementScolaire();

			patchSql.append(SiteContexteFrFR.SQL_modifier);
			patchSqlParams.addAll(Arrays.asList(pk, "org.computate.scolaire.frFR.paiement.PaiementScolaire"));
			for(String methodeNom : methodeNoms) {
				switch(methodeNom) {
					case "setCree":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "cree"));
						} else {
							o2.setCree(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("cree", o2.jsonCree(), pk));
						}
						break;
					case "setModifie":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "modifie"));
						} else {
							o2.setModifie(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("modifie", o2.jsonModifie(), pk));
						}
						break;
					case "setArchive":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "archive"));
						} else {
							o2.setArchive(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("archive", o2.jsonArchive(), pk));
						}
						break;
					case "setSupprime":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "supprime"));
						} else {
							o2.setSupprime(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("supprime", o2.jsonSupprime(), pk));
						}
						break;
					case "setInscriptionCle":
						o2.setInscriptionCle(requeteJson.getString(methodeNom));
						patchSql.append(SiteContexteFrFR.SQL_setA1);
						patchSqlParams.addAll(Arrays.asList("inscriptionCle", pk, "paiementCles", o2.getInscriptionCle()));
						break;
					case "removeInscriptionCle":
						o2.setInscriptionCle(requeteJson.getString(methodeNom));
						patchSql.append(SiteContexteFrFR.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("inscriptionCle", pk, "paiementCles", o2.getInscriptionCle()));
						break;
					case "setEnfantNomCompletPrefere":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enfantNomCompletPrefere"));
						} else {
							o2.setEnfantNomCompletPrefere(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enfantNomCompletPrefere", o2.jsonEnfantNomCompletPrefere(), pk));
						}
						break;
					case "setEnfantDateNaissance":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enfantDateNaissance"));
						} else {
							o2.setEnfantDateNaissance(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enfantDateNaissance", o2.jsonEnfantDateNaissance(), pk));
						}
						break;
					case "setMereNomCompletPrefere":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "mereNomCompletPrefere"));
						} else {
							o2.setMereNomCompletPrefere(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("mereNomCompletPrefere", o2.jsonMereNomCompletPrefere(), pk));
						}
						break;
					case "setPereNomCompletPrefere":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "pereNomCompletPrefere"));
						} else {
							o2.setPereNomCompletPrefere(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("pereNomCompletPrefere", o2.jsonPereNomCompletPrefere(), pk));
						}
						break;
					case "setInscriptionPaimentChaqueMois":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "inscriptionPaimentChaqueMois"));
						} else {
							o2.setInscriptionPaimentChaqueMois(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("inscriptionPaimentChaqueMois", o2.jsonInscriptionPaimentChaqueMois(), pk));
						}
						break;
					case "setInscriptionPaimentComplet":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "inscriptionPaimentComplet"));
						} else {
							o2.setInscriptionPaimentComplet(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("inscriptionPaimentComplet", o2.jsonInscriptionPaimentComplet(), pk));
						}
						break;
					case "setPaiementDescription":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementDescription"));
						} else {
							o2.setPaiementDescription(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("paiementDescription", o2.jsonPaiementDescription(), pk));
						}
						break;
					case "setPaiementDate":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementDate"));
						} else {
							o2.setPaiementDate(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("paiementDate", o2.jsonPaiementDate(), pk));
						}
						break;
					case "setPaiementMontant":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementMontant"));
						} else {
							o2.setPaiementMontant(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("paiementMontant", o2.jsonPaiementMontant(), pk));
						}
						break;
					case "setFraisMontant":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMontant"));
						} else {
							o2.setFraisMontant(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("fraisMontant", o2.jsonFraisMontant(), pk));
						}
						break;
					case "setFraisMontantFuture":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMontantFuture"));
						} else {
							o2.setFraisMontantFuture(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("fraisMontantFuture", o2.jsonFraisMontantFuture(), pk));
						}
						break;
					case "setFraisInscription":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisInscription"));
						} else {
							o2.setFraisInscription(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("fraisInscription", o2.jsonFraisInscription(), pk));
						}
						break;
					case "setFraisPremierDernier":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisPremierDernier"));
						} else {
							o2.setFraisPremierDernier(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("fraisPremierDernier", o2.jsonFraisPremierDernier(), pk));
						}
						break;
					case "setFraisMois":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "fraisMois"));
						} else {
							o2.setFraisMois(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("fraisMois", o2.jsonFraisMois(), pk));
						}
						break;
					case "setPaiementEspeces":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementEspeces"));
						} else {
							o2.setPaiementEspeces(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("paiementEspeces", o2.jsonPaiementEspeces(), pk));
						}
						break;
					case "setPaiementCheque":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementCheque"));
						} else {
							o2.setPaiementCheque(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("paiementCheque", o2.jsonPaiementCheque(), pk));
						}
						break;
					case "setPaiementSysteme":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementSysteme"));
						} else {
							o2.setPaiementSysteme(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("paiementSysteme", o2.jsonPaiementSysteme(), pk));
						}
						break;
					case "setPaiementPar":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementPar"));
						} else {
							o2.setPaiementPar(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("paiementPar", o2.jsonPaiementPar(), pk));
						}
						break;
					case "setTransactionId":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "transactionId"));
						} else {
							o2.setTransactionId(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("transactionId", o2.jsonTransactionId(), pk));
						}
						break;
					case "setCustomerProfileId":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "customerProfileId"));
						} else {
							o2.setCustomerProfileId(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("customerProfileId", o2.jsonCustomerProfileId(), pk));
						}
						break;
					case "setTransactionStatus":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "transactionStatus"));
						} else {
							o2.setTransactionStatus(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("transactionStatus", o2.jsonTransactionStatus(), pk));
						}
						break;
					case "setPaiementRecu":
						if(requeteJson.getBoolean(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementRecu"));
						} else {
							o2.setPaiementRecu(requeteJson.getBoolean(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("paiementRecu", o2.jsonPaiementRecu(), pk));
						}
						break;
					case "setPaiementNomCourt":
						if(requeteJson.getString(methodeNom) == null) {
							patchSql.append(SiteContexteFrFR.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "paiementNomCourt"));
						} else {
							o2.setPaiementNomCourt(requeteJson.getString(methodeNom));
							patchSql.append(SiteContexteFrFR.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("paiementNomCourt", o2.jsonPaiementNomCourt(), pk));
						}
						break;
				}
			}
			connexionSql.queryWithParams(
					patchSql.toString()
					, new JsonArray(patchSqlParams)
					, patchAsync
			-> {
				if(patchAsync.succeeded()) {
					PaiementScolaire o3 = new PaiementScolaire();
					o3.setRequeteSite_(o.getRequeteSite_());
					o3.setPk(pk);
					gestionnaireEvenements.handle(Future.succeededFuture(o3));
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(new Exception(patchAsync.cause())));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void reponse200PATCHPaiementScolaire(RequeteApi requeteApi, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = requeteApi.getRequeteSite_();
			JsonObject json = JsonObject.mapFrom(requeteApi);
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Optional.ofNullable(json).orElse(new JsonObject()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getPaiementScolaire(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementScolaire(siteContexte, operationRequete);
			sqlPaiementScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementScolaire(requeteSite, b -> {
						if(b.succeeded()) {
							recherchePaiementScolaire(requeteSite, false, true, null, c -> {
								if(c.succeeded()) {
									ListeRecherche<PaiementScolaire> listePaiementScolaire = c.result();
									reponse200GETPaiementScolaire(listePaiementScolaire, d -> {
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
																erreurPaiementScolaire(requeteSite, gestionnaireEvenements, f);
															}
														});
													} else {
														gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
													}
												});
											}
										} else {
											erreurPaiementScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurPaiementScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurPaiementScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void reponse200GETPaiementScolaire(ListeRecherche<PaiementScolaire> listePaiementScolaire, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listePaiementScolaire.getRequeteSite_();
			SolrDocumentList documentsSolr = listePaiementScolaire.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listePaiementScolaire.getList().stream().findFirst().orElse(null));
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Optional.ofNullable(json).orElse(new JsonObject()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// DELETE //

	@Override
	public void deletePaiementScolaire(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementScolaire(siteContexte, operationRequete);
			sqlPaiementScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					recherchePaiementScolaire(requeteSite, false, true, null, b -> {
						if(b.succeeded()) {
							ListeRecherche<PaiementScolaire> listePaiementScolaire = b.result();
							supprimerDELETEPaiementScolaire(requeteSite, c -> {
								if(c.succeeded()) {
									reponse200DELETEPaiementScolaire(requeteSite, d -> {
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
																erreurPaiementScolaire(requeteSite, gestionnaireEvenements, f);
															}
														});
													} else {
														gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
													}
												});
											}
										} else {
											erreurPaiementScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurPaiementScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurPaiementScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void supprimerDELETEPaiementScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			String utilisateurId = requeteSite.getUtilisateurId();
			Long pk = requeteSite.getRequetePk();

			connexionSql.queryWithParams(
					SiteContexteFrFR.SQL_supprimer
					, new JsonArray(Arrays.asList(pk, PaiementScolaire.class.getCanonicalName(), pk, pk, pk, pk))
					, supprimerAsync
			-> {
				gestionnaireEvenements.handle(Future.succeededFuture());
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void reponse200DELETEPaiementScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			JsonObject json = new JsonObject();
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Optional.ofNullable(json).orElse(new JsonObject()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// Recherche //

	@Override
	public void recherchePaiementScolaire(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementScolaire(siteContexte, operationRequete);
			sqlPaiementScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementScolaire(requeteSite, b -> {
						if(b.succeeded()) {
							recherchePaiementScolaire(requeteSite, false, true, "/api/paiement", c -> {
								if(c.succeeded()) {
									ListeRecherche<PaiementScolaire> listePaiementScolaire = c.result();
									reponse200RecherchePaiementScolaire(listePaiementScolaire, d -> {
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
																erreurPaiementScolaire(requeteSite, gestionnaireEvenements, f);
															}
														});
													} else {
														gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
													}
												});
											}
										} else {
											erreurPaiementScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurPaiementScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurPaiementScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void reponse200RecherchePaiementScolaire(ListeRecherche<PaiementScolaire> listePaiementScolaire, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listePaiementScolaire.getRequeteSite_();
			QueryResponse reponseRecherche = listePaiementScolaire.getQueryResponse();
			SolrDocumentList documentsSolr = listePaiementScolaire.getSolrDocumentList();
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
			listePaiementScolaire.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listePaiementScolaire.getFields();
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
			gestionnaireEvenements.handle(Future.succeededFuture(OperationResponse.completedWithJson(Optional.ofNullable(json).orElse(new JsonObject()))));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// PageRecherche //

	@Override
	public void pagerecherchePaiementScolaireId(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		pagerecherchePaiementScolaire(operationRequete, gestionnaireEvenements);
	}

	@Override
	public void pagerecherchePaiementScolaire(OperationRequest operationRequete, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = genererRequeteSiteFrFRPourPaiementScolaire(siteContexte, operationRequete);
			sqlPaiementScolaire(requeteSite, a -> {
				if(a.succeeded()) {
					utilisateurPaiementScolaire(requeteSite, b -> {
						if(b.succeeded()) {
							recherchePaiementScolaire(requeteSite, false, true, "/paiement", c -> {
								if(c.succeeded()) {
									ListeRecherche<PaiementScolaire> listePaiementScolaire = c.result();
									reponse200PageRecherchePaiementScolaire(listePaiementScolaire, d -> {
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
																erreurPaiementScolaire(requeteSite, gestionnaireEvenements, f);
															}
														});
													} else {
														gestionnaireEvenements.handle(Future.succeededFuture(d.result()));
													}
												});
											}
										} else {
											erreurPaiementScolaire(requeteSite, gestionnaireEvenements, d);
										}
									});
								} else {
									erreurPaiementScolaire(requeteSite, gestionnaireEvenements, c);
								}
							});
						} else {
							erreurPaiementScolaire(requeteSite, gestionnaireEvenements, b);
						}
					});
				} else {
					erreurPaiementScolaire(requeteSite, gestionnaireEvenements, a);
				}
			});
		} catch(Exception e) {
			erreurPaiementScolaire(null, gestionnaireEvenements, Future.failedFuture(e));
		}
	}

	public void reponse200PageRecherchePaiementScolaire(ListeRecherche<PaiementScolaire> listePaiementScolaire, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		try {
			RequeteSiteFrFR requeteSite = listePaiementScolaire.getRequeteSite_();
			Buffer buffer = Buffer.buffer();
			ToutEcrivain w = ToutEcrivain.creer(listePaiementScolaire.getRequeteSite_(), buffer);
			PaiementPage page = new PaiementPage();
			SolrDocument pageDocumentSolr = new SolrDocument();
			CaseInsensitiveHeaders requeteEnTetes = new CaseInsensitiveHeaders();
			requeteSite.setRequeteEnTetes(requeteEnTetes);

			pageDocumentSolr.setField("pageUri_frFR_stored_string", "/paiement");
			page.setPageDocumentSolr(pageDocumentSolr);
			page.setW(w);
			if(listePaiementScolaire.size() == 1)
				requeteSite.setRequetePk(listePaiementScolaire.get(0).getPk());
			requeteSite.setW(w);
			page.setListePaiementScolaire(listePaiementScolaire);
			page.setRequeteSite_(requeteSite);
			page.initLoinPaiementPage(requeteSite);
			page.html();
			gestionnaireEvenements.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requeteEnTetes)));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	// Partagé //

	public void creerPaiementScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<PaiementScolaire>> gestionnaireEvenements) {
		try {
			SQLConnection connexionSql = requeteSite.getConnexionSql();
			String utilisateurId = requeteSite.getUtilisateurId();

			connexionSql.queryWithParams(
					SiteContexteFrFR.SQL_creer
					, new JsonArray(Arrays.asList(PaiementScolaire.class.getCanonicalName(), utilisateurId))
					, creerAsync
			-> {
				JsonArray creerLigne = creerAsync.result().getResults().stream().findFirst().orElseGet(() -> null);
				Long pk = creerLigne.getLong(0);
				PaiementScolaire o = new PaiementScolaire();
				o.setPk(pk);
				o.setRequeteSite_(requeteSite);
				gestionnaireEvenements.handle(Future.succeededFuture(o));
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void requeteApiPaiementScolaire(PaiementScolaire o) {
		RequeteApi requeteApi = o.getRequeteSite_().getRequeteApi_();
		if(requeteApi != null) {
			List<Long> pks = requeteApi.getPks();
			List<String> classes = requeteApi.getClasses();
			if(o.getInscriptionCle() != null) {
				if(!pks.contains(o.getInscriptionCle())) {
					pks.add(o.getInscriptionCle());
					classes.add("InscriptionScolaire");
				}
			}
		}
	}

	public void erreurPaiementScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements, AsyncResult<?> resultatAsync) {
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
		ConfigSite configSite = requeteSite.getConfigSite_();
		SiteContexteFrFR siteContexte = requeteSite.getSiteContexte_();
		MailClient mailClient = siteContexte.getMailClient();
		MailMessage message = new MailMessage();
		message.setFrom(configSite.getMailDe());
		message.setTo(configSite.getMailAdmin());
		message.setText(ExceptionUtils.getStackTrace(e));
		message.setSubject(String.format(configSite.getSiteUrlBase() + " " + e.getMessage()));
		WorkerExecutor workerExecutor = siteContexte.getExecuteurTravailleur();
		workerExecutor.executeBlocking(
			blockingCodeHandler -> {
				mailClient.sendMail(message, result -> {
					if (result.succeeded()) {
						LOGGER.info(result.result());
					} else {
						LOGGER.error(result.cause());
					}
				});
			}, resultHandler -> {
			}
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

	public void sqlPaiementScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
						gestionnaireEvenements.handle(Future.failedFuture(new Exception(sqlAsync.cause())));
					}
				});
			}
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public RequeteSiteFrFR genererRequeteSiteFrFRPourPaiementScolaire(SiteContexteFrFR siteContexte, OperationRequest operationRequete) {
		return genererRequeteSiteFrFRPourPaiementScolaire(siteContexte, operationRequete, null);
	}

	public RequeteSiteFrFR genererRequeteSiteFrFRPourPaiementScolaire(SiteContexteFrFR siteContexte, OperationRequest operationRequete, JsonObject body) {
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

	public void utilisateurPaiementScolaire(RequeteSiteFrFR requeteSite, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
						try {
							JsonArray utilisateurValeurs = selectCAsync.result().getResults().stream().findFirst().orElse(null);
							UtilisateurSiteFrFRGenApiServiceImpl utilisateurService = new UtilisateurSiteFrFRGenApiServiceImpl(siteContexte);
							if(utilisateurValeurs == null) {
								JsonObject utilisateurVertx = requeteSite.getOperationRequete().getUser();
								JsonObject principalJson = KeycloakHelper.parseToken(utilisateurVertx.getString("access_token"));

								JsonObject jsonObject = new JsonObject();
								jsonObject.put("utilisateurNom", principalJson.getString("preferred_username"));
								jsonObject.put("utilisateurPrenom", principalJson.getString("given_name"));
								jsonObject.put("utilisateurNomFamille", principalJson.getString("family_name"));
								jsonObject.put("utilisateurId", principalJson.getString("sub"));
								utilisateurPaiementScolaireDefinir(siteRequest, jsonObject);

								RequeteSiteFrFR requeteSite2 = new RequeteSiteFrFR();
								requeteSite2.setConnexionSql(requeteSite.getConnexionSql());
								requeteSite2.setObjetJson(jsonObject);
								requeteSite2.setVertx(requeteSite.getVertx());
								requeteSite2.setSiteContexte_(siteContexte);
								requeteSite2.setConfigSite_(siteContexte.getConfigSite());
								requeteSite2.setUtilisateurId(requeteSite.getUtilisateurId());
								requeteSite2.initLoinRequeteSiteFrFR(requeteSite);

								utilisateurService.creerSiteUser(requeteSite2, b -> {
									if(b.succeeded()) {
										SiteUser siteUser = b.result();
										utilisateurService.sqlPOSTSiteUser(siteUser, c -> {
											if(c.succeeded()) {
												utilisateurService.definirSiteUser(siteUser, d -> {
													if(d.succeeded()) {
														utilisateurService.attribuerSiteUser(siteUser, e -> {
															if(e.succeeded()) {
																utilisateurService.indexerSiteUser(siteUser, f -> {
																	if(f.succeeded()) {
																		requeteSite.setUtilisateurSite(utilisateurSite);
																		requeteSite.setUtilisateurNom(principalJson.getString("preferred_username"));
																		requeteSite.setUtilisateurPrenom(principalJson.getString("given_name"));
																		requeteSite.setUtilisateurNomFamille(principalJson.getString("family_name"));
																		requeteSite.setUtilisateurId(principalJson.getString("sub"));
																		gestionnaireEvenements.handle(Future.succeededFuture());
																	} else {
																		erreurPaiementScolaire(requeteSite, gestionnaireEvenements, f);
																	}
																});
															} else {
																erreurPaiementScolaire(requeteSite, gestionnaireEvenements, e);
															}
														});
													} else {
														erreurPaiementScolaire(requeteSite, gestionnaireEvenements, d);
													}
												});
											} else {
												erreurPaiementScolaire(requeteSite, gestionnaireEvenements, c);
											}
										});
									} else {
										erreurPaiementScolaire(requeteSite, gestionnaireEvenements, b);
									}
								});
							} else {
								Long pkUtilisateur = utilisateurValeurs.getLong(0);
								ListeRecherche<UtilisateurSite> listeRecherche = new ListeRecherche<UtilisateurSite>();
								listeRecherche.setStocker(true);
								listeRecherche.setC(UtilisateurSite.class);
								listeRecherche.addFilterQuery("utilisateurId_indexed_string:" + ClientUtils.escapeQueryChars(utilisateurId));
								listeRecherche.addFilterQuery("pk_indexed_long:" + pkUtilisateur);
								listeRecherche.initLoinListeRecherche(requeteSite);
								UtilisateurSite utilisateurSite1 = listeRecherche.getList().stream().findFirst().orElse(null);

								JsonObject jsonObject = Optional.ofNullable(utilisateurSite1).map(u -> JsonObject.mapFrom(u)).orElse(new JsonObject());
								Boolean definir = utilisateurPaiementScolaireDefinir(siteRequest, jsonObject);
								if(definir) {
									UtilisateurSite utilisateurSite;
									if(utilisateurSite1 == null) {
										utilisateurSite = new UtilisateurSite();
										utilisateurSite.setPk(pkUtilisateur);
										utilisateurSite.setRequeteSite_(requeteSite);
									} else {
										utilisateurSite = utilisateurSite1;
									}

									RequeteSiteFrFR requeteSite2 = new RequeteSiteFrFR();
									requeteSite2.setConnexionSql(requeteSite.getConnexionSql());
									requeteSite2.setObjetJson(jsonObject);
									requeteSite2.setVertx(requeteSite.getVertx());
									requeteSite2.setSiteContexte_(siteContexte);
									requeteSite2.setConfigSite_(siteContexte.getConfigSite());
									requeteSite2.setUtilisateurId(requeteSite.getUtilisateurId());
									requeteSite2.initLoinRequeteSiteFrFR(requeteSite);
									utilisateurSite.setRequeteSite_(requeteSite2);

									utilisateurService.sqlPATCHSiteUser(siteUser, c -> {
										if(c.succeeded()) {
											utilisateurService.definirSiteUser(siteUser, d -> {
												if(d.succeeded()) {
													utilisateurService.attribuerSiteUser(siteUser, e -> {
														if(e.succeeded()) {
															utilisateurService.indexerSiteUser(siteUser, f -> {
																if(f.succeeded()) {
																	requeteSite.setUtilisateurSite(utilisateurSite);
																	requeteSite.setUtilisateurNom(utilisateurSite.getUtilisateurNom());
																	requeteSite.setUtilisateurPrenom(utilisateurSite.getUtilisateurPrenom());
																	requeteSite.setUtilisateurNomFamille(utilisateurSite.getUtilisateurNomFamille());
																	requeteSite.setUtilisateurId(utilisateurSite.getUtilisateurId());
																	gestionnaireEvenements.handle(Future.succeededFuture());
																} else {
																	erreurPaiementScolaire(requeteSite, gestionnaireEvenements, f);
																}
															});
														} else {
															erreurPaiementScolaire(requeteSite, gestionnaireEvenements, e);
														}
													});
												} else {
													erreurPaiementScolaire(requeteSite, gestionnaireEvenements, d);
												}
											});
										} else {
											erreurPaiementScolaire(requeteSite, gestionnaireEvenements, c);
										}
									});
								} else {
									requeteSite.setUtilisateurSite(utilisateurSite1);
									requeteSite.setUtilisateurNom(utilisateurSite1.getUtilisateurNom());
									requeteSite.setUtilisateurPrenom(utilisateurSite1.getUtilisateurPrenom());
									requeteSite.setUtilisateurNomFamille(utilisateurSite1.getUtilisateurNomFamille());
									requeteSite.setUtilisateurId(utilisateurSite1.getUtilisateurId());
									gestionnaireEvenements.handle(Future.succeededFuture());
								}
							}
						} catch(Exception e) {
							gestionnaireEvenements.handle(Future.failedFuture(e));
						}
					} else {
						gestionnaireEvenements.handle(Future.failedFuture(new Exception(selectCAsync.cause())));
					}
				});
			}
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public Boolean utilisateurPaiementScolaireDefinir(RequeteSiteFrFR siteRequest, JsonObject jsonObject) {
		return true;
	}

	public void recherchePaiementScolaire(RequeteSiteFrFR requeteSite, Boolean peupler, Boolean stocker, String classeApiUriMethode, Handler<AsyncResult<ListeRecherche<PaiementScolaire>>> gestionnaireEvenements) {
		try {
			OperationRequest operationRequete = requeteSite.getOperationRequete();
			String entiteListeStr = requeteSite.getOperationRequete().getParams().getJsonObject("query").getString("fl");
			String[] entiteListe = entiteListeStr == null ? null : entiteListeStr.split(",\\s*");
			ListeRecherche<PaiementScolaire> listeRecherche = new ListeRecherche<PaiementScolaire>();
			listeRecherche.setPeupler(peupler);
			listeRecherche.setStocker(stocker);
			listeRecherche.setQuery("*:*");
			listeRecherche.setC(PaiementScolaire.class);
			if(entiteListe != null)
				listeRecherche.addFields(entiteListe);
			listeRecherche.add("json.facet", "{max_modifie:'max(modifie_indexed_date)'}");
			listeRecherche.add("json.facet", "{terms_enfantNomCompletPrefere:{terms:{field:enfantNomCompletPrefere_indexed_string}}}");
			listeRecherche.add("json.facet", "{sum_paiementMontant:'sum(paiementMontant_indexed_double)'}");
			listeRecherche.add("json.facet", "{sum_fraisMontant:'sum(fraisMontant_indexed_double)'}");
			listeRecherche.add("json.facet", "{sum_fraisMontantFuture:'sum(fraisMontantFuture_indexed_double)'}");

			String id = operationRequete.getParams().getJsonObject("path").getString("id");
			if(id != null) {
				listeRecherche.addFilterQuery("(id:" + ClientUtils.escapeQueryChars(id) + " OR objetId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
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
								varIndexe = "*".equals(entiteVar) ? entiteVar : PaiementScolaire.varRecherchePaiementScolaire(entiteVar);
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
								varIndexe = PaiementScolaire.varIndexePaiementScolaire(entiteVar);
								listeRecherche.addFilterQuery(varIndexe + ":" + ClientUtils.escapeQueryChars(valeurIndexe));
								break;
							case "sort":
								entiteVar = StringUtils.trim(StringUtils.substringBefore((String)paramObjet, " "));
								valeurTri = StringUtils.trim(StringUtils.substringAfter((String)paramObjet, " "));
								varIndexe = PaiementScolaire.varIndexePaiementScolaire(entiteVar);
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
			if(listeRecherche.getSorts().size() == 0) {
				listeRecherche.addSort("paiementDate_indexed_date", ORDER.desc);
				listeRecherche.addSort("paiementPar_indexed_string", ORDER.desc);
			}
			listeRecherche.initLoinPourClasse(requeteSite);
			gestionnaireEvenements.handle(Future.succeededFuture(listeRecherche));
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void definirPaiementScolaire(PaiementScolaire o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
							try {
								o.definirPourClasse(definition.getString(0), definition.getString(1));
							} catch(Exception e) {
								LOGGER.error(e);
							}
						}
						gestionnaireEvenements.handle(Future.succeededFuture());
					} catch(Exception e) {
						gestionnaireEvenements.handle(Future.failedFuture(e));
					}
				} else {
					gestionnaireEvenements.handle(Future.failedFuture(new Exception(definirAsync.cause())));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void attribuerPaiementScolaire(PaiementScolaire o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
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
						gestionnaireEvenements.handle(Future.failedFuture(new Exception(attribuerAsync.cause())));
					}
				} catch(Exception e) {
					gestionnaireEvenements.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}

	public void indexerPaiementScolaire(PaiementScolaire o, Handler<AsyncResult<OperationResponse>> gestionnaireEvenements) {
		RequeteSiteFrFR requeteSite = o.getRequeteSite_();
		try {
			o.initLoinPourClasse(requeteSite);
			o.indexerPourClasse();
			if(BooleanUtils.isFalse(Optional.ofNullable(requeteSite.getRequeteApi_()).map(RequeteApi::getEmpty).orElse(null))) {
				RequeteSiteFrFR requeteSite2 = genererRequeteSiteFrFRPourPaiementScolaire(siteContexte, requeteSite.getOperationRequete(), new JsonObject());
				requeteSite2.setConnexionSql(requeteSite.getConnexionSql());
				ListeRecherche<PaiementScolaire> listeRecherche = new ListeRecherche<PaiementScolaire>();
				listeRecherche.setPeupler(true);
				listeRecherche.setQuery("*:*");
				listeRecherche.setC(PaiementScolaire.class);
				listeRecherche.addFilterQuery("modifie_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(requeteSite.getRequeteApi_().getCree().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				listeRecherche.add("json.facet", "{inscriptionCle:{terms:{field:inscriptionCle_indexed_longs, limit:1000}}}");
				listeRecherche.setRows(1000);
				listeRecherche.initLoinListeRecherche(requeteSite2);
				List<Future> futures = new ArrayList<>();

				{
					InscriptionScolaire o2 = new InscriptionScolaire();
					InscriptionScolaireFrFRGenApiServiceImpl service = new InscriptionScolaireFrFRGenApiServiceImpl(requeteSite2.getSiteContexte_());
					Long pk = o.getInscriptionCle();

					o2.setPk(pk);
					o2.setRequeteSite_(requeteSite2);
					futures.add(
						service.futurePATCHInscriptionScolaire(o2, a -> {
							if(a.succeeded()) {
								LOGGER.info(String.format("InscriptionScolaire %s rechargé. ", pk));
							} else {
								LOGGER.info(String.format("InscriptionScolaire %s a échoué. ", pk));
								gestionnaireEvenements.handle(Future.failedFuture(a.cause()));
							}
						})
					);
				}

				CompositeFuture.all(futures).setHandler(a -> {
					if(a.succeeded()) {
						LOGGER.info("Recharger relations a réussi. ");
						PaiementScolaireFrFRGenApiServiceImpl service = new PaiementScolaireFrFRGenApiServiceImpl(requeteSite2.getSiteContexte_());
						List<Future> futures2 = new ArrayList<>();
						for(PaiementScolaire o2 : listeRecherche.getList()) {
							futures2.add(
								service.futurePATCHPaiementScolaire(o2, b -> {
									if(b.succeeded()) {
										LOGGER.info(String.format("PaiementScolaire %s rechargé. ", o2.getPk()));
									} else {
										LOGGER.info(String.format("PaiementScolaire %s a échoué. ", o2.getPk()));
										gestionnaireEvenements.handle(Future.failedFuture(b.cause()));
									}
								})
							);
						}

						CompositeFuture.all(futures2).setHandler(b -> {
							if(b.succeeded()) {
								LOGGER.info("Recharger PaiementScolaire a réussi. ");
								gestionnaireEvenements.handle(Future.succeededFuture());
							} else {
								LOGGER.error("Recharger relations a échoué. ", b.cause());
								erreurPaiementScolaire(requeteSite2, gestionnaireEvenements, b);
							}
						});
					} else {
						LOGGER.error("Recharger relations a échoué. ", a.cause());
						erreurPaiementScolaire(requeteSite2, gestionnaireEvenements, a);
					}
				});
			} else {
				gestionnaireEvenements.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			gestionnaireEvenements.handle(Future.failedFuture(e));
		}
	}
}
