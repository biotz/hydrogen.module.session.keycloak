(ns hydrogen.module.session.keycloak
  (:require [duct.core :as core]
            [duct.core.env :as env]
            [integrant.core :as ig]))

(defn project-ns [config options]
  (:project-ns options (:duct.core/project-ns config)))

(defn- session-config-base [project-ns]
  {(keyword (str project-ns ".api/config"))
   {:keycloak
    {:realm (env/env '["KEYCLOAK_REALM" Str])
     :url (env/env '["KEYCLOAK_URL" Str])
     :clientId (env/env '["KEYCLOAK_CLIENT_ID" Str])}}})

(defn- session-config [options project-ns]
  (cond->
   (session-config-base project-ns)

    (:add-example-api? options)
    (assoc (keyword (str project-ns ".api/example")) {})))

(defmethod ig/init-key :hydrogen.module/session.keycloak [_ options]
  (fn [config]
    (let [project-ns (project-ns config options)]
      (core/merge-configs config (session-config options project-ns)))))

