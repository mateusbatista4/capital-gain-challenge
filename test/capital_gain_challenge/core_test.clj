(ns capital-gain-challenge.core-test
  (:require
   [capital-gain-challenge.controllers.capital-gain :as cg-controller]
   [capital-gain-challenge.core :refer :all]
   [capital-gain-challenge.models.capital-gain :as models]
   [clojure.test :refer :all]))


(deftest testcase-1
  (testing "Returns correct taxes"
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 100}
                  {:operation "sell" :unit-cost 15.0 :quantity 50}
                  {:operation "sell" :unit-cost 15.0 :quantity 50}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0} {:tax 0} {:tax 0}] taxes)))))

(deftest testcase-2
  (testing "Returns correct taxes"
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 10000}
                  {:operation "sell" :unit-cost 20.0 :quantity 5000}
                  {:operation "sell" :unit-cost 5.0 :quantity 5000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0} {:tax 10000.0} {:tax 0}] taxes)))))


(deftest testcase-3
  (testing "Returns correct taxes"
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 10000}
                  {:operation "sell" :unit-cost 5.0 :quantity 5000}
                  {:operation "sell" :unit-cost 20.0 :quantity 3000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0} {:tax 0} {:tax 1000.0}] taxes)))))

;; {:operation s/Str :unit-cost s/Num :quantity s/Int}
;; Test 1:
;;[{"operation":"buy", "unit-cost":10.00, "quantity": 100}, {"operation":"sell", "unit-cost":15.00, "quantity": 50},{"operation":"sell", "unit-cost":15.00, "quantity": 50}]

;; Test 2:
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000}, {"operation":"sell", "unit-cost":20.00, "quantity": 5000},{"operation":"sell", "unit-cost":5.00, "quantity": 5000}]

;; Test 1+2:
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 100},{"operation":"sell", "unit-cost":15.00, "quantity": 50},{"operation":"sell", "unit-cost":15.00, "quantity": 50}]
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"sell", "unit-cost":20.00, "quantity": 5000},{"operation":"sell", "unit-cost":5.00, "quantity": 5000}]

;; Test 3;
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"sell", "unit-cost":5.00, "quantity": 5000},{"operation":"sell", "unit-cost":20.00, "quantity": 3000}]

;; Test 4;
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"buy", "unit-cost":25.00, "quantity": 5000},{"operation":"sell", "unit-cost":15.00, "quantity": 10000}]

;; Test 5
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"buy", "unit-cost":25.00, "quantity": 5000},{"operation":"sell", "unit-cost":15.00, "quantity": 10000},{"operation":"sell", "unit-cost":25.00, "quantity": 5000}]

;; Test 6
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"sell", "unit-cost":2.00, "quantity": 5000},{"operation":"sell", "unit-cost":20.00, "quantity": 2000},{"operation":"sell", "unit-cost":20.00, "quantity": 2000},{"operation":"sell", "unit-cost":25.00, "quantity": 1000}]

;; Test 7
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"sell", "unit-cost":2.00, "quantity": 5000},{"operation":"sell", "unit-cost":20.00, "quantity": 2000},{"operation":"sell", "unit-cost":20.00, "quantity": 2000},{"operation":"sell", "unit-cost":25.00, "quantity": 1000},{"operation":"buy", "unit-cost":20.00, "quantity": 10000},{"operation":"sell", "unit-cost":15.00, "quantity": 5000},{"operation":"sell", "unit-cost":30.00, "quantity": 4350},{"operation":"sell", "unit-cost":30.00, "quantity": 650}]

;; Test 8
;; [{"operation":"buy", "unit-cost":10.00, "quantity": 10000},{"operation":"sell", "unit-cost":50.00, "quantity": 10000},{"operation":"buy", "unit-cost":20.00, "quantity": 10000},{"operation":"sell", "unit-cost":50.00, "quantity": 10000}]

;; Test 9 
;; [{"operation": "buy", "unit-cost": 5000.00, "quantity": 10}, {"operation": "sell", "unit-cost": 4000.00, "quantity": 5},{"operation": "buy", "unit-cost": 15000.00, "quantity": 5},{"operation": "buy", "unit-cost": 4000.00, "quantity": 2},{"operation": "buy", "unit-cost": 23000.00, "quantity": 2},{"operation": "sell", "unit-cost": 20000.00, "quantity": 1},{"operation": "sell", "unit-cost": 12000.00, "quantity": 10},{"operation": "sell", "unit-cost": 15000.00, "quantity": 3}]