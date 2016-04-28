(ns modern-cljs.core-test
  (:require [modern-cljs.core :refer [foo hello-world]]
            [cljs.test :refer-macros [deftest is are testing]]))

(deftest foo-test
  (testing "testing foo"
    (are [expected actual] (= expected actual)
         "Hello World!" (foo "World")
         "Hello Foo!" (foo "Foo")
         "Hello Bar!" (foo "Bar"))))

(deftest hello-world-test
  (testing "testing hello-world"
    (is (= "Hello World!" (hello-world)))))
