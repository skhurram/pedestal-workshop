(defproject todoit "0.1.0-SNAPSHOT"
  :url "https://github.com/rkneufeld/pedestal-workshop"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :repositories {"my.datomic.com" {:url "https://my.datomic.com/repo"
                                   :username "myemail"
                                   :password "mypassword"}}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.datomic/datomic-pro "0.9.4384"
                  :exclusions [org.slf4j/jul-to-slf4j
                               org.slf4j/slf4j-nop]]
                 [io.pedestal/pedestal.service "0.3.0-SNAPSHOT"]
                 [io.pedestal/pedestal.service-tools "0.3.0-SNAPSHOT"]
                 [io.pedestal/pedestal.jetty "0.3.0-SNAPSHOT"]
                 [ns-tracker "0.2.2"]
                 [ring/ring-devel "1.2.2"]
                 [hiccup "1.0.5"]
                 [environ "0.5.0"]]

  :plugins [[lein-environ "0.5.0"]]

  :main ^{:skip-aot true} todoit.core)

