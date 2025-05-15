(ns capital-gain-challenge.core
  (:gen-class)
  (:require [capital-gain-challenge.controllers.console :as console]))


;test for function
;; (let [m (l/new-pondered-average 10 20 0 0)
;;       m2 (l/new-pondered-average 5 10 5 m)]
;;   (println m2))


(defn -main [& args]
  (console/start-console))
