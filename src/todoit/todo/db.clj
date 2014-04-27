(ns todoit.todo.db
  (:require [datomic.api :as d]
            [environ.core :as env]))

(def aws-creds
  {:access-key (env/env :aws-access-key)
   :secret-key (env/env :aws-secret-key)})

;; (defonce uri (str "datomic:mem://" (gensym "todos")))

(defonce uri (str "datomic:ddb://us-east-1/your-system-name/test-db"
                  "?aws_access_key_id=" (:access-key aws-creds)
                  "&aws_secret_key=" (:secret-key aws-creds)))

(d/create-database uri)
(def conn (d/connect uri))

(defn communities [db]
  (d/q '[:find ?c :where [?c :community/name]]
       db))
;; (communities (d/db conn))


(def schema-tx (->> "todos.edn"
                    clojure.java.io/resource
                    slurp
                    (clojure.edn/read-string {:readers *data-readers*})))

@(d/transact conn schema-tx)

(defn todo-tx [title desc]
  (cond-> {:db/id (d/tempid :db.part/user)
           :todo/title title
           :todo/completed? false}
          desc (assoc :todo/description desc) ;; Add description if not nil
          true vector))                       ;; Wrap in vector

(defn create-todo [title desc]
  @(d/transact conn (todo-tx title desc)))

(defn all-todos [db]
  (->> (d/q '[:find ?id
              :where [?id :todo/title]]
            db)                 ; #{[12341123] [12357223] [134571345]}
       (map first)              ; (12341123 12357223 134571345)
       (map #(d/entity db %)))) ; ({:db/id 12341123} ...)

(defn completed-todos [db]
  (->> (d/q '[:find ?id
              :where
              [?id :todo/title]
              [?id :todo/completed? true]]
            db)
       (map first)
       (map #(d/entity db %))))

(defn toggle-status [id status]
  @(d/transact conn [[:db/add id :todo/completed? status]]))

(defn delete-todo [id]
  @(d/transact conn [[:db.fn/retractEntity id]]))
