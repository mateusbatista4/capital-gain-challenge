(ns capital-gain-challenge.logic.console
  (:require
   [capital-gain-challenge.models.capital-gain :as m]
   [schema.core :as s]
   [clojure.data.json :as json]))

(defn- keywordize-keys [map]
  (reduce-kv (fn [m k v]
               (assoc m (keyword k) v))
             {}
             map))

(s/defn json-string->user-input :- m/UserInput
  [json-str :- s/Str]
  (let [parsed-json (json/read-str json-str)
        orders (map keywordize-keys parsed-json)]
    (s/validate m/UserInput orders)
    orders))