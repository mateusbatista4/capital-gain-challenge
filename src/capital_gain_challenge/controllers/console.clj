(ns capital-gain-challenge.controllers.console
  (:require
   [capital-gain-challenge.controllers.capital-gain :as cg-controller]
   [capital-gain-challenge.logic.console :as c-logic]
   [capital-gain-challenge.models.capital-gain :as m]
   [schema.core :as s]))

(s/defn read-json-from-keyboard :- m/UserInput
  "Funcion responsible for read json orders from keyboard as the example bellow"
  ;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000}, {"operation":"sell", "unit-cost":20.00, "quantity": 5000}]
  ;; 
  []
  (println "Enter JSON orders (all on one line):")
  (let [input (read-line)]
    (try
      (c-logic/json-string->user-input input)
      (catch Exception e
        (println "Error parsing JSON input:" (.getMessage e))
        []))))


(s/defn start-console
  "Function responsible for running the core console interface"
  []
  (println "Welcome to the Capital-Gain Order System")
  (let [wallet (atom (m/new-wallet))]
    (while true
      (println "Choose option:")
      (println "1. Enter JSON with the orders (only one line)")
      (println "2. Show wallet")
      (println "3. Finish")
      (let [choice (read-line)]
        (case choice
          "1" (let [orders (read-json-from-keyboard)
                    taxes (cg-controller/process-orders! wallet orders)]
                (println taxes))
          "2" (println @wallet)
          "3" (throw (ex-info "break" {}))
          (println "Invalid choice"))))))
