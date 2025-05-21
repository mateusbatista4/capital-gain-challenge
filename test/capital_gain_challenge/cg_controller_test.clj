(ns capital-gain-challenge.cg-controller-test
  (:require
   [capital-gain-challenge.controllers.capital-gain :as cg-controller]
   [capital-gain-challenge.core :refer :all]
   [capital-gain-challenge.models.capital-gain :as models]
   [clojure.test :refer :all]))

;; Replying the readme.md test cases

(deftest testcase-1
  (testing "Returns correct taxes"
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 100}
                  {:operation "sell" :unit-cost 15.0 :quantity 50}
                  {:operation "sell" :unit-cost 15.0 :quantity 50}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:tax 0.0} {:tax 0.0}] taxes)))))

(deftest testcase-2
  (testing "Returns correct taxes-"
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 10000}
                  {:operation "sell" :unit-cost 20.0 :quantity 5000}
                  {:operation "sell" :unit-cost 5.0 :quantity 5000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:tax 10000.0} {:tax 0.0}] taxes)))))


(deftest testcase-3
  (testing "Returns correct taxes "
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 10000}
                  {:operation "sell" :unit-cost 5.0 :quantity 5000}
                  {:operation "sell" :unit-cost 20.0 :quantity 3000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:tax 0.0} {:tax 1000.0}] taxes)))))

(deftest testcase-4
  (testing "Returns correct taxes "
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 10000}
                  {:operation "buy" :unit-cost 25.0 :quantity 5000}
                  {:operation "sell" :unit-cost 15.0 :quantity 10000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:tax 0.0} {:tax 0.0}] taxes)))))

(deftest testcase-5
  (testing "Returns correct taxes "
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 10000}
                  {:operation "buy" :unit-cost 25.0 :quantity 5000}
                  {:operation "sell" :unit-cost 15.0 :quantity 10000}
                  {:operation "sell" :unit-cost 25.0 :quantity 5000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:tax 0.0} {:tax 0.0} {:tax 10000.0}] taxes)))))

(deftest testcase-6
  (testing "Returns correct taxes "
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 10000}
                  {:operation "sell" :unit-cost 2.0 :quantity 5000}
                  {:operation "sell" :unit-cost 20.0 :quantity 2000}
                  {:operation "sell" :unit-cost 20.0 :quantity 2000}
                  {:operation "sell" :unit-cost 25.0 :quantity 1000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:tax 0.0} {:tax 0.0} {:tax 0.0} {:tax 3000.0}] taxes)))))


(deftest testcase-7
  (testing "Returns correct taxes "
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 10000}
                  {:operation "sell" :unit-cost 2.0 :quantity 5000}
                  {:operation "sell" :unit-cost 20.0 :quantity 2000}
                  {:operation "sell" :unit-cost 20.0 :quantity 2000}
                  {:operation "sell" :unit-cost 25.0 :quantity 1000}
                  {:operation "buy" :unit-cost 20.0 :quantity 10000}
                  {:operation "sell" :unit-cost 15.0 :quantity 5000}
                  {:operation "sell" :unit-cost 30.0 :quantity 4350}
                  {:operation "sell" :unit-cost 30.0 :quantity 650}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:tax 0.0} {:tax 0.0} {:tax 0.0} {:tax 3000.0} {:tax 0.0} {:tax 0.0} {:tax 3700.0} {:tax 0.0}] taxes)))))


(deftest testcase-8
  (testing "Returns correct taxes "
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10.0 :quantity 10000}
                  {:operation "sell" :unit-cost 50.0 :quantity 10000}
                  {:operation "buy" :unit-cost 20.0 :quantity 10000}
                  {:operation "sell" :unit-cost 50.0 :quantity 10000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:tax 80000.0} {:tax 0.0} {:tax 60000.0}] taxes)))))


(deftest testcase-9
  (testing "Returns correct taxes "
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 5000.00 :quantity 10}
                  {:operation "sell" :unit-cost 4000.00 :quantity 5}
                  {:operation "buy" :unit-cost 15000.00 :quantity 5}
                  {:operation "buy" :unit-cost 4000.00 :quantity 2}
                  {:operation "buy" :unit-cost 23000.00 :quantity 2}
                  {:operation "sell" :unit-cost 20000.00 :quantity 1}
                  {:operation "sell" :unit-cost 12000.00 :quantity 10}
                  {:operation "sell" :unit-cost 15000.00 :quantity 3}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:tax 0.0} {:tax 0.0} {:tax 0.0} {:tax 0.0} {:tax 0.0} {:tax 1000.0} {:tax 2400.0}] taxes)))))

(deftest testcase-10
  (testing "Returns correct taxes "
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10 :quantity 10000}
                  {:operation "sell" :unit-cost 20 :quantity 11000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:error "Can't sell more stocks than you have"}] taxes)))))

(deftest testcase-11
  (testing "Returns correct taxes and cannot sell more stocks than you have"
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "buy" :unit-cost 10 :quantity 10000}
                  {:operation "sell" :unit-cost 20 :quantity 11000}
                  {:operation "sell" :unit-cost 20 :quantity 5000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:tax 0.0} {:error "Can't sell more stocks than you have"} {:tax 10000.0}] taxes)))))

(deftest testcase-12
  (testing "Successfully blocks account after 3 errors"
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "sell" :unit-cost 20 :quantity 10000}
                  {:operation "sell" :unit-cost 20 :quantity 10000}
                  {:operation "sell" :unit-cost 20 :quantity 1000}
                  {:operation "buy" :unit-cost 10 :quantity 10000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:error "Can't sell more stocks than you have"}
              {:error "Can't sell more stocks than you have"}
              {:error "Can't sell more stocks than you have"}
              {:error "Your account is blocked"}] taxes)))))

(deftest testcase-13
  (testing "Returns correct taxes after account unblocks"
    (let [wallet (atom (models/new-wallet))
          orders [{:operation "sell" :unit-cost 20 :quantity 10000}
                  {:operation "sell" :unit-cost 20 :quantity 10000}
                  {:operation "buy" :unit-cost 10 :quantity 5000}
                  {:operation "sell" :unit-cost 20 :quantity 10000}
                  {:operation "buy" :unit-cost 10 :quantity 5000}]
          taxes (cg-controller/process-orders! wallet orders)]
      (is (= [{:error "Can't sell more stocks than you have"}
              {:error "Can't sell more stocks than you have"}
              {:tax 0.0}
              {:error "Can't sell more stocks than you have"}
              {:tax 0.0}] taxes)))))