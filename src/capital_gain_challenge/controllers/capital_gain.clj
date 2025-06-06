(ns capital-gain-challenge.controllers.capital-gain
  (:require [schema.core :as s]
            [capital-gain-challenge.models.capital-gain :as models]
            [capital-gain-challenge.logic.capital-gain :as logic]))


(s/defn buy-stocks!
  [wallet :- models/Wallet
   quantity :- s/Int
   price :- s/Num]
  (let [current-weighted-average (:average-price @wallet)
        current-stocks-quantity (:quantity @wallet)
        new-stocks-quantity (+ current-stocks-quantity quantity)
        new-weighted-average (logic/new-weighted-average quantity price current-stocks-quantity current-weighted-average)]

    (swap! wallet (fn [w] (-> w
                              (assoc :error-count 0)
                              (assoc :average-price new-weighted-average)
                              (assoc :quantity new-stocks-quantity))))
    {:tax 0.0}))

(s/defn sell-stocks!
  [wallet :- models/Wallet
   quantity :- s/Int
   price :- s/Num]
  (if (not (logic/operation-allowed? (:quantity @wallet) quantity))
    (do
      (swap! wallet (fn [w] (-> w
                                (assoc :error-count (inc (:error-count @wallet))))))
      {:error "Can't sell more stocks than you have"})
    (let [current-weighted-average (:average-price @wallet)
          current-stocks-quantity (:quantity @wallet)
          current-accumulated-loss (:accumulated-loss @wallet)
          profit (logic/profit price quantity current-weighted-average)
          new-stocks-quantity (- current-stocks-quantity quantity)
          tax-amount (logic/tax-amount profit current-accumulated-loss price quantity)
          new-accumulated-loss (logic/new-accumulated-loss profit current-accumulated-loss quantity price)]

      (swap! wallet (fn [w] (-> w
                                (assoc :error-count 0)
                                (assoc :quantity new-stocks-quantity)
                                (assoc :accumulated-loss new-accumulated-loss))))
      {:tax tax-amount})))

(s/defn process-orders!
  [wallet :- models/Wallet
   orders :- models/UserInput]
  (let [results (atom [])]
    (doseq [item orders]
      (let [operation-type (:operation item)
            quantity (:quantity item)
            price (:unit-cost item)
            result (if (not (logic/wallet-blocked? @wallet)) (if (logic/buy? operation-type)
                                                               (buy-stocks! wallet quantity price)
                                                               (sell-stocks! wallet quantity price))
                       {:error "Your account is blocked"})]
        (swap! results conj result))) @results))

