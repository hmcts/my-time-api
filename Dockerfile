ARG APP_INSIGHTS_AGENT_VERSION=3.2.10
FROM hmctspublic.azurecr.io/base/java:17-distroless

COPY build/libs/mytime-api.jar /opt/app/

EXPOSE 4000
CMD [ "mytime-api.jar" ]
