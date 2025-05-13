(ns capital-gain-challenge.controllers.console
  (:require
   [capital-gain-challenge.controllers.capital-gain :as cg-controller]
   [capital-gain-challenge.logic.capital-gain :as cg-logic]
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

(s/defn process-orders!
  [wallet :- m/Wallet
   orders :- m/UserOrder]
  (println "Processing" (count orders) "order(s)")
  (let [results (atom [])]
    (doseq [item orders]

      (let [operation-type (:operation item)
            quantity (:quantity item)
            price (:unit-cost item)
            result (if (cg-logic/buy? operation-type)
                     (cg-controller/buy-stocks! wallet quantity price)
                     (cg-controller/sell-stocks! wallet quantity price))]
        (swap! results conj result))) @results))

(s/defn start-console
  "Function responsible for running the core console interface"
  []
  (println "Welcome to the Capital-Gain Order System")
  (let [wallet (atom (m/new-wallet))]
    (while true
      (println "Choose option:")
      (println "1. Enter JSON with the orders (only one line)")
      (println "2. Show wallet")
      (let [choice (read-line)]
        (case choice
          "1" (let [orders (read-json-from-keyboard)
                    taxes (process-orders! wallet orders)]
                (println taxes))
          "2" (println @wallet)
          (println "Invalid choice"))))))

;; Test 1:
;;[{"operation":"buy", "unit-cost":10.00, "quantity": 100}, {"operation":"sell", "unit-cost":15.00, "quantity": 50},{"operation":"sell", "unit-cost":15.00, "quantity": 50}]

;; Test 2:
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000}, {"operation":"sell", "unit-cost":20.00, "quantity": 5000},{"operation":"sell", "unit-cost":5.00, "quantity": 5000}]

;; Test 3:
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 100},{"operation":"sell", "unit-cost":15.00, "quantity": 50},{"operation":"sell", "unit-cost":15.00, "quantity": 50}]
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"sell", "unit-cost":20.00, "quantity": 5000},{"operation":"sell", "unit-cost":5.00, "quantity": 5000}]

;; Test 4;
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"sell", "unit-cost":5.00, "quantity": 5000},{"operation":"sell", "unit-cost":20.00, "quantity": 3000}]

;; Test 5;
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"buy", "unit-cost":25.00, "quantity": 5000},{"operation":"sell", "unit-cost":15.00, "quantity": 10000}]