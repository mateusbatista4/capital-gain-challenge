(ns capital-gain-challenge.models.capital-gain
  (:require
    [schema.core :as s]))

(def UserOrder
  {:operation s/Str :unit-cost s/Num :quantity s/Int})

(def UserInput
  [UserOrder])

(def Wallet 
  {:average-price s/Num :quantity s/Int :accumulated-loss s/Num})

(s/defn new-wallet :- Wallet
  []
  {:average-price 0 :quantity 0 :accumulated-loss 0})