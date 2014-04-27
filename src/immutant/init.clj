(ns immutant.init
  (:require [immutant.web             :as web]
            [io.pedestal.http :as http]
            [io.pedestal.http.servlet :as servlet]
            [todoit.core            :as app]))
(web/start-servlet "/" (::http/servlet (http/create-servlet app/service)))
