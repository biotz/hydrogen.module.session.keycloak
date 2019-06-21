(ns hydrogen.module.session.keycloak-test
  (:require [clojure.test :refer :all]
            [duct.core :as core]
            [duct.core.env :as env]
            [hydrogen.module.session.keycloak :as sut]
            [integrant.core :as ig]))

(core/load-hierarchy)

(def base-config
  {:duct.profile/base {:duct.core/project-ns 'foo.bar}
   :hydrogen.module/session.keycloak {:keycloak {:realm "foo"
                                                 :url "http://localhost:8080/auth"
                                                 :client-id 123}
                                      :oidc {:issuer "http://localhost:8080/auth/realms/foo"
                                             :audience "foobarbaz"
                                             :jwks-uri "http://localhost:8080/auth/realms/foo/protocol/openid-connect/certs"}}})

(deftest module-test
  (testing "blank config"
    (is (= {:duct.core/project-ns 'foo.bar
            :foo.bar.api/config
            {:keycloak
             {:realm "foo"
              :url "http://localhost:8080/auth"
              :client-id 123}}

            :magnet.buddy-auth/jwt-oidc
            {:claims
             {:iss "http://localhost:8080/auth/realms/foo"
              :aud "foobarbaz"}
             :jwks-uri "http://localhost:8080/auth/realms/foo/protocol/openid-connect/certs"}

            :duct.middleware.buddy/authentication
            {:backend :token
             :token-name "Bearer"
             :authfn (ig/ref :magnet.buddy-auth/jwt-oidc)}}
           (core/build-config base-config))))

  (testing "deprecated empty config still works"
    (is (= {:duct.core/project-ns 'foo.bar
            :foo.bar.api/config
            {:keycloak
             {:realm (env/env '["KEYCLOAK_REALM" Str])
              :url (env/env '["KEYCLOAK_URL" Str])
              :client-id (env/env '["KEYCLOAK_CLIENT_ID" Str])}}

            :magnet.buddy-auth/jwt-oidc
            {:claims
             {:iss (env/env '["OIDC_ISSUER_URL" Str])
              :aud (env/env '["OIDC_AUDIENCE" Str])}
             :jwks-uri (env/env '["OIDC_JWKS_URI" Str])}

            :duct.middleware.buddy/authentication
            {:backend :token
             :token-name "Bearer"
             :authfn (ig/ref :magnet.buddy-auth/jwt-oidc)}}
           (core/build-config (assoc base-config :hydrogen.module/session.keycloak {})))))

  (testing "add example api config"
    (let [config (core/build-config (merge base-config
                                           {:hydrogen.module/session.keycloak {:add-example-api? true}}))]
      (is (some-> config
                  (get :foo.bar.api/example)
                  (= {:auth-middleware (ig/ref :duct.middleware.buddy/authentication)}))))))