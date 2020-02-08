package org.computate.scolaire.frFR.vertx;    

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.computate.scolaire.frFR.age.AgeScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.annee.AnneeScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.bloc.BlocScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.cluster.ClusterFrFRGenApiService;
import org.computate.scolaire.frFR.config.ConfigSite;
import org.computate.scolaire.frFR.contexte.SiteContexteFrFR;
import org.computate.scolaire.frFR.ecole.EcoleFrFRGenApiService;
import org.computate.scolaire.frFR.enfant.EnfantScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.gardien.GardienScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.html.part.PartHtmlFrFRGenApiService;
import org.computate.scolaire.frFR.inscription.InscriptionScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.inscription.design.DesignInscriptionFrFRGenApiService;
import org.computate.scolaire.frFR.java.LocalDateSerializer;
import org.computate.scolaire.frFR.java.LocalTimeSerializer;
import org.computate.scolaire.frFR.java.ZonedDateTimeSerializer;
import org.computate.scolaire.frFR.mere.MereScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.paiement.PaiementScolaire;
import org.computate.scolaire.frFR.paiement.PaiementScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.pere.PereScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.recherche.ListeRecherche;
import org.computate.scolaire.frFR.requete.RequeteSiteFrFR;
import org.computate.scolaire.frFR.saison.SaisonScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.session.SessionScolaireFrFRGenApiService;
import org.computate.scolaire.frFR.utilisateur.UtilisateurSiteFrFRGenApiService;

import com.fasterxml.jackson.databind.module.SimpleModule;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.net.JksOptions;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import io.vertx.ext.auth.oauth2.providers.KeycloakAuth;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.Status;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.api.contract.openapi3.impl.AppOpenAPI3RouterFactory;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.BridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import net.authorize.Environment;
import net.authorize.api.contract.v1.ArrayOfTransactionSummaryType;
import net.authorize.api.contract.v1.BatchDetailsType;
import net.authorize.api.contract.v1.CustomerProfileIdType;
import net.authorize.api.contract.v1.GetSettledBatchListRequest;
import net.authorize.api.contract.v1.GetSettledBatchListResponse;
import net.authorize.api.contract.v1.GetTransactionListRequest;
import net.authorize.api.contract.v1.GetTransactionListResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.Paging;
import net.authorize.api.contract.v1.TransactionListOrderFieldEnum;
import net.authorize.api.contract.v1.TransactionListSorting;
import net.authorize.api.contract.v1.TransactionSummaryType;
import net.authorize.api.controller.GetSettledBatchListController;
import net.authorize.api.controller.GetTransactionListController;
import net.authorize.api.controller.base.ApiOperationBase;

/**
 * NomCanonique.enUS: org.computate.scolaire.enUS.vertx.AppVertx
 * enUS: A Java class to start the Vert.x application as a main method. 
 */    
public class AppliVertx extends AppliVertxGen<AbstractVerticle> {

	/**
	 * enUS: A SQL query for creating a database table "c" to store any type of object in the application. 
	 * r: nom_canonique
	 * r.enUS: canonical_name
	 * r: ajour
	 * r.enUS: current
	 * r: nom_canonique
	 * r.enUS: canonical_name
	 * r: cree
	 * r.enUS: created
	 * r: modifie
	 * r.enUS: modified
	 * r: id_utilisateur
	 * r.enUS: user_id
	 */
	public static final String SQL_createTableC = "create table if not exists c(pk bigserial primary key, ajour boolean, nom_canonique text, cree timestamp with time zone default now(), modifie timestamp with time zone default now(), id_utilisateur text); ";

	/**
	 * enUS: A SQL query for creating a unique index on the "c" table based on the pk, canonical_name, and user_id fields for faster lookup. 
	 * r: nom_canonique
	 * r.enUS: canonical_name
	 * r: ajour
	 * r.enUS: current
	 * r: nom_canonique
	 * r.enUS: canonical_name
	 * r: cree
	 * r.enUS: created
	 * r: modifie
	 * r.enUS: modified
	 * r: id_utilisateur
	 * r.enUS: user_id
	 * r: utilisateur
	 * r.enUS: user
	 */
	public static final String SQL_uniqueIndexC = "create unique index if not exists c_index_utilisateur on c(pk, nom_canonique, id_utilisateur); ";

	/**
	 * enUS: A SQL query for creating a database table "a" to store relations (like entity relations) between one other record in the "c" table with another record in the "c" table. 
	 * r: entite
	 * r.enUS: entity
	 * r: actuel
	 * r.enUS: current
	 * r: cree
	 * r.enUS: created
	 * r: modifie
	 * r.enUS: modified
	 */
	public static final String SQL_createTableA = "create table if not exists a(pk bigserial primary key, pk1 bigint, pk2 bigint, entite1 text, entite2 text, actuel boolean, cree timestamp with time zone default now(), modifie timestamp with time zone default now(), foreign key(pk1) references c(pk), foreign key(pk2) references c(pk)); ";

	/**
	 * enUS: A SQL query for creating a database table "d" to store String values to define fields in an instance of a class based on a record in the "c" table. 
	 * r: actuel
	 * r.enUS: current
	 * r: cree
	 * r.enUS: created
	 * r: modifie
	 * r.enUS: modified
	 * r: valeur
	 * r.enUS: value
	 * r: chemin
	 * r.enUS: path
	 */
	public static final String SQL_createTableD = "create table if not exists d(pk bigserial primary key, chemin text, valeur text, actuel boolean, cree timestamp with time zone default now(), modifie timestamp with time zone default now(), pk_c bigint, foreign key(pk_c) references c(pk)); ";

	/**
	 * enUS: Concatenate all of the SQL together to execute when the server starts. 
	 * Var.enUS: SQL_initAll
	 */
	public static final String SQL_initTout = SQL_createTableC + SQL_uniqueIndexC + SQL_createTableA + SQL_createTableD;

	/**
	 * enUS: A io.vertx.ext.jdbc.JDBCClient for connecting to the relational database PostgreSQL. 
	 */
	private JDBCClient jdbcClient;

	/**
	 * enUS: A site context object for storing information about the entire site in English. 
	 * Var.enUS: siteContextEnUS
	 */
	SiteContexteFrFR siteContexteFrFR;

	/**
	 * enUS: For logging information and errors in the application. 
	 * r: AppliVertx
	 * r.enUS: AppVertx
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AppliVertx.class);

	/**
	 * r: CoureurVertx
	 * r.enUS: RunnerVertx
	 * r: AppliVertx
	 * r.enUS: AppVertx
	 * enUS: The main method for the Vert.x application that runs the Vert.x Runner class
	 */
	public static void main(String[] args) {
		CoureurVertx.run(AppliVertx.class);
	}

	/**
	 * Param1.var.enUS: startPromise
	 * r: SiteContexteFrFR
	 * r.enUS: SiteContextEnUS
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: initLoin
	 * r.enUS: initDeep
	 * r: demarrerPromise
	 * r.enUS: startPromise
	 * r: configurerDonnees
	 * r.enUS: configureData
	 * r: configurerCluster
	 * r.enUS: configureCluster
	 * r: configurerOpenApi
	 * r.enUS: configureOpenApi
	 * r: configurerControlesSante
	 * r.enUS: configureHealthChecks
	 * r: configurerExecuteurTravailleurPartage
	 * r.enUS: configureSharedWorkerExecutor
	 * r: configurerWebsockets
	 * r.enUS: configureWebsockets
	 * r: configurerMail
	 * r.enUS: configureEmail
	 * r: demarrerServeur
	 * r.enUS: startServer
	 * r: etapesPromises
	 * r.enUS: promiseSteps
	 * r: configurerAuthorizeNet
	 * r.enUS: configureAuthorizeNet
	 * 
	 * enUS: This is called by Vert.x when the verticle instance is deployed. 
	 * enUS: Initialize a new site context object for storing information about the entire site in English. 
	 * enUS: Setup the startPromise to handle the configuration steps and starting the server. 
	 */
	@Override
	public void start(Promise<Void> demarrerPromise) throws Exception {

		siteContexteFrFR = new SiteContexteFrFR();
		siteContexteFrFR.setVertx(vertx);
		siteContexteFrFR.initLoinSiteContexteFrFR();

		Future<Void> etapesPromises = configurerDonnees().future().compose(a -> 
			configurerCluster().future().compose(b -> 
				configurerOpenApi().future().compose(c -> 
					configurerControlesSante().future().compose(d -> 
						configurerExecuteurTravailleurPartage().future().compose(e -> 
							configurerWebsockets().future().compose(f -> 
								configurerMail().future().compose(g -> 
									configurerAuthorizeNet().future().compose(h -> 
										demarrerServeur().future()
									)
								)
							)
						)
					)
				)
			)
		);
		etapesPromises.setHandler(demarrerPromise);
	}

	/**
	 * Var.enUS: configureData
	 * 
	 * Val.ConnectionError.enUS:Could not open the database client connection. 
	 * Val.ErreurConnexion.frFR:Impossible d'ouvrir la connexion du client de base de données. 
	 * Val.ConnectionSuccess.enUS:The database client connection was successful. 
	 * Val.SuccesConnexion.frFR:La connexion du client de base de données a réussi. 
	 * 
	 * Val.InitError.enUS:Could not initialize the database tables. 
	 * Val.ErreurInit.frFR:Impossible d'initialiser les tables de la base de données. 
	 * Val.InitSuccess.enUS:The database tables were created successfully. 
	 * Val.SuccesInit.frFR:Les tables de base de données ont été créées avec succès. 
	 * 
	 * enUS: Configure shared database connections across the cluster for massive scaling of the application. 
	 * enUS: Return a promise that configures a shared database client connection. 
	 * enUS: Load the database configuration into a shared io.vertx.ext.jdbc.JDBCClient for a scalable, clustered datasource connection pool. 
	 * enUS: Initialize the database tables if not already created for the first time. 
	 * 
	 * r: configurerDonnees
	 * r.enUS: configureData
	 * r: ErreurConnexion
	 * r.enUS: ConnectionError
	 * r: SuccesConnexion
	 * r.enUS: ConnectionSuccess
	 * r: ErreurInit
	 * r.enUS: InitError
	 * r: SuccesInit
	 * r.enUS: InitSuccess
	 * r: initTout
	 * r.enUS: initAll
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: getJdbcClassePilote
	 * r.enUS: getJdbcDriverClass
	 * r: getJdbcUtilisateur
	 * r.enUS: getJdbcUsername
	 * r: getJdbcMotDePasse
	 * r.enUS: getJdbcPassword
	 * r: getJdbcTailleMaxPiscine
	 * r.enUS: getJdbcMaxPoolSize
	 * r: getJdbcTailleInitialePiscine
	 * r.enUS: getJdbcInitialPoolSize
	 * r: getJdbcTailleMinPiscine
	 * r.enUS: getJdbcMinPoolSize
	 * r: getJdbcMaxDeclarationsParConnexion
	 * r.enUS: getJdbcMaxStatementsPerConnection
	 * r: getJdbcMaxDeclarations
	 * r.enUS: getJdbcMaxStatements
	 * r: getJdbcTempsInactiviteMax
	 * r.enUS: getJdbcMaxIdleTime
	 * r: ClientSql
	 * r.enUS: SqlClient
	 */
	private Promise<Void> configurerDonnees() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();

		JsonObject jdbcConfig = new JsonObject();
		if (StringUtils.isNotEmpty(configSite.getJdbcUrl()))
			jdbcConfig.put("url", configSite.getJdbcUrl());
		if (StringUtils.isNotEmpty(configSite.getJdbcClassePilote()))
			jdbcConfig.put("driver_class", configSite.getJdbcClassePilote());
		if (StringUtils.isNotEmpty(configSite.getJdbcUtilisateur()))
			jdbcConfig.put("user", configSite.getJdbcUtilisateur());
		if (StringUtils.isNotEmpty(configSite.getJdbcMotDePasse()))
			jdbcConfig.put("password", configSite.getJdbcMotDePasse());
		if (configSite.getJdbcTailleMaxPiscine() != null)
			jdbcConfig.put("max_pool_size", configSite.getJdbcTailleMaxPiscine());
		if (configSite.getJdbcTailleInitialePiscine() != null)
			jdbcConfig.put("initial_pool_size", configSite.getJdbcTailleInitialePiscine());
		if (configSite.getJdbcTailleMinPiscine() != null)
			jdbcConfig.put("min_pool_size", configSite.getJdbcTailleMinPiscine());
		if (configSite.getJdbcMaxDeclarations() != null)
			jdbcConfig.put("max_statements", configSite.getJdbcMaxDeclarations());
		if (configSite.getJdbcMaxDeclarationsParConnexion() != null)
			jdbcConfig.put("max_statements_per_connection", configSite.getJdbcMaxDeclarationsParConnexion());
		if (configSite.getJdbcTempsInactiviteMax() != null)
			jdbcConfig.put("max_idle_time", configSite.getJdbcTempsInactiviteMax());
		jdbcConfig.put("castUUID", false);
		jdbcConfig.put("castDateTime", false);
		jdbcConfig.put("castTime", false);
		jdbcConfig.put("castDate", false);
		jdbcClient = JDBCClient.createShared(vertx, jdbcConfig);

		siteContexteFrFR.setClientSql(jdbcClient);

		jdbcClient.getConnection(a -> {
			if (a.failed()) {
				LOGGER.error(configurerDonneesErreurConnexion, a.cause());
				promise.fail(a.cause());
			} else {
				LOGGER.info(configurerDonneesSuccesConnexion);
				SQLConnection connection = a.result();
				connection.execute(SQL_initTout, create -> {
					connection.close();
					if (create.failed()) {
						LOGGER.error(configurerDonneesErreurInit, create.cause());
						promise.fail(create.cause());
					} else {
						LOGGER.info(configurerDonneesSuccesInit);
						promise.complete();
					}
				});
			}
		});

		return promise;
	}

	/**  
	 * Var.enUS: configureCluster
	 * 
	 * Val.DataError.enUS:Could not configure the shared cluster data. 
	 * Val.ErreurDonnees.frFR:Impossible de configurer les données du cluster partagé.
	 * Val.DataSuccess.enUS:The shared cluster data was configured successfully. 
	 * Val.SuccesDonnees.frFR:Les données du cluster partagé ont été configurées avec succès. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: donneesPartagees
	 * r.enUS: sharedData
	 * r: donneesCluster
	 * r.enUS: clusterData
	 * r: configurerCluster
	 * r.enUS: configureCluster
	 * r: ErreurDonnees
	 * r.enUS: DataError
	 * r: SuccesDonnees
	 * r.enUS: DataSuccess
	 * 
	 * enUS: Configure shared data across the cluster for massive scaling of the application. 
	 * enUS: Return a promise that configures a shared cluster data. 
	 */
	private Promise<Void> configurerCluster() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();
		SharedData donneesPartagees = vertx.sharedData();
		donneesPartagees.getClusterWideMap("donneesCluster", res -> {
			if (res.succeeded()) {
				AsyncMap<Object, Object> donneesCluster = res.result();
				donneesCluster.put("configSite", configSite, resPut -> {
					if (resPut.succeeded()) {
						LOGGER.info(configurerClusterSuccesDonnees);
						promise.complete();
					} else {
						LOGGER.error(configurerClusterErreurDonnees, res.cause());
						promise.fail(res.cause());
					}
				});
			} else {
				LOGGER.error(configurerClusterErreurDonnees, res.cause());
				promise.fail(res.cause());
			}
		});
		return promise;
	}

	/**
	 * Var.enUS: configureOpenApi
	 * 
	 * enUS: Configure the connection to the auth server and setup the routes based on the OpenAPI definition. 
	 * enUS: Setup a callback route when returning from the auth server after successful authentication. 
	 * enUS: Setup a logout route for logging out completely of the application. 
	 * enUS: Return a promise that configures the authentication server and OpenAPI. 
	 * 
	 * Val.Error.enUS:Could not configure the auth server and API. 
	 * Val.Erreur.frFR:Impossible de configurer le serveur auth et le API.
	 * Val.Success.enUS:The auth server and API was configured successfully. 
	 * Val.Succes.frFR:Le serveur auth et le API ont été configurées avec succès. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: siteNomHote
	 * r.enUS: siteHostName
	 * r: routeur
	 * r.enUS: router
	 * r: usineRouteur
	 * r.enUS: routerFactory
	 * r: UsineRouteur
	 * r.enUS: RouterFactory
	 * r: getAuthRoyaume
	 * r.enUS: getAuthRealm
	 * r: getAuthRessource
	 * r.enUS: getAuthResource
	 * r: getAuthSslRequis
	 * r.enUS: getAuthSslRequired
	 * r: getSiteNomHote
	 * r.enUS: getSiteHostName
	 * r: getSiteUrlBase
	 * r.enUS: getSiteBaseUrl
	 * r: /deconnexion
	 * r.enUS: /logout
	 * r: Erreur
	 * r.enUS: Error
	 * r: Succes
	 * r.enUS: Success
	 * r: configurerOpenApi
	 * r.enUS: configureOpenApi
	 * r: openapi3-frFR.yaml
	 * r.enUS: openapi3-enUS.yaml
	 * r: /ecole
	 * r.enUS: /school
	 * r: "computate-scolaire-frFR-session"
	 * r.enUS: "computate-scolaire-enUS-session"
	 */
	private Promise<Void> configurerOpenApi() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();
		Router routeur = Router.router(vertx);

		AppOpenAPI3RouterFactory.create(vertx, routeur, "openapi3-frFR.yaml", ar -> {
			if (ar.succeeded()) {
				AppOpenAPI3RouterFactory usineRouteur = ar.result();
				usineRouteur.mountServicesFromExtensions();
				siteContexteFrFR.setUsineRouteur(usineRouteur);

				JsonObject keycloakJson = new JsonObject() {
					{
						put("realm", configSite.getAuthRoyaume());
						put("resource", configSite.getAuthRessource());
						put("auth-server-url", configSite.getAuthUrl());
						put("ssl-required", configSite.getAuthSslRequis());
						put("use-resource-role-mappings", false);
						put("bearer-only", false);
						put("enable-basic-auth", false);
						put("expose-token", true);
						put("credentials", new JsonObject().put("secret", configSite.getAuthSecret()));
						put("connection-pool-size", 20);
						put("disable-trust-manager", false);
						put("allow-any-hostname", false);
						put("policy-enforcer", new JsonObject());
						put("redirect-rewrite-rules", new JsonObject().put("^(.*)$", "$1"));
					}
				};

				String siteNomHote = configSite.getSiteNomHote();
				Integer sitePort = configSite.getSitePort();
				String siteUrlBase = configSite.getSiteUrlBase();
				OAuth2Auth authFournisseur = KeycloakAuth.create(vertx, OAuth2FlowType.AUTH_CODE, keycloakJson);

				routeur.route().handler(CookieHandler.create());
				LocalSessionStore sessionStore = LocalSessionStore.create(vertx);
//				ClusteredSessionStore sessionStore = ClusteredSessionStore.create(vertx, "computate-scolaire-frFR-session");
				SessionHandler sessionHandler = SessionHandler.create(sessionStore);
				sessionHandler.setAuthProvider(authFournisseur);
				routeur.route().handler(sessionHandler);

//				routeur.route().handler(UserSessionHandler.create(authFournisseur));

				OAuth2AuthHandler gestionnaireAuth = OAuth2AuthHandler.create(authFournisseur, siteUrlBase + "/callback");

				gestionnaireAuth.setupCallback(routeur.get("/callback"));

				routeur.get("/deconnexion").handler(rc -> {
					Session session = rc.session();
					if (session != null) {
						session.destroy();
					}
					rc.clearUser();
					rc.reroute("/ecole");
				});

				usineRouteur.addSecurityHandler("openIdConnect", gestionnaireAuth);

				usineRouteur.initRouter();

				LOGGER.info(configurerOpenApiSucces);
				promise.complete();
			} else {
				LOGGER.error(configurerOpenApiErreur, ar.cause());
				promise.fail(ar.cause());
			}
		});
		return promise;
	}

	/**
	 * Var.enUS: configureSharedWorkerExecutor
	 * 
	 * enUS: Configure a shared worker executor for running blocking tasks in the background. 
	 * enUS: Return a promise that configures the shared worker executor. 
	 * 
	 * r: executeurTravailleur
	 * r.enUS: workerExecutor
	 * r: ExecuteurTravailleur
	 * r.enUS: WorkerExecutor
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 */
	private Promise<Void> configurerExecuteurTravailleurPartage() {
		Promise<Void> promise = Promise.promise();

		WorkerExecutor executeurTravailleur = vertx.createSharedWorkerExecutor("WorkerExecutor");
		siteContexteFrFR.setExecuteurTravailleur(executeurTravailleur);
		promise.complete();
		return promise;
	}

	/**
	 * Var.enUS: configureHealthChecks
	 * 
	 * Val.ErrorDatabase.enUS:The database is not configured properly. 
	 * Val.ErreurBaseDeDonnees.frFR:La base de données n'est pas configurée correctement. 
	 * 
	 * Val.EmptySolr.enUS:The Solr search engine is empty. 
	 * Val.VideSolr.frFR:Le moteur de recherche Solr est vide. 
	 * 
	 * Val.ErrorSolr.enUS:The Solr search engine is not configured properly. 
	 * Val.ErreurSolr.frFR:Le moteur de recherche Solr n'est pas configuré correctement. 
	 * 
	 * Val.ErrorVertx.enUS:The Vert.x application is not configured properly. 
	 * Val.ErreurVertx.frFR:L'application Vert.x n'est pas configuré correctement. 
	 * 
	 * enUS: Configure health checks for the status of the website and it's dependent services. 
	 * enUS: Return a promise that configures the health checks. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: UsineRouteur
	 * r.enUS: RouterFactory
	 * r: gestionnaireControlesSante
	 * r.enUS: healthCheckHandler
	 * r: baseDeDonnees
	 * r.enUS: database
	 * r: configurerControlesSanteErreurBaseDeDonnees
	 * r.enUS: configureHealthChecksErrorDatabase
	 * r: configurerControlesSanteErreurSolr
	 * r.enUS: configureHealthChecksErrorSolr
	 * r: configurerControlesSanteVideSolr
	 * r.enUS: configureHealthChecksEmptySolr
	 * r: configurerControlesSanteErreurVertx
	 * r.enUS: configureHealthChecksErrorVertx
	 * r: ClientSolr
	 * r.enUS: SolrClient
	 * r: ClientSql
	 * r.enUS: SqlClient
	 */
	private Promise<Void> configurerControlesSante() {
		Promise<Void> promise = Promise.promise();
		Router siteRouteur = siteContexteFrFR.getUsineRouteur().getRouter();
		HealthCheckHandler gestionnaireControlesSante = HealthCheckHandler.create(vertx);

		gestionnaireControlesSante.register("baseDeDonnees", 2000, a -> {
			siteContexteFrFR.getClientSql().queryWithParams("select current_timestamp"
					, new JsonArray(Arrays.asList())
					, selectCAsync
			-> {
				if(selectCAsync.succeeded()) {
					a.complete(Status.OK());
				} else {
					LOGGER.error(configurerControlesSanteErreurBaseDeDonnees, a.future().cause());
					promise.fail(a.future().cause());
				}
			});
		});
		gestionnaireControlesSante.register("solr", 2000, a -> {
			SolrQuery query = new SolrQuery();
			query.setQuery("*:*");
			try {
				QueryResponse r = siteContexteFrFR.getClientSolr().query(query);
				if(r.getResults().size() > 0)
					a.complete(Status.OK());
				else {
					LOGGER.error(configurerControlesSanteVideSolr, a.future().cause());
					promise.fail(a.future().cause());
				}
			} catch (SolrServerException | IOException e) {
				LOGGER.error(configurerControlesSanteErreurSolr, a.future().cause());
				promise.fail(a.future().cause());
			}
		});
		siteRouteur.get("/health").handler(gestionnaireControlesSante);
		promise.complete();
		return promise;
	}

	/**
	 * Var.enUS: configureWebsockets
	 * 
	 * enUS: Configure websockets for realtime messages. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: gestionnaireSockJs
	 * r.enUS: sockJsHandler
	 * r: GestionnaireSockJs
	 * r.enUS: SockJsHandler
	 * r: siteRouteur
	 * r.enUS: siteRouter
	 * r: usineRouteur
	 * r.enUS: routerFactory
	 * r: UsineRouteur
	 * r.enUS: RouterFactory
	 */
	private Promise<Void> configurerWebsockets() {
		Promise<Void> promise = Promise.promise();
		Router siteRouteur = siteContexteFrFR.getUsineRouteur().getRouter();
		BridgeOptions options = new BridgeOptions()
				.addOutboundPermitted(new PermittedOptions().setAddressRegex("websocket.*"));
		SockJSHandler gestionnaireSockJs = SockJSHandler.create(vertx);
		gestionnaireSockJs.bridge(options);
		siteRouteur.route("/eventbus/*").handler(gestionnaireSockJs);
		promise.complete();
		return promise;
	}

	/**
	 * Var.enUS: configureEmail
	 * 
	 * enUS: Configure sending email. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: MailHote
	 * r.enUS: EmailHost
	 * r: MailPort
	 * r.enUS: EmailPort
	 * r: MailSsl
	 * r.enUS: EmailSsl
	 * r: MailUtilisateur
	 * r.enUS: EmailUsername
	 * r: MailMotDePasse
	 * r.enUS: EmailPassword
	 */
	private Promise<Void> configurerMail() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();
		MailConfig config = new MailConfig();
		config.setHostname(configSite.getMailHote());
		config.setPort(configSite.getMailPort());
		config.setSsl(configSite.getMailSsl());
		config.setUsername(configSite.getMailUtilisateur());
		config.setPassword(configSite.getMailMotDePasse());
		MailClient mailClient = MailClient.createShared(vertx, config);
		siteContexteFrFR.setMailClient(mailClient);
		promise.complete();
		return promise;
	}

	/**
	 * Var.enUS: configureAuthorizeNet
	 * 
	 * enUS: Configure payments with Authorize.net. 
	 * 
	 * r: org.computate.scolaire.frFR.paiement.PaiementScolaireFrFRGenApiServiceImpl
	 * r.enUS: org.computate.scolaire.enUS.payment.SchoolPaymentEnUSGenApiServiceImpl
	 * r: PaiementScolaireFrFRGenApiService
	 * r.enUS: SchoolPaymentEnUSGenApiService
	 * r: setSiteContexte
	 * r.enUS: setSiteContext
	 * r: ExecuteurTravailleur
	 * r.enUS: WorkerExecutor
	 * r: initLoinRequeteSiteFrFR
	 * r.enUS: initDeepSiteRequestEnUS
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: RequeteSiteFrFR
	 * r.enUS: SiteRequestEnUS
	 * r: requeteSite
	 * r.enUS: siteRequest
	 * r: RequeteSite
	 * r.enUS: SiteRequest
	 * r: initLoinListeRecherche
	 * r.enUS: initDeepSearchList
	 * r: ListeRecherche
	 * r.enUS: SearchList
	 * r: initLoinPaiementScolaire
	 * r.enUS: initDeepSchoolPayment
	 * r: indexerPaiementScolaire
	 * r.enUS: indexSchoolPayment
	 * r: PaiementScolaire
	 * r.enUS: SchoolPayment
	 * r: "paiement créé"
	 * r.enUS: "payment created"
	 * r: setPaiementPar
	 * r.enUS: setPaymentBy
	 * r: paiement
	 * r.enUS: payment
	 * r: PaiementMontant
	 * r.enUS: PaymentAmount
	 * r: PaiementDate
	 * r.enUS: PaymentDate
	 * r: PaiementSysteme
	 * r.enUS: PaymentSystem
	 * r: setPeupler
	 * r.enUS: setPopulate
	 * r: "Commencer à peupler les transactions nouveaux. "
	 * r.enUS: "Start to populate the new transactions. "
	 * r: "Finir à peupler les transactions nouveaux. "
	 * r.enUS: "Finish populating the new transactions. "
	 */
	private Promise<Void> configurerAuthorizeNet() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();

		vertx.setPeriodic(1000 * 60 * 60 * 24, a -> {
			WorkerExecutor executeurTravailleur = siteContexteFrFR.getExecuteurTravailleur();
			executeurTravailleur.executeBlocking(
				blockingCodeHandler -> {
					LOGGER.info("Commencer à peupler les transactions nouveaux. ");
					try {
						RequeteSiteFrFR requeteSite = new RequeteSiteFrFR();
						requeteSite.setVertx(vertx);
						requeteSite.setSiteContexte_(siteContexteFrFR);
						requeteSite.setConfigSite_(siteContexteFrFR.getConfigSite());
						requeteSite.initLoinRequeteSiteFrFR(requeteSite);
		
						PaiementScolaireFrFRGenApiService service = new org.computate.scolaire.frFR.paiement.PaiementScolaireFrFRGenApiServiceImpl(siteContexteFrFR);
					
						ApiOperationBase.setEnvironment(Environment.PRODUCTION);
		
						MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
						String authorizeApiLoginId = configSite.getAuthorizeApiLoginId();
						String authorizeTransactionKey = configSite.getAuthorizeTransactionKey();
						merchantAuthenticationType.setName(authorizeApiLoginId);
						merchantAuthenticationType.setTransactionKey(authorizeTransactionKey);
						ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
		
						Paging paging = new Paging();
						paging.setLimit(100);
						paging.setOffset(1);
						DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
		
						GetSettledBatchListRequest batchRequest = new GetSettledBatchListRequest();
						batchRequest.setMerchantAuthentication(merchantAuthenticationType);
						batchRequest.setFirstSettlementDate(datatypeFactory.newXMLGregorianCalendar(GregorianCalendar.from(LocalDate.now().minusDays(7).atStartOfDay(ZoneId.of("America/Denver")))));
						batchRequest.setLastSettlementDate(datatypeFactory.newXMLGregorianCalendar(GregorianCalendar.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.of("America/Denver")))));
		
						GetSettledBatchListController batchController = new GetSettledBatchListController(batchRequest);
						GetSettledBatchListController.setEnvironment(Environment.PRODUCTION);
						batchController.execute();
		
						GetSettledBatchListResponse batchResponse = batchController.getApiResponse();
		
						for(BatchDetailsType batch : batchResponse.getBatchList().getBatch()) {
							
							GetTransactionListRequest getRequest = new GetTransactionListRequest();
							getRequest.setMerchantAuthentication(merchantAuthenticationType);
							getRequest.setBatchId(batch.getBatchId());
			
							getRequest.setPaging(paging);
			
							TransactionListSorting sorting = new TransactionListSorting();
							sorting.setOrderBy(TransactionListOrderFieldEnum.SUBMIT_TIME_UTC);
							sorting.setOrderDescending(true);
			
							getRequest.setSorting(sorting);
			
							GetTransactionListController controller = new GetTransactionListController(getRequest);
							GetTransactionListController.setEnvironment(Environment.PRODUCTION);
							controller.execute();
			
							GetTransactionListResponse getResponse = controller.getApiResponse();
							if (getResponse != null) {
			
								if (getResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {
									for(TransactionSummaryType transaction : Optional.ofNullable(getResponse).map(GetTransactionListResponse::getTransactions).map(ArrayOfTransactionSummaryType::getTransaction).orElse(Arrays.asList())) {
			
										transaction.getProfile().getCustomerProfileId();
										XMLGregorianCalendar submitTimeLocal = transaction.getSubmitTimeLocal();
										PaiementScolaire paiement = new PaiementScolaire();
										paiement.setRequeteSite_(requeteSite);
										paiement.setPaiementMontant(transaction.getSettleAmount());
										paiement.setPaiementDate(submitTimeLocal.toGregorianCalendar().getTime());
										paiement.setPaiementSysteme(true);
										paiement.setTransactionId(transaction.getTransId());
										paiement.setCustomerProfileId(Optional.ofNullable(transaction.getProfile()).map(CustomerProfileIdType::getCustomerProfileId).orElse(null));
										paiement.setTransactionStatus(transaction.getTransactionStatus());
										paiement.setPaiementPar(String.format("%s %s", transaction.getFirstName(), transaction.getLastName()).trim());
			
										if(transaction.getTransId() != null) {
											ListeRecherche<PaiementScolaire> listeRecherche = new ListeRecherche<PaiementScolaire>();
											listeRecherche.setPeupler(true);
											listeRecherche.setQuery("*:*");
											listeRecherche.setC(PaiementScolaire.class);
											listeRecherche.addFilterQuery("transactionId_indexed_string:" + ClientUtils.escapeQueryChars(transaction.getTransId()));
											listeRecherche.initLoinListeRecherche(requeteSite);
			
											if(listeRecherche.size() == 0) {
												service.postPaiementScolaire(JsonObject.mapFrom(paiement), null, handler -> {
													if(handler.succeeded()) {
														LOGGER.info("paiement créé. ");
													} else {
														LOGGER.error(handler.cause());
													}
												});
											}
										}
										break;
									}
								}
							}
							break;
						}
					} catch (Exception e) {
						ExceptionUtils.rethrow(e);
					}
					LOGGER.info("Finir à peupler les transactions nouveaux. ");
				}, resultHandler -> {
				}
			);
		});

		promise.complete();
		return promise;
	}

	/**
	 * Var.enUS: startServer
	 * 
	 * Val.ErrorServer.enUS:The server is not configured properly. 
	 * Val.ErreurServeur.frFR:Le serveur n'est pas configurée correctement. 
	 * Val.SuccessServer.enUS:The HTTP server is running: %s:%s
	 * Val.SuccesServeur.frFR:Le serveur HTTP est démarré : %s:%s
	 * Val.BeforeServer.enUS:HTTP server starting: %s://%s:%s
	 * Val.AvantServeur.frFR:Le serveur HTTP est démarré : %s:%s
	 * 
	 * enUS: Start the Vert.x server. 
	 * enUS: Démarrer le serveur Vert.x. 
	 * 
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ConfigSite
	 * r.enUS: SiteConfig
	 * r: configSite
	 * r.enUS: siteConfig
	 * r: UsineRouteur
	 * r.enUS: RouterFactory
	 * r: siteRouteur
	 * r.enUS: siteRouter
	 * r: gestionnaireStatic
	 * r.enUS: staticHandler
	 * r: siteNomHote
	 * r.enUS: siteHostName
	 * r: demarrerServeurErreurServeur
	 * r.enUS: startServerErrorServer
	 * r: demarrerServeurSuccesServeur
	 * r.enUS: startServerSuccessServer
	 * r: demarrerServeurAvantServeur
	 * r.enUS: startServerBeforeServer
	 * r: getSiteNomHote
	 * r.enUS: getSiteHostName
	 * r: getSslJksChemin
	 * r.enUS: getSslJksPath
	 * r: getSslJksMotDePasse
	 * r.enUS: getSslJksPassword
	 * r: ClusterFrFRGenApiService
	 * r.enUS: ClusterEnUSGenApiService
	 * r: EcoleFrFRGenApiService
	 * r.enUS: SchoolEnUSGenApiService
	 * r: UtilisateurSiteFrFRGenApiService
	 * r.enUS: SiteUserEnUSGenApiService
	 * r: AnneeScolaireFrFRGenApiService
	 * r.enUS: SchoolYearEnUSGenApiService
	 * r: SaisonScolaireFrFRGenApiService
	 * r.enUS: SchoolSeasonEnUSGenApiService
	 * r: SessionScolaireFrFRGenApiService
	 * r.enUS: SchoolSessionEnUSGenApiService
	 * r: AgeScolaireFrFRGenApiService
	 * r.enUS: SchoolAgeEnUSGenApiService
	 * r: BlocScolaireFrFRGenApiService
	 * r.enUS: SchoolBlockEnUSGenApiService
	 * r: InscriptionScolaireFrFRGenApiService
	 * r.enUS: SchoolEnrollmentEnUSGenApiService
	 * r: EnfantScolaireFrFRGenApiService
	 * r.enUS: SchoolChildEnUSGenApiService
	 * r: MereScolaireFrFRGenApiService
	 * r.enUS: SchoolMomEnUSGenApiService
	 * r: PereScolaireFrFRGenApiService
	 * r.enUS: SchoolDadEnUSGenApiService
	 * r: ContactScolaireFrFRGenApiService
	 * r.enUS: SchoolContactEnUSGenApiService
	 * r: GardienScolaireFrFRGenApiService
	 * r.enUS: SchoolGuardianEnUSGenApiService
	 * r: PaiementScolaireFrFRGenApiService
	 * r.enUS: SchoolPaymentEnUSGenApiService
	 * r: DesignInscriptionFrFRGenApiService
	 * r.enUS: EnrollmentDesignEnUSGenApiService
	 * r: PartHtmlFrFRGenApiService
	 * r.enUS: HtmlPartEnUSGenApiService
	 * r: enregistrerService
	 * r.enUS: registerService
	 */   
	private Promise<Void> demarrerServeur() {
		ConfigSite configSite = siteContexteFrFR.getConfigSite();
		Promise<Void> promise = Promise.promise();

		ClusterFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		EcoleFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		UtilisateurSiteFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		AnneeScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		SaisonScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		SessionScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		AgeScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		BlocScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		InscriptionScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		EnfantScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		MereScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		PereScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		GardienScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		PaiementScolaireFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		DesignInscriptionFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);
		PartHtmlFrFRGenApiService.enregistrerService(siteContexteFrFR, vertx);

		Router siteRouteur = siteContexteFrFR.getUsineRouteur().getRouter();

		StaticHandler gestionnaireStatic = StaticHandler.create().setCachingEnabled(false).setFilesReadOnly(true);
		if("scolaire-dev.computate.org".equals(configSite.getSiteNomHote())) {
			gestionnaireStatic.setAllowRootFileSystemAccess(true);
			gestionnaireStatic.setWebRoot("/usr/local/src/computate-scolaire-static");
		}
		siteRouteur.route("/static/*").handler(gestionnaireStatic);

		SimpleModule module = new SimpleModule();
		module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());
		module.addSerializer(LocalDate.class, new LocalDateSerializer());
		module.addSerializer(LocalTime.class, new LocalTimeSerializer());
		Json.mapper.registerModule(module);

		String siteNomHote = configSite.getSiteNomHote();
		Integer sitePort = configSite.getSitePort();
		HttpServerOptions options = new HttpServerOptions();
		if(new File(configSite.getSslJksChemin()).exists()) {
			options.setKeyStoreOptions(new JksOptions().setPath(configSite.getSslJksChemin()).setPassword(configSite.getSslJksMotDePasse()));
			options.setSsl(true);
		}
		options.setPort(sitePort);

		LOGGER.info(String.format(demarrerServeurAvantServeur, "https", siteNomHote, sitePort));
		vertx.createHttpServer(options).requestHandler(siteRouteur).listen(ar -> {
			if (ar.succeeded()) {
				LOGGER.info(String.format(demarrerServeurSuccesServeur, "*", sitePort));
				promise.complete();
			} else {
				LOGGER.error(demarrerServeurErreurServeur, ar.cause());
				promise.fail(ar.cause());
			}
		});

		return promise;
	}

	/**
	 * Param1.var.enUS: stopPromise
	 * r: SiteContexteFrFR
	 * r.enUS: SiteContextEnUS
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: initLoin
	 * r.enUS: initDeep
	 * r: arreterPromise
	 * r.enUS: stopPromise
	 * r: fermerDonnees
	 * r.enUS: closeData
	 * r: etapesPromises
	 * r.enUS: promiseSteps
	 * 
	 * enUS: This is called by Vert.x when the verticle instance is undeployed. 
	 * enUS: Setup the stopPromise to handle tearing down the server. 
	 */
	@Override
	public void stop(Promise<Void> arreterPromise) throws Exception {
		Promise<Void> etapesPromises = fermerDonnees();
		etapesPromises.future().setHandler(arreterPromise);
	}

	/**
	 * Var.enUS: closeData
	 * Val.Error.enUS:Could not close the database client connection. 
	 * Val.Erreur.frFR:Impossible de fermer la connexion du client de base de données. 
	 * Val.Success.enUS:The database client connextion was closed. 
	 * Val.Succes.frFR:La connexion client de la base de données a été fermée.
	 * r: fermerDonneesErreur
	 * r.enUS: closeDataError
	 * r: fermerDonneesSucces
	 * r.enUS: closeDataSuccess
	 * r: siteContexteFrFR
	 * r.enUS: siteContextEnUS
	 * r: ClientSql
	 * r.enUS: SqlClient
	 * 
	 * enUS: Return a promise to close the database client connection. 
	 */        
	private Promise<Void> fermerDonnees() {
		Promise<Void> promise = Promise.promise();
		SQLClient clientSql = siteContexteFrFR.getClientSql();

		if(clientSql != null) {
			clientSql.close(a -> {
				if (a.failed()) {
					LOGGER.error(fermerDonneesErreur, a.cause());
					promise.fail(a.cause());
				} else {
					LOGGER.error(fermerDonneesSucces, a.cause());
					promise.complete();
				}
			});
		}
		return promise;
	}
}
