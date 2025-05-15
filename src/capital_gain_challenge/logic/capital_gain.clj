(ns capital-gain-challenge.logic.capital-gain
  (:require
   [schema.core :as s]))

(s/defn buy?
  [order]
  (= order "buy"))

(s/defn operation-allowed?
  [current-stocks-quantity
   quantity]
  (>= current-stocks-quantity quantity))

(s/defn new-weighted-average
  [buy-quantity :- s/Int
   buy-price :- s/Num
   current-stocks-quantity :- s/Int
   current-weighted-average :- s/Num]
  (/ (+ (* current-stocks-quantity current-weighted-average) (* buy-quantity buy-price)) (+ current-stocks-quantity buy-quantity)))

(s/defn new-accumulated-loss
  [profit
   current-accumulated-loss
   quantity
   price]
  (let [operation-amount (* quantity price)]
    (if (and (> profit 0) (<= operation-amount 20000))
      current-accumulated-loss
      (if (< profit 0)
        (+ current-accumulated-loss (- profit))
        (if (>= profit current-accumulated-loss)
          0
          (Math/abs (- profit current-accumulated-loss)))))))


(s/defn profit
  [sell-price
   sell-quantity
   current-weighted-average]
  (- (* sell-price sell-quantity) (* current-weighted-average sell-quantity)))

(s/defn sell-passes-limit-value?
  [sell-price
   sell-quantity]
  (> (* sell-price sell-quantity) 20000))

(s/defn tax-amount
  [profit
   accumulated-loss
   sell-price
   sell-quantity]
  (let [final-profit (max 0 (if (> accumulated-loss 0)  (- profit accumulated-loss) profit))
        sell-passes-limit-value (sell-passes-limit-value? sell-price sell-quantity)]
    (if sell-passes-limit-value (* final-profit 0.2) 0)))
