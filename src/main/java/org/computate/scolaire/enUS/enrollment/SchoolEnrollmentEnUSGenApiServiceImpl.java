package org.computate.scolaire.enUS.enrollment;

import org.computate.scolaire.enUS.year.SchoolYearEnUSGenApiServiceImpl;
import org.computate.scolaire.enUS.year.SchoolYear;
import org.computate.scolaire.enUS.block.SchoolBlockEnUSGenApiServiceImpl;
import org.computate.scolaire.enUS.block.SchoolBlock;
import org.computate.scolaire.enUS.child.SchoolChildEnUSGenApiServiceImpl;
import org.computate.scolaire.enUS.child.SchoolChild;
import org.computate.scolaire.enUS.mom.SchoolMomEnUSGenApiServiceImpl;
import org.computate.scolaire.enUS.mom.SchoolMom;
import org.computate.scolaire.enUS.dad.SchoolDadEnUSGenApiServiceImpl;
import org.computate.scolaire.enUS.dad.SchoolDad;
import org.computate.scolaire.enUS.guardian.SchoolGuardianEnUSGenApiServiceImpl;
import org.computate.scolaire.enUS.guardian.SchoolGuardian;
import org.computate.scolaire.enUS.payment.SchoolPaymentEnUSGenApiServiceImpl;
import org.computate.scolaire.enUS.payment.SchoolPayment;
import org.computate.scolaire.enUS.user.SiteUserEnUSGenApiServiceImpl;
import org.computate.scolaire.enUS.user.SiteUser;
import org.computate.scolaire.enUS.config.SiteConfig;
import org.computate.scolaire.enUS.request.SiteRequestEnUS;
import org.computate.scolaire.enUS.contexte.SiteContextEnUS;
import org.computate.scolaire.enUS.user.SiteUser;
import org.computate.scolaire.enUS.request.api.ApiRequest;
import org.computate.scolaire.enUS.search.SearchResult;
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
import org.computate.scolaire.enUS.user.SiteUserEnUSGenApiServiceImpl;
import org.computate.scolaire.enUS.search.SearchList;
import org.computate.scolaire.enUS.writer.AllWriter;


/**
 * Translate: false
 * CanonicalName.frFR: org.computate.scolaire.frFR.inscription.InscriptionScolaireFrFRGenApiServiceImpl
 **/
public class SchoolEnrollmentEnUSGenApiServiceImpl implements SchoolEnrollmentEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SchoolEnrollmentEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "SchoolEnrollmentEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public SchoolEnrollmentEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// POST //

	@Override
	public void postSchoolEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest, body);

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							ApiRequest apiRequest = new ApiRequest();
							apiRequest.setRows(1);
							apiRequest.setNumFound(1L);
							apiRequest.initDeepApiRequest(siteRequest);
							siteRequest.setApiRequest_(apiRequest);
							postSchoolEnrollmentFuture(siteRequest, c -> {
								if(c.succeeded()) {
									SchoolEnrollment schoolEnrollment = c.result();
									apiRequestSchoolEnrollment(schoolEnrollment);
									postSchoolEnrollmentResponse(schoolEnrollment, d -> {
										if(d.succeeded()) {
											eventHandler.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("postSchoolEnrollment succeeded. "));
										} else {
											LOGGER.error(String.format("postSchoolEnrollment failed. ", d.cause()));
											errorSchoolEnrollment(siteRequest, eventHandler, d);
										}
									});
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public Future<SchoolEnrollment> postSchoolEnrollmentFuture(SiteRequestEnUS siteRequest, Handler<AsyncResult<SchoolEnrollment>> eventHandler) {
		Promise<SchoolEnrollment> promise = Promise.promise();
		try {
			createSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					SchoolEnrollment schoolEnrollment = a.result();
					sqlPOSTSchoolEnrollment(schoolEnrollment, b -> {
						if(b.succeeded()) {
							defineIndexSchoolEnrollment(schoolEnrollment, c -> {
								if(c.succeeded()) {
									eventHandler.handle(Future.succeededFuture(schoolEnrollment));
									promise.complete(schoolEnrollment);
								} else {
									errorSchoolEnrollment(siteRequest, null, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, null, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, null, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTSchoolEnrollment(SchoolEnrollment o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SQLConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			StringBuilder postSql = new StringBuilder();
			List<Object> postSqlParams = new ArrayList<Object>();

			if(jsonObject != null) {
				Set<String> entityVars = jsonObject.fieldNames();
				for(String entityVar : entityVars) {
					switch(entityVar) {
					case "yearKey":
						postSql.append(SiteContextEnUS.SQL_addA);
						postSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(jsonObject.getString(entityVar)), "yearKey", pk));
						break;
					case "blockKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							postSql.append(SiteContextEnUS.SQL_addA);
							postSqlParams.addAll(Arrays.asList("blockKeys", pk, "enrollmentKeys", l));
						}
						break;
					case "childKey":
						postSql.append(SiteContextEnUS.SQL_addA);
						postSqlParams.addAll(Arrays.asList("childKey", pk, "enrollmentKeys", Long.parseLong(jsonObject.getString(entityVar))));
						break;
					case "momKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							postSql.append(SiteContextEnUS.SQL_addA);
							postSqlParams.addAll(Arrays.asList("enrollmentKeys", l, "momKeys", pk));
						}
						break;
					case "dadKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							postSql.append(SiteContextEnUS.SQL_addA);
							postSqlParams.addAll(Arrays.asList("dadKeys", pk, "enrollmentKeys", l));
						}
						break;
					case "guardianKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							postSql.append(SiteContextEnUS.SQL_addA);
							postSqlParams.addAll(Arrays.asList("enrollmentKeys", l, "guardianKeys", pk));
						}
						break;
					case "paymentKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							postSql.append(SiteContextEnUS.SQL_addA);
							postSqlParams.addAll(Arrays.asList("enrollmentKey", l, "paymentKeys", pk));
						}
						break;
					case "userKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							postSql.append(SiteContextEnUS.SQL_addA);
							postSqlParams.addAll(Arrays.asList("enrollmentKeys", l, "userKeys", pk));
						}
						break;
					case "childCompleteName":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("childCompleteName", jsonObject.getString(entityVar), pk));
						break;
					case "childCompleteNamePreferred":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("childCompleteNamePreferred", jsonObject.getString(entityVar), pk));
						break;
					case "childBirthDate":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("childBirthDate", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "schoolAddress":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("schoolAddress", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentApproved":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentApproved", jsonObject.getBoolean(entityVar), pk));
						break;
					case "enrollmentImmunizations":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentImmunizations", jsonObject.getBoolean(entityVar), pk));
						break;
					case "familyMarried":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("familyMarried", jsonObject.getBoolean(entityVar), pk));
						break;
					case "familySeparated":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("familySeparated", jsonObject.getBoolean(entityVar), pk));
						break;
					case "familyDivorced":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("familyDivorced", jsonObject.getBoolean(entityVar), pk));
						break;
					case "familyAddress":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("familyAddress", jsonObject.getString(entityVar), pk));
						break;
					case "familyHowDoYouKnowTheSchool":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("familyHowDoYouKnowTheSchool", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSpecialConsiderations":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSpecialConsiderations", jsonObject.getString(entityVar), pk));
						break;
					case "childMedicalConditions":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("childMedicalConditions", jsonObject.getString(entityVar), pk));
						break;
					case "childPreviousSchoolsAttended":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("childPreviousSchoolsAttended", jsonObject.getString(entityVar), pk));
						break;
					case "childDescription":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("childDescription", jsonObject.getString(entityVar), pk));
						break;
					case "childObjectives":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("childObjectives", jsonObject.getString(entityVar), pk));
						break;
					case "childPottyTrained":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("childPottyTrained", jsonObject.getBoolean(entityVar), pk));
						break;
					case "enrollmentGroupName":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentGroupName", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentPaymentEachMonth":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentPaymentEachMonth", jsonObject.getBoolean(entityVar), pk));
						break;
					case "enrollmentPaymentComplete":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentPaymentComplete", jsonObject.getBoolean(entityVar), pk));
						break;
					case "customerProfileId":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("customerProfileId", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentChargeDate":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentChargeDate", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentParentNames":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentParentNames", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature1":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSignature1", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature2":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSignature2", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature3":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSignature3", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature4":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSignature4", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature5":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSignature5", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature6":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSignature6", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature7":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSignature7", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature8":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSignature8", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature9":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSignature9", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature10":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentSignature10", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentDate1":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentDate1", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate2":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentDate2", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate3":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentDate3", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate4":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentDate4", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate5":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentDate5", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate6":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentDate6", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate7":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentDate7", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate8":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentDate8", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate9":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentDate9", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate10":
						postSql.append(SiteContextEnUS.SQL_setD);
						postSqlParams.addAll(Arrays.asList("enrollmentDate10", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					}
				}
			}
			sqlConnection.queryWithParams(
					postSql.toString()
					, new JsonArray(postSqlParams)
					, postAsync
			-> {
				if(postAsync.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					eventHandler.handle(Future.failedFuture(new Exception(postAsync.cause())));
				}
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postSchoolEnrollmentResponse(SchoolEnrollment schoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = schoolEnrollment.getSiteRequest_();
		response200POSTSchoolEnrollment(schoolEnrollment, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								ApiRequest apiRequest = apiRequestSchoolEnrollment(schoolEnrollment);
								schoolEnrollment.apiRequestSchoolEnrollment();
								siteRequest.getVertx().eventBus().publish("websocketSchoolEnrollment", JsonObject.mapFrom(apiRequest).toString());
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200POSTSchoolEnrollment(SchoolEnrollment o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTImport //

	@Override
	public void putimportSchoolEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest, body);

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							ApiRequest apiRequest = new ApiRequest();
							apiRequest.setRows(1);
							apiRequest.setNumFound(1L);
							apiRequest.initDeepApiRequest(siteRequest);
							siteRequest.setApiRequest_(apiRequest);
							SQLConnection sqlConnection = siteRequest.getSqlConnection();
							sqlConnection.close(c -> {
								if(c.succeeded()) {
									WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
									workerExecutor.executeBlocking(
										blockingCodeHandler -> {
											sqlSchoolEnrollment(siteRequest, d -> {
												if(d.succeeded()) {
													try {
														listPUTImportSchoolEnrollment(apiRequest, siteRequest, e -> {
															if(e.succeeded()) {
																putimportSchoolEnrollmentResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		eventHandler.handle(Future.succeededFuture(f.result()));
																		LOGGER.info(String.format("putimportSchoolEnrollment succeeded. "));
																	} else {
																		LOGGER.error(String.format("putimportSchoolEnrollment failed. ", f.cause()));
																		errorSchoolEnrollment(siteRequest, eventHandler, f);
																	}
																});
															} else {
																blockingCodeHandler.handle(Future.failedFuture(e.cause()));
															}
														});
													} catch(Exception ex) {
												blockingCodeHandler.handle(Future.failedFuture(ex));
													}
												} else {
													blockingCodeHandler.handle(Future.failedFuture(d.cause()));
												}
											});
										}, resultHandler -> {
										}
									);
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void listPUTImportSchoolEnrollment(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		jsonArray.forEach(obj -> {
			JsonObject json = (JsonObject)obj;

			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSchoolEnrollment(siteContext, siteRequest.getOperationRequest(), json);
			siteRequest2.setSqlConnection(siteRequest.getSqlConnection());

			SearchList<SchoolEnrollment> searchList = new SearchList<SchoolEnrollment>();
			searchList.setStore(true);
			searchList.setQuery("*:*");
			searchList.setC(SchoolEnrollment.class);
			searchList.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
			searchList.initDeepForClass(siteRequest2);

			if(searchList.size() == 1) {
				SchoolEnrollment o = searchList.get(0);
				JsonObject json2 = new JsonObject();
				for(String f : json.fieldNames()) {
					json2.put("set" + StringUtils.capitalize(f), json.getValue(f));
				}
				for(String f : o.getSaves()) {
					if(!json.fieldNames().contains(f))
						json2.putNull("set" + StringUtils.capitalize(f));
				}
				siteRequest2.setJsonObject(json2);
				futures.add(
					patchSchoolEnrollmentFuture(o, a -> {
						if(a.succeeded()) {
							SchoolEnrollment schoolEnrollment = a.result();
							apiRequestSchoolEnrollment(schoolEnrollment);
						} else {
							errorSchoolEnrollment(siteRequest2, eventHandler, a);
						}
					})
				);
			} else {
				futures.add(
					postSchoolEnrollmentFuture(siteRequest2, a -> {
						if(a.succeeded()) {
							SchoolEnrollment schoolEnrollment = a.result();
							apiRequestSchoolEnrollment(schoolEnrollment);
						} else {
							errorSchoolEnrollment(siteRequest2, eventHandler, a);
						}
					})
				);
			}
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + jsonArray.size());
				response200PUTImportSchoolEnrollment(siteRequest, eventHandler);
			} else {
				errorSchoolEnrollment(apiRequest.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public void putimportSchoolEnrollmentResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		response200PUTImportSchoolEnrollment(siteRequest, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								ApiRequest apiRequest = siteRequest.getApiRequest_();
								siteRequest.getVertx().eventBus().publish("websocketSchoolEnrollment", JsonObject.mapFrom(apiRequest).toString());
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200PUTImportSchoolEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(JsonObject.mapFrom(apiRequest).encodePrettily()))));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeSchoolEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest, body);

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							ApiRequest apiRequest = new ApiRequest();
							apiRequest.setRows(1);
							apiRequest.setNumFound(1L);
							apiRequest.initDeepApiRequest(siteRequest);
							siteRequest.setApiRequest_(apiRequest);
							SQLConnection sqlConnection = siteRequest.getSqlConnection();
							sqlConnection.close(c -> {
								if(c.succeeded()) {
									WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
									workerExecutor.executeBlocking(
										blockingCodeHandler -> {
											sqlSchoolEnrollment(siteRequest, d -> {
												if(d.succeeded()) {
													try {
														listPUTMergeSchoolEnrollment(apiRequest, siteRequest, e -> {
															if(e.succeeded()) {
																putmergeSchoolEnrollmentResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		eventHandler.handle(Future.succeededFuture(f.result()));
																		LOGGER.info(String.format("putmergeSchoolEnrollment succeeded. "));
																	} else {
																		LOGGER.error(String.format("putmergeSchoolEnrollment failed. ", f.cause()));
																		errorSchoolEnrollment(siteRequest, eventHandler, f);
																	}
																});
															} else {
																blockingCodeHandler.handle(Future.failedFuture(e.cause()));
															}
														});
													} catch(Exception ex) {
												blockingCodeHandler.handle(Future.failedFuture(ex));
													}
												} else {
													blockingCodeHandler.handle(Future.failedFuture(d.cause()));
												}
											});
										}, resultHandler -> {
										}
									);
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void listPUTMergeSchoolEnrollment(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		jsonArray.forEach(obj -> {
			JsonObject json = (JsonObject)obj;

			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSchoolEnrollment(siteContext, siteRequest.getOperationRequest(), json);
			siteRequest2.setSqlConnection(siteRequest.getSqlConnection());

			SearchList<SchoolEnrollment> searchList = new SearchList<SchoolEnrollment>();
			searchList.setStore(true);
			searchList.setQuery("*:*");
			searchList.setC(SchoolEnrollment.class);
			searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
			searchList.initDeepForClass(siteRequest2);

			if(searchList.size() == 1) {
				SchoolEnrollment o = searchList.get(0);
				JsonObject json2 = new JsonObject();
				for(String f : json.fieldNames()) {
					json2.put("set" + StringUtils.capitalize(f), json.getValue(f));
				}
				for(String f : o.getSaves()) {
					if(!json.fieldNames().contains(f))
						json2.putNull("set" + StringUtils.capitalize(f));
				}
				siteRequest2.setJsonObject(json2);
				futures.add(
					patchSchoolEnrollmentFuture(o, a -> {
						if(a.succeeded()) {
							SchoolEnrollment schoolEnrollment = a.result();
							apiRequestSchoolEnrollment(schoolEnrollment);
						} else {
							errorSchoolEnrollment(siteRequest2, eventHandler, a);
						}
					})
				);
			} else {
				futures.add(
					postSchoolEnrollmentFuture(siteRequest2, a -> {
						if(a.succeeded()) {
							SchoolEnrollment schoolEnrollment = a.result();
							apiRequestSchoolEnrollment(schoolEnrollment);
						} else {
							errorSchoolEnrollment(siteRequest2, eventHandler, a);
						}
					})
				);
			}
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + jsonArray.size());
				response200PUTMergeSchoolEnrollment(siteRequest, eventHandler);
			} else {
				errorSchoolEnrollment(apiRequest.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public void putmergeSchoolEnrollmentResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		response200PUTMergeSchoolEnrollment(siteRequest, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								ApiRequest apiRequest = siteRequest.getApiRequest_();
								siteRequest.getVertx().eventBus().publish("websocketSchoolEnrollment", JsonObject.mapFrom(apiRequest).toString());
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200PUTMergeSchoolEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(JsonObject.mapFrom(apiRequest).encodePrettily()))));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopySchoolEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest, body);

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							ApiRequest apiRequest = new ApiRequest();
							apiRequest.setRows(1);
							apiRequest.setNumFound(1L);
							apiRequest.initDeepApiRequest(siteRequest);
							siteRequest.setApiRequest_(apiRequest);
							SQLConnection sqlConnection = siteRequest.getSqlConnection();
							sqlConnection.close(c -> {
								if(c.succeeded()) {
									aSearchSchoolEnrollment(siteRequest, false, true, null, d -> {
										if(d.succeeded()) {
											SearchList<SchoolEnrollment> listSchoolEnrollment = d.result();
											WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
											workerExecutor.executeBlocking(
												blockingCodeHandler -> {
													sqlSchoolEnrollment(siteRequest, e -> {
														if(e.succeeded()) {
															try {
																listPUTCopySchoolEnrollment(apiRequest, listSchoolEnrollment, f -> {
																	if(f.succeeded()) {
																		putcopySchoolEnrollmentResponse(listSchoolEnrollment, g -> {
																			if(g.succeeded()) {
																				eventHandler.handle(Future.succeededFuture(g.result()));
																				LOGGER.info(String.format("putcopySchoolEnrollment succeeded. "));
																			} else {
																				LOGGER.error(String.format("putcopySchoolEnrollment failed. ", g.cause()));
																				errorSchoolEnrollment(siteRequest, eventHandler, d);
																			}
																		});
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
										} else {
											errorSchoolEnrollment(siteRequest, eventHandler, d);
										}
									});
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void listPUTCopySchoolEnrollment(ApiRequest apiRequest, SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		listSchoolEnrollment.getList().forEach(o -> {
			futures.add(
				putcopySchoolEnrollmentFuture(siteRequest, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
						SchoolEnrollment schoolEnrollment = a.result();
						apiRequestSchoolEnrollment(schoolEnrollment);
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listSchoolEnrollment.size());
				if(listSchoolEnrollment.next()) {
					siteRequest.getVertx().eventBus().publish("websocketSchoolEnrollment", JsonObject.mapFrom(apiRequest).toString());
					listPUTCopySchoolEnrollment(apiRequest, listSchoolEnrollment, eventHandler);
				} else {
					response200PUTCopySchoolEnrollment(listSchoolEnrollment, eventHandler);
				}
			} else {
				errorSchoolEnrollment(listSchoolEnrollment.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SchoolEnrollment> putcopySchoolEnrollmentFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<SchoolEnrollment>> eventHandler) {
		Promise<SchoolEnrollment> promise = Promise.promise();
		try {

			jsonObject.put("saves", Optional.ofNullable(jsonObject.getJsonArray("saves")).orElse(new JsonArray()));
			JsonObject jsonPatch = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonObject("patch")).orElse(new JsonObject());
			jsonPatch.stream().forEach(o -> {
				jsonObject.put(o.getKey(), o.getValue());
				jsonObject.getJsonArray("saves").add(o.getKey());
			});

			createSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					SchoolEnrollment schoolEnrollment = a.result();
					sqlPUTCopySchoolEnrollment(schoolEnrollment, jsonObject, b -> {
						if(b.succeeded()) {
							defineSchoolEnrollment(schoolEnrollment, c -> {
								if(c.succeeded()) {
									attributeSchoolEnrollment(schoolEnrollment, d -> {
										if(d.succeeded()) {
											indexSchoolEnrollment(schoolEnrollment, e -> {
												if(e.succeeded()) {
													eventHandler.handle(Future.succeededFuture(schoolEnrollment));
													promise.complete(schoolEnrollment);
												} else {
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopySchoolEnrollment(SchoolEnrollment o, JsonObject jsonObject, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SQLConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			StringBuilder putSql = new StringBuilder();
			List<Object> putSqlParams = new ArrayList<Object>();

			if(jsonObject != null) {
				JsonArray entityVars = jsonObject.getJsonArray("saves");
				for(Integer i = 0; i < entityVars.size(); i++) {
					String entityVar = entityVars.getString(i);
					switch(entityVar) {
					case "yearKey":
						putSql.append(SiteContextEnUS.SQL_addA);
						putSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(jsonObject.getString(entityVar)), "yearKey", pk));
						break;
					case "blockKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							putSql.append(SiteContextEnUS.SQL_addA);
							putSqlParams.addAll(Arrays.asList("blockKeys", pk, "enrollmentKeys", l));
						}
						break;
					case "childKey":
						putSql.append(SiteContextEnUS.SQL_addA);
						putSqlParams.addAll(Arrays.asList("childKey", pk, "enrollmentKeys", Long.parseLong(jsonObject.getString(entityVar))));
						break;
					case "momKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							putSql.append(SiteContextEnUS.SQL_addA);
							putSqlParams.addAll(Arrays.asList("enrollmentKeys", l, "momKeys", pk));
						}
						break;
					case "dadKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							putSql.append(SiteContextEnUS.SQL_addA);
							putSqlParams.addAll(Arrays.asList("dadKeys", pk, "enrollmentKeys", l));
						}
						break;
					case "guardianKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							putSql.append(SiteContextEnUS.SQL_addA);
							putSqlParams.addAll(Arrays.asList("enrollmentKeys", l, "guardianKeys", pk));
						}
						break;
					case "paymentKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							putSql.append(SiteContextEnUS.SQL_addA);
							putSqlParams.addAll(Arrays.asList("enrollmentKey", l, "paymentKeys", pk));
						}
						break;
					case "userKeys":
						for(Long l : jsonObject.getJsonArray(entityVar).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							putSql.append(SiteContextEnUS.SQL_addA);
							putSqlParams.addAll(Arrays.asList("enrollmentKeys", l, "userKeys", pk));
						}
						break;
					case "childCompleteName":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("childCompleteName", jsonObject.getString(entityVar), pk));
						break;
					case "childCompleteNamePreferred":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("childCompleteNamePreferred", jsonObject.getString(entityVar), pk));
						break;
					case "childBirthDate":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("childBirthDate", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "schoolAddress":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("schoolAddress", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentApproved":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentApproved", jsonObject.getBoolean(entityVar), pk));
						break;
					case "enrollmentImmunizations":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentImmunizations", jsonObject.getBoolean(entityVar), pk));
						break;
					case "familyMarried":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("familyMarried", jsonObject.getBoolean(entityVar), pk));
						break;
					case "familySeparated":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("familySeparated", jsonObject.getBoolean(entityVar), pk));
						break;
					case "familyDivorced":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("familyDivorced", jsonObject.getBoolean(entityVar), pk));
						break;
					case "familyAddress":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("familyAddress", jsonObject.getString(entityVar), pk));
						break;
					case "familyHowDoYouKnowTheSchool":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("familyHowDoYouKnowTheSchool", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSpecialConsiderations":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSpecialConsiderations", jsonObject.getString(entityVar), pk));
						break;
					case "childMedicalConditions":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("childMedicalConditions", jsonObject.getString(entityVar), pk));
						break;
					case "childPreviousSchoolsAttended":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("childPreviousSchoolsAttended", jsonObject.getString(entityVar), pk));
						break;
					case "childDescription":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("childDescription", jsonObject.getString(entityVar), pk));
						break;
					case "childObjectives":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("childObjectives", jsonObject.getString(entityVar), pk));
						break;
					case "childPottyTrained":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("childPottyTrained", jsonObject.getBoolean(entityVar), pk));
						break;
					case "enrollmentGroupName":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentGroupName", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentPaymentEachMonth":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentPaymentEachMonth", jsonObject.getBoolean(entityVar), pk));
						break;
					case "enrollmentPaymentComplete":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentPaymentComplete", jsonObject.getBoolean(entityVar), pk));
						break;
					case "customerProfileId":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("customerProfileId", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentChargeDate":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentChargeDate", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentParentNames":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentParentNames", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature1":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSignature1", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature2":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSignature2", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature3":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSignature3", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature4":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSignature4", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature5":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSignature5", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature6":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSignature6", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature7":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSignature7", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature8":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSignature8", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature9":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSignature9", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentSignature10":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentSignature10", jsonObject.getString(entityVar), pk));
						break;
					case "enrollmentDate1":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentDate1", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate2":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentDate2", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate3":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentDate3", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate4":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentDate4", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate5":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentDate5", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate6":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentDate6", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate7":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentDate7", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate8":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentDate8", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate9":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentDate9", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					case "enrollmentDate10":
						putSql.append(SiteContextEnUS.SQL_setD);
						putSqlParams.addAll(Arrays.asList("enrollmentDate10", DateTimeFormatter.ofPattern("MM/dd/yyyy").format(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(jsonObject.getString(entityVar))), pk));
						break;
					}
				}
			}
			sqlConnection.queryWithParams(
					putSql.toString()
					, new JsonArray(putSqlParams)
					, postAsync
			-> {
				if(postAsync.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					eventHandler.handle(Future.failedFuture(new Exception(postAsync.cause())));
				}
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopySchoolEnrollmentResponse(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		response200PUTCopySchoolEnrollment(listSchoolEnrollment, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								ApiRequest apiRequest = siteRequest.getApiRequest_();
								siteRequest.getVertx().eventBus().publish("websocketSchoolEnrollment", JsonObject.mapFrom(apiRequest).toString());
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200PUTCopySchoolEnrollment(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(JsonObject.mapFrom(apiRequest).encodePrettily()))));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchSchoolEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest, body);

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							ApiRequest apiRequest = new ApiRequest();
							apiRequest.setRows(1);
							apiRequest.setNumFound(1L);
							apiRequest.initDeepApiRequest(siteRequest);
							siteRequest.setApiRequest_(apiRequest);
							SQLConnection sqlConnection = siteRequest.getSqlConnection();
							sqlConnection.close(c -> {
								if(c.succeeded()) {
									aSearchSchoolEnrollment(siteRequest, false, true, null, d -> {
										if(d.succeeded()) {
											SearchList<SchoolEnrollment> listSchoolEnrollment = d.result();
											SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listSchoolEnrollment.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
											Date date = null;
											if(facets != null)
												date = (Date)facets.get("max_modified");
											String dt;
											if(date == null)
												dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
											else
												dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
											listSchoolEnrollment.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

											if(listSchoolEnrollment.size() == 1) {
												SchoolEnrollment o = listSchoolEnrollment.get(0);
												apiRequest.setPk(o.getPk());
												apiRequest.setOriginal(o);
												apiRequestSchoolEnrollment(o);
											o.apiRequestSchoolEnrollment();
											}
											WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
											workerExecutor.executeBlocking(
												blockingCodeHandler -> {
													sqlSchoolEnrollment(siteRequest, e -> {
														if(e.succeeded()) {
															try {
																listPATCHSchoolEnrollment(apiRequest, listSchoolEnrollment, dt, f -> {
																	if(f.succeeded()) {
																		patchSchoolEnrollmentResponse(listSchoolEnrollment, g -> {
																			if(g.succeeded()) {
																				eventHandler.handle(Future.succeededFuture(g.result()));
																				LOGGER.info(String.format("patchSchoolEnrollment succeeded. "));
																			} else {
																				LOGGER.error(String.format("patchSchoolEnrollment failed. ", g.cause()));
																				errorSchoolEnrollment(siteRequest, eventHandler, d);
																			}
																		});
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
										} else {
											errorSchoolEnrollment(siteRequest, eventHandler, d);
										}
									});
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void listPATCHSchoolEnrollment(ApiRequest apiRequest, SearchList<SchoolEnrollment> listSchoolEnrollment, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		listSchoolEnrollment.getList().forEach(o -> {
			futures.add(
				patchSchoolEnrollmentFuture(o, a -> {
					if(a.succeeded()) {
							SchoolEnrollment schoolEnrollment = a.result();
							apiRequestSchoolEnrollment(schoolEnrollment);
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listSchoolEnrollment.size());
				if(listSchoolEnrollment.next(dt)) {
					siteRequest.getVertx().eventBus().publish("websocketSchoolEnrollment", JsonObject.mapFrom(apiRequest).toString());
					listPATCHSchoolEnrollment(apiRequest, listSchoolEnrollment, dt, eventHandler);
				} else {
					response200PATCHSchoolEnrollment(listSchoolEnrollment, eventHandler);
				}
			} else {
				errorSchoolEnrollment(listSchoolEnrollment.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SchoolEnrollment> patchSchoolEnrollmentFuture(SchoolEnrollment o, Handler<AsyncResult<SchoolEnrollment>> eventHandler) {
		Promise<SchoolEnrollment> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			sqlPATCHSchoolEnrollment(o, a -> {
				if(a.succeeded()) {
					SchoolEnrollment schoolEnrollment = a.result();
					defineSchoolEnrollment(schoolEnrollment, b -> {
						if(b.succeeded()) {
							attributeSchoolEnrollment(schoolEnrollment, c -> {
								if(c.succeeded()) {
									indexSchoolEnrollment(schoolEnrollment, d -> {
										if(d.succeeded()) {
											eventHandler.handle(Future.succeededFuture(schoolEnrollment));
											promise.complete(schoolEnrollment);
										} else {
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHSchoolEnrollment(SchoolEnrollment o, Handler<AsyncResult<SchoolEnrollment>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SQLConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			JsonObject requestJson = siteRequest.getJsonObject();
			StringBuilder patchSql = new StringBuilder();
			List<Object> patchSqlParams = new ArrayList<Object>();
			Set<String> methodNames = requestJson.fieldNames();
			SchoolEnrollment o2 = new SchoolEnrollment();

			patchSql.append(SiteContextEnUS.SQL_modify);
			patchSqlParams.addAll(Arrays.asList(pk, "org.computate.scolaire.enUS.enrollment.SchoolEnrollment"));
			for(String methodName : methodNames) {
				switch(methodName) {
					case "setCreated":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "created"));
						} else {
							o2.setCreated(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("created", o2.jsonCreated(), pk));
						}
						break;
					case "setModified":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "modified"));
						} else {
							o2.setModified(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("modified", o2.jsonModified(), pk));
						}
						break;
					case "setArchived":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "archived"));
						} else {
							o2.setArchived(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("archived", o2.jsonArchived(), pk));
						}
						break;
					case "setDeleted":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "deleted"));
						} else {
							o2.setDeleted(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("deleted", o2.jsonDeleted(), pk));
						}
						break;
					case "setYearKey":
						o2.setYearKey(requestJson.getString(methodName));
						patchSql.append(SiteContextEnUS.SQL_setA2);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", o2.getYearKey(), "yearKey", pk));
						break;
					case "removeYearKey":
						o2.setYearKey(requestJson.getString(methodName));
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", o2.getYearKey(), "yearKey", pk));
						break;
					case "addBlockKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("blockKeys", pk, "enrollmentKeys", Long.parseLong(requestJson.getString(methodName))));
						break;
					case "addAllBlockKeys":
						JsonArray addAllBlockKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllBlockKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("blockKeys", pk, "enrollmentKeys", addAllBlockKeysValues.getString(i)));
						}
						break;
					case "setBlockKeys":
						JsonArray setBlockKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA1);
						patchSqlParams.addAll(Arrays.asList("blockKeys", "enrollmentKeys", pk));
						for(Integer i = 0; i <  setBlockKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("blockKeys", pk, "enrollmentKeys", Long.parseLong(setBlockKeysValues.getString(i))));
						}
						break;
					case "removeBlockKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("blockKeys", pk, "enrollmentKeys", Long.parseLong(requestJson.getString(methodName))));
						break;
					case "setChildKey":
						o2.setChildKey(requestJson.getString(methodName));
						patchSql.append(SiteContextEnUS.SQL_setA1);
						patchSqlParams.addAll(Arrays.asList("childKey", pk, "enrollmentKeys", o2.getChildKey()));
						break;
					case "removeChildKey":
						o2.setChildKey(requestJson.getString(methodName));
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("childKey", pk, "enrollmentKeys", o2.getChildKey()));
						break;
					case "addMomKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "momKeys", pk));
						break;
					case "addAllMomKeys":
						JsonArray addAllMomKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllMomKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", addAllMomKeysValues.getString(i), "momKeys", pk));
						}
						break;
					case "setMomKeys":
						JsonArray setMomKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", "momKeys", pk));
						for(Integer i = 0; i <  setMomKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(setMomKeysValues.getString(i)), "momKeys", pk));
						}
						break;
					case "removeMomKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "momKeys", pk));
						break;
					case "addDadKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("dadKeys", pk, "enrollmentKeys", Long.parseLong(requestJson.getString(methodName))));
						break;
					case "addAllDadKeys":
						JsonArray addAllDadKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllDadKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("dadKeys", pk, "enrollmentKeys", addAllDadKeysValues.getString(i)));
						}
						break;
					case "setDadKeys":
						JsonArray setDadKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA1);
						patchSqlParams.addAll(Arrays.asList("dadKeys", "enrollmentKeys", pk));
						for(Integer i = 0; i <  setDadKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("dadKeys", pk, "enrollmentKeys", Long.parseLong(setDadKeysValues.getString(i))));
						}
						break;
					case "removeDadKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("dadKeys", pk, "enrollmentKeys", Long.parseLong(requestJson.getString(methodName))));
						break;
					case "addGuardianKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "guardianKeys", pk));
						break;
					case "addAllGuardianKeys":
						JsonArray addAllGuardianKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllGuardianKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", addAllGuardianKeysValues.getString(i), "guardianKeys", pk));
						}
						break;
					case "setGuardianKeys":
						JsonArray setGuardianKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", "guardianKeys", pk));
						for(Integer i = 0; i <  setGuardianKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(setGuardianKeysValues.getString(i)), "guardianKeys", pk));
						}
						break;
					case "removeGuardianKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "guardianKeys", pk));
						break;
					case "addPaymentKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKey", Long.parseLong(requestJson.getString(methodName)), "paymentKeys", pk));
						break;
					case "addAllPaymentKeys":
						JsonArray addAllPaymentKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllPaymentKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKey", addAllPaymentKeysValues.getString(i), "paymentKeys", pk));
						}
						break;
					case "setPaymentKeys":
						JsonArray setPaymentKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("enrollmentKey", "paymentKeys", pk));
						for(Integer i = 0; i <  setPaymentKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKey", Long.parseLong(setPaymentKeysValues.getString(i)), "paymentKeys", pk));
						}
						break;
					case "removePaymentKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKey", Long.parseLong(requestJson.getString(methodName)), "paymentKeys", pk));
						break;
					case "addUserKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "userKeys", pk));
						break;
					case "addAllUserKeys":
						JsonArray addAllUserKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllUserKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", addAllUserKeysValues.getString(i), "userKeys", pk));
						}
						break;
					case "setUserKeys":
						JsonArray setUserKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", "userKeys", pk));
						for(Integer i = 0; i <  setUserKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(setUserKeysValues.getString(i)), "userKeys", pk));
						}
						break;
					case "removeUserKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "userKeys", pk));
						break;
					case "setChildCompleteName":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childCompleteName"));
						} else {
							o2.setChildCompleteName(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childCompleteName", o2.jsonChildCompleteName(), pk));
						}
						break;
					case "setChildCompleteNamePreferred":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childCompleteNamePreferred"));
						} else {
							o2.setChildCompleteNamePreferred(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childCompleteNamePreferred", o2.jsonChildCompleteNamePreferred(), pk));
						}
						break;
					case "setChildBirthDate":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childBirthDate"));
						} else {
							o2.setChildBirthDate(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childBirthDate", o2.jsonChildBirthDate(), pk));
						}
						break;
					case "setSchoolAddress":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "schoolAddress"));
						} else {
							o2.setSchoolAddress(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("schoolAddress", o2.jsonSchoolAddress(), pk));
						}
						break;
					case "setEnrollmentApproved":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentApproved"));
						} else {
							o2.setEnrollmentApproved(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentApproved", o2.jsonEnrollmentApproved(), pk));
						}
						break;
					case "setEnrollmentImmunizations":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentImmunizations"));
						} else {
							o2.setEnrollmentImmunizations(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentImmunizations", o2.jsonEnrollmentImmunizations(), pk));
						}
						break;
					case "setFamilyMarried":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "familyMarried"));
						} else {
							o2.setFamilyMarried(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("familyMarried", o2.jsonFamilyMarried(), pk));
						}
						break;
					case "setFamilySeparated":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "familySeparated"));
						} else {
							o2.setFamilySeparated(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("familySeparated", o2.jsonFamilySeparated(), pk));
						}
						break;
					case "setFamilyDivorced":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "familyDivorced"));
						} else {
							o2.setFamilyDivorced(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("familyDivorced", o2.jsonFamilyDivorced(), pk));
						}
						break;
					case "setFamilyAddress":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "familyAddress"));
						} else {
							o2.setFamilyAddress(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("familyAddress", o2.jsonFamilyAddress(), pk));
						}
						break;
					case "setFamilyHowDoYouKnowTheSchool":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "familyHowDoYouKnowTheSchool"));
						} else {
							o2.setFamilyHowDoYouKnowTheSchool(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("familyHowDoYouKnowTheSchool", o2.jsonFamilyHowDoYouKnowTheSchool(), pk));
						}
						break;
					case "setEnrollmentSpecialConsiderations":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSpecialConsiderations"));
						} else {
							o2.setEnrollmentSpecialConsiderations(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSpecialConsiderations", o2.jsonEnrollmentSpecialConsiderations(), pk));
						}
						break;
					case "setChildMedicalConditions":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childMedicalConditions"));
						} else {
							o2.setChildMedicalConditions(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childMedicalConditions", o2.jsonChildMedicalConditions(), pk));
						}
						break;
					case "setChildPreviousSchoolsAttended":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childPreviousSchoolsAttended"));
						} else {
							o2.setChildPreviousSchoolsAttended(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childPreviousSchoolsAttended", o2.jsonChildPreviousSchoolsAttended(), pk));
						}
						break;
					case "setChildDescription":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childDescription"));
						} else {
							o2.setChildDescription(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childDescription", o2.jsonChildDescription(), pk));
						}
						break;
					case "setChildObjectives":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childObjectives"));
						} else {
							o2.setChildObjectives(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childObjectives", o2.jsonChildObjectives(), pk));
						}
						break;
					case "setChildPottyTrained":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childPottyTrained"));
						} else {
							o2.setChildPottyTrained(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childPottyTrained", o2.jsonChildPottyTrained(), pk));
						}
						break;
					case "setEnrollmentGroupName":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentGroupName"));
						} else {
							o2.setEnrollmentGroupName(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentGroupName", o2.jsonEnrollmentGroupName(), pk));
						}
						break;
					case "setEnrollmentPaymentEachMonth":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentPaymentEachMonth"));
						} else {
							o2.setEnrollmentPaymentEachMonth(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentPaymentEachMonth", o2.jsonEnrollmentPaymentEachMonth(), pk));
						}
						break;
					case "setEnrollmentPaymentComplete":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentPaymentComplete"));
						} else {
							o2.setEnrollmentPaymentComplete(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentPaymentComplete", o2.jsonEnrollmentPaymentComplete(), pk));
						}
						break;
					case "setCustomerProfileId":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "customerProfileId"));
						} else {
							o2.setCustomerProfileId(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("customerProfileId", o2.jsonCustomerProfileId(), pk));
						}
						break;
					case "setEnrollmentChargeDate":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentChargeDate"));
						} else {
							o2.setEnrollmentChargeDate(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentChargeDate", o2.jsonEnrollmentChargeDate(), pk));
						}
						break;
					case "setEnrollmentParentNames":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentParentNames"));
						} else {
							o2.setEnrollmentParentNames(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentParentNames", o2.jsonEnrollmentParentNames(), pk));
						}
						break;
					case "setEnrollmentSignature1":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature1"));
						} else {
							o2.setEnrollmentSignature1(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature1", o2.jsonEnrollmentSignature1(), pk));
						}
						break;
					case "setEnrollmentSignature2":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature2"));
						} else {
							o2.setEnrollmentSignature2(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature2", o2.jsonEnrollmentSignature2(), pk));
						}
						break;
					case "setEnrollmentSignature3":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature3"));
						} else {
							o2.setEnrollmentSignature3(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature3", o2.jsonEnrollmentSignature3(), pk));
						}
						break;
					case "setEnrollmentSignature4":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature4"));
						} else {
							o2.setEnrollmentSignature4(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature4", o2.jsonEnrollmentSignature4(), pk));
						}
						break;
					case "setEnrollmentSignature5":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature5"));
						} else {
							o2.setEnrollmentSignature5(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature5", o2.jsonEnrollmentSignature5(), pk));
						}
						break;
					case "setEnrollmentSignature6":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature6"));
						} else {
							o2.setEnrollmentSignature6(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature6", o2.jsonEnrollmentSignature6(), pk));
						}
						break;
					case "setEnrollmentSignature7":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature7"));
						} else {
							o2.setEnrollmentSignature7(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature7", o2.jsonEnrollmentSignature7(), pk));
						}
						break;
					case "setEnrollmentSignature8":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature8"));
						} else {
							o2.setEnrollmentSignature8(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature8", o2.jsonEnrollmentSignature8(), pk));
						}
						break;
					case "setEnrollmentSignature9":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature9"));
						} else {
							o2.setEnrollmentSignature9(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature9", o2.jsonEnrollmentSignature9(), pk));
						}
						break;
					case "setEnrollmentSignature10":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature10"));
						} else {
							o2.setEnrollmentSignature10(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature10", o2.jsonEnrollmentSignature10(), pk));
						}
						break;
					case "setEnrollmentDate1":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate1"));
						} else {
							o2.setEnrollmentDate1(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate1", o2.jsonEnrollmentDate1(), pk));
						}
						break;
					case "setEnrollmentDate2":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate2"));
						} else {
							o2.setEnrollmentDate2(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate2", o2.jsonEnrollmentDate2(), pk));
						}
						break;
					case "setEnrollmentDate3":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate3"));
						} else {
							o2.setEnrollmentDate3(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate3", o2.jsonEnrollmentDate3(), pk));
						}
						break;
					case "setEnrollmentDate4":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate4"));
						} else {
							o2.setEnrollmentDate4(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate4", o2.jsonEnrollmentDate4(), pk));
						}
						break;
					case "setEnrollmentDate5":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate5"));
						} else {
							o2.setEnrollmentDate5(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate5", o2.jsonEnrollmentDate5(), pk));
						}
						break;
					case "setEnrollmentDate6":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate6"));
						} else {
							o2.setEnrollmentDate6(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate6", o2.jsonEnrollmentDate6(), pk));
						}
						break;
					case "setEnrollmentDate7":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate7"));
						} else {
							o2.setEnrollmentDate7(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate7", o2.jsonEnrollmentDate7(), pk));
						}
						break;
					case "setEnrollmentDate8":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate8"));
						} else {
							o2.setEnrollmentDate8(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate8", o2.jsonEnrollmentDate8(), pk));
						}
						break;
					case "setEnrollmentDate9":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate9"));
						} else {
							o2.setEnrollmentDate9(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate9", o2.jsonEnrollmentDate9(), pk));
						}
						break;
					case "setEnrollmentDate10":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate10"));
						} else {
							o2.setEnrollmentDate10(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate10", o2.jsonEnrollmentDate10(), pk));
						}
						break;
				}
			}
			sqlConnection.queryWithParams(
					patchSql.toString()
					, new JsonArray(patchSqlParams)
					, patchAsync
			-> {
				if(patchAsync.succeeded()) {
					SchoolEnrollment o3 = new SchoolEnrollment();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					eventHandler.handle(Future.failedFuture(new Exception(patchAsync.cause())));
				}
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchSchoolEnrollmentResponse(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		response200PATCHSchoolEnrollment(listSchoolEnrollment, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								ApiRequest apiRequest = siteRequest.getApiRequest_();
								siteRequest.getVertx().eventBus().publish("websocketSchoolEnrollment", JsonObject.mapFrom(apiRequest).toString());
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200PATCHSchoolEnrollment(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			JsonObject json = JsonObject.mapFrom(apiRequest);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getSchoolEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest);
			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							aSearchSchoolEnrollment(siteRequest, false, true, null, c -> {
								if(c.succeeded()) {
									SearchList<SchoolEnrollment> listSchoolEnrollment = c.result();
									getSchoolEnrollmentResponse(listSchoolEnrollment, d -> {
										if(d.succeeded()) {
											eventHandler.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("getSchoolEnrollment succeeded. "));
										} else {
											LOGGER.error(String.format("getSchoolEnrollment failed. ", d.cause()));
											errorSchoolEnrollment(siteRequest, eventHandler, d);
										}
									});
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void getSchoolEnrollmentResponse(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		response200GETSchoolEnrollment(listSchoolEnrollment, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200GETSchoolEnrollment(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
			SolrDocumentList solrDocuments = listSchoolEnrollment.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listSchoolEnrollment.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchSchoolEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest);
			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							aSearchSchoolEnrollment(siteRequest, false, true, "/api/enrollment", c -> {
								if(c.succeeded()) {
									SearchList<SchoolEnrollment> listSchoolEnrollment = c.result();
									searchSchoolEnrollmentResponse(listSchoolEnrollment, d -> {
										if(d.succeeded()) {
											eventHandler.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("searchSchoolEnrollment succeeded. "));
										} else {
											LOGGER.error(String.format("searchSchoolEnrollment failed. ", d.cause()));
											errorSchoolEnrollment(siteRequest, eventHandler, d);
										}
									});
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void searchSchoolEnrollmentResponse(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		response200SearchSchoolEnrollment(listSchoolEnrollment, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200SearchSchoolEnrollment(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
			QueryResponse responseSearch = listSchoolEnrollment.getQueryResponse();
			SolrDocumentList solrDocuments = listSchoolEnrollment.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			json.put("searchTime", searchTime);
			json.put("transmissionTime", transmissionTime);
			JsonArray l = new JsonArray();
			listSchoolEnrollment.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listSchoolEnrollment.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("saves")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("pk");
					}
					else if(fls.size() >= 1) {
						fieldNames.removeAll(fls);
					}
					for(String fieldName : fieldNames) {
						if(!fls.contains(fieldName))
							json2.remove(fieldName);
					}
				}
				l.add(json2);
			});
			json.put("list", l);
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCHPayments //

	@Override
	public void patchpaymentsSchoolEnrollment(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest, body);

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), new CaseInsensitiveHeaders()
					)
				));
			}

			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							ApiRequest apiRequest = new ApiRequest();
							apiRequest.setRows(1);
							apiRequest.setNumFound(1L);
							apiRequest.initDeepApiRequest(siteRequest);
							siteRequest.setApiRequest_(apiRequest);
							SQLConnection sqlConnection = siteRequest.getSqlConnection();
							sqlConnection.close(c -> {
								if(c.succeeded()) {
									aSearchSchoolEnrollment(siteRequest, false, true, null, d -> {
										if(d.succeeded()) {
											SearchList<SchoolEnrollment> listSchoolEnrollment = d.result();
											SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listSchoolEnrollment.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
											Date date = null;
											if(facets != null)
												date = (Date)facets.get("max_modified");
											String dt;
											if(date == null)
												dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
											else
												dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
											listSchoolEnrollment.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

											if(listSchoolEnrollment.size() == 1) {
												SchoolEnrollment o = listSchoolEnrollment.get(0);
												apiRequest.setPk(o.getPk());
												apiRequest.setOriginal(o);
												apiRequestSchoolEnrollment(o);
											o.apiRequestSchoolEnrollment();
											}
											WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
											workerExecutor.executeBlocking(
												blockingCodeHandler -> {
													sqlSchoolEnrollment(siteRequest, e -> {
														if(e.succeeded()) {
															try {
																listPATCHPaymentsSchoolEnrollment(apiRequest, listSchoolEnrollment, dt, f -> {
																	if(f.succeeded()) {
																		patchpaymentsSchoolEnrollmentResponse(listSchoolEnrollment, g -> {
																			if(g.succeeded()) {
																				eventHandler.handle(Future.succeededFuture(g.result()));
																				LOGGER.info(String.format("patchpaymentsSchoolEnrollment succeeded. "));
																			} else {
																				LOGGER.error(String.format("patchpaymentsSchoolEnrollment failed. ", g.cause()));
																				errorSchoolEnrollment(siteRequest, eventHandler, d);
																			}
																		});
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
										} else {
											errorSchoolEnrollment(siteRequest, eventHandler, d);
										}
									});
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void listPATCHPaymentsSchoolEnrollment(ApiRequest apiRequest, SearchList<SchoolEnrollment> listSchoolEnrollment, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		listSchoolEnrollment.getList().forEach(o -> {
			futures.add(
				patchpaymentsSchoolEnrollmentFuture(o, a -> {
					if(a.succeeded()) {
							SchoolEnrollment schoolEnrollment = a.result();
							apiRequestSchoolEnrollment(schoolEnrollment);
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listSchoolEnrollment.size());
				if(listSchoolEnrollment.next(dt)) {
					siteRequest.getVertx().eventBus().publish("websocketSchoolEnrollment", JsonObject.mapFrom(apiRequest).toString());
					listPATCHPaymentsSchoolEnrollment(apiRequest, listSchoolEnrollment, dt, eventHandler);
				} else {
					response200PATCHPaymentsSchoolEnrollment(listSchoolEnrollment, eventHandler);
				}
			} else {
				errorSchoolEnrollment(listSchoolEnrollment.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SchoolEnrollment> patchpaymentsSchoolEnrollmentFuture(SchoolEnrollment o, Handler<AsyncResult<SchoolEnrollment>> eventHandler) {
		Promise<SchoolEnrollment> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			sqlPATCHPaymentsSchoolEnrollment(o, a -> {
				if(a.succeeded()) {
					SchoolEnrollment schoolEnrollment = a.result();
					defineSchoolEnrollment(schoolEnrollment, b -> {
						if(b.succeeded()) {
							attributeSchoolEnrollment(schoolEnrollment, c -> {
								if(c.succeeded()) {
									indexSchoolEnrollment(schoolEnrollment, d -> {
										if(d.succeeded()) {
											eventHandler.handle(Future.succeededFuture(schoolEnrollment));
											promise.complete(schoolEnrollment);
										} else {
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHPaymentsSchoolEnrollment(SchoolEnrollment o, Handler<AsyncResult<SchoolEnrollment>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SQLConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			JsonObject requestJson = siteRequest.getJsonObject();
			StringBuilder patchSql = new StringBuilder();
			List<Object> patchSqlParams = new ArrayList<Object>();
			Set<String> methodNames = requestJson.fieldNames();
			SchoolEnrollment o2 = new SchoolEnrollment();

			patchSql.append(SiteContextEnUS.SQL_modify);
			patchSqlParams.addAll(Arrays.asList(pk, "org.computate.scolaire.enUS.enrollment.SchoolEnrollment"));
			for(String methodName : methodNames) {
				switch(methodName) {
					case "setCreated":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "created"));
						} else {
							o2.setCreated(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("created", o2.jsonCreated(), pk));
						}
						break;
					case "setModified":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "modified"));
						} else {
							o2.setModified(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("modified", o2.jsonModified(), pk));
						}
						break;
					case "setArchived":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "archived"));
						} else {
							o2.setArchived(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("archived", o2.jsonArchived(), pk));
						}
						break;
					case "setDeleted":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "deleted"));
						} else {
							o2.setDeleted(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("deleted", o2.jsonDeleted(), pk));
						}
						break;
					case "setYearKey":
						o2.setYearKey(requestJson.getString(methodName));
						patchSql.append(SiteContextEnUS.SQL_setA2);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", o2.getYearKey(), "yearKey", pk));
						break;
					case "removeYearKey":
						o2.setYearKey(requestJson.getString(methodName));
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", o2.getYearKey(), "yearKey", pk));
						break;
					case "addBlockKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("blockKeys", pk, "enrollmentKeys", Long.parseLong(requestJson.getString(methodName))));
						break;
					case "addAllBlockKeys":
						JsonArray addAllBlockKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllBlockKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("blockKeys", pk, "enrollmentKeys", addAllBlockKeysValues.getString(i)));
						}
						break;
					case "setBlockKeys":
						JsonArray setBlockKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA1);
						patchSqlParams.addAll(Arrays.asList("blockKeys", "enrollmentKeys", pk));
						for(Integer i = 0; i <  setBlockKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("blockKeys", pk, "enrollmentKeys", Long.parseLong(setBlockKeysValues.getString(i))));
						}
						break;
					case "removeBlockKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("blockKeys", pk, "enrollmentKeys", Long.parseLong(requestJson.getString(methodName))));
						break;
					case "setChildKey":
						o2.setChildKey(requestJson.getString(methodName));
						patchSql.append(SiteContextEnUS.SQL_setA1);
						patchSqlParams.addAll(Arrays.asList("childKey", pk, "enrollmentKeys", o2.getChildKey()));
						break;
					case "removeChildKey":
						o2.setChildKey(requestJson.getString(methodName));
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("childKey", pk, "enrollmentKeys", o2.getChildKey()));
						break;
					case "addMomKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "momKeys", pk));
						break;
					case "addAllMomKeys":
						JsonArray addAllMomKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllMomKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", addAllMomKeysValues.getString(i), "momKeys", pk));
						}
						break;
					case "setMomKeys":
						JsonArray setMomKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", "momKeys", pk));
						for(Integer i = 0; i <  setMomKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(setMomKeysValues.getString(i)), "momKeys", pk));
						}
						break;
					case "removeMomKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "momKeys", pk));
						break;
					case "addDadKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("dadKeys", pk, "enrollmentKeys", Long.parseLong(requestJson.getString(methodName))));
						break;
					case "addAllDadKeys":
						JsonArray addAllDadKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllDadKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("dadKeys", pk, "enrollmentKeys", addAllDadKeysValues.getString(i)));
						}
						break;
					case "setDadKeys":
						JsonArray setDadKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA1);
						patchSqlParams.addAll(Arrays.asList("dadKeys", "enrollmentKeys", pk));
						for(Integer i = 0; i <  setDadKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_addA);
							patchSqlParams.addAll(Arrays.asList("dadKeys", pk, "enrollmentKeys", Long.parseLong(setDadKeysValues.getString(i))));
						}
						break;
					case "removeDadKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("dadKeys", pk, "enrollmentKeys", Long.parseLong(requestJson.getString(methodName))));
						break;
					case "addGuardianKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "guardianKeys", pk));
						break;
					case "addAllGuardianKeys":
						JsonArray addAllGuardianKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllGuardianKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", addAllGuardianKeysValues.getString(i), "guardianKeys", pk));
						}
						break;
					case "setGuardianKeys":
						JsonArray setGuardianKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", "guardianKeys", pk));
						for(Integer i = 0; i <  setGuardianKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(setGuardianKeysValues.getString(i)), "guardianKeys", pk));
						}
						break;
					case "removeGuardianKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "guardianKeys", pk));
						break;
					case "addPaymentKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKey", Long.parseLong(requestJson.getString(methodName)), "paymentKeys", pk));
						break;
					case "addAllPaymentKeys":
						JsonArray addAllPaymentKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllPaymentKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKey", addAllPaymentKeysValues.getString(i), "paymentKeys", pk));
						}
						break;
					case "setPaymentKeys":
						JsonArray setPaymentKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("enrollmentKey", "paymentKeys", pk));
						for(Integer i = 0; i <  setPaymentKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKey", Long.parseLong(setPaymentKeysValues.getString(i)), "paymentKeys", pk));
						}
						break;
					case "removePaymentKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKey", Long.parseLong(requestJson.getString(methodName)), "paymentKeys", pk));
						break;
					case "addUserKeys":
						patchSql.append(SiteContextEnUS.SQL_addA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "userKeys", pk));
						break;
					case "addAllUserKeys":
						JsonArray addAllUserKeysValues = requestJson.getJsonArray(methodName);
						for(Integer i = 0; i <  addAllUserKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", addAllUserKeysValues.getString(i), "userKeys", pk));
						}
						break;
					case "setUserKeys":
						JsonArray setUserKeysValues = requestJson.getJsonArray(methodName);
						patchSql.append(SiteContextEnUS.SQL_clearA2);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", "userKeys", pk));
						for(Integer i = 0; i <  setUserKeysValues.size(); i++) {
							patchSql.append(SiteContextEnUS.SQL_setA2);
							patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(setUserKeysValues.getString(i)), "userKeys", pk));
						}
						break;
					case "removeUserKeys":
						patchSql.append(SiteContextEnUS.SQL_removeA);
						patchSqlParams.addAll(Arrays.asList("enrollmentKeys", Long.parseLong(requestJson.getString(methodName)), "userKeys", pk));
						break;
					case "setChildCompleteName":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childCompleteName"));
						} else {
							o2.setChildCompleteName(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childCompleteName", o2.jsonChildCompleteName(), pk));
						}
						break;
					case "setChildCompleteNamePreferred":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childCompleteNamePreferred"));
						} else {
							o2.setChildCompleteNamePreferred(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childCompleteNamePreferred", o2.jsonChildCompleteNamePreferred(), pk));
						}
						break;
					case "setChildBirthDate":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childBirthDate"));
						} else {
							o2.setChildBirthDate(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childBirthDate", o2.jsonChildBirthDate(), pk));
						}
						break;
					case "setSchoolAddress":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "schoolAddress"));
						} else {
							o2.setSchoolAddress(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("schoolAddress", o2.jsonSchoolAddress(), pk));
						}
						break;
					case "setEnrollmentApproved":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentApproved"));
						} else {
							o2.setEnrollmentApproved(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentApproved", o2.jsonEnrollmentApproved(), pk));
						}
						break;
					case "setEnrollmentImmunizations":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentImmunizations"));
						} else {
							o2.setEnrollmentImmunizations(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentImmunizations", o2.jsonEnrollmentImmunizations(), pk));
						}
						break;
					case "setFamilyMarried":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "familyMarried"));
						} else {
							o2.setFamilyMarried(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("familyMarried", o2.jsonFamilyMarried(), pk));
						}
						break;
					case "setFamilySeparated":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "familySeparated"));
						} else {
							o2.setFamilySeparated(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("familySeparated", o2.jsonFamilySeparated(), pk));
						}
						break;
					case "setFamilyDivorced":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "familyDivorced"));
						} else {
							o2.setFamilyDivorced(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("familyDivorced", o2.jsonFamilyDivorced(), pk));
						}
						break;
					case "setFamilyAddress":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "familyAddress"));
						} else {
							o2.setFamilyAddress(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("familyAddress", o2.jsonFamilyAddress(), pk));
						}
						break;
					case "setFamilyHowDoYouKnowTheSchool":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "familyHowDoYouKnowTheSchool"));
						} else {
							o2.setFamilyHowDoYouKnowTheSchool(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("familyHowDoYouKnowTheSchool", o2.jsonFamilyHowDoYouKnowTheSchool(), pk));
						}
						break;
					case "setEnrollmentSpecialConsiderations":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSpecialConsiderations"));
						} else {
							o2.setEnrollmentSpecialConsiderations(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSpecialConsiderations", o2.jsonEnrollmentSpecialConsiderations(), pk));
						}
						break;
					case "setChildMedicalConditions":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childMedicalConditions"));
						} else {
							o2.setChildMedicalConditions(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childMedicalConditions", o2.jsonChildMedicalConditions(), pk));
						}
						break;
					case "setChildPreviousSchoolsAttended":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childPreviousSchoolsAttended"));
						} else {
							o2.setChildPreviousSchoolsAttended(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childPreviousSchoolsAttended", o2.jsonChildPreviousSchoolsAttended(), pk));
						}
						break;
					case "setChildDescription":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childDescription"));
						} else {
							o2.setChildDescription(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childDescription", o2.jsonChildDescription(), pk));
						}
						break;
					case "setChildObjectives":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childObjectives"));
						} else {
							o2.setChildObjectives(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childObjectives", o2.jsonChildObjectives(), pk));
						}
						break;
					case "setChildPottyTrained":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "childPottyTrained"));
						} else {
							o2.setChildPottyTrained(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("childPottyTrained", o2.jsonChildPottyTrained(), pk));
						}
						break;
					case "setEnrollmentGroupName":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentGroupName"));
						} else {
							o2.setEnrollmentGroupName(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentGroupName", o2.jsonEnrollmentGroupName(), pk));
						}
						break;
					case "setEnrollmentPaymentEachMonth":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentPaymentEachMonth"));
						} else {
							o2.setEnrollmentPaymentEachMonth(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentPaymentEachMonth", o2.jsonEnrollmentPaymentEachMonth(), pk));
						}
						break;
					case "setEnrollmentPaymentComplete":
						if(requestJson.getBoolean(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentPaymentComplete"));
						} else {
							o2.setEnrollmentPaymentComplete(requestJson.getBoolean(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentPaymentComplete", o2.jsonEnrollmentPaymentComplete(), pk));
						}
						break;
					case "setCustomerProfileId":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "customerProfileId"));
						} else {
							o2.setCustomerProfileId(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("customerProfileId", o2.jsonCustomerProfileId(), pk));
						}
						break;
					case "setEnrollmentChargeDate":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentChargeDate"));
						} else {
							o2.setEnrollmentChargeDate(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentChargeDate", o2.jsonEnrollmentChargeDate(), pk));
						}
						break;
					case "setEnrollmentParentNames":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentParentNames"));
						} else {
							o2.setEnrollmentParentNames(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentParentNames", o2.jsonEnrollmentParentNames(), pk));
						}
						break;
					case "setEnrollmentSignature1":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature1"));
						} else {
							o2.setEnrollmentSignature1(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature1", o2.jsonEnrollmentSignature1(), pk));
						}
						break;
					case "setEnrollmentSignature2":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature2"));
						} else {
							o2.setEnrollmentSignature2(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature2", o2.jsonEnrollmentSignature2(), pk));
						}
						break;
					case "setEnrollmentSignature3":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature3"));
						} else {
							o2.setEnrollmentSignature3(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature3", o2.jsonEnrollmentSignature3(), pk));
						}
						break;
					case "setEnrollmentSignature4":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature4"));
						} else {
							o2.setEnrollmentSignature4(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature4", o2.jsonEnrollmentSignature4(), pk));
						}
						break;
					case "setEnrollmentSignature5":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature5"));
						} else {
							o2.setEnrollmentSignature5(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature5", o2.jsonEnrollmentSignature5(), pk));
						}
						break;
					case "setEnrollmentSignature6":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature6"));
						} else {
							o2.setEnrollmentSignature6(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature6", o2.jsonEnrollmentSignature6(), pk));
						}
						break;
					case "setEnrollmentSignature7":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature7"));
						} else {
							o2.setEnrollmentSignature7(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature7", o2.jsonEnrollmentSignature7(), pk));
						}
						break;
					case "setEnrollmentSignature8":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature8"));
						} else {
							o2.setEnrollmentSignature8(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature8", o2.jsonEnrollmentSignature8(), pk));
						}
						break;
					case "setEnrollmentSignature9":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature9"));
						} else {
							o2.setEnrollmentSignature9(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature9", o2.jsonEnrollmentSignature9(), pk));
						}
						break;
					case "setEnrollmentSignature10":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentSignature10"));
						} else {
							o2.setEnrollmentSignature10(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentSignature10", o2.jsonEnrollmentSignature10(), pk));
						}
						break;
					case "setEnrollmentDate1":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate1"));
						} else {
							o2.setEnrollmentDate1(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate1", o2.jsonEnrollmentDate1(), pk));
						}
						break;
					case "setEnrollmentDate2":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate2"));
						} else {
							o2.setEnrollmentDate2(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate2", o2.jsonEnrollmentDate2(), pk));
						}
						break;
					case "setEnrollmentDate3":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate3"));
						} else {
							o2.setEnrollmentDate3(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate3", o2.jsonEnrollmentDate3(), pk));
						}
						break;
					case "setEnrollmentDate4":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate4"));
						} else {
							o2.setEnrollmentDate4(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate4", o2.jsonEnrollmentDate4(), pk));
						}
						break;
					case "setEnrollmentDate5":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate5"));
						} else {
							o2.setEnrollmentDate5(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate5", o2.jsonEnrollmentDate5(), pk));
						}
						break;
					case "setEnrollmentDate6":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate6"));
						} else {
							o2.setEnrollmentDate6(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate6", o2.jsonEnrollmentDate6(), pk));
						}
						break;
					case "setEnrollmentDate7":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate7"));
						} else {
							o2.setEnrollmentDate7(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate7", o2.jsonEnrollmentDate7(), pk));
						}
						break;
					case "setEnrollmentDate8":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate8"));
						} else {
							o2.setEnrollmentDate8(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate8", o2.jsonEnrollmentDate8(), pk));
						}
						break;
					case "setEnrollmentDate9":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate9"));
						} else {
							o2.setEnrollmentDate9(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate9", o2.jsonEnrollmentDate9(), pk));
						}
						break;
					case "setEnrollmentDate10":
						if(requestJson.getString(methodName) == null) {
							patchSql.append(SiteContextEnUS.SQL_removeD);
							patchSqlParams.addAll(Arrays.asList(pk, "enrollmentDate10"));
						} else {
							o2.setEnrollmentDate10(requestJson.getString(methodName));
							patchSql.append(SiteContextEnUS.SQL_setD);
							patchSqlParams.addAll(Arrays.asList("enrollmentDate10", o2.jsonEnrollmentDate10(), pk));
						}
						break;
				}
			}
			sqlConnection.queryWithParams(
					patchSql.toString()
					, new JsonArray(patchSqlParams)
					, patchAsync
			-> {
				if(patchAsync.succeeded()) {
					SchoolEnrollment o3 = new SchoolEnrollment();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					eventHandler.handle(Future.failedFuture(new Exception(patchAsync.cause())));
				}
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchpaymentsSchoolEnrollmentResponse(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		response200PATCHPaymentsSchoolEnrollment(listSchoolEnrollment, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								ApiRequest apiRequest = siteRequest.getApiRequest_();
								siteRequest.getVertx().eventBus().publish("websocketSchoolEnrollment", JsonObject.mapFrom(apiRequest).toString());
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200PATCHPaymentsSchoolEnrollment(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			JsonObject json = JsonObject.mapFrom(apiRequest);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// SearchPage //

	@Override
	public void searchpageSchoolEnrollmentId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		searchpageSchoolEnrollment(operationRequest, eventHandler);
	}

	@Override
	public void searchpageSchoolEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest);
			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							aSearchSchoolEnrollment(siteRequest, false, true, "/enrollment", c -> {
								if(c.succeeded()) {
									SearchList<SchoolEnrollment> listSchoolEnrollment = c.result();
									searchpageSchoolEnrollmentResponse(listSchoolEnrollment, d -> {
										if(d.succeeded()) {
											eventHandler.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("searchpageSchoolEnrollment succeeded. "));
										} else {
											LOGGER.error(String.format("searchpageSchoolEnrollment failed. ", d.cause()));
											errorSchoolEnrollment(siteRequest, eventHandler, d);
										}
									});
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void searchpageSchoolEnrollmentResponse(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		response200SearchPageSchoolEnrollment(listSchoolEnrollment, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200SearchPageSchoolEnrollment(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listSchoolEnrollment.getSiteRequest_(), buffer);
			EnrollmentPage page = new EnrollmentPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/enrollment");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listSchoolEnrollment.size() == 1)
				siteRequest.setRequestPk(listSchoolEnrollment.get(0).getPk());
			siteRequest.setW(w);
			page.setListSchoolEnrollment(listSchoolEnrollment);
			page.setSiteRequest_(siteRequest);
			page.initDeepEnrollmentPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// FormSearchPage //

	@Override
	public void formsearchpageSchoolEnrollmentId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		formsearchpageSchoolEnrollment(operationRequest, eventHandler);
	}

	@Override
	public void formsearchpageSchoolEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest);
			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							aSearchSchoolEnrollment(siteRequest, false, true, "/enrollment-form", c -> {
								if(c.succeeded()) {
									SearchList<SchoolEnrollment> listSchoolEnrollment = c.result();
									formsearchpageSchoolEnrollmentResponse(listSchoolEnrollment, d -> {
										if(d.succeeded()) {
											eventHandler.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("formsearchpageSchoolEnrollment succeeded. "));
										} else {
											LOGGER.error(String.format("formsearchpageSchoolEnrollment failed. ", d.cause()));
											errorSchoolEnrollment(siteRequest, eventHandler, d);
										}
									});
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void formsearchpageSchoolEnrollmentResponse(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		response200FormSearchPageSchoolEnrollment(listSchoolEnrollment, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200FormSearchPageSchoolEnrollment(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listSchoolEnrollment.getSiteRequest_(), buffer);
			EnrollmentFormPage page = new EnrollmentFormPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/enrollment-form");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listSchoolEnrollment.size() == 1)
				siteRequest.setRequestPk(listSchoolEnrollment.get(0).getPk());
			siteRequest.setW(w);
			page.setListSchoolEnrollment(listSchoolEnrollment);
			page.setSiteRequest_(siteRequest);
			page.initDeepEnrollmentFormPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PdfSearchPage //

	@Override
	public void pdfsearchpageSchoolEnrollmentId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		pdfsearchpageSchoolEnrollment(operationRequest, eventHandler);
	}

	@Override
	public void pdfsearchpageSchoolEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest);
			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							aSearchSchoolEnrollment(siteRequest, false, true, "/enrollment-pdf", c -> {
								if(c.succeeded()) {
									SearchList<SchoolEnrollment> listSchoolEnrollment = c.result();
									pdfsearchpageSchoolEnrollmentResponse(listSchoolEnrollment, d -> {
										if(d.succeeded()) {
											eventHandler.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("pdfsearchpageSchoolEnrollment succeeded. "));
										} else {
											LOGGER.error(String.format("pdfsearchpageSchoolEnrollment failed. ", d.cause()));
											errorSchoolEnrollment(siteRequest, eventHandler, d);
										}
									});
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void pdfsearchpageSchoolEnrollmentResponse(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		response200PdfSearchPageSchoolEnrollment(listSchoolEnrollment, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200PdfSearchPageSchoolEnrollment(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listSchoolEnrollment.getSiteRequest_(), buffer);
			EnrollmentPdfPage page = new EnrollmentPdfPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/enrollment-pdf");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listSchoolEnrollment.size() == 1)
				siteRequest.setRequestPk(listSchoolEnrollment.get(0).getPk());
			siteRequest.setW(w);
			page.setListSchoolEnrollment(listSchoolEnrollment);
			page.setSiteRequest_(siteRequest);
			page.initDeepEnrollmentPdfPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// EmailSearchPage //

	@Override
	public void emailsearchpageSchoolEnrollmentId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		emailsearchpageSchoolEnrollment(operationRequest, eventHandler);
	}

	@Override
	public void emailsearchpageSchoolEnrollment(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest);
			sqlSchoolEnrollment(siteRequest, a -> {
				if(a.succeeded()) {
					userSchoolEnrollment(siteRequest, b -> {
						if(b.succeeded()) {
							aSearchSchoolEnrollment(siteRequest, false, true, "/enrollment-email", c -> {
								if(c.succeeded()) {
									SearchList<SchoolEnrollment> listSchoolEnrollment = c.result();
									emailsearchpageSchoolEnrollmentResponse(listSchoolEnrollment, d -> {
										if(d.succeeded()) {
											eventHandler.handle(Future.succeededFuture(d.result()));
											LOGGER.info(String.format("emailsearchpageSchoolEnrollment succeeded. "));
										} else {
											LOGGER.error(String.format("emailsearchpageSchoolEnrollment failed. ", d.cause()));
											errorSchoolEnrollment(siteRequest, eventHandler, d);
										}
									});
								} else {
									errorSchoolEnrollment(siteRequest, eventHandler, c);
								}
							});
						} else {
							errorSchoolEnrollment(siteRequest, eventHandler, b);
						}
					});
				} else {
					errorSchoolEnrollment(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception e) {
			errorSchoolEnrollment(null, eventHandler, Future.failedFuture(e));
		}
	}


	public void emailsearchpageSchoolEnrollmentResponse(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
		response200EmailSearchPageSchoolEnrollment(listSchoolEnrollment, a -> {
			if(a.succeeded()) {
				SQLConnection sqlConnection = siteRequest.getSqlConnection();
				sqlConnection.commit(b -> {
					if(b.succeeded()) {
						sqlConnection.close(c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(a.result()));
							} else {
								errorSchoolEnrollment(siteRequest, eventHandler, c);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, eventHandler, b);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, eventHandler, a);
			}
		});
	}
	public void response200EmailSearchPageSchoolEnrollment(SearchList<SchoolEnrollment> listSchoolEnrollment, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSchoolEnrollment.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listSchoolEnrollment.getSiteRequest_(), buffer);
			EnrollmentEmailPage page = new EnrollmentEmailPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/enrollment-email");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listSchoolEnrollment.size() == 1)
				siteRequest.setRequestPk(listSchoolEnrollment.get(0).getPk());
			siteRequest.setW(w);
			page.setListSchoolEnrollment(listSchoolEnrollment);
			page.setSiteRequest_(siteRequest);
			page.initDeepEnrollmentEmailPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<SchoolEnrollment> defineIndexSchoolEnrollment(SchoolEnrollment schoolEnrollment, Handler<AsyncResult<SchoolEnrollment>> eventHandler) {
		Promise<SchoolEnrollment> promise = Promise.promise();
		SiteRequestEnUS siteRequest = schoolEnrollment.getSiteRequest_();
		defineSchoolEnrollment(schoolEnrollment, c -> {
			if(c.succeeded()) {
				attributeSchoolEnrollment(schoolEnrollment, d -> {
					if(d.succeeded()) {
						indexSchoolEnrollment(schoolEnrollment, e -> {
							if(e.succeeded()) {
								eventHandler.handle(Future.succeededFuture(schoolEnrollment));
								promise.complete(schoolEnrollment);
							} else {
								errorSchoolEnrollment(siteRequest, null, e);
							}
						});
					} else {
						errorSchoolEnrollment(siteRequest, null, d);
					}
				});
			} else {
				errorSchoolEnrollment(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createSchoolEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<SchoolEnrollment>> eventHandler) {
		try {
			SQLConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();

			sqlConnection.queryWithParams(
					SiteContextEnUS.SQL_create
					, new JsonArray(Arrays.asList(SchoolEnrollment.class.getCanonicalName(), userId))
					, createAsync
			-> {
				JsonArray createLine = createAsync.result().getResults().stream().findFirst().orElseGet(() -> null);
				Long pk = createLine.getLong(0);
				SchoolEnrollment o = new SchoolEnrollment();
				o.setPk(pk);
				o.setSiteRequest_(siteRequest);
				eventHandler.handle(Future.succeededFuture(o));
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public ApiRequest apiRequestSchoolEnrollment(SchoolEnrollment o) {
		ApiRequest apiRequest = o.getSiteRequest_().getApiRequest_();
		if(apiRequest != null) {
			List<Long> pks = apiRequest.getPks();
			List<String> classes = apiRequest.getClasses();
			if(o.getYearKey() != null) {
				if(!pks.contains(o.getYearKey())) {
					pks.add(o.getYearKey());
					classes.add("SchoolYear");
				}
			}
			for(Long pk : o.getBlockKeys()) {
				if(!pks.contains(pk)) {
					pks.add(pk);
					classes.add("SchoolBlock");
				}
			}
			if(o.getChildKey() != null) {
				if(!pks.contains(o.getChildKey())) {
					pks.add(o.getChildKey());
					classes.add("SchoolChild");
				}
			}
			for(Long pk : o.getMomKeys()) {
				if(!pks.contains(pk)) {
					pks.add(pk);
					classes.add("SchoolMom");
				}
			}
			for(Long pk : o.getDadKeys()) {
				if(!pks.contains(pk)) {
					pks.add(pk);
					classes.add("SchoolDad");
				}
			}
			for(Long pk : o.getGuardianKeys()) {
				if(!pks.contains(pk)) {
					pks.add(pk);
					classes.add("SchoolGuardian");
				}
			}
			for(Long pk : o.getPaymentKeys()) {
				if(!pks.contains(pk)) {
					pks.add(pk);
					classes.add("SchoolPayment");
				}
			}
			for(Long pk : o.getUserKeys()) {
				if(!pks.contains(pk)) {
					pks.add(pk);
					classes.add("SiteUser");
				}
			}
		}
		return apiRequest;
	}

	public void errorSchoolEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
		Throwable e = resultAsync.cause();
		ExceptionUtils.printRootCauseStackTrace(e);
		OperationResponse responseOperation = new OperationResponse(400, "BAD REQUEST", 
			Buffer.buffer().appendString(
				new JsonObject() {{
					put("error", new JsonObject() {{
					put("message", e.getMessage());
					}});
				}}.encodePrettily()
			)
			, new CaseInsensitiveHeaders()
		);
		SiteConfig siteConfig = siteRequest.getSiteConfig_();
		SiteContextEnUS siteContext = siteRequest.getSiteContext_();
		MailClient mailClient = siteContext.getMailClient();
		MailMessage message = new MailMessage();
		message.setFrom(siteConfig.getEmailFrom());
		message.setTo(siteConfig.getEmailAdmin());
		message.setText(ExceptionUtils.getStackTrace(e));
		message.setSubject(String.format(siteConfig.getSiteBaseUrl() + " " + e.getMessage()));
		WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
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
		if(siteRequest != null) {
			SQLConnection sqlConnection = siteRequest.getSqlConnection();
			if(sqlConnection != null) {
				sqlConnection.rollback(a -> {
					if(a.succeeded()) {
						sqlConnection.close(b -> {
							if(a.succeeded()) {
								eventHandler.handle(Future.succeededFuture(responseOperation));
							} else {
								eventHandler.handle(Future.succeededFuture(responseOperation));
							}
						});
					} else {
						eventHandler.handle(Future.succeededFuture(responseOperation));
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture(responseOperation));
			}
		} else {
			eventHandler.handle(Future.succeededFuture(responseOperation));
		}
	}

	public void sqlSchoolEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SQLClient sqlClient = siteRequest.getSiteContext_().getSqlClient();

			if(sqlClient == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlClient.getConnection(sqlAsync -> {
					if(sqlAsync.succeeded()) {
						SQLConnection sqlConnection = sqlAsync.result();
						sqlConnection.setAutoCommit(false, a -> {
							if(a.succeeded()) {
								siteRequest.setSqlConnection(sqlConnection);
								eventHandler.handle(Future.succeededFuture());
							} else {
								eventHandler.handle(Future.failedFuture(a.cause()));
							}
						});
					} else {
						eventHandler.handle(Future.failedFuture(new Exception(sqlAsync.cause())));
					}
				});
			}
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSchoolEnrollment(SiteContextEnUS siteContext, OperationRequest operationRequest) {
		return generateSiteRequestEnUSForSchoolEnrollment(siteContext, operationRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSchoolEnrollment(SiteContextEnUS siteContext, OperationRequest operationRequest, JsonObject body) {
		Vertx vertx = siteContext.getVertx();
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
		siteRequest.setJsonObject(body);
		siteRequest.setVertx(vertx);
		siteRequest.setSiteContext_(siteContext);
		siteRequest.setSiteConfig_(siteContext.getSiteConfig());
		siteRequest.setOperationRequest(operationRequest);
		siteRequest.initDeepSiteRequestEnUS(siteRequest);

		return siteRequest;
	}

	public void userSchoolEnrollment(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SQLConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			if(userId == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnection.queryWithParams(
						SiteContextEnUS.SQL_selectC
						, new JsonArray(Arrays.asList("org.computate.scolaire.enUS.user.SiteUser", userId))
						, selectCAsync
				-> {
					if(selectCAsync.succeeded()) {
						try {
							JsonArray userValues = selectCAsync.result().getResults().stream().findFirst().orElse(null);
							SiteUserEnUSGenApiServiceImpl userService = new SiteUserEnUSGenApiServiceImpl(siteContext);
							if(userValues == null) {
								JsonObject userVertx = siteRequest.getOperationRequest().getUser();
								JsonObject jsonPrincipal = KeycloakHelper.parseToken(userVertx.getString("access_token"));

								JsonObject jsonObject = new JsonObject();
								jsonObject.put("userName", jsonPrincipal.getString("preferred_username"));
								jsonObject.put("userFirstName", jsonPrincipal.getString("given_name"));
								jsonObject.put("userLastName", jsonPrincipal.getString("family_name"));
								jsonObject.put("userId", jsonPrincipal.getString("sub"));
								userSchoolEnrollmentDefine(siteRequest, jsonObject, false);

								SiteRequestEnUS siteRequest2 = new SiteRequestEnUS();
								siteRequest2.setSqlConnection(siteRequest.getSqlConnection());
								siteRequest2.setJsonObject(jsonObject);
								siteRequest2.setVertx(siteRequest.getVertx());
								siteRequest2.setSiteContext_(siteContext);
								siteRequest2.setSiteConfig_(siteContext.getSiteConfig());
								siteRequest2.setUserId(siteRequest.getUserId());
								siteRequest2.initDeepSiteRequestEnUS(siteRequest);

								userService.createSiteUser(siteRequest2, b -> {
									if(b.succeeded()) {
										SiteUser siteUser = b.result();
										userService.sqlPOSTSiteUser(siteUser, c -> {
											if(c.succeeded()) {
												userService.defineSiteUser(siteUser, d -> {
													if(d.succeeded()) {
														userService.attributeSiteUser(siteUser, e -> {
															if(e.succeeded()) {
																userService.indexSiteUser(siteUser, f -> {
																	if(f.succeeded()) {
																		siteRequest.setSiteUser(siteUser);
																		siteRequest.setUserName(jsonPrincipal.getString("preferred_username"));
																		siteRequest.setUserFirstName(jsonPrincipal.getString("given_name"));
																		siteRequest.setUserLastName(jsonPrincipal.getString("family_name"));
																		siteRequest.setUserId(jsonPrincipal.getString("sub"));
																		siteRequest.setUserKey(siteUser.getPk());
																		eventHandler.handle(Future.succeededFuture());
																	} else {
																		errorSchoolEnrollment(siteRequest, eventHandler, f);
																	}
																});
															} else {
																errorSchoolEnrollment(siteRequest, eventHandler, e);
															}
														});
													} else {
														errorSchoolEnrollment(siteRequest, eventHandler, d);
													}
												});
											} else {
												errorSchoolEnrollment(siteRequest, eventHandler, c);
											}
										});
									} else {
										errorSchoolEnrollment(siteRequest, eventHandler, b);
									}
								});
							} else {
								Long pkUser = userValues.getLong(0);
								SearchList<SiteUser> searchList = new SearchList<SiteUser>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(SiteUser.class);
								searchList.addFilterQuery("userId_indexed_string:" + ClientUtils.escapeQueryChars(userId));
								searchList.addFilterQuery("pk_indexed_long:" + pkUser);
								searchList.initDeepSearchList(siteRequest);
								SiteUser siteUser1 = searchList.getList().stream().findFirst().orElse(null);

								JsonObject userVertx = siteRequest.getOperationRequest().getUser();
								JsonObject jsonPrincipal = KeycloakHelper.parseToken(userVertx.getString("access_token"));

								JsonObject jsonObject = new JsonObject();
								jsonObject.put("setUserName", jsonPrincipal.getString("preferred_username"));
								jsonObject.put("setUserFirstName", jsonPrincipal.getString("given_name"));
								jsonObject.put("setUserLastName", jsonPrincipal.getString("family_name"));
								jsonObject.put("setUserCompleteName", jsonPrincipal.getString("name"));
								jsonObject.put("setCustomerProfileId", Optional.ofNullable(siteUser1).map(u -> u.getCustomerProfileId()).orElse(null));
								jsonObject.put("setUserId", jsonPrincipal.getString("sub"));
								jsonObject.put("setUserEmail", jsonPrincipal.getString("email"));
								Boolean define = userSchoolEnrollmentDefine(siteRequest, jsonObject, true);
								if(define) {
									SiteUser siteUser;
									if(siteUser1 == null) {
										siteUser = new SiteUser();
										siteUser.setPk(pkUser);
										siteUser.setSiteRequest_(siteRequest);
									} else {
										siteUser = siteUser1;
									}

									SiteRequestEnUS siteRequest2 = new SiteRequestEnUS();
									siteRequest2.setSqlConnection(siteRequest.getSqlConnection());
									siteRequest2.setJsonObject(jsonObject);
									siteRequest2.setVertx(siteRequest.getVertx());
									siteRequest2.setSiteContext_(siteContext);
									siteRequest2.setSiteConfig_(siteContext.getSiteConfig());
									siteRequest2.setUserId(siteRequest.getUserId());
									siteRequest2.setUserKey(siteRequest.getUserKey());
									siteRequest2.initDeepSiteRequestEnUS(siteRequest);
									siteUser.setSiteRequest_(siteRequest2);

									userService.sqlPATCHSiteUser(siteUser, c -> {
										if(c.succeeded()) {
											SiteUser siteUser2 = c.result();
											userService.defineSiteUser(siteUser2, d -> {
												if(d.succeeded()) {
													userService.attributeSiteUser(siteUser2, e -> {
														if(e.succeeded()) {
															userService.indexSiteUser(siteUser2, f -> {
																if(f.succeeded()) {
																	siteRequest.setSiteUser(siteUser2);
																	siteRequest.setUserName(siteUser2.getUserName());
																	siteRequest.setUserFirstName(siteUser2.getUserFirstName());
																	siteRequest.setUserLastName(siteUser2.getUserLastName());
																	siteRequest.setUserId(siteUser2.getUserId());
																	siteRequest.setUserKey(siteUser2.getPk());
																	eventHandler.handle(Future.succeededFuture());
																} else {
																	errorSchoolEnrollment(siteRequest, eventHandler, f);
																}
															});
														} else {
															errorSchoolEnrollment(siteRequest, eventHandler, e);
														}
													});
												} else {
													errorSchoolEnrollment(siteRequest, eventHandler, d);
												}
											});
										} else {
											errorSchoolEnrollment(siteRequest, eventHandler, c);
										}
									});
								} else {
									siteRequest.setSiteUser(siteUser1);
									siteRequest.setUserName(siteUser1.getUserName());
									siteRequest.setUserFirstName(siteUser1.getUserFirstName());
									siteRequest.setUserLastName(siteUser1.getUserLastName());
									siteRequest.setUserId(siteUser1.getUserId());
									siteRequest.setUserKey(siteUser1.getPk());
									eventHandler.handle(Future.succeededFuture());
								}
							}
						} catch(Exception e) {
							eventHandler.handle(Future.failedFuture(e));
						}
					} else {
						eventHandler.handle(Future.failedFuture(new Exception(selectCAsync.cause())));
					}
				});
			}
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public Boolean userSchoolEnrollmentDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return jsonObject.getString("setCustomerProfileId") == null;
		} else {
			return jsonObject.getString("customerProfileId") == null;
		}
	}

	public void aSearchSchoolEnrollmentQ(String classApiUriMethod, SearchList<SchoolEnrollment> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
			searchList.setHighlight(true);
			searchList.setHighlightSnippets(3);
			searchList.addHighlightField(varIndexed);
			searchList.setParam("hl.encoder", "html");
		}
	}

	public void aSearchSchoolEnrollmentFq(String classApiUriMethod, SearchList<SchoolEnrollment> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addFilterQuery(varIndexed + ":" + ClientUtils.escapeQueryChars(valueIndexed));
	}

	public void aSearchSchoolEnrollmentSort(String classApiUriMethod, SearchList<SchoolEnrollment> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchSchoolEnrollmentRows(String classApiUriMethod, SearchList<SchoolEnrollment> searchList, Integer valueRows) {
		searchList.setRows(valueRows);
	}

	public void aSearchSchoolEnrollmentStart(String classApiUriMethod, SearchList<SchoolEnrollment> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchSchoolEnrollmentVar(String classApiUriMethod, SearchList<SchoolEnrollment> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchSchoolEnrollment(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, String classApiUriMethod, Handler<AsyncResult<SearchList<SchoolEnrollment>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();
			String entityListStr = siteRequest.getOperationRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<SchoolEnrollment> searchList = new SearchList<SchoolEnrollment>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(SchoolEnrollment.class);
			searchList.setSiteRequest_(siteRequest);
			if(entityList != null)
				searchList.addFields(entityList);
			searchList.add("json.facet", "{max_modified:'max(modified_indexed_date)'}");

			String id = operationRequest.getParams().getJsonObject("path").getString("id");
			if(id != null) {
				searchList.addFilterQuery("(id:" + ClientUtils.escapeQueryChars(id) + " OR objectId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
			}

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				searchList.addFilterQuery("sessionId_indexed_string:" + ClientUtils.escapeQueryChars(Optional.ofNullable(siteRequest.getSessionId()).orElse("-----"))
						+ " OR userKeys_indexed_longs:" + Optional.ofNullable(siteRequest.getUserKey()).orElse(0L));
			}

			operationRequest.getParams().getJsonObject("query").forEach(paramRequest -> {
				String entityVar = null;
				String valueIndexed = null;
				String varIndexed = null;
				String valueSort = null;
				Integer valueStart = null;
				Integer valueRows = null;
				String paramName = paramRequest.getKey();
				Object paramValuesObject = paramRequest.getValue();
				JsonArray paramObjects = paramValuesObject instanceof JsonArray ? (JsonArray)paramValuesObject : new JsonArray().add(paramValuesObject);

				try {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								varIndexed = "*".equals(entityVar) ? entityVar : SchoolEnrollment.varSearchSchoolEnrollment(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchSchoolEnrollmentQ(classApiUriMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								varIndexed = SchoolEnrollment.varIndexedSchoolEnrollment(entityVar);
								aSearchSchoolEnrollmentFq(classApiUriMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "sort":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
								valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
								varIndexed = SchoolEnrollment.varIndexedSchoolEnrollment(entityVar);
								aSearchSchoolEnrollmentSort(classApiUriMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = (Integer)paramObject;
								aSearchSchoolEnrollmentStart(classApiUriMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = (Integer)paramObject;
								aSearchSchoolEnrollmentRows(classApiUriMethod, searchList, valueRows);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchSchoolEnrollmentVar(classApiUriMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
				} catch(Exception e) {
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			if(searchList.getSorts().size() == 0) {
				searchList.addSort("created_indexed_date", ORDER.desc);
			}
			searchList.initDeepForClass(siteRequest);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void defineSchoolEnrollment(SchoolEnrollment o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SQLConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.queryWithParams(
					SiteContextEnUS.SQL_define
					, new JsonArray(Arrays.asList(pk, pk, pk))
					, defineAsync
			-> {
				if(defineAsync.succeeded()) {
					try {
						for(JsonArray definition : defineAsync.result().getResults()) {
							try {
								o.defineForClass(definition.getString(0), definition.getString(1));
							} catch(Exception e) {
								LOGGER.error(e);
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					eventHandler.handle(Future.failedFuture(new Exception(defineAsync.cause())));
				}
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeSchoolEnrollment(SchoolEnrollment o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SQLConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.queryWithParams(
					SiteContextEnUS.SQL_attribute
					, new JsonArray(Arrays.asList(pk, pk))
					, attributeAsync
			-> {
				try {
					if(attributeAsync.succeeded()) {
						if(attributeAsync.result() != null) {
							for(JsonArray definition : attributeAsync.result().getResults()) {
								if(pk.equals(definition.getLong(0)))
									o.attributeForClass(definition.getString(2), definition.getLong(1));
								else
									o.attributeForClass(definition.getString(3), definition.getLong(0));
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} else {
						eventHandler.handle(Future.failedFuture(new Exception(attributeAsync.cause())));
					}
				} catch(Exception e) {
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexSchoolEnrollment(SchoolEnrollment o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			if(BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(null))) {
				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSchoolEnrollment(siteContext, siteRequest.getOperationRequest(), new JsonObject());
				siteRequest2.setSqlConnection(siteRequest.getSqlConnection());
				SearchList<SchoolEnrollment> searchList = new SearchList<SchoolEnrollment>();
				searchList.setPopulate(true);
				searchList.setQuery("*:*");
				searchList.setC(SchoolEnrollment.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{yearKey:{terms:{field:yearKey_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{blockKeys:{terms:{field:blockKeys_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{childKey:{terms:{field:childKey_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{momKeys:{terms:{field:momKeys_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{dadKeys:{terms:{field:dadKeys_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{guardianKeys:{terms:{field:guardianKeys_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{paymentKeys:{terms:{field:paymentKeys_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{userKeys:{terms:{field:userKeys_indexed_longs, limit:1000}}}");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest2);
				List<Future> futures = new ArrayList<>();

				{
					SchoolYear o2 = new SchoolYear();
					SchoolYearEnUSGenApiServiceImpl service = new SchoolYearEnUSGenApiServiceImpl(siteRequest2.getSiteContext_());
					Long pk = o.getYearKey();

					if(pk != null) {
						o2.setPk(pk);
						o2.setSiteRequest_(siteRequest2);
						futures.add(
							service.patchSchoolYearFuture(o2, a -> {
								if(a.succeeded()) {
									LOGGER.info(String.format("SchoolYear %s refreshed. ", pk));
								} else {
									LOGGER.info(String.format("SchoolYear %s failed. ", pk));
									eventHandler.handle(Future.failedFuture(a.cause()));
								}
							})
						);
					}
				}

				{
					SchoolBlockEnUSGenApiServiceImpl service = new SchoolBlockEnUSGenApiServiceImpl(siteRequest2.getSiteContext_());
					for(Long pk : o.getBlockKeys()) {
						SchoolBlock o2 = new SchoolBlock();

						o2.setPk(pk);
						o2.setSiteRequest_(siteRequest2);
						futures.add(
							service.patchSchoolBlockFuture(o2, a -> {
								if(a.succeeded()) {
									LOGGER.info(String.format("SchoolBlock %s refreshed. ", pk));
								} else {
									LOGGER.info(String.format("SchoolBlock %s failed. ", pk));
									eventHandler.handle(Future.failedFuture(a.cause()));
								}
							})
						);
					}
				}

				{
					SchoolChild o2 = new SchoolChild();
					SchoolChildEnUSGenApiServiceImpl service = new SchoolChildEnUSGenApiServiceImpl(siteRequest2.getSiteContext_());
					Long pk = o.getChildKey();

					if(pk != null) {
						o2.setPk(pk);
						o2.setSiteRequest_(siteRequest2);
						futures.add(
							service.patchSchoolChildFuture(o2, a -> {
								if(a.succeeded()) {
									LOGGER.info(String.format("SchoolChild %s refreshed. ", pk));
								} else {
									LOGGER.info(String.format("SchoolChild %s failed. ", pk));
									eventHandler.handle(Future.failedFuture(a.cause()));
								}
							})
						);
					}
				}

				{
					SchoolMomEnUSGenApiServiceImpl service = new SchoolMomEnUSGenApiServiceImpl(siteRequest2.getSiteContext_());
					for(Long pk : o.getMomKeys()) {
						SchoolMom o2 = new SchoolMom();

						o2.setPk(pk);
						o2.setSiteRequest_(siteRequest2);
						futures.add(
							service.patchSchoolMomFuture(o2, a -> {
								if(a.succeeded()) {
									LOGGER.info(String.format("SchoolMom %s refreshed. ", pk));
								} else {
									LOGGER.info(String.format("SchoolMom %s failed. ", pk));
									eventHandler.handle(Future.failedFuture(a.cause()));
								}
							})
						);
					}
				}

				{
					SchoolDadEnUSGenApiServiceImpl service = new SchoolDadEnUSGenApiServiceImpl(siteRequest2.getSiteContext_());
					for(Long pk : o.getDadKeys()) {
						SchoolDad o2 = new SchoolDad();

						o2.setPk(pk);
						o2.setSiteRequest_(siteRequest2);
						futures.add(
							service.patchSchoolDadFuture(o2, a -> {
								if(a.succeeded()) {
									LOGGER.info(String.format("SchoolDad %s refreshed. ", pk));
								} else {
									LOGGER.info(String.format("SchoolDad %s failed. ", pk));
									eventHandler.handle(Future.failedFuture(a.cause()));
								}
							})
						);
					}
				}

				{
					SchoolGuardianEnUSGenApiServiceImpl service = new SchoolGuardianEnUSGenApiServiceImpl(siteRequest2.getSiteContext_());
					for(Long pk : o.getGuardianKeys()) {
						SchoolGuardian o2 = new SchoolGuardian();

						o2.setPk(pk);
						o2.setSiteRequest_(siteRequest2);
						futures.add(
							service.patchSchoolGuardianFuture(o2, a -> {
								if(a.succeeded()) {
									LOGGER.info(String.format("SchoolGuardian %s refreshed. ", pk));
								} else {
									LOGGER.info(String.format("SchoolGuardian %s failed. ", pk));
									eventHandler.handle(Future.failedFuture(a.cause()));
								}
							})
						);
					}
				}

				{
					SchoolPaymentEnUSGenApiServiceImpl service = new SchoolPaymentEnUSGenApiServiceImpl(siteRequest2.getSiteContext_());
					for(Long pk : o.getPaymentKeys()) {
						SchoolPayment o2 = new SchoolPayment();

						o2.setPk(pk);
						o2.setSiteRequest_(siteRequest2);
						futures.add(
							service.patchSchoolPaymentFuture(o2, a -> {
								if(a.succeeded()) {
									LOGGER.info(String.format("SchoolPayment %s refreshed. ", pk));
								} else {
									LOGGER.info(String.format("SchoolPayment %s failed. ", pk));
									eventHandler.handle(Future.failedFuture(a.cause()));
								}
							})
						);
					}
				}

				{
					SiteUserEnUSGenApiServiceImpl service = new SiteUserEnUSGenApiServiceImpl(siteRequest2.getSiteContext_());
					for(Long pk : o.getUserKeys()) {
						SiteUser o2 = new SiteUser();

						o2.setPk(pk);
						o2.setSiteRequest_(siteRequest2);
						futures.add(
							service.patchSiteUserFuture(o2, a -> {
								if(a.succeeded()) {
									LOGGER.info(String.format("SiteUser %s refreshed. ", pk));
								} else {
									LOGGER.info(String.format("SiteUser %s failed. ", pk));
									eventHandler.handle(Future.failedFuture(a.cause()));
								}
							})
						);
					}
				}

				CompositeFuture.all(futures).setHandler(a -> {
					if(a.succeeded()) {
						LOGGER.info("Refresh relations succeeded. ");
						SchoolEnrollmentEnUSGenApiServiceImpl service = new SchoolEnrollmentEnUSGenApiServiceImpl(siteRequest2.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(SchoolEnrollment o2 : searchList.getList()) {
							futures2.add(
								service.patchSchoolEnrollmentFuture(o2, b -> {
									if(b.succeeded()) {
										LOGGER.info(String.format("SchoolEnrollment %s refreshed. ", o2.getPk()));
									} else {
										LOGGER.info(String.format("SchoolEnrollment %s failed. ", o2.getPk()));
										eventHandler.handle(Future.failedFuture(b.cause()));
									}
								})
							);
						}

						CompositeFuture.all(futures2).setHandler(b -> {
							if(b.succeeded()) {
								LOGGER.info("Refresh SchoolEnrollment succeeded. ");
								eventHandler.handle(Future.succeededFuture());
							} else {
								LOGGER.error("Refresh relations failed. ", b.cause());
								errorSchoolEnrollment(siteRequest2, eventHandler, b);
							}
						});
					} else {
						LOGGER.error("Refresh relations failed. ", a.cause());
						errorSchoolEnrollment(siteRequest2, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
