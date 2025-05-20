(ns capital-gain-challenge.cg-logic-test
  (:require 
   [capital-gain-challenge.logic.capital-gain :as cg-logic]
   [clojure.test :refer :all]))




(deftest test-new-weighted-average
  (testing "New Weighted Average function"
    (let [m (cg-logic/new-weighted-average 10 20 0 0)
           m2 (cg-logic/new-weighted-average 5 10 5 m)]
      (is (= m2 15)))))


(deftest test-tax-amount
  (testing "No tax when sell value is under limit"
    (is (= 0.0 (cg-logic/tax-amount 10000.0 0.0 100.0 100))))

  (testing "Tax applied when sell value is over limit and no loss"
    (is (= 2000.0 (cg-logic/tax-amount 10000.0 0.0 250.0 100))))

  (testing "Tax applied on reduced profit after loss"
    (is (= 1000.0 (cg-logic/tax-amount 10000.0 5000.0 250.0 100))))

  (testing "No tax when profit is completely offset by losses"
    (is (= 0.0 (cg-logic/tax-amount 5000.0 10000.0 250.0 100))))

  (testing "No tax when final profit is zero after applying loss"
    (is (= 0.0 (cg-logic/tax-amount 5000.0 5000.0 250.0 100))))

  (testing "Tax is only calculated on positive final profit"
    (is (= 0.0 (cg-logic/tax-amount -1000.0 0.0 250.0 100)))))



(deftest test-sell-passes-limit-value?
  (testing "Sell value just below limit"
    (is (false? (cg-logic/sell-passes-limit-value? 100.0 199))))

  (testing "Sell value exactly at the limit"
    (is (false? (cg-logic/sell-passes-limit-value? 200.0 100))))

  (testing "Sell value above the limit"
    (is (true? (cg-logic/sell-passes-limit-value? 250.0 100)))))


(deftest test-profit
  (testing "Positive profit"
    (is (= 1000.0 (cg-logic/profit 50.0 10 40.0))))  ; 500 - 400

  (testing "Zero profit"
    (is (= 0.0 (cg-logic/profit 50.0 10 50.0))))     ; 500 - 500

  (testing "Negative profit (loss)"
    (is (= -200.0 (cg-logic/profit 30.0 10 50.0)))) ; 300 - 500

  (testing "Zero quantity"
    (is (= 0.0 (cg-logic/profit 100.0 0 50.0))))     ; 0 - 0

  (testing "Edge case: zero prices"
    (is (= 0.0 (cg-logic/profit 0.0 10 0.0))))       ; 0 - 0

  (testing "High volume and precision"
    (is (= 123456.7 (cg-logic/profit 1234.567 100 0.0)))))


