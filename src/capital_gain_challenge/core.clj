(ns capital-gain-challenge.core
  (:gen-class)
  (:require [capital-gain-challenge.controllers.console :as console]))



(defn -main [& args]
  (console/start-console))
